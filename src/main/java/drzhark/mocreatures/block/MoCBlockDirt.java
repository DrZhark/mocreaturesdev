package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MoCBlockDirt extends MoCBlock {

    public MoCBlockDirt(String name) {
        super(name, Material.GROUND);
        setTickRandomly(true);
        this.setSoundType(SoundType.GROUND);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs tab, List<ItemStack> subItems) {
        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }
}
