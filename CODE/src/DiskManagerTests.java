import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class DiskManagerTests
{

	public static PageId testAllocPage()
	{
		DiskManager dm = DiskManager.getInstance();
		PageId p = null;
		System.out.println("TestAllocPage");
		System.out.println("Path = " + DBParams.DBPath);
		System.out.println("pageSize = " + DBParams.pageSize);
		System.out.println("maxPagesPerFile = " + DBParams.maxPagesPerFile);

		try
		{
			p = dm.AllocPage();
			System.out.println("Allocated Page : " + p);
		}
		catch (Exception e)
		{
			System.out.println("Exception : " + e.getMessage());
		}
		return (p);
	}

	public static void testWritePage(PageId p, byte[] buf)
	{
		ByteBuffer byteBuff = ByteBuffer.allocate(DBParams.pageSize);
		byteBuff.put(buf);
		DiskManager dm = DiskManager.getInstance();
		System.out.println("TestWritePage");
		try
		{
			dm.writePage(p, byteBuff);
			System.out.println("Wrote " + Arrays.toString(buf) + " to page " + p);
		}
		catch(Exception e)
		{
			System.out.println("Exception : " + e.getMessage());
		}
	}

	public static void testReadPage(PageId p)
	{
		ByteBuffer buf = ByteBuffer.allocate(DBParams.pageSize);
		DiskManager dm = DiskManager.getInstance();
		System.out.println("TestReadPage");
		try
		{
			dm.readPage(p, buf);
			System.out.println("Read " + Arrays.toString(buf.array()) + "from page " + p);
		}
		catch(Exception e)
		{
			System.out.println("Exception : " + e.getMessage());
		}
	}

	public static void testDeallocPage(PageId p)
	{
		DiskManager dm = DiskManager.getInstance();
		System.out.println("TestDeallocPage");
		try
		{
			dm.deallocPage(p);
			System.out.println("Deallocated page " + p);
		}
		catch(Exception e)
		{
			System.out.println("Exception : " + e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		String path = args[0];
		DBParams.DBPath = path;
		DBParams.pageSize = 10;
		DBParams.maxPagesPerFile = 4;
		final int TOT_PAGES = 10;
		ArrayList<PageId> pages = new ArrayList<PageId>();

		for (int i = 0; i < TOT_PAGES; i++)
		{
			pages.add(testAllocPage());
		}
		byte[] text = "BONJOUR".getBytes();
		for (int i = 0; i < TOT_PAGES; i++)
		{
			testWritePage(pages.get(i), text);
		}
		testDeallocPage(pages.get(TOT_PAGES / 2));
		pages.add(TOT_PAGES / 2, testAllocPage());
		for (int i = 0; i < TOT_PAGES; i++)
		{
			testReadPage(pages.get(i));
			testDeallocPage(pages.get(i));
		}
		DiskManager dm = DiskManager.getInstance();
		dm.cleanupFiles();
	}
}
