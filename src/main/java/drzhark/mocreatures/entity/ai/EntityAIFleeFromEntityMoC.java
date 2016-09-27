package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class EntityAIFleeFromEntityMoC extends EntityAIBase {

    public final Predicate<Entity> canBeSeenSelector = new Predicate<Entity>() {

        public boolean isApplicable(Entity entityIn) {
            return entityIn.isEntityAlive() && EntityAIFleeFromEntityMoC.this.theEntity.getEntitySenses().canSee(entityIn);
        }

        @Override
        public boolean apply(Entity p_apply_1_) {
            return this.isApplicable(p_apply_1_);
        }
    };
    /** The entity we are attached to */
    protected EntityCreature theEntity;
    private double farSpeed;
    private double nearSpeed;
    protected Entity closestLivingEntity;
    private float avoidDistance;
    private Predicate<Entity> avoidTargetSelector;

    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIFleeFromEntityMoC(EntityCreature creature, Predicate<Entity> targetSelector, float searchDistance, double farSpeedIn, double nearSpeedIn) {
        this.theEntity = creature;
        this.avoidTargetSelector = targetSelector;
        this.avoidDistance = searchDistance;
        this.farSpeed = farSpeedIn;
        this.nearSpeed = nearSpeedIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean shouldExecute() {
        if (this.theEntity instanceof IMoCEntity && ((IMoCEntity) this.theEntity).isNotScared()) {
            return false;
        }

        if (this.theEntity instanceof MoCEntityAquatic && !this.theEntity.isInWater()) {
            return false;
        }

        List<Entity> list =
                this.theEntity.worldObj.getEntitiesInAABBexcluding(this.theEntity,
                        this.theEntity.getEntityBoundingBox().expand((double) this.avoidDistance, 3.0D, (double) this.avoidDistance),
                        Predicates.and(new Predicate[] {EntitySelectors.NOT_SPECTATING, this.canBeSeenSelector, this.avoidTargetSelector}));

        if (list.isEmpty()) {
            return false;
        } else {
            this.closestLivingEntity = list.get(0);
            Vec3d vec3 =
                    RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3d(this.closestLivingEntity.posX,
                            this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

            if (vec3 == null) {
                return false;
            } else if (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity
                    .getDistanceSqToEntity(this.theEntity)) {
                return false;
            } else {
                this.randPosX = vec3.xCoord;
                this.randPosY = vec3.yCoord;
                this.randPosZ = vec3.zCoord;
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.theEntity.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.nearSpeed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        return !this.theEntity.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 8.0D) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }
}
