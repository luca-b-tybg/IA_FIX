package ui;

import circle.CircleOfFifthsKeyFile;
import diatonicscale.DS7Scales;
import scale.KeyFile;
import scale.Note;
import scale.RhythmType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ProgressionScorePanel extends ScorePanel {
    private Map<KeyFile, Integer> positions = Map.of(KeyFile.A, -9,
            KeyFile.B, -8, KeyFile.C, -7, KeyFile.D, -6, KeyFile.E, -5, KeyFile.G, -10);


    public void setProgressions(List<CircleOfFifthsKeyFile> progressions) {
        int noteIndex = 0;
        int columnPosition = 0;
        java.util.List<Note> notes = getProgressionNotes(progressions);
        for (Note note : notes) {
            int linePosition = getScoreNotePosition(note);
            List<Note> topBarNotes = getTopBarNotes(note);
            addTopBarNotes(columnPosition, topBarNotes);
            columnPosition = topBarNotes.size() < 1 ? columnPosition + 1 : (columnPosition + topBarNotes.size());
            System.out.println(topBarNotes + " " + columnPosition);
            addNote(linePosition, (columnPosition - topBarNotes.size() / 2) - 1, note);
            for (int i = 1; i <= 2; i++) {
                int newLinePosition = linePosition + 2 * i;
                int x = DS7Scales.getNotePosition(note.getKey()) + (2 * i);
                KeyFile newKeyFile = DS7Scales.getKeyForScorePosition(x);
                Note newNote = new Note(newKeyFile, false, false, RhythmType.SEMIBREVE, note.getOctave());
                addNote(newLinePosition, (columnPosition - topBarNotes.size() / 2) - 1, newNote);
            }
            // showBar(columnPosition);
            noteIndex++;
        }
        //adding last bar
        //    showBar(columnPosition +1);
    }

    private void addTopBarNotes(int startColumn, List<Note> notes) {
        int columnPosition = startColumn;
        for (Note note : notes) {
            addNote(DS7Scales.getNotePositionRelativeToMiddleC(note), columnPosition++, note);
        }
        showBar(columnPosition);
    }

    protected List<Note> getTopBarNotes(Note progressionNote) {
        return new ArrayList<>();
    }

    private void showBar(int columnPosition) {
        addScoreBar(columnPosition, -12, -4);
        addScoreBar(columnPosition, 0, 8);
    }

    private java.util.List<Note> getProgressionNotes(List<CircleOfFifthsKeyFile> progressions) {
        java.util.List<Note> progressionNotes = new ArrayList<>();
        for (CircleOfFifthsKeyFile keyFile : progressions) {
            Note newNote = new Note(keyFile.getKeyFile(), false, keyFile.isSharp(), RhythmType.SEMIBREVE, 2);
            newNote.setDiminished(keyFile.isDiminished());
            progressionNotes.add(newNote);
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


}
