package pickup.pattern.factory;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by YongGang on 2017/3/8.
 */
public class FactoryTest {

  @Before
  public void setUp() {

  }

  @Test
  public void factory_test() {
    // 一个 product类型，对应一个 factory
    Factory factory = new IDCardFactory();

    // 一个 factory，可以创建多个 product instance，这些 product 是同一类的；
    // 多个 product instance 的信息，存储在 factory 中；
    Product card1 = factory.create("小明");
    Product card2 = factory.create("小红");
    Product card3 = factory.create("小刚");
    card1.use();
    card2.use();
    card3.use();

    IDCardFactory specFac = (IDCardFactory)factory;
    specFac.getOwners().forEach(System.out::println);
  }

  @Test
  public void anotherFactory_test() {
    AnotherFactory factory = AnotherFactory.getFactory("pickup.pattern.factory.XiaoMiFactory");
    IMobilePhone mobilePhone1=factory.createMobilePhone("小米2");
    IMobilePhone mobilePhone2=factory.createMobilePhone("小米5");

    ITelevision television1=factory.createTelevision("小米电视2");
    ITelevision television2=factory.createTelevision("小米电视3");

    mobilePhone1.dial();
    mobilePhone2.dial();

    television1.watch();
    television2.watch();
  }
}
