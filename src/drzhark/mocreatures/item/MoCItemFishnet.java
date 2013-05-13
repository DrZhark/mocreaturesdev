package drzhark.mocreatures.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.MoCIMoCreature;
import drzhark.mocreatures.network.MoCServerPacketHandler;

public class MoCItemFishnet extends MoCItem
{
    private Icon[] icons;
    private int ageCounter;
    private String name;
    private int health;
    private int edad;
    private int creatureType;
    private String spawnClass;
    private String ownerName;

    public MoCItemFishnet(int i) 
    {
        super(i);
        maxStackSize = 1;
        setHasSubtypes(true);
        ageCounter = 0;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World worldObj, EntityPlayer entityplayer)
    {
        if (++ageCounter < 2) 
        { 
            return itemstack; 
        }
        
        ageCounter = 0;
        int i = itemstack.getItemDamage();
       
        if (i == 0) //empty fishnet
        {
            return itemstack;
        }

        if (i != 0)
        {

            double dist = 1D;
            double newPosY = entityplayer.posY;
            double newPosX = entityplayer.posX - (dist * Math.cos((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = entityplayer.posZ - (dist * Math.sin((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));

            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.fishnet, 1, 0));
            
            if (MoCreatures.isServer())
            {
                if( itemstack.stackTagCompound == null )
                {
                    itemstack.setTagCompound(new NBTTagCompound());
                }

                NBTTagCompound nbtcompound = itemstack.stackTagCompound;
                readFromNBT(nbtcompound);
                
                if (spawnClass.isEmpty() || creatureType == 0)
                {
                    return itemstack;
                }
                try
                {
                    EntityLiving tempLiving = MoCTools.spawnListByNameClass(spawnClass, worldObj);
                    if (tempLiving != null && tempLiving instanceof MoCIMoCreature)
                    {
                        MoCIMoCreature storedCreature = (MoCIMoCreature) tempLiving;
                        ((EntityLiving)storedCreature).setPosition(newPosX, newPosY, newPosZ);
                        storedCreature.setType(creatureType);
                        storedCreature.setTamed(true);
                        storedCreature.setName(name);

                        //if the player using the amulet is different than the original owner
                        if (MoCreatures.proxy.enableOwnership && ownerName != "" && !(ownerName.equals(entityplayer.username)) )
                        {
                            EntityPlayer epOwner = worldObj.getPlayerEntityByName(ownerName);
                            if (epOwner != null)
                            {
                                MoCTools.reduceTamedByPlayer(epOwner);
                            }
                            else
                            {
                                MoCTools.reduceTamedByOfflinePlayer(ownerName);
                            }
                        }

                        if (MoCreatures.proxy.enableOwnership) storedCreature.setOwner(ownerName);

                        entityplayer.worldObj.spawnEntityInWorld((EntityLiving)storedCreature);
                        MoCServerPacketHandler.sendAppearPacket(((EntityLiving)storedCreature).entityId, worldObj.provider.dimensionId);

                        ((EntityLiving)storedCreature).setEntityHealth(health);
                        storedCreature.setEdad(edad);
                        if ((MoCreatures.proxy.enableOwnership && ownerName.isEmpty()) || name.isEmpty()) 
                        {
                             MoCTools.tameWithName(entityplayer, storedCreature);
                        }
                    }
                }catch (Exception ex) 
                {
                    if (MoCreatures.proxy.debugLogging) MoCreatures.log.warning("Error spawning creature from fishnet " + ex);
                }
            }
       }
        return itemstack;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        this.creatureType = nbt.getInteger("CreatureType");
        this.health = nbt.getInteger("Health");
        this.edad = nbt.getInteger("Edad");
        this.name = nbt.getString("Name");
        this.spawnClass = nbt.getString("SpawnClass");
        this.ownerName = nbt.getString("OwnerName");
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("CreatureType", this.creatureType);
        nbt.setInteger("Health", this.health);
        nbt.setInteger("Edad", this.edad);
        nbt.setString("Name", this.name);
        nbt.setString("SpawnClass", this.spawnClass);
        nbt.setString("OwnerName", this.ownerName);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[2];
        icons[0] = par1IconRegister.registerIcon("mocreatures"+ this.getUnlocalizedName().replaceFirst("item.", ":")); //empty fishnet
        icons[1] = par1IconRegister.registerIcon("mocreatures"+ this.getUnlocalizedName().replaceFirst("item.", ":") + "full"); //fishnet with generic fish
        //TODO add more icons
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int par1)
    {
        if (par1 < 1)
        {
            return icons[0];
        }
        return icons[1];
    }
}