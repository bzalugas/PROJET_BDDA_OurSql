import java.nio.ByteBuffer;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class TestsDivers
{
    public static void main(String[] args) {
        final int size = 23;
        ByteBuffer buff = ByteBuffer.allocate(size);
        byte[] expected = new byte[size];
        for (int i = 0; i < size; i++)
            expected[i] = 1;
        int start = 0;
        String path = "./file";
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            file.seek(start);
            file.write(expected);
            file.close();
            file = new RandomAccessFile(path, "r");
            file.seek(start);
            file.read(buff.array());
            file.close();
            System.out.println("expected : " + Arrays.toString(expected));
            System.out.println("output   : " + Arrays.toString(buff.array()));
            buff.put(5, (byte)0);
            System.out.println("output   : " + Arrays.toString(buff.array()));
            System.out.println("bytebuffer size : " + buff.array().length);
            byte b = 'a';
            System.out.println(b);
        } catch (Exception e) {
            System.out.println("exception : " + e.getMessage());
        }
    }
}
