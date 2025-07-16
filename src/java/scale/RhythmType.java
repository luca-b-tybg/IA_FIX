package scale;

public enum RhythmType {

    SEMIBREVE(4),
    DOTTEDMINIM(3),
    MINIM(3),
    DOTTEDCROTCHET(1.5),
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
