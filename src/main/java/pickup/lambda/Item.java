package pickup.lambda;

import java.math.BigDecimal;

/**
 * Created by YongGang on 2016/10/30.
 */
public class Item {
    private String name;
    private int qty;
    private BigDecimal price;

    public String display() {
        return  ( this.getName() + "\t" + this.getQty() + "\t" + this.getPrice() );
    }

    public Item merge(Item obj) {
        return new Item(this.getName(),
                this.getQty() + obj.getQty(),
                this.getPrice().max(obj.getPrice()));
    }

    public int byQtyDesc(Item obj) {
        return ( this.getQty() - obj.getQty() );
    }

    public Item(String name, int qty, BigDecimal price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public Item(String name, int qty) {
        this.name = name;
        this.qty = qty;
        this.price = BigDecimal.ZERO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
