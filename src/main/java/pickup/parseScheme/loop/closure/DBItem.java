package pickup.parseScheme.loop.closure;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by YongGang on 2016/10/22.
 */
public class DBItem {
    private String tag;

    private String name;
    private String type;
    private String attr;
    private ArrayList<DBItem> items = new ArrayList<DBItem>();

    public void listAll(int i) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < i; j++) {
            builder.append("\t");
        }

        builder.append(this.display());

        System.out.println(builder.toString());

        for(DBItem subItm : this.getItems()) {
            subItm.listAll(i + 1);
        }
    }

    // 为当前元素，增加子元素；
    // 如果子元素已经存在，则返回存在的那个子元素；
    // 如果子元素不存在，增加子元素，并返回子元素；
    public DBItem addItem(DBItem newItm) {
        DBItem rtn = null;

        for(DBItem itm : this.getItems()) {
            if (itm.getName().equals(newItm.getName())) {
                rtn = itm;
                break;
            }
        }

        if (rtn == null) {
            rtn = newItm;
            this.getItems().add(rtn);
        }

        return rtn;
    }

    // 根据数据库导出的表结构文件，构建
    public void initAll(String fileName) {
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                DBItem head = new DBItem();
                String tableName = record.get(0);
                head.setTag("Table");
                head.setName(tableName);

                DBItem tbl = this.addItem(head);

                String colName = record.get(1);
                String dataType = record.get(2);
                String isNull = record.get(3);

                DBItem col = new DBItem();
                col.setTag("Column");
                col.setName(colName);
                col.setType(dataType);
                col.setAttr(isNull);
                tbl.addItem(col);
            }
        } catch (Exception e) {
            System.out.println("initAll Failed...");
            e.printStackTrace();
        }
    }

    public String display() {
//        StringBuilder builder = new StringBuilder();
//        builder.append(this.getName());

        String rtn = this.getName() + "\t" + this.getType() + "\t" + this.getAttr();

        return rtn;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public ArrayList<DBItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DBItem> items) {
        this.items = items;
    }
}
