import java.util.ArrayList;

public class CursaRelleus {
    private final Atleta trialRunner;
    private final Atleta sprinter;
    private final Atleta longRunner;
    private final float velMitjana;

    public CursaRelleus(Atleta trialRunner, Atleta sprinter, Atleta longRunner) {
        this.trialRunner = trialRunner;
        this.sprinter = sprinter;
        this.longRunner = longRunner;
        velMitjana = (trialRunner.getAvgVel()+ sprinter.getAvgVel()+ longRunner.getAvgVel())/3;
    }
}
