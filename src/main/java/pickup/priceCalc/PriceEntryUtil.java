package pickup.priceCalc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yonggang on 25/11/16.
 */

// 根据键值对，在价目表中，找到对应的价目；（价目对应的是一个分段计价的表格）
public class PriceEntryUtil {

  // 返回空的价目表
  public static List<PriceEntry> create() {
    List<PriceEntry> priceTable = new ArrayList<>();
    return priceTable;
  }


  // 返回重复出现的键值
  public static List<String> findDup(final List<PriceEntry> inlines) {
    // 按所有 key, 做 group
    Map<String, List<PriceEntry>> keyList = inlines.stream()
                                                   .collect(Collectors
                                                      .groupingBy(PriceEntry::combineAllKeys));

    // rtn 中是重复的 key
    List<String> rtn = keyList.entrySet().stream()
                              .filter((mapEntry) -> mapEntry.getValue().size() > 1)
                              .map(Map.Entry::getKey)
                              .collect(Collectors.toList());

    return rtn;
  }

  // 根据键值，找到价目表中的入口；
  public static PriceEntry findPrice(final List<PriceEntry> inLines, final String key1,
      final String key2) {

    // 顺序重要,排在前面的优先级高;
    // 先是精确匹配 key1, key2;
    // 然后是 key1 匹配，key2 通配；
    // 然后是 key2 匹配，key1 通配；
    // 最后是 key1，key2 都通配；
    List<PriceEntry> conditions = new ArrayList<>();
    conditions.add(new PriceEntry(key1, key2));
    conditions.add(new PriceEntry(key1, PriceEntry.DEFAULT));
    conditions.add(new PriceEntry(PriceEntry.DEFAULT, key2));
    conditions.add(new PriceEntry(PriceEntry.DEFAULT, PriceEntry.DEFAULT));

    PriceEntry price = null;

    // 遍历 conditions，也即：conditions 中对象的先后顺序，就是匹配的优先级；
    // 只要匹配到了，就不再做后续的匹配了；
    for (PriceEntry cond : conditions) {
      price = cond.match(inLines);

      if (null != price) {
        break;
      }
    }

    return price;
  }

//    public static void test (final List<PriceEntry> priceList,
//        final String key1, final String key2) {
//
//        System.out.println("try to find " + key1 + " " + key2);
//
//        List<PriceEntry> prices = PriceEntryUtil.match(priceList, key1, key2);
//
//        List<String> msgList = new ArrayList<String> ();
//
//        int cnt = prices.size();
//        if (1 == cnt) {
//            System.out.println("got it");
//
//            PriceEntry price = prices.get(0);
//
//            System.out.println(price.toString());
//        } else if (0 == cnt) {
//            msgList.add("can not find price for " + key1 + " " + key2);
//        } else {
//            msgList.add("find multiple price for " + key1 + " " + key2);
//
//            prices.stream().forEach( (p) ->
// msgList.add("got " + p.getKey1() + "/" + p.getKey2()) );
//        }
//
//        msgList.stream().forEach(System.out::println);
//    }
}
