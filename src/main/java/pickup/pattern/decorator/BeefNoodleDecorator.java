package pickup.pattern.decorator;

/**
 * Created by yonggang on 6/11/16.
 */
public class BeefNoodleDecorator extends NoodleDecorator {
    public BeefNoodleDecorator(Noodle shape) {
        super(shape);
    }

    @Override
    public void cook() {
        System.out.println("Beef Receipt....A/B/C....");
        super.cook();
        System.out.println("add beef....and beef and beef...");
    }
}
