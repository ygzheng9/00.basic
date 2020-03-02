package pickup.fundMgmt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonggang on 26/11/16.
 */
public class fundTest {
    @Before
    public void setUp() {

    }

    @Test
    public void cleanUp_test() {
        FundReg reg = new FundReg("A001", 345.23);

        reg.offset(45);
        Assert.assertEquals(reg.getBalAmt(), 345.23 - 45, Double.MIN_VALUE);

        FundReg cls = FundUtil.setToZero(reg);
        Assert.assertEquals(cls.getBalAmt(), 0.0, Double.MIN_VALUE);
        Assert.assertEquals("A001", cls.getRefCode());

    }

    @Test
    public void combo_test() {
        List<FundReg> regs = new ArrayList<FundReg>();

        regs.add(new FundReg("A001", 100.5));
        regs.add(new FundReg("A002", 95));
        regs.add(new FundReg("B004", 34.7));

        List<FundReg> cob = FundUtil.combo("Z002", regs);

        Assert.assertEquals(regs.size() + 1, cob.size());

        Assert.assertEquals(100.5 + 95 + 34.7, cob.get(3).getTotalAmt(), Double.MIN_VALUE);

        Assert.assertEquals(cob.get(3).getBalAmt(), cob.get(3).getTotalAmt(), Double.MIN_VALUE);

        Assert.assertEquals("A002", cob.get(1).getRefCode());
    }

}
