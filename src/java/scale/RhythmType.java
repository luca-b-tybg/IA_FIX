package scale;
public enum RhythmType {
    SEMIBREVE(4),
    DOTTED_MINIM(3),
    MINIM(2),
    DOTTED_CROTCHET(1.5),
    CROTCHET(1),
    QUAVER(0.5);
    RhythmType(double beatCount) {
        this.beatCount = beatCount;
    }
    private double beatCount;
    public double getBeatCount() {
        return beatCount;
    }
}
