package drzhark.mocreatures.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;

public class EntityAIWanderMoC extends EntityAIWander {

    private EntityCreature theEntityCreature;

    public EntityAIWanderMoC(EntityCreature creature, double speedIn) {
        super(creature, speedIn);
        this.theEntityCreature = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        /*if (theEntityCreature instanceof MoCEntityAnimal && ((MoCEntityAnimal)theEntityCreature).getisSitting())
        {
            return false;
        }*/
        return super.shouldExecute();
    }

}
