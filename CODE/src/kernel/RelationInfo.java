package kernel;

import java.io.Serializable;
import java.util.Arrays;

public class RelationInfo implements Serializable
{

	private static final long   serialVersionUID = 23L;
	private String				relationName;
	private int					columnNumber;
	private String[]			columnName;
	private String[]			columnType;
	private PageId				headerPageId;
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

	public RelationInfo(String relationName, int columnNumber, String[] columnNames, String[] columnTypes, PageId headerPageId)
	{
		this.relationName = relationName;
		this.columnNumber = columnNumber;
		this.columnName = columnNames;
		this.columnType =  columnTypes;
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

		// System.out.println("columnType : " + Arrays.toString(columnType));
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
                    if (type.startsWith("string"))
                    {
                        len = Integer.parseInt(type.substring(6));
                        recordSize += 2 * len;
                    }
                    break;
			}
		}
		return (recordSize);
	}

	/**
	 * Calculation of the slotCount
	 * @return the number of slots needed for the relation
	 */
	public int calculSlotCount()
	{
		/*slotCount = nb slots in a page for the relation*/
		/*slotCount = pageSize - 2 pageId size - bytemap size / recordSize*/
		int recordSize = calculRecordSize();
		int byteMapLength = (DBParams.pageSize - 8) / recordSize;
		int slotCount = (DBParams.pageSize - 8 - byteMapLength) / recordSize;
		return (slotCount);
	}

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

	/**
	 * Get the index of the columnName
	 * @param name the name of the column
	 * @return index of the column or -1 if the name is wrong
	 *
	 */
	public int getIndexColumnName(String name)
	{
		for (int i = 0; i < columnName.length; i++)
			if (columnName[i].equals(name))
				return (i);
		return (-1);
	}

	public String[] getColumnType()
	{
		return this.columnType;
	}

	/**
	 * Get the type of the column by index
	 * @param index the index of the column
	 * @return the columnType or empty String if the index is incorrect
	 *
	 */
	public String getPreciseColumnType(int index)
	{
		if (index < columnType.length)
			return columnType[index];
		return "";
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

	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder(relationName);
		s.append(" (");
		for (int i = 0; i < columnName.length; i++)
		{
			s.append(columnName[i]).append(":").append(columnType[i]);
			if (i + 1 < columnName.length)
				s.append(",");
		}
		s.append(")");
		return (s.toString());
	}
}
