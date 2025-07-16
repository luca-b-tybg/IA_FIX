package ui;

import circle.CircleOfFifthsKeyFile;
import scale.KeyFile;
import scale.Note;
import scale.RhythmType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ProgressionScorePanel extends ScorePanel {
    private Map<KeyFile, Integer> positions = Map.of(KeyFile.A, -9,
            KeyFile.B, -8, KeyFile.C, -7, KeyFile.D, -6, KeyFile.E, -5, KeyFile.G, -10);

    public void setProgressions(List<CircleOfFifthsKeyFile> progressions) {
        int noteIndex = 0;
        java.util.List<Note> notes = getProgressionNotes(progressions);
        for (Note note : notes) {
            int linePosition = getScoreNotePosition(note);
            addNote(linePosition, noteIndex, note);
            for (int i = 1; i <= 2; i++) {
                addNote(linePosition + 2 * i, noteIndex,
                        new Note(KeyFile.C, false, false, RhythmType.SEMIBREVE, note.getOctave()));
            }
            noteIndex++;
        }
    }

    private java.util.List<Note> getProgressionNotes(List<CircleOfFifthsKeyFile> progressions) {
        java.util.List<Note> progressionNotes = new ArrayList<>();
        for (CircleOfFifthsKeyFile keyFile : progressions) {
            progressionNotes.add(new Note(keyFile.getKeyFile(), false, keyFile.isSharp(), RhythmType.SEMIBREVE, 2));
        }
        return progressionNotes;
    }

    protected int getScoreNotePosition(Note note) {

        if (note.getKey() == KeyFile.F) {
            return -11;
        }
        if (positions.containsKey(note.getKey())) {
            return positions.get(note.getKey());
        }
        return 0;
    }


    @Override
    protected void noteAdded(Note note, int linePosition, int columnPosition) {
        super.showScoreBar(columnPosition, -12, -4);
        for (int i = 1; i <= 2; i++) {
            JComponent note1 = getNote(linePosition + 2 * i, columnPosition, new Note(KeyFile.C, false, false, RhythmType.SEMIBREVE, note.getOctave()));
            add(note1);
        }

    }

}
