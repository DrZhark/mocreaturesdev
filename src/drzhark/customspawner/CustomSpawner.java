package drzhark.customspawner;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeEventFactory;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;


@Mod(modid = "CustomSpawner", name = "DrZhark's CustomSpawner", version = "2.3.0")
public final class CustomSpawner {
    private int maxCreatures;
    private int maxMonsters;
    private int maxWaterCreatures;
    private int maxAmbients;
    public static boolean verboseConsole;

    public List<BiomeGenBase> biomeList;
    public List[] entityClasses;
    protected List[] customMonsterSpawnList;
    protected List[] customAmbientSpawnList;
    protected List[] customCreatureSpawnList;
    protected List[] customWaterCreatureSpawnList;
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
            customMonsterSpawnList = new List[biomeList.size()];
            customAmbientSpawnList = new List[biomeList.size()];
            customWaterCreatureSpawnList = new List[biomeList.size()];
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

    public void resetCMS()
    {
        this.biomeList = new ArrayList<BiomeGenBase>();
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
            customMonsterSpawnList = new List[biomeList.size()];
            customAmbientSpawnList = new List[biomeList.size()];
            customWaterCreatureSpawnList = new List[biomeList.size()];
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
            customMonsterSpawnList[x] = new ArrayList();
            customAmbientSpawnList[x] = new ArrayList();
            customWaterCreatureSpawnList[x] = new ArrayList();
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

    // Spigot start - get entity count only from chunks being processed in theChunkProviderServer
    public static final int getEntityCount(WorldServer server, EnumCreatureType type)
    {
        int i = 0;
        Iterator iterator = eligibleChunksForSpawning.keySet().iterator();
        while (iterator.hasNext())
        {
            ChunkCoordIntPair chunkcoordintpair1 = (ChunkCoordIntPair)iterator.next();

            if (server.theChunkProviderServer.chunkExists(chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos))
            {
                for (List<Entity> entitySlice : server.getChunkFromChunkCoords(chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos).entityLists)
                {
                    for (Entity entity : entitySlice)
                    {
                        if (entity.isCreatureType(type, true))
                        {
                            ++i;
                        }
                    }
                }
            }
        }
        return i;
    }
    // Spigot end

    /**
     * New customSpawner
     * 
     * 
     */
    public final int doCustomSpawning(WorldServer worldObj, EnumCreatureType enumcreaturetype, int mobSpawnRange, int lightLevel, boolean checkAmbientLightLevel, boolean enforceMaxSpawnLimits)
    {
        eligibleChunksForSpawning.clear();
        int countTotal;
        int var6;

        byte spawnRadius = 8;
        for (countTotal = 0; countTotal < worldObj.playerEntities.size(); ++countTotal)
        {
            EntityPlayer entityplayer = (EntityPlayer) worldObj.playerEntities.get(countTotal);
            int var5 = MathHelper.floor_double(entityplayer.posX / 16.0D);
            var6 = MathHelper.floor_double(entityplayer.posZ / 16.0D);
           // spawnRadius = 8;
            spawnRadius = (byte) mobSpawnRange;
            // Spigot Start

            spawnRadius = (spawnRadius > FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getViewDistance() ) ? (byte) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getViewDistance() : spawnRadius;
            spawnRadius = ( spawnRadius > 8 ) ? 8 : spawnRadius;
            // Spigot End

            for (int var8 = -spawnRadius; var8 <= spawnRadius; ++var8)
            {
                for (int var9 = -spawnRadius; var9 <= spawnRadius; ++var9)
                {
                    boolean var10 = var8 == -spawnRadius || var8 == spawnRadius || var9 == -spawnRadius || var9 == spawnRadius;
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
        int limit = 0;
        //0 = monster, 1 = creature, 2 = ambient, 3 = watercreature
        switch (enumcreaturetype)
        {
            case monster:
                limit = maxMonsters;
                break;
            case creature:
                limit = maxCreatures;
                break;
            case waterCreature:
                limit = maxWaterCreatures;
                break;
            case ambient:
                limit = maxAmbients;
                break;
        }

        if (limit == 0)
        {
            return 0;
        }
        //System.out.println("CustomMobSpawner limit = " + limit);
        int mobcnt = 0;

        //modified to allow custom creature counts instead of vanillas
        //System.out.println("countEntity = " + worldObj.countEntities(enumcreaturetype, true) + ", max = " + (getMax(enumcreaturetype) * eligibleChunksForSpawning.size() / 256));
        //System.out.println("getEntityCount = " + getEntityCount(worldObj, enumcreaturetype));
        if ((mobcnt = getEntityCount(worldObj, enumcreaturetype)) <= getMax(enumcreaturetype) * eligibleChunksForSpawning.size() / 256)
        {
            Iterator iterator = eligibleChunksForSpawning.keySet().iterator();
            ArrayList<ChunkCoordIntPair> tmp = new ArrayList(eligibleChunksForSpawning.keySet());
            Collections.shuffle(tmp);
            iterator = tmp.iterator();
            int moblimit = (limit * eligibleChunksForSpawning.size() / 256) - mobcnt + 1;
            label108: while (iterator.hasNext() && moblimit > 0)
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
                                                if (spawnDist >= 576.0F)
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

                                                    Result canSpawn = ForgeEventFactory.canEntitySpawn(entityliving, worldObj, spawnX, spawnY, spawnZ);
                                                    if (isValidLightLevel(entityliving, worldObj, lightLevel, checkAmbientLightLevel) && (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere())))
                                                    {
                                                        ++spawnedMob;
                                                        worldObj.spawnEntityInWorld(entityliving);
                                                        creatureSpecificInit(entityliving, worldObj, spawnX, spawnY, spawnZ);
                                                        // changed check from maxSpawnedInChunk to maxGroupCount.
                                                       // System.out.println("spawned " + entityliving + ", spawnedMob = " + spawnedMob + ", maxGroupCount = " + spawnlistentry.maxGroupCount);
                                                        if (verboseConsole) log.info("[spawnedMob " + spawnedMob + "]:[spawned " + entityliving.getEntityName() + " at " + spawnX + ", " + spawnY + ", " + spawnZ + " with " + enumcreaturetype.name().toUpperCase() + ":" + spawnlistentry.itemWeight + ":" + spawnlistentry.minGroupCount + ":" + spawnlistentry.maxGroupCount + ":" + ForgeEventFactory.getMaxSpawnPackSize(entityliving) + " in biome " + worldObj.getBiomeGenForCoords((chunkcoordintpair.chunkXPos * 16) + 16, (chunkcoordintpair.chunkZPos * 16) + 16).biomeName + "]:[spawns left in limit " + moblimit + "]");
                                                        if (spawnedMob >= ForgeEventFactory.getMaxSpawnPackSize(entityliving))
                                                        {
                                                            continue label108;
                                                        }
                                                        if (enforceMaxSpawnLimits)
                                                        {
                                                            moblimit--;
                                                           // System.out.println("moblimit now = " + moblimit);
                                                            if (moblimit <= 0)  // If we're past limit, stop spawn
                                                            {
                                                                continue label108;
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        //if (verboseConsole) log.info("unable to spawn " + entityliving + " at coords " + var26 + ", " + var27 + ", " + var28);
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

        return countTotal;
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
        if (verboseConsole) log.info("AddCustomSpawn class " + class1 + ", with " + i + ":" + j + ":" + k + "  type = " + enumcreaturetype);
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
        //if (enumcreaturetype == null) { throw new IllegalArgumentException("spawnList cannot be null"); }
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
                int x = biomeList.indexOf(element);
                if (x >= 0) // don't iterate through list if biome wasn't found
                {
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
    }

    public void copyVanillaSpawnLists(BiomeGenBase abiomegenbase[])
    {
        if (abiomegenbase == null)
        {
            abiomegenbase = new BiomeGenBase[biomeList.size()];
            abiomegenbase = biomeList.toArray(abiomegenbase);
        }
       // System.out.println("abiomegenbase size = " + abiomegenbase.length);
        for (BiomeGenBase element : abiomegenbase)
        {
           // System.out.println("Checking biome " + element);
            for (EnumCreatureType enumType : EnumCreatureType.values())
            {
                //System.out.println("found type " + enumType);
                List[] fulllist = getCustomSpawnableList(enumType);
                //System.out.println("fulllist = " + fulllist + ", size = " + fulllist.length);
                if (fulllist != null)
                {
                    //System.out.println("checking biome name " + element.biomeName);
                    int x = biomeList.indexOf(element);
                   // System.out.println("x = " + x);
                    if (x >= 0) // don't iterate through list if biome wasn't found
                    {
                       // System.out.println("element.getSpawnableList for type " + enumType + " = " + element.getSpawnableList(enumType));
                        for (Iterator iterator = element.getSpawnableList(enumType).iterator(); iterator.hasNext();)
                        {
                            if (iterator != null)
                            {
                                SpawnListEntry spawnlistentry = (SpawnListEntry) iterator.next();
                                fulllist[x].add(spawnlistentry);
                                iterator.remove();
                            }
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

    public List[] getCustomSpawnableList(EnumCreatureType enumcreaturetype)
    {
        if (enumcreaturetype == EnumCreatureType.monster) { return customMonsterSpawnList; }
        if (enumcreaturetype == EnumCreatureType.creature) { return customCreatureSpawnList; }
        if (enumcreaturetype == EnumCreatureType.waterCreature) { return customWaterCreatureSpawnList; }
        if (enumcreaturetype == EnumCreatureType.ambient) { return customAmbientSpawnList; }
        return null;
    }

    private List getCustomBiomeSpawnList(List[] fulllist, BiomeGenBase biomegenbase)
    {
        int x = biomeList.indexOf(biomegenbase);
        if (x >= 0) { return fulllist[x]; }
        return null;
    }

    public void updateSpawnListEntry(Class<? extends EntityLiving> clazz, EnumCreatureType type, int freq, int min, int max)
    {
        List[] customList = getCustomSpawnableList(type);
        for (int i = 0; i < customList.length; i++)
        {
            List spawnList = customList[i];
            BiomeGenBase biome = biomeList.get(i);
            for (int j = 0; j < spawnList.size(); j++)
            {
                SpawnListEntry spawnlistentry = (SpawnListEntry)spawnList.get(j);
                if (spawnlistentry.entityClass == clazz)
                {
                    if (verboseConsole) log.info("updateSpawnListEntry " + clazz + " to " + freq + ":" + min + ":" + max + " in biome " + biome.biomeName);
                    spawnlistentry.itemWeight = freq;
                    spawnlistentry.minGroupCount = min;
                    spawnlistentry.maxGroupCount = max;
                }
            }
        }
    }

    public void updateSpawnListBiomes(Class<? extends EntityLiving> clazz, EnumCreatureType type, int freq, int min, int max, List<BiomeGenBase> biomes)
    {
        if (biomes != null)
        {
            if (verboseConsole) log.info("updateSpawnListBiomes for clazz " + clazz + " with " + freq + ":" + min + ":" + max + " in " + biomes);
            RemoveCustomSpawn(clazz, type, null);
            BiomeGenBase[] allBiomes = new BiomeGenBase[biomes.size()];
            AddCustomSpawn(clazz, freq, min, max, type, biomes.toArray(allBiomes));
        }
    }

    public List getCustomBiomeSpawnList(BiomeGenBase biome)
    {
        int x = biomeList.indexOf(biome);
        if (x >= 0) { return this.customCreatureSpawnList[x]; }
        return null;
    }

    public int getMax(EnumCreatureType enumcreaturetype)
    {
        if (enumcreaturetype == EnumCreatureType.monster) { return getMaxMonsters(); }
        if (enumcreaturetype == EnumCreatureType.creature) { return getMaxCreatures(); }
        if (enumcreaturetype == EnumCreatureType.waterCreature) { return getMaxWaterCreatures(); }
        if (enumcreaturetype == EnumCreatureType.ambient) { return getMaxAmbients(); }
        return 30;
    }

    public int getMaxCreatures()
    {
        return maxCreatures;
    }

    public void setMaxCreatures(int max)
    {
        maxCreatures = max;
    }

    public int getMaxMonsters()
    {
        return maxMonsters;
    }

    public void setMaxMonsters(int max)
    {
        maxMonsters = max;
    }

    public int getMaxWaterCreatures()
    {
        return maxWaterCreatures;
    }

    public void setMaxWaterCreatures(int max)
    {
        maxWaterCreatures = max;
    }

    public int getMaxAmbients()
    {
        return maxAmbients;
    }

    public void setMaxAmbients(int max)
    {
        maxAmbients = max;
    }

    public boolean isVerboseConsole() 
    {
        return verboseConsole;
    }

    public void setVerboseConsole(boolean flag) 
    {
        this.verboseConsole = flag;
    }

    /**
     * Returns whether or not the specified creature type can spawn at the specified location.
     */
    public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World, int par2, int par3, int par4)
    {
        if (par0EnumCreatureType.getCreatureMaterial() == Material.water)
        {
            return par1World.getBlockMaterial(par2, par3, par4).isLiquid() && par1World.getBlockMaterial(par2, par3 - 1, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
        }
        else if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4))
        {
            return false;
        }
        else
        {
            int l = par1World.getBlockId(par2, par3 - 1, par4);
            boolean spawnBlock = (Block.blocksList[l] != null && Block.blocksList[l].canCreatureSpawn(par0EnumCreatureType, par1World, par2, par3 - 1, par4));
            return spawnBlock && l != Block.bedrock.blockID && !par1World.isBlockNormalCube(par2, par3, par4) && !par1World.getBlockMaterial(par2, par3, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
        }
    }

    protected final int entityDespawnCheck(WorldServer worldObj, EntityLiving entityliving)
    {
        return entityDespawnCheck(worldObj, entityliving, 7);
    }

    //New DesPawner stuff
    protected final int entityDespawnCheck(WorldServer worldObj, EntityLiving entityliving, int despawnLightLevel)
    {
        if (entityliving instanceof EntityWolf && ((EntityWolf) entityliving).isTamed()) { return 0; }
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

    public final int despawnVanillaAnimals(WorldServer worldObj, int despawnLightLevel)
    {
        int count = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++)
        {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if (!(entity instanceof EntityLiving))
            {
                continue;
            }
            if ((entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityPig || entity instanceof EntityOcelot || entity instanceof EntityChicken || entity instanceof EntitySquid || entity instanceof EntityWolf || entity instanceof EntityMooshroom))
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

    public boolean isValidLightLevel(Entity entity, WorldServer worldObj, int lightLevel, boolean checkAmbientLightLevel)
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
            i = this.getBlockLightValue(worldObj.getChunkFromChunkCoords(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (i > lightLevel)
        {
            if (verboseConsole) log.info("Denied spawn! for " + entity.getEntityName() + ". LightLevel over threshold of " + lightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
        }
        return i <= lightLevel;
    }
    
    protected boolean isValidDespawnLightLevel(Entity entity, WorldServer worldObj, int despawnLightLevel)
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
            i = this.getBlockLightValue(worldObj.getChunkFromChunkCoords(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (i > despawnLightLevel)
        {
            if (verboseConsole) log.info("Denied despawn! for vanilla " + entity.getEntityName() + ". LightLevel over threshold of " + despawnLightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
        }
        /*else {
            if (verboseConsole) log.info("Valid LightLevel " + i + " found. Proceeding to despawn vanilla " + entity.getEntityName() + " at " + x + ", " + y + ", " + z);
        }*/
        return i <= despawnLightLevel;
    }

    /**
     * Gets the amount of light on a block without taking into account sunlight
     */
    public int getBlockLightValue(Chunk chunk, int x, int y, int z)
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

    /**
     * determines if a skeleton spawns on a spider, and if a sheep is a different color
     */
    private static EntityLivingData creatureSpecificInit(EntityLiving par0EntityLiving, World par1World, float par2, float par3, float par4)
    {
    	EntityLivingData entitylivingdata = null;
    	if (!ForgeEventFactory.doSpecialSpawn(par0EntityLiving, par1World, par2, par3, par4))
        {
            entitylivingdata = par0EntityLiving.func_110161_a(entitylivingdata);
        }
    	return entitylivingdata;
    }

    /**
     * Called during chunk generation to spawn initial creatures.
     */
    public static void performWorldGenSpawning(World worldObj, BiomeGenBase par1BiomeGenBase, int par2, int par3, int par4, int par5, Random par6Random, List customSpawnList, boolean worldGenCreatureSpawning)
    {
        if (!customSpawnList.isEmpty() && worldGenCreatureSpawning)
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
                            Result canSpawn = ForgeEventFactory.canEntitySpawn(entityliving, worldObj, f, f1, f2);
                            if (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere()))
                            {
                                worldObj.spawnEntityInWorld(entityliving);
                                if (verboseConsole) log.info("[WorldGen spawned " + entityliving.getEntityName() + " at " + f + ", " + f1 + ", " + f2 + " with CREATURE:" + spawnlistentry.itemWeight + ":" + spawnlistentry.minGroupCount + ":" + spawnlistentry.maxGroupCount + ":" + ForgeEventFactory.getMaxSpawnPackSize(entityliving) + " in biome " + par1BiomeGenBase.biomeName + "]");
                                creatureSpecificInit(entityliving, worldObj, f, f1, f2);
                                flag = true;
                            }
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
