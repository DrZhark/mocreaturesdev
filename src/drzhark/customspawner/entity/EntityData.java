package drzhark.customspawner.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.EntitySpawnType;
import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.mocreatures.client.gui.WidgetSimplewindow;

public class EntityData {

    private Class<? extends EntityLiving> clazz;
    private EnumCreatureType typeOfCreature;
    private SpawnListEntry spawnlistentry;
    private String entityName;
    private boolean canSpawn = true;
    private boolean vanillaControl = false;
    private int entityId;
    private int minY = -1;
    private int maxY = -1;
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

    public EntityData(Class<? extends EntityLiving> entityClass, int id, EnumCreatureType type, String name)
    {
        this.clazz = entityClass;
        this.entityId = id;
        this.typeOfCreature = type;
        this.entityName = name;
    }

    public EntityData(Class<? extends EntityLiving> entityClass, int id, EnumCreatureType type, SpawnListEntry spawnlistentry)
    {
        this.clazz = entityClass;
        this.entityId = id;
        this.typeOfCreature = type;
        this.spawnlistentry = spawnlistentry;
        this.frequency = spawnlistentry.itemWeight;
        this.minGroup = spawnlistentry.minGroupCount;
        this.maxGroup = spawnlistentry.maxGroupCount;
        if (this.frequency <= 0 || this.minGroup == 0 || this.maxGroup == 0)
            this.canSpawn = false;
        else this.canSpawn = true;
        // TODO
        this.minY = 0;
        this.maxY = 0;
    }

    public Class<? extends EntityLiving> getEntityClass()
    {
        return this.clazz;
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
            this.canSpawn = false;
            this.vanillaControl = false;
        }
        else 
        {
            this.frequency = freq;
            this.canSpawn = true;
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
            this.canSpawn = false;
        }
        else 
        {
            this.minGroup = min;
            this.canSpawn = true;
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
            this.canSpawn = false;
        }
        else 
        {
            this.maxGroup = max;
            this.canSpawn = true;
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
            this.canSpawn = false;
        }
        else 
        {
            this.maxSpawnInChunk = max;
            this.canSpawn = true;
        }
    }

    public int getMinY()
    {
        return this.minY;
    }

    public int getMaxY()
    {
        return this.maxY;
    }

    public void setMinY(int y)
    {
        this.minY = y;
    }

    public void setMaxY(int y)
    {
        this.maxY = y;
    }

    public List<String> getSpawnBlockBlacklist()
    {
        return this.spawnBlockBlacklist;
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

    public void setSpawnBiomes(List<BiomeGenBase> biomes)
    {
        this.spawnBiomes = biomes;
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
        //System.out.println("checking type " + enumcreaturetype.name().toUpperCase());
        this.entitySpawnType = CustomSpawner.entitySpawnTypes.get(enumcreaturetype == null ? "UNDEFINED" : enumcreaturetype.name().toUpperCase());
    }
}
