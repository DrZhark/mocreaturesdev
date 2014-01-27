package drzhark.customspawner.registry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import drzhark.customspawner.configuration.CMSConfigCategory;
import drzhark.customspawner.configuration.CMSProperty;
import drzhark.customspawner.configuration.CMSProperty.Type;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.utils.CMSUtils;

public class StructureRegistry {


    public StructureRegistry()
    {
    }

    public void registerStructure(EnvironmentSettings environment, EventType type, MapGenBase base)
    {
        if (type != null && base != null)
        {
            String structKey = "";
            switch (type) {
            case NETHER_BRIDGE :
            {
                structKey = type.name();
                addStructToConfig(environment, base, structKey);
                break;
            }
            case SCATTERED_FEATURE : // handle witchhut
            {
                structKey = type.name();
                addStructToConfig(environment, base, structKey);
                break;
            }
            default:
                break;
            }
        }
    }

    public void addStructToConfig(EnvironmentSettings environment, MapGenBase base, String structKey)
    {
        List spawnList = null;
        String structCategoryName = "";
        if (structKey.equalsIgnoreCase("NETHER_BRIDGE") && base instanceof MapGenNetherBridge)
        {
            structCategoryName = "netherbridge";
            spawnList = ((MapGenNetherBridge)base).getSpawnList();
        }
        else if (structKey.equalsIgnoreCase("SCATTERED_FEATURE") && base instanceof MapGenScatteredFeature)
        {
            structCategoryName = "witchhut";
            spawnList = ((MapGenScatteredFeature)base).getScatteredFeatureSpawnList();
        }
        environment.CMSStructureConfig.load(); // needed ?

        if (spawnList != null)
        {
            if (!environment.CMSStructureConfig.hasCategory(structCategoryName)) // populate default list
            {
                if (environment.debug) environment.envLog.logger.info("Creating new category for STRUCTURE " + structCategoryName + " in environment " + environment.name());
                CMSConfigCategory spawnListCat = environment.CMSStructureConfig.getCategory("spawnlist");
                spawnListCat.setComment("To add entities to a specific structure, add the entity in the format of TAG|ENTITYNAME to list." + "/n" + "Example: <MC|Witch:MOC|Horse:MC|Sheep");
                CMSConfigCategory structCat = environment.CMSStructureConfig.getCategory(structCategoryName);
                CMSConfigCategory spawnEntryCat = new CMSConfigCategory("spawnentries", structCat);
                spawnListCat.put(structCategoryName, new CMSProperty(structCategoryName, new ArrayList<String>(), Type.STRING));
                CMSProperty spawnListProp = spawnListCat.get(structCategoryName);
                for (int i = 0; i < spawnList.size(); i++)
                {
                    SpawnListEntry spawnlistentry = (SpawnListEntry)spawnList.get(i);
                    // determine mod
                    EntityData entityData = environment.classToEntityMapping.get(spawnlistentry.entityClass);
                    if (entityData == null)
                    {
                        // generate entity data for CMS
                        entityData = environment.registerEntity(spawnlistentry.entityClass);
                    }

                    if ((!entityData.getCanSpawn() || entityData.getFrequency() <= 0 || entityData.getMaxSpawn() <= 0 || entityData.getMaxInChunk() <= 0))
                    {
                        spawnList.remove(i);
                    }
                    else
                    {
                        String tag = entityData.getEntityMod().getModTag();
                        String entityName = (tag + "|" + entityData.getEntityName());
                        CMSConfigCategory entityCategory = new CMSConfigCategory(entityName, spawnEntryCat);
                        entityCategory.put("frequency", new CMSProperty("frequency", Integer.toString(spawnlistentry.itemWeight), CMSProperty.Type.INTEGER));
                        entityCategory.put("minSpawn", new CMSProperty("minSpawn", Integer.toString(spawnlistentry.minGroupCount), CMSProperty.Type.INTEGER));
                        entityCategory.put("maxSpawn", new CMSProperty("maxSpawn", Integer.toString(spawnlistentry.maxGroupCount), CMSProperty.Type.INTEGER));
                        if (environment.debug) environment.envLog.logger.info("Adding default spawnentry " + entityData.getEntityName() + " to STRUCTURE " + structCategoryName + " with frequency " + spawnlistentry.itemWeight + ", minSpawn " + spawnlistentry.minGroupCount + ", maxSpawn " + spawnlistentry.maxGroupCount);
                        spawnListProp.valueList.add(entityName);
                    }
                }
                environment.CMSStructureConfig.save();
              }
            else
            {
                // read config
                CMSConfigCategory spawnListCat = environment.CMSStructureConfig.getCategory("spawnlist");
                CMSConfigCategory structCat = environment.CMSStructureConfig.getCategory(structCategoryName);
                environment.initializeEntities();
                //spawnList.clear();
                CMSProperty spawnListProperty = spawnListCat.get(structCategoryName);

                if (spawnListProperty != null)
                {
                    CMSConfigCategory spawnEntries = environment.CMSStructureConfig.getCategory(structCategoryName + ".spawnentries");
                    if (spawnListProperty.valueList.size() == 0) // remove all entries
                    {
                        for (CMSConfigCategory cat : spawnEntries.getChildren())
                        {
                            spawnEntries.removeChild(cat);
                        }
                    }
                    else
                    {
                        Iterator<String> iterator = spawnEntries.keySet().iterator();
                        while (iterator.hasNext())
                        {
                            if (!spawnListProperty.valueList.contains(iterator.next()))
                            {
                                if (environment.debug) environment.envLog.logger.info("SpawnList " + structCategoryName + " does NOT contain spawn entry " + iterator.next() + ", REMOVING!!");
                                iterator.remove();
                            }
                        }
                    }
                    for (int i = 0; i < spawnListProperty.valueList.size(); i++)
                    {
                        EntityData entityData = environment.entityMap.get(spawnListProperty.valueList.get(i));
                        if (entityData != null)
                        {
                            CMSConfigCategory spawnEntryCat = null;
                            int frequency = entityData.getFrequency();
                            int minSpawn = entityData.getMinSpawn();
                            int maxSpawn = entityData.getMaxSpawn();
                            if (!environment.CMSStructureConfig.hasCategory(structCategoryName + ".spawnentries." + (spawnListProperty.valueList.get(i))))
                            {
                                // check if spawnlist already contains entity and if so, use the settings
                                SpawnListEntry spawnListEntry = CMSUtils.getSpawnListEntry(entityData.getEntityClass(), spawnList);
                                if (spawnListEntry != null)
                                {
                                    frequency = spawnListEntry.itemWeight;
                                    minSpawn = spawnListEntry.minGroupCount;
                                    maxSpawn = spawnListEntry.maxGroupCount;
                                }
                                spawnEntryCat = environment.CMSStructureConfig.getCategory(structCategoryName + ".spawnentries." + (spawnListProperty.valueList.get(i)));
                                spawnEntryCat.put("frequency", new CMSProperty("frequency", Integer.toString(frequency), CMSProperty.Type.INTEGER));
                                spawnEntryCat.put("minSpawn", new CMSProperty("minSpawn", Integer.toString(minSpawn), CMSProperty.Type.INTEGER));
                                spawnEntryCat.put("maxSpawn", new CMSProperty("maxSpawn", Integer.toString(maxSpawn), CMSProperty.Type.INTEGER));
                            }
                            else
                            {
                                spawnEntryCat = environment.CMSStructureConfig.getCategory(structCategoryName + ".spawnentries." + (spawnListProperty.valueList.get(i)));
                                // read data in config
                                for (Map.Entry<String, CMSProperty> entityEntry : spawnEntryCat.entrySet())
                                {
                                    if (entityEntry.getKey().equals("frequency"))
                                    {
                                        frequency = Integer.parseInt(entityEntry.getValue().value);
                                    }
                                    else if (entityEntry.getKey().equals("minSpawn"))
                                    {
                                        minSpawn = Integer.parseInt(entityEntry.getValue().value);
                                    }
                                    if (entityEntry.getKey().equals("maxSpawn"))
                                    {
                                        maxSpawn = Integer.parseInt(entityEntry.getValue().value);
                                    }
                                }
                            }

                            SpawnListEntry spawnListEntry = new SpawnListEntry(entityData.getEntityClass(), frequency, minSpawn, maxSpawn);
                            if (CMSUtils.getSpawnListEntry(entityData.getEntityClass(), spawnList) == null) // dont add dupes
                            {
                                if (environment.debug) environment.envLog.logger.info("Adding spawnentry " + entityData.getEntityName() + " to STRUCTURE " + structCategoryName + " with frequency " + frequency + ", minSpawn " + minSpawn + ", maxSpawn " + maxSpawn);
                                spawnList.add(new SpawnListEntry(entityData.getEntityClass(), frequency, minSpawn, maxSpawn));
                            }
                            else // update values
                            {
                                if (environment.debug) environment.envLog.logger.info("Updating existing entity in " + structCategoryName + " with settings " + spawnListEntry.itemWeight + ":" + spawnListEntry.minGroupCount + ":" + spawnListEntry.maxGroupCount);
                                SpawnListEntry existingSpawnEntry = CMSUtils.getSpawnListEntry(entityData.getEntityClass(), spawnList);
                                existingSpawnEntry.itemWeight = spawnListEntry.itemWeight;
                                existingSpawnEntry.minGroupCount = spawnListEntry.minGroupCount;
                                existingSpawnEntry.maxGroupCount = spawnListEntry.maxGroupCount;
                            }
                        }
                    }
                }
                environment.CMSStructureConfig.save();
            }
        }
    }
}