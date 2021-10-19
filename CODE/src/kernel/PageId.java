package kernel;
// Class that manage pages id.

public class PageId
{
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

    // Test if two page id are equals.
    public boolean equals(PageId obj)
    {
        return obj.getFileIdx() == this.FileIdx
            && obj.getPageIdx() == this.PageIdx;
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
