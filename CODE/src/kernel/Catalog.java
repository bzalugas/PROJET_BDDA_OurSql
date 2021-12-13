package kernel;

import java.util.ArrayList;
import java.io.*;

/**
 * Contains scheme informations for all database
 */

public class Catalog implements Serializable
{
    private static final long   serialVersionUID = 23L;
    /*Single instance of Catalog*/
    private static Catalog instance = null;
    /*List of relations*/
    private ArrayList<RelationInfo> listRelationInfo;
    /*path of the file containing the catalog*/
    private String catalogPath;

    /**
     * Private constructor for the singleton
     */
    private Catalog(String catalogPath)
    {   
        this.catalogPath = catalogPath;
        this.listRelationInfo = new ArrayList<RelationInfo>();
        //Init();
    }

    /**
     * Get the unique instance of Catalog
     * Method init is directly implemented inside getInstance()
     * @return unique instance of Catalog
     */
    public static final Catalog getInstance()
    {
        if (instance == null)
        {
            String path = DBParams.DBPath + "/Catalog.def";
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
                System.out.println("IOException in Catalog.getInstance");
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException in Catalog.getInstance " + e.getMessage());
                e.printStackTrace();
            }

        }
        return instance;
    }

    /**
     * Method to save instance of Catalog in a file (serialization)
     */
    private void save ()
    {
        try
        {
            File f  = new File(catalogPath);
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
            e.printStackTrace();
        }
    }

    /**
     * Called at the end of Catalog
     */
    public void finish()
    {
        save();
    }

    /**
     * Method to add a relation
     * @param relationInfo relation's informations
     */
    public void addRelation(RelationInfo relationInfo)
    {
        this.listRelationInfo.add(relationInfo);
        // System.out.println("Relations du catalog : " + listRelationInfo);
    }

    /**
     * Method to delete a relation of catalog
     * @param nomRelation the name of the relation to delete
     */
    public void delRelation(String nomRelation)
    {
        int i;
        for(i = 0; i < listRelationInfo.size(); i++)
            if(nomRelation.equals(listRelationInfo.get(i).getRelationName()))
                listRelationInfo.remove(i);
    }

    public ArrayList<RelationInfo> getListRelationInfo()
    {
        return this.listRelationInfo;
    }

    public void setListRelationInfo(ArrayList<RelationInfo> listRelationInfo)
    {
        this.listRelationInfo = listRelationInfo;
    }

	/**
	 * Reset all values of Catalog
	 */
	public void reset()
	{
		for (int i = 0; i < listRelationInfo.size(); i++)
			delRelation(listRelationInfo.get(i).getRelationName());
	}
    
    public Boolean relationExiste(String name){
        for (RelationInfo rel : listRelationInfo ) {
            if (rel.getRelationName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public RelationInfo getRelationByName(String name){
        for (RelationInfo rel : listRelationInfo ) {
            if (rel.getRelationName().equals(name)) {
                return rel;
            }
        }
        // return new RelationInfo("NULL",0,new PageId(0,0));
        return null;
    }
}
