// Class that manage Frame's properties and method.

import java.nio.ByteBuffer;

public class Frame {
    private PageId pageId;
    private int pinCount = 0;
    private boolean flagDirty = false;
    private ByteBuffer buffer;

    // Constructur of the Frame class
    public Frame(PageId pageId, ByteBuffer buffer) {
        this.pageId = pageId;
        this.buffer = buffer;
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
        return this.flagDirty;
    }

    // Getter of the buffer property
    public ByteBuffer getBuffer(){
        return this.buffer;
    }

    // Setter of the pageId property
    public void setPageId(PageId pageId) {
        this.pageId = pageId;
    }

    // Setter of the pinCount property
    public void setPinCount(int pinCount){
        this.pinCount = pinCount;
    }

    // Setter of the flagDirty property
    public void setFlagDirty(boolean flagDirty) {
        this.flagDirty = flagDirty;
    }

    // Setter of the buffer property
    public ByteBuffer setBuffer(ByteBuffer buffer){
         this.buffer = buffer;
    }

}