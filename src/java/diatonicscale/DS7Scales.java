package diatonicscale;

import scale.KeyFile;
import scale.Mode;
import scale.Note;

import java.util.*;

/*
  This class generates a 7-note musical scale based on a specified key and mode.
  It handles the proper calculation of notes and applies necessary sharps/flats
  to maintain the correct tonal relationships for the selected mode.
 */
public class DS7Scales {
    // Core components for scale generation
    public static List<KeyFile> C_MAJOR_NOTES = Arrays.asList(KeyFile.C, KeyFile.D, KeyFile.E, KeyFile.F, KeyFile.G, KeyFile.A, KeyFile.B);

    private KeyFile[] cOrganisedScale = new KeyFile[7];
    private Note[] organisedScale = new Note[7];

    /*
      Semitone patterns for the 7 modes:
      Each row represents the semitone intervals between consecutive notes in a mode
     */
    private static Map<Mode, List<Integer>> semitones = Map.of(
            Mode.IONIAN, Arrays.asList(2, 2, 1, 2, 2, 2, 1), // Ionian/Major: W-W-H-W-W-W-H
            Mode.DORIAN, Arrays.asList(2, 1, 2, 2, 2, 1, 2), // Dorian: W-H-W-W-W-H-W
            Mode.PHRYGIAN, Arrays.asList(1, 2, 2, 2, 1, 2, 2), // Phrygian: H-W-W-W-H-W-W
            Mode.LYDIAN, Arrays.asList(2, 2, 2, 1, 2, 2, 1), // Lydian: W-W-W-H-W-W-H
            Mode.MIXOLYDIAN, Arrays.asList(2, 2, 1, 2, 2, 1, 2), // Mixolydian: W-W-H-W-W-H-W
            Mode.AEOLIAN, Arrays.asList(2, 1, 2, 2, 1, 2, 2), // Aeolian/Minor: W-H-W-W-H-W-W
            Mode.LOCRIAN, Arrays.asList(1, 2, 2, 1, 2, 2, 2)  // Locrian: H-W-W-H-W-W-W
    );

    // Known major scales with their correct accidentals for reference
    private static Map<Note, List<Note>> majorScales = new LinkedHashMap<>();

    static {
        majorScales.put(Note.forKey(KeyFile.C),
                Arrays.asList(Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.forKey(KeyFile.E),
                        Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.forKey(KeyFile.B)));
        majorScales.put(Note.forKey(KeyFile.G),
                Arrays.asList(Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.forKey(KeyFile.B),
                        Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.forKey(KeyFile.E), Note.sharp(KeyFile.F)));
        majorScales.put(Note.forKey(KeyFile.D),
                Arrays.asList(Note.forKey(KeyFile.D), Note.forKey(KeyFile.E), Note.sharp(KeyFile.F),
                        Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.forKey(KeyFile.B), Note.sharp(KeyFile.C)));
        majorScales.put(Note.forKey(KeyFile.A),
                Arrays.asList(Note.forKey(KeyFile.A), Note.forKey(KeyFile.B), Note.sharp(KeyFile.C),
                        Note.forKey(KeyFile.D), Note.forKey(KeyFile.E), Note.sharp(KeyFile.F), Note.sharp(KeyFile.G)));
        majorScales.put(Note.forKey(KeyFile.E),
                Arrays.asList(Note.forKey(KeyFile.E), Note.sharp(KeyFile.F), Note.sharp(KeyFile.G),
                        Note.forKey(KeyFile.A), Note.forKey(KeyFile.B), Note.sharp(KeyFile.C), Note.sharp(KeyFile.D)));
        majorScales.put(Note.forKey(KeyFile.B),
                Arrays.asList(Note.forKey(KeyFile.B), Note.sharp(KeyFile.C), Note.sharp(KeyFile.D),
                        Note.forKey(KeyFile.E), Note.sharp(KeyFile.F), Note.sharp(KeyFile.G), Note.sharp(KeyFile.A)));
        majorScales.put(Note.sharp(KeyFile.F),
                Arrays.asList(Note.sharp(KeyFile.F), Note.sharp(KeyFile.G), Note.sharp(KeyFile.A),
                        Note.forKey(KeyFile.B), Note.sharp(KeyFile.C), Note.sharp(KeyFile.D), Note.sharp(KeyFile.E)));
        majorScales.put(Note.forKey(KeyFile.F),
                Arrays.asList(Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.forKey(KeyFile.A),
                        Note.flat(KeyFile.B), Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.forKey(KeyFile.E)));
        majorScales.put(Note.flat(KeyFile.B),
                Arrays.asList(Note.flat(KeyFile.B), Note.forKey(KeyFile.C), Note.forKey(KeyFile.D),
                        Note.flat(KeyFile.E), Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.forKey(KeyFile.A)));
        majorScales.put(Note.flat(KeyFile.E),
                Arrays.asList(Note.flat(KeyFile.E), Note.forKey(KeyFile.F), Note.forKey(KeyFile.G),
                        Note.flat(KeyFile.A), Note.flat(KeyFile.B), Note.forKey(KeyFile.C), Note.forKey(KeyFile.D)));
        majorScales.put(Note.flat(KeyFile.A),
                Arrays.asList(Note.flat(KeyFile.A), Note.flat(KeyFile.B), Note.forKey(KeyFile.C),
                        Note.flat(KeyFile.D), Note.flat(KeyFile.E), Note.forKey(KeyFile.F), Note.forKey(KeyFile.G)));
        majorScales.put(Note.flat(KeyFile.D),
                Arrays.asList(Note.flat(KeyFile.D), Note.flat(KeyFile.E), Note.forKey(KeyFile.F),
                        Note.flat(KeyFile.G), Note.flat(KeyFile.A), Note.flat(KeyFile.B), Note.forKey(KeyFile.C)));
    }

