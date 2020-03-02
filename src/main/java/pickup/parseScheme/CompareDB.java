package pickup.parseScheme;

/**
 * Created by YongGang on 2016/10/22.
 */

import pickup.parseScheme.pipeline.CompareUtil;


public class CompareDB {

    public static void main(String[] args) {
        try {
//            DBScheme schemeDev = new DBScheme();
//            DBScheme schemeProd = new DBScheme();
//
//            schemeDev.initAll("c:/tmp/test.csv");
//            schemeProd.initAll("c:/tmp/prd.csv");
//
//            schemeDev.listAll();
//            schemeProd.listAll();

//
//            DBItem dbDev = new DBItem();
//            dbDev.setTag("DB");
//            dbDev.setName("DEV");
//            dbDev.initAll("c:/tmp/test.csv");
//            dbDev.listAll(0);

//            CompareUtil.testLoop();

            CompareUtil.testStream();

        } catch (Exception e) {
            System.out.println("CompareDB Failed...");
            e.printStackTrace();
        }
    }



}
