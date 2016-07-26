package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityTurtle extends MoCEntityTameableAnimal {

    private boolean isSwinging;
    private boolean twistright;
    private int flopcounter;

    public MoCEntityTurtle(World world) {
        super(world);
        setSize(0.6F, 0.4F);
        setAdult(false);
        setEdad(60 + this.rand.nextInt(50));
        this.tasks.addTask(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 0.8D, 50));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isUpsideDown - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isHiding - 0 false 1 true
    }

    @Override
    public ResourceLocation getTexture() {
        String tempText = "turtle.png";

        if (getPetName().equals("Donatello") || getPetName().equals("donatello")) {
            tempText = "turtled.png";
        }

        if (getPetName().equals("Leonardo") || getPetName().equals("leonardo")) {
            tempText = "turtlel.png";
        }

        if (getPetName().equals("Rafael") || getPetName().equals("rafael") || getPetName().equals("raphael") || getPetName().equals("Raphael")) {
            tempText = "turtler.png";
        }

        if (getPetName().equals("Michelangelo") || getPetName().equals("michelangelo") || getPetName().equals("Michaelangelo")
                || getPetName().equals("michaelangelo")) {
            tempText = "turtlem.png";
        }

        return MoCreatures.proxy.getTexture(tempText);
    }

    public boolean getIsHiding() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getIsUpsideDown() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setIsHiding(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setIsUpsideDown(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.flopcounter = 0;
        this.swingProgress = 0.0F;
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public double getYOffset() {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            if (((EntityPlayer) this.getRidingEntity()).isSneaking()) {
                return -0.25D + ((300D - this.getEdad()) / 500D);
            }
            return (300D - this.getEdad()) / 500D;
        }
        if ((this.getRidingEntity() instanceof EntityPlayer) && !MoCreatures.isServer()) {
            return (super.getYOffset() + 0.3F);
        }

        return super.getYOffset();
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }

        if (getIsTamed()) {
            if (getIsUpsideDown()) {
                flipflop(false);
                return true;
            }
            if (this.getRidingEntity() == null) {
                this.rotationYaw = entityplayer.rotationYaw;
                // TODO change sound
                this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                if (MoCreatures.isServer()) {
                    mountEntity(entityplayer);
                }
            } else {
                if (MoCreatures.isServer()) {
                    this.mountEntity(null);
                }
                this.motionX = entityplayer.motionX * 5D;
                this.motionY = (entityplayer.motionY / 2D) + 0.2D;
                this.motionZ = entityplayer.motionZ * 5D;
            }
            return true;
        }
        flipflop(!getIsUpsideDown());

        return true;
    }

    @Override
    protected void jump() {
        if (isInsideOfMaterial(Material.water)) {
            this.motionY = 0.3D;
            if (isSprinting()) {
                float f = this.rotationYaw * 0.01745329F;
                this.motionX -= MathHelper.sin(f) * 0.2F;
                this.motionZ += MathHelper.cos(f) * 0.2F;
            }
            this.isAirBorne = true;
        }
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            if (!getIsUpsideDown() && !getIsTamed()) {
                EntityLivingBase entityliving = getBoogey(4D);
                if ((entityliving != null) && canEntityBeSeen(entityliving)) {
                    if (!getIsHiding() && !isInWater()) {
                        this.worldObj.playSoundAtEntity(this, "mocreatures:turtlehissing", 1.0F,
                                1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                        setIsHiding(true);
                    }

                    this.getNavigator().clearPathEntity();
                } else {

                    setIsHiding(false);
                    if (!hasPath() && this.rand.nextInt(50) == 0) {
                        EntityItem entityitem = getClosestItem(this, 10D, Items.melon, Items.reeds);
                        if (entityitem != null) {
                            float f = entityitem.getDistanceToEntity(this);
                            if (f > 2.0F) {
                                getMyOwnPath(entityitem, f);
                            }
                            if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0)) {
                                entityitem.setDead();
                                this.worldObj.playSoundAtEntity(this, "mocreatures:turtleeating", 1.0F,
                                        1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));

                                EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                                if (entityplayer != null) {
                                    MoCTools.tameWithName(entityplayer, this);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean swimmerEntity() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if (this.getRidingEntity() != null) {
            return false;
        }
        if (entity == null) {
            return super.attackEntityFrom(damagesource, i);
        }
        if (getIsHiding()) {
            if (this.rand.nextInt(10) == 0) {
                flipflop(true);
            }
            return false;
        } else {
            boolean flag = super.attackEntityFrom(damagesource, i);
            if (this.rand.nextInt(3) == 0) {
                flipflop(true);
            }
            return flag;
        }
    }

    public void flipflop(boolean flip) {
        setIsUpsideDown(flip);
        setIsHiding(false);
        this.getNavigator().clearPathEntity();
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return (entity instanceof MoCEntityTurtle) || ((entity.height <= this.height) && (entity.width <= this.width))
                || super.entitiesToIgnore(entity);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if ((this.getRidingEntity() != null) && (this.getRidingEntity() instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) this.getRidingEntity();
            if (entityplayer != null) {
                this.rotationYaw = entityplayer.rotationYaw;
            }
        }
        //to make mega turtles if tamed
        if (getIsTamed() && getEdad() < 300 && this.rand.nextInt(900) == 0) {
            setEdad(getEdad() + 1);
        }
        if (getIsUpsideDown() && isInWater()) {
            setIsUpsideDown(false);
        }
        if (getIsUpsideDown() && (this.getRidingEntity() == null) && this.rand.nextInt(20) == 0) {
            setSwinging(true);
            this.flopcounter++;
        }

        if (getIsSwinging()) {
            this.swingProgress += 0.2F;

            boolean flag = (this.flopcounter > (this.rand.nextInt(3) + 8));

            if (this.swingProgress > 2.0F && (!flag || this.rand.nextInt(20) == 0)) {
                setSwinging(false);
                this.swingProgress = 0.0F;
                if (this.rand.nextInt(2) == 0) {
                    this.twistright = !this.twistright;
                }

            } else if (this.swingProgress > 9.0F && flag) {
                setSwinging(false);
                this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                setIsUpsideDown(false);
            }
        }
    }

    public boolean getIsSwinging() {
        return this.isSwinging;
    }

    public void setSwinging(boolean flag) {
        this.isSwinging = flag;
    }

    @Override
    public boolean isMovementCeased() {
        return (getIsUpsideDown() || getIsHiding());
    }

    public int getFlipDirection() {
        if (this.twistright) {
            return 1;
        }
        return -1;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setIsUpsideDown(nbttagcompound.getBoolean("UpsideDown"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("UpsideDown", getIsUpsideDown());
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:turtlehurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:turtlegrunt";
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:turtledying";
    }

    @Override
    protected Item getDropItem() {
        if (getPetName().equals("Donatello") || getPetName().equals("donatello")) {
            return MoCreatures.bo;
        }

        if (getPetName().equals("Leonardo") || getPetName().equals("leonardo")) {
            return MoCreatures.katana;
        }

        if (getPetName().equals("Rafael") || getPetName().equals("rafael") || getPetName().equals("raphael") || getPetName().equals("Raphael")) {
            return MoCreatures.sai;
        }

        if (getPetName().equals("Michelangelo") || getPetName().equals("michelangelo") || getPetName().equals("Michaelangelo")
                || getPetName().equals("michaelangelo")) {
            return MoCreatures.nunchaku;
        }
        return MoCreatures.turtleraw;
    }

    /**
     * Used to avoid rendering the top shell cube
     *
     * @return
     */
    public boolean isTMNT() {
        if (getPetName().equals("Donatello") || getPetName().equals("donatello") || getPetName().equals("Leonardo") || getPetName().equals("leonardo")
                || getPetName().equals("Rafael") || getPetName().equals("rafael") || getPetName().equals("raphael") || getPetName().equals("Raphael")
                || getPetName().equals("Michelangelo") || getPetName().equals("michelangelo") || getPetName().equals("Michaelangelo")
                || getPetName().equals("michaelangelo")) {
            return true;
        }
        return false;
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }*/

    /*@Override
    public boolean forceUpdates() {
        return getIsTamed();
    }*/

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.getItem() == Items.reeds || par1ItemStack.getItem() == Items.melon);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public int nameYOffset() {
        return -10 - (getEdad() / 5);
    }

    @Override
    public boolean isPushedByWater() {
        return true;
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (isInWater()) {
            return 0.08F;
        }
        return 0.12F;
    }

    @Override
    protected double minDivingDepth() {
        return (getEdad() + 8D) / 340D;
    }

    @Override
    protected double maxDivingDepth() {
        return 1D * (this.getEdad() / 100D);
    }

    @Override
    public int getMaxEdad() {
        return 120;
    }
}
