package drzhark.customspawner.command;

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
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandCMS extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List<String> aliases = new ArrayList<String>();
    private static List<String> tabCompletionStrings = new ArrayList<String>();

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

    @Override
    public String getCommandName() {
        return "customspawner";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.customspawner.usage";
    }

    /**
     * Adds the strings available in this command to the given list of tab
     * completion options.
     */
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return getListOfStringsMatchingLastWord(par2ArrayOfStr, (String[]) tabCompletionStrings.toArray(new String[tabCompletionStrings.size()]));
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String par1 = "";
        if (args.length == 0) {
            this.sendCommandHelp(sender);
            return;
        } else {
            par1 = args[0];
        }
        String par2 = "";
        if (args.length > 1) {
            par2 = args[1];
        }
        String par3 = "";
        if (args.length > 2) {
            par3 = args[2];
        }

        EnvironmentSettings environment = CMSUtils.getEnvironment(sender.getEntityWorld());
        CMSConfiguration config = environment.CMSEnvironmentConfig;
        boolean saved = false;

        if (par1.equalsIgnoreCase("addbg") && !par2.equals("") && !par3.equals("")) {
            EntityData entityData = environment.entityMap.get(par2);
            if (entityData != null) {
                // check if biomegroup is valid
                BiomeGroupData biomeGroupData = environment.biomeGroupMap.get(par3);
                if (biomeGroupData == null) {
                    sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED
                            + "Invalid Biome Group entered. Please enter a valid biome group."));
                    return;
                }
                if (biomeGroupData != null && !entityData.getBiomeGroups().contains(biomeGroupData.getBiomeGroupName())) {
                    entityData.addBiomeGroup(biomeGroupData.getBiomeGroupName());
                    Collections.sort(entityData.getBiomeGroups());
                    CustomSpawner.INSTANCE.updateSpawnListBiomes(entityData.getEntityClass(), entityData.getLivingSpawnType(),
                            entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(),
                            entityData.getBiomeGroupSpawnMap(biomeGroupData.getBiomeGroupName()));
                    sender.addChatMessage(new TextComponentTranslation("Added biomegroup " + TextFormatting.AQUA
                            + biomeGroupData.getBiomeGroupName() + TextFormatting.WHITE + " to Entity " + TextFormatting.GREEN + par2));
                    entityData.getEntityConfig().save();
                } else {
                    sender.addChatMessage(new TextComponentTranslation("Biome Group " + biomeGroupData.getBiomeGroupName()
                            + " already exists!!, please choose another from the following list :"));
                }
                return;
            }
        } else if (par1.equalsIgnoreCase("removebg") && !par2.equals("") && !par3.equals("")) {
            EntityData entityData = environment.entityMap.get(par2);
            if (entityData != null) {
                // check if biomegroup is valid
                BiomeGroupData biomeGroupData = environment.biomeGroupMap.get(par3);
                if (biomeGroupData == null) {
                    sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED
                            + "Invalid Biome Group entered. Please enter a valid biome group."));
                    return;
                }
                if (biomeGroupData != null && entityData.getBiomeGroups().contains(biomeGroupData.getBiomeGroupName())) {
                    entityData.removeBiomeGroup(biomeGroupData.getBiomeGroupName());
                    CustomSpawner.INSTANCE.updateSpawnListBiomes(entityData.getEntityClass(), entityData.getLivingSpawnType(),
                            entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(),
                            entityData.getBiomeGroupSpawnMap(biomeGroupData.getBiomeGroupName()));
                    sender.addChatMessage(new TextComponentTranslation("Removed biomegroup " + TextFormatting.AQUA
                            + biomeGroupData.getBiomeGroupName() + TextFormatting.WHITE + " from Entity " + TextFormatting.GREEN + par2));
                    entityData.getEntityConfig().save();
                    return;
                } else {
                    sender.addChatMessage(new TextComponentTranslation("Biome Group " + biomeGroupData.getBiomeGroupName()
                            + " already exists!!, please choose another from the following list :"));
                    for (int i = 0; i < biomeGroupData.getBiomeList().size(); i++) {
                        sender.addChatMessage(new TextComponentTranslation(biomeGroupData.getBiomeList().get(i)));
                    }
                    return;
                }
            }
        } else if (par1.equalsIgnoreCase("biomegroups") || par1.equalsIgnoreCase("bg")) {
            String biomeGroups = "";
            sender.addChatMessage(new TextComponentTranslation("The following biome groups have been found :"));
            for (Map.Entry<String, BiomeGroupData> biomeGroupEntry : environment.biomeGroupMap.entrySet()) {
                biomeGroups += biomeGroupEntry.getKey() + ", ";
            }
            sender.addChatMessage(new TextComponentTranslation(TextFormatting.AQUA + biomeGroups));
            return;
        } else if ((par1.equalsIgnoreCase("biomegroups") || par1.equalsIgnoreCase("bg")) && !par2.equals(""))// handle entity biomegroup listings
        {
            EntityData entityData = environment.entityMap.get(par2);//modEntry.getValue().getCreature(name);
            if (entityData != null) {
                String biomeGroups = "";
                sender.addChatMessage(new TextComponentTranslation("The following biome groups have been found :"));
                for (String biomeGroup : entityData.getBiomeGroups()) {
                    biomeGroups += biomeGroup + ", ";
                }
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.AQUA + biomeGroups));
                return;
            }
            sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED + "Entity " + TextFormatting.GREEN + par2
                    + TextFormatting.RED + " is invalid. Please enter a valid entity."));
            return;
        } else if (par1.equalsIgnoreCase("countentities")) {
            String title;
            int totalCount = 0;
            World world = sender.getEntityWorld();
            Map<String, Integer> countMap = new HashMap<String, Integer>();
            BlockPos pos = sender.getPosition();

            if (par2.equalsIgnoreCase("chunk")) {
                for (EntityData entityData : CMSUtils.getEnvironment(world).classToEntityMapping.values()) {
                    int count = 0;
                    for (int j = 0; j < world.loadedEntityList.size(); j++) {
                        Entity entity = (Entity) world.loadedEntityList.get(j);
                        if (entity.getClass() == entityData.getEntityClass() && pos.equals(entity.getPosition())) {
                            count++;
                        }
                    }
                    if (count != 0) {
                        countMap.put(TextFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + TextFormatting.WHITE + "|"
                                + TextFormatting.GREEN + entityData.getEntityName(), count);
                    }
                }
                title =
                        "Showing total entities in chunk " + TextFormatting.AQUA + pos.getX() + TextFormatting.WHITE + ", "
                                + TextFormatting.AQUA + pos.getZ() + TextFormatting.WHITE + " ";
            } else if (par2.equalsIgnoreCase("all")) {
                for (Class <? extends Entity > clazz : EntityList.CLASS_TO_NAME.keySet()) {
                    int count = 0;
                    for (int i = 0; i < world.loadedEntityList.size(); i++) {
                        Entity entity = (Entity) world.loadedEntityList.get(i);
                        if (entity instanceof EntityPlayer || !entity.getClass().isAssignableFrom(clazz)) {
                            continue;
                        }
                        count++;
                        totalCount++;
                    }
                    if (count != 0) {
                        countMap.put(TextFormatting.GREEN + clazz.getName(), count);
                    }
                }
                title = "Showing total entities in dimension " + world.provider.getDimensionType().getId();
            } else {
                for (EntityData entityData : CMSUtils.getEnvironment(world).classToEntityMapping.values()) {
                    int count = 0;
                    for (int i = 0; i < world.loadedEntityList.size(); i++) {
                        Entity entity = (Entity) world.loadedEntityList.get(i);
                        if (entity instanceof EntityPlayer || (entity.getClass() != entityData.getEntityClass())) {
                            continue;
                        }
                        count++;
                        totalCount++;
                    }
                    if (count != 0) {
                        countMap.put(TextFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + TextFormatting.WHITE + "|"
                                + TextFormatting.GREEN + entityData.getEntityName(), count);
                    }
                }
                title = "Showing total entities in dimension " + world.provider.getDimensionType().getId();
            }

            Map<String, Integer> sortedMap = CMSUtils.sortByComparator(countMap, false); // sort desc
            ArrayList<String> countList = new ArrayList<String>();
            if (countMap.size() > 0) {
                for (Map.Entry<String, Integer> entityEntry : sortedMap.entrySet()) {
                    countList.add(TextFormatting.WHITE + " " + TextFormatting.AQUA + entityEntry.getValue() + " " + entityEntry.getKey());
                    //par1ICommandSender.addChatMessage(new TextComponentTranslation(TextFormatting.WHITE + " " + TextFormatting.AQUA + entityEntry.getKey() + " " + entityEntry.getValue() + TextFormatting.WHITE + "."));
                }
                sendPageHelp(sender, (byte) 10, countList, args, title);
                if (!par2.equalsIgnoreCase("chunk")) {
                    sender.addChatMessage(new TextComponentTranslation(TextFormatting.WHITE + "Total entities "
                            + TextFormatting.AQUA + totalCount));
                }
            } else if (par2.equalsIgnoreCase("chunk")) {
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.WHITE + "No entities found in chunk "
                        + TextFormatting.AQUA + pos.getX() + TextFormatting.WHITE + ", " + TextFormatting.AQUA + pos.getZ()
                        + TextFormatting.WHITE + "."));
            } else {
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.WHITE + "No entities found in world "
                        + world.getWorldInfo().getWorldName() + " in dimension " + world.provider.getDimensionType().getId() + "."));
            }
            return;
        } else if (par1.equalsIgnoreCase("killall")) {
            if ((!environment.entityMap.containsKey(par2) && args.length == 2 && !par2.equalsIgnoreCase("force"))
                    || args.length == 1) {
                String list = "";
                List<String> entityTypes = new ArrayList<String>();
                sender.addChatMessage(new TextComponentTranslation("Must specify a valid entity type to kill. Current types are : "));
                for (Map.Entry<String, EntityData> entityEntry : environment.entityMap.entrySet()) {
                    EntityData entityData = entityEntry.getValue();
                    entityTypes.add(TextFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + TextFormatting.WHITE + "|"
                            + TextFormatting.GREEN + entityData.getEntityName());
                }
                Collections.sort(entityTypes);
                for (int i = 0; i < entityTypes.size(); i++) {
                    if (i == entityTypes.size() - 1) {
                        list += entityTypes.get(i) + ".";
                    } else {
                        list += entityTypes.get(i) + ", ";
                    }
                }
                sender.addChatMessage(new TextComponentTranslation(list));
                return;
            } else if (par2.contains("|")) // tagged entity
            {
                // get entity type
                EntityData entityData = environment.entityMap.get(par2);
                int count = 0;
                for (int dimension : DimensionManager.getIDs()) {
                    WorldServer world = DimensionManager.getWorld(dimension);
                    for (int i = 0; i < world.loadedEntityList.size(); i++) {
                        Entity entity = (Entity) world.loadedEntityList.get(i);
                        if (entityData.getEntityClass().isInstance(entity)) {
                            // villagers
                            if ((entity instanceof EntityVillager)) //|| (CustomSpawner.killallUseLightLevel && !CustomSpawner.isValidLightLevel(entity, world, CustomSpawner.lightLevel, CustomSpawner.checkAmbientLightLevel)))
                            {
                                continue;
                            } else if (!CMSUtils.isTamed(entity)) // not tamed
                            {
                                entity.isDead = true;
                                entity.worldObj.setEntityState(entity, (byte) 3); // inform the client that the entity is dead
                                count++;
                            }
                        }
                    }
                }
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED + "Killed " + TextFormatting.AQUA + count
                        + " " + TextFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + TextFormatting.WHITE + "|"
                        + TextFormatting.GREEN + entityData.getEntityName() + TextFormatting.WHITE + "."));
                return;
            } else if (par2.equalsIgnoreCase("force")) // kill everything
            {
                int count = 0;
                World world = sender.getEntityWorld();
                for (int i = 0; i < world.loadedEntityList.size(); i++) {
                    Entity entity = (Entity) world.loadedEntityList.get(i);
                    if (entity instanceof EntityPlayer) {
                        continue;
                    }
                    entity.isDead = true;
                    entity.worldObj.setEntityState(entity, (byte) 3); // inform the client that the entity is dead
                    count++;
                }
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED + "Killed " + TextFormatting.AQUA + count
                        + " entities in world " + TextFormatting.LIGHT_PURPLE + world.getWorldInfo().getWorldName() + TextFormatting.WHITE
                        + "."));
                return;
            } else if (par2.equalsIgnoreCase("tamed")) // kill all tamed creatures of owner specified
            {
                if (args.length > 2) {
                    String playername = args[2];
                    int count = 0;
                    for (int dimension : DimensionManager.getIDs()) {
                        WorldServer world = DimensionManager.getWorld(dimension);
                        for (int i = 0; i < world.loadedEntityList.size(); i++) {
                            Entity entity = (Entity) world.loadedEntityList.get(i);
                            // check if one of ours
                            if (entity.isCreatureType(EnumCreatureType.CREATURE, false)) {
                                NBTTagCompound nbt = new NBTTagCompound();
                                entity.writeToNBT(nbt);
                                if (nbt != null) {
                                    if (nbt.hasKey("Owner") && !nbt.getString("Owner").equals("")) {
                                        if (nbt.getString("Owner").equalsIgnoreCase(playername)) {
                                            ;
                                        }
                                        entity.isDead = true;
                                        entity.worldObj.setEntityState(entity, (byte) 3); // inform the client that the entity is dead
                                        count++;
                                        return; // ignore
                                    }
                                }
                            }
                        }
                    }
                    sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED + "Killed " + TextFormatting.AQUA
                            + count + TextFormatting.LIGHT_PURPLE + " tamed" + TextFormatting.WHITE + " pets with owner "
                            + TextFormatting.GREEN + playername + TextFormatting.WHITE + "."));
                }
            }
        }
        // START ENTITY FREQUENCY/BIOME SECTION
        else if (args.length >= 2
                && (par1.equalsIgnoreCase("frequency") || par1.equalsIgnoreCase("min") || par1.equalsIgnoreCase("max")
                        || par1.equalsIgnoreCase("maxchunk") || par1.equalsIgnoreCase("canspawn") || par1.equalsIgnoreCase("biomegroup") || par1
                            .equalsIgnoreCase("bg"))) {
            if (environment.entityMap.get(par2) == null) {
                return;
            }

            EntityData entityData = environment.entityMap.get(par2);
            if (entityData != null) {
                if (par1.equalsIgnoreCase("frequency")) {
                    if (par3.equals("")) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " frequency is " + TextFormatting.AQUA + entityData.getFrequency()
                                + TextFormatting.WHITE + "."));
                        return;
                    } else {
                        try {
                            entityData.setFrequency(Integer.parseInt(par3));
                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName().toLowerCase(), "frequency");
                            prop.value = par3;
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " frequency to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (par1.equalsIgnoreCase("min")) {
                    if (par3.equals("")) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " minGroupSpawn is " + TextFormatting.AQUA + entityData.getMinSpawn()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setMinSpawn(Integer.parseInt(par3));
                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(), "min");
                            prop.value = par3;
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " minGroupSpawn to " + TextFormatting.AQUA
                                    + par3 + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (par1.equalsIgnoreCase("max")) {
                    if (par3.equals("")) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " maxGroupSpawn is " + TextFormatting.AQUA + entityData.getMaxSpawn()
                                + TextFormatting.WHITE + "."));
                        return;
                    } else {
                        try {
                            entityData.setMaxSpawn(Integer.parseInt(par3));
                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(), "max");
                            prop.value = par3;
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " maxGroupSpawn to " + TextFormatting.AQUA
                                    + par3 + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (par1.equalsIgnoreCase("maxchunk")) {
                    if (par3.equals("")) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " maxInChunk is " + TextFormatting.AQUA + entityData.getMaxInChunk()
                                + TextFormatting.WHITE + "."));
                        return;
                    } else {
                        try {
                            entityData.setMaxSpawn(Integer.parseInt(par3));
                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(), "maxchunk");
                            prop.value = par3;
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " maxInChunk to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (par1.equalsIgnoreCase("canspawn")) {
                    if (par3.equals("")) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " canSpawn is " + TextFormatting.AQUA + entityData.getCanSpawn()
                                + TextFormatting.WHITE + "."));
                        return;
                    } else {
                        try {
                            entityData.setCanSpawn(Boolean.parseBoolean(par3));
                            CMSProperty prop = entityData.getEntityConfig().get(entityData.getEntityName(), "canSpawn");
                            prop.set(par3);
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " canSpawn to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            sendCommandHelp(sender);
                        }
                    }
                }
                if (saved == true) {
                    CustomSpawner.INSTANCE.updateSpawnListEntry(entityData);
                    entityData.getEntityConfig().save();
                    return;
                }
            }
        } else if (par1.equalsIgnoreCase("spawntickrate") && args.length <= 3) {
            config = environment.CMSLivingSpawnTypeConfig;
            config.load();
            if (par2.equals("")) {
                sender.addChatMessage(new TextComponentTranslation("Please specify a valid LivingSpawnType from the following list :"));
                for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values()) {
                    if (entitySpawnType.name().equals("UNDEFINED")) {
                        continue;
                    }
                    sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entitySpawnType.name()));
                }
                return;
            }
            EntitySpawnType entitySpawnType = environment.entitySpawnTypes.get(par2.toUpperCase());
            if (entitySpawnType == null) {
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED + "The LivingSpawnType " + par2
                        + " is not valid."));
                return;
            }
            if (par3.equals("")) {
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entitySpawnType.name()
                        + TextFormatting.WHITE + " spawnTickRate is " + TextFormatting.AQUA + entitySpawnType.getSpawnTickRate()
                        + TextFormatting.WHITE + "."));
                return;
            }
            CMSConfigCategory typeCat = config.getCategory(par2.toLowerCase());
            CMSProperty prop = typeCat.get(par1.toLowerCase());
            if (prop != null) {
                prop.value = par3;
            }
            entitySpawnType.setSpawnTickRate(Integer.parseInt(par3));
            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN + entitySpawnType.name()
                    + TextFormatting.WHITE + " spawnTickRate to " + TextFormatting.AQUA + par3 + TextFormatting.WHITE + "."));
            config.save();
            environment.readConfigValues();
            return;
        } else if (par1.equalsIgnoreCase("spawncap") && args.length <= 3) {
            config = environment.CMSLivingSpawnTypeConfig;
            if (par2.equals("")) {
                sender.addChatMessage(new TextComponentTranslation("Please specify a valid LivingSpawnType from the following list :"));
                for (EntitySpawnType entitySpawnType : environment.entitySpawnTypes.values()) {
                    if (entitySpawnType.name().equals("UNDEFINED")) {
                        continue;
                    }
                    sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entitySpawnType.name()));
                }
                return;
            }
            EntitySpawnType entitySpawnType = environment.entitySpawnTypes.get(par2.toUpperCase());
            if (entitySpawnType == null) {
                return;
            }
            if (par3.equals("")) {
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entitySpawnType.name()
                        + TextFormatting.WHITE + " spawnCap is " + TextFormatting.AQUA + entitySpawnType.getSpawnCap()
                        + TextFormatting.WHITE + "."));
                return;
            }
            CMSConfigCategory typeCat = config.getCategory(par2.toLowerCase());
            CMSProperty prop = typeCat.get(par1.toLowerCase());
            if (prop != null) {
                prop.value = par3;
            }
            entitySpawnType.setSpawnCap(Integer.parseInt(par3));
            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN + entitySpawnType.name()
                    + TextFormatting.WHITE + " spawnCap to " + TextFormatting.AQUA + par3 + TextFormatting.WHITE + "."));
            config.save();
            environment.readConfigValues();
            return;
        } else if (par1.equalsIgnoreCase("tag") || par1.equalsIgnoreCase("tags")) {
            for (Map.Entry<String, EntityModData> modEntry : environment.entityModMap.entrySet()) {
                sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + modEntry.getKey()
                        + TextFormatting.WHITE + " uses tag " + TextFormatting.LIGHT_PURPLE + modEntry.getValue().getModTag()));
            }

            return;
        } else if (args.length == 1) {
            for (Map.Entry<String, CMSConfigCategory> catEntry : config.categories.entrySet()) {
                for (Map.Entry<String, CMSProperty> propEntry : catEntry.getValue().entrySet()) {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(par1)) {
                        continue;
                    }
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().value;
                    if (propList == null && propValue == null) {
                        continue;
                    }
                    if (par2.equals("")) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + propEntry.getKey()
                                + TextFormatting.WHITE + " is " + TextFormatting.AQUA + propValue));
                        return;
                    }
                }
            }
        }
        // END ENTITY FREQUENCY/BIOME SECTION
        // START OTHER SECTIONS
        else {
            for (Map.Entry<String, CMSConfigCategory> catEntry : config.categories.entrySet()) {
                for (Map.Entry<String, CMSProperty> propEntry : catEntry.getValue().entrySet()) {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(par1)) {
                        continue;
                    }
                    CMSProperty property = propEntry.getValue();
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().value;

                    if (propList == null && propValue == null) {
                        continue;
                    }

                    if (propEntry.getValue().getType() == Type.BOOLEAN) {
                        if (par2.equalsIgnoreCase("true") || par2.equalsIgnoreCase("false")) {
                            property.set(par2);
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN + propEntry.getKey()
                                    + " to " + TextFormatting.AQUA + par2 + "."));
                        }
                    } else if (propEntry.getValue().getType() == Type.INTEGER) {
                        try {
                            Integer.parseInt(par2);
                            property.set(par2);
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN + propEntry.getKey()
                                    + " to " + TextFormatting.AQUA + par2 + "."));
                        } catch (NumberFormatException ex) {
                            sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED
                                    + "Invalid value entered. Please enter a valid number."));
                        }

                    } else if (propEntry.getValue().getType() == Type.DOUBLE) {
                        try {
                            Double.parseDouble(par2);
                            property.set(par2);
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN + propEntry.getKey()
                                    + " to " + TextFormatting.AQUA + par2 + "."));
                        } catch (NumberFormatException ex) {
                            sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED
                                    + "Invalid value entered. Please enter a valid number."));
                        }
                    }
                }
            }
        }
        // START HELP COMMAND
        if (par1.equalsIgnoreCase("help")) {
            List<String> list = this.getSortedPossibleCommands(sender);
            byte b0 = 10;
            int i = (list.size() - 1) / b0;
            int j = 0;

            if (args.length > 1) {
                try {
                    j = args.length == 0 ? 0 : parseInt(args[1], 1, i + 1) - 1;
                } catch (NumberInvalidException numberinvalidexception) {
                    numberinvalidexception.printStackTrace();
                }
            }

            int k = Math.min((j + 1) * b0, list.size());
            sender.addChatMessage(new TextComponentTranslation(TextFormatting.DARK_GREEN + "--- Showing CustomSpawner help page "
                    + Integer.valueOf(j + 1) + " of " + Integer.valueOf(i + 1) + "(/moc help <page>)---"));

            for (int l = j * b0; l < k; ++l) {
                String command = list.get(l);
                sender.addChatMessage(new TextComponentTranslation(command));
            }
        }
        // END HELP COMMAND
        if (saved) {
            // TODO: update only what is needed instead of everything
            config.save();
            environment.readConfigValues();
        } else {
            this.sendCommandHelp(sender);
        }
    }

    /**
     * Returns a sorted list of all possible commands for the given
     * ICommandSender.
     */
    protected List<String> getSortedPossibleCommands(ICommandSender par1ICommandSender) {
        Collections.sort(CommandCMS.commands);
        return CommandCMS.commands;
    }

    public void sendCommandHelp(ICommandSender sender) {
        sender.addChatMessage(new TextComponentTranslation("\u00a72Listing CustomSpawner commands"));
        for (int i = 0; i < commands.size(); i++) {
            sender.addChatMessage(new TextComponentTranslation(commands.get(i)));
        }
    }

    public void sendPageHelp(ICommandSender sender, byte pagelimit, ArrayList<String> list, String[] par2ArrayOfStr, String title) {
        int x = (list.size() - 1) / pagelimit;
        int j = 0;

        if (Character.isDigit(par2ArrayOfStr[par2ArrayOfStr.length - 1].charAt(0))) {
            try {
                j = par2ArrayOfStr.length == 0 ? 0 : parseInt(par2ArrayOfStr[par2ArrayOfStr.length - 1], 1, x + 1) - 1;
            } catch (NumberInvalidException numberinvalidexception) {
                numberinvalidexception.printStackTrace();
            }
        }
        int k = Math.min((j + 1) * pagelimit, list.size());

        sender.addChatMessage(new TextComponentTranslation(TextFormatting.WHITE + title + " (pg " + TextFormatting.WHITE
                + Integer.valueOf(j + 1) + TextFormatting.DARK_GREEN + "/" + TextFormatting.WHITE + Integer.valueOf(x + 1) + ")"));

        for (int l = j * pagelimit; l < k; ++l) {
            String tamedInfo = list.get(l);
            sender.addChatMessage(new TextComponentTranslation(tamedInfo));
        }
    }

}
