package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sharose.mods.guiapi.WidgetSimplewindow;
import sharose.mods.guiapi.WidgetSingleRow;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class MoCEntityData {

    private Class<? extends EntityLiving> clazz;
    private EnumCreatureType typeOfCreature;
    private String entityName = "";
    private boolean canSpawn = true;
    private boolean vanillaControl = false;
    private int entityId;
    private MoCConfiguration config;
    @SideOnly(Side.CLIENT)
    private WidgetSimplewindow entityWindow;

    private List<BiomeGenBase> spawnBiomes = new ArrayList<BiomeGenBase>();
    private List<String> biomeGroups;
    private int frequency = 8;
    private int minGroup = 1;
    private int maxGroup = 1;
    private int maxSpawnInChunk = 1;
    private MoCEntityModData modData;

    public MoCEntityData(Class<? extends EntityLiving> entityClass)
    {
        this.clazz = entityClass;
        this.typeOfCreature = null;
    }

    public MoCEntityData(Class<? extends EntityLiving> entityClass, int id, EnumCreatureType type)
    {
        this.clazz = entityClass;
        this.entityId = id;
        this.typeOfCreature = type;
    }

    public MoCEntityData(Class<? extends EntityLiving> entityClass, int id, EnumCreatureType type, String name)
    {
        this.clazz = entityClass;
        this.entityId = id;
        this.typeOfCreature = type;
        this.entityName = name;
    }

    public MoCEntityData(Class<? extends EntityLiving> entityClass, int id, EnumCreatureType type, int freq, int min, int max)
    {
        this.clazz = entityClass;
        this.entityId = id;
        this.typeOfCreature = type;
        this.frequency = freq;
        this.minGroup = min;
        this.maxGroup = max;
        if (frequency <= 0 || min == 0 || max == 0)
            this.canSpawn = false;
        else this.canSpawn = true;
    }

    public MoCEntityData(Class<? extends EntityLiving> entityClass, int id, EnumCreatureType type, int freq, int min, int max, int maxchunk)
    {
        this.clazz = entityClass;
        this.entityId = id;
        this.typeOfCreature = type;
        this.frequency = freq;
        this.minGroup = min;
        this.maxGroup = max;
        this.maxSpawnInChunk = maxchunk;
        if (frequency <= 0 || min == 0 || max == 0)
            this.canSpawn = false;
        else this.canSpawn = true;
    }

    public MoCEntityData(Class<? extends EntityLiving> entityClass, int id, String name, EnumCreatureType type, int freq, int min, int max, List<String> biomeGroups)
    {
        this.clazz = entityClass;
        this.entityId = id;
        this.entityName = name;
        this.typeOfCreature = type;
        this.frequency = freq;
        this.minGroup = min;
        this.maxGroup = max;
        this.biomeGroups = biomeGroups;
        if (frequency <= 0 || min == 0 || max == 0 || biomeGroups.size() == 0)
            this.canSpawn = false;
        else this.canSpawn = true;
    }

    public MoCEntityData(Class<? extends EntityLiving> entityClass, EnumCreatureType type, int freq, int min, int max, int maxchunk, List<String> biomeGroups)
    {
        this.clazz = entityClass;
      //  this.entityId = -1;
        this.typeOfCreature = type;
        this.frequency = freq;
        this.minGroup = min;
        this.maxGroup = max;
        this.biomeGroups = biomeGroups;
        this.maxSpawnInChunk = maxchunk;
        if (frequency <= 0 || min == 0 || max == 0 || biomeGroups.size() == 0)
            this.canSpawn = false;
        else this.canSpawn = true;
    }

    public MoCEntityData(Class<? extends EntityLiving> entityClass, int id, EnumCreatureType type, int freq, int min, int max, int maxchunk, List<String> biomeGroups)
    {
        this.clazz = entityClass;
        this.entityId = id;
        this.typeOfCreature = type;
        this.frequency = freq;
        this.minGroup = min;
        this.maxGroup = max;
        this.biomeGroups = biomeGroups;
        this.maxSpawnInChunk = maxchunk;
        if (frequency <= 0 || min == 0 || max == 0 || biomeGroups.size() == 0)
            this.canSpawn = false;
        else this.canSpawn = true;
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

    public MoCConfiguration getEntityConfig()
    {
        return this.config;
    }

    public void setEntityConfig(MoCConfiguration config)
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

    public MoCEntityModData getEntityMod()
    {
        return this.modData;
    }

    public void setEntityMod(MoCEntityModData mod)
    {
        this.modData = mod;
    }

    public void setCanSpawn(boolean flag)
    {
        this.canSpawn = flag;
    }

    public boolean getCanSpawn()
    {
        if (this.frequency > 0 
         && ((this.getType() == EnumCreatureType.creature && MoCreatures.proxy.spawnCreatures && !(MoCreatures.proxy.maxCreatures <= 0))
         || (this.getType() == EnumCreatureType.ambient && MoCreatures.proxy.spawnAmbients && !(MoCreatures.proxy.maxAmbients <= 0))
         || (this.getType() == EnumCreatureType.waterCreature && MoCreatures.proxy.spawnWaterCreatures && !(MoCreatures.proxy.maxWaterCreatures <= 0))
         || (this.getType() == EnumCreatureType.monster && MoCreatures.proxy.spawnMonsters && !(MoCreatures.proxy.maxMonsters <= 0))))
            this.canSpawn = true;
        else this.canSpawn = false;
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

    public void addSpawnBiome(BiomeGenBase biome)
    {
        this.spawnBiomes.add(biome);
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
}
