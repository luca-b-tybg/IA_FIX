package ui;

import diatonicscale.DS7Scales;
import scale.KeyFile;
import scale.Note;
import scale.Octave;
import scale.OctaveRange;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ScorePanel extends JPanel {

    private static final int SCORE_LINE_SPACE = 20;

    private static final int NOTE_GAP_SPACE = 45;

    private static final int SCORE_LINE_COUNT = 13;

    private final int SCORE_TOP_SPACE = 360;


    private static Map<KeyFile, Integer> SYMBOLS_SCORE_POSITIONS = Map.of(
            KeyFile.C, 7,
            KeyFile.D, 8,
            KeyFile.E, 9,
            KeyFile.F, 4,
            KeyFile.G, 5,
            KeyFile.B, 7,
            KeyFile.A, 6);

    private static String FLAT_SYMBOL = "♭";
    private static String SHARP_SYMBOL = "♯";

    private JLabel clefIcon = new JLabel();
    private JLabel baseClefIcon = new JLabel();

    private ImageIcon fullNoteImage;
    private ImageIcon crossNoteImage;


    public ScorePanel() {
        setLayout(null);


        fullNoteImage = new ImageIcon(getResource("music_note.png"));
        crossNoteImage = new ImageIcon(getResource("music_note-dash.png"));
        // clef icon setup
        ImageIcon clefIconImage = new ImageIcon(getResource("clef_small.png"));
        clefIcon.setIcon(clefIconImage);
        clefIcon.setSize(new Dimension(50, 127));
        clefIcon.setLocation(10, SCORE_TOP_SPACE);

        // base clef icon setup
        ImageIcon baseClefIconImage = new ImageIcon(getResource("base_clef.png"));
        baseClefIcon.setIcon(baseClefIconImage);
        baseClefIcon.setSize(new Dimension(51, 48));
        baseClefIcon.setLocation(10, (SCORE_TOP_SPACE) + SCORE_LINE_SPACE * 8);

    }


    private URL getResource(String resourceName) {
        return getClass().getResource("/" + resourceName);
    }


    int cleveSymbolCount = 0;
    private Set<Note> sharpNotes = new HashSet<>();
    private Set<Note> flatNotes = new HashSet<>();

    private void showCleveSymbol(double linePosition, String symbol, int size) {
        JLabel label = new JLabel(symbol);
        label.setFont(new Font("Default", Font.BOLD, size));
        label.setSize(40, 40);
        label.setLocation(60 + (cleveSymbolCount * 15), getNoteScorePosition(null, (int) linePosition) - 40);
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

    private int getStartScorePosition(OctaveRange octaveRange, Octave octave) {
        int octaveCount = Math.abs(4 - octaveRange.octaveStart);
        int direction = Integer.compare(octaveRange.octaveStart, 4);

        Note startNote = octave.getNotes().getFirst();
        int keyPosition = DS7Scales.C_MAJOR_NOTES.indexOf(startNote.getKey());


        return keyPosition + (direction * ((7 * octaveCount)));

    }

    public void setOctaves(OctaveRange octaveRange, java.util.List<Octave> octaves) {
        resetView();
        int noteHeight = 80;
        int column = 0;
        int notePosition = getStartScorePosition(octaveRange, octaves.getFirst());

        for (Octave octave : octaves) {
            for (Note note : octave.getNotes()) {
                JLabel noteLabel = new JLabel();
                if (note.getKey() == KeyFile.C && (octave.getPosition() == 2 || octave.getPosition() == 4)) {
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
                int noteXpos = 120 + (NOTE_GAP_SPACE * column);
                noteLabel.setLocation(noteXpos, getNoteScorePosition(note, notePosition) - 50);
                noteLabel.setSize(30, noteHeight);
                showNoteText(octave.getPosition(), noteXpos, note);

                this.add(noteLabel);
                column++;
                notePosition++;

            }
        }
        add(this.clefIcon);
        add(this.baseClefIcon);

        this.repaint();
    }

    private void showNoteText(int octave, int xpos,  Note note) {
        JLabel x = new JLabel(note.getKey().toString() + octave);
        x.setLocation(xpos, SCORE_TOP_SPACE + SCORE_LINE_SPACE * 12);
        x.setSize(new Dimension(30, 30));
        add(x);
    }

    private int getC4Position() {
        return SCORE_LINE_SPACE * 6;
    }

    public int getNoteScorePosition(Note n, int scaleLine) {
        double linePosition = scaleLine * 0.5;
        return ((int) (getC4Position() + SCORE_TOP_SPACE - (SCORE_LINE_SPACE * linePosition)));
    }


    private int getLineY(int lineIndex) {
        return SCORE_TOP_SPACE + SCORE_LINE_SPACE * lineIndex;
    }

    private boolean isLineVisible(int lineIndex) {
        return ((lineIndex > 1) && lineIndex != 7);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        for (int i = 0; i < SCORE_LINE_COUNT; i++) {
            if (isLineVisible(i)) { //not rendering first 2 lines
                g.drawLine(10, getLineY(i), this.getWidth() - 10, getLineY(i));
            }
        }

        g.drawLine(10, SCORE_TOP_SPACE + SCORE_LINE_SPACE * 2, 10, SCORE_TOP_SPACE + SCORE_LINE_SPACE * 12);
        g.drawLine(this.getWidth() - 10, SCORE_TOP_SPACE + SCORE_LINE_SPACE * 2, this.getWidth() - 10, SCORE_TOP_SPACE + SCORE_LINE_SPACE * 12);


    }
}
