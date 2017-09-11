package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageExplode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityOgre extends MoCEntityMob {

    public int attackCounterLeft;
    public int attackCounterRight;
    private int movingHead;
    public int smashCounter;
    public int armToAnimate; // 1 = left, 2 = right, 3 = both
    public int attackCounter;

    public MoCEntityOgre(World world) {
        super(world);
        setSize(1.9F, 3F);
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(2) + 1);
        }
    }

    public float calculateMaxHealth() {
        return 35F;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if ((entity != this) && (this.world.getDifficulty().getDifficultyId() > 0) && entity instanceof EntityLivingBase) {
                setAttackTarget((EntityLivingBase) entity);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldAttackPlayers() {
        return (this.getBrightness() < 0.5F) && super.shouldAttackPlayers();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_OGRE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_OGRE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_OGRE_AMBIENT;
    }

    public boolean isFireStarter() {
        return false;
    }

    /**
     * Returns the strength of the blasting power
     * @return
     */
    public float getDestroyForce() {
        return MoCreatures.proxy.ogreStrength;
    }

    public int getAttackRange() {
        return MoCreatures.proxy.ogreAttackRange;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.world.isRemote) {
            if (this.smashCounter > 0 && ++this.smashCounter > 10) {
                this.smashCounter = 0;
                performDestroyBlastAttack();
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageExplode(this.getEntityId()),
                        new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            }

            if ((this.getAttackTarget() != null) && (this.rand.nextInt(40) == 0) && this.smashCounter == 0 && this.attackCounter == 0) {
                startDestroyBlast();
            }
        }

        if (this.attackCounter > 0) {
            if (armToAnimate == 3) {
                this.attackCounter++;
            } else {
                this.attackCounter += 2;
            }

            if (this.attackCounter > 10) {
                this.attackCounter = 0;
                this.armToAnimate = 0;
            }
        }
        super.onLivingUpdate();
    }

    /**
     * Starts counter to perform the DestroyBlast and synchronizes animations with clients
     */
    protected void startDestroyBlast() {
        this.smashCounter = 1;
        MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
    }

    /**
     * Performs the destroy Blast Attack
     */
    public void performDestroyBlastAttack() {
        if (this.deathTime > 0) {
            return;
        }
        MoCTools.DestroyBlast(this, this.posX, this.posY + 1.0D, this.posZ, getDestroyForce(), isFireStarter());
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return false;
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    protected void startArmSwingAttack() {
        if (!this.world.isRemote) {
            if (this.smashCounter != 0)
                return;

            boolean leftArmW = (getType() == 2 || getType() == 4 || getType() == 6) && this.rand.nextInt(2) == 0;

            if (leftArmW) {
                this.attackCounter = 1;
                this.armToAnimate = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                        new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            } else {
                this.attackCounter = 1;
                this.armToAnimate = 2;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2),
                        new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType != 0) {
            this.attackCounter = 1;
            this.armToAnimate = animationType;
        }

    }

    public int getMovingHead() {
        if (getType() == 1) //single headed ogre
        {
            return 1;
        }

        if (this.rand.nextInt(60) == 0) {
            this.movingHead = this.rand.nextInt(2) + 2; //randomly changes the focus head, returns 2 or 3
        }
        return this.movingHead;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startArmSwingAttack();
        return super.attackEntityAsMob(entityIn);
    }
}
