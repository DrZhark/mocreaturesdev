package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityKomodo extends MoCEntityTameableAnimal {

    private int sitCounter;
    public int tailCounter;
    public int tongueCounter;
    public int mouthCounter;

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
        //this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.1D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 1.1D, 4D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(7, new EntityAIWanderMoC2(this, 0.9D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));

    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.18D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // rideable: 0 nothing, 1 saddle
    }

    @Override
    public void setRideable(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public boolean getIsRideable() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    @Override
    public boolean getCanSpawnHere() {
        return getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    @Override
    protected String getDeathSound() {
        openmouth();

        return "mocreatures:snakedying";//"komododying";
    }

    @Override
    protected String getHurtSound() {
        openmouth();
        return "mocreatures:snakehurt";//"komodohurt";
    }

    @Override
    protected String getLivingSound() {
        openmouth();
        return "mocreatures:snakehiss";//"komodo";
    }

    @Override
    public int getTalkInterval() {
        return 500;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.sitCounter > 0 && (this.riddenByEntity != null || ++this.sitCounter > 150)) {
            this.sitCounter = 0;
        }
        if (MoCreatures.isServer()) {
            if (!this.isSwimming() && this.riddenByEntity == null && this.sitCounter == 0 && this.rand.nextInt(500) == 0) { //TODO
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
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.getNavigator().clearPathEntity();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //sitting animation
        {
            this.sitCounter = 1;
            this.getNavigator().clearPathEntity();
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        boolean flag2 = (getEdad() > 90 && this.rand.nextInt(5) == 0);

        if (flag2) {
            int j = this.rand.nextInt(2) + 1;
            for (int k = 0; k < j; k++) {
                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, 33), 0.0F);
            }
        } else {

            entityDropItem(new ItemStack(MoCreatures.hideCroc, 1, 0), 0.0F);
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
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if ((itemstack != null) && getIsTamed() && (getEdad() > 90 || getIsAdult()) && !getIsRideable()
                && (itemstack.getItem() == Items.saddle || itemstack.getItem() == MoCreatures.horsesaddle)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setRideable(true);
            return true;
        }

        if (getIsRideable() && getIsTamed() && getEdad() > 90 && (this.riddenByEntity == null)) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;

            if (MoCreatures.isServer() && (this.riddenByEntity == null)) {
                entityplayer.mountEntity(this);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean isMovementCeased() {
        return this.getIsSitting() || (this.riddenByEntity != null);
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
            Entity entity = damagesource.getEntity();

            if ((entity != null && getIsTamed() && entity instanceof EntityPlayer) || !(entity instanceof EntityLivingBase)) {
                return false;
            }

            if ((this.riddenByEntity != null) && (entity == this.riddenByEntity)) {
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
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.getItem() == MoCreatures.ratRaw || par1ItemStack.getItem() == MoCreatures.rawTurkey);
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
        ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.poison.id, 150, 0));
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.isNotScared() && !this.isMovementCeased() && this.riddenByEntity == null;
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
