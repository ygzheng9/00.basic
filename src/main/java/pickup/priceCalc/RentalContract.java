package pickup.priceCalc;

import com.google.common.collect.ImmutableList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Created by YongGang on 2017/3/5.
 */

// 租船协议
// 协议中有：协议号，协议有效期，实际还船时间
// 日租金的费率表
public class RentalContract {

  private String contractNo;
  private String vesselCode;
  private LocalDateTime from;
  private LocalDateTime to;
  private LocalDateTime actualReturnDate;

  private ImmutableList<RentalRateEntry> rateTable;



  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public RentalContract(String contractNo, String vesselCode, String from, String to,
      List<RentalRateEntry> rateTable) {
    this.contractNo = contractNo;
    this.vesselCode = vesselCode;
    this.from = LocalDateTime.parse(from, RentalContract.formatter);
    this.to = LocalDateTime.parse(to, RentalContract.formatter);
    // this.actualReturnDate = LocalDateTime.parse(actualReturnDate, RentalContract.formatter);
    this.rateTable = ImmutableList.copyOf(rateTable);
  }

  public String getContractNo() {
    return contractNo;
  }

  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }

  public String getVesselCode() {
    return vesselCode;
  }

  public void setVesselCode(String vesselCode) {
    this.vesselCode = vesselCode;
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

  public LocalDateTime getActualReturnDate() {
    return actualReturnDate;
  }

  public void setActualReturnDate(String actualReturnDate) {
    this.actualReturnDate = LocalDateTime.parse(actualReturnDate, RentalContract.formatter);
  }

  public ImmutableList<RentalRateEntry> getRateTable() {
    return rateTable;
  }

  public void setRateTable(
      List<RentalRateEntry> rateTable) {
    this.rateTable = ImmutableList.copyOf(rateTable);
  }

  @Override
  public String toString() {
    return "RentalContract{" +
        "contractNo='" + contractNo + '\'' +
        ", vesselCode='" + vesselCode + '\'' +
        ", from=" + from +
        ", to=" + to +
        ", actualReturnDate=" + actualReturnDate +
        ", rateTable=" + rateTable +
        '}';
  }

  public String toSimpleString() {
    return "RentalContract{" +
        "contractNo='" + contractNo + '\'' +
        ", vesselCode='" + vesselCode + '\'' +
        ", from=" + from +
        ", to=" + to +
        ", actualReturnDate=" + actualReturnDate +
        '}';
  }

  // 计算的期间，必须是协议有效期内的，
  // 并且，如果实际还船时间有值，还必须是早于实际还船时间的；
  public Optional<DurationUtil> validPeriod(Optional<DurationUtil> p) {
    if (p.isPresent()) {
      // 传入的计算期间是一个有效期的期间，计算这个计算期间和协议有效的交集
      DurationUtil validPeriod = DurationUtil.of(this.getFrom(), this.getTo());
      Optional<DurationUtil> validP = DurationUtil.intersect(validPeriod, p.get());

      if (validP.isPresent()) {
        // 计算期间，和协议有效期，有交集

        if (null != this.getActualReturnDate() ) {
          // 维护了时间还船时间
          DurationUtil cp = validP.get();

          if (cp.getFrom().compareTo(this.getActualReturnDate()) >= 0) {
            // 交集的开始时间，比实际还船时间还大，不能计算；
            return Optional.empty();
          } else if (cp.getTo().compareTo(this.getActualReturnDate()) > 0) {
            // 实际还船时间，在交集内部，所以截至时间应为：实际还船时间
            DurationUtil aNew = DurationUtil.of(cp.getFrom(), this.getActualReturnDate());
            return Optional.of(aNew);
          } else {
            // 实际还船时间，大于交集，直接返回交集；
            return validP;
          }
        } else {
          // 实际还船时间没有维护；
          return validP;
        }
      } else {
        // 计算期间，和协议有效期，无交集
        return Optional.empty();
      }
    } else {
      return Optional.empty();
    }
  }
}
