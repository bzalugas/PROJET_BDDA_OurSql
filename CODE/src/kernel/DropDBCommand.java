package kernel;

import java.io.File;

/**
 * Handling the DropDBCommand
 */
public class DropDBCommand
{

	/**
	 * Launch the DropDBCommand
	 */
	public static void execute()
	{
		resetAll();
		deleteFiles();
	}

	/**
	 * Delete all files of the DB path
	 */
	private static void deleteFiles()
	{
		File DBFolder = new File(DBParams.DBPath);
		File[] files = DBFolder.listFiles();

		for (File f : files)
			f.delete();
	}

	/**
	 * Reset all instances of the DB
	 */
	private static void resetAll()
	{
		BufferManager.getInstance().reset();
		Catalog.getInstance().reset();
	}
}
