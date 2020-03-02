package pickup.voucher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pickup.fundMgmt.FundBalance;
import pickup.fundMgmt.FundReg;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by yonggang on 26/11/16.
 */
public class FundVouTest {
    @Before
    public void setUp() {

    }

    @Test
    public void normalVou_test() {
        FundReg reg = new FundReg("A001", 345.23);
        reg.setType("Normal");
        reg.setRemark("BNK20161203002");

        List<VouItem> items = VouGeneratorUtil.run(reg);

        Assert.assertEquals(2, items.size());
//        Assert.assertEquals("50", items.get(1).getType());
//        Assert.assertEquals("AR-TEMP", items.get(1).getAccount());
//        Assert.assertEquals(reg.getTotalAmt(), items.get(1).getOrigAmt(), Double.MIN_VALUE);

//        Assert.assertTrue(items.get(0).isSame(new VouItem("40", "BANK", reg.getTotalAmt())));

        items.stream().forEach(System.out::println);
    }

    @Test
    public void reflect_tst() {
        try {
            VouItem itm = new VouItem();
            Method setter = VouItem.class.getMethod("setKostl", Class.forName("java.lang.String"));

            setter.invoke(itm, "226811001");

            Assert.assertEquals("226811001", itm.getKostl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void method_test() {
        try {
            FundVouGeneratorAdvanced a = new FundVouGeneratorAdvanced();

            Method md = FundVouGeneratorAdvanced.class.getMethod("getNewko");
            Assert.assertEquals("CUST-SAPID", md.invoke(a));

        } catch (NoSuchMethodException e) {
            System.out.println("method can not find");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("can not access method.");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("target is wrong. ");
            e.printStackTrace();
        }
    }


    @Test
    public void advancedVou_test() {
        FundReg reg = new FundReg("A002", 23.45);
        reg.setType("Advanced");

        List<VouItem> items = VouGeneratorUtil.run(reg);

        Assert.assertEquals(2, items.size());
//        Assert.assertEquals("19", items.get(1).getType());
//        Assert.assertEquals("AR CUST(A)", items.get(1).getAccount());
//        Assert.assertEquals(reg.getTotalAmt(), items.get(1).getOrigAmt(), Double.MIN_VALUE);
//
//        Assert.assertTrue(items.get(0).isSame(new VouItem("40", "BANK", reg.getTotalAmt())));

        items.stream().forEach(System.out::println);
    }

    @Test
    public void relatedVou_test() {
        FundReg reg = new FundReg("A003", 123.45);
        reg.setType("Related");

        List<VouItem> items = VouGeneratorUtil.run(reg);

        Assert.assertEquals(2, items.size());
//        Assert.assertEquals("09", items.get(0).getType());
//        Assert.assertEquals("AR CUST(O)", items.get(0).getAccount());
//        Assert.assertEquals(reg.getTotalAmt(), items.get(0).getOrigAmt(), Double.MIN_VALUE);
//
//        Assert.assertTrue(items.get(1).isSame(new VouItem("50", "AR TEMP", reg.getTotalAmt())));

        items.stream().forEach(System.out::println);
    }

    @Test
    public void regVou_test() {
        FundReg reg1 = new FundReg("A001", 345.23);
        reg1.setType("Normal");

        FundReg reg2 = new FundReg("A002", 23.45);
        reg2.setType("Advanced");

        FundReg reg3 = new FundReg("A003", 123.45);
        reg3.setType("Related");

        FundReg reg4 = new FundReg("A004", 34.23);
        reg4.setType("Normal");

        List<FundReg> regs = new ArrayList<FundReg>();
        regs.add(reg1);
        regs.add(reg2);
        regs.add(reg3);
        regs.add(reg4);

        List<VouItem> allItems = regs.stream()
                .map(VouGeneratorUtil::run)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Assert.assertEquals(2 * 4, allItems.size());

        allItems.stream().forEach(System.out::println);
    }

    @Test
    public void balVou_test() {
        FundReg reg1 = new FundReg("A002", 23.45);
        reg1.setType("Advanced");
        reg1.offset(10.15);

        FundReg reg2 = new FundReg("A002", 23.45);
        reg2.setType("Advanced");

        FundReg reg3 = new FundReg("A003", 10.12);
        reg3.setType("Balance");

        FundReg reg4 = new FundReg("A004", 5.25);
        reg4.setType("Normal");

        List<FundReg> openRegs = new ArrayList<FundReg>();
        openRegs.add(reg1);
        openRegs.add(reg2);
        openRegs.add(reg3);
        openRegs.add(reg4);

        FundBalance fundBal = new FundBalance();
        fundBal.build("BAL_201611", openRegs);

        List<VouItem> allItems = FundVouBalFactory.generate(fundBal);

        Assert.assertEquals(4 + 1, allItems.size());

        allItems.stream().forEach(System.out::println);


        allItems = VouGeneratorUtil.run(fundBal);

        Assert.assertEquals(4 + 1, allItems.size());

        allItems.stream().forEach(System.out::println);
    }

    @Test
    public void mixedVou_test() {
        FundReg reg1 = new FundReg("A001", 23.45);
        reg1.setType("Advanced");

        FundReg reg2 = new FundReg("A002", 45.89);
        reg2.setType("Advanced");

        FundReg reg4 = new FundReg("A004", 5.25);
        reg4.setType("Normal");

        FundReg reg5 = new FundReg("A003", 123.45);
        reg5.setType("Related");

        FundReg reg6 = new FundReg("A004", 34.23);
        reg6.setType("Normal");

        List<FundReg> regs = new ArrayList<FundReg>();
        regs.add(reg1);
        regs.add(reg2);
        regs.add(reg4);
        regs.add(reg5);
        regs.add(reg6);

        // 资金登记的业务
        List<VouPayload> payloads = regs.stream()
                                        .map(Function.identity())
                                        .collect(Collectors.toList());

        Assert.assertEquals(payloads.size(), regs.size());

        // 上期的 balance
        FundReg reg3 = new FundReg("A003", 10.12);
        reg3.setType("Balance");
        regs.add(reg3);

        Assert.assertEquals(payloads.size() + 1, regs.size());

        // 本期 预付款结转的业务
        FundBalance fundBal = new FundBalance();
        fundBal.build("BAL_201611", regs);
        payloads.add(fundBal);

        // 所有业务: 资金登记业务 + 预付款结转业务
        List<VouItem> allItems = payloads.stream()
                                    .map(VouGeneratorUtil::run)
                                    .flatMap(List::stream)
                                    .collect(Collectors.toList());

        Assert.assertEquals(5 * 2 + 6 + 1, allItems.size());

        allItems.stream().forEach(System.out::println);
    }

    @Test
    public void nullVou_test() {
        FundReg reg1 = new FundReg("A001", 23.45);
        reg1.setType("Balance");

        FundReg reg2 = new FundReg("A002", 45.45);
        reg2.setType("Balance");

        List<FundReg> regs = new ArrayList<FundReg>();
        regs.add(reg1);
        regs.add(reg2);

        List<VouItem> allItems =  regs.stream()
                .map(VouGeneratorUtil::run)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Assert.assertEquals(0, allItems.size());

    }
}
