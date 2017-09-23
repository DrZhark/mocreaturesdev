package drzhark.mocreatures.client;

import drzhark.mocreatures.init.MoCItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCCreativeTabs extends CreativeTabs {

    public MoCCreativeTabs(int length, String name) {
        super(length, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return new ItemStack(MoCItems.amuletfairyfull, 1);
    }

    /**
     * only shows items which have tabToDisplayOn == this
     */
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> items) {
        for (Item item : Item.REGISTRY)
        {
            if (item == MoCItems.mocegg) {
                continue;
            }
            item.getSubItems(this, items);
        }
        // show eggs last
        MoCItems.mocegg.getSubItems(this, items);
    }
}
