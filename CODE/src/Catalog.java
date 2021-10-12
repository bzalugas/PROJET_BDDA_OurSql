import java.util.ArrayList;
import java.io.*;

public class Catalog
{
    //Single instance of Catalog
    private static Catalog instance = null;

    private ArrayList<RelationInfo> listRelationInfo;
    private String catPath;

    private Catalog(String catPath)
    {   
        this.catPath = catPath;
        Init();
    }

    public static final Catalog getInstance()
    {
        if (instance == null)
        {
            String path = DBParams.DBPath + "catalog.reg";
            try
            {
                File f = new File(path);
                if (!f.exists())
                    instance = new Catalog(path);
                else
                {
                    FileInputStream fin = new FileInputStream(f);
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    instance = (Catalog)oin.readObject();
                    oin.close();
                    fin.close();
                }
            }
            catch (IOException e)
            {
                System.out.println("IOException in Catalog.getInstance" + e.getMessage());
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException in Catalog.getInstance" + e.getMessage());
            }

        }
        return instance;
    }

    public void save ()
    {
        try
        {
            File f  = new File(catPath);
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(this);
            objOut.flush();
            objOut.close();
            fileOut.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException in Catalog.save : " + e.getMessage());
        }

    }

    public void Init()
    {
 
    }

    public void Finish()
    {

    }

    public void addRelation(RelationInfo relationInfo)
    {
        this.listRelationInfo.add(relationInfo);
    }
    public void supRelation(String nomRelation)
    {   
        int i = -1;
        for(RelationInfo inf : this.listRelationInfo) {
             i++;
             if(nomRelation.equals(inf.getRelationName())){
                listRelationInfo.remove(i);
             }
        }
    }

    public ArrayList<RelationInfo> getListRelationInfo()
    {
        return this.listRelationInfo;
    }

    public void setListRelationInfo(ArrayList<RelationInfo> listRelationInfo)
    {
        this.listRelationInfo = listRelationInfo;
    }

 
}
