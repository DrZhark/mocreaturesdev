package drzhark.mocreatures.command;

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
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;
import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfigCategory;
import drzhark.mocreatures.configuration.MoCConfiguration;
import drzhark.mocreatures.configuration.MoCProperty;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;

public class CommandMoCreatures extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();
    private static Map<String, String> commmentMap = new TreeMap<String, String>();
    private static List tabCompletionStrings = new ArrayList<String>();

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

    public String getCommandName()
    {
        return "mocreatures";
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
        return "commands.mocreatures.usage";
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return getListOfStringsMatchingLastWord(par2ArrayOfStr, (String[])tabCompletionStrings.toArray(new String[tabCompletionStrings.size()]));
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] charArray)
    {
        String command = "";
        if (charArray.length == 0)
            command = "help";
        else command = charArray[0];
        String par2 = "";
        if (charArray.length > 1)
            par2 = charArray[1];
        String par3 = "";
        if (charArray.length == 3)
        {
            par3 = charArray[2];
        }

        MoCConfiguration config = MoCreatures.proxy.mocSettingsConfig;
        boolean saved = false;

        if (command.equalsIgnoreCase("tamed") || command.equalsIgnoreCase("tame"))
        {
            if (charArray.length == 2 && !Character.isDigit(charArray[1].charAt(0)))
            {
                int unloadedCount = 0;
                int loadedCount = 0;
                ArrayList foundIds = new ArrayList();
                ArrayList<String> tamedlist = new ArrayList<String>();
                String playername = par2;
                // search for tamed entity
                for (int dimension : DimensionManager.getIDs())
                {
                    WorldServer world = DimensionManager.getWorld(dimension);
                    for (int j = 0; j < world.loadedEntityList.size(); j++)
                    {
                        Entity entity = (Entity) world.loadedEntityList.get(j);
                        if (IMoCTameable.class.isAssignableFrom(entity.getClass()))
                        {
                            IMoCTameable mocreature = (IMoCTameable)entity;
                            if (mocreature.getOwnerName().equalsIgnoreCase(playername))
                            {
                                loadedCount++;
                                foundIds.add(mocreature.getOwnerPetId());
                                tamedlist.add(EnumChatFormatting.WHITE + "Found pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + ((EntityLiving)mocreature).getCommandSenderName() + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getName() + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getOwnerName() + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getOwnerPetId() + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + entity.dimension + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posZ));
                            }
                        }
                    }
                }
                MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(playername);
                if (ownerPetData != null)
                {
                    for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
                    {
                        NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().getCompoundTagAt(i);
                        if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId")))
                        {
                            unloadedCount++;
                            double posX = nbt.getTagList("Pos", 6).func_150309_d(0);
                            double posY = nbt.getTagList("Pos", 6).func_150309_d(1);
                            double posZ = nbt.getTagList("Pos", 6).func_150309_d(2);
                            tamedlist.add(EnumChatFormatting.WHITE + "Found unloaded pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("EntityName") + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Name") + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Owner") + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("PetId") + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("Dimension") + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ));
                        }
                    }
                }
                if (tamedlist.size() > 0)
                {
                    sendPageHelp(par1ICommandSender, (byte)10, tamedlist, charArray, "Listing tamed pets");
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Loaded tamed count : " + EnumChatFormatting.AQUA + loadedCount + EnumChatFormatting.WHITE + ", Unloaded count : " + EnumChatFormatting.AQUA + unloadedCount + EnumChatFormatting.WHITE + ", Total count : " + EnumChatFormatting.AQUA + (ownerPetData != null ? ownerPetData.getTamedList().tagCount() : 0)));
                }
                else
                {
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation("Player " + EnumChatFormatting.GREEN + playername + EnumChatFormatting.WHITE + " does not have any tamed animals."));
                }
            }
            else if (command.equalsIgnoreCase("tamed") || command.equalsIgnoreCase("tame") && !par2.equals(""))
            {
                String playername = par1ICommandSender.getCommandSenderName();
                List players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
                int unloadedCount = 0;
                int loadedCount = 0;
                ArrayList foundIds = new ArrayList();
                ArrayList<String> tamedlist = new ArrayList<String>();
                // search for mocreature tamed entities
                for (int dimension : DimensionManager.getIDs())
                {
                    WorldServer world = DimensionManager.getWorld(dimension);
                    for (int j = 0; j < world.loadedEntityList.size(); j++)
                    {
                        Entity entity = (Entity) world.loadedEntityList.get(j);
                        if (IMoCTameable.class.isAssignableFrom(entity.getClass()))
                        {
                            IMoCTameable mocreature = (IMoCTameable)entity;
                            if (mocreature.getOwnerPetId() != -1)
                            {
                                loadedCount++;
                                foundIds.add(mocreature.getOwnerPetId());
                                tamedlist.add(EnumChatFormatting.WHITE + "Found pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + ((EntityLiving)mocreature).getCommandSenderName() + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getName() + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getOwnerName() + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getOwnerPetId() + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + entity.dimension + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posZ));
                            }
                        }
                    }
                }
               // if (!MoCreatures.isServer())
               // {
                    for (MoCPetData ownerPetData : MoCreatures.instance.mapData.getPetMap().values())
                    {
                        for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
                        {
                            NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().getCompoundTagAt(i);
                            if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId")))
                            {
                                unloadedCount++;
                                double posX = nbt.getTagList("Pos", 10).func_150309_d(0);
                                double posY = nbt.getTagList("Pos", 10).func_150309_d(1);
                                double posZ = nbt.getTagList("Pos", 10).func_150309_d(2);
                                tamedlist.add(EnumChatFormatting.WHITE + "Found unloaded pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("EntityName") + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Name") + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Owner") + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("PetId") + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("Dimension") + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ));
                            }
                        }
                    }
                //}
                sendPageHelp(par1ICommandSender, (byte)10, tamedlist, charArray, "Listing tamed pets");
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("Loaded tamed count : " + EnumChatFormatting.AQUA + loadedCount + EnumChatFormatting.WHITE + (!MoCreatures.isServer() ? ", Unloaded Count : " + EnumChatFormatting.AQUA + unloadedCount + EnumChatFormatting.WHITE + ", Total count : " + EnumChatFormatting.AQUA + (loadedCount + unloadedCount) : "")));
            }
        }
        else if (command.equalsIgnoreCase("tp") && charArray.length == 3)
        {
            int petId = 0;
            try 
            {
                petId = Integer.parseInt(par2);
            }
            catch (NumberFormatException e)
            {
                petId = -1;
            }
            String playername = charArray[2];
            EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152612_a(playername);
            // search for tamed entity in mocreatures.dat
            MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(playername);
            if (player != null & ownerPetData != null)
            {
                for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
                {
                    NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().getCompoundTagAt(i);
                    if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == petId)
                    {
                        String petName = nbt.getString("Name");
                        WorldServer world = DimensionManager.getWorld(nbt.getInteger("Dimension"));
                        if (!teleportLoadedPet(world, player, petId, petName, par1ICommandSender))
                        {
                            double posX = nbt.getTagList("Pos", 10).func_150309_d(0);
                            double posY = nbt.getTagList("Pos", 10).func_150309_d(1);
                            double posZ = nbt.getTagList("Pos", 10).func_150309_d(2);
                            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Found unloaded pet " + EnumChatFormatting.GREEN + nbt.getString("id") + EnumChatFormatting.WHITE + " with name " + EnumChatFormatting.AQUA + nbt.getString("Name") + EnumChatFormatting.WHITE + " at location " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ) + EnumChatFormatting.WHITE + " with Pet ID " + EnumChatFormatting.BLUE + nbt.getInteger("PetId")));
                            int x = MathHelper.floor_double( posX );
                            int z = MathHelper.floor_double( posZ );
                            Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                            boolean result = teleportLoadedPet(world, player, petId, petName, par1ICommandSender); // attempt to TP again
                            if (!result)
                            {
                                par1ICommandSender.addChatMessage(new ChatComponentTranslation("Unable to transfer entity ID " + EnumChatFormatting.GREEN + petId + EnumChatFormatting.WHITE + ". It may only be transferred to " + EnumChatFormatting.AQUA + player.getCommandSenderName()));
                            }
                        }
                        break;
                    }
                }
            }
            else
            {
                par1ICommandSender.addChatMessage(new ChatComponentTranslation("Tamed entity could not be located."));
            }
        }
        else if (command.equalsIgnoreCase("tamedcount"))
        {
            String playername = par2;
            List players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
            for (int i = 0; i < players.size(); i++)
            {
                EntityPlayerMP player = (EntityPlayerMP) players.get(i);
                if (player.getCommandSenderName().equalsIgnoreCase(playername))
                {
                    int tamedCount = MoCTools.numberTamedByPlayer(player);
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + playername + "'s recorded tamed count is " + EnumChatFormatting.AQUA + tamedCount));
                }
            }
            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Could not find player " + EnumChatFormatting.GREEN + playername + EnumChatFormatting.RED + ". Please verify the player is online and/or name was entered correctly."));
        }
        // START ENTITY FREQUENCY/BIOME SECTION
        else if (charArray.length >= 2 && (command.equalsIgnoreCase("frequency") || command.equalsIgnoreCase("minspawn") || command.equalsIgnoreCase("maxspawn") || command.equalsIgnoreCase("maxchunk") || command.equalsIgnoreCase("canspawn")))
        {
            MoCEntityData entityData = MoCreatures.mocEntityMap.get(par2);//modEntry.getValue().getCreature(name);

            if (entityData != null)
            {
                if (command.equalsIgnoreCase("frequency"))
                {
                    if (par3 == null)
                    {
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency is " + EnumChatFormatting.AQUA + entityData.getFrequency() + EnumChatFormatting.WHITE + "."));
                    }
                    else {
                        try 
                        {
                            entityData.setFrequency(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "frequency");
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
                else if (command.equalsIgnoreCase("min") || command.equalsIgnoreCase("minspawn"))
                {
                    if (par3 == null)
                    {
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMinSpawn() + EnumChatFormatting.WHITE + "."));
                    }
                    else {
                        try 
                        {
                            entityData.setMinSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "minspawn");
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
                else if (command.equalsIgnoreCase("max") || command.equalsIgnoreCase("maxspawn"))
                {
                    if (par3 == null)
                    {
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMaxSpawn() + EnumChatFormatting.WHITE + "."));
                    }
                    else {
                        try 
                        {
                            entityData.setMaxSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "maxspawn");
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
                else if (command.equalsIgnoreCase("chunk") || command.equalsIgnoreCase("maxchunk"))
                {
                    if (par3 == null)
                    {
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk is " + EnumChatFormatting.AQUA + entityData.getMaxInChunk() + EnumChatFormatting.WHITE + "."));
                    }
                    else {
                        try 
                        {
                            entityData.setMaxSpawn(Integer.parseInt(par3));
                            MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "maxchunk");
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
                else if (command.equalsIgnoreCase("canspawn"))
                {
                  if (par3 == null)
                  {
                    par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " canSpawn is " + EnumChatFormatting.AQUA + entityData.getCanSpawn() + EnumChatFormatting.WHITE + "."));
                  }
                  else
                  {
                    try
                    {
                      entityData.setCanSpawn(Boolean.parseBoolean(par3));
                      MoCProperty prop = MoCreatures.proxy.mocEntityConfig.get(entityData.getEntityName(), "canspawn");
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
                // TODO - remove spawnlist entry from vanilla list and readd it
            }
        }
        else if (charArray.length == 1) 
        {
            OUTER: for (Map.Entry<String, MoCConfigCategory> catEntry : config.categories.entrySet())
            {
                String catName = catEntry.getValue().getQualifiedName();
                if (catName.equalsIgnoreCase("custom-id-settings"))
                    continue;

                for (Map.Entry<String, MoCProperty> propEntry : catEntry.getValue().entrySet())
                {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(command))
                        continue;
                    MoCProperty property = propEntry.getValue();
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().value;
                    if (propList == null && propValue == null)
                        continue;
                    if (par2.equals(""))
                    {
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + propEntry.getKey() + EnumChatFormatting.WHITE + " is " + EnumChatFormatting.AQUA + propValue));
                        break OUTER;
                    }
                }
            }
        }
        // END ENTITY FREQUENCY/BIOME SECTION
        // START OTHER SECTIONS
        else 
        {
            for (Map.Entry<String, MoCConfigCategory> catEntry : config.categories.entrySet())
            {
                String catName = catEntry.getValue().getQualifiedName();

                for (Map.Entry<String, MoCProperty> propEntry : catEntry.getValue().entrySet())
                {
                    if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(command))
                        continue;
                    MoCProperty property = propEntry.getValue();
                    List<String> propList = propEntry.getValue().valueList;
                    String propValue = propEntry.getValue().getString();

                    if (propList == null && propValue == null)
                        continue;

                    if (propEntry.getValue().getType() == MoCProperty.Type.BOOLEAN)
                    {
                        if (par2.equalsIgnoreCase("true") || par2.equalsIgnoreCase("false"))
                        {
                            property.set(par2);
                            saved = true;
                            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + "."));
                        }
                    }
                    else if (propEntry.getValue().getType() == MoCProperty.Type.INTEGER)
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
                    else if (propEntry.getValue().getType() == MoCProperty.Type.DOUBLE)
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
                    //break OUTER; // exit since we found the property we need to save
                }
            }
        }
        // START HELP COMMAND
        if (command.equalsIgnoreCase("help"))
        {
            List<String> list = this.getSortedPossibleCommands(par1ICommandSender);
            byte b0 = 10;
            int i = (list.size() - 1) / b0;
            boolean flag = false;
            ICommand icommand;
            int j = 0;

            if (charArray.length > 1)
            {
                try
                {
                    j = charArray.length == 0 ? 0 : parseIntBounded(par1ICommandSender, charArray[1], 1, i + 1) - 1;
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
            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.DARK_GREEN + "--- Showing MoCreatures help page " + Integer.valueOf(j + 1) + " of " + Integer.valueOf(i + 1) + "(/moc help <page>)---"));

            for (int l = j * b0; l < k; ++l)
            {
                String commandToSend = list.get(l);
                par1ICommandSender.addChatMessage(new ChatComponentTranslation(commandToSend));
            }
        }
        // END HELP COMMAND
        if (saved)
        {
            // TODO: update only what is needed instead of everything
            config.save();
            MoCreatures.proxy.readGlobalConfigValues();
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

    public boolean teleportLoadedPet(WorldServer world, EntityPlayerMP player, int petId, String petName, ICommandSender par1ICommandSender)
    {
        for (int j = 0; j < world.loadedEntityList.size(); j++)
        {
            Entity entity = (Entity) world.loadedEntityList.get(j);
            // search for entities that are MoCEntityAnimal's
            if (IMoCTameable.class.isAssignableFrom(entity.getClass()) && !((IMoCTameable)entity).getName().equals("") && ((IMoCTameable)entity).getOwnerPetId() == petId)
            {
                // grab the entity data
                NBTTagCompound compound = new NBTTagCompound();
                entity.writeToNBT(compound);
                if (compound != null && compound.getString("Owner") != null)
                {
                    String owner = compound.getString("Owner");
                    String name = compound.getString("Name");
                    if (owner != null && owner.equalsIgnoreCase(player.getCommandSenderName()))
                    {
                        // check if in same dimension
                        if (entity.dimension == player.dimension)
                            entity.setPosition(player.posX, player.posY, player.posZ);
                        else if (!player.worldObj.isRemote)// transfer entity to player dimension
                        {
                            Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(entity), player.worldObj);
                            if (newEntity != null)
                            {
                                newEntity.copyDataFrom(entity, true); // transfer all existing data to our new entity
                                newEntity.setPosition(player.posX, player.posY, player.posZ);
                                DimensionManager.getWorld(player.dimension).spawnEntityInWorld(newEntity);
                            }
                            if (entity.riddenByEntity == null)
                            {
                                entity.isDead = true;
                            }
                            else // dismount players
                            {
                                entity.riddenByEntity.mountEntity(null);
                                entity.isDead = true;
                            }
                            world.resetUpdateEntityTick();
                            DimensionManager.getWorld(player.dimension).resetUpdateEntityTick();
                        }
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.GREEN + name + EnumChatFormatting.WHITE + " has been tp'd to location " + Math.round(player.posX) + ", " + Math.round(player.posY) + ", " + Math.round(player.posZ) + " in dimension " + player.dimension));
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void sendCommandHelp(ICommandSender sender)
    {
        sender.addChatMessage(new ChatComponentTranslation("\u00a72Listing MoCreatures commands"));
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