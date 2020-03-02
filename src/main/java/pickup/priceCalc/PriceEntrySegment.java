package pickup.priceCalc;

import java.math.BigDecimal;

/**
 * Created by yonggang on 25/11/16.
 */

// 分段计价的价目表中的一段；
// 每一段：5以内 20块，10以内 15块，20以内 12块，超过 20 10块；
public class PriceEntrySegment {
    private Integer limit;
    private Integer lower;

    private Integer base = 1;;
    private BigDecimal price;

    // 根据传入的数量，计算当前价格段对应的金额
    // 全额，0，部分金额；
    public BigDecimal calc(Integer quantity) {
        Integer range = 0;

        Integer ground = this.getLower();
        Integer celling = this.getLimit();

        if (quantity < ground) {
            // 比下限小
            range = 0;
        } else if (quantity <= celling) {
            // 上下限之间
            range = quantity - ground;
        } else {
            // 比上限大
            range = celling - ground;
        }

        BigDecimal qty = new BigDecimal(range.toString());
        BigDecimal divisor = new BigDecimal(this.getBase().toString());

        return this.getPrice().multiply(qty).divide(divisor);
    }

    // 在计算费用前, 按照升序, 对分段计费排序;
    public int byLimitASC(final PriceEntrySegment s) {
        return this.getLimit() - s.getLimit();
    }

    public PriceEntrySegment(BigDecimal price) {
        this.limit = Integer.MAX_VALUE;
        this.price = price;
    }

    public PriceEntrySegment(int price) {
        this.limit = Integer.MAX_VALUE;
        this.price = new BigDecimal(price);
    }

    public PriceEntrySegment(Integer limit, BigDecimal price) {
        this.limit = limit;
        this.price = price;
    }

    public PriceEntrySegment(Integer limit, int price) {
        this.limit = limit;
        this.price = new BigDecimal(price);
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getBase() {
        return base;
    }

    public void setBase(Integer base) {
        this.base = base;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Integer getLower() {
        return lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }
}
