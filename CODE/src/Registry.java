import java.util.ArrayList;
import java.io.Serializable;

public class Registry implements Serializable {

    private static Registry instance = null;
    private ArrayList<DataFile> files = new ArrayList<DataFile>();
    private int lastFileIndex = 0;

    private Registry(){
        
    }

    public static final Registry getInstance(){
        if (instance == null)
            instance = new Registry();
        return instance;
    }

    public void newFile(){
        //add new DataFile to ArrayList (increment lastFileIndex)
    }

    public int[] pageAvailable() {
        int [] page = {-1, -1};

        for (DataFile file : files)
            if ((page[1] = file.pageAvailable()) != -1)
                page[0] = file.getFileIndex();
        return (page);
    }

    public int getLastFileIndex(){
        return (lastFileIndex);
    }
}
