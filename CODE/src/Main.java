//Main class of the project.

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import kernel.*;

public class Main
{
	public static void main(String[] args)
	{
		String[] cmd;
		Scanner in;
		DBManager dbm;
		String[] commands = {"HELP", "EXIT", "CREATE RELATION", "DROPDB", "INSERT",
			"BATCHINSERT", "SELECTMONO", "DELETE", "SHOW"};

		DBParams.DBPath = args[0];
		DBParams.pageSize = 4096;
		DBParams.maxPagesPerFile = 4;
		DBParams.frameCount = 2;

		in = new Scanner(System.in);
		dbm = DBManager.getInstance();

		do
		{
			System.out.print("Entrez une commande (HELP pour l'aide et EXIT pour quitter): ");
			cmd = in.nextLine().split(" ");
			cmd[0] = cmd[0].toUpperCase();
			switch(cmd[0])
			{
			case "HELP":
				System.out.println("Commandes disponibles : " + Arrays.toString(commands));
				break;
			case "EXIT":
				try {
					dbm.finish();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "SHOW":
				Catalog.getInstance().displayRelations();
				break;
			default:
				dbm.processCommand(cmd);
				break;
			}
		}
		while (cmd[0].compareTo("EXIT") != 0);
		in.close();
	}
}
