/**
 * This class generates a 7-note musical scale based on a specified key and mode.
 * It handles the proper calculation of notes and applies necessary sharps/flats
 * to maintain the correct tonal relationships for the selected mode.
 */
public class Generate7NoteScale {
    // Core components for scale generation
    private KeyFile.Key key;                        // The tonic/root note of the scale (e.g., C, D, E, etc.)
    private ModeFile.Mode mode;                     // The mode of the scale (Ionian/Major, Dorian, Phrygian, etc.)
    private String[] CNOTES = {"C", "D", "E", "F", "G", "A", "B"};  // Natural notes in C major
    private String[] cOrganisedScale = new String[7];  // C major scale reorganized to start from the tonic
    private String[] organisedScale = new String[7];   // Final scale with appropriate sharps/flats applied

    /**
     * Semitone patterns for the 7 modes:
     * Each row represents the semitone intervals between consecutive notes in a mode
     * For example, Ionian/Major mode: 2-2-1-2-2-2-1 (whole-whole-half-whole-whole-whole-half steps)
     */
    private String[][] semitones = {
            {"2", "2", "1", "2", "2", "2", "1"}, // Ionian/Major
            {"2", "1", "2", "2", "2", "1", "2"}, // Dorian
            {"1", "2", "2", "2", "1", "2", "2"}, // Phrygian
            {"2", "2", "2", "1", "2", "2", "1"}, // Lydian
            {"2", "2", "1", "2", "2", "1", "2"}, // Mixolydian
            {"2", "1", "2", "2", "1", "2", "2"}, // Aeolian/Minor
            {"1", "2", "2", "1", "2", "2", "2"}  // Locrian
    };

    // Known major scales with their correct accidentals for reference
    private String[][] majorScales = {
            {"C", "D", "E", "F", "G", "A", "B"},      // C major - no sharps/flats
            {"G", "A", "B", "C", "D", "E", "F#"},     // G major - F#
            {"D", "E", "F#", "G", "A", "B", "C#"},    // D major - F#, C#
            {"A", "B", "C#", "D", "E", "F#", "G#"},   // A major - F#, C#, G#
            {"E", "F#", "G#", "A", "B", "C#", "D#"},  // E major - F#, C#, G#, D#
            {"B", "C#", "D#", "E", "F#", "G#", "A#"}, // B major - F#, C#, G#, D#, A#
            {"F", "G", "A", "Bb", "C", "D", "E"},     // F major - Bb
            {"Bb", "C", "D", "Eb", "F", "G", "A"},    // Bb major - Bb, Eb
            {"Eb", "F", "G", "Ab", "Bb", "C", "D"},   // Eb major - Bb, Eb, Ab
            {"Ab", "Bb", "C", "Db", "Eb", "F", "G"}   // Ab major - Bb, Eb, Ab, Db
    };

    private String[] scaleTones = new String[7];        // Semitone pattern for the selected mode
    private String[] organisedCTones = new String[7];   // Reorganized semitone pattern of C major

    // Getters and setters

    /**
     * Sets the mode and updates the scale tones based on the selected mode
     */
    private void setMode(ModeFile.Mode mode) {
        this.mode = mode;
        scaleTones = semitones[mode.ordinal()];  // Get semitone pattern for the chosen mode
    }

    public String[] getcOrganisedScale() {return cOrganisedScale;}
    public String[] getOrganisedScale(){return organisedScale;}

    /**
     * Reorders the C major scale to start from the tonic note of the selected key
     * This creates our base scale before applying accidentals (sharps/flats)
     */
    public void reorganizeScale() {
        // Find where our tonic is in the C major scale
        findNoteIndex tiTest = new findNoteIndex();
        int tonicIndex = tiTest.fni(key.toString(), CNOTES);

        // Reorganize the scale and semitones starting from the tonic
        for (int i = 0; i < 7; i++) {
            // Use modulo to wrap around when we reach the end of the array
            cOrganisedScale[i] = CNOTES[(tonicIndex + i) % 7];
            organisedScale[i] = CNOTES[(tonicIndex + i) % 7];

            // Reorganize C major's semitone pattern (Ionian mode) to match our new starting point
            organisedCTones[i] = semitones[0][(tonicIndex + i) % 7];
        }
    }

