package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.List;

import javax.annotation.Nullable;

public class MoCEntityBunny extends MoCEntityTameableAnimal {

    private int bunnyReproduceTickerA;
    private int bunnyReproduceTickerB;
    private int jumpTimer;
    private static final DataParameter<Boolean> HAS_EATEN = EntityDataManager.<Boolean>createKey(MoCEntityBunny.class, DataSerializers.BOOLEAN);
    
    public MoCEntityBunny(World world) {
        super(world);
        setAdult(true);
        setTamed(false);
        setEdad(50 + this.rand.nextInt(15));
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);
        }
        setSize(0.5F, 0.5F);
        this.bunnyReproduceTickerA = this.rand.nextInt(64);
        this.bunnyReproduceTickerB = 0;
    }

    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 6F, 5F));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HAS_EATEN, Boolean.valueOf(false));
    }

    public boolean getHasEaten() {
    	return ((Boolean)this.dataManager.get(HAS_EATEN)).booleanValue();
    }

    public void setHasEaten(boolean flag) {
    	this.dataManager.set(HAS_EATEN, Boolean.valueOf(flag));
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {
            setType(this.rand.nextInt(5) + 1);
        }

    }

    @Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        Biome currentbiome = MoCTools.Biomekind(this.worldObj, pos);
        try {
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
                setType(3); //snow white bunnies!
                return true;
            }
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("bunny.png");
            case 2:
                return MoCreatures.proxy.getTexture("bunnyb.png");
            case 3:
                return MoCreatures.proxy.getTexture("bunnyc.png");
            case 4:
                return MoCreatures.proxy.getTexture("bunnyd.png");
            case 5:
                return MoCreatures.proxy.getTexture("bunnye.png");

            default:
                return MoCreatures.proxy.getTexture("bunny.png");
        }
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_RABBIT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return MoCSoundEvents.ENTITY_RABBIT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        if ((stack != null) && onMainHand && (stack.getItem() == Items.GOLDEN_CARROT) && !getHasEaten()) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            setHasEaten(true);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            return true;
        }
        if (this.getRidingEntity() == null) {
            this.startRiding(player);
            this.rotationYaw = player.rotationYaw;
            if (MoCreatures.isServer() && !getIsTamed()) {
                MoCTools.tameWithName(player, this);
            }
        }
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.getRidingEntity() != null) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }
        if (MoCreatures.isServer()) {

            if (--this.jumpTimer <= 0 && this.onGround
                    && ((this.motionX > 0.05D) || (this.motionZ > 0.05D) || (this.motionX < -0.05D) || (this.motionZ < -0.05D))) {
                this.motionY = 0.3D;
                this.jumpTimer = 15;
            }

            if (!getIsTamed() || !getIsAdult() || !getHasEaten() || (this.getRidingEntity() != null)) {
                return;
            }
            if (this.bunnyReproduceTickerA < 1023) {
                this.bunnyReproduceTickerA++;
            } else if (this.bunnyReproduceTickerB < 127) {
                this.bunnyReproduceTickerB++;
            } else {
                List<Entity> list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4.0D, 4.0D, 4.0D));
                for (int i1 = 0; i1 < list1.size(); i1++) {
                    Entity entity1 = (Entity) list1.get(i1);
                    if (!(entity1 instanceof MoCEntityBunny) || (entity1 == this)) {
                        continue;
                    }
                    MoCEntityBunny entitybunny = (MoCEntityBunny) entity1;
                    if ((entitybunny.getRidingEntity() != null) || (entitybunny.bunnyReproduceTickerA < 1023) || !entitybunny.getIsAdult()
                            || !entitybunny.getHasEaten()) {
                        continue;
                    }
                    MoCEntityBunny entitybunny1 = new MoCEntityBunny(this.worldObj);
                    entitybunny1.setPosition(this.posX, this.posY, this.posZ);
                    entitybunny1.setAdult(false);
                    int babytype = this.getType();
                    if (this.rand.nextInt(2) == 0) {
                        babytype = entitybunny.getType();
                    }
                    entitybunny1.setType(babytype);
                    this.worldObj.spawnEntityInWorld(entitybunny1);
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    proceed();
                    entitybunny.proceed();
                    break;
                }
            }
        }
    }

    public void proceed() {
        setHasEaten(false);
        this.bunnyReproduceTickerB = 0;
        this.bunnyReproduceTickerA = this.rand.nextInt(64);
    }

    @Override
    public int nameYOffset() {
        return -40;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.CARROT;
    }

    /**
     * So bunny-hats don't suffer damage
     */
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (this.getRidingEntity() != null) {
            return false;
        }
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public double getYOffset() {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            return ((EntityPlayer) this.getRidingEntity()).isSneaking() ? 0.25 : 0.5F;
        }
        if ((this.getRidingEntity() instanceof EntityPlayer) && !MoCreatures.isServer()) {
            return (super.getYOffset() + 0.5F);
        }
        return super.getYOffset();
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }
    
    @Override
    public boolean canRidePlayer()
    {
    	return true;
    }
}
