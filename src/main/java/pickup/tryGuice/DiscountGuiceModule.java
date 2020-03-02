package pickup.tryGuice;

/**
 * Created by YongGang on 2017/2/1.
 */

import static pickup.tryGuice.DiscountGuiceModule.DiscountOption.EarlyBird;
import static pickup.tryGuice.DiscountGuiceModule.DiscountOption.NightOwl;
import static pickup.tryGuice.DiscountGuiceModule.DiscountOption.None;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

public class DiscountGuiceModule extends AbstractModule {

//  @Override
//  protected void configure() {
//    bind(Discountable.class).to(NightOwlDiscount.class);
//    bind(Discountable.class).toProvider(DiscountableProvider.class);

//    MapBinder<Integer, Discountable> mapBinder
//        = MapBinder.newMapBinder(
//        binder(),
//        Integer.class, Discountable.class);
//
//    mapBinder.addBinding(0).to(None.class);
//    mapBinder.addBinding(1).to(EarlyBirdDiscount.class);
//    mapBinder.addBinding(2).to(NightOwlDiscount.class);
//
//    bind(Random.class).toInstance(new Random());//does it for you anyways
//
//    bind(Discountable.class).toProvider(DiscountableProvider.class);

//  }

  @Override
  protected void configure() {
    MapBinder<DiscountOption, Discountable> mapBinder
        = MapBinder.newMapBinder(binder(), DiscountOption.class, Discountable.class);

    mapBinder.addBinding(EarlyBird).to(EarlyBirdDiscount.class);
    mapBinder.addBinding(NightOwl).to(NightOwlDiscount.class);
    mapBinder.addBinding(None).to(NoDiscount.class);
  }

  public enum DiscountOption {
    EarlyBird, NightOwl, None;
  }
}

