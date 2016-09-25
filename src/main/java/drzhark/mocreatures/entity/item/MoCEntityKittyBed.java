package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MoCEntityKittyBed extends EntityLiving {

    public float milklevel;
    public String bedColor;
    private static final DataParameter<Boolean> HAS_MILK = EntityDataManager.<Boolean>createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_FOOD = EntityDataManager.<Boolean>createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PICKED_UP = EntityDataManager.<Boolean>createKey(MoCEntityKittyBed.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SHEET_COLOR = EntityDataManager.<Integer>createKey(MoCEntityKittyBed.class, DataSerializers.VARINT);
    
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
        return MoCreatures.proxy.getTexture("kittybeda.png");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D); // setMaxHealth
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HAS_MILK, Boolean.valueOf(false));
        this.dataManager.register(HAS_FOOD, Boolean.valueOf(false));
        this.dataManager.register(PICKED_UP, Boolean.valueOf(false));
        this.dataManager.register(SHEET_COLOR, Integer.valueOf(0));
    }

    public boolean getHasFood() {
    	return ((Boolean)this.dataManager.get(HAS_FOOD)).booleanValue();
    }

    public boolean getHasMilk() {
    	return ((Boolean)this.dataManager.get(HAS_MILK)).booleanValue();
    }

    public boolean getPickedUp() {
    	return ((Boolean)this.dataManager.get(PICKED_UP)).booleanValue();
    }

    public int getSheetColor() {
    	return ((Integer)this.dataManager.get(SHEET_COLOR)).intValue();
    }

    public void setHasFood(boolean flag) {
    	this.dataManager.set(HAS_FOOD, Boolean.valueOf(flag));
    }

    public void setHasMilk(boolean flag) {
    	this.dataManager.set(HAS_MILK, Boolean.valueOf(flag));
    }

    public void setPickedUp(boolean flag) {
    	this.dataManager.set(PICKED_UP, Boolean.valueOf(flag));
    }

    public void setSheetColor(int i) {
    	this.dataManager.set(SHEET_COLOR, Integer.valueOf(i));
        this.bedColor = EnumDyeColor.byMetadata(i).getUnlocalizedName().toLowerCase();
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
        if (((this.getRidingEntity() instanceof EntityPlayer) && !this.worldObj.isRemote) || this.getRidingEntity() == MoCreatures.proxy.getPlayer())//MoCProxy.mc().thePlayer)
        {
            setPickedUp(true);
            return ((EntityPlayer) this.getRidingEntity()).isSneaking() ? 0.25 : 0.5F;
        }
        return super.getYOffset();
    }

    @Override
    public void handleStatusUpdate(byte byte0) {
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && MoCreatures.isServer() && (itemstack.getItem() == Items.MILK_BUCKET)) {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.BUCKET, 1));
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
                && ((itemstack.getItem() == Items.STONE_PICKAXE) || (itemstack.getItem() == Items.WOODEN_PICKAXE)
                        || (itemstack.getItem() == Items.IRON_PICKAXE) || (itemstack.getItem() == Items.GOLDEN_PICKAXE) || (itemstack.getItem() == Items.DIAMOND_PICKAXE))) {
            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.kittybed[getSheetColor()], 1));
            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            setDead();
            return true;
        } else {
            this.rotationYaw = entityplayer.rotationYaw;
            if (this.getRidingEntity() == null) {
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
        if ((this.getRidingEntity() != null) || !this.onGround || !MoCreatures.proxy.staticLitter) {
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
        if ((getHasMilk() || getHasFood()) && (this.isBeingRidden()) && MoCreatures.isServer()) {
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
