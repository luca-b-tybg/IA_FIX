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
    private MusicScoreComponent musicScoreComponent;
    JScrollPane scrollPane;


    public DiatonicScalePanel() {
        setLayout(new BorderLayout());
        inputParamsPanel.addParameterChangeListener(this);
        add(inputParamsPanel, BorderLayout.LINE_START);

    }

    private void showBar(int columnPosition) {
        musicScoreComponent.addScoreBar(columnPosition, -12, -4);
        musicScoreComponent.addScoreBar(columnPosition, 0, 8);
    }

    private void updateView(java.util.List<Note> notes) {

        if (scrollPane != null) {
            remove(scrollPane);
            scrollPane.removeAll();
            musicScoreComponent = null;
            scrollPane = null;
        }
        musicScoreComponent = new MusicScoreComponent();
        scrollPane = new JScrollPane(musicScoreComponent);

        musicScoreComponent.setPreferredSize(new Dimension(60 * notes.size(), 300));
        int noteCount = 0;
        for (Note note : notes) {
            musicScoreComponent.addNote(DS7Scales.getNotePositionRelativeToMiddleC(note), noteCount, note);
            noteCount++;
        }
        add(scrollPane, BorderLayout.CENTER);
        revalidate();
    }

    @Override
    public void onDiatonicScaleParametersChanged(DiatonicScaleInputs diatonicScaleInputs) {
        var ds7Scales = new DS7Scales(diatonicScaleInputs.getScale());
        var notes = ds7Scales.findSharpsAndFlats(diatonicScaleInputs.getScale(), diatonicScaleInputs.getMode());
        var octaveNotes = OctaveGenerator.generateFullScaleWithOctaves(diatonicScaleInputs.getOctRange(), notes);

        updateView(octaveNotes);
        //render the vertical bars
        for (int i = 0; i <= (octaveNotes.size() / 4) + 1; i++) {
            showBar(i * 4);
        }
    }
}
