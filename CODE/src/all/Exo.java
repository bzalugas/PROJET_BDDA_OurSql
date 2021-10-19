package all;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class Exo{
    public static void main(String[] args) throws IOException{
    
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(in);

        System.out.print("Entrer le mots : ");
        String mots = reader.readLine();
        Vector<String[]> a = new Vector<String[]>();
        System.out.println(mots);

        a.add(mots.split(" "));
        for (String[] z : a){
            System.out.println(z);
        }
        System.out.println(a);




    
    }
}
