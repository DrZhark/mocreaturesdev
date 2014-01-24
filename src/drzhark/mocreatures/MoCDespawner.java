package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import drzhark.customspawner.utils.CMSUtils;
import drzhark.mocreatures.utils.MoCLog;

public class MoCDespawner {

    public static boolean debug = false;
    public static int despawnLightLevel = 7;
    public static int despawnTickRate = 111;
    public List<BiomeGenBase> biomeList = new ArrayList<BiomeGenBase>();
    private List<Class> vanillaClassList;

    public MoCDespawner()
    {
        biomeList = new ArrayList<BiomeGenBase>();
        try
        {
            for (BiomeGenBase biomegenbase : BiomeGenBase.getBiomeGenArray())
            {
                if (biomegenbase == null)
                {
                    continue;
                }

                biomeList.add(biomegenbase);
            }

            vanillaClassList = new ArrayList<Class>();
            vanillaClassList.add(EntityChicken.class);
            vanillaClassList.add(EntityCow.class);
            vanillaClassList.add(EntityPig.class);
            vanillaClassList.add(EntitySheep.class);
            vanillaClassList.add(EntityWolf.class);
            vanillaClassList.add(EntitySquid.class);
            vanillaClassList.add(EntityOcelot.class);
            vanillaClassList.add(EntityBat.class);
            vanillaClassList.add(EntityHorse.class);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    //New DesPawner stuff
    protected final static int entityDespawnCheck(WorldServer worldObj, EntityLiving entityliving, int minDespawnLightLevel, int maxDespawnLightLevel)
    {
        if (entityliving instanceof EntityWolf && ((EntityWolf) entityliving).isTamed()) { return 0; }
        if (entityliving instanceof EntityOcelot && ((EntityOcelot) entityliving).isTamed()) { return 0; }
        if (!isValidDespawnLightLevel(entityliving, worldObj, minDespawnLightLevel, maxDespawnLightLevel)) { return 0; }

        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(entityliving, -1D);
        if (entityplayer != null) //entityliving.canDespawn() && 
        {
            double d = ((Entity) (entityplayer)).posX - entityliving.posX;
            double d1 = ((Entity) (entityplayer)).posY - entityliving.posY;
            double d2 = ((Entity) (entityplayer)).posZ - entityliving.posZ;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 > 16384D)
            {
                entityliving.setDead();
                return 1;
            }
            if (entityliving.getAge() > 600 && worldObj.rand.nextInt(800) == 0)
            {
                if (d3 < 1024D)
                {
                    //entityliving.attackEntityFrom(DamageSource.generic, 0);
                }
                else
                {
                    entityliving.setDead();
                    return 1;
                }
            }
        }
        return 0;
    }

    public final static int despawnVanillaAnimals(WorldServer worldObj, int minDespawnLightLevel, int maxDespawnLightLevel)
    {
        int count = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++)
        {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if (!(entity instanceof EntityLiving))
            {
                continue;
            }
            if ((entity instanceof EntityHorse || entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityPig || entity instanceof EntityOcelot || entity instanceof EntityChicken || entity instanceof EntitySquid || entity instanceof EntityWolf || entity instanceof EntityMooshroom))
            {
                count += entityDespawnCheck(worldObj, (EntityLiving) entity, minDespawnLightLevel, maxDespawnLightLevel);
            }
        }
        return count;
    }

    public final int countEntities(Class class1, World worldObj)
    {
        int i = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++)
        {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if (class1.isAssignableFrom(entity.getClass()))
            {
                i++;
            }
        }

        return i;
    }

    public static boolean isValidLightLevel(Entity entity, WorldServer worldObj, int lightLevel, boolean checkAmbientLightLevel)
    {
        if (entity.isCreatureType(EnumCreatureType.monster, false)) // ignore monsters since monsters should be checking ValidLightLevel
            return true;
        else if (entity.isCreatureType(EnumCreatureType.ambient, false) && !checkAmbientLightLevel)
        {
            return true;
        }
        else if (!entity.isCreatureType(EnumCreatureType.creature, false))
        {
            return true;
        }
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
        if (i > lightLevel)
        {
            if (debug) MoCLog.logger.info("Denied spawn! for " + entity.getCommandSenderName() + ". LightLevel over threshold of " + lightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
        }
        return i <= lightLevel;
    }
    
    public static boolean isValidDespawnLightLevel(Entity entity, World worldObj, int minDespawnLightLevel, int maxDespawnLightLevel)
    {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.boundingBox.minY);
        int z = MathHelper.floor_double(entity.posZ);
        int blockLightLevel = 0;
        if (y >= 0)
        {
            if (y >= 256)
            {
                y = 255;
            }
            blockLightLevel = CMSUtils.getBlockLightValue(worldObj.getChunkFromChunkCoords(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (blockLightLevel < minDespawnLightLevel && maxDespawnLightLevel != -1)
        {
            //if (debug) CMSUtils.getEnvironment(worldObj).envLog.logger.info("Denied spawn! for " + entity.getCommandSenderName() + blockLightLevel + " under minimum threshold of " + minDespawnLightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
            return false;
        }
        else if (blockLightLevel > maxDespawnLightLevel && maxDespawnLightLevel != -1)
        {
            //if (debug) CMSUtils.getEnvironment(worldObj).envLog.logger.info("Denied spawn! for " + entity.getCommandSenderName() + blockLightLevel + " over maximum threshold of " + maxDespawnLightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
            return false;
        }
        return true;
    }

    /**
     * Gets the amount of light on a block without taking into account sunlight
     */
    public static int getBlockLightValue(Chunk chunk, int x, int y, int z)
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
