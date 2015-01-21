package drzhark.mocreatures.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;

public class MoCEntityTameableAmbient extends MoCEntityAmbient implements IMoCTameable
{
    public MoCEntityTameableAmbient(World world)
    {
        super(world);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(30, -1); // PetId    
    }

    public int getOwnerPetId()
    {
        return dataWatcher.getWatchableObjectInt(30);
    }

    public void setOwnerPetId(int i)
    {
        dataWatcher.updateObject(30, i);
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        //before ownership check 
        if ((itemstack != null) && getIsTamed() && ((itemstack.getItem() == MoCreatures.scrollOfOwner)) 
                && MoCreatures.proxy.enableResetOwnership && MoCTools.isThisPlayerAnOP(entityplayer))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwner("");
                
            }
            return true;
        }
        //if the player interacting is not the owner, do nothing!
        if (MoCreatures.proxy.enableOwnership && getOwnerName() != null && !getOwnerName().equals("") && !entityplayer.getCommandSenderName().equals(getOwnerName()) && !MoCTools.isThisPlayerAnOP(entityplayer)) 
        {
            return true; 
        }

        //changes name
        if (MoCreatures.isServer() && itemstack != null && getIsTamed() && (itemstack.getItem() == MoCreatures.medallion|| itemstack.getItem() == Items.book|| itemstack.getItem() == Items.name_tag))
        {
            if (MoCTools.tameWithName(entityplayer, this))
            {
                return true;
            }
            return false;
        }
        
        //sets it free, untamed
        if ((itemstack != null) && getIsTamed() 
                && ((itemstack.getItem() == MoCreatures.scrollFreedom)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwner("");
                this.setName("");
                this.dropMyStuff();
                this.setTamed(false);
            }

            return true;
        }

        //removes owner, any other player can claim it by renaming it
        if ((itemstack != null) && getIsTamed() 
                    && ((itemstack.getItem() == MoCreatures.scrollOfSale)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwner("");
            }
            return true;
        }

        if ((itemstack != null) && getIsTamed() && isMyHealFood(itemstack))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            worldObj.playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            if (MoCreatures.isServer())
            {
                this.setHealth(getMaxHealth());
            }
            return true;
        }
        
        //stores in fishnet
        if (itemstack != null && itemstack.getItem() == MoCreatures.fishnet && itemstack.getItemDamage() == 0 && this.canBeTrappedInNet()) 
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            if (MoCreatures.isServer())
            {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerName());
                if (petData != null)
                {
                    petData.setInAmulet(this.getOwnerPetId(), true);
                }
                MoCTools.dropAmulet((IMoCTameable) this, 1);
                this.isDead = true;
            }

            return true;
        }
        
        
      //heals
        if ((itemstack != null) && getIsTamed() && isMyHealFood(itemstack))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            worldObj.playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            if (MoCreatures.isServer())
            {
                this.setHealth(getMaxHealth());
            }
            return true;
        }

        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == Items.shears))
        {
            if (MoCreatures.isServer())
            {
                dropMyStuff();
            }
            
            return true;
        }

       
        return super.interact(entityplayer);
    }

    // Fixes despawn issue when chunks unload and duplicated mounts when disconnecting on servers
    @Override
    public void setDead()
    {
        if (MoCreatures.isServer() && getIsTamed() && getHealth() > 0 && !this.riderIsDisconnecting)
        {
            return;
        }
        super.setDead();
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    public void playTameEffect(boolean par1)
    {
        String s = "heart";

        if (!par1)
        {
            s = "smoke";
        }

        for (int i = 0; i < 7; ++i)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(s, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        if (getOwnerPetId() != -1)
            nbttagcompound.setInteger("PetId", this.getOwnerPetId());
        if (this instanceof IMoCTameable && getIsTamed() && MoCreatures.instance.mapData != null)
        {
            MoCreatures.instance.mapData.updateOwnerPet((IMoCTameable)this, nbttagcompound);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.hasKey("PetId"))
            setOwnerPetId(nbttagcompound.getInteger("PetId"));
        if (this.getIsTamed() && nbttagcompound.hasKey("PetId"))
        {
            MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerName());
            if (petData != null)
            {
                NBTTagList tag = petData.getOwnerRootNBT().getTagList("TamedList", 10);
                for (int i = 0; i < tag.tagCount(); i++)
                {
                    NBTTagCompound nbt = (NBTTagCompound)tag.getCompoundTagAt(i);
                    if (nbt.getInteger("PetId") == nbttagcompound.getInteger("PetId"))
                    {
                        // update amulet flag
                        nbt.setBoolean("InAmulet", false);
                        // check if cloned and if so kill
                        if (nbt.hasKey("Cloned"))
                        {
                            // entity was cloned
                            nbt.removeTag("Cloned"); // clear flag
                            this.setTamed(false);
                            this.setDead();
                        }
                    }
                }
            }
            else // no pet data was found, mocreatures.dat could of been deleted so reset petId to -1
            {
                this.setOwnerPetId(-1);
            }
        }
    }

    @Override
    public float getPetHealth() {
        return this.getHealth();
    }

    @Override
    public boolean isRiderDisconnecting() {
        return this.riderIsDisconnecting;
    }
}