package ui;

import diatonicscale.DS7Note;
import diatonicscale.DiatonicScaleInputs;
import scale.*;

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
        DS7Note x = new DS7Note(KeyFile.C, Mode.IONIAN);
        scorePanel.setNotes(x.getScaleForKey(diatonicScaleInputs.getKey()));
        //octaveTest.generateFullScaleWithOctavesOld(diatonicScaleInputs.getOctRange(), diatonicScaleInputs.getKey(), diatonicScaleInputs.getMode())
        /*for (Octave s : octaves) {
            System.out.println(s);
        }*/
    }
}
