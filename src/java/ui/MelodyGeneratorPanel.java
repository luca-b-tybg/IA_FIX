package ui;

import scale.KeyFile;
import scale.Note;
import scale.Octave;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MelodyGeneratorPanel extends JPanel {

    class ProgressionListPanel extends JPanel {
        //TODO: add the progression list component
        //TODO: add control bar with open


        public ProgressionListPanel() {
            setLayout(new BorderLayout());
            JPanel controlPanel = new JPanel();

            add(controlPanel, BorderLayout.SOUTH);
            controlPanel.add(new JButton("Add"));
            controlPanel.add(new JButton("Remove"));
        }
    }

    public MelodyGeneratorPanel() {
        setLayout(new BorderLayout());
        add(new ProgressionListPanel(), BorderLayout.LINE_START);
        ScorePanel scorePanel = new ScorePanel();
        Octave octave = new Octave(4);
        octave.add(Note.forKey(KeyFile.A));
        octave.add(Note.forKey(KeyFile.C));
        octave.add(Note.forKey(KeyFile.E));

        scorePanel.setOctaves(List.of(octave));
        add(scorePanel, BorderLayout.CENTER);

    }
}
