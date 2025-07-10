package circle;

import scale.KeyFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static circle.CircleOfFifthsKeyFile.*;

public class CircleOfFifthsGenerator {

    //public static final String[] MAJORS = {"C", "G", "D", "A", "E", "B", "F#", "C#", "G#", "D#", "A#", "F"};
    //public static final String[] MINORS = {"a", "e", "b", "f#", "c#", "g#", "d#", "a#", "f", "c", "g", "d"};

    //String[] diminished = {"Bdim", "F#dim", "C#dim", "G#dim", "D#dim", "A#dim", "Fdim", "Cdim", "Gdim", "Ddim", "Adim", "Edim"};

    public static final CircleOfFifthsKeyFile[] MAJOR_KEYS = {
            major(KeyFile.C),
            major(KeyFile.G),
            major(KeyFile.D),
            major(KeyFile.A),
            major(KeyFile.E),
            major(KeyFile.B),
            sharpMajor(KeyFile.F),
            sharpMajor(KeyFile.C),
            sharpMajor(KeyFile.G),
            sharpMajor(KeyFile.D),
            sharpMajor(KeyFile.A),
            major(KeyFile.F)};
    public static final CircleOfFifthsKeyFile[] MINOR_KEYS = {
            minor(KeyFile.A),
            minor(KeyFile.E),
            minor(KeyFile.B),
            sharpMinor(KeyFile.F),
            sharpMinor(KeyFile.C),
            sharpMinor(KeyFile.G),
            sharpMinor(KeyFile.D),
            sharpMinor(KeyFile.A),
            minor(KeyFile.F),
            minor(KeyFile.C),
            minor(KeyFile.G),
            minor(KeyFile.D),
    };

    public static final CircleOfFifthsKeyFile[] DIMINISHED_KEYS = {
            diminished(KeyFile.B),
            sharpDiminished(KeyFile.F),
            sharpDiminished(KeyFile.C),
            sharpDiminished(KeyFile.G),
            sharpDiminished(KeyFile.D),
            sharpDiminished(KeyFile.A),
            diminished(KeyFile.F),
            diminished(KeyFile.C),
            diminished(KeyFile.G),
            diminished(KeyFile.D),
            diminished(KeyFile.A),
            diminished(KeyFile.E),
    };

// A - > D b G#dim c# f# E

    public static List<CircleOfFifthsKeyFile> select(CircleOfFifthsKeyFile requestedKey) {

        var highlightRelated = new ArrayList<CircleOfFifthsKeyFile>();
        if (requestedKey.isMajor()) {
            for (int i = 0; i < MAJOR_KEYS.length; i++) {
                CircleOfFifthsKeyFile majorKey = MAJOR_KEYS[i];
                if (majorKey.equals(requestedKey)) {
                   // highlightTonic = majors[i];
                  //  highlightDiminished = diminished[i];
                    if (i == 0) {
                        highlightRelated.add(MAJOR_KEYS[11]);
                        highlightRelated.add(MAJOR_KEYS[i + 1]);
                        highlightRelated.add(MINOR_KEYS[11]);
                        highlightRelated.add(MINOR_KEYS[i + 1]);
                    } else if (i == 11) {
                        highlightRelated.add(MAJOR_KEYS[0]);
                        highlightRelated.add(MAJOR_KEYS[i - 1]);
                        highlightRelated.add(MINOR_KEYS[0]);
                        highlightRelated.add(MINOR_KEYS[i - 1]);
                    } else {
                        highlightRelated.add(MAJOR_KEYS[i - 1]);
                        highlightRelated.add(MAJOR_KEYS[i + 1]);
                        highlightRelated.add(MINOR_KEYS[i - 1]);
                        highlightRelated.add(MINOR_KEYS[i + 1]);
                    }
                    highlightRelated.add(DIMINISHED_KEYS[i]);
                   highlightRelated.add(MINOR_KEYS[i]);
                }
            }
        } else if (requestedKey.isMinor()) {
            for (int i = 0; i < MINOR_KEYS.length; i++) {
                CircleOfFifthsKeyFile minorKey = MINOR_KEYS[i];
                if (minorKey.equals( requestedKey)) {
                 //   highlightTonic = minors[i];
                  //  highlightDiminished = diminished[i];
                    if (i == 0) {
                        highlightRelated.add(MINOR_KEYS[11]);
                        highlightRelated.add(MINOR_KEYS[i + 1]);
                        highlightRelated.add(MAJOR_KEYS[11]);
                        highlightRelated.add(MAJOR_KEYS[i + 1]);
                    } else if (i == 11) {
                        highlightRelated.add(MINOR_KEYS[0]);
                        highlightRelated.add(MINOR_KEYS[i - 1]);
                        highlightRelated.add(MAJOR_KEYS[0]);
                        highlightRelated.add(MAJOR_KEYS[i - 1]);
                    } else {
                        highlightRelated.add(MAJOR_KEYS[i - 1]);
                        highlightRelated.add(MAJOR_KEYS[i + 1]);
                        highlightRelated.add(MINOR_KEYS[i - 1]);
                        highlightRelated.add(MINOR_KEYS[i + 1]);
                    }
                    highlightRelated.add(DIMINISHED_KEYS[i]);
                    highlightRelated.add(MAJOR_KEYS[i]);

                }
            }
        }
        return highlightRelated;
    }


}
