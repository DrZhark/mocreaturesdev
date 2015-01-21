package drzhark.mocreatures.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
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
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;

public class CommandMoCTP extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();

    static {
        commands.add("/moctp <entityid> <playername>");
        commands.add("/moctp <petname> <playername>");
        aliases.add("moctp");
       //tabCompletionStrings.add("moctp");
    }

    public String getCommandName()
    {
        return "moctp";
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
        return "commands.moctp.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] charArray)
    {
        int petId = 0;
        if (charArray == null || charArray.length == 0)
        {
            par1ICommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "Error" + EnumChatFormatting.WHITE + ": You must enter a valid entity ID."));
            return;
        }
        try 
        {
            petId = Integer.parseInt(charArray[0]);
        }
        catch (NumberFormatException e)
        {
            petId = -1;
        }
        String playername = par1ICommandSender.getCommandSenderName();
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
                        double posX = nbt.getTagList("Pos", 6).func_150309_d(0);
                        double posY = nbt.getTagList("Pos", 6).func_150309_d(1);
                        double posZ = nbt.getTagList("Pos", 6).func_150309_d(2);
                        int x = MathHelper.floor_double( posX );
                        int y = MathHelper.floor_double( posY );
                        int z = MathHelper.floor_double( posZ );
                        par1ICommandSender.addChatMessage(new ChatComponentTranslation("Found unloaded pet " + EnumChatFormatting.GREEN + nbt.getString("id") + EnumChatFormatting.WHITE + " with name " + EnumChatFormatting.AQUA + nbt.getString("Name") + EnumChatFormatting.WHITE + " at location " + EnumChatFormatting.LIGHT_PURPLE + x + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + y + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + z + EnumChatFormatting.WHITE + " with Pet ID " + EnumChatFormatting.BLUE + nbt.getInteger("PetId")));
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
}