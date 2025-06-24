public class COFUserInputResult {
    private final COFKeyFile.Key key;
    private final COFMMFile.COFMM COFMM;

    public COFUserInputResult(COFKeyFile.Key key, COFMMFile.COFMM COFMM) {
        this.key = key;
        this.COFMM = COFMM;
    }
    public COFKeyFile.Key getKey() {return key;}
    public COFMMFile.COFMM getCOFMM() {return COFMM;}
}