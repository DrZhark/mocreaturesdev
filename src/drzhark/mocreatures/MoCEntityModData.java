package drzhark.mocreatures;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sharose.mods.guiapi.WidgetSimplewindow;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.gen.MapGenBase;

public class MoCEntityModData {

    private MoCConfiguration config;
    private Map<String, MoCEntityData> creatureMap = new TreeMap<String, MoCEntityData>();
    private Map<String, MoCEntityData> monsterMap = new TreeMap<String, MoCEntityData>();
    private Map<String, MoCEntityData> waterCreatureMap = new TreeMap<String, MoCEntityData>();
    private Map<String, MoCEntityData> ambientMap = new TreeMap<String, MoCEntityData>();
    private Map<String, MoCEntityData> undefinedMap = new TreeMap<String, MoCEntityData>();
    private Map<String, MapGenBase> structureMap = new TreeMap<String, MapGenBase>();
    @SideOnly(Side.CLIENT)
    private WidgetSimplewindow entityModWindow;
    public static enum StructureType { CAVE, MINESHAFT, NETHER_BRIDGE, NETHER_CAVE, RAVINE, SCATTERED_FEATURE, STRONGHOLD, VILLAGE, CUSTOM }
    private String tag;
    private String modClassID;

    public MoCEntityModData(String key, String tag, MoCConfiguration config)
    {
        this.config = config;
        this.modClassID = key;
        this.tag = tag;
    }

    public MoCConfiguration getModConfig()
    {
        return this.config;
    }

    public Map<String, MoCEntityData> getCreatureMap()
    {
        return this.creatureMap;
    }

    public void addCreature(MoCEntityData entityData)
    {
        if (entityData != null)
        {
        	if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Adding " + entityData.getEntityClass() + " to " + entityData.getType() + " spawnList for mod " + this.modClassID);
            if (entityData.getType() == null)
                this.undefinedMap.put(entityData.getEntityName(), entityData);
            else if (entityData.getType() == EnumCreatureType.creature)
                this.creatureMap.put(entityData.getEntityName(), entityData);
            else if (entityData.getType() == EnumCreatureType.waterCreature)
                this.waterCreatureMap.put(entityData.getEntityName(), entityData);
            else if (entityData.getType() == EnumCreatureType.monster)
                this.monsterMap.put(entityData.getEntityName(), entityData);
            else if (entityData.getType() == EnumCreatureType.ambient)
                this.ambientMap.put(entityData.getEntityName(), entityData);
        }
    }

    public Map<String, MoCEntityData> getSpawnListFromType(EnumCreatureType type)
    {
        if (type == null)
            return this.undefinedMap;
        else if (type == EnumCreatureType.creature)
            return this.creatureMap;
        else if (type == EnumCreatureType.waterCreature)
            return this.waterCreatureMap;
        else if (type == EnumCreatureType.monster)
            return this.monsterMap;
        else if (type == EnumCreatureType.ambient)
            return this.ambientMap;
        return null;
    }

