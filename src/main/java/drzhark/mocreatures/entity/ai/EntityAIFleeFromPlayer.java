package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class EntityAIFleeFromPlayer extends EntityAIBase {

    private EntityCreature entityCreature;
    protected double speed;
    protected double distance;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIFleeFromPlayer(EntityCreature creature, double speedIn, double distanceToCheck) {
        this.entityCreature = creature;
        this.distance = distanceToCheck;
        this.speed = speedIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {

        if (this.entityCreature instanceof IMoCEntity) {
            if (((IMoCEntity) this.entityCreature).isNotScared()) {
                return false;
            }
        }

        if (!this.IsNearPlayer(this.distance)) {
            return false;
        } else {
            Vec3d vec3 = RandomPositionGenerator.findRandomTarget(this.entityCreature, 5, 4);

            if (vec3 == null) {
                return false;
            } else {
                this.randPosX = vec3.x;
                this.randPosY = vec3.y;
                this.randPosZ = vec3.z;
                return true;
            }
        }
    }

    protected boolean IsNearPlayer(double d) {
        EntityPlayer entityplayer1 = this.entityCreature.world.getClosestPlayerToEntity(this.entityCreature, d);
        if (entityplayer1 != null) {
            return true;
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.entityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.entityCreature.getNavigator().noPath();
    }
}
