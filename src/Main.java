import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Atleta> atletas = Json.llegirAtletes();
        ArrayList<Cursa> curses = Json.llegirCurses();


        for (Atleta atleta: atletas){
            //System.out.println(atleta.toString());
        }
        System.out.println("-------------------------------------------------------------------------------------");

        for (Cursa cursa : curses) {
            //System.out.println(cursa.toString());
        }


        gestioHoraris(curses);
    }

    public static int gestioHoraris(ArrayList<Cursa> curses){
       int numCurses=0;
        LocalTime fiUltimacursa = LocalTime.parse("00:00");
        //Greedy

        Comparator<Cursa> comparaDurada = (Cursa r1, Cursa r2) -> (r1.compareDuration(r2));
        curses.sort(comparaDurada);



        Comparator<Cursa> comparaHora = (Cursa r1, Cursa r2) -> (r1.compareHour(r2));
        curses.sort(comparaHora);

        for (Cursa cursa : curses){
            System.out.println("Duration:  " + cursa.getDuration());
            System.out.println("Start time:   " + cursa.getStart());
            System.out.println("End time:     " + cursa.getEnd());
            System.out.println("_________________");
        }
        return numCurses;
    }
}
