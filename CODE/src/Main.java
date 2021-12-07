//Main class of the project.

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import kernel.*;

public class Main{

    public static void main(String[] args)
    {
        String cmd;
        Scanner in = new Scanner(System.in);
        DBManager dbm = DBManager.getInstance();
        String[] commands = {"HELP", "ADD", "LIST", "QUIT"};

        DBParams.DBPath = args[0];
        DBParams.pageSize = 4096;
        DBParams.maxPagesPerFile = 4;

        do
        {
            System.out.print("Entrez une commande (HELP pour l'aide et EXIT pour quitter): ");
            cmd = in.nextLine();
            switch(cmd)
            {
            case "HELP":
                System.out.println("Commandes disponibles : " + Arrays.toString(commands));
                break;
            case "EXIT":
                dbm.finish();
                break;
            default:
                dbm.processCommand(cmd);
                break;
            }
        }
        while (cmd.compareTo("EXIT") != 0);

        in.close();

    }
}
