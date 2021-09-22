import java.util.ArrayList;
import java.util.ListIterator;
import java.io.*;

public class ListPeople implements Serializable
{
    private ArrayList<Person> list = null;

    public ListPeople() {
        list = new ArrayList<Person>();
    }
    public ListPeople(ArrayList<Person> list) {
        this.list = list;
    }

    public void add(Person p){
        list.add(p);
    }

    public Person getLast() {
        ListIterator li = list.listIterator();
        while (li.hasNext())
            li.next();
        return ((Person)li.previous());
    }

    public void display() {
        for (Person p : list)
            System.out.println(p.toString());
    }
}
