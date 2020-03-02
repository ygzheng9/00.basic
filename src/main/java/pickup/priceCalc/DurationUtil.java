package pickup.priceCalc;

import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by YongGang on 2017/1/24.
 */

// 期次的开始时间，结束时间；
// 如果结束时间，在开始时间之前，sign = -1；
// 只允许通过 of 创建，而且一旦创建后，就不可修改；
public class DurationUtil {
  private LocalDateTime from;
  private LocalDateTime to;
  private int sign;

  // 字符串和日期转换的格式
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  // 一天的秒数
  private static long secondsOfDay = 60 * 60 * 24;

  private DurationUtil() {
  }

  public LocalDateTime getFrom() {
    return from;
  }

  private void setFrom(LocalDateTime from) {
    this.from = from;
  }

  public LocalDateTime getTo() {
    return to;
  }

  private void setTo(LocalDateTime to) {
    this.to = to;
  }

  public int getSign() {
    return sign;
  }

  private void setSign(int sign) {
    this.sign = sign;
  }

  public static DurationUtil of(String from, String to) {
    LocalDateTime dt1 = LocalDateTime.parse(from, DurationUtil.formatter);
    LocalDateTime dt2 = LocalDateTime.parse(to, DurationUtil.formatter);

    return DurationUtil.of(dt1, dt2);
  }

  public static DurationUtil of(LocalDateTime from, LocalDateTime to) {
    LocalDateTime dt1 = from;
    LocalDateTime dt2 = to;

    int cmp = to.compareTo(from);

    // 如果 from 比 to 大，互换，然后计算的结果需要再乘以 -1；
    if (cmp < 0) {
      cmp = -1;
      dt1 = to;
      dt2 = from;
    } else if (cmp > 0) {
      cmp = 1;
    } else {
      cmp = 0;
    }

    DurationUtil leg = new DurationUtil();
    leg.setFrom(dt1);
    leg.setTo(dt2);
    leg.setSign(cmp);

    return leg;
  }

  // 计算这两段期间的重叠部分
  public static Optional<DurationUtil> intersect(final DurationUtil p1,
      final DurationUtil p2) {
    Preconditions
        .checkArgument(p1.getFrom().compareTo(p1.getTo()) <= 0, "p1: start must early than end.");
    Preconditions
        .checkArgument(p2.getFrom().compareTo(p2.getTo()) <= 0, "p2: start must early than end.");

    // 如果没有重叠，返回 Optional<null>
    if (p1.getFrom().compareTo(p2.getTo()) >= 0 ||
        p1.getTo().compareTo(p2.getFrom()) <= 0) {
      return Optional.empty();
    }

    // 如果有重叠
    // 重叠部分的开始时间，取开始时间中大的；
    LocalDateTime dt1 = p1.getFrom();
    if (p1.getFrom().compareTo(p2.getFrom()) < 0) {
      dt1 = p2.getFrom();
    }

    // 重叠部分的结束时间，取结束时间中小的；
    LocalDateTime dt2 = p1.getTo();
    if (p1.getTo().compareTo(p2.getTo()) > 0) {
      dt2 = p2.getTo();
    }

    return Optional.of(DurationUtil.of(dt1, dt2));
  }

  // 返回这段期间的日历天数，保留5位小数；
  public BigDecimal getDayCount() {
    ZoneOffset base = ZoneOffset.of("Z");
    long diff = this.getTo().toEpochSecond(base) - this.getFrom().toEpochSecond(base);

    BigDecimal cnt = new BigDecimal(diff * 1.0 / DurationUtil.secondsOfDay);

    cnt = cnt.setScale(5, BigDecimal.ROUND_UP);

    return cnt;
  }

  // 返回两个日期间，完整的月份，from/to 都不包含；
  // 如果 from/to 在同一个月，返回 -1；--> 正是需要的；
  public int getMonthCount() {
    int fullYears = (to.getYear() - from.getYear() - 1) * 12;
    int first = 12 - from.getMonthValue();
    int second = to.getMonthValue() - 1;

    return first + fullYears + second;
  }

  // 返回两个日期间，完整的月份，from/to 都不包含；
  // 如果 from/to 在同一个月，返回 -1；--> 正是需要的；
  public static int getMonthCount(LocalDate from, LocalDate to) {
    int fullYears = (to.getYear() - from.getYear() - 1) * 12;
    int first = 12 - from.getMonthValue();
    int second = to.getMonthValue() - 1;

    return first + fullYears + second;
  }

  @Override
  public String toString() {
    return "DurationUtil{" +
        "from=" + from +
        ", to=" + to +
        ", sign=" + sign +
        '}';
  }
}
