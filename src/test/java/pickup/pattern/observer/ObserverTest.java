package pickup.pattern.observer;

import org.junit.Before;
import org.junit.Test;


/**
 * Created by YongGang on 2017/3/10.
 */
public class ObserverTest {

  @Before
  public void setUp() {

  }

  @Test
  public void observer_test() {
    Observable target = new RandomObservable();

    Observer observer1 = new DigitObserver();
    Observer observer2 = new GraphObserver();
    target.addObserver(observer1);
    target.addObserver(observer2);

    target.execute();
  }
}
