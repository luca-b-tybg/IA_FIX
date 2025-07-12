package ui;

import scale.KeyFile;
import scale.Note;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ScorePanel extends JPanel {

    private static final int SCORE_LINE_SPACE = 20;

    private static final int NOTE_GAP_SPACE = 45;

    private static final int SCORE_LINE_COUNT = 14;

    private final int SCORE_TOP_SPACE = 360;

    private static Map<KeyFile, Double> KEYS_SCORE_POSITIONS = Map.of(
            KeyFile.C, -1.,
            KeyFile.D, -0.5,
            KeyFile.E, 0.,
            KeyFile.F, 0.5,
            KeyFile.G, 2.,
            KeyFile.B, -1.5,
            KeyFile.A, 1.5);

    private static Map<KeyFile, Double> SYMBOLS_SCORE_POSITIONS = Map.of(
            KeyFile.C, 2.5,
            KeyFile.D, 3.,
            KeyFile.E, 3.5,
            KeyFile.F, 4.,
            KeyFile.G, 4.5,
            KeyFile.B, 2.,
            KeyFile.A, 1.5);

    private static String FLAT_SYMBOL = "♭";
    private static String SHARP_SYMBOL = "♯";

    private JLabel clefIcon = new JLabel();
    private ImageIcon fullNoteImage;
    private ImageIcon crossNoteImage;


    public ScorePanel() {
        setLayout(null);

        ImageIcon clefIconImage = new ImageIcon(getResource("clef_small.png"));
        fullNoteImage = new ImageIcon(getResource("music_note.png"));
        crossNoteImage = new ImageIcon(getResource("music_note-dash.png"));
        clefIcon.setIcon(clefIconImage);
        clefIcon.setSize(new Dimension(50, 127));
        clefIcon.setLocation(10, SCORE_TOP_SPACE);

    }

    private URL getResource(String resourceName) {
        return getClass().getResource("/" + resourceName);
    }

    public double getStartScorePosition(java.util.List<Note> notes) {
        Note firstNote = notes.getFirst();
        return KEYS_SCORE_POSITIONS.get(firstNote.getKey());
    }

    int cleveSymbolCount = 0;
    private Set<Note> sharpNotes = new HashSet<>();
    private Set<Note> flatNotes = new HashSet<>();

    private void showCleveSymbol(double linePosition, String symbol, int size) {
        JLabel label = new JLabel(symbol);
        label.setFont(new Font("Default", Font.BOLD, size));
        label.setSize(40, 40);
        label.setLocation(60 + (cleveSymbolCount * 15), getNoteScorePosition(linePosition) - 40);
        cleveSymbolCount++;
        this.add(label);
    }

    private void showSharpSymbol(Note note) {
        if (!sharpNotes.contains(note)) {
            double linePosition = SYMBOLS_SCORE_POSITIONS.get(note.getKey());
            showCleveSymbol(linePosition, SHARP_SYMBOL, 24);
            sharpNotes.add(note);
        }
    }

    public void showFlatSymbol(Note note) {
        if (!flatNotes.contains(note)) {
            double linePosition = SYMBOLS_SCORE_POSITIONS.get(note.getKey());
            showCleveSymbol(linePosition, FLAT_SYMBOL, 34);
            flatNotes.add(note);
        }
    }

    private void resetView() {
        this.removeAll();
        sharpNotes.clear();
        flatNotes.clear();
        cleveSymbolCount = 0;
    }


    public void setNotes(java.util.List<Note> notes) {
        resetView();
        int noteHeight = 100;
        int column = 0;
        double notePosition = getStartScorePosition(notes);


        for (Note note : notes) {

            JLabel noteLabel = new JLabel();
            if (note.getKey() == KeyFile.C && notePosition < 0) {
                noteLabel.setIcon(crossNoteImage);
            } else {
                noteLabel.setIcon(fullNoteImage);
            }
            if (note.isSharp) {
                showSharpSymbol(note);
            }

            if (note.isFlat()) {
                showFlatSymbol(note);
            }
            noteLabel.setLocation(120 + (NOTE_GAP_SPACE * column), getNoteScorePosition(notePosition) - noteHeight);
            noteLabel.setSize(30, noteHeight);
            this.add(noteLabel);
            column++;
            notePosition += 0.5;

        }
        add(this.clefIcon);
        this.repaint();
    }


    public int getNoteScorePosition(double linePosition) {
        int bottomLinePosition = (SCORE_LINE_COUNT - 1) * SCORE_LINE_SPACE;
        return bottomLinePosition + SCORE_TOP_SPACE - (int) (SCORE_LINE_SPACE * linePosition);
    }



    private int getLineY(int lineIndex) {
        return SCORE_TOP_SPACE + SCORE_LINE_SPACE * (lineIndex-1);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        for (int i = 0; i < SCORE_LINE_COUNT; i++) {
            if (i >= 2) { //not rendering first 2 lines
                g.drawLine(10, getLineY(i), this.getWidth() - 10, getLineY(i));
            }
        }

        g.drawLine(10, SCORE_TOP_SPACE + SCORE_LINE_SPACE, 10, SCORE_TOP_SPACE + SCORE_LINE_SPACE * 5);
        g.drawLine(this.getWidth() - 10, SCORE_TOP_SPACE + SCORE_LINE_SPACE, this.getWidth() - 10, SCORE_TOP_SPACE + SCORE_LINE_SPACE * 5);
    }
}
