package circle;

import scale.KeyFile;
import utils.StringUtils;

import java.util.Objects;

public class CircleOfFifthsKeyFile {

    private final KeyFile keyFile;
    private final CircleOfFifthsMinorMajor majorOrMinor;
    private boolean isSharp = false;
    private boolean isDiminished = false;


    private CircleOfFifthsKeyFile(KeyFile keyFile, CircleOfFifthsMinorMajor majorOrMinor, boolean isSharp, boolean isDiminished) {
        this.keyFile = keyFile;
        this.majorOrMinor = majorOrMinor;
        this.isSharp = isSharp;
        this.isDiminished = isDiminished;
    }

    public static CircleOfFifthsKeyFile fromString(String spec) {
        String key = String.valueOf(spec.charAt(0));
        CircleOfFifthsMinorMajor majorOrMinor = StringUtils.isUpperCase(spec.charAt(0)) ? CircleOfFifthsMinorMajor.MAJOR : CircleOfFifthsMinorMajor.MINOR;
        boolean isSharp = spec.contains("#");
        boolean isDim = spec.contains("dim");


        return new CircleOfFifthsKeyFile(KeyFile.valueOf(key.toUpperCase()), majorOrMinor, isSharp, isDim);
    }

    public static CircleOfFifthsKeyFile sharp(KeyFile key) {
        return new CircleOfFifthsKeyFile(key, CircleOfFifthsMinorMajor.MINOR, true, false);
    }

    public static CircleOfFifthsKeyFile major(KeyFile key) {
        return new CircleOfFifthsKeyFile(key, CircleOfFifthsMinorMajor.MAJOR, false, false);
    }

    public static CircleOfFifthsKeyFile minor(KeyFile key) {
        return new CircleOfFifthsKeyFile(key, CircleOfFifthsMinorMajor.MINOR, false, false);
    }

    public static CircleOfFifthsKeyFile sharpMinor(KeyFile key) {
        return new CircleOfFifthsKeyFile(key, CircleOfFifthsMinorMajor.MINOR, true, false);
    }

    public static CircleOfFifthsKeyFile sharpMajor(KeyFile key) {
        return new CircleOfFifthsKeyFile(key, CircleOfFifthsMinorMajor.MAJOR, true, false);
    }

    public static CircleOfFifthsKeyFile diminished(KeyFile key) {
        return new CircleOfFifthsKeyFile(key, CircleOfFifthsMinorMajor.MAJOR, false, true);
    }

    public static CircleOfFifthsKeyFile sharpDiminished(KeyFile key) {
        return new CircleOfFifthsKeyFile(key, CircleOfFifthsMinorMajor.MAJOR, true, true);
    }

    public boolean isMinor() {
        return majorOrMinor == CircleOfFifthsMinorMajor.MINOR;
    }

    public boolean isMajor() {
        return majorOrMinor == CircleOfFifthsMinorMajor.MAJOR;
    }

    public KeyFile getKeyFile() {
        return keyFile;
    }

    public String toString() {
        String result = keyFile.toString().toLowerCase();
        if (majorOrMinor != null && majorOrMinor == CircleOfFifthsMinorMajor.MAJOR) {
            result = keyFile.toString();
        }
        if (isSharp) {
            result = result + "#";
        }
        if (isDiminished) {
            result = result + "dim";
        }


        return result;
    }

    public boolean isDiminished() {
        return isDiminished;
    }

    public boolean isSharp() {
        return isSharp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircleOfFifthsKeyFile that = (CircleOfFifthsKeyFile) o;
        return isSharp == that.isSharp && isDiminished == that.isDiminished && keyFile == that.keyFile && majorOrMinor == that.majorOrMinor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyFile, majorOrMinor, isSharp, isDiminished);
    }
}