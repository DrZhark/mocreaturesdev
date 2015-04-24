package drzhark.customspawner;

import drzhark.customspawner.command.CommandCMS;
import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.handlers.SpawnTickHandler;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSLog;
import drzhark.customspawner.utils.CMSUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

@Mod(modid = "CustomSpawner", name = "DrZhark's CustomSpawner", version = "3.4.0")
public final class CustomSpawner {

    @Instance("CustomSpawner")
    public static CustomSpawner INSTANCE;

    public static boolean forceDespawns = false;
    public static boolean debug = false;
    public static boolean doMobSpawning = false;
    public static File ROOT;
    public static CMSLog globalLog;

    public static Map<Class<? extends WorldProvider>, EnvironmentSettings> environmentMap =
            new HashMap<Class<? extends WorldProvider>, EnvironmentSettings>();
    public static Map<String, ArrayList<BiomeGenBase>> entityDefaultSpawnBiomes = new TreeMap<String, ArrayList<BiomeGenBase>>(
            String.CASE_INSENSITIVE_ORDER);
    public static Map<String, SpawnListEntry> defaultSpawnListEntryMap = new TreeMap<String, SpawnListEntry>(String.CASE_INSENSITIVE_ORDER);
    public static Map<String, EnumCreatureType> entityTypes = new HashMap<String, EnumCreatureType>();

    private static final String CREATURES_FILE_PATH = File.separator + "Creatures" + File.separator;
    private static final String BIOMES_FILE_PATH = File.separator + "Biomes" + File.separator;

    // default environments
    private static final String WORLD_ENVIRONMENT_OVERWORLD_ROOT = "CustomSpawner" + File.separator + "overworld";
    private static final String WORLD_ENVIRONMENT_NETHER_ROOT = "CustomSpawner" + File.separator + "nether";
    private static final String WORLD_ENVIRONMENT_END_ROOT = "CustomSpawner" + File.separator + "end";

    public static CMSConfiguration CMSGlobalConfig;
    public static final String CATEGORY_GLOBAL_SETTINGS = "global-settings";

    private static List<BiomeGenBase> biomeList;

    private HashMap eligibleChunksForSpawning = new HashMap();

    static {
        entityTypes.put("UNDEFINED", null);
        entityTypes.put("CREATURE", EnumCreatureType.CREATURE);
        entityTypes.put("MONSTER", EnumCreatureType.MONSTER);
        entityTypes.put("WATER_CREATURE", EnumCreatureType.WATER_CREATURE);
        entityTypes.put("AMBIENT", EnumCreatureType.AMBIENT);
    }

