package pickup.parseScheme.pipeline;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by YongGang on 2016/10/24.
 */
public class CompareUtil {
    public final static String A = "A";
    public final static String B = "B";

    public static void testLoop() {
        ArrayList<CompareItem> itmList = new ArrayList<CompareItem>();

        CompareUtil.readDB(itmList, CompareUtil.A, "c:/tmp/test.csv");
        CompareUtil.readDB(itmList, CompareUtil.B, "c:/tmp/prd.csv");

        ArrayList<CompareItem> diffList = CompareUtil.getDiff(itmList);

        diffList.sort(CompareItem::byGroupingKey);
        CompareUtil.printAll(diffList);
    }

    public static void testStream() {
        Stream<CompareItem> streamA = CompareUtil.parseScheme(CompareUtil.A, "c:/tmp/test.csv");
        Stream<CompareItem> streamB = CompareUtil.parseScheme(CompareUtil.B, "c:/tmp/prd.csv");

        Map<String, CompareItem> mergedMap =
                Stream.concat(streamA, streamB)
                        .collect(Collectors.toMap(
                                CompareItem::getGroupingKey,
                                Function.identity(),
                                (s, a) -> s.merge(a)));

        mergedMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(CompareUtil::isDiff)
                .forEach(CompareUtil::printItem);
    }

    public static void printItem(CompareItem itm) {
        System.out.println(itm.getTableName() + "|" + itm.getTableColumn() + "|" +
                itm.getColTypeA() + "|" + itm.getColAttrA() + "|" +
                itm.getColTypeB() + "|" + itm.getColAttrB());
    }

    public static void printAll(ArrayList<CompareItem> itmList) {
        for (CompareItem itm: itmList) {
            CompareUtil.printItem(itm);
        }
    }

    public static ArrayList<CompareItem> getDiff(ArrayList<CompareItem> itmList ) {
        ArrayList<CompareItem> diffList = new ArrayList<CompareItem>();

        for (CompareItem itm: itmList) {
            if (CompareUtil.isDiff(itm)) {
                diffList.add(itm);
            }
        }

        return diffList;
    }

    public static Stream<CompareItem> parseScheme(String source, String fileName) {
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

            return StreamSupport.stream(records.spliterator(), false)
                                .map((record) -> CompareUtil.mapItem(source, record));

        } catch (Exception e) {
            System.out.println("readDB Failed...");
            e.printStackTrace();
        }

        return null;
    }


    public static void readDB(ArrayList<CompareItem> itmList, String source, String fileName) {
        try {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            for (CSVRecord record : records) {
                CompareUtil.addItem(itmList, source, record);
            }
        } catch (Exception e) {
            System.out.println("readDB Failed...");
            e.printStackTrace();
        }
    }



    public static CompareItem addItem(ArrayList<CompareItem> itmList, String source, CSVRecord record) {
        String tableName = record.get(0);
        String tableCol = record.get(1);
        String colType = record.get(2);
        String colAttr = record.get(3);

        return CompareUtil.addItem(itmList, source, tableName, tableCol, colType, colAttr);
    }

    public static CompareItem addItem(ArrayList<CompareItem> itmList,
                          String source, String tblName, String tblCol, String colType, String colAttr) {

//        CompareItem itm  = CompareUtil.findItem(itmList, tblName, tblCol);

        CompareItem itm = CompareUtil.sfindItem(itmList, tblName, tblCol);

        if (itm == null) {
            itm = CompareUtil.createItem(source, tblName, tblCol, colType, colAttr);
            itmList.add(itm);
        } else {
            CompareUtil.updateItem(itm, source, colType, colAttr);
        }

        return itm;
    }

    public static CompareItem findItem(ArrayList<CompareItem> itmList, String tblName, String tblCol) {
        CompareItem rtn = null;

        for (CompareItem itm : itmList) {
            if (CompareUtil.isExist(itm, tblName, tblCol)) {
                rtn = itm;
                break;
            }
        }

        return rtn;
    }

    public static CompareItem sfindItem(ArrayList<CompareItem> itmList, String tblName, String tblCol) {
        Optional<CompareItem> rtn = itmList.stream()
                .filter((itm) -> CompareUtil.isExist(itm, tblName, tblCol))
                .findFirst();

        return rtn.orElse(null);
    }

    public static boolean isExist(CompareItem itm, String tblName, String tblCol) {
        boolean rtn = false;

        if ( itm.getTableName().equals(tblName) && itm.getTableColumn().equals(tblCol) ) {
            rtn = true;
        }

        return rtn;
    }

    public static boolean isDiff(CompareItem itm) {
        return (!itm.getColTypeA().equals(itm.getColTypeB()) || !itm.getColAttrA().equals(itm.getColAttrB()));
    }

    public static CompareItem mapItem(String source, CSVRecord record) {
        String tableName = record.get(0);
        String tableCol = record.get(1);
        String colType = record.get(2);
        String colAttr = record.get(3);

        return CompareUtil.createItem(source, tableName, tableCol, colType, colAttr);
    }

    public static CompareItem createItem(String source, String tblName, String tblCol, String colType, String colAttr) {
        CompareItem itm = new CompareItem();
        itm.setTableName(tblName);
        itm.setTableColumn(tblCol);

        CompareUtil.updateItem(itm, source, colType, colAttr);

        return itm;
    }

    public static void updateItem(CompareItem itm, String source, String colType, String colAttr) {
        if (source.equals("A")) {
            itm.setColTypeA(colType);
            itm.setColAttrA(colAttr);
        } else {
            itm.setColTypeB(colType);
            itm.setColAttrB(colAttr);
        }
    }
}
