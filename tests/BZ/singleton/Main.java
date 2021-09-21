public class Main
{
    public static void main(String[] args){
        Singleton x = Singleton.getInstance();
        System.out.println("Nb of x = " + x.getNb());
        x.setPath("./file");
        x = x.getDB();
        if (args[0].compareTo("set") == 0){
            x.setNb(Integer.parseInt(args[1]));
            System.out.println("Nb set to " + args[1]);
        }
        System.out.println("Nb of x = " + x.getNb());
    }
}
