package drzhark.customspawner.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.biomes.BiomeGroupData;
import drzhark.customspawner.configuration.CMSConfigCategory;
import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.customspawner.configuration.CMSProperty;
import drzhark.customspawner.configuration.CMSProperty.Type;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.entity.EntityModData;
import drzhark.customspawner.environment.EnvironmentSettings;
import drzhark.customspawner.type.EntitySpawnType;
import drzhark.customspawner.utils.CMSUtils;

public class CommandCMS extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();
    private static List<String> commandsCustomSpawner = new ArrayList<String>();
    private static Map<String, String> commmentMap = new TreeMap<String, String>();
    private static List tabCompletionStrings = new ArrayList<String>();

    static {
        commands.add("/cms addbg <tag|entity> <group>");
        commands.add("/cms bg");
        commands.add("/cms bg <tag|entity>");
        commands.add("/cms canspawn <tag|entity> <boolean>");
        commands.add("/cms chunkspawning <boolean>");
        commands.add("/cms countentities");
        commands.add("/cms countentities chunk");
        commands.add("/cms debug <boolean>");
        commands.add("/cms despawnlightlevel <boolean>");
        commands.add("/cms despawntickrate <int>");
        commands.add("/cms enforcemaxspawnlimits <boolean>");
        commands.add("/cms entityspawnrange <int>");
        commands.add("/cms forcedespawns <boolean>");
        commands.add("/cms frequency <tag|name> <int>");
        commands.add("/cms killall");
        commands.add("/cms killall <tag|entity>");
        commands.add("/cms killall force");
        commands.add("/cms killall tamed <playername>");
        commands.add("/cms lightlevel <int>");
        commands.add("/cms min <tag|entity> <int>");
        commands.add("/cms max <tag|entity> <int>");
        commands.add("/cms removebg <tag|entity> <group>");
        commands.add("/cms spawncap <livingtype> <int>");
        commands.add("/cms spawntickrate <livingtype> <int>");
        commands.add("/cms tag");
        aliases.add("cms");
        tabCompletionStrings.add("addbg");
        tabCompletionStrings.add("bg");
        tabCompletionStrings.add("canspawn");
        tabCompletionStrings.add("chunkspawning");
        tabCompletionStrings.add("countentities");
        tabCompletionStrings.add("debug");
        tabCompletionStrings.add("despawnlightlevel");
        tabCompletionStrings.add("despawntickrate");
        tabCompletionStrings.add("enforcemaxspawnlimits");
        tabCompletionStrings.add("entityspawnrange");
        tabCompletionStrings.add("forcedespawns");
        tabCompletionStrings.add("killall");
        tabCompletionStrings.add("lightlevel");
        tabCompletionStrings.add("min");
        tabCompletionStrings.add("max");
        tabCompletionStrings.add("removebg");
        tabCompletionStrings.add("spawncap");
        tabCompletionStrings.add("spawntickrate");
        tabCompletionStrings.add("tag");
    }

    public String getCommandName()
    {
        return "customspawner";
    }

    public List getCommandAliases()
    {
        return aliases;
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.customspawner.usage";
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return getListOfStringsMatchingLastWord(par2ArrayOfStr, (String[])tabCompletionStrings.toArray(new String[tabCompletionStrings.size()]));
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        String par1 = "";
        if (par2ArrayOfStr.length == 0)
        {
            this.sendCommandHelp(par1ICommandSender);
            return;
        }
        else par1 = par2ArrayOfStr[0];
        String par2 = "";
        if (par2ArrayOfStr.length > 1)
            par2 = par2ArrayOfStr[1];
        String par3 = "";
        if (par2ArrayOfStr.length > 2)
        {
            par3 = par2ArrayOfStr[2];
        }

        EnvironmentSettings environment = CMSUtils.getEnvironment(par1ICommandSender.getEntityWorld());
        CMSConfiguration config = environment.CMSEnvironmentConfig;
        boolean saved = false;
        boolean doNotShowHelp = false;

        if (par1.equalsIgnoreCase("addbg") && !par2.equals("") && !par3.equals(""))
        { 
            EntityData entityData = environment.entityMap.get(par2);
            if (entityData != null)
            {
                // check if biomegroup is valid
                BiomeGroupData biomeGroupData = environment.biomeGroupMap.get(par3);
                if (biomeGroupData == null)
                {
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Invalid Biome Group entered. Please enter a valid biome group."));
                    return;
                }
                if (biomeGroupData != null && !entityData.getBiomeGroups().contains(biomeGroupData.getBiomeGroupName()))
                {
                    entityData.addBiomeGroup(biomeGroupData.getBiomeGroupName());
                    Collections.sort(entityData.getBiomeGroups());
                    CustomSpawner.INSTANCE.updateSpawnListBiomes(entityData.getEntityClass(), entityData.getLivingSpawnType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getBiomeGroupSpawnMap(biomeGroupData.getBiomeGroupName()));
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Added biomegroup " + EnumChatFormatting.AQUA + biomeGroupData.getBiomeGroupName() + EnumChatFormatting.WHITE + " to Entity " + EnumChatFormatting.GREEN + par2));
                    entityData.getEntityConfig().save();
                }
                else
                {
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Biome Group " + biomeGroupData.getBiomeGroupName() + " already exists!!, please choose another from the following list :"));
                }
                return;
            }
        }
        else if (par1.equalsIgnoreCase("removebg") && !par2.equals("") && !par3.equals(""))
        { 
            EntityData entityData = environment.entityMap.get(par2);
            if (entityData != null)
            {
                // check if biomegroup is valid
                BiomeGroupData biomeGroupData = environment.biomeGroupMap.get(par3);
                if (biomeGroupData == null)
                {
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Invalid Biome Group entered. Please enter a valid biome group."));
                    return;
                }
                if (biomeGroupData != null && entityData.getBiomeGroups().contains(biomeGroupData.getBiomeGroupName()))
                {
                    entityData.removeBiomeGroup(biomeGroupData.getBiomeGroupName());
                    CustomSpawner.INSTANCE.updateSpawnListBiomes(entityData.getEntityClass(), entityData.getLivingSpawnType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getBiomeGroupSpawnMap(biomeGroupData.getBiomeGroupName()));
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Removed biomegroup " + EnumChatFormatting.AQUA + biomeGroupData.getBiomeGroupName() + EnumChatFormatting.WHITE + " from Entity " + EnumChatFormatting.GREEN + par2));
                    entityData.getEntityConfig().save();
                    return;
                }
                else
                {
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Biome Group " + biomeGroupData.getBiomeGroupName() + " already exists!!, please choose another from the following list :"));
                    for (int i = 0; i < biomeGroupData.getBiomeList().size(); i++)
                    {
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation(biomeGroupData.getBiomeList().get(i)));
                    }
                    return;
                }
            }
        }
        else if (par1.equalsIgnoreCase("biomegroups") || par1.equalsIgnoreCase("bg"))
        {  
            String biomeGroups = "";
            par1ICommandSender.addChatMessage(new ChatComponentTranslation("The following biome groups have been found :"));
            for (Map.Entry<String, BiomeGroupData> biomeGroupEntry: environment.biomeGroupMap.entrySet())
            {
                biomeGroups += biomeGroupEntry.getKey() + ", ";
            }
            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.AQUA + biomeGroups));
            return;
        }
        else if ((par1.equalsIgnoreCase("biomegroups") || par1.equalsIgnoreCase("bg")) && !par2.equals(""))// handle entity biomegroup listings
        {
            EntityData entityData = environment.entityMap.get(par2);//modEntry.getValue().getCreature(name);
            if (entityData != null)
            {
                String biomeGroups = "";
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("The following biome groups have been found :"));
                for (String biomeGroup : entityData.getBiomeGroups())
                {
                    biomeGroups += biomeGroup + ", ";
                }
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.AQUA + biomeGroups));
                return;
            }
            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Entity " + EnumChatFormatting.GREEN + par2 + EnumChatFormatting.RED + " is invalid. Please enter a valid entity."));
            return;
        }
        else if (par1.equalsIgnoreCase("countentities"))
        {
            String title;
            int totalCount = 0;
            World world = par1ICommandSender.getEntityWorld();
            Map<String, Integer> countMap = new HashMap<String, Integer>();
            int playerChunkCoordX = par1ICommandSender.getPlayerCoordinates().posX >> 4;
            int playerChunkCoordZ = par1ICommandSender.getPlayerCoordinates().posZ >> 4;
            if (par2.equalsIgnoreCase("chunk"))
            {
                for (EntityData entityData : CMSUtils.getEnvironment(world).classToEntityMapping.values())
                {
                    int count = 0;
                    for (int j = 0; j < world.loadedEntityList.size(); j++)
                    {
                        Entity entity = (Entity) world.loadedEntityList.get(j);
                        if (entity.getClass() == entityData.getEntityClass() && playerChunkCoordX == entity.chunkCoordX && playerChunkCoordZ == entity.chunkCoordZ)
                        {
                            count++;
                        }
                    }
                    if (count != 0)
                        countMap.put(EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE + "|" + EnumChatFormatting.GREEN + entityData.getEntityName(), count);
                }
                title = "Showing total entities in chunk " + EnumChatFormatting.AQUA + playerChunkCoordX + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.AQUA + playerChunkCoordZ + EnumChatFormatting.WHITE + " ";
            }
            else
            {
                for (EntityData entityData : CMSUtils.getEnvironment(world).classToEntityMapping.values())
                {
                    int count = 0;
                    for (int i = 0; i < world.loadedEntityList.size(); i++)
                    {
                        Entity entity = (Entity) world.loadedEntityList.get(i);
                        if (entity instanceof EntityPlayer || (entity.getClass() != entityData.getEntityClass())) continue;
                        count++;
                        totalCount++;
                    }
                    if (count != 0)
                    {
                        countMap.put(EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE + "|" + EnumChatFormatting.GREEN + entityData.getEntityName(), count);
                    }
                }
                title = "Showing total entities in dimension " + world.provider.dimensionId;
            }

            Map<String, Integer> sortedMap = CMSUtils.sortByComparator(countMap, false); // sort desc
            ArrayList<String> countList = new ArrayList<String>();
            if (countMap.size() > 0)
            {
                for (Map.Entry<String, Integer> entityEntry : sortedMap.entrySet())
                {
                    countList.add(EnumChatFormatting.WHITE + " " + EnumChatFormatting.AQUA + entityEntry.getValue() + " " + entityEntry.getKey());
                    //par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.WHITE + " " + EnumChatFormatting.AQUA + entityEntry.getKey() + " " + entityEntry.getValue() + EnumChatFormatting.WHITE + "."));
                }
                sendPageHelp(par1ICommandSender, (byte)10, countList, par2ArrayOfStr, title);
                if (!par2.equalsIgnoreCase("chunk"))
                {
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.WHITE + "Total entities " + EnumChatFormatting.AQUA + totalCount));
                }
            }
            else if (par2.equalsIgnoreCase("chunk"))
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.WHITE + "No entities found in chunk " + EnumChatFormatting.AQUA + playerChunkCoordX + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.AQUA + playerChunkCoordZ + EnumChatFormatting.WHITE + ".")); 
            }
            else
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.WHITE + "No entities found in world " + world.getWorldInfo().getWorldName() + " in dimension " + world.provider.dimensionId + "."));
            }
            return;
        }
        else if (par1.equalsIgnoreCase("killall"))
        {
            if ((!environment.entityMap.containsKey(par2) && par2ArrayOfStr.length == 2 && !par2.equalsIgnoreCase("force")) || par2ArrayOfStr.length == 1)
            {
                String list = "";
                List<String> entityTypes = new ArrayList();
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("Must specify a valid entity type to kill. Current types are : "));
                for (Map.Entry<String, EntityData> entityEntry : environment.entityMap.entrySet())
                {
                    EntityData entityData = entityEntry.getValue();
                    entityTypes.add(EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE + "|" + EnumChatFormatting.GREEN + entityData.getEntityName());
                }
                Collections.sort(entityTypes);
                for (int i = 0; i < entityTypes.size(); i++)
                {
                    if (i == entityTypes.size() - 1)
                        list += entityTypes.get(i) + ".";
                    else list += entityTypes.get(i) + ", ";
                }
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(list));
                return;
            }
            else if (par2.contains("|")) // tagged entity
            {
                // get entity type
                EntityData entityData = environment.entityMap.get(par2);
                String playername = par1ICommandSender.getCommandSenderName();
                int count = 0;
                for (int dimension : DimensionManager.getIDs())
                {
                    WorldServer world = DimensionManager.getWorld(dimension);
                    for (int i = 0; i < world.loadedEntityList.size(); i++)
                    {
                        Entity entity = (Entity) world.loadedEntityList.get(i);
                        if (entityData.getEntityClass().isInstance(entity))
                        {
                            // villagers
                            if ((entity instanceof EntityVillager)) //|| (CustomSpawner.killallUseLightLevel && !CustomSpawner.isValidLightLevel(entity, world, CustomSpawner.lightLevel, CustomSpawner.checkAmbientLightLevel)))
                            {
                                continue;
                            }
                            else if (!CMSUtils.isTamed(entity)) // not tamed
                            {
                                entity.isDead = true;
                                entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                count++;
                            }
                        }
                    }
                }
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + " " + EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE  + "|" + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + "."));
                return;
            }
            else if (par2.equalsIgnoreCase("force")) // kill everything
            {
                int count = 0;
                World world = par1ICommandSender.getEntityWorld();
                for (int i = 0; i < world.loadedEntityList.size(); i++)
                {
                    Entity entity = (Entity) world.loadedEntityList.get(i);
                    if (entity instanceof EntityPlayer) continue;
                    entity.isDead = true;
                    entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                    count++;
                }
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + " entities in world " + EnumChatFormatting.LIGHT_PURPLE + world.getWorldInfo().getWorldName() + EnumChatFormatting.WHITE + "."));
                return;
            }
            else if (par2.equalsIgnoreCase("tamed")) // kill all tamed creatures of owner specified
            {
                if (par2ArrayOfStr.length > 2)
                {
                    String playername =  par2ArrayOfStr[2];
                    int count = 0;
                    for (int dimension : DimensionManager.getIDs())
                    {
                        WorldServer world = DimensionManager.getWorld(dimension);
                        for (int i = 0; i < world.loadedEntityList.size(); i++)
                        {
                            Entity entity = (Entity) world.loadedEntityList.get(i);
                            // check if one of ours
                            if (entity.isCreatureType(EnumCreatureType.creature, false))
                            {
                                NBTTagCompound nbt = new NBTTagCompound();
                                entity.writeToNBT(nbt);
                                if (nbt != null)
                                {
                                    if (nbt.hasKey("Owner") && !nbt.getString("Owner").equals(""))
                                    {
                                        if (nbt.getString("Owner").equalsIgnoreCase(playername));
                                        entity.isDead = true;
                                        entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                        count++;
                                        return; // ignore
                                    }
                                }
                            }
                        }
                    }
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + EnumChatFormatting.LIGHT_PURPLE + " tamed" + EnumChatFormatting.WHITE + " pets with owner " + EnumChatFormatting.GREEN + playername + EnumChatFormatting.WHITE + "."));
                    doNotShowHelp = true;
                }
            }
        }
        // START ENTITY FREQUENCY/BIOME SECTION
        else if (par2ArrayOfStr.length >=2 && (par1.equalsIgnoreCase("frequency") || par1.equalsIgnoreCase("min") || par1.equalsIgnoreCase("max") || par1.equalsIgnoreCase("maxchunk") || par1.equalsIgnoreCase("canspawn") || par1.equalsIgnoreCase("biomegroup") || par1.equalsIgnoreCase("bg")))
        {
            if (environment.entityMap.get(par2) == null)
                return;

            OUTER: for (Map.Entry<String, EntityModData> modEntry : environment.entityModMap.entrySet())
            {
                    EntityData entityData = environment.entityMap.get(par2);//modEntry.getValue().getCreature(name);
                    if (entityData != null)
                    {
                        if (par1.equalsIgnoreCase("frequency"))
                        {
                            if (par3.equals(""))
                            {
                                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency is " + EnumChatFormatting.AQUA + entityData.getFrequency() + EnumChatFormatting.WHITE + "."));
                                return;
                            }
                            else {
                                try 
                                {
                                    entityData.setFrequency(Integer.parseInt(par3));
                                    CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName().toLowerCase(),"frequency");
                                    prop.value = par3;
                                    saved = true;
                                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        else if (par1.equalsIgnoreCase("min"))
                        {
                            if (par3.equals(""))
                            {
                                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMinSpawn() + EnumChatFormatting.WHITE + "."));
                                doNotShowHelp = true;
                            }
                            else {
                                try 
                                {
                                    entityData.setMinSpawn(Integer.parseInt(par3));
                                    CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"min");
                                    prop.value = par3;
                                    saved = true;
                                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        else if (par1.equalsIgnoreCase("max"))
                        {
                            if (par3.equals(""))
                            {
                                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMaxSpawn() + EnumChatFormatting.WHITE + "."));
                                return;
                            }
                            else {
                                try 
                                {
                                    entityData.setMaxSpawn(Integer.parseInt(par3));
                                    CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"max");
                                    prop.value = par3;
                                    saved = true;
                                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        else if (par1.equalsIgnoreCase("maxchunk"))
                        {
                            if (par3.equals(""))
                            {
                                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk is " + EnumChatFormatting.AQUA + entityData.getMaxInChunk() + EnumChatFormatting.WHITE + "."));
                                return;
                            }
                            else {
                                try 
                                {
                                    entityData.setMaxSpawn(Integer.parseInt(par3));
                                    CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"maxchunk");
                                    prop.value = par3;
                                    saved = true;
                                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        else if (par1.equalsIgnoreCase("canspawn"))
                        {
                            if (par3.equals(""))
                            {
                                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " canSpawn is " + EnumChatFormatting.AQUA + entityData.getCanSpawn() + EnumChatFormatting.WHITE + "."));
                                return;
                            }
                            else {
                                try
                                {
                                  entityData.setCanSpawn(Boolean.parseBoolean(par3));
                                  CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"canSpawn");
                                  prop.set(par3);
                                  saved = true;
                                  par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " canSpawn to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
                                }
                                catch (NumberFormatException ex)
                                {
                                  sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        if (saved == true)
                        {
                           CustomSpawner.INSTANCE.updateSpawnListEntry(entityData);
                           entityData.getEntityConfig().save();
                           return;
                        }
                        break OUTER;
                    }
            }
        }
        else if (par1.equalsIgnoreCase("spawntickrate") && par2ArrayOfStr.length <= 3)
        {
            config = environment.CMSLivingSpawnTypeConfig;
            config.load();
            if (par2.equals(""))
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("Please specify a valid LivingSpawnType from the following list :"));
                for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values())
                {
                    if (entitySpawnType.name().equals("UNDEFINED")) continue;
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entitySpawnType.name()));
                }
                return;
            }
            EntitySpawnType entitySpawnType = environment.entitySpawnTypes.get(par2.toUpperCase());
            if (entitySpawnType == null)
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "The LivingSpawnType " + par2 + " is not valid."));
                return;
            }
            if (par3.equals(""))
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entitySpawnType.name() + EnumChatFormatting.WHITE + " spawnTickRate is " + EnumChatFormatting.AQUA + entitySpawnType.getSpawnTickRate() + EnumChatFormatting.WHITE + "."));
                return;
            }
            CMSConfigCategory typeCat = config.getCategory(par2.toLowerCase());
            CMSProperty prop = typeCat.get(par1.toLowerCase());
            if (prop != null)
            {
                prop.value = par3;
            }
            entitySpawnType.setSpawnTickRate(Integer.parseInt(par3));
            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + entitySpawnType.name() + EnumChatFormatting.WHITE + " spawnTickRate to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
            config.save();
            environment.readConfigValues();
            return;
        }
        else if (par1.equalsIgnoreCase("spawncap") && par2ArrayOfStr.length <= 3)
        {
            config = environment.CMSLivingSpawnTypeConfig;
            if (par2.equals(""))
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("Please specify a valid LivingSpawnType from the following list :"));
                for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values())
                {
                    if (entitySpawnType.name().equals("UNDEFINED")) continue;
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entitySpawnType.name()));
                }
                return;
            }
            EntitySpawnType entitySpawnType = environment.entitySpawnTypes.get(par2.toUpperCase());
            if (entitySpawnType == null)
            {
                return;
            }
            if (par3.equals(""))
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entitySpawnType.name() + EnumChatFormatting.WHITE + " spawnCap is " + EnumChatFormatting.AQUA + entitySpawnType.getSpawnCap() + EnumChatFormatting.WHITE + "."));
                return;
            }
            CMSConfigCategory typeCat = config.getCategory(par2.toLowerCase());
            CMSProperty prop = typeCat.get(par1.toLowerCase());
            if (prop != null)
            {
                prop.value = par3;
            }
            entitySpawnType.setSpawnCap(Integer.parseInt(par3));
            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + entitySpawnType.name() + EnumChatFormatting.WHITE + " spawnCap to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
            config.save();
            environment.readConfigValues();
            return;
        }
        else if (par1.equalsIgnoreCase("tag") || par1.equalsIgnoreCase("tags"))
        {
            for (Map.Entry<String, EntityModData> modEntry : environment.entityModMap.entrySet())
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + modEntry.getKey() + EnumChatFormatting.WHITE + " uses tag " + EnumChatFormatting.LIGHT_PURPLE + modEntry.getValue().getModTag()));
            }

            return;
        }
        else if (par2ArrayOfStr.length == 1){
            for (Map.Entry<String, CMSConfigCategory> catEntry : config.categories.entrySet())
            {
                String catName = catEntry.getValue().getQualifiedName();

                for (Map.Entry<String, CMSProperty> propEntry : catEntry.getValue().entrySet())
                {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(par1))
                        continue;
                    CMSProperty property = propEntry.getValue();
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().value;
                    if (propList == null && propValue == null)
                        continue;
                    if (par2.equals(""))
                    {
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + propEntry.getKey() + EnumChatFormatting.WHITE + " is " + EnumChatFormatting.AQUA + propValue));
                        return;
                    }
                }
            }
        }
        // END ENTITY FREQUENCY/BIOME SECTION
        // START OTHER SECTIONS
        else 
        {
            for (Map.Entry<String, CMSConfigCategory> catEntry : config.categories.entrySet())
            {
                String catName = catEntry.getValue().getQualifiedName();

                for (Map.Entry<String, CMSProperty> propEntry : catEntry.getValue().entrySet())
                {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(par1))
                        continue;
                    CMSProperty property = propEntry.getValue();
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().value;

                    if (propList == null && propValue == null)
                        continue;

                    if (propEntry.getValue().getType() == Type.BOOLEAN)
                    {
                        if (par2.equalsIgnoreCase("true") || par2.equalsIgnoreCase("false"))
                        {
                            property.set(par2);
                            saved = true;
                            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + "."));
                        }
                    }
                    else if (propEntry.getValue().getType() == Type.INTEGER)
                    {
                        try {
                            Integer.parseInt(par2);
                            property.set(par2);
                            saved = true;
                            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + "."));
                        }
                        catch (NumberFormatException ex)
                        {
                            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Invalid value entered. Please enter a valid number."));
                        }
                        
                    }
                    else if (propEntry.getValue().getType() == Type.DOUBLE)
                    {
                        try {
                            Double.parseDouble(par2);
                            property.set(par2);
                            saved = true;
                            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + "."));
                        }
                        catch (NumberFormatException ex)
                        {
                            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Invalid value entered. Please enter a valid number."));
                        }
                    }
                }
            }
        }
        // START HELP COMMAND
        if (par1.equalsIgnoreCase("help"))
        {
            doNotShowHelp = true;
            List<String> list = this.getSortedPossibleCommands(par1ICommandSender);
            byte b0 = 10;
            int i = (list.size() - 1) / b0;
            boolean flag = false;
            ICommand icommand;
            int j = 0;

            if (par2ArrayOfStr.length > 1)
            {
                try
                {
                    j = par2ArrayOfStr.length == 0 ? 0 : parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 1, i + 1) - 1;
                }
                catch (NumberInvalidException numberinvalidexception)
                {
                    if (par2 != null)
                    {
                        throw new WrongUsageException(par2, new Object[0]);
                    }
    
                    throw new CommandNotFoundException();
                }
            }

            int k = Math.min((j + 1) * b0, list.size());
            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.DARK_GREEN + "--- Showing CustomSpawner help page " + Integer.valueOf(j + 1) + " of " + Integer.valueOf(i + 1) + "(/moc help <page>)---"));

            for (int l = j * b0; l < k; ++l)
            {
                String command = list.get(l);
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(command));
            }
        }
        // END HELP COMMAND
        if (saved)
        {
            // TODO: update only what is needed instead of everything
            config.save();
            environment.readConfigValues();
        }
        else this.sendCommandHelp(par1ICommandSender);
    }

    /**
     * Returns a sorted list of all possible commands for the given ICommandSender.
     */
    protected List getSortedPossibleCommands(ICommandSender par1ICommandSender)
    {
        Collections.sort(this.commands);
        return this.commands;
    }

    public void sendCommandHelp(ICommandSender sender)
    {
        sender.addChatMessage(new ChatComponentTranslation("\u00a72Listing CustomSpawner commands"));
        for (int i = 0; i < commands.size(); i++)
        {
            sender.addChatMessage(new ChatComponentTranslation(commands.get(i)));
        }
    }

    public void sendPageHelp(ICommandSender sender, byte pagelimit, ArrayList<String> list, String[] par2ArrayOfStr, String title)
    {
        int x = (list.size() - 1) / pagelimit;
        boolean flag = false;
        int j = 0;
        String par1 = "";
        if (par2ArrayOfStr.length > 1)
            par1 = par2ArrayOfStr[0];
        String par2 = "";
        if (par2ArrayOfStr.length > 1)
            par2 = par2ArrayOfStr[1];

        if (Character.isDigit(par2ArrayOfStr[par2ArrayOfStr.length - 1].charAt(0)))
        {
            try
            {
                j = par2ArrayOfStr.length == 0 ? 0 : parseIntBounded(sender, par2ArrayOfStr[par2ArrayOfStr.length - 1], 1, x + 1) - 1;
            }
            catch (NumberInvalidException numberinvalidexception)
            {
                if (par2 != null)
                {
                    throw new WrongUsageException(par2, new Object[0]);
                }

                throw new CommandNotFoundException();
            }
        }
        int k = Math.min((j + 1) * pagelimit, list.size());

        sender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.WHITE + title + " (pg " + EnumChatFormatting.WHITE + Integer.valueOf(j + 1) + EnumChatFormatting.DARK_GREEN + "/" + EnumChatFormatting.WHITE + Integer.valueOf(x + 1) + ")"));

        for (int l = j * pagelimit; l < k; ++l)
        {
            String tamedInfo = list.get(l);
            sender.addChatMessage(new ChatComponentTranslation(tamedInfo));
        }
    }
}