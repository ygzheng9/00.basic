package pickup.priceCalc;

import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonggang on 26/11/16.
 */
public class PriceTest {

  @Before
  public void setUp() {

  }

  private List<PriceEntry> createPrices() {
    // 初始化 priceTable ;
    // 20 RF 100    -->  10 以内 100, 20 以内 110, 超过 120;
    // 20 * 70
    // 40 HQ 120
    // 40 RF 130
    // 45 * 90       -->  5 以内 200, 20 以内 300, 超过 400;

    // 顺序无关
    PriceEntry line1 = new PriceEntry("20", "RF", 100);
    line1.clearSegments();
    line1.addSegment(new PriceEntrySegment(20, 110));
    line1.addSegment(new PriceEntrySegment(10, 100));
    line1.addSegment(new PriceEntrySegment(120));

    PriceEntry line2 = new PriceEntry("20", "*", 70);
    PriceEntry line3 = new PriceEntry("40", "HQ", 120);
    PriceEntry line4 = new PriceEntry("40", "RF", 140);

    PriceEntry line5 = new PriceEntry("45", "*", 190);
    line5.clearSegments();
    line5.addSegment(new PriceEntrySegment(400));
    line5.addSegment(new PriceEntrySegment(5, 200));
    line5.addSegment(new PriceEntrySegment(20, 300));

    List<PriceEntry> priceTable = new ArrayList<>();

    priceTable.add(line1);
    priceTable.add(line2);
    priceTable.add(line3);
    priceTable.add(line4);
    priceTable.add(line5);

    return priceTable;
  }

  @Test
  public void findPrice_1() {
    List<PriceEntry> priceTable = createPrices();

    PriceEntry price = PriceEntryUtil.findPrice(priceTable, "20", "RF");
    Assert.assertTrue(null != price);
    Assert.assertEquals("20", price.getKey1());
    Assert.assertEquals("RF", price.getKey2());
  }

  @Test
  public void findPrice_2() {
    List<PriceEntry> priceTable = createPrices();

    PriceEntry price = PriceEntryUtil.findPrice(priceTable, "20", "NONE");
    Assert.assertTrue(null != price);
    Assert.assertEquals("20", price.getKey1());
    Assert.assertEquals("*", price.getKey2());
  }

  @Test
  public void findPrice_3() {
    List<PriceEntry> priceTable = createPrices();

    PriceEntry price = PriceEntryUtil.findPrice(priceTable, "40", "HQ");
    Assert.assertTrue(null != price);
    Assert.assertEquals("40", price.getKey1());
    Assert.assertEquals("HQ", price.getKey2());
  }

  @Test
  public void findPrice_4() {
    List<PriceEntry> priceTable = createPrices();

    PriceEntry price = PriceEntryUtil.findPrice(priceTable, "40", "GP");
    Assert.assertTrue(null == price);
  }

  @Test
  public void findPrice_5() {
    List<PriceEntry> priceTable = createPrices();

    PriceEntry price = PriceEntryUtil.findPrice(priceTable, "45", "GP");
    Assert.assertTrue(null != price);
    Assert.assertEquals("45", price.getKey1());
    Assert.assertEquals("*", price.getKey2());
  }

  @Test
  public void checkValid() {
    List<PriceEntry> priceTable = createPrices();

    List<String> msgs = PriceEntryUtil.findDup(priceTable);
    Assert.assertEquals(0, msgs.size());

    priceTable.add(new PriceEntry("20", "RF", 100));
    priceTable.add(new PriceEntry("*", "*", 100));

    msgs = PriceEntryUtil.findDup(priceTable);

    Assert.assertEquals(1, msgs.size());

    Assert.assertTrue(0 <= msgs.indexOf("20-RF-*"));
    Assert.assertEquals(-1, msgs.indexOf("*-*-*"));
  }

  @Test
  public void checkCalc() {
    // 初始化费率表
    List<PriceEntry> priceTable = createPrices();

    // 根据交易数据中的 keys, 取得对应的费率
    PriceEntry price = PriceEntryUtil.findPrice(priceTable, "20", "RF");

    Assert.assertTrue(null != price);

    // 计算费用总金额 = 取得的费率 * 交易数据中的数量
    Assert.assertEquals(500, price.calc(5).intValue());

    Assert.assertEquals(100 * 10 + 2 * 110, price.calc(12).intValue());

    Assert.assertEquals(100 * 10 + 110 * 10 + 120 * 3, price.calc(23).intValue());

    price = PriceEntryUtil.findPrice(priceTable, "45", "RF");
    Assert.assertTrue(null != price);
    Assert.assertEquals(200 * 2, price.calcAmt(2).intValue());

    Assert.assertEquals(200 * 5 + 300 * 11, price.calcAmt(16).intValue());

    Assert.assertEquals(200 * 5 + 300 * 15 + 400 * 3, price.calcAmt(23).intValue());
  }

  @Test
  public void rentalCal_test() {
    RentalCalcUtil util = new RentalCalcUtil();
    List<RentalResult> results = util.calc(util.initContractList(), util.initCalcPeriod());

    results.forEach(System.out::println);
  }

  @Test
  public void getLatestIns_test() {
    RentalCalcUtil util = new RentalCalcUtil();

    Map<String, Optional<Installment>> results = util.getLastConfirmedIns(util.initInstallment());

    results.entrySet().forEach((entry) -> {
      String contractNo = entry.getKey();
      String ins = "NONE";

      if (entry.getValue().isPresent()) ins = entry.getValue().get().toString();

      System.out.println(contractNo + ": " + ins);
    });
  }
}
