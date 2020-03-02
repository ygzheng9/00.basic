package pickup.pattern.decorator;

/**
 * Created by yonggang on 6/11/16.
 */
public class SpicyNoodleDecorator extends NoodleDecorator {
    public SpicyNoodleDecorator(Noodle shape) {
        super(shape);
    }

    @Override
    public void cook() {
        System.out.println("Spicy Receipt....add 1/2/3....");
        super.cook();
        System.out.println("add spicy....and others...");
    }
}
