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

    public void writeToBuffer(ByteBuffer buff, int pos)
    {
        buff.position(pos);
        for(String value : this.values)
        {
            for(String type : this.relInfo.getColumnType())
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

    public void readFromBuffer(ByteBuffer buff, int pos){
            for(String type : this.relInfo.getColumnType())
            {
                switch(type)
                {
                    case "int":
                        int res = buff.getInt(pos);
                        this.values.add(String.valueOf(res));
                        break;
                    case "float":
                        float res =buff.getFloat(pos);
                        this.values.add(String.valueOf(res));
                        break;
                    default:
                        int len = value.length();
                        int j = O;
                        char [] chars = new char[len];
                        for (int i = pos; i < pos+len; i++){
                            chars[j] = buf.getChar(i));
                            j++;
                        }
                        
                        String res = new String(chars);
                        this.values.add(res+len);

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
        return this.relationCount;
    }

    public void setRelInfo(RelationInfo relInfo)
    {
        this.relInfo = relInfo;
    }
}
