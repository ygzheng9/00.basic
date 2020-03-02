package pickup.parseScheme.loop;

/**
 * Created by YongGang on 2016/10/22.
 */
public class DBColumn {
    private String colName;
    private String dataType;
    private String isNull;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String isNull() {
        return isNull;
    }

    public void setNull(String aNull) {
        isNull = aNull;
    }
}
