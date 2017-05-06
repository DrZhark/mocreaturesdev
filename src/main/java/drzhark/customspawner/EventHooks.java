package drzhark.customspawner;

import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EventHooks {

    @SubscribeEvent
    public void peformCustomWorldGenSpawning(PopulateChunkEvent.Populate event) {
        int par1 = event.getChunkX() * 16;
        int par2 = event.getChunkZ() * 16;
        int x = event.getChunkX() * 16 + 8 + event.getWorld().rand.nextInt(16);
        int z = event.getChunkZ() * 16 + 8 + event.getWorld().rand.nextInt(16);
        CMSUtils.addWorldEnvironment(event.getWorld().provider.getClass()); // make sure world environment has been created
        EnvironmentSettings environment = CMSUtils.getEnvironment(event.getWorld());
        for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values()) {
            if (entitySpawnType.getChunkSpawnChance() > 0 && entitySpawnType.getSpawnCap() > 0) {
                List<SpawnListEntry> customSpawnList =
                        entitySpawnType.getBiomeSpawnList(Biome.getIdForBiome(event.getWorld().getBiome(new BlockPos(x, 0, z))));
                if (customSpawnList != null) {
                    CustomSpawner.performWorldGenSpawning(entitySpawnType, event.getWorld(), event.getWorld().getBiome(new BlockPos(x, 0, z)),
                            par1 + 8, par2 + 8, 16, 16, event.getRand(), customSpawnList, environment.worldGenCreatureSpawning);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingPackSize(LivingPackSizeEvent event) {
        EntityData entityData = CMSUtils.getEnvironment(event.getEntity().worldObj).classToEntityMapping.get(event.getEntityLiving().getClass());
        if (entityData != null) {
            event.setMaxPackSize(entityData.getMaxInChunk());
            event.setResult(Result.ALLOW); // needed for changes to take effect
        }
    }

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityData entityData = CMSUtils.getEnvironment(event.getEntity().worldObj).classToEntityMapping.get(event.getEntityLiving().getClass());
        if (entityData != null && !entityData.getCanSpawn()) {
            if (entityData.getEnvironment().debug) {
                entityData.getEnvironment().envLog.logger.info("Denied spawn for entity " + entityData.getEntityClass()
                        + ". CanSpawn set to false or frequency set to 0!");
            }
            event.setResult(Result.DENY);
        }
    }

    // used for Despawner
    @SubscribeEvent
    public void onLivingDespawn(LivingSpawnEvent.AllowDespawn event) {
        if (CustomSpawner.forceDespawns) {
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
                if (CustomDespawner.isValidDespawnLightLevel(event.getEntity(), event.getWorld(), CMSUtils.getEnvironment(event.getWorld()).minDespawnLightLevel,
                        CMSUtils.getEnvironment(event.getWorld()).maxDespawnLightLevel)) {
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
            // despawn check
            EntityPlayer entityplayer = event.getWorld().getClosestPlayerToEntity(event.getEntityLiving(), -1D);
            if (entityplayer != null) //entityliving.canDespawn() &&
            {
                double d = ((Entity) (entityplayer)).posX - event.getEntityLiving().posX;
                double d1 = ((Entity) (entityplayer)).posY - event.getEntityLiving().posY;
                double d2 = ((Entity) (entityplayer)).posZ - event.getEntityLiving().posZ;
                double distance = d * d + d1 * d1 + d2 * d2;
                if (distance > 16384D) {
                    event.setResult(Result.ALLOW);
                } else if (event.getEntityLiving().getAge() > 600) {
                    if (distance < 1024D) {
                        return;
                    } else {
                        event.setResult(Result.ALLOW);
                    }
                }
            }

            if (event.getResult() == Result.ALLOW && CustomSpawner.debug) {
                int x = MathHelper.floor_double(event.getEntity().posX);
                int y = MathHelper.floor_double(event.getEntity().getEntityBoundingBox().minY);
                int z = MathHelper.floor_double(event.getEntity().posZ);
                CMSUtils.getEnvironment(event.getWorld()).envLog.logger.info("Forced Despawn of entity " + event.getEntityLiving() + " at " + x + ", " + y
                        + ", " + z + ". To prevent forced despawns, use /moc forceDespawns false.");
            }
        }
        EntityData entityData = CMSUtils.getEnvironment(event.getEntity().worldObj).classToEntityMapping.get(event.getEntityLiving().getClass());
        if (entityData != null && entityData.getLivingSpawnType() == CMSUtils.getEnvironment(event.getEntity().worldObj).LIVINGTYPE_UNDERGROUND) {
            event.setResult(Result.ALLOW);
        }
    }

    // this triggers before serverStarting
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        CMSUtils.addWorldEnvironment(event.getWorld().provider.getClass());
        GameRules gameRule = event.getWorld().getGameRules();
        if (gameRule != null) {
            gameRule.setOrCreateGameRule("doMobSpawning", new Boolean(CustomSpawner.doMobSpawning).toString());
        }
    }

    @SubscribeEvent
    public void structureMapGen(InitMapGenEvent event) {
        if (event.getType() == EventType.NETHER_BRIDGE) {
            if (CustomSpawner.environmentMap.get(WorldProviderHell.class) == null) {
                CMSUtils.addWorldEnvironment(WorldProviderHell.class);
            }
            CustomSpawner.environmentMap.get(WorldProviderHell.class).structureData.registerStructure(
                    CustomSpawner.environmentMap.get(WorldProviderHell.class), event.getType(), event.getOriginalGen());
        } else if (event.getType() == EventType.SCATTERED_FEATURE) {
            if (CustomSpawner.environmentMap.get(WorldProviderSurface.class) == null) {
                CMSUtils.addWorldEnvironment(WorldProviderSurface.class);
            }
            CustomSpawner.environmentMap.get(WorldProviderSurface.class).structureData.registerStructure(
                    CustomSpawner.environmentMap.get(WorldProviderSurface.class), event.getType(), event.getOriginalGen());
        }
    }
}
