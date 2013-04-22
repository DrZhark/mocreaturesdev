package drzhark.mocreatures.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCItemTurtleSoup extends MoCItemFood {
    public MoCItemTurtleSoup(int i, int j)
    {
        super(i, j);
        maxStackSize = 1;

    }

    public MoCItemTurtleSoup(int i, int j, float f, boolean flag)
    {
        super(i, j, f, flag);
        maxStackSize = 1;

    }

    @Override
    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        super.onEaten(itemstack, world, entityplayer);
        return new ItemStack(Item.bowlEmpty);
    }
}
