package pickup.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongGang on 2017/3/9.
 */
public class Person {

  private String name;
  private int age;
  private long salary;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public Person(String name, int age, long salary) {
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("Person{name='%s', age=%d, salary=%d}", name, age, salary);
  }

  public int getAge() {
    return age;
  }

  public static List<Person> getList() {
    List<Person> list = new ArrayList<>();
    list.add(new Person("Ram", 30));
    list.add(new Person("Shyam", 20));
    list.add(new Person("Shiv", 20));
    list.add(new Person("Heidi", 36));
    list.add(new Person("Craig", 36));
    list.add(new Person("Jerry", 20));
    list.add(new Person("Tom", 30));

    return list;
  }
}
