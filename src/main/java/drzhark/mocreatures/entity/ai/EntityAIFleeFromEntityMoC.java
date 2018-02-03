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
            return entityIn.isEntityAlive() && EntityAIFleeFromEntityMoC.this.entity.getEntitySenses().canSee(entityIn);
        }

        @Override
        public boolean apply(Entity p_apply_1_) {
            return this.isApplicable(p_apply_1_);
        }
    };
    /** The entity we are attached to */
    protected EntityCreature entity;
    private double farSpeed;
    private double nearSpeed;
    protected Entity closestLivingEntity;
    private float avoidDistance;
    private Predicate<Entity> avoidTargetSelector;

    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIFleeFromEntityMoC(EntityCreature creature, Predicate<Entity> targetSelector, float searchDistance, double farSpeedIn, double nearSpeedIn) {
        this.entity = creature;
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
        if (this.entity instanceof IMoCEntity && ((IMoCEntity) this.entity).isNotScared()) {
            return false;
        }

        if (this.entity instanceof MoCEntityAquatic && !this.entity.isInWater()) {
            return false;
        }

        List<Entity> list =
                this.entity.world.getEntitiesInAABBexcluding(this.entity,
                        this.entity.getEntityBoundingBox().expand((double) this.avoidDistance, 3.0D, (double) this.avoidDistance),
                        Predicates.and(new Predicate[] {EntitySelectors.NOT_SPECTATING, this.canBeSeenSelector, this.avoidTargetSelector}));

        if (list.isEmpty()) {
            return false;
        } else {
            this.closestLivingEntity = list.get(0);
            Vec3d vec3 =
                    RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vec3d(this.closestLivingEntity.posX,
                            this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

            if (vec3 == null) {
                return false;
            } else if (this.closestLivingEntity.getDistanceSq(vec3.x, vec3.y, vec3.z) < this.closestLivingEntity
                    .getDistanceSq(this.entity)) {
                return false;
            } else {
                this.randPosX = vec3.x;
                this.randPosY = vec3.y;
                this.randPosZ = vec3.z;
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.entity.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.nearSpeed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.entity.getNavigator().noPath();
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
        if (this.entity.getDistanceSq(this.closestLivingEntity) < 8.0D) {
            this.entity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.entity.getNavigator().setSpeed(this.farSpeed);
        }
    }
}
