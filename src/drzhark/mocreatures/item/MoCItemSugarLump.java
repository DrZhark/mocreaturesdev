package drzhark.mocreatures.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCItemSugarLump extends MoCItemFood {

    public MoCItemSugarLump(int i)
    {
        super(i, 3);
        maxStackSize = 32;
    }
}