package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowAdult extends EntityAIBase {

    /** The child that is following its parent. */
    EntityLiving childAnimal;
    EntityLiving parentAnimal;
    double moveSpeed;
    private int delayCounter;

    public EntityAIFollowAdult(EntityLiving animal, double speed) {
        this.childAnimal = animal;
        this.moveSpeed = speed;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if ((!(this.childAnimal instanceof IMoCEntity)) || ((IMoCEntity) this.childAnimal).isMovementCeased()
                || ((IMoCEntity) this.childAnimal).getIsAdult()) {
            return false;
        } else {
            List list =
                    this.childAnimal.worldObj.getEntitiesWithinAABB(this.childAnimal.getClass(),
                            this.childAnimal.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D));
            EntityLiving entityliving = null;
            double d0 = Double.MAX_VALUE;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityLiving entityliving1 = (EntityLiving) iterator.next();

                if (((IMoCEntity) entityliving1).getIsAdult()) {
                    double d1 = this.childAnimal.getDistanceSqToEntity(entityliving1);

                    if (d1 <= d0) {
                        d0 = d1;
                        entityliving = entityliving1;
                    }
                }
            }

            if (entityliving == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.parentAnimal = entityliving;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        if (((IMoCEntity) this.childAnimal).getIsAdult()) {
            return false;
        } else if (!this.parentAnimal.isEntityAlive()) {
            return false;
        } else {
            double d0 = this.childAnimal.getDistanceSqToEntity(this.parentAnimal);
            return d0 >= 9.0D && d0 <= 256.0D;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.delayCounter = 0;
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void updateTask() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }
}
