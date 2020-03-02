package pickup.tryGuice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import pickup.tryGuice.DiscountGuiceModule.DiscountOption;

/**
 * Created by YongGang on 2017/2/2.
 */

@Singleton
public class CartDiscountFactory implements DiscountFactory {
  final Map<DiscountOption, Discountable> discountableBinder;

  @Inject
  public CartDiscountFactory(Map<DiscountOption, Discountable> mapBinder) {
    this.discountableBinder = mapBinder;
  }

  @Override
  public Discountable getDiscount(ShoppingCart cart) {
    int currentHour = cart.getTimeOfCheckout().getHour();

    if (isEarlyMorning(currentHour)) {
      return discountableBinder.get(DiscountOption.EarlyBird);
    } else if (isLateAtNight(currentHour)) {
      return discountableBinder.get(DiscountOption.NightOwl);
    }

    return discountableBinder.get(DiscountOption.None);
  }

  /********************************************************/
  /************************ PRIVATE ***********************/
  /********************************************************/

  private boolean isEarlyMorning(int currentHour) {
    return (currentHour >= 5 && currentHour <= 9);
  }

  private boolean isLateAtNight(int currentHour) {
    return (currentHour >= 0 && currentHour <= 4);
  }
}
