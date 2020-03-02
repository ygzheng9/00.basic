package pickup.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by YongGang on 2017/3/9.
 */
public class PersonConsumer {

  public static void main(String[] argv) {
    // Collectors 有很多函数，toList，toSet，toMap
    Map<String, String> map = Stream.of("AA", "BB", "CC")
                                    .collect(Collectors.toMap(k -> k, v -> v + v));
    map.forEach((k, v) -> System.out.println("key:" + k + "  value:" + v));

    List<Person> list = Person.getList();

    // listagg：按照 age 做 group，然后把 name 用 逗号 拼接起来；
    Map<Integer, String> nameByAge =
        list.stream()
            .collect(
                Collectors.groupingBy(Person::getAge,
                    Collectors.mapping(Person::getName, Collectors.joining(","))));
    nameByAge.forEach((k, v) -> System.out.println("Age:" + k + "  Persons: " + v));

    // group by multiple fields
    Stream<Person> people = Stream.of(new Person("Paul", 24, 20000),
        new Person("Mark", 30, 30000),
        new Person("Will", 28, 28000),
        new Person("William", 28, 28000),
        new Person("Mark", 30, 33000));

    // Map<Integer, List<Person>> peopleByAge = people
    //     .collect(Collectors.groupingBy(Person::getAge));
    //
    // System.out.println(peopleByAge);


    // 按两个属性 group by，结果还是一个 map，但是 key 是 list，只包含两个元素；
    Map<List, List<Person>> byNameAndAge =
        people.collect(Collectors.groupingBy(p -> Arrays.asList(p.getName(), p.getAge())));

    System.out.println(byNameAndAge);

  }
}
