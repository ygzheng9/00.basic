package pickup.fundMgmt;

import pickup.voucher.VouPayload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yonggang on 27/11/16.
 */
public class FundBalance implements VouPayload {

  private Long id;
  private String refCode;
  private String type;

  private String remark;

  List<FundReg> openRegs = null;
  FundReg balReg = null;

  @Override
  public String getVouKey(String type) {
    Map<String, String> payload = new HashMap<>();
    payload.put(VouPayload.KEY1, FundBalance.class.getSimpleName());
    payload.put(VouPayload.KEY2, this.getType());

    return payload.get(type);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRefCode() {
    return refCode;
  }

  public void setRefCode(String refCode) {
    this.refCode = refCode;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public List<FundReg> getOpenRegs() {
    return openRegs;
  }

  public FundReg getBalReg() {
    return balReg;
  }


  // 把传入的记录的余额都清掉，并且结转到新的记录上；
  // 将新纪录，和被清掉后的记录，一起返回；
  public List<FundReg> build(final String refCode, final List<FundReg> inFunds) {
    this.refCode = refCode;
    this.type = "BALANCE";

    openRegs = inFunds.stream()
                      .map((reg) -> {
                        reg.setTotalAmt(reg.getBalAmt());
                        return reg;
                      })
                      .collect(Collectors.toList());

    // 待结转条目的余额总额
    double total = openRegs.stream()
                           .map(FundReg::getBalAmt)
                           .reduce(0.0, Double::sum);

    // 结转后新生的条目
    balReg = new FundReg(refCode, total);
    balReg.setType("BALANCE");

    // 待结转条目,全部清空
    List<FundReg> clr = inFunds.stream().map(FundUtil::setToZero)
                               .collect(Collectors.toList());

    clr.add(balReg);

    return clr;
  }
}
