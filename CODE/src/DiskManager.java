import java.nio.ByteBuffer;
import java.io.File;
// class to manage disk operations (Singleton)
public class DiskManager
{
    //Single instance of DiskManager
    private static DiskManager instance = null;
    //registry of pages (singleton)
    private Registry reg = Registry.getInstance();

    private DiskManager(){
        //Private constructor to avoid default
    }

    //getter for instance
    public static final DiskManager getInstance(){
        if (instance == null)
            instance = new DiskManager();
        return instance;
    }

    public PageId AllocPage(){
        int[] page = reg.pageAvailable();
        if (page[0] == -1)
        {
            String pathFile = DBParams.DBPath + "F" + Integer.toString(reg.getLastFileIndex()) + ".df";
            File f = new File(pathFile);
        }
            //Create file -> add file & pages to reg -> page=reg.pageAvailable();
            PageId pi = new PageId(page[0], page[1]);
        return (pi);

    }

    public void deallocPage(PageId pi){

    }

    public void readPage(PageId pi, ByteBuffer buf){

    }

    public void writePage(PageId pi, ByteBuffer buf){

    }


}
