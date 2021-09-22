import java.nio.ByteBuffer;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public void readPage(PageId pi, ByteBuffer buf) throws FileNotFoundException, IOException {
        int start = pi.getPageIdx() * DBParams.pageSize;
        String pathFile = DBParams.DBPath + "/F" + Integer.toString(pi.getFileIdx()) + ".df";
        RandomAccessFile file = new RandomAccessFile(pathFile, "r");

        //place cursor on the begining of the page
        file.seek(start);
        //verify if the buffer is not too big in order to read only the asked page
        //if the buffer length is different from PageSize, read in the file byte by byte
        if (buf.array().length == DBParams.pageSize)
            file.read(buf.array());
        else
            for (int i = 0; i < DBParams.pageSize; i++)
                buf.put(i, file.readByte());
        file.close();
    }

    public void writePage(PageId pi, ByteBuffer buf) throws FileNotFoundException, IOException {
        int start = pi.getPageIdx() * DBParams.pageSize;
        String pathFile = DBParams.DBPath + "/F" + Integer.toString(pi.getFileIdx()) + ".df";
        RandomAccessFile file = new RandomAccessFile(pathFile, "rw");

        file.seek(start);
        if (buf.array().length == DBParams.pageSize)
            file.write(buf.array());
        else
            for (int i = 0; i < DBParams.pageSize; i++)
                file.writeByte(buf.get(i)); //maybe need to use file.write ??
        file.close();
    }


}
