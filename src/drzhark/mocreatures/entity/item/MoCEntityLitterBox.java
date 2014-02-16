package drzhark.mocreatures.entity.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;

public class MoCEntityLitterBox extends EntityLiving {
    public int littertime;

    public MoCEntityLitterBox(World world)
    {
        super(world);
        setSize(1.0F, 0.3F);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "litterbox.png";
    }

    public ResourceLocation getTexture()
    {
        return MoCreatures.proxy.getTexture("litterbox.png");
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D); // setMaxHealth
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(15, Byte.valueOf((byte) 0)); // usedLitter - 0 false 1 true
        dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // pickedUp - 0 false 1 true
    }

    public boolean getPickedUp()
    {
        return (dataWatcher.getWatchableObjectByte(15) == 1);
    }

    public boolean getUsedLitter()
    {
        return (dataWatcher.getWatchableObjectByte(16) == 1);
    }

    public void setPickedUp(boolean flag)
    {
        if (worldObj.isRemote) { return; }
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(15, Byte.valueOf(input));
    }

    public void setUsedLitter(boolean flag)
    {
        if (worldObj.isRemote) { return; }
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(16, Byte.valueOf(input));
    }

    public boolean attackEntityFrom(Entity entity, int i)
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    @Override
    public boolean canBePushed()
    {
        return !isDead;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }

    @Override
    protected String getHurtSound()
    {
        return null;
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.0F;
    }

    @Override
    public double getYOffset()
    {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (((ridingEntity instanceof EntityPlayer) && !worldObj.isRemote) || ridingEntity == MoCreatures.proxy.getPlayer())//MoCProxy.mc().thePlayer)
        {
            setPickedUp(true);
            return (yOffset - 1.15F);
        }
        else
        {
            return yOffset;
        }
    }

    @Override
    public void handleHealthUpdate(byte byte0)
    {
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && MoCreatures.isServer() && ((itemstack.getItem() == Items.stone_pickaxe) || (itemstack.getItem() == Items.wooden_pickaxe) || (itemstack.getItem() == Items.iron_pickaxe) || (itemstack.getItem() == Items.golden_pickaxe) || (itemstack.getItem() == Items.diamond_pickaxe)))
        {
            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.litterbox));
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((rand.nextFloat() - rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            setDead();
            return true;
        }

        if ((itemstack != null) && MoCreatures.isServer() && (itemstack.getItem() == Item.getItemFromBlock(Blocks.sand)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setUsedLitter(false);
            littertime = 0;
            return true;
        }
        else
        {
            rotationYaw = entityplayer.rotationYaw;
            if (this.ridingEntity == null)
            {
                if (MoCreatures.isServer())
                {
                    mountEntity(entityplayer);
                }
            }
            else
            {
                if (MoCreatures.isServer())
                {
                    this.mountEntity(null);
                }
            }
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }
    }

    @Override
    public void moveEntity(double d, double d1, double d2)
    {
        if ((ridingEntity != null) || !onGround || !MoCreatures.proxy.staticLitter)
        {
            if (!worldObj.isRemote)
            {
                super.moveEntity(d, d1, d2);
            }
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (onGround)
        {
            setPickedUp(false);
        }
        if (getUsedLitter() && MoCreatures.isServer())
        {
            littertime++;
            worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12D, 4D, 12D));
            for (int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity) list.get(i);
                if (!(entity instanceof EntityMob))
                {
                    continue;
                }
                EntityMob entitymob = (EntityMob) entity;
                entitymob.setAttackTarget(this);
                if (entitymob instanceof EntityCreeper)
                {
                    ((EntityCreeper) entitymob).setCreeperState(-1);
                }
                if (entitymob instanceof MoCEntityOgre)
                {
                    ((MoCEntityOgre) entitymob).pendingSmashAttack = false;
                }
            }

        }
        if (littertime > 5000 && MoCreatures.isServer())
        {
            setUsedLitter(false);
            littertime = 0;
        }
    }

    @Override
    protected void updateEntityActionState()
    {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setBoolean("UsedLitter", getUsedLitter());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        setUsedLitter(nbttagcompound.getBoolean("UsedLitter"));
    }
}