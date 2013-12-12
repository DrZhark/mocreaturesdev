package drzhark.customspawner.command;

import java.util.ArrayList;
import java.util.Collections;
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
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import drzhark.customspawner.CustomSpawner;
import drzhark.customspawner.EntitySpawnType;
import drzhark.customspawner.biomes.BiomeGroupData;
import drzhark.customspawner.configuration.CMSConfigCategory;
import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.customspawner.configuration.CMSProperty;
import drzhark.customspawner.configuration.CMSProperty.Type;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.entity.EntityModData;

public class CommandCMS extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();
    private static List<String> commandsCustomSpawner = new ArrayList<String>();
    private static Map<String, String> commmentMap = new TreeMap<String, String>();
    private static List tabCompletionStrings = new ArrayList<String>();

    static {
        commands.add("/cms spawntickrate <livingtype> <int>");
        commands.add("/cms checkambientlightlevel <boolean>");
        commands.add("/cms debug <boolean>");
        commands.add("/cms despawnlightlevel <boolean>");
        commands.add("/cms despawnvanilla <boolean>");
        commands.add("/cms despawntickrate <int>");
        commands.add("/cms enforcemaxspawnlimits <boolean>");
        commands.add("/cms entityspawnrange <int>");
        commands.add("/cms killall");
        commands.add("/cms killall <tag|entity>");
        commands.add("/cms killall tamed <playername>");
        commands.add("/cms lightlevel <int>");
        commands.add("/cms biomegroups");
        commands.add("/cms <tag> <entity> biomegroups");
        commands.add("/cms tags");
        //commands.add("/cms modifyvanillaspawns <boolean>");
        commands.add("/cms tag");
        commands.add("/cms <tag> <entity> biomegroup add <group>");
        commands.add("/cms <tag> <entity> biomegroup remove <group>");
        commands.add("/cms frequency <tag|name> <int>");
        commands.add("/cms <tag> <entity> min <int>");
        commands.add("/cms <tag> <entity> max <int>");
        commands.add("/cms <tag> <entity> canspawn <boolean>");
        commands.add("/cms worldgencreaturespawning <boolean>");
        aliases.add("cms");
        tabCompletionStrings.add("biomegroups");
        tabCompletionStrings.add("debug");
        tabCompletionStrings.add("despawnlightlevel");
        tabCompletionStrings.add("despawntickrate");
        tabCompletionStrings.add("despawnvanilla");
        tabCompletionStrings.add("enforcemaxspawnlimits");
        tabCompletionStrings.add("entityspawnrange");
        tabCompletionStrings.add("killall");
        tabCompletionStrings.add("lightlevel");
        tabCompletionStrings.add("spawncap");
        tabCompletionStrings.add("spawntickrate");
        tabCompletionStrings.add("tags");
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
            par1 = "help";
        else par1 = par2ArrayOfStr[0];
        String par2 = "";
        if (par2ArrayOfStr.length > 1)
            par2 = par2ArrayOfStr[1];
        CMSConfiguration config = CustomSpawner.CMSGlobalConfig;
        boolean saved = false;
        boolean doNotShowHelp = false;
        System.out.println("par1 = " + par1 + ", par2 = " +par2);
        System.out.println("size = " + par2ArrayOfStr.length);
        if (par2ArrayOfStr.length >= 2)
        {
            OUTER: if (par2ArrayOfStr.length <= 5)
            {
                String par3 = "";
                if (par2ArrayOfStr.length >= 3)
                {
                    par3 = par2ArrayOfStr[2];
                }
                if (par2.equalsIgnoreCase("biomegroups") || par2.equalsIgnoreCase("bg"))
                {
                    for (Map.Entry<String, BiomeGroupData> biomeGroupEntry: CustomSpawner.biomeGroupMap.entrySet())
                    {
                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.AQUA + biomeGroupEntry.getKey()));
                    }
                    return;
                }
                else if (par2ArrayOfStr.length == 4)// handle entity biomegroup listings
                {
                    String tag = par2ArrayOfStr[1];
                    String name = par2ArrayOfStr[2];
                    for (Map.Entry<String, EntityModData> modEntry : CustomSpawner.entityModMap.entrySet())
                    {
                        if (modEntry.getValue().getModTag().equalsIgnoreCase(tag))
                        {
                            EntityData entityData = CustomSpawner.entityMap.get(modEntry.getValue().getModTag() + "|" + name);//modEntry.getValue().getCreature(name);
                            if (entityData != null)
                            {
                                for (int i = 0; i < entityData.getBiomeGroups().size(); i++)
                                {
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(entityData.getBiomeGroups().get(i)));
                                }
                                return;
                            }
                        }
                    }
                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Entity " + EnumChatFormatting.GREEN + par2 + EnumChatFormatting.RED + " is invalid. Please enter a valid entity."));
                    return;
                }
                // END LIST COMMAND
                else if (par1.equalsIgnoreCase("killall"))
                {
                    if (!CustomSpawner.entityMap.containsKey(par2) && par2ArrayOfStr.length == 2)
                    {
                        String list = "";
                        List<String> entityTypes = new ArrayList();
                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Must specify a valid entity type to kill. Current types are : "));
                        for (Map.Entry<String, EntityData> entityEntry : CustomSpawner.entityMap.entrySet())
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
                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(list));
                        return;
                    }
                    else if (par2.contains("|")) // tagged entity
                    {
                        // get entity type
                        EntityData entityData = CustomSpawner.entityMap.get(par2);
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
                                    // check if one of ours
                                    NBTTagCompound nbt = new NBTTagCompound();
                                    entity.writeToNBT(nbt);
                                    if (nbt.hasKey("PetId"))
                                    {
                                        // ignore tamed
                                    }
                                    else if (entity instanceof EntityTameable)
                                    {
                                        EntityTameable tameableEntity = (EntityTameable)entity;
                                        if (!tameableEntity.isTamed())
                                        {
                                            entity.isDead = true;
                                            entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                            count++;
                                        }
                                    }
                                    else {
                                        nbt = new NBTTagCompound();
                                        entity.writeToNBT(nbt);
                                        if (!nbt.hasKey("Owner") || nbt.getString("Owner").equalsIgnoreCase("")) // Support Thaumcraft Golems
                                        {
                                            if ((entity instanceof EntityVillager && !CustomSpawner.killallVillagers) || (CustomSpawner.killallUseLightLevel && !CustomSpawner.isValidLightLevel(entity, world, CustomSpawner.lightLevel, CustomSpawner.checkAmbientLightLevel)))
                                            {
                                                continue;
                                            }
                                            entity.isDead = true;
                                            entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + " " + EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE  + "|" + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + "."));
                        return;
                    }
                }
                // START ENTITY FREQUENCY/BIOME SECTION
                else if (par2ArrayOfStr.length >=2 && (par1.equalsIgnoreCase("frequency") || par1.equalsIgnoreCase("min") || par1.equalsIgnoreCase("max") || par1.equalsIgnoreCase("maxchunk") || par3.equalsIgnoreCase("biomegroup") || par3.equalsIgnoreCase("bg")))
                {
                    System.out.println("1par2 = " + par2);
                    //String tag = par2ArrayOfStr[0];
                    if (CustomSpawner.entityMap.get(par2) == null)
                        return;
                    //String name = par2ArrayOfStr[1];
                    for (Map.Entry<String, EntityModData> modEntry : CustomSpawner.entityModMap.entrySet())
                    {
                            EntityData entityData = CustomSpawner.entityMap.get(par2);//modEntry.getValue().getCreature(name);
                            if (entityData != null)
                            {
                                if (par1.equalsIgnoreCase("frequency"))
                                {
                                    if (par3.equals(""))
                                    {
                                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency is " + EnumChatFormatting.AQUA + entityData.getFrequency() + EnumChatFormatting.WHITE + "."));
                                        return;
                                    }
                                    else {
                                        try 
                                        {
                                            entityData.setFrequency(Integer.parseInt(par3));
                                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"frequency");
                                            prop.value = par3;
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
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
                                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMinSpawn() + EnumChatFormatting.WHITE + "."));
                                        doNotShowHelp = true;
                                    }
                                    else {
                                        try 
                                        {
                                            entityData.setMinSpawn(Integer.parseInt(par3));
                                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"min");
                                            prop.value = par3;
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
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
                                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMaxSpawn() + EnumChatFormatting.WHITE + "."));
                                        return;
                                    }
                                    else {
                                        try 
                                        {
                                            entityData.setMaxSpawn(Integer.parseInt(par3));
                                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"max");
                                            prop.value = par3;
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
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
                                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk is " + EnumChatFormatting.AQUA + entityData.getMaxInChunk() + EnumChatFormatting.WHITE + "."));
                                        return;
                                    }
                                    else {
                                        try 
                                        {
                                            entityData.setMaxSpawn(Integer.parseInt(par3));
                                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"maxchunk");
                                            prop.value = par3;
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
                                        }
                                        catch(NumberFormatException ex)
                                        {
                                            this.sendCommandHelp(par1ICommandSender);
                                        }
                                    }
                                }
                                else if (par3.equalsIgnoreCase("canspawn"))
                                {
                                  if (par3 == null)
                                  {
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " canSpawn is " + EnumChatFormatting.AQUA + entityData.getCanSpawn() + EnumChatFormatting.WHITE + "."));
                                    return;
                                  }
                                  else
                                  {
                                    try
                                    {
                                      entityData.setCanSpawn(Boolean.parseBoolean(par3));
                                      CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(),"canspawn");
                                      prop.value = par3;
                                      saved = true;
                                      par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " canSpawn to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
                                    }
                                    catch (NumberFormatException ex)
                                    {
                                      sendCommandHelp(par1ICommandSender);
                                    }
                                  }
                                }
                                // handle biome groups
                                else if (par1.equalsIgnoreCase("biomegroup") || par1.equalsIgnoreCase("bg"))
                                {
                                    if (par2ArrayOfStr.length != 5)
                                        break OUTER;
                                    String value = par2ArrayOfStr[4].toUpperCase();
                                    try 
                                    {
                                        List<String> biomeGroups = entityData.getBiomeGroups();
                                        if (par2ArrayOfStr[3].equalsIgnoreCase("add"))
                                        {
                                            if (!biomeGroups.contains(value))
                                            {
                                                if (!CustomSpawner.biomeGroupMap.containsKey(value))
                                                {
                                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Invalid Biome Group entered. Please choose a biome group from the following list : "));
                                                    for (Map.Entry<String, BiomeGroupData> biomeGroupEntry : CustomSpawner.biomeGroupMap.entrySet())
                                                    {
                                                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(biomeGroupEntry.getKey()));
                                                    }
                                                    return;
                                                }
                                                biomeGroups.add(value);
                                                Collections.sort(biomeGroups);
                                                saved = true;
                                                // update lists
                                                CustomSpawner.INSTANCE.updateSpawnListBiomes(entityData.getEntityClass(), entityData.getLivingSpawnType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getBiomeGroupSpawnMap(value));
                                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Added biome group " + EnumChatFormatting.GREEN + value + " to entity " + EnumChatFormatting.AQUA + entityData.getEntityName() + EnumChatFormatting.WHITE + "."));
                                                break OUTER;
                                            }
                                            else 
                                            {
                                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Biome Group " + value + " already exists!!, please choose another from the following list :"));
                                                for (int i = 0; i < CustomSpawner.biomeGroupMap.size(); i++)
                                                {
                                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(CustomSpawner.biomeGroupMap.get(i).getBiomeGroupName()));
                                                }
                                            }
                                        }
                                        else if (par3.equalsIgnoreCase("rem") || par3.equalsIgnoreCase("remove"))
                                        {
                                            for (int i = 0; i < biomeGroups.size(); i++)
                                            {
                                                if (value.equals(biomeGroups.get(i)))
                                                {
                                                    biomeGroups.remove(i);
                                                    saved = true;
                                                    // update lists
                                                    CustomSpawner.INSTANCE.updateSpawnListBiomes(entityData.getEntityClass(), entityData.getLivingSpawnType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getBiomeGroupSpawnMap(value));
                                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Removed biome group " + EnumChatFormatting.GREEN + value.toUpperCase() + EnumChatFormatting.WHITE + " from entity " + EnumChatFormatting.AQUA + entityData.getEntityName() + EnumChatFormatting.WHITE + "."));
                                                    break OUTER;
                                                }
                                            }
                                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Invalid biomegroup entered, please choose from the following list :"));
                                            for (int i = 0; i < biomeGroups.size(); i++)
                                            {
                                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(biomeGroups.get(i)));
                                            }
                                            return;
                                        }
                                    }
                                    catch(NumberFormatException ex)
                                    {
                                        this.sendCommandHelp(par1ICommandSender);
                                    }
                                    break OUTER;
                                }
                                if (saved == true && (par1.equalsIgnoreCase("frequency") || par1.equalsIgnoreCase("min") || par1.equalsIgnoreCase("max")))
                                {
                                   CustomSpawner.INSTANCE.updateSpawnListEntry(entityData.getEntityClass(), entityData.getLivingSpawnType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn());
                                   System.out.println("setting config");
                                   config = entityData.getEntityConfig(); // update config
                                }
                                break OUTER;
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
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + "."));
                                }
                            }
                            else if (propEntry.getValue().getType() == Type.INTEGER)
                            {
                                try {
                                    Integer.parseInt(par2);
                                    property.set(par2);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + "."));
                                }
                                catch (NumberFormatException ex)
                                {
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Invalid value entered. Please enter a valid number."));
                                }
                                
                            }
                            else if (propEntry.getValue().getType() == Type.DOUBLE)
                            {
                                try {
                                    Double.parseDouble(par2);
                                    property.set(par2);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + "."));
                                }
                                catch (NumberFormatException ex)
                                {
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Invalid value entered. Please enter a valid number."));
                                }
                            }
                            break OUTER; // exit since we found the property we need to save
                        }
                    }
                }
                // END OTHER SECTIONS
            }
        }
        else if (par1.equalsIgnoreCase("spawntickrate") && par2ArrayOfStr.length <= 3)
        {
            System.out.println("par2 = " + par2);
            if (par2.equals(""))
            {
                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Please specify a valid LivingSpawnType from the following list :"));
                for (EntitySpawnType entitySpawnType : CustomSpawner.entitySpawnTypes.values())
                {
                    if (entitySpawnType == CustomSpawner.LIVINGTYPE_UNDEFINED) continue;
                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entitySpawnType.getLivingSpawnTypeName()));
                }
                return;
            }
            EntitySpawnType entitySpawnType = CustomSpawner.entitySpawnTypes.get(par2.toUpperCase());
            if (entitySpawnType == null)
            {
                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "The LivingSpawnType " + par2 + " is not valid."));
                return;
            }
            String par3 = par2ArrayOfStr[2];
            entitySpawnType.setSpawnTickRate(Integer.parseInt(par3));
            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entitySpawnType.getLivingSpawnTypeName() + EnumChatFormatting.WHITE + " spawnTickRate to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
            return;
        }
        else if (par1.equalsIgnoreCase("spawncap") && par2ArrayOfStr.length == 3)
        {
            EntitySpawnType entitySpawnType = CustomSpawner.entitySpawnTypes.get(par2.toUpperCase());
            if (entitySpawnType == null)
            {
                return;
            }
            String par3 = par2ArrayOfStr[2];
            entitySpawnType.setSpawnCap(Integer.parseInt(par3));
            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entitySpawnType.getLivingSpawnTypeName() + EnumChatFormatting.WHITE + " spawnCap to " + EnumChatFormatting.AQUA + par3 + EnumChatFormatting.WHITE + "."));
            return;
        }
        else if (par1.equalsIgnoreCase("tag") || par1.equalsIgnoreCase("tags"))
        {
            for (Map.Entry<String, EntityModData> modEntry : CustomSpawner.entityModMap.entrySet())
            {
                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + modEntry.getKey() + EnumChatFormatting.WHITE + " uses tag " + EnumChatFormatting.LIGHT_PURPLE + modEntry.getValue().getModTag()));
            }

            return;
        }
        else if (par1.equalsIgnoreCase("biomegroups") || par1.equalsIgnoreCase("bg"))
        {  
            String biomeGroups = "";
            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("The following biome groups have been found :"));
            for (Map.Entry<String, BiomeGroupData> biomeGroupEntry: CustomSpawner.biomeGroupMap.entrySet())
            {
                biomeGroups += biomeGroupEntry.getKey() + ", ";
            }
            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.AQUA + biomeGroups));
            return;
        }
        else {
            if (par1.equalsIgnoreCase("killall"))
            {
                if (par1.equalsIgnoreCase("tag") || par1.equalsIgnoreCase("tags"))
                {
                    for (Map.Entry<String, EntityModData> modEntry :  CustomSpawner.entityModMap.entrySet())
                    {
                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + modEntry.getKey() + EnumChatFormatting.WHITE + " uses tag " + EnumChatFormatting.LIGHT_PURPLE + modEntry.getValue().getModTag()));
                    }
                    return;
                }
                else if (par1.equalsIgnoreCase("killall"))
                {
                    if (!par2.equals("") && !CustomSpawner.entityMap.containsKey(par2))
                    {
                        String list = "";
                        List<String> entityTypes = new ArrayList();
                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Must specify a valid entity type to kill. Current types are : "));
                        for (Map.Entry<String, EntityData> entityEntry : CustomSpawner.entityMap.entrySet())
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
                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(list));
                        return;
                    }
                    else {
                        // get entity type
                        EntityData entityData = null;
                        if (!par2.equals(""))
                        {
                            entityData = CustomSpawner.entityMap.get(par2);
                        }
                        String playername = par1ICommandSender.getCommandSenderName();
                        int count = 0;
                        for (int dimension : DimensionManager.getIDs())
                        {
                            WorldServer world = DimensionManager.getWorld(dimension);
                            for (int j = 0; j < world.loadedEntityList.size(); j++)
                            {
                                Entity entity = (Entity) world.loadedEntityList.get(j);
                                if (entity instanceof EntityPlayer)
                                    continue;
                                if (par2.equals("") || entityData != null && entityData.getEntityClass().isInstance(entity))
                                {
                                    // check if one of ours
                                    NBTTagCompound nbt = new NBTTagCompound();
                                    entity.writeToNBT(nbt);
                                    if (nbt.hasKey("PetId"))
                                    {
                                        // ignore tamed
                                    }
                                    else if (entity instanceof EntityTameable)
                                    {
                                        EntityTameable tameableEntity = (EntityTameable)entity;
                                        if (!tameableEntity.isTamed())
                                        {
                                            entity.isDead = true;
                                            entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                            count++;
                                        }
                                    }
                                    else {
                                        nbt = new NBTTagCompound();
                                        entity.writeToNBT(nbt);
                                        if (!nbt.hasKey("Owner") || nbt.getString("Owner").equalsIgnoreCase("")) // Support Thaumcraft Golems
                                        {
                                            if (entity instanceof IBossDisplayData || (entity instanceof EntityVillager && !CustomSpawner.killallVillagers) || (CustomSpawner.killallUseLightLevel && !CustomSpawner.isValidLightLevel(entity, world, CustomSpawner.lightLevel, CustomSpawner.checkAmbientLightLevel)))
                                            {
                                                continue;
                                            }
                                            entity.isDead = true;
                                            entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                        if (entityData != null)
                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + " " + EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE  + "|" + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + "."));
                        else par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + EnumChatFormatting.WHITE + "."));
                        return;
                    }
                }
            }
            else {
                OUTER: for (Map.Entry<String, CMSConfigCategory> catEntry : config.categories.entrySet())
                {
                    String catName = catEntry.getValue().getQualifiedName();
                    if (catName.equalsIgnoreCase("custom-id-settings"))
                        continue;
    
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
                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + propEntry.getKey() + EnumChatFormatting.WHITE + " is " + EnumChatFormatting.AQUA + propValue));
                            return;
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
            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.DARK_GREEN + "--- Showing CustomSpawner help page " + Integer.valueOf(j + 1) + " of " + Integer.valueOf(i + 1) + "(/moc help <page>)---"));

            for (int l = j * b0; l < k; ++l)
            {
                String command = list.get(l);
                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(command));
            }
        }
        // END HELP COMMAND
        if (saved)
        {
            // TODO: update only what is needed instead of everything
            config.save();
            CustomSpawner.readConfigValues();
        }
        else if (!doNotShowHelp)
        {
            this.sendCommandHelp(par1ICommandSender);
        }
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
        sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("\u00a72Listing CustomSpawner commands"));
        for (int i = 0; i < commands.size(); i++)
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(commands.get(i)));
        }
    }

    public void sendPageHelp(ICommandSender sender, byte pagelimit, ArrayList<String> list, String[] par2ArrayOfStr)
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
                j = par2ArrayOfStr.length == 0 ? 0 : parseIntBounded(sender, par2ArrayOfStr.length > 3 ? par2ArrayOfStr[3] : par2ArrayOfStr[2], 1, x + 1) - 1;
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

        sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.DARK_GREEN + "--- Showing CustomSpawner Help Info " + EnumChatFormatting.AQUA + Integer.valueOf(j + 1) + EnumChatFormatting.WHITE + " of " + EnumChatFormatting.AQUA + Integer.valueOf(x + 1) + EnumChatFormatting.GRAY + " (/moc " + par1 + " " + par2 + " <page>)" + EnumChatFormatting.DARK_GREEN + "---"));

        for (int l = j * pagelimit; l < k; ++l)
        {
            String tamedInfo = list.get(l);
            sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(tamedInfo));
        }
    }
}