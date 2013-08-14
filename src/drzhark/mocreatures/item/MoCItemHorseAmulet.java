package drzhark.mocreatures.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityTameable;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCServerPacketHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class MoCItemHorseAmulet extends MoCItem {

    private int ageCounter;
    private String name;
    private float health;
    private int edad;
    private int creatureType;
    private int spawnClass;
    private boolean rideable;
    private byte armor;
    private boolean adult;
    private String ownerName;
    private int PetId;
    
    public MoCItemHorseAmulet(int i)
    {
        super(i);
        maxStackSize = 1;
        setHasSubtypes(true);
        ageCounter = 0;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World worldObj, EntityPlayer entityplayer)
    {
        if (++ageCounter < 2) { return itemstack; }
        
        int i = itemstack.getItemDamage();

        if (MoCreatures.isServer())
        {
        	initAndReadNBT(itemstack);
        }
        
        if (spawnClass == 21 || spawnClass == 0) // horses or old amulets
        {
            //dirty fix for old amulets
            spawnClass = 22;
            if (spawnClass == 0 || creatureType == 0)
            {
                creatureType = i;
                spawnClass = 22;
                edad = 100;
                health = 20;
                armor = 0;
                name = "";
                ownerName = "";
                rideable = false;
                adult = true;
            }
            //gives an empty amulet
            if (creatureType == 26 || creatureType == 27 || creatureType == 28)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.amuletbone, 1, 0));
            }
            else if (creatureType == 21 || creatureType == 22)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.amuletghost, 1, 0));
            }
            else if ((creatureType > 47 && creatureType < 60))
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.amuletfairy, 1, 0));
            }
            else if (creatureType == 39 || creatureType == 40)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.amuletpegasus, 1, 0));
            }
        }

        if (i != 0)
        {

            double dist = 3D;
            double newPosY = entityplayer.posY;
            double newPosX = entityplayer.posX - (dist * Math.cos((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = entityplayer.posZ - (dist * Math.sin((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));

            if (MoCreatures.isServer())
            {
                try
                {
                    MoCEntityTameable storedCreature = new MoCEntityHorse(worldObj); 
                    storedCreature.setPosition(newPosX, newPosY, newPosZ);
                    storedCreature.setType(creatureType);
                    storedCreature.setTamed(true);
                    storedCreature.setRideable(rideable);
                    storedCreature.setEdad(edad);
                    storedCreature.setName(name);
                    storedCreature.setArmorType(armor);
                    storedCreature.setEntityHealth((int)health);
                    storedCreature.setAdult(adult);
                    storedCreature.setOwnerPetId(PetId);
                    storedCreature.setOwner(entityplayer.username);

                    //if the player using the amulet is different than the original owner
                    if (MoCreatures.proxy.enableOwnership && ownerName != "" && !(ownerName.equals(entityplayer.username)) && MoCreatures.instance.mapData != null)
                    {
                        MoCPetData oldOwner = MoCreatures.instance.mapData.getPetData(ownerName);
                        MoCPetData newOwner = MoCreatures.instance.mapData.getPetData(entityplayer.username);
                        EntityPlayer epOwner = worldObj.getPlayerEntityByName(entityplayer.username);
                        int maxCount = MoCreatures.proxy.maxTamed;
                        if (MoCTools.isThisPlayerAnOP(epOwner))
                        {
                            maxCount = MoCreatures.proxy.maxOPTamed;
                        }
                        if (newOwner == null)
                        {
                            if (maxCount > 0)
                            {
                                // create new PetData for new owner
                                NBTTagCompound petNBT = new NBTTagCompound();
                                storedCreature.writeEntityToNBT(petNBT);
                                MoCreatures.instance.mapData.updateOwnerPet((IMoCTameable)storedCreature, petNBT);
                            }
                        }
                        else // add pet to existing pet data
                        {
                            if (newOwner.getTamedList().tagCount() < maxCount)
                            {
                                NBTTagCompound petNBT = new NBTTagCompound();
                                storedCreature.writeEntityToNBT(petNBT);
                                MoCreatures.instance.mapData.updateOwnerPet((IMoCTameable)storedCreature, petNBT);
                            }
                        }
                        // remove pet entry from old owner
                        if (oldOwner != null)
                        {
                            for (int j = 0; j < oldOwner.getTamedList().tagCount(); j++)
                            {
                                NBTTagCompound petEntry = (NBTTagCompound)oldOwner.getTamedList().tagAt(j);
                                if (petEntry.getInteger("PetId") == PetId)
                                {
                                    // found match, remove
                                    oldOwner.getTamedList().removeTag(j);
                                }
                            }
                        }
                    }

                    entityplayer.worldObj.spawnEntityInWorld(storedCreature);
                    MoCServerPacketHandler.sendAppearPacket(storedCreature.entityId, worldObj.provider.dimensionId);
                    MoCTools.playCustomSound(storedCreature, "appearmagic", worldObj);
                }catch (Exception ex) 
                {
                    System.out.println("Error spawning creature from amulet " + ex);
                }
            }
            ageCounter = 0;
       }

        return itemstack;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
    	this.PetId = nbt.getInteger("PetId");
        this.creatureType = nbt.getInteger("CreatureType");
        this.health = nbt.getFloat("Health");
        this.edad = nbt.getInteger("Edad");
        this.name = nbt.getString("Name");
        this.spawnClass = nbt.getInteger("SpawnClass");
        this.rideable = nbt.getBoolean("Rideable");
        this.armor = nbt.getByte("Armor");
        this.adult = nbt.getBoolean("Adult");
        this.ownerName = nbt.getString("OwnerName");
    }
    
    public void writeToNBT(NBTTagCompound nbt)
    {
    	nbt.setInteger("PetID", this.PetId);
        nbt.setInteger("CreatureType", this.creatureType);
        nbt.setFloat("Health", this.health);
        nbt.setInteger("Edad", this.edad);
        nbt.setString("Name", this.name);
        nbt.setInteger("SpawnClass", this.spawnClass);
        nbt.setBoolean("Rideable", this.rideable);
        nbt.setByte("Armor", this.armor);
        nbt.setBoolean("Adult", this.adult);
        nbt.setString("OwnerName", this.ownerName);
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	initAndReadNBT(par1ItemStack);
    	par3List.add(EnumChatFormatting.BLUE + this.name);
    	par3List.add(EnumChatFormatting.AQUA + "Owned by " + this.ownerName);
    }
    
    private void initAndReadNBT(ItemStack itemstack)
    {
    	if( itemstack.stackTagCompound == null )
        {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbtcompound = itemstack.stackTagCompound;
        readFromNBT(nbtcompound);
    }
}