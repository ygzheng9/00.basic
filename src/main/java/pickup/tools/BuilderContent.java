package pickup.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Created by YongGang on 2017/3/27.
 */
public class BuilderContent {

  @Deprecated
  public static void readTxtFile(String filePath) {
    try {
      String encoding = "GBK";
      File file = new File(filePath);
      if (file.isFile() && file.exists()) { //判断文件是否存在
        InputStreamReader read = new InputStreamReader(
            new FileInputStream(file), encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {
          String ln = "builder.append(\"" + lineTxt + "\");";
          System.out.println(ln);
        }
        read.close();
      } else {
        System.out.println("找不到指定的文件");
      }
    } catch (Exception e) {
      System.out.println("读取文件内容出错");
      e.printStackTrace();
    }
  }

  public static void main(String argv[]) {
    String fileName = "builder.txt";

    BuilderContent builder = new BuilderContent();
    try {
      builder.parseFile(fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void parseFile(String filename) throws IOException {
    File inputFile = new File(filename);

    Stream<String> lines = Files.asCharSource(inputFile, Charsets.UTF_8).readLines().stream();

    lines.map(lineTxt -> "builder.append(\" " + lineTxt + " \");")
         .forEach(System.out::println);
  }
}
