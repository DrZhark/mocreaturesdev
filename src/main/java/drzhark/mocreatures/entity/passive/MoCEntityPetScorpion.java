package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
    private int transformCounter;

    public MoCEntityPetScorpion(World world) {
        super(world);
        setSize(1.4F, 0.9F);
        this.poisontimer = 0;
        setAdult(true);//TODO
        setEdad(20);
        setHasBabies(false);
        this.stepHeight = 20.0F;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(4, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFleeFromPlayer(this, 1.2D, 4D));
        this.tasks.addTask(6, new EntityAIFollowOwnerPlayer(this, 1.0D, 2F, 10F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
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

        if (this.transformCounter != 0) {
            String newText = saddle ? "scorpionundeadsaddle.png" : "scorpionundead.png";
            if ((this.transformCounter % 5) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }
            if (this.transformCounter > 50 && (this.transformCounter % 3) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }

            if (this.transformCounter > 75 && (this.transformCounter % 4) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }
        }

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
        //this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isPicked - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isSitting - 0 false 1 true
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
        //return (this.dataWatcher.getWatchableObjectByte(24) == 1);
        return this.ridingEntity != null;
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    @Override
    public boolean getIsSitting() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setSitting(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
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

    public void setPicked2(boolean flag) {
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
        } else if (animationType == 5) //transforming into undead scorpion
        {
            this.transformCounter = 1;
        }
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

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, "transform", this.worldObj);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                setType(5);
                selectType();
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

            if ((entity != null) && (entity != this) && (super.shouldAttackPlayers()) && getIsAdult()) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void func_174815_a(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        boolean flag = (entityIn instanceof EntityPlayer);
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof EntityLivingBase) {
            setPoisoning(true);
            if (getType() <= 2)// regular scorpions
            {
                if (flag) {
                    MoCreatures.poisonPlayer((EntityPlayer) entityIn);
                }
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.poison.id, 70, 0));
            } else if (getType() == 4)// blue scorpions
            {
                if (flag) {
                    MoCreatures.freezePlayer((EntityPlayer) entityIn);
                }
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 70, 0));

            } else if (getType() == 3)// red scorpions
            {
                if (flag && MoCreatures.isServer() && !this.worldObj.provider.doesWaterVaporize()) {
                    MoCreatures.burnPlayer((EntityPlayer) entityIn);
                    ((EntityLivingBase) entityIn).setFire(15);
                }
            }
        } else {
            swingArm();
        }
        super.func_174815_a(entityLivingBaseIn, entityIn);
    }

    public void swingArm() {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
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

        if ((itemstack != null) && (itemstack.getItem() == MoCreatures.whip) && getIsTamed() && (this.riddenByEntity == null)) {
            setSitting(!getIsSitting());
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == MoCreatures.essenceundead) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }
            transform(5);
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
        if (this.ridingEntity == null && getEdad() < 60 && !getIsAdult()) {
            this.rotationYaw = entityplayer.rotationYaw;
            if (MoCreatures.isServer()) {
                mountEntity(entityplayer);
            }

            if (MoCreatures.isServer() && !getIsTamed()) {
                MoCTools.tameWithName(entityplayer, this);
            }
        } else if (this.ridingEntity != null && getIsPicked()) {
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
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
    public int nameYOffset() {
        int n = (int) (1 - (getEdad() * 0.8));
        if (n < -60) {
            n = -60;
        }
        if (getIsAdult()) {
            n = -60;
        }
        if (getIsSitting()) {
            n = (int) (n * 0.8);
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
        return (this.riddenByEntity != null) || getIsSitting();
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

    @Override
    public int getMaxEdad() {
        return 120;
    }

    @Override
    public double getMountedYOffset() {
        return (this.height * 0.75D) - 0.15D;
    }

    @Override
    public void updateRiderPosition() {
        double dist = (0.2D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    public void transform(int tType) {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.transformCounter = 1;
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityFox) && entity.height <= 1D && entity.width <= 1D;
    }

}
