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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;
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

public class CommandMoCreatures extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();
    private static Map<String, String> commmentMap = new TreeMap<String, String>();

    static {
        commands.add("/moc attackdolphins <boolean>");
        commands.add("/moc attackhorses <boolean>");
        commands.add("/moc attackwolves <boolean>");
        commands.add("/moc caveogrechance <float>");
        commands.add("/moc caveogrestrength <float>");
        commands.add("/moc creaturespawntickrate <int>");
        commands.add("/moc debuglogging <boolean>");
        commands.add("/moc deletepets <playername>");
        commands.add("/moc destroydrops <boolean>");
        commands.add("/moc fireogrechance <int>");
        commands.add("/moc fireogrestrength <float>");
        commands.add("/moc easybreeding <boolean>");
        commands.add("/moc elephantbulldozer <boolean>");
        commands.add("/moc enableownership <boolean>");
        commands.add("/moc enableresetownerscroll <boolean>");
        commands.add("/moc golemdestroyblocks <boolean>");
        commands.add("/moc list tamed");
        commands.add("/moc list tamed <playername>");
        commands.add("/moc maxtamedperop <int>");
        commands.add("/moc maxtamedperplayer <int>");
        commands.add("/moc ogreattackrange <int>");
        commands.add("/moc ogrestrength <float>");
        commands.add("/moc tamedcount <playername>");
        commands.add("/moc tp <entityid> <playername>");
        commands.add("/moc tp <petname> <playername>");
        commands.add("/moc zebrachance <int>");
        aliases.add("moc");
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

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        String par1 = "";
        if (par2ArrayOfStr.length == 0)
            par1 = "help";
        else par1 = par2ArrayOfStr[0];
        String par2 = "";
        if (par2ArrayOfStr.length > 1)
            par2 = par2ArrayOfStr[1];

        MoCConfiguration config = MoCreatures.proxy.mocGlobalConfig;
        boolean saved = false;
        boolean doNotShowHelp = false;
        if (par2ArrayOfStr.length >= 2)
        {
            OUTER: if (par2ArrayOfStr.length <= 5)
            {
                String par3 = "";
                if (par2ArrayOfStr.length >= 3)
                {
                    par3 = par2ArrayOfStr[2];
                }

                // START LIST COMMAND
                if (par1.equalsIgnoreCase("list"))
                {
                    if (par2 != null)
                    {
                        if (par2.equalsIgnoreCase("tamed") || par2.equalsIgnoreCase("tame"))
                        {
                            if (par2ArrayOfStr.length > 2 && !Character.isDigit(par2ArrayOfStr[2].charAt(0)))
                            {
                                int unloadedCount = 0;
                                int loadedCount = 0;
                                ArrayList foundIds = new ArrayList();
                                ArrayList<String> tamedlist = new ArrayList<String>();
                                String playername = par2ArrayOfStr[2];
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
                                                tamedlist.add(EnumChatFormatting.WHITE + "Found pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getEntityName() + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getName() + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getOwnerName() + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getOwnerPetId() + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + entity.dimension + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posZ));
                                            }
                                        }
                                    }
                                }
                                MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(playername);
                                if (ownerPetData != null)
                                {
                                    for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
                                    {
                                        NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().tagAt(i);
                                        if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId")))
                                        {
                                            unloadedCount++;
                                            double posX = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(0)).data;
                                            double posY = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(1)).data;
                                            double posZ = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(2)).data;
                                            tamedlist.add(EnumChatFormatting.WHITE + "Found unloaded pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("EntityName") + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Name") + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Owner") + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("PetId") + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("Dimension") + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ));
                                        }
                                    }
                                }
                                if (tamedlist.size() > 0)
                                {
                                    sendPageHelp(par1ICommandSender, (byte)10, tamedlist, par2ArrayOfStr);
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Loaded tamed count : " + EnumChatFormatting.AQUA + loadedCount + EnumChatFormatting.WHITE + ", Unloaded count : " + EnumChatFormatting.AQUA + unloadedCount + EnumChatFormatting.WHITE + ", Total count : " + EnumChatFormatting.AQUA + (ownerPetData != null ? ownerPetData.getTamedList().tagCount() : 0)));
                                    doNotShowHelp = true;
                                    break OUTER;
                                }
                                else
                                {
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Player " + EnumChatFormatting.GREEN + playername + EnumChatFormatting.WHITE + " does not have any tamed animals."));
                                    doNotShowHelp = true;
                                    break OUTER;
                                }
                            }
                            else
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
                                                tamedlist.add(EnumChatFormatting.WHITE + "Found pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getEntityName() + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getName() + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getOwnerName() + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + mocreature.getOwnerPetId() + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + entity.dimension + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posZ));
                                            }
                                        }
                                    }
                                }
                                if (!MoCreatures.isServer())
                                {
                                    for (MoCPetData ownerPetData : MoCreatures.instance.mapData.getPetMap().values())
                                    {
                                        for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
                                        {
                                            NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().tagAt(i);
                                            if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId")))
                                            {
                                                unloadedCount++;
                                                double posX = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(0)).data;
                                                double posY = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(1)).data;
                                                double posZ = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(2)).data;
                                                tamedlist.add(EnumChatFormatting.WHITE + "Found unloaded pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("EntityName") + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Name") + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Owner") + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("PetId") + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("Dimension") + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ));
                                            }
                                        }
                                    }
                                }
                                sendPageHelp(par1ICommandSender, (byte)10, tamedlist, par2ArrayOfStr);
                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Loaded tamed count : " + EnumChatFormatting.AQUA + loadedCount + EnumChatFormatting.WHITE + (!MoCreatures.isServer() ? ", Unloaded Count : " + EnumChatFormatting.AQUA + unloadedCount + EnumChatFormatting.WHITE + ", Total count : " + EnumChatFormatting.AQUA + (loadedCount + unloadedCount) : "")));
                                doNotShowHelp = true;
                                break OUTER;
                            }
                        }
                    }
                    break OUTER;
                }
                // END LIST COMMAND
                else if (par1.equalsIgnoreCase("tp") && par2ArrayOfStr.length == 3)
                {
                    if (par2 != null)
                    {
                        int entityId = 0;
                        String petName = "";
                        try 
                        {
                            entityId = Integer.parseInt(par2);
                        }
                        catch (NumberFormatException e)
                        {
                            entityId = -1;
                            petName = par2;
                        }
                        String playername = par2ArrayOfStr[2];
                        List players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
                        for (int i = 0; i < players.size(); i++)
                        {
                            EntityPlayerMP player = (EntityPlayerMP) players.get(i);
                            if (player.username.equalsIgnoreCase(playername))
                            {
                                for (int dimension : DimensionManager.getIDs())
                                {
                                    WorldServer world = DimensionManager.getWorld(dimension);
                                    for (int j = 0; j < world.loadedEntityList.size(); j++)
                                    {
                                        Entity entity = (Entity) world.loadedEntityList.get(j);
                                        // search for entities that are MoCEntityAnimal's
                                        if (IMoCTameable.class.isAssignableFrom(entity.getClass()))
                                        {
                                            if (entity.entityId == entityId || ((IMoCTameable)entity).getName().equalsIgnoreCase(petName))
                                            {
                                                // grab the entity data
                                                NBTTagCompound compound = new NBTTagCompound();
                                                entity.writeToNBT(compound);
                                                if (compound != null && compound.getString("Owner") != null)
                                                {
                                                    String owner = compound.getString("Owner");
                                                    String name = compound.getString("Name");
                                                    if (owner != null && owner.equalsIgnoreCase(playername))
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
                                                        par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + name + EnumChatFormatting.WHITE + " has been tp'd to location " + Math.round(player.posX) + ", " + Math.round(player.posY) + ", " + Math.round(player.posZ) + " in dimension " + player.dimension));
                                                        doNotShowHelp = true;
                                                        break OUTER;
                                                    }
                                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Unable to transfer entity ID " + EnumChatFormatting.GREEN + entityId + EnumChatFormatting.WHITE + ". It may only be transferred to " + EnumChatFormatting.AQUA + owner));
                                                    doNotShowHelp = true;
                                                    break OUTER;
                                                }
                                            }
                                        }
                                    } // end for
                                } // end for
                                // search for tamed entity in mocreatures.dat
                                MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(playername);
                                if (ownerPetData != null)
                                {
                                    for (i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
                                    {
                                        NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().tagAt(i);
                                        if (nbt.hasKey("Name") && nbt.getString("Name").equalsIgnoreCase(petName) || nbt.hasKey("PetId") && nbt.getInteger("PetId") == entityId)
                                        {
                                            double posX = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(0)).data;
                                            double posY = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(1)).data;
                                            double posZ = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(2)).data;
                                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Found unloaded pet " + EnumChatFormatting.GREEN + nbt.getString("id") + EnumChatFormatting.WHITE + " with name " + EnumChatFormatting.AQUA + nbt.getString("Name") + EnumChatFormatting.WHITE + " at location " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ) + EnumChatFormatting.WHITE + " with Pet ID " + EnumChatFormatting.BLUE + nbt.getInteger("PetId")));
                                            if (!player.worldObj.isRemote)// transfer entity to player dimension
                                            {
                                                Entity newEntity = EntityList.createEntityByName(nbt.getString("id"), player.worldObj);
                                                if (newEntity != null)
                                                {
                                                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                                                    nbttagcompound = (NBTTagCompound)nbt.copy();
                                                    // remove petdata info
                                                    nbttagcompound.removeTag("PetId");
                                                    nbttagcompound.removeTag("Name");
                                                    nbttagcompound.removeTag("ChunkX");
                                                    nbttagcompound.removeTag("ChunkY");
                                                    nbttagcompound.removeTag("ChunkZ");
                                                    nbttagcompound.removeTag("Dimension");
                                                    newEntity.readFromNBT(nbttagcompound);
                                                    nbttagcompound.setBoolean("Cloned", true); // make sure to flag unloaded entity to killself on next load
                                                    newEntity.setPosition(player.posX, player.posY, player.posZ);
                                                    DimensionManager.getWorld(player.dimension).spawnEntityInWorld(newEntity);
                                                }
                                                DimensionManager.getWorld(player.dimension).resetUpdateEntityTick();
                                            }
                                        }
                                    }
                                }
                                doNotShowHelp = true;
                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Tamed entity could not be located."));
                                break OUTER;
                            } // end if
                    } // end for
                    }
                    break OUTER;
                }
                else if (par1.equalsIgnoreCase("tamedcount"))
                {
                    String playername = par2;
                    List players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
                    for (int i = 0; i < players.size(); i++)
                    {
                        EntityPlayerMP player = (EntityPlayerMP) players.get(i);
                        if (player.username.equalsIgnoreCase(playername))
                        {
                            int tamedCount = MoCTools.numberTamedByPlayer(player);
                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + playername + "'s recorded tamed count is " + EnumChatFormatting.AQUA + tamedCount));
                            doNotShowHelp = true;
                            break OUTER;
                        }
                    }
                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.RED + "Could not find player " + EnumChatFormatting.GREEN + playername + EnumChatFormatting.RED + ". Please verify the player is online and/or name was entered correctly."));
                    doNotShowHelp = true;
                    break OUTER;
                }
                // START ENTITY FREQUENCY/BIOME SECTION
                else if (par2ArrayOfStr.length >=3 && (par3.equalsIgnoreCase("frequency") || par3.equalsIgnoreCase("min") || par3.equalsIgnoreCase("max") || par3.equalsIgnoreCase("chunk") || par3.equalsIgnoreCase("biomegroup") || par3.equalsIgnoreCase("bg")))
                {
                    String tag = par2ArrayOfStr[0];
                    String name = par2ArrayOfStr[1];
                    String par4 = null;
                    if (par2ArrayOfStr.length >=4)
                        par4 = par2ArrayOfStr[3];
                    MoCEntityData entityData = MoCreatures.mocEntityMap.get(name);//modEntry.getValue().getCreature(name);
                    if (entityData != null)
                    {
                        if (par3.equalsIgnoreCase("frequency"))
                        {
                            if (par4 == null)
                            {
                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency is " + EnumChatFormatting.AQUA + entityData.getFrequency() + EnumChatFormatting.WHITE + "."));
                                doNotShowHelp = true;
                            }
                            else {
                                try 
                                {
                                    entityData.setFrequency(Integer.parseInt(par4));
                                    MoCProperty prop = MoCreatures.proxy.mocGlobalConfig.get(MoCreatures.proxy.CATEGORY_ENTITY_SPAWN_SETTINGS, entityData.getEntityName());
                                    prop.valueList.set(0, par4);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + "."));
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        else if (par3.equalsIgnoreCase("min"))
                        {
                            if (par4 == null)
                            {
                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMinSpawn() + EnumChatFormatting.WHITE + "."));
                                doNotShowHelp = true;
                            }
                            else {
                                try 
                                {
                                    entityData.setMinSpawn(Integer.parseInt(par4));
                                    MoCProperty prop = MoCreatures.proxy.mocGlobalConfig.get(MoCreatures.proxy.CATEGORY_ENTITY_SPAWN_SETTINGS, entityData.getEntityName());
                                    prop.valueList.set(1, par4);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + "."));
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        else if (par3.equalsIgnoreCase("max"))
                        {
                            if (par4 == null)
                            {
                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMaxSpawn() + EnumChatFormatting.WHITE + "."));
                                doNotShowHelp = true;
                            }
                            else {
                                try 
                                {
                                    entityData.setMaxSpawn(Integer.parseInt(par4));
                                    MoCProperty prop = MoCreatures.proxy.mocGlobalConfig.get(MoCreatures.proxy.CATEGORY_ENTITY_SPAWN_SETTINGS, entityData.getEntityName());
                                    prop.valueList.set(2, par4);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + "."));
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        else if (par3.equalsIgnoreCase("chunk"))
                        {
                            if (par4 == null)
                            {
                                par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk is " + EnumChatFormatting.AQUA + entityData.getMaxInChunk() + EnumChatFormatting.WHITE + "."));
                                doNotShowHelp = true;
                            }
                            else {
                                try 
                                {
                                    entityData.setMaxSpawn(Integer.parseInt(par4));
                                    MoCProperty prop = MoCreatures.proxy.mocGlobalConfig.get(MoCreatures.proxy.CATEGORY_ENTITY_SPAWN_SETTINGS, entityData.getEntityName());
                                    prop.valueList.set(3, par4);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + "."));
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                            }
                        }
                        else if (par3.equalsIgnoreCase("canspawn"))
                        {
                          if (par4 == null)
                          {
                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " canSpawn is " + EnumChatFormatting.AQUA + entityData.getCanSpawn() + EnumChatFormatting.WHITE + "."));
                            doNotShowHelp = true;
                          }
                          else
                          {
                            try
                            {
                              entityData.setCanSpawn(Boolean.parseBoolean(par4));
                              MoCProperty prop = MoCreatures.proxy.mocGlobalConfig.get("entity-canspawn-settings", entityData.getEntityName());
                              prop.value = par4;
                              saved = true;
                              par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " canSpawn to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + "."));
                            }
                            catch (NumberFormatException ex)
                            {
                              sendCommandHelp(par1ICommandSender);
                            }
                          }
                        }
                        // TODO - remove spawnlist entry from vanilla list and readd it
                        break OUTER;
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
                            if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(par1))
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
                                    par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + "."));
                                }
                            }
                            else if (propEntry.getValue().getType() == MoCProperty.Type.INTEGER)
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
                            else if (propEntry.getValue().getType() == MoCProperty.Type.DOUBLE)
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
        else {
                OUTER: for (Map.Entry<String, MoCConfigCategory> catEntry : config.categories.entrySet())
                {
                    String catName = catEntry.getValue().getQualifiedName();
                    if (catName.equalsIgnoreCase("custom-id-settings"))
                        continue;
    
                    for (Map.Entry<String, MoCProperty> propEntry : catEntry.getValue().entrySet())
                    {
                        if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(par1))
                            continue;
                        MoCProperty property = propEntry.getValue();
                        List<String> propList = propEntry.getValue().valueList;
                        String propValue = propEntry.getValue().value;
                        if (propList == null && propValue == null)
                            continue;
                        if (par2.equals(""))
                        {
                            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.GREEN + propEntry.getKey() + EnumChatFormatting.WHITE + " is " + EnumChatFormatting.AQUA + propValue));
                            doNotShowHelp = true;
                            break OUTER;
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
            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.DARK_GREEN + "--- Showing MoCreatures help page " + Integer.valueOf(j + 1) + " of " + Integer.valueOf(i + 1) + "(/moc help <page>)---"));

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
            MoCreatures.proxy.readConfigValues();
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
        sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("\u00a72Listing MoCreatures commands"));
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

        sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(EnumChatFormatting.DARK_GREEN + "--- Showing MoCreatures Help Info " + EnumChatFormatting.AQUA + Integer.valueOf(j + 1) + EnumChatFormatting.WHITE + " of " + EnumChatFormatting.AQUA + Integer.valueOf(x + 1) + EnumChatFormatting.GRAY + " (/moc " + par1 + " " + par2 + " <page>)" + EnumChatFormatting.DARK_GREEN + "---"));

        for (int l = j * pagelimit; l < k; ++l)
        {
            String tamedInfo = list.get(l);
            sender.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey(tamedInfo));
        }
    }
}