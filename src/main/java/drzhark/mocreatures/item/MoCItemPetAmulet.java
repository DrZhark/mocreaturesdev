package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityTameableAmbient;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityNewBigCat;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import drzhark.mocreatures.utils.MoCLog;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MoCItemPetAmulet extends MoCItem {

    private int ageCounter;
    private String name;
    private float health;
    private int edad;
    private int creatureType;
    private String spawnClass;
    private String ownerName;
    private int amuletType;
    private boolean adult;
    private int PetId;

    public MoCItemPetAmulet(String name) {
        super(name);
        this.maxStackSize = 1;
        setHasSubtypes(true);
    }

    public MoCItemPetAmulet(String name, int type) {
        this(name);
        this.amuletType = type;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World worldObj, EntityPlayer entityplayer) {

        double dist = 1D;
        double newPosY = entityplayer.posY;
        double newPosX = entityplayer.posX - (dist * Math.cos((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = entityplayer.posZ - (dist * Math.sin((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));

        ItemStack emptyAmulet = new ItemStack(MoCreatures.fishnet, 1, 0);
        if (this.amuletType == 1) {
            emptyAmulet = new ItemStack(MoCreatures.petamulet, 1, 0);
        }

        //entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.fishnet, 1, 0));
        if (MoCreatures.isServer()) {
            initAndReadNBT(itemstack);
            if (this.spawnClass.isEmpty())// || creatureType == 0)
            {
                return itemstack;
            }
            try {
                if (this.spawnClass.equalsIgnoreCase("MoCHorse")) {
                    this.spawnClass = "WildHorse";
                }
                if (this.spawnClass.equalsIgnoreCase("BigCat")) {
                    switch (this.creatureType) {
                        case 1:
                            this.spawnClass = "Lion";
                            this.creatureType = 1;//female
                            break;
                        case 2:
                            this.spawnClass = "Lion";
                            this.creatureType = 2;//male
                            break;
                        case 3:
                            this.spawnClass = "Panther";
                            this.creatureType = 1;
                            break;
                        case 4:
                            //cheetah
                            break;
                        case 5:
                            this.spawnClass = "Tiger";
                            this.creatureType = 1;
                            break;
                        case 6:
                            this.spawnClass = "Leopard";
                            this.creatureType = 2;
                            break;
                        case 7:
                            this.spawnClass = "Tiger";
                            this.creatureType = 2;
                            break;
                    }
                }
                if (this.spawnClass.equalsIgnoreCase("Ray")) {
                    switch (this.creatureType) {
                        case 1:
                            this.spawnClass = "MantaRay";
                            break;
                        case 2:
                            this.spawnClass = "StingRay";
                            break;
                    }
                }
                EntityLiving tempLiving = MoCTools.spawnListByNameClass(this.spawnClass, worldObj);
                if (tempLiving != null && tempLiving instanceof IMoCEntity) {
                    IMoCTameable storedCreature = (IMoCTameable) tempLiving;
                    ((EntityLiving) storedCreature).setPosition(newPosX, newPosY, newPosZ);
                    storedCreature.setType(this.creatureType);
                    storedCreature.setTamed(true);
                    storedCreature.setName(this.name);
                    storedCreature.setOwnerPetId(this.PetId);
                    storedCreature.setOwner(entityplayer.getCommandSenderName());
                    ((EntityLiving) storedCreature).setHealth(this.health);
                    storedCreature.setEdad(this.edad);
                    storedCreature.setAdult(this.adult);
                    if (storedCreature instanceof MoCEntityNewBigCat) {
                        ((MoCEntityNewBigCat) storedCreature).setHasAmulet(true);
                    }
                    // special case for kitty
                    if (this.spawnClass.equalsIgnoreCase("Kitty")) {
                        ((MoCEntityKitty) storedCreature).setKittyState(2); // allows name to render
                    }

                    //if the player using the amulet is different than the original owner
                    if (this.ownerName != "" && !(this.ownerName.equals(entityplayer.getCommandSenderName())) && MoCreatures.instance.mapData != null) {
                        MoCPetData oldOwner = MoCreatures.instance.mapData.getPetData(this.ownerName);
                        MoCPetData newOwner = MoCreatures.instance.mapData.getPetData(entityplayer.getCommandSenderName());
                        EntityPlayer epOwner = worldObj.getPlayerEntityByName(entityplayer.getCommandSenderName());
                        int maxCount = MoCreatures.proxy.maxTamed;
                        if (MoCTools.isThisPlayerAnOP(epOwner)) {
                            maxCount = MoCreatures.proxy.maxOPTamed;
                        }
                        if ((newOwner != null && newOwner.getTamedList().tagCount() < maxCount) || (newOwner == null && maxCount > 0)
                                || !MoCreatures.proxy.enableOwnership) {
                            NBTTagCompound petNBT = new NBTTagCompound();
                            if (tempLiving instanceof MoCEntityTameableAnimal) {
                                ((MoCEntityTameableAnimal) storedCreature).writeEntityToNBT(petNBT);
                            } else if (tempLiving instanceof MoCEntityTameableAquatic) {
                                ((MoCEntityTameableAquatic) storedCreature).writeEntityToNBT(petNBT);
                            } else if (tempLiving instanceof MoCEntityTameableAmbient) {
                                ((MoCEntityTameableAmbient) storedCreature).writeEntityToNBT(petNBT);
                            }
                            MoCreatures.instance.mapData.updateOwnerPet(storedCreature, petNBT);
                        }
                        // remove pet entry from old owner
                        if (oldOwner != null) {
                            for (int j = 0; j < oldOwner.getTamedList().tagCount(); j++) {
                                NBTTagCompound petEntry = oldOwner.getTamedList().getCompoundTagAt(j);
                                if (petEntry.getInteger("PetId") == this.PetId) {
                                    // found match, remove
                                    oldOwner.getTamedList().removeTag(j);
                                }
                            }
                        }
                    }

                    if (entityplayer.worldObj.spawnEntityInWorld((EntityLiving) storedCreature)) {
                        MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAppear(((EntityLiving) storedCreature).getEntityId()),
                                new TargetPoint(entityplayer.worldObj.provider.getDimensionId(), entityplayer.posX, entityplayer.posY,
                                        entityplayer.posZ, 64));
                        if ((MoCreatures.proxy.enableOwnership && this.ownerName.isEmpty()) || this.name.isEmpty()) {
                            MoCTools.tameWithName(entityplayer, storedCreature);
                        }

                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, emptyAmulet);
                        MoCPetData petData = MoCreatures.instance.mapData.getPetData(storedCreature.getOwnerName());
                        if (petData != null) {
                            petData.setInAmulet(storedCreature.getOwnerPetId(), false);
                        }
                    }
                }
            } catch (Exception ex) {
                if (MoCreatures.proxy.debug) {
                    MoCLog.logger.warn("Error spawning creature from fishnet/amulet " + ex);
                }
            }
        }

        return itemstack;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.PetId = nbt.getInteger("PetId");
        this.creatureType = nbt.getInteger("CreatureType");
        this.health = nbt.getFloat("Health");
        this.edad = nbt.getInteger("Edad");
        this.name = nbt.getString("Name");
        this.spawnClass = nbt.getString("SpawnClass");
        this.adult = nbt.getBoolean("Adult");
        this.ownerName = nbt.getString("OwnerName");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("PetID", this.PetId);
        nbt.setInteger("CreatureType", this.creatureType);
        nbt.setFloat("Health", this.health);
        nbt.setInteger("Edad", this.edad);
        nbt.setString("Name", this.name);
        nbt.setString("SpawnClass", this.spawnClass);
        nbt.setBoolean("Adult", this.adult);
        nbt.setString("OwnerName", this.ownerName);
    }

    @SideOnly(Side.CLIENT)
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        initAndReadNBT(par1ItemStack);
        if (this.spawnClass != "") {
            par3List.add(EnumChatFormatting.AQUA + this.spawnClass);
        }
        if (this.name != "") {
            par3List.add(EnumChatFormatting.BLUE + this.name);
        }
        if (this.ownerName != "") {
            par3List.add(EnumChatFormatting.DARK_BLUE + "Owned by " + this.ownerName);
        }
    }

    private void initAndReadNBT(ItemStack itemstack) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbtcompound = itemstack.getTagCompound();
        readFromNBT(nbtcompound);
    }
}
