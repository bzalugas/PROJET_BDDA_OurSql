import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

/**
 * Class to manage frames
 */
public class BufferManager
{
	private static BufferManager instance = null;
	private Frame[] bufferPool;
	private DiskManager dmanager;
	private Stack unusedFrames;

	//Private constructor because BufferManage is Singleton
	private BufferManager()
	{
		this.bufferPool = new Frame[DBParams.frameCount];
		this.dmanager = DiskManager.getInstance();
		this.unusedFrames = new Stack();
		initBufferPool();
	}

	private void initBufferPool()
	{
		for (int i = 0; i < bufferPool.length; i++)
			bufferPool[i] = new Frame();
	}

	//Method to get instance
	public static final BufferManager getInstance()
	{
		if (instance == null)
			instance = new BufferManager();
		return instance;
	}

	public ByteBuffer getPage(PageId pageId) throws FileNotFoundException, IOException
	{
		int idx = -1; //idx of the first free frame (in order to visit the buffer pool only one time)

		//Check if pageId already in bufferPool
		for (int i = 0; i < bufferPool.length; i++)
		{
			if (bufferPoolp[i].getPageId().equals(pageId))
			{
				//bufferPool[i].setPinCount(bufferPool[i].getPinCount() + 1);
				bufferPool[i].incrementPinCount();
				return (bufferPool[i].getBuffer());
			}
			else if (idx == -1 && bufferPool[i].isEmpty())
				idx = i;
		}
		//if not, check if there is an empty frame (idx != -1 means the first loop found a free frame)
		if (idx != -1)
		{
			bufferPool[idx].setPageId(pageId);
			bufferPool[idx].setPinCount(1);
			bufferPool[idx].setFlagDirty(false);
			dmanager.readPage(pageId, bufferPool[idx].getBuffer());
		}
		else
		{
			//Politique MRU => ne pas oublier de save si dirty == 1
			// => Exception a creer si stack est vide
			Frame old = unusedFrames.pop();
			//Check dirty : if true, save
			if (old.getFlagDirty() == true)
				dmanager.writePage(old.getPageId(), old.getBuffer());
			//search the frame in bufferPool to replace it
			int i = 0;
			while (i < bufferPool.length && !bufferPool[i].equals(old))
				i++;
			//Replace old frame by new frame
			bufferPool[i].setPageId(pageId);
			bufferPool[i].setPinCount(1);
			bufferPool[i].setFlagDirty(false);
			dmanager.readPage(pageId, bufferPool[i].getBuffer());
		}
		return (bufferPool[i].getBuffer());
	}

	public void freePage(PageId pageId, boolean valDirty)
	{
		int i = 0;

		while (bufferPool[i].getPageId().equals(pageId) == false)
			i++;
		//bufferPool[i].setPinCount(bufferPool[i].getPinCount() - 1);

		//FAUT-IL CONSIDERER LE CAS OU ON REND DEUX FOIS LA MEME PAGE (PIN_COUNT < 0) ?
		bufferPool[i].decrementPinCount();
		bufferPool[i].setFlagDirty(valDirty);

		//If pinCount == 0, this is the most recently used
		if (buffer[i].getPinCount() == 0)
			unusedFrames.push(bufferPool[i]);
	}

	// Method that write all pages and reset all properties of all the frames.
	public void flushAll() throws FileNotFoundException, IOException
	{
		for(Frame frame : this.bufferPool)
		{
			if(frame.getFlagDirty())
				this.dmanager.writePage(frame.getPageId(), frame.getBuffer());
				
			frame.setPageId(null);
			frame.setPinCount(0);
			frame.setFlagDirty(false);
		}
	}
}
