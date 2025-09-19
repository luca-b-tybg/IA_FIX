package ui;

import circle.CircleOfFifthsKeyFile;
import diatonicscale.DS7Scales;
import scale.KeyFile;
import scale.Mode;
import scale.Note;
import scale.RhythmType;
import ui.components.MusicScoreComponent;

import java.util.*;

class ProgressionMusicScoreComponent extends MusicScoreComponent {
    private Map<KeyFile, Integer> positions = Map.of(KeyFile.A, -9,
            KeyFile.B, -8, KeyFile.C, -7, KeyFile.D, -6, KeyFile.E, -5, KeyFile.G, -10);

    List<Note> keyNotes;
    private CircleOfFifthsKeyFile keySignatureKey; // The key that determines the key signature

    public void setProgressions(List<CircleOfFifthsKeyFile> bottomBarNotes) {
        int noteIndex = 0;
        int columnPosition = 0;
        
        // Set the key signature based on the first chord in the progression
        if (!bottomBarNotes.isEmpty()) {
            keySignatureKey = bottomBarNotes.get(0);
        }
        
        List<Note> notes = getProgressionNotes(bottomBarNotes);
        for (Note note : notes) {
            if (noteIndex == 0) {
                var keyNote = new Note(note.getKey(), note.isFlat(), note.isSharp);
                var ds7Scales = new DS7Scales(keyNote);
                if (note.isMajor()) {
                    keyNotes = ds7Scales.findSharpsAndFlats(keyNote, Mode.IONIAN);
                } else {
                    keyNotes = ds7Scales.findSharpsAndFlats(keyNote, Mode.AEOLIAN);
                }
            }
            int linePosition = getScoreNotePosition(note);
            List<Note> topBarNotes = getTopBarNotes(note);
            addTopBarNotes(columnPosition, topBarNotes);
            columnPosition = topBarNotes.size() < 1 ? columnPosition + 1 : (columnPosition + topBarNotes.size());
            addNote(linePosition, (columnPosition - topBarNotes.size() / 2) - 1, note);
            for (int i = 1; i <= 2; i++) {
                int newLinePosition = linePosition + 2 * i;
                int x = DS7Scales.getNotePosition(note.getKey()) + (2 * i);
                KeyFile newKeyFile = DS7Scales.getKeyForScorePosition(x);
                Note newNote = new Note(newKeyFile, false, false, RhythmType.SEMIBREVE, note.getOctave());
                addNote(newLinePosition, (columnPosition - topBarNotes.size() / 2) - 1, newNote);
            }
            noteIndex++;
        }
    }

    @Override
    public Set<KeyFile> getSharpKeys() {
        Set<KeyFile> sharpKeys = new HashSet<>();
        
        // Determine key signature based on the first chord in progression
        if (keySignatureKey != null) {
            Note keyNote = new Note(keySignatureKey.getKeyFile(), 
                                   false, 
                                   keySignatureKey.isSharp());
            
            DS7Scales ds7Scales = new DS7Scales(keyNote);
            List<Note> scaleNotes;
            
            if (keySignatureKey.isMajor()) {
                scaleNotes = ds7Scales.findSharpsAndFlats(keyNote, Mode.IONIAN);
            } else {
                scaleNotes = ds7Scales.findSharpsAndFlats(keyNote, Mode.AEOLIAN);
            }
            
            for (Note note : scaleNotes) {
                if (note.isSharp()) {
                    sharpKeys.add(note.getKey());
                }
            }
        }
        
        return sharpKeys;
    }
    
    @Override
    public Set<KeyFile> getFlatKeys() {
        Set<KeyFile> flatKeys = new HashSet<>();
        
        // Determine key signature based on the first chord in progression
        if (keySignatureKey != null) {
            Note keyNote = new Note(keySignatureKey.getKeyFile(), 
                                   false, 
                                   keySignatureKey.isSharp());
            
            DS7Scales ds7Scales = new DS7Scales(keyNote);
            List<Note> scaleNotes;
            
            if (keySignatureKey.isMajor()) {
                scaleNotes = ds7Scales.findSharpsAndFlats(keyNote, Mode.IONIAN);
            } else {
                scaleNotes = ds7Scales.findSharpsAndFlats(keyNote, Mode.AEOLIAN);
            }
            
            for (Note note : scaleNotes) {
                if (note.isFlat()) {
                    flatKeys.add(note.getKey());
                }
            }
        }
        
        return flatKeys;
    }

    protected List<Note> getTopBarNotes(Note note) {
        return new ArrayList<>();
    }

    private void addTopBarNotes(int startColumn, List<Note> notes) {
        int columnPosition = startColumn;
        for (Note note : notes) {
            addNote(DS7Scales.getNotePositionRelativeToMiddleC(note), columnPosition++, note);
        }
        showBar(columnPosition);
    }

    private void showBar(int columnPosition) {
        addScoreBar(columnPosition, -12, -4);
        addScoreBar(columnPosition, 0, 8);
    }

    private List<Note> getProgressionNotes(List<CircleOfFifthsKeyFile> progressions) {
        List<Note> progressionNotes = new ArrayList<>();
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