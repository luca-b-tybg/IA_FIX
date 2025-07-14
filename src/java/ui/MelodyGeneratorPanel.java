package ui;

import scale.KeyFile;
import scale.Note;
import scale.Octave;
import scale.RhythmType;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MelodyGeneratorPanel extends JPanel {
    private JFrame parentWindow;

    ProgressionScorePanel scorePanel = new ProgressionScorePanel();

    class ProgressionListPanel extends JPanel {
        //TODO: add the progression list component
        //TODO: add control bar with open


        public ProgressionListPanel() {
            setLayout(new BorderLayout());
            JPanel controlPanel = new JPanel();

            add(controlPanel, BorderLayout.SOUTH);

            JButton addProgressionBtn = new JButton("Add");
            addProgressionBtn.addActionListener(ev -> {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(parentWindow) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        String content = FileUtils.loadFile(file);
                        String[] progressions = content.split(",");
                        Octave octave = new Octave(2);
                        for (String progression : progressions) {
                            Note n = Note.fromString(progression);
                            n.setRhythmType(RhythmType.SEMIBREVE);
                            octave.add(n);
                        }

                        scorePanel.setOctaves(List.of(octave));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            controlPanel.add(addProgressionBtn);
            controlPanel.add(new JButton("Remove"));
        }
    }

    public MelodyGeneratorPanel(JFrame parentWindow) {
        this.parentWindow = parentWindow;
        setLayout(new BorderLayout());
        add(new ProgressionListPanel(), BorderLayout.LINE_START);

        add(scorePanel, BorderLayout.CENTER);

    }
}
