//Class to manage frames
import java.util.ArrayList;

public class BufferManager {
	ArrayList<Frame> bufferPool;

	public BufferManager(){

	}

	public void freePage(PageId pageId, boolean valDirty) {
		for(Frame frame : this.bufferPool) {
			if(frame.pageId == pageId) {
				frame.pinCount--;
				frame.flagDirty = valDirty;
			}
		}
	}
}