package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MoCEntityFishBowl extends EntityLiving {

    private int rotInt = 0;
    private boolean moving = false;

    public MoCEntityFishBowl(World world) {
        super(world);
        setSize(1.0F, 1.0F);
    }

    public MoCEntityFishBowl(World world, double d, double d1, double d2) {
        super(world);
        setSize(1.0F, 1.0F);
    }

    public MoCEntityFishBowl(World world, int i) {
        this(world);
        setType(i);
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("fishbowl.png");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D); // setMaxHealth
    }

    @Override
    protected void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(16, Integer.valueOf(0)); // sheetColor int
        this.dataWatcher.addObject(17, Byte.valueOf((byte) 0)); // pickedUp - 0 false 1 true
    }

    public int getType() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    public boolean getPickedUp() {
        return (this.dataWatcher.getWatchableObjectByte(17) == 1);
    }

    public void setPickedUp(boolean flag) {
        if (this.worldObj.isRemote) {
            return;
        }
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(17, Byte.valueOf(input));
    }

    public void setType(int i) {
        if (this.worldObj.isRemote) {
            return;
        }
        this.dataWatcher.updateObject(16, Integer.valueOf(i));
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public boolean canEntityBeSeen(Entity entity) {
        return this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + getEyeHeight(), this.posZ),
                new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ)) == null;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected String getDeathSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return null;
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public double getYOffset() {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (((this.ridingEntity instanceof EntityPlayer) && !this.worldObj.isRemote) || this.ridingEntity == MoCreatures.proxy.getPlayer())//MoCProxy.mc().thePlayer)
        {
            setPickedUp(true);
            return (super.getYOffset() - 1.0F);
        } else {
            return super.getYOffset();
        }
    }

    @Override
    public void handleHealthUpdate(byte byte0) {
    }

    public static ItemStack toItemStack(int type) {
        switch (type) {
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
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null)
                && ((itemstack.getItem() == Items.stone_pickaxe) || (itemstack.getItem() == Items.wooden_pickaxe)
                        || (itemstack.getItem() == Items.iron_pickaxe) || (itemstack.getItem() == Items.golden_pickaxe) || (itemstack.getItem() == Items.diamond_pickaxe))) {
            ItemStack mystack = toItemStack(getType());

            entityplayer.inventory.addItemStackToInventory(mystack);
            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            if (!this.worldObj.isRemote) {
                entityplayer.onItemPickup(this, 1);
            }
            setDead();
            return true;
        }

        if ((itemstack != null) && (getType() > 0 && getType() < 11)
                && ((itemstack.getItem() == MoCreatures.fishbowl_e) || (itemstack.getItem() == MoCreatures.fishbowl_w))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            ItemStack mystack = toItemStack(getType());
            entityplayer.inventory.addItemStackToInventory(mystack);
            setType(0);
            return true;
        }

        if (this.ridingEntity == null && MoCreatures.isServer()) {
            this.rotationYaw = entityplayer.rotationYaw;
            // TODO change sound
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            mountEntity(entityplayer);
        } else
        //TODO 4FIX
        {
            this.mountEntity(null);
            this.motionX = entityplayer.motionX * 5D;
            this.motionY = (entityplayer.motionY / 2D) + 0.2D;
            this.motionZ = entityplayer.motionZ * 5D;
        }

        return false;

    }

    @Override
    public void moveEntity(double d, double d1, double d2) {
        if ((this.ridingEntity != null) || !this.onGround) {
            if (!this.worldObj.isRemote) {
                super.moveEntity(d, d1, d2);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.rand.nextInt(80) == 0) {
            this.moving = !this.moving;
        }
        if (this.moving) {
            this.rotInt += this.rand.nextInt(10);
            if (this.rotInt > 360) {
                this.rotInt = 0;
            }
        }
        this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setType(nbttagcompound.getInteger("SheetColour"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("SheetColour", getType());
    }

    public int getRotation() {
        return this.rotInt;
    }
}
