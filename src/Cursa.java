import java.time.LocalTime;

public class Cursa {
    private final String name;
    private final LocalTime start;
    private final LocalTime end;

    public Cursa(String name, String start, String end) {
        this.name=name;
        this.start=LocalTime.parse(start);
        this.end=LocalTime.parse(end);
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

    @Override
    public String toString() {
        return "Cursa{" +
                "name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
