package all;
// Class that manage Frame's properties and method.

import java.nio.ByteBuffer;

public class Frame
{
    private PageId pageId;
    private int pinCount;
    private boolean flagDirty;
    private ByteBuffer buffer;

    // Constructor of the Frame class
    public Frame()
    {
        pageId = new PageId(-1, 0);
        pinCount = 0;
        flagDirty = false;
        buffer = ByteBuffer.allocate(DBParams.pageSize);
    }

    // Getter of the pageId property
    public PageId getPageId()
    {
        return this.pageId;
    }

    // Getter of the pinCount property
    public int getPinCount()
    {
        return this.pinCount;
    }

    // Getter of the flagDirty property
    public boolean getFlagDirty()
    {
        return this.flagDirty;
    }

    // Getter of the buffer property
    public ByteBuffer getBuffer()
    {
        return this.buffer;
    }

    // Setter of the pageId property
    public void setPageId(PageId pageId)
    {
        this.pageId = pageId;
    }

    // Setter of the pinCount property
    public void setPinCount(int pinCount)
    {
        this.pinCount = pinCount;
    }

    // Setter of the flagDirty property
    public void setFlagDirty(boolean flagDirty)
    {
        this.flagDirty = flagDirty;
    }

    // Setter of the buffer property
    public void setBuffer(ByteBuffer buffer)
    {
        this.buffer = buffer;
    }

    public void incrementPinCount()
    {
        this.pinCount++;
    }

    public void decrementPinCount()
    {
        this.pinCount--;
    }

    public boolean equals(Frame f)
    {
        return (this.pageId.equals(f.getPageId()));
    }

    public boolean isEmpty()
    {
        if (pageId.getFileIdx() == -1)
            return (true);
        return (false);
    }

    public String toString()
    {
        return ("PageId : " + pageId + ", pinCount = " + pinCount + ", dirty : " + flagDirty);
    }
}
