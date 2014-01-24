package drzhark.mocreatures.handlers;

import java.util.EnumSet;


public class MoCDespawnTickHandler
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


}