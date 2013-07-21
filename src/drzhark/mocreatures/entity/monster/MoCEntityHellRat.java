package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class MoCEntityHellRat extends MoCEntityRat {

    public MoCEntityHellRat(World world)
    {
        super(world);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "hellrat1.png";
        setSize(0.7F, 0.7F);
        //health = 20;
        isImmuneToFire = true;
    }

    @Override
    public void selectType()
    {
        setType(4);
    }

    @Override
    public float getMaxHealth()
    {
        return 20;
    }

    @Override
    protected int getDropItemId()
    {
        boolean flag = (rand.nextInt(3) == 0);
        if (flag) { return Block.fire.blockID; }
        return Item.redstone.itemID;
    }
}