    // Known modal scales for verification and direct lookup
    private static Map<String, List<Note>> knownModalScales = new LinkedHashMap<>();

    static {
        // C modes for verification
        knownModalScales.put("C-IONIAN", Arrays.asList(
                Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.forKey(KeyFile.E),
                Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.forKey(KeyFile.B)));

        knownModalScales.put("C-DORIAN", Arrays.asList(
                Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.flat(KeyFile.E),
                Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.flat(KeyFile.B)));

        knownModalScales.put("C-PHRYGIAN", Arrays.asList(
                Note.forKey(KeyFile.C), Note.flat(KeyFile.D), Note.flat(KeyFile.E),
                Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.flat(KeyFile.A), Note.flat(KeyFile.B)));

        knownModalScales.put("C-LYDIAN", Arrays.asList(
                Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.forKey(KeyFile.E),
                Note.sharp(KeyFile.F), Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.forKey(KeyFile.B)));

        knownModalScales.put("C-MIXOLYDIAN", Arrays.asList(
                Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.forKey(KeyFile.E),
                Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.flat(KeyFile.B)));

        knownModalScales.put("C-AEOLIAN", Arrays.asList(
                Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.flat(KeyFile.E),
                Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.flat(KeyFile.A), Note.flat(KeyFile.B)));

        knownModalScales.put("C-LOCRIAN", Arrays.asList(
                Note.forKey(KeyFile.C), Note.flat(KeyFile.D), Note.flat(KeyFile.E),
                Note.forKey(KeyFile.F), Note.flat(KeyFile.G), Note.flat(KeyFile.A), Note.flat(KeyFile.B)));

        // D modes
        knownModalScales.put("D-DORIAN", Arrays.asList(
                Note.forKey(KeyFile.D), Note.forKey(KeyFile.E), Note.forKey(KeyFile.F),
                Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.forKey(KeyFile.B), Note.forKey(KeyFile.C)));
    }

    // Known natural minor scales
    private static Map<Note, List<Note>> minorScales = new LinkedHashMap<>();

