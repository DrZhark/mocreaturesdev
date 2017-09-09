package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ambient.MoCEntityAnt;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityAIWanderMoC2 extends EntityAIBase {

    private EntityCreature entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private int executionChance;
    private boolean mustUpdate;

    public EntityAIWanderMoC2(EntityCreature creatureIn, double speedIn) {
        this(creatureIn, speedIn, 120);
    }

    public EntityAIWanderMoC2(EntityCreature creatureIn, double speedIn, int chance) {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (this.entity instanceof IMoCEntity && ((IMoCEntity) this.entity).isMovementCeased()) {
            return false;
        }
        if (this.entity.isBeingRidden() && !(this.entity instanceof MoCEntityAnt || this.entity instanceof MoCEntityMob)) {
            return false;
        }

        if (!this.mustUpdate) {
            if (this.entity.getIdleTime() >= 100) {
                //System.out.println("exiting path finder !mustUpdate + Age > 100" + this.entity);
                return false;
            }

            if (this.entity.getRNG().nextInt(this.executionChance) != 0) {
                //System.out.println(this.entity + "exiting due executionChance, age = " + this.entity.getIdleTime() + ", executionChance = " + this.executionChance );
                return false;
            }
        }

        Vec3d vec3 = RandomPositionGeneratorMoCFlyer.findRandomTarget(this.entity, 10, 12);

        if (vec3 != null && this.entity instanceof IMoCEntity && this.entity.getNavigator() instanceof PathNavigateFlyer) {
            int distToFloor = MoCTools.distanceToFloor(this.entity);
            int finalYHeight = distToFloor + MathHelper.floor(vec3.y - this.entity.posY);
            if ((finalYHeight < ((IMoCEntity) this.entity).minFlyingHeight())) {
                //System.out.println("vector height " + finalYHeight + " smaller than min flying height " + ((IMoCEntity) this.entity).minFlyingHeight());
                return false;
            }
            if ((finalYHeight > ((IMoCEntity) this.entity).maxFlyingHeight())){
                //System.out.println("vector height " + finalYHeight + " bigger than max flying height " + ((IMoCEntity) this.entity).maxFlyingHeight());
                return false;
            }

        }

        if (vec3 == null) {
            //System.out.println("exiting path finder null Vec3");
            return false;
        } else {
            //System.out.println("found vector " + vec3.x + ", " +  vec3.y + ", " + vec3.z);
            this.xPosition = vec3.x;
            this.yPosition = vec3.y;
            this.zPosition = vec3.z;
            this.mustUpdate = false;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        return !this.entity.getNavigator().noPath() && !entity.isBeingRidden();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        //System.out.println(this.entity + "moving to " + this.xPosition + ", " + this.yPosition + ", " + this.zPosition);
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    /**
     * Makes task to bypass chance
     */
    public void makeUpdate() {
        //System.out.println(entity + " has forced update");
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }
}
