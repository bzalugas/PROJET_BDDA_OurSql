public class TestSavedSingleton
{
	public static void main(String[] args)
	{
		try
		{
			SavedSingleton s = SavedSingleton.getInstance("./save");
			System.out.println("apres instanciation : " + s);
			s.setInfo1("blablabla");
			System.out.println("Apres modif : " + s);
			s.save();
		}
		catch (Exception e)
		{
			System.out.println("exception : " + e.getMessage());
		}

	}
}
