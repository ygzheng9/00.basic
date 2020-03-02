package pickup.pattern.factory;

/**
 * Created by YongGang on 2017/3/8.
 */
public abstract class Factory {

  protected abstract Product createProduct(String owner);

  protected abstract void registerProduct(Product p);


  public final Product create(String owner) {
    Product p = this.createProduct(owner);
    this.registerProduct(p);
    return p;
  }
}
