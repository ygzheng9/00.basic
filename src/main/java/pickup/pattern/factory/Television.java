package pickup.pattern.factory;

import pickup.lambda.Item;

/**
 * Created by YongGang on 2017/3/8.
 */
public class Television extends ITelevision {

  private String name;
  public Television(String name) {
    this.name=name;
    System.out.println("制作电视"+name);
  }

  @Override
  public void watch() {
    System.out.println("通过"+name+"看电视");
  }
}
