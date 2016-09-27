package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class MoCBlockLog extends MoCBlock {

    public MoCBlockLog(String name) {
        super(name, Material.WOOD);
        this.setSoundType(SoundType.WOOD);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock(MoCreatures.mocLog);
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs tab, List<ItemStack> subItems) {
        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }
}
