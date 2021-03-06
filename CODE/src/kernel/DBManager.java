package kernel;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Entry point of the DBMS
 */
public class DBManager
{
    /*Unique instance of DBManager*/
    private static DBManager		instance = null;
    /*Instance of the Catalog*/
    private static Catalog			catalog = null;

    /**
     * Private constructor to handle singleton
     */
    private DBManager()
    {

    }

    /**
     * Method to get the unique instance of DBManager (singleton)
     * @return
     */
    public static DBManager getInstance()
    {
        if (instance == null)
            instance = new DBManager();
        if (catalog == null)
			init();
        return (instance);
    }

	/**
	 * Instanciate the catalog
	 */
    private static void init()
    {
    	catalog = Catalog.getInstance();
    }

	/**
	 * Ending method
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void finish() throws FileNotFoundException, IOException
	{
		catalog.finish();
		BufferManager.getInstance().flushAll();
	}

	/**
	 * Processing a command 
	 * @param command the command to process
	 */
	public void processCommand(String[] command)
	{
		switch(command[0])
		{
		case "CREATE":
			if (command[1].toUpperCase().equals("RELATION"))
			{
				CreateRelationCommand crc = new CreateRelationCommand(command[2],command[3]);
				crc.execute();
			}
			break;
		case "INSERT":
			if(command[1].toUpperCase().equals("INTO"))
			{
				InsertCommand ic = new InsertCommand(command[2], command[4]);
				ic.execute();
			}
			break;
		case "BATCHINSERT":
			if(command[1].toUpperCase().equals("INTO"))
			{
				BatchInsertCommand bic = new BatchInsertCommand(command[2], command[5]);
				bic.execute();
			}
			break;
		case "DROPDB":
			DropDBCommand.execute();
			break;
		case "SELECTMONO":
			SelectMonoCommand select = new SelectMonoCommand(command);
			select.execute();
			break;
		case "DELETE":

			break;
		}
	}
}
