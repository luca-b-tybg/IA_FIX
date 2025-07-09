package scale;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Octave {
    private int position;
    private List<Note> notes = new ArrayList<Note>();

    public Octave(int position) {
        this.position = position;

    }

    public void add(Note note) {
        notes.add(note);

    }

    @Override
    public String toString() {
        return notes.stream().map((n)-> n.toString() + position).toList().toString();
    }
}
