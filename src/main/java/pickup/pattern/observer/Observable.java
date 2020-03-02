package pickup.pattern.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YongGang on 2017/3/10.
 */

// 被观察对象的 规范，定义了一些了的接口
// 内部维护 observer 列表；
// 当有改变时，遍历调用每一个 observer.update，把 自己 self 作为参数传入；

public abstract class Observable {

  private List<Observer> observers = new ArrayList<>();

  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  public void deleteObserver(Observer observer) {
    observers.remove(observer);
  }

  public void notifyObservers() {
    observers.forEach((o) -> o.update(this));
  }

  public abstract int getNumber();

  public abstract void execute();
}
