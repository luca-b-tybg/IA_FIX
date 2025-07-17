package melodygenerator;

import circle.CircleOfFifthsKeyFile;
import scale.KeyFile;
import scale.Note;
import scale.RhythmType;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

public class MelodyGenerator {
    private static final RhythmType[] NOTE_LENGTHS = RhythmType.values();
    private static final List<Note> AVAILABLE_NOTES = new ArrayList<>();

    // initialising the available notes with all possible random notes
    static {
        for(KeyFile keyFile: KeyFile.values()) {
            for(int i = 4; i<=5 ; i++) {
                AVAILABLE_NOTES.add(new Note(keyFile, false, false, RhythmType.CROTCHET, i));
            }
        }
    }

    public List<Note> generateMelodyProgression(List<CircleOfFifthsKeyFile> progressionKeys) {
        List<Note> notes = new ArrayList<>();
        for (CircleOfFifthsKeyFile keyFile : progressionKeys) {
          notes.addAll(generateMelodyProgression(keyFile));
        }
        return notes;
    }

    public List<Note> generateMelodyProgression(CircleOfFifthsKeyFile progressionKey) {
        List<Note> notes = new ArrayList<>();
        //todo: check if the key file has anything to do with the generation

        for (RhythmType rhythm : getBarRhythm()) {
            int selectedNote = RandomGenerator.getDefault().nextInt(AVAILABLE_NOTES.size());
            Note note = new Note (AVAILABLE_NOTES.get(selectedNote));
            note.setRhythmType(rhythm);
            notes.add(note);
        }
        return notes;
    }


    public static List<RhythmType> getBarRhythm() {
        double beatCount = 4;
        ArrayList<RhythmType> barNoteLengths = new ArrayList<>();
        while (beatCount > 0) {
            int randomLength = RandomGenerator.getDefault().nextInt(RhythmType.values().length);
            RhythmType selectedRhythm = NOTE_LENGTHS[randomLength];
            if (beatCount >= selectedRhythm.getBitCount()) {
                barNoteLengths.add(selectedRhythm);
                beatCount -= selectedRhythm.getBitCount();
            }

        }
        return barNoteLengths;
    }


}
