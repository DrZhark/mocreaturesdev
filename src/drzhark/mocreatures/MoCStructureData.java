package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drzhark.mocreatures.MoCProperty.Type;

import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

public class MoCStructureData {

    private Map<String, MapGenBase> structures = new HashMap<String, MapGenBase>();

    public MoCStructureData()
    {
    }

    public void addStructure(EventType type, MapGenBase base)
    {
        if (type != null && base != null)
        {
            String structKey = "";
            if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Detected " + type + " structure.");
            switch (type) {
                
                case NETHER_BRIDGE :
                {
                    structKey = type.name();
                    List spawnList = ((MapGenNetherBridge)base).getSpawnList();
                    if (!MoCreatures.proxy.mocStructureConfig.hasCategory(structKey)) // populate default list
                    {
                        //MoCConfiguration entityConfig = MoCreatures.proxy.entityModMap.get("undefined").getModConfig();
                        for (int i = 0; i < spawnList.size(); i++)
                        {
                            SpawnListEntry spawnlistentry = (SpawnListEntry)spawnList.get(i);
                            // determine mod
                            MoCEntityData entityData = MoCreatures.proxy.classToEntityMapping.get(spawnlistentry.entityClass);
                            if (!entityData.getCanSpawn() )
                            {
                                if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Removing " + spawnlistentry.entityClass + " + from structure " + structKey);
                                spawnList.remove(i);
                            }
                            else
                            {
                                MoCConfigCategory cat = MoCreatures.proxy.mocStructureConfig.getCategory(structKey.toLowerCase());
                                MoCProperty prop = cat.get(structKey);
                                MoCBiomeModData biomeData = MoCreatures.proxy.biomeModMap.get(entityData.getEntityMod().getModKey());
                                String tag = entityData.getEntityMod().getModKey();
                                if (biomeData != null)
                                {
                                    tag = biomeData.getBiomeModKey();
                                    if (tag.equals("vanilla"))
                                        tag = "MC";
                                }
                                if (prop != null && prop.valueList != null && !prop.valueList.contains(tag + "|" + entityData.getEntityName()))
                                {
                                    if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Added " + spawnlistentry.entityClass + " from structure " + structKey + " to config.");
                                    prop.valueList.add(tag + "|" + entityData.getEntityName());
                                }
                                else {
                                    if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Created new prop, Added " + spawnlistentry.entityClass + " from structure " + structKey + " to config.");
                                    cat.put(structKey, new MoCProperty(structKey, new ArrayList(Arrays.asList(tag + "|" + entityData.getEntityName())), Type.STRING));
                                }
                                MoCreatures.proxy.mocStructureConfig.save();
                            }
                        }
                      }
                    else 
                    {
                        // read config
                        MoCConfigCategory cat = MoCreatures.proxy.mocStructureConfig.getCategory(structKey.toLowerCase());
                        MoCProperty prop = cat.get(structKey);
                        if (prop != null && prop.valueList != null && prop.valueList.size() > 0)
                        {
                            spawnList.clear(); // clear to avoid duplicates
                            for (int i = 0; i < prop.valueList.size(); i++)
                            {
                                List<String> nameParts = MoCreatures.proxy.parseBiome(prop.valueList.get(i));
                                if (nameParts != null && nameParts.size() == 2)
                                {
                                    String tag = nameParts.get(0);
                                    String name = nameParts.get(1);
                                    MoCBiomeModData biomeModData = MoCreatures.proxy.tagConfigMap.get(tag);
                                    if (biomeModData != null)
                                    {
                                        String modKey = biomeModData.getBiomeModKey();
                                        MoCEntityModData modData = MoCreatures.proxy.entityModMap.get(modKey);
                                        if (modData != null)
                                        {
                                            if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("FOUND VALID MOD " + modKey);
                                            MoCEntityData entityData = modData.getCreature(name);
                                            if (entityData != null)
                                            {
                                                if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("FOUND VALID ENTITY " + entityData.getEntityName() + " in STRUCTURE");
                                                if (entityData.getCanSpawn())
                                                {
                                                    if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("ADDED " + entityData.getEntityClass() + " to structure spawn list.");
                                                    spawnList.add(new SpawnListEntry(entityData.getEntityClass(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn()));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    //}
                    this.structures.put(type.name(), base);
                    }
                }
                case STRONGHOLD :
                {
                    
                }
            default:
                break;
            }
        }
    }

    /*public MoCEntityModData locateModData(String entityClass)
    {
        if (entityClass.contains("net.minecraft") || entityClass.toString().length() <= 3) // vanilla
        {
            MoCEntityModData modData = MoCreatures.proxy.entityModMap.get("vanilla");
            return modData;
        }
        else { // custom
            for (Map.Entry<String, MoCEntityModData> modEntry : MoCreatures.proxy.entityModMap.entrySet())
            {
                if (entityClass.contains(modEntry.getKey()))
                {
                    // Found match, use config
                    System.out.println("FOUND MATCHING MOD, " + entityClass + " contains " + modEntry.getKey());
                    MoCEntityModData modData = modEntry.getValue();
                    return modData;
                }
            }
        }
        return null;
    }*/
}
