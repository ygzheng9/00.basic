package pickup.orm;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by YongGang on 2017/3/29.
 */

@Entity
@Table(name = "showroom")
public class Showroom {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;

  @Column(name="manager")
  private String manager;

  @Column(name="location")
  private String location;

  @OneToMany(cascade= CascadeType.ALL)
  @JoinColumn(name = "showroom_id")
  private List<Car> cars = new ArrayList<>();

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getManager() {
    return manager;
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public List<Car> getCars() {
    return cars;
  }

  public void setCars(List<Car> cars) {
    this.cars = cars;
  }

  public Showroom() {
  }

  @Override
  public String toString() {
    return "Showroom{" +
        "id=" + id +
        ", manager='" + manager + '\'' +
        ", location='" + location + '\'' +
        ", cars=" + cars +
        '}';
  }
}
