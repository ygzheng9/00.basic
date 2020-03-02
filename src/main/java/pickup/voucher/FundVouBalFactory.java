package pickup.voucher;

import java.util.List;
import java.util.Optional;

/**
 * Created by yonggang on 27/11/16.
 */
@Deprecated
public class FundVouBalFactory {
//    public <T> Optional<VouGenerator> getGenerator(T reg, List<VouGeneratorEntry> inlists, String className) {
//        return VouGeneratorUtil.getGenerator(className, reg.getType(), inlists);
//    }
//
//    public <T> List<VouItem> run(T reg, String className) {
//        List<VouGeneratorEntry> genMap = VouGeneratorUtil.init();
//
//        Optional<VouGenerator> gen = this.getGenerator(reg, genMap, className);
//
//        if (gen.isPresent()) {
//            return gen.get().run(reg);
//        }
//
//        return null;
//    }

//    public  Optional<VouGenerator> getGenerator(FundBalance reg, List<VouGeneratorEntry> inlists) {
//        return VouGeneratorUtil.getGenerator(FundBalance.class.getSimpleName(), reg.getType(), inlists);
//    }
//
//    public List<VouItem> run(FundBalance reg, String className) {
//        List<VouGeneratorEntry> genMap = VouGeneratorUtil.init();
//
//        Optional<VouGenerator> gen = this.getGenerator(reg, genMap);
//
//        if (gen.isPresent()) {
//            return gen.get().run(reg);
//        }
//
//        return null;
//    }


    public  static Optional<VouGenerator> findGen(VouPayload payload, List<VouGeneratorEntry> inlists) {
        return VouGeneratorUtil.getGenerator(payload.getVouKey(VouPayload.KEY1), payload.getVouKey(
            VouPayload.KEY2), inlists);
    }

    public static List<VouItem> generate(VouPayload payload) {
        List<VouGeneratorEntry> genMap = VouGeneratorUtil.init();

        Optional<VouGenerator> gen = FundVouBalFactory.findGen(payload, genMap);

        if (gen.isPresent()) {
            return gen.get().run(payload);
        }

        return null;
    }
}
