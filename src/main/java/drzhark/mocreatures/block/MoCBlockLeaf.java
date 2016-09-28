package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

public class MoCBlockLeaf extends MoCBlock {

    public MoCBlockLeaf(String name) {
        super(name, Material.LEAVES);
        setTickRandomly(true);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setUnlocalizedName(name);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(20) != 0 ? 0 : 1;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack) {
        if (!worldIn.isRemote && stack != null && stack.getItem() == Items.SHEARS) {
            player.addStat(StatList.getBlockStats(this), 1);
            spawnAsEntity(worldIn, pos, new ItemStack(MoCreatures.mocLeaf, 1, 0));
        } else {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> subItems) {
        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
            subItems.add(new ItemStack(item, 1, ix));
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return 0;
    }

}
