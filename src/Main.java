import edu.salleurl.RaceHelper;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Main {
    private static ArrayList<Atleta> atletas;
    public static void main(String[] args) throws FileNotFoundException {
        atletas = Json.llegirAtletes();
        final ArrayList<Cursa> curses = Json.llegirCurses();

        int option;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Menú principal\n\t1) Cursa relleus amb Backtracking (KO)\n\t2) Cursa individual amb Backtracking\n\t3) Cursa individual amb Branch and Bound\n\t4) Gestió d'horaris amb Greedy\n\t5) Sortir");
            System.out.print("Tria una opció: ");
            option = scanner.nextInt();
            long startTime = System.currentTimeMillis();
            int numTrams;
            switch (option) {
                case 1:
                    //Backtracking
                    cursaRelleus(atletas);
                    System.out.println("Temps tardat: " + (System.currentTimeMillis() - startTime) + " milisegons");
                    break;
                case 2:
                    System.out.print("Nombre de trams de la cursa: ");
                    numTrams = scanner.nextInt();
                    //BackTracking
                    cursesIndividuals(numTrams, 0);
                    System.out.println("Temps tardat: " + (System.currentTimeMillis() - startTime) + " milisegons");
                    break;
                case 3:
                    //branch and bound
                    System.out.print("Nombre de trams de la cursa: ");
                    numTrams = scanner.nextInt();
                    cursesIndividuals(numTrams, 1);
                    System.out.println("Temps tardat: " + (System.currentTimeMillis() - startTime) + " milisegons");
                    break;
                case 4:
                    //Greedy OK
                    gestioHoraris(curses);
                    System.out.println("Temps tardat: " + (double) (System.currentTimeMillis() - startTime) / 1000 + " segons");
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Introudueix un nombre del 1 al 5");
            }
            System.out.println("--------------------------------------------------------------------------------------");
        } while (option != 5);
    }

    private static void cursaRelleus(ArrayList<Atleta> atletes) {
        //Numero d'equips que tindrem
        int numEquips = Math.min(Atleta.getNumLongDistance(), Math.min(Atleta.getNumSprinter(), Atleta.getNumTrailRunner()));

        int[] config = new int[atletas.size()];
        Arrays.fill(config,0);
        int[] equips = new int[3];
        Arrays.fill(equips, 0);
        /*System.out.print("[");
        for(Atleta a:atletes){
            switch (a.getType()) {
                case "Sprinter":
                    System.out.print("S, ");
                    break;
                case "Long distance Runner,":
                    System.out.print("L, ");
                    break;
                case "Trail Runner":
                    System.out.print("T, ");
                    break;
            }
        }
        System.out.println("]");*/
        backtrackingRelleusV2(config, 0, numEquips, equips);

    }
    //Crear totes possibles combinacions
    //Un cop creades, escollir millor solucio mirant mitjanes d'equips
    //Podar les opcions que no siguin solucio

    // equips[Sprinter, LongDistance, TrailRunner);
    private static void backtrackingRelleusV2(int[] config, int atletaActual, int numEquips, int[] equips) {
        int equipMinim = 0;
        if (atletaActual == atletas.size()) {
            //Ja hem fet una combinació d'equips
            if (comprovarEquips(config, numEquips)) {
                System.out.println(Arrays.toString(config));
            }
        } else {

            for (int i = 0; i <= numEquips; i++) {
                config[atletaActual] = i;
                int atletaActual2 = atletaActual + 1;
                backtrackingRelleusV2(config, atletaActual2, numEquips, equips);
            }
        }
    }

    private static boolean comprovarEquips(int[] config, int numEquips){

        for (int i = 1; i <= numEquips; i++) {
            int numLD=0;
            int numTR=0;
            int numS=0;
            for (int j = 0; j < config.length; j++) {
                if (config[j] == i) {
                    //Mirem que no hi hagi un atleta del mateix tipus repetit al mateix equip
                    switch (atletas.get(j).getType()) {
                        case "Sprinter":
                            numS++;
                            if (numS >= 2) {
                                return false;
                            }
                            break;
                        case "Long distance Runner,":
                            numLD++;
                            if (numLD >= 2) {
                                return false;
                            }
                            break;
                        case "Trail Runner":
                            numTR++;
                            if (numTR >= 2) {
                                return false;
                            }
                            break;
                    }
                }

            }
            //Mirem que hi hagi un atleta de cada a cada equip
            if (numS!=1 || numLD !=1 || numTR!= 1){
                return false;
            }
        }

        return true;
    }

    public static void cursesIndividuals(int numTrams, int algorisme) {
        RaceHelper.init(numTrams);

        if (algorisme == 0) {
            //BackTracking
            int[] configBT = new int[numTrams];
            int tram = 0;
            boolean[] utilitzats = new boolean[numTrams];
            backtrackingIndividuals(configBT, tram, numTrams, utilitzats);
            System.out.println("Best option with backtracking\nConfig: " + Arrays.toString(Globals.bestEstimateConfig) + " Estimate: " + Globals.bestEstimate);
        } else if (algorisme == 1) {
            //Branch and Bound
            long bestEstimate = Long.MAX_VALUE;
            int[] bestConfig = new int[numTrams];
            CIconfig.setNumTrams(numTrams);
            PriorityQueue<CIconfig> cua = new PriorityQueue<>();
            CIconfig primera = new CIconfig();
            cua.offer(primera);

            while (!cua.isEmpty()) {
                CIconfig config = cua.poll();
                ArrayList<CIconfig> successors = config.obtenirSuccessors();
                for (CIconfig successor : successors) {
                    if (CIconfig.getNumTrams() == successor.getTramActual()) {
                        //És solució
                        //System.out.println("Config: " + Arrays.toString(successor.getConfig()) + " Estimate: "+ successor.estimate());
                        if (successor.estimate() < bestEstimate) {
                            //  System.out.println("Millor configuració");
                            bestEstimate = successor.estimate();
                            bestConfig = successor.getConfig();
                        }
                    } else if (successor.estimate() < bestEstimate) {
                        cua.offer(successor);
                    } /*else {
                    System.err.println("Config: " + Arrays.toString(successor.getConfig()) + " Estimate: "+ successor.estimate());
                }*/
                }
            }

            System.out.println("Best option with Branch and bound\nConfig: " + Arrays.toString(bestConfig) + " Estimate: " + bestEstimate);
        } else {
            System.err.println("Número de algorisme inesperat");
        }
    }

    private static void backtrackingIndividuals(int[] config, int tramActual, int numTrams, boolean[] utilitzats) {
        config[tramActual] = 1;
        while (config[tramActual] <= numTrams) {
            //Si no l'he utilitzat provo una combinació amb ell
            if (!utilitzats[config[tramActual] - 1]) {
                //Marco que ja he utilitzat aquest tramActual
                utilitzats[config[tramActual] - 1] = true;
                //TODO Fer valor absolut o no?
                long estimate = Math.abs(RaceHelper.estimate(config, tramActual));

                if (tramActual == numTrams - 1) {
                    //Ja tenim tots els nombres d'esforços posats
                    //Tenim una possible solució
                    //Si és la millor fins ara guardem la informació
                    //System.out.println(Arrays.toString(config) +" Estimate: "+ estimate);
                    if (estimate < Globals.bestEstimate) {
                        Globals.bestEstimate = estimate;
                        Globals.bestEstimateConfig = config.clone();
                        //System.out.println("Millor solució ");
                    }
                } else {
                    if (estimate < Globals.bestEstimate) {
                        backtrackingIndividuals(config, tramActual + 1, numTrams, utilitzats);
                    } else {
                        //Poda
                        //System.err.println(Arrays.toString(config) +" Estimate: "+ estimate);
                    }
                }

                //Ja he fet totes les combinacions amb l'tramActual x, el desmarco
                utilitzats[config[tramActual] - 1] = false;
            }
            config[tramActual]++;
        }
    }


    public static int gestioHoraris(ArrayList<Cursa> curses) {
        int[] configCurses = new int[curses.size()];
        boolean[] cursesDescartades = new boolean[curses.size()];
        int numCurses = 0;

        int j = 0;
        Comparator<Cursa> comparaDurada = (Cursa r1, Cursa r2) -> (r1.compareDuration(r2));
        curses.sort(comparaDurada);
        while (!areAllTrue(cursesDescartades)) {
            //Comparator<Cursa> comparaDurada = (Cursa r1, Cursa r2) -> (r1.compareDuration(r2));
            //curses.sort(comparaDurada);
            //Busquem i descartem minim
            if (!cursesDescartades[j]) {
                //Agafem cursaMinima
                Cursa cursaMinima = curses.get(j);
                cursesDescartades[j] = true;
                configCurses[j] = 1;

                //Busquem solapaments
                for (int i = 0; i < curses.size(); i++) {
                    //Mirem que cursa no estigui descartada primer per tal d'evitar càcluls de solapament inecessaris
                    if (!cursesDescartades[i]) {
                        //Comprovem que no es solapi
                        if ((curses.get(i).getStart().isAfter(cursaMinima.getStart()) && curses.get(i).getStart().isBefore(cursaMinima.getEnd())) ||     //comença a l'interval de cursaMinima
                                (curses.get(i).getStart().isBefore(cursaMinima.getStart()) && curses.get(i).getEnd().isAfter(cursaMinima.getEnd())) ||  //engolba a l'interval de cursaMinima
                                (curses.get(i).getEnd().isAfter(cursaMinima.getStart()) && curses.get(i).getEnd().isBefore(cursaMinima.getEnd())) ||    //acaba a l'interval de cursaMinima
                                (curses.get(i).getStart().equals(cursaMinima.getStart())) ||                                                            //comencen alhora
                                (curses.get(i).getStart().equals(cursaMinima.getEnd())) ||                                                              //cursa comença minut q cursaMinima acaba
                                (curses.get(i).getEnd().equals(cursaMinima.getStart()))) {                                                               //cursa acaba minut q cursaMinima comença

                            cursesDescartades[i] = true;

                        }
                    }
                }
            }
            j++;
        }

        //Copiem les curses que podem assitir
        ArrayList<Cursa> cursesOrdenades = new ArrayList<>();
        for (int i = 0; i < configCurses.length; i++) {
            if (configCurses[i] == 1) {
                cursesOrdenades.add(curses.get(i));
            }
        }
        //Ordenem per hora i printem
        Comparator<Cursa> compareHour = Cursa::compareHour;
        cursesOrdenades.sort(compareHour);
        for (int i = 0; i < cursesOrdenades.size(); i++) {
            System.out.println(cursesOrdenades.get(i).getStart() + "  " + cursesOrdenades.get(i).getEnd() + "  " + cursesOrdenades.get(i).getName() + "  Duration:  " + cursesOrdenades.get(i).getDuration());
        }

        //Màxim numero de curses sense que es solapin
        numCurses = IntStream.of(configCurses).sum();
        System.out.println("\nNúmero de curses: " + numCurses);
        return numCurses;
    }

    public static boolean areAllTrue(boolean[] array) {
        for (boolean b : array) if (!b) return false;
        return true;
    }
}
