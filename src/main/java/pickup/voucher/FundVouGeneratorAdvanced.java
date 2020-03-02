package pickup.voucher;

import pickup.fundMgmt.FundReg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonggang on 26/11/16.
 */
public class FundVouGeneratorAdvanced extends VouGeneratorAbstract implements VouGenerator {

    private FundReg bizData;

    @Override
    public List<VouItem> run(Object param) {
        this.bizData = (FundReg) param;

        return this.newRun();
    }

    public List<VouItem> oldRun() {
        VouItem item_dr = new VouItem("40", "BANK", bizData.getTotalAmt());
        VouItem item_cr = new VouItem("19", "AR CUST(A)", bizData.getTotalAmt());

        List<VouItem> item_list = new ArrayList<>();
        item_list.add(item_dr);
        item_list.add(item_cr);

        return item_list;
    }

    private List<VouItem> newRun() {
        List<VouItem> item_list = new ArrayList<>();

        try {
            item_list = this.pumpVouItems("BANK-IN-ADVANCED");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return item_list;
    }

    public String getBankAcct() {
        return "BANK";
    }

    public String getNewko() {
        return "CUST-SAPID";
    }

    public double getWrbtr() {
        return bizData.getTotalAmt();
    }

    public double getDmbtr() {
        return bizData.getTotalAmt() * 6.4;
    }

    public String getZuonr() {
        return bizData.getRemark();
    }

    public String getSgtxt() {
        return bizData.getRemark() + " --- remark.";
    }
}
