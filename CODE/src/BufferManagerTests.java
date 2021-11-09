
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import kernel.*;
import kernel.exceptions.TooManyFreePageException;
import java.util.Arrays;


public class BufferManagerTests {

    public static void testGetPage() throws FileNotFoundException, IOException{
        BufferManager bm = BufferManager.getInstance();
        DiskManager dm = DiskManager.getInstance();
        PageId pageId = dm.AllocPage();
        ByteBuffer buf = bm.getPage(pageId);

        System.out.println("testGetPage");
        System.out.println(Arrays.toString(buf.array()));
    }

    public static void testFreePage() throws FileNotFoundException, IOException, TooManyFreePageException{
        BufferManager bm = BufferManager.getInstance();
        DiskManager dm = DiskManager.getInstance();
        PageId pageId = dm.AllocPage();

        System.out.println("testFreePage");
        bm.getPage(pageId);
        System.out.println("Page demandee = " + pageId);
        System.out.println("BufferPool avant free : " + Arrays.toString(bm.getBufferPool()));
        bm.freePage(pageId, true);
        System.out.println("BufferPool apres  free : " + Arrays.toString(bm.getBufferPool()));

    }

    public static void testFlushAll() throws FileNotFoundException, IOException, TooManyFreePageException{
        BufferManager bm = BufferManager.getInstance();
        DiskManager dm = DiskManager.getInstance();
        ByteBuffer buff= ByteBuffer.allocate(DBParams.pageSize);
        PageId pageId = dm.AllocPage();

        System.out.println("testFreePage");

        bm.freePage(pageId, true);

        dm.readPage(pageId, buff);
        System.out.print("Before flush all : " + Arrays.toString(buff.array()));

        bm.flushAll();

        dm.readPage(pageId, buff);
        System.out.print("After flush all : " + Arrays.toString(buff.array()));
    }

    public static void main(String[] args){
        String path = args[0];
		DBParams.DBPath = path;
		DBParams.pageSize = 10;
		DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 4;
		final int TOT_PAGES = 10;

        try {
            testGetPage();
            testFreePage();
            testFlushAll();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        } catch (TooManyFreePageException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
}
