package kernel;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.io.FileNotFoundException;
import kernel.exceptions.TooManyFreePageException;
import java.io.IOException;
import java.nio.ByteBuffer;


/**
 * Handling Delete command
 */
public class DeleteCommand
{
	/*String array of the arguments of the command*/
	private String[]			cmd;
	/*relation in which delete records*/
	private RelationInfo		relation;
	/*Records of the relation*/
	private ArrayList<Record>	records;
	/*Maximum of conditions*/
	private static final int	MAX_CRITERIA = 20;

	public DeleteCommand(String[] cmd)
	{
		this.cmd = cmd;
		parseRelation();
		this.records = getRecords();
	}

	/**
	 * get the relation identified by the command
	 *
	 */
	private void parseRelation()
	{
		if (cmd.length >= 3 && cmd[1].toUpperCase().equals("FROM"))
			this.relation = Catalog.getInstance().getRelationByName(cmd[2]);
		else
			this.relation = null;
		if (this.relation == null)
			System.out.println("Problem with relation name in Delete Command");
	}

	/**
	 * Get the records from the relation parsed
	 * @return the records if the relation is set, null otherwise
	 *
	 */
	private ArrayList<Record> getRecords()
	{
		try
		{
			if (relation != null)
				return (FileManager.getInstance().getAllRecords(this.relation));
		}
		catch (FileNotFoundException | EmptyStackException | TooManyFreePageException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Relation not defined");
		return null;
	}

	/**
	 * Execute Delete command
	 *
	 */
	public void execute()
	{
		try
		{
			selectRecords();
			deleteRecords();
		}
		catch (FileNotFoundException | EmptyStackException | TooManyFreePageException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void deleteRecords() throws FileNotFoundException, IOException, TooManyFreePageException
	{
		Rid rid;
		BufferManager bm = BufferManager.getInstance();
		ByteBuffer buf;

		for (Record r : this.records)
		{
			rid = r.getRid();
			buf = bm.getPage(rid.getPageId());
			buf.put(16 + rid.getSlotIdx(), (byte)0);
			bm.freePage(rid.getPageId(), false);
		}


	}

	/**
	 * Selects the records according to the conditions of the command
	 * @throws FileNotFoundException
	 * @throws EmptyStackException
	 * @throws IOException
	 * @throws TooManyFreePageException
	 */
	private void selectRecords() throws FileNotFoundException, EmptyStackException, IOException, TooManyFreePageException
	{
		String				condition;
		String				columnName = "";
		int					indexColumnName;
		String				columnType;
		String				operator = null;
		int					indexOperator;
		String[]			operators = {"=", "<", ">", "<=", ">=", "<>"};
		String				askedValueString = "";
		boolean				conditionOk;
		int					i;
		int					j;

		/*If there are conditions*/
		if (this.cmd.length >= 6 && this.cmd[4].toUpperCase().equals("WHERE"))
		{
			/*Read all records*/
			for (Record r : this.records)
			{
				/*Read all conditions*/
				for (i = 0; i < MAX_CRITERIA; i++)
				{
					if (i > 0 && !this.cmd[6 + (i - 1) * 2].toUpperCase().equals("AND"))
					{
						System.out.println("Error in the command.");
						return;
					}
					/*Get the whole condition*/
					condition = this.cmd[4 + i * 2];
					for (j = 0; j < operators.length; j++)
						/*Find the operator used in condition & its index*/
						if ( (indexOperator = condition.indexOf(operators[j])) > -1)
						{
							/*operator isolation*/
							operator = condition.substring(indexOperator, (indexOperator + operators[j].length()));
							/*Name of the column in condition*/
							columnName = condition.substring(0, indexOperator - 1);
							/*Value to compare with*/
							askedValueString = condition.substring(indexOperator + operators[j].length() + 1, condition.length());
							/*Stop the loop*/
							j = operators.length;
						}
					if (operator == null)
					{
						System.out.println("Incorrect operator");
						return;
					}
					/*Index of the column in the relation*/
					indexColumnName = this.relation.getIndexColumnName(columnName);
					/*Type of the column*/
					columnType = this.relation.getPreciseColumnType(indexColumnName);
					switch (columnType.toLowerCase())
					{
					case "int":
						conditionOk = checkConditionNumber(Integer.parseInt(r.getValue(indexColumnName)), Integer.parseInt(askedValueString), columnType);
						break;
					case "float":
						conditionOk = checkConditionNumber(Float.parseFloat(r.getValue(indexColumnName)), Float.parseFloat(askedValueString), columnType);
						break;
					default:
						if (columnType.toLowerCase().startsWith("string"))
							conditionOk = checkConditionString(r.getValue(indexColumnName),askedValueString, columnType);
						else
						{
							System.out.println("Problem of type");
							return;
						}
						break;
					}
					if (!conditionOk)
						records.remove(r);
				}
			}
		}
	}

	/**
	 * Check if the asked condition is checked for a number type (int or float)
	 * @param columnValue the value from the record
	 * @param askedValue the value from the user
	 * @param operator the operator of SELECTMONO
	 * @return true if the condition is checked, false otherwise
	 */
	private boolean checkConditionNumber(float columnValue, float askedValue, String operator)
	{
		switch(operator)
		{
		case "=":
			return columnValue == askedValue;
		case "<":
			return columnValue < askedValue;
		case ">":
			return columnValue > askedValue;
		case "<=":
			return columnValue <= askedValue;
		case ">=":
			return columnValue >= askedValue;
		case "<>":
			return columnValue != askedValue;
		default:
			System.out.println("Incorrect operator");
			return false;
		}
	}

	/**
	 * Check if the asked condition is checked for a String type
	 * @param columnValue the value from the record
	 * @param askedValue the value from the user
	 * @param operator the operator of SELECTMONO
	 * @return true if the condition is checked, false otherwise
	 */
	private boolean checkConditionString(String columnValue, String askedValue, String operator)
	{
		switch(operator)
		{
		case "=":
			return columnValue.equals(askedValue);
		case "<":
			return columnValue.compareTo(askedValue) < 0;
		case ">":
			return columnValue.compareTo(askedValue) > 0;
		case "<=":
			return columnValue.compareTo(askedValue) <= 0;
		case ">=":
			return columnValue.compareTo(askedValue) >= 0;
		case "<>":
			return columnValue.compareTo(askedValue) != 0;
		default:
			System.out.println("Incorrect operator");
			return false;
		}
	}
}
