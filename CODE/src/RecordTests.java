import java.util.ArrayList;
import java.nio.ByteBuffer;

import kernel.*;
import kernel.Record;

public class RecordTests
{

    public static void testWriteToBuffer(Record rec, ByteBuffer buff, int pos){
        
        System.out.println("Test de writeToBuffer : \n");
        System.out.println("Buffer avant : ");
        System.out.println(buff.toString());

        rec.writeToBuffer(buff,pos);

        System.out.println("Buffer apres : ");
        System.out.println(buff.toString());


    }
    public static void testReadFromBuffer(Record rec, ByteBuffer buff, int pos){

        System.out.println("Test de readFromoBuffer : \n");
        System.out.println("Record.values avant : ");
        rec.printValues();

        rec.readFromBuffer(buff,pos);

        System.out.println("Record.values avant : ");
        rec.printValues();

    }

    public static void main(String[] args){
        String path = args[0];
        DBParams.DBPath = path;
        DBParams.pageSize = 10;
        DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 4;
        final int TOT_PAGES = 10;

        //Test de writeToBuffer

        //Creation du RelationInfo necessaire au Record
        //Les valeurs d'initialisation sont arbitraire 
        RelationInfo rel = new RelationInfo("TestRelInfo",4,new PageId(0,0));
        String nomColumn = "Test ";
        int columnNumber = 4;
        String[] columnType = {"String2","String8","int","float"};
        
        //Permet de nommer toutes les colonnes
        for (int i = 0; i<columnNumber ;i++ ) {
             nomColumn = "Test ";
             nomColumn.concat(String.valueOf(i));
             rel.setColumnName(nomColumn,i);
        }

        Record rec = new Record(rel);
        int tailleString = 0;
        String stringValue = "\0";
        
        //Ajout de values choisi arbitrairement au record, les valeurs correspondent au type de la colonne
        for (int i = 0; i<columnNumber ;i++ ){
            switch(columnType[i]){
                 case "int":
                    rec.setValues(String.valueOf(i+10));
                    break;
                case "float":
                    rec.setValues(String.valueOf(3.5));
                    break;
                default:
                    if (columnType[i].substring(0,5).equals("string"))
                    {
                        //Boucle permettant d'ajouter un String de la longueur correspondante
                        for (tailleString = Integer.parseInt(columnType[i].substring(6));tailleString>0 ;tailleString-- ) {
                            stringValue.concat("a");
                        }
                        rec.setValues(stringValue);
                        stringValue = "\0";
                    }
                    break;
            }
        }
        ByteBuffer byteBuff = ByteBuffer.allocate(DBParams.pageSize);
        testWriteToBuffer(rec,byteBuff,4);

/*----------------------------------------------------------------------------------------------------------------------------------------------------------*/
       //Test readFromBuffer
        
        Record rec2 = new Record(rel);
        ByteBuffer byteBuff2 = ByteBuffer.allocate(DBParams.pageSize);

        //Ajout de values choisi arbitrairement au buffer, les valeurs correspondent au type de la colonne
        for (int i = 0; i<columnNumber ;i++ ){
            switch(columnType[i]){
                case "int":
                    byteBuff2.putInt((i+1)*5);
                    break;
                case "float":
                    byteBuff2.putFloat(8.5f);
                    break;
                default:
                    if (columnType[i].substring(0,5).equals("string"))
                    {
                        tailleString = Integer.parseInt(columnType[i].substring(6));
                        //Boucle permettant d'ajouter un String de la longueur correspondante
                        for (int j = 0; j < tailleString; j++) {
                            byteBuff2.putChar('a');
                        }
                        
                    }
                    break;
            }
        }
        testReadFromBuffer(rec2,byteBuff2,0);


        
    }
}
