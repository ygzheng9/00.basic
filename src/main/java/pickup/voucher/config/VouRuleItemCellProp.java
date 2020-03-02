package pickup.voucher.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonggang on 3/12/16.
 */

// 凭证分录每个栏位的逻辑配置：栏位名，对应的函数名，参数类型
public class VouRuleItemCellProp {

  private String propName;
  private String setterFunc;
  private String paramType;

  public VouRuleItemCellProp(String propName, String setterFunc, String paramType) {
    this.propName = propName;
    this.setterFunc = setterFunc;
    this.paramType = paramType;
  }

  public String getPropName() {
    return propName;
  }

  public void setPropName(String propName) {
    this.propName = propName;
  }

  public String getSetterFunc() {
    return setterFunc;
  }

  public void setSetterFunc(String setterFunc) {
    this.setterFunc = setterFunc;
  }

  public String getParamType() {
    return paramType;
  }

  public void setParamType(String paramType) {
    this.paramType = paramType;
  }

  @Override
  public String toString() {
    return "VouRuleItemCellProp{" +
        "propName='" + propName + '\'' +
        ", setterFunc='" + setterFunc + '\'' +
        ", paramType='" + paramType + '\'' +
        '}';
  }
}
