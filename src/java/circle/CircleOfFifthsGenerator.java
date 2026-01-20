package circle;

import scale.KeyFile;

import java.util.ArrayList;
import java.util.List;

import static circle.CircleOfFifthsKeyFile.*;

/*
    overview:
    need to take key and tonality the user is working in
    generate a default circle of fifths. the same image will always be used to present the circle of fifths
    highlight section of circle of fifths being worked with. ie draw an outline around the tonic key and the notes next to it that are used:
    this will help the client with generating chord progressions
    the chord progressions and key changes will be stored in a file that represents their chord progression they created.
*/

public class CircleOfFifthsGenerator {


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
