package pickup.voucher.config;

import org.junit.Before;
import org.junit.Test;
import pickup.voucher.VouItem;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by yonggang on 3/12/16.
 */
public class VouConfTest {
    @Before
    public void setUp() {

    }

    @Test
    public void cellConf_test() {
        List<VouRuleItemCellProp> confs = VouRuleBuilder.initVouItemCellProp();

        confs.stream().forEach(System.out::println);
    }

    @Test
    public void ruleBuild_test() {
        List<VouRule> rules = VouRuleBuilder.initVouRules();

        rules.stream().forEach(System.out::println);
    }

    @Test
    public void prop_test() {
        assertEquals("Bschl", UtilHelper.onlyKeepInitialCapital("bschl"));

        assertEquals("Bschl", UtilHelper.onlyKeepInitialCapital("BSCHL"));

        assertEquals("getBschl", UtilHelper.getterFunc("BSCHL"));

        assertEquals("setBschl", UtilHelper.setterFunc("BSCHL"));

        assertEquals(false, UtilHelper.isFuncName("BSCHL"));

        assertEquals(true, UtilHelper.isFuncName("@getBschl"));

        assertEquals("getBschl", UtilHelper.getActualFuncName("@getBschl"));

        assertEquals("getTYPE", UtilHelper.getActualFuncName("@getTYPE"));

        assertEquals("java.lang.String", UtilHelper.getFullClassName("String"));
        assertEquals("java.lang.Double", UtilHelper.getFullClassName("Double"));
    }

    @Test
    public void hasValue_test() {
        VouItem itm = new VouItem();
        itm.setType("50");
        itm.setKostl("@getKostl");

        assertEquals("50", UtilHelper.getPropValue(itm, "type"));

        assertEquals("", UtilHelper.getPropValue(itm, "bschl"));
        assertEquals("getKostl", UtilHelper.getActualFuncName(UtilHelper.getPropValue(itm, "kostl")));


    }
}
