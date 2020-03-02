package pickup.parseScheme.loop;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pickup.parseScheme.loop.closure.DBItem;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by YongGang on 2016/10/22.
 */
public class DBScheme {
    private ArrayList<DBTable> tableList = new ArrayList<DBTable>();

    private ArrayList<DBItem> itemList = new ArrayList<DBItem>();

    public DBTable addTable(String tableName) {
        DBTable rtn = null;

        for(DBTable tbl : tableList) {
            if (tableName.equals(tbl.getTableName())) {
                rtn = tbl;
                break;
            }
        }

        if (rtn == null) {
            rtn  = new DBTable();
            rtn.setCnt(1);
            rtn.setTableName(tableName);
            tableList.add(rtn);
        }

        return rtn;
    }

    public void listAll() {
        for(DBTable tbl : tableList) {
            System.out.println(tbl.getTableName());

            for(DBColumn col : tbl.getColumns()) {
                System.out.println("\t" + col.getColName());
            }
        }
    }

    public void initAll(String fileName) {
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                String tableName = record.get(0);
                DBTable tbl = addTable(tableName);

                String colName = record.get(1);
                String dataType = record.get(2);
                String isNull = record.get(3);

                tbl.addColumn(colName, dataType, isNull);
            }
        } catch (Exception e) {
            System.out.println("initAll Failed...");
            e.printStackTrace();
        }
    }
}