    static {
        minorScales.put(Note.forKey(KeyFile.A),
                Arrays.asList(Note.forKey(KeyFile.A), Note.forKey(KeyFile.B), Note.forKey(KeyFile.C),
                        Note.forKey(KeyFile.D), Note.forKey(KeyFile.E), Note.forKey(KeyFile.F), Note.forKey(KeyFile.G)));
        minorScales.put(Note.forKey(KeyFile.E),
                Arrays.asList(Note.forKey(KeyFile.E), Note.sharp(KeyFile.F), Note.forKey(KeyFile.G),
                        Note.forKey(KeyFile.A), Note.forKey(KeyFile.B), Note.forKey(KeyFile.C), Note.forKey(KeyFile.D)));
        minorScales.put(Note.forKey(KeyFile.B),
                Arrays.asList(Note.forKey(KeyFile.B), Note.sharp(KeyFile.C), Note.forKey(KeyFile.D),
                        Note.forKey(KeyFile.E), Note.sharp(KeyFile.F), Note.forKey(KeyFile.G), Note.forKey(KeyFile.A)));
        minorScales.put(Note.sharp(KeyFile.F),
                Arrays.asList(Note.sharp(KeyFile.F), Note.sharp(KeyFile.G), Note.forKey(KeyFile.A),
                        Note.forKey(KeyFile.B), Note.sharp(KeyFile.C), Note.forKey(KeyFile.D), Note.forKey(KeyFile.E)));
        minorScales.put(Note.sharp(KeyFile.C),
                Arrays.asList(Note.sharp(KeyFile.C), Note.sharp(KeyFile.D), Note.forKey(KeyFile.E),
                        Note.sharp(KeyFile.F), Note.sharp(KeyFile.G), Note.forKey(KeyFile.A), Note.forKey(KeyFile.B)));
        minorScales.put(Note.sharp(KeyFile.G),
                Arrays.asList(Note.sharp(KeyFile.G), Note.sharp(KeyFile.A), Note.forKey(KeyFile.B),
                        Note.sharp(KeyFile.C), Note.sharp(KeyFile.D), Note.forKey(KeyFile.E), Note.sharp(KeyFile.F)));
        minorScales.put(Note.sharp(KeyFile.D),
                Arrays.asList(Note.sharp(KeyFile.D), Note.sharp(KeyFile.E), Note.sharp(KeyFile.F),
                        Note.sharp(KeyFile.G), Note.sharp(KeyFile.A), Note.forKey(KeyFile.B), Note.sharp(KeyFile.C)));
        minorScales.put(Note.forKey(KeyFile.D),
                Arrays.asList(Note.forKey(KeyFile.D), Note.forKey(KeyFile.E), Note.forKey(KeyFile.F),
                        Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.flat(KeyFile.B), Note.forKey(KeyFile.C)));
        minorScales.put(Note.forKey(KeyFile.G),
                Arrays.asList(Note.forKey(KeyFile.G), Note.forKey(KeyFile.A), Note.flat(KeyFile.B),
                        Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.flat(KeyFile.E), Note.forKey(KeyFile.F)));
        minorScales.put(Note.forKey(KeyFile.C),
                Arrays.asList(Note.forKey(KeyFile.C), Note.forKey(KeyFile.D), Note.flat(KeyFile.E),
                        Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.flat(KeyFile.A), Note.flat(KeyFile.B)));
        minorScales.put(Note.forKey(KeyFile.F),
                Arrays.asList(Note.forKey(KeyFile.F), Note.forKey(KeyFile.G), Note.flat(KeyFile.A),
                        Note.flat(KeyFile.B), Note.forKey(KeyFile.C), Note.flat(KeyFile.D), Note.flat(KeyFile.E)));
        minorScales.put(Note.flat(KeyFile.B),
                Arrays.asList(Note.flat(KeyFile.B), Note.forKey(KeyFile.C), Note.flat(KeyFile.D),
                        Note.flat(KeyFile.E), Note.forKey(KeyFile.F), Note.flat(KeyFile.G), Note.flat(KeyFile.A)));
    }

