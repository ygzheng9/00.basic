package pickup.orm;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * Created by YongGang on 2017/3/28.
 */

@Entity
@Table( name = "EVENTS" )
public class Event {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;

  @Column(name = "title")
  private String title;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "EVENT_DATE")
  private Date eventDate;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getEventDate() {
    return eventDate;
  }

  public void setEventDate(Date eventDate) {
    this.eventDate = eventDate;
  }

  public Event() {
  }

  public Event(String title, Date eventDate) {
    this.title = title;
    this.eventDate = eventDate;
  }

  @Override
  public String toString() {
    return "Event{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", eventDate=" + eventDate +
        '}';
  }
}
