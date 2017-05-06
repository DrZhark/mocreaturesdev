package drzhark.mocreatures.entity.passive;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.util.MoCSoundEvents;

public class MoCEntityBear extends MoCEntityTameableAnimal {

    public int mouthCounter;
    private int attackCounter;
    private int standingCounter;
    private static final DataParameter<Integer> BEAR_STATE = EntityDataManager.<Integer>createKey(MoCEntityBear.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.<Boolean>createKey(MoCEntityBear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.<Boolean>createKey(MoCEntityBear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GHOST = EntityDataManager.<Boolean>createKey(MoCEntityBear.class, DataSerializers.BOOLEAN);
    public MoCAnimalChest localchest;
    public ItemStack localstack;
    
    public MoCEntityBear(World world) {
        super(world);
        setSize(1.2F, 1.5F);
        setEdad(55);
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);
        } else {
            setAdult(true);
        }
        this.stepHeight = 1.0F;
    }
 
    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowOwnerPlayer(this, 1D, 2F, 10F));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getAttackStrength());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    /**
     * Initializes datawatchers for entity. Each datawatcher is used to sync
     * server data to client.
     */
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BEAR_STATE, Integer.valueOf(0));
        this.dataManager.register(RIDEABLE, Boolean.valueOf(false)); 
        this.dataManager.register(CHESTED, Boolean.valueOf(false)); 
        this.dataManager.register(GHOST, Boolean.valueOf(false));
    }

    /**
     * 0 - bear is on fours 1 - standing 2 - sitting
     *
     * @return
     */
    public int getBearState() {
    	return ((Integer)this.dataManager.get(BEAR_STATE)).intValue();
    }

    public void setBearState(int i) {
    	this.dataManager.set(BEAR_STATE, Integer.valueOf(i));
    }

    @Override
    public boolean getIsRideable() {
    	return ((Boolean)this.dataManager.get(RIDEABLE)).booleanValue();
    }

    public boolean getIsChested() {
    	return ((Boolean)this.dataManager.get(CHESTED)).booleanValue();
    }

    public boolean getIsGhost() {
    	return ((Boolean)this.dataManager.get(GHOST)).booleanValue();
    }

    public void setIsChested(boolean flag) {
    	this.dataManager.set(CHESTED, Boolean.valueOf(flag));
    }

    public void setRideable(boolean flag) {
    	this.dataManager.set(RIDEABLE, Boolean.valueOf(flag));
    }

    public void setIsGhost(boolean flag) {
    	this.dataManager.set(GHOST, Boolean.valueOf(flag));
    }
    
    @Override
    public void selectType() {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getAttackStrength());
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(getAttackRange());
        if (getIsAdult()) {
            setEdad(getMaxEdad());
        }
    }

    /**
     * Returns the factor size for the bear, polars are bigger and pandas
     * smaller
     *
     * @return
     */
    public float getBearSize() {
                return 1.0F;
    }

    public float calculateMaxHealth() {
                  return 30;
    }

    /**
     * Returns the distance at which the bear attacks prey
     *
     * @return
     */
    public double getAttackRange() {
              return 8D;
    }

    /**
     * The damage the bear does
     *
     * @return
     */
    public int getAttackStrength() {
                   return 2;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startAttack();
        return super.attackEntityAsMob(entityIn);
    }

    /**
     * Checks if entity is sitting.
     */
    @Override
    public boolean isMovementCeased() {
        return getBearState() == 2;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && entity instanceof EntityLivingBase && super.shouldAttackPlayers()) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.mouthCounter > 0 && ++this.mouthCounter > 20) {
            this.mouthCounter = 0;
        }
        if (this.attackCounter > 0 && ++this.attackCounter > 9) {
            this.attackCounter = 0;
        }
        if (MoCreatures.isServer() && !getIsAdult() && getEdad() < 80 && (this.rand.nextInt(300) == 0)) {
            setBearState(2);
        }
        /**
         * Sitting non tamed bears will resume on fours stance every now and then
         */
        if ((MoCreatures.isServer()) && (getBearState() == 2) && !getIsTamed() && (this.rand.nextInt(800) == 0)) {
            setBearState(0);
        }
        if ((MoCreatures.isServer()) && (getBearState() == 2) && !getIsTamed() && !this.getNavigator().noPath()) {
            setBearState(0);
        }
        if ((MoCreatures.isServer()) && this.standingCounter > 0 && ++this.standingCounter > 100) {
            this.standingCounter = 0;
            setBearState(0);
        }
        /**\
         * Standing if close to a vulnerable player
         */
        if ((MoCreatures.isServer()) && !getIsTamed() && getIsStanding() 
        		&& getBearState() != 2 && getIsAdult() && (this.rand.nextInt(200) == 0) && shouldAttackPlayers()) {
        	EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 4D);
            if ((entityplayer1 != null && this.canEntityBeSeen(entityplayer1) && !entityplayer1.capabilities.disableDamage)) {
            	this.setStand();
                setBearState(1);
            }
        }
        //TODO move to AI
        if (MoCreatures.isServer() && getType() == 3 && (this.deathTime == 0) && getBearState() != 2) {
            EntityItem entityitem = getClosestItem(this, 12D, Items.REEDS, Items.SUGAR);
            if (entityitem != null) {

                float f = entityitem.getDistanceToEntity(this);
                if (f > 2.0F) {
                    getMyOwnPath(entityitem, f);
                }
                if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0)) {
                    entityitem.setDead();
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
                    this.setHealth(getMaxHealth());
                }

            }
        }
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityBear) && entity.height <= 1D && entity.width <= 1D;
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.animalHide;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_BEAR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_BEAR_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_BEAR_AMBIENT;
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public float getAttackSwing() {
        if (attackCounter == 0)
            return 0;
        return 1.5F + ((float) (attackCounter / 10F) - 10F) * 5F;
    }

    private void startAttack() {
       if (MoCreatures.isServer() && this.attackCounter == 0 && getBearState() == 1) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            this.attackCounter = 1;
        }
    }

    @Override
    public void performAnimation(int i) {
        this.attackCounter = 1;
    }

    protected void eatingAnimal() {
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
    }

    @Override
    public double getCustomSpeed() {
        if (getBearState() == 2) {
            return 0D;
        }
        return super.getCustomSpeed();
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }
    
    public boolean getIsStanding(){
    	return this.standingCounter != 0;
    }
    
    public void setStand(){
    	this.standingCounter = 1;
    }
    
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        if ((stack != null) && onMainHand && getIsTamed() && !getIsRideable() && (getEdad() > 80)
                && (stack.getItem() == Items.SADDLE || stack.getItem() == MoCreatures.horsesaddle)) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            setRideable(true);
            return true;
        }
        if ((stack != null) && getIsTamed() && onMainHand && (MoCTools.isItemEdibleforCarnivores(stack.getItem()))) {
        	if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
        	this.setHealth(getMaxHealth());
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            setIsHunting(false);
            setHasEaten(true);
            return true;
        }
        if ((stack != null) && onMainHand && getIsTamed() && getIsAdult() && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }
        if (getIsChested() && player.isSneaking()) {
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("BigBearChest", 18);
            }
            if (MoCreatures.isServer()) {
               player.displayGUIChest(this.localchest);
            }
            return true;
        }
        return false;
    }

    @Override
    public double getMountedYOffset() {
        double Yfactor = ((0.086D * this.getEdad()) - 2.5D) / 10D;
        return this.height * Yfactor;
    }
    
    @Override
    public int nameYOffset() {
        return (int) (((0.445D * this.getEdad()) + 15D) * -1);
    }
    
    @Override
    public boolean rideableEntity() {
        return true;
    }
    
    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }
    
    @Override
    public void updatePassenger(Entity passenger) {
        double dist = getSizeFactor() * (0.1D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.posY + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }
    
    /*@Override
    public int nameYOffset() {
        if (getIsAdult()) {
            return (-55);
        }
        return (100 / getEdad()) * (-40);
    }*/

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
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
        nbttagcompound.setBoolean("Chested", getIsChested());
        nbttagcompound.setBoolean("Ghost", getIsGhost());
        nbttagcompound.setInteger("BearState", getBearState());
        if (getIsChested() && this.localchest != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                // grab the current item stack
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
        setIsGhost(nbttagcompound.getBoolean("Ghost"));
        setBearState(nbttagcompound.getInteger("BearState"));
        if (getIsChested()) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
            this.localchest = new MoCAnimalChest("BigBearChest", 18);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
    }
}
