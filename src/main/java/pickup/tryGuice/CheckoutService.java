package pickup.tryGuice;

import com.google.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by YongGang on 2017/2/1.
 */
public class CheckoutService {
//  private final Discountable discountablentable;
  //private final Provider<Discountable> discountable;
  private final Logger logger;

  private final DiscountFactory discountFactory;

  @Inject
  private CheckoutService(DiscountFactory discountFactory, Logger logger) {
    this.discountFactory = discountFactory;
    this.logger = logger;
  }

  public double checkout(ShoppingCart cart) {
    Discountable discountable = discountFactory.getDiscount(cart);

    double discount = discountable.getDiscount();
    double total = cart.getCartTotal();

    double totalAfterDiscount = total - (total * discount);
    System.out.printf("%nShopping cart initially [$%.2f] with a discount of %.2f%% = [$%.2f]%n%n",
        total,
        discount * 100,
        totalAfterDiscount);

    logger.info("checkout finished. ");

    return totalAfterDiscount;
  }
}
