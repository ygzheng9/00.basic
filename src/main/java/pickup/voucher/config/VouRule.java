package pickup.voucher.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonggang on 2/12/16.
 */

// 一个业务类型（key1）对应的凭证规则
public class VouRule {

  private String key1;
  List<VouRuleItem> items = new ArrayList<>();

  public VouRule(String key1, List<VouRuleItem> items) {
    this.key1 = key1;
    this.items = items;
  }

  public String getKey1() {
    return key1;
  }

  public void setKey1(String key1) {
    this.key1 = key1;
  }

  public List<VouRuleItem> getItems() {
    return items;
  }

  public void setItems(List<VouRuleItem> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "VouRule{" +
        "key1='" + key1 + '\'' +
        ", items=" + items +
        '}';
  }
}
