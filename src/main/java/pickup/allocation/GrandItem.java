package pickup.allocation;

import java.math.BigDecimal;

/**
 * Created by YongGang on 2017/3/3.
 */
public class GrandItem {
  private String key1;

  // 待分摊的总数
  private BigDecimal total;

  // 明细的分摊业务量的汇总
  private BigDecimal subTotal;

  public GrandItem(String key1, BigDecimal total) {
    this.key1 = key1;
    this.total = this.purify(total);
  }

  @Override
  public String toString() {
    return "GrandItem{" +
        "key1='" + key1 + '\'' +
        ", total=" + total +
        ", subTotal=" + subTotal +
        '}';
  }

  private BigDecimal purify(BigDecimal b) {
    return b.setScale(2, BigDecimal.ROUND_HALF_UP);
  }

  public String getKey1() {
    return key1;
  }

  public void setKey1(String key1) {
    this.key1 = key1;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = this.purify(total);
  }

  public BigDecimal getSubTotal() {
    return subTotal;
  }

  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = this.purify(subTotal);
  }
}
