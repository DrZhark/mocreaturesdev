package drzhark.mocreatures.dimension;

import drzhark.mocreatures.block.MoCBlockTallGrass;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenWyvernGrass extends WorldGenerator {

    /** Stores ID for WorldGenTallGrass */
    private IBlockState iBlockStateGrass;
    private MoCBlockTallGrass grass;

    public WorldGenWyvernGrass(IBlockState iblockstategrass) {
        this.iBlockStateGrass = iblockstategrass;
        this.grass = (MoCBlockTallGrass) iBlockStateGrass.getBlock();
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        IBlockState blockstate;

        do {
            blockstate = worldIn.getBlockState(position);
            Block block = blockstate.getBlock();
            if (!block.isAir(blockstate, worldIn, position) && !block.isLeaves(blockstate, worldIn, position))
                break;
            position = position.down();
        } while (position.getY() > 0);

        for (int i = 0; i < 128; ++i) {
            BlockPos blockpos1 =
                    position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            if (worldIn.isAirBlock(blockpos1) && grass.canBlockStay(worldIn, blockpos1, iBlockStateGrass)) {
                worldIn.setBlockState(blockpos1, this.iBlockStateGrass, 2);
            }
        }

        return true;
    }
}
