package pickup.pattern.factory;

/**
 * Created by YongGang on 2017/3/8.
 */
public class XiaoMiFactory extends AnotherFactory {
  @Override
  public MobilePhone createMobilePhone(String type) {
    return new MobilePhone(type);
  }

  @Override
  public Television createTelevision(String type) {
    return new Television(type);
  }
}
