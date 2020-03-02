package pickup.orm;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by YongGang on 2017/3/28.
 */
public class Runner {

  private SessionFactory sessionFactory = null;
  private EntityManagerFactory emf = null;

  protected void setupNative() {
    Configuration cfg = new Configuration().addAnnotatedClass(Event.class)
                                           .addAnnotatedClass(Car.class)
                                           .addAnnotatedClass(Showroom.class);

    try {
      sessionFactory = cfg.buildSessionFactory();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void dummyEvents() {
    try {
      this.setupNative();

      Session session = sessionFactory.openSession();

      session.beginTransaction();

      session.save(new Event("Our very first event!", new Date(2017, 3, 28)));
      session.save(new Event("A follow up event", new Date(2017, 2, 26)));

      session.getTransaction().commit();

      session.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void getAllEvents() {
    this.setupNative();

    Session session = sessionFactory.openSession();

    List<Event> events = session.createQuery(" from Event").list();

    events.forEach(System.out::println);

    session.close();
  }

  public void setupShowroom() {
    System.out.println("setupShowroom....");

    this.setupNative();

    Session session = this.sessionFactory.openSession();
    session.beginTransaction();

    Showroom showroom = new Showroom();
    showroom.setLocation("Pudong, Shanghai");
    showroom.setManager("Zheng YongGang");

    // session.save(showroom);

    List<Car> cars = new ArrayList<Car>();
    cars.add(new Car("QQ", "Racing Green"));
    cars.add(new Car("BYD", "White"));
    cars.add(new Car("QR", "Black"));

    // for (Car c : cars) {
    //   c.setShowroom(showroom);
    //   session.save(c);
    // }

    showroom.setCars(cars);
    session.save(showroom);

    session.getTransaction().commit();
    session.close();

    System.out.println("setupShowroom....Completed.");
  }

  public void getAllShowroom() {
    this.setupNative();

    List<Showroom> allShowroom = this.getAll("Showroom", Showroom.class);

    Session session = this.sessionFactory.openSession();
    allShowroom.forEach(System.out::println);
    session.close();
  }


  private <T> List<T> getAll(String entityName, Class<T> clz) {
    Session session = this.sessionFactory.openSession();

    String queryString = " from " + entityName;

    @SuppressWarnings("unchecked")
    List<T> results = session.createQuery(queryString).list();

    results.forEach(System.out::println);

    session.close();

    return results;
  }

  public void removeSome() {
    this.setupNative();

    Session session = this.sessionFactory.openSession();
    session.beginTransaction();

    Query q = session.createQuery("from Showroom where id = :id ");
    q.setParameter("id", 10);

    Showroom showroom = (Showroom) q.getResultList().get(0);

    System.out.println(showroom);

    // showroom.getCars().remove(0);
    showroom.getCars().get(0).setColor("YELLOW");
    showroom.getCars().remove(1);
    showroom.getCars().add(new Car("QQ", "RED"));

    session.save(showroom);

    session.getTransaction().commit();
    session.close();

  }


  private void setupJPA() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CRM");
  }

  public void japSelect() {
    this.setupJPA();

    EntityManager em = emf.createEntityManager();
    List<Showroom> showrooms = em.createQuery("from Showroom", Showroom.class).getResultList();

    showrooms.forEach(System.out::println);

  }

  public static void main(String[] args) {
    Runner runner = new Runner();
    // runner.setupShowroom();
    // runner.removeSome();
    // runner.getAllShowroom();

    runner.japSelect();
  }

}
