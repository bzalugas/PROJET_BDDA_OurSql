
//Main class of the project.

import java.io.File;

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
        }
        catch(Exception e)
        {
            System.out.println("exception : " + e.getMessage());
        }
    }
}
