// Class that manage Frames.

public class Frame {
    private PageId pageId;
    private int pinCount;
    private byte flagDirty;

    // Getter of the pageId property
    public PageId getPageId(){
        return this.pageId;
    }

    // Getter of the pinCount property
    public int getPinCount(){
        return this.pinCount;
    }

    // Getter of the flagDirty property
    public byte getFlagDirty(){
        return this.FlagDirty;
    }

    // Setter of the pageId property
    public void setPageId(PageId pageId) {
        this.pageId = pageId;
    }

    // Setter of the pinCount property
    public void setPinCount(int pinCount) {
        this.pinCount = pinCount;
    }

    // Setter of the flagDirty property
    public void flagDirty(byte flagDirty) {
        this.flagDirty = flagDirty;
    }


}