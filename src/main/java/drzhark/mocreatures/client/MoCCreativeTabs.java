package drzhark.mocreatures.client;

import drzhark.mocreatures.init.MoCItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCCreativeTabs extends CreativeTabs {

    public MoCCreativeTabs(int par1, String par2Str) {
        super(par1, par2Str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(MoCItems.amuletfairyfull, 1);
    }
}