    public static Note[] getKnownScales() {
        Set<Note> allScales = new HashSet<>();
        allScales.addAll(majorScales.keySet());
        allScales.addAll(minorScales.keySet());
        Note[] entries = allScales.toArray(new Note[0]);
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

    private Integer[] organisedCTones = new Integer[7];

    public DS7Scales(Note scaleNote) {
        int tonicIndex = Math.max(C_MAJOR_NOTES.indexOf(scaleNote.getKey()), 0);

        for (int i = 0; i < 7; i++) {
            cOrganisedScale[i] = C_MAJOR_NOTES.get((tonicIndex + i) % 7);
            organisedScale[i] = new Note(C_MAJOR_NOTES.get((tonicIndex + i) % 7));
            organisedCTones[i] = semitones.get(Mode.IONIAN).get((tonicIndex + i) % 7);
        }
    }

    //Generate the correct modal scale using chromatic intervals

    public List<Note> findSharpsAndFlats(Note scaleNote, Mode mode) {
        // Check for known modal scales first
        String scaleKey = scaleNote.getKey().toString() +
                (scaleNote.isSharp() ? "#" : "") +
                (scaleNote.isFlat() ? "b" : "") +
                "-" + mode.toString().toUpperCase();

        if (knownModalScales.containsKey(scaleKey)) {
            return new ArrayList<>(knownModalScales.get(scaleKey));
        }

        // For Ionian/Major mode, use predefined major scales
        if (mode == Mode.IONIAN) {
            List<Note> majorScale = majorScales.get(scaleNote);
            if (majorScale != null) {
                return new ArrayList<>(majorScale);
            }
            // Try to find any major scale with the same key
            for (Map.Entry<Note, List<Note>> entry : majorScales.entrySet()) {
                if (entry.getKey().getKey() == scaleNote.getKey() &&
                        entry.getKey().isSharp() == scaleNote.isSharp() &&
                        entry.getKey().isFlat() == scaleNote.isFlat()) {
                    return new ArrayList<>(entry.getValue());
                }
            }
        }

        // For Aeolian/Minor mode, use predefined minor scales
        if (mode == Mode.AEOLIAN) {
            List<Note> minorScale = minorScales.get(scaleNote);
            if (minorScale != null) {
                return new ArrayList<>(minorScale);
            }
            // Try to find any minor scale with the same key
            for (Map.Entry<Note, List<Note>> entry : minorScales.entrySet()) {
                if (entry.getKey().getKey() == scaleNote.getKey() &&
                        entry.getKey().isSharp() == scaleNote.isSharp() &&
                        entry.getKey().isFlat() == scaleNote.isFlat()) {
                    return new ArrayList<>(entry.getValue());
                }
            }
        }

        // For other modes, generate using chromatic approach
        return generateModalScaleFromChromatic(scaleNote, mode);
    }

    /*
    Generate modal scale using proper chromatic intervals and note naming conventions
    This ensures that C Dorian starts with C (not C#), C Phrygian starts with C, etc.
     */
    private List<Note> generateModalScaleFromChromatic(Note tonic, Mode mode) {
        List<Note> modalScale = new ArrayList<>();

        // Get the semitone pattern for this mode
        List<Integer> intervals = semitones.get(mode);

        // Start with the exact tonic note as provided (C Dorian starts with C)
        modalScale.add(new Note(tonic));

        // Get starting chromatic position
        int chromaticPosition = getChromaticPosition(tonic);

        // Generate the scale using the mode's interval pattern
        for (int i = 0; i < 6; i++) { // 6 more notes after the tonic
            chromaticPosition += intervals.get(i);
            chromaticPosition %= 12; // Wrap around the octave

            // Determine the letter name based on the scale degree (always ascending from tonic)
            KeyFile expectedLetter = getExpectedLetterName(tonic.getKey(), i + 1);

            // Create the note with proper accidentals
            Note nextNote = createNoteFromChromaticPosition(chromaticPosition, expectedLetter);
            modalScale.add(nextNote);
        }

        return modalScale;
    }


    //Get chromatic position (0-11) for a note

    private int getChromaticPosition(Note note) {
        int basePosition = switch (note.getKey()) {
            case C -> 0;
            case D -> 2;
            case E -> 4;
            case F -> 5;
            case G -> 7;
            case A -> 9;
            case B -> 11;
        };

        if (note.isSharp()) basePosition += 1;
        if (note.isFlat()) basePosition -= 1;

        return (basePosition + 12) % 12;
    }


    //Get the expected letter name for a scale degree

    private KeyFile getExpectedLetterName(KeyFile tonic, int scaleDegree) {
        int tonicIndex = C_MAJOR_NOTES.indexOf(tonic);
        int letterIndex = (tonicIndex + scaleDegree) % 7;
        return C_MAJOR_NOTES.get(letterIndex);
    }

    //Create a note from chromatic position with the expected letter name

    private Note createNoteFromChromaticPosition(int chromaticPos, KeyFile expectedLetter) {
        int expectedChromaticPos = switch (expectedLetter) {
            case C -> 0;
            case D -> 2;
            case E -> 4;
            case F -> 5;
            case G -> 7;
            case A -> 9;
            case B -> 11;
        };

        int difference = chromaticPos - expectedChromaticPos;

        // Handle wrapping
        if (difference > 6) difference -= 12;
        if (difference < -6) difference += 12;

        if (difference == 0) {
            return Note.forKey(expectedLetter);
        } else if (difference == 1) {
            return Note.sharp(expectedLetter);
        } else if (difference == -1) {
            return Note.flat(expectedLetter);
        } else {
            // For double sharps/flats, use enharmonic equivalent
            return getEnharmonicEquivalent(chromaticPos);
        }
    }

    //Get enharmonic equivalent for complex accidentals

    private Note getEnharmonicEquivalent(int chromaticPos) {
        return switch (chromaticPos) {
            case 0 -> Note.forKey(KeyFile.C);
            case 1 -> Note.sharp(KeyFile.C);
            case 2 -> Note.forKey(KeyFile.D);
            case 3 -> Note.flat(KeyFile.E);
            case 4 -> Note.forKey(KeyFile.E);
            case 5 -> Note.forKey(KeyFile.F);
            case 6 -> Note.sharp(KeyFile.F);
            case 7 -> Note.forKey(KeyFile.G);
            case 8 -> Note.flat(KeyFile.A);
            case 9 -> Note.forKey(KeyFile.A);
            case 10 -> Note.flat(KeyFile.B);
            case 11 -> Note.forKey(KeyFile.B);
            default -> Note.forKey(KeyFile.C);
        };
    }
}