package drzhark.mocreatures.dimension;

//perhaps not needed.
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenWyvernGrass extends WorldGenerator {

    /** Stores ID for WorldGenTallGrass */
    private Block tallGrass;
    private int tallGrassMetadata;

    public WorldGenWyvernGrass(Block block, int par2) {
        this.tallGrass = block;
        this.tallGrassMetadata = par2;
    }

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        int var11;

        /*Block block = null;
        do {
            block = par1World.getBlockState(new BlockPos(par3, par4, par5)).getBlock();
            if (block != null && !block.isLeaves(par1World, new BlockPos(par3, par4, par5))) {
                break;
            }
            par4--;
        } while (par4 > 0);

        for (int var7 = 0; var7 < 128; ++var7) {
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
            BlockPos pos = new BlockPos(var8, var9, var10);
            if (par1World.isAirBlock(pos) && this.tallGrass.canBlockStay(par1World, var8, var9, var10)) {
                par1World.setBlockState(pos, this.tallGrass.getDefaultState(), 3);
            }
        }*/

        return true;
    }
}
