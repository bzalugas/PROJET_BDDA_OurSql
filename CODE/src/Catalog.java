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

    public ArrayList<RelationInfo> getListRelationInfo()
    {
        return this.listRelationInfo;
    }

    public void setListRelationInfo(ArrayList<RelationInfo> listRelationInfo)
    {
        this.listRelationInfo = listRelationInfo;
    }

    public int getRelationCount()
    {
        return this.relationCount;
    }

    public void setRelationCount(int relationCount)
    {
        this.relationCount = relationCount;
    }
}
