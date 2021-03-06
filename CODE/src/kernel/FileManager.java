package kernel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.EmptyStackException;

import kernel.exceptions.TooManyFreePageException;

public class FileManager {
    private static FileManager instance = null;

    private FileManager()
	{

    }

	//Method to get instance
	public static final FileManager getInstance()
	{
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	/** Read a pageId from a buffer.
	 * 
	 * @param buf 
	 * @param first 
	 * @return pageId
	 */
    public PageId readPageIdFromPageBuffer(ByteBuffer buf, boolean first)
	{
		int pageIdint = first 
							? buf.getInt(0)
							: buf.getInt(3);

		int fileIdx = pageIdint / 10;
		int pageIdx = pageIdint % 10;
		PageId pageId = new PageId(fileIdx, pageIdx); 

		return pageId;
    }

	/** Write a page id into a buffer.
	 * 
	 * @param pageId
	 * @param buf
	 * @param first
	 */
	public void writePageIdToPageBuffer(PageId pageId, ByteBuffer buf, boolean first)
	{
		String tmp = pageId.getFileIdx() + "" + pageId.getPageIdx();

		int pageIdInt = Integer.valueOf(tmp);

		if(first)
			buf.putInt(0, pageIdInt);
		else
			buf.putInt(8, pageIdInt);
    }
	/** Creates a new HeaderPage
	 * 
	 * @return PageId of the new HeaderPage
	 * @throws IOException
	 * @throws TooManyFreePageException
	 */
	public PageId createHeaderPage() throws IOException, TooManyFreePageException
	{
		DiskManager dm = DiskManager.getInstance();
		BufferManager  bm = BufferManager.getInstance();

		PageId pageId = dm.AllocPage();

		ByteBuffer buf = bm.getPage(pageId);

		writePageIdToPageBuffer(new PageId(-1, 0), buf, true);
		writePageIdToPageBuffer(new PageId(-1, 0), buf, false);

		bm.freePage(pageId, true);

		return pageId;
	}
	/** Add a new data page to the heap file of the given relation.
	 * 
	 * @param relInfo
	 * @return PageId of the new DataPage.
	 * @throws IOException
	 * @throws TooManyFreePageException
	 */
	public PageId addDataPage(RelationInfo relInfo) throws IOException, TooManyFreePageException
	{
		DiskManager dm = DiskManager.getInstance();
		BufferManager bm = BufferManager.getInstance();

		PageId pageId = dm.AllocPage();

		ByteBuffer bufHeaderPage = bm.getPage(relInfo.getHeaderPageId());
		ByteBuffer buf = bm.getPage(pageId);
		PageId nextPageId = readPageIdFromPageBuffer(bufHeaderPage, true);

		writePageIdToPageBuffer(pageId, bufHeaderPage, true);
		writePageIdToPageBuffer(relInfo.getHeaderPageId(), buf, true);
		writePageIdToPageBuffer(nextPageId, buf, false);
		
		bm.freePage(pageId, true);
		bm.freePage(relInfo.getHeaderPageId(), true);

		return pageId;
	}

	/** Gets a free data page from the given relation.
	 * 
	 * @param relInfo
	 * @return PageId of the free data pag.
	 * @throws FileNotFoundException
	 * @throws EmptyStackException
	 * @throws IOException
	 * @throws TooManyFreePageException
	 */
	public PageId getFreeDataPageId(RelationInfo relInfo) throws FileNotFoundException, EmptyStackException, IOException, TooManyFreePageException
	{ 
		BufferManager bm = BufferManager.getInstance();
		PageId pageIdHeaderPage = relInfo.getHeaderPageId();
		ByteBuffer bufHp = bm.getPage(pageIdHeaderPage);
		PageId pageId = readPageIdFromPageBuffer(bufHp, true);

		if(pageId.getFileIdx() == -1){
			return this.addDataPage(relInfo);
		}

		// bm.freePage(pageId);
		bm.freePage(pageIdHeaderPage, false);

		return pageId;
	}

	/** Write a new record to a given data page.
	 * 
	 * @param relInfo
	 * @param record
	 * @param pageId
	 * @return Rid
	 * @throws FileNotFoundException
	 * @throws EmptyStackException
	 * @throws IOException
	 * @throws TooManyFreePageException
	 */
	public Rid writeToDataPage(RelationInfo relInfo, Record record, PageId pageId) throws FileNotFoundException, EmptyStackException, IOException, TooManyFreePageException
	{
		BufferManager bm = BufferManager.getInstance();
		ByteBuffer headerPage = bm.getPage(relInfo.getHeaderPageId());
		ByteBuffer bufCurPageId = bm.getPage(pageId);
		int freeSlot = 0, freePos = 0, cursor = 16 + relInfo.calculSlotCount();

		for(int i = 16; i < relInfo.calculSlotCount() + 16; i++){
			if(bufCurPageId.get(i) == 0){
				freeSlot++;
				if(freeSlot == 1){
					freePos = i - 16;
				}
			}
		}

		for(int i = 0; i < freePos; i++){
			cursor += relInfo.calculRecordSize();
		}

		record.writeToBuffer(bufCurPageId, cursor);
		bufCurPageId.put(16 + freePos, (byte)1);
		if(freeSlot == 1){
			PageId prevPage = readPageIdFromPageBuffer(bufCurPageId, true);
			PageId nextPage = readPageIdFromPageBuffer(bufCurPageId, false);

			ByteBuffer prevBuf = bm.getPage(prevPage);
			ByteBuffer nextBuf = bm.getPage(nextPage);

			writePageIdToPageBuffer(pageId, headerPage, false);
			writePageIdToPageBuffer(relInfo.getHeaderPageId(), bufCurPageId, true);

			if(!prevPage.equals(relInfo.getHeaderPageId())){
				writePageIdToPageBuffer(nextPage, prevBuf, false);
				writePageIdToPageBuffer(prevPage, nextBuf, true);
			} else {
				writePageIdToPageBuffer(nextPage, headerPage, true);
				writePageIdToPageBuffer(relInfo.getHeaderPageId(), nextBuf, true);
				
			}

			bm.freePage(prevPage, true);
			bm.freePage(nextPage, true);

		}

		bm.freePage(pageId, true);
		bm.freePage(relInfo.getHeaderPageId(), true);


		return new Rid(pageId, freePos);
	}

	public ArrayList<Record> getRecordsInDataPage (RelationInfo relInfo, PageId pageId) throws FileNotFoundException, EmptyStackException, IOException{
		BufferManager bm = BufferManager.getInstance();
		ArrayList<Record> res = new ArrayList<>();
		ByteBuffer bufCurPageId = bm.getPage(pageId);
		int cursor = 16 + relInfo.calculSlotCount();

		for(int i = 0; i < relInfo.calculSlotCount(); i++){
			byte [] newBuf = new byte[relInfo.calculRecordSize()];
			Record newRecord = new Record(relInfo);
			
			for(int j = 0; j < relInfo.calculRecordSize(); j++){
				newBuf[j] = bufCurPageId.get(cursor + j);
			}
			
			newRecord.readFromBuffer(ByteBuffer.wrap(newBuf), 0);
			res.add(newRecord);

			cursor += relInfo.calculRecordSize();
		}
		return res;
	}

	public Rid insertRecordIntoRelation(RelationInfo relInfo, Record record) throws FileNotFoundException, EmptyStackException, IOException, TooManyFreePageException{
		return this.writeToDataPage(relInfo, record, this.getFreeDataPageId(relInfo));
	}

	public ArrayList<Record> getAllRecords(RelationInfo relInfo) throws FileNotFoundException, EmptyStackException, IOException, TooManyFreePageException{
		// System.out.println("nb records = " + );
		return this.getRecordsInDataPage(relInfo, this.getFreeDataPageId(relInfo));
	}
}
