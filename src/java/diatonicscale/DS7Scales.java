package diatonicscale;

import scale.KeyFile;
import scale.Mode;
import scale.Note;

import java.util.*;

/**
 * This class generates a 7-note musical scale based on a specified key and mode.
 * It handles the proper calculation of notes and applies necessary sharps/flats
 * to maintain the correct tonal relationships for the selected mode.
 */
public class DS7Scales {
    // Core components for scale generation
    public static List<KeyFile> C_MAJOR_NOTES = Arrays.asList(KeyFile.C, KeyFile.D, KeyFile.E, KeyFile.F, KeyFile.G, KeyFile.A, KeyFile.B);  // Natural notes in C major
    //TODO review the usage of these
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

    // Known major scales with their correct accidentals for reference
    private static Map<Note, List<Note>> majorScales = new LinkedHashMap<>(

    );

    static {
        majorScales.put(
                Note.forKey(KeyFile.C),
                Arrays.asList(Note.forKey(KeyFile.C),
                        Note.forKey(KeyFile.D),
                        Note.forKey(KeyFile.E),
                        Note.forKey(KeyFile.F),
                        Note.forKey(KeyFile.G),
                        Note.forKey(KeyFile.A),
                        Note.forKey(KeyFile.B)));     // C major - no sharps/flats
        majorScales.put(
                Note.forKey(KeyFile.G), Arrays.asList(
                        Note.forKey(KeyFile.G),
                        Note.forKey(KeyFile.A),
                        Note.forKey(KeyFile.B),
                        Note.forKey(KeyFile.C),
                        Note.forKey(KeyFile.D),
                        Note.forKey(KeyFile.E),
                        Note.sharp(KeyFile.F)));     // G major - F#
        majorScales.put(Note.forKey(KeyFile.D),
                Arrays.asList(
                        Note.forKey(KeyFile.D),
                        Note.forKey(KeyFile.E),
                        Note.sharp(KeyFile.F),
                        Note.forKey(KeyFile.G),
                        Note.forKey(KeyFile.A),
                        Note.forKey(KeyFile.B),
                        Note.sharp(KeyFile.C)));   // D major - F#, C#
        majorScales.put(Note.forKey(KeyFile.A),
                Arrays.asList(
                        Note.forKey(KeyFile.A),
                        Note.forKey(KeyFile.B),
                        Note.sharp(KeyFile.C),
                        Note.forKey(KeyFile.D),
                        Note.forKey(KeyFile.E),
                        Note.sharp(KeyFile.F),
                        Note.sharp(KeyFile.G)));   // A major - F#, C#, G#
        majorScales.put(Note.forKey(KeyFile.E),
                Arrays.asList(Note.forKey(KeyFile.E),
                        Note.sharp(KeyFile.F),
                        Note.sharp(KeyFile.G),
                        Note.forKey(KeyFile.A),
                        Note.forKey(KeyFile.B),
                        Note.sharp(KeyFile.C),
                        Note.sharp(KeyFile.D)));  // E major - F#, C#, G#, D#
        majorScales.put(Note.forKey(KeyFile.B),
                Arrays.asList(Note.forKey(KeyFile.B),
                        Note.sharp(KeyFile.C),
                        Note.sharp(KeyFile.D),
                        Note.forKey(KeyFile.E),
                        Note.sharp(KeyFile.F), Note.sharp(KeyFile.G)
                        , Note.sharp(KeyFile.A)));// B major - F#, C#, G#, D#, A#
        majorScales.put(Note.forKey(KeyFile.F),
                Arrays.asList(Note.forKey(KeyFile.F),
                        Note.forKey(KeyFile.G),
                        Note.forKey(KeyFile.A),
                        Note.flat(KeyFile.B),
                        Note.forKey(KeyFile.C),
                        Note.forKey(KeyFile.D),
                        Note.forKey(KeyFile.E)));     // F major - Bb
        majorScales.put(Note.flat(KeyFile.D),
                Arrays.asList(
                        Note.flat(KeyFile.D),
                        Note.flat(KeyFile.E),
                        Note.forKey(KeyFile.F),
                        Note.flat(KeyFile.G),
                        Note.flat(KeyFile.A),
                        Note.flat(KeyFile.B),
                        Note.forKey(KeyFile.C)));// Db/C# major - //{"Db", "Eb", "F", "Gb", "Ab", "Bb", "C"},
        majorScales.put(Note.flat(KeyFile.A), Arrays.asList(
                Note.flat(KeyFile.A),
                Note.flat(KeyFile.B),
                Note.forKey(KeyFile.C),
                Note.flat(KeyFile.D),
                Note.flat(KeyFile.E),
                Note.forKey(KeyFile.F),
                Note.forKey(KeyFile.G)//{"Ab", "Bb", "C", "Db", "Eb", "F", "G"},   // Ab/G# major - F#, C#, G#
        ));
        majorScales.put(Note.sharp(KeyFile.F), Arrays.asList(
                Note.sharp(KeyFile.F),
                Note.sharp(KeyFile.G),
                Note.sharp(KeyFile.A),
                Note.forKey(KeyFile.B),
                Note.sharp(KeyFile.C),
                Note.sharp(KeyFile.D),
                Note.forKey(KeyFile.E) //{"F#", "G#", "A#", "B", "C#", "D#", "E#"},     // F#/Gb major - remember, E# = F
        ));
        majorScales.put(Note.flat(KeyFile.B),
                Arrays.asList(Note.flat(KeyFile.B),
                        Note.forKey(KeyFile.C),
                        Note.forKey(KeyFile.D),
                        Note.flat(KeyFile.E),
                        Note.forKey(KeyFile.F),
                        Note.forKey(KeyFile.G),
                        Note.forKey(KeyFile.A))); //{"Bb", "C", "D", "Eb", "F", "G", "A"}      // Bb/A# major
        majorScales.put(Note.flat(KeyFile.E),
                Arrays.asList(Note.flat(KeyFile.E),
                        Note.forKey(KeyFile.F),
                        Note.forKey(KeyFile.G),
                        Note.flat(KeyFile.A),
                        Note.flat(KeyFile.B),
                        Note.forKey(KeyFile.C),
                        Note.forKey(KeyFile.D)));//{"Eb" "F", "G", "Ab", "Bb", "C", "D"}    //  Eb
    }

