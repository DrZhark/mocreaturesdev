package drzhark.customspawner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.SpawnListEntry;

public class EntitySpawnType {

    private String entitySpawnType;
    private int spawnTickRate;
    private int spawnCap;
    private float chunkSpawnChance;
    private boolean hardSpawnLimit;
    private Material livingMaterial;
    private boolean enabled;
    // optional
    private int spawnLightLevel;
    private int despawnLightLevel;
    private int minSpawnHeight = 0;
    private int maxSpawnHeight = 256;
    private boolean allowChunkSpawning;
    private Boolean shouldSeeSky;
    private int spawnDistance;
    private Map<Integer, ArrayList<SpawnListEntry>> livingSpawnList = new HashMap<Integer, ArrayList<SpawnListEntry>>();

    public static final String UNDEFINED = "UNDEFINED";
    public static final String CREATURE = "CREATURE";
    public static final String AMBIENT = "AMBIENT";
    public static final String MONSTER = "MONSTER";
    public static final String WATERCREATURE = "WATERCREATURE";
    public static final String UNDERGROUND = "UNDERGROUND";

    public EntitySpawnType(String type, int spawnTickRate, int spawnCap)
    {
        this(type, spawnTickRate, spawnCap, 0.0F, Material.air, null, 8, false, true);
    }

    public EntitySpawnType(String type, int spawnTickRate, int spawnCap, Material livingMaterial)
    {
        this(type, spawnTickRate, spawnCap, 0.0F, livingMaterial, null, 8, false, true);
    }

    public EntitySpawnType(String type, int spawnTickRate, int spawnCap, float chunkSpawnChance, Boolean shouldSeeSky)
    {
        this(type, spawnTickRate, spawnCap, chunkSpawnChance, Material.air, shouldSeeSky, 8, false, true);
    }

    public EntitySpawnType(String type, int spawnTickRate, int spawnCap, int minY, int maxY, float chunkSpawnChance, Boolean shouldSeeSky)
    {
        this(type, spawnTickRate, spawnCap, chunkSpawnChance, Material.air, shouldSeeSky, 8, false, true);
        this.minSpawnHeight = minY;
        this.maxSpawnHeight = maxY;
    }

    public EntitySpawnType(String type, int spawnTickRate, int spawnCap, float chunkSpawnChance, Material livingMaterial, Boolean shouldSeeSky, int spawnDistance, boolean hardSpawnLimit, boolean enabled)
    {
        this.entitySpawnType = type;
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

    public String getLivingSpawnTypeName()
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

    public Map<Integer, ArrayList<SpawnListEntry>> getLivingSpawnList()
    {
        return this.livingSpawnList;
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
