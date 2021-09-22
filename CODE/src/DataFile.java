//class for a single DataFile
public class DataFile
{
    //array to keep trace of which page is available
    private byte[] pagesStatus = null;
    private String filePath = null;
    private int fileIndex = -1;

    public DataFile(int fileIndex, String filePath){
        pagesStatus = new byte[DBParams.maxPagesPerFile];
        this.fileIndex = fileIndex;
        this.filePath = filePath;
        this.init();
    }

    //Initiate the byte array to 0
    private void init(){
        if (pagesStatus != null)
            for (int i = 0; i < DBParams.maxPagesPerFile; i++)
                pagesStatus[i] = 0;
    }

    //return the index of first available page in file, or -1 if there is not
    public int pageAvailable(){
        for (int i = 0; i < DBParams.maxPagesPerFile; i++)
            if (pagesStatus[i] == 0)
                return (i);
        return (-1);
    }

    public int getFileIndex(){
        return (fileIndex);
    }

    public String getFilePath(){
        return (filePath);
    }

    public void setUsedPage(int pageIdx){
        pagesStatus[pageIdx] = 1;
    }

    public void setFreePage(int pageIdx){
        pagesStatus[pageIdx] = 0;
    }
}
