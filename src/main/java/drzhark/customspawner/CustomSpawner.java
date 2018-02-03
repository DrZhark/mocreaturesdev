package drzhark.customspawner;

import drzhark.customspawner.command.CommandCMS;
import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.handlers.SpawnTickHandler;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSLog;
import drzhark.customspawner.utils.CMSUtils;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
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

@Mod(modid = CMSConstants.MOD_ID, name = CMSConstants.MOD_NAME, version = CMSConstants.MOD_VERSION)
public final class CustomSpawner {

    @Instance(CMSConstants.MOD_ID)
    public static CustomSpawner INSTANCE;

    public static boolean forceDespawns = false;
    public static boolean debug = false;
    public static boolean doMobSpawning = false;
    public static File ROOT;
    public static CMSLog globalLog;

    public static Map<Class<? extends WorldProvider>, EnvironmentSettings> environmentMap =
            new HashMap<Class<? extends WorldProvider>, EnvironmentSettings>();
    public static Map<String, ArrayList<Biome>> entityDefaultSpawnBiomes = new TreeMap<String, ArrayList<Biome>>(
            String.CASE_INSENSITIVE_ORDER);
    public static Map<String, SpawnListEntry> defaultSpawnListEntryMap = new TreeMap<String, SpawnListEntry>(String.CASE_INSENSITIVE_ORDER);
    public static Map<String, EnumCreatureType> entityTypes = new HashMap<String, EnumCreatureType>();

    public static CMSConfiguration CMSGlobalConfig;
    public static final String CATEGORY_GLOBAL_SETTINGS = "global-settings";

    private static List<Biome> biomeList;

    private final LongOpenHashSet eligibleChunksForSpawning = new LongOpenHashSet();

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
        MinecraftForge.EVENT_BUS.register(new SpawnTickHandler());
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

