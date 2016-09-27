package drzhark.mocreatures.command;

import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfigCategory;
import drzhark.mocreatures.configuration.MoCConfiguration;
import drzhark.mocreatures.configuration.MoCProperty;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandMoCreatures extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List<String> aliases = new ArrayList<String>();
    private static List<String> tabCompletionStrings = new ArrayList<String>();

    static {
        commands.add("/moc attackdolphins <boolean>");
        commands.add("/moc attackhorses <boolean>");
        commands.add("/moc attackwolves <boolean>");
        commands.add("/moc canspawn <boolean>");
        commands.add("/moc caveogrechance <float>");
        commands.add("/moc caveogrestrength <float>");
        commands.add("/moc debug <boolean>");
        // TODO commands.add("/moc deletepets <playername>");
        commands.add("/moc destroydrops <boolean>");
        commands.add("/moc enablehunters <boolean>");
        commands.add("/moc easybreeding <boolean>");
        commands.add("/moc elephantbulldozer <boolean>");
        commands.add("/moc enableownership <boolean>");
        commands.add("/moc enableresetownerscroll <boolean>");
        commands.add("/moc fireogrechance <int>");
        commands.add("/moc fireogrestrength <float>");
        commands.add("/moc frequency <entity> <int>");
        commands.add("/moc golemdestroyblocks <boolean>");
        commands.add("/moc tamed");
        commands.add("/moc tamed <playername>");
        commands.add("/moc maxchunk <entity> <int>");
        commands.add("/moc maxspawn <entity> <int>");
        commands.add("/moc maxtamedperop <int>");
        commands.add("/moc maxtamedperplayer <int>");
        commands.add("/moc minspawn <entity> <int>");
        commands.add("/moc motherwyverneggdropchance <int>");
        commands.add("/moc ogreattackrange <int>");
        commands.add("/moc ogrestrength <float>");
        commands.add("/moc ostricheggdropchance <int>");
        commands.add("/moc rareitemdropchance <int>");
        commands.add("/moc spawnhorse <int>");
        commands.add("/moc spawnwyvern <int>");
        commands.add("/moc tamedcount <playername>");
        commands.add("/moc tp <petid> <playername>");
        commands.add("/moc <command> value");
        commands.add("/moc wyverneggdropchance <int>");
        commands.add("/moc zebrachance <int>");
        aliases.add("moc");
        tabCompletionStrings.add("attackdolphins");
        tabCompletionStrings.add("attackhorses");
        tabCompletionStrings.add("attackwolves");
        tabCompletionStrings.add("canspawn");
        tabCompletionStrings.add("caveogrechance");
        tabCompletionStrings.add("caveogrestrength");
        tabCompletionStrings.add("debug");
        // TODO tabCompletionStrings.add("deletepets");
        tabCompletionStrings.add("destroydrops");
        tabCompletionStrings.add("easybreeding");
        tabCompletionStrings.add("elephantbulldozer");
        tabCompletionStrings.add("enableownership");
        tabCompletionStrings.add("enableresetownerscroll");
        tabCompletionStrings.add("fireogrechance");
        tabCompletionStrings.add("fireogrestrength");
        tabCompletionStrings.add("forcedespawns");
        tabCompletionStrings.add("frequency");
        tabCompletionStrings.add("golemdestroyblocks");
        tabCompletionStrings.add("tamed");
        tabCompletionStrings.add("maxchunk");
        tabCompletionStrings.add("maxspawn");
        tabCompletionStrings.add("maxtamedperop");
        tabCompletionStrings.add("maxtamedperplayer");
        tabCompletionStrings.add("minspawn");
        tabCompletionStrings.add("motherwyverneggdropchance");
        tabCompletionStrings.add("ogreattackrange");
        tabCompletionStrings.add("ogreattackstrength");
        tabCompletionStrings.add("ostricheggdropchance");
        tabCompletionStrings.add("rareitemdropchance");
        tabCompletionStrings.add("spawnhorse");
        tabCompletionStrings.add("spawnwyvern");
        tabCompletionStrings.add("tamedcount");
        tabCompletionStrings.add("tp");
        tabCompletionStrings.add("wyverneggdropchance");
        tabCompletionStrings.add("zebrachance");
    }

    @Override
    public String getCommandName() {
        return "mocreatures";
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
        return "commands.mocreatures.usage";
    }

    /**
     * Adds the strings available in this command to the given list of tab
     * completion options.
     */
    public List<String> addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return getListOfStringsMatchingLastWord(par2ArrayOfStr, (String[]) tabCompletionStrings.toArray(new String[tabCompletionStrings.size()]));
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        String command = "";
        if (args.length == 0) {
            command = "help";
        } else {
            command = args[0];
        }
        String par2 = "";
        if (args.length > 1) {
            par2 = args[1];
        }
        String par3 = "";
        if (args.length == 3) {
            par3 = args[2];
        }

        MoCConfiguration config = MoCreatures.proxy.mocSettingsConfig;
        boolean saved = false;

        if (command.equalsIgnoreCase("tamed") || command.equalsIgnoreCase("tame")) {
            if (args.length == 2 && !Character.isDigit(args[1].charAt(0))) {
                int unloadedCount = 0;
                int loadedCount = 0;
                ArrayList<Integer> foundIds = new ArrayList<Integer>();
                ArrayList<String> tamedlist = new ArrayList<String>();
                String playername = par2;
                GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(playername);
                if (profile == null) {
                    return;
                }
                // search for tamed entity
                for (int dimension : DimensionManager.getIDs()) {
                    WorldServer world = DimensionManager.getWorld(dimension);
                    for (int j = 0; j < world.loadedEntityList.size(); j++) {
                        Entity entity = (Entity) world.loadedEntityList.get(j);
                        if (IMoCTameable.class.isAssignableFrom(entity.getClass())) {
                            IMoCTameable mocreature = (IMoCTameable) entity;
                            if (mocreature.getOwnerId().equals(profile.getId())) {
                                loadedCount++;
                                foundIds.add(mocreature.getOwnerPetId());
                                tamedlist.add(TextFormatting.WHITE + "Found pet with " + TextFormatting.DARK_AQUA + "Type"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                        + ((EntityLiving) mocreature).getName() + TextFormatting.DARK_AQUA + ", Name"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN + mocreature.getPetName()
                                        + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                        + profile.getName() + TextFormatting.DARK_AQUA + ", PetId" + TextFormatting.WHITE + ":"
                                        + TextFormatting.GREEN + mocreature.getOwnerPetId() + TextFormatting.DARK_AQUA + ", Dimension"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN + entity.dimension + TextFormatting.DARK_AQUA
                                        + ", Pos" + TextFormatting.WHITE + ":" + TextFormatting.LIGHT_PURPLE + Math.round(entity.posX)
                                        + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(entity.posY)
                                        + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(entity.posZ));
                            }
                        }
                    }
                }
                MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(profile.getId());
                if (ownerPetData != null) {
                    for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++) {
                        NBTTagCompound nbt = ownerPetData.getTamedList().getCompoundTagAt(i);
                        if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId"))) {
                            unloadedCount++;
                            double posX = nbt.getTagList("Pos", 6).getDoubleAt(0);
                            double posY = nbt.getTagList("Pos", 6).getDoubleAt(1);
                            double posZ = nbt.getTagList("Pos", 6).getDoubleAt(2);
                            tamedlist.add(TextFormatting.WHITE + "Found unloaded pet with " + TextFormatting.DARK_AQUA + "Type"
                                    + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getString("EntityName")
                                    + TextFormatting.DARK_AQUA + ", Name" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                    + nbt.getString("Name") + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":"
                                    + TextFormatting.GREEN + nbt.getString("Owner") + TextFormatting.DARK_AQUA + ", PetId"
                                    + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getInteger("PetId")
                                    + TextFormatting.DARK_AQUA + ", Dimension" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                    + nbt.getInteger("Dimension") + TextFormatting.DARK_AQUA + ", Pos" + TextFormatting.WHITE + ":"
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posX) + TextFormatting.WHITE + ", "
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posY) + TextFormatting.WHITE + ", "
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posZ));
                        }
                    }
                }
                if (tamedlist.size() > 0) {
                    sendPageHelp(sender, (byte) 10, tamedlist, args, "Listing tamed pets");
                    sender.addChatMessage(new TextComponentTranslation("Loaded tamed count : " + TextFormatting.AQUA + loadedCount
                            + TextFormatting.WHITE + ", Unloaded count : " + TextFormatting.AQUA + unloadedCount + TextFormatting.WHITE
                            + ", Total count : " + TextFormatting.AQUA + (ownerPetData != null ? ownerPetData.getTamedList().tagCount() : 0)));
                } else {
                    sender.addChatMessage(new TextComponentTranslation("Player " + TextFormatting.GREEN + playername
                            + TextFormatting.WHITE + " does not have any tamed animals."));
                }
            } else if (command.equalsIgnoreCase("tamed") || command.equalsIgnoreCase("tame") && !par2.equals("")) {
                int unloadedCount = 0;
                int loadedCount = 0;
                ArrayList<Integer> foundIds = new ArrayList<Integer>();
                ArrayList<String> tamedlist = new ArrayList<String>();
                // search for mocreature tamed entities
                for (int dimension : DimensionManager.getIDs()) {
                    WorldServer world = DimensionManager.getWorld(dimension);
                    for (int j = 0; j < world.loadedEntityList.size(); j++) {
                        Entity entity = (Entity) world.loadedEntityList.get(j);
                        if (IMoCTameable.class.isAssignableFrom(entity.getClass())) {
                            IMoCTameable mocreature = (IMoCTameable) entity;
                            if (mocreature.getOwnerPetId() != -1) {
                                loadedCount++;
                                foundIds.add(mocreature.getOwnerPetId());
                                tamedlist.add(TextFormatting.WHITE + "Found pet with " + TextFormatting.DARK_AQUA + "Type"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                        + ((EntityLiving) mocreature).getName() + TextFormatting.DARK_AQUA + ", Name"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN + mocreature.getPetName()
                                        + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                        + mocreature.getOwnerId() + TextFormatting.DARK_AQUA + ", PetId" + TextFormatting.WHITE + ":"
                                        + TextFormatting.GREEN + mocreature.getOwnerPetId() + TextFormatting.DARK_AQUA + ", Dimension"
                                        + TextFormatting.WHITE + ":" + TextFormatting.GREEN + entity.dimension + TextFormatting.DARK_AQUA
                                        + ", Pos" + TextFormatting.WHITE + ":" + TextFormatting.LIGHT_PURPLE + Math.round(entity.posX)
                                        + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(entity.posY)
                                        + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(entity.posZ));
                            }
                        }
                    }
                }
                // if (!MoCreatures.isServer())
                // {
                for (MoCPetData ownerPetData : MoCreatures.instance.mapData.getPetMap().values()) {
                    for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++) {
                        NBTTagCompound nbt = ownerPetData.getTamedList().getCompoundTagAt(i);
                        if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId"))) {
                            unloadedCount++;
                            double posX = nbt.getTagList("Pos", 10).getDoubleAt(0);
                            double posY = nbt.getTagList("Pos", 10).getDoubleAt(1);
                            double posZ = nbt.getTagList("Pos", 10).getDoubleAt(2);
                            tamedlist.add(TextFormatting.WHITE + "Found unloaded pet with " + TextFormatting.DARK_AQUA + "Type"
                                    + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getString("EntityName")
                                    + TextFormatting.DARK_AQUA + ", Name" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                    + nbt.getString("Name") + TextFormatting.DARK_AQUA + ", Owner" + TextFormatting.WHITE + ":"
                                    + TextFormatting.GREEN + nbt.getString("Owner") + TextFormatting.DARK_AQUA + ", PetId"
                                    + TextFormatting.WHITE + ":" + TextFormatting.GREEN + nbt.getInteger("PetId")
                                    + TextFormatting.DARK_AQUA + ", Dimension" + TextFormatting.WHITE + ":" + TextFormatting.GREEN
                                    + nbt.getInteger("Dimension") + TextFormatting.DARK_AQUA + ", Pos" + TextFormatting.WHITE + ":"
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posX) + TextFormatting.WHITE + ", "
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posY) + TextFormatting.WHITE + ", "
                                    + TextFormatting.LIGHT_PURPLE + Math.round(posZ));
                        }
                    }
                }
                //}
                sendPageHelp(sender, (byte) 10, tamedlist, args, "Listing tamed pets");
                sender.addChatMessage(new TextComponentTranslation("Loaded tamed count : "
                        + TextFormatting.AQUA
                        + loadedCount
                        + TextFormatting.WHITE
                        + (!MoCreatures.isServer() ? ", Unloaded Count : " + TextFormatting.AQUA + unloadedCount + TextFormatting.WHITE
                                + ", Total count : " + TextFormatting.AQUA + (loadedCount + unloadedCount) : "")));
            }
        } else if (command.equalsIgnoreCase("tp") && args.length == 3) {
            int petId = 0;
            try {
                petId = Integer.parseInt(par2);
            } catch (NumberFormatException e) {
                petId = -1;
            }
            String playername = args[2];
            EntityPlayerMP player =
                    FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(playername);
            if (player == null) {
                return;
            }
            // search for tamed entity in mocreatures.dat
            MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(player.getUniqueID());
            if (ownerPetData != null) {
                for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++) {
                    NBTTagCompound nbt = ownerPetData.getTamedList().getCompoundTagAt(i);
                    if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == petId) {
                        String petName = nbt.getString("Name");
                        WorldServer world = DimensionManager.getWorld(nbt.getInteger("Dimension"));
                        if (!teleportLoadedPet(world, player, petId, petName, sender)) {
                            double posX = nbt.getTagList("Pos", 10).getDoubleAt(0);
                            double posY = nbt.getTagList("Pos", 10).getDoubleAt(1);
                            double posZ = nbt.getTagList("Pos", 10).getDoubleAt(2);
                            sender.addChatMessage(new TextComponentTranslation("Found unloaded pet " + TextFormatting.GREEN
                                    + nbt.getString("id") + TextFormatting.WHITE + " with name " + TextFormatting.AQUA
                                    + nbt.getString("Name") + TextFormatting.WHITE + " at location " + TextFormatting.LIGHT_PURPLE
                                    + Math.round(posX) + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(posY)
                                    + TextFormatting.WHITE + ", " + TextFormatting.LIGHT_PURPLE + Math.round(posZ) + TextFormatting.WHITE
                                    + " with Pet ID " + TextFormatting.BLUE + nbt.getInteger("PetId")));
                            boolean result = teleportLoadedPet(world, player, petId, petName, sender); // attempt to TP again
                            if (!result) {
                                sender.addChatMessage(new TextComponentTranslation("Unable to transfer entity ID "
                                        + TextFormatting.GREEN + petId + TextFormatting.WHITE + ". It may only be transferred to "
                                        + TextFormatting.AQUA + player.getName()));
                            }
                        }
                        break;
                    }
                }
            } else {
                sender.addChatMessage(new TextComponentTranslation("Tamed entity could not be located."));
            }
        } else if (command.equalsIgnoreCase("tamedcount")) {
            String playername = par2;
            List<EntityPlayerMP> players = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList();
            for (int i = 0; i < players.size(); i++) {
                EntityPlayerMP player = (EntityPlayerMP) players.get(i);
                if (player.getName().equalsIgnoreCase(playername)) {
                    int tamedCount = MoCTools.numberTamedByPlayer(player);
                    sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + playername
                            + "'s recorded tamed count is " + TextFormatting.AQUA + tamedCount));
                }
            }
            sender.addChatMessage(new TextComponentTranslation(TextFormatting.RED + "Could not find player "
                    + TextFormatting.GREEN + playername + TextFormatting.RED
                    + ". Please verify the player is online and/or name was entered correctly."));
        }
        // START ENTITY FREQUENCY/BIOME SECTION
        else if (args.length >= 2
                && (command.equalsIgnoreCase("frequency") || command.equalsIgnoreCase("minspawn") || command.equalsIgnoreCase("maxspawn")
                        || command.equalsIgnoreCase("maxchunk") || command.equalsIgnoreCase("canspawn"))) {
            MoCEntityData entityData = MoCreatures.mocEntityMap.get(par2);//modEntry.getValue().getCreature(name);

            if (entityData != null) {
                if (command.equalsIgnoreCase("frequency")) {
                    if (par3 == null) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " frequency is " + TextFormatting.AQUA + entityData.getFrequency()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setFrequency(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "frequency");
                            prop.value = par3;
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " frequency to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (command.equalsIgnoreCase("min") || command.equalsIgnoreCase("minspawn")) {
                    if (par3 == null) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " minGroupSpawn is " + TextFormatting.AQUA + entityData.getMinSpawn()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setMinSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "minspawn");
                            prop.value = par3;
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " minGroupSpawn to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (command.equalsIgnoreCase("max") || command.equalsIgnoreCase("maxspawn")) {
                    if (par3 == null) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " maxGroupSpawn is " + TextFormatting.AQUA + entityData.getMaxSpawn()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setMaxSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "maxspawn");
                            prop.value = par3;
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " maxGroupSpawn to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (command.equalsIgnoreCase("chunk") || command.equalsIgnoreCase("maxchunk")) {
                    if (par3 == null) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " maxInChunk is " + TextFormatting.AQUA + entityData.getMaxInChunk()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setMaxSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "maxchunk");
                            prop.value = par3;
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN
                                    + entityData.getEntityName() + TextFormatting.WHITE + " maxInChunk to " + TextFormatting.AQUA + par3
                                    + TextFormatting.WHITE + "."));
                        } catch (NumberFormatException ex) {
                            this.sendCommandHelp(sender);
                        }
                    }
                } else if (command.equalsIgnoreCase("canspawn")) {
                    if (par3 == null) {
                        sender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + entityData.getEntityName()
                                + TextFormatting.WHITE + " canSpawn is " + TextFormatting.AQUA + entityData.getCanSpawn()
                                + TextFormatting.WHITE + "."));
                    } else {
                        try {
                            entityData.setCanSpawn(Boolean.parseBoolean(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "canspawn");
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
                // TODO - remove spawnlist entry from vanilla list and readd it
            }
        } else if (args.length == 1) {
            OUTER: for (Map.Entry<String, MoCConfigCategory> catEntry : config.categories.entrySet()) {
                String catName = catEntry.getValue().getQualifiedName();
                if (catName.equalsIgnoreCase("custom-id-settings")) {
                    continue;
                }

                for (Map.Entry<String, MoCProperty> propEntry : catEntry.getValue().entrySet()) {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(command)) {
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
                        break OUTER;
                    }
                }
            }
        }
        // END ENTITY FREQUENCY/BIOME SECTION
        // START OTHER SECTIONS
        else {
            for (Map.Entry<String, MoCConfigCategory> catEntry : config.categories.entrySet()) {
                for (Map.Entry<String, MoCProperty> propEntry : catEntry.getValue().entrySet()) {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(command)) {
                        continue;
                    }
                    MoCProperty property = propEntry.getValue();
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().getString();

                    if (propList == null && propValue == null) {
                        continue;
                    }

                    if (propEntry.getValue().getType() == MoCProperty.Type.BOOLEAN) {
                        if (par2.equalsIgnoreCase("true") || par2.equalsIgnoreCase("false")) {
                            property.set(par2);
                            saved = true;
                            sender.addChatMessage(new TextComponentTranslation("Set " + TextFormatting.GREEN + propEntry.getKey()
                                    + " to " + TextFormatting.AQUA + par2 + "."));
                        }
                    } else if (propEntry.getValue().getType() == MoCProperty.Type.INTEGER) {
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

                    } else if (propEntry.getValue().getType() == MoCProperty.Type.DOUBLE) {
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
                    //break OUTER; // exit since we found the property we need to save
                }
            }
        }
        // START HELP COMMAND
        if (command.equalsIgnoreCase("help")) {
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
            sender.addChatMessage(new TextComponentTranslation(TextFormatting.DARK_GREEN + "--- Showing MoCreatures help page "
                    + Integer.valueOf(j + 1) + " of " + Integer.valueOf(i + 1) + "(/moc help <page>)---"));

            for (int l = j * b0; l < k; ++l) {
                String commandToSend = list.get(l);
                sender.addChatMessage(new TextComponentTranslation(commandToSend));
            }
        }
        // END HELP COMMAND
        if (saved) {
            // TODO: update only what is needed instead of everything
            config.save();
            MoCreatures.proxy.readGlobalConfigValues();
        }
    }

    /**
     * Returns a sorted list of all possible commands for the given
     * ICommandSender.
     */
    protected List<String> getSortedPossibleCommands(ICommandSender par1ICommandSender) {
        Collections.sort(CommandMoCreatures.commands);
        return CommandMoCreatures.commands;
    }

    public boolean teleportLoadedPet(WorldServer world, EntityPlayerMP player, int petId, String petName, ICommandSender par1ICommandSender) {
        for (int j = 0; j < world.loadedEntityList.size(); j++) {
            Entity entity = (Entity) world.loadedEntityList.get(j);
            // search for entities that are MoCEntityAnimal's
            if (IMoCTameable.class.isAssignableFrom(entity.getClass()) && !((IMoCTameable) entity).getPetName().equals("")
                    && ((IMoCTameable) entity).getOwnerPetId() == petId) {
                // grab the entity data
                NBTTagCompound compound = new NBTTagCompound();
                entity.writeToNBT(compound);
                if (compound != null && compound.getString("Owner") != null) {
                    String owner = compound.getString("Owner");
                    String name = compound.getString("Name");
                    if (owner != null && owner.equalsIgnoreCase(player.getName())) {
                        // check if in same dimension
                        if (entity.dimension == player.dimension) {
                            entity.setPosition(player.posX, player.posY, player.posZ);
                        } else if (!player.worldObj.isRemote)// transfer entity to player dimension
                        {
                            Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(entity), player.worldObj);
                            if (newEntity != null) {
                                MoCTools.copyDataFromOld(newEntity, entity); // transfer all existing data to our new entity
                                newEntity.setPosition(player.posX, player.posY, player.posZ);
                                DimensionManager.getWorld(player.dimension).spawnEntityInWorld(newEntity);
                            }
                            if (entity.getRidingEntity() == null) {
                                entity.isDead = true;
                            } else // dismount players
                            {
                                entity.getRidingEntity().dismountRidingEntity();
                                entity.isDead = true;
                            }
                            world.resetUpdateEntityTick();
                            DimensionManager.getWorld(player.dimension).resetUpdateEntityTick();
                        }
                        par1ICommandSender.addChatMessage(new TextComponentTranslation(TextFormatting.GREEN + name + TextFormatting.WHITE
                                + " has been tp'd to location " + Math.round(player.posX) + ", " + Math.round(player.posY) + ", "
                                + Math.round(player.posZ) + " in dimension " + player.dimension));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void sendCommandHelp(ICommandSender sender) {
        sender.addChatMessage(new TextComponentTranslation("\u00a72Listing MoCreatures commands"));
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
