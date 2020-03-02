package pickup.allocation;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by YongGang on 2017/3/3.
 */
public class testSimple {
  @Before
  public void setUp() {

  }

  @Test
  public void calc_test() {
    SimpleStrategy sim = new SimpleStrategy();

    sim.calcWrapper();
  }
}
