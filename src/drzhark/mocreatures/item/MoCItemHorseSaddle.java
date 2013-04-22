package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

public class MoCItemHorseSaddle extends MoCItem {

    public MoCItemHorseSaddle(int i)
    {
        super(i);
        maxStackSize = 32;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityLiving entityliving)
    {
        if (entityliving instanceof MoCEntityHorse)
        {
            MoCEntityHorse entityhorse = (MoCEntityHorse) entityliving;
            if (!entityhorse.getIsRideable() && entityhorse.getIsAdult())
            {
                entityhorse.setRideable(true);
                itemstack.stackSize--;
                return true;
            }
        }
        return false;
    }

}