    /**
     * Applies appropriate sharps and flats to the scale based on the selected key and mode
     */
    public void findSharpsAndFlats() {
        // For Ionian/Major mode, use predefined major scales
        if (mode == ModeFile.Mode.IONIAN) {
            applyMajorScale();
            return;
        }

        // For other modes, first get the relative major key's scale
        // Then rotate it to get the correct mode
        String[] majorScale = getMajorScaleForKey();
        applyModeToMajorScale(majorScale);
    }

    /**
     * Applies a predefined major scale based on the current key
     */
    private void applyMajorScale() {
        String keyStr = key.toString();

        switch(keyStr) {
            case "C":
                System.arraycopy(majorScales[0], 0, organisedScale, 0, 7);
                break;
            case "G":
                System.arraycopy(majorScales[1], 0, organisedScale, 0, 7);
                break;
            case "D":
                System.arraycopy(majorScales[2], 0, organisedScale, 0, 7);
                break;
            case "A":
                System.arraycopy(majorScales[3], 0, organisedScale, 0, 7);
                break;
            case "E":
                System.arraycopy(majorScales[4], 0, organisedScale, 0, 7);
                break;
            case "B":
                System.arraycopy(majorScales[5], 0, organisedScale, 0, 7);
                break;
            case "F":
                System.arraycopy(majorScales[6], 0, organisedScale, 0, 7);
                break;
            default:
                // For other keys, use the algorithm (but it's better to add them to majorScales)
                applyAlgorithmicScale();
                break;
        }
    }

    /**
     * Determines the relative major scale for a given mode
     */
    private String[] getMajorScaleForKey() {
        // First find the relative major key based on the mode
        // For example, if we're in D Dorian, the relative major is C
        int modeOffset = mode.ordinal();
        String relativeMajorKey = CNOTES[(findNoteIndex(new findNoteIndex().fni(key.toString(), CNOTES)) - modeOffset + 7) % 7];

        // Return the major scale for this relative key
        switch(relativeMajorKey) {
            case "C": return majorScales[0];
            case "G": return majorScales[1];
            case "D": return majorScales[2];
            case "A": return majorScales[3];
            case "E": return majorScales[4];
            case "B": return majorScales[5];
            case "F": return majorScales[6];
            default: return majorScales[0]; // Default to C major if not found
        }
    }

    /**
     * Applies mode rotation to a major scale to get the correct mode
     */
    private void applyModeToMajorScale(String[] majorScale) {
        int modeOffset = mode.ordinal();
        for (int i = 0; i < 7; i++) {
            organisedScale[i] = majorScale[(i + modeOffset) % 7];
        }
    }

    /**
     * Fallback method that uses the original algorithm approach for scales
     * that aren't explicitly defined
     */
    private void applyAlgorithmicScale() {
        for (int current = 0; current < 7; current++) {
            int next = (current + 1) % 7;

            int st = Integer.parseInt(scaleTones[current]);
            int oct = Integer.parseInt(organisedCTones[current]);
            int st1 = Integer.parseInt(scaleTones[next]);
            int oct1 = Integer.parseInt(organisedCTones[next]);

            if (st > oct && st1 < oct1) {
                organisedCTones[current] = scaleTones[current];
                organisedCTones[next] = scaleTones[next];
                organisedScale[next] += "#";
            }

            if (st > oct && st1 == oct1) {
                organisedCTones[current] = scaleTones[current];
                organisedCTones[next] = scaleTones[next];
                organisedScale[current] += "b";
            }

            if (st < oct && st1 > oct1) {
                organisedCTones[current] = scaleTones[current];
                organisedCTones[next] = scaleTones[next];
                organisedScale[next] += "b";
            }

            if (st < oct && st1 == oct1) {
                organisedCTones[current] = scaleTones[current];
                organisedCTones[next] = scaleTones[next];
                organisedScale[next] += "b";
            }
        }
    }

    /**
     * Helper method to find note index in array
     */
    private int findNoteIndex(int index) {
        return index;
    }

    public void generateFinalScale(KeyFile.Key key, ModeFile.Mode mode) {
        this.key = key;
        setMode(mode);
        reorganizeScale();        // Reorganize C major to start from the chosen key
        findSharpsAndFlats();     // Apply accidentals to match the mode
    }
}