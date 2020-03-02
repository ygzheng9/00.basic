package pickup.parseScheme.loop;

import java.util.ArrayList;

/**
 * Created by YongGang on 2016/10/22.
 */
public class DBTable {
    private String tableName;
    private int cnt;
    private ArrayList<DBColumn> columns = new ArrayList<DBColumn>();


    public DBColumn addColumn(String colName, String dataType, String isNull) {
        DBColumn rtn = null;

        for(DBColumn col : columns) {
            if (col.getColName().equals(colName)) {
                rtn = col;
                break;
            }
        }

        if (rtn == null) {
            rtn = new DBColumn();
            rtn.setColName(colName);
            rtn.setDataType(dataType);
            rtn.setNull(isNull);

            columns.add(rtn);
        }

        return rtn;
    }

    public boolean addColumn(DBColumn newCol) {
        boolean alreadyExist = false;

        for(DBColumn col : columns) {
            if (col.getColName().equals(newCol.getColName())) {
                alreadyExist = true;
                break;
            }
        }

        if (!alreadyExist) {
            columns.add(newCol);
            return true;
        }

        return false;
    }

    public ArrayList<DBColumn> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<DBColumn> columns) {
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

}
