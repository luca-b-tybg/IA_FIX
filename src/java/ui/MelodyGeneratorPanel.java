package ui;

import circle.CircleOfFifthsKeyFile;
import melodygenerator.MelodyGenerator;
import scale.KeyFile;
import scale.Note;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MelodyGeneratorPanel extends JPanel {
    private JFrame parentWindow;
    private MelodyGenerator melodyGenerator = new MelodyGenerator();
    private JScrollPane progressionScroller;

    public JPanel getControlPanel() {
        JPanel controlPanel = new JPanel();

        JButton addProgressionBtn = new JButton("Open Progression");
        addProgressionBtn.addActionListener(ev -> {
            //Displaying the file selection dialog
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(parentWindow) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String content = FileUtils.loadFile(file);
                    String[] progressions = content.split(",");
                    List<CircleOfFifthsKeyFile> progressionKeys = new ArrayList<>();
                    for (String progression : progressions) {
                        if (!progression.trim().isEmpty()) {
                            CircleOfFifthsKeyFile key = CircleOfFifthsKeyFile.fromString(progression.trim());
                            progressionKeys.add(key);
                        }
                    }
                    setProgressions(progressionKeys);// once the file is loaded, the new progressions are displayed

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        controlPanel.add(addProgressionBtn);
        return controlPanel;
    }

    private void setProgressions(List<CircleOfFifthsKeyFile> progressionKeys) {
        if (progressionScroller != null) {
            remove(progressionScroller);
            progressionScroller = null;
        }
        
        // Create a score component that displays key signature based on first chord
        ProgressionMusicScoreComponent scorePanel = new ProgressionMusicScoreComponent() {
            @Override
            protected List<Note> getTopBarNotes(Note note) {
                // Generate melody based on the chord progression
                return melodyGenerator.generateMelodyProgression(progressionKeys.get(0));
            }
        };
        
        scorePanel.setProgressions(progressionKeys);
        scorePanel.setPreferredSize(new Dimension(progressionKeys.size() * 175, 300));
        progressionScroller = new JScrollPane(scorePanel);
        add(progressionScroller, BorderLayout.CENTER);
        validate();
    }

    public MelodyGeneratorPanel(JFrame parentWindow) {
        this.parentWindow = parentWindow;
        setLayout(new BorderLayout());
        add(getControlPanel(), BorderLayout.SOUTH);
    }
}