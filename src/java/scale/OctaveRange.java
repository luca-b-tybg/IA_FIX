package scale;

public class OctaveRange {
    public final int octaveStart;
    public final int octaveEnd;

    public OctaveRange(int octaveStart, int octaveEnd) {
        this.octaveStart = octaveStart;
        this.octaveEnd = octaveEnd;
    }

    @Override
    public String toString() {
        return "OctaveRange{" +
                "octaveStart=" + octaveStart +
                ", octaveEnd=" + octaveEnd +
                '}';
    }
}
