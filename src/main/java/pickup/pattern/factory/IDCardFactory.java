package pickup.pattern.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YongGang on 2017/3/8.
 */
public class IDCardFactory extends Factory {
  private List<String> owners = new ArrayList<>();

  @Override
  protected Product createProduct(String owner) {
    return new IDCard(owner);
  }

  @Override
  protected void registerProduct(Product p) {
    IDCard card = (IDCard)p;
    this.owners.add(card.getOwner());
  }

  public List<String> getOwners() {
    return this.owners;
  }
}
