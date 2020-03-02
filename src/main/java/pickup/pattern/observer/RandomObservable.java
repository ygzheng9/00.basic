package pickup.pattern.observer;

import java.util.Random;

/**
 * Created by YongGang on 2017/3/10.
 */
public class RandomObservable extends Observable {

  private Random random = new Random();
  private int number;

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public void execute() {
    for (int i = 0; i < 5; i++) {
      number = random.nextInt(30);
      this.notifyObservers();
    }
  }
}
