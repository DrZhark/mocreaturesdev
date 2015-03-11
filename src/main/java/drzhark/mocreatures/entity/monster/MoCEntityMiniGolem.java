package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityMiniGolem extends MoCEntityMob {

    public int tcounter;
    public MoCEntityThrowableRock tempRock;

    public MoCEntityMiniGolem(World world) {
        super(world);
        this.texture = "minigolem.png";
        setSize(1.0F, 1.0F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
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
                if (getIsAngry()) {
                    setIsAngry(false);
                }
            } else {
                if (!getIsAngry()) {
                    setIsAngry(true);
                }
            }

            if (this.worldObj.isDaytime()) {
                float var1 = this.getBrightness(1.0F);
                if (var1 > 0.5F
                        && this.worldObj.canBlockSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY),
                                MathHelper.floor_double(this.posZ))) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
                    this.setFire(8);
                }
            }

            if (getIsAngry() && this.getAttackTarget() != null) {
                if (!getHasRock() && this.rand.nextInt(30) == 0) {
                    acquireTRock();
                }

                if (getHasRock()) {
                    attackWithTRock();
                }
            }
        }
    }

    protected void acquireTRock() {
        int[] tRockInfo = MoCTools.destroyRandomBlockWithMetadata(this, 3D);
        if (tRockInfo[0] == -1) {
            this.tcounter = 1;
            setHasRock(false);
            return;
        }
        //creates a dummy Trock on top of it
        MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.worldObj, this, this.posX, this.posY + 2.0D, this.posZ);//, true, false);
        this.worldObj.spawnEntityInWorld(trock);

        trock.setType(tRockInfo[0]);
        trock.setMetadata(tRockInfo[1]);
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
                //System.out.println("distance = " + this.getDistanceToEntity(this.getAttackTarget()));
                ThrowStone(this.getAttackTarget(), this.tempRock.getType(), this.tempRock.getMetadata());
            }

            this.tempRock.setDead();
            setHasRock(false);
            this.tcounter = 0;
        }
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (this.attackTime <= 0 && (f < 2.0D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
        }
    }*/

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
        return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    /**
     * Throws stone at entity
     *
     * @param targetEntity
     * @param rocktype
     * @param metadata
     */
    protected void ThrowStone(Entity targetEntity, int rocktype, int metadata) {
        ThrowStone((int) targetEntity.posX, (int) targetEntity.posY, (int) targetEntity.posZ, rocktype, metadata);
    }

    /**
     * Throws stone at X,Y,Z coordinates
     *
     * @param X
     * @param Y
     * @param Z
     * @param rocktype
     * @param metadata
     */
    protected void ThrowStone(int X, int Y, int Z, int rocktype, int metadata) {
        MoCEntityThrowableRock etrock = new MoCEntityThrowableRock(this.worldObj, this, this.posX, this.posY + 3.0D, this.posZ);//, false, false);
        this.worldObj.spawnEntityInWorld(etrock);
        etrock.setType(rocktype);
        etrock.setMetadata(metadata);
        etrock.setBehavior(0);
        etrock.motionX = ((X - this.posX) / 20.0D);
        etrock.motionY = ((Y - this.posY) / 20.0D + 0.5D);
        etrock.motionZ = ((Z - this.posZ) / 20.0D);
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
}
