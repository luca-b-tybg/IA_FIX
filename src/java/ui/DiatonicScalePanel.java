package ui;

import diatonicscale.DS7Scales;
import diatonicscale.DiatonicScaleInputs;
import scale.OctaveGenerator;
import scale.Note;
import scale.Octave;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiatonicScalePanel extends JPanel implements DiatonicScaleParameterListener {
    private final DiatonicScaleInputsPanel inputParamsPanel = new DiatonicScaleInputsPanel();
    private final ScorePanel scorePanel = new ScorePanel();


    public DiatonicScalePanel() {
        setLayout(new BorderLayout());
        add(inputParamsPanel, BorderLayout.LINE_START);
        JScrollPane scrollPane = new JScrollPane(scorePanel);
        scorePanel.setPreferredSize(new Dimension(960, 300));
        add(scrollPane, BorderLayout.CENTER);
        inputParamsPanel.addParameterChangeListener(this);
    }


    @Override
    public void onDiatonicScaleParametersChanged(DiatonicScaleInputs diatonicScaleInputs) {
        System.out.println("onDiatonicScaleParametersChanged" + diatonicScaleInputs);
        var ds7Scales = new DS7Scales(diatonicScaleInputs.getKey(), diatonicScaleInputs.getMode());
        //TODO: generate the new scale using the algorithm for now we render only the majors
        var notes = ds7Scales.getScaleForKey(diatonicScaleInputs.getKey());
        var octaves = OctaveGenerator.generateFullScaleWithOctaves(diatonicScaleInputs.getOctRange(), notes);
        List<Note> allOctaveNotes = new ArrayList<>();
        for (Octave octave : octaves) {
            allOctaveNotes.addAll(octave.getNotes());
        }

        scorePanel.setNotes(allOctaveNotes);
        for (Octave s : octaves) {
            System.out.println(s);
        }
    }
}
