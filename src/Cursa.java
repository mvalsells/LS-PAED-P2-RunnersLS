import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;

public class Cursa {
    private final String name;
    private final LocalTime start;
    private final LocalTime end;
    private final long duration;

    public Cursa(String name, String start, String end) {
        this.name=name;
        this.start=LocalTime.parse(start);
        this.end=LocalTime.parse(end);
        this.duration = Duration.between(this.start, this.end).getSeconds() / 60;
    }

    public String getName() {
        return name;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public long getDuration() {
        return duration;
    }

    public int compareDuration(Cursa cursa){
        return (int) (this.duration - cursa.duration);
    }

    public int compareHour(Cursa cursa){
        return this.start.getHour() - cursa.start.getHour();
        //return this.start.compareTo(cursa.start);
    }



    @Override
    public String toString() {
        return "Cursa{" +
                "name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
