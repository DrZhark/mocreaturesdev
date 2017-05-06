package drzhark.customspawner.handlers;

import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSUtils;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class SpawnTickHandler {

    @SubscribeEvent
    public void worldTick(WorldTickEvent event) {
        WorldServer worldObj = (WorldServer) event.world;
        EnvironmentSettings environment = CMSUtils.getEnvironment(worldObj);
        for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values()) {
            if (entitySpawnType.name().equals("UNDEFINED")) {
                continue;
            }
            if (worldObj != null && (worldObj.getWorldInfo().getWorldTotalTime() % entitySpawnType.getSpawnTickRate() == 0L)
                    && entitySpawnType.allowSpawning() && entitySpawnType.getSpawnCap() > 0) {
                int spawnAmount = 0;

                if (worldObj.playerEntities.size() > 0) {
                    spawnAmount =
                            CustomSpawner.INSTANCE.doCustomSpawning(worldObj, entitySpawnType, entitySpawnType.getMobSpawnRange());
                    if (CustomSpawner.debug) {
                        environment.envLog.logger.info("[" + environment.name() + "]" + "CustomSpawner Spawned " + spawnAmount + entitySpawnType.getEnumCreatureType());
                    }
                }
            }
        }
    }
}
