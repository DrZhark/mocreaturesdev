package drzhark.customspawner.handlers;

import java.util.EnumSet;

import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSUtils;

public class SpawnTickHandler
{
    /*@Override
    public String getLabel()
    {
        return "CustomMobSpawner";
    }*/
    
    @SubscribeEvent
    public void worldTick(WorldTickEvent event) 
    {
        WorldServer worldObj = (WorldServer) event.world;
        for (EntitySpawnType entitySpawnType : CMSUtils.getEnvironment(worldObj).entitySpawnTypes.values())
        {
            if (entitySpawnType.name().equals("UNDEFINED"))
                continue;
            if (worldObj != null && (worldObj.getWorldInfo().getWorldTotalTime() % entitySpawnType.getSpawnTickRate() == 0L) && entitySpawnType.allowSpawning() && entitySpawnType.getSpawnCap() > 0) 
            {
                int spawnAmount = 0;

                if (worldObj.playerEntities.size() > 0)
                {
                    spawnAmount = CustomSpawner.INSTANCE.doCustomSpawning(worldObj, entitySpawnType, entitySpawnType.getEntitySpawnDistance(), entitySpawnType.getHardSpawnLimit());
                    //if (CustomSpawner.debug) CMSLog.logger.info("[" + environment.name() + "]" + "CustomSpawner Spawned " + spawnAmount + entitySpawnType.getLivingSpawnTypeName().toUpperCase());
                }
            }
        }
    }
}