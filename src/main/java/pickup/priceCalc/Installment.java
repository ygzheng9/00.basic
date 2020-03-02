package pickup.priceCalc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by YongGang on 2017/3/9.
 */

// 每期的租金账单
// 这里是账单头，本期的期次号，开始时间，结束时间等；
// 还有费用明细表，记录该期次的各种各样的费用项；
public class Installment {

  private String insNo;
  private LocalDateTime from;
  private LocalDateTime to;
  private String contractNo;
  private String bizStatus;

  public static String DRAFT = "DRAFT";
  public static String CONFIRM = "CONFIRM";
  public static String DELETE = "DELETE";

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public Installment(String contractNo, String insNo, String from, String to, String bizStatus) {
    this.contractNo = contractNo;
    this.insNo = insNo;
    this.from = LocalDateTime.parse(from, Installment.formatter);
    this.to = LocalDateTime.parse(to, Installment.formatter);
    this.bizStatus = bizStatus;
  }

  // 按 业务状态 过滤
  public boolean byBizStatus(String status) {
    int cmp = this.getBizStatus().compareToIgnoreCase(status);
    return (cmp == 0);
  }

  public boolean onlyConfirmed() {
    return this.byBizStatus(Installment.CONFIRM);
  }

  // 按 截至时间 排序
  public int byToDesc(Installment obj) {
    return -1 * this.to.compareTo(obj.getTo());
  }

  public String getInsNo() {
    return insNo;
  }

  public void setInsNo(String insNo) {
    this.insNo = insNo;
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

  public String getContractNo() {
    return contractNo;
  }

  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }

  public String getBizStatus() {
    return bizStatus;
  }

  public void setBizStatus(String bizStatus) {
    this.bizStatus = bizStatus;
  }

  @Override
  public String toString() {
    return "Installment{" +
        "insNo='" + insNo + '\'' +
        ", from=" + from +
        ", to=" + to +
        ", contractNo='" + contractNo + '\'' +
        ", bizStatus='" + bizStatus + '\'' +
        '}';
  }
}
