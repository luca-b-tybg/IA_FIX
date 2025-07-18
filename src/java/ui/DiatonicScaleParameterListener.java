package ui;

import diatonicscale.DiatonicScaleInputs;

/**
 * Interface to define an event listener to handle Diatonic scale changes
 */
public interface DiatonicScaleParameterListener {
     void onDiatonicScaleParametersChanged(DiatonicScaleInputs diatonicScaleInputs);
}
