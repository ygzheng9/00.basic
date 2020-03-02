package pickup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YongGang on 2017/1/14.
 */
public class log {
    static public Logger logger = LoggerFactory.getLogger(log.class);

    public static void main(String [] args) {
        String var = "bababalab";

        logger.error("error");
        logger.debug("debug");
        logger.info("info");
        logger.trace("trace");
        logger.warn("warn");
        logger.error("error {}", var);
    }

}
