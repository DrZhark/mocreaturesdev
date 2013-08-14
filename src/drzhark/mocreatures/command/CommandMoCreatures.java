package drzhark.mocreatures.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cpw.mods.fml.common.FMLCommonHandler;

import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.MoCBiomeData;
import drzhark.mocreatures.MoCBiomeGroupData;
import drzhark.mocreatures.MoCConfigCategory;
import drzhark.mocreatures.MoCConfiguration;
import drzhark.mocreatures.MoCEntityModData;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCProxy;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCProperty;
import drzhark.mocreatures.MoCProperty.Type;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraftforge.common.DimensionManager;

public class CommandMoCreatures extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();
    private static List<String> commandsCustomSpawner = new ArrayList<String>();
    private static Map<String, String> commmentMap = new TreeMap<String, String>();

    static {
        commands.add("/moc ambientspawntickrate <int>");
        commands.add("/moc attackdolphins <boolean>");
        commands.add("/moc attackhorses <boolean>");
        commands.add("/moc attackwolves <boolean>");
        commands.add("/moc caveogrechance <float>");
        commands.add("/moc caveogrestrength <float>");
        commands.add("/moc creaturespawntickrate <int>");
        commands.add("/moc checkambientlightlevel <boolean>");
        commands.add("/moc debugCMS <boolean>");
        commands.add("/moc debuglogging <boolean>");
        commands.add("/moc deletepets <playername>");
        commands.add("/moc despawnlightlevel <boolean>");
        commands.add("/moc despawnvanilla <boolean>");
        commands.add("/moc despawntickrate <int>");
        commands.add("/moc destroydrops <boolean>");
        commands.add("/moc disallowmonsterspawningduringday <boolean>");
        commands.add("/moc enforcemaxspawnlimits <boolean>");
        commands.add("/moc fireogrechance <int>");
        commands.add("/moc fireogrestrength <float>");
        commands.add("/moc easybreeding <boolean>");
        commands.add("/moc elephantbulldozer <boolean>");
        commands.add("/moc enableownership <boolean>");
        commands.add("/moc enableresetownerscroll <boolean>");
        commands.add("/moc entityspawnrange <int>");
        commands.add("/moc golemsdestroyblocks <boolean>");
        commands.add("/moc lightlevel <int>");
        commands.add("/moc list biomegroups");
        commands.add("/moc list <tag> <entity> biomegroups");
        commands.add("/moc list tags");
        commands.add("/moc list tamed");
        commands.add("/moc list tamed <playername>");
        commands.add("/moc maxambients <int>");
        commands.add("/moc maxcreatures <int>");
        commands.add("/moc maxmonsters <int>");
        commands.add("/moc maxtamedperop <int>");
        commands.add("/moc maxtamedperplayer <int>");
        commands.add("/moc maxwatercreatures <int>");
        commands.add("/moc modifyvanillaspawns <boolean>");
        commands.add("/moc monsterspawntickrate <int>");
        commands.add("/moc ogreattackrange <int>");
        commands.add("/moc ogrestrength <float>");
        commands.add("/moc spawnambients <boolean>");
        commands.add("/moc spawncreatures <boolean>");
        commands.add("/moc spawnmonsters <boolean>");
        commands.add("/moc spawnpiranhas <boolean>");
        commands.add("/moc spawnwatercreatures <boolean>");
        commands.add("/moc tag");
        commands.add("/moc <tag> <entity> biomegroup add <group>");
        commands.add("/moc <tag> <entity> biomegroup remove <group>");
        commands.add("/moc <tag> <entity> frequency <int>");
        commands.add("/moc <tag> <entity> min <int>");
        commands.add("/moc <tag> <entity> max <int>");
        commands.add("/moc tamedcount <playername>");
        commands.add("/moc tp <entityid> <playername>");
        commands.add("/moc tp <petname> <playername>");
        commands.add("/moc usecustomspawner <boolean>");
        commands.add("/moc waterspawntickrate <int>");
        commands.add("/moc worldgencreaturespawning <boolean>");
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
        return par1ICommandSender.translateString("commands.mocreatures.usage", new Object[0]);
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
                        if (par2.equalsIgnoreCase("tags"))
                        {
                            for (Map.Entry<String, MoCEntityModData> modEntry : MoCreatures.proxy.entityModMap.entrySet())
                            {
                                par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + modEntry.getKey() + EnumChatFormatting.WHITE + " uses tag " + EnumChatFormatting.LIGHT_PURPLE + modEntry.getValue().getModTag());
                            }

                            doNotShowHelp = true;
                            break OUTER;
                        }
                        if (par2.equalsIgnoreCase("biomegroups") || par2.equalsIgnoreCase("bg"))
                        {
                            for (Map.Entry<String, MoCBiomeGroupData> biomeGroupEntry: MoCreatures.proxy.biomeGroupMap.entrySet())
                            {
                                par1ICommandSender.sendChatToPlayer(EnumChatFormatting.AQUA + biomeGroupEntry.getKey());
                            }
                            doNotShowHelp = true;
                            break OUTER;
                        }
                        else if (par2.equalsIgnoreCase("tamed") || par2.equalsIgnoreCase("tame"))
                        {
                            if (par2ArrayOfStr.length > 2)
                            {
                                int entityCount = 0;
                                ArrayList foundIds = new ArrayList();
                                String playername = par2ArrayOfStr[2];
                                List players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
                                for (int i = 0; i < players.size(); i++)
                                {
                                    EntityPlayerMP player = (EntityPlayerMP) players.get(i);
                                    if (player.username.equalsIgnoreCase(playername))
                                    {
                                        // search for tamed entity
                                        for (int dimension : DimensionManager.getIDs())
                                        {
                                            WorldServer world = DimensionManager.getWorld(dimension);
                                            for (int j = 0; j < world.loadedEntityList.size(); j++)
                                            {
                                                Entity entity = (Entity) world.loadedEntityList.get(j);
                                                // search for entities that are MoCEntityAnimal's
                                                if (MoCEntityAnimal.class.isAssignableFrom(entity.getClass()))
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
                                                            entityCount++;
                                                            foundIds.add(compound.getCompoundTag("ForgeData").getInteger("PetId"));
                                                            par1ICommandSender.sendChatToPlayer("Found pet " + EnumChatFormatting.GREEN + entity.getEntityName() + " with name " + EnumChatFormatting.AQUA + name + EnumChatFormatting.WHITE + " at location " + Math.round(entity.posX) + ", " + Math.round(entity.posY) + ", " + Math.round(entity.posZ) + " with ID " + entity.entityId);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(playername);
                                        if (ownerPetData != null)
                                        {
                                            for (i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
                                            {
                                                NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().tagAt(i);
                                                if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId")))
                                                {
                                                    entityCount++;
                                                    double posX = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(0)).data;
                                                    double posY = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(1)).data;
                                                    double posZ = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(2)).data;
                                                    par1ICommandSender.sendChatToPlayer("Found unloaded pet " + EnumChatFormatting.GREEN + nbt.getString("id") + EnumChatFormatting.WHITE + " with name " + EnumChatFormatting.AQUA + nbt.getString("Name") + EnumChatFormatting.WHITE + " at location " + Math.round(posX) + ", " + Math.round(posY) + ", " + Math.round(posZ) + " with Pet ID " + EnumChatFormatting.BLUE + nbt.getInteger("PetId"));
                                                }
                                            }
                                        }
                                        par1ICommandSender.sendChatToPlayer("Listed tamed count : " + EnumChatFormatting.AQUA + entityCount + EnumChatFormatting.WHITE + ", Recorded count : " + EnumChatFormatting.AQUA + player.getEntityData().getInteger("NumberTamed"));
                                        doNotShowHelp = true;
                                        break OUTER;
                                    }
                                }
                                par1ICommandSender.sendChatToPlayer("Player " + EnumChatFormatting.GREEN + playername + EnumChatFormatting.WHITE + " does not have any tamed animals.");
                                doNotShowHelp = true;
                                break OUTER;
                            }
                            else
                            {
                                String playername = par1ICommandSender.getCommandSenderName();
                                List players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
                                int entityCount = 0;
                                ArrayList foundIds = new ArrayList();
                                for (int i = 0; i < players.size(); i++)
                                {
                                    EntityPlayerMP player = (EntityPlayerMP) players.get(i);
                                    if (player.username.equalsIgnoreCase(playername))
                                    {
                                        // search for mocreature tamed entities
                                        for (int dimension : DimensionManager.getIDs())
                                        {
                                            WorldServer world = DimensionManager.getWorld(dimension);
                                            for (int j = 0; j < world.loadedEntityList.size(); j++)
                                            {
                                                Entity entity = (Entity) world.loadedEntityList.get(j);
                                                // search for entities that are MoCEntityAnimal's
                                                if (MoCEntityAnimal.class.isAssignableFrom(entity.getClass()))
                                                {
                                                    // grab the entity data
                                                    NBTTagCompound compound = new NBTTagCompound();
                                                    entity.writeToNBT(compound);
                                                   // System.out.println("found entity " + entity);
                                                    if (compound != null && compound.getString("Owner") != null)
                                                    {
                                                        String owner = compound.getString("Owner");
                                                        String name = compound.getString("Name");
                                                        //System.out.println("owner = " + owner + ", name = " + name);
                                                        if ((owner != null && !owner.equalsIgnoreCase("")) || (name != null && !name.equalsIgnoreCase("")))
                                                        {
                                                            entityCount++;
                                                            foundIds.add(compound.getCompoundTag("ForgeData").getInteger("PetId"));
                                                            par1ICommandSender.sendChatToPlayer("Found pet " + EnumChatFormatting.GREEN + entity.getEntityName() + EnumChatFormatting.WHITE + " with name " + EnumChatFormatting.AQUA + name + EnumChatFormatting.WHITE + " at location " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(entity.posZ) + EnumChatFormatting.WHITE + " with ID " + EnumChatFormatting.BLUE + entity.entityId + EnumChatFormatting.WHITE + " in dimension " + EnumChatFormatting.GRAY + dimension);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(playername);
                                        if (ownerPetData != null)
                                        {
                                            for (i = 0; i < ownerPetData.getTamedList().tagCount(); i++)
                                            {
                                                NBTTagCompound nbt = (NBTTagCompound)ownerPetData.getTamedList().tagAt(i);
                                                if (nbt.hasKey("PetId") && !foundIds.contains(nbt.getInteger("PetId")))
                                                {
                                                    entityCount++;
                                                    double posX = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(0)).data;
                                                    double posY = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(1)).data;
                                                    double posZ = ((NBTTagDouble)nbt.getTagList("Pos").tagAt(2)).data;
                                                    par1ICommandSender.sendChatToPlayer("Found unloaded pet " + EnumChatFormatting.GREEN + nbt.getString("id") + EnumChatFormatting.WHITE + " with name " + EnumChatFormatting.AQUA + nbt.getString("Name") + EnumChatFormatting.WHITE + " at location " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ) + EnumChatFormatting.WHITE + " with Pet ID " + EnumChatFormatting.BLUE + nbt.getInteger("PetId"));
                                                }
                                            }
                                        }
                                        par1ICommandSender.sendChatToPlayer("Listed tamed count : " + EnumChatFormatting.AQUA + entityCount + EnumChatFormatting.WHITE); //+ ", Recorded count : " + EnumChatFormatting.AQUA + player.getEntityData().getInteger("NumberTamed"));
                                        doNotShowHelp = true;
                                        break OUTER;
                                    }
                                }
                            }
                        }
                        else if (par2ArrayOfStr.length == 4)// handle entity biomegroup listings
                        {
                            String tag = par2ArrayOfStr[1];
                            String name = par2ArrayOfStr[2];
                            for (Map.Entry<String, MoCEntityModData> modEntry : MoCreatures.proxy.entityModMap.entrySet())
                            {
                                if (modEntry.getValue().getModTag().equalsIgnoreCase(tag))
                                {
                                    MoCEntityData entityData = modEntry.getValue().getCreature(name);
                                    if (entityData != null)
                                    {
                                        for (int i = 0; i < entityData.getBiomeGroups().size(); i++)
                                        {
                                            par1ICommandSender.sendChatToPlayer(entityData.getBiomeGroups().get(i));
                                        }
                                        doNotShowHelp = true;
                                        break OUTER;
                                    }
                                }
                            }
                            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Entity " + EnumChatFormatting.GREEN + par2 + EnumChatFormatting.RED + " is invalid. Please enter a valid entity.");
                            doNotShowHelp = true;
                            break OUTER;
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
                            //par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "INVALID ENTITY ID");
                            //doNotShowHelp = true;
                            //break OUTER;
                            entityId = -1;
                            petName = par2;
                        }
                        String playername = par2ArrayOfStr[2];
                        System.out.println("petName = " + petName + ", playername = " + playername);
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
                                            if (entity.entityId == entityId || ((MoCEntityAnimal)entity).getName().equalsIgnoreCase(petName))
                                            {
                                                System.out.println("petName = " + petName + ", id = " + entityId);
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
                                                        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + name + EnumChatFormatting.WHITE + " has been tp'd to location " + Math.round(player.posX) + ", " + Math.round(player.posY) + ", " + Math.round(player.posZ) + " in dimension " + player.dimension);
                                                        doNotShowHelp = true;
                                                        break OUTER;
                                                    }
                                                    par1ICommandSender.sendChatToPlayer("Unable to transfer entity ID " + EnumChatFormatting.GREEN + entityId + EnumChatFormatting.WHITE + ". It may only be transferred to " + EnumChatFormatting.AQUA + owner);
                                                    doNotShowHelp = true;
                                                    break OUTER;
                                                }
                                            }
                                        }
                                    } // end for
                                } // end for
                                // search for tamed entity in mocreatures.dat
                                MoCPetData ownerPetData = MoCreatures.instance.mapData.getPetData(playername);
                                System.out.println("petData = " + ownerPetData);
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
                                            par1ICommandSender.sendChatToPlayer("Found unloaded pet " + EnumChatFormatting.GREEN + nbt.getString("id") + EnumChatFormatting.WHITE + " with name " + EnumChatFormatting.AQUA + nbt.getString("Name") + EnumChatFormatting.WHITE + " at location " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posX) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posY) + EnumChatFormatting.WHITE + ", " + EnumChatFormatting.LIGHT_PURPLE + Math.round(posZ) + EnumChatFormatting.WHITE + " with Pet ID " + EnumChatFormatting.BLUE + nbt.getInteger("PetId"));
                                            if (!player.worldObj.isRemote)// transfer entity to player dimension
                                            {
                                                System.out.println("ENTITY CLASS = " + nbt.getString("id"));
                                                Entity newEntity = EntityList.createEntityByName(nbt.getString("id"), player.worldObj);
                                                System.out.println("CREATED ENTITY " + newEntity);
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
                                par1ICommandSender.sendChatToPlayer("Tamed entity could not be located.");
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
                            NBTTagCompound compound = player.getEntityData();
                            if (compound != null && compound.hasKey("NumberTamed"))
                            {
                                int tamedCount = compound.getInteger("NumberTamed");
                                par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + playername + "'s recorded tamed count is " + EnumChatFormatting.AQUA + tamedCount);
                                doNotShowHelp = true;
                                break OUTER;
                            }
                        }
                    }
                    par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Could not find player " + EnumChatFormatting.GREEN + playername + EnumChatFormatting.RED + ". Please verify the player is online and/or name was entered correctly.");
                    doNotShowHelp = true;
                    break OUTER;
                }
                else if (par1.equalsIgnoreCase("killall"))
                {
                    if (!MoCProxy.entityMap.containsKey(par2))
                    {
                        String list = "";
                        List<String> entityTypes = new ArrayList();
                        par1ICommandSender.sendChatToPlayer("Must specify a valid entity type to kill. Current types are : ");
                        for (Map.Entry<String, MoCEntityData> entityEntry : MoCProxy.entityMap.entrySet())
                        {
                            MoCEntityData entityData = entityEntry.getValue();
                            entityTypes.add(EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE + "|" + EnumChatFormatting.GREEN + entityData.getEntityName());
                        }
                        Collections.sort(entityTypes);
                        for (int i = 0; i < entityTypes.size(); i++)
                        {
                            if (i == entityTypes.size() - 1)
                                list += entityTypes.get(i) + ".";
                            else list += entityTypes.get(i) + ", ";
                        }
                        par1ICommandSender.sendChatToPlayer(list);
                        doNotShowHelp = true;
                        break OUTER;
                    }
                    else {
                        // get entity type
                        MoCEntityData entityData = MoCProxy.entityMap.get(par2);
                        String playername = par1ICommandSender.getCommandSenderName();
                        List players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
                        for (int i = 0; i < players.size(); i++)
                        {
                            EntityPlayerMP player = (EntityPlayerMP) players.get(i);
                            if (player.username.equalsIgnoreCase(playername))
                            {
                                int count = 0;
                                for (int j = 0; j < player.worldObj.loadedEntityList.size(); j++)
                                {
                                    Entity entity = (Entity) player.worldObj.loadedEntityList.get(j);
                                    if (entityData.getEntityClass().isInstance(entity))
                                    {
                                        // check if one of ours
                                        if (entity instanceof IMoCEntity)
                                        {
                                            IMoCEntity mocreature = (IMoCEntity)entity;
                                            if (!mocreature.getIsTamed())
                                            {
                                                entity.isDead = true;
                                                entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                                count++;
                                            }
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
                                            entity.isDead = true;
                                            entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                            count++;
                                        }
                                    }
                                }
                                par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + " " + EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE  + "|" + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + ".");
                                doNotShowHelp = true;
                                break OUTER;
                            }
                        }
                    }
                }
                // START ENTITY FREQUENCY/BIOME SECTION
                else if (par2ArrayOfStr.length >=3 && (par3.equalsIgnoreCase("frequency") || par3.equalsIgnoreCase("min") || par3.equalsIgnoreCase("max") || par3.equalsIgnoreCase("chunk") || par3.equalsIgnoreCase("biomegroup") || par3.equalsIgnoreCase("bg")))
                {
                    String tag = par2ArrayOfStr[0];
                    String name = par2ArrayOfStr[1];
                    String par4 = null;
                    if (par2ArrayOfStr.length >=4)
                        par4 = par2ArrayOfStr[3];
                    for (Map.Entry<String, MoCEntityModData> modEntry : MoCreatures.proxy.entityModMap.entrySet())
                    {
                        if (modEntry.getValue().getModTag().equalsIgnoreCase(tag))
                        {
                            MoCEntityData entityData = modEntry.getValue().getCreature(name);
                            if (entityData != null)
                            {
                                if (par3.equalsIgnoreCase("frequency"))
                                {
                                    if (par4 == null)
                                    {
                                        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency is " + EnumChatFormatting.AQUA + entityData.getFrequency() + EnumChatFormatting.WHITE + ".");
                                        doNotShowHelp = true;
                                    }
                                    else {
                                        try 
                                        {
                                            entityData.setFrequency(Integer.parseInt(par4));
                                            MoCProperty prop = entityData.getEntityConfig().get(MoCreatures.proxy.CATEGORY_ENTITY_SPAWN_SETTINGS, entityData.getEntityName());
                                            prop.valueList.set(0, par4);
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " frequency to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + ".");
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
                                        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMinSpawn() + EnumChatFormatting.WHITE + ".");
                                        doNotShowHelp = true;
                                    }
                                    else {
                                        try 
                                        {
                                            entityData.setMinSpawn(Integer.parseInt(par4));
                                            MoCProperty prop = entityData.getEntityConfig().get(MoCreatures.proxy.CATEGORY_ENTITY_SPAWN_SETTINGS, entityData.getEntityName());
                                            prop.valueList.set(1, par4);
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " minGroupSpawn to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + ".");
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
                                        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn is " + EnumChatFormatting.AQUA + entityData.getMaxSpawn() + EnumChatFormatting.WHITE + ".");
                                        doNotShowHelp = true;
                                    }
                                    else {
                                        try 
                                        {
                                            entityData.setMaxSpawn(Integer.parseInt(par4));
                                            MoCProperty prop = entityData.getEntityConfig().get(MoCreatures.proxy.CATEGORY_ENTITY_SPAWN_SETTINGS, entityData.getEntityName());
                                            prop.valueList.set(2, par4);
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxGroupSpawn to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + ".");
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
                                        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk is " + EnumChatFormatting.AQUA + entityData.getMaxInChunk() + EnumChatFormatting.WHITE + ".");
                                        doNotShowHelp = true;
                                    }
                                    else {
                                        try 
                                        {
                                            entityData.setMaxSpawn(Integer.parseInt(par4));
                                            MoCProperty prop = entityData.getEntityConfig().get(MoCreatures.proxy.CATEGORY_ENTITY_SPAWN_SETTINGS, entityData.getEntityName());
                                            prop.valueList.set(2, par4);
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer("Set " + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + " maxInChunk to " + EnumChatFormatting.AQUA + par4 + EnumChatFormatting.WHITE + ".");
                                        }
                                        catch(NumberFormatException ex)
                                        {
                                            this.sendCommandHelp(par1ICommandSender);
                                        }
                                    }
                                }
                                // handle biome groups
                                else if (par3.equalsIgnoreCase("biomegroup") || par3.equalsIgnoreCase("bg"))
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
                                                if (!MoCreatures.proxy.biomeGroupMap.containsKey(value))
                                                {
                                                    par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Invalid Biome Group entered. Please choose a biome group from the following list : ");
                                                    for (Map.Entry<String, MoCBiomeGroupData> biomeGroupEntry : MoCreatures.proxy.biomeGroupMap.entrySet())
                                                    {
                                                        par1ICommandSender.sendChatToPlayer(biomeGroupEntry.getKey());
                                                    }
                                                    doNotShowHelp = true;
                                                    break OUTER;
                                                }
                                                biomeGroups.add(value);
                                                Collections.sort(biomeGroups);
                                                saved = true;
                                                if (MoCreatures.myCustomSpawner != null)
                                                {
                                                    // update lists
                                                    MoCreatures.myCustomSpawner.updateSpawnListBiomes(entityData.getEntityClass(), entityData.getType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getBiomeGroupSpawnMap(value));
                                                }
                                                par1ICommandSender.sendChatToPlayer("Added biome group " + EnumChatFormatting.GREEN + value + " to entity " + EnumChatFormatting.AQUA + entityData.getEntityName() + EnumChatFormatting.WHITE + ".");
                                                break OUTER;
                                            }
                                            else 
                                            {
                                                par1ICommandSender.sendChatToPlayer("Biome Group " + value + " already exists!!, please choose another from the following list :");
                                                for (int i = 0; i < MoCreatures.proxy.biomeGroupMap.size(); i++)
                                                {
                                                    par1ICommandSender.sendChatToPlayer(MoCreatures.proxy.biomeGroupMap.get(i).getBiomeGroupName());
                                                }
                                            }
                                        }
                                        else if (par4.equalsIgnoreCase("rem") || par4.equalsIgnoreCase("remove"))
                                        {
                                            for (int i = 0; i < biomeGroups.size(); i++)
                                            {
                                                if (value.equals(biomeGroups.get(i)))
                                                {
                                                    biomeGroups.remove(i);
                                                    saved = true;
                                                    if (MoCreatures.myCustomSpawner != null)
                                                    {
                                                        // update lists
                                                        MoCreatures.myCustomSpawner.updateSpawnListBiomes(entityData.getEntityClass(), entityData.getType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getBiomeGroupSpawnMap(value));
                                                    }
                                                    par1ICommandSender.sendChatToPlayer("Removed biome group " + EnumChatFormatting.GREEN + value.toUpperCase() + EnumChatFormatting.WHITE + " from entity " + EnumChatFormatting.AQUA + entityData.getEntityName() + EnumChatFormatting.WHITE + ".");
                                                    break OUTER;
                                                }
                                            }
                                            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Invalid biomegroup entered, please choose from the following list :");
                                            for (int i = 0; i < biomeGroups.size(); i++)
                                            {
                                                par1ICommandSender.sendChatToPlayer(biomeGroups.get(i));
                                            }
                                            doNotShowHelp = true;
                                        }
                                    }
                                    catch(NumberFormatException ex)
                                    {
                                        this.sendCommandHelp(par1ICommandSender);
                                    }
                                    break OUTER;
                                }
                                if (MoCreatures.myCustomSpawner != null && saved == true && (par3.equalsIgnoreCase("frequency") || par3.equalsIgnoreCase("min") || par3.equalsIgnoreCase("max")))
                                {
                                   MoCreatures.myCustomSpawner.updateSpawnListEntry(entityData.getEntityClass(), entityData.getType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn());
                                }
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
                            if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(par1))
                                continue;
                            MoCProperty property = propEntry.getValue();
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
                                    par1ICommandSender.sendChatToPlayer("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + ".");
                                }
                            }
                            else if (propEntry.getValue().getType() == Type.INTEGER)
                            {
                                try {
                                    Integer.parseInt(par2);
                                    property.set(par2);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + ".");
                                }
                                catch (NumberFormatException ex)
                                {
                                    par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Invalid value entered. Please enter a valid number.");
                                }
                                
                            }
                            else if (propEntry.getValue().getType() == Type.DOUBLE)
                            {
                                try {
                                    Double.parseDouble(par2);
                                    property.set(par2);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer("Set " + EnumChatFormatting.GREEN + propEntry.getKey() + " to " + EnumChatFormatting.AQUA + par2 + ".");
                                }
                                catch (NumberFormatException ex)
                                {
                                    par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Invalid value entered. Please enter a valid number.");
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
            if (par1.equalsIgnoreCase("tag") || par1.equalsIgnoreCase("tags"))
            {
                for (Map.Entry<String, MoCEntityModData> modEntry : MoCreatures.proxy.entityModMap.entrySet())
                {
                    par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + modEntry.getKey() + EnumChatFormatting.WHITE + " uses tag " + EnumChatFormatting.LIGHT_PURPLE + modEntry.getValue().getModTag());
                }
                doNotShowHelp = true;
            }
            else if (par1.equalsIgnoreCase("killall"))
            {
                if (par1.equalsIgnoreCase("tag") || par1.equalsIgnoreCase("tags"))
                {
                    for (Map.Entry<String, MoCEntityModData> modEntry : MoCreatures.proxy.entityModMap.entrySet())
                    {
                        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + modEntry.getKey() + EnumChatFormatting.WHITE + " uses tag " + EnumChatFormatting.LIGHT_PURPLE + modEntry.getValue().getModTag());
                    }
                    doNotShowHelp = true;
                }
                else if (par1.equalsIgnoreCase("killall"))
                {
                    boolean killall = false;
                    if (par2.equals("") || par2 == null)
                    {
                        killall = true;
                    }
                    System.out.println("par2 = " + par2);
                    if (!killall && !MoCProxy.entityMap.containsKey(par2))
                    {
                        String list = "";
                        List<String> entityTypes = new ArrayList();
                        par1ICommandSender.sendChatToPlayer("Must specify a valid entity type to kill. Current types are : ");
                        for (Map.Entry<String, MoCEntityData> entityEntry : MoCProxy.entityMap.entrySet())
                        {
                            MoCEntityData entityData = entityEntry.getValue();
                            entityTypes.add(EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE + "|" + EnumChatFormatting.GREEN + entityData.getEntityName());
                        }
                        Collections.sort(entityTypes);
                        for (int i = 0; i < entityTypes.size(); i++)
                        {
                            if (i == entityTypes.size() - 1)
                                list += entityTypes.get(i) + ".";
                            else list += entityTypes.get(i) + ", ";
                        }
                        par1ICommandSender.sendChatToPlayer(list);
                        doNotShowHelp = true;
                    }
                    else {
                        // get entity type
                        MoCEntityData entityData = null;
                        if (!killall)
                            entityData = MoCProxy.entityMap.get(par2);
                        String playername = par1ICommandSender.getCommandSenderName();
                        List players = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
                        for (int i = 0; i < players.size(); i++)
                        {
                            EntityPlayerMP player = (EntityPlayerMP) players.get(i);
                           // if (player.username.equalsIgnoreCase(playername))
                            //{
                                int count = 0;
                                for (int j = 0; j < player.worldObj.loadedEntityList.size(); j++)
                                {
                                    Entity entity = (Entity) player.worldObj.loadedEntityList.get(j);
                                    if (entity instanceof EntityPlayer)
                                        continue;
                                    if (entityData == null)
                                    {
                                        if (entity instanceof IMoCEntity)
                                        {
                                            IMoCEntity mocreature = (IMoCEntity)entity;
                                            if (!mocreature.getIsTamed())
                                            {
                                                entity.isDead = true;
                                                entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                                count++;
                                            }
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
                                            entity.isDead = true;
                                            entity.worldObj.setEntityState(entity, (byte)3);
                                            count++;
                                        }
                                    }
                                    else if (entityData.getEntityClass().isInstance(entity))
                                    {
                                        // check if one of ours
                                        if (entity instanceof IMoCEntity)
                                        {
                                            IMoCEntity mocreature = (IMoCEntity)entity;
                                            if (!mocreature.getIsTamed())
                                            {
                                                entity.isDead = true;
                                                entity.worldObj.setEntityState(entity, (byte)3); // inform the client that the entity is dead
                                                count++;
                                            }
                                        }
                                        else {
                                            entity.isDead = true;
                                            entity.worldObj.setEntityState(entity, (byte)3);
                                            count++;
                                        }
                                    }
                                }
                                if (!killall)
                                    par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + " " + EnumChatFormatting.LIGHT_PURPLE + entityData.getEntityMod().getModTag() + EnumChatFormatting.WHITE  + "|" + EnumChatFormatting.GREEN + entityData.getEntityName() + EnumChatFormatting.WHITE + ".");
                                else par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "Killed " + EnumChatFormatting.AQUA + count + EnumChatFormatting.WHITE + ".");
                                doNotShowHelp = true;
                            //}
                        }
                    }
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
                            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + propEntry.getKey() + EnumChatFormatting.WHITE + " is " + EnumChatFormatting.AQUA + propValue);
                            doNotShowHelp = true;
                            break OUTER;
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
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + "--- Showing MoCreatures help page " + Integer.valueOf(j + 1) + " of " + Integer.valueOf(i + 1) + "(/moc help <page>)---");

            for (int l = j * b0; l < k; ++l)
            {
                String command = list.get(l);
                par1ICommandSender.sendChatToPlayer(command);
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
        sender.sendChatToPlayer("\u00a72Listing MoCreatures commands");
        for (int i = 0; i < commands.size(); i++)
        {
            sender.sendChatToPlayer(commands.get(i));
        }
    }
}