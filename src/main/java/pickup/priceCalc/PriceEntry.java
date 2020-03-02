package pickup.priceCalc;

//import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by yonggang on 25/11/16.
 */

// 按（精确+统配），优先级匹配：PriceEntry: 根据多个键值对（key1/2/3）确定的价目入口；
// 分段计价：每个价目入口，又是包含分段计费的 segments；
public class PriceEntry {

  private String key1;
  private String key2;
  private String key3;

  static final public String DEFAULT = "*";

  private List<PriceEntrySegment> segments = new ArrayList<>();

  // key1，key2 精确匹配，返回 true；其余返回 false
  public boolean isMatch(String key1, String key2) {
    boolean rtn = false;

    if (this.getKey1().compareToIgnoreCase((key1)) == 0 &&
        this.getKey2().compareToIgnoreCase((key2)) == 0) {
      rtn = true;
    }

    return rtn;
  }

  public boolean isMatch(PriceEntry l) {
    return this.isMatch(l.getKey1(), l.getKey2());
  }

//    public List<PriceEntry> findAllMatch(final List<PriceEntry> inLines) {
//        return inLines.stream()
//                .filter((line) -> this.isMatch(line))
//                .collect(Collectors.toList());
//    }

  // 价目表是分段的 this.segments，然后计算 quantity 对应的总额
  public BigDecimal calcAmt(Integer quantity) {

    // 按升序
    this.segments.sort(PriceEntrySegment::byLimitASC);

    int cnt = this.segments.size();

    // 由于初始只有 upper, 没有 lower, 所以, 此处先取得每段的 lower;
    // 然后, 对于每段, 计算费用金额;
    // 最后, 再汇总;
    BigDecimal result =
        IntStream.range(0, cnt).mapToObj((i) -> {
          PriceEntrySegment seg = segments.get(i);

          if (i == 0) {
            seg.setLower(0);
          } else {
            seg.setLower(segments.get(i - 1).getLimit());
          }
          return seg;
        })
                 .map((PriceEntrySegment s) -> s.calc(quantity))
                 .reduce(BigDecimal.ZERO, BigDecimal::add);

    return result;
  }

  // 作用是：如果是分段计价的，根据传入的数量，计算总金额；
  // 比如：5 个以内，每个 10 块钱；20 个以内，每个 8 块钱；50 个以内，每个 5 块钱；再超过，每个 3 块钱
  @Deprecated
  public BigDecimal calc(Integer quantity) {
    this.segments.sort(PriceEntrySegment::byLimitASC);

    int cnt = this.segments.size();

    BigDecimal total = BigDecimal.ZERO;

    for (int i = 0; i < cnt; i++) {
      PriceEntrySegment curr = segments.get(i);

      // 每一个价格段的上限
      Integer upper = curr.getLimit();

      // 每一个价格段的下限，
      // 第一段的下限是 0，后面每段的下限，就是上一段的上限；
      Integer lower = 0;
      if (i > 0) {
        lower = segments.get(i - 1).getLimit();
      }

      // 每 divider 个 price 钱
      BigDecimal divider = new BigDecimal(curr.getBase().toString());
      BigDecimal price = curr.getPrice();

      // 如果 quantity 比这一段的上限还大，那么，也就是说，这一段是全额
      if (upper < quantity) {
        Integer diff = upper - lower;
        BigDecimal q = new BigDecimal(diff.toString());
        total = total.add(price.multiply(q).divide(divider, 2));
      } else {
        // 在这一段之内，那么 quantity 和 下限 就是这一段对应的金额；
        // 不需要再找更高的价格段了
        Integer diff = quantity - lower;
        BigDecimal q = new BigDecimal(diff.toString());
        total = total.add(price.multiply(q).divide(divider, 2));
        break;
      }
    }

    return total;
  }

  // 当前对象，如果在 inLines 中能按照 key1, key2 匹配上，则返回 inLines 中第一个匹配上的对象；否则返回 null；
  public PriceEntry match(final List<PriceEntry> inLines) {
    List<PriceEntry> prices = inLines.stream()
                                     .filter((line) -> this.isMatch(line))
                                     .collect(Collectors.toList());

    if (prices.size() == 0) {
      return null;
    }

    return prices.get(0);
  }

  public String combineAllKeys() {
    return this.getKey1() + "-" + this.getKey2() + "-" + this.getKey3();
  }

  public PriceEntry(String key1) {
    this.key1 = key1;
    this.key2 = this.DEFAULT;
    this.key3 = this.DEFAULT;
  }


  public PriceEntry(String key1, String key2) {
    this.key1 = key1;
    this.key2 = key2;
    this.key3 = this.DEFAULT;
  }

  public PriceEntry(String key1, String key2, int price) {
    this.key1 = key1;
    this.key2 = key2;
    this.key3 = this.DEFAULT;

    PriceEntrySegment line = new PriceEntrySegment(price);
    segments.add(line);
  }

  public PriceEntry(String key1, String key2, String key3) {
    this.key1 = key1;
    this.key2 = key2;
    this.key3 = key3;
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

  public String getKey3() {
    return key3;
  }

  public void setKey3(String key3) {
    this.key3 = key3;
  }

  public void clearSegments() {
    segments.clear();
  }

  public void addSegment(PriceEntrySegment line) {
    segments.add(line);
  }

  @Override
  public String toString() {
    return this.getKey1() + " " + this.getKey2();
  }
}
