package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;

public class EntityAIWanderMoC extends EntityAIWander {

    private EntityCreature entityCreature;

    public EntityAIWanderMoC(EntityCreature creature, double speedIn) {
        super(creature, speedIn);
        this.entityCreature = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (this.entityCreature instanceof IMoCEntity && ((IMoCEntity) this.entityCreature).isMovementCeased()) {
            return false;
        }
        return super.shouldExecute();
    }

}
