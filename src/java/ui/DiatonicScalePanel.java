package ui;

import diatonicscale.DS7Scales;
import diatonicscale.DiatonicScaleInputs;
import scale.Note;
import scale.OctaveGenerator;

import javax.swing.*;
import java.awt.*;

public class DiatonicScalePanel extends JPanel implements DiatonicScaleParameterListener {
    private final DiatonicScaleInputsPanel inputParamsPanel = new DiatonicScaleInputsPanel();
    private final ScorePanel scorePanel = new ScorePanel() {
        private int count = 0;

        @Override
        protected void noteAdded(Note note, int linePosition, int columnPosition) {
            if (columnPosition == 0 || count == 4) {
//if(octave.getPosition() == 4 || octave.getPosition() == 5) {
                showInTopBar(columnPosition);
                //          }
                //              if(octave.getPosition() == 2 || octave.getPosition() == 3) {
                showInBottomBar(columnPosition);
                //            }
                count = 0;
            }
           /* if(columnPosition > octave.getNotes().size()) {
                if(octave.getPosition() == 4 || octave.getPosition() == 5) {
                    showInTopBar(columnPosition +1);
                }
                if(octave.getPosition() == 2 || octave.getPosition() == 3) {

                    showInBottomBar(columnPosition +1);
                }
            }*/
            count++;
        }

        private void showInTopBar(int columnPosition) {
            super.showScoreBar(columnPosition, 0, 8);
        }

        private void showInBottomBar(int columnPosition) {
            super.showScoreBar(columnPosition, -12, -4);
        }
    };


    public DiatonicScalePanel() {
        setLayout(new BorderLayout());
        inputParamsPanel.addParameterChangeListener(this);
        add(inputParamsPanel, BorderLayout.LINE_START);
        JScrollPane scrollPane = new JScrollPane(scorePanel);
        scorePanel.setPreferredSize(new Dimension(960, 300));
        add(scrollPane, BorderLayout.CENTER);

    }


    @Override
    public void onDiatonicScaleParametersChanged(DiatonicScaleInputs diatonicScaleInputs) {
        var ds7Scales = new DS7Scales(diatonicScaleInputs.getScale());
        var notes = ds7Scales.findSharpsAndFlats(diatonicScaleInputs.getScale(), diatonicScaleInputs.getMode());
        var octaveNotes = OctaveGenerator.generateFullScaleWithOctaves(diatonicScaleInputs.getOctRange(), notes);
        int noteCout = 0;

        scorePanel.reset();
        for (Note note : octaveNotes) {
            scorePanel.addNote(DS7Scales.getNotePositionRelativeToMiddleC(note), noteCout++, note);
        }
        System.out.println(octaveNotes);

    }
}
