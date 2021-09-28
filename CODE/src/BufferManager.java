//Class to manage frames
import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.io.FileNotFoundException;
import java.io.IOException;


public class BufferManager{
	private ArrayList<Frame> bufferPool;
	private DiskManager dmanager = DiskManager.getInstance();

	public BufferManager(){

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
		 ByteBuffer buff =  ByteBuffer.allocate(DBParams.pageSize);
		 dmanager.readPage(pageId,buff);

		 Frame f = new Frame(pageId,buff);
		 f.setPinCount(f.getPinCount()+1);

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