package pickup.voucher;

/**
 * Created by yonggang on 27/11/16.
 */
public interface VouPayload {
    public String KEY1 = "KEY1";
    public String KEY2 = "KEY2";

    // 返回: 业务数据对应的凭证逻辑的索引值, 用以在表格中查找凭证生成程序
    public String getVouKey(String type);
}
