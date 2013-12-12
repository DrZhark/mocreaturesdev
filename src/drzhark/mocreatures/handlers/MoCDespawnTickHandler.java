package drzhark.mocreatures.handlers;

import java.util.EnumSet;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import drzhark.mocreatures.MoCDespawner;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.utils.MoCLog;


public class MoCDespawnTickHandler implements IScheduledTickHandler
{
    public int despawnLightLevel = 7;
    public int despawnTickRate = 111;

    private void onTickInGame()
    {
        if (MoCreatures.proxy.useDespawner)
        {
                for (int dimension : DimensionManager.getIDs())
                {
                    WorldServer worldObj = DimensionManager.getWorld(dimension);

                    // despawn tick
                    if (worldObj != null && (worldObj.getWorldInfo().getWorldTotalTime() % despawnTickRate == 0L)) //&& worldObj.provider.dimensionId != MoCreatures.WyvernLairDimensionID)
                    {
                        if (worldObj.playerEntities.size() > 0)
                        {
                            int numDespawns = MoCDespawner.despawnVanillaAnimals(worldObj, despawnLightLevel);
                            if (MoCDespawner.debug)
                            {
                                MoCLog.logger.info("MoCDespawner DeSpawned " + numDespawns + " Vanilla Creatures");
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