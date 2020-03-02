package pickup.tryGuice;

/**
 * Created by YongGang on 2017/2/1.
 */
public class EarlyBirdDiscount implements Discountable {

  @Override
  public double getDiscount() {
    return 0.25;
  }
}
