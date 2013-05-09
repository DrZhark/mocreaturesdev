package drzhark.mocreatures;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.entity.EnumCreatureType;

public class MoCModData {

    private MoCConfiguration config;
    private Map<Integer, MoCEntityData> creatureMap = new TreeMap<Integer, MoCEntityData>();
    private Map<Integer, MoCEntityData> monsterMap = new TreeMap<Integer, MoCEntityData>();
    private Map<Integer, MoCEntityData> waterCreatureMap = new TreeMap<Integer, MoCEntityData>();
    private Map<Integer, MoCEntityData> ambientMap = new TreeMap<Integer, MoCEntityData>();
    private Map<Integer, MoCEntityData> undefinedMap = new TreeMap<Integer, MoCEntityData>();
    private String modClassID;

    public MoCModData(String key, MoCConfiguration config)
    {
        this.config = config;
        this.modClassID = key;
    }

    public MoCConfiguration getModConfig()
    {
        return this.config;
    }

    public Map<Integer, MoCEntityData> getCreatureMap()
    {
        return this.creatureMap;
    }

    public void addCreature(MoCEntityData entityData)
    {
        if (entityData != null)
        {
            if (entityData.getType() == null)
                this.undefinedMap.put(entityData.getEntityID(), entityData);
            else if (entityData.getType() == EnumCreatureType.creature)
                this.creatureMap.put(entityData.getEntityID(), entityData);
            else if (entityData.getType() == EnumCreatureType.waterCreature)
                this.waterCreatureMap.put(entityData.getEntityID(), entityData);
            else if (entityData.getType() == EnumCreatureType.monster)
                this.monsterMap.put(entityData.getEntityID(), entityData);
            else if (entityData.getType() == EnumCreatureType.ambient)
                this.ambientMap.put(entityData.getEntityID(), entityData);
        }
    }

    public MoCEntityData removeCreature(MoCEntityData entityData)
    {
        if (entityData != null)
        {
            if (entityData.getType() == null)
                return(this.undefinedMap.remove(entityData.getEntityID()));
            else if (entityData.getType() == EnumCreatureType.creature)
                return this.creatureMap.remove(entityData.getEntityID());
            else if (entityData.getType() == EnumCreatureType.waterCreature)
                return this.waterCreatureMap.remove(entityData.getEntityID());
            else if (entityData.getType() == EnumCreatureType.monster)
                return this.monsterMap.remove(entityData.getEntityID());
            else if (entityData.getType() == EnumCreatureType.ambient)
                return this.ambientMap.remove(entityData.getEntityID());
        }
        return null;
    }

    public Map<Integer, MoCEntityData> getWaterCreatureMap()
    {
        return this.waterCreatureMap;
    }

    public Map<Integer, MoCEntityData> getMonsterMap()
    {
        return this.monsterMap;
    }

    public Map<Integer, MoCEntityData> getAmbientMap()
    {
        return this.ambientMap;
    }

    public Map<Integer, MoCEntityData> getUndefinedMap()
    {
        return this.undefinedMap;
    }
}