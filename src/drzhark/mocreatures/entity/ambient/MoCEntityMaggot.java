package drzhark.mocreatures.entity.ambient;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import drzhark.mocreatures.entity.MoCEntityAmbient;

public class MoCEntityMaggot extends MoCEntityAmbient
{
    public MoCEntityMaggot(World world)
    {
        super(world);
        setSize(0.2F, 0.2F);
        //health = 2;
        texture = "maggot.png";
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.15F;
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    protected Item getDropItem()
    {
        return Items.slime_ball;
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    public boolean climbing()
    {
        return !onGround && isOnLadder();
    }

    @Override
    public void jump()
    {
    }
}