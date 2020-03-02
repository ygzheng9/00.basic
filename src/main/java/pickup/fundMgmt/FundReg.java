package pickup.fundMgmt;

import pickup.voucher.VouPayload;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yonggang on 26/11/16.
 */
public class FundReg implements VouPayload {
    private Long id;
    private String refCode;
    private String type;

    private double totalAmt;
    private double balAmt;
    private String remark;

    @Override
    public String toString() {
        return "FundReg{" +
                "id=" + id +
                ", refCode='" + refCode + '\'' +
                ", type='" + type + '\'' +
                ", totalAmt=" + totalAmt +
                ", balAmt=" + balAmt +
                ", remark='" + remark + '\'' +
                '}';
    }
//    public enumConstants FundTypeEnum {
//        NORMAL("NORMAL");
//        NORMAL("ADVANCED"),
//        "RELATED"
//    }

    @Override
    public String getVouKey(String type) {
        Map<String, String> payload = new HashMap<>();
        payload.put(VouPayload.KEY1, FundReg.class.getSimpleName());
        payload.put(VouPayload.KEY2, this.getType());

        return payload.get(type);
    }

    public FundReg(String refCode, double totalAmt) {
        this.id = 0L;
        this.refCode = refCode;
        this.totalAmt = totalAmt;
        this.balAmt = totalAmt;

        this.type = "NORMAL";
    }

    public void offset(double amt) {
        this.balAmt = this.balAmt - amt;
    }

//    public void setToZero() {
//        this.offset(this.balAmt);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public double getBalAmt() {
        return balAmt;
    }

    public void setBalAmt(double balAmt) {
        this.balAmt = balAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
