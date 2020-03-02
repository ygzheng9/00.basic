package pickup.tryGuice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.Map;
import java.util.Random;

/**
 * Created by YongGang on 2017/2/2.
 */
public class DiscountableProvider implements Provider<Discountable> {

  private final Random random;
  private final Map<Integer, Discountable > mapBinder;

  @Inject
  public DiscountableProvider(Map<Integer, Discountable> mapBinder, Random random) {
    this.mapBinder = mapBinder;
    this.random = random;
  }

  @Override
  public Discountable get() {
    return mapBinder.get(random.nextInt(mapBinder.size()));
  }

//  private boolean isEarlyMorning(int currentHour) {
//    return (currentHour >= 5 && currentHour <= 8);
//  }
//
//  private boolean isLateAtNight(int currentHour) {
//    return (currentHour >= 0 && currentHour <= 4);
//  }
//
//  @Override
//  public Discountable get() {
//    int hour = LocalTime.now().getHour();
//
//    if (isEarlyMorning(hour)) {
//      return new EarlyBirdDiscount();
//    } else if (isLateAtNight(hour)) {
//      return new NightOwlDiscount();
//    }
//
//    return new None();
//  }
}
