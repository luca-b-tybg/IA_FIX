package scale;

import java.util.Locale;
import java.util.Objects;

public class Note {
    private KeyFile key;
    private boolean isFlat = false;

    public boolean isSharp;

    private Note(KeyFile key, boolean isFlat, boolean isSharp) {
        this.key = key;
        this.isFlat = isFlat;
        this.isSharp = isSharp;
    }

    public static Note sharp(KeyFile key) {
        var n = new Note(key);
        n.isSharp = true;
        return n;
    }

    public static Note fromString(String spec) {
       String  key =   String.valueOf (spec.charAt(0));
       boolean isSharp = spec.contains("#");
       boolean isFFlat = spec.contains("b");
       return new Note(KeyFile.valueOf( key.toUpperCase()), isFFlat, isSharp);
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

    @Override
    public String toString() {
        return key.name() + (isFlat ? "b" : "") + (isSharp ? "#" : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return isFlat == note.isFlat && isSharp == note.isSharp && key == note.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, isFlat, isSharp);
    }
}
