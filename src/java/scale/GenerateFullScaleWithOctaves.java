package scale;

import diatonicscale.DS7Note;

import java.util.ArrayList;
import java.util.List;

/**
 * This class generates a complete musical scale across multiple octaves
 * based on a 7-note scale pattern provided by generate7NoteScale
 */
public class GenerateFullScaleWithOctaves {
    private KeyFile[] sevenNotesNoMod = new KeyFile[7]; // Natural notes without sharps/flats (used to track octave transitions)
    private Note[] sevenNotes = new Note[7];      // Notes with any sharps/flats applied
    private String[] finalScale;             // The complete scale across all octaves

    // Setters

    /**
     * Calculates the total length of the scale based on octave range
     * Formula: (number of octaves × 7 notes per octave) + 1 final tonic note
     */
    public int getScaleLength(OctaveRange octRange) {
        return octRange.getSelectedLength() * 7 + 1;
    }

    /**
     * Sets the 7-note scale pattern to use for generating the full scale
     *
     * @param sevenNotes      The scale with accidentals (sharps/flats)
     * @param sevenNotesNoMod The scale without accidentals (for tracking octave changes)
     */
    public void setScale(Note[] sevenNotes, KeyFile[] sevenNotesNoMod) {
        this.sevenNotes = sevenNotes;
        this.sevenNotesNoMod = sevenNotesNoMod;
    }

    /**
     * Generates the complete scale across the specified octave range
     * In Western music theory, the octave changes after B, so this method
     * tracks when B is reached to increment the octave number
     */
    private List<Octave> generateFullScaleWithOctaves(OctaveRange octRange) {
        //finalScale = new String[getScaleLength(octRange)];
        List<Octave> allOctaves = new ArrayList<Octave>();
        int scaleIndex = 0;
        int currentOctave = octRange.octaveStart; // Start at the first octave in the range
        Octave octave = new Octave(currentOctave );
        // Loop through each octave in the range
        for (int o = octRange.octaveStart; o < octRange.octaveEnd; o++) {
            // Loop through each of the 7 notes in the scale

            for (int i = 0; i < 7; i++) {
                // Add the current note with its octave number to the final scale
                octave.add(sevenNotes[i]);
              //  finalScale[scaleIndex] = sevenNotes[i] .toString() + currentOctave;
                scaleIndex++;

                // If we've reached B, increment the octave
                // This follows standard music notation where C starts a new octave
                if (sevenNotesNoMod[i].name().equals("B")) {
                    currentOctave++;
                    allOctaves.add(octave);
                    octave = new Octave(currentOctave );
                }
            }
            // Add the final tonic note to complete the octave (e.g., C after B)
            //finalScale[scaleIndex] = sevenNotes[0] .toString() + currentOctave;
            octave.add(sevenNotes[0]);
        }

        return allOctaves;
    }

    public List< Octave>fullFinalScale(OctaveRange octRange, KeyFile key, Mode mode) {

       var y = new DS7Note(key, mode);
       y.findSharpsAndFlats(key);
        setScale(y.getOrganisedScale(), y.getcOrganisedScale());
        return generateFullScaleWithOctaves(octRange);
    }
}