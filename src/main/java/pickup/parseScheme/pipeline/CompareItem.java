package pickup.parseScheme.pipeline;

/**
 * Created by YongGang on 2016/10/24.
 */
public class CompareItem {
    private String tableName;
    private String tableColumn;
    private String colTypeA;
    private String colAttrA;
    private String colTypeB;
    private String colAttrB;

    public CompareItem() {
        this.init("", "", "", "", "", "");
    }


    public void init(String tableName, String tableColumn, String colTypeA, String colAttrA, String colTypeB, String colAttrB) {
        this.tableName = tableName;
        this.tableColumn = tableColumn;
        this.colTypeA = colTypeA;
        this.colAttrA = colAttrA;
        this.colTypeB = colTypeB;
        this.colAttrB = colAttrB;
    }

    public CompareItem merge(CompareItem obj) {
        if (this.getGroupingKey().equals(obj.getGroupingKey())) {
            CompareItem item = new CompareItem();

            item.setTableName(this.getTableName());
            item.setTableColumn(this.getTableColumn());

            item.setColAttrA(this.getColAttrA() + obj.getColAttrA());
            item.setColAttrB(this.getColAttrB() + obj.getColAttrB());
            item.setColTypeA(this.getColTypeA() + obj.getColTypeA());
            item.setColTypeB(this.getColTypeB() + obj.getColTypeB());

            return item;
        } else {
            return this;
        }
    }

    public String getGroupingKey() {
        return ( this.getTableName() + "." + this.getTableColumn() );
    };

    public int byGroupingKey(CompareItem item) {
        return ( this.getGroupingKey().compareTo(item.getGroupingKey()) );
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableColumn() {
        return tableColumn;
    }

    public void setTableColumn(String tableColumn) {
        this.tableColumn = tableColumn;
    }

    public String getColTypeA() {
        return colTypeA;
    }

    public void setColTypeA(String colTypeA) {
        this.colTypeA = colTypeA;
    }

    public String getColAttrA() {
        return colAttrA;
    }

    public void setColAttrA(String colAttrA) {
        this.colAttrA = colAttrA;
    }

    public String getColTypeB() {
        return colTypeB;
    }

    public void setColTypeB(String colTypeB) {
        this.colTypeB = colTypeB;
    }

    public String getColAttrB() {
        return colAttrB;
    }

    public void setColAttrB(String colAttrB) {
        this.colAttrB = colAttrB;
    }
}
