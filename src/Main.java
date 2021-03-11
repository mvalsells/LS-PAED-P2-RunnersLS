
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
            option=scanner.nextInt();
            long startTime = System.currentTimeMillis();
            int numTrams;
            switch (option){
                case 1:
                    //Backtracking
                    cursaRelleus(atletas);
                    System.out.println("Temps tardat: " + (System.currentTimeMillis()-startTime) + " milisegons");
                    break;
                case 2:
                    System.out.print("Nombre de trams de la cursa: ");
                    numTrams = scanner.nextInt();
                    //BackTracking
                    cursesIndividuals(numTrams,0);
                    System.out.println("Temps tardat: " + (System.currentTimeMillis()-startTime) + " milisegons");
                    break;
                case 3:
                    //branch and bound
                    System.out.print("Nombre de trams de la cursa: ");
                    numTrams = scanner.nextInt();
                    cursesIndividuals(numTrams,1);
                    System.out.println("Temps tardat: " + (System.currentTimeMillis()-startTime) + " milisegons");
                    break;
                case 4:
                    //Greedy OK
                    gestioHoraris(curses);
                    System.out.println("Temps tardat: " + (double)(System.currentTimeMillis()-startTime)/1000 + " segons");
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Introudueix un nombre del 1 al 5");
            }
            System.out.println("--------------------------------------------------------------------------------------");
        } while (option!=5);
    }

    private static void cursaRelleus(ArrayList<Atleta> atletes) {
        //Numero d'equips que tindrem
        int numEquips = Math.min(Atleta.getNumLongDistance(),Math.min(Atleta.getNumSprinter(), Atleta.getNumTrailRunner()));
        //Número d'atletes a descartar de cada tipus
        /*int numDescartarLD=Atleta.getNumLongDistance()-numEquips;
        int numDescartarTR=Atleta.getNumTrailRunner()-numEquips;
        int numDescartarS=Atleta.getNumSprinter()-numEquips;

        Collections.shuffle(atletes);

        ArrayList<Atleta> sprinters = new ArrayList<>();
        ArrayList<Atleta> trailRunners = new ArrayList<>();
        ArrayList<Atleta> longDistance = new ArrayList<>();

        float mitjanaSprinter = 0;
        float mitjanaTrailRunner = 0;
        float mitjanaLongDistance = 0;

        //Descartem i separem per tipus
        for (Atleta atleta: atletes){
            switch (atleta.getType()){
                case ("Sprinter"): {
                    if (numDescartarS > 0){
                        numDescartarS--;
                    } else {
                        sprinters.add(atleta);
                        mitjanaSprinter+=atleta.getAvgVel();
                    }
                    break;
                }
                case ("Long distance Runner,"):{
                    if (numDescartarLD > 0){
                        numDescartarLD--;
                    } else {
                        longDistance.add(atleta);
                        mitjanaLongDistance+=atleta.getAvgVel();
                    }
                    break;
                }
                case ("Trail Runner"):{
                    if (numDescartarTR > 0){
                        numDescartarTR--;
                    } else {
                        trailRunners.add(atleta);
                        mitjanaTrailRunner+=atleta.getAvgVel();
                    }
                    break;
                }
                default:{
                    System.err.println("Error, tipus d'atleta desconegut");
                }
            }
        }*/
        /*System.out.println("------------mitjana Sprinter");
        for (Atleta at: sprinters){
            System.out.println(at.getAvgVel()*100);
        }
        System.out.println("------------mitjana tr");
        for (Atleta at: trailRunners){
            System.out.println(at.getAvgVel()*100);
        }
        System.out.println("------------mitjana long dist");
        for (Atleta at: longDistance){
            System.out.println(at.getAvgVel()*100);
        }*/
        //int[] config = new int[3];
        //boolean[] utilitzats = new boolean[numEquips];
        //backtrackingRelleus(config,0, 26, utilitzats);


        int[] config = new int[atletas.size()];
        for (int i = 0; i < numEquips; i++) {
            config[i] = 0;
        }
        boolean[] utilitzats = new boolean[atletas.size()];
        int[] equips = new int[3];
        Arrays.fill(equips, 0);
        backtrackingRelleusV2(config, 0, numEquips, utilitzats, equips);


    }
    //Crear totes possibles combinacions
    //Un cop creades, escollir millor solucio mirant mitjanes d'equips
    //Podar les opcions que no siguin solucio

    // equips[Sprinter, LongDistance, TrailRunner);
    private static void backtrackingRelleusV2(int[] config, int atletaActual, int numEquips, boolean[] utilitzats, int[] equips){
        int equipMinim = 0;
        while (atletaActual<atletas.size()){
            if (!utilitzats[atletaActual]){

                equipMinim = Math.min(Math.min(equips[0], equips[1]), equips[2]);
                if (/*equipMinim >= numEquips*/atletaActual==atletas.size()-1){
                    //Ja hem fet una combinació d'equips
                    System.out.println(Arrays.toString(config));
                } else {

                    for (int i = 1; i <= numEquips; i++) {
                        switch (atletas.get(atletaActual).getType()){
                            case "Sprinter":
                                if(equips[0] <= numEquips && i>equips[0]){
                                    equips[0]++;
                                    config[atletaActual]=i;
                                }
                                break;
                            case "Long distance Runner,":
                                if(equips[1] <= numEquips && i>equips[1]){
                                    equips[1]++;
                                    config[atletaActual]=i;
                                }
                                break;
                            case "Trail Runner":
                                if(equips[2] <= numEquips && i>equips[2]){
                                    equips[2]++;
                                    config[atletaActual]=i;
                                }
                                break;
                        }

                        utilitzats[atletaActual] = true;
                        atletaActual++;
                        backtrackingRelleusV2(config, atletaActual,numEquips,utilitzats,equips);
                        atletaActual--;
                        utilitzats[atletaActual] = false;
                    }
                }
                utilitzats[atletaActual]=false;
                atletaActual--;
            }
        }
    }

    //Backtracking cursa relleus KO
    private static void backtrackingRelleus(int[] config, int atletaActual, int numAtletes, boolean[] utilitzats) {
        config[atletaActual]=0;
        while (config[atletaActual]<numAtletes){
            //Si no l'he utilitzat provo una combinació amb ell
            if (!utilitzats[config[atletaActual]]){
                //Marco que ja he utilitzat aquest atletaActual
                utilitzats[config[atletaActual]] = true;
                long estimate = Math.abs(RaceHelper.estimate(config,atletaActual));

                if (atletaActual == numAtletes-1) {
                    //Ja tenim tots els 3 atletes posats
                    //Tenim una possible solució
                    //Si és la millor fins ara guardem la informació
                    System.out.println(Arrays.toString(config));
                    /*if (estimate<Globals.bestEstimate) {
                        Globals.bestEstimate=estimate;
                        Globals.bestEstimateConfig=config.clone();
                        //System.out.println("Millor solució ");
                    }*/
                } else {
                   // if (estimate<Globals.bestEstimate){
                        backtrackingRelleus(config, atletaActual+1, numAtletes, utilitzats);
                   // } else {
                        //Poda
                        //System.err.println(Arrays.toString(config) +" Estimate: "+ estimate);
                   // }
                }

                //Ja he fet totes les combinacions amb l'atletaActual x, el desmarco
                utilitzats[config[atletaActual]] = false;
            }
            config[atletaActual]++;
        }
    }


    public static void cursesIndividuals(int numTrams, int algorisme){
        RaceHelper.init(numTrams);

        if (algorisme==0){
            //BackTracking
            int[] configBT = new int[numTrams];
            int tram = 0;
            boolean[] utilitzats = new boolean[numTrams];
            backtrackingIndividuals(configBT, tram, numTrams, utilitzats);
            System.out.println("Best option with backtracking\nConfig: " + Arrays.toString(Globals.bestEstimateConfig) + " Estimate: "+ Globals.bestEstimate);
        } else if (algorisme==1) {
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
        config[tramActual]=1;
        while (config[tramActual]<=numTrams){
            //Si no l'he utilitzat provo una combinació amb ell
            if (!utilitzats[config[tramActual]-1]){
                //Marco que ja he utilitzat aquest tramActual
                utilitzats[config[tramActual]-1] = true;
                //TODO Fer valor absolut o no?
                long estimate = Math.abs(RaceHelper.estimate(config,tramActual));

                if (tramActual == numTrams-1) {
                    //Ja tenim tots els nombres d'esforços posats
                    //Tenim una possible solució
                    //Si és la millor fins ara guardem la informació
                    //System.out.println(Arrays.toString(config) +" Estimate: "+ estimate);
                    if (estimate<Globals.bestEstimate) {
                        Globals.bestEstimate=estimate;
                        Globals.bestEstimateConfig=config.clone();
                        //System.out.println("Millor solució ");
                    }
                } else {
                    if (estimate<Globals.bestEstimate){
                        backtrackingIndividuals(config, tramActual+1, numTrams, utilitzats);
                    } else {
                        //Poda
                        //System.err.println(Arrays.toString(config) +" Estimate: "+ estimate);
                    }
                }

                //Ja he fet totes les combinacions amb l'tramActual x, el desmarco
                utilitzats[config[tramActual]-1] = false;
            }
            config[tramActual]++;
        }
    }


    public static int gestioHoraris(ArrayList<Cursa> curses){
        int[] configCurses = new int[curses.size()];
        boolean[] cursesDescartades = new boolean[curses.size()];
        int numCurses=0;

        int j = 0;
        Comparator<Cursa> comparaDurada = (Cursa r1, Cursa r2) -> (r1.compareDuration(r2));
        curses.sort(comparaDurada);
        while(!areAllTrue(cursesDescartades)){
            //Comparator<Cursa> comparaDurada = (Cursa r1, Cursa r2) -> (r1.compareDuration(r2));
            //curses.sort(comparaDurada);
            //Busquem i descartem minim
            if(!cursesDescartades[j]){
                //Agafem cursaMinima
                Cursa cursaMinima = curses.get(j);
                cursesDescartades[j] = true;
                configCurses[j] = 1;

                //Busquem solapaments
                for(int i = 0; i < curses.size(); i++){
                    //Mirem que cursa no estigui descartada primer per tal d'evitar càcluls de solapament inecessaris
                    if(!cursesDescartades[i]){
                        //Comprovem que no es solapi
                        if((curses.get(i).getStart().isAfter(cursaMinima.getStart()) && curses.get(i).getStart().isBefore(cursaMinima.getEnd())) ||     //comença a l'interval de cursaMinima
                                (curses.get(i).getStart().isBefore(cursaMinima.getStart() )&& curses.get(i).getEnd().isAfter(cursaMinima.getEnd())) ||  //engolba a l'interval de cursaMinima
                                (curses.get(i).getEnd().isAfter(cursaMinima.getStart()) && curses.get(i).getEnd().isBefore(cursaMinima.getEnd())) ||    //acaba a l'interval de cursaMinima
                                (curses.get(i).getStart().equals(cursaMinima.getStart())) ||                                                            //comencen alhora
                                (curses.get(i).getStart().equals(cursaMinima.getEnd())) ||                                                              //cursa comença minut q cursaMinima acaba
                                (curses.get(i).getEnd().equals(cursaMinima.getStart()))){                                                               //cursa acaba minut q cursaMinima comença

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
            if(configCurses[i] == 1){
                cursesOrdenades.add(curses.get(i));
            }
        }
        //Ordenem per hora i printem
        Comparator<Cursa> compareHour = Cursa::compareHour;
        cursesOrdenades.sort(compareHour);
        for (int i = 0; i < cursesOrdenades.size(); i++) {
            System.out.println(cursesOrdenades.get(i).getStart() + "  " + cursesOrdenades.get(i).getEnd() +  "  "  + cursesOrdenades.get(i).getName() + "  Duration:  " +  cursesOrdenades.get(i).getDuration());
        }

        //Màxim numero de curses sense que es solapin
        numCurses = IntStream.of(configCurses).sum();
        System.out.println("\nNúmero de curses: "+ numCurses);
        return numCurses;
    }

    public static boolean areAllTrue(boolean[] array)
    {
        for(boolean b : array) if(!b) return false;
        return true;
    }
}
