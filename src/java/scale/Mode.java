package scale;

public enum Mode {

    IONIAN("Ioninan"),
    DORIAN("Dorian"),
    PHRYGIAN("Phrygian"),
    LYDIAN("Lydioan"),
    MIXOLYDIAN("Mixolydian"),
    AEOLIAN("Aeolian"),
    LOCRIAN("Locrian");

    private final String name;

    Mode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
