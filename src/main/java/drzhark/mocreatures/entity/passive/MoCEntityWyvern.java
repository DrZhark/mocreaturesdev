package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import javax.annotation.Nullable;

public class MoCEntityWyvern extends MoCEntityTameableAnimal {

    public MoCAnimalChest localchest;
    public ItemStack localstack;
    public int mouthCounter;
    public int wingFlapCounter;
    public int diveCounter;
    public static final String wyvernNames[] = {"Jungle", "Swamp", "Savanna", "Sand", "Mother", "Undead", "Light", "Dark", "Arctic", "Cave",
            "Mountain", "Sea"};

    protected EntityAIWanderMoC2 wander;
    private int transformType;
    private int transformCounter;
    private int tCounter;
    private float fTransparency;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.<Boolean>createKey(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.<Boolean>createKey(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.<Boolean>createKey(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GHOST = EntityDataManager.<Boolean>createKey(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FLYING = EntityDataManager.<Boolean>createKey(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARMOR_TYPE = EntityDataManager.<Integer>createKey(MoCEntityWyvern.class, DataSerializers.VARINT);


    public MoCEntityWyvern(World world) {
        super(world);
        setSize(1.9F, 1.7F);
        setAdult(false);
        setTamed(false);
        this.stepHeight = 1.0F;

        if (this.rand.nextInt(6) == 0) {
            setEdad(50 + this.rand.nextInt(50));
        } else {
            setEdad(80 + this.rand.nextInt(20));
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHunt(this, EntityAnimal.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(RIDEABLE, Boolean.valueOf(false)); // rideable: 0 nothing, 1 saddle
        this.dataManager.register(SITTING, Boolean.valueOf(false)); // rideable: 0 nothing, 1 saddle
        this.dataManager.register(CHESTED, Boolean.valueOf(false));
        this.dataManager.register(FLYING, Boolean.valueOf(false));
        this.dataManager.register(GHOST, Boolean.valueOf(false));
        this.dataManager.register(ARMOR_TYPE, Integer.valueOf(0));// armor 0 by default, 1 metal, 2 gold, 3 diamond, 4 crystaline
    }

    public boolean getIsFlying() {
    	return ((Boolean)this.dataManager.get(FLYING)).booleanValue();
    }

    public void setIsFlying(boolean flag) {
    	this.dataManager.set(FLYING, Boolean.valueOf(flag));
    }

    @Override
    public int getArmorType() {
    	return ((Integer)this.dataManager.get(ARMOR_TYPE)).intValue();
    }

    @Override
    public void setArmorType(int i) {
    	this.dataManager.set(ARMOR_TYPE, Integer.valueOf(i));
    }

    @Override
    public boolean getIsRideable() {
    	return ((Boolean)this.dataManager.get(RIDEABLE)).booleanValue();
    }

    @Override
    public void setRideable(boolean flag) {
    	this.dataManager.set(RIDEABLE, Boolean.valueOf(flag));
    }

    public boolean getIsChested() {
    	return ((Boolean)this.dataManager.get(CHESTED)).booleanValue();
    }

    public void setIsChested(boolean flag) {
    	this.dataManager.set(CHESTED, Boolean.valueOf(flag));
    }

    @Override
    public boolean getIsSitting() {
    	return ((Boolean)this.dataManager.get(SITTING)).booleanValue();
    }

    public void setSitting(boolean flag) {
    	this.dataManager.set(SITTING, Boolean.valueOf(flag));
    }

    public boolean getIsGhost() {
    	return ((Boolean)this.dataManager.get(GHOST)).booleanValue();
    }

    public void setIsGhost(boolean flag) {
    	this.dataManager.set(GHOST, Boolean.valueOf(flag));
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            if (rand.nextInt(5) == 0) {
                setType(5);
            } else {
                int i = this.rand.nextInt(100);
                if (i <= 12) {
                    setType(1);
                } else if (i <= 24) {
                    setType(2);
                } else if (i <= 36) {
                    setType(3);
                } else if (i <= 48) {
                    setType(4);
                } else if (i <= 60) {
                    setType(9);
                } else if (i <= 72) {
                    setType(10);
                } else if (i <= 84) {
                    setType(11);
                } else if (i <= 95) {
                    setType(12);
                } else {
                    setType(5);
                }
            }
        }
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(calculateAttackDmg());
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    public double calculateMaxHealth() {
        if (this.getType() == 6 || this.getType() == 7 || this.getType() == 8) {
            return 60D;
        }
        if (this.getType() == 5) {
            return 80D;
        }
        if (this.getType() == 13) {
            return 100D;
        }
        return 40D;
    }

    public double calculateAttackDmg() {
        if (this.getType() == 6 || this.getType() == 7 || this.getType() == 8 || this.getType() == 13) {
            return 8D;
        }
        if (this.getType() == 5) {
            return 12D;
        }
        return 5D;
    }

    /**
    * 1-4 regular wyverns
    * 5 mother wyvern
    * 6 undead
    * 7 light
    * 8 darkness
    * 9-12 extra wyverns
    */
    @Override
    public ResourceLocation getTexture() {
        if (this.transformCounter != 0 && this.transformType > 5) {
            String newText = "wyverndark.png";
            if (this.transformType == 6) {
                newText = "wyvernundead.png";
            }
            if (this.transformType == 7) {
                newText = "wyvernlight.png";
            }
            if (this.transformType == 8) {
                newText = "wyverndark.png";
            }

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
                return MoCreatures.proxy.getTexture("wyvernjungle.png");
            case 2:
                return MoCreatures.proxy.getTexture("wyvernmix.png");
            case 3:
                return MoCreatures.proxy.getTexture("wyvernsand.png");
            case 4:
                return MoCreatures.proxy.getTexture("wyvernsun.png");
            case 5:
                return MoCreatures.proxy.getTexture("wyvernmother.png");
            case 6:
                return MoCreatures.proxy.getTexture("wyvernundead.png");
            case 7:
                return MoCreatures.proxy.getTexture("wyvernlight.png");
            case 8:
                return MoCreatures.proxy.getTexture("wyverndark.png");
            case 9:
                return MoCreatures.proxy.getTexture("wyvernarctic.png");
            case 10:
                return MoCreatures.proxy.getTexture("wyverncave.png");
            case 11:
                return MoCreatures.proxy.getTexture("wyvernmountain.png");
            case 12:
                return MoCreatures.proxy.getTexture("wyvernsea.png");
                //case 13:
                //    return MoCreatures.proxy.getTexture("wyvernghost.png");
            default:
                return MoCreatures.proxy.getTexture("wyvernsun.png");
        }
    }

    public void transform(int tType) {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
                    new TargetPoint(this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
        this.transformType = tType;
        this.transformCounter = 1;
    }

    @Override
    public void onLivingUpdate() {

        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
        }
        if (this.wingFlapCounter == 5 && MoCreatures.isServer()) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_WYVERN_WINGFLAP);
        }

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    setType(this.transformType);
                    selectType();
                }
            }
        }

        if (MoCreatures.isServer()) {
            if (!isMovementCeased() && !this.getIsTamed() && this.rand.nextInt(300) == 0) {
                setIsFlying(!getIsFlying());
            }
            if (isMovementCeased() && getIsFlying()) {
                setIsFlying(false);
            }

            if (getAttackTarget() != null && (!this.getIsTamed() || this.getRidingEntity() != null) && !isMovementCeased() && this.rand.nextInt(20) == 0) {
                setIsFlying(true);
            }
            if (!getIsTamed() && this.dimension == MoCreatures.WyvernLairDimensionID && (this.rand.nextInt(50) == 0) && this.posY < 10D) {
                this.setDead();
            }

            if (getIsFlying() && this.getNavigator().noPath() && !isMovementCeased() && this.getAttackTarget() == null && rand.nextInt(30) == 0) {
                this.wander.makeUpdate();
            }

            if (this.motionY > 0.5) // prevent large boundingbox checks
            {
                this.motionY = 0.5;
            }

            if (isOnAir()) {
                float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
                int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
                if (!this.isBeingRidden() || wingFlapFreq < 5) {
                    wingFlapFreq = 5;
                }
                if (this.rand.nextInt(wingFlapFreq) == 0) {
                    wingFlap();
                }
            }

            if (getIsGhost() && getEdad() > 0 && getEdad() < 10 && this.rand.nextInt(5) == 0) {
                setEdad(getEdad() + 1);
                if (getEdad() == 9) {
                    setEdad(140);
                    setAdult(true);
                }
            }

        } else {

            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.diveCounter > 0 && ++this.diveCounter > 5) {
                this.diveCounter = 0;
            }
        }
        super.onLivingUpdate();
    }

    public void wingFlap() {
        if (this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                    new TargetPoint(this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }

    @Override
    public boolean isFlyingAlone() {
        return getIsFlying() && !this.isBeingRidden();
    }

    @Override
    public int maxFlyingHeight() {
        if (getIsTamed())
            return 5;
        return 18;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        if ((stack != null) && (stack.getItem() == MoCreatures.whip) && getIsTamed() && (!this.isBeingRidden())) {
            setSitting(!getIsSitting());
            return true;
        }

        if ((stack != null) && onMainHand && !getIsRideable() && getEdad() > 90 && this.getIsTamed()
                && (stack.getItem() == Items.SADDLE || stack.getItem() == MoCreatures.horsesaddle)) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            setRideable(true);
            return true;
        }

        if ((stack != null) && onMainHand && this.getIsTamed() && getEdad() > 90 && stack.getItem() == Items.IRON_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 1);
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }

            return true;
        }

        if ((stack != null) && onMainHand && this.getIsTamed() && getEdad() > 90 && stack.getItem() == Items.GOLDEN_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 2);
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            return true;
        }

