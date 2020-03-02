package pickup.voucher;

import pickup.fundMgmt.FundBalance;
import pickup.fundMgmt.FundReg;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yonggang on 26/11/16.
 */
public class VouGeneratorUtil {

  // 每种凭证生成逻辑
  private static VouGenerator normalGen = new FundVouGeneratorNormal();
  private static VouGenerator advancedGen = new FundVouGeneratorAdvanced();
  private static VouGenerator relatedGen = new FundVouGeneratorRelated();
  private static VouGenerator balGen = new FundVouGeneratorBal();

  // 为每种类型,设置处理程序
  // TODO 应该做为一个 bean，注册到容器中去
  private static List<VouGeneratorEntry> genMap = null;

  // 这是个 singleton
  public static List<VouGeneratorEntry> init() {
    if (null == genMap) {
      genMap = new ArrayList<VouGeneratorEntry>();
      genMap.add(new VouGeneratorEntry(FundReg.class.getSimpleName(), "NORMAL", normalGen));
      genMap.add(new VouGeneratorEntry(FundReg.class.getSimpleName(), "ADVANCED", advancedGen));
      genMap.add(new VouGeneratorEntry(FundReg.class.getSimpleName(), "RELATED", relatedGen));
      genMap.add(new VouGeneratorEntry(FundBalance.class.getSimpleName(), "BALANCE", balGen));
    }

    return genMap;
  }

  // 按照 keys 查找对应的凭证生成程序
  public static Optional<VouGenerator> getGenerator(String key1, String key2,
      List<VouGeneratorEntry> inlists) {
    // 按照 (key1 + key2), 取得凭证生成程序
    return inlists.stream()
                  .filter(entry -> entry.isMatched(key1, key2))
                  .map(VouGeneratorEntry::getFunc)
                  .findFirst();
  }

  public static Optional<VouGenerator> getGenerator(VouPayload payload,
      List<VouGeneratorEntry> inlists) {
    String key1 = payload.getVouKey(VouPayload.KEY1);
    String key2 = payload.getVouKey(VouPayload.KEY2);

    return VouGeneratorUtil.getGenerator(key1, key2, inlists);
  }

  // payload 是待记账数据
  // 首先，根据【待记账数据】的业务类型，找到对应的 【凭证生成器】
  // 然后，把【待记账数据】，传给【凭证生成器】，生成凭证
  public static List<VouItem> run(VouPayload payload) {
    // 初始化表格
    List<VouGeneratorEntry> genMap = VouGeneratorUtil.init();

    // 根据业务数据的类型,找到对应的凭证生成程序
    Optional<VouGenerator> gen = VouGeneratorUtil.getGenerator(payload, genMap);

    // 生成凭证行项目
    if (gen.isPresent()) {
      return gen.get().run(payload);
    } else {
      System.out.println("not found proc for: " + payload);
    }

    return (new ArrayList<VouItem>());
  }
}
