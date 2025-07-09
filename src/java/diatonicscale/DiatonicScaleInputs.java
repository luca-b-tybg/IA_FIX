package diatonicscale;

import scale.KeyFile;
import scale.Mode;
import scale.OctaveRange;

public class DiatonicScaleInputs {
    private final OctaveRange octRange;
    private final KeyFile key;
    private final Mode mode;

    public DiatonicScaleInputs(OctaveRange octRange, KeyFile key, Mode mode) {
        if (octRange.octaveStart < 2 || octRange.octaveStart > 5) {
            throw new RuntimeException("Starting octave must be between 2 and 5.");
        }
        if( octRange.octaveEnd < 3 || octRange.octaveEnd > 6) {
            throw new RuntimeException("Ending octave must be between 3 and 6.");
        }
        this.octRange = octRange;
        this.key = key;
        this.mode = mode;
    }

    public OctaveRange getOctRange() {return octRange;}
    public KeyFile getKey() {return key;}
    public Mode getMode() {return mode;}

    @Override
    public String toString() {
        return "DiatonicScaleInputs{" +
                "octRange=" + octRange +
                ", key=" + key +
                ", mode=" + mode +
                '}';
    }
}