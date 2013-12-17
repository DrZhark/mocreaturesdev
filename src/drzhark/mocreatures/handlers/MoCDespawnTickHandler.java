package drzhark.mocreatures.handlers;

import java.util.EnumSet;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;


public class MoCDespawnTickHandler implements IScheduledTickHandler
{
    private void onTickInGame()
    {
        /*if (MoCreatures.proxy.useDespawner)
        {
                for (int dimension : DimensionManager.getIDs())
                {
                    WorldServer worldObj = DimensionManager.getWorld(dimension);

                    // despawn tick
                    if (worldObj != null && (worldObj.getWorldInfo().getWorldTotalTime() % MoCDespawner.despawnTickRate == 0L)) //&& worldObj.provider.dimensionId != MoCreatures.WyvernLairDimensionID)
                    {
                        if (worldObj.playerEntities.size() > 0)
                        {
                            int numDespawns = MoCDespawner.despawnVanillaAnimals(worldObj, MoCDespawner.despawnLightLevel);
                            if (MoCDespawner.debug)
                            {
                                MoCLog.logger.info("MoCDespawner DeSpawned " + numDespawns + " Vanilla Creatures");
                            }
                        }
                    }
            }
        }*/
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