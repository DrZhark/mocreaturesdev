package drzhark.mocreatures.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class MoCEntityFlyerMob extends MoCEntityMob {
    protected int attackStrength;
    private PathEntity entitypath;

    public MoCEntityFlyerMob(World world)
    {
        super(world);
        isCollidedVertically = false;
        setSize(1.5F, 1.5F);
        attackStrength = 3;
        //health = 10;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackStrength);
        }
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 20D);
        if ((entityplayer != null) && canEntityBeSeen(entityplayer))
        {
            return entityplayer;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHere();
    }

    @Override
    public boolean isOnLadder()
    {
        return false;
    }

    @Override
    public void moveEntityWithHeading(float f, float f1)
    {
        if (handleWaterMovement())
        {
            double d = posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.80000001192092896D;
            motionY *= 0.80000001192092896D;
            motionZ *= 0.80000001192092896D;
        }
        else if (handleLavaMovement())
        {
            double d1 = posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
        }
        else
        {
            float f2 = 0.91F;
            if (onGround)
            {
                f2 = 0.5460001F;
                Block block = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if (block != Blocks.air)
                {
                    f2 = block.slipperiness * 0.91F;
                }
            }
            float f3 = 0.162771F / (f2 * f2 * f2);
            moveFlying(f, f1, onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (onGround)
            {
                f2 = 0.5460001F;
                Block block = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if (block != Blocks.air)
                {
                    f2 = block.slipperiness * 0.91F;
                }
            }
            moveEntity(motionX, motionY, motionZ);
            motionX *= f2;
            motionY *= f2;
            motionZ *= f2;
            if (isCollidedHorizontally)
            {
                motionY = 0.20000000000000001D;
            }
            if (rand.nextInt(30) == 0)
            {
                motionY = -0.25D;
            }
        }
        double d2 = posX - prevPosX;
        double d3 = posZ - prevPosZ;
        float f4 = MathHelper.sqrt_double((d2 * d2) + (d3 * d3)) * 4F;
        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }
    }

    @Override
    protected void updateEntityActionState()
    {
        hasAttacked = false;
        float f = 16F;
        if (entityToAttack == null)
        {
            entityToAttack = findPlayerToAttack();
            if (entityToAttack != null)
            {
                entitypath = worldObj.getPathEntityToEntity(this, entityToAttack, f, true, false, false, true);
            }
        }
        else if (!entityToAttack.isEntityAlive())
        {
            entityToAttack = null;
        }
        else
        {
            float f1 = entityToAttack.getDistanceToEntity(this);
            if (canEntityBeSeen(entityToAttack))
            {
                attackEntity(entityToAttack, f1);
            }
        }
        if (!hasAttacked && (entityToAttack != null) && ((entitypath == null) || (rand.nextInt(10) == 0)))
        {
            entitypath = worldObj.getPathEntityToEntity(this, entityToAttack, f, true, false, false, true);
        }
        else if (((entitypath == null) && (rand.nextInt(80) == 0)) || (rand.nextInt(80) == 0))
        {
            boolean flag = false;
            int j = -1;
            int k = -1;
            int l = -1;
            float f2 = -99999F;
            for (int i1 = 0; i1 < 10; i1++)
            {
                int j1 = MathHelper.floor_double((posX + rand.nextInt(13)) - 6D);
                int k1 = MathHelper.floor_double((posY + rand.nextInt(7)) - 3D);
                int l1 = MathHelper.floor_double((posZ + rand.nextInt(13)) - 6D);
                float f3 = getBlockPathWeight(j1, k1, l1);
                if (f3 > f2)
                {
                    f2 = f3;
                    j = j1;
                    k = k1;
                    l = l1;
                    flag = true;
                }
            }

            if (flag)
            {
                entitypath = worldObj.getEntityPathToXYZ(this, j, k, l, 10F, true, false, false, true);
            }
        }
        int i = MathHelper.floor_double(boundingBox.minY);
        boolean flag1 = handleWaterMovement();
        boolean flag2 = handleLavaMovement();
        rotationPitch = 0.0F;
        if ((entitypath == null) || (rand.nextInt(100) == 0))
        {
            super.updateEntityActionState();
            entitypath = null;
            return;
        }
        //TODO 4FIX test!
        Vec3 vec3d = entitypath.getPosition(this); //Client
        //Vec3D vec3d = entitypath.getPosition(this); //Server
        for (double d = width * 2.0F; (vec3d != null) && (vec3d.squareDistanceTo(posX, vec3d.yCoord, posZ) < (d * d));)
        {
            entitypath.incrementPathIndex();
            if (entitypath.isFinished())
            {
                vec3d = null;
                entitypath = null;
            }
            else
            {
                //TODO 4FIX test!
                vec3d = entitypath.getPosition(this); //client
                //vec3d = entitypath.getPosition(this); //server
            }
        }

        isJumping = false;
        if (vec3d != null)
        {
            double d1 = vec3d.xCoord - posX;
            double d2 = vec3d.zCoord - posZ;
            double d3 = vec3d.yCoord - i;
            float f4 = (float) ((Math.atan2(d2, d1) * 180D) / 3.1415927410125728D) - 90F;
            float f5 = f4 - rotationYaw;
            moveForward = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            for (; f5 < -180F; f5 += 360F)
            {
            }
            for (; f5 >= 180F; f5 -= 360F)
            {
            }
            if (f5 > 30F)
            {
                f5 = 30F;
            }
            if (f5 < -30F)
            {
                f5 = -30F;
            }
            rotationYaw += f5;
            if (hasAttacked && (entityToAttack != null))
            {
                double d4 = entityToAttack.posX - posX;
                double d5 = entityToAttack.posZ - posZ;
                float f6 = rotationYaw;
                rotationYaw = (float) ((Math.atan2(d5, d4) * 180D) / 3.1415927410125728D) - 90F;
                float f7 = (((f6 - rotationYaw) + 90F) * 3.141593F) / 180F;
                moveStrafing = -MathHelper.sin(f7) * moveForward * 1.0F;
                moveForward = MathHelper.cos(f7) * moveForward * 1.0F;
            }
            if (d3 > 0.0D)
            {
                isJumping = true;
            }
        }
        if (entityToAttack != null)
        {
            faceEntity(entityToAttack, 30F, 30F);
        }
        if (isCollidedHorizontally)
        {
            isJumping = true;
        }
        if ((rand.nextFloat() < 0.8F) && (flag1 || flag2))
        {
            isJumping = true;
        }
    }
}