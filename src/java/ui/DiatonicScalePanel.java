package ui;

import diatonicscale.DS7Scales;
import diatonicscale.DiatonicScaleInputs;
import scale.OctaveGenerator;
import scale.Octave;

import javax.swing.*;
import java.awt.*;

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
        var ds7Scales = new DS7Scales(diatonicScaleInputs.getScale());
        var notes = ds7Scales.findSharpsAndFlats(diatonicScaleInputs.getScale(), diatonicScaleInputs.getMode());
        var octaves = OctaveGenerator.generateFullScaleWithOctaves(diatonicScaleInputs.getOctRange(), notes);


        scorePanel.setOctaves(octaves);
        for (Octave s : octaves) {
            System.out.println(s);
        }
    }
}
