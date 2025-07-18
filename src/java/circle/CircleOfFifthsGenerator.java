package circle;

import scale.KeyFile;

import java.util.ArrayList;
import java.util.List;

import static circle.CircleOfFifthsKeyFile.*;

/*
    take key and mode the user is working in
    take chord type the user is starting with
    generates a default circle of fifths. the same image will always be used to present the circle of fifths
    highlight section of circle of fifths being worked with. ie draw an outline around the tonic key and the notes next to it that are used
    label which surrounding notes/chords are what chord type in relation to the tonic ie c major will have:
    f - subdominant
    g - dominant
    a - superdominant/submediant
    etc...

    each chord change in music theory has a certain sound
    changing from a dominant chord to a tonic chord is a perfect cadence, which has this resolved, finished sound
    this function aims to take a chord within a key (let say g within c major), and determine either:
    how it sounds when it changes from g to c (perfect cadence)
    or
    what chord is needed to achieve a sound input by the user (ie user asks for a resolved sound, the program will output c major and that it is a
    perfect cadence)

    this will help the client with generating chord progressions

    the function should also have a feature to restablish the key their in, in case they want to make a key change in the piece their composing
    ie a progression goes from c to g, and then the client decides to continue composing in g major, then the relevant chords to the g major scale shows up


   the chord progressions and key changes will be stored in a file that represents their chord progression they created.
   also output the notes in each chord needed ie c major will output c, e and g, d major will output d, f#, and a, etc (just to avoid confusion)

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
