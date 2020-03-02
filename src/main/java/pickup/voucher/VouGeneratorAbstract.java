package pickup.voucher;

import pickup.voucher.config.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yonggang on 3/12/16.
 */


public abstract class VouGeneratorAbstract implements VouGenerator {

  @Deprecated
  protected List<VouItem> genVouItems(String key) throws Exception {
    List<VouItem> rtnLists = new ArrayList<>();

    // 初始化分类配置
    List<VouRule> allRules = VouRuleBuilder.initVouRules();
    List<VouRuleItemCellProp> cellConfs = VouRuleBuilder.initVouItemCellProp();

    // 根据 key 找到配置
    Optional<VouRule> rule = allRules.stream()
                                     .filter(r -> r.getKey1().equalsIgnoreCase(key))
                                     .findFirst();

    if (rule.isPresent()) {
      List<VouRuleItem> items = rule.get().getItems();

      for (VouRuleItem it : items) {
        // 对每一个分录的配置, 生成一条分录
        VouItem newItem = new VouItem();

        // 每一行分录的每个栏位,都是有一个配置项
        List<VouRuleItemCell> cells = it.getCells();

        for (VouRuleItemCell cell : cells) {
          // 分录栏位的属性名字
          String propName = cell.getPropName();

          // 根据属性名字, 找到对应的 setter, 以及 setter 的 param.type
          Optional<VouRuleItemCellProp> cellConf = cellConfs.stream()
                                                            .filter(c -> c.getPropName()
                                                                          .equalsIgnoreCase(
                                                                              propName))
                                                            .findFirst();

          if (cellConf.isPresent()) {
            VouRuleItemCellProp conf = cellConf.get();

            String paramType = UtilHelper.getFullClassName(conf.getParamType());

            Method setter = VouItem.class.getMethod(conf.getSetterFunc(),
                Class.forName(paramType));

            String getterFunc = cell.getGetterFunc();

            if (UtilHelper.isFuncName(getterFunc)) {
              // 属性的计算方法
              Method getter = this.getClass()
                                  .getMethod(UtilHelper.getActualFuncName(getterFunc));
              setter.invoke(newItem, getter.invoke(this));
            } else {
              setter.invoke(newItem, getterFunc);
            }
          } else {
            System.out.println("Can not find config for: " + cell);
          }
        }

        rtnLists.add(newItem);
      }
    } else {
      System.out.println("Can not find rules for: " + key);
    }

    return rtnLists;
  }

  // 根据业务类型(key)的凭证分录配置，生成凭证分录
  // 因为配置了分录中的一个一个栏位的取数规则，所以，就对分录中的一个一个栏位，进行赋值；
  // 赋值时，如果配置是常量，就直接赋值；
  // 如果配置是一个 func，则需要先调用这个 func，取得值，再把这个值，给凭证分录的栏位；
  // func 的 target 就是 this，也即 凭证生成器；
  // 凭证生成器 是 待生成凭证数据 的一个 wrapper，将 func 请求转给内部的 待生成凭证数据；
  // 限制：只能处理一笔业务数据，对应多个凭证分录的情况，比如：资金登记，资金转出，收入成本记账；基于发票的记账等；
  // 不能处理打包的情况：也即：不同行项目，打包成一个面单，需要对这个面单做凭证；根据行项目类型不同，分录不一样；
  // 不能处理比如：代收代付的托单；多笔业务打包成多张发票；多笔发票与多笔资金的核销；

  protected List<VouItem> pumpVouItems(String key) {
    List<VouItem> rtnLists = new ArrayList<>();

    // 初始化分录配置
    // TODO 应该 Inject 进来，而不是每次创建
    List<VouRule> allRules = VouRuleBuilder.buildVouRules();
    List<VouRuleItemCellProp> propList = VouRuleBuilder.initVouItemCellProp();

    // 根据 key 找到配置
    Optional<VouRule> rule = allRules.stream()
                                     .filter((r) -> r.getKey1().equalsIgnoreCase(key))
                                     .findFirst();

    if (rule.isPresent()) {
      List<VouRuleItem> items = rule.get().getItems();

      for (VouRuleItem item : items) {
        // 对每一个分录的配置, 生成一条分录
        VouItem newItem = new VouItem();

        // 根据分录的每一个属性的配置规则，对分录的属性赋值（同一个分录 newItem 的不同属性）
        for (VouRuleItemCellProp config : propList) {
          String prop = config.getPropName();
          String propVal = UtilHelper.getPropValue(item, prop);

          // 如果设置了属性值，则需要使用 setter 对 newItem 进行赋值；
          // 如果没有设置，newItem 中保持空；
          if (propVal.length() > 0) {
            // 取得属性的类型
            String paramType = UtilHelper.getFullClassName(config.getParamType());

            try {
              // 取得分录中，该属性的 setter 函数
              Method setter = VouItem.class.getMethod(UtilHelper.setterFunc(prop),
                  Class.forName(paramType));

              if (UtilHelper.isFuncName(propVal)) {
                // 属性值是一个函数，则需要先通过 getter，取得属性值，然后再对 newItem 赋值；
                Method getter = this.getClass()
                                    .getMethod(UtilHelper.getActualFuncName(propVal));
                setter.invoke(newItem, getter.invoke(this));
              } else {
                // 分录规则中，该栏位是个固定的值，直接赋值；
                setter.invoke(newItem, propVal);
              }
            } catch (ClassNotFoundException e) {
              System.out.println("Can not find class: " + paramType);
              e.printStackTrace();
            } catch (NoSuchMethodException e) {
              System.out.println("method can not find: " + UtilHelper.getActualFuncName(propVal));
              e.printStackTrace();
            } catch (IllegalAccessException e) {
              System.out.println("can not access method.");
              e.printStackTrace();
            } catch (InvocationTargetException e) {
              System.out.println("target is wrong. ");
              e.printStackTrace();
            }
          }
        }
        rtnLists.add(newItem);

      }
    } else {
      System.out.println("Can not find rules for: " + key);
    }

    return rtnLists;
  }
}
