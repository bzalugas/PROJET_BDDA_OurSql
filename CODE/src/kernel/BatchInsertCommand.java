package kernel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;

import kernel.exceptions.TooManyFreePageException;

public class BatchInsertCommand {
    private String filePath;
    private String name;

    public BatchInsertCommand(String name,String filePath){
        this.filePath = filePath;
        this.name = name;
    }

    /** Execute the BatchInsert command
     * 
     * 
     */
    public void execute(){
        File f = new File(this.filePath);
        FileManager fm = FileManager.getInstance();
        Catalog cat = Catalog.getInstance();
        Rid rid;

        if(cat.relationExiste(this.name))
            try(BufferedReader br = new BufferedReader(new FileReader(f))){
                String l = null;
                while((l = br.readLine()) != null){
                    String [] parsedLine = this.parseLine(l);
                    Record newRecord = new Record(cat.getRelationByName(this.name));

                    for(String str : parsedLine){
                        // System.out.println("ajout valeur " + str);
                        newRecord.setValues(str);
                    }
                    rid = fm.insertRecordIntoRelation(cat.getRelationByName(this.name),newRecord);
                    // String[] tab = {"SELECTMONO","*","FROM","test"};
                    // SelectMonoCommand s = new SelectMonoCommand(tab);
                    // s.execute();
                    newRecord.setRid(rid);
                }
            } catch(IOException | EmptyStackException | TooManyFreePageException e) {
                e.printStackTrace();
            }
    }

    private String [] parseLine(String line){
        return line.split(",");
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
