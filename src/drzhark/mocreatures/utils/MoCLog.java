package drzhark.mocreatures.utils;

import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;

import cpw.mods.fml.common.FMLLog;

public class MoCLog {

   public static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("MoCreatures");

   public static void initLog() {
       logger.info("Starting MoCreatures 6.3.0.d2");
       logger.info("Copyright (c) DrZhark, Bloodshot, BlockDaddy 2013");
   }
}
