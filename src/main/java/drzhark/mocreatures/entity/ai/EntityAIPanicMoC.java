package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIPanic;

public class EntityAIPanicMoC extends EntityAIPanic {

    private EntityCreature theEntityCreature;

    public EntityAIPanicMoC(EntityCreature creature, double speedIn) {
        super(creature, speedIn);
        this.theEntityCreature = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (this.theEntityCreature instanceof IMoCEntity && ((IMoCEntity) this.theEntityCreature).isNotScared()) {
            return false;
        }
        return super.shouldExecute();
    }

}
