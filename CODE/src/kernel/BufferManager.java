package kernel;

import java.nio.ByteBuffer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Stack;
import kernel.exceptions.*;

/**
 * Class to manage frames
 */
public class BufferManager
{
	private static BufferManager instance = null;
	private Frame[] bufferPool;
	private DiskManager dmanager;
	private Stack<Frame> unusedFrames;

	//Private constructor because BufferManage is Singleton
	private BufferManager()
	{
		this.bufferPool = new Frame[DBParams.frameCount];
		this.dmanager = DiskManager.getInstance();
		this.unusedFrames = new Stack<Frame>();
		initBufferPool();
	}

	private void initBufferPool()
	{
		for (int i = 0; i < DBParams.frameCount; i++)
			bufferPool[i] = new Frame();
	}

	//Method to get instance
	public static final BufferManager getInstance()
	{
		if (instance == null)
			instance = new BufferManager();
		return instance;
	}

	public ByteBuffer getPage(PageId pageId) throws FileNotFoundException, IOException,
													EmptyStackException
	{
		int idx = -1; //idx of the first free frame (in order to visit the buffer pool only one time)
		// System.out.println("bf length = " + bufferPool.length);
		//Check if pageId already in bufferPool
		for (int i = 0; i < bufferPool.length; i++)
		{
			if (bufferPool[i].getPageId().equals(pageId))
			{
				bufferPool[i].incrementPinCount();
				return (bufferPool[i].getBuffer());
			}
			else if (idx == -1 && bufferPool[i].isEmpty()){
				idx = i;
			}
				
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
			//Politique MRU
			Frame old = unusedFrames.pop();
			//Check dirty : if true, save
			if (old.getFlagDirty() == true)
				dmanager.writePage(old.getPageId(), old.getBuffer());
			//search the frame in bufferPool to replace it
			idx = 0;
			while (idx < bufferPool.length && !bufferPool[idx].equals(old))
				idx++;
			//Replace old frame by new frame
			bufferPool[idx].setPageId(pageId);
			bufferPool[idx].setPinCount(1);
			bufferPool[idx].setFlagDirty(false);
			dmanager.readPage(pageId, bufferPool[idx].getBuffer());
		}
		return (bufferPool[idx].getBuffer());
	}

	public void freePage(PageId pageId, boolean valDirty) throws TooManyFreePageException
	{
		int i = 0;

		while (i < bufferPool.length && (bufferPool[i].getPageId().equals(pageId) == false))
			i++;
		//bufferPool[i].setPinCount(bufferPool[i].getPinCount() - 1);

		if (i >= bufferPool.length || bufferPool[i].getPinCount() == 0)
			throw (new TooManyFreePageException());
		bufferPool[i].decrementPinCount();
		bufferPool[i].setFlagDirty(valDirty);

		//If pinCount == 0, this is the most recently used
		if (bufferPool[i].getPinCount() == 0)
			unusedFrames.push(bufferPool[i]);
	}

	// Method that write all pages and reset all properties of all the frames.
	public void flushAll() throws FileNotFoundException, IOException
	{
		for(Frame frame : this.bufferPool)
		{
			if(frame.getFlagDirty())
				this.dmanager.writePage(frame.getPageId(), frame.getBuffer());
				
			frame.setPageId(new PageId(-1, 0));
			frame.setPinCount(0);
			frame.setFlagDirty(false);
		}
	}

	/**
	 * Resets all values of the BufferManager
	 */
	public void reset()
	{
		for (Frame frame : bufferPool)
		{
			frame.setPageId(new PageId(-1, 0));
			frame.setPinCount(0);
			frame.setFlagDirty(false);
			frame.setBuffer(ByteBuffer.allocate(DBParams.pageSize));
		}
		unusedFrames = new Stack<Frame>();
	}

	public Frame[] getBufferPool() {
		return this.bufferPool;
	}

	public void setBufferPool(Frame[] bufferPool) {
		this.bufferPool = bufferPool;
	}

	public DiskManager getDmanager() {
		return this.dmanager;
	}

	public void setDmanager(DiskManager dmanager) {
		this.dmanager = dmanager;
	}

	public Stack<Frame> getUnusedFrames() {
		return this.unusedFrames;
	}

	public void setUnusedFrames(Stack<Frame> unusedFrames) {
		this.unusedFrames = unusedFrames;
	}

}
