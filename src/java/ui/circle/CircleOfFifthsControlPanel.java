package ui.circle;

import circle.CircleOfFifthsKeyFile;
import ui.components.ProgressionMusicScoreComponent;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

class CircleOfFifthsControlPanel extends JPanel implements ProgressionChangeListener {

    private List<CircleOfFifthsKeyFile> progressions;
    JButton showProgressionBtn = new JButton("Show Progression");
    JButton saveProgressionBtn = new JButton("Save Progression");

    public CircleOfFifthsControlPanel(JFrame parentWindow, CircleOfFifthsComponent circleOfFifthsComponent) {

        JPanel circleControlPanel = new JPanel();
        JButton resetBtn = new JButton("Reset");
        showProgressionBtn.setEnabled(false);
        saveProgressionBtn.setEnabled(false);
        showProgressionBtn.addActionListener(e -> {
            JDialog dialog = new JDialog(parentWindow);
            dialog.setSize(new Dimension(800, 600));
            Container dialogContainer = dialog.getContentPane();
            ProgressionMusicScoreComponent scorePanel = new ProgressionMusicScoreComponent();
            scorePanel.setLocation(new Point(10, 10));
            scorePanel.setProgressions(progressions);
            JScrollPane progressionScroller = new JScrollPane(scorePanel);
            scorePanel.setPreferredSize(new Dimension(65 * progressions.size(), 300));
            dialogContainer.add(progressionScroller);
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


    @Override
    public void onNewProgression(List<CircleOfFifthsKeyFile> progressionKeys) {
        this.progressions = progressionKeys;
        boolean isProgressionValid = progressionKeys.size() >= 2;
        showProgressionBtn.setEnabled(isProgressionValid);
        saveProgressionBtn.setEnabled(isProgressionValid);
    }
}
