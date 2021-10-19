
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import kernel.*;
import kernel.exceptions.TooManyFreePageException;


public class BufferManagerTests {

    public static void testGetPage() throws FileNotFoundException, IOException{
        BufferManager bm = BufferManager.getInstance();
        PageId pageId = new PageId(1, 1);
        ByteBuffer buf = bm.getPage(pageId);

        System.out.println(buf);

    }

    public static void testFreePage() throws FileNotFoundException, IOException, TooManyFreePageException{
        BufferManager bm = BufferManager.getInstance();
        PageId pageId = new PageId(1, 1);
        bm.getPage(pageId);

        System.out.println("BufferPool avant free : " + bm.getBufferPool());

        bm.freePage(pageId, true);

        System.out.println("BufferPool avant free : " + bm.getBufferPool());

    }

    public static void main(String[] args){
        String path = args[0];
		DBParams.DBPath = path;
		DBParams.pageSize = 10;
		DBParams.maxPagesPerFile = 4;
		final int TOT_PAGES = 10;

        try {
            testGetPage();
            testFreePage();
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