package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.entity.MoCEntityAmbient;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class MoCEntityMaggot extends MoCEntityAmbient {

    public MoCEntityMaggot(World world) {
        super(world);
        setSize(0.2F, 0.2F);
        //health = 2;
        this.texture = "maggot.png";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
    }

    @Override
    public float getMoveSpeed() {
        return 0.15F;
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
