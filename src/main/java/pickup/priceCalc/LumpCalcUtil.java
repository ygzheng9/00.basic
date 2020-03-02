package pickup.priceCalc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by YongGang on 2017/1/23.
 */

// 计算 lumpsum：
//   根据开始时间，截止时间，计算 lumpsum 金额；
//   两种模式：每月多少钱，每期多少钱；
//   其中，每期多少钱的，重算和不重算，算法不一样；

public class LumpCalcUtil {

  public static BigDecimal calcLump(LocalDateTime from, LocalDateTime to, LumpRateEntry term,
      Boolean isRecalc) {
    DurationUtil duration = DurationUtil.of(from, to);

    if (duration.getSign() == 0) {
      return BigDecimal.ZERO;
    }

    BigDecimal rtn = BigDecimal.ZERO;

    if (term.getQuotationType().equalsIgnoreCase(LumpRateEntry.BY_MONTH)) {
      // 按月计算，每月 3000：也即：每天 = 3000 / 当月日历天数；
      rtn = LumpCalcUtil.calcByMonth(duration.getFrom(), duration.getTo(), term);
    } else if (term.getQuotationType().equalsIgnoreCase(LumpRateEntry.BY_PHASE)) {
      // 分段计算，需要区分：重算；
      if (isRecalc) {
        // 重算
        rtn = LumpCalcUtil.reCalcByPhase(duration.getFrom(), duration.getTo(), term);
      } else {
        // 分段且不重算，也即：一整段的费用
        rtn = term.getPrice();
      }
    }

    return rtn.multiply(new BigDecimal(duration.getSign()))
              .setScale(2, BigDecimal.ROUND_UP);
  }

  // 每月固定金额，无论天数多少
  // 无论是同一个月，还是不同月，都可以用这个算法；
  private static BigDecimal calcByMonth(LocalDateTime dt1, LocalDateTime dt2, LumpRateEntry term) {
    // 先计算相隔的完整月份的个数
    BigDecimal fullMonth = term.getPrice()
                               .multiply(new BigDecimal(DurationUtil.of(dt1, dt2).getMonthCount()))
                               .setScale(2, BigDecimal.ROUND_UP);

    // 再计算两头的天数；
    // 起始段的日费率
    BigDecimal dailyRate = term.getPrice()
                               .divide(new BigDecimal(dt1
                                   .toLocalDate().lengthOfMonth()), 2, BigDecimal.ROUND_UP);

    // 起始段的月末零点
    LocalDateTime brk1 = dt1.toLocalDate()
                            .with(TemporalAdjusters.firstDayOfNextMonth())
                            .atTime(0, 0, 0);
    // 日费率 * (起始点 到 月末零点)的天数
    BigDecimal first = dailyRate.multiply(DurationUtil.of(dt1, brk1).getDayCount());

    // 终止段日费率
    dailyRate = term.getPrice()
                    .divide(new BigDecimal(dt2
                        .toLocalDate().lengthOfMonth()), 2, BigDecimal.ROUND_UP);

    // 终止段的月初零点
    LocalDateTime brk2 = dt2.toLocalDate()
                            .with(TemporalAdjusters.firstDayOfMonth())
                            .atTime(0, 0, 0);

    BigDecimal second = dailyRate.multiply(DurationUtil.of(brk2, dt2).getDayCount());

    // 合并起来，就是最后的结果
    return first.add(fullMonth).add(second);
  }

  // 按期次计算的 lumpsum，重算时，日费率 = 月费率 * 12 / 当前日历天数
  public static BigDecimal reCalcByPhase(LocalDateTime dt1, LocalDateTime dt2, LumpRateEntry term) {
    BigDecimal dailyRate = term.getMonthPrice()
                               .multiply(new BigDecimal(12.0))
                               .divide(new BigDecimal(dt1.toLocalDate().lengthOfYear()), 2,
                                   BigDecimal.ROUND_UP);

    return dailyRate.multiply(DurationUtil.of(dt1, dt2).getDayCount());
  }
}
