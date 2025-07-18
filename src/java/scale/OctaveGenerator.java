package scale;

import java.util.ArrayList;
import java.util.List;

/**
 * This class generates a complete musical scale across multiple octaves
 * based on a 7-note scale pattern provided by generate7NoteScale
 */
public class OctaveGenerator {


    /**
     * Generates the complete scale across the specified octave range
     * In Western music theory, the octave changes after B, so this method
     * tracks when B is reached to increment the octave number
     */
    public static List<Note> generateFullScaleWithOctaves(OctaveRange octRange, List<Note> notes) {
        List<Note> allOctaveNotes = new ArrayList<>();
        int currentOctave = octRange.octaveStart; // Start at the first octave in the range

        // Loop through each octave in the range
        for (int o = octRange.octaveStart; o < octRange.octaveEnd; o++) {
            // Loop through each of the 7 notes in the scale
            for (Note note : notes) {
                // Add the current note with its octave number to the final scale
                Note noteOnOctave = new Note(note);
                noteOnOctave.setOctave(currentOctave);
                allOctaveNotes.add(noteOnOctave);
                // If we've reached B, increment the octave
                // This follows standard music notation where C starts a new octave
                if (note.getKey() == KeyFile.B) {
                    currentOctave++;
                }
            }

        }
        Note lastNote = new Note(notes.getFirst());
        lastNote.setOctave(currentOctave);
        allOctaveNotes.add(lastNote);

        return allOctaveNotes;
    }
}