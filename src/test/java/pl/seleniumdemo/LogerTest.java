package pl.seleniumdemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogerTest {
    public static void main(String[] args) {

        Logger logger = LogManager.getLogger();
        logger.info("info");
        logger.error("error");
        logger.warn("warn");
        logger.debug("debug");
        logger.fatal("fatal");
        logger.trace("trace");

    }
}
