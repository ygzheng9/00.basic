package pickup.pattern.decorator;

/**
 * Created by yonggang on 6/11/16.
 */

public class RiceNoodle implements Noodle {
    @Override
    public void cook() {
        System.out.println("RiceNoodle.");
    }
}
