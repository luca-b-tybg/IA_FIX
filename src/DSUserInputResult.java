public class DSUserInputResult {
    private final int[] octRange;
    private final KeyFile.Key key;
    private final ModeFile.Mode mode;

    public DSUserInputResult(int[] octRange,KeyFile.Key key, ModeFile.Mode mode) {
        this.octRange = octRange;
        this.key = key;
        this.mode = mode;
    }

    public int[] getOctRange() {return octRange;}
    public KeyFile.Key getKey() {return key;}
    public ModeFile.Mode getMode() {return mode;}

}