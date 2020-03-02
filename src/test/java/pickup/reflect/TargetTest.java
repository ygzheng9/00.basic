package pickup.reflect;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by yonggang on 2/12/16.
 */

public class TargetTest {
    @Before
    public void setUp() {

    }

    @Test
    public void doRun_test() {
        try {
            Class clz = Class.forName("pickup.reflect.Target");

            System.out.println(clz);

            Method mt = clz.getMethod("doRun", new Class[]{String.class, String.class, String.class});

            System.out.println(mt);

            Target test = (Target) clz.newInstance();

            System.out.println(mt.invoke(test, new Object[]{"i love you ", "gogo ", "i fuck you",}));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
