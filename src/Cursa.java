public class Cursa {
    private final String name;
    private final String start;
    private final String end;

    public Cursa(String name, String start, String end) {
        this.name=name;
        this.start=start;
        this.end=end;
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
