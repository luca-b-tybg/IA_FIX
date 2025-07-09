package ui;

import diatonicscale.DiatonicScaleInputs;
import scale.GenerateFullScaleWithOctaves;
import scale.KeyFile;
import scale.Note;
import scale.Octave;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DiatonicScalePanel extends JPanel implements DiatonicScaleParameterListener {
    private final DiatonicScaleInputsPanel inputParamsPanel = new DiatonicScaleInputsPanel();
    private final ScorePanel scorePanel = new ScorePanel();
    GenerateFullScaleWithOctaves octaveTest = new GenerateFullScaleWithOctaves();

    public DiatonicScalePanel() {
        setLayout(new BorderLayout());
        add(inputParamsPanel, BorderLayout.LINE_START);
        add(scorePanel, BorderLayout.CENTER);
        inputParamsPanel.addParameterChangeListener(this);
    }


    @Override
    public void onDiatonicScaleParametersChanged(DiatonicScaleInputs diatonicScaleInputs) {
        System.out.println("onDiatonicScaleParametersChanged" + diatonicScaleInputs);
        var octaves = octaveTest.fullFinalScale(diatonicScaleInputs.getOctRange(), diatonicScaleInputs.getKey(), diatonicScaleInputs.getMode());
        scorePanel.setNotes(List.of(new Note(KeyFile.C), new Note(KeyFile.D),
                new Note(KeyFile.E)
                , new Note(KeyFile.F)));

        for (Octave s : octaves) {
            System.out.println(s);
        }
    }
}