        if ((stack != null) && onMainHand && this.getIsTamed() && getEdad() > 90 && stack.getItem() == Items.DIAMOND_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 3);
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            return true;
        }

        if ((stack != null) && onMainHand && getIsTamed() && getEdad() > 90 && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }

        if (getIsChested() && player.isSneaking()) {
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("WyvernChest", 9);
            }
            if (MoCreatures.isServer()) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }

        if ((stack != null) && onMainHand && this.getIsGhost() && this.getIsTamed() && stack.getItem() == MoCreatures.amuletghost) {

            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            if (!this.worldObj.isRemote) {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerId());
                if (petData != null) {
                    petData.setInAmulet(this.getOwnerPetId(), true);
                }
                this.dropMyStuff();
                MoCTools.dropAmulet(this, 3, player);
                this.isDead = true;
            }

            return true;

        }

        if ((stack != null) && onMainHand && !this.getIsGhost() && (stack.getItem() == MoCreatures.essencelight) && getIsTamed() && getEdad() > 90
                && getType() < 5) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.worldObj.isRemote) {
                int i = getType() + 49;
                MoCEntityEgg entityegg = new MoCEntityEgg(this.worldObj, i);
                entityegg.setPosition(player.posX, player.posY, player.posZ);
                player.worldObj.spawnEntityInWorld(entityegg);
                entityegg.motionY += this.worldObj.rand.nextFloat() * 0.05F;
                entityegg.motionX += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
                entityegg.motionZ += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
            }
            return true;
        }

        if ((stack != null) && onMainHand && this.transformCounter == 0 && !this.getIsGhost() && getType() == 5
                && (stack.getItem() == MoCreatures.essenceundead) && getIsTamed()) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (MoCreatures.isServer()) {
                transform(6);
            }
            return true;
        }

        if ((stack != null) && onMainHand && this.transformCounter == 0 && !this.getIsGhost() && getType() == 5
                && (stack.getItem() == MoCreatures.essencelight) && getIsTamed()) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (MoCreatures.isServer()) {
                transform(7);
            }
            return true;
        }

        if ((stack != null) && onMainHand && this.transformCounter == 0 && !this.getIsGhost() && getType() == 5
                && (stack.getItem() == MoCreatures.essencedarkness) && getIsTamed()) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (MoCreatures.isServer()) {
                transform(8);
            }
            return true;
        }

        if (getIsRideable() && getEdad() > 90 && (!this.isBeingRidden())) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;

            if (MoCreatures.isServer()) {
                player.startRiding(this);
                setSitting(false);
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Drops the current armor
     */
    @Override
    public void dropArmor() {
        if (MoCreatures.isServer()) {
            int i = getArmorType();
            if (i != 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            }

            if (i == 1) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.IRON_HORSE_ARMOR, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 2) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 3) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.DIAMOND_HORSE_ARMOR, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            setArmorType((byte) 0);
        }
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_WYVERN_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_WYVERN_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_WYVERN_AMBIENT;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isBeingRidden()) || getIsSitting();
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.85 * getSizeFactor();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = getSizeFactor() * (0.3D);
        double newPosX = this.posX - (dist * Math.cos((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.sin((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        passenger.setPosition(newPosX, this.posY + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof EntityPlayer && !shouldAttackPlayers()) {
            return false;
        }
        openMouth();
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        if (entityIn instanceof EntityPlayer && this.rand.nextInt(3) == 0) {
            MoCreatures.poisonPlayer((EntityPlayer) entityIn);
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 200, 0));
        }

        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if (this.isRidingOrBeingRiddenBy(entity)) {
            return false;
        }
        if (super.attackEntityFrom(damagesource, i)) {
            if (entity != null && getIsTamed() && entity instanceof EntityPlayer) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers())) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        }
        return false;
    }

    /*@Override
    public boolean entitiesToIgnore(Entity entity) {
        return (super.entitiesToIgnore(entity) || (entity instanceof MoCEntityWyvern) || (entity instanceof EntityPlayer));
    }*/

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
        nbttagcompound.setBoolean("Chested", getIsChested());
        nbttagcompound.setInteger("ArmorType", getArmorType());
        nbttagcompound.setBoolean("isSitting", getIsSitting());
        nbttagcompound.setBoolean("isGhost", getIsGhost());
        if (getIsChested() && this.localchest != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                this.localstack = this.localchest.getStackInSlot(i);
                if (this.localstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items", nbttaglist);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setIsChested(nbttagcompound.getBoolean("Chested"));
        setArmorType(nbttagcompound.getInteger("ArmorType"));
        setSitting(nbttagcompound.getBoolean("isSitting"));
        setIsGhost(nbttagcompound.getBoolean("isGhost"));
        if (getIsChested()) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
            this.localchest = new MoCAnimalChest("WyvernChest", 14);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
    }

    @Override
    public int nameYOffset() {
        int yOff = getEdad() * -1;
        if (yOff < -120) {
            yOff = -120;
        }
        if (getIsSitting())
            yOff += 25;
        return yOff;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.getItem() == MoCreatures.ratRaw || par1ItemStack.getItem() == MoCreatures.rawTurkey);
    }

    private void openMouth() {
        if (MoCreatures.isServer()) {
            this.mouthCounter = 1;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                    new TargetPoint(this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }

    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //opening mouth
        {
            this.mouthCounter = 1;
        }
        if (animationType == 2) //diving mount
        {
            this.diveCounter = 1;
        }
        if (animationType == 3) {
            this.wingFlapCounter = 1;
        }
        if (animationType > 5 && animationType < 9) //transform 6 - 8
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }
    }

    @Override
    public void makeEntityDive() {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2),
                    new TargetPoint(this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
        super.makeEntityDive();
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        int chance = MoCreatures.proxy.wyvernEggDropChance;
        if (getType() == 5) { //mother wyverns drop eggs more frequently
            chance = MoCreatures.proxy.motherWyvernEggDropChance;
        }
        if (this.rand.nextInt(100) < chance) {
            entityDropItem(new ItemStack(MoCreatures.mocegg, 1, getType() + 49), 0.0F);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void dropMyStuff() {
        if (MoCreatures.isServer()) {
            dropArmor();
            MoCTools.dropSaddle(this, this.worldObj);

            if (getIsChested()) {
                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }
    }

    @Override
    public float getAdjustedYOffset() {
        if (getIsSitting()) {
            return 0.4F;
        }
        return 0F;
    }

    @Override
    public double getCustomSpeed() {
        if (this.isBeingRidden()) {
            return 1.0D;
        }
        return 0.8D;
    }

    @Override
    public int getMaxEdad() {
        if (this.getType() == 5) {
            return 180;
        }
        if (this.getType() == 6 || this.getType() == 7 || this.getType() == 8) {
            return 160;
        }
        return 120;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        if (getType() == 6 || getIsGhost()) {
            return EnumCreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
    }

    @Override
    public boolean isReadyToHunt() {
        return !this.isMovementCeased() && !this.isBeingRidden();
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityWyvern) && entity.height <= 1D && entity.width <= 1D;
    }

    @Override
    protected double flyerThrust() {
        return 0.6D;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.4F;
        }
        return super.getAIMoveSpeed();
    }

    @Override
    protected float flyerFriction() {
        if (this.getType() == 5) {
            return 0.96F;
        }
        if (this.getType() == 6 || this.getType() == 7 || this.getType() == 8 || this.getIsGhost()) {
            return 0.96F;
        }
        return 0.94F;
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && super.shouldAttackPlayers();
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if (MoCreatures.isServer()) {
            if (this.getType() == 6) {
                MoCTools.spawnMaggots(this.worldObj, this);
            }

            if (!getIsGhost() && getIsTamed() && this.rand.nextInt(4) == 0) {
                MoCEntityWyvern entitywyvern = new MoCEntityWyvern(this.worldObj);
                entitywyvern.setPosition(this.posX, this.posY, this.posZ);
                this.worldObj.spawnEntityInWorld(entitywyvern);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR);

                entitywyvern.setOwnerId(this.getOwnerId());
                entitywyvern.setTamed(true);
                EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                if (entityplayer != null) {
                    MoCTools.tameWithName(entityplayer, entitywyvern);
                }

                entitywyvern.setAdult(false);
                entitywyvern.setEdad(1);
                entitywyvern.setType(this.getType());
                entitywyvern.selectType();
                entitywyvern.setIsGhost(true);
            }

        }
        super.onDeath(damagesource);

    }

    public float tFloat() {

        if (++this.tCounter > 30) {
            this.tCounter = 0;
            this.fTransparency = (this.rand.nextFloat() * (0.4F - 0.2F) + 0.15F);
        }

        if (this.getEdad() < 10) {
            return 0F;
        }
        return fTransparency;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return this.getIsTamed() && !this.getIsGhost();
    }
}
