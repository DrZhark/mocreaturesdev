package drzhark.mocreatures.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;

public class MoCEntityHellRat extends MoCEntityRat {

    private int textCounter;

    public MoCEntityHellRat(World world)
    {
        super(world);
        setSize(0.7F, 0.7F);
        isImmuneToFire = true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20.0D);
    }

    @Override
    public void selectType()
    {
        setType(4);
    }

    @Override
    public ResourceLocation getTexture()
    {
        if (rand.nextInt(2) == 0)
        {
            textCounter++;
        }
        if (textCounter < 10)
        {
            textCounter = 10;
        }
        if (textCounter > 29)
        {
            textCounter = 10;
        }
        String textNumber = "" + textCounter;
        textNumber = textNumber.substring(0, 1);
        return MoCreatures.proxy.getTexture("hellrat" + textNumber + ".png");
    }

    @Override
    protected int getDropItemId()
    {
        boolean flag = (rand.nextInt(3) == 0);
        if (flag) { return Block.fire.blockID; }
        return Item.redstone.itemID;
    }
}