package exceptions;

@SuppressWarnings("serial")
/**
 * Class to manage a too big number of free request on a page */
public class TooManyFreePageException extends Exception
{
	private String message;

	public TooManyFreePageException()
	{
		this.message = "Too many freePage";
	}

	@Override
	public String getMessage()
	{
		return (this.message);
	}
}
