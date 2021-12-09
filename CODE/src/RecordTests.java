import java.util.ArrayList;
import java.nio.ByteBuffer;
import java.util.*;

import kernel.*;
import kernel.Record;

public class RecordTests
{
    
    public static void afficheBuffer(ByteBuffer buff){
        int posSav = buff.position();
        buff.position(0);
        //for (int i = 0; i<buff.capacity() ;i++ ) {
            System.out.print(Arrays.toString(buff.array()));
        //}
        System.out.println();
        buff.position(posSav);
    }
    public static void testWriteToBuffer(Record rec, ByteBuffer buff, int pos){
        
        System.out.println("\nTest de writeToBuffer : \n");
        System.out.println("Buffer avant : ");
        //System.out.println(buff.toString());
        //System.out.println("Position : "+buff.position());
        afficheBuffer(buff);


        

        //System.out.println("Dans testwriteTo : " + rec.getRelInfo().getPreciseColumnType(0));
        rec.writeToBuffer(buff,pos);
        //System.out.println("Position : "+buff.position());
        //buff.putChar('9');
        //System.out.println("Position : "+buff.position());
        System.out.println("Buffer apres : ");
        //System.out.println(buff.toString());
        afficheBuffer(buff);


    }
    public static void testReadFromBuffer(Record rec, ByteBuffer buff, int pos){

        System.out.println("Test de readFromBuffer : \n");
        System.out.println("Record.values avant : ");
        rec.printValues();

        rec.readFromBuffer(buff,pos);

        System.out.println("Record.values apres : ");
        rec.printValues();

    }

    public static void main(String[] args){
        String path = "C:/Users/user/Desktop/Algo_a_test/CODE/src/kernel";
        DBParams.DBPath = path;
        DBParams.pageSize = 10;
        DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 4;
        final int TOT_PAGES = 10;

        //Test de writeToBuffer

        //Creation du RelationInfo necessaire au Record
        //Les valeurs d'initialisation sont arbitraire 
        int columnNumber = 4;
        RelationInfo rel = new RelationInfo("TestRelInfo",columnNumber,new PageId(0,0));
        String nomColumn = "Test ";
        String[] columnType = {"string2","string8","int","float"};
        
        //Permet de nommer toutes les colonnes
        for (int i = 0; i<columnNumber ;i++ ) {
             nomColumn = "Test ";
             nomColumn = nomColumn.concat(String.valueOf(i));
             rel.setColumnName(nomColumn,i);
              rel.setColumnType(columnType[i],i);

             //System.out.println("Dans main : " + nomColumn);
             //System.out.println("Dans main : " + rel.getPreciseColumnType(i));
        }

        Record rec = new Record(rel);
        int tailleString = 0;
        String stringValue = "";
        
        //Ajout de values choisi arbitrairement au record, les valeurs correspondent au type de la colonne
        for (int i = 0; i<columnNumber ;i++ ){
            switch(columnType[i]){
                 case "int":
                     //System.out.println("Int");
                    rec.setValues(String.valueOf(i+10));
                    break;
                case "float":
                    //System.out.println("Float");
                    rec.setValues(String.valueOf(3.5));
                    break;
                default:
                    
                    if (columnType[i].startsWith("string"))
                    {
                        //System.out.println("String");
                        //Boucle permettant d'ajouter un String de la longueur correspondante
                        for (tailleString = Integer.parseInt(columnType[i].substring(6));tailleString>0 ;tailleString-- ) {
                            stringValue = stringValue.concat("a");
                        }
                        rec.setValues(stringValue);
                        stringValue = "";
                    }
                    break;
            }
        }
        //System.out.println("Affichage des valeurs : ");
        //rec.printValues();
        ByteBuffer byteBuff = ByteBuffer.allocate(DBParams.pageSize*10);
        testWriteToBuffer(rec,byteBuff,0);


/*----------------------------------------------------------------------------------------------------------------------------------------------------------*/
       //Test readFromBuffer
        System.out.println("\n");

        Record rec2 = new Record(rel);
        ByteBuffer byteBuff2 = ByteBuffer.allocate(DBParams.pageSize*10);


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
                    if (columnType[i].startsWith("string"))
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
