package pickup.tryGuice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;

/**
 * Created by YongGang on 2017/2/1.
 */
public class SimpleApplication {

  private final CheckoutService checkoutService;

  @Inject
  public SimpleApplication(CheckoutService checkoutService) {
    this.checkoutService = checkoutService;
  }

  private void start() {
    while (true) {
      checkoutService.checkout(getNewUserCheckout());
    }
  }

  public static void main(String[] args) {
    Injector guice = Guice.createInjector(new DiscountGuiceModule());
    SimpleApplication application = guice.getInstance(SimpleApplication.class);

    application.start();
  }

  /**********************************************************/
  /****************  Our GUI simulator **********************/
  /**********************************************************/

  private ShoppingCart getNewUserCheckout() {
    ShoppingCart cart = new ShoppingCart();
    cart.setCartTotal(getTotalFromInput());
    cart.setTimeOfCheckout(getCheckoutTimeFromInput());

    return cart;
  }

  private double getTotalFromInput() {
    String total = null;
    System.out.print("Enter cart total: ");

    try {
      BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
      total = bufferRead.readLine();
    } catch (IOException doh) {
    }

    return Double.valueOf(total);
  }

  private LocalTime getCheckoutTimeFromInput() {
    LocalTime checkoutTime = null;
    String hour = null;
    System.out.print("Enter Checkout hour: ");

    try {
      BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
      hour = bufferRead.readLine();
    } catch (IOException doh) {
    }

    return LocalTime.of(Integer.valueOf(hour), 0);
  }
}
