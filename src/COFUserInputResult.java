public class COFUserInputResult {
    private final KeyFile.Key key;
    private final COFMMFile.COFMM COFMM;

    public COFUserInputResult(KeyFile.Key key, COFMMFile.COFMM COFMM) {
        this.key = key;
        this.COFMM = COFMM;
    }
    public KeyFile.Key getKey() {return key;}
    public COFMMFile.COFMM getCOFMM() {return COFMM;}
}