import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class Registry implements Serializable
{

    private static Registry instance = null;
    private ArrayList<DataFile> files;
    private int lastFileIndex;
    private String regPath;
    
    private Registry(String regPath)
    {
        this.regPath = regPath;
        this.files = new ArrayList<DataFile>();
        this.lastFileIndex = -1;
    }

    //Method to get instance of Registry (get instance from file if it was already saved)
    public static final Registry getInstance() throws IOException, ClassNotFoundException
    {
        if (instance == null)
        {
            String path = DBParams.DBPath + "registry.reg";
            try
            {
                File f = new File(path);
                if (!f.exists())
                    instance = new Registry(path);
                else
                {
                    FileInputStream fin = new FileInputStream(f);
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    instance = (Registry)oin.readObject();
                    oin.close();
                    fin.close();
                }
            }
            catch (IOException e)
            {
                System.out.println("IOException in getInstance" + e.getMessage());
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException in getInstance" + e.getMessage());
            }

        }
        return instance;
    }

    //method to add new DataFile in the list
    public void newFile(String filePath)
    {
        files.add(new DataFile(++lastFileIndex, filePath));
    }

    //Method to save the Registry instance.
    public void save () throws IOException
    {
        File f  = new File(regPath);

        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        objOut.writeObject(this);
        objOut.flush();
        objOut.close();
        fileOut.close();
    }

    public int[] pageAvailable()
    {
        int [] page = {-1, -1};

        for (DataFile file : files)
            if ((page[1] = file.pageAvailable()) != -1)
            {
                page[0] = file.getFileIndex();
                return (page);
            }

        return (page);
    }

    public void setUsedPage(PageId page)
    {
        files.get(page.getFileIdx()).setUsedPage(page.getPageIdx());
    }

    public void setFreePage(PageId page)
    {
        files.get(page.getFileIdx()).setFreePage(page.getPageIdx());
    }

    public int getLastFileIndex()
    {
        return (lastFileIndex);
    }
}
