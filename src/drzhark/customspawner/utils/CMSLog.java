package drzhark.customspawner.utils;


import java.io.File;
import java.io.IOException;

import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

import org.apachev1.log4j.FileAppender;
import org.apachev1.log4j.Logger;
import org.apachev1.log4j.SimpleLayout;

import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.environment.EnvironmentSettings;

public class CMSLog {

    public final Logger logger;

    public CMSLog(String environment)
    {
        logger = Logger.getLogger(environment);
        SimpleLayout layout = new SimpleLayout();    
        FileAppender appender = null;
     try {
         appender = new FileAppender(layout,CustomSpawner.ROOT.getPath() + File.separator + "logs" + File.separator + environment + ".log",false);
     } catch (IOException e) {
         e.printStackTrace();
     }    
        logger.addAppender(appender);
        logger.info("Logger initialized for environment " + environment);
    }

   public void logSpawn(EnvironmentSettings environment, String entitySpawnType, String biomeName, String entityName, int x, int y, int z, int moblimit, SpawnListEntry spawnlistentry)
   {
       if (environment.debug)
           logger.info("[" + environment.name() + "]" + "[" + entitySpawnType.toUpperCase() + " TICKHANDLER]:[spawned " + entityName + " at " + x + ", " + y + ", " + z + " with " + entitySpawnType.toUpperCase() + ":" + spawnlistentry.itemWeight + ":" + spawnlistentry.minGroupCount + ":" + spawnlistentry.maxGroupCount + " in biome " + biomeName + "]:[spawns left in limit " + moblimit + "]");
   }
}
