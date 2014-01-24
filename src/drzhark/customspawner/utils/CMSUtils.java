package drzhark.customspawner.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.BiomeDictionary;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.biomes.BiomeModData;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.type.EntitySpawnType;

public class CMSUtils {

    public static boolean isTamed(Entity entity)
    {
        if (entity instanceof EntityTameable)
        {
            if (((EntityTameable)entity).isTamed())
            {
                return true;
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        entity.writeToNBT(nbt);
        if (nbt != null)
        {
            if (nbt.hasKey("Owner") && !nbt.getString("Owner").equals(""))
            {
                return true; // ignore
            }
            if (nbt.hasKey("Tamed") && nbt.getBoolean("Tamed") == true)
            {
                return true; // ignore
            }
        }
        return false;
    }

    public static List<String> parseName(String biomeConfigEntry)
    {
        String tag = biomeConfigEntry.substring(0, biomeConfigEntry.indexOf('|'));
        String biomeName = biomeConfigEntry.substring(biomeConfigEntry.indexOf('|') + 1, biomeConfigEntry.length());
        List<String> biomeParts = new ArrayList();
        biomeParts.add(tag);
        biomeParts.add(biomeName);
        return biomeParts;
    }

    public static EnvironmentSettings getEnvironment(World world)
    {
        return CustomSpawner.environmentMap.get(world.provider.getClass());
    }

    public static String generateModPackage(String entityClass)
    {
        String tokens[] = entityClass.split("\\.");

        // no mapping for class in config so lets generate one
        if (tokens.length >= 2)
        {
            String modKey = tokens[0];
            if (modKey.equalsIgnoreCase("mod") || modKey.equalsIgnoreCase("mods") || modKey.equalsIgnoreCase("mob") || modKey.equalsIgnoreCase("mobs") || modKey.equalsIgnoreCase("com") || modKey.equalsIgnoreCase("coms") || modKey.equalsIgnoreCase("net"))
                modKey = tokens[1];
            // handle mods that use net.minecraft namespace
            if (entityClass.contains("net.minecraft") && tokens.length > 2)
                modKey = tokens[2];
            if (modKey.equalsIgnoreCase("minecraft") && tokens.length > 2)
                modKey = tokens[2];
            return modKey.toUpperCase();
        }
        return "";
    }

    public static boolean addWorldEnvironment(Class<? extends WorldProvider> worldProviderClass)
    {
        if (!CustomSpawner.environmentMap.containsKey(worldProviderClass))
        {
            String worldEnvironment = worldProviderClass.getSimpleName().toLowerCase();
            worldEnvironment = worldEnvironment.replace("worldprovider", "");
            worldEnvironment = worldEnvironment.replace("provider", "");
            if (worldProviderClass == WorldProviderSurface.class) // overworld
            {
                if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("Adding World Environment " + worldEnvironment + " for class " + worldProviderClass.getName());
                CustomSpawner.environmentMap.put(worldProviderClass, new EnvironmentSettings(new File(CustomSpawner.ROOT, "overworld"), "overworld", worldProviderClass));
            }
            else if (worldProviderClass == WorldProviderHell.class) // nether
            {
                if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("Adding World Environment " + worldEnvironment + " for class " + worldProviderClass.getName());
                CustomSpawner.environmentMap.put(worldProviderClass, new EnvironmentSettings(new File(CustomSpawner.ROOT, "nether"), "nether", worldProviderClass));
            }
            else if (worldProviderClass == WorldProviderEnd.class) // end
            {
                if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("Adding World Environment " + worldEnvironment + " for class " + worldProviderClass.getName());
                CustomSpawner.environmentMap.put(worldProviderClass, new EnvironmentSettings(new File(CustomSpawner.ROOT, "end"), "end", worldProviderClass));
            }
            else // custom mod world provider
            {
                if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("Adding World Environment " + worldEnvironment + " for class " + worldProviderClass.getName());
                CustomSpawner.environmentMap.put(worldProviderClass, new EnvironmentSettings(new File(CustomSpawner.ROOT, worldEnvironment), worldEnvironment, worldProviderClass));
            }
            // initialize environment settings - fixes Mystcraft spawning
            EnvironmentSettings environment = CustomSpawner.environmentMap.get(worldProviderClass);
            CMSUtils.copyVanillaSpawnLists();
            environment.initializeBiomes();
            environment.initializeEntities();
            environment.updateSettings(); // refresh settings
            return true;
                //CustomSpawner.worldEnvironmentMap.put(worldEnvironment, )
        }
        return false;
    }

    public static BiomeModData getBiomeModData(Map<String, BiomeModData> biomeMap, String tag)
    {
        for (BiomeModData biomeModData : biomeMap.values())
        {
            if (biomeModData.getModTag().equalsIgnoreCase(tag))
                return biomeModData;
        }
        return null;
    }

    public static void dumpEntitySpawnLists()
    {
        for (EnvironmentSettings environment : CustomSpawner.environmentMap.values())
        {
            if (environment.debug) environment.envLog.logger.info("[" + environment.name().toUpperCase() + "]" + " Dumping SPAWN LISTS...");
            else continue;
            for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values())
            {
                if (entitySpawnType.name().equalsIgnoreCase("undefined")) continue;
                if (environment.debug) environment.envLog.logger.info("Reading type " + entitySpawnType.name().toUpperCase() + "...");
                for (int j = 0; j < BiomeGenBase.getBiomeGenArray().length; j++)
                {
                    if (BiomeGenBase.getBiomeGenArray()[j] != null)
                    {
                        BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[j];
                        if (environment.debug) environment.envLog.logger.info("Found biome " + biome.biomeName + " with spawn entries : ");
                        if (entitySpawnType.getBiomeSpawnList(biome.biomeID) == null)
                        {
                            if (environment.debug) environment.envLog.logger.info("NONE!");
                            continue;
                        }
                        for (SpawnListEntry spawn : entitySpawnType.getBiomeSpawnList(biome.biomeID))
                        {
                            if (environment.debug) environment.envLog.logger.info("[SpawnListEntry]" + spawn.entityClass + " - " + spawn.itemWeight + ":" + spawn.minGroupCount + ":" + spawn.maxGroupCount);
                        }
                    }
                }
            }
        }
    }

    public static void dumpDefaultSpawnList()
    {
        if (CustomSpawner.debug)
        {
            for (Map.Entry<String, ArrayList<BiomeGenBase>> defaultEntry : CustomSpawner.entityDefaultSpawnBiomes.entrySet())
            {
                if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("Found entity " + defaultEntry.getKey() + ", printing biome list :");
                for (BiomeGenBase biome : defaultEntry.getValue())
                {
                    if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("with biome " + biome.biomeName);
                }
                if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("");
            }
        }
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

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    public static boolean isValidUndergroundLightLevel(EntityLiving entityliving)
    {
        int i = MathHelper.floor_double(entityliving.posX);
        int j = MathHelper.floor_double(entityliving.boundingBox.minY);
        int k = MathHelper.floor_double(entityliving.posZ);

        if (entityliving.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > entityliving.worldObj.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int l = entityliving.worldObj.getBlockLightValue(i, j, k);

            if (entityliving.worldObj.isThundering())
            {
                int i1 = entityliving.worldObj.skylightSubtracted;
                entityliving.worldObj.skylightSubtracted = 10;
                l = entityliving.worldObj.getBlockLightValue(i, j, k);
                entityliving.worldObj.skylightSubtracted = i1;
            }

            return l <= entityliving.worldObj.rand.nextInt(8);
        }
    }

    public static SpawnListEntry getSpawnListEntry(Class<? extends EntityLiving> clazz, List<SpawnListEntry> spawnList)
    {
        for (SpawnListEntry spawnListEntry : spawnList)
        {
            if (spawnListEntry.entityClass == clazz)
                return spawnListEntry;
        }
        return null;
    }

    public static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void copyVanillaSpawnLists()
    {
        // SAFEST POINT TO COPY VANILLA SPAWN LISTS //
        List<BiomeGenBase> biomeList = new ArrayList<BiomeGenBase>();
        for (int j = 0; j < BiomeGenBase.getBiomeGenArray().length; j++)
        {
            if (BiomeGenBase.getBiomeGenArray()[j] != null)
            {
                biomeList.add(BiomeGenBase.getBiomeGenArray()[j]);
            }
        }

        if (biomeList.size() > 0)
        {
            BiomeGenBase[] allBiomes = new BiomeGenBase[biomeList.size()];
            allBiomes = biomeList.toArray(allBiomes);
            // used for generating default entity biome groups and settings.
            // the defaults will only generate if a biomegroup category is not found for an entity
            if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("Copying spawn list data from all biomes...");
            CustomSpawner.copyVanillaSpawnData(allBiomes);
        }
    }

    public static void registerAllBiomes()
    {
        for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++)
        {
            if (BiomeGenBase.getBiomeGenArray()[i] != null)
            {
                if(!BiomeDictionary.isBiomeRegistered(BiomeGenBase.getBiomeGenArray()[i]))
                {
                    BiomeDictionary.makeBestGuess(BiomeGenBase.getBiomeGenArray()[i]);
                }
            }
        }
    }
}
