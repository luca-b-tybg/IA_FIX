package circle;

import scale.KeyFile;

import java.util.ArrayList;
import java.util.List;

import static circle.CircleOfFifthsKeyFile.*;

/*
 Circle of Fifths generator that correctly maps major and minor keys
 according to their relationships in the circle of fifths.
 */
public class CircleOfFifthsGenerator {

    // Major keys arranged in circle of fifths order (clockwise from C)
    public static final CircleOfFifthsKeyFile[] MAJOR_KEYS = {
            major(KeyFile.C),           // 0 sharps/flats
            major(KeyFile.G),           // 1 sharp (F#)
            major(KeyFile.D),           // 2 sharps (F#, C#)
            major(KeyFile.A),           // 3 sharps (F#, C#, G#)
            major(KeyFile.E),           // 4 sharps (F#, C#, G#, D#)
            major(KeyFile.B),           // 5 sharps (F#, C#, G#, D#, A#)
            sharpMajor(KeyFile.F),      // 6 sharps (F# major)
            sharpMajor(KeyFile.C),      // 7 sharps (C# major)
            flatMajor(KeyFile.A),       // 4 flats (Ab major)
            flatMajor(KeyFile.E),       // 3 flats (Eb major)
            flatMajor(KeyFile.B),       // 2 flats (Bb major)
            major(KeyFile.F)            // 1 flat (Bb)
    };

    // Minor keys - relative minors of the major keys above (same key signature)
    public static final CircleOfFifthsKeyFile[] MINOR_KEYS = {
            minor(KeyFile.A),           // relative to C major - 0 sharps/flats
            minor(KeyFile.E),           // relative to G major - 1 sharp (F#)
            minor(KeyFile.B),           // relative to D major - 2 sharps (F#, C#)
            sharpMinor(KeyFile.F),      // relative to A major - 3 sharps (F# minor)
            sharpMinor(KeyFile.C),      // relative to E major - 4 sharps (C# minor)
            sharpMinor(KeyFile.G),      // relative to B major - 5 sharps (G# minor)
            sharpMinor(KeyFile.D),      // relative to F# major - 6 sharps (D# minor)
            sharpMinor(KeyFile.A),      // relative to C# major - 7 sharps (A# minor)
            flatMinor(KeyFile.F),       // relative to Ab major - 4 flats (F minor)
            minor(KeyFile.C),           // relative to Eb major - 3 flats (C minor)
            minor(KeyFile.G),           // relative to Bb major - 2 flats (G minor)
            minor(KeyFile.D),           // relative to F major - 1 flat (D minor)
    };

    // Diminished chords built on the 7th degree of each major scale
    public static final CircleOfFifthsKeyFile[] DIMINISHED_KEYS = {
            diminished(KeyFile.B),      // vii° of C major
            sharpDiminished(KeyFile.F), // vii° of G major (F# dim)
            sharpDiminished(KeyFile.C), // vii° of D major (C# dim)
            sharpDiminished(KeyFile.G), // vii° of A major (G# dim)
            sharpDiminished(KeyFile.D), // vii° of E major (D# dim)
            sharpDiminished(KeyFile.A), // vii° of B major (A# dim)
            diminished(KeyFile.E),      // vii° of F# major (E# dim, enharmonic F dim)
            diminished(KeyFile.B),      // vii° of C# major (B# dim, enharmonic C dim)
            diminished(KeyFile.G),      // vii° of Ab major
            diminished(KeyFile.D),      // vii° of Eb major
            diminished(KeyFile.A),      // vii° of Bb major
            diminished(KeyFile.E),      // vii° of F major
    };

    // Helper method to create flat major keys
    private static CircleOfFifthsKeyFile flatMajor(KeyFile key) {
        return CircleOfFifthsKeyFile.flatMajor(key);
    }
    
    // Helper method to create flat minor keys  
    private static CircleOfFifthsKeyFile flatMinor(KeyFile key) {
        return CircleOfFifthsKeyFile.flatMinor(key);
    }

    public static List<CircleOfFifthsKeyFile> select(CircleOfFifthsKeyFile requestedKey) {
        var highlightRelated = new ArrayList<CircleOfFifthsKeyFile>();
        
        if (requestedKey.isMajor()) {
            for (int i = 0; i < MAJOR_KEYS.length; i++) {
                CircleOfFifthsKeyFile majorKey = MAJOR_KEYS[i];
                if (majorKey.equals(requestedKey)) {
                    // Add subdominant (IV) and dominant (V) relationships
                    if (i == 0) {
                        highlightRelated.add(MAJOR_KEYS[11]); // subdominant
                        highlightRelated.add(MAJOR_KEYS[i + 1]); // dominant
                        highlightRelated.add(MINOR_KEYS[11]); // relative of subdominant
                        highlightRelated.add(MINOR_KEYS[i + 1]); // relative of dominant
                    } else if (i == 11) {
                        highlightRelated.add(MAJOR_KEYS[0]); // dominant
                        highlightRelated.add(MAJOR_KEYS[i - 1]); // subdominant
                        highlightRelated.add(MINOR_KEYS[0]); // relative of dominant
                        highlightRelated.add(MINOR_KEYS[i - 1]); // relative of subdominant
                    } else {
                        highlightRelated.add(MAJOR_KEYS[i - 1]); // subdominant
                        highlightRelated.add(MAJOR_KEYS[i + 1]); // dominant
                        highlightRelated.add(MINOR_KEYS[i - 1]); // relative of subdominant
                        highlightRelated.add(MINOR_KEYS[i + 1]); // relative of dominant
                    }
                    // Add relative minor and leading tone diminished
                    highlightRelated.add(MINOR_KEYS[i]); // relative minor
                    highlightRelated.add(DIMINISHED_KEYS[i]); // leading tone diminished
                    break;
                }
            }
        } else if (requestedKey.isMinor()) {
            for (int i = 0; i < MINOR_KEYS.length; i++) {
                CircleOfFifthsKeyFile minorKey = MINOR_KEYS[i];
                if (minorKey.equals(requestedKey)) {
                    // Add related keys for minor
                    if (i == 0) {
                        highlightRelated.add(MINOR_KEYS[11]); // iv (subdominant minor)
                        highlightRelated.add(MINOR_KEYS[i + 1]); // v (dominant minor, though usually major)
                        highlightRelated.add(MAJOR_KEYS[11]); // bVII (subtonic major)
                        highlightRelated.add(MAJOR_KEYS[i + 1]); // V (dominant major)
                    } else if (i == 11) {
                        highlightRelated.add(MINOR_KEYS[0]); // v
                        highlightRelated.add(MINOR_KEYS[i - 1]); // iv
                        highlightRelated.add(MAJOR_KEYS[0]); // V
                        highlightRelated.add(MAJOR_KEYS[i - 1]); // bVII
                    } else {
                        highlightRelated.add(MINOR_KEYS[i - 1]); // iv
                        highlightRelated.add(MINOR_KEYS[i + 1]); // v
                        highlightRelated.add(MAJOR_KEYS[i - 1]); // bVII
                        highlightRelated.add(MAJOR_KEYS[i + 1]); // V
                    }
                    // Add relative major and diminished
                    highlightRelated.add(MAJOR_KEYS[i]); // relative major
                    highlightRelated.add(DIMINISHED_KEYS[i]); // ii° (supertonic diminished)
                    break;
                }
            }
        }
        return highlightRelated;
    }
}