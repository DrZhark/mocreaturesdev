package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MoCEntityKittyBed extends EntityLiving {

    public float milklevel;
    public String bedColor;

    public MoCEntityKittyBed(World world) {
        super(world);
        setSize(1.0F, 0.3F);
        this.milklevel = 0.0F;
    }

    public MoCEntityKittyBed(World world, double d, double d1, double d2) {
        super(world);
        setSize(1.0F, 0.3F);
        this.milklevel = 0.0F;
    }

    public MoCEntityKittyBed(World world, int i) {
        this(world);
        setSheetColor(i);
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("fullkittybed.png");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D); // setMaxHealth
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // hasMilk - 0 false 1 true
        this.dataWatcher.addObject(17, Byte.valueOf((byte) 0)); // pickedUp - 0 false 1 true
        this.dataWatcher.addObject(18, Integer.valueOf(0)); // sheetColor int
        this.dataWatcher.addObject(19, Byte.valueOf((byte) 0)); // hasFood - 0 false 1 true
    }

    public boolean getHasFood() {
        return (this.dataWatcher.getWatchableObjectByte(19) == 1);
    }

    public boolean getHasMilk() {
        return (this.dataWatcher.getWatchableObjectByte(16) == 1);
    }

    public boolean getPickedUp() {
        return (this.dataWatcher.getWatchableObjectByte(17) == 1);
    }

    public int getSheetColor() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void setHasFood(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(19, Byte.valueOf(input));
    }

    public void setHasMilk(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(16, Byte.valueOf(input));
    }

    public void setPickedUp(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(17, Byte.valueOf(input));
    }

    public void setSheetColor(int i) {
        this.dataWatcher.updateObject(18, Integer.valueOf(i));
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
            return ((EntityPlayer) this.ridingEntity).isSneaking() ? 0.25 : 0.5F;
        }
        return super.getYOffset();
    }

    @Override
    public void handleStatusUpdate(byte byte0) {
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && MoCreatures.isServer() && (itemstack.getItem() == Items.milk_bucket)) {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bucket, 1));
            this.worldObj.playSoundAtEntity(this, "mocreatures:pouringmilk", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            setHasMilk(true);
            setHasFood(false);
            return true;
        }
        if ((itemstack != null) && MoCreatures.isServer() && !getHasFood() && (itemstack.getItem() == MoCreatures.petfood)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            this.worldObj.playSoundAtEntity(this, "mocreatures:pouringfood", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            setHasMilk(false);
            setHasFood(true);
            return true;
        }
        if (MoCreatures.isServer()
                && (itemstack != null)
                && ((itemstack.getItem() == Items.stone_pickaxe) || (itemstack.getItem() == Items.wooden_pickaxe)
                        || (itemstack.getItem() == Items.iron_pickaxe) || (itemstack.getItem() == Items.golden_pickaxe) || (itemstack.getItem() == Items.diamond_pickaxe))) {
            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.kittybed[Math.abs(getSheetColor() - 15)], 1));
            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            setDead();
            return true;
        } else {
            this.rotationYaw = entityplayer.rotationYaw;
            if (this.ridingEntity == null) {
                if (MoCreatures.isServer()) {
                    mountEntity(entityplayer);
                }
            } else {
                if (MoCreatures.isServer()) {
                    this.mountEntity(null);
                }
            }
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }
    }

    @Override
    public void moveEntity(double d, double d1, double d2) {
        if ((this.ridingEntity != null) || !this.onGround || !MoCreatures.proxy.staticLitter) {
            if (!this.worldObj.isRemote) {
                super.moveEntity(d, d1, d2);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.onGround) {
            setPickedUp(false);
        }
        if ((getHasMilk() || getHasFood()) && (this.riddenByEntity != null) && MoCreatures.isServer()) {
            this.milklevel += 0.003F;
            if (this.milklevel > 2.0F) {
                this.milklevel = 0.0F;
                setHasMilk(false);
                setHasFood(false);
            }
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        setHasMilk(nbttagcompound.getBoolean("HasMilk"));
        setSheetColor(nbttagcompound.getInteger("SheetColour"));
        setHasFood(nbttagcompound.getBoolean("HasFood"));
        this.milklevel = nbttagcompound.getFloat("MilkLevel");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.setBoolean("HasMilk", getHasMilk());
        nbttagcompound.setInteger("SheetColour", getSheetColor());
        nbttagcompound.setBoolean("HasFood", getHasFood());
        nbttagcompound.setFloat("MilkLevel", this.milklevel);
    }

    @Override
    public void onLivingUpdate() {
        this.moveStrafing = 0.0F;
        this.moveForward = 0.0F;
        this.randomYawVelocity = 0.0F;
        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        return false;
    }
}
