package drzhark.mocreatures.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandNotFoundException;
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
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;

public class CommandMoCPets extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();

    static {
        commands.add("/mocpets");
        aliases.add("mocpets");
       //tabCompletionStrings.add("moctp");
    }

    public String getCommandName()
    {
        return "mocpets";
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
        return "commands.mocpets.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] paramArray)
    {
        String command = "";
        if (paramArray.length == 0)
            command = "help";
        else command = paramArray[0];
        String page = "";

        if (paramArray.length == 1)
            page = paramArray[0];

        int unloadedCount = 0;
        int loadedCount = 0;
        ArrayList foundIds = new ArrayList();
        ArrayList<String> tamedlist = new ArrayList<String>();
        String playername = par1ICommandSender.getCommandSenderName();
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
            MoCreatures.instance.mapData.forceSave(); // force save so we get correct information
            for (int i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
            {
                NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().getCompoundTagAt(i);
                if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId")))
                {
                    unloadedCount++;
                    double posX = nbt.getTagList("Pos", 6).func_150309_d(0);
                    double posY = nbt.getTagList("Pos", 6).func_150309_d(1);
                    double posZ = nbt.getTagList("Pos", 6).func_150309_d(2);
                    if (nbt.getBoolean("InAmulet"))
                    {
                        tamedlist.add(EnumChatFormatting.WHITE + "Found unloaded pet in " + EnumChatFormatting.DARK_PURPLE + "AMULET" + EnumChatFormatting.WHITE + " with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + (nbt.getString("id")).replace("MoCreatures.", "") + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Name") + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Owner") + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("PetId") + EnumChatFormatting.WHITE + ".");
                    }
                    else tamedlist.add(EnumChatFormatting.WHITE + "Found unloaded pet with " + EnumChatFormatting.DARK_AQUA + "Type" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + (nbt.getString("id")).replace("MoCreatures.", "") + EnumChatFormatting.DARK_AQUA + ", Name" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Name") + EnumChatFormatting.DARK_AQUA + ", Owner" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getString("Owner") + EnumChatFormatting.DARK_AQUA + ", PetId" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("PetId") + EnumChatFormatting.DARK_AQUA + ", Dimension" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.GREEN + nbt.getInteger("Dimension") + EnumChatFormatting.DARK_AQUA + ", Pos" + EnumChatFormatting.WHITE + ":" + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ));
                }
            }
        }

        if (tamedlist.size() > 0)
        {
            sendPageHelp(par1ICommandSender, (byte)10, tamedlist, paramArray);
            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Loaded tamed count : " + EnumChatFormatting.AQUA + loadedCount + EnumChatFormatting.WHITE + ", Unloaded count : " + EnumChatFormatting.AQUA + unloadedCount + EnumChatFormatting.WHITE + ", Total count : " + EnumChatFormatting.AQUA + (ownerPetData != null ? ownerPetData.getTamedList().tagCount() : 0)));
        }
        else
        {
            par1ICommandSender.addChatMessage(new ChatComponentTranslation("Loaded tamed count : " + EnumChatFormatting.AQUA + loadedCount + EnumChatFormatting.WHITE + (!MoCreatures.isServer() ? ", Unloaded Count : " + EnumChatFormatting.AQUA + unloadedCount + EnumChatFormatting.WHITE + ", Total count : " + EnumChatFormatting.AQUA + (loadedCount + unloadedCount) : "")));
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

    public void sendPageHelp(ICommandSender sender, byte pagelimit, ArrayList<String> list, String[] par2ArrayOfStr)
    {
        int x = (list.size() - 1) / pagelimit;
        boolean flag = false;
        int j = 0;
        String par1 = "";
        if (par2ArrayOfStr.length > 1)
            par1 = par2ArrayOfStr[0];

        if (par2ArrayOfStr.length > 0 && Character.isDigit(par2ArrayOfStr[0].charAt(0)))
        {
            try
            {
                j = par2ArrayOfStr.length == 0 ? 0 : parseIntBounded(sender, par2ArrayOfStr[0], 1, x + 1) - 1;
            }
            catch (NumberInvalidException numberinvalidexception)
            {
                if (par1 != null)
                {
                    throw new WrongUsageException(par1, new Object[0]);
                }

                throw new CommandNotFoundException();
            }
        }
        int k = Math.min((j + 1) * pagelimit, list.size());

        sender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.DARK_GREEN + "--- Showing MoCreatures Help Info " + EnumChatFormatting.AQUA + Integer.valueOf(j + 1) + EnumChatFormatting.WHITE + " of " + EnumChatFormatting.AQUA + Integer.valueOf(x + 1) + EnumChatFormatting.GRAY + " (/mocpets <page>)" + EnumChatFormatting.DARK_GREEN + "---"));

        for (int l = j * pagelimit; l < k; ++l)
        {
            String tamedInfo = list.get(l);
            sender.addChatMessage(new ChatComponentTranslation(tamedInfo));
        }
    }
}