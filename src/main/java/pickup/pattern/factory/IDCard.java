package pickup.pattern.factory;

/**
 * Created by YongGang on 2017/3/8.
 */
public class IDCard extends Product {
  private String owner;

  IDCard(String owner) {
    System.out.println("制作ID卡：" + owner);
    this.owner = owner;
  }

  @Override
  public void use() {
    System.out.println("使用ID卡：" + owner);
  }

  public String getOwner() {
    return this.owner;
  }
}
