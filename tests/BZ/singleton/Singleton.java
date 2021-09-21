import java.io.*;

public class Singleton implements Serializable
{
    private static Singleton instance;
    private int nb;
    private String path = null;

    private Singleton(){
        System.out.println("Construction singleton au premier appel.");
    }

    public static final Singleton getInstance(){
        if (instance == null)
            instance = new Singleton();
        return instance;
    }

    public Singleton getDB(){
        if (this.path == null)
            System.out.println("Please set path.");
        else {
            File f = new File(this.path);
            if (f.exists()) {
                try {

                    FileInputStream fileIn = new FileInputStream(path);
                    ObjectInputStream objIn = new ObjectInputStream(fileIn);
                    instance = (Singleton)objIn.readObject();
                    objIn.close();
                    fileIn.close();
                } catch(IOException e) {
                    System.out.println("I/O error : "  + e.getMessage());
                } catch(ClassNotFoundException e) {
                    System.out.println("Class error : " + e.getMessage());
                }
            }
        }
        return (instance);
    }

    public int getNb(){
        return (this.nb);
    }

    public void setPath(String path){
        this.path = path;
    }

    public void setNb(int n){
        if (this.path == null)
            System.out.println("Please set path.");
        else {
            this.nb = n;
            try {
                File f = new File(path);
                FileOutputStream fileOut = new FileOutputStream(path);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                objOut.writeObject(instance);
                objOut.flush();
                objOut.close();
                fileOut.close();
            } catch(FileNotFoundException e) {
                System.out.println("File not found.");
            } catch(IOException e) {
                System.out.println("I/O error.");
            }
        }
    }
}
