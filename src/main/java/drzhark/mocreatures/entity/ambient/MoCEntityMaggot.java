package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class MoCEntityMaggot extends MoCEntityAmbient {

    public MoCEntityMaggot(World world) {
        super(world);
        setSize(0.2F, 0.2F);
        this.texture = "maggot.png";
        this.tasks.addTask(1, new EntityAIWanderMoC2(this, 0.8D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.1D);
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected Item getDropItem() {
        return Items.slime_ball;
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }
}