    public MoCEntityData getCreature(String entityName)
    {
        System.out.println("entityName = " + entityName);
        // check case-insensitive names to support commands
        for (Map.Entry<String, MoCEntityData> entityEntry : this.undefinedMap.entrySet())
        {
            if (entityEntry.getKey().equalsIgnoreCase(entityName))
                if (this.undefinedMap.get(entityEntry.getKey()) != null)
                    return this.undefinedMap.get(entityEntry.getKey());
        }
        for (Map.Entry<String, MoCEntityData> entityEntry : this.creatureMap.entrySet())
        {
            System.out.println("Found entityEntry " + entityEntry.getKey());
            if (entityEntry.getKey().equalsIgnoreCase(entityName))
                if (this.creatureMap.get(entityEntry.getKey()) != null)
                    return this.creatureMap.get(entityEntry.getKey());
        }
        for (Map.Entry<String, MoCEntityData> entityEntry : this.ambientMap.entrySet())
        {
            if (entityEntry.getKey().equalsIgnoreCase(entityName))
                if (this.ambientMap.get(entityEntry.getKey()) != null)
                    return this.ambientMap.get(entityEntry.getKey());
        }
        for (Map.Entry<String, MoCEntityData> entityEntry : this.waterCreatureMap.entrySet())
        {
            if (entityEntry.getKey().equalsIgnoreCase(entityName))
                if (this.waterCreatureMap.get(entityEntry.getKey()) != null)
                    return this.waterCreatureMap.get(entityEntry.getKey());
        }
        for (Map.Entry<String, MoCEntityData> entityEntry : this.monsterMap.entrySet())
        {
            if (entityEntry.getKey().equalsIgnoreCase(entityName))
                if (this.monsterMap.get(entityEntry.getKey()) != null)
                    return this.monsterMap.get(entityEntry.getKey());
        }
        return null;
    }

    public void addStructureSpawn(MoCEntityData entityData)
    {
        if (entityData != null)
        {
        	if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Adding " + entityData.getEntityClass() + " to " + entityData.getType() + " spawnList for mod " + this.modClassID);
            if (entityData.getType() == null)
                this.undefinedMap.put(entityData.getEntityName(), entityData);
            else if (entityData.getType() == EnumCreatureType.creature)
                this.creatureMap.put(entityData.getEntityName(), entityData);
            else if (entityData.getType() == EnumCreatureType.waterCreature)
                this.waterCreatureMap.put(entityData.getEntityName(), entityData);
            else if (entityData.getType() == EnumCreatureType.monster)
                this.monsterMap.put(entityData.getEntityName(), entityData);
            else if (entityData.getType() == EnumCreatureType.ambient)
                this.ambientMap.put(entityData.getEntityName(), entityData);
        }
    }

    public MoCEntityData removeCreature(MoCEntityData entityData)
    {
        if (entityData != null)
        {
        	if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Removing " + entityData.getEntityClass() + " from " + entityData.getType() + " spawnList for mod " + this.modClassID);
            if (entityData.getType() == null)
                return(this.undefinedMap.remove(entityData.getEntityName()));
            else if (entityData.getType() == EnumCreatureType.creature)
                return this.creatureMap.remove(entityData.getEntityName());
            else if (entityData.getType() == EnumCreatureType.waterCreature)
                return this.waterCreatureMap.remove(entityData.getEntityName());
            else if (entityData.getType() == EnumCreatureType.monster)
                return this.monsterMap.remove(entityData.getEntityName());
            else if (entityData.getType() == EnumCreatureType.ambient)
                return this.ambientMap.remove(entityData.getEntityName());
        }
        return null;
    }

    public Map<String, MoCEntityData> getWaterCreatureMap()
    {
        return this.waterCreatureMap;
    }

    public Map<String, MoCEntityData> getMonsterMap()
    {
        return this.monsterMap;
    }

    public Map<String, MoCEntityData> getAmbientMap()
    {
        return this.ambientMap;
    }

    public Map<String, MoCEntityData> getUndefinedMap()
    {
        return this.undefinedMap;
    }

    public void addStructure(String type, MapGenBase struct)
    {
        this.structureMap.put(type, struct);
    }

    public MapGenBase getStructure(String type)
    {
        return this.structureMap.get(type);
    }

    public List getStructureSpawnList(String type)
    {
       /* if (this.getStructure(type) != null)
        {
            MapGenBase structureBase = 
        }*/
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void setEntityWindow(WidgetSimplewindow window)
    {
        this.entityModWindow = window;
    }

    @SideOnly(Side.CLIENT)
    public WidgetSimplewindow getEntityWindow()
    {
        return this.entityModWindow;
    }

    public String getModKey()
    {
        return this.modClassID;
    }

    public String getModTag()
    {
        return this.tag;
    }
}