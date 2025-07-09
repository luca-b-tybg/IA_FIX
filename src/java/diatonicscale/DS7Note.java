package diatonicscale;

import scale.KeyFile;
import scale.Mode;
import scale.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This class generates a 7-note musical scale based on a specified key and mode.
 * It handles the proper calculation of notes and applies necessary sharps/flats
 * to maintain the correct tonal relationships for the selected mode.
 */
public class DS7Note {
    // Core components for scale generation
    private KeyFile key;                        // The tonic/root note of the scale (e.g., C, D, E, etc.)
    private Mode mode;                     // The mode of the scale (Ionian/Major, Dorian, Phrygian, etc.)
    private static List<KeyFile> C_MAJOR_NOTES = Arrays.asList(KeyFile.C, KeyFile.D, KeyFile.E, KeyFile.F, KeyFile.G, KeyFile.A, KeyFile.B);  // Natural notes in C major
    private KeyFile[] cOrganisedScale = new KeyFile[7];  // C major scale reorganized to start from the tonic
    private Note[] organisedScale = new Note[7];   // Final scale with appropriate sharps/flats applied

    /**
     * Semitone patterns for the 7 modes:
     * Each row represents the semitone intervals between consecutive notes in a mode
     * For example, Ionian/Major mode: 2-2-1-2-2-2-1 (whole-whole-half-whole-whole-whole-half steps)
     */
    private static Map<Mode, List<Integer>> semitones = Map.of(
            Mode.IONIAN, Arrays.asList(2, 2, 1, 2, 2, 2, 1), // Ionian/Major
            Mode.DORIAN, Arrays.asList(2, 1, 2, 2, 2, 1, 2), // Dorian
            Mode.PHRYGIAN, Arrays.asList(1, 2, 2, 2, 1, 2, 2), // Phrygian
            Mode.LYDIAN, Arrays.asList(2, 2, 2, 1, 2, 2, 1), // Lydian
            Mode.MIXOLYDIAN, Arrays.asList(2, 2, 1, 2, 2, 1, 2), // Mixolydian
            Mode.AEOLIAN, Arrays.asList(2, 1, 2, 2, 1, 2, 2), // Aeolian/Minor
            Mode.LOCRIAN, Arrays.asList(1, 2, 2, 1, 2, 2, 2)  // Locrian
    );

    //in: key + mode
    //take mode and search for the list of semitones in the mode
    private static Map<Note, List<String>> scales = Map.of(
            Note.forKey(KeyFile.C), Arrays.asList("C", "D", "E", "F", "G", "A", "B"));      // C major - no sharps/flats
    //{"G", "A", "B", "C", "D", "E", "F#"},     // G major - F#
    //{"D", "E", "F#", "G", "A", "B", "C#"},    // D major - F#, C#
    //{"A", "B", "C#", "D", "E", "F#", "G#"},   // A major - F#, C#, G#
    //{"E", "F#", "G#", "A", "B", "C#", "D#"},  // E major - F#, C#, G#, D#
    //{"B", "C#", "D#", "E", "F#", "G#", "A#"}, // B major - F#, C#, G#, D#, A#
    //{"F", "G", "A", "Bb", "C", "D", "E"},     // F major - Bb

    //{"Db", "Eb", "F", "Gb", "Ab", "Bb", "C"},    // Db/C# major
    //{"Ab", "Bb", "C", "Db", "Eb", "F", "G"},   // Ab/G# major - F#, C#, G#
    //{"F#", "G#", "A#", "B", "C#", "D#", "E#"},     // F#/Gb major - remember, E# = F
    //{"Bb", "C", "D", "Eb", "F", "G", "A"}      // Bb/A# major
    //{"Eb" "F", "G", "Ab", "Bb", "C", "D"}    //  Eb


    // Known major scales with their correct accidentals for reference
    private static Map<Note, List<Note>> majorScales = Map.of(
            Note.forKey(KeyFile.C),
            Arrays.asList(Note.forKey(KeyFile.C),
                    Note.forKey(KeyFile.D),
                    Note.forKey(KeyFile.E),
                    Note.forKey(KeyFile.F),
                    Note.forKey(KeyFile.G),
                    Note.forKey(KeyFile.A),
                    Note.forKey(KeyFile.B)),     // C major - no sharps/flats
            Note.forKey(KeyFile.G), Arrays.asList(
                    Note.forKey(KeyFile.G),
                    Note.forKey(KeyFile.A),
                    Note.forKey(KeyFile.B),
                    Note.forKey(KeyFile.C),
                    Note.forKey(KeyFile.D),
                    Note.forKey(KeyFile.E),
                    Note.sharp(KeyFile.F)),     // G major - F#
            Note.forKey(KeyFile.D),
            Arrays.asList(
                    Note.forKey(KeyFile.D),
                    Note.forKey(KeyFile.E),
                    Note.sharp(KeyFile.F),
                    Note.forKey(KeyFile.G),
                    Note.forKey(KeyFile.A),
                    Note.forKey(KeyFile.B),
                    Note.sharp(KeyFile.C)),   // D major - F#, C#
            Note.forKey(KeyFile.A),
            Arrays.asList(
                    Note.forKey(KeyFile.A),
                    Note.forKey(KeyFile.B),
                    Note.sharp(KeyFile.C),
                    Note.forKey(KeyFile.D),
                    Note.forKey(KeyFile.E),
                    Note.sharp(KeyFile.F),
                    Note.sharp(KeyFile.G))   // A major - F#, C#, G#
            // KeyFile.E,  Arrays.asList("E", "F#", "G#", "A", "B", "C#", "D#"),  // E major - F#, C#, G#, D#
            // KeyFile.B, Arrays.asList("B", "C#", "D#", "E", "F#", "G#", "A#"), // B major - F#, C#, G#, D#, A#
            // KeyFile.F,  Arrays.asList("F", "G", "A", "Bb", "C", "D", "E")     // F major - Bb
            //  {"Bb", "C", "D", "Eb", "F", "G", "A"},    // Bb major - Bb, Eb
            //  {"Eb", "F", "G", "Ab", "Bb", "C", "D"},   // Eb major - Bb, Eb, Ab
            //  {"Ab", "Bb", "C", "Db", "Eb", "F", "G"}   // Ab major - Bb, Eb, Ab, Db
    );

