package ui;

import scale.KeyFile;
import scale.Note;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Map;

public class ScorePanel extends JPanel {

    private static final int SCORE_SPACE = 20;
    private static final int NOTE_GAP_SPACE = 40;
    private static Map<KeyFile, Double> KEYS_SCORE_POSITIONS = Map.of(
            KeyFile.C, -2.,
            KeyFile.D, -1.5,
            KeyFile.E, -1.,
            KeyFile.F, -0.5);

    private JLabel clefIcon = new JLabel();
    private ImageIcon fullNoteImage;
    private ImageIcon crossNoteImage;

    // private java.util.List<Note> notes;

    public ScorePanel() {
        setLayout(null);

        ImageIcon clefIconImage = new ImageIcon(getResource("clef_small.png"));
        fullNoteImage = new ImageIcon(getResource("music_note.png"));
        clefIcon.setIcon(clefIconImage);
        clefIcon.setSize(new Dimension(50, 127));
        //clefIcon.setLocation(10, 10);
        ////      this.scoreLabel = new JLabel("test");

        add(this.clefIcon);
    }

    private URL getResource(String resourceName) {
        return getClass().getResource("/" + resourceName);
    }

    public void setNotes(java.util.List<Note> notes) {
        int column = 0;
        int noteHeight = 100;
        for (Note note : notes) {
            JLabel noteLabel = new JLabel();
            noteLabel.setIcon(fullNoteImage);
            noteLabel.setLocation(50 + (NOTE_GAP_SPACE * column), getNoteScorePosition(note) - noteHeight);
            noteLabel.setSize(30, noteHeight);
            this.add(noteLabel);
            column ++;
        }
        this.repaint();

    }

    public int getNoteScorePosition(Note note   ) {
       double position =  KEYS_SCORE_POSITIONS.get(note.getKey());
       int bottomLinePosition = 5 * SCORE_SPACE;
        return bottomLinePosition - (int)(SCORE_SPACE * position);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ((Graphics2D) g).setStroke(new BasicStroke(2));
        for (int i = 1; i <= 5; i++) {
            g.drawLine(10, SCORE_SPACE * i, this.getWidth() - 10, SCORE_SPACE * i);
        }

          g.drawLine(10, SCORE_SPACE, 10, SCORE_SPACE* 5);
        g.drawLine(this.getWidth() -10, SCORE_SPACE, this.getWidth() -10, SCORE_SPACE* 5);
    }
}
