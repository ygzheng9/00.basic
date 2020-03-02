package pickup.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;

/**
 * Created by YongGang on 2017/4/25.
 */
public class StringTpl {
  public static void main(String[] args) {
    StringTpl tpl = new StringTpl();
    tpl.loadSingleFile();
    tpl.opMemory();
  }

  public void opMemory() {
    String template = "decl(type, name, value) ::= \"<type> <name><init(value)>;\"\n"
        + "init(v) ::= \"<if(v)> = <v><endif>\"";
    STGroup views = new STGroupString(template);
    ST decl = views.getInstanceOf("decl");
    decl.add("type", "int");
    decl.add("name", "x");
    decl.add("value", 12);
    System.out.println(decl.render());
  }

  public void loadSingleFile() {
    // Load the file
    final STGroup stGroup = new STGroupFile("custTemplate.stg");

    // Pick the correct template
    final ST templateExample = stGroup.getInstanceOf("firstTemplate");

    // Pass on values to use when rendering
    templateExample.add("param", "Hello World");

    // Render
    final String render = templateExample.render();

    // Print
    System.out.println(render);
  }
}
