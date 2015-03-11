package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.entity.EntityCreature;

public class EntityAIHunt extends EntityAINearestAttackableTargetMoC {

    private EntityCreature hunter;
    private static final String __OBFID = "CL_00001623";

    public EntityAIHunt(EntityCreature entity, Class classTarget, int chance, boolean checkSight, boolean onlyNearby, Predicate predicate) {
        super(entity, classTarget, chance, checkSight, onlyNearby, predicate);
        this.hunter = entity;
    }

    public EntityAIHunt(EntityCreature entityCreature, Class classTarget, boolean checkSight) {
        this(entityCreature, classTarget, checkSight, false);
    }

    public EntityAIHunt(EntityCreature entity, Class classTarget, boolean checkSight, boolean onlyNearby) {
        this(entity, classTarget, 10, checkSight, onlyNearby, (Predicate) null);

    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        return ((MoCEntityAnimal) this.hunter).getIsHunting() && super.shouldExecute();
    }
}
