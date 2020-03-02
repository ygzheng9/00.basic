package pickup.voucher;

import pickup.fundMgmt.FundReg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonggang on 26/11/16.
 */

// 正常资金登记的记账程序，内部是【待记账数据】，
// 分录中需要的属性，都通过 getter 代理给【待记账数据】，
// getter 的函数名，和凭证分录规则中的配置，保持一致；
public class FundVouGeneratorNormal extends VouGeneratorAbstract {

  // 待记账数据
  private FundReg bizData;

  @Override
  public List<VouItem> run(Object param) {
    this.bizData = (FundReg) param;

//        return oldRun();
    return newRun();
  }

  @Deprecated
  private List<VouItem> oldRun() {
    VouItem item_dr = new VouItem("40", "BANK", bizData.getTotalAmt());
    VouItem item_cr = new VouItem("50", "AR-TEMP", bizData.getTotalAmt());

    List<VouItem> item_list = new ArrayList<VouItem>();
    item_list.add(item_dr);
    item_list.add(item_cr);

    return item_list;
  }

  private List<VouItem> newRun() {
    List<VouItem> item_list = new ArrayList<>();

    try {
      item_list = this.pumpVouItems("BANK-IN-NORMAL");
    } catch (Exception e) {
      e.printStackTrace();
    }

    return item_list;
  }

  public String getBankAcct() {
    return "BANK";
  }

  public double getWrbtr() {
    return bizData.getTotalAmt();
  }

  public double getDmbtr() {
    return bizData.getTotalAmt() * 6.4;
  }

  public String getZuonr() {
    return bizData.getRemark();
  }

  public String getSgtxt() {
    return bizData.getRemark() + " --- remark.";
  }

}
