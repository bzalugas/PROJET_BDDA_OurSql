import java.nio.ByteBuffer;
import kernel.*;
import java.io.FileNotFoundException;
import java.io.IOException;



public class FileManagerTests{

    public static void testreadPageIdFromPageBuffer(){

        FileManager fm = FileManager.getInstance();
        try {
            ByteBuffer buf = BufferManager.getInstance().getPage(new PageId(1,0));
            /*System.out.println("1");
            BufferManager bm = BufferManager.getInstance();
            System.out.println("2");
            DiskManager dm = DiskManager.getInstance();
            System.out.println("3");
            PageId pageId = new PageId(1,0);
            System.out.println("4");
            ByteBuffer buf = bm.getPage(pageId);
            System.out.println("5");*/
            boolean first = true;
            PageId pid = fm.readPageIdFromPageBuffer(buf, first);
            System.out.println(pid.getFileIdx());
            System.out.println(pid.getPageIdx());
            System.out.println("6");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void testwritePageIdFromPageBuffer(){

        FileManager fm = FileManager.getInstance();
        try {
            ByteBuffer buf = BufferManager.getInstance().getPage(new PageId(1,0));

            boolean first = false;
            PageId pid = new PageId(1,0);
            System.out.println("Avant");
            System.out.println(pid.getFileIdx());
            System.out.println(pid.getPageIdx());
            fm.writePageIdFromPageBuffer(pid,buf, first);
            System.out.println("après");
            System.out.println(pid.getFileIdx());
            System.out.println(pid.getPageIdx());
        } catch (Exception e) {
            System.out.println(e);
        }


    }


    public static void main(String[] args){
         //System.out.println("abc");
         String path = args[0];
		DBParams.DBPath = path;
		DBParams.pageSize = 10;
		DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 4;
        final int TOT_PAGES = 10;
        

         //testreadPageIdFromPageBuffer();
         testwritePageIdFromPageBuffer();
       
    }


}