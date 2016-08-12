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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityWyvern extends MoCEntityTameableAnimal {

    public MoCAnimalChest localchest;
    public MoCAnimalChest emptychest;
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

    public MoCEntityWyvern(World world) {
        super(world);
        setSize(1.9F, 1.7F); //TODO
        setAdult(false);
        setTamed(false);
        this.stepHeight = 1.0F;

        if (this.rand.nextInt(6) == 0) {
            setEdad(50 + this.rand.nextInt(50));
        } else {
            setEdad(80 + this.rand.nextInt(20));
        }
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHunt(this, EntityAnimal.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isChested - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // armor 0 by default, 1 metal, 2 gold, 3 diamond, 4 crystaline
        this.dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // isFlying 0 false 1 true
        this.dataWatcher.addObject(26, Byte.valueOf((byte) 0)); // isSitting - 0 false 1 true
        this.dataWatcher.addObject(27, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
        this.dataWatcher.addObject(28, Byte.valueOf((byte) 0)); // isGhost - 0 false 1 true
    }

    @Override
    public boolean getIsFlying() {
        return (this.dataWatcher.getWatchableObjectByte(25) == 1);
    }

    public void setIsFlying(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(25, Byte.valueOf(input));
    }

    @Override
    public byte getArmorType() {
        return this.dataWatcher.getWatchableObjectByte(24);
    }

    @Override
    public void setArmorType(byte i) {
        this.dataWatcher.updateObject(24, Byte.valueOf(i));
    }

    @Override
    public boolean getIsRideable() {
        return (this.dataWatcher.getWatchableObjectByte(27) == 1);
    }

    @Override
    public void setRideable(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(27, Byte.valueOf(input));
    }

    public boolean getIsChested() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setIsChested(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public boolean getIsSitting() {
        return (this.dataWatcher.getWatchableObjectByte(26) == 1);
    }

    public void setSitting(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(26, Byte.valueOf(input));
    }

    public boolean getIsGhost() {
        return (this.dataWatcher.getWatchableObjectByte(28) == 1);
    }

    public void setIsGhost(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(28, Byte.valueOf(input));
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(calculateAttackDmg());
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
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.transformType = tType;
    }

    @Override
    public void onLivingUpdate() {

        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
        }
        if (this.wingFlapCounter == 5 && MoCreatures.isServer()) {
            MoCTools.playCustomSound(this, "wyvernwingflap", this.worldObj);
        }

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, "transform", this.worldObj);
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

            if (getAttackTarget() != null && (!this.getIsTamed() || this.ridingEntity != null) && !isMovementCeased() && this.rand.nextInt(20) == 0) {
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
                if (this.riddenByEntity == null || wingFlapFreq < 5) {
                    wingFlapFreq = 5;
                }
                if (this.rand.nextInt(wingFlapFreq) == 0) {
                    wingFlap();
                }
            }

            if (getIsGhost() && getEdad() < 10 && this.rand.nextInt(5) == 0) {
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
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }

    @Override
    public boolean isFlyingAlone() {
        return getIsFlying() && this.riddenByEntity == null;
    }

    @Override
    public int maxFlyingHeight() {
        if (getIsTamed()) {
            return 5;
        }
        return 18;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if ((itemstack != null) && (itemstack.getItem() == MoCreatures.whip) && getIsTamed() && (this.riddenByEntity == null)) {
            setSitting(!getIsSitting());
            return true;
        }

        if ((itemstack != null) && !getIsRideable() && getEdad() > 90 && this.getIsTamed()
                && (itemstack.getItem() == Items.saddle || itemstack.getItem() == MoCreatures.horsesaddle)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setRideable(true);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && getEdad() > 90 && itemstack.getItem() == Items.iron_horse_armor) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, "armorput", this.worldObj);
            }
            dropArmor();
            setArmorType((byte) 1);
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && getEdad() > 90 && itemstack.getItem() == Items.golden_horse_armor) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, "armorput", this.worldObj);
            }
            dropArmor();
            setArmorType((byte) 2);
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && getEdad() > 90 && itemstack.getItem() == Items.diamond_horse_armor) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, "armorput", this.worldObj);
            }
            dropArmor();
            setArmorType((byte) 3);
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            return true;
        }

        if ((itemstack != null) && getIsTamed() && getEdad() > 90 && !getIsChested() && (itemstack.getItem() == Item.getItemFromBlock(Blocks.chest))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setIsChested(true);
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }

        if (getIsChested() && entityplayer.isSneaking()) {
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("WyvernChest", 9);
            }
            if (this.emptychest == null) {
                this.emptychest = new MoCAnimalChest("WyvernChest", 0);
            }
            if (MoCreatures.isServer()) {

                InventoryLargeChest singleChest = new InventoryLargeChest("WyvernChest", this.localchest, this.emptychest);
                entityplayer.displayGUIChest(singleChest);
            }
            return true;
        }

        if ((itemstack != null) && this.getIsGhost() && this.getIsTamed() && itemstack.getItem() == MoCreatures.amuletghost) {

            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            if (MoCreatures.isServer()) {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerName());
                if (petData != null) {
                    petData.setInAmulet(this.getOwnerPetId(), true);
                }
                this.dropMyStuff();
                MoCTools.dropAmulet(this, 3);
                this.isDead = true;
            }

            return true;

        }

        if ((itemstack != null) && !this.getIsGhost() && (itemstack.getItem() == MoCreatures.essencelight) && getIsTamed() && getEdad() > 90
                && getType() < 5) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }

            if (MoCreatures.isServer()) {
                int i = getType() + 49;
                MoCEntityEgg entityegg = new MoCEntityEgg(this.worldObj, i);
                entityegg.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                entityplayer.worldObj.spawnEntityInWorld(entityegg);
                entityegg.motionY += this.worldObj.rand.nextFloat() * 0.05F;
                entityegg.motionX += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
                entityegg.motionZ += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
            }
            return true;
        }

        if ((itemstack != null) && this.transformCounter == 0 && !this.getIsGhost() && getType() == 5
                && (itemstack.getItem() == MoCreatures.essenceundead) && getIsTamed()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }

            if (MoCreatures.isServer()) {
                transform(6);
            }
            return true;
        }

        if ((itemstack != null) && this.transformCounter == 0 && !this.getIsGhost() && getType() == 5
                && (itemstack.getItem() == MoCreatures.essencelight) && getIsTamed()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }

            if (MoCreatures.isServer()) {
                transform(7);
            }
            return true;
        }

        if ((itemstack != null) && this.transformCounter == 0 && !this.getIsGhost() && getType() == 5
                && (itemstack.getItem() == MoCreatures.essencedarkness) && getIsTamed()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }

            if (MoCreatures.isServer()) {
                transform(8);
            }
            return true;
        }

        if (getIsRideable() && getEdad() > 90 && (this.riddenByEntity == null)) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;

            if (MoCreatures.isServer()) {
                entityplayer.mountEntity(this);
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
                MoCTools.playCustomSound(this, "armoroff", this.worldObj);
            }

            if (i == 1) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.iron_horse_armor, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 2) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.golden_horse_armor, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 3) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.diamond_horse_armor, 1));
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
    protected String getDeathSound() {
        return "mocreatures:wyverndying";
    }

    @Override
    protected String getHurtSound() {
        openMouth();
        return "mocreatures:wyvernhurt";
    }

    @Override
    protected String getLivingSound() {
        openMouth();
        return "mocreatures:wyverngrunt";
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public boolean isMovementCeased() {
        return (this.riddenByEntity != null) || getIsSitting();
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
    public void updateRiderPosition() {
        double dist = getSizeFactor() * (0.3D);
        double newPosX = this.posX - (dist * Math.cos((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.sin((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);
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
    protected void func_174815_a(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        if (entityIn instanceof EntityPlayer && this.rand.nextInt(3) == 0) {
            MoCreatures.poisonPlayer((EntityPlayer) entityIn);
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.poison.id, 200, 0));
        }

        super.func_174815_a(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if ((this.riddenByEntity != null) && (entity == this.riddenByEntity)) {
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
        nbttagcompound.setByte("ArmorType", getArmorType());
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
        setArmorType(nbttagcompound.getByte("ArmorType"));
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
        if (getIsSitting()) {
            yOff += 25;
        }
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
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
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
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
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
        return this.riddenByEntity == null;
    }

    @Override
    public void dropMyStuff() {
        if (MoCreatures.isServer()) {
            dropArmor();
            MoCTools.dropSaddle(this, this.worldObj);

            if (getIsChested()) {
                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Blocks.chest, 1));
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
        if (this.riddenByEntity != null) {
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
        return !this.isMovementCeased() && this.riddenByEntity == null;
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
    public void onDeath(DamageSource damagesource) {
        if (MoCreatures.isServer()) {
            if (this.getType() == 6) {
                MoCTools.spawnMaggots(this.worldObj, this);
            }

            if (!getIsGhost() && getIsTamed() && this.rand.nextInt(4) == 0) {
                MoCEntityWyvern entitywyvern = new MoCEntityWyvern(this.worldObj);
                entitywyvern.setPosition(this.posX, this.posY, this.posZ);
                this.worldObj.spawnEntityInWorld(entitywyvern);
                MoCTools.playCustomSound(this, "appearmagic", this.worldObj);

                entitywyvern.setOwner(this.getOwnerName());
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
