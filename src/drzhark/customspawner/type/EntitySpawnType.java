package drzhark.customspawner.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import drzhark.customspawner.environment.EnvironmentSettings;

public class EntitySpawnType {

    private String entitySpawnType = "NONE";
    private EnvironmentSettings environment;
    private int spawnTickRate = 400;
    private int spawnCap = 15;
    private float chunkSpawnChance = 0.0f;
    private boolean hardSpawnLimit;
    private Material livingMaterial = Material.air;
    private boolean enabled = true;
    private boolean debug = false;
    // optional
    private int spawnLightLevel;
    private int despawnLightLevel;
    private int minSpawnHeight = 0;
    private int maxSpawnHeight = 256;
    private boolean allowChunkSpawning = false;
    private Boolean shouldSeeSky;
    private int spawnDistance = 8;
    private Map<Integer, ArrayList<SpawnListEntry>> livingSpawnList = new HashMap<Integer, ArrayList<SpawnListEntry>>();

    public static final String UNDEFINED = "UNDEFINED";
    public static final String CREATURE = "CREATURE";
    public static final String AMBIENT = "AMBIENT";
    public static final String MONSTER = "MONSTER";
    public static final String WATERCREATURE = "WATERCREATURE";
    public static final String UNDERGROUND = "UNDERGROUND";

    public EntitySpawnType(EnvironmentSettings environment, String type) // used for initial config creation
    {
        this.environment = environment;
        this.entitySpawnType = type;
    }

    public EntitySpawnType(EnvironmentSettings environment, String type, int spawnTickRate, int spawnCap)
    {
        this(environment, type, spawnTickRate, spawnCap, 0.0F, Material.air, null, 8, false, true);
    }

    public EntitySpawnType(EnvironmentSettings environment, String type, int spawnTickRate, int spawnCap, Material livingMaterial)
    {
        this(environment, type, spawnTickRate, spawnCap, 0.0F, livingMaterial, null, 8, false, true);
    }

    public EntitySpawnType(EnvironmentSettings environment, String type, int spawnTickRate, int spawnCap, float chunkSpawnChance, Boolean shouldSeeSky)
    {
        this(environment, type, spawnTickRate, spawnCap, chunkSpawnChance, Material.air, shouldSeeSky, 8, false, true);
    }

    public EntitySpawnType(EnvironmentSettings environment, String type, int spawnTickRate, int spawnCap, int minY, int maxY, float chunkSpawnChance, Boolean shouldSeeSky)
    {
        this(environment, type, spawnTickRate, spawnCap, chunkSpawnChance, Material.air, shouldSeeSky, 8, false, true);
        this.minSpawnHeight = minY;
        this.maxSpawnHeight = maxY;
    }

    public EntitySpawnType(EnvironmentSettings environment, String spawnType, int spawnTickRate, int spawnCap, float chunkSpawnChance, Material livingMaterial, Boolean shouldSeeSky, int spawnDistance, boolean hardSpawnLimit, boolean enabled)
    {
        this.entitySpawnType = spawnType;
        this.environment = environment;
        this.spawnTickRate = spawnTickRate;
        this.spawnCap = spawnCap;
        this.chunkSpawnChance = chunkSpawnChance;
        if (shouldSeeSky != null)
            this.shouldSeeSky = new Boolean(shouldSeeSky);
        this.spawnDistance = spawnDistance;
        this.hardSpawnLimit = hardSpawnLimit;
        this.livingMaterial = livingMaterial;
        this.enabled = enabled;
    }

    public float getChunkSpawnChance()
    {
        return this.chunkSpawnChance;
    }

    public void setChunkSpawnChance(float chunkSpawnChance)
    {
        this.chunkSpawnChance = chunkSpawnChance;
    }

    public int getSpawnTickRate()
    {
        return spawnTickRate;
    }

    public void setSpawnTickRate(int spawnTickRate)
    {
        this.spawnTickRate = spawnTickRate;
    }

    public boolean allowSpawning()
    {
        return this.enabled;
    }

    public int getSpawnCap()
    {
        return this.spawnCap;
    }

    public void setSpawnCap(int spawnCap)
    {
        this.spawnCap = spawnCap;
    }

    public String name()
    {
        return this.entitySpawnType;
    }

    public Boolean getShouldSeeSky()
    {
        return this.shouldSeeSky;
    }

    public void setShouldSeeSky(Boolean shouldSeeSky)
    {
        this.shouldSeeSky = shouldSeeSky;
    }

    public int getEntitySpawnDistance()
    {
        return this.spawnDistance;
    }

    public boolean getHardSpawnLimit()
    {
        return this.hardSpawnLimit;
    }

    public Material getLivingMaterial()
    {
        return this.livingMaterial;
    }

    public int getMinSpawnHeight()
    {
        return this.minSpawnHeight;
    }

    public void setMinSpawnHeight(int spawnheight)
    {
        this.minSpawnHeight = spawnheight;
    }

    public int getMaxSpawnHeight()
    {
        return this.maxSpawnHeight;
    }

    public void setMaxSpawnHeight(int spawnheight)
    {
        this.maxSpawnHeight = spawnheight;
    }

    public ArrayList<SpawnListEntry> getBiomeSpawnList(int biomeID)
    {
        return this.livingSpawnList.get(biomeID);
    }

    public void addBiomeSpawnList(int biomeID)
    {
        if (this.livingSpawnList.get(biomeID) == null)
            this.livingSpawnList.put(biomeID, new ArrayList<SpawnListEntry>());
    }
    public Map<Integer, ArrayList<SpawnListEntry>> getLivingSpawnList()
    {
        return this.livingSpawnList;
    }

    public EnvironmentSettings getEnvironment()
    {
        return this.environment;
    }

    public EnumCreatureType getEnumCreatureType()
    {
        if (this.entitySpawnType.equalsIgnoreCase(EntitySpawnType.AMBIENT))
            return EnumCreatureType.ambient;
        if (this.entitySpawnType.equalsIgnoreCase(EntitySpawnType.CREATURE))
            return EnumCreatureType.creature;
        if (this.entitySpawnType.equalsIgnoreCase(EntitySpawnType.MONSTER))
            return EnumCreatureType.monster;
        if (this.entitySpawnType.equalsIgnoreCase(EntitySpawnType.WATERCREATURE))
            return EnumCreatureType.waterCreature;
        return null;
    }
}
