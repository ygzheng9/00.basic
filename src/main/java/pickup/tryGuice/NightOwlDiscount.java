package pickup.tryGuice;

/**
 * Created by YongGang on 2017/2/1.
 */
public class NightOwlDiscount implements Discountable {

  @Override
  public double getDiscount() {
    return 0.35;
  }
}
