import java.util.ArrayList;

public class Catalog
{
    //Single instance of DiskManager
    private static DiskManager instance = null;

    private ArrayList<RelationInfo> listRelationInfo;

    private Catalog()
    {
        Init();
    }

    public static final Catalog getInstance()
    {
        if (instance == null)
            instance = new Catalog();

        return instance;
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
