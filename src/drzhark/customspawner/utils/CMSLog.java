package drzhark.customspawner.utils;

import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class CMSLog {

   public static final Logger logger = Logger.getLogger("CustomSpawner");

   public static void initLog() {
       logger.setParent(FMLLog.getLogger());
       logger.info("Starting CustomSpawner 2.3.0.d1");
       logger.info("Copyright (c) DrZhark, Bloodshot 2013");
   }
}
