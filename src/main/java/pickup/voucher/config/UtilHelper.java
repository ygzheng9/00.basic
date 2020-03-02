package pickup.voucher.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yonggang on 3/12/16.
 */
public class UtilHelper {

  public static final String GETTER_PREFIX = "get";
  public static final String SETTER_PREFIX = "set";

  public static final String JAVA_DEFAULT_PKG = "java.lang.";
  public static final char FUNC_PREFIX = '@';


  // 由于自动生成的 getXxxx, setXxx 中，方法名第一个字符需要大写；
  public static String onlyKeepInitialCapital(String s) {
    int len = s.length();

    if (len == 0) {
      return "";
    } else if (len == 1) {
      return s.toUpperCase();
    } else {
      String head = s.substring(0, 1).toUpperCase();
      String remaining = s.substring(1).toLowerCase();
      return head + remaining;
    }
  }

  // 根据属性名，得到自动生成 getter 函数名
  public static String getterFunc(String prop) {
    String p = UtilHelper.onlyKeepInitialCapital(prop);
    return UtilHelper.GETTER_PREFIX + p;
  }

  // 根据属性名，得到自动生成 setter 函数名
  public static String setterFunc(String prop) {
    String p = UtilHelper.onlyKeepInitialCapital(prop);
    return UtilHelper.SETTER_PREFIX + p;
  }

  // 规则中，如果以 @ 开头，则表示是一个函数；
  public static boolean isFuncName(String funcName) {
    return funcName.charAt(0) == FUNC_PREFIX;
  }

  // 对于规则中配置的函数，取得 @ 之后的部分作为真正的函数名
  public static String getActualFuncName(String funcName) {
    return funcName.substring(1);
  }

  // 添加包路径
  public static String getFullClassName(String clsName) {
    return UtilHelper.JAVA_DEFAULT_PKG + clsName;
  }

  // 取得 target.propName
  public static String getPropValue(Object target, String propName) {
    String rtn = "";

    String getterFunc = UtilHelper.getterFunc(propName);

    try {
      Method getter = target.getClass().getMethod(getterFunc);
      Object val = getter.invoke(target);

      if (null != val) {
        rtn = val.toString();
      }
    } catch (NoSuchMethodException e) {
      System.out.println("can not find: " + propName);
    } catch (IllegalAccessException e) {
      System.out.println("can not call: " + propName);
    } catch (InvocationTargetException e) {
      System.out.println("InvocationTargetException: " + target);
    }

    return rtn;
  }
}