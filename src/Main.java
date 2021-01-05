import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Atleta> atletas = Json.llegirAtletes();
        for (Atleta atleta: atletas){
            System.out.println(atleta.toString());
        }
        System.out.println("-------------------------------------------------------------------------------------");
        ArrayList<Cursa> curses = Json.llegirCurses();
        for (Cursa cursa : curses) {
            System.out.println(cursa.toString());
        }
    }
}
