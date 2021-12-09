import kernel.*;
import kernel.RelationInfo;

public class RelationInfoTests
{

    public static void testCalculRecordSize(String relationName, int columnNumber, String[]	columnType){
        RelationInfo rel = new RelationInfo(relationName,columnNumber,new PageId(0,0));
        String nomColumn = "Test ";
        int j = 0;
        
        //Permet de nommer toutes les colonnes
        for (int i = 0; i<columnNumber ;i++ ) {
        	 nomColumn = "Test ";
        	 nomColumn.concat(String.valueOf(i));
        	 rel.setColumnName(nomColumn,i);
        }
        
        System.out.println("Test calculRecordSize :\n ");

        for (String type : columnType){
            if (j<columnNumber) {
            	System.out.println("Colonne : "+j+"; Type : "+type +"\n");

            	rel.setColumnType(type,j);
            }
            j++;
        }    

       System.out.println("Taille du record = "+rel.calculRecordSize());

    }

    public static void main(String[] args){
        String path = args[0];
		DBParams.DBPath = path;
		DBParams.pageSize = 10;
		DBParams.maxPagesPerFile = 4;
        DBParams.frameCount = 4;
		// final int TOT_PAGES = 10;
        
        //Valeurs choisi arbitrairement
        String relationName = "TestRelInfo";
        int columnNumber = 4;
        String[] columnType = {"String2","String8","int","float"};

		testCalculRecordSize(relationName,columnNumber,columnType);
    }
}
