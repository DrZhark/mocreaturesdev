package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityKomodo extends MoCEntityTameableAnimal {

    private int sitCounter;
    public int tailCounter;
    public int tongueCounter;
    public int mouthCounter;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.<Boolean>createKey(MoCEntityKomodo.class, DataSerializers.BOOLEAN);
    
    public MoCEntityKomodo(World world) {
        super(world);
        setSize(1.6F, 0.5F);
        this.texture = "komododragon.png";
        setTamed(false);
        setAdult(false);
        this.stepHeight = 1.0F;

        if (this.rand.nextInt(6) == 0) {
            setEdad(30 + this.rand.nextInt(40));
        } else {
            setEdad(90 + this.rand.nextInt(20));
        }
    }
    
    @Override
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.1D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 1.1D, 4D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(7, new EntityAIWanderMoC2(this, 0.9D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(RIDEABLE, Boolean.valueOf(false));; // rideable: 0 nothing, 1 saddle
    }

    @Override
    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, Boolean.valueOf(flag));
    }

    @Override
    public boolean getIsRideable() {
        return ((Boolean)this.dataManager.get(RIDEABLE)).booleanValue();
    }

    @Override
    public boolean getCanSpawnHere() {
        return getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    @Override
    protected SoundEvent getDeathSound() {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_AMBIENT;
    }

    @Override
    public int getTalkInterval() {
        return 500;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.sitCounter > 0 && (this.isBeingRidden() || ++this.sitCounter > 150)) {
            this.sitCounter = 0;
        }
        if (!this.world.isRemote) {
            if (!this.isSwimming() && !this.isBeingRidden() && this.sitCounter == 0 && this.rand.nextInt(500) == 0) { //TODO
                sit();
            }

        } else //animation counters, not needed on server
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 60) {
                this.tailCounter = 0;
            }

            if (this.rand.nextInt(100) == 0) {
                this.tailCounter = 1;
            }

            if (this.rand.nextInt(100) == 0) {
                this.tongueCounter = 1;
            }

            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.tongueCounter > 0 && ++this.tongueCounter > 20) {
                this.tongueCounter = 0;
            }
        }
    }

    private void openmouth() {
        this.mouthCounter = 1;
    }

    private void sit() {
        this.sitCounter = 1;
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
        this.getNavigator().clearPath();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //sitting animation
        {
            this.sitCounter = 1;
            this.getNavigator().clearPath();
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        boolean flag2 = (getEdad() > 90 && this.rand.nextInt(5) == 0);

        if (flag2) {
            int j = this.rand.nextInt(2) + 1;
            for (int k = 0; k < j; k++) {
                entityDropItem(new ItemStack(MoCItems.mocegg, 1, 33), 0.0F);
            }
        } else {

            entityDropItem(new ItemStack(MoCItems.hideCroc, 1, 0), 0.0F);
        }
    }

    @Override
    public float getSizeFactor() {
        if (!getIsAdult()) {
            return getEdad() * 0.01F;
        }
        return 1.2F;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && (getEdad() > 90 || getIsAdult()) && !getIsRideable()
                && (stack.getItem() == Items.SADDLE || stack.getItem() == MoCItems.horsesaddle)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setRideable(true);
            return true;
        }

        if (getIsRideable() && getIsTamed() && getEdad() > 90 && (!this.isBeingRidden())) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean isMovementCeased() {
        return this.getIsSitting() || (this.isBeingRidden());
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public int nameYOffset() {
        if (getIsAdult()) {
            return (-50);
        }
        return (-50 + (getEdad() / 2));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
    }

    @Override
    public double getMountedYOffset() {
        double yOff = 0.15F;
        boolean sit = (this.sitCounter != 0);
        if (sit) {
            //yOff = -0.5F;
        }
        if (getIsAdult()) {
            return yOff + (this.height);
        }
        return this.height * (120 / getEdad());
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if ((entity != null && getIsTamed() && entity instanceof EntityPlayer) || !(entity instanceof EntityLivingBase)) {
                return false;
            }

            if ((this.isBeingRidden()) && (entity == this.getRidingEntity())) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers())) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == MoCItems.ratRaw || stack.getItem() == MoCItems.rawTurkey);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void dropMyStuff() {
        if (!this.world.isRemote) {
            dropArmor();
            MoCTools.dropSaddle(this, this.world);
        }
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityKomodo) && super.canAttackTarget(entity);
    }

    @Override
    public int getMaxEdad() {
        return 120;
    }

    @Override
    public boolean getIsSitting() {
        return this.sitCounter != 0;
    }

    @Override
    public boolean isNotScared() {
        return getEdad() > 70;
    }

    @Override
    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        if (entityIn instanceof EntityPlayer) {
            MoCreatures.poisonPlayer((EntityPlayer) entityIn);
        }
        ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 150, 0));
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.isNotScared() && !this.isMovementCeased() && !this.isBeingRidden();
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public boolean isSwimming() {
        return this.isInWater();
    }
}
