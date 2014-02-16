package drzhark.mocreatures.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;

public class MoCEntityFishBowl extends EntityLiving {
    private int rotInt = 0;
    private boolean moving = false;

    public MoCEntityFishBowl(World world)
    {
        super(world);
        setSize(1.0F, 1.0F);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "fishbowl.png";
    }

    public MoCEntityFishBowl(World world, double d, double d1, double d2)
    {
        super(world);
        setSize(1.0F, 1.0F);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "fishbowl.png";
    }

    public MoCEntityFishBowl(World world, int i)
    {
        this(world);
        setType(i);
    }

    public ResourceLocation getTexture()
    {
        return MoCreatures.proxy.getTexture("fishbowl.png");
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D); // setMaxHealth
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();

        dataWatcher.addObject(15, Byte.valueOf((byte) 0)); // pickedUp - 0 false 1 true
        dataWatcher.addObject(16, Integer.valueOf(0)); // sheetColor int
    }

    public int getType()
    {
        return dataWatcher.getWatchableObjectInt(16);
    }

    public boolean getPickedUp()
    {
        return (dataWatcher.getWatchableObjectByte(15) == 1);
    }

    public void setPickedUp(boolean flag)
    {
        if (worldObj.isRemote) { return; }
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(15, Byte.valueOf(input));
    }

    public void setType(int i)
    {
        if (worldObj.isRemote) { return; }
        dataWatcher.updateObject(16, Integer.valueOf(i));
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
    public boolean canEntityBeSeen(Entity entity)
    {
        return worldObj.rayTraceBlocks(Vec3.createVectorHelper(posX, posY + getEyeHeight(), posZ), Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ)) == null;
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
            return (yOffset - 1.0F);
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

    public static ItemStack toItemStack(int type)
    {
        switch (type)
        {
        case 0:
            return new ItemStack(MoCreatures.fishbowl_e, 1, 0);
        case 1:
            return new ItemStack(MoCreatures.fishbowl_1, 1, 1);
        case 2:
            return new ItemStack(MoCreatures.fishbowl_2, 1, 2);
        case 3:
            return new ItemStack(MoCreatures.fishbowl_3, 1, 3);
        case 4:
            return new ItemStack(MoCreatures.fishbowl_4, 1, 4);
        case 5:
            return new ItemStack(MoCreatures.fishbowl_5, 1, 5);
        case 6:
            return new ItemStack(MoCreatures.fishbowl_6, 1, 6);
        case 7:
            return new ItemStack(MoCreatures.fishbowl_7, 1, 7);
        case 8:
            return new ItemStack(MoCreatures.fishbowl_8, 1, 8);
        case 9:
            return new ItemStack(MoCreatures.fishbowl_9, 1, 9);
        case 10:
            return new ItemStack(MoCreatures.fishbowl_10, 1, 10);
        case 11:
            return new ItemStack(MoCreatures.fishbowl_w, 1, 11);
        default:
            return null;
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && ((itemstack.getItem() == Items.stone_pickaxe) || (itemstack.getItem() == Items.wooden_pickaxe) || (itemstack.getItem() == Items.iron_pickaxe) || (itemstack.getItem() == Items.golden_pickaxe) || (itemstack.getItem() == Items.diamond_pickaxe)))
        {
            ItemStack mystack = toItemStack(getType());

            entityplayer.inventory.addItemStackToInventory(mystack);
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((rand.nextFloat() - rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            if (!worldObj.isRemote)
            {
                entityplayer.onItemPickup(this, 1);
            }
            setDead();
            return true;
        }

        if ((itemstack != null) && (getType() > 0 && getType() < 11) && ((itemstack.getItem() == MoCreatures.fishbowl_e) || (itemstack.getItem() == MoCreatures.fishbowl_w)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            ItemStack mystack = toItemStack(getType());
            entityplayer.inventory.addItemStackToInventory(mystack);
            setType(0);
            return true;
        }

        if (ridingEntity == null && MoCreatures.isServer())
        {
            rotationYaw = entityplayer.rotationYaw;
            // TODO change sound
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            mountEntity(entityplayer);
        }
        else
        //TODO 4FIX
        {
            this.mountEntity(null);
            motionX = entityplayer.motionX * 5D;
            motionY = (entityplayer.motionY / 2D) + 0.2D;
            motionZ = entityplayer.motionZ * 5D;
        }

        return false;

    }

    @Override
    public void moveEntity(double d, double d1, double d2)
    {
        if ((ridingEntity != null) || !onGround)
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
        if (rand.nextInt(80) == 0)
        {
            moving = !moving;
        }
        if (moving)
        {
            rotInt += rand.nextInt(10);
            if (rotInt > 360)
            {
                rotInt = 0;
            }
        }
        prevRenderYawOffset = renderYawOffset = rotationYaw = prevRotationYaw;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setType(nbttagcompound.getInteger("SheetColour"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("SheetColour", getType());
    }

    public int getRotation()
    {
        return rotInt;
    }
}