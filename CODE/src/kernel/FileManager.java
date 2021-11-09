package kernel;

import java.nio.ByteBuffer;

public class FileManager {
    private static FileManager instance = null;

    private FileManager(){

    }

	//Method to get instance
	public static final FileManager getInstance()
	{
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

    public PageId readPageIdFromPageBuffer(ByteBuffer buf, boolean first){
        return ;
    }

}