        biomeList = new ArrayList<Biome>();
        try {
            Iterator<Biome> iterator = Biome.REGISTRY.iterator();
            while (iterator.hasNext()) {
                Biome biome = iterator.next();
                if (biome == null) {
                    continue;
                }

                biomeList.add(biome);
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

    protected static BlockPos getRandomSpawningPointInChunk(Chunk chunk, World world) {
        int x = chunk.x * 16 + world.rand.nextInt(16);
        int z = chunk.z * 16 + world.rand.nextInt(16);
        int height = MathHelper.roundUp(chunk.getHeight(new BlockPos(x, 0, z)) + 1, 16);
        int y = world.rand.nextInt(height > 0 ? height : chunk.getTopFilledSegment() + 16 - 1);
        return new BlockPos(x, y, z);
    }

    /**
     * Returns a count of entities that classify themselves as the specified
     * creature type.
     */
     public int countEntities(WorldServer world, EntitySpawnType entitySpawnType) {
         int count = 0;
         final EnvironmentSettings environment = CMSUtils.getEnvironment(world);
         for (int i = 0; i < world.loadedEntityList.size(); i++) {
             final Entity entity = ((Entity) world.loadedEntityList.get(i));
             if (!(entity instanceof EntityLivingBase)) {
                 continue;
             }
             final EntityData entityData = environment.classToEntityMapping.get(entity.getClass());
             final int x = MathHelper.floor(entity.posX);
             final int z = MathHelper.floor(entity.posZ);
             final long chunkPos = CMSUtils.asLong(x >> 4, z >> 4);
             if (this.eligibleChunksForSpawning.contains(chunkPos)) {
                 if (entityData != null && entityData.getLivingSpawnType() == entitySpawnType) {
                     count++;
                 }
             }
         }

         return count;
    }

    public final int doCustomSpawning(WorldServer world, EntitySpawnType entitySpawnType, int mobSpawnRange) {
        this.eligibleChunksForSpawning.clear();
        Iterator<EntityPlayer> iterator = world.playerEntities.iterator();
        byte chunkSpawnRadius = (byte) mobSpawnRange;
        byte serverViewDistance = (byte) FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getViewDistance();
        chunkSpawnRadius = chunkSpawnRadius > serverViewDistance ? serverViewDistance : chunkSpawnRadius;
        chunkSpawnRadius = chunkSpawnRadius > 8 ? 8 : chunkSpawnRadius;
        // Vanilla uses a div count of 289 (17x17) which assumes the view distance is 8.
        // Since we allow for custom ranges, we need to adjust the div count based on the 
        // mob spawn range set by server.
        final int MOB_SPAWN_COUNT_DIV = (2 * chunkSpawnRadius + 1)*(2 * chunkSpawnRadius + 1);

        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();

            if (!entityplayer.isSpectator()) {
                int x = MathHelper.floor(entityplayer.posX / 16.0D);
                int z = MathHelper.floor(entityplayer.posZ / 16.0D);

                for (int var8 = -chunkSpawnRadius; var8 <= chunkSpawnRadius; ++var8) {
                    for (int var9 = -chunkSpawnRadius; var9 <= chunkSpawnRadius; ++var9) {
                        boolean flag = var8 == -chunkSpawnRadius || var8 == chunkSpawnRadius || var9 == -chunkSpawnRadius || var9 == chunkSpawnRadius;
                        final long chunkPos = CMSUtils.asLong(var8 + x, var9 + z);
                        if (!this.eligibleChunksForSpawning.contains(chunkPos)) {
                            if (!flag && isInBorderBounds(world.getWorldBorder(), var8 + x, var9 + z)) {
                                this.eligibleChunksForSpawning.add(chunkPos);
                            }
                        }
                    }
                }
            }
        }

        int countTotal = 0;
        BlockPos chunkcoordspawn = world.getSpawnPoint();
        int limit = 0;

        limit = entitySpawnType.getSpawnCap();
        if (limit == 0) {
            return 0;
        }

        int mobcnt = countEntities(world, entitySpawnType);
        int maxcnt = getMax(entitySpawnType) * this.eligibleChunksForSpawning.size() / MOB_SPAWN_COUNT_DIV;
        int spawnsLeft = maxcnt - mobcnt;
        if (mobcnt > maxcnt) {
            if (CMSUtils.getEnvironment(world).debug) {
                CMSUtils.getEnvironment(world).envLog.logger.info("[" + entitySpawnType.name().toUpperCase() + "]Unable to spawn, Total count "
                        + mobcnt + " exceeds limit of " + maxcnt);
            }
            return countTotal;
        }

        Iterator<Long> iterator1 = this.eligibleChunksForSpawning.iterator();
        ArrayList<Long> tmp = com.google.common.collect.Lists.newArrayList(this.eligibleChunksForSpawning);
        Collections.shuffle(tmp);
        iterator1 = tmp.iterator();
        int moblimit = maxcnt - mobcnt + 1;

        label108: while (iterator1.hasNext() && moblimit > 0) {
            final long chunkPos = iterator1.next();
            final int chunkX = CMSUtils.getChunkX(chunkPos);
            final int chunkZ = CMSUtils.getChunkZ(chunkPos);
            final Chunk chunk = getLoadedChunkWithoutMarkingActive(world.getChunkProvider(), chunkX, chunkZ);
            if (chunk == null || chunk.unloadQueued) {
                continue;
            }
            BlockPos blockPos = getRandomSpawningPointInChunk(chunk, world);
            int posX = blockPos.getX();
            int posY = blockPos.getY();
            int posZ = blockPos.getZ();

            if (!chunk.getBlockState(blockPos).isNormalCube()
                    && chunk.getBlockState(blockPos).getMaterial() == entitySpawnType.getLivingMaterial()) {
                int spawnedMob = 0;
                int spawnCount = 0;

                while (spawnCount < 3) {
                    int tempX = posX;
                    int tempY = posY;
                    int tempZ = posZ;
                    byte var20 = 6;
                    SpawnListEntry spawnlistentry = null;
                    IEntityLivingData entityLivingData = null;
                    int spawnAttempt = 0;

                    while (true) {
                        if (spawnAttempt < 4) {
                            label101: {
                                tempX += world.rand.nextInt(var20) - world.rand.nextInt(var20);
                                tempY += world.rand.nextInt(1) - world.rand.nextInt(1);
                                tempZ += world.rand.nextInt(var20) - world.rand.nextInt(var20);
                                float spawnX = tempX + 0.5F;
                                float spawnY = tempY;
                                float spawnZ = tempZ + 0.5F;
                                BlockPos spawnPos = new BlockPos(tempX, tempY, tempZ);
                                final Chunk spawnChunk = getLoadedChunkWithoutMarkingActive(world.getChunkProvider(), spawnPos.getX() >> 4, spawnPos.getZ() >> 4);
                                if (spawnChunk == null || spawnChunk.unloadQueued) {
                                    ++spawnAttempt;
                                    continue;
                                }

                                if (!world.isAnyPlayerWithinRangeAt(spawnX, spawnY, spawnZ, 24.0D) && chunkcoordspawn.distanceSq(spawnX, spawnY, spawnZ) >= 576.0D) {
                                    if (spawnlistentry == null) {
                                        //this is where it has to be changed to include the custom list
                                        spawnlistentry = getRandomCustomMob(world, entitySpawnType, tempX, tempY, tempZ);
                                        if (spawnlistentry == null) {
                                            break label101;
                                        }
                                    }

                                    if (canCreatureTypeSpawnAtLocation(spawnChunk, entitySpawnType, spawnlistentry, world, spawnPos)) {

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
                                        Result canSpawn = ForgeEventFactory.canEntitySpawn(entityliving, world, spawnX, spawnY, spawnZ, false);
                                        boolean validLightLevel = isValidLightLevel(entityliving, world, entityData.getMinLightLevel(), entityData.getMaxLightLevel());
                                        boolean canEntitySpawn = canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere());
                                        boolean underGroundSpawn = (entityData.getLivingSpawnType() == entityData.getEnvironment().LIVINGTYPE_UNDERGROUND && CMSUtils
                                                .isValidUndergroundLightLevel(entityliving));

                                        if (validLightLevel && (canEntitySpawn || underGroundSpawn)) {
                                            ++spawnedMob;
                                            world.spawnEntity(entityliving);
                                            entityLivingData = creatureSpecificInit(entityliving, world, new BlockPos(spawnX, spawnY, spawnZ), entityLivingData);
                                            // changed check from maxSpawnedInChunk to maxGroupCount.
                                            if (CustomSpawner.debug) {
                                                CMSUtils.getEnvironment(world).envLog.logSpawn(CMSUtils.getEnvironment(world),
                                                        entitySpawnType.name(), world.getBiome(new BlockPos(
                                                                (chunkX * 16) + 16, 0,
                                                                (chunkZ * 16) + 16)).biomeName, entityData
                                                                .getEntityName(), MathHelper.floor(spawnX), MathHelper
                                                                .floor(spawnY), MathHelper.floor(spawnZ), spawnsLeft,
                                                        spawnlistentry);
                                            }

                                            if (spawnedMob >= ForgeEventFactory.getMaxSpawnPackSize(entityliving)) {
                                                continue label108;
                                            }

                                            moblimit--;
                                            if (moblimit <= 0) { // If we're past limit, stop spawn
                                                continue label108;
                                            }
                                        } else {
                                            if (CMSUtils.getEnvironment(world).debug) {
                                                CMSUtils.getEnvironment(world).envLog.logger.info("[Unable to spawn " + entityliving + " at coords " + spawnX + ", " + spawnY + ", " + spawnZ + "][ValidLightLevel ? " + validLightLevel + "][CanSpawn ? " + canEntitySpawn + "]");
                                            }
                                        }
                                        countTotal += spawnedMob;
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

        return countTotal;
    }

    public static boolean isValidLightLevel(Entity entity, WorldServer world, int minLightLevel, int maxLightLevel) {
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
        if (blockLightLevel < minLightLevel && minLightLevel != -1) {
            if (debug) {
                CMSUtils.getEnvironment(world).envLog.logger.info("Denied spawn! for " + entity.getName() + blockLightLevel
                        + " under minimum threshold of " + minLightLevel + " in dimension " + world.provider.getDimensionType().getId() + " at coords " + x
                        + ", " + y + ", " + z);
            }
            return false;
        } else if (blockLightLevel > maxLightLevel && maxLightLevel != -1) {
            if (debug) {
                CMSUtils.getEnvironment(world).envLog.logger.info("Denied spawn! for " + entity.getName() + blockLightLevel
                        + " over maximum threshold of " + maxLightLevel + " in dimension " + world.provider.getDimensionType().getId() + " at coords " + x
                        + ", " + y + ", " + z);
            }
            return false;
        }
        return true;
    }

    public void AddCustomSpawn(Class <? extends EntityLiving> class1, int i, int j, int k, EntitySpawnType entitySpawnType, Biome biomes[]) {
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
            biomes = new Biome[biomeList.size()];
            biomes = biomeList.toArray(biomes);
        }

        for (Biome biome : biomes) {
            if (biome == null) {
                continue;
            }

            if (entitySpawnType.getEnvironment().getWorldProviderClass() == WorldProviderEnd.class && !BiomeDictionary.hasType(biome, Type.END)) {
                continue;
            } else if (entitySpawnType.getEnvironment().getWorldProviderClass() == WorldProviderHell.class
                    && !BiomeDictionary.hasType(biome, Type.NETHER)) {
                continue;
            }

            ArrayList<SpawnListEntry> spawnList = entitySpawnType.getBiomeSpawnList(Biome.getIdForBiome(biome));
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
                entitySpawnType.addBiomeSpawnList(Biome.getIdForBiome(biome));
                entitySpawnType.getBiomeSpawnList(Biome.getIdForBiome(biome)).add(new SpawnListEntry(class1, i, j, k));
            }
        }

    }

    public void RemoveCustomSpawn(Class <? extends EntityLiving>  class1, EntitySpawnType entitySpawnType, Biome biomes[]) {
        if (class1 == null) {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        //if (enumcreaturetype == null) { throw new IllegalArgumentException("spawnList cannot be null"); }
        if (biomes == null) {
            biomes = new Biome[biomeList.size()];
            biomes = biomeList.toArray(biomes);
        }

        for (Biome biome : biomes) {
            ArrayList<SpawnListEntry> spawnList = entitySpawnType.getBiomeSpawnList(Biome.getIdForBiome(biome));

            if (spawnList != null) {
                Iterator<SpawnListEntry> iterator = spawnList.iterator();
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

    public static void copyVanillaSpawnData() {
        Iterator<Biome> iterator = Biome.REGISTRY.iterator();
        while (iterator.hasNext()) {
            Biome biome = iterator.next();
            for (EnumCreatureType enumcreaturetype : entityTypes.values()) {
                if (enumcreaturetype == null) {
                    continue;
                }

                for (Iterator<SpawnListEntry> spawnIterator = biome.getSpawnableList(enumcreaturetype).iterator(); spawnIterator.hasNext();) {
                    if (spawnIterator != null) {
                        SpawnListEntry spawnlistentry = spawnIterator.next();
                        if (entityDefaultSpawnBiomes.containsKey(spawnlistentry.entityClass.getName())) { // add biome to existing list
                            if (!entityDefaultSpawnBiomes.get(spawnlistentry.entityClass.getName()).contains(biome)) {
                                entityDefaultSpawnBiomes.get(spawnlistentry.entityClass.getName()).add(biome);
                            }
                        } else { // create new biome list for entity
                            ArrayList<Biome> biomes = new ArrayList<Biome>();
                            biomes.add(biome);
                            entityDefaultSpawnBiomes.put(spawnlistentry.entityClass.getName(), biomes);
                            if (!defaultSpawnListEntryMap.containsKey(spawnlistentry.entityClass.getName())) {
                                defaultSpawnListEntryMap.put(spawnlistentry.entityClass.getName(), spawnlistentry);
                            }
                        }
                        spawnIterator.remove();
                    }
                }
            }
        }
    }

    public void updateSpawnListEntry(EntityData entityData) {
        for (int i = 0; i < biomeList.size(); i++) {
            ArrayList<SpawnListEntry> spawnList = entityData.getLivingSpawnType().getBiomeSpawnList(Biome.getIdForBiome(biomeList.get(i)));
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
            List<Biome> biomes) {
        if (biomes != null) {
            if (debug) {
                globalLog.logger.info("updateSpawnListBiomes for clazz " + clazz + " with " + freq + ":" + min + ":" + max + " in " + biomes);
            }
            RemoveCustomSpawn(clazz, entitySpawnType, null);
            Biome[] allBiomes = new Biome[biomes.size()];
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
    public static boolean canCreatureTypeSpawnAtLocation(Chunk chunk, EntitySpawnType entitySpawnType, SpawnListEntry spawnListEntry, WorldServer world, BlockPos pos) {
        if (!world.getWorldBorder().contains(pos)) {
            return false;
        }

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

        final EntityLiving.SpawnPlacementType spawnPlacementType = EntitySpawnPlacementRegistry.getPlacementForEntity(spawnListEntry.entityClass);
        if ((spawnPlacementType != null && spawnPlacementType == EntityLiving.SpawnPlacementType.IN_WATER) || entitySpawnType.getLivingMaterial() == Material.WATER) {
            return chunk.getBlockState(pos).getMaterial().isLiquid()
                    && chunk.getBlockState(pos.down()).getMaterial().isLiquid()
                    && !chunk.getBlockState(pos.up()).isNormalCube();
        }

        final IBlockState blockstate = chunk.getBlockState(pos);
        BlockPos blockpos = pos.down();
        final IBlockState blockstate1 = chunk.getBlockState(blockpos);

        if (!blockstate1.getBlock().canCreatureSpawn(blockstate1, world, blockpos, spawnPlacementType)) {
            return false;
        }

        boolean flag = blockstate1.getBlock() != Blocks.BEDROCK && blockstate1.getBlock() != Blocks.BARRIER;
        boolean result = flag && !blockstate.isNormalCube() && !blockstate.getMaterial().isLiquid() && !chunk.getBlockState(pos.up()).isNormalCube();
        return result;
    }

    public final int countEntities(Class <? extends EntityLiving>  class1, World world) {
        int i = 0;
        for (int j = 0; j < world.loadedEntityList.size(); j++) {
            Entity entity = world.loadedEntityList.get(j);
            if (class1.isAssignableFrom(entity.getClass())) {
                i++;
            }
        }

        return i;
    }

    /**
     * Gets a random custom mob for spawning based on XYZ coordinates
     */
    public SpawnListEntry getRandomCustomMob(World world, EntitySpawnType entitySpawnType, int pX, int pY, int pZ) {
        List<SpawnListEntry> list = getPossibleCustomCreatures(world, entitySpawnType, pX, pY, pZ);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            // Prevent crash when setting frequencies to 0
            try {
                return (SpawnListEntry) WeightedRandom.getRandomItem(world.rand, list);
            } catch (Throwable t) {
                return null;
            }
        }
    }

    /**
     * Returns a list of creatures of the specified type (mob/aquatic/animal)
     * that can spawn at the given XYZ location, based on biomes.
     */
    public List<SpawnListEntry> getPossibleCustomCreatures(World world, EntitySpawnType entitySpawnType, int pX, int pY, int pZ) {
        Biome biome = world.getBiome(new BlockPos(pX, 0, pZ));
        if (biome == null) {
            return null;
        } else {
            return entitySpawnType.getBiomeSpawnList(Biome.getIdForBiome(biome));
        }
    }

    /**
     * determines if a skeleton spawns on a spider, and if a sheep is a
     * different color
     */
    private static IEntityLivingData creatureSpecificInit(EntityLiving entityLiving, World world, BlockPos pos, IEntityLivingData entityLivingData) {
        if (!ForgeEventFactory.doSpecialSpawn(entityLiving, world, pos.getX(), pos.getY(), pos.getZ())) {
            entityLivingData =
                    entityLiving.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityLiving)), entityLivingData);
        }
        return entityLivingData;
    }

    /**
     * Called during chunk generation to spawn initial creatures.
     */
    public static void performWorldGenSpawning(EntitySpawnType entitySpawnType, World world, Biome par1Biome, int par2, int par3,
            int par4, int par5, Random par6Random, List<SpawnListEntry> customSpawnList, boolean worldGenCreatureSpawning) {
        if (!worldGenCreatureSpawning || customSpawnList.isEmpty() || entitySpawnType.getSpawnCap() <= 0) {
            return;
        }

        while (par6Random.nextFloat() < entitySpawnType.getChunkSpawnChance()) {
            //this is where it has to be changed to include the custom list
            //spawnlistentry = world.getRandomMob(enumcreaturetype, tempX, tempY, tempZ);
            SpawnListEntry spawnlistentry = null;
            try {
                spawnlistentry = (SpawnListEntry) WeightedRandom.getRandomItem(world.rand, customSpawnList);
            } catch (IllegalArgumentException e) {
                // If world.rand returns an invalid value, just continue to avoid crash
                continue;
            }
            if (spawnlistentry == null) {
                continue;
            }

            int i1 = spawnlistentry.minGroupCount + par6Random.nextInt(1 + spawnlistentry.maxGroupCount - spawnlistentry.minGroupCount);
            int j1 = par2 + par6Random.nextInt(par4);
            int k1 = par3 + par6Random.nextInt(par5);
            int l1 = j1;
            int i2 = k1;
            IEntityLivingData entityLivingData = null;
            EntityData entityData = CMSUtils.getEnvironment(world).classToEntityMapping.get(spawnlistentry.entityClass);
            for (int j2 = 0; j2 < i1; ++j2) {
                boolean flag = false;

                for (int k2 = 0; !flag && k2 < 4; ++k2) {
                    final Chunk chunk = getLoadedChunkWithoutMarkingActive((ChunkProviderServer) world.getChunkProvider(), j1 >> 4, k1 >> 4);
                    if (chunk == null || chunk.unloadQueued) {
                        continue;
                    }
                    BlockPos blockpos = world.getTopSolidOrLiquidBlock(new BlockPos(j1, 0, k1));

                    if (canCreatureTypeSpawnAtLocation(chunk, entityData.getLivingSpawnType(), spawnlistentry, (WorldServer) world, blockpos)) {

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
                                        .getPosition().getY(), entityliving.getPosition().getZ(), false);
                        if (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere())) {
                            world.spawnEntity(entityliving);
                            if (CMSUtils.getEnvironment(world).debug) {
                                CMSUtils.getEnvironment(world).envLog.logger.info("[WorldGen spawned " + entityliving.getName()
                                        + " at " + entityliving.getPosition() + " with CREATURE:" + spawnlistentry.itemWeight + ":"
                                        + spawnlistentry.minGroupCount + ":" + spawnlistentry.maxGroupCount + ":"
                                        + ForgeEventFactory.getMaxSpawnPackSize(entityliving) + " in biome " + par1Biome.biomeName
                                        + "]");
                            }
                            entityLivingData = creatureSpecificInit(entityliving, world, pos, entityLivingData);
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

    public static IBlockState getBlockStateWithoutMarkingActive(WorldServer world, BlockPos pos)
    {
        final Chunk chunk = getLoadedChunkWithoutMarkingActive(world.getChunkProvider(), pos.getX() >> 4, pos.getZ() >> 4);
        if (chunk == null || chunk.unloadQueued || pos.getY() < 0 || pos.getY() >= 256)
        {
            return Blocks.AIR.getDefaultState();
        }

        return chunk.getBlockState(pos);
    }

    public static Chunk getLoadedChunkWithoutMarkingActive(ChunkProviderServer chunkProviderServer, int x, int z)
    {
        long i = ChunkPos.asLong(x, z);
        Chunk chunk = chunkProviderServer.id2ChunkMap.get(i);
        return chunk;
    }

    private static boolean isInBorderBounds(WorldBorder border, int x, int z) {
        return (double) ((x  << 4) + 15) > border.minX() && (double) (x << 4) < border.maxX() && (double) ((z  << 4) + 15) > border.minZ() && (double) (x << 4) < border.maxZ();
    }
}
