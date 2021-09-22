// Main class of the project.

public class Main{

    public static void main(String[] args){
        DBParams.DBPath = args[0];
        DBParams.pageSize = 4096;
        DBParams.maxPagesPerFile = 4;
        try {
            DiskManager dm = DiskManager.getInstance();
            PageId page;
            for (int i = 0; i < 23; i++){
                page = dm.AllocPage();
                System.out.println("page created : " + page.toString());
            }
        } catch(Exception e){
            System.out.println("exception : " + e.getMessage());
        }
    }

}
