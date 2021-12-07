package kernel;

public class InsertCommand {
    private String nameRelation;
    private String cmd;

    public InsertCommand(String nameRelation, String cmd){
        this.setNameRelation(nameRelation);
        this.setCmd(cmd);
    }

    public void execute(){
        
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
