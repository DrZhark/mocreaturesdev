package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.Iterator;
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
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import drzhark.customspawner.EntitySpawnType;
import drzhark.mocreatures.utils.MoCLog;

public class MoCDespawner {

    public static boolean debug = false;
    public List<BiomeGenBase> biomeList = new ArrayList<BiomeGenBase>();
    private List<Class> vanillaClassList;

    public MoCDespawner()
    {
        biomeList = new ArrayList<BiomeGenBase>();
        try
        {
            for (BiomeGenBase biomegenbase : BiomeGenBase.biomeList)
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

    protected final int entityDespawnCheck(WorldServer worldObj, EntityLiving entityliving)
    {
        return entityDespawnCheck(worldObj, entityliving, 7);
    }

    //New DesPawner stuff
    protected final static int entityDespawnCheck(WorldServer worldObj, EntityLiving entityliving, int despawnLightLevel)
    {
        if (entityliving instanceof EntityWolf && ((EntityWolf) entityliving).isTamed()) { return 0; }
        if (entityliving instanceof EntityOcelot && ((EntityOcelot) entityliving).isTamed()) { return 0; }
        if (!isValidDespawnLightLevel(entityliving, worldObj, despawnLightLevel)) { return 0; }

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

    public final static int despawnVanillaAnimals(WorldServer worldObj, int despawnLightLevel)
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
                count += entityDespawnCheck(worldObj, (EntityLiving) entity, despawnLightLevel);
            }
        }
        return count;
    }

    public final int despawnMob(WorldServer worldObj)
    {
        List<Class> myNullList = null;
        return despawnMob(worldObj, myNullList);
    }

    public final int despawnMob(WorldServer worldObj, Class... classList)
    {
        List<Class> myList = new ArrayList();
        for (Class element : classList)
        {
            myList.add(element);
        }
        return despawnMob(worldObj, myList);
    }

    public final int despawnMob(WorldServer worldObj, List<Class> classList)
    {
        int count = 0;
        if (classList == null)
        {
            classList = vanillaClassList;
        }

        for (int j = 0; j < worldObj.loadedEntityList.size(); j++)
        {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if (!(entity instanceof EntityLiving))
            {
                continue;
            }
            for (Iterator iterator = classList.iterator(); iterator.hasNext();)
            {
                if (iterator != null)
                {
                    Class class2 = (Class) iterator.next();
                    if (class2 == entity.getClass())
                    {
                        count += entityDespawnCheck(worldObj, (EntityLiving) entity);
                    }
                }
            }

        }
        return count;
    }

    public final int despawnMobWithMinimum(WorldServer worldObj, Class class1, int minimum)
    {
        int killedcount = 0;
        int mobcount = countEntities(class1, worldObj);
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++)
        {
            if ((mobcount - killedcount) <= minimum)
            {
                worldObj.updateEntities();
                return killedcount;
            }
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if (!(entity instanceof EntityLiving))
            {
                continue;
            }
            if (class1 == entity.getClass())
            {
                killedcount += entityDespawnCheck(worldObj, (EntityLiving) entity);
            }
        }
        worldObj.updateEntities();
        return killedcount;

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

    /**
     * Gets a random custom mob for spawning based on XYZ coordinates
     */
    public SpawnListEntry getRandomCustomMob(World worldObj, EntitySpawnType entitySpawnType, int pX, int pY, int pZ)
    {
        List list = getPossibleCustomCreatures(worldObj, entitySpawnType, pX, pY, pZ);
        if (list == null || list.isEmpty())
        {
            return null;
        }
        else
        {
            return (SpawnListEntry) WeightedRandom.getRandomItem(worldObj.rand, list);
        }
    }

    /**
     * Returns a list of creatures of the specified type (mob/aquatic/animal)
     * that can spawn at the given XYZ location, based on biomes.
     */
    public List getPossibleCustomCreatures(World worldObj, EntitySpawnType entitySpawnType, int pX, int pY, int pZ)
    {
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(pX, pZ);
        if (biomegenbase == null)
        {
            return null;
        }
        else
        {
            //System.out.println("getting biomelist for biome " + biomegenbase.biomeName + ", size = " + entitySpawnType.getBiomeSpawnList(biomegenbase.biomeID).size());
            return entitySpawnType.getBiomeSpawnList(biomegenbase.biomeID);
        }
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
            if (debug) MoCLog.logger.info("Denied spawn! for " + entity.getEntityName() + ". LightLevel over threshold of " + lightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
        }
        return i <= lightLevel;
    }
    
    protected static boolean isValidDespawnLightLevel(Entity entity, WorldServer worldObj, int despawnLightLevel)
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
            if (debug) MoCLog.logger.info("Denied despawn! for vanilla " + entity.getEntityName() + ". LightLevel over threshold of " + despawnLightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
        }
        /*else {
            if (verboseConsole) CMSLog.logger.info("Valid LightLevel " + i + " found. Proceeding to despawn vanilla " + entity.getEntityName() + " at " + x + ", " + y + ", " + z);
        }*/
        return i <= despawnLightLevel;
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
