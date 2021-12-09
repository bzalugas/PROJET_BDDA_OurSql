package kernel;

import java.io.IOException;
import java.util.ArrayList;

import kernel.exceptions.TooManyFreePageException;

public class CreateRelationCommand {
    private String relationName;
    private int nbColumn;
    private ArrayList<String> columnNames;
    private ArrayList<String> columnTypes;

    public CreateRelationCommand(String name, String cmd){
       this.relationName = name;
       this.columnNames = new ArrayList<>();
       this.columnTypes = new ArrayList<>();
       this.parse(cmd);
    }

    private void parse(String cmd){
        String command = cmd.substring(1, cmd.length() - 1);
        String [] columns;
        columns = command.split(",");

        this.nbColumn = columns.length;

        for(String column : columns){
            String [] split = column.split(":");
            String name = split[0];
            String type = split[1];

            this.columnNames.add(name);
            this.columnTypes.add(type);
        }
    
    }

    public void execute(){
        FileManager fm = FileManager.getInstance();
        try {
            PageId headerPageId = fm.createHeaderPage();
            RelationInfo relationInfo = new RelationInfo(this.relationName, this.nbColumn, headerPageId);

            Catalog.getInstance().addRelation(relationInfo);
        } catch (IOException | TooManyFreePageException e) {
            e.printStackTrace();
        }
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public int getNbColumn() {
        return nbColumn;
    }

    public void setNbColumn(int nbColumn) {
        this.nbColumn = nbColumn;
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ArrayList<String> columnNames) {
        this.columnNames = columnNames;
    }

    public ArrayList<String> getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(ArrayList<String> columnTypes) {
        this.columnTypes = columnTypes;
    }

}
