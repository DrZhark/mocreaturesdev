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

public class MoCEntityKittyBed extends EntityLiving {
    public float milklevel;

    public MoCEntityKittyBed(World world)
    {
        super(world);
        setSize(1.0F, 0.3F);
        milklevel = 0.0F;
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "kittybed.png";
    }

    public MoCEntityKittyBed(World world, double d, double d1, double d2)
    {
        super(world);
        setSize(1.0F, 0.3F);
        milklevel = 0.0F;
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "kittybed.png";
    }

    public MoCEntityKittyBed(World world, int i)
    {
        this(world);
        setSheetColor(i);
    }

    public ResourceLocation getTexture()
    {
        return MoCreatures.proxy.getTexture("kittybed.png");
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
        dataWatcher.addObject(15, Byte.valueOf((byte) 0)); // hasFood - 0 false 1 true
        dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // hasMilk - 0 false 1 true
        dataWatcher.addObject(17, Byte.valueOf((byte) 0)); // pickedUp - 0 false 1 true
        dataWatcher.addObject(18, Integer.valueOf(0)); // sheetColor int
    }

    public boolean getHasFood()
    {
        return (dataWatcher.getWatchableObjectByte(15) == 1);
    }

    public boolean getHasMilk()
    {
        return (dataWatcher.getWatchableObjectByte(16) == 1);
    }

    public boolean getPickedUp()
    {
        return (dataWatcher.getWatchableObjectByte(17) == 1);
    }

    public int getSheetColor()
    {
        return dataWatcher.getWatchableObjectInt(18);
    }

    public void setHasFood(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(15, Byte.valueOf(input));
    }

    public void setHasMilk(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(16, Byte.valueOf(input));
    }

    public void setPickedUp(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(17, Byte.valueOf(input));
    }

    public void setSheetColor(int i)
    {
        dataWatcher.updateObject(18, Integer.valueOf(i));
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
            return (yOffset - 1.15F);
        }
        return yOffset;
    }

    @Override
    public void handleHealthUpdate(byte byte0)
    {
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && MoCreatures.isServer() && (itemstack.getItem() == Items.milk_bucket))
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bucket, 1));
            worldObj.playSoundAtEntity(this, "mocreatures:pouringmilk", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            setHasMilk(true);
            setHasFood(false);
            return true;
        }
        if ((itemstack != null) && MoCreatures.isServer() && !getHasFood() && (itemstack.getItem() == MoCreatures.petfood))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            worldObj.playSoundAtEntity(this, "mocreatures:pouringfood", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            setHasMilk(false);
            setHasFood(true);
            return true;
        }
        if (MoCreatures.isServer() && (itemstack != null) && ((itemstack.getItem() == Items.stone_pickaxe) || (itemstack.getItem() == Items.wooden_pickaxe) || (itemstack.getItem() == Items.iron_pickaxe) || (itemstack.getItem() == Items.golden_pickaxe) || (itemstack.getItem() == Items.diamond_pickaxe)))
        {
            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.kittybed, 1, getSheetColor()));
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((rand.nextFloat() - rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            setDead();
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
        if ((ridingEntity != null) || !onGround || !MoCreatures.proxy.staticBed)
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
        if ((getHasMilk() || getHasFood()) && (riddenByEntity != null) && MoCreatures.isServer())
        {
            milklevel += 0.003F;
            if (milklevel > 2.0F)
            {
                milklevel = 0.0F;
                setHasMilk(false);
                setHasFood(false);
            }
        }
    }

    @Override
    protected void updateEntityActionState()
    {
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        setHasMilk(nbttagcompound.getBoolean("HasMilk"));
        setSheetColor(nbttagcompound.getInteger("SheetColour"));
        setHasFood(nbttagcompound.getBoolean("HasFood"));
        milklevel = nbttagcompound.getFloat("MilkLevel");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setBoolean("HasMilk", getHasMilk());
        nbttagcompound.setInteger("SheetColour", getSheetColor());
        nbttagcompound.setBoolean("HasFood", getHasFood());
        nbttagcompound.setFloat("MilkLevel", milklevel);
    }
}