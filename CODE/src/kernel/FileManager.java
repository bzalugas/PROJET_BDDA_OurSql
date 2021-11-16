package kernel;

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

	public void writePageIdFromPageBuffer(PageId pageId, ByteBuffer buf, boolean first)
	{
		String tmp = pageId.getFileIdx() + "" + pageId.getPageIdx();

		int pageIdInt = Integer.valueOf(tmp);

		if(first)
			buf.putInt(0, pageIdInt);
		else
			buf.putInt(3, pageIdInt);
    }

}
