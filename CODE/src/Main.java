//Main class of the project.

import java.io.File;
import kernel.*;

public class Main{

    public static void main(String[] args)
    {
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4096;
        DBParams.maxPagesPerFile = 4;
        try
        {
            DiskManager dm = DiskManager.getInstance();
            PageId page;
            Registry reg = Registry.getInstance();
            reg.save();
            Catalog cat = Catalog.getInstance();
            cat.finish();
        }
        catch(Exception e)
        {
            System.out.println("exception : " + e.getMessage());
        }
    }
}
