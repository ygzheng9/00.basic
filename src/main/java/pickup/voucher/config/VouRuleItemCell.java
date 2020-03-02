package pickup.voucher.config;

/**
 * Created by yonggang on 2/12/16.
 */

@Deprecated
public class VouRuleItemCell {
    private String propName;
    private String getterFunc;

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getGetterFunc() {
        return getterFunc;
    }

    public void setGetterFunc(String getterFunc) {
        this.getterFunc = getterFunc;
    }

    public VouRuleItemCell(String propName, String getterFunc) {

        this.propName = propName;
        this.getterFunc = getterFunc;
    }

    @Override
    public String toString() {
        return "VouRuleItemCell{" +
                "propName='" + propName + '\'' +
                ", getterFunc='" + getterFunc + '\'' +
                '}';
    }
}
