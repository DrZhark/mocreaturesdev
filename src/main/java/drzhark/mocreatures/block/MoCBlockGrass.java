package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class MoCBlockGrass extends MoCBlock {

    public MoCBlockGrass(String name) {
        super(name, Material.grass);
        setTickRandomly(true);
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!MoCreatures.isServer()) {
            return;
        }

        BlockPos pos = new BlockPos(par2, par3, par4);
        if (par1World.getLightFromNeighbors(pos.up()) < 4 && par1World.getBlockState(pos.up()).getBlock().getLightOpacity() > 2) {
            par1World.setBlockState(pos, MoCreatures.mocDirt.getDefaultState(), 3);
        } else if (par1World.getLightFromNeighbors(pos.up()) >= 9) {
            for (int i = 0; i < 45; i++) {
                int j = (par2 + par5Random.nextInt(3)) - 1;
                int k = (par3 + par5Random.nextInt(5)) - 3;
                int l = (par4 + par5Random.nextInt(3)) - 1;
                pos = new BlockPos(j, k, l);
                IBlockState blockstate = par1World.getBlockState(pos.up());

                if (par1World.getBlockState(pos).getBlock() == MoCreatures.mocDirt && par1World.getLightFromNeighbors(pos.up()) >= 4
                        && blockstate.getBlock().getLightOpacity() <= 2) {
                    par1World.setBlockState(pos, MoCreatures.mocGrass.getDefaultState(), 3);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
            subItems.add(new ItemStack(item, 1, ix));
        }
    }
}
