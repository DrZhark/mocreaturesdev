package drzhark.mocreatures.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class MoCEntityFlyerMob extends MoCEntityMob {

    protected int attackStrength;
    private PathEntity entitypath;

    public MoCEntityFlyerMob(World world) {
        super(world);
        this.isCollidedVertically = false;
        setSize(1.5F, 1.5F);
        this.attackStrength = 3;
        //health = 10;
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (attackTime <= 0 && (f < 2.5D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), this.attackStrength);
        }
    }*/

    @Override
    public void fall(float f, float f1) {
    }

    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 20D);
        if ((entityplayer != null) && canEntityBeSeen(entityplayer)) {
            return entityplayer;
        } else {
            return null;
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere();
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public void moveEntityWithHeading(float f, float f1) {
        if (handleWaterMovement()) {
            double d = this.posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.80000001192092896D;
            this.motionY *= 0.80000001192092896D;
            this.motionZ *= 0.80000001192092896D;
        } else if (handleLavaMovement()) {
            double d1 = this.posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        } else {
            float f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                Block block =
                        this.worldObj.getBlockState(
                                new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper
                                        .floor_double(this.posZ))).getBlock();
                if (block != Blocks.air) {
                    f2 = block.slipperiness * 0.91F;
                }
            }
            float f3 = 0.162771F / (f2 * f2 * f2);
            moveFlying(f, f1, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                Block block =
                        this.worldObj.getBlockState(
                                new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper
                                        .floor_double(this.posZ))).getBlock();
                if (block != Blocks.air) {
                    f2 = block.slipperiness * 0.91F;
                }
            }
            moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= f2;
            this.motionY *= f2;
            this.motionZ *= f2;
            if (this.isCollidedHorizontally) {
                this.motionY = 0.20000000000000001D;
            }
            if (this.rand.nextInt(30) == 0) {
                this.motionY = -0.25D;
            }
        }
        double d2 = this.posX - this.prevPosX;
        double d3 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt_double((d2 * d2) + (d3 * d3)) * 4F;
        if (f4 > 1.0F) {
            f4 = 1.0F;
        }
    }

    /*@Override
    protected void updateEntityActionState() {
        hasAttacked = false;
        float f = 16F;
        if (this.entityLivingToAttack == null) {
            this.entityLivingToAttack = findPlayerToAttack();
            if (this.entityLivingToAttack != null) {
                this.entitypath = this.worldObj.getPathEntityToEntity(this, this.entityLivingToAttack, f, true, false, false, true);
            }
        } else if (!this.entityLivingToAttack.isEntityAlive()) {
            this.entityLivingToAttack = null;
        } else {
            float f1 = this.entityLivingToAttack.getDistanceToEntity(this);
            if (canEntityBeSeen(this.entityLivingToAttack)) {
                attackEntity(this.entityLivingToAttack, f1);
            }
        }
        if (!hasAttacked && (this.entityLivingToAttack != null) && ((this.entitypath == null) || (this.rand.nextInt(10) == 0))) {
            this.entitypath = this.worldObj.getPathEntityToEntity(this, this.entityLivingToAttack, f, true, false, false, true);
        } else if (((this.entitypath == null) && (this.rand.nextInt(80) == 0)) || (this.rand.nextInt(80) == 0)) {
            boolean flag = false;
            int j = -1;
            int k = -1;
            int l = -1;
            float f2 = -99999F;
            for (int i1 = 0; i1 < 10; i1++) {
                int j1 = MathHelper.floor_double((this.posX + this.rand.nextInt(13)) - 6D);
                int k1 = MathHelper.floor_double((this.posY + this.rand.nextInt(7)) - 3D);
                int l1 = MathHelper.floor_double((this.posZ + this.rand.nextInt(13)) - 6D);
                float f3 = getBlockPathWeight(j1, k1, l1);
                if (f3 > f2) {
                    f2 = f3;
                    j = j1;
                    k = k1;
                    l = l1;
                    flag = true;
                }
            }

            if (flag) {
                this.entitypath = this.worldObj.getEntityPathToXYZ(this, j, k, l, 10F, true, false, false, true);
            }
        }
        int i = MathHelper.floor_double(getEntityBoundingBox().minY);
        boolean flag1 = handleWaterMovement();
        boolean flag2 = handleLavaMovement();
        this.rotationPitch = 0.0F;
        if ((this.entitypath == null) || (this.rand.nextInt(100) == 0)) {
            super.updateEntityActionState();
            this.entitypath = null;
            return;
        }
        //TODO 4FIX test!
        Vec3 vec3d = this.entitypath.getPosition(this); //Client
        //Vec3D vec3d = entitypath.getPosition(this); //Server
        for (double d = this.width * 2.0F; (vec3d != null) && (vec3d.squareDistanceTo(this.posX, vec3d.yCoord, this.posZ) < (d * d));) {
            this.entitypath.incrementPathIndex();
            if (this.entitypath.isFinished()) {
                vec3d = null;
                this.entitypath = null;
            } else {
                //TODO 4FIX test!
                vec3d = this.entitypath.getPosition(this); //client
                //vec3d = entitypath.getPosition(this); //server
            }
        }

        this.isJumping = false;
        if (vec3d != null) {
            double d1 = vec3d.xCoord - this.posX;
            double d2 = vec3d.zCoord - this.posZ;
            double d3 = vec3d.yCoord - i;
            float f4 = (float) ((Math.atan2(d2, d1) * 180D) / 3.1415927410125728D) - 90F;
            float f5 = f4 - this.rotationYaw;
            this.moveForward = (float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            for (; f5 < -180F; f5 += 360F) {
            }
            for (; f5 >= 180F; f5 -= 360F) {
            }
            if (f5 > 30F) {
                f5 = 30F;
            }
            if (f5 < -30F) {
                f5 = -30F;
            }
            this.rotationYaw += f5;
            if (hasAttacked && (this.entityLivingToAttack != null)) {
                double d4 = entityLivingToAttack.posX - this.posX;
                double d5 = entityLivingToAttack.posZ - this.posZ;
                float f6 = this.rotationYaw;
                this.rotationYaw = (float) ((Math.atan2(d5, d4) * 180D) / 3.1415927410125728D) - 90F;
                float f7 = (((f6 - this.rotationYaw) + 90F) * 3.141593F) / 180F;
                this.moveStrafing = -MathHelper.sin(f7) * this.moveForward * 1.0F;
                this.moveForward = MathHelper.cos(f7) * this.moveForward * 1.0F;
            }
            if (d3 > 0.0D) {
                this.isJumping = true;
            }
        }
        if (this.entityLivingToAttack != null) {
            faceEntity(this.entityLivingToAttack, 30F, 30F);
        }
        if (this.isCollidedHorizontally) {
            this.isJumping = true;
        }
        if ((this.rand.nextFloat() < 0.8F) && (flag1 || flag2)) {
            this.isJumping = true;
        }
    }*/
}
