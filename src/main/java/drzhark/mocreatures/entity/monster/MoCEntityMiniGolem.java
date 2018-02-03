package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MoCEntityMiniGolem extends MoCEntityMob {

    public int tcounter;
    public MoCEntityThrowableRock tempRock;
    private static final DataParameter<Boolean> ANGRY = EntityDataManager.<Boolean>createKey(MoCEntityMiniGolem.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ROCK = EntityDataManager.<Boolean>createKey(MoCEntityMiniGolem.class, DataSerializers.BOOLEAN);
    

    public MoCEntityMiniGolem(World world) {
        super(world);
        this.texture = "minigolem.png";
        setSize(1.0F, 1.0F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ANGRY, Boolean.valueOf(false));
        this.dataManager.register(HAS_ROCK, Boolean.valueOf(false)); 
    }

    public boolean getIsAngry() {
        return ((Boolean)this.dataManager.get(ANGRY)).booleanValue();
    }

    public void setIsAngry(boolean flag) {
        this.dataManager.set(ANGRY, Boolean.valueOf(flag));
    }

    public boolean getHasRock() {
        return ((Boolean)this.dataManager.get(HAS_ROCK)).booleanValue();
    }

    public void setHasRock(boolean flag) {
        this.dataManager.set(HAS_ROCK, Boolean.valueOf(flag));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            if (this.getAttackTarget() == null) {
                setIsAngry(false);

            } else {
                setIsAngry(true);
            }

            if (getIsAngry() && this.getAttackTarget() != null) {
                if (!getHasRock() && this.rand.nextInt(30) == 0) {
                    acquireTRock();
                }

                if (getHasRock()) {
                    this.getNavigator().clearPath();
                    attackWithTRock();
                }
            }
        }
    }

    protected void acquireTRock() {
        IBlockState tRockState = MoCTools.destroyRandomBlockWithIBlockState(this, 3D);
        if (tRockState == null) {
            this.tcounter = 1;
            setHasRock(false);
            return;
        }

        //creates a dummy Trock on top of it
        MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.world, this, this.posX, this.posY + 1.5D, this.posZ);
        this.world.spawnEntity(trock);
        trock.setState(tRockState);
        trock.setBehavior(1);
        this.tempRock = trock;
        setHasRock(true);
    }

    @Override
    public boolean isMovementCeased() {
        return getHasRock() && this.getAttackTarget() != null;
    }

    /**
     *
     */
    protected void attackWithTRock() {
        this.tcounter++;

        if (this.tcounter < 50) {
            //maintains position of Trock above head
            this.tempRock.posX = this.posX;
            this.tempRock.posY = (this.posY + 1.0D);
            this.tempRock.posZ = this.posZ;
        }

        if (this.tcounter >= 50) {
            //throws a newly spawned Trock and destroys the held Trock
            if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) < 48F) {
                MoCTools.ThrowStone(this, this.getAttackTarget(), this.tempRock.getState(), 10D, 0.25D);
            }

            this.tempRock.setDead();
            setHasRock(false);
            this.tcounter = 0;
        }
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_WALK);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_GOLEM_DYING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_GOLEM_AMBIENT;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }
}
