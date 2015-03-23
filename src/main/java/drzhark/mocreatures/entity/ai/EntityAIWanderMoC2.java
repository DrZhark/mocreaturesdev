package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIWanderMoC2 extends EntityAIBase {

    private EntityCreature entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private int executionChance;
    private boolean mustUpdate;
    //private int moveExecutionCounter;

    public EntityAIWanderMoC2(EntityCreature creatureIn, double speedIn)
    {
        this(creatureIn, speedIn, 120);
    }
    
    
    public EntityAIWanderMoC2(EntityCreature creatureIn, double speedIn, int chance)
    {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setMutexBits(1);
    }
    
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (entity instanceof IMoCEntity && ((IMoCEntity) entity).isMovementCeased()) {
            return false;
        }
        if (entity.riddenByEntity != null)
        {
            return false;
        }
        
        if (!this.mustUpdate)
        {
            if (this.entity.getAge() >= 100)
            {
                //System.out.println("exiting path finder !mustUpdate + Age > 100" + this.entity);
                return false;
            }

            if (this.entity.getRNG().nextInt(this.executionChance) != 0)
            {
                //System.out.println(this.entity + "exiting due executionChance, age = " + this.entity.getAge() + ", executionChance = " + this.executionChance );
                return false;
            }
        }

        Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

        if (vec3 == null)
        {
            //System.out.println("exiting path finder null Vec3");
            return false;
        }
        else
        {
            //System.out.println("found vector " + vec3.xCoord + ", " +  vec3.yCoord + ", " + vec3.zCoord);
            this.xPosition = vec3.xCoord;
            this.yPosition = vec3.yCoord;
            this.zPosition = vec3.zCoord;
            this.mustUpdate = false;
            return true;
        }
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.entity.getNavigator().noPath() && this.entity.riddenByEntity == null;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        //System.out.println("moving to " + this.xPosition + ", " + this.yPosition + ", " + this.zPosition);
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    /**
     * Makes task to bypass chance
     */
    public void makeUpdate()
    {
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance)
    {
        this.executionChance = newchance;
    }
}


