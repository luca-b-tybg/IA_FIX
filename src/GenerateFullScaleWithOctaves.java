/**
 * This class generates a complete musical scale across multiple octaves
 * based on a 7-note scale pattern provided by generate7NoteScale
 */
public class GenerateFullScaleWithOctaves {

    private int[] octRange;                  // Range of octaves to generate (e.g., [2,6] means octaves 2-5 inclusive)
    private int scaleLength;                 // Total number of notes in the full scale
    private String[] sevenNotesNoMod = new String[7]; // Natural notes without sharps/flats (used to track octave transitions)
    private String[] sevenNotes = new String[7];      // Notes with any sharps/flats applied
    private String[] finalScale;             // The complete scale across all octaves

    // Setters

    /**
     * Calculates the total length of the scale based on octave range
     * Formula: (number of octaves × 7 notes per octave) + 1 final tonic note
     */
    public void setScaleLength() {scaleLength = (octRange[1] - octRange[0]) * 7 + 1;}

    /**
     * Sets the 7-note scale pattern to use for generating the full scale
     * @param sevenNotes The scale with accidentals (sharps/flats)
     * @param sevenNotesNoMod The scale without accidentals (for tracking octave changes)
     */
    public void setScale(String[] sevenNotes, String[] sevenNotesNoMod) {
        this.sevenNotes = sevenNotes;
        this.sevenNotesNoMod = sevenNotesNoMod;
    }

    /**
     * Generates the complete scale across the specified octave range
     * In Western music theory, the octave changes after B, so this method
     * tracks when B is reached to increment the octave number
     */
    public void generateFullScaleWithOctaves() {
        finalScale = new String[scaleLength];
        int scaleIndex = 0;
        int currentOctave = octRange[0]; // Start at the first octave in the range

        // Loop through each octave in the range
        for (int o = octRange[0]; o < octRange[1]; o++) {
            // Loop through each of the 7 notes in the scale
            for (int i = 0; i < 7; i++) {
                // Add the current note with its octave number to the final scale
                finalScale[scaleIndex] = sevenNotes[i] + currentOctave;
                scaleIndex++;

                // If we've reached B, increment the octave
                // This follows standard music notation where C starts a new octave
                if (sevenNotesNoMod[i].equals("B")) {
                    currentOctave++;
                }
            }
            // Add the final tonic note to complete the octave (e.g., C after B)
            finalScale[scaleIndex] = sevenNotes[0] + currentOctave;
        }

        // Print the complete scale
        for (String note : finalScale) {
            System.out.println(note);
        }
    }

    public void fullFinalScale(int[]octRange, Generate7NoteScale scaleGenerator) {
        this.octRange = octRange;
        setScaleLength();
        setScale(scaleGenerator.getOrganisedScale(), scaleGenerator.getcOrganisedScale());
        generateFullScaleWithOctaves();
    }
}