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


    public List<Note> generateMelodyProgression(List<CircleOfFifthsKeyFile> progressionKeys) {
        List<Note> notes = new ArrayList<>();
        for (CircleOfFifthsKeyFile keyFile : progressionKeys) {
            //todo: check if the key file has anything to do with the generation
            for (RhythmType rhythm : getBarRhythm()) {
                Note note = Note.forKey(KeyFile.C);
                note.setOctave(2);
                note.setRhythmType(rhythm);
                notes.add(note);
            }
        }

        return notes;
    }


    public static List<RhythmType> getBarRhythm() {
        double beatCount = 4;
        ArrayList<RhythmType> barNoteLengths = new ArrayList<>();
        while (beatCount > 0) {
            int randomLength = RandomGenerator.getDefault().nextInt(6);
            RhythmType selectedRhythm = NOTE_LENGTHS[randomLength];
            if (beatCount >= selectedRhythm.getBitCount()) {
                barNoteLengths.add(selectedRhythm);
                beatCount -= selectedRhythm.getBitCount();
            }

        }
        return barNoteLengths;
    }


}
