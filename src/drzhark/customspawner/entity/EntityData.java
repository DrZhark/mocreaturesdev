package drzhark.customspawner.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.guiapi.widget.WidgetSimplewindow;

public class EntityData {

    private EnumCreatureType typeOfCreature;
    private SpawnListEntry spawnlistentry;
    private EnvironmentSettings environment;
    private String entityName;
    private boolean canSpawn = true;
    private boolean vanillaControl = false;
    private int entityId;
    private int minSpawnHeight = 0;
    private int maxSpawnHeight = 256;
    private int minLightLevel = -1;
    private int maxLightLevel = -1;
    private Boolean opaqueBlock = null;
    private List<String> spawnBlockBlacklist = new ArrayList<String>();
    private EntitySpawnType entitySpawnType;
    private CMSConfiguration config;
    @SideOnly(Side.CLIENT)
    private WidgetSimplewindow entityWindow;

    private List<BiomeGenBase> spawnBiomes = new ArrayList<BiomeGenBase>();
    private List<String> biomeGroups = new ArrayList<String>();
    private Map<String, List<BiomeGenBase>> biomeGroupSpawnMap = new HashMap<String, List<BiomeGenBase>>();
    private int frequency = 8;
    private int minGroup = 1;
    private int maxGroup = 1;
    private int maxSpawnInChunk = 1;
    private EntityModData modData;

    public EntityData(EnvironmentSettings environment, SpawnListEntry spawnlistentry, String name, int id, EnumCreatureType type)
    {
        this.environment = environment;
        this.entityName = name;
        this.entityId = id;
        this.typeOfCreature = type;
        this.spawnlistentry = spawnlistentry;
        this.frequency = spawnlistentry.itemWeight;
        this.minGroup = spawnlistentry.minGroupCount;
        this.maxGroup = spawnlistentry.maxGroupCount;
        this.minSpawnHeight = 0;
        this.maxSpawnHeight = 256;
    }

    public Class<? extends EntityLiving> getEntityClass()
    {
        return this.spawnlistentry.entityClass;
    }

    public EnumCreatureType getType()
    {
        if (this.typeOfCreature != null)
            return this.typeOfCreature;
        return null;
    }

    public void setType(EnumCreatureType type)
    {
        this.typeOfCreature = type;
    }

    public int getEntityID()
    {
        return this.entityId;
    }

    public void setEntityID(int id)
    {
        this.entityId = id;
    }

    public CMSConfiguration getEntityConfig()
    {
        return this.config;
    }

    public void setEntityConfig(CMSConfiguration config)
    {
        this.config = config;
    }

    public int getFrequency()
    {
        return this.frequency;
    }

    public void setFrequency(int freq)
    {
        if (freq <= 0)
        {
            this.frequency = 0;
            this.vanillaControl = false;
        }
        else 
        {
            this.frequency = freq;
        }
    }

    public int getMinSpawn()
    {
        return this.minGroup;
    }

    public void setMinSpawn(int min)
    {
        if (min <= 0)
        {
            this.minGroup = 0;
        }
        else 
        {
            this.minGroup = min;
        }
    }

    public int getMaxSpawn()
    {
        return this.maxGroup;
    }

    public void setMaxSpawn(int max)
    {
        if (max <= 0)
        {
            this.maxGroup = 0;
        }
        else 
        {
            this.maxGroup = max;
        }
    }

    public int getMaxInChunk()
    {
        return this.maxSpawnInChunk;
    }

    public void setMaxInChunk(int max)
    {
        if (max <= 0)
        {
            this.maxSpawnInChunk = 0;
        }
        else 
        {
            this.maxSpawnInChunk = max;
        }
    }

    public int getMinSpawnHeight()
    {
        return this.minSpawnHeight;
    }

    public int getMaxSpawnHeight()
    {
        return this.maxSpawnHeight;
    }

    public void setMinHeight(int y)
    {
        this.minSpawnHeight = y;
    }

