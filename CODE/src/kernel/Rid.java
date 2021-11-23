package kernel;

public class Rid {
    PageId pageId;
    int slotIdx;

    public Rid(PageId pageId, int slotIdx){
        this.pageId = pageId;
        this.slotIdx = slotIdx;
    }
    
}
