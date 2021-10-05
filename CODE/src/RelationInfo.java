public class RelationInfo{
    
    String relationName;
    int columnNumber;
    String[] columnName = new String[columnNumber];
    String[] columnType = new String[columnNumber];

    public RelationInfo(String relationName, int columnNumber){
        this.relationName = relationName;
        this.columnNumber = columnNumber;
    }

    public String getRelationName(){
        return relationName;
    }

    public int getColumnNumber(){
        return columnNumber;
    }

    
}