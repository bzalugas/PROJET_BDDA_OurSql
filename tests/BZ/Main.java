import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;

public class Main
{
    private static void menu(String path) {

        ListPeople pers = null;
        String[] cmd = null;
        Scanner in = new Scanner(System.in);
        String[] commands = {"HELP", "ADD", "LIST", "QUIT"};

        //Get the ListPeople in the file if already exists
        File f = new File(path);
        if (f.exists())
        {
            try {
                FileInputStream fin = new FileInputStream(f);
                ObjectInputStream oin = new ObjectInputStream(fin);
                pers = (ListPeople)oin.readObject();
                oin.close();
                fin.close();
            } catch(IOException e) {
                System.out.println("I/O Exception : " + e.getMessage());
            } catch(ClassNotFoundException e) {
                System.out.println("Class exception : " + e.getMessage());
            }
        }
        else
            pers = new ListPeople();

        //loop for commands
        do
        {
            System.out.print("Entrez une commande (help pour l'aide): ");
            cmd = in.nextLine().split(" ");
            cmd[0] = cmd[0].toUpperCase();
            switch(cmd[0])
            {
            case "HELP":
                System.out.println("Commandes disponibles : " + Arrays.toString(commands));
                break;
            case "ADD":
                //add the person to the list then save the list to the file
                pers.add(new Person(cmd[1]));
                try {
                    FileOutputStream fileOut = new FileOutputStream(f);
                    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                    objOut.writeObject(pers);
                    objOut.flush();
                    objOut.close();
                    fileOut.close();
                } catch(IOException e) {
                    System.out.println("I/O Exception : " + e.getMessage());
                }
                System.out.println("Added " + pers.getLast().getName());
                break;
            case "LIST":
                pers.display();
                break;
            default:
                if (cmd[0].compareTo("QUIT") != 0)
                    System.out.println("Commande non reconnue.");
                break;
            }
        }
        while (cmd[0].compareTo("QUIT") != 0);
        in.close();
    }

    public static void main(String[] args) {
        String path = args[0] + "/people.txt";
        menu(path);
    }
}
