package ui;

import diatonicscale.DS7Scales;
import diatonicscale.DiatonicScaleInputs;
import scale.Note;
import scale.OctaveGenerator;
import ui.components.MusicScoreComponent;

import javax.swing.*;
import java.awt.*;

public class DiatonicScalePanel extends JPanel implements DiatonicScaleParameterListener {
    private final DiatonicScaleInputsComponent inputParamsPanel = new DiatonicScaleInputsComponent();
    private final MusicScoreComponent musicScoreComponent = new MusicScoreComponent();


    public DiatonicScalePanel() {
        setLayout(new BorderLayout());
        inputParamsPanel.addParameterChangeListener(this);
        add(inputParamsPanel, BorderLayout.LINE_START);
        JScrollPane scrollPane = new JScrollPane(musicScoreComponent);
        musicScoreComponent.setPreferredSize(new Dimension(960, 300));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void showBar(int columnPosition) {
        musicScoreComponent.addScoreBar(columnPosition, -12, -4);
        musicScoreComponent.addScoreBar(columnPosition, 0, 8);
    }

    @Override
    public void onDiatonicScaleParametersChanged(DiatonicScaleInputs diatonicScaleInputs) {
        var ds7Scales = new DS7Scales(diatonicScaleInputs.getScale());
        var notes = ds7Scales.findSharpsAndFlats(diatonicScaleInputs.getScale(), diatonicScaleInputs.getMode());
        var octaveNotes = OctaveGenerator.generateFullScaleWithOctaves(diatonicScaleInputs.getOctRange(), notes);
        int noteCount = 0;
        musicScoreComponent.reset();

        for (Note note : octaveNotes) {
            musicScoreComponent.addNote(DS7Scales.getNotePositionRelativeToMiddleC(note), noteCount, note);
            noteCount++;
        }
        //render the vertical bars
        for (int i = 0; i <= (octaveNotes.size() / 4) + 1; i++) {
            showBar(i * 4);
        }
    }
}
