package drzhark.mocreatures.utils;

import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class MoCLog {

   public static final Logger logger = Logger.getLogger("MoCreatures");

   public static void initLog() {
       logger.setParent(FMLLog.getLogger());
       logger.info("Starting MoCreatures 6.3.0.d2");
       logger.info("Copyright (c) DrZhark, Bloodshot, BlockDaddy 2013");
   }
}
