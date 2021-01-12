import edu.salleurl.RaceHelper;

import java.util.ArrayList;

public class CIconfig implements Comparable<CIconfig> {
    private int[] config;
    private boolean[] utilitzats;
    private int tramActual;
    private static int numTrams;

    public CIconfig(){
        config = new int[numTrams];
        utilitzats = new boolean[numTrams];
        tramActual = 0;
    }

    public CIconfig(CIconfig cIconfig) {
        this.config= cIconfig.config.clone();
        this.utilitzats= cIconfig.utilitzats.clone();
        this.tramActual= cIconfig.tramActual;
    }

    public static void setNumTrams(int numTrams) {
        CIconfig.numTrams = numTrams;
    }

    public int getTramActual() {
        return tramActual;
    }

    public int[] getConfig() {
        return config;
    }

    public static int getNumTrams() {
        return numTrams;
    }

    public long estimate(){
        return Math.abs(RaceHelper.estimate(config, tramActual-1));
    }

    @Override
    public int compareTo(CIconfig o) {
        return (int) (this.estimate()-o.estimate());
    }

    public ArrayList<CIconfig> obtenirSuccessors() {
        ArrayList<CIconfig> successors = new ArrayList<>();
        for (int i=0; i< numTrams; i++){
            if (!utilitzats[i]){
                CIconfig successor = new CIconfig(this);

                successor.tramActual++;
                successor.config[tramActual]=i+1;
                successor.utilitzats[i] = true;
                successors.add(successor);
            }
        }
        return successors;
    }
}
