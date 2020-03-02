package pickup.priceCalc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by YongGang on 2017/3/5.
 */

// 日租金费率表中的一行
// 每一行费率都有起止日期，表示该费率的使用期间

public class RentalRateEntry {
  // TODO 所对应的协议
  // private int contractID;

  private LocalDateTime from;
  private LocalDateTime to;
  private BigDecimal rentalRate;
  private BigDecimal discount;

  // String str = "1986-04-08 12:30";
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public RentalRateEntry(String from, String to, double rentalRate, double discount) {
    this.from = LocalDateTime.parse(from, RentalRateEntry.formatter);
    this.to = LocalDateTime.parse(to, RentalRateEntry.formatter);
    this.rentalRate = BigDecimal.valueOf(rentalRate).setScale(2, BigDecimal.ROUND_UP);
    this.discount = BigDecimal.valueOf(discount).setScale(2, BigDecimal.ROUND_UP);
  }

  // 费率有效期，与 p 的时间重叠区间
  public Optional<DurationUtil> getIntersection(DurationUtil p) {
    DurationUtil validPeriod = DurationUtil.of(this.getFrom(), this.getTo());
    return DurationUtil.intersect(validPeriod, p);
  }

  public LocalDateTime getFrom() {
    return from;
  }

  public void setFrom(LocalDateTime from) {
    this.from = from;
  }

  public LocalDateTime getTo() {
    return to;
  }

  public void setTo(LocalDateTime to) {
    this.to = to;
  }

  public BigDecimal getRentalRate() {
    return rentalRate;
  }

  public void setRentalRate(BigDecimal rentalRate) {
    this.rentalRate = rentalRate;
  }

  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  @Override
  public String toString() {
    return "RentalRateEntry{" +
        "from=" + from +
        ", to=" + to +
        ", rentalRate=" + rentalRate +
        ", discount=" + discount +
        '}';
  }
}
