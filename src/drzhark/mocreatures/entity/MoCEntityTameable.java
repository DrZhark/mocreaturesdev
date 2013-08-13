package drzhark.mocreatures.entity;

import java.util.List;
import java.util.Map;

import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class MoCEntityTameable extends MoCEntityAnimal implements IMoCTameable
{
    public MoCEntityTameable(World world)
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
        if ((itemstack != null) && getIsTamed() && ((itemstack.itemID == MoCreatures.scrollOfOwner.itemID)) 
                && MoCreatures.proxy.enableResetOwnership && MoCTools.isThisPlayerAnOP(entityplayer))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (MoCreatures.proxy.enableOwnership) 
                {
                    EntityPlayer epOwner = this.worldObj.getPlayerEntityByName(this.getOwnerName());
                    if (epOwner != null)
                    {
                        MoCTools.reduceTamedByPlayer(epOwner);
                    }
                    else
                    {
                        MoCTools.reduceTamedByOfflinePlayer(this.getOwnerName());
                    }
                }
                if (this.getEntityData().hasKey("PetId")) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getEntityData().getInteger("PetId"));//this.getOwnerPetId());
                }
                this.setOwner("");
            }
            return true;
        }
        //if the player interacting is not the owner, do nothing!
        if (MoCreatures.proxy.enableOwnership && getOwnerName() != null && !getOwnerName().equals("") && !entityplayer.username.equals(getOwnerName())) 
        {
            return true; 
        }

        //changes name
        if ((itemstack != null) && getIsTamed() //&& MoCreatures.isServer()
                && ((itemstack.itemID == MoCreatures.medallion.itemID) || (itemstack.itemID == Item.book.itemID)))
        {
            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
            }
            return true;
        }
        
        //sets it free, untamed
        if ((itemstack != null) && getIsTamed() 
                && ((itemstack.itemID == MoCreatures.scrollFreedom.itemID)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (MoCreatures.proxy.enableOwnership) MoCTools.reduceTamedByPlayer(entityplayer);
                System.out.println("hasKey PetId ? " + this.getEntityData().hasKey("PetId"));
                if (this.getEntityData().hasKey("PetId")) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getEntityData().getInteger("PetId"));//this.getOwnerPetId());
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
                    && ((itemstack.itemID == MoCreatures.scrollOfSale.itemID)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (MoCreatures.proxy.enableOwnership) MoCTools.reduceTamedByPlayer(entityplayer);
                if (this.getEntityData().hasKey("PetId")) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getEntityData().getInteger("PetId"));//this.getOwnerPetId());
                }
                this.setOwner("");
            }
            return true;
        }
        
        //stores in petAmulet
        if (itemstack != null && itemstack.itemID == MoCreatures.superAmulet.itemID && this.canBeTrappedInNet()) 
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            if (MoCreatures.isServer())
            {
            	this.dropMyStuff();
                MoCTools.dropAmulet(this, 2);
                this.isDead = true;
            }

            return true;
        }
        
        if ((itemstack != null) && getIsTamed() && (itemstack.itemID == Item.shears.itemID))
        {
            if (MoCreatures.isServer())
            {
                dropMyStuff();
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
            worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            if (MoCreatures.isServer())
            {
                health = getMaxHealth();
            }
            return true;
        }
        
        return super.interact(entityplayer);
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        if (this.getIsTamed())
            System.out.println("onDeath for entity " + this + " with name " + this.getName());
        if (MoCreatures.proxy.enableOwnership && this.getIsTamed() && (this.getOwnerName() != null) && MoCreatures.isServer())
        {
            EntityPlayer ep = worldObj.getPlayerEntityByName(this.getOwnerName());
            if (ep != null)
            {
                MoCTools.reduceTamedByPlayer(ep);
            }
            else
            {
                MoCTools.reduceTamedByOfflinePlayer(getOwnerName());
            }
        }
        if (this.getEntityData().hasKey("PetId")) // required since getInteger will always return 0 if no key is found
        {
            MoCreatures.instance.mapData.removeOwnerPet(this, this.getEntityData().getInteger("PetId"));//this.getOwnerPetId());
        }
        super.onDeath(damagesource);
    }

    // Fixes despawn issue when chunks unload and duplicated mounts when disconnecting on servers
    @Override
    public void setDead()
    {
        if (this.getIsTamed())
            System.out.println("setDead for entity " + this + " with name " + this.getName());
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (MoCreatures.isServer() && getIsTamed() && this.health > 0 && !this.riderIsDisconnecting)
        {
            return;
        }
        if (MoCreatures.isServer() && getIsTamed() && this.getEntityData().hasKey("PetId")) // required since getInteger will always return 0 if no key is found
        {
            System.out.println("SETTING " + this + " with name " + this.getName() + " to DEAD!!");
            this.setTamed(false);
            MoCreatures.instance.mapData.removeOwnerPet(this, this.getEntityData().getInteger("PetId"));//this.getOwnerPetId());
        }
        super.setDead();
    }

    @Override
    protected boolean canDespawn()
    {
        return !this.getIsTamed();
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
        if (this instanceof IMoCTameable && getIsTamed())
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
        if (this.getIsTamed() && nbttagcompound.hasKey("PetId"))//this.getEntityData().hasKey("PetId"))
        {
           // System.out.println("mapdata = " + MoCreatures.instance.mapData);
            System.out.println("PET ID = " + nbttagcompound.getInteger("PetId"));
            MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerName());
            NBTTagList tag = petData.getPetData().getTagList("TamedList");
            for (int i = 0; i < tag.tagCount(); i++)
            {
                System.out.println("found tag " + tag.tagAt(i));
                NBTTagCompound nbt = (NBTTagCompound)tag.tagAt(i);
                if (nbt.getInteger("PetId") == nbttagcompound.getInteger("PetId"))
                {
                    // check if cloned and if so kill
                    if (nbt.hasKey("Cloned"))
                    {
                        // entity was cloned
                        System.out.println("CLONED!!, killing self");
                        nbt.removeTag("Cloned"); // clear flag
                        this.setTamed(false);
                        this.setDead();
                    }
                }
            }
        }
    }
}