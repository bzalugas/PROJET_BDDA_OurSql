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
	public void processCommand(String command)
	{
		/*To implement*/
	}
}