package kernel;

public class RelationInfo
{

	private String		relationName;
	private int		columnNumber;
	private String[]	columnName;
	private String[]	columnType;
	private PageId		headerPageId;
	// private int		recordSize;
	// private int		slotCount;

	public RelationInfo(String relationName, int columnNumber, PageId headerPageId)
	{
		this.relationName = relationName;
		this.columnNumber = columnNumber;
		this.columnName = new String[columnNumber];
		this.columnType =  new String[columnNumber];
		this.headerPageId = headerPageId;
	}

	/**
	 * Calculation of the size of a Record
	 * @return the size of a Record
	 */
	public int calculRecordSize()
	{
		int	recordSize = 0;
		int	len;

		for (String type : columnType)
		{
			type.toLowerCase();
            switch(type)
            {
                case "int":
                    recordSize += 4;
                    break;
                case "float":
                    recordSize += 4;
                    break;
                default:
                    if (type.substring(0,5).equals("string"))
                    {
                        len = Integer.parseInt(type.substring(6));
                        recordSize += 2 * len;
                    }
                    break;
			}
		}
		return (recordSize);
	}
    /*
	public int calculSlotCount()
	{

	}
    */
	public String getRelationName()
	{
		return this.relationName;
	}

	public int getColumnNumber()
	{
		return this.columnNumber;
	}

	public String[] getColumnName()
	{
		return this.columnName;
	}

	public String[] getColumnType()
	{
		return this.columnType;
	}

    public PageId getHeaderPageId()
    {
        return (this.headerPageId);
    }

	public void setRelationName(String relationName)
	{
		this.relationName = relationName;
	}

	public void setColumnNumber(int columnNumber)
	{
		this.columnNumber = columnNumber;
	}

	public void setColumnName(String columnName, int i)
	{
		this.columnName[i] = columnName;
	}

	public void setColumnType(String columnType, int i)
	{
		this.columnType[i] = columnType;
	}

	// public void listColumnType()
    // {

	// }
}
