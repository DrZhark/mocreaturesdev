package drzhark.mocreatures.util;

import org.apache.logging.log4j.LogManager;

public class MoCLog {

    public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("MoCreatures");

    public static void initLog() {
        logger.info("Starting MoCreatures 8.0.0.devA");
        logger.info("Copyright (c) DrZhark, Bloodshot, BlockDaddy 2013 - 2015");
    }
}
