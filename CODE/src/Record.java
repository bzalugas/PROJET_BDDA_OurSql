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
                        int resInt = Integer.valueOf(value);
                        buff.putInt(resInt);
                        break;
                    case "float":
                        float resFloat = Float.valueOf(value);
                        buff.putFloat(resFloat);
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
        this.values.clear();
            for(String type : this.relInfo.getColumnType())
            {
                switch(type)
                {
                    case "int":
                        int resInt = buff.getInt(pos);
                        this.values.add(String.valueOf(resInt));
                        break;
                    case "float":
                        float resFloat = buff.getFloat(pos);
                        this.values.add(String.valueOf(resFloat));
                        break;
                    default:
                        int len = Integer.parseInt(type.substring(6));
                        int j = O;
                        char [] chars = new char[len];
                        for (int i = pos; i < pos+len; i++){
                            chars[j] = buff.getChar(i);
                            j++;
                        }
                        
                        String resString = new String(chars);
                        this.values.add(resString+len);

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
