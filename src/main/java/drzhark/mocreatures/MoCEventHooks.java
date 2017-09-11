package drzhark.mocreatures;

import drzhark.customspawner.utils.CMSUtils;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.util.MoCLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MoCEventHooks {

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        // if overworld has been deleted or unloaded, reset our flag
        if (event.getWorld().provider.getDimensionType().getId() == 0) {
            MoCreatures.proxy.worldInitDone = false;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (DimensionManager.getWorld(0) != null && !MoCreatures.proxy.worldInitDone) // if overworld has loaded, use its mapstorage
        {
            MoCPetMapData data = (MoCPetMapData) DimensionManager.getWorld(0).getMapStorage().getOrLoadData(MoCPetMapData.class, "mocreatures");
            if (data == null) {
                data = new MoCPetMapData("mocreatures");
            }

            DimensionManager.getWorld(0).getMapStorage().setData("mocreatures", data);
            DimensionManager.getWorld(0).getMapStorage().saveAllData();
            MoCreatures.instance.mapData = data;
            MoCreatures.proxy.worldInitDone = true;
        }
        // make sure doMobSpawning is on if CMS is not installed
        GameRules gameRule = event.getWorld().getGameRules();
        if (gameRule != null && !MoCreatures.isCustomSpawnerLoaded) {
            gameRule.setOrCreateGameRule("doMobSpawning", "true");
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        if (!event.getEntity().world.isRemote) {
            if (IMoCTameable.class.isAssignableFrom(event.getEntityLiving().getClass())) {
                IMoCTameable mocEntity = (IMoCTameable) event.getEntityLiving();
                if (mocEntity.getIsTamed() && mocEntity.getPetHealth() > 0 && !mocEntity.isRiderDisconnecting()) {
                    return;
                }

                if (mocEntity.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(mocEntity, mocEntity.getOwnerPetId());
                }
            }
        }
    }

    // used for Despawner
    @SubscribeEvent
    public void onLivingDespawn(LivingSpawnEvent.AllowDespawn event) {
        if (MoCreatures.proxy.forceDespawns && !MoCreatures.isCustomSpawnerLoaded) {
            // try to guess what we should ignore
            // Monsters
            if ((IMob.class.isAssignableFrom(event.getEntityLiving().getClass()) || IRangedAttackMob.class.isAssignableFrom(event.getEntityLiving().getClass()))
                    || event.getEntityLiving().isCreatureType(EnumCreatureType.MONSTER, false)) {
                return;
            }
            // Tameable
            if (event.getEntityLiving() instanceof EntityTameable) {
                if (((EntityTameable) event.getEntityLiving()).isTamed()) {
                    return;
                }
            }
            // Farm animals
            if (event.getEntityLiving() instanceof EntitySheep || event.getEntityLiving() instanceof EntityPig || event.getEntityLiving() instanceof EntityCow
                    || event.getEntityLiving() instanceof EntityChicken) {
                // check lightlevel
                if (isValidDespawnLightLevel(event.getEntity(), event.getWorld(), MoCreatures.proxy.minDespawnLightLevel,
                        MoCreatures.proxy.maxDespawnLightLevel)) {
                    return;
                }
            }
            // Others
            NBTTagCompound nbt = new NBTTagCompound();
            event.getEntityLiving().writeToNBT(nbt);
            if (nbt != null) {
                if (nbt.hasKey("Owner") && !nbt.getString("Owner").equals("")) {
                    return; // ignore
                }
                if (nbt.hasKey("Tamed") && nbt.getBoolean("Tamed") == true) {
                    return; // ignore
                }
            }
            // Deny Rest
            if (event.getEntityLiving().getIdleTime() > 600) {
                event.setResult(Result.ALLOW);
            }

            if (MoCreatures.proxy.debug) {
                int x = MathHelper.floor(event.getEntity().posX);
                int y = MathHelper.floor(event.getEntity().getEntityBoundingBox().minY);
                int z = MathHelper.floor(event.getEntity().posZ);
                MoCLog.logger.info("Forced Despawn of entity " + event.getEntityLiving() + " at " + x + ", " + y + ", " + z
                        + ". To prevent forced despawns, use /moc forceDespawns false.");
            }
        }
    }

    private boolean isValidDespawnLightLevel(Entity entity, World world, int minDespawnLightLevel, int maxDespawnLightLevel) {
        int x = MathHelper.floor(entity.posX);
        int y = MathHelper.floor(entity.getEntityBoundingBox().minY);
        int z = MathHelper.floor(entity.posZ);
        int blockLightLevel = 0;
        if (y >= 0) {
            if (y >= 256) {
                y = 255;
            }
            blockLightLevel = CMSUtils.getLightFromNeighbors(world.getChunkFromChunkCoords(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (blockLightLevel < minDespawnLightLevel && maxDespawnLightLevel != -1) {
            //if (debug) CMSUtils.getEnvironment(world).envLog.logger.info("Denied spawn! for " + entity.getName() + blockLightLevel + " under minimum threshold of " + minDespawnLightLevel + " in dimension " + world.provider.getDimensionType().getId() + " at coords " + x + ", " + y + ", " + z);
            return false;
        } else if (blockLightLevel > maxDespawnLightLevel && maxDespawnLightLevel != -1) {
            //if (debug) CMSUtils.getEnvironment(world).envLog.logger.info("Denied spawn! for " + entity.getName() + blockLightLevel + " over maximum threshold of " + maxDespawnLightLevel + " in dimension " + world.provider.getDimensionType().getId() + " at coords " + x + ", " + y + ", " + z);
            return false;
        }
        return true;
    }
}
