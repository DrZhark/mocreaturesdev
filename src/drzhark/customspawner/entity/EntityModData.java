package drzhark.customspawner.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.gen.MapGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.guiapi.widget.WidgetSimplewindow;

public class EntityModData {

    private CMSConfiguration config;
    /*private Map<String, EntityData> creatureMap = new TreeMap<String, EntityData>();
    private Map<String, EntityData> monsterMap = new TreeMap<String, EntityData>();
    private Map<String, EntityData> waterCreatureMap = new TreeMap<String, EntityData>();
    private Map<String, EntityData> ambientMap = new TreeMap<String, EntityData>();
    private Map<String, EntityData> undefinedMap = new TreeMap<String, EntityData>();*/
    private Map<EntitySpawnType, Map<String, EntityData>> livingSpawnMap = new HashMap<EntitySpawnType, Map<String, EntityData>>();
    
    private Map<String, MapGenBase> structureMap = new TreeMap<String, MapGenBase>();
    private Map<EnumCreatureType, WidgetSimplewindow> widgetMap = new HashMap<EnumCreatureType, WidgetSimplewindow>();
    private String tag;
    private String modClassID;

    public EntityModData(String key, String tag, CMSConfiguration config)
    {
        this.config = config;
        this.modClassID = key;
        this.tag = tag;
    }

    public CMSConfiguration getModConfig()
    {
        return this.config;
    }

    public Map<String, EntityData> getCreatureMap(EntitySpawnType entitySpawnType)
    {
        //return this.creatureMap;
        return this.livingSpawnMap.get(entitySpawnType);
    }

    public boolean addCreature(EntityData entityData)
    {
        boolean result = true;
        if (entityData != null)
        {
            if (this.livingSpawnMap.get(entityData.getLivingSpawnType()) == null)
            {
                this.livingSpawnMap.put(entityData.getLivingSpawnType(), new HashMap<String, EntityData>());
                this.livingSpawnMap.get(entityData.getLivingSpawnType()).put(entityData.getEntityName(), entityData);
            }
            else if(!this.livingSpawnMap.get(entityData.getLivingSpawnType()).containsKey(entityData.getEntityName()))
            {
                this.livingSpawnMap.get(entityData.getLivingSpawnType()).put(entityData.getEntityName(), entityData);
            }
            else result = false;
        }
        return result;
    }

    public Map<String, EntityData> getSpawnListFromType(EntitySpawnType entitySpawnType)
    {
        return livingSpawnMap.get(entitySpawnType);
    }

    public EntityData getCreature(EntitySpawnType entitySpawnType, String entityName)
    {
        // check case-insensitive names to support commands
        return livingSpawnMap.get(entitySpawnType).get(entityName);
    }

    public void addStructureSpawn(EntityData entityData)
    {
        if (entityData != null)
        {
            if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("Adding " + entityData.getEntityClass() + " to " + entityData.getLivingSpawnType().name() + " spawnList for mod " + this.modClassID);
            this.livingSpawnMap.get(entityData.getLivingSpawnType()).put(entityData.getEntityName(), entityData);
        }
    }

    public EntityData removeCreature(EntityData entityData)
    {
        if (entityData != null)
        {
            if (CustomSpawner.debug) CustomSpawner.globalLog.logger.info("Removing " + entityData.getEntityClass() + " from " + entityData.getLivingSpawnType().name() + " spawnList for mod " + this.modClassID);
            return (this.livingSpawnMap.get(entityData.getLivingSpawnType()).remove(entityData.getEntityName()));
        }
        return null;
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
    public void setEntityWindow(EnumCreatureType type, WidgetSimplewindow window)
    {
        this.widgetMap.put(type, window);
    }

    @SideOnly(Side.CLIENT)
    public WidgetSimplewindow getEntityWindow(EnumCreatureType type)
    {
        return this.widgetMap.get(type);
    }

    @SideOnly(Side.CLIENT)
    public Map<EnumCreatureType, WidgetSimplewindow> getWidgetWindows()
    {
        return this.widgetMap;
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