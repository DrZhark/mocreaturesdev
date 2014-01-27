package drzhark.mocreatures.item;

import net.minecraft.creativetab.CreativeTabs;

public class MoCItemHayStack extends MoCItem {

    public MoCItemHayStack(int i)
    {
        super(i);
        maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabFood);
    }
}