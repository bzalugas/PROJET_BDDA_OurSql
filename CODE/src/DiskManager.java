// class to manage disk operations (Singleton)
public class DiskManager
{
    //Single instance of DiskManager
    private static final DiskManager INSTANCE = null;

    private DiskManager(){
        //Private constructor to avoid default
    }

    //getter for instance
    public static final DiskManager getInstance(){
        if (INSTANCE == null)
            INSTANCE = new DiskManager();
        return INSTANCE;
    }

    public PageId AllocPage(){

    }

    public void deallocPage(PageId pi){

    }

    public void readPage(PageId pi, ByteBuffer buf){

    }

    public void writePage(PageId pi, ByteBuffer buf){

    }


}
