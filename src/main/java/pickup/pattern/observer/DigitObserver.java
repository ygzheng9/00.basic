package pickup.pattern.observer;

/**
 * Created by YongGang on 2017/3/10.
 */
public class DigitObserver implements Observer {

  @Override
  public void update(Observable generator) {
    System.out.println("DigitObserver:" + generator.getNumber());
  }
}
