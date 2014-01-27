package drzhark.mocreatures.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;

public class MoCItemHorseSaddle extends MoCItem {

    public MoCItemHorseSaddle(int i)
    {
        super(i);
        maxStackSize = 32;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer entityplayer, EntityLivingBase entityliving)
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