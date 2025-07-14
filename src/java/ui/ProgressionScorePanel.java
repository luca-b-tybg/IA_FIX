package ui;

import scale.KeyFile;
import scale.Note;
import scale.Octave;
import scale.RhythmType;

import javax.swing.*;
import java.util.Map;

class ProgressionScorePanel extends ScorePanel {
    private Map<KeyFile, Integer> positions = Map.of(KeyFile.A, -9,
            KeyFile.B, -8, KeyFile.C, -7, KeyFile.D, -6, KeyFile.E, -5, KeyFile.G, -10);

    @Override
    protected int getScoreNotePosition(int octaveIndex, Note note) {
        if (octaveIndex == 2 && note.getKey() == KeyFile.F) {
            return -11;
        }
        if (positions.containsKey(note.getKey())) {
            return positions.get(note.getKey());
        }

        return super.getScoreNotePosition(octaveIndex, note);
    }

    @Override
    protected void noteAdded(Note note, Octave octave, int linePosition, int columnPosition) {

        for (int i = 1; i <= 2; i++) {
            JComponent note1 = getNote(linePosition + 2 * i, columnPosition, octave, new Note(KeyFile.C, false, false, RhythmType.SEMIBREVE));
            add(note1);
        }
    }
}
