package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityPetScorpion extends MoCEntityTameableAnimal {

    public static final String scorpionNames[] = {"Dirt", "Cave", "Nether", "Frost", "Undead"};
    private boolean isPoisoning;
    private int poisontimer;
    public int mouthCounter;
    public int armCounter;
    private int hideCounter;

    public MoCEntityPetScorpion(World world) {
        super(world);
        setSize(1.4F, 0.9F);
        this.poisontimer = 0;
        setAdult(false);
        setEdad(20);
        setHasBabies(false);
        this.stepHeight = 20.0F;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        boolean saddle = getIsRideable();
        switch (getType()) {
            case 1:
                if (!saddle) {
                    return MoCreatures.proxy.getTexture("scorpiondirt.png");
                }
                return MoCreatures.proxy.getTexture("scorpiondirtsaddle.png");
            case 2:
                if (!saddle) {
                    return MoCreatures.proxy.getTexture("scorpioncave.png");
                }
                return MoCreatures.proxy.getTexture("scorpioncavesaddle.png");
            case 3:
                if (!saddle) {
                    return MoCreatures.proxy.getTexture("scorpionnether.png");
                }
                return MoCreatures.proxy.getTexture("scorpionnethersaddle.png");
            case 4:
                if (!saddle) {
                    return MoCreatures.proxy.getTexture("scorpionfrost.png");
                }
                return MoCreatures.proxy.getTexture("scorpionfrostsaddle.png");
            case 5:
                if (!saddle) {
                    return MoCreatures.proxy.getTexture("scorpionundead.png");
                }
                return MoCreatures.proxy.getTexture("scorpionundeadsaddle.png");
            default:
                return MoCreatures.proxy.getTexture("scorpiondirt.png");
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // has babies - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isPicked - 0 false 1 true
        this.dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
    }

    @Override
    public boolean getIsRideable() {
        return (this.dataWatcher.getWatchableObjectByte(25) == 1);
    }

    public boolean getHasBabies() {
        return getIsAdult() && (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getIsPicked() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    @Override
    public void setRideable(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(25, Byte.valueOf(input));
    }

    public void setHasBabies(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setPicked(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setPoisoning(boolean flag) {
        if (flag && MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.isPoisoning = flag;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //tail animation
        {
            setPoisoning(true);
        } else if (animationType == 1) //arm swinging
        {
            this.armCounter = 1;
            //swingArm();
        } else if (animationType == 3) //movement of mouth
        {
            this.mouthCounter = 1;
        }
    }

    @Override
    public float getMoveSpeed() {
        return 0.8F;
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void onLivingUpdate() {
        if (!this.onGround && (this.ridingEntity != null)) {
            this.rotationYaw = this.ridingEntity.rotationYaw;
        }
        // TODO
        /*
        if (getIsAdult() && fleeingTick > 0) {
            fleeingTick = 0;
        }*/

        if (this.mouthCounter != 0 && this.mouthCounter++ > 50) {
            this.mouthCounter = 0;
        }

        if (MoCreatures.isServer() && (this.armCounter == 10 || this.armCounter == 40)) {
            this.worldObj.playSoundAtEntity(this, "mocreatures:scorpionclaw", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
        }

        if (this.armCounter != 0 && this.armCounter++ > 24) {
            this.armCounter = 0;
        }

        if (getIsPoisoning()) {
            this.poisontimer++;
            if (this.poisontimer == 1) {
                this.worldObj.playSoundAtEntity(this, "mocreatures:scorpionsting", 1.0F,
                        1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            }
            if (this.poisontimer > 50) {
                this.poisontimer = 0;
                setPoisoning(false);
            }
        }

        if (MoCreatures.isServer() && !getIsAdult() && (this.rand.nextInt(200) == 0)) {
            setEdad(getEdad() + 1);
            if (getEdad() >= 120) {
                setAdult(true);
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (!(entity instanceof EntityLivingBase) || ((entity != null) && (entity instanceof EntityPlayer) && getIsTamed())) {
                return false;
            }

            if ((entity != null) && (entity != this) && (this.worldObj.getDifficulty().getDifficultyId() > 0) && getIsAdult()) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        if (this.worldObj.getDifficulty().getDifficultyId() > 0 && (!this.worldObj.isDaytime()) && getIsAdult())// only attacks player at night
        {
            if (!getIsTamed()) {
                EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 12D);
                if ((entityplayer != null) && getIsAdult()) {
                    return entityplayer;
                }
            } else {
                if ((this.rand.nextInt(80) == 0)) {
                    EntityLivingBase entityliving = getClosestEntityLiving(this, 10D);
                    return entityliving;
                }

            }
        }
        return null;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((super.entitiesToIgnore(entity)) || (this.getIsTamed() && entity instanceof MoCEntityScorpion && ((MoCEntityScorpion) entity)
                .getIsTamed()));
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if ((f > 2.0F) && (f < 6F) && (this.rand.nextInt(50) == 0)) {
            if (this.onGround) {
                double d = entity.posX - this.posX;
                double d1 = entity.posZ - this.posZ;
                float f1 = MathHelper.sqrt_double((d * d) + (d1 * d1));
                this.motionX = ((d / f1) * 0.5D * 0.8D) + (this.motionX * 0.2D);
                this.motionZ = ((d1 / f1) * 0.5D * 0.8D) + (this.motionZ * 0.2D);
                this.motionY = 0.4D;
            }
        } else if (attackTime <= 0 && (f < 3.0D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 20;
            boolean flag = (entity instanceof EntityPlayer);
            if (!getIsPoisoning() && this.rand.nextInt(5) == 0) {
                setPoisoning(true);
                if (getType() <= 2)// regular scorpions
                {
                    if (flag) {
                        MoCreatures.poisonPlayer((EntityPlayer) entity);
                    }
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 70, 0));
                } else if (getType() == 4)// blue scorpions
                {
                    if (flag) {
                        MoCreatures.freezePlayer((EntityPlayer) entity);
                    }
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 70, 0));

                } else if (getType() == 3)// red scorpions
                {
                    if (flag && MoCreatures.isServer() && !this.worldObj.provider.doesWaterVaporize()) {
                        MoCreatures.burnPlayer((EntityPlayer) entity);
                        ((EntityLivingBase) entity).setFire(15);

                    }

                }

            } else {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
                swingArm();
            }
        }
    }*/

    public void swingArm() {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);

        if (MoCreatures.isServer() && getIsAdult() && getHasBabies()) {
            int k = this.rand.nextInt(5);
            for (int i = 0; i < k; i++) {

                MoCEntityScorpion entityscorpy = new MoCEntityScorpion(this.worldObj);
                entityscorpy.setPosition(this.posX, this.posY, this.posZ);
                entityscorpy.setAdult(false);
                entityscorpy.setType(getType());
                this.worldObj.spawnEntityInWorld(entityscorpy);
                this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);

            }
        }
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:scorpiondying";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:scorpionhurt";
    }

    @Override
    protected String getLivingSound() {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }

        return "mocreatures:scorpiongrunt";
    }

    @Override
    protected Item getDropItem() {
        if (!getIsAdult()) {
            return Items.string;
        }

        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);

        switch (getType()) {
            case 1:
                if (flag) {
                    return MoCreatures.scorpStingDirt;
                }
                return MoCreatures.chitin;
            case 2:
                if (flag) {
                    return MoCreatures.scorpStingCave;
                }
                return MoCreatures.chitinCave;
            case 3:
                if (flag) {
                    return MoCreatures.scorpStingNether;
                }
                return MoCreatures.chitinNether;
            case 4:
                if (flag) {
                    return MoCreatures.scorpStingFrost;
                }
                return MoCreatures.chitinFrost;
            case 5:
                return Items.rotten_flesh;

            default:
                return Items.string;
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && getIsAdult() && !getIsRideable()
                && (itemstack.getItem() == Items.saddle || itemstack.getItem() == MoCreatures.horsesaddle)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setRideable(true);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == MoCreatures.essenceundead) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }
            setType(5);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == MoCreatures.essencedarkness) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }
            this.setHealth(getMaxHealth());
            if (MoCreatures.isServer()) {
                int i = getType() + 40;
                MoCEntityEgg entityegg = new MoCEntityEgg(this.worldObj, i);
                entityegg.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                entityplayer.worldObj.spawnEntityInWorld(entityegg);
                entityegg.motionY += this.worldObj.rand.nextFloat() * 0.05F;
                entityegg.motionX += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
                entityegg.motionZ += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
            }
            return true;
        }
        if (this.ridingEntity == null && getEdad() < 60) {
            this.rotationYaw = entityplayer.rotationYaw;
            if (MoCreatures.isServer()) {
                mountEntity(entityplayer);
            }
            setPicked(true);

            if (MoCreatures.isServer() && !getIsTamed()) {
                MoCTools.tameWithName(entityplayer, this);
            }
        } else if (this.ridingEntity != null && getIsPicked()) {
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            setPicked(false);
            if (MoCreatures.isServer()) {
                this.mountEntity(null);
            }
            this.motionX = entityplayer.motionX * 5D;
            this.motionY = (entityplayer.motionY / 2D) + 0.5D;
            this.motionZ = entityplayer.motionZ * 5D;
        }

        if (getIsRideable() && getIsTamed() && getIsAdult() && (this.riddenByEntity == null)) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;
            setEating(false);
            if (MoCreatures.isServer()) {
                entityplayer.mountEntity(this);
            }

            return true;
        }

        return false;
    }

    @Override
    public double getYOffset() {
        if (this.ridingEntity instanceof EntityPlayer && this.ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            return (super.getYOffset() - 1.7F);
        }

        if ((this.ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer()) {
            return (super.getYOffset() - 0.1F);
        } else {
            return super.getYOffset();
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
        setRideable(nbttagcompound.getBoolean("Saddled"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Babies", getHasBabies());
        nbttagcompound.setBoolean("Saddled", getIsRideable());
    }

    @Override
    public boolean updateMount() {
        return getIsTamed();
    }

    @Override
    public boolean forceUpdates() {
        return getIsTamed();
    }

    @Override
    public int nameYOffset() {
        int n = (int) (1 - (getEdad() * 0.8));
        if (n < -70) {
            n = -70;
        }

        return n;
    }

    @Override
    protected boolean isMyHealFood(ItemStack itemstack) {
        return (itemstack.getItem() == MoCreatures.ratRaw || itemstack.getItem() == MoCreatures.ratCooked);
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public boolean canBeCollidedWith() {

        return this.riddenByEntity == null;
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public boolean isMovementCeased() {
        return (this.riddenByEntity != null);
    }

    @Override
    public void dropMyStuff() {
        MoCTools.dropSaddle(this, this.worldObj);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }
}
