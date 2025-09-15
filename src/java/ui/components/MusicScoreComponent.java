package ui.components;

import scale.KeyFile;
import scale.Note;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Reusable component to render the music score and its components
 */
public class MusicScoreComponent extends JPanel {

    static class PositionedNote {
        Note note;
        int column;
        int line;

        public PositionedNote(Note note, int column, int line) {
            this.note = note;
            this.column = column;
            this.line = line;
        }
    }

    static class ScoreBar {
        int column = 0;
        int startLine = 0;
        int endLine = 0;

        public ScoreBar(int column, int startLine, int endLine) {
            this.column = column;
            this.startLine = startLine;
            this.endLine = endLine;
        }
    }

    private static final int SCORE_LINE_SPACE = 20;
    private static final int NOTE_GAP_SPACE = 55;
    private static final int SCORE_LINE_COUNT = 13;
    private static final int STAVE_SPACING = 140; //The amount of pixels allocated to the keys symbols


    // Key signature positions for sharps and flats on the treble clef
    private static final Map<KeyFile, Integer> SHARP_POSITIONS = Map.of(
            KeyFile.F, 9,   // F# on top line
            KeyFile.C, 6,   // C# on 3rd space
            KeyFile.G, 10,  // G# above staff
            KeyFile.D, 7,   // D# on 4th line
            KeyFile.A, 5,   // A# on 2nd space
            KeyFile.E, 8,   // E# on 4th space (enharmonic F)
            KeyFile.B, 4    // B# on 2nd line (enharmonic C)
    );

    private static final Map<KeyFile, Integer> FLAT_POSITIONS = Map.of(
            KeyFile.B, 7,   // Bb on 4th line
            KeyFile.E, 9,   // Eb on top line
            KeyFile.A, 6,   // Ab on 3rd space
            KeyFile.D, 8,   // Db on 4th space
            KeyFile.G, 5,   // Gb on 2nd space
            KeyFile.C, 7,   // Cb on 4th line (enharmonic B)
            KeyFile.F, 4    // Fb on 2nd line (enharmonic E)
    );

    // Order for sharps: F# C# G# D# A# E# B#
    private static final KeyFile[] SHARP_ORDER = {KeyFile.F, KeyFile.C, KeyFile.G, KeyFile.D, KeyFile.A, KeyFile.E, KeyFile.B};

    // Order for flats: Bb Eb Ab Db Gb Cb Fb
    private static final KeyFile[] FLAT_ORDER = {KeyFile.B, KeyFile.E, KeyFile.A, KeyFile.D, KeyFile.G, KeyFile.C, KeyFile.F};

    private static final String FLAT_SYMBOL = "♭";
    private static final String SHARP_SYMBOL = "♯";

    int cleveSymbolCount = 0;

    private java.util.List<PositionedNote> notes = new ArrayList<>();
    private java.util.List<ScoreBar> scoreBars = new ArrayList<>();

    public MusicScoreComponent() {
        setLayout(null);
    }

    private URL getResource(String resourceName) {
        return getClass().getResource("/" + resourceName);
    }

    private void showClefIcon() {
        JLabel clefIcon = new JLabel();
        ImageIcon clefIconImage = new ImageIcon(getResource("clef_small.png"));
        clefIcon.setIcon(clefIconImage);
        clefIcon.setSize(new Dimension(50, 127));
        clefIcon.setLocation(10, getScoreTopPosition());
        add(clefIcon);
    }

    private void showBaseClefIcon() {
        JLabel baseClefIcon = new JLabel();
        ImageIcon baseClefIconImage = new ImageIcon(getResource("base_clef.png"));
        baseClefIcon.setIcon(baseClefIconImage);
        baseClefIcon.setSize(new Dimension(51, 48));
        baseClefIcon.setLocation(10, (getScoreTopPosition()) + SCORE_LINE_SPACE * 8);
        this.add(baseClefIcon);
    }

    private void showKeySignature() {
        // Display sharps in the correct order
        Set<KeyFile> displayedSharps = getSharpKeys();
        for (KeyFile sharpKey : SHARP_ORDER) {
            if (displayedSharps.contains(sharpKey)) {
                int linePosition = SHARP_POSITIONS.get(sharpKey);
                showKeySignatureSymbol(linePosition, SHARP_SYMBOL, 24);
            }
        }
        // Display flats in the correct order
        Set<KeyFile> displayedFlats = getFlatKeys();
        for (KeyFile flatKey : FLAT_ORDER) {
            if (displayedFlats.contains(flatKey)) {
                int linePosition = FLAT_POSITIONS.get(flatKey);
                showKeySignatureSymbol(linePosition, FLAT_SYMBOL, 34);
            }
        }
    }

