package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityAIFollowOwnerPlayer extends EntityAIBase {

    private EntityLiving thePet;
    private EntityPlayer theOwner;
    World world;
    private double speed;
    private PathNavigate petPathfinder;
    private int delayCounter;
    float maxDist;
    float minDist;

    public EntityAIFollowOwnerPlayer(EntityLiving thePetIn, double speedIn, float minDistIn, float maxDistIn) {
        this.thePet = thePetIn;
        this.world = thePetIn.world;
        this.speed = speedIn;
        this.petPathfinder = thePetIn.getNavigator();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setMutexBits(3);

        //if (!(thePetIn.getNavigator() instanceof PathNavigateGround)) {
        //System.out.println("exiting due to first illegal argument");
        //    throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        //}
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (((IMoCEntity) this.thePet).getIsSitting()) {
            return false;
        }

        UUID ownerUniqueId = ((IMoCTameable) this.thePet).getOwnerId();
        if (ownerUniqueId == null) {
            return false;
        }

        EntityPlayer entityplayer = EntityAITools.getIMoCTameableOwner((IMoCTameable) this.thePet);

        if (entityplayer == null) {
            return false;
        }

        else if (this.thePet.getDistanceSq(entityplayer) < this.minDist * this.minDist
                || this.thePet.getDistanceSq(entityplayer) > this.maxDist * this.maxDist) {
            return false;
        } else {
            this.theOwner = entityplayer;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.petPathfinder.noPath() && this.thePet.getDistanceSq(this.theOwner) > this.maxDist * this.maxDist
                && !((IMoCEntity) this.thePet).getIsSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.delayCounter = 0;
        //this.flag = ((PathNavigateGround) this.thePet.getNavigator()).getAvoidsWater();
        //((PathNavigateGround) this.thePet.getNavigator()).setAvoidsWater(false);
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPath();
        //((PathNavigateGround) this.thePet.getNavigator()).setAvoidsWater(true); //TODO
    }

    private boolean isEmptyBlock(BlockPos pos)
    {
        IBlockState iblockstate = this.world.getBlockState(pos);
        return iblockstate.getMaterial() == Material.AIR ? true : !iblockstate.isFullCube();
    }
    
    public void updateTask()
    {
        this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, (float)this.thePet.getVerticalFaceSpeed());

        if (!((IMoCEntity) this.thePet).getIsSitting()) {
            if (--this.delayCounter <= 0)
            {
                this.delayCounter = 10;

                if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.speed))
                {
                    if (!this.thePet.getLeashed())
                    {
                        if (this.thePet.getDistanceSq(this.theOwner) >= 144.0D)
                        {
                            int i = MathHelper.floor(this.theOwner.posX) - 2;
                            int j = MathHelper.floor(this.theOwner.posZ) - 2;
                            int k = MathHelper.floor(this.theOwner.getEntityBoundingBox().minY);

                            for (int l = 0; l <= 4; ++l)
                            {
                                for (int i1 = 0; i1 <= 4; ++i1)
                                {
                                    final BlockPos pos = new BlockPos(i + l, k - 1, j + i1);
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.world.getBlockState(pos).isSideSolid(world, pos, EnumFacing.DOWN) && this.isEmptyBlock(new BlockPos(i + l, k, j + i1)) && this.isEmptyBlock(new BlockPos(i + l, k + 1, j + i1)))
                                    {
                                        this.thePet.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.thePet.rotationYaw, this.thePet.rotationPitch);
                                        this.petPathfinder.clearPath();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
