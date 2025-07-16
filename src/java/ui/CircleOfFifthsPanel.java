package ui;

import circle.CircleOfFifthsKeyFile;
import scale.Note;
import scale.Octave;
import scale.RhythmType;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CircleOfFifthsPanel extends JPanel {


    class ControlPanel extends JPanel implements ProgressionChangeListener {
        private List<CircleOfFifthsKeyFile> progressions;
        JButton showProgressionBtn = new JButton("Show Progression");
        JButton saveProgressionBtn = new JButton("Save Progression");

        public ControlPanel(CircleOfFifthsComponent circleOfFifthsComponent) {
            JPanel circleControlPanel = new JPanel();
            JButton resetBtn = new JButton("Reset");


            showProgressionBtn.setEnabled(false);
            saveProgressionBtn.setEnabled(false);
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
                saveProgressionBtn.setEnabled(false);
            });

            saveProgressionBtn.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(parentWindow) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    StringBuilder progressionText = new StringBuilder();
                    for (CircleOfFifthsKeyFile keyFile : progressions) {
                        progressionText.append(keyFile.toString()).append(",");
                    }
                    try {
                        FileUtils.saveFile(file, progressionText.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
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
            boolean isProgressionValid = progressionKeys.size() >= 2;
            showProgressionBtn.setEnabled(isProgressionValid);
            saveProgressionBtn.setEnabled(isProgressionValid);
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
