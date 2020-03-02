package pickup.fundMgmt;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yonggang on 26/11/16.
 */
public class FundUtil {

    public static FundReg setToZero(final FundReg fund) {
        FundReg a = fund;
        a.offset(a.getBalAmt());
        return a;
    }

    // 把传入的余额，全部清掉，并结转到一个新的记录上，
    // 新记录，和传入后被清掉余额的记录，作为一个新 list，返回；
    public static List<FundReg> combo(final String refCode, final List<FundReg> inFunds) {
        // 待结转条目的余额总额
        double total = inFunds.stream()
                .map(FundReg::getBalAmt)
                .reduce(0.0, Double::sum);

        // 待结转条目,全部清空
        List<FundReg> clr = inFunds.stream().map(FundUtil::setToZero)
                .collect(Collectors.toList());

        // 结转后新生的条目
        clr.add(new FundReg(refCode, total));

        return clr;
    }
}
