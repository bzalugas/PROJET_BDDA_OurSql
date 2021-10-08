import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class DiskManagerTests
{
	public static void testAllocPage(String path)
	{
		DBParams.DBPath = path;
		DBParams.pageSize = 10;
		DBParams.maxPagesPerFile = 4;

		final int TOT_PAGES = 30;

		DiskManager dm = DiskManager.getInstance();
		ArrayList<PageId> pages = new ArrayList<PageId>(TOT_PAGES);

		System.out.println("TestAllocPage");
		System.out.println("Path = " + DBParams.path);
		System.out.println("pageSize = " + DBParams.pageSize);
		System.out.println("maxPagesPerFile = " + DBParams.maxPagesPerFile);

		try
		{
			System.out.println("Allocation of " + TOT_PAGES + " pages : ");
			for (int i = 0; i < TOT_PAGES; i++)
			{
				pages.add(dm.AllocPage());
				System.out.println("Allocated Page : " + pages.get(i).toString());

				ByteBuffer buf = ByteBuffer.allocate(DBParams.pageSize);
				buf.putChar('B').putChar('O').putChar('N').putChar('J').putChar('O').putChar('U').putChar('R');
				dm.writePage(pages.get(i), buf);
				System.out.println("Wrote : " + Arrays.toString(buf.array()));
			}
			for (int i = 0; i < TOT_PAGES; i++)
			{
				dm.deallocPage(pages.get(i));
				System.out.println("dealloc " + pages.get(i).toString());
			}
			System.out.println("Try to alloc now they all free : ");
			PageId p = dm.AllocPage();
			System.out.println("Allocated Page : " + p.toString());
			for (int i = 0; i < TOT_PAGES; i++)
			{
				ByteBuffer buf = ByteBuffer.allocate(DBParams.pageSize);
				dm.readPage(pages.get(i), buf);
				System.out.println("read " + pages.get(i) + " : " + Arrays.toString(buf.array()));
			}
		} catch (Exception e){
			System.out.println("Error : " + e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		String path = args[0];
		testAllocPage(path);
	}
}
