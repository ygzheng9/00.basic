package pickup.voucher.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonggang on 3/12/16.
 */
public class VouRuleBuilder {

  @Deprecated
  public static List<String> initPropList() {
    List<String> propList = new ArrayList<>();

    // TODO: 从配置文件加载
    propList.add("type");
    propList.add("bschl");
    propList.add("umskz");
    propList.add("xnegp");
    propList.add("newko");
    propList.add("wrbtr");
    propList.add("dmbtr");
    propList.add("zuonr");
    propList.add("sgtxt");
    propList.add("xref1");
    propList.add("xref2");
    propList.add("xref3");
    propList.add("prctr");
    propList.add("kostl");

    return propList;
  }

  public static List<VouRuleItemCellProp> initVouItemCellProp() {
    // TODO: 从文件读取;

    List<VouRuleItemCellProp> props = new ArrayList<>();

    props.add(new VouRuleItemCellProp("type", "setType", "String"));
    props.add(new VouRuleItemCellProp("bschl", "setBschl", "String"));
    props.add(new VouRuleItemCellProp("umskz", "setUmskz", "String"));
    props.add(new VouRuleItemCellProp("xnegp", "setXnegp", "String"));

    props.add(new VouRuleItemCellProp("newko", "setNewko", "String"));

    props.add(new VouRuleItemCellProp("wrbtr", "setWrbtr", "Double"));
    props.add(new VouRuleItemCellProp("dmbtr", "setDmbtr", "Double"));

    props.add(new VouRuleItemCellProp("zuonr", "setZuonr", "String"));
    props.add(new VouRuleItemCellProp("sgtxt", "setSgtxt", "String"));

    props.add(new VouRuleItemCellProp("xref1", "setXref1", "String"));
    props.add(new VouRuleItemCellProp("xref2", "setXref2", "String"));
    props.add(new VouRuleItemCellProp("xref3", "setXref3", "String"));

    props.add(new VouRuleItemCellProp("prctr", "setPrctr", "String"));
    props.add(new VouRuleItemCellProp("kostl", "setKostl", "String"));

    return props;
  }

  @Deprecated
  public static List<VouRule> initVouRules() {
    List<VouRule> ruleList = new ArrayList<>();

    // 到账
    List<VouRuleItem> normal_items = new ArrayList<>();

    // Dr
    List<VouRuleItemCell> bankinNormal_DR = new ArrayList<VouRuleItemCell>();
    bankinNormal_DR.add(new VouRuleItemCell("type", "GL"));
    bankinNormal_DR.add(new VouRuleItemCell("bschl", "40"));
    bankinNormal_DR.add(new VouRuleItemCell("newko", "@getNewKo"));
    bankinNormal_DR.add(new VouRuleItemCell("wrbtr", "@getWrbtr"));
    bankinNormal_DR.add(new VouRuleItemCell("dmbtr", "@getDmbtr"));
    bankinNormal_DR.add(new VouRuleItemCell("zuonr", "@getZuonr"));
    bankinNormal_DR.add(new VouRuleItemCell("sgtxt", "@getSgtxt"));
    normal_items.add(new VouRuleItem("BANK-IN-NORMAL_DR", bankinNormal_DR));

    // 到账 Cr
    List<VouRuleItemCell> bankin_CR = new ArrayList<VouRuleItemCell>();
    bankin_CR.add(new VouRuleItemCell("type", "GL"));
    bankin_CR.add(new VouRuleItemCell("bschl", "50"));
    bankin_CR.add(new VouRuleItemCell("newko", "AR-TEMP1"));
    bankin_CR.add(new VouRuleItemCell("wrbtr", "@getWrbtr"));
    bankin_CR.add(new VouRuleItemCell("dmbtr", "@getDmbtr"));
    bankin_CR.add(new VouRuleItemCell("zuonr", "@getZuonr"));
    bankin_CR.add(new VouRuleItemCell("sgtxt", "@getSgtxt"));
    normal_items.add(new VouRuleItem("BANK-IN_CR", bankin_CR));

    ruleList.add(new VouRule("BANK-IN-NORMAL", normal_items));

    // 客户开票
    List<VouRuleItem> invCust_items = new ArrayList<>();

    // Dr
    List<VouRuleItemCell> inv_ttl = new ArrayList<VouRuleItemCell>();
    inv_ttl.add(new VouRuleItemCell("type", "AR"));
    inv_ttl.add(new VouRuleItemCell("bschl", "40"));
    inv_ttl.add(new VouRuleItemCell("newko", "AR-Cust"));
    inv_ttl.add(new VouRuleItemCell("wrbtr", "@getWrbtrTotal"));
    inv_ttl.add(new VouRuleItemCell("dmbtr", "@getDmbtrTotal"));
    inv_ttl.add(new VouRuleItemCell("zuonr", "@getInvNo"));
    inv_ttl.add(new VouRuleItemCell("sgtxt", "@getSgtxt"));

    invCust_items.add(new VouRuleItem("INV-CUST-AR", inv_ttl));

    // Cr
    List<VouRuleItemCell> inv_net = new ArrayList<VouRuleItemCell>();
    inv_net.add(new VouRuleItemCell("type", "GL"));
    inv_net.add(new VouRuleItemCell("bschl", "50"));
    inv_net.add(new VouRuleItemCell("newko", "Rev"));
    inv_net.add(new VouRuleItemCell("wrbtr", "@getWrbtrNet"));
    inv_net.add(new VouRuleItemCell("dmbtr", "@getDmbtrNet"));
    inv_net.add(new VouRuleItemCell("zuonr", "@getZuonr"));
    inv_net.add(new VouRuleItemCell("sgtxt", "@getSgtxt"));
    invCust_items.add(new VouRuleItem("INV-CUST-REV", inv_net));

    List<VouRuleItemCell> inv_tax = new ArrayList<VouRuleItemCell>();
    inv_tax.add(new VouRuleItemCell("type", "GL"));
    inv_tax.add(new VouRuleItemCell("bschl", "50"));
    inv_tax.add(new VouRuleItemCell("newko", "Tax"));
    inv_tax.add(new VouRuleItemCell("wrbtr", "@getWrbtrTax"));
    inv_tax.add(new VouRuleItemCell("dmbtr", "@getDmbtrTax"));
    inv_tax.add(new VouRuleItemCell("zuonr", "@getZuonr"));
    inv_tax.add(new VouRuleItemCell("sgtxt", "@getSgtxt"));

    invCust_items.add(new VouRuleItem("INV-CUST-TAX", inv_tax));

    ruleList.add(new VouRule("INV_CUST", invCust_items));

    return ruleList;
  }

