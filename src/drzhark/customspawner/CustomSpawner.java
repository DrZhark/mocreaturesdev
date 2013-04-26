package drzhark.customspawner;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenEnd;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.Event.Result;
import cpw.mods.fml.common.FMLLog;
//import net.minecraftforge.event.entity.living.LivingSpecialSpawnEvent;
import cpw.mods.fml.common.Mod;
import drzhark.mocreatures.MoCreatures;

@Mod(modid = "CustomSpawner", name = "DrZhark's CustomSpawner", version = "1.12.5")
public final class CustomSpawner {
    private int maxAnimals = 40;
    private int maxMobs = 60;
    private int maxAquatic = 10;
    private int maxAmbient = 10;

    public List<BiomeGenBase> biomeList;
    public List[] entityClasses;
    protected List[] customMobSpawnList;
    protected List[] customAmbientSpawnList;
    protected List[] customCreatureSpawnList;
    protected List[] customAquaticSpawnList;
    private List<Class> vanillaClassList;
    private static Logger log = Logger.getLogger("CustomSpawner");

    private static HashMap eligibleChunksForSpawning = new HashMap();

    public CustomSpawner()
    {
        biomeList = new ArrayList<BiomeGenBase>();
        log.setParent(FMLLog.getLogger());
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

            customCreatureSpawnList = new List[biomeList.size()];
            customMobSpawnList = new List[biomeList.size()];
            customAmbientSpawnList = new List[biomeList.size()];
            customAquaticSpawnList = new List[biomeList.size()];
            entityClasses = new List[4];
            vanillaClassList = new ArrayList<Class>();
            vanillaClassList.add(EntityChicken.class);
            vanillaClassList.add(EntityCow.class);
            vanillaClassList.add(EntityPig.class);
            vanillaClassList.add(EntitySheep.class);
            vanillaClassList.add(EntityWolf.class);
            vanillaClassList.add(EntitySquid.class);
            vanillaClassList.add(EntityOcelot.class);
            vanillaClassList.add(EntityBat.class);
            clearLists();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    protected static ChunkPosition getRandomSpawningPointInChunk(World worldObj, int par1, int par2)
    {
        Chunk chunk = worldObj.getChunkFromChunkCoords(par1, par2);
        int i = par1 * 16 + worldObj.rand.nextInt(16);
        int j = worldObj.rand.nextInt(chunk == null ? worldObj.getActualHeight() : chunk.getTopFilledSegment() + 16 - 1);
        int k = par2 * 16 + worldObj.rand.nextInt(16);
        return new ChunkPosition(i, j, k);
    }

    public void clearLists()
    {
        for (int x = 0; x < biomeList.size(); x++)
        {
            customCreatureSpawnList[x] = new ArrayList();
            customMobSpawnList[x] = new ArrayList();
            customAmbientSpawnList[x] = new ArrayList();
            customAquaticSpawnList[x] = new ArrayList();
        }
        for (int x = 0; x < 4; x++)
        {
            entityClasses[x] = new ArrayList();
        }
    }

    //this one spawns a single mob up to max times
    public final int doSpecificSpawning(WorldServer worldObj, Class myClass, int max, EnumCreatureType enumcreaturetype)
    {
        //this initialises chunks for spawning
        eligibleChunksForSpawning.clear();
        int countTotal;
        int var6;

        for (countTotal = 0; countTotal < worldObj.playerEntities.size(); ++countTotal)
        {
            EntityPlayer entityplayer = (EntityPlayer) worldObj.playerEntities.get(countTotal);
            int var5 = MathHelper.floor_double(entityplayer.posX / 16.0D);
            var6 = MathHelper.floor_double(entityplayer.posZ / 16.0D);
            byte var7 = 8;

            for (int var8 = -var7; var8 <= var7; ++var8)
            {
                for (int var9 = -var7; var9 <= var7; ++var9)
                {
                    boolean var10 = var8 == -var7 || var8 == var7 || var9 == -var7 || var9 == var7;
                    ChunkCoordIntPair var11 = new ChunkCoordIntPair(var8 + var5, var9 + var6);
                    if (!var10)
                    {
                        eligibleChunksForSpawning.put(var11, Boolean.valueOf(false));
                    }
                    else if (!eligibleChunksForSpawning.containsKey(var11))
                    {
                        eligibleChunksForSpawning.put(var11, Boolean.valueOf(true));
                    }
                }
            }

        }
        countTotal = 0;
        ChunkCoordinates chunkcoordspawn = worldObj.getSpawnPoint();

        Iterator iterator = eligibleChunksForSpawning.keySet().iterator();
        label113: while (iterator.hasNext())
        {
            ChunkCoordIntPair var10 = (ChunkCoordIntPair) iterator.next();

            ChunkPosition chunkpos = getRandomSpawningPointInChunk(worldObj, var10.chunkXPos * 16, var10.chunkZPos * 16);
            int chunkX = chunkpos.x;
            int chunkY = chunkpos.y;
            int chunkZ = chunkpos.z;
            if (!worldObj.isBlockNormalCube(chunkX, chunkY, chunkZ) && worldObj.getBlockMaterial(chunkX, chunkY, chunkZ) == enumcreaturetype.getCreatureMaterial())
            {
                int countSpawn = 0;

                for (int var21 = 0; var21 < 3; ++var21)
                {
                    int tempPosX = chunkX;
                    int tempPosY = chunkY;
                    int tempPosZ = chunkZ;
                    byte var25 = 6;

                    for (int var26 = 0; var26 < 4; ++var26)
                    {
                        tempPosX += worldObj.rand.nextInt(var25) - worldObj.rand.nextInt(var25);
                        tempPosY += worldObj.rand.nextInt(1) - worldObj.rand.nextInt(1);
                        tempPosZ += worldObj.rand.nextInt(var25) - worldObj.rand.nextInt(var25);
                        if (canCreatureTypeSpawnAtLocation(enumcreaturetype, worldObj, tempPosX, tempPosY, tempPosZ))
                        {
                            float finalPosX = (float) tempPosX + 0.5F;
                            float finalPosY = (float) tempPosY;
                            float finalPosZ = (float) tempPosZ + 0.5F;
                            if (worldObj.getClosestPlayer((double) finalPosX, (double) finalPosY, (double) finalPosZ, 24.0D) == null)
                            {
                                float distSpawnX = finalPosX - (float) chunkcoordspawn.posX;
                                float distSpawnY = finalPosY - (float) chunkcoordspawn.posY;
                                float distSpawnZ = finalPosZ - (float) chunkcoordspawn.posZ;
                                float sqDist = distSpawnX * distSpawnX + distSpawnY * distSpawnY + distSpawnZ * distSpawnZ;
                                if (sqDist >= 576.0F)
                                {
                                    EntityLiving entityliving;
                                    try
                                    {
                                        entityliving = (EntityLiving) myClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldObj });
                                    }
                                    catch (Exception exception)
                                    {
                                        exception.printStackTrace();
                                        return countTotal;
                                    }

                                    entityliving.setLocationAndAngles((double) finalPosX, (double) finalPosY, (double) finalPosZ, worldObj.rand.nextFloat() * 360.0F, 0.0F);
                                    if (entityliving.getCanSpawnHere())
                                    {
                                        ++countSpawn;
                                        countTotal += countSpawn;
                                        if (countTotal > max) { return countTotal; }

                                        worldObj.spawnEntityInWorld(entityliving);
                                        if (countSpawn >= entityliving.getMaxSpawnedInChunk())
                                        {
                                            continue label113;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        return countTotal;
    }

    /**
     * New customSpawner
     * 
     * 
     */
    public final int doCustomSpawning(WorldServer worldObj, boolean spawnMobs, boolean spawnAnmls)
    {
        if (!spawnMobs && !spawnAnmls)
        {
            return 0;
        }
        else
        {
            eligibleChunksForSpawning.clear();
            int countTotal;
            int var6;

            for (countTotal = 0; countTotal < worldObj.playerEntities.size(); ++countTotal)
            {
                EntityPlayer entityplayer = (EntityPlayer) worldObj.playerEntities.get(countTotal);
                int var5 = MathHelper.floor_double(entityplayer.posX / 16.0D);
                var6 = MathHelper.floor_double(entityplayer.posZ / 16.0D);
                byte var7 = 8;

                for (int var8 = -var7; var8 <= var7; ++var8)
                {
                    for (int var9 = -var7; var9 <= var7; ++var9)
                    {
                        boolean var10 = var8 == -var7 || var8 == var7 || var9 == -var7 || var9 == var7;
                        ChunkCoordIntPair var11 = new ChunkCoordIntPair(var8 + var5, var9 + var6);

                        if (!var10)
                        {
                            eligibleChunksForSpawning.put(var11, Boolean.valueOf(false));
                        }
                        else if (!eligibleChunksForSpawning.containsKey(var11))
                        {
                            eligibleChunksForSpawning.put(var11, Boolean.valueOf(true));
                        }
                    }
                }
            }

            countTotal = 0;
            ChunkCoordinates chunkcoordspawn = worldObj.getSpawnPoint();
            EnumCreatureType[] enumcreaturevalues = EnumCreatureType.values();
            var6 = enumcreaturevalues.length;
            //0 = monster, 1 = creature, 2 = ambient, 3 = watercreature

            for (int enumType = 0; enumType < var6; ++enumType)
            {
                EnumCreatureType enumcreaturetype = enumcreaturevalues[enumType];

                int enumC = countSpawnedEntities(worldObj, enumcreaturetype);

                //modified to allow custom creature counts instead of vanillas
                if ((!enumcreaturetype.getPeacefulCreature() || spawnAnmls) && (enumcreaturetype.getPeacefulCreature() || spawnMobs) && (enumC <= getMax(enumcreaturetype) * eligibleChunksForSpawning.size() / 256))
                {
                    Iterator iterator = eligibleChunksForSpawning.keySet().iterator();
                    ArrayList<ChunkCoordIntPair> tmp = new ArrayList(eligibleChunksForSpawning.keySet());
                    Collections.shuffle(tmp);
                    iterator = tmp.iterator();

                    label108: while (iterator.hasNext())
                    {
                        ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) iterator.next();

                        if (eligibleChunksForSpawning.get(chunkcoordintpair) != null && !((Boolean) eligibleChunksForSpawning.get(chunkcoordintpair)).booleanValue()) // blood - added null check to avoid crashing during SSP spawning
                        {
                            ChunkPosition chunkpos = getRandomSpawningPointInChunk(worldObj, chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
                            int posX = chunkpos.x;
                            int posY = chunkpos.y;
                            int posZ = chunkpos.z;

                            if (!worldObj.isBlockNormalCube(posX, posY, posZ) && worldObj.getBlockMaterial(posX, posY, posZ) == enumcreaturetype.getCreatureMaterial())
                            {
                                int spawnedMob = 0;
                                int spawnCount = 0;

                                while (spawnCount < 3)
                                {
                                    int tempX = posX;
                                    int tempY = posY;
                                    int tempZ = posZ;
                                    byte var20 = 6;
                                    SpawnListEntry spawnlistentry = null;
                                    int spawnAttempt = 0;

                                    while (true)
                                    {
                                        if (spawnAttempt < 4)

                                        {
                                            label101:
                                            {
                                                tempX += worldObj.rand.nextInt(var20) - worldObj.rand.nextInt(var20);
                                                tempY += worldObj.rand.nextInt(1) - worldObj.rand.nextInt(1);
                                                tempZ += worldObj.rand.nextInt(var20) - worldObj.rand.nextInt(var20);

                                                //if(canCreatureTypeSpawnAtLocation(enumcreaturetype, worldObj, tempPosX, tempPosY, tempPosZ))
                                                if (canCreatureTypeSpawnAtLocation(enumcreaturetype, worldObj, tempX, tempY, tempZ))
                                                {
                                                    float spawnX = (float) tempX + 0.5F;
                                                    float spawnY = (float) tempY;
                                                    float spawnZ = (float) tempZ + 0.5F;
                                                    //changed so creatures spawn closer
                                                    if (worldObj.getClosestPlayer((double) spawnX, (double) spawnY, (double) spawnZ, 24.0D) == null)
                                                    {
                                                        float var26 = spawnX - (float) chunkcoordspawn.posX;
                                                        float var27 = spawnY - (float) chunkcoordspawn.posY;
                                                        float var28 = spawnZ - (float) chunkcoordspawn.posZ;
                                                        float spawnDist = var26 * var26 + var27 * var27 + var28 * var28;
                                                        //changed as well to make creatures spawn closer
                                                        if (spawnDist >= 256.0F)
                                                        {
                                                            if (spawnlistentry == null)
                                                            {
                                                                //this is where it has to be changed to include the custom list
                                                                //spawnlistentry = worldObj.getRandomMob(enumcreaturetype, tempX, tempY, tempZ);
                                                                spawnlistentry = getRandomCustomMob(worldObj, enumcreaturetype, tempX, tempY, tempZ);

                                                                if (spawnlistentry == null)
                                                                {
                                                                    break label101;
                                                                }
                                                            }

                                                            EntityLiving entityliving;

                                                                try
                                                                {
                                                                    entityliving = (EntityLiving) spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldObj });
                                                                }
                                                                catch (Exception exception)
                                                                {
                                                                    exception.printStackTrace();
                                                                    return countTotal;
                                                                }

                                                                entityliving.setLocationAndAngles((double) spawnX, (double) spawnY, (double) spawnZ, worldObj.rand.nextFloat() * 360.0F, 0.0F);

                                                                    if (entityliving.getCanSpawnHere())
                                                                    {
                                                                        ++spawnedMob;
                                                                        worldObj.spawnEntityInWorld(entityliving);
                                                                        creatureSpecificInit(entityliving, worldObj, spawnX, spawnY, spawnZ);
                                                                // changed check from maxSpawnedInChunk to maxGroupCount.
                                                                if (MoCreatures.proxy.debugLogging) log.info("spawned " + entityliving + ", spawnedMob = " + spawnedMob + ", maxGroupCount = " + spawnlistentry.maxGroupCount);
                                                                if (spawnedMob >= spawnlistentry.maxGroupCount)
                                                                        {
                                                                            continue label108;
                                                                        }
                                                                    }
                                                                    else
                                                                    {
                                                                        if (MoCreatures.proxy.debugLogging) log.info("unable to spawn " + entityliving + " at coords " + var26 + ", " + var27 + ", " + var28);
                                                                    }
                                                                    countTotal += spawnedMob;
                                                                }
                                                    }
                                                }

                                                ++spawnAttempt;
                                                continue;
                                            }
                                        }

                                        ++spawnCount;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return countTotal;
        }
    }

    public void AddCustomSpawn(Class class1, int i, int max, EnumCreatureType enumcreaturetype)
    {
        AddCustomSpawn(class1, i, 4, max, enumcreaturetype, null);
    }

    public void AddCustomSpawn(Class class1, int i, EnumCreatureType enumcreaturetype)
    {
        AddCustomSpawn(class1, i, 4, 5, enumcreaturetype, null);
    }

    public void AddCustomSpawn(Class class1, int i, int max, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        AddCustomSpawn(class1, i, 4, max, enumcreaturetype, abiomegenbase);
    }

    public void AddCustomSpawn(Class class1, int i, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        AddCustomSpawn(class1, i, 4, 5, enumcreaturetype, abiomegenbase);
    }

    //this one adds spawn where biome is not specified
    public void AddCustomSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype)
    {
        AddCustomSpawn(class1, i, j, k, enumcreaturetype, null);
    }

    public void AddCustomSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        if (MoCreatures.proxy.debugLogging) log.info("AddCustomSpawn class " + class1 + ", at coords " + i + ", " + j + ", " + k + "  type = " + enumcreaturetype);
        if (class1 == null) { throw new IllegalArgumentException("entityClass cannot be null"); }
        if (enumcreaturetype == null) { throw new IllegalArgumentException("spawnList cannot be null"); }
        if (abiomegenbase == null)
        {
            abiomegenbase = new BiomeGenBase[biomeList.size()];
            abiomegenbase = biomeList.toArray(abiomegenbase);
        }

        int x1 = getEnumIndex(enumcreaturetype);
        {
            boolean flag = false;
            for (Iterator iterator = entityClasses[x1].iterator(); iterator.hasNext();)
            {
                if (iterator != null)
                {
                    Class class2 = (Class) iterator.next();
                    if (class2 == class1)
                    {
                        flag = true;
                        break;
                    }
                }
            }

            if (!flag)
            {
                entityClasses[x1].add(class1);
            }
        }

        for (BiomeGenBase element : abiomegenbase)
        {
            List[] fulllist = getCustomSpawnableList(enumcreaturetype);
            if (fulllist != null)
            {
                int x = biomeList.indexOf(element);
                if (x < 0)
                {
                    continue;
                }
                boolean flag = false;
                for (Iterator iterator = fulllist[x].iterator(); iterator.hasNext();)
                {
                    if (iterator != null)
                    {
                        SpawnListEntry spawnlistentry = (SpawnListEntry) iterator.next();
                        if (spawnlistentry.entityClass == class1)
                        {
                            spawnlistentry.itemWeight = i;
                            spawnlistentry.minGroupCount = j;
                            spawnlistentry.maxGroupCount = k;
                            flag = true;
                            break;
                        }
                    }
                }

                if (!flag)
                {
                    fulllist[x].add(new SpawnListEntry(class1, i, j, k));
                }
            }
        }

    }

    public void RemoveCustomSpawn(Class class1, EnumCreatureType enumcreaturetype)
    {
        RemoveCustomSpawn(class1, enumcreaturetype, null);
    }

    public void RemoveCustomSpawn(Class class1, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        if (class1 == null) { throw new IllegalArgumentException("entityClass cannot be null"); }
        if (enumcreaturetype == null) { throw new IllegalArgumentException("spawnList cannot be null"); }
        if (abiomegenbase == null)
        {
            abiomegenbase = new BiomeGenBase[biomeList.size()];
            abiomegenbase = biomeList.toArray(abiomegenbase);
        }

        for (BiomeGenBase element : abiomegenbase)
        {
            List[] fulllist = getCustomSpawnableList(enumcreaturetype);

            if (fulllist != null)
            {
                int x = biomeList.indexOf(element.biomeName);
                for (Iterator iterator = fulllist[x].iterator(); iterator.hasNext();)
                {
                    if (iterator != null)
                    {
                        SpawnListEntry spawnlistentry = (SpawnListEntry) iterator.next();
                        if (spawnlistentry.entityClass == class1)
                        {
                            iterator.remove();
                        }
                    }
                }

            }

        }

    }

    private int getEnumIndex(EnumCreatureType enumcreaturetype)
    {
        if (enumcreaturetype == EnumCreatureType.monster) { return 0; }
        if (enumcreaturetype == EnumCreatureType.creature) { return 1; }
        if (enumcreaturetype == EnumCreatureType.waterCreature) { return 3; }
        if (enumcreaturetype == EnumCreatureType.ambient) { return 2; }
        return 0;
    }

    public int countSpawnedEntities(World world, EnumCreatureType enumcreaturetype)
    {
        int i = getEnumIndex(enumcreaturetype);
        int finalcount = 0;
        {
            boolean flag = false;
            for (Iterator iterator = entityClasses[i].iterator(); iterator.hasNext();)
            {
                try
                {
                    if (iterator != null)
                    {
                        Class class1 = (Class) iterator.next();
                        if (class1 != null)
                        {
                            finalcount += world.countEntities(class1);
                        }
                    }
                }
                catch (Exception e)
                {
                }
            }
        }
        return finalcount;
    }

    private List[] getCustomSpawnableList(EnumCreatureType enumcreaturetype)
    {
        if (enumcreaturetype == EnumCreatureType.monster) { return customMobSpawnList; }
        if (enumcreaturetype == EnumCreatureType.creature) { return customCreatureSpawnList; }
        if (enumcreaturetype == EnumCreatureType.waterCreature) { return customAquaticSpawnList; }
        if (enumcreaturetype == EnumCreatureType.ambient) { return customAmbientSpawnList; }
        return null;
    }

    private List getCustomBiomeSpawnList(List[] fulllist, BiomeGenBase biomegenbase)
    {
        int x = biomeList.indexOf(biomegenbase);
        if (x >= 0) { return fulllist[x]; }
        return null;
    }

    public List getCustomBiomeSpawnList(BiomeGenBase biome)
    {
        int x = biomeList.indexOf(biome);
        if (x >= 0) { return this.customCreatureSpawnList[x]; }
        return null;
    }

    private int getMax(EnumCreatureType enumcreaturetype)
    {
        if (enumcreaturetype == EnumCreatureType.monster) { return getMaxMobs(); }
        if (enumcreaturetype == EnumCreatureType.creature) { return getMaxAnimals(); }
        if (enumcreaturetype == EnumCreatureType.waterCreature) { return getMaxAquatic(); }
        if (enumcreaturetype == EnumCreatureType.ambient) { return getMaxAmbient(); }
        return 30;
    }

    public int getMaxAnimals()
    {
        return maxAnimals;
    }

    public void setMaxAnimals(int max)
    {
        maxAnimals = max;
    }

    public int getMaxMobs()
    {
        return maxMobs;
    }

    public void setMaxMobs(int max)
    {
        maxMobs = max;
    }

    public int getMaxAquatic()
    {
        return maxAquatic;
    }

    public void setMaxAquatic(int max)
    {
        maxAquatic = max;
    }

    public int getMaxAmbient()
    {
        return maxAmbient;
    }

    public void setMaxAmbient(int max)
    {
        maxAmbient = max;
    }

    /*public int getMaxSpawnsPerChunk()
    {
        return maxSpawnsPerChunk;
    }

    public void setMaxSpawnsPerChunk(int max)
    {
        maxSpawnsPerChunk = max;
    }*/

    private static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType enumcreaturetype, World world, int i, int j, int k)
    {
        if (enumcreaturetype.getCreatureMaterial() == Material.water)
        {
            return world.getBlockMaterial(i, j, k).isLiquid() && !world.isBlockNormalCube(i, j + 1, k);
        }
        else
        {
            return world.isBlockNormalCube(i, j - 1, k) && !world.isBlockNormalCube(i, j, k) && !world.getBlockMaterial(i, j, k).isLiquid() && !world.isBlockNormalCube(i, j + 1, k);
        }
    }

    //New DesPawner stuff
    protected final int entityDespawnCheck(WorldServer worldObj, EntityLiving entityliving)
    {
        
        if (entityliving instanceof EntityWolf && ((EntityWolf) entityliving).isTamed()) { return 0; }
        if (isNearTorch(entityliving, 8D, worldObj)) { return 0; }

        /*if (!(entityliving instanceof EntityCow 
        		|| entityliving instanceof EntitySheep 
        		|| entityliving instanceof EntityChicken 
        		|| entityliving instanceof EntityPig
        		|| entityliving instanceof EntityOcelot
        		|| entityliving instanceof EntitySquid
        		))
        {
        	return 0;
        }*/
        
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

    public final int despawnVanillaAnimals(WorldServer worldObj)
    {
        int count = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++)
        {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if (!(entity instanceof EntityLiving))
            {
                continue;
            }
            if ((entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityPig || entity instanceof EntityOcelot || entity instanceof EntityChicken || entity instanceof EntitySquid || entity instanceof EntityWolf))
            {
                count += entityDespawnCheck(worldObj, (EntityLiving) entity);

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

    /**
     * Gets a random custom mob for spawning based on XYZ coordinates
     */
    public SpawnListEntry getRandomCustomMob(World worldObj, EnumCreatureType enumCreatureType, int pX, int pY, int pZ)
    {
        List list = getPossibleCustomCreatures(worldObj, enumCreatureType, pX, pY, pZ);
        if (list == null || list.isEmpty())
        {
            //System.out.println("list = NULL!! for type " + enumCreatureType.name());
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
    public List getPossibleCustomCreatures(World worldObj, EnumCreatureType enumcreaturetype, int pX, int pY, int pZ)
    {
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(pX, pZ);
        if (biomegenbase == null)
        {
            //System.out.println("null biome");
            return null;
        }
        else
        {
            return getCustomBiomeSpawnList(getCustomSpawnableList(enumcreaturetype), biomegenbase);
        }
    }

    public static boolean isNearTorch(Entity entity, Double dist, World worldObj)
    {
        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(dist, 2D, dist);
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    int j2 = worldObj.getBlockId(k1, l1, i2);

                    if (j2 != 0)
                    {
                        String nameToCheck = "";
                        nameToCheck = Block.blocksList[j2].getUnlocalizedName();//.getBlockName();
                        if (nameToCheck != null && nameToCheck != "" && nameToCheck.equals("tile.fence"))
                        {
                        	//System.out.println("fence! @ " + entity);
                        	return true;
                            //if (nameToCheck.equals("tile.torch") || nameToCheck.equals("tile.lightgem") || nameToCheck.equals("tile.redstoneLight") || nameToCheck.equals("tile.litpumpkin")) { return true; }
                        }

                    }

                }

            }

        }

        return false;
    }

   
    /**
     * determines if a skeleton spawns on a spider, and if a sheep is a different color
     */
    private static void creatureSpecificInit(EntityLiving par0EntityLiving, World par1World, float par2, float par3, float par4)
    {
        if (ForgeEventFactory.doSpecialSpawn(par0EntityLiving, par1World, par2, par3, par4))
        {
            return;
        }

        par0EntityLiving.initCreature();
    }

    /**
     * Called during chunk generation to spawn initial creatures.
     */
    public static void performWorldGenSpawning(World worldObj, BiomeGenBase par1BiomeGenBase, int par2, int par3, int par4, int par5, Random par6Random, List customSpawnList)
    {
        if (!customSpawnList.isEmpty() && MoCreatures.proxy.worldGenCreatureSpawning)
        {
            while (par6Random.nextFloat() < par1BiomeGenBase.getSpawningChance())
            {
                //this is where it has to be changed to include the custom list
                //spawnlistentry = worldObj.getRandomMob(enumcreaturetype, tempX, tempY, tempZ);
                SpawnListEntry spawnlistentry = (SpawnListEntry)WeightedRandom.getRandomItem(worldObj.rand, customSpawnList);
                int i1 = spawnlistentry.minGroupCount + par6Random.nextInt(1 + spawnlistentry.maxGroupCount - spawnlistentry.minGroupCount);
                int j1 = par2 + par6Random.nextInt(par4);
                int k1 = par3 + par6Random.nextInt(par5);
                int l1 = j1;
                int i2 = k1;

                for (int j2 = 0; j2 < i1; ++j2)
                {
                    boolean flag = false;

                    for (int k2 = 0; !flag && k2 < 4; ++k2)
                    {
                        int l2 = worldObj.getTopSolidOrLiquidBlock(j1, k1);

                        if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, worldObj, j1, l2, k1))
                        {
                            float f = (float)j1 + 0.5F;
                            float f1 = (float)l2;
                            float f2 = (float)k1 + 0.5F;
                            EntityLiving entityliving;

                            try
                            {
                                entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldObj});
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                                continue;
                            }

                            entityliving.setLocationAndAngles((double)f, (double)f1, (double)f2, par6Random.nextFloat() * 360.0F, 0.0F);
                            worldObj.spawnEntityInWorld(entityliving);
                            if (MoCreatures.proxy.debugLogging) log.info("worldgen spawned " + entityliving);
                            creatureSpecificInit(entityliving, worldObj, f, f1, f2);
                            flag = true;
                        }

                        j1 += par6Random.nextInt(5) - par6Random.nextInt(5);

                        for (k1 += par6Random.nextInt(5) - par6Random.nextInt(5); j1 < par2 || j1 >= par2 + par4 || k1 < par3 || k1 >= par3 + par4; k1 = i2 + par6Random.nextInt(5) - par6Random.nextInt(5))
                        {
                            j1 = l1 + par6Random.nextInt(5) - par6Random.nextInt(5);
                        }
                    }
                }
            }
        }
    }
}
