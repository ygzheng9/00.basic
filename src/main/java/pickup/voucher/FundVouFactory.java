package pickup.voucher;

import pickup.fundMgmt.FundReg;

import java.util.List;
import java.util.Optional;

/**
 * Created by yonggang on 26/11/16.
 */
@Deprecated
public class FundVouFactory {

    public static Optional<VouGenerator> findGen(FundReg reg, List<VouGeneratorEntry> inlists) {
        return VouGeneratorUtil.getGenerator(FundReg.class.getSimpleName(), reg.getType(), inlists);
    }

    public static List<VouItem> generate(FundReg reg) {
        List<VouGeneratorEntry> genMap = VouGeneratorUtil.init();

        Optional<VouGenerator> gen = FundVouFactory.findGen(reg, genMap);

        if (gen.isPresent()) {
            return gen.get().run(reg);
        }

        return null;
    }
}
