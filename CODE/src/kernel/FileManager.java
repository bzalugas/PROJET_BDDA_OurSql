package kernel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
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
	public void writePageIdFromPageBuffer(PageId pageId, ByteBuffer buf, boolean first)
	{
		String tmp = pageId.getFileIdx() + "" + pageId.getPageIdx();

		int pageIdInt = Integer.valueOf(tmp);

		if(first)
			buf.putInt(0, pageIdInt);
		else
			buf.putInt(3, pageIdInt);
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

		writePageIdFromPageBuffer(new PageId(-1, 0), buf, true);
		writePageIdFromPageBuffer(new PageId(-1, 0), buf, false);

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


		writePageIdFromPageBuffer(pageId, bufHeaderPage, true);
		writePageIdFromPageBuffer(relInfo.getHeaderPageId(), buf, false);
		
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
	 */
	public PageId getFreeDataPageId(RelationInfo relInfo) throws FileNotFoundException, EmptyStackException, IOException
	{ //to finish
		BufferManager bm = BufferManager.getInstance();
		PageId pageIdHeaderPage = relInfo.getHeaderPageId();
		ByteBuffer buf = bm.getPage(pageIdHeaderPage);
		PageId currentPageId = readPageIdFromPageBuffer(buf, true);

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
	 */
	public Rid writeToDataPage(RelationInfo relInfo, Record record, PageId pageId) throws FileNotFoundException, EmptyStackException, IOException
	{//to finish
		BufferManager bm = BufferManager.getInstance();

		bm.getPage(pageId);

	}
}
