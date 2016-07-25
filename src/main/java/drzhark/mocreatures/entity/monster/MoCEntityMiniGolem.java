package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class MoCEntityMiniGolem extends MoCEntityMob {

    public int tcounter;
    public MoCEntityThrowableRock tempRock;

    public MoCEntityMiniGolem(World world) {
        super(world);
        this.texture = "minigolem.png";
        setSize(1.0F, 1.0F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(2, this.aiAvoidExplodingCreepers);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // angry 0 = false, 1 = true
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // hasRock 0 = false, 1 = true
    }

    public boolean getIsAngry() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setIsAngry(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public boolean getHasRock() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setHasRock(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {
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
                    this.getNavigator().clearPathEntity();
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
        MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.worldObj, this, this.posX, this.posY + 1.5D, this.posZ);
        this.worldObj.spawnEntityInWorld(trock);
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
            if (this.getAttackTarget() != null && this.getDistanceToEntity(this.getAttackTarget()) < 48F) {
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
        this.playSound("mocreatures:minigolemwalk", 1.0F, 1.0F);
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:golemgrunt";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:golemgrunt";
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }
}