    public static Note[] getKnownScales() {
        Note[] entries = majorScales.keySet().toArray(new Note[0]);
        Arrays.sort(entries, Comparator.comparing(Note::toString));
        return entries;
    }

    public static int getNotePositionRelativeToMiddleC(Note note) {
        int keyPosition = DS7Scales.C_MAJOR_NOTES.indexOf(note.getKey());
        return keyPosition + (7 * (note.getOctave() - 4));
    }

    public static int getNotePosition(KeyFile keyFile) {
        return DS7Scales.C_MAJOR_NOTES.indexOf(keyFile);
    }


    public static KeyFile getKeyForScorePosition(int keyScorePosition) {

        var x = keyScorePosition < C_MAJOR_NOTES.size() ? keyScorePosition : keyScorePosition % 7;
        return DS7Scales.C_MAJOR_NOTES.get(x);
    }

    //   private String[] scaleTones = new String[7];        // Semitone pattern for the selected mode
    private Integer[] organisedCTones = new Integer[7];   // Reorganized semitone pattern of C major

    public DS7Scales(Note scaleNote) {

        // Find where our tonic is in the C major scale
        int tonicIndex = Math.max(C_MAJOR_NOTES.indexOf(scaleNote.getKey()), 0);

        // Reorganize the scale and semitones starting from the tonic
        for (int i = 0; i < 7; i++) {
            // Use modulo to wrap around when we reach the end of the array
            cOrganisedScale[i] = C_MAJOR_NOTES.get((tonicIndex + i) % 7);
            organisedScale[i] = new Note(C_MAJOR_NOTES.get((tonicIndex + i) % 7));

            // Reorganize C major's semitone pattern (Ionian mode) to match our new starting point
            organisedCTones[i] = semitones.get(Mode.IONIAN).get((tonicIndex + i) % 7);
        }
    }


    /**
     * Applies appropriate sharps and flats to the scale based on the selected key and mode
     */
    public List<Note> findSharpsAndFlats(Note scaleNote, Mode mode) {
        // For Ionian/Major mode, use predefined major scales
        if (mode == Mode.IONIAN) {
            // applyMajorScale(key, mode);
            return majorScales.get(scaleNote);
        }

        // For other modes, first get the relative major key's scale
        // Then rotate it to get the correct mode
        List<Note> majorScale = getMajorScaleForKey(scaleNote, mode);
        return applyModeToMajorScale(mode, majorScale);
    }

    /**
     * Determines the relative major scale for a given mode
     */
    private List<Note> getMajorScaleForKey(Note note, Mode mode) {
        // First find the relative major key based on the mode
        // For example, if we're in D Dorian, the relative major is C
        int modeOffset = mode.ordinal();
        int cMajorIndex = Math.max(C_MAJOR_NOTES.indexOf(note.getKey()), 0);

        ///TODO: Fix these lookups
        KeyFile relativeMajorKey = C_MAJOR_NOTES.get((cMajorIndex - modeOffset + 7) % 7);
        List<Boolean> flatFlags = Arrays.asList(true, false);
        List<Boolean> sharpFlags = Arrays.asList(true, false);
        for (boolean isSharp : flatFlags) {
            for (boolean isFlat : sharpFlags) {
                Note noteCandidate = new Note(relativeMajorKey, isFlat, isSharp);
                if (majorScales.containsKey(noteCandidate)) {
                    return majorScales.get(noteCandidate);
                }

            }
        }
        return majorScales.get(Note.forKey(KeyFile.C));
    }

    /**
     * Applies mode rotation to a major scale to get the correct mode
     */
    private List<Note> applyModeToMajorScale(Mode mode, List<Note> majorScale) {
        int modeOffset = mode.ordinal();
        List<Note> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            result.add(majorScale.get((i++ + modeOffset) % 7));
        }
        return result;
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