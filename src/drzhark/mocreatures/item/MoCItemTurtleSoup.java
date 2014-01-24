package drzhark.mocreatures.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCItemTurtleSoup extends MoCItemFood {
    public MoCItemTurtleSoup(String name, int j)
    {
        super(name, j);
        maxStackSize = 1;
    }

    public MoCItemTurtleSoup(String name, int j, float f, boolean flag)
    {
        super(name, j, f, flag);
        maxStackSize = 1;
    }

    @Override
    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        super.onEaten(itemstack, world, entityplayer);
        return new ItemStack(Items.bowl);
    }
}