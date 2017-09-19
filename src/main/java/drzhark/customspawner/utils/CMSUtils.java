package drzhark.customspawner.utils;

import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.biomes.BiomeModData;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.type.EntitySpawnType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CMSUtils {

    public static boolean isTamed(Entity entity) {
        if (entity instanceof EntityTameable) {
            if (((EntityTameable) entity).isTamed()) {
                return true;
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        entity.writeToNBT(nbt);
        if (nbt != null) {
            if (nbt.hasKey("Owner") && !nbt.getString("Owner").equals("")) {
                return true; // ignore
            }
            if (nbt.hasKey("Tamed") && nbt.getBoolean("Tamed") == true) {
                return true; // ignore
            }
        }
        return false;
    }

    public static List<String> parseName(String biomeConfigEntry) {
        List<String> biomeParts = new ArrayList<String>();
        if (biomeConfigEntry.indexOf('|') == -1) {
            biomeParts.add("");
            if (CustomSpawner.debug) {
                CustomSpawner.globalLog.logger.info("Invalid biome entry '" + biomeConfigEntry + "' detected in config while attempting to parse name. Please review your biome configs for possible typos. Skipping...");
            }
            return biomeParts;
        }
        String tag = biomeConfigEntry.substring(0, biomeConfigEntry.indexOf('|'));
        String biomeName = biomeConfigEntry.substring(biomeConfigEntry.indexOf('|') + 1, biomeConfigEntry.length());
        biomeParts.add(tag);
        biomeParts.add(biomeName);
        return biomeParts;
    }

    public static EnvironmentSettings getEnvironment(World world) {
        return CustomSpawner.environmentMap.get(world.provider.getClass());
    }

    public static String generateModPackage(String entityClass) {
        String tokens[] = entityClass.split("\\.");

        // no mapping for class in config so lets generate one
        if (tokens.length >= 2) {
            String modKey = tokens[0];
            if (modKey.equalsIgnoreCase("mod") || modKey.equalsIgnoreCase("mods") || modKey.equalsIgnoreCase("mob")
                    || modKey.equalsIgnoreCase("mobs") || modKey.equalsIgnoreCase("com") || modKey.equalsIgnoreCase("coms")
                    || modKey.equalsIgnoreCase("net")) {
                modKey = tokens[1];
            }
            // handle mods that use net.minecraft namespace
            if (entityClass.contains("net.minecraft") && tokens.length > 2) {
                modKey = tokens[2];
            }
            if (modKey.equalsIgnoreCase("minecraft") && tokens.length > 2) {
                modKey = tokens[2];
            }
            return modKey.toUpperCase();
        }
        return "";
    }

    public static boolean addWorldEnvironment(Class<? extends WorldProvider> worldProviderClass) {
        if (!CustomSpawner.environmentMap.containsKey(worldProviderClass)) {
            String worldEnvironment = worldProviderClass.getSimpleName().toLowerCase();
            worldEnvironment = worldEnvironment.replace("worldprovider", "");
            worldEnvironment = worldEnvironment.replace("provider", "");
            if (worldProviderClass == WorldProviderSurface.class) // overworld
            {
                if (CustomSpawner.debug) {
                    CustomSpawner.globalLog.logger
                            .info("Adding World Environment " + worldEnvironment + " for class " + worldProviderClass.getName());
                }
                CustomSpawner.environmentMap.put(worldProviderClass, new EnvironmentSettings(new File(CustomSpawner.ROOT, "overworld"), "overworld",
                        worldProviderClass));
            } else if (worldProviderClass == WorldProviderHell.class) // nether
            {
                if (CustomSpawner.debug) {
                    CustomSpawner.globalLog.logger
                            .info("Adding World Environment " + worldEnvironment + " for class " + worldProviderClass.getName());
                }
                CustomSpawner.environmentMap.put(worldProviderClass, new EnvironmentSettings(new File(CustomSpawner.ROOT, "nether"), "nether",
                        worldProviderClass));
            } else if (worldProviderClass == WorldProviderEnd.class) // end
            {
                if (CustomSpawner.debug) {
                    CustomSpawner.globalLog.logger
                            .info("Adding World Environment " + worldEnvironment + " for class " + worldProviderClass.getName());
                }
                CustomSpawner.environmentMap.put(worldProviderClass, new EnvironmentSettings(new File(CustomSpawner.ROOT, "end"), "end",
                        worldProviderClass));
            } else // custom mod world provider
            {
                if (CustomSpawner.debug) {
                    CustomSpawner.globalLog.logger
                            .info("Adding World Environment " + worldEnvironment + " for class " + worldProviderClass.getName());
                }
                CustomSpawner.environmentMap.put(worldProviderClass, new EnvironmentSettings(new File(CustomSpawner.ROOT, worldEnvironment),
                        worldEnvironment, worldProviderClass));
            }
            // initialize environment settings - fixes Mystcraft spawning
            EnvironmentSettings environment = CustomSpawner.environmentMap.get(worldProviderClass);
            if (CustomSpawner.debug) {
                CustomSpawner.globalLog.logger.info("Copying spawn list data from all biomes...");
            }
            CustomSpawner.copyVanillaSpawnData();
            environment.initializeBiomes();
            environment.initializeEntities();
            environment.updateSettings(); // refresh settings
            return true;
            //CustomSpawner.worldEnvironmentMap.put(worldEnvironment, )
        }
        return false;
    }

    public static BiomeModData getBiomeModData(Map<String, BiomeModData> biomeMap, String tag) {
        for (BiomeModData biomeModData : biomeMap.values()) {
            if (biomeModData.getModTag().equalsIgnoreCase(tag)) {
                return biomeModData;
            }
        }
        return null;
    }

    public static void dumpEntitySpawnLists() {
        for (EnvironmentSettings environment : CustomSpawner.environmentMap.values()) {
            if (!environment.debug) {
                continue;
            }

            environment.envLog.logger.info("[" + environment.name().toUpperCase() + "]" + " Dumping SPAWN LISTS...");
            for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values()) {
                if (entitySpawnType.name().equalsIgnoreCase("undefined")) {
                    continue;
                }
                environment.envLog.logger.info("Reading type " + entitySpawnType.name().toUpperCase() + "...");
                Iterator<Biome> iterator = Biome.REGISTRY.iterator();
                while (iterator.hasNext()) {
                    Biome biome = iterator.next();
                    environment.envLog.logger.info("Found biome " + biome.biomeName + " with spawn entries : ");
                    if (entitySpawnType.getBiomeSpawnList(Biome.getIdForBiome(biome)) == null) {
                        environment.envLog.logger.info("NONE!");
                        continue;
                    }
                    for (SpawnListEntry spawn : entitySpawnType.getBiomeSpawnList(Biome.getIdForBiome(biome))) {
                        environment.envLog.logger.info("[SpawnListEntry]" + spawn.entityClass + " - " + spawn.itemWeight + ":"
                                + spawn.minGroupCount + ":" + spawn.maxGroupCount);
                    }
                }
            }
        }
    }

    public static void dumpDefaultSpawnList() {
        if (!CustomSpawner.debug) {
            return;
        }

        for (Map.Entry<String, ArrayList<Biome>> defaultEntry : CustomSpawner.entityDefaultSpawnBiomes.entrySet()) {
            CustomSpawner.globalLog.logger.info("Found entity " + defaultEntry.getKey() + ", printing biome list :");
            for (Biome biome : defaultEntry.getValue()) {
                CustomSpawner.globalLog.logger.info("with biome " + biome.biomeName);
            }
            CustomSpawner.globalLog.logger.info("");
        }
    }

    /**
     * Gets the amount of light on a block without taking into account sunlight
     */
    public static int getLightFromNeighbors(Chunk chunk, int x, int y, int z) {
        ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y >> 4];

        if (extendedblockstorage == null) {
            return 0;
        } else {
            return extendedblockstorage.getBlockLight(x, y & 15, z);
        }
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    public static boolean isValidUndergroundLightLevel(EntityLiving entityliving) {
        BlockPos pos = entityliving.getPosition();

        if (entityliving.world.getLightFor(EnumSkyBlock.SKY, pos) > entityliving.world.rand.nextInt(32)) {
            return false;
        } else {
            int l = entityliving.world.getLightFromNeighbors(pos);

            if (entityliving.world.isThundering()) {
                int i1 = entityliving.world.getSkylightSubtracted();
                entityliving.world.setSkylightSubtracted(10);
                l = entityliving.world.getLightFromNeighbors(pos);
                entityliving.world.setSkylightSubtracted(i1);
            }

            return l <= entityliving.world.rand.nextInt(8);
        }
    }

    public static SpawnListEntry getSpawnListEntry(Class<? extends EntityLiving> clazz, List<SpawnListEntry> spawnList) {
        for (SpawnListEntry spawnListEntry : spawnList) {
            if (spawnListEntry.entityClass == clazz) {
                return spawnListEntry;
            }
        }
        return null;
    }

    public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>() {

            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /*public static void registerAllBiomes() {
        for (int i = 0; i < Biome.getBiomeGenArray().length; i++) {
            if (Biome.getBiomeGenArray()[i] != null) {
                if (!BiomeDictionary.isBiomeRegistered(Biome.getBiomeGenArray()[i])) {
                    BiomeDictionary.makeBestGuess(Biome.getBiomeGenArray()[i]);
                }
            }
        }
    }*/

    public static long asLong(int x, int z) {
        return ((long) x << 32) + z - Integer.MIN_VALUE;
    }

    public static int getChunkX(long l) {
        return (int) (l >> 32);
    }

    public static int getChunkZ(long l) {
        return (int) (l & 0xFFFFFFFF) + Integer.MIN_VALUE;
    }
}
