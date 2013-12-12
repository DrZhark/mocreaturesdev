package drzhark.customspawner.handlers;

import java.util.EnumSet;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.EntitySpawnType;
import drzhark.customspawner.utils.CMSLog;

public class SpawnTickHandler implements IScheduledTickHandler
{
    private void onTickInGame()
    {
        for (EntitySpawnType entitySpawnType : CustomSpawner.entitySpawnTypes.values())
        {
            if (entitySpawnType == CustomSpawner.LIVINGTYPE_UNDEFINED)
                continue;
            //if (entitySpawnType.getLivingSpawnTypeName().equalsIgnoreCase("UNDEFINED"))
               // continue;
            for (int dimension : DimensionManager.getIDs())
            {
                WorldServer worldObj = DimensionManager.getWorld(dimension);

                if (worldObj != null && (worldObj.getWorldInfo().getWorldTotalTime() % entitySpawnType.getSpawnTickRate() == 0L) && entitySpawnType.allowSpawning() && entitySpawnType.getSpawnCap() > 0) 
                {
                    int spawnAmount = 0;

                    if (worldObj.playerEntities.size() > 0)
                    {
                        spawnAmount = CustomSpawner.INSTANCE.doCustomSpawning(worldObj, entitySpawnType, entitySpawnType.getEntitySpawnDistance(), CustomSpawner.lightLevel, CustomSpawner.checkAmbientLightLevel, entitySpawnType.getHardSpawnLimit());
                        //if (CustomSpawner.debug) CMSLog.logger.info("CustomSpawner Spawned " + spawnAmount + entitySpawnType.getLivingSpawnTypeName().toUpperCase());
                    }
                }

                // despawn tick
                if (CustomSpawner.despawnVanilla && worldObj != null && (worldObj.getWorldInfo().getWorldTotalTime() % CustomSpawner.despawnTickRate == 0L)) //&& worldObj.provider.dimensionId != MoCreatures.WyvernLairDimensionID)
                {
                    if (worldObj.playerEntities.size() > 0)
                    {
                        int numDespawns = CustomSpawner.INSTANCE.despawnVanillaAnimals(worldObj, CustomSpawner.despawnLightLevel);
                        if (CustomSpawner.debug)
                        {
                            CMSLog.logger.info("CustomSpawner DeSpawned " + numDespawns + " Vanilla Creatures");
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getLabel()
    {
        return null;
    }
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) 
    {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) 
    {
        onTickInGame();
    }

    @Override
    public EnumSet<TickType> ticks() 
    {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public int nextTickSpacing() {
        // TODO Auto-generated method stub
        return 0;
    }
}