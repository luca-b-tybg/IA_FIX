package scale;

import java.util.Objects;

public class Note {
    private KeyFile key;
    private boolean isFlat = false;
    private RhythmType rhythmType = RhythmType.CROTCHET;
    private int octave = 4;
    private boolean isMajor = true;



    public boolean isSharp;

    public Note(KeyFile key, boolean isFlat, boolean isSharp, RhythmType rhythmType, int octave) {
        this.key = key;
        this.isFlat = isFlat;
        this.isSharp = isSharp;
        this.rhythmType = rhythmType;
        this.octave = octave;
    }

    public Note(Note n) {
        this.octave = n.getOctave();
        this.isFlat = n.isFlat();
        this.isSharp = n.isSharp();
        this.key = n.getKey();
        this.rhythmType = n.getRhythmType();
    }

    public static Note sharp(KeyFile key) {
        var n = new Note(key);
        n.isSharp = true;
        return n;
    }

    public boolean isFlat() {
        return isFlat;
    }

    public static Note flat(KeyFile key) {
        return new Note(key, true, false, RhythmType.CROTCHET, 4);
    }

    public static Note fromString(String spec) {
       String  key =   String.valueOf (spec.charAt(0));
       boolean isSharp = spec.contains("#");
       boolean isFFlat = spec.contains("b");
       int octave = 4;
       if(isFFlat && isSharp) {
           octave = spec.charAt(4);
       }
       return new Note(KeyFile.valueOf( key.toUpperCase()), isFFlat, isSharp, RhythmType.CROTCHET, octave);
    }

    public static Note forKey(KeyFile key) {
        return new Note(key);
    }

    public boolean isSharp() {
        return isSharp;
    }

    public void setSharp(boolean sharp) {
        isSharp = sharp;
    }

    public Note(KeyFile key) {
        this.key = key;
    }

    public KeyFile getKey() {
        return key;
    }

    public void setFlat(boolean isSharp) {
        isFlat = isSharp;
    }

    public void setRhythmType(RhythmType rhythmType) {
        this.rhythmType = rhythmType;
    }

    @Override
    public String toString() {
        String keyName = isMajor? key.name(): key.name().toLowerCase();
        return keyName + (isFlat ? "b" : "") + (isSharp ? "#" : "");
    }



    public RhythmType getRhythmType() {
        return rhythmType;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public boolean isMajor() {
        return isMajor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return isFlat == note.isFlat && octave == note.octave && isMajor == note.isMajor && isSharp == note.isSharp && key == note.key && rhythmType == note.rhythmType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, isFlat, rhythmType, octave, isMajor, isSharp);
    }
}
