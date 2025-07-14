package scale;

import java.util.ArrayList;
import java.util.List;

public class Octave {
    private int position;
    private List<Note> notes = new ArrayList<Note>();

    public Octave(int position) {
        this.position = position;

    }

    public void add(Note note) {
        notes.add(note);

    }

    public List<Note> getNotes() {
        return notes;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return notes.stream().map((n) -> n.toString() + position).toList().toString();
    }
}
