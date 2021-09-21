public class MainTestStatic
{
    public static void main(String[] args){
        TestStatic x = new TestStatic();
        TestStatic y = new TestStatic();

        System.out.println(x.hashCode() + "," + y.hashCode());
        //System.out.println(x.nb.hashCode() + "," + y.nb.hashCode());
    }
}
