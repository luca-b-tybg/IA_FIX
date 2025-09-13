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
    private static Map<Note, List<Note>> majorScales = new LinkedHashMap<>();

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
                        Note.forKey(KeyFile.C)));// Db major
        majorScales.put(Note.flat(KeyFile.A), Arrays.asList(
                Note.flat(KeyFile.A),
                Note.flat(KeyFile.B),
                Note.forKey(KeyFile.C),
                Note.flat(KeyFile.D),
                Note.flat(KeyFile.E),
                Note.forKey(KeyFile.F),
                Note.forKey(KeyFile.G))); // Ab major
        majorScales.put(Note.sharp(KeyFile.F), Arrays.asList(
                Note.sharp(KeyFile.F),
                Note.sharp(KeyFile.G),
                Note.sharp(KeyFile.A),
                Note.forKey(KeyFile.B),
                Note.sharp(KeyFile.C),
                Note.sharp(KeyFile.D),
                Note.forKey(KeyFile.E))); // F# major
        majorScales.put(Note.flat(KeyFile.B),
                Arrays.asList(Note.flat(KeyFile.B),
                        Note.forKey(KeyFile.C),
                        Note.forKey(KeyFile.D),
                        Note.flat(KeyFile.E),
                        Note.forKey(KeyFile.F),
                        Note.forKey(KeyFile.G),
                        Note.forKey(KeyFile.A))); // Bb major
        majorScales.put(Note.flat(KeyFile.E),
                Arrays.asList(Note.flat(KeyFile.E),
                        Note.forKey(KeyFile.F),
                        Note.forKey(KeyFile.G),
                        Note.flat(KeyFile.A),
                        Note.flat(KeyFile.B),
                        Note.forKey(KeyFile.C),
                        Note.forKey(KeyFile.D))); // Eb major
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
            return majorScales.get(scaleNote);
        }

        // For other modes, find the relative major and rotate it
        Note relativeMajor = getRelativeMajorKey(scaleNote, mode);
        List<Note> relativeMajorScale = majorScales.get(relativeMajor);

        if (relativeMajorScale == null) {
            // Try to find any major scale with the same key
            for (Map.Entry<Note, List<Note>> entry : majorScales.entrySet()) {
                if (entry.getKey().getKey() == relativeMajor.getKey()) {
                    relativeMajorScale = entry.getValue();
                    break;
                }
            }
        }

        if (relativeMajorScale == null) {
            return majorScales.get(Note.forKey(KeyFile.C)); // fallback
        }

        return applyModeToMajorScale(mode, relativeMajorScale);
    }

    /**
     * Calculate the relative major key for a given modal tonic
     */
    private Note getRelativeMajorKey(Note modalTonic, Mode mode) {
        // Map mode to its relative major interval
        int modeOffset = mode.ordinal();
        int modalTonicIndex = C_MAJOR_NOTES.indexOf(modalTonic.getKey());
        int relativeMajorIndex = (modalTonicIndex - modeOffset + 7) % 7;
        KeyFile relativeMajorKeyFile = C_MAJOR_NOTES.get(relativeMajorIndex);

        // Create relative major with same accidentals as modal tonic
        Note relativeMajor;
        if (modalTonic.isSharp()) {
            relativeMajor = Note.sharp(relativeMajorKeyFile);
        } else if (modalTonic.isFlat()) {
            relativeMajor = Note.flat(relativeMajorKeyFile);
        } else {
            relativeMajor = Note.forKey(relativeMajorKeyFile);
        }

        // Try to find exact match first
        if (majorScales.containsKey(relativeMajor)) {
            return relativeMajor;
        }

        // Try to find any match with the same key
        for (Note candidate : majorScales.keySet()) {
            if (candidate.getKey() == relativeMajorKeyFile) {
                return candidate;
            }
        }

        return relativeMajor;
    }

    /**
     * Applies mode rotation to a major scale to get the correct modal scale
     */
    private List<Note> applyModeToMajorScale(Mode mode, List<Note> relativeMajorScale) {
        int modeOffset = mode.ordinal();
        List<Note> result = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Note originalNote = relativeMajorScale.get((i + modeOffset) % 7);
            // Create a copy to avoid modifying the original
            result.add(new Note(originalNote));
        }

        return result;
    }
}