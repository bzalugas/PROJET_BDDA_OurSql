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
        for(String value : this.values)
        {
            for(String type : this.relInfo.columnType)
            {
                switch(type)
                {
                    case "int":
                        break;
                    case "float":
                        break;
                    default:
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
