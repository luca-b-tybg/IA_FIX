package ui;

import scale.KeyFile;
import scale.Note;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ScoreLine {
    private KeyFile keyFile;
    private boolean isVisible = true;

    public ScoreLine(KeyFile keyFile) {
        this(keyFile, true);
    }

    public ScoreLine(KeyFile keyFile, boolean isVisible) {
        this.keyFile = keyFile;
        this.isVisible = isVisible;
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



    private java.util.List<ScoreLine> lines = Arrays.asList(
            new ScoreLine(KeyFile.C, false),
            new ScoreLine(KeyFile.D, false),
            new ScoreLine(KeyFile.E),
            new ScoreLine(KeyFile.F),
            new ScoreLine(KeyFile.G),
            new ScoreLine(KeyFile.A),
            new ScoreLine(KeyFile.B)
    );



    public ScorePanel() {
        setLayout(null);

        ImageIcon clefIconImage = new ImageIcon(getResource("clef_small.png"));
        fullNoteImage = new ImageIcon(getResource("music_note.png"));
        crossNoteImage = new ImageIcon(getResource("music_note-dash.png"));
        clefIcon.setIcon(clefIconImage);
        clefIcon.setSize(new Dimension(50, 127));
        clefIcon.setLocation(10, SCORE_PREFIX);

    }

    private URL getResource(String resourceName) {
        return getClass().getResource("/" + resourceName);
    }

    public double getStartScorePosition(java.util.List<Note> notes) {
        Note firstNote = notes.getFirst();
        return switch (firstNote.getKey()) {
          //  case KeyFile.C -> -1.;
           // case KeyFile.D -> -0.5;
           // case KeyFile.B -> -1.5;

            default -> KEYS_SCORE_POSITIONS.get(firstNote.getKey());
        };
    }

    int cleveSymbolCount = 0;

    private void showCleveSymbol(double linePosition, String symbol, int size) {
        JLabel label = new JLabel(symbol);
        label.setFont(new Font("Default", Font.BOLD, size));
        label.setSize(40, 40);
        label.setLocation(60 + (cleveSymbolCount * 15), getNoteScorePosition(linePosition) - 40);
        cleveSymbolCount++;
        this.add(label);
    }

    private void showSharpSymbol(double linePosition) {
     //   double linePosition = KEYS_SCORE_POSITIONS.get(note.getKey());
        showCleveSymbol(linePosition, SHARP_SYMBOL, 24);
    }

    public void showFlatSymbol(double linePosition) {
        showCleveSymbol(linePosition, FLAT_SYMBOL, 34);
    }

    private void resetView() {
        this.removeAll();
        cleveSymbolCount = 0;
    }

    private  List<Note> getNoteForKey(List <Note> notes , KeyFile key) {
        return notes.stream().filter(note-> note.getKey() == key).collect(Collectors.toList());
    }

    public void setNotes(java.util.List<Note> notes) {
        resetView();
        int noteHeight = 100;
        int column = 0;
        double notePosition = getStartScorePosition(notes);



        for (Note note : notes) {
            //double labelPosition = KEYS_SCORE_POSITIONS.get(note.getKey());
          //  int lineIndex =0;
                for (ScoreLine scoreLine : lines) {
                  //  Note note = getNoteForKey(notes,  scoreLine.getKeyFile()).getFirst();
                if (scoreLine.getKeyFile() == note.getKey()) {
                    JLabel noteLabel = new JLabel();
                    if (note.getKey() == KeyFile.C && notePosition < 0) {
                        noteLabel.setIcon(crossNoteImage);
                    } else {
                        noteLabel.setIcon(fullNoteImage);
                    }

                    if (note.isSharp) {

                        showSharpSymbol(SYMBOLS_SCORE_POSITIONS.get(note.getKey()) );
                    }

                    if(note.isFlat()) {
                        showFlatSymbol(SYMBOLS_SCORE_POSITIONS.get(note.getKey()));
                    }


                    noteLabel.setLocation(120 + (NOTE_GAP_SPACE * column), getNoteScorePosition(notePosition) - noteHeight);
                    noteLabel.setSize(30, noteHeight);
                    this.add(noteLabel);
                    column++;
                    notePosition += 0.5;
                    //lineIndex ++;
                    break;
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

        g.drawLine(10, SCORE_PREFIX + SCORE_SPACE, 10, SCORE_PREFIX + SCORE_SPACE * 5);
        g.drawLine(this.getWidth() - 10, SCORE_PREFIX + SCORE_SPACE, this.getWidth() - 10, SCORE_PREFIX + SCORE_SPACE * 5);
    }
}
