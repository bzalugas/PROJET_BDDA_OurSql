import kernel.*;

public class CatalogTests
{

    public static void main(String[] args)
    {
        //System.out.println("a");
        Catalog cat = Catalog.getInstance();
        PageId p = new PageId(0,1);
        RelationInfo rel = new RelationInfo("prof", 1, p);
        RelationInfo rel2 = new RelationInfo("etudiant", 1, p);

        //System.out.println("nom de la relation : "+rel.getRelationName());
        //System.out.println("nombre de colonne : "+rel.getColumnNumber());

        cat.addRelation(rel);
        cat.addRelation(rel2);

        for(int i = 0; i < cat.getListRelationInfo().size(); i++){
            System.out.println("nom de la realtion "+i+" est : "+cat.getListRelationInfo().get(i).getRelationName());
            System.out.println("nombre de colonne "+i+" est : "+cat.getListRelationInfo().get(i).getColumnNumber());
        }

        cat.delRelation("prof");
        cat.delRelation("etudiant");

        // for(int i = 0; i < cat.getListRelationInfo().size(); i++){
        //     System.out.println("nom de la realtion "+i+" est : "+cat.getListRelationInfo().get(i).getRelationName());
        //     System.out.println("nombre de colonne "+i+" est : "+cat.getListRelationInfo().get(i).getColumnNumber());
        // }


    }


}