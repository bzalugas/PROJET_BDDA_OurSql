package kernel;

import java.io.IOException;
import exceptions.*;

public class InsertCommand {
    private String nameRelation;
    private String cmd;

    public InsertCommand(String nameRelation, String cmd){
        this.setNameRelation(nameRelation);
        this.setCmd(cmd);
    }

    public void execute(){
        FileManager fm = FileManager.getInstance();
        Catalog cat = Catalog.getInstance();

        if (cat.relationExiste(nameRelation)) {
            Record rec = new Record(cat.getRelationByName(nameRelation));
            refreshRecord(rec);
            try{
               fm.insertRecordIntoRelation(cat.getRelationByName(nameRelation),rec);
            }
            catch (IOException | TooManyFreePageException e) {
            e.printStackTrace();
            }
            
        }
             
    }

    public void refreshRecord(Record rec){
        String command = this.cmd.substring(1, cmd.length() - 1);
        String [] values;
        values = command.split(",");

        for(String val : values){
           rec.setValues(val);
        }
    }
    public String getNameRelation() {
        return nameRelation;
    }

    public void setNameRelation(String nameRelation) {
        this.nameRelation = nameRelation;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    
}
