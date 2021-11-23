package kernel;

import java.io.IOException;
import java.nio.ByteBuffer;

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

	public PageId createHeaderPage() throws IOException{
		DiskManager dm = DiskManager.getInstance();
		BufferManager  bm = BufferManager.getInstance();
		PageId pageId = dm.AllocPage();

		ByteBuffer buf = bm.getPage(pageId);

		writePageIdFromPageBuffer(new PageId(-1, 0), buf, true);
		writePageIdFromPageBuffer(new PageId(-1, 0), buf, false);

		return pageId;
	}

}
