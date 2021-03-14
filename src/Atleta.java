public class Atleta {
    //Atributs
    private final String name;
    private final String lastName;
    private final int age;
    private final String nation;
    private final float distance;
    private final float time;
    private final String type;
    private final float avgVel;
    private static int numSprinter=0;
    private static int numLongDistance=0;
    private static int numTrailRunner=0;

    //Constructor
    public Atleta(String name, String lastName, int age, String nation, float distance, float time, String type) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.nation = nation;
        this.distance = distance;
        this.time = time;
        this.type = type;
        avgVel=distance/time;
        switch (this.type){
            case ("Sprinter"): {
                numSprinter++;
                break;
            }
            case ("Long distance Runner,"):{
                numLongDistance++;
                break;
            }
            case ("Trail Runner"):{
                numTrailRunner++;
                break;
            }
            default:{
                System.err.println("Error, tipus d'atleta desconegut");
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public static int getNumSprinter() {
        return numSprinter;
    }

    public static int getNumLongDistance() {
        return numLongDistance;
    }

    public static int getNumTrailRunner() {
        return numTrailRunner;
    }

    public float getAvgVel() {
        return avgVel;
    }
    public String getType(){
        return type;
    }
    @Override
    public String toString() {
        return "Atleta{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", nation='" + nation + '\'' +
                ", distance=" + distance +
                ", time=" + time +
                ", type='" + type + '\'' +
                ", avgVel=" + avgVel +
                '}';
    }


}
