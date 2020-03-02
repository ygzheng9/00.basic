package pickup.pattern.factory;

/**
 * Created by YongGang on 2017/3/8.
 */
public abstract class AnotherFactory {
  public static AnotherFactory getFactory(String classname) {
    AnotherFactory factory = null;
    try {
      factory = (AnotherFactory)Class.forName(classname).newInstance();
    } catch (ClassNotFoundException e) {
      System.err.println("没有找到 " + classname + "类。");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return factory;
  }
  public abstract MobilePhone createMobilePhone(String type);
  public abstract Television createTelevision(String type);
}
