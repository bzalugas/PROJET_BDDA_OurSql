package kernel;
import java.util.ArrayList;
import java.nio.ByteBuffer;

public class Record
{

    private RelationInfo relInfo;
    private ArrayList<String> values;
    private Rid rid;

    public Record(RelationInfo relInfo)
    {
        this.relInfo = relInfo;
        this.values = new ArrayList<String>();
        this.rid = null;
    }

    /**
     * Method to write values to buffer from a position
     * @param buff the buffer
     * @param pos the starting position
     */
    public void writeToBuffer(ByteBuffer buff, int pos)
    {
        int     i;
        int     j;
        String  type;
        int     len;

        buff.position(pos);
        for (i = 0; i < values.size(); i++)
        {
            type = relInfo.getColumnType()[i].toLowerCase();
            switch(type)
            {
            case "int":
                int resInt = Integer.valueOf(values.get(i));
                buff.putInt(resInt);
                break;
            case "float":
                float resFloat = Float.valueOf(values.get(i));
                buff.putFloat(resFloat);
                break;
            default:
                if (type.startsWith("string"))
                {
                    len = Integer.parseInt(type.substring(6));
                    for (j = 0; j < len && j < values.get(i).length(); j++)
                        buff.putChar(values.get(i).charAt(j));
                }
                break;
            }
        }
    }

    public void readFromBuffer(ByteBuffer buff, int pos)
    {
        int     resInt;
        float   resFloat;
        int     len;
        int     i;
        int     j;
        char[]  chars;
        String  resString;

        this.values.clear();
        buff.position(pos);
        for(String type : this.relInfo.getColumnType())
        {
            switch(type)
            {
            case "int":
                resInt = buff.getInt();
                this.values.add(String.valueOf(resInt));
                break;
            case "float":
                resFloat = buff.getFloat();
                this.values.add(String.valueOf(resFloat));
                break;
            default:
                if (type.startsWith("string"))
                {
                    len = Integer.parseInt(type.substring(6));
                    j = 0;
                    chars = new char[len];
                    for (i = pos; i < pos+len; i++){
                        chars[j] = buff.getChar();
                        j++;
                    }
                    resString = new String(chars);
                    this.values.add(resString);
                }
                break;
            }
        }
    }

    public ArrayList<String> getValues()
    {
        return this.values;
    }

    /**
     * Get a value by index
     * @param index the index of the value
     * @return the value or empty string if the index is incorrect
     *
     */
    public String getValue(int index)
    {
        if (index < values.size())
            return this.values.get(index);
        return "";
    }

    public void setValues(String values)
    {
        this.values.add(values);
    }

    public RelationInfo getRelInfo()
    {
        return this.relInfo;
    }

    public void setRelInfo(RelationInfo relInfo)
    {
        this.relInfo = relInfo;
    }

    public void printValues()
    {   
        System.out.println("");
        for(String value : this.values) {
            System.out.print("["+value+"]");
        }
    }

    public void setRid(Rid rid)
    {
        this.rid = rid;
    }

    public Rid getRid()
    {
        return this.rid;
    }
}
