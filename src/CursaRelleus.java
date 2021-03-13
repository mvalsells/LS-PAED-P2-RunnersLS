import java.util.ArrayList;

public class CursaRelleus {
    private final ArrayList<Atleta> atletas;
    private final float velMitjana;

    public CursaRelleus(ArrayList<Atleta> atletas) {
        this.atletas = atletas;
        float sumVel = 0;
        for (Atleta a : atletas){
            sumVel+=a.getAvgVel();
        }
        velMitjana =sumVel/atletas.size();
    }
}
