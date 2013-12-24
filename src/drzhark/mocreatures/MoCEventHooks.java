package drzhark.mocreatures;

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
import net.minecraft.util.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.WorldEvent;
import drzhark.mocreatures.utils.MoCLog;

public class MoCEventHooks {

    @ForgeSubscribe
    public void onWorldUnload(WorldEvent.Unload event)
    {
        // if overworld has been deleted or unloaded, reset our flag
        if (event.world.provider.dimensionId == 0)
        {
            MoCreatures.proxy.worldInitDone = false;
        }
    }

    @ForgeSubscribe
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (DimensionManager.getWorld(0) != null && !MoCreatures.proxy.worldInitDone) // if overworld has loaded, use its mapstorage
        {
            MoCPetMapData data = (MoCPetMapData)DimensionManager.getWorld(0).mapStorage.loadData(MoCPetMapData.class, "mocreatures");
            if (data == null)
            {
                data = new MoCPetMapData("mocreatures");
            }

            DimensionManager.getWorld(0).mapStorage.setData("mocreatures", data);
            DimensionManager.getWorld(0).mapStorage.saveAllData();
            MoCreatures.instance.mapData = data;
            MoCreatures.proxy.worldInitDone = true;
        }
        // make sure doMobSpawning is on if CMS is not installed
        GameRules gameRule = event.world.getGameRules();
        if (gameRule != null && !MoCreatures.isCustomSpawnerLoaded) {
            gameRule.setOrCreateGameRule("doMobSpawning", "true");
        }
    }

    // used for Despawner
    @ForgeSubscribe
    public void onLivingDespawn(LivingSpawnEvent.AllowDespawn event)
    {
        if (MoCreatures.proxy.forceDespawns && !MoCreatures.isCustomSpawnerLoaded)
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
                if (isValidDespawnLightLevel(event.entity, event.world, MoCreatures.proxy.despawnLightLevel))
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
            // Deny Rest
            if (event.entityLiving.getAge() > 600)
            {
                event.setResult(Result.ALLOW);
            }

            if (MoCreatures.proxy.debug) 
            {
                int x = MathHelper.floor_double(event.entity.posX);
                int y = MathHelper.floor_double(event.entity.boundingBox.minY);
                int z = MathHelper.floor_double(event.entity.posZ);
                MoCLog.logger.info("Forced Despawn of entity " + event.entityLiving + " at " + x + ", " + y + ", " + z + ". To prevent forced despawns, use /moc forceDespawns false.");
            }
        }
    }

    private boolean isValidDespawnLightLevel(Entity entity, World worldObj, int despawnLightLevel)
    {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.boundingBox.minY);
        int z = MathHelper.floor_double(entity.posZ);
        int i = 0;
        if (y >= 0)
        {
            if (y >= 256)
            {
                y = 255;
            }
            i = getBlockLightValue(worldObj.getChunkFromChunkCoords(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (i > despawnLightLevel)
        {
            if (MoCreatures.proxy.debug) MoCLog.logger.info("Denied despawn! for vanilla " + entity.getEntityName() + ". LightLevel over threshold of " + despawnLightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
        }
        /*else {
            if (verboseConsole) CMSLog.logger.info("Valid LightLevel " + i + " found. Proceeding to despawn vanilla " + entity.getEntityName() + " at " + x + ", " + y + ", " + z);
        }*/
        return i <= despawnLightLevel;
    }

    /**
     * Gets the amount of light on a block without taking into account sunlight
     */
    private int getBlockLightValue(Chunk chunk, int x, int y, int z)
    {
        ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y >> 4];

        if (extendedblockstorage == null)
        {
            return 0;
        }
        else
        {
            return extendedblockstorage.getExtBlocklightValue(x, y & 15, z);
        }
    }
}