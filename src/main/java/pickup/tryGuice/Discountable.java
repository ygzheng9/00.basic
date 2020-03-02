package pickup.tryGuice;

import com.google.inject.ImplementedBy;

/**
 * Created by YongGang on 2017/2/1.
 */

// 优先级最低；
//@ImplementedBy(NightOwlDiscount.class)
public interface Discountable {
  double getDiscount();

}
