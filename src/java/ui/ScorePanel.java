package ui;

import diatonicscale.DS7Scales;
import scale.*;
import ui.components.ScoreBarComponent;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ScorePanel extends JPanel {

    private static final int SCORE_LINE_SPACE = 20;

    private static final int NOTE_GAP_SPACE = 45;

    private static final int SCORE_LINE_COUNT = 13;

    private static final Map<KeyFile, Integer> SYMBOLS_SCORE_POSITIONS = Map.of(
            KeyFile.C, 7,
            KeyFile.D, 8,
            KeyFile.E, 9,
            KeyFile.F, 4,
            KeyFile.G, 5,
            KeyFile.B, 7,
            KeyFile.A, 6);

    private static final String FLAT_SYMBOL = "♭";
    private static final String SHARP_SYMBOL = "♯";


    private ImageIcon fullNoteImage;
    private ImageIcon semiNoteImage;
    private ImageIcon crossNoteImage;
    int cleveSymbolCount = 0;
    private Set<Note> sharpNotes = new HashSet<>();
    private Set<Note> flatNotes = new HashSet<>();

    private java.util.List<Octave> octaves = new ArrayList<>();


    public ScorePanel() {
        setLayout(null);
        fullNoteImage = new ImageIcon(getResource("music_note.png"));
        crossNoteImage = new ImageIcon(getResource("music_note-dash.png"));
        semiNoteImage = new ImageIcon(getResource("semibreve.png"));
    }


    private URL getResource(String resourceName) {
        return getClass().getResource("/" + resourceName);
    }

    private void showClefIcon() {
        // clef icon setup
        JLabel clefIcon = new JLabel();
        ImageIcon clefIconImage = new ImageIcon(getResource("clef_small.png"));
        clefIcon.setIcon(clefIconImage);
        clefIcon.setSize(new Dimension(50, 127));
        clefIcon.setLocation(10, getScoreTopPosition());
        add(clefIcon);
    }

    private void showBaseClefIcon() {
        // base clef icon setup
        JLabel baseClefIcon = new JLabel();
        ImageIcon baseClefIconImage = new ImageIcon(getResource("base_clef.png"));
        baseClefIcon.setIcon(baseClefIconImage);
        baseClefIcon.setSize(new Dimension(51, 48));
        baseClefIcon.setLocation(10, (getScoreTopPosition()) + SCORE_LINE_SPACE * 8);
        this.add(baseClefIcon);
    }


    private void showCleveSymbol(double linePosition, String symbol, int size) {
        JLabel label = new JLabel(symbol);
        label.setFont(new Font("Default", Font.BOLD, size));
        label.setSize(40, 40);
        label.setLocation(60 + (cleveSymbolCount * 15), getNoteScorePosition((int) linePosition) - 40);
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

    protected int getScoreNotePosition(int octaveIndex, Note note) {
        int keyPosition = DS7Scales.C_MAJOR_NOTES.indexOf(note.getKey());
        return keyPosition + (7 * (octaveIndex - 4));
    }


    public void setOctaves(java.util.List<Octave> octaves) {
        this.octaves = octaves;
        repaint();
    }

    public void showScoreBar(int columnPosition, int startLineIndex, int endLineIndex) {
       int barXPos =  getColumnPosition(columnPosition) -10;
       int barYPos =  getNoteScorePosition(endLineIndex);
       int barHeight = Math.abs(Math.abs(endLineIndex) -Math.abs( startLineIndex)) * SCORE_LINE_SPACE/2;
        ScoreBarComponent scoreBarComponent = new ScoreBarComponent(4, barHeight);
        scoreBarComponent.setLocation(barXPos, barYPos);
        add(scoreBarComponent);
    }

    protected ImageIcon getNoteImage(Octave octave, Note note) {
        if (note.getRhythmType() == RhythmType.SEMIBREVE) {
            return semiNoteImage;
        }
        if (note.getKey() == KeyFile.C && (octave.getPosition() == 2 || octave.getPosition() == 4)) {
            return crossNoteImage;
        }
        return fullNoteImage;
    }

    private void renderComponents(java.util.List<Octave> octaves) {
        int columnPosition = 0;
        for (Octave octave : octaves) {
            for (Note note : octave.getNotes()) {
                int notePosition = getScoreNotePosition(octave.getPosition(), note);

                var noteComponent = getNote(notePosition, columnPosition, octave, note);
                showNoteText(octave.getPosition(), noteComponent.getX(), note);
                add(noteComponent);
                noteAdded(note, octave, notePosition, columnPosition);
                columnPosition++;

            }
        }

    }

    public void addNote(int linePosition, int columnPosition, Note note) {

    }

    public void addNote(int linePosition, Note note) {

    }

    protected void noteAdded(Note note, Octave octave, int linePosition, int columnPosition) {

    }

    protected JComponent getNote(int linePosition, int columnPosition, Octave octave, Note note) {
        JComponent noteLabel = getNoteComponent(octave, note);
        if (note.isSharp) {
            showSharpSymbol(note);
        }
        if (note.isFlat()) {
            showFlatSymbol(note);
        }
        int noteXPos = getColumnPosition(columnPosition);
        noteLabel.setLocation(noteXPos, getNoteScorePosition(linePosition) - getImageCorrection(noteLabel));
        return noteLabel;
    }

//TODO: merge this into getNote
    private JComponent getNoteComponent(Octave octave, Note note) {
        JLabel noteLabel = new JLabel();
        ImageIcon imageIcon = getNoteImage(octave, note);
        noteLabel.setIcon(imageIcon);
        noteLabel.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        return noteLabel;
    }

    private int getImageCorrection(JComponent imageIcon) {
        if (imageIcon.getHeight() > 40) {
            return (imageIcon.getHeight() / 2 + SCORE_LINE_SPACE / 2);
        } else {
            return - SCORE_LINE_SPACE / 2;
        }
    }


    private int getScoreTopPosition() {
        return getHeight() / 2 - SCORE_LINE_SPACE * SCORE_LINE_COUNT / 2;
    }

    private void showNoteText(int octave, int xPosition, Note note) {
        JLabel noteText = new JLabel(note.toString() + octave);
        noteText.setLocation(xPosition + 6, getScoreTopPosition() + SCORE_LINE_SPACE * 15);
        noteText.setSize(new Dimension(30, 30));
        add(noteText);
    }

    private int getC4Position() {
        return SCORE_LINE_SPACE * 6;
    }

    private int getColumnPosition(int columnIndex) {
        return 120 + NOTE_GAP_SPACE * columnIndex;
    }
    private int getNoteScorePosition(int scaleLine) {
        double linePosition = scaleLine * 0.5;
        return ((int) (getC4Position() + getScoreTopPosition() - (SCORE_LINE_SPACE * linePosition)));
    }


    private int getLineY(int lineIndex) {
        return getScoreTopPosition() + SCORE_LINE_SPACE * lineIndex;
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


        g.drawLine(10, getScoreTopPosition() + SCORE_LINE_SPACE * 2, 10, getScoreTopPosition() + SCORE_LINE_SPACE * 12);
        g.drawLine(this.getWidth() - 10, getScoreTopPosition() + SCORE_LINE_SPACE * 2, this.getWidth() - 10, getScoreTopPosition() + SCORE_LINE_SPACE * 12);
        if (!octaves.isEmpty()) {
            resetView();
            renderComponents(octaves);
            showClefIcon();
            showBaseClefIcon();

        }

    }
}
