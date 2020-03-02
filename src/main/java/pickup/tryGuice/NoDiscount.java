package pickup.tryGuice;

/**
 * Created by YongGang on 2017/2/2.
 */
public class NoDiscount implements Discountable {

  @Override
  public double getDiscount() {
    return 0D;
  }
}
