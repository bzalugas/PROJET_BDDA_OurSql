package exceptions;

@SuppressWarnings("serial")
public class TooManyFreePageException extends Exception
{
	private String message;

	public TooManyFreePageException(String m)
	{
		this.message = m;
	}

	public String getMessage()
	{
		return (this.message);
	}
}
