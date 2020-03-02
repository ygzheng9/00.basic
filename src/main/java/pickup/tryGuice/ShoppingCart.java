package pickup.tryGuice;

import java.time.LocalTime;

/**
 * Created by YongGang on 2017/2/2.
 */
public class ShoppingCart {
  private LocalTime timeOfCheckout;
  private double cartTotal;

  public double getCartTotal() {
    return cartTotal;
  }

  public void setCartTotal(double cartTotal) {
    this.cartTotal = cartTotal;
  }

  public LocalTime getTimeOfCheckout() {
    return timeOfCheckout;
  }

  public void setTimeOfCheckout(LocalTime timeOfCheckout) {
    this.timeOfCheckout = timeOfCheckout;
  }
}
