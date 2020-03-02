package pickup.allocation;

import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongGang on 2017/3/3.
 */
public class SimpleStrategy {

  // 初始化 待分摊 数据
  List<GrandItem> genGrantList() {
    List<GrandItem> items = new ArrayList<>();

    items.add(new GrandItem("A", new BigDecimal(567.89)));
    items.add(new GrandItem("B", new BigDecimal(59.23)));
    items.add(new GrandItem("C", new BigDecimal(100.00)));
    items.add(new GrandItem("D", new BigDecimal(120.00)));
    items.add(new GrandItem("E", new BigDecimal(120.00)));

    return items;
  }

  // 初始化 分摊依据
  List<DetailItem> genDetailList() {
    List<DetailItem> items = new ArrayList<>();

    items.add(new DetailItem("A", "A1", new BigDecimal(67.89)));
    items.add(new DetailItem("A", "A2", new BigDecimal(57.9)));
    items.add(new DetailItem("A", "A3", new BigDecimal(17.00)));
    items.add(new DetailItem("A", "A4", new BigDecimal(37.19)));

    items.add(new DetailItem("B", "B1", new BigDecimal(89.12)));
    items.add(new DetailItem("B", "B2", new BigDecimal(73.89)));
    items.add(new DetailItem("B", "B3", new BigDecimal(8.00)));

    items.add(new DetailItem("C", "C1", new BigDecimal(30)));
    items.add(new DetailItem("C", "C2", new BigDecimal(30)));
    items.add(new DetailItem("C", "C3", new BigDecimal(30)));

    items.add(new DetailItem("D", "D1", new BigDecimal(0)));

    items.add(new DetailItem("F", "F1", new BigDecimal(30)));

    return items;
  }

  public void calcWrapper() {
    List<GrandItem> grandList = this.genGrantList();
    List<DetailItem> detailList = this.genDetailList();

    this.calc(grandList, detailList);
  }

  // 将会改变【输入参数】
  // 传入的是计算因子，执行本函数后，分摊的结果将写会到输入参数中；
  public void calc(List<GrandItem> grandList, List<DetailItem> detailList ) {

    // 对 每一个 待分摊 数据
    // 汇总 对应的 分摊依据
    for (GrandItem g : grandList) {
      BigDecimal subTotal = new BigDecimal(0.0);

      for (DetailItem d : detailList) {
        if (d.getKey1().compareToIgnoreCase(g.getKey1()) == 0) {
          subTotal = subTotal.add(d.getValue());
        }
      }

      g.setSubTotal(subTotal);
    }

    // 计算 明细 对应的 分摊比例
    // 根据 分摊比例 和 待分摊数据，计算分摊到的金额
    for (GrandItem g : grandList) {
      BigDecimal total = g.getTotal();
      BigDecimal subTotal = g.getSubTotal();

      for (DetailItem d : detailList) {
        if (d.getKey1().compareToIgnoreCase(g.getKey1()) == 0) {
          if (subTotal.compareTo(BigDecimal.ZERO) == 0) {
            d.setAllocPect(new BigDecimal(0.0));
            d.setAllocAmount(new BigDecimal(0.0));
          } else {
            BigDecimal pect = d.getValue().divide(subTotal, 4, BigDecimal.ROUND_HALF_UP);
            d.setAllocPect(pect);

            BigDecimal amt = pect.multiply(total);
            d.setAllocAmount(amt);
          }
        }
      }
    }

    // 对 每一个 待分摊 数据
    // 计算 待分摊总额 和 汇总的分摊金额 的 差异
    // 将 差异，计入第一个分摊明细中
    for (GrandItem g : grandList) {
      BigDecimal total = g.getTotal();

      BigDecimal subTotal = new BigDecimal(0.0);
      BigDecimal pectSubTotal = new BigDecimal(0.0);
      for (DetailItem d : detailList) {
        if (d.getKey1().compareToIgnoreCase(g.getKey1()) == 0) {
          subTotal = subTotal.add(d.getAllocAmount());
          pectSubTotal = pectSubTotal.add(d.getAllocPect());
        }
      }

      // 没有待分摊数据，或待分摊数据为零，则不分摊
      if (subTotal.compareTo(BigDecimal.ZERO) == 0) {
        continue;
      }

      BigDecimal diff = total.subtract(subTotal);
      BigDecimal pectDiff = BigDecimal.ONE.subtract(pectSubTotal);

      for (DetailItem d : detailList) {
        if (d.getKey1().compareToIgnoreCase(g.getKey1()) == 0) {
          BigDecimal already = d.getAllocAmount();
          d.setAllocAmount(already.add(diff));

          BigDecimal pectAlready = d.getAllocPect();
          d.setAllocPect(pectAlready.add(pectDiff));

          break;
        }
      }
    }

    // 将结果打印出来
    grandList.forEach(System.out::println);
    detailList.forEach(System.out::println);
  }


  // todo refactor using stream API
  public void calcStream() {
    // 使用 gruva 的 immutable 集合类

    // 循环 每个待分摊数据，处理 分摊明细
    // filter -> map -> reduce
    // 得到 待分摊的汇总

    // 对 分摊明细，根据 待分摊汇总 与 待分摊明细，计算 分摊比例，分摊到的金额
    // map

    // 将 尾差 处理掉

  }

}

