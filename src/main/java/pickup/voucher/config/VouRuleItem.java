package pickup.voucher.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonggang on 2/12/16.
 */

// 代表一个借贷分录规则
// 分录中的多个栏位，通过不同的属性表示（而不是一个子表）
public class VouRuleItem {

  private String vouItemType;

  // 改成使用不同的属性字段来表示，而不是再做一个子表；
  // 更多的列，而不是更多的父子表关联；
  @Deprecated
  List<VouRuleItemCell> cells = new ArrayList<>();


  public String getVouItemType() {
    return vouItemType;
  }

  public void setVouItemType(String vouItemType) {
    this.vouItemType = vouItemType;
  }

  public VouRuleItem(String vouItemType, List<VouRuleItemCell> cells) {
    this.vouItemType = vouItemType;
    this.cells = cells;
  }

  public List<VouRuleItemCell> getCells() {
    return cells;
  }

  public void setCells(List<VouRuleItemCell> cells) {
    this.cells = cells;
  }

  @Override
  public String toString() {
    return "VouRuleItem{" +
        "vouItemType=" + vouItemType +
        "cells=" + cells +
        '}';
  }

  // 使用不同的列,做设置;
  private String type;        // AR/AP/GL/GL_COPA
  private String bschl;       // 40/50/01/11/21/31/09/19/29/39/07/17
  private String umskz;       // O/A/W
  private String xnegp;       // blank/X

  private String newko;       // GL account

  private String wrbtr;       // original amount
  private String dmbtr;       // local amount

  private String zuonr;
  private String sgtxt;

  private String xref1;
  private String xref2;
  private String xref3;

  private String prctr;       // profit center
  private String kostl;       // cost center

  public VouRuleItem(String vouItemType) {
    this.vouItemType = vouItemType;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBschl() {
    return bschl;
  }

  public void setBschl(String bschl) {
    this.bschl = bschl;
  }

  public String getUmskz() {
    return umskz;
  }

  public void setUmskz(String umskz) {
    this.umskz = umskz;
  }

  public String getXnegp() {
    return xnegp;
  }

  public void setXnegp(String xnegp) {
    this.xnegp = xnegp;
  }

  public String getNewko() {
    return newko;
  }

  public void setNewko(String newko) {
    this.newko = newko;
  }

  public String getWrbtr() {
    return wrbtr;
  }

  public void setWrbtr(String wrbtr) {
    this.wrbtr = wrbtr;
  }

  public String getDmbtr() {
    return dmbtr;
  }

  public void setDmbtr(String dmbtr) {
    this.dmbtr = dmbtr;
  }

  public String getZuonr() {
    return zuonr;
  }

  public void setZuonr(String zuonr) {
    this.zuonr = zuonr;
  }

  public String getSgtxt() {
    return sgtxt;
  }

  public void setSgtxt(String sgtxt) {
    this.sgtxt = sgtxt;
  }

  public String getXref1() {
    return xref1;
  }

  public void setXref1(String xref1) {
    this.xref1 = xref1;
  }

  public String getXref2() {
    return xref2;
  }

  public void setXref2(String xref2) {
    this.xref2 = xref2;
  }

  public String getXref3() {
    return xref3;
  }

  public void setXref3(String xref3) {
    this.xref3 = xref3;
  }

  public String getPrctr() {
    return prctr;
  }

  public void setPrctr(String prctr) {
    this.prctr = prctr;
  }

  public String getKostl() {
    return kostl;
  }

  public void setKostl(String kostl) {
    this.kostl = kostl;
  }
}
