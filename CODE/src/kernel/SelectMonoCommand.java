package kernel;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.Iterator;

import kernel.exceptions.TooManyFreePageException;

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
		ArrayList<Record>	records;
		RelationInfo		relation;

		try
		{
			/*If the command has the correct format*/
			if (selectCmd.length >= 4 && selectCmd[1].equals("*")
				&& selectCmd[2].toUpperCase().equals("FROM"))
			{
				records = getRecords();
				relation = Catalog.getInstance().getRelationByName(selectCmd[3]);
				selectRecords(records, relation);
				displayRecords(records);
			}
			else
			{
				System.out.println("Error in the command SELECTMONO");
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		catch (EmptyStackException | IOException | TooManyFreePageException e)
		{
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void displayRecords(ArrayList<Record> records)
	{
		Iterator<String> iterator;

		for (Record r : records)
		{
			iterator = r.getValues().iterator();
			while (iterator.hasNext())	
			{
				System.out.println(iterator.next());
				if (iterator.hasNext())
					System.out.println(" ; ");
				else
					System.out.println(".");
			}
			System.out.println();
		}
		System.out.println("Total records = " + records.size());
	}

	/**
	 * Selects the records according to the conditions of the command
	 * @param records
	 * @param relation
	 * @throws FileNotFoundException
	 * @throws EmptyStackException
	 * @throws IOException
	 * @throws TooManyFreePageException
	 */
	private void selectRecords(ArrayList<Record> records, RelationInfo relation) throws FileNotFoundException, EmptyStackException, IOException, TooManyFreePageException
	{
		String				condition;
		String				columnName = "";
		int					indexColumnName;
		// String				columnValue;
		String				columnType;
		String				operator = null;
		int					indexOperator;
		String[]			operators = {"=", "<", ">", "<=", ">=", "<>"};
		String				askedValueString = "";
		boolean				conditionOk;
		int					i;
		int					j;

		/*If there are conditions*/
		if (selectCmd.length >= 6 && selectCmd[4].toUpperCase().equals("WHERE"))
		{
			/*Read all records*/
			for (Record r : records)
			{
				/*Read all conditions*/
				for (i = 0; i < MAX_CRITERIA; i++)
				{
					if (i > 0 && !selectCmd[6 + (i - 1) * 2].toUpperCase().equals("AND"))
					{
						System.out.println("Error in the command.");
						return;
					}
					condition = selectCmd[5 + i * 2];
					for (j = 0; j < operators.length; j++)
						if ( (indexOperator = condition.indexOf(operators[j])) > -1)
						{
							operator = condition.substring(indexOperator, (indexOperator + operators[j].length()));
							columnName = condition.substring(0, indexOperator - 1);
							askedValueString = condition.substring(indexOperator + operators[j].length() + 1, condition.length());
							j = operators.length;
						}
					if (operator == null)
					{
						System.out.println("Incorrect operator");
						return;
					}
					// indexColumnName = r.getRelInfo().getIndexColumnName(columnName);
					indexColumnName = relation.getIndexColumnName(columnName);
					columnType = relation.getPreciseColumnType(indexColumnName);
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

	/**
	 * Return the records of the relation
	 * @return the records if the relation exists, null otherwise
	 * @throws FileNotFoundException
	 * @throws EmptyStackException
	 * @throws IOException
	 * @throws TooManyFreePageException
	 */
	private ArrayList<Record> getRecords() throws FileNotFoundException, EmptyStackException, IOException, TooManyFreePageException
	{
		String				relationName;
		RelationInfo		relation;


		relationName = selectCmd[3];
		relation = Catalog.getInstance().getRelationByName(relationName);
		if (relation != null)
			return (FileManager.getInstance().getAllRecords(relation));
		System.out.println("La relation " + relationName + " n'existe pas.");
		return null;
	}
}
