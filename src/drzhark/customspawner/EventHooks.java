package drzhark.customspawner;

import java.util.List;

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
import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSUtils;

public class EventHooks {

    @SubscribeEvent
    public void peformCustomWorldGenSpawning(PopulateChunkEvent.Populate event)
    {
        int par1 = event.chunkX * 16;
        int par2 = event.chunkZ * 16;
        int x = event.chunkX * 16 + 8 + event.world.rand.nextInt(16);
        int z = event.chunkZ * 16 + 8 + event.world.rand.nextInt(16);
        CMSUtils.addWorldEnvironment(event.world.provider.getClass()); // make sure world environment has been created
        EnvironmentSettings environment = CMSUtils.getEnvironment(event.world);
        for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values())
        {
            if (entitySpawnType.getChunkSpawnChance() > 0)
            {
                List<SpawnListEntry> customSpawnList = entitySpawnType.getBiomeSpawnList(event.world.getBiomeGenForCoords(x, z).biomeID);
                if (customSpawnList != null)
                {
                    CustomSpawner.INSTANCE.performWorldGenSpawning(entitySpawnType, event.world, event.world.getBiomeGenForCoords(x, z), par1 + 8, par2 + 8, 16, 16, event.rand, customSpawnList, environment.worldGenCreatureSpawning);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingPackSize(LivingPackSizeEvent event)
    {
        EntityData entityData = CMSUtils.getEnvironment(event.entity.worldObj).classToEntityMapping.get(event.entityLiving.getClass());
        if (entityData != null)
        {
            event.maxPackSize = entityData.getMaxInChunk();
            event.setResult(Result.ALLOW); // needed for changes to take effect
        }
    }

    @SubscribeEvent
    public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        EntityData entityData = CMSUtils.getEnvironment(event.entity.worldObj).classToEntityMapping.get(event.entityLiving.getClass());
        int x = MathHelper.floor_double(event.x);
        int y = MathHelper.floor_double(event.y);
        int z = MathHelper.floor_double(event.z);
        if (entityData != null && !entityData.getCanSpawn())
        {
            if (entityData.getEnvironment().debug)
                entityData.getEnvironment().envLog.logger.info("Denied spawn for entity " + entityData.getEntityClass() + ". CanSpawn set to false or frequency set to 0!");
            event.setResult(Result.DENY);
        }
        /*BiomeGenBase biome = event.world.getBiomeGenForCoords(x, z);
        if (biome != null)
        {
            // handle biome specific spawn settings
        }*/
    }

    // used for Despawner
    @SubscribeEvent
    public void onLivingDespawn(LivingSpawnEvent.AllowDespawn event)
    {
        if (CustomSpawner.forceDespawns)
        {
            // try to guess what we should ignore
            // Monsters
            if ((IMob.class.isAssignableFrom(event.entityLiving.getClass()) || IRangedAttackMob.class.isAssignableFrom(event.entityLiving.getClass())) || event.entityLiving.isCreatureType(EnumCreatureType.monster, false))
            {
                return;
            }
            // Tameable
            if (event.entityLiving instanceof EntityTameable)
            {
                if (((EntityTameable)event.entityLiving).isTamed())
                {
                    return;
                }
            }
            // Farm animals
            if (event.entityLiving instanceof EntitySheep || event.entityLiving instanceof EntityPig || event.entityLiving instanceof EntityCow || event.entityLiving instanceof EntityChicken)
            {
                // check lightlevel
                if (CustomDespawner.isValidDespawnLightLevel(event.entity, event.world, CMSUtils.getEnvironment(event.world).minDespawnLightLevel, CMSUtils.getEnvironment(event.world).maxDespawnLightLevel))
                {
                    return;
                }
            }
            // Others
            NBTTagCompound nbt = new NBTTagCompound();
            event.entityLiving.writeToNBT(nbt);
            if (nbt != null)
            {
                if (nbt.hasKey("Owner") && !nbt.getString("Owner").equals(""))
                {
                    return; // ignore
                }
                if (nbt.hasKey("Tamed") && nbt.getBoolean("Tamed") == true)
                {
                    return; // ignore
                }
            }
            // despawn check
            EntityPlayer entityplayer = event.world.getClosestPlayerToEntity(event.entityLiving, -1D);
            if (entityplayer != null) //entityliving.canDespawn() && 
            {
                double d = ((Entity) (entityplayer)).posX - event.entityLiving.posX;
                double d1 = ((Entity) (entityplayer)).posY - event.entityLiving.posY;
                double d2 = ((Entity) (entityplayer)).posZ - event.entityLiving.posZ;
                double distance = d * d + d1 * d1 + d2 * d2;
                if (distance > 16384D)
                {
                    event.setResult(Result.ALLOW);
                }
                else if (event.entityLiving.getAge() > 600)
                {
                    if (distance < 1024D)
                    {
                        return;
                    }
                    else
                    {
                        event.setResult(Result.ALLOW);
                    }
                }
            }

            if (event.getResult() == Result.ALLOW && CustomSpawner.debug) 
            {
                int x = MathHelper.floor_double(event.entity.posX);
                int y = MathHelper.floor_double(event.entity.boundingBox.minY);
                int z = MathHelper.floor_double(event.entity.posZ);
                CMSUtils.getEnvironment(event.world).envLog.logger.info("Forced Despawn of entity " + event.entityLiving + " at " + x + ", " + y + ", " + z + ". To prevent forced despawns, use /moc forceDespawns false.");
            }
        }
        EntityData entityData = CMSUtils.getEnvironment(event.entity.worldObj).classToEntityMapping.get(event.entityLiving.getClass());
        if (entityData != null && entityData.getLivingSpawnType() == CMSUtils.getEnvironment(event.entity.worldObj).LIVINGTYPE_UNDERGROUND)
        {
            event.setResult(Result.ALLOW);
        }
    }

    // this triggers before serverStarting
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        CMSUtils.addWorldEnvironment(event.world.provider.getClass());
        GameRules gameRule = event.world.getGameRules();
        if (gameRule != null) {
            gameRule.setOrCreateGameRule("doMobSpawning", new Boolean(CustomSpawner.doMobSpawning).toString());
        }
    }

   @SubscribeEvent
    public void structureMapGen(InitMapGenEvent event)
    {
        String structureClass = event.originalGen.getClass().toString();

        if (event.type == event.type.NETHER_BRIDGE)
        {
            if (CustomSpawner.environmentMap.get(WorldProviderHell.class) == null)
                CMSUtils.addWorldEnvironment(WorldProviderHell.class);
            CustomSpawner.environmentMap.get(WorldProviderHell.class).structureData.registerStructure(CustomSpawner.environmentMap.get(WorldProviderHell.class), event.type, event.originalGen);
        }
        else if (event.type == event.type.SCATTERED_FEATURE)
        {
            if (CustomSpawner.environmentMap.get(WorldProviderSurface.class) == null)
                CMSUtils.addWorldEnvironment(WorldProviderSurface.class);
            CustomSpawner.environmentMap.get(WorldProviderSurface.class).structureData.registerStructure(CustomSpawner.environmentMap.get(WorldProviderSurface.class), event.type, event.originalGen);
        }
    }
}