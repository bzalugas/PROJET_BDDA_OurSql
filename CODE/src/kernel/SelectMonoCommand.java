package kernel;

/**
 * Handling SelectMono command
 */
public class SelectMonoCommand
{
	/*String array of the arguments of the command*/
	private String[]			selectCmd;
	/*Maximum of criteria*/
	private static final int	MAX_CRITERIA = 20;
	
	/**
	 * Constructor
	 * @param cmd the string array command
	 */
	public SelectMonoCommand(String[] cmd)
	{
		this.selectCmd = cmd;
	}

	/**
	 * Execution of the SelectMono command
	 */
	public void execute()
	{
		String relationName;
		if (selectCmd.length >= 4 && selectCmd[1].equals("*") && selectCmd[2].toUpperCase().equals("FROM"))
		{
			relationName = selectCmd[3];
		}
	}
}