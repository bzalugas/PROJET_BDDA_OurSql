// Class that manage Frame's properties and method.

public class Frame {
    private PageId pageId;
    private int pinCount;
    private boolean flagDirty;

    // Constructur of the Frame class
    public void Frame(PageId pageId, int pinCount, boolean flagDirty) {
        this.pageId = pageId;
        this.pinCount = pinCount;
        this.flagDirty = flagDirty;
    }

    // Getter of the pageId property
    public PageId getPageId(){
        return this.pageId;
    }

    // Getter of the pinCount property
    public int getPinCount(){
        return this.pinCount;
    }

    // Getter of the flagDirty property
    public boolean getFlagDirty(){
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
    public void flagDirty(boolean flagDirty) {
        this.flagDirty = flagDirty;
    }


}