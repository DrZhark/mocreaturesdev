package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class EntityAIFleeFromPlayer extends EntityAIBase {

    private EntityCreature theEntityCreature;
    protected double speed;
    protected double distance;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIFleeFromPlayer(EntityCreature creature, double speedIn, double distanceToCheck) {
        this.theEntityCreature = creature;
        this.distance = distanceToCheck;
        this.speed = speedIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {

        if (this.theEntityCreature instanceof IMoCEntity) {
            if (((IMoCEntity) this.theEntityCreature).isNotScared()) {
                return false;
            }
        }

        if (!this.IsNearPlayer(this.distance)) {
            return false;
        } else {
            Vec3d vec3 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);

            if (vec3 == null) {
                return false;
            } else {
                this.randPosX = vec3.xCoord;
                this.randPosY = vec3.yCoord;
                this.randPosZ = vec3.zCoord;
                return true;
            }
        }
    }

    protected boolean IsNearPlayer(double d) {
        EntityPlayer entityplayer1 = this.theEntityCreature.worldObj.getClosestPlayerToEntity(this.theEntityCreature, d);
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
        this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean continueExecuting() {
        return !this.theEntityCreature.getNavigator().noPath();
    }
}
