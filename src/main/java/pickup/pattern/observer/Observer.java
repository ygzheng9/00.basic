package pickup.pattern.observer;

/**
 * Created by YongGang on 2017/3/10.
 */

// update 的参数是【被观察对象 Observable】;
// 被观察对象 也是有规范的，这样 update 的实现中，才能使用，这个规范的意思就是 interface
// 被观察对象 主动 调用这里的 update
public interface Observer {
  public abstract void update(Observable generator);
}
