import java.io.*;

@SuppressWarnings("serial")
public class SavedSingleton implements Serializable
{
	private static SavedSingleton instance = null;
	private String info1;
	private String info2;
	private String path;

	private SavedSingleton(String path)
	{
		System.out.println("Premiere instanciation.");
		this.path = path;
		this.info1 = "info 1";
		this.info2 = "info 2";
	}

	public static final SavedSingleton getInstance(String path) throws IOException,
		ClassNotFoundException
	{
		if (instance == null)
		{
			File f = new File(path);
			if (!f.exists())
				instance = new SavedSingleton(path);
			else
			{
				FileInputStream fin = new FileInputStream(f);
				ObjectInputStream oin = new ObjectInputStream(fin);
				instance = (SavedSingleton)oin.readObject();
				oin.close();
				fin.close();
			}
		}
		return (instance);
	}

	public void setInfo1(String s)
	{
		this.info1 = s;
	}
	public void setInfo2(String s)
	{
		this.info2 = s;
	}
	public String getInfo1()
	{
		return this.info1;
	}
	public String getInfo2()
	{
		return this.info2;
	}
	public String toString()
	{
		return ("Info1 : " + info1 + ", Info2 : " + info2);
	}

	public void save() throws IOException
	{
		File f = new File(path);
		FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(this);
		objOut.flush();
		objOut.close();
		fileOut.close();
	}
}