    private void showKeySignatureSymbol(int linePosition, String symbol, int size) {
        JLabel label = new JLabel(symbol);
        label.setFont(new Font("Default", Font.BOLD, size));
        label.setSize(40, 40);
        label.setLocation(55 + (cleveSymbolCount * 11), getNoteScorePosition(linePosition) - 15);
        cleveSymbolCount++;
        this.add(label);
    }

    public Set<KeyFile> getSharpKeys() {
        Set<KeyFile> displayedSharps = new HashSet<>();
        for (PositionedNote posNote : notes) {
            if (posNote.note.isSharp()) {
                displayedSharps.add(posNote.note.getKey());
            }
        }
        return displayedSharps;
    }

    public Set<KeyFile> getFlatKeys() {
        Set<KeyFile> displayedFlats = new HashSet<>();
        for (PositionedNote posNote : notes) {
            if (posNote.note.isFlat()) {
                displayedFlats.add(posNote.note.getKey());
            }
        }
        return displayedFlats;
    }


    private void resetView() {
        this.removeAll();

        cleveSymbolCount = 0;
        labeledColumns.clear();
    }

    public void addScoreBar(int columnPosition, int startLineIndex, int endLineIndex) {
        scoreBars.add(new ScoreBar(columnPosition, startLineIndex, endLineIndex));
    }

    private ScoreBarComponent getScorebarComponent(ScoreBar scoreBar) {
        int barXPos = getColumnPosition(scoreBar.column) - 10;
        int barYPos = getNoteScorePosition(scoreBar.endLine);
        int barHeight = Math.abs(Math.abs(scoreBar.endLine) - Math.abs(scoreBar.startLine)) * SCORE_LINE_SPACE / 2;
        ScoreBarComponent scoreBarComponent = new ScoreBarComponent(4, barHeight);
        scoreBarComponent.setLocation(barXPos, barYPos);
        return scoreBarComponent;
    }

    private Set<Integer> labeledColumns = new HashSet<>();

    private void renderComponents(java.util.List<PositionedNote> notes) {
        for (PositionedNote localNote : notes) {
            var noteComponent = getNote(localNote.line, localNote.column, localNote.note);
            //make sure we don't overlap bottom labels
            if (!labeledColumns.contains(localNote.column)) {
                showNoteText(noteComponent.getX(), localNote.note);
                labeledColumns.add(localNote.column);
            }
            add(noteComponent);
        }
        for (ScoreBar scoreBar : scoreBars) {
            add(getScorebarComponent(scoreBar));
        }
    }

    public void addNote(int linePosition, int columnPosition, Note note) {
        notes.add(new PositionedNote(note, columnPosition, linePosition));
    }

    private JComponent getNote(int linePosition, int columnPosition, Note note) {
        JComponent noteLabel = getNoteComponent(linePosition, note);
        int noteXPos = getColumnPosition(columnPosition);
        noteLabel.setLocation(noteXPos, getNoteScorePosition(linePosition) - getImageCorrection(noteLabel));
        return noteLabel;
    }

    private JComponent getNoteComponent(int linePosition, Note note) {
        if (linePosition == 0 || linePosition == 12 || linePosition == -12 || linePosition == -14) {
            return new NoteComponent(note, NoteComponent.CrossLinePosition.MIDDLE);
        }
        if (linePosition == 13) {
            return new NoteComponent(note, NoteComponent.CrossLinePosition.BOTTOM);
        }
        if (linePosition == -13) {
            return new NoteComponent(note, NoteComponent.CrossLinePosition.TOP);
        }
        return new NoteComponent(note);
    }

    private int getImageCorrection(JComponent imageIcon) {
        if (imageIcon.getHeight() > 40) {
            return (imageIcon.getHeight() / 2 + SCORE_LINE_SPACE / 2);
        } else {
            return -SCORE_LINE_SPACE / 2;
        }
    }

    private int getScoreTopPosition() {
        return getHeight() / 2 - SCORE_LINE_SPACE * SCORE_LINE_COUNT / 2;
    }

    private void showNoteText(int xPosition, Note note) {
        // Create proper note label with accidentals
        String noteLabel = note.getKey().toString();
        if (note.isFlat()) {
            noteLabel += "b";
        }
        if (note.isSharp()) {
            noteLabel += "#";
        }
        noteLabel += note.getOctave();

        JLabel noteText = new JLabel(noteLabel);
        noteText.setLocation(xPosition + 6, getScoreTopPosition() + SCORE_LINE_SPACE * 15);
        noteText.setSize(new Dimension(50, 30)); // Made wider to accommodate accidentals
        add(noteText);
    }

    private int getC4Position() {
        return SCORE_LINE_SPACE * 6;
    }

    private int getColumnPosition(int columnIndex) {
        return STAVE_SPACING + NOTE_GAP_SPACE * columnIndex;
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

        if (!notes.isEmpty()) {
            resetView();
            showClefIcon();
            showKeySignature(); // Show key signature in correct order
            showBaseClefIcon();
            renderComponents(notes);
        }
    }
}