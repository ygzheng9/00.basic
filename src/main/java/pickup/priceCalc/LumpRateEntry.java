package pickup.priceCalc;

import java.math.BigDecimal;

/**
 * Created by YongGang on 2017/1/23.
 */

// 包干杂费的费率
// 两种模式：按月收；按期收；
public class LumpRateEntry {
  public static String BY_MONTH = "BY_MONTH";
  public static String BY_PHASE = "BY_PHASE";

  private String quotationType;
  private BigDecimal price;
  private BigDecimal monthPrice;

  public LumpRateEntry() {
  }

  public LumpRateEntry(String quotationType, BigDecimal price, BigDecimal monthPrice) {
    this.quotationType = quotationType;
    this.price = price;
    this.monthPrice = monthPrice;
  }

  public String getQuotationType() {
    return quotationType;
  }

  public void setQuotationType(String quotationType) {
    this.quotationType = quotationType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getMonthPrice() {
    return monthPrice;
  }

  public void setMonthPrice(BigDecimal monthPrice) {
    this.monthPrice = monthPrice;
  }
}
