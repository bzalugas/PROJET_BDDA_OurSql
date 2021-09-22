import java.io.Serializable;

public class Person implements Serializable
{
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return (this.name);
    }

    public String toString() {
        return ("name : " + this.name);
    }
}
