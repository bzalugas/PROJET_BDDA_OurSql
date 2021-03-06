package kernel;
// Class that manage pages id.

import java.io.Serializable;

public class PageId implements Serializable
{
    private static final long   serialVersionUID = 23L;
    // Properties of the PageId class.
    private int FileIdx; // Id of the file which contains the current page.
    private int PageIdx; // Id of the current page.

    // Constructor of the PageId class.
    public PageId (int FileIdx, int PageIdx)
    {
        this.FileIdx = FileIdx;
        this.PageIdx = PageIdx;
    }

    // Stringify page id.
    public String toString()
    {
        return "(" + FileIdx + "," + PageIdx + ")";
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        PageId pid = (PageId) obj;
        return pid.getFileIdx() == this.FileIdx
            && pid.getPageIdx() == this.PageIdx;
        // return obj.getFileIdx() == this.FileIdx
        //     && obj.getPageIdx() == this.PageIdx;
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 31 * result + FileIdx;
        result = 31 * result + PageIdx;
        return result;
    }

    // Gets FileIdx.
    public int getFileIdx()
    {
        return FileIdx;
    }

    // Gets FileIdx.
    public int getPageIdx()
    {
        return PageIdx;
    }

    // Set file id.
    public void setFileIdx(int FileIdx)
    {
        this.FileIdx = FileIdx;
    }

    // Set page id.
    public void setPageIdx(int PageIdx)
    {
        this.PageIdx = PageIdx;
    }
}