    public void setMaxHeight(int y)
    {
        this.maxSpawnHeight = y;
    }

    public int getMinLightLevel()
    {
        return this.minLightLevel;
    }

    public void setMinLightLevel(int lightLevel)
    {
        this.minLightLevel = lightLevel;
    }

    public int getMaxLightLevel()
    {
        return this.maxLightLevel;
    }

    public void setMaxLightLevel(int lightLevel)
    {
        this.maxLightLevel = lightLevel;
    }

    public Boolean getOpaqueBlock()
    {
        return this.opaqueBlock;
    }

    public void setOpaqueBlock(Boolean flag)
    {
        this.opaqueBlock = flag;
    }

    public List<String> getSpawnBlockBlacklist()
    {
        return this.spawnBlockBlacklist;
    }

    public EnvironmentSettings getEnvironment()
    {
        return this.environment;
    }

    public void addSpawnBlockToBanlist(String bannedBlock)
    {
        if (this.spawnBlockBlacklist == null)
            this.spawnBlockBlacklist = new ArrayList<String>();
        this.spawnBlockBlacklist.add(bannedBlock);
    }

    public boolean getVanillaControl()
    {
        return this.vanillaControl;
    }

    public void setVanillaControl(boolean flag)
    {
        this.vanillaControl = flag;
    }

    public String getEntityName()
    {
        return this.entityName;
    }

    public void setEntityName(String name)
    {
        this.entityName = name;
    }

    public EntityModData getEntityMod()
    {
        return this.modData;
    }

    public void setEntityMod(EntityModData mod)
    {
        this.modData = mod;
    }

    public void setCanSpawn(boolean flag)
    {
        this.canSpawn = flag;
    }

    public boolean getCanSpawn()
    {
        return this.canSpawn;
    }

    @SideOnly(Side.CLIENT)
    public WidgetSimplewindow getEntityWindow()
    {
        return this.entityWindow;
    }

    @SideOnly(Side.CLIENT)
    public void setEntityWindow(WidgetSimplewindow window)
    {
        this.entityWindow = window;
    }

    public void addBiomeGroupSpawnMap(String biomeGroupName, List<BiomeGenBase> biomes)
    {
        this.biomeGroupSpawnMap.put(biomeGroupName, biomes);
    }

    public List<BiomeGenBase> getBiomeGroupSpawnMap(String biomeGroupName)
    {
        return this.biomeGroupSpawnMap.get(biomeGroupName);
    }

    public void addSpawnBiome(BiomeGenBase biome)
    {
        if (!this.spawnBiomes.contains(biome))
            this.spawnBiomes.add(biome);
    }

    public void removeSpawnBiome(BiomeGenBase biome)
    {
        this.spawnBiomes.remove(biome);
    }

    public List<BiomeGenBase> getSpawnBiomes()
    {
        return this.spawnBiomes;
    }

    public List<String> getBiomeGroups()
    {
        return this.biomeGroups;
    }

    public void setBiomeGroups(List<String> valueList)
    {
        this.biomeGroups = valueList;
    }

    public void removeBiomeGroup(String biome)
    {
        this.biomeGroups.remove(biome);
        if (this.biomeGroups.size() == 0 && this.frequency > 0 && this.minGroup > 0 && this.maxGroup > 0 && this.maxSpawnInChunk > 0)
        {
            this.vanillaControl = true;
        }
    }

    public void addBiomeGroup(String biome)
    {
        this.biomeGroups.add(biome);
        this.vanillaControl = false;
    }

    public EntitySpawnType getLivingSpawnType()
    {
        return this.entitySpawnType;
    }

    public void setLivingSpawnType(EntitySpawnType entitySpawnType)
    {
        this.entitySpawnType = entitySpawnType;
    }

    public void setLivingSpawnType(EnumCreatureType enumcreaturetype)
    {
        this.entitySpawnType = this.environment.entitySpawnTypes.get(enumcreaturetype == null ? "UNDEFINED" : enumcreaturetype.name().toUpperCase());
    }
}
