package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityCrocodile extends MoCEntityTameableAnimal {

    // TODO
    // fix floating so it moves faster if it's deep and caughts prey underneath

    // for later?
    // birds to clean their mouths?
    // implement taming? (pick up small crocs to insta tame them)

    public float biteProgress;
    public float spin;
    public int spinInt;
    private float myMoveSpeed;
    private boolean waterbound;
    private int hunting;

    public MoCEntityCrocodile(World world) {
        super(world);
        this.texture = "crocodile.png";
        setSize(2F, 0.6F);
        this.myMoveSpeed = 0.5F;
        //health = 25;
        setEdad(50 + this.rand.nextInt(50));
        setTamed(false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isResting - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // caughtPrey - 0 false 1 true
        this.dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // isBiting - 0 false 1 true
    }

    public boolean getIsBiting() {
        return (this.dataWatcher.getWatchableObjectByte(25) == 1);
    }

    public boolean getIsResting() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getHasCaughtPrey() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setBiting(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(25, Byte.valueOf(input));
    }

    public void setIsResting(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setHasCaughtPrey(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    @Override
    protected void jump() {

        if (isInsideOfMaterial(Material.water)) {
            if (getHasCaughtPrey() || (this.getAttackTarget() == null && this.rand.nextInt(20) != 0)) {
                return;
            }

            this.motionY = 0.3D;
            if (isSprinting()) {
                float f = this.rotationYaw * 0.01745329F;
                this.motionX -= MathHelper.sin(f) * 0.2F;
                this.motionZ += MathHelper.cos(f) * 0.2F;
            }
            this.isAirBorne = true;

        } else if (this.getAttackTarget() != null || getHasCaughtPrey()) {
            super.jump();
        }
    }

    @Override
    public boolean isMovementCeased() {
        return getIsResting(); //TODO getIsSitting() instead?
    }

    /*
    @Override
    protected void updateEntityActionState() {
        if (!getIsResting()) {
            super.updateEntityActionState();
        }
    }*/

    @Override
    public boolean swimmerEntity() {
        return true;
    }

    @Override
    public float getMoveSpeed() {
        return this.myMoveSpeed;
    }

    @Override
    public void onLivingUpdate() {
        if (getIsResting()) {
            this.rotationPitch = -5F;
            if (!isInsideOfMaterial(Material.water) && this.biteProgress < 0.3F && this.rand.nextInt(5) == 0) {
                this.biteProgress += 0.005F;
            }
            setAttackTarget(findPlayerToAttack());
            if (this.getAttackTarget() != null) {
                setIsResting(false);
                getMyOwnPath(this.getAttackTarget(), 16F);
            }
            if (MoCreatures.isServer() && this.getAttackTarget() != null || getHasCaughtPrey() || this.rand.nextInt(500) == 0)// isInsideOfMaterial(Material.water)
            {
                setIsResting(false);
                this.biteProgress = 0;
                this.hunting = 1;
            }

        } else {
            if (MoCreatures.isServer() && (this.rand.nextInt(500) == 0) && this.getAttackTarget() == null && !getHasCaughtPrey())// && !isInsideOfMaterial(Material.water))
            {
                setIsResting(true);
                this.getNavigator().clearPathEntity();
            }

        }

        if (isInsideOfMaterial(Material.water)) {
            this.myMoveSpeed = 0.8F;
        } else {
            this.myMoveSpeed = 0.4F;

        }
        if (this.hunting > 0) {
            this.hunting++;
            if (this.hunting > 120) {
                this.hunting = 0;
                this.myMoveSpeed = 0.5F;
            } else {
                this.myMoveSpeed = 1.0F;
            }

            if (getAttackTarget() == null) {
                this.hunting = 0;
                this.myMoveSpeed = 0.5F;
            }

        }

        if (this.rand.nextInt(80) == 0 && !getHasCaughtPrey() && !getIsResting()) {
            crocBite();
        }

        if (MoCreatures.isServer() && this.rand.nextInt(500) == 0 && !this.waterbound && !getIsResting() && !isInsideOfMaterial(Material.water)) {
            MoCTools.MoveToWater(this);
        }

        if (this.waterbound) {
            if (!isInsideOfMaterial(Material.water)) {
                MoCTools.MoveToWater(this);
            } else {
                this.waterbound = false;
            }
        }

        if (getHasCaughtPrey()) {
            if (this.riddenByEntity != null) {
                setAttackTarget(null);

                this.biteProgress = 0.4F;
                setIsResting(false);

                if (!isInsideOfMaterial(Material.water)) {
                    this.waterbound = true;
                    if (this.riddenByEntity instanceof EntityLiving && ((EntityLivingBase) this.riddenByEntity).getHealth() > 0) {
                        ((EntityLivingBase) this.riddenByEntity).deathTime = 0;
                    }

                    if (MoCreatures.isServer() && this.rand.nextInt(50) == 0) {
                        this.riddenByEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
                        if (!(this.riddenByEntity instanceof EntityPlayer)) {
                            MoCTools.destroyDrops(this, 3D);
                        }
                    }
                }
            } else {
                setHasCaughtPrey(false);
                this.biteProgress = 0F;
                this.waterbound = false;
            }

            if (isSpinning()) {
                this.spinInt += 3;
                if ((this.spinInt % 20) == 0) {
                    this.worldObj.playSoundAtEntity(this, "mocreatures:crocroll", 1.0F,
                            1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                }
                if (this.spinInt > 80) {
                    this.spinInt = 0;
                    this.riddenByEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 4);
                    if (!(this.riddenByEntity instanceof EntityPlayer)) {
                        MoCTools.destroyDrops(this, 3D);
                    }
                }

                //the following if to be removed from SMP

                if (!this.worldObj.isRemote && this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
                    //TODO 4FIX
                    //MoCreatures.mc.gameSettings.thirdPersonView = 1;
                }
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public boolean getIsSitting() {
        double d1 = 0.01D;
        return (getIsResting() || ((this.motionX < d1 && this.motionX > -d1) && (this.motionZ < d1 && this.motionZ > -d1)));
    }

    public void crocBite() {
        if (!getIsBiting()) {
            setBiting(true);
            this.biteProgress = 0.0F;
        }
    }

    @Override
    public void onUpdate() {
        if (getIsBiting() && !getHasCaughtPrey())// && biteProgress <0.3)
        {
            this.biteProgress += 0.1F;
            if (this.biteProgress == 0.4F) {
                this.worldObj.playSoundAtEntity(this, "mocreatures:crocjawsnap", 1.0F,
                        1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            }
            if (this.biteProgress > 0.6F) {

                setBiting(false);
                this.biteProgress = 0.0F;
            }
        }

        super.onUpdate();
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.capabilities.isCreativeMode) {
                return;
            }
        }
        if (getHasCaughtPrey()) {
            return;
        }

        if (attackTime <= 0 && (f < 3F) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 20;
            if (entity.ridingEntity == null && this.rand.nextInt(3) == 0) {
                entity.mountEntity(this);
                setHasCaughtPrey(true);
            } else {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
                if (!(entity instanceof EntityPlayer)) {
                    MoCTools.destroyDrops(this, 3D);
                }
                crocBite();
                setHasCaughtPrey(false);
            }

        }
    }*/

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (this.riddenByEntity != null) {

            Entity entity = damagesource.getEntity();
            if (entity != null && this.riddenByEntity == entity) {
                if (this.rand.nextInt(2) != 0) {
                    return false;
                } else {
                    unMount();
                }
            }

        }
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if (this.riddenByEntity != null && this.riddenByEntity == entity) {
                if ((entity != this) && entity instanceof EntityLivingBase && this.worldObj.getDifficulty().getDifficultyId() > 0) {
                    setAttackTarget((EntityLivingBase) entity);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected EntityLivingBase findPlayerToAttack() {
        if (getHasCaughtPrey()) {
            return null;
        }

        if (this.worldObj.getDifficulty().getDifficultyId() > 0) {
            double attackD = 12D;

            if (getIsResting()) {
                attackD = 6D;
            }

            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, attackD);
            if ((entityplayer != null) && getIsAdult()) {
                return entityplayer;
            }

            EntityLivingBase entityliving = getClosestEntityLiving(this, attackD);
            return entityliving;
        }
        return null;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((super.entitiesToIgnore(entity)) || (entity instanceof MoCEntityCrocodile) || (entity.height < this.height && entity.width < this.width)

        );
    }

    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity == null) {
            return;
        }
        int direction = 1;

        double dist = getEdad() * 0.01F + this.riddenByEntity.width - 0.4D;
        double newPosX = this.posX - (dist * Math.cos((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.sin((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);

        if (this.spinInt > 40) {
            direction = -1;
        } else {
            direction = 1;
        }

        ((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.rotationYaw * direction;
        ((EntityLivingBase) this.riddenByEntity).prevRenderYawOffset = this.rotationYaw * direction;
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.35D;
    }

    @Override
    public void floating() {
        if ((getAttackTarget() != null && ((getAttackTarget().posY < (this.posY - 0.5D)) && getDistanceToEntity(getAttackTarget()) < 10F))) // || caughtPrey)
        {
            if (this.motionY < -0.1) {
                this.motionY = -0.1;
            }
        } else {
            super.floating();
        }
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:crocdying";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:crochurt";
    }

    @Override
    protected String getLivingSound() {
        if (getIsResting()) {
            return "mocreatures:crocresting";
        }
        return "mocreatures:crocgrunt";
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.hideCroc;
    }

    public boolean isSpinning() {
        return getHasCaughtPrey() && (this.riddenByEntity != null) && (isInsideOfMaterial(Material.water));
    }

    @Override
    public void onDeath(DamageSource damagesource) {

        unMount();
        MoCTools.checkForTwistedEntities(this.worldObj);
        super.onDeath(damagesource);
    }

    public void unMount() {

        if (this.riddenByEntity != null) {
            if (this.riddenByEntity instanceof EntityLiving && ((EntityLivingBase) this.riddenByEntity).getHealth() > 0) {
                ((EntityLivingBase) this.riddenByEntity).deathTime = 0;
            }

            this.riddenByEntity.mountEntity(null);
            setHasCaughtPrey(false);
        }
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
    }
}
