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

    public void writeToBuffer(ByteBuffer buff, int index)
    {
        buff.position(index);
        for(String value : this.values)
        {
            for(String type : this.relInfo.columnType)
            {
                switch(type)
                {
                    case "int":
                        int outType = Integer.valueOf(value);
                        buff.putInt(outType);
                        break;
                    case "float":
                        float outType = Float.valueOf(value);
                        buff.putFloat(outType);
                        break;
                    default:
                        int len = value.length();
                        for (int i = 0; i < len; i++)
                            buf.putChar(value.charAt(i));
            
                        break;
                }
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
        return this.relationCount;
    }

    public void setRelInfo(RelationInfo relInfo)
    {
        this.relInfo = relInfo;
    }
}
