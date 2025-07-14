package ui;

import circle.CircleOfFifthsKeyFile;
import scale.KeyFile;
import scale.Note;
import scale.Octave;
import scale.RhythmType;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CircleOfFifthsPanel extends JPanel {
    private Map<KeyFile, Integer> positions = Map.of(KeyFile.A, -9,
            KeyFile.B, -8, KeyFile.C, -7, KeyFile.D, -6, KeyFile.E, -5, KeyFile.G, -10);

    class ProgressionScorePanel extends ScorePanel {
        @Override
        protected int getScoreNotePosition(int octaveIndex, Note note) {
            if (octaveIndex == 2 && note.getKey() == KeyFile.F) {
                return -11;
            }
            if (positions.containsKey(note.getKey())) {
                return positions.get(note.getKey());
            }

            return super.getScoreNotePosition(octaveIndex, note);
        }

        @Override
        protected void noteAdded(Note note, Octave octave, int linePosition, int columnPosition) {

            for (int i = 1; i <= 2; i++) {
                JComponent note1 = getNote(linePosition + 2 * i, columnPosition, octave, new Note(KeyFile.C, false, false, RhythmType.SEMIBREVE));
                add(note1);
            }
        }
    }

    class ControlPanel extends JPanel implements ProgressionChangeListener {
        private List<CircleOfFifthsKeyFile> progressions;
        JButton showProgressionBtn = new JButton("Show Progression");
        JButton saveProgressionBtn = new JButton("Save Progression");

        public ControlPanel(CircleOfFifthsComponent circleOfFifthsComponent) {
            JPanel circleControlPanel = new JPanel();
            JButton resetBtn = new JButton("Reset");


            showProgressionBtn.setEnabled(false);
            showProgressionBtn.addActionListener(e -> {
                JDialog dialog = new JDialog(parentWindow);
                dialog.setSize(new Dimension(800, 600));
                Container dialogContainer = dialog.getContentPane();
                ScorePanel scorePanel = new ProgressionScorePanel();
                scorePanel.setLocation(new Point(10, 10));
                scorePanel.setSize(new Dimension(800, 600));
                scorePanel.setVisible(true);
                scorePanel.setOctaves(getProgressionOctave());
                dialogContainer.add(scorePanel);
                dialog.setVisible(true);
            });
            resetBtn.addActionListener(_e -> {
                circleOfFifthsComponent.reset();
                showProgressionBtn.setEnabled(false);
            });

            circleControlPanel.add(resetBtn);
            circleControlPanel.add(showProgressionBtn);
            circleControlPanel.add(saveProgressionBtn);
            add(circleControlPanel);
        }

        private java.util.List<Octave> getProgressionOctave() {
            Octave defaultOctave = new Octave(2);
            for (CircleOfFifthsKeyFile keyFile : progressions) {
                defaultOctave.add(new Note(keyFile.getKeyFile(), false, keyFile.isSharp(), RhythmType.SEMIBREVE));
            }
            System.out.println(defaultOctave);
            return List.of(defaultOctave);
        }

        @Override
        public void onNewProgression(List<CircleOfFifthsKeyFile> progressionKeys) {
            this.progressions = progressionKeys;
            showProgressionBtn.setEnabled(progressionKeys.size() >= 2);
        }
    }

    private final JFrame parentWindow;
    private final CircleOfFifthsComponent circleOfFifthsComponent = new CircleOfFifthsComponent();

    public CircleOfFifthsPanel(JFrame parentWindow) {
        this.parentWindow = parentWindow;
        setLayout(new BorderLayout());
        ControlPanel controlPanel = new ControlPanel(circleOfFifthsComponent);
        add(controlPanel, BorderLayout.PAGE_END);
        circleOfFifthsComponent.addProgressionChangeListener(controlPanel);
        add(circleOfFifthsComponent, BorderLayout.CENTER);
    }
}