    /**
     * @return the instance
     */
    public static CustomSpawner instance() {
        return INSTANCE;
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new SpawnTickHandler());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ROOT = new File(event.getSuggestedConfigurationFile().getParent(), "CustomSpawner");
        globalLog = new CMSLog("Global");
        CMSGlobalConfig = new CMSConfiguration(new File(ROOT, "Global.cfg"));
        CMSGlobalConfig.load();
        MinecraftForge.TERRAIN_GEN_BUS.register(new EventHooks()); // register our event subscriptions
        MinecraftForge.EVENT_BUS.register(new EventHooks());
        debug = CMSGlobalConfig.get(CATEGORY_GLOBAL_SETTINGS, "debug", false, "Turns on global debug logging.").getBoolean(false);
        doMobSpawning =
                CMSGlobalConfig
                        .get(CATEGORY_GLOBAL_SETTINGS,
                                "doMobSpawning",
                                false,
                                "If false, turns off vanilla spawner completely to provide better compatibility with CMS. Note: if you remove CMS, set back to true and load up game at least once so it reenables vanilla spawner. You can also type /gamerule doMobSpawning true")
                        .getBoolean(false);
        CMSGlobalConfig.save();
        if (debug) {
            globalLog.logger.info("Initializing CustomSpawner Config File at " + event.getSuggestedConfigurationFile().getParent() + "Global.cfg");
        }
    }

    // need to generate configs here since structureGen is called before serverStarting
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        for (EnvironmentSettings environment : environmentMap.values()) {
            environment.readConfigValues();
        }
        CMSUtils.registerAllBiomes();
        biomeList = new ArrayList<BiomeGenBase>();
        try {
            for (BiomeGenBase biomegenbase : BiomeGenBase.getBiomeGenArray()) {
                if (biomegenbase == null) {
                    continue;
                }

                biomeList.add(biomegenbase);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        // We need to run through overworld init a second time since structure gen occurs before all spawns are ready
        EnvironmentSettings overworld = CustomSpawner.environmentMap.get(WorldProviderSurface.class);
        if (overworld != null) {
            //overworld.initializeBiomes();
            overworld.initializeEntities();
            overworld.updateSettings(); // refresh settings
        }
        event.registerServerCommand(new CommandCMS());
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        CMSUtils.dumpEntitySpawnLists();
        CMSUtils.dumpDefaultSpawnList();
    }

    protected static BlockPos getRandomSpawningPointInChunk(World worldObj, int par1, int par2) {
        Chunk chunk = worldObj.getChunkFromChunkCoords(par1, par2);
        int i = par1 * 16 + worldObj.rand.nextInt(16);
        int j = worldObj.rand.nextInt(chunk == null ? worldObj.getActualHeight() : chunk.getTopFilledSegment() + 16 - 1);
        int k = par2 * 16 + worldObj.rand.nextInt(16);
        return new BlockPos(i, j, k);
    }

    /**
     * Returns a count of entities that classify themselves as the specified
     * creature type.
     */
    public int countEntities(WorldServer world, EntitySpawnType entitySpawnType) {
        int count = 0;
        for (int x = 0; x < world.loadedEntityList.size(); x++) {
            EntityData entityData = CMSUtils.getEnvironment(world).classToEntityMapping.get(((Entity) world.loadedEntityList.get(x)).getClass());
            if (entityData != null && entityData.getLivingSpawnType() == entitySpawnType) {
                count++;
            }
        }
        return count;
    }

    /**
     * New customSpawner
     *
     *
     */
    public final int doCustomSpawning(WorldServer world, EntitySpawnType entitySpawnType, int mobSpawnRange, boolean enforceMaxSpawnLimits) {
        this.eligibleChunksForSpawning.clear();
        int countTotal;
        int var6;

        byte spawnRadius = 8;
        for (countTotal = 0; countTotal < world.playerEntities.size(); ++countTotal) {
            EntityPlayer entityplayer = (EntityPlayer) world.playerEntities.get(countTotal);
            int var5 = MathHelper.floor_double(entityplayer.posX / 16.0D);
            var6 = MathHelper.floor_double(entityplayer.posZ / 16.0D);
            // spawnRadius = 8;
            spawnRadius = (byte) mobSpawnRange;
            // Spigot Start

            spawnRadius =
                    (spawnRadius > FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getViewDistance())
                            ? (byte) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getViewDistance()
                            : spawnRadius;
            spawnRadius = (spawnRadius > 8) ? 8 : spawnRadius;
            // Spigot End

            for (int var8 = -spawnRadius; var8 <= spawnRadius; ++var8) {
                for (int var9 = -spawnRadius; var9 <= spawnRadius; ++var9) {
                    boolean var10 = var8 == -spawnRadius || var8 == spawnRadius || var9 == -spawnRadius || var9 == spawnRadius;
                    ChunkCoordIntPair var11 = new ChunkCoordIntPair(var8 + var5, var9 + var6);

                    if (!var10) {
                        this.eligibleChunksForSpawning.put(var11, Boolean.valueOf(false));
                    } else if (!this.eligibleChunksForSpawning.containsKey(var11)) {
                        this.eligibleChunksForSpawning.put(var11, Boolean.valueOf(true));
                    }
                }
            }
        }

        countTotal = 0;
        BlockPos chunkcoordspawn = world.getSpawnPoint();
        EnumCreatureType[] enumcreaturevalues = EnumCreatureType.values();
        var6 = enumcreaturevalues.length;
        int limit = 0;

        limit = entitySpawnType.getSpawnCap();

        if (limit == 0) {
            return 0;
        }

        int mobcnt = 0;
        //System.out.println("1max = " + (getMax(entitySpawnType) * eligibleChunksForSpawning.size() / 256) + ", count = " + countEntities(world, entitySpawnType));
        int spawnsLeft = (getMax(entitySpawnType) * this.eligibleChunksForSpawning.size() / 256) - (countEntities(world, entitySpawnType));
        int maxcnt = (getMax(entitySpawnType) * this.eligibleChunksForSpawning.size() / 256);
        mobcnt = countEntities(world, entitySpawnType);
        if (mobcnt <= maxcnt) {
            Iterator iterator = this.eligibleChunksForSpawning.keySet().iterator();
            ArrayList<ChunkCoordIntPair> tmp = new ArrayList(this.eligibleChunksForSpawning.keySet());
            Collections.shuffle(tmp);
            iterator = tmp.iterator();
            int moblimit = (limit * this.eligibleChunksForSpawning.size() / 256) - mobcnt + 1;

            label108: while (iterator.hasNext() && moblimit > 0) {
                ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) iterator.next();
                if (this.eligibleChunksForSpawning.get(chunkcoordintpair) != null
                        && !((Boolean) this.eligibleChunksForSpawning.get(chunkcoordintpair)).booleanValue()) // blood - added null check to avoid crashing during SSP spawning
                {
                    BlockPos chunkpos = getRandomSpawningPointInChunk(world, chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
                    int posX = chunkpos.getX();
                    int posY = chunkpos.getY();
                    int posZ = chunkpos.getZ();

                    BlockPos pos = new BlockPos(posX, posY, posZ);
                    if (!world.getBlockState(pos).getBlock().isNormalCube()
                            && world.getBlockState(pos).getBlock().getMaterial() == entitySpawnType.getLivingMaterial()) {
                        int spawnedMob = 0;
                        int spawnCount = 0;

                        while (spawnCount < 3) {
                            int tempX = posX;
                            int tempY = posY;
                            int tempZ = posZ;
                            byte var20 = 6;
                            SpawnListEntry spawnlistentry = null;
                            int spawnAttempt = 0;

                            while (true) {
                                if (spawnAttempt < 4)

                                {
                                    label101: {
                                        tempX += world.rand.nextInt(var20) - world.rand.nextInt(var20);
                                        tempY += world.rand.nextInt(1) - world.rand.nextInt(1);
                                        tempZ += world.rand.nextInt(var20) - world.rand.nextInt(var20);

                                        if (canCreatureTypeSpawnAtLocation(entitySpawnType, world, new BlockPos(tempX, tempY, tempZ))) {

                                            float spawnX = tempX + 0.5F;
                                            float spawnY = tempY;
                                            float spawnZ = tempZ + 0.5F;
                                            if (world.getClosestPlayer(spawnX, spawnY, spawnZ, 24.0D) == null) {
                                                float var26 = spawnX - chunkcoordspawn.getX();
                                                float var27 = spawnY - chunkcoordspawn.getY();
                                                float var28 = spawnZ - chunkcoordspawn.getZ();
                                                float spawnDist = var26 * var26 + var27 * var27 + var28 * var28;
                                                if (spawnDist >= 576.0F) {
                                                    if (spawnlistentry == null) {
                                                        //this is where it has to be changed to include the custom list
                                                        //spawnlistentry = worldObj.getRandomMob(enumcreaturetype, tempX, tempY, tempZ);
                                                        spawnlistentry = getRandomCustomMob(world, entitySpawnType, tempX, tempY, tempZ);
                                                        //System.out.println("spawnlistentry = " + spawnlistentry + " for type " + entitySpawnType.name());
                                                        if (spawnlistentry == null) {
                                                            break label101;
                                                        }
                                                    }

                                                    EntityLiving entityliving;
                                                    EntityData entityData;

                                                    try {
                                                        entityliving =
                                                                (EntityLiving) spawnlistentry.entityClass.getConstructor(new Class[] {World.class})
                                                                        .newInstance(new Object[] {world});
                                                    } catch (Exception exception) {
                                                        exception.printStackTrace();
                                                        return countTotal;
                                                    }
                                                    entityData = CMSUtils.getEnvironment(world).classToEntityMapping.get(spawnlistentry.entityClass);
                                                    // spawn checks
                                                    entityliving.setLocationAndAngles(spawnX, spawnY, spawnZ, world.rand.nextFloat() * 360.0F, 0.0F);
                                                    Result canSpawn = ForgeEventFactory.canEntitySpawn(entityliving, world, spawnX, spawnY, spawnZ);

                                                    if (isValidLightLevel(entityliving, world, entityData.getMinLightLevel(),
                                                            entityData.getMaxLightLevel())
                                                            && (canSpawn == Result.ALLOW
                                                                    || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere()) || (entityData
                                                                    .getLivingSpawnType() == entityData.getEnvironment().LIVINGTYPE_UNDERGROUND && CMSUtils
                                                                    .isValidUndergroundLightLevel(entityliving)))) {
                                                        ++spawnedMob;
                                                        world.spawnEntityInWorld(entityliving);
                                                        creatureSpecificInit(entityliving, world, new BlockPos(spawnX, spawnY, spawnZ));
                                                        // changed check from maxSpawnedInChunk to maxGroupCount.
                                                        CMSUtils.getEnvironment(world).envLog.logSpawn(CMSUtils.getEnvironment(world),
                                                                entitySpawnType.name(), world.getBiomeGenForCoords(new BlockPos(
                                                                        (chunkcoordintpair.chunkXPos * 16) + 16, 0,
                                                                        (chunkcoordintpair.chunkZPos * 16) + 16)).biomeName, entityData
                                                                        .getEntityName(), MathHelper.floor_double(spawnX), MathHelper
                                                                        .floor_double(spawnY), MathHelper.floor_double(spawnZ), spawnsLeft,
                                                                spawnlistentry);

                                                        if (spawnedMob >= ForgeEventFactory.getMaxSpawnPackSize(entityliving)) {
                                                            continue label108;
                                                        }
                                                        if (enforceMaxSpawnLimits) {
                                                            moblimit--;
                                                            if (moblimit <= 0) // If we're past limit, stop spawn
                                                            {
                                                                continue label108;
                                                            }
                                                        }
                                                    } else {
                                                        //if (verboseConsole) globalLog.logger.info("unable to spawn " + entityliving + " at coords " + var26 + ", " + var27 + ", " + var28);
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
        } else {
            if (CMSUtils.getEnvironment(world).debug) {
                CMSUtils.getEnvironment(world).envLog.logger.info("[" + entitySpawnType.name().toUpperCase() + "]Unable to spawn, Total count "
                        + mobcnt + " exceeds limit of " + maxcnt);
            }
        }

        return countTotal;
    }

    public static boolean isValidLightLevel(Entity entity, WorldServer worldObj, int minLightLevel, int maxLightLevel) {
        if (minLightLevel == -1 && maxLightLevel == -1) {
            return true;
        }
        if (entity.isCreatureType(EnumCreatureType.MONSTER, false)) {
            return true;
        } else if (entity.isCreatureType(EnumCreatureType.AMBIENT, false)) {
            return true;
        } else if (!entity.isCreatureType(EnumCreatureType.CREATURE, false)) {
            return true;
        }
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.getEntityBoundingBox().minY);
        int z = MathHelper.floor_double(entity.posZ);
        int blockLightLevel = 0;
        if (y >= 0) {
            if (y >= 256) {
                y = 255;
            }
            blockLightLevel = CMSUtils.getLightFromNeighbors(worldObj.getChunkFromChunkCoords(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (blockLightLevel < minLightLevel && minLightLevel != -1) {
            if (debug) {
                CMSUtils.getEnvironment(worldObj).envLog.logger.info("Denied spawn! for " + entity.getCommandSenderName() + blockLightLevel
                        + " under minimum threshold of " + minLightLevel + " in dimension " + worldObj.provider.getDimensionId() + " at coords " + x
                        + ", " + y + ", " + z);
            }
            return false;
        } else if (blockLightLevel > maxLightLevel && maxLightLevel != -1) {
            if (debug) {
                CMSUtils.getEnvironment(worldObj).envLog.logger.info("Denied spawn! for " + entity.getCommandSenderName() + blockLightLevel
                        + " over maximum threshold of " + maxLightLevel + " in dimension " + worldObj.provider.getDimensionId() + " at coords " + x
                        + ", " + y + ", " + z);
            }
            return false;
        }
        return true;
    }

    public void AddCustomSpawn(Class class1, int i, int j, int k, EntitySpawnType entitySpawnType, BiomeGenBase biomes[]) {
        if (debug) {
            globalLog.logger.info("AddCustomSpawn class " + class1 + ", with " + i + ":" + j + ":" + k + "  type = " + entitySpawnType);
        }
        if (class1 == null) {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        if (entitySpawnType == null) {
            throw new IllegalArgumentException("spawnList cannot be null");
        }
        if (biomes == null) {
            biomes = new BiomeGenBase[biomeList.size()];
            biomes = biomeList.toArray(biomes);
        }

        for (BiomeGenBase biome : biomes) {
            if (biome == null) {
                continue;
            }

            if (entitySpawnType.getEnvironment().getWorldProviderClass() == WorldProviderEnd.class && !BiomeDictionary.isBiomeOfType(biome, Type.END)) {
                continue;
            } else if (entitySpawnType.getEnvironment().getWorldProviderClass() == WorldProviderHell.class
                    && !BiomeDictionary.isBiomeOfType(biome, Type.NETHER)) {
                continue;
            }

            ArrayList<SpawnListEntry> spawnList = entitySpawnType.getBiomeSpawnList(biome.biomeID);
            if (spawnList != null) {
                boolean flag = false;
                // check to see if spawn entry already exists
                for (SpawnListEntry spawnEntry : spawnList) {
                    if (spawnEntry.entityClass == class1) {
                        spawnEntry.itemWeight = i;
                        spawnEntry.minGroupCount = j;
                        spawnEntry.maxGroupCount = k;
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    spawnList.add(new SpawnListEntry(class1, i, j, k));
                }
            } else {
                entitySpawnType.addBiomeSpawnList(biome.biomeID);
                entitySpawnType.getBiomeSpawnList(biome.biomeID).add(new SpawnListEntry(class1, i, j, k));
            }
        }

    }

    public void RemoveCustomSpawn(Class class1, EntitySpawnType entitySpawnType, BiomeGenBase biomes[]) {
        if (class1 == null) {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        //if (enumcreaturetype == null) { throw new IllegalArgumentException("spawnList cannot be null"); }
        if (biomes == null) {
            biomes = new BiomeGenBase[biomeList.size()];
            biomes = biomeList.toArray(biomes);
        }

        for (BiomeGenBase biome : biomes) {
            ArrayList<SpawnListEntry> spawnList = entitySpawnType.getBiomeSpawnList(biome.biomeID);

            if (spawnList != null) {
                Iterator iterator = spawnList.iterator();
                while (iterator.hasNext()) {
                    SpawnListEntry spawnEntry = (SpawnListEntry) iterator.next();
                    if (spawnEntry.entityClass == class1) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }

    public static void copyVanillaSpawnData(BiomeGenBase abiomegenbase[]) {
        if (abiomegenbase == null) {
            abiomegenbase = new BiomeGenBase[biomeList.size()];
            abiomegenbase = biomeList.toArray(abiomegenbase);
        }

        for (BiomeGenBase biome : abiomegenbase) {
            for (EnumCreatureType enumcreaturetype : entityTypes.values()) {
                if (enumcreaturetype == null) {
                    continue;
                }

                for (Iterator iterator = biome.getSpawnableList(enumcreaturetype).iterator(); iterator.hasNext();) {
                    if (iterator != null) {
                        SpawnListEntry spawnlistentry = (SpawnListEntry) iterator.next();
                        if (entityDefaultSpawnBiomes.containsKey(spawnlistentry.entityClass.getName())) // add biome to existing list
                        {
                            if (!entityDefaultSpawnBiomes.get(spawnlistentry.entityClass.getName()).contains(biome)) {
                                entityDefaultSpawnBiomes.get(spawnlistentry.entityClass.getName()).add(biome);
                            }
                        } else // create new biome list for entity
                        {
                            ArrayList<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
                            biomes.add(biome);
                            entityDefaultSpawnBiomes.put(spawnlistentry.entityClass.getName(), biomes);
                            if (!defaultSpawnListEntryMap.containsKey(spawnlistentry.entityClass.getName())) {
                                defaultSpawnListEntryMap.put(spawnlistentry.entityClass.getName(), spawnlistentry);
                            }
                        }
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void updateSpawnListEntry(EntityData entityData) {
        for (int i = 0; i < biomeList.size(); i++) {
            ArrayList<SpawnListEntry> spawnList = entityData.getLivingSpawnType().getBiomeSpawnList(biomeList.get(i).biomeID);
            if (spawnList != null) {
                for (SpawnListEntry spawnEntry : spawnList) {
                    if (spawnEntry.entityClass == entityData.getEntityClass()) {
                        if (debug) {
                            globalLog.logger.info("updateSpawnListEntry " + entityData.getEntityClass() + " to " + entityData.getFrequency() + ":"
                                    + entityData.getMinSpawn() + ":" + entityData.getMaxSpawn() + " in biome " + biomeList.get(i).biomeName);
                        }
                        spawnEntry.itemWeight = entityData.getFrequency();
                        spawnEntry.minGroupCount = entityData.getMinSpawn();
                        spawnEntry.maxGroupCount = entityData.getMaxSpawn();
                    }
                }
            }
        }
    }

    public void updateSpawnListBiomes(Class<? extends EntityLiving> clazz, EntitySpawnType entitySpawnType, int freq, int min, int max,
            List<BiomeGenBase> biomes) {
        if (biomes != null) {
            if (debug) {
                globalLog.logger.info("updateSpawnListBiomes for clazz " + clazz + " with " + freq + ":" + min + ":" + max + " in " + biomes);
            }
            RemoveCustomSpawn(clazz, entitySpawnType, null);
            BiomeGenBase[] allBiomes = new BiomeGenBase[biomes.size()];
            AddCustomSpawn(clazz, freq, min, max, entitySpawnType, biomes.toArray(allBiomes));
        }
    }

    public int getMax(EntitySpawnType entitySpawnType) {
        return entitySpawnType.getSpawnCap();
    }

    /**
     * Returns whether or not the specified creature type can spawn at the
     * specified location.
     */
    public static boolean canCreatureTypeSpawnAtLocation(EntitySpawnType entitySpawnType, World par1World, BlockPos pos) {
        if (entitySpawnType.getMinSpawnHeight() != -1) {
            if (pos.getY() < entitySpawnType.getMinSpawnHeight()) {
                return false;
            }
        }
        if (entitySpawnType.getMaxSpawnHeight() != -1) {
            if (pos.getY() > entitySpawnType.getMaxSpawnHeight()) {
                return false;
            }
        }

        if (entitySpawnType.getLivingMaterial() == Material.water) {
            return par1World.getBlockState(pos).getBlock().getMaterial().isLiquid()
                    && par1World.getBlockState(pos.down()).getBlock().getMaterial().isLiquid()
                    && !par1World.getBlockState(pos.up()).getBlock().isNormalCube();
        } else if (!World.doesBlockHaveSolidTopSurface(par1World, pos.down())) {
            return false;
        } else {
            Block block = par1World.getBlockState(pos.down()).getBlock();
            boolean spawnBlock;
            if (entitySpawnType.getEnumCreatureType() != null) {
                spawnBlock = block != null && block.canCreatureSpawn(par1World, pos.down(), EntityLiving.SpawnPlacementType.ON_GROUND);
            } else {
                spawnBlock = (block != null && canCreatureSpawn(block, par1World, pos.down()));
            }
            return spawnBlock && block != Blocks.bedrock && !par1World.getBlockState(pos).getBlock().isNormalCube()
                    && !par1World.getBlockState(pos).getBlock().getMaterial().isLiquid()
                    && !par1World.getBlockState(pos.up()).getBlock().isNormalCube();
        }
    }

    public static boolean canCreatureSpawn(Block block, World world, BlockPos pos) {
        IBlockState blockstate = world.getBlockState(pos);
        int meta = blockstate.getBlock().getMetaFromState(blockstate);
        if (block instanceof BlockStoneSlab) {
            return (((meta & 8) == 8) || block.isOpaqueCube());
        } else if (block instanceof BlockStairs) {
            return ((meta & 4) != 0);
        }
        return block.isSideSolid(world, pos, EnumFacing.UP);
    }

    public final int countEntities(Class class1, World worldObj) {
        int i = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++) {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if (class1.isAssignableFrom(entity.getClass())) {
                i++;
            }
        }

        return i;
    }

    /**
     * Gets a random custom mob for spawning based on XYZ coordinates
     */
    public SpawnListEntry getRandomCustomMob(World worldObj, EntitySpawnType entitySpawnType, int pX, int pY, int pZ) {
        List list = getPossibleCustomCreatures(worldObj, entitySpawnType, pX, pY, pZ);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return (SpawnListEntry) WeightedRandom.getRandomItem(worldObj.rand, list);
        }
    }

    /**
     * Returns a list of creatures of the specified type (mob/aquatic/animal)
     * that can spawn at the given XYZ location, based on biomes.
     */
    public List getPossibleCustomCreatures(World worldObj, EntitySpawnType entitySpawnType, int pX, int pY, int pZ) {
        BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(new BlockPos(pX, 0, pZ));
        if (biomegenbase == null) {
            return null;
        } else {
            return entitySpawnType.getBiomeSpawnList(biomegenbase.biomeID);
        }
    }

    /**
     * determines if a skeleton spawns on a spider, and if a sheep is a
     * different color
     */
    private static IEntityLivingData creatureSpecificInit(EntityLiving par0EntityLiving, World par1World, BlockPos pos) {
        IEntityLivingData entitylivingdata = null;
        if (!ForgeEventFactory.doSpecialSpawn(par0EntityLiving, par1World, pos.getX(), pos.getY(), pos.getZ())) {
            entitylivingdata =
                    par0EntityLiving.onSpawnFirstTime(par1World.getDifficultyForLocation(new BlockPos(par0EntityLiving)), entitylivingdata);
        }
        return entitylivingdata;
    }

    /**
     * Called during chunk generation to spawn initial creatures.
     */
    public static void performWorldGenSpawning(EntitySpawnType entitySpawnType, World world, BiomeGenBase par1BiomeGenBase, int par2, int par3,
            int par4, int par5, Random par6Random, List customSpawnList, boolean worldGenCreatureSpawning) {
        if (!customSpawnList.isEmpty() && worldGenCreatureSpawning) {
            while (par6Random.nextFloat() < entitySpawnType.getChunkSpawnChance()) {
                //this is where it has to be changed to include the custom list
                //spawnlistentry = worldObj.getRandomMob(enumcreaturetype, tempX, tempY, tempZ);
                SpawnListEntry spawnlistentry = null;
                try {
                    spawnlistentry = (SpawnListEntry) WeightedRandom.getRandomItem(world.rand, customSpawnList);
                } catch (IllegalArgumentException e) {
                    // If we world.rand returns an invalid value, just continue to avoid crash
                    continue;
                }
                if (spawnlistentry != null) {
                    int i1 = spawnlistentry.minGroupCount + par6Random.nextInt(1 + spawnlistentry.maxGroupCount - spawnlistentry.minGroupCount);
                    int j1 = par2 + par6Random.nextInt(par4);
                    int k1 = par3 + par6Random.nextInt(par5);
                    int l1 = j1;
                    int i2 = k1;
                    EntityData entityData = CMSUtils.getEnvironment(world).classToEntityMapping.get(spawnlistentry.entityClass);
                    for (int j2 = 0; j2 < i1; ++j2) {
                        boolean flag = false;

                        for (int k2 = 0; !flag && k2 < 4; ++k2) {
                            BlockPos blockpos = world.getTopSolidOrLiquidBlock(new BlockPos(j1, 0, k1));

                            if (canCreatureTypeSpawnAtLocation(entityData.getLivingSpawnType(), world, blockpos)) {

                                EntityLiving entityliving;

                                try {
                                    entityliving =
                                            (EntityLiving) spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(
                                                    new Object[] {world});
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                    continue;
                                }

                                BlockPos pos = new BlockPos(j1 + 0.5F, blockpos.getY(), k1 + 0.5F);
                                entityliving.setLocationAndAngles(j1 + 0.5F, blockpos.getY(), k1 + 0.5F, par6Random.nextFloat() * 360.0F, 0.0F);
                                Result canSpawn =
                                        ForgeEventFactory.canEntitySpawn(entityliving, world, entityliving.getPosition().getX(), entityliving
                                                .getPosition().getY(), entityliving.getPosition().getZ());
                                if (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere())) {
                                    world.spawnEntityInWorld(entityliving);
                                    if (CMSUtils.getEnvironment(world).debug) {
                                        CMSUtils.getEnvironment(world).envLog.logger.info("[WorldGen spawned " + entityliving.getCommandSenderName()
                                                + " at " + entityliving.getPosition() + " with CREATURE:" + spawnlistentry.itemWeight + ":"
                                                + spawnlistentry.minGroupCount + ":" + spawnlistentry.maxGroupCount + ":"
                                                + ForgeEventFactory.getMaxSpawnPackSize(entityliving) + " in biome " + par1BiomeGenBase.biomeName
                                                + "]");
                                    }
                                    creatureSpecificInit(entityliving, world, pos);
                                    flag = true;
                                }
                            }

                            j1 += par6Random.nextInt(5) - par6Random.nextInt(5);

                            for (k1 += par6Random.nextInt(5) - par6Random.nextInt(5); j1 < par2 || j1 >= par2 + par4 || k1 < par3
                                    || k1 >= par3 + par4; k1 = i2 + par6Random.nextInt(5) - par6Random.nextInt(5)) {
                                j1 = l1 + par6Random.nextInt(5) - par6Random.nextInt(5);
                            }
                        }
                    }
                }
            }
        }
    }
}
