package pickup.priceCalc;

import com.google.common.collect.Maps;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by YongGang on 2017/3/5.
 */

// 计算截止时间
//   根据上次的截止时间，计算下次的截止时间；
//   两种模式：固定天数，固定日期；
// 计算租金：
//   多个协议
//   每个协议，多个费率段；
//   计算一段期间的租金，
//   这段期间，允许跨多个费率段，或者在费率有效期之外；

public class RentalCalcUtil {

  private static Integer MONTH_START = 1;
  private static Integer MONTH_END = 28;

  // 每隔固定天数，比如：每15天计算一次
  public static LocalDateTime calcNextEndDTByFixedDay(final LocalDateTime stopAt, int dayCount) {
    return stopAt.plusDays(dayCount);
  }

  public static Integer clearDate(Integer i) {
    Integer rtn = i;

    if (i < MONTH_START) {
      rtn = MONTH_START;
    } else if (i > MONTH_END) {
      rtn = MONTH_END;
    }

    return rtn;
  }

  // 按照每月确定的日期，进行下一次计算的日历天，比如：每月 1、16 号计算，或者每月 月底 计算；
  // timer: 每月的定时器 1, 16
  public static LocalDateTime calcNextEndDTByTimer(final LocalDateTime lastStopAt,
      final List<Integer> timer) {

    // 对于大于等于 28 的日期，统一作为月底；
    // 对指定日期，做 distinct；
    // 对指定日期，从小到大排序；
    // 对指定日期，循环
    // 传入日期的 dayOfMonth，如果比当前天数小，则返回传入日期当月+当前段天数；
    // 如果等于当前天数，则返回传入日期当月 + 下一段天数；
    // 如果当前天数是最后一个，则返回传入的下月 + 第一段天数；
    // 对于月底，1/31 的下一个是 2/28，再下一个是 3/31;

    List<Integer> validTimer = timer.stream()
                                    .map(RentalCalcUtil::clearDate)
                                    .distinct()
                                    .sorted()
                                    .collect(Collectors.toList());

    int stopDate = lastStopAt.getDayOfMonth();
    int nextDate = stopDate;
    int addMonth = 0;
    int isFound = 0;

    int totalCnt = validTimer.size();

    // 如果没有设定，直接返回；
    if (totalCnt == 0) {
      return lastStopAt;
    }

    for (int idx = 0; idx < totalCnt; idx++) {
      int t = validTimer.get(idx);

      if (stopDate < t) {
        // 比当前设定值小，直接取当前设定值
        nextDate = t;
        isFound = 1;
        break;
      } else if (stopDate == t) {
        // 和当前设定值一样大
        if (idx < totalCnt - 1) {
          // 当前不是最后一个，那就取下一个
          nextDate = validTimer.get(idx + 1);
        } else {
          // 当前是最后一个，也即：进入下一个月，需要取第一个
          addMonth = 1;
          nextDate = validTimer.get(0);
        }
        isFound = 1;
        break;
      }
    }

    // 通过循环没找到，也即：传入的天数，比设定的最大值还大，
    // 进入下月，取第一个
    if (isFound == 0) {
      addMonth = 1;
      nextDate = validTimer.get(0);
    }

    LocalDate d = lastStopAt.toLocalDate();

    LocalDateTime rtn = lastStopAt;
    int dayDiff = 0;

    if (addMonth == 0) {
      // 当月
      if (nextDate == MONTH_END) {
        // 月底
        dayDiff = d.lengthOfMonth() - stopDate;
      } else {
        // 月中
        dayDiff = nextDate - stopDate;
      }

      rtn = lastStopAt.plusDays(dayDiff);
    } else {
      // 下个月
      d = lastStopAt.toLocalDate().with(TemporalAdjusters.firstDayOfNextMonth());

      if (nextDate == MONTH_END) {
        // 月底
        d = d.with(TemporalAdjusters.lastDayOfMonth());
      } else {
        // 月中
        d = d.withDayOfMonth(nextDate);
      }

      rtn = LocalDateTime.of(d, lastStopAt.toLocalTime());
    }

    return rtn;
  }

  // 初始化协议列表；
  // 每个协议，有自己的租金费率表；
  // 费率表中的每一行，代表该费率的有效时间；
  // todo 从数据库获取
  public List<RentalContract> initContractList() {
    List<RentalContract> contractList = new ArrayList<>();

    // 第一个协议的费率，只有一个费率，和协议有效期一致；
    List<RentalRateEntry> entryList1 = new ArrayList<>();
    entryList1.add(new RentalRateEntry("2016-01-01 12:00:00", "2017-12-01 13:24:00", 10000, 0));

    RentalContract contract1 = new RentalContract("S7SA01001", "S7S",
        "2016-01-01 12:00:00", "2017-12-01 13:24:00", entryList1);

    contractList.add(contract1);

    // 第二个协议的费率，多段费率，和协议有效期不一致；
    List<RentalRateEntry> entryList2 = new ArrayList<>();
    entryList2.add(new RentalRateEntry("2016-11-01 08:00:00", "2016-12-15 16:30:00", 10000, 0));
    entryList2.add(new RentalRateEntry("2016-12-15 16:30:00", "2017-03-01 08:30:00", 9500, 2.5));
    entryList2.add(new RentalRateEntry("2017-03-01 08:30:00", "2017-05-01 19:00:00", 8000, 2.5));

    RentalContract contract2 = new RentalContract("T57A01002", "T75",
        "2016-01-01 12:00:00", "2017-07-01 13:24:00", entryList2);

    contract2.setActualReturnDate("2017-04-13 07:15:23");

    contractList.add(contract2);

    return contractList;
  }

