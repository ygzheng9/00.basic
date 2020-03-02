package pickup.tryGuice;

import com.google.inject.ImplementedBy;

/**
 * Created by YongGang on 2017/2/2.
 */

@ImplementedBy(CartDiscountFactory.class)
public interface DiscountFactory {
  public Discountable getDiscount(ShoppingCart cart);
}
