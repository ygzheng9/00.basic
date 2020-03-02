package pickup.voucher;

import java.util.List;

/**
 * Created by yonggang on 26/11/16.
 */


public interface VouGenerator {
    // 每种业务活动+类型的凭证生成逻辑
    public List<VouItem> run(Object bizData);
}
