import java.util.ArrayList;

public class Registry implements Serializable {

    private static Singleton instance;
    ArrayList<DataFile> files = new DataFile();

    private Singleton(){
        
    }

    public static final Singleton getInstance(){
        if (instance == null)
            instance = new Singleton();
        return instance;
    }

}