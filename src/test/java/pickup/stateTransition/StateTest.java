package pickup.stateTransition;

import static org.hamcrest.core.Is.is;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pickup.priceCalc.DurationUtil;
import pickup.priceCalc.LumpCalcUtil;
import pickup.priceCalc.LumpRateEntry;
import pickup.priceCalc.RentalCalcUtil;

/**
 * Created by YongGang on 2017/1/22.
 */
public class StateTest {

  private List<TransitionEntry> transEntries = new ArrayList<TransitionEntry>();

  @Before
  public void setUp() {
    transEntries.add(new TransitionEntry("DRAFT", "SUBMITTED"));
    transEntries.add(new TransitionEntry("DRAFT", "CANCELLED"));

    transEntries.add(new TransitionEntry("SUBMITTED", "WITHDRAW"));
    transEntries.add(new TransitionEntry("SUBMITTED", "APPROVED"));
    transEntries.add(new TransitionEntry("SUBMITTED", "REJECTED"));

    transEntries.add(new TransitionEntry("REJECTED", "SUBMITTED"));

    transEntries.add(new TransitionEntry("WITHDRAW", "DRAFT"));
    transEntries.add(new TransitionEntry("WITHDRAW", "SUBMITTED"));
  }

  @Test
  public void getPreStates_test() {
    List<String> rtn1 = TransitionUtil.getPreStates(transEntries, "SUBMITTED");
    Assert.assertThat(rtn1, is(Arrays.asList("DRAFT", "REJECTED", "WITHDRAW")));

    List<String> rtn2 = TransitionUtil.getPreStates(transEntries, "APPROVED");
    Assert.assertThat(rtn2, is(Arrays.asList("SUBMITTED")));
  }

  @Test
  public void getPostStates_test() {
    List<String> rtn1 = TransitionUtil.getNextStates(transEntries, "SUBMITTED");
    Assert.assertThat(rtn1, is(Arrays.asList("WITHDRAW", "APPROVED", "REJECTED")));

    List<String> rtn2 = TransitionUtil.getNextStates(transEntries, "APPROVED");
    Assert.assertEquals(0, rtn2.size());
  }

  @Test
  public void isAllowed_test() {
    Boolean rtn = TransitionUtil.isAllowed(transEntries, "DRAFT", "SUBMITTED");
    Assert.assertEquals(true, rtn);

    rtn = TransitionUtil.isAllowed(transEntries, "DRAFT", "APPROVED");
    Assert.assertEquals(false, rtn);

    rtn = TransitionUtil.isAllowed(transEntries, "SUBMITTED", "WITHDRAW");
    Assert.assertEquals(true, rtn);
  }

  @Test
  public void getAllNextStates_test() {
    List<String> from = new ArrayList<>();
    from.add("DRAFT");
    from.add("DRAFT");

    List<String> to = TransitionUtil.getAllNextStates(transEntries, from);
    Assert.assertThat(to, is(Arrays.asList("CANCELLED", "SUBMITTED")));

    from.add("REJECTED");
    from.add("WITHDRAW");
    to = TransitionUtil.getAllNextStates(transEntries, from);
    Assert.assertThat(to, is(Arrays.asList("SUBMITTED")));

    from.add("SUBMITTED");
    to = TransitionUtil.getAllNextStates(transEntries, from);
    Assert.assertEquals(0, to.size());
  }

  @Test
  public void calcByFixedDays_test() {
    LocalDateTime dt = LocalDateTime.of(2017, 1, 23, 10, 20, 30);

    LocalDateTime next = RentalCalcUtil.calcNextEndDTByFixedDay(dt, 15);
    Assert.assertEquals(LocalDateTime.of(2017, 2, 7, 10, 20, 30), next);
  }

  @Test
  public void calcByCycle_test() {
    System.out.println("第一组测试");
    List<Integer> timer = new ArrayList<>();
    timer.add(1);
    timer.add(16);

    LocalDateTime dt2 = LocalDateTime.of(2016, 10, 4, 16, 25, 30);
    LocalDateTime dt = dt2;

    System.out.println(dt2);

    for (int i = 0; i <= 12; i++) {
      dt = RentalCalcUtil.calcNextEndDTByTimer(dt2, timer);
      System.out.println(dt);

      dt2 = dt;
    }

    System.out.println("第二组测试");
    timer = new ArrayList<>();
    timer.add(15);
    timer.add(31);

    dt2 = LocalDateTime.of(2016, 10, 15, 16, 25, 30);
    System.out.println(dt2);

    for (int i = 0; i <= 12; i++) {
      dt = RentalCalcUtil.calcNextEndDTByTimer(dt2, timer);
      System.out.println(dt);

      dt2 = dt;
    }

    System.out.println("第三组测试");
    timer = new ArrayList<>();
    timer.add(31);

    dt2 = LocalDateTime.of(2016, 1, 15, 16, 25, 30);
    System.out.println(dt2);

    for (int i = 0; i <= 20; i++) {
      dt = RentalCalcUtil.calcNextEndDTByTimer(dt2, timer);
      System.out.println(dt);

      dt2 = dt;
    }
  }

  @Test
  public void dayDiff_test() {
    LocalDateTime dt1 = LocalDateTime.of(2017, 1, 20, 16, 25, 30);
    LocalDateTime dt2 = LocalDateTime.now();

    BigDecimal rtn = DurationUtil.of(dt1, dt2).getDayCount();

    System.out.println(rtn);

    LocalDateTime dt3 = dt1.with(TemporalAdjusters.firstDayOfMonth());
    System.out.println(dt3);

    dt3 = dt1.with(TemporalAdjusters.firstDayOfNextMonth());
    System.out.println(dt3);
  }

  @Test
  public void calcFullMonthDiff_test() {
    LocalDate dt1 = LocalDate.of(2016, 5, 10);
    LocalDate dt2 = LocalDate.of(2016, 9, 2);
    int i = DurationUtil.getMonthCount(dt1, dt2);

    Assert.assertEquals(3, i);

    dt1 = LocalDate.of(2016, 5, 10);
    dt2 = LocalDate.of(2016, 6, 1);
    i = DurationUtil.getMonthCount(dt1, dt2);

    Assert.assertEquals(0, i);

    dt1 = LocalDate.of(2016, 12, 1);
    dt2 = LocalDate.of(2017, 1, 1);
    i = DurationUtil.getMonthCount(dt1, dt2);

    Assert.assertEquals(0, i);

    dt1 = LocalDate.of(2016, 12, 1);
    dt2 = LocalDate.of(2017, 2, 1);
    i = DurationUtil.getMonthCount(dt1, dt2);

    Assert.assertEquals(1, i);

    dt1 = LocalDate.of(2016, 10, 1);
    dt2 = LocalDate.of(2017, 2, 1);
    i = DurationUtil.getMonthCount(dt1, dt2);

    Assert.assertEquals(3, i);

    dt1 = LocalDate.of(2016, 10, 20);
    dt2 = LocalDate.of(2017, 10, 5);
    i = DurationUtil.getMonthCount(dt1, dt2);

    Assert.assertEquals(11, i);

    dt1 = LocalDate.of(2016, 10, 5);
    dt2 = LocalDate.of(2016, 10, 25);
    i = DurationUtil.getMonthCount(dt1, dt2);

    Assert.assertEquals(-1, i);

  }

  @Test
  public void calcLumpsum_test() {
    LocalDateTime dt1 = LocalDateTime.of(2016, 1, 15, 16, 0, 0);
    LocalDateTime dt2 = LocalDateTime.now();

    LumpRateEntry term = new LumpRateEntry(LumpRateEntry.BY_PHASE, new BigDecimal(1500.0), new BigDecimal(3000.0));
    BigDecimal amt = LumpCalcUtil.calcLump(dt1, dt2, term, false);
    System.out.println(amt);

    dt1 = LocalDateTime.of(2016, 1, 20, 16, 0, 0);
    dt2 = LocalDateTime.now();
    amt = LumpCalcUtil.calcLump(dt1, dt2, term, false);
    System.out.println(amt);

    dt1 = LocalDateTime.of(2016, 1, 20, 16, 0, 0);
    dt2 = LocalDateTime.of(2016, 1, 30, 16, 0, 0);
    amt = LumpCalcUtil.calcLump(dt1, dt2, term, true);
    System.out.println(amt);

    term = new LumpRateEntry(LumpRateEntry.BY_MONTH, new BigDecimal(3000.0), new BigDecimal(3000.0));
    dt1 = LocalDateTime.of(2016, 1, 20, 16, 0, 0);
    dt2 = LocalDateTime.of(2016, 1, 30, 16, 0, 0);
    amt = LumpCalcUtil.calcLump(dt1, dt2, term, false);
    System.out.println(amt);

    dt1 = LocalDateTime.of(2017, 1, 20, 16, 0, 0);
    dt2 = LocalDateTime.of(2017, 2, 5, 0, 0, 0);
    amt = LumpCalcUtil.calcLump(dt1, dt2, term, false);
    System.out.println(amt);

//    Assert.assertEquals(, );
  }

  @Test
  public void date_test() {
    LocalDateTime lastStopAt = LocalDateTime.of(2017, 1, 20, 19, 0, 0);

    LocalDate d = lastStopAt.toLocalDate().with(TemporalAdjusters.firstDayOfNextMonth());
    System.out.println(d);

    d = d.with(TemporalAdjusters.lastDayOfMonth());
    System.out.println(d);

    d = d.withDayOfMonth(16);
    System.out.println(d);
  }

}
