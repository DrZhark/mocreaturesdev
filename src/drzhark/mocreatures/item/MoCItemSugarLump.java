package drzhark.mocreatures.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCItemSugarLump extends MoCItemFood {

    //private final int a;

    public MoCItemSugarLump(int i)
    {
        super(i, 3);
        maxStackSize = 32;
        //a = 3;
    }

    /*@Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        entityplayer.heal(a);
        return itemstack;
    }*/
}
