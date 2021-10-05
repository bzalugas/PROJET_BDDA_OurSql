//Class to manage frames
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

public class BufferManager{
	private static BufferManager instance = null;
	private Frame[] bufferPool;
	private DiskManager dmanager;

	//Private constructor because BufferManage is Singleton
	private BufferManager(){
		this.bufferPool = new Frame[DBParams.frameCount];
		this.dmanager = DiskManager.getInstance();
		initBufferPool();
	}

	private void initBufferPool() {
		for (int i = 0; i < bufferPool.length; i++)
			bufferPool[i] = new Frame();
	}

	//Method to get instance
	public static final BufferManager getInstance() {
		if (instance == null)
			instance = new BufferManager();
		return instance;
	}

	public void freePage(PageId pageId, boolean valDirty) {
		for(Frame frame : this.bufferPool){
			if(frame.getPageId().equals(pageId)) {
				frame.setPinCount(frame.getPinCount()-1);
				frame.setFlagDirty(valDirty);
			}
			//Ajouter a la stack si pinCount == 0
		}
	}

	private int getFreeFrameId() {
		for (int i = 0; i < bufferPool.length; i++)
			if (bufferPool[i].getPageId().getFileIdx() == -1)
				return (i);
		return (-1);
	}

	public ByteBuffer getPage(PageId pageId) throws FileNotFoundException, IOException {
		int i;

		for (Frame f : bufferPool){
			if (f.getPageId().equals(pageId)){
				f.setPinCount(f.getPinCount() + 1);
				return (f.getBuffer());
			}
		}
		if ((i = getFreeFrameId()) != -1)
		{
			bufferPool[i].setPageId(pageId);
			bufferPool[i].setPinCount(1);
			bufferPool[i].setFlagDirty(false);
			dmanager.readPage(pageId, bufferPool[i].getBuffer());
		}
		else
		{
			//Politique MRU
		}

		return (bufferPool[i].getBuffer());
	}

	// Method that write all pages and reset all properties of all the frames.
	public void flushAll() throws FileNotFoundException, IOException {
		for(Frame frame : this.bufferPool){
			if(frame.getFlagDirty())
				this.dmanager.writePage(frame.getPageId(), frame.getBuffer());
				
			frame.setPageId(null);
			frame.setPinCount(0);
			frame.setFlagDirty(false);
		}
	}
}
