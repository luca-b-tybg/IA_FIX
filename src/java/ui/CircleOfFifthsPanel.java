package ui;

import circle.CircleOfFifthsKeyFile;
import scale.Note;
import scale.Octave;
import scale.OctaveRange;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CircleOfFifthsPanel extends JPanel {

    class ControlPanel extends JPanel implements ProgressionChangeListener {
        private List<CircleOfFifthsKeyFile> progressions;
        JButton showProgressionBtn = new JButton("Show Progression");

        public ControlPanel(CircleOfFifthsComponent circleOfFifthsComponent) {
            JPanel circleControlPanel = new JPanel();
            JButton resetBtn = new JButton("Reset");


            showProgressionBtn.setEnabled(false);
            showProgressionBtn.addActionListener(e -> {
                JDialog dialog = new JDialog(parentWindow);
                dialog.setSize(new Dimension(800, 600));
                Container dialogContainer = dialog.getContentPane();
                ScorePanel scorePanel = new ScorePanel();
                scorePanel.setLocation(new Point(10, 10));
                scorePanel.setSize(new Dimension(800, 600));
                scorePanel.setVisible(true);
                scorePanel.setOctaves(new OctaveRange(4, 5), getProgressionOctave());
                dialogContainer.add(scorePanel);
                dialog.setVisible(true);
            });
            resetBtn.addActionListener(_e -> {
                circleOfFifthsComponent.reset();
                showProgressionBtn.setEnabled(false);
            });

            circleControlPanel.add(resetBtn);
            circleControlPanel.add(showProgressionBtn);
            add(circleControlPanel);
        }

        private java.util.List<Octave> getProgressionOctave() {
            Octave defaultOctave = new Octave(4);
            for (CircleOfFifthsKeyFile keyFile : progressions) {
                defaultOctave.add(new Note(keyFile.getKeyFile(), false, keyFile.isSharp()));
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
