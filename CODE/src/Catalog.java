public class Catalog{
    //Single instance of DiskManager
    private static DiskManager instance = null;

    private ArrayList<RelationInfo> listRelationInfo;
    private int relationCount;

    private Catalog(){
        Init()
    }

    public static final Catalog getInstance(){
        if (instance == null)
            instance = new Catalog();

        return instance;
    }

    public void Init(){
        this.listRelationInfo = new ArrayList<RelationInfo>();
        this.relationCount = 0;
    }

    public void Finish(){
        this.listRelationInfo = null;
        this.relationCount = 0;
    }

    public void addRelation(RelationInfo relationInfo){
        this.listRelationInfo.add(relationInfo);
        this.relationCount++;
    }

    public ArrayList<RelationInfo> getListRelationInfo(){
        return this.listRelationInfo;
    }

    public void getListRelationInfo(ArrayList<RelationInfo> listRelationInfo){
        this.listRelationInfo = listRelationInfo;
    }

    public int getRelationCount(){
        return this.relationCount;
    }

    public void setRelationCount(int relationCount){
        this.relationCount = relationCount;
    }

}