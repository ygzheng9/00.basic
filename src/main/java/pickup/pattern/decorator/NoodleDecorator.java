package pickup.pattern.decorator;

/**
 * Created by yonggang on 6/11/16.
 */
public abstract class NoodleDecorator implements Noodle {
    protected Noodle decoratorShape;

    public NoodleDecorator(Noodle shape) {
        this.decoratorShape = shape;
    }

    @Override
    public void cook() {
        this.decoratorShape.cook();
    }
}
