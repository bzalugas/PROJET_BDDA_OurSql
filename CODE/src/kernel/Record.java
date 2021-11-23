package kernel;
import java.util.ArrayList;
import java.nio.ByteBuffer;

public class Record
{

    private RelationInfo relInfo;
    private ArrayList<String> values;

    public Record(RelationInfo relInfo)
    {
        this.relInfo = relInfo;
        this.values = new ArrayList<String>();
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
                    if (type.substring(0,5).equals("string"))
                    {
                        len = Integer.parseInt(type.substring(6));
                        for (j = 0; j < len; j++)
                            buff.putChar(values.get(i).charAt(i));
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
                    if (type.substring(0,5).equals("string"))
                    {
                        len = Integer.parseInt(type.substring(6));
                        j = 0;
                        chars = new char[len];
                        for (i = pos; i < pos+len; i++){
                            chars[j] = buff.getChar(i);
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
}
