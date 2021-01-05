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
