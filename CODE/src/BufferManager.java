//Class to manage frames
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Stack;

public class BufferManager{
	private static instance = null;
	private Frame[] bufferPool;
	private DiskManager dmanager;

	//Private constructor because BufferManage is Singleton
	private BufferManager(){
		this.bufferPool = new Frame[DBParams.frameCount];
		this.dmanager = DiskManager.getInstance();
	}

	//Method to get instance
	public static final BufferManager getInstance() {
		if (instance == null)
			instance = new BufferManager();
		return instance;
	}

	public void freePage(PageId pageId, boolean valDirty) {
		for(Frame frame : this.bufferPool){
			if(frame.getPageId() == pageId) {
				frame.setPinCount(frame.getPinCount()-1);
				frame.setFlagDirty(valDirty);
			}
		}
	}

	public ByteBuffer getPage(PageId pageId) throws FileNotFoundException, IOException {
		for(Frame frame : this.bufferPool){
			if(frame.getPageId().equals(pageId)){
				f.getBuffer

			} else {
				ByteBuffer buff =  ByteBuffer.allocate(DBParams.pageSize);
				dmanager.readPage(pageId,buff);

				Frame f = new Frame(pageId,buff);
				f.setPinCount(f.getPinCount()+1)
				this.bufferPool.add(f);
			}
		}
		 return buff;
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
