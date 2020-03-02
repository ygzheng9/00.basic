package pickup.seqNo;

import java.awt.*;

/**
 * Created by YongGang on 2016/11/5.
 */
public class TranslationItem {
    private String itemFrom;
    private String itemTo;

    private static final String fromPrefix = "#";
    private static final String toSep = "=";
    private String transItem;
    private String fromValue;
    private String toValue;


    public String getTransItem() {
        return transItem;
    }

    public void setTransItem(String transItem) {
        this.transItem = transItem;
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(String toValue) {
        this.toValue = toValue;
    }

    public String getItemFrom() {
        return itemFrom;
    }

    public void setItemFrom(String itemFrom) {
        this.itemFrom = itemFrom;
    }

    public String getItemTo() {
        return itemTo;
    }

    public void setItemTo(String itemTo) {
        this.itemTo = itemTo;
    }

    public TranslationItem() {
        this.setItemFrom("");
        this.setItemTo("");
        this.setFromValue("");
        this.setToValue("");
        this.setTransItem("");
    }

    public TranslationItem(String itemFrom, String itemTo) {
        this.itemFrom = itemFrom.trim();
        this.itemTo = itemTo.trim();

        this.breakToPieces();
    }

    private void breakToPieces() {
        // break rm.orderBillNumber=xxxx

        String tmpTo = this.getItemTo();
        int toLen = tmpTo.length();
        int posSep = tmpTo.indexOf(this.toSep);
        if (posSep <= 0) {
            System.out.println("** Error: " + this.getItemTo());
        } else if (toLen >= posSep) {
            this.setTransItem(tmpTo.substring(0, posSep));
            this.setToValue(tmpTo.substring(posSep + 1, toLen));
        }

        // break ##业务识别号必输
        String tmpFrom = this.getItemFrom();
        int i = 0;
        int fromLen = tmpFrom.length();
        for (; i < fromLen; i ++) {
            if (!this.fromPrefix.equals(tmpFrom.substring(i, i + 1))) break;
        }

        if (i < fromLen) {
            this.setFromValue(tmpFrom.substring(i, fromLen).trim());
        }
    }

    public static TranslationItem buildUp(String item, String from, String to) {
        TranslationItem it = new TranslationItem();
        it.setTransItem(item);
        it.setFromValue(from);
        it.setToValue(to);

        it.setItemTo( item + TranslationItem.toSep + to );
        it.setItemFrom( TranslationItem.fromPrefix + TranslationItem.fromPrefix + from );

        return it;
    }

    @Override
    public String toString() {
        return this.getTransItem() + "|" + this.getFromValue() + "|" + this.getToValue();
    }

    public String[] breakToCSV() {
        return (new String[] {this.getTransItem(), this.getFromValue(), this.getToValue()});
    }

    public String[] dump() {
        return (new String[] {this.getItemTo(), this.getItemFrom()});
    }
}
