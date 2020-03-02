package pickup.allocation;

import java.math.BigDecimal;

/**
 * Created by YongGang on 2017/3/3.
 */
public class DetailItem {
  private String key1;
  private String key2;

  // 明细的分摊的业务量
  private BigDecimal value;

  // 明细的分摊占比
  private BigDecimal allocPect;

  // 明细的分摊金额
  private BigDecimal allocAmount;

  public DetailItem(String key1, String key2, BigDecimal value) {
    this.key1 = key1;
    this.key2 = key2;
    this.value = this.purify(value);
  }

  @Override
  public String toString() {
    return "DetailItem{" +
        "key1='" + key1 + '\'' +
        ", key2='" + key2 + '\'' +
        ", value=" + value +
        ", allocPect=" + allocPect +
        ", allocAmount=" + allocAmount +
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

  public String getKey2() {
    return key2;
  }

  public void setKey2(String key2) {
    this.key2 = key2;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = this.purify(value);
  }

  public BigDecimal getAllocPect() {
    return allocPect;
  }

  public void setAllocPect(BigDecimal allocPect) {
    this.allocPect = allocPect.setScale(4, BigDecimal.ROUND_HALF_UP);
  }

  public BigDecimal getAllocAmount() {
    return allocAmount;
  }

  public void setAllocAmount(BigDecimal allocAmount) {
    this.allocAmount = this.purify(allocAmount);
  }
}
