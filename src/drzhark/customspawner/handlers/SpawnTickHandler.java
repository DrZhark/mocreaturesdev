package drzhark.customspawner.handlers;

import java.util.EnumSet;

import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSUtils;

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
        WorldServer worldObj = (WorldServer) tickData[0];
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