import jdk.vm.ci.meta.Local;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Atleta> atletas = Json.llegirAtletes();
        ArrayList<Cursa> curses = Json.llegirCurses();


        for (Atleta atleta: atletas){
            System.out.println(atleta.toString());
        }
        System.out.println("-------------------------------------------------------------------------------------");

        for (Cursa cursa : curses) {
            System.out.println(cursa.toString());
        }
    }

    public static int gestioHoraris(ArrayList<Cursa> curses){
       int numCurses=0;
        LocalTime fiUltimacursa = LocalTime.parse("00:00");
        //Greedy

        for (Cursa cursa : curses){

        }
        return numCurses;
    }
}
