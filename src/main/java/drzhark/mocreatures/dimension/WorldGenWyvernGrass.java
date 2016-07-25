package drzhark.mocreatures.dimension;

import drzhark.mocreatures.block.MoCBlockTallGrass;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenWyvernGrass extends WorldGenerator {

    /** Stores ID for WorldGenTallGrass */
    private IBlockState iBlockStateGrass;
    private MoCBlockTallGrass grass;

    public WorldGenWyvernGrass(IBlockState iblockstategrass) {
        this.iBlockStateGrass = iblockstategrass;
        this.grass = (MoCBlockTallGrass) this.iBlockStateGrass.getBlock();
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        Block block;

        do {
            block = worldIn.getBlockState(position).getBlock();
            if (!block.isAir(worldIn, position) && !block.isLeaves(worldIn, position)) {
                break;
            }
            position = position.down();
        } while (position.getY() > 0);

        for (int i = 0; i < 128; ++i) {
            BlockPos blockpos1 =
                    position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (worldIn.isAirBlock(blockpos1) && this.grass.canBlockStay(worldIn, blockpos1, this.iBlockStateGrass)) {
                worldIn.setBlockState(blockpos1, this.iBlockStateGrass, 2);
            }
        }

        return true;
    }
}
