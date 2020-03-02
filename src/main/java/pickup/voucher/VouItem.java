package pickup.voucher;

/**
 * Created by yonggang on 26/11/16.
 */
public class VouItem {
    private String type;        // AR/AP/GL/GL_COPA
    private String bschl;       // 40/50/01/11/21/31/09/19/29/39/07/17
    private String umskz;       // O/A/W
    private String xnegp;       // blank/X

    private String newko;       // GL account

    private Double wrbtr;       // original amount
    private Double dmbtr;       // local amount

    private String zuonr;
    private String sgtxt;

    private String xref1;
    private String xref2;
    private String xref3;

    private String prctr;       // profit center
    private String kostl;       // cost center

    private String account;
    private Double origAmt;

    public VouItem() {
    }

    public String getBschl() {
        return bschl;
    }

    public void setBschl(String bschl) {
        this.bschl = bschl;
    }

    public String getUmskz() {
        return umskz;
    }

    public void setUmskz(String umskz) {
        this.umskz = umskz;
    }

    public String getXnegp() {
        return xnegp;
    }

    public void setXnegp(String xnegp) {
        this.xnegp = xnegp;
    }

    public String getNewko() {
        return newko;
    }

    public void setNewko(String newko) {
        this.newko = newko;
    }

    public Double getWrbtr() {
        return wrbtr;
    }

    public void setWrbtr(Double wrbtr) {
        this.wrbtr = wrbtr;
    }

    public Double getDmbtr() {
        return dmbtr;
    }

    public void setDmbtr(Double dmbtr) {
        this.dmbtr = dmbtr;
    }

    public String getZuonr() {
        return zuonr;
    }

    public void setZuonr(String zuonr) {
        this.zuonr = zuonr;
    }

    public String getSgtxt() {
        return sgtxt;
    }

    public void setSgtxt(String sgtxt) {
        this.sgtxt = sgtxt;
    }

    public String getXref1() {
        return xref1;
    }

    public void setXref1(String xref1) {
        this.xref1 = xref1;
    }

    public String getXref2() {
        return xref2;
    }

    public void setXref2(String xref2) {
        this.xref2 = xref2;
    }

    public String getXref3() {
        return xref3;
    }

    public void setXref3(String xref3) {
        this.xref3 = xref3;
    }

    public String getPrctr() {
        return prctr;
    }

    public void setPrctr(String prctr) {
        this.prctr = prctr;
    }

    public String getKostl() {
        return kostl;
    }

    public void setKostl(String kostl) {
        this.kostl = kostl;
    }

    public boolean isSame(VouItem item) {
        boolean rtn1 = this.getType().equalsIgnoreCase(item.getType()) &&
                        this.getAccount().equalsIgnoreCase(item.getAccount());

        if (rtn1) {
            return (0 == Double.compare(this.getOrigAmt(), item.getOrigAmt()));
        }

        return false;
    }

    public VouItem(String type, String account, Double origAmt) {
        this.type = type;
        this.account = account;
        this.origAmt = origAmt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getOrigAmt() {
        return origAmt;
    }

    public void setOrigAmt(Double origAmt) {
        this.origAmt = origAmt;
    }

    @Override
    public String toString() {
        return "VouItem{" +
                "type='" + type + '\'' +
                ", bschl='" + bschl + '\'' +
                ", umskz='" + umskz + '\'' +
                ", xnegp='" + xnegp + '\'' +
                ", newko='" + newko + '\'' +
                ", wrbtr=" + wrbtr +
                ", dmbtr=" + dmbtr +
                ", zuonr='" + zuonr + '\'' +
                ", sgtxt='" + sgtxt + '\'' +
                ", xref1='" + xref1 + '\'' +
                ", xref2='" + xref2 + '\'' +
                ", xref3='" + xref3 + '\'' +
                ", prctr='" + prctr + '\'' +
                ", kostl='" + kostl + '\'' +
                ", account='" + account + '\'' +
                ", origAmt=" + origAmt +
                '}';
    }
}
