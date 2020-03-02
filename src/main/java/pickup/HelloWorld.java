package pickup;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.net.URLClassLoader;

public class HelloWorld {

//    private static final Logger LOGGER = LogManager.getLogger(HelloWorld.class);

  public static void main(String[] args) {

    System.out.println("all class path: ");

    showClassPath();

    System.out.println("where is HelloWorld:");

    findClass();
  }

  private static void showClassPath() {
    ClassLoader cl = ClassLoader.getSystemClassLoader();

    URL[] urls = ((URLClassLoader) cl).getURLs();

    for (URL url : urls) {
      System.out.println(url.getFile());
    }
  }

  private static void findClass() {
//    ClassLoader loader = HelloWorld.class.getClassLoader();
//    System.out.println(loader.getResource("HelloWorld.class"));

    System.out.println(HelloWorld.class.getResource("pickup.HelloWorld.class"));
  }
}