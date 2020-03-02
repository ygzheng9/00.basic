package pickup.voucher;

import pickup.fundMgmt.FundBalance;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yonggang on 27/11/16.
 */
public class FundVouGeneratorBal implements VouGenerator {
    @Override
    public List<VouItem> run(Object bizData) {
        FundBalance fundBal = (FundBalance) bizData;

        List<VouItem> item_list = fundBal.getOpenRegs().stream()
                .map(reg -> {
                    String t = "NA";
                    String gl = "NA";

                    switch (reg.getType().toUpperCase()) {
                        case "ADVANCED":
                        case "BALANCE":
                            t = "09";
                            gl = "AR CUST(A)";
                            break;
                        case "NORMAL":
                        case "RELATED":
                            t = "40";
                            gl = "AR TEMP";
                            break;
                    }
                    return (new VouItem(t, gl, reg.getTotalAmt()));})
                .collect(Collectors.toList());

        VouItem item_cr = new VouItem("19", "AR CUST(A)", fundBal.getBalReg().getTotalAmt());

        item_list.add(item_cr);

        return item_list;
    }
}
