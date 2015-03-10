package drzhark.mocreatures.client;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCCreativeTabs extends CreativeTabs {

    public MoCCreativeTabs(int par1, String par2Str) {
        super(par1, par2Str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return MoCreatures.amuletfairyfull;
    }
}
