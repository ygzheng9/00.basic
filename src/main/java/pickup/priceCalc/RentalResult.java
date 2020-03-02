package pickup.priceCalc;

import java.math.BigDecimal;

/**
 * Created by YongGang on 2017/3/5.
 */

// 租金计算的结果
// 颗粒度是：协议，每段费率，在这段费率上的起止时间，对应的租金
public class RentalResult {

  private RentalContract contract;
  private RentalRateEntry rateEntry;
  private DurationUtil durationUtil;

  public RentalResult(RentalContract contract, RentalRateEntry rateEntry,
      DurationUtil durationUtil) {
    this.contract = contract;
    this.rateEntry = rateEntry;
    this.durationUtil = durationUtil;
  }

  // 返回期间内的天数
  private BigDecimal getDayCont() {
    return this.durationUtil.getDayCount();
  }

  // 返回日租金
  private BigDecimal getDailyRate() {
    return this.rateEntry.getRentalRate();
  }

  // 返回回佣比例
  private BigDecimal getDiscountRate() {
    return this.rateEntry.getDiscount();
  }

  // 回佣前租金
  public BigDecimal getGrossAmt() {
    BigDecimal dayCnt = this.getDayCont();
    BigDecimal dailyRate = this.getDailyRate();

    return dailyRate.multiply(dayCnt).setScale(2, BigDecimal.ROUND_UP);
  }

  // 回佣金额
  public BigDecimal getDiscountAmt() {
    BigDecimal discountRate = this.getDiscountRate();

    return this.getGrossAmt()
               .multiply(discountRate)
               .divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
  }

  // 净租金 = 回佣前租金 - 回佣金额
  public BigDecimal getActualAmt() {
    return this.getGrossAmt()
               .subtract(this.getDiscountAmt())
               .setScale(2, BigDecimal.ROUND_UP);
  }

  public RentalContract getContract() {
    return contract;
  }

  public void setContract(RentalContract contract) {
    this.contract = contract;
  }

  public RentalRateEntry getRateEntry() {
    return rateEntry;
  }

  public void setRateEntry(RentalRateEntry rateEntry) {
    this.rateEntry = rateEntry;
  }

  public DurationUtil getDurationUtil() {
    return durationUtil;
  }

  public void setDurationUtil(DurationUtil durationUtil) {
    this.durationUtil = durationUtil;
  }

  @Override
  public String toString() {
    return "RentalResult{" +
        "contract=" + contract.toSimpleString() +
        ", rateEntry=" + rateEntry +
        ", durationUtil=" + durationUtil +
        ", dayCnt=" + getDayCont() +
        ", grossAmt=" + getGrossAmt() +
        ", discountAmt=" + getDiscountAmt() +
        ", actualAmt=" + getActualAmt() +
        '}';
  }
}