  // 设置各个业务类型的凭证规则
  // 每种业务的凭证，又有借贷方的分录；
  // 不同业务凭证的分录，还有相同的；
  public static List<VouRule> buildVouRules() {

    // 所有业务的凭证逻辑入口，key1 代理业务类型
    List<VouRule> ruleList = new ArrayList<>();

    // 到账凭证
    // 两个分录，一借一贷
    List<VouRuleItem> normal_items = new ArrayList<>();

    // Dr
    VouRuleItem normal_dr = new VouRuleItem("BANK-IN-NORMAL_DR");
    normal_dr.setType("GL");
    normal_dr.setBschl("40");
    normal_dr.setNewko("@getBankAcct");
    normal_dr.setWrbtr("@getWrbtr");
    normal_dr.setDmbtr("@getDmbtr");
    normal_dr.setZuonr("@getZuonr");
    normal_dr.setSgtxt("@getSgtxt");

    normal_items.add(normal_dr);

    // Cr
    VouRuleItem bankin_cr = new VouRuleItem("BANK-IN_CR");

    bankin_cr.setType("GL");
    bankin_cr.setBschl("50");
    bankin_cr.setNewko("AR-TEMP");
    bankin_cr.setWrbtr("@getWrbtr");
    bankin_cr.setDmbtr("@getDmbtr");
    bankin_cr.setZuonr("@getZuonr");
    bankin_cr.setSgtxt("@getSgtxt");

    // 到账凭证的两个分录
    normal_items.add(bankin_cr);

    // 到账凭证，加入到凭证规则表中
    ruleList.add(new VouRule("BANK-IN-NORMAL", normal_items));

    // 上级公司到账
    List<VouRuleItem> related_items = new ArrayList<>();

    // Dr
    VouRuleItem related_dr = new VouRuleItem("BANK-IN-RELATED_DR");
    related_dr.setType("AR");
    related_dr.setBschl("09");
    related_dr.setUmskz("O");
    related_dr.setNewko("@getSAPID");
    related_dr.setWrbtr("@getWrbtr");
    related_dr.setDmbtr("@getDmbtr");
    related_dr.setZuonr("@getZuonr");
    related_dr.setSgtxt("@getSgtxt");

    related_items.add(related_dr);

    // Cr
    related_items.add(bankin_cr);

    ruleList.add(new VouRule("BANK-IN-RELATED", related_items));

    // 预付款
    List<VouRuleItem> advanced_items = new ArrayList<>();

    // Dr
    advanced_items.add(normal_dr);

    // Cr
    VouRuleItem advanced_cr = new VouRuleItem("BANK-IN-ADVANCED_CR");
    advanced_cr.setType("AR");
    advanced_cr.setBschl("19");
    advanced_cr.setUmskz("A");
    advanced_cr.setNewko("@getNewko");
    advanced_cr.setWrbtr("@getWrbtr");
    advanced_cr.setDmbtr("@getDmbtr");
    advanced_cr.setZuonr("@getZuonr");
    advanced_cr.setSgtxt("@getSgtxt");

    advanced_items.add(advanced_cr);

    ruleList.add(new VouRule("BANK-IN-ADVANCED", advanced_items));

    return ruleList;
  }
}
