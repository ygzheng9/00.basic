package pickup.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by YongGang on 2017/3/29.
 */

@Entity
@Table(name="car")
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "color")
  private String color;

  @ManyToOne
  @JoinColumn(name = "showroom_id")
  private Showroom showroom;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Showroom getShowroom() {
    return showroom;
  }

  public void setShowroom(Showroom showroom) {
    this.showroom = showroom;
  }

  public Car() {
  }

  public Car(String name, String color) {
    this.name = name;
    this.color = color;
  }

  @Override
  public String toString() {
    return "Car{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", color='" + color + '\'' +
        '}';
  }
}
