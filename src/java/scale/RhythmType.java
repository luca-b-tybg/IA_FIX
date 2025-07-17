package scale;

public enum RhythmType {

    SEMIBREVE(4),
    DOTTED_MINIM(3),
    MINIM(2),
    DOTTED_CROTCHET(1.5),
    CROTCHET(1),
    QUAVER(0.5);

    RhythmType(double bitCount) {
        this.bitCount = bitCount;
    }

    private double bitCount;

    public double getBitCount() {
        return bitCount;
    }
}
