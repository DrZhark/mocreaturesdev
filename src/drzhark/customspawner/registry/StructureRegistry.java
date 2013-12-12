package drzhark.customspawner.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.feature.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.configuration.CMSConfigCategory;
import drzhark.customspawner.configuration.CMSProperty;
import drzhark.customspawner.configuration.CMSProperty.Type;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.utils.CMSLog;

public class StructureRegistry {

    private Map<String, MapGenBase> structures = new HashMap<String, MapGenBase>();

    public StructureRegistry()
    {
    }

    public void registerStructure(EventType type, MapGenBase base)
    {
        if (type != null && base != null)
        {
            String structKey = "";
            switch (type) {
            case NETHER_BRIDGE :
            {
                structKey = type.name();
                addStructToConfig(base, structKey);
                this.structures.put(type.name(), base);
                break;
            }
            case SCATTERED_FEATURE : // handle witchhut
            {
                structKey = type.name();
                addStructToConfig(base, structKey);
                break;
            }
            default:
                break;
            }
        }
    }

    public void addStructToConfig(MapGenBase base, String structKey)
    {
        List spawnList = null;
        if (structKey.equalsIgnoreCase("NETHER_BRIDGE") && base instanceof MapGenNetherBridge)
            spawnList = ((MapGenNetherBridge)base).getSpawnList();
        else if (structKey.equalsIgnoreCase("SCATTERED_FEATURE") && base instanceof MapGenScatteredFeature)
            spawnList = ((MapGenScatteredFeature)base).getScatteredFeatureSpawnList();

        if (spawnList != null)
        {
            if (!CustomSpawner.CMSStructureConfig.hasCategory(structKey)) // populate default list
            {
                for (int i = 0; i < spawnList.size(); i++)
                {
                    SpawnListEntry spawnlistentry = (SpawnListEntry)spawnList.get(i);
                    // determine mod
                    EntityData entityData = CustomSpawner.classToEntityMapping.get(spawnlistentry.entityClass);
                    if (entityData == null)
                    {
                        //CustomSpawner.INSTANCE.registerLivingSpawnTypes();
                        // generate entity data for CMS
                        entityData = CustomSpawner.INSTANCE.registerEntity(spawnlistentry.entityClass);
                    }

                    if ((!entityData.getCanSpawn() || entityData.getFrequency() <= 0 || entityData.getMaxSpawn() <= 0 || entityData.getMaxInChunk() <= 0))
                    {
                        spawnList.remove(i);
                    }
                    else
                    {
                        CMSConfigCategory cat = CustomSpawner.CMSStructureConfig.getCategory(structKey.toLowerCase());
                        CMSProperty prop = cat.get(structKey);
                        String tag = entityData.getEntityMod().getModTag();

                        if (prop != null && prop.valueList != null && !prop.valueList.contains(tag + "|" + entityData.getEntityName()))
                        {
                            prop.valueList.add(tag + "|" + entityData.getEntityName());
                        }
                        else 
                        {
                            cat.put(structKey, new CMSProperty(structKey, new ArrayList(Arrays.asList(tag + "|" + entityData.getEntityName())), Type.STRING));
                        }
                    }
                }
              }
            else
            {
                // read config
                CMSConfigCategory cat = CustomSpawner.CMSStructureConfig.getCategory(structKey.toLowerCase());
                CMSProperty prop = cat.get(structKey);
                spawnList.clear(); // clear to avoid duplicates
                if (prop != null && prop.valueList != null && prop.valueList.size() > 0)
                {
                    for (int i = 0; i < prop.valueList.size(); i++)
                    {
                        EntityData entityData = CustomSpawner.entityMap.get(prop.valueList.get(i));
                        if (entityData != null)
                        {
                            if (entityData.getCanSpawn())
                            {
                                if (CustomSpawner.debug) CMSLog.logger.info("Adding spawnlistentry " + entityData.getEntityClass() + " to structure " + structKey);
                                spawnList.add(new SpawnListEntry(entityData.getEntityClass(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn()));
                            }
                        }
                    }
                }
            }
            CustomSpawner.CMSStructureConfig.save();
        }
    }
}