import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.IOException;


public class Registry implements Serializable {

    private static Registry instance = null;
    private ArrayList<DataFile> files = new ArrayList<DataFile>();
    private int lastFileIndex = -1;
    private File file;
    
    private Registry(){

    }

    public static final Registry getInstance(){
        if (instance == null)
            instance = new Registry();
        return instance;
    }

    //method to add new DataFile in the list
    public void newFile(String filePath){
        files.add(new DataFile(++lastFileIndex, filePath));
    }

    //Method to save the Registry instance.
    public void save () throws IOException{
            this.file  = new File("./DB/registry.reg");

            FileOutputStream fileOut = new FileOutputStream(this.file);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(this);
            objOut.flush();
            objOut.close();
            fileOut.close();

    }

    // Method that retrieve an instance of Registry from a file.
    static Registry retrieve(File file) throws IOException {
        if(file.exists()){
            try {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream oin = new ObjectInputStream(fin);

                Registry reg = (Registry)oin.readObject();

                oin.close();
                fin.close();

                return reg;

            } catch (IOException e) {
                System.out.println("IOException : " + e);
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFoundException : " + e);
            }
        }
    return null;    
    
    }

    public int[] pageAvailable() {
        int [] page = {-1, -1};

        for (DataFile file : files)
            if ((page[1] = file.pageAvailable()) != -1)
            {
                page[0] = file.getFileIndex();
                return (page);
            }

        return (page);
    }

    public void setUsedPage(PageId page){
        files.get(page.getFileIdx()).setUsedPage(page.getPageIdx());
    }

    public void setFreePage(PageId page){
        files.get(page.getFileIdx()).setFreePage(page.getPageIdx());
    }

    public int getLastFileIndex(){
        return (lastFileIndex);
    }
}
