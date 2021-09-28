//Class to manage frames
import java.util.ArrayList;

public class BufferManager {
	private ArrayList<Frame> bufferPool;
	private DiskManager dmanager = DiskManager.getInstance();

	public BufferManager(){

	}

	public void freePage(PageId pageId, boolean valDirty) {
		for(Frame frame : this.bufferPool) {
			if(frame.getPageId() == pageId) {
				frame.setPinCount(frame.getPinCount()-1);
				frame.setFlagDirty(valDirty);
			}
		}
	}

	public ByteBuffer getPage(PageId pageId) {
		 ByteBuffer buff = new ByteBuffer();
		 buff.allocate(DBParams.pageSize);
		 dmanager.readPage(pageId,buff);

		 Frame f = new Frame(pageId,buff);
		 f.setPinCount(f.getPinCount()+1);

		 return buff;
	}
}