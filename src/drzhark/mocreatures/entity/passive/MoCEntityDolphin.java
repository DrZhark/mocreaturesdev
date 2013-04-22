package drzhark.mocreatures.entity.passive;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityDolphin extends MoCEntityAquatic {

    public int gestationtime;

    public MoCEntityDolphin(World world)
    {
        super(world);
        setSize(1.5F, 0.8F);
        setEdad(80 + rand.nextInt(100));
        //setMaxHealth(30);
        health = 30;
        // unused_flag = true;
        // moveSpeed = 0.9F;

    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            int i = rand.nextInt(100);
            if (i <= 35)
            {
                setType(1);
            }
            else if (i <= 60)
            {
                setType(2);
            }
            else if (i <= 85)
            {
                setType(3);
            }
            else if (i <= 96)
            {
                setType(4);
            }
            else if (i <= 98)
            {
                setType(5);
            }
            else
            {
                setType(6);
            }
        }


    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "dolphin.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "dolphin2.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "dolphin3.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "dolphin4.png";
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "dolphin5.png";
        case 6:
            return MoCreatures.proxy.MODEL_TEXTURE + "dolphin6.png";
        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "dolphin.png";
        }
    }

    @Override
    public int getMaxTemper()
    {

        switch (getType())
        {
        case 1:
            return 50;
        case 2:
            return 100;
        case 3:
            return 150;
        case 4:
            return 200;
        case 5:
            return 250;
        case 6:
            return 300;
        default:
            return 100;
        }
    }

    public int getInitialTemper()
    {
        switch (getType())
        {
        case 1:
            return 50;
        case 2:
            return 100;
        case 3:
            return 150;
        case 4:
            return 200;
        case 5:
            return 250;
        case 6:
            return 300;
        default:
            return 50;
        }
    }

    @Override
    public double getCustomSpeed()
    {
        switch (getType())
        {
        case 1:
            return 1.5D;
        case 2:
            return 2.5D;
        case 3:
            return 3.5D;
        case 4:
            return 4.5D;
        case 5:
            return 5.5D;
        case 6:
            return 6.5D;
        default:
            return 1.5D;
        }
    }

    @Override
    public int getMaxHealth()
    {
        return 30;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // byte isHungry 0 = false 1 = true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // byte hasEaten 0 = false 1 = true
    }

    public boolean getIsHungry()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public boolean getHasEaten()
    {
        return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setIsHungry(boolean flag)
    {
        //if (worldObj.isRemote) return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public void setHasEaten(boolean flag)
    {
        //if (worldObj.isRemote) return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 3.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY) && (getEdad() >= 100))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i) && (worldObj.difficultySetting > 0))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if (entity != this)
            {
                entityToAttack = entity;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return riddenByEntity == null;
    }

    /*
     * @Override protected boolean canDespawn() { return !getIsTamed(); }
     */

    @Override
    protected Entity findPlayerToAttack()
    {
        if ((worldObj.difficultySetting > 0) && (getEdad() >= 100) && MoCreatures.proxy.attackDolphins && (rand.nextInt(50) == 0))
        {
            EntityLiving entityliving = FindTarget(this, 12D);
            if ((entityliving != null) && entityliving.isInWater()) { return entityliving; }
        }
        return null;
    }

    public EntityLiving FindTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof MoCEntityShark) || (entity1 instanceof MoCEntityShark && ((MoCEntityShark) entity1).getIsTamed()))
            {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLiving) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving) entity1;
            }
        }

        return entityliving;
    }

    private int Genetics(MoCEntityDolphin entitydolphin, MoCEntityDolphin entitydolphin1)
    {
        if (entitydolphin.getType() == entitydolphin1.getType()) { return entitydolphin.getType(); }
        int i = entitydolphin.getType() + entitydolphin1.getType();
        boolean flag = rand.nextInt(3) == 0;
        boolean flag1 = rand.nextInt(10) == 0;
        if ((i < 5) && flag) { return i; }
        if (((i == 5) || (i == 6)) && flag1)
        {
            return i;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    protected String getDeathSound()
    {
        return "dolphindying";
    }

    @Override
    protected int getDropItemId()
    {
        return Item.fishRaw.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        return "dolphinhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "dolphin";
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    @Override
    protected String getUpsetSound()
    {
        return "dolphinupset";
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        
        //TODO remove before rlease
        //setTamed(true);
        
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && (itemstack.itemID == Item.fishRaw.itemID))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper())
                {
                    setTemper(getMaxTemper() - 1);
                }

                if ((health += 15) > getMaxHealth())
                {
                    health = getMaxHealth();
                }

                if (!getIsAdult())
                {
                    setEdad(getEdad() + 1);
                }
            }

            worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));

            return true;
        }
        if ((itemstack != null) && ((itemstack.itemID == Item.appleRed.itemID) || (itemstack.itemID == Item.appleGold.itemID)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
            }
            return true;
        }
        if ((itemstack != null) && (itemstack.itemID == Item.fishCooked.itemID) && getIsTamed() && getIsAdult())
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if ((health += 25) > getMaxHealth())
            {
                health = getMaxHealth();
            }
            setHasEaten(true);
            worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            return true;
        }
        /*if ((itemstack != null) && getIsTamed() //&& MoCreatures.isServer()
                && ((itemstack.itemID == MoCreatures.medallion.itemID) || (itemstack.itemID == Item.book.itemID)))
        {
            if (!MoCreatures.isServer())
            {
                MoCreatures.proxy.setName(entityplayer, this);
            }
            return true;
        }*/
        //if ((itemstack != null) && getIsTamed() && ((itemstack.itemID == Item.pickaxeDiamond.itemID) || (itemstack.itemID == Item.pickaxeWood.itemID) || (itemstack.itemID == Item.pickaxeStone.itemID) || (itemstack.itemID == Item.pickaxeIron.itemID) || (itemstack.itemID == Item.pickaxeGold.itemID))) { return true; }
        if (riddenByEntity == null)
        {
            entityplayer.rotationYaw = rotationYaw;
            entityplayer.rotationPitch = rotationPitch;
            entityplayer.posY = posY;
            if (!worldObj.isRemote)
            {
                entityplayer.mountEntity(this);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        //debugging purposes
        /*EntityPlayer ep = worldObj.getClosestPlayerToEntity(this, 6D);
        if (ep != null)
        {
            if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            {
                //System.out.println("Server mounting entity = " + this.riddenByEntity);
            }else
            {
                //System.out.println("Client mounting entity = " + this.riddenByEntity);
            }
        }*/

        if (MoCreatures.isServer())
        {
            if (!getIsAdult() && (rand.nextInt(50) == 0))
            {
                setEdad(getEdad() + 1);
                if (getEdad() >= 150)
                {
                    setAdult(true);
                }
            }
            if (!getIsHungry() && (rand.nextInt(100) == 0))
            {
                setIsHungry(true);
            }
            // Riding();
            if ((riddenByEntity == null) && (deathTime == 0) && (!getIsTamed() || getIsHungry()))
            {
                EntityItem entityitem = getClosestFish(this, 12D);
                if (entityitem != null)
                {
                    MoveToNextEntity(entityitem);
                    EntityItem entityitem1 = getClosestFish(this, 2D);
                    if ((rand.nextInt(20) == 0) && (entityitem1 != null) && (deathTime == 0))
                    {

                        entityitem1.setDead();
                        setTemper(getTemper() + 25);
                        if (getTemper() > getMaxTemper())
                        {
                            setTemper(getMaxTemper() - 1);
                        }
                        health = getMaxHealth();
                    }
                }
            }
            if (!ReadyforParenting(this)) { return; }
            int i = 0;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(8D, 2D, 8D));
            for (int j = 0; j < list.size(); j++)
            {
                Entity entity = (Entity) list.get(j);
                if (entity instanceof MoCEntityDolphin)
                {
                    i++;
                }
            }

            if (i > 1) { return; }
            List list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(4D, 2D, 4D));
            for (int k = 0; k < list1.size(); k++)
            {
                Entity entity1 = (Entity) list1.get(k);
                if (!(entity1 instanceof MoCEntityDolphin) || (entity1 == this))
                {
                    continue;
                }
                MoCEntityDolphin entitydolphin = (MoCEntityDolphin) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entitydolphin))
                {
                    continue;
                }
                if (rand.nextInt(100) == 0)
                {
                    gestationtime++;
                }
                if (gestationtime <= 50)
                {
                    continue;
                }
                MoCEntityDolphin entitydolphin1 = new MoCEntityDolphin(worldObj);
                entitydolphin1.setPosition(posX, posY, posZ);
                worldObj.spawnEntityInWorld(entitydolphin1);
                worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                setHasEaten(false);
                entitydolphin.setHasEaten(false);
                gestationtime = 0;
                entitydolphin.gestationtime = 0;
                int l = Genetics(this, entitydolphin);
                entitydolphin1.setEdad(35);
                entitydolphin1.setAdult(false);
                entitydolphin1.setTypeInt(l);
                break;
            }
        }

    }

    public boolean ReadyforParenting(MoCEntityDolphin entitydolphin)
    {
        return (entitydolphin.riddenByEntity == null) && (entitydolphin.ridingEntity == null) && entitydolphin.getIsTamed() && entitydolphin.getHasEaten() && entitydolphin.getIsAdult();
    }

    @Override
    public boolean renderName()
    {
        return getDisplayName() && (riddenByEntity == null);
    }

    @Override
    public void setDead()
    {
        if (MoCreatures.isServer() && getIsTamed() && (health > 0))
        {
            return;
        }
        else
        {
            super.setDead();
            return;
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setDisplayName(nbttagcompound.getBoolean("DisplayName"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("DisplayName", getDisplayName());
    }

}
