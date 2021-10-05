public class RelationInfo{
    
    private String relationName;
    private int columnNumber;
    private String[] columnName;
    private String[] columnType;

    public RelationInfo(String relationName, int columnNumber){
        this.relationName = relationName;
        this.columnNumber = columnNumber;
        this.columnName = new String[columnNumber];
        this.columnType =  new String[columnNumber];
    }

    public String getRelationName(){
        return this.relationName;
    }

    public int getColumnNumber(){
        return this.columnNumber;
    }
        
    public String[] getColumnName(){
        return this.columnName;
    }

    public String[] getColumnType(){
        return this.columnType;
    }


    public void setRelationName(String relationName){
        this.relationName = relationName;
    }

    public void setColumnNumber(int columnNumber){
        this.columnNumber = columnNumber;
    }
        
    public void setColumnName(String columnName, int i){
        this.columnName[i] = columnName;
    }

    public void setColumnType(String columnType, int i){
        this.columnType[i] = columnType;
    }
    
}