package ui;

import scale.KeyFile;
import scale.Note;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

class ScoreLine {
    private KeyFile keyFile;
    private boolean isVisible = true;
    private double linePosition = 0;


    public ScoreLine(KeyFile keyFile, double linePosition) {
        this(keyFile, true, linePosition);
    }

    public ScoreLine(KeyFile keyFile, boolean isVisible, double linePosition) {
        this.keyFile = keyFile;
        this.isVisible = isVisible;
        this.linePosition = linePosition;
    }

    public KeyFile getKeyFile() {
        return keyFile;
    }

    public boolean isVisible() {
        return isVisible;
    }

}

public class ScorePanel extends JPanel {

    private static final int SCORE_SPACE = 20;
    private static final int NOTE_GAP_SPACE = 45;
    private static Map<KeyFile, Double> KEYS_SCORE_POSITIONS = Map.of(
            KeyFile.C, -2.,
            KeyFile.D, -0.5,
            KeyFile.E, 1.,
            KeyFile.F, -0.5,
            KeyFile.B, 2.,
            KeyFile.A, 1.5);

    private JLabel clefIcon = new JLabel();
    private ImageIcon fullNoteImage;
    private ImageIcon crossNoteImage;

    private java.util.List<ScoreLine> lines = Arrays.asList(
            new ScoreLine(KeyFile.C, false, -2),
            new ScoreLine(KeyFile.D, false, -1.5),
            new ScoreLine(KeyFile.E, 1.),
            new ScoreLine(KeyFile.F, -0.5),
            new ScoreLine(KeyFile.G, 0),
            new ScoreLine(KeyFile.A, 1.5),
            new ScoreLine(KeyFile.B, 1)
    );

    public ScorePanel() {
        setLayout(null);

        ImageIcon clefIconImage = new ImageIcon(getResource("clef_small.png"));
        fullNoteImage = new ImageIcon(getResource("music_note.png"));
        crossNoteImage = new ImageIcon(getResource("music_note-dash.png"));
        clefIcon.setIcon(clefIconImage);
        clefIcon.setSize(new Dimension(50, 127));
        clefIcon.setLocation(10,  SCORE_PREFIX );
        ////      this.scoreLabel = new JLabel("test");


    }

    private URL getResource(String resourceName) {
        return getClass().getResource("/" + resourceName);
    }

    public double getStartScorePosition(java.util.List<Note> notes) {
        Note firstNote = notes.getFirst();
        return switch (firstNote.getKey()) {
            case KeyFile.C -> -1.;
            case KeyFile.D -> -0.5;
            case KeyFile.B -> -1.5;

            default -> KEYS_SCORE_POSITIONS.get(firstNote.getKey());
        };
    }
    int sharpCount = 0;
    private JLabel getSharpSign() {
        return new JLabel("#");
    }
    private void showSharp(double linePosition) {
        JLabel sharpSign  = getSharpSign();
        sharpSign.setLocation(50 +(sharpCount * 15), getNoteScorePosition(linePosition) - 40);
        sharpSign.setFont(new Font("Default", Font.BOLD, 24));
        sharpSign.setSize(40,40);
        sharpCount++;
        this.add(sharpSign);
    }

    public void setNotes(java.util.List<Note> notes) {
        this.removeAll();
        int column = 0;
        int noteHeight = 100;
        sharpCount = 0;
        double linePosition = getStartScorePosition(notes);

        for (Note note : notes) {
            for (ScoreLine scoreLine : lines) {
                if (scoreLine.getKeyFile() == note.getKey()) {
                    JLabel noteLabel = new JLabel();
                    if (note.getKey() == KeyFile.C && linePosition < 0) {
                        noteLabel.setIcon(crossNoteImage);
                    } else {
                        noteLabel.setIcon(fullNoteImage);
                    }

                    if(note.isSharp) {
                        System.out.println(note);
                        showSharp(linePosition);
                    }

                    noteLabel.setLocation(120 + (NOTE_GAP_SPACE * column), getNoteScorePosition(linePosition) - noteHeight);
                    noteLabel.setSize(30, noteHeight);
                    this.add(noteLabel);
                    column++;
                    linePosition += 0.5;
                }
            }
        }
        add(this.clefIcon);
        this.repaint();

    }


    public int getNoteScorePosition(double linePosition) {
        //double position = KEYS_SCORE_POSITIONS.get(note.getKey());
        int bottomLinePosition = (lines.size() - 1) * SCORE_SPACE;
        return bottomLinePosition + SCORE_PREFIX - (int) (SCORE_SPACE * linePosition);
    }
    private int SCORE_PREFIX = 100;

    private int getLineY(int lineIndex) {
        return SCORE_PREFIX + SCORE_SPACE * lineIndex;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ((Graphics2D) g).setStroke(new BasicStroke(2));
        int lineIndex = -1;
        for (ScoreLine line : lines) {
            if (line.isVisible()) {
                g.drawLine(10, getLineY(lineIndex), this.getWidth() - 10, getLineY(lineIndex));
            }
            lineIndex++;
        }

        g.drawLine(10, SCORE_PREFIX + SCORE_SPACE, 10, SCORE_PREFIX +SCORE_SPACE * 5);
        g.drawLine(this.getWidth() - 10, SCORE_PREFIX + SCORE_SPACE, this.getWidth() - 10, SCORE_PREFIX +SCORE_SPACE * 5);
    }
}