    //   private String[] scaleTones = new String[7];        // Semitone pattern for the selected mode
    private Integer[] organisedCTones = new Integer[7];   // Reorganized semitone pattern of C major

    public DS7Note(KeyFile key, Mode mode) {
        this.key = key;
        this.mode = mode;
        // Find where our tonic is in the C major scale
        //   findNoteIndex tiTest = new findNoteIndex();
        int tonicIndex = Math.max(C_MAJOR_NOTES.indexOf(key), 0);

        // Reorganize the scale and semitones starting from the tonic
        for (int i = 0; i < 7; i++) {
            // Use modulo to wrap around when we reach the end of the array
            cOrganisedScale[i] = C_MAJOR_NOTES.get((tonicIndex + i) % 7);
            organisedScale[i] = new Note(C_MAJOR_NOTES.get((tonicIndex + i) % 7));

            // Reorganize C major's semitone pattern (Ionian mode) to match our new starting point
            organisedCTones[i] = semitones.get(Mode.IONIAN).get((tonicIndex + i) % 7);
        }
    }

    // Getters and setters


    public Note[] getOrganisedScale() {
        return organisedScale;
    }

    public KeyFile[] getcOrganisedScale() {
        return cOrganisedScale;
    }

    /**
     * Applies appropriate sharps and flats to the scale based on the selected key and mode
     */
    public void findSharpsAndFlats(KeyFile key) {
        // For Ionian/Major mode, use predefined major scales
        if (mode == Mode.IONIAN) {
            applyMajorScale(key, mode);
            return;
        }

        // For other modes, first get the relative major key's scale
        // Then rotate it to get the correct mode
        List<Note> majorScale = getMajorScaleForKey(key, mode);
        applyModeToMajorScale(majorScale);
    }

    /**
     * Applies a predefined major scale based on the current key
     */
    private List<Note> applyMajorScale(KeyFile key, Mode mode) {
        return majorScales.getOrDefault(Note.forKey(key), applyAlgorithmicScale(mode));
    }

    /**
     * Determines the relative major scale for a given mode
     */
    private List<Note> getMajorScaleForKey(KeyFile key, Mode mode) {
        // First find the relative major key based on the mode
        // For example, if we're in D Dorian, the relative major is C
        int modeOffset = mode.ordinal();
        int cMajorIndex = Math.max(C_MAJOR_NOTES.indexOf(key), 0);
        KeyFile relativeMajorKey = C_MAJOR_NOTES.get((cMajorIndex - modeOffset + 7) % 7);
        return majorScales.getOrDefault(relativeMajorKey, majorScales.get(KeyFile.C));
    }

    /**
     * Applies mode rotation to a major scale to get the correct mode
     */
    private void applyModeToMajorScale(List<Note> majorScale) {
        int modeOffset = mode.ordinal();
        List<Note> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            result.add(majorScale.get((i++ + modeOffset) % 7));
        }
    }

    /**
     * Fallback method that uses the original algorithm approach for scales
     * that aren't explicitly defined
     */
    private List<Note> applyAlgorithmicScale(Mode mode) {
        List<Integer> scaleTones = semitones.get(mode);
        for (int current = 0; current < 7; current++) {
            int next = (current + 1) % 7;

            int st = scaleTones.get(current);
            int oct = organisedCTones[current];
            int st1 = scaleTones.get(next);
            int oct1 = organisedCTones[next];

            if (st > oct && st1 < oct1) {
                organisedCTones[current] = scaleTones.get(current);
                organisedCTones[next] = scaleTones.get(next);
                organisedScale[next].setSharp(true);
            }

            if (st > oct && st1 == oct1) {
                organisedCTones[current] = scaleTones.get(current);
                organisedCTones[next] = scaleTones.get(next);
                organisedScale[current].setFlat(true);
            }

            if (st < oct && st1 > oct1) {
                organisedCTones[current] = scaleTones.get(current);
                organisedCTones[next] = scaleTones.get(next);
                organisedScale[next].setFlat(true);
            }

            if (st < oct && st1 == oct1) {
                organisedCTones[current] = scaleTones.get(current);
                organisedCTones[next] = scaleTones.get(next);
                organisedScale[next].setFlat(true);
            }
        }
        return new ArrayList<>();
    }

}