  // 初始化每个协议需要计算的期间；
  // key: 协议号，value：该协议对应的计算期间
  // todo 从数据库，或界面获取
  public Map<String, DurationUtil> initCalcPeriod() {
    Map<String, DurationUtil> rtn = new HashMap<>();

    rtn.put("S7SA01001", DurationUtil.of("2016-12-15 08:00:00", "2017-01-31 16:00:00"));
    rtn.put("T57A01002", DurationUtil.of("2016-10-01 08:00:00", "2017-04-15 16:00:00"));

    return rtn;
  }

  // 初始化协议的期次；
  public List<Installment> initInstallment() {
    List<Installment> ins = new ArrayList<>();

    // 特意不按照时间顺序插入
    // 使用时，需要按照时间顺序，从早到晚排序，找到 CONFIRM 的最后一个期次；
    ins.add(new Installment("S7SA01001", "201611", "2016-11-01 00:00:00", "2016-12-01 00:00:00",
        Installment.CONFIRM));
    ins.add(new Installment("S7SA01001", "201612", "2016-12-01 00:00:00", "2017-01-01 00:00:00",
        Installment.DRAFT));
    ins.add(new Installment("S7SA01001", "201610", "2016-10-01 00:00:00", "2016-11-01 00:00:00",
        Installment.CONFIRM));

    ins.add(new Installment("T57A01002", "201701-1", "2017-01-05 16:00:00", "2017-01-05 16:00:00",
        Installment.CONFIRM));
    ins.add(new Installment("T57A01002", "201701-2", "2017-01-20 16:00:00", "2017-02-05 16:00:00",
        Installment.CONFIRM));
    ins.add(new Installment("T57A01002", "201612-1", "2016-12-05 16:00:00", "2016-12-20 16:00:00",
        Installment.CONFIRM));
    ins.add(new Installment("T57A01002", "201612-2", "2016-12-20 16:00:00", "2017-01-05 16:00:00",
        Installment.DRAFT));
    ins.add(new Installment("T57A01002", "201611-1", "2016-11-05 16:00:00", "2016-11-20 16:00:00",
        Installment.DELETE));
    ins.add(new Installment("T57A01002", "201611-2", "2016-11-20 16:00:00", "2016-12-05 16:00:00",
        Installment.CONFIRM));


    return ins;
  }

  // 取得协议的最后一个 confirmed 的期次
  public Map<String, Optional<Installment>> getLastConfirmedIns(List<Installment> ins) {
    // 取得一个协议下的所有期次，Key=协议号
    Map<String, List<Installment>> insByContract =
        ins.stream()
           .collect(Collectors.groupingBy(Installment::getContractNo));

    // 对每个协议下期次，过滤掉不是 CONFIRMED, 然后按结束时间倒序排序，并取第一个
    Map<String, Optional<Installment>> rtn =
        insByContract.entrySet()
                     .stream()
                     .map((entry) -> {
                       String contractNo = entry.getKey();

                       // confirmed 状态的，并且是最新的协议
                       Optional<Installment> latest =
                           entry.getValue()
                                .stream()
                                .filter(Installment::onlyConfirmed)
                                .sorted(Installment::byToDesc)
                                .findFirst();

                       return Maps.immutableEntry(contractNo, latest);
                     })
                     .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    return rtn;
  }

  // 每个协议，计算一段时间的租金；
  // 对于费率分段的情况，需要计算出每一段费率的租金；
  public List<RentalResult> calc(final List<RentalContract> contractList,
      final Map<String, DurationUtil> calcPeriod) {
    List<RentalResult> resultList = new ArrayList<>();

    // 对每个协议
    for (RentalContract contract : contractList) {
      // 找到协议需要计算的期间
      DurationUtil period = calcPeriod.get(contract.getContractNo());

      // 如果协议没有需要计算的期间，不做任何事；
      // 如果有需要计算的期间，需要按照逐条费率，计算租金
      if (null != period) {
        for (RentalRateEntry rateEntry : contract.getRateTable()) {
          // 这一段费率的有效期，和协议需要计算的期间，的时间交集
          Optional<DurationUtil> intersection = rateEntry.getIntersection(period);

          // 交集需要再和协议有效期，实际还船时间做比较
          Optional<DurationUtil> p = contract.validPeriod(intersection);
          if (p.isPresent()) {
            RentalResult result = new RentalResult(contract, rateEntry, p.get());
            resultList.add(result);
          }
        }
      }
    }

    return resultList;
  }

}
