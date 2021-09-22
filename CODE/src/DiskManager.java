import java.nio.ByteBuffer;
import java.io.File;
import java.io.RandomAccessFile;
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
            //Create file -> add file & pages to reg -> page=reg.pageAvailable();
            String filePath = DBParams.DBPath + "/F" + Integer.toString(reg.getLastFileIndex() + 1) + ".df";
            File f = new File(filePath);
            reg.newFile(filePath);
            page = reg.pageAvailable();
        }
        PageId pi = new PageId(page[0], page[1]);
        reg.setUsedPage(pi);
        return (pi);

    }

    public void deallocPage(PageId pi){

    }

    public void readPage(PageId pi, ByteBuffer buf){
        long start = pi.getPageIdx * DBParams.PageSize;
        String pathFile = DBParams.DBPath + "/F" + Integer.toString(pi.FileIdx) + ".df";
        RandomAccessFile file = new RandomAccessFile(pathFile, "r");

        //place cursor on the begining of the page
        file.seek(start);
        //verify if the buffer is not too big in order to read only the asked page
        //if the buffer length is different from PageSize, read in the file byte by byte
        if (buf.array().length == DBParams.PageSize)
            file.read(buf.array());
        else
            for (long i = 0; i < DBParams.PageSize; i++)
                buf.put(i, file.readByte());
        file.close();
    }

    public void writePage(PageId pi, ByteBuffer buf){
        long start = pi.getPageIdx * DBParams.PageSize;
        String pathFile = DBParams.DBPath + "/F" + Integer.toString(pi.FileIdx) + ".df";
        RandomAccessFile file = new RandomAccessFile(pathFile, "rw");

        file.seek(start);
        if (buf.array().length == DBParams.PageSize)
            file.write(buf.array());
        else
            for (long i = 0; i < DBParams.PageSize; i++)
                file.writeByte(buf.get(i)); //maybe need to use file.write ??
        file.close();
    }


}
