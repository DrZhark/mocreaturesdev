package drzhark.customspawner.handlers;

import java.util.EnumSet;

import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.EntitySpawnType;
import drzhark.customspawner.utils.CMSLog;

public class SpawnTickHandler implements IScheduledTickHandler
{
    @Override
    public String getLabel()
    {
        return "CustomMobSpawner";
    }
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) 
    {
        for (EntitySpawnType entitySpawnType : CustomSpawner.entitySpawnTypes.values())
        {
            if (entitySpawnType == CustomSpawner.LIVINGTYPE_UNDEFINED)
                continue;
            //if (entitySpawnType.getLivingSpawnTypeName().equalsIgnoreCase("UNDEFINED"))
               // continue;

                WorldServer worldObj = (WorldServer) tickData[0];
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTotalTime() % entitySpawnType.getSpawnTickRate() == 0L) && entitySpawnType.allowSpawning() && entitySpawnType.getSpawnCap() > 0) 
                {
                    int spawnAmount = 0;

                    if (worldObj.playerEntities.size() > 0)
                    {
                        spawnAmount = CustomSpawner.INSTANCE.doCustomSpawning(worldObj, entitySpawnType, entitySpawnType.getEntitySpawnDistance(), CustomSpawner.checkAmbientLightLevel, entitySpawnType.getHardSpawnLimit());
                        if (CustomSpawner.debug) CMSLog.logger.info("CustomSpawner Spawned " + spawnAmount + entitySpawnType.getLivingSpawnTypeName().toUpperCase());
                    }
                }

                // handle despawn logic in EventHooks onLivingDespawn
                // despawn tick
                /*if (CustomSpawner.forceDespawns && worldObj != null && (worldObj.getWorldInfo().getWorldTotalTime() % CustomSpawner.despawnTickRate == 0L)) //&& worldObj.provider.dimensionId != MoCreatures.WyvernLairDimensionID)
                {
                    if (worldObj.playerEntities.size() > 0)
                    {
                        int numDespawns = CustomSpawner.INSTANCE.despawnVanillaAnimals(worldObj, CustomSpawner.despawnLightLevel);
                        if (CustomSpawner.debug)
                        {
                            CMSLog.logger.info("CustomSpawner DeSpawned " + numDespawns + " Creatures");
                        }
                    }
                }*/
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) 
    {
    }

    @Override
    public EnumSet<TickType> ticks() 
    {
        return EnumSet.of(TickType.WORLD);
    }

    @Override
    public int nextTickSpacing() {
        // TODO Auto-generated method stub
        return 0;
    }
}