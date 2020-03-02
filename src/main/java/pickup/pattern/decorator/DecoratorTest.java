package pickup.pattern.decorator;

/**
 * Created by yonggang on 6/11/16.
 */
public class DecoratorTest {
    public static void main(String[] args) {
        System.out.println("Rice Noodle");
        RiceNoodle riceNdl = new RiceNoodle();
        riceNdl.cook();
        System.out.println();

        System.out.println("Spicy Noodle");
        SpicyNoodleDecorator spicyDecorator = new SpicyNoodleDecorator(riceNdl);
        spicyDecorator.cook();
        System.out.println();

        System.out.println("Spicy + Beef Noodle");
        BeefNoodleDecorator beefDecorator = new BeefNoodleDecorator(spicyDecorator);
        beefDecorator.cook();
        System.out.println();

        System.out.println("Spicy + Beef + Beef Noodle");
        BeefNoodleDecorator anotherDecorator = new BeefNoodleDecorator(beefDecorator);
        anotherDecorator.cook();
        System.out.println();
    }
}
