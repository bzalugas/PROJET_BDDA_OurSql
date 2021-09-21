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
}
