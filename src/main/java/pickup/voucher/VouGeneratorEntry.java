package pickup.voucher;

/**
 * Created by yonggang on 26/11/16.
 */
public class VouGeneratorEntry {
    private String key1;
    private String key2;
    private VouGenerator func;

    public boolean isMatched(String k1, String k2) {
        return ( this.getKey1().equalsIgnoreCase(k1) && this.getKey2().equalsIgnoreCase(k2) );
    }

    public VouGeneratorEntry(String key1, String key2, VouGenerator func) {
        this.key1 = key1;
        this.key2 = key2;
        this.func = func;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public VouGenerator getFunc() {
        return func;
    }

    public void setFunc(VouGenerator func) {
        this.func = func;
    }
}
