package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class MoCEntityHellRat extends MoCEntityRat {

    public MoCEntityHellRat(World world)
    {
        super(world);
        texture = MoCreatures.proxy.MODEL_TEXTURE + "hellrat1.png";
        setSize(0.7F, 0.7F);
        health = 20;
        //attackStrength = 2;
        isImmuneToFire = true;
    }

    private int textCounter;

    @Override
    public void selectType()
    {
        setType(4);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "hellrat1.png";
    }

    /**
     * Overridden for the dynamic nightmare texture.
     */
    @Override
    public String getTexture()
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
        return MoCreatures.proxy.MODEL_TEXTURE + "hellrat" + textNumber + ".png";

    }

    @Override
    public int getMaxHealth()
    {
        return 20;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    protected int getDropItemId()
    {
        boolean flag = (rand.nextInt(3) == 0);
        if (flag) { return Block.fire.blockID; }
        return Item.redstone.itemID;
    }
}
