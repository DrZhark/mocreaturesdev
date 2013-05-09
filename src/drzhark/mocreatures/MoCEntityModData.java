package drzhark.mocreatures;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    public static enum StructureType { CAVE, MINESHAFT, NETHER_BRIDGE, NETHER_CAVE, RAVINE, SCATTERED_FEATURE, STRONGHOLD, VILLAGE, CUSTOM }

    private String modClassID;

    public MoCEntityModData(String key, MoCConfiguration config)
    {
        this.config = config;
        this.modClassID = key;
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

    public MoCEntityData getCreature(String entityName)
    {
        for (int i = 0; i < this.undefinedMap.size(); i++)
        {
            if (this.undefinedMap.get(entityName) != null)
                return this.undefinedMap.get(entityName);
        }
        for (int i = 0; i < this.creatureMap.size(); i++)
        {
            if (this.creatureMap.get(entityName) != null)
                return this.creatureMap.get(entityName);
        }
        for (int i = 0; i < this.ambientMap.size(); i++)
        {
            if (this.ambientMap.get(entityName) != null)
                return this.ambientMap.get(entityName);
        }
        for (int i = 0; i < this.waterCreatureMap.size(); i++)
        {
            if (this.waterCreatureMap.get(entityName) != null)
                return this.waterCreatureMap.get(entityName);
        }
        for (int i = 0; i < this.monsterMap.size(); i++)
        {
            if (this.monsterMap.get(entityName) != null)
                return this.monsterMap.get(entityName);
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

    public String getModKey()
    {
        return this.modClassID;
    }
}