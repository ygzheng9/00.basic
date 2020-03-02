package pickup.lambda;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by YongGang on 2016/10/30.
 */
public class ItemConsumer {
    public static void main(String[] args) {

        //3 apple, 2 banana, others 1
        List<Item> items = Arrays.asList(
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("apple", 20, new BigDecimal("11.99")),
                new Item("apple", 10, new BigDecimal("9.99")),
                new Item("apple", 20, new BigDecimal("9.99")),
                new Item("apple", 10, new BigDecimal("10.99")),
                new Item("orang", 10, new BigDecimal("29.99")),
                new Item("watermelon", 10, new BigDecimal("29.99")),
                new Item("papaya", 20, new BigDecimal("9.99")),
                new Item("banana", 10, new BigDecimal("19.99")),
                new Item("banana", 20, new BigDecimal("19.99")),
                new Item("banana", 10, new BigDecimal("23.99")),
                new Item("papaya", 20, new BigDecimal("12.99"))
        );

        // select name, count(1) from items group by name;
        Map<String, Long> counting = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.counting()));

        System.out.println(counting);
        // {banana=3, papaya=2, apple=5, orang=1, watermelon=1}

        Map<String, Integer> sum = items.stream().collect(
                Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getQty)));

        System.out.println(sum);
        // {papaya=20, banana=30, apple=40, orang=10, watermelon=10}

        // if the name is unique, otherwise throw
//        Map<String, Item> tmp =
//                items.stream().collect(Collectors.toMap(Item::getName,
//                        Function.identity()));
//        System.out.println(tmp);

        // if name is not unique, can only get List<Item> by name.
        Map<String, List<Item>> grp =
                items.stream().collect(Collectors.groupingBy(Item::getName));

        System.out.println(grp);
        // {papaya=[pickup.lambda.Item@50040f0c], banana=[pickup.lambda.Item@2dda6444, pickup.lambda.Item@5e9f23b4], apple=[pickup.lambda.Item@4783da3f, pickup.lambda.Item@378fd1ac, pickup.lambda.Item@49097b5d], orang=[pickup.lambda.Item@6e2c634b], watermelon=[pickup.lambda.Item@37a71e93]}

        //group by price
        Map<BigDecimal, List<Item>> groupByPriceMap =
                items.stream().collect(Collectors.groupingBy(Item::getPrice));

        System.out.println(groupByPriceMap);
        // {19.99=[pickup.lambda.Item@79fc0f2f, pickup.lambda.Item@50040f0c], 29.99=[pickup.lambda.Item@2dda6444, pickup.lambda.Item@5e9f23b4], 9.99=[pickup.lambda.Item@4783da3f, pickup.lambda.Item@378fd1ac, pickup.lambda.Item@49097b5d, pickup.lambda.Item@6e2c634b]}


        // group by price, uses 'mapping' to convert List<Item> to Set<String>
        Map<BigDecimal, Set<String>> result =
                items.stream().collect(
                        Collectors.groupingBy(Item::getPrice,
                                Collectors.mapping(Item::getName, Collectors.toSet())
                        )
                );

        System.out.println(result);
        // {19.99=[banana], 29.99=[orang, watermelon], 9.99=[papaya, apple]}


        // group by ... sum ....
        List<Item> sumQty =
                items.stream().collect(
                        Collectors.groupingBy(Item::getName,
                                Collectors.reducing(0, Item::getQty, Integer::sum)))
                .entrySet().stream()
                .map(e -> new Item(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        sumQty.forEach((itm) -> System.out.println(itm.display()));

        // group by ... sum(col1), max(col2)
        System.out.println("this is a map.");
        Map<String, Item> maxPrice =
                    items.stream().collect(
                        Collectors.toMap(
                                Item::getName,                  // group by
                                Function.identity(),            // returned data structure
                                (s, a) -> { return s.merge(a); } ));    // aggregration functions

        maxPrice.entrySet().forEach((itm) -> System.out.println(itm.getValue().display()));


        System.out.println("convert map to list and sort.");
        List<Item> itmList =
            maxPrice.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

        // order by ...
        itmList.sort(Item::byQtyDesc);
        itmList.forEach((itm) -> System.out.println(itm.display()));

    }
}
