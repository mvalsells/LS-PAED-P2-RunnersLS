
import edu.salleurl.RaceHelper;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final ArrayList<Atleta> atletas = Json.llegirAtletes();
        final ArrayList<Cursa> curses = Json.llegirCurses();

        //Comparator<Cursa> comparaDurada = (Cursa r1, Cursa r2) -> (r1.compareDuration(r2));
        //curses.sort(comparaDurada);

        //Branch and bound
        cursaRelleus(atletas);

        //Backtracking
        cursesIndividuals(5);

        //Greedy OK
        //gestioHoraris(curses);

    }

    private static void cursaRelleus(ArrayList<Atleta> atletes) {
        //Numero d'equips que tindrem
        int numEquips = Math.min(Atleta.getNumLongDistance(),Math.min(Atleta.getNumSprinter(), Atleta.getNumTrailRunner()));
        //Número d'atletes a descartar de cada tipus
        int numDescartarLD=Atleta.getNumLongDistance()-numEquips;
        int numDescartarTR=Atleta.getNumTrailRunner()-numEquips;
        int numDescartarS=Atleta.getNumSprinter()-numEquips;

        Collections.shuffle(atletes);

        ArrayList<Atleta> sprinters = new ArrayList<>();
        ArrayList<Atleta> trailRunners = new ArrayList<>();
        ArrayList<Atleta> longDistance = new ArrayList<>();

        //Descartem i separem per tipus
        for (Atleta atleta: atletes){
            switch (atleta.getType()){
                case ("Sprinter"): {
                    if (numDescartarS > 0){
                        numDescartarS--;
                    } else {
                        sprinters.add(atleta);
                    }
                    break;
                }
                case ("Long distance Runner,"):{
                    if (numDescartarLD > 0){
                        numDescartarLD--;
                    } else {
                        longDistance.add(atleta);
                    }
                    break;
                }
                case ("Trail Runner"):{
                    if (numDescartarTR > 0){
                        numDescartarTR--;
                    } else {
                        trailRunners.add(atleta);
                    }
                    break;
                }
                default:{
                    System.err.println("Error, tipus d'atleta desconegut");
                }
            }
        }



    }


    public static int[] cursesIndividuals(int numEsforços){
        int[] config = new int[numEsforços];
        int esforç = 0;
        boolean[] utilitzats = new boolean[numEsforços];
        RaceHelper.init(numEsforços);
        backtracking(config, esforç, numEsforços, utilitzats);
        return config;
    }

    private static void backtracking(int[] config, int esforçActual, int numEsforços, boolean[] utilitzats) {
        config[esforçActual]=1;
        while (config[esforçActual]<=numEsforços){
            //Si no l'he utilitzat provo una combinació amb ell
            if (!utilitzats[config[esforçActual]-1]){
                //Marco que ja he utilitzat aquest esforçActual
                utilitzats[config[esforçActual]-1] = true;
                long estimate = Math.abs(RaceHelper.estimate(config,esforçActual));

                if (esforçActual == numEsforços-1) {
                    //Ja tenim tots els nombres d'esforços posats
                    //Tenim una possible solució
                    //Si és la millor fins ara guardem la informació
                    System.out.println(Arrays.toString(config) +" Estimate: "+ estimate);
                    if (estimate<Globals.bestEstimate) {
                        Globals.bestEstimate=estimate;
                        Globals.bestEstimateConfg=config.clone();
                        System.out.println("Millor solució ");
                    }
                } else {
                    if (estimate<Globals.bestEstimate){
                        backtracking(config, esforçActual+1, numEsforços, utilitzats);
                    } else {
                        System.err.println("Poda");
                    }
                }

                //Ja he fet totes les combinacions amb l'esforçActual x, el desmarco
                utilitzats[config[esforçActual]-1] = false;
            }
            config[esforçActual]++;
        }
    }


    //TODO Preguntar si cursa pot ser > 24h
    public static int gestioHoraris(ArrayList<Cursa> curses){
        int[] configCurses = new int[curses.size()];
        boolean[] cursesDescartades = new boolean[curses.size()];
        int numCurses=0;

        int j = 0;

        while(!areAllTrue(cursesDescartades)){

            Comparator<Cursa> comparaDurada = (Cursa r1, Cursa r2) -> (r1.compareDuration(r2));
            curses.sort(comparaDurada);
            //Busquem i descartem minim
            if(!cursesDescartades[j]){
                //Agafem cursaMinima
                Cursa cursaMinima = curses.get(j);
                cursesDescartades[j] = true;
                configCurses[j] = 1;

                //Busquem solapaments
                for(int i = 0; i < curses.size(); i++){
                    //Mirem que cursa no esta descartada
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

        //Printem les curses ordenades
        ArrayList<Cursa> cursesOrdenades = new ArrayList<>();
        for (int i = 0; i < configCurses.length; i++) {
            if(configCurses[i] == 1){
                cursesOrdenades.add(curses.get(i));
            }
        }
        Comparator<Cursa> compareHour = Cursa::compareHour;
        cursesOrdenades.sort(compareHour);
        for (int i = 0; i < cursesOrdenades.size(); i++) {
            System.out.println(cursesOrdenades.get(i).getStart() + "  " + cursesOrdenades.get(i).getEnd() +  "  "  + cursesOrdenades.get(i).getName() + "  Duration:  " +  cursesOrdenades.get(i).getDuration());
        }

        //Màxim numero de curses sense que es solapin
        numCurses = IntStream.of(configCurses).sum();
        System.out.println("\nNumero de curses: "+ numCurses);
        return numCurses;
    }

    public static boolean areAllTrue(boolean[] array)
    {
        for(boolean b : array) if(!b) return false;
        return true;
    }
}
