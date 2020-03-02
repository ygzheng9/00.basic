package pickup.tryGuava;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pickup.lambda.Author;
import pickup.log;

/**
 * Created by YongGang on 2017/2/4.
 */
public class CollectionTest {

  static public Logger logger = LoggerFactory.getLogger(log.class);

  public static void main(String[] args) {
    CollectionTest test = new CollectionTest();
    // test.fun();
    //
    // test.run();

    // test.BarTest();

    test.twoPlaces();
  }

  public void fun() {
    List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");

    Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
    Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);
    MapDifference<String, Integer> diff = Maps.difference(left, right);

    logger.info(diff.entriesInCommon().toString()); // {"b" => 2}
    diff.entriesDiffering(); // {"c" => (3, 4)}
    diff.entriesOnlyOnLeft(); // {"a" => 1}
    diff.entriesOnlyOnRight(); // {"d" => 5}

  }

  public void run() {
    List<Integer> in = Lists.newArrayList(1,2,3,4,10, 20, 30);

    System.out.println("Before: in: ");
    in.forEach(System.out::println);

    // List<Integer> out = this.change(in);

    ImmutableList<Integer> tmp = ImmutableList.copyOf(in);
    List<Integer> out = this.foo(tmp);

    System.out.println("After: in: ");
    in.forEach(System.out::println);

    System.out.println("After: out: ");
    out.forEach(System.out::println);
  }


  public List<Integer> change(final List<Integer> in) {
    in.remove(2);
    in.add(4);
    in.add(5);

    // in = Lists.newArrayList(100,200);

    return in;
  }

  public List<Integer> foo(ImmutableList<Integer> in) {
    return in.stream().filter( i -> i > 5).collect(Collectors.toList());
  }


  public void BarTest() {
    List<Author> l = Lists.newArrayList(new Author(1,2));

    System.out.println("Before....");
    l.forEach(System.out::println);

    ImmutableList<Author> in = ImmutableList.copyOf(l);

    List<Author> out = this.bar(in);

    System.out.println("After....in");
    in.forEach(System.out::println);

    System.out.println("After....out");
    out.forEach(System.out::println);
  }

  public List<Author> bar(ImmutableList<Author> in) {
    for (Author a : in) {
      a.setSn(999);
    }
    return in;
  }

  public void twoPlaces() {
    Author au = new Author(1,2);
    ImmutableList<Author> ia = ImmutableList.of(au);

    Map<Integer, Author> m = new HashMap<>();
    m.put(1, au);
    m.get(1).setCount(9999);

    ia.forEach(System.out::println);

    Author au2 = new Author(2, 666);

    au2 = au;
    m.put(2, au2);
    m.get(2).setCount(888);

    System.out.println("au: " + au);

    ia.forEach(System.out::println);

    final Author au3 = new Author(3, 777);
    au3.setCount(666);
    System.out.println(au3);


    // Author au3 = ;
    // m.put(2, au2);
    // m.get(2).setSn(888);
    // ia.forEach(System.out::println);

  }
}
