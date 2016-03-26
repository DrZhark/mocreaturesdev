package drzhark.mocreatures.dimension;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldChunkManagerWyvernLair extends WorldChunkManager {

    /** The biome generator object. */
    private BiomeGenBase biomeGenerator = MoCreatures.WyvernLairBiome;;
    private float hellTemperature;

    /** The rainfall in the world */
    private float rainfall;

    public WorldChunkManagerWyvernLair(BiomeGenBase par1BiomeGenBase, float par2, float par3) {
        this.biomeGenerator = par1BiomeGenBase;
        this.hellTemperature = par2;
        this.rainfall = par3;
    }

    /**
     * Returns the BiomeGenBase related to the x, z position on the world.
     */
    public BiomeGenBase getBiomeGenAt(int par1, int par2) {
        return this.biomeGenerator;
    }

    /**
     * Returns an array of biomes for the location input.
     */
    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int xStart, int zStart, int xSize, int zSize) {
        return getBiomeGenAt(biomes, xStart, zStart, xSize, zSize, true);
    }

    /**
     * Returns a list of temperatures to use for the specified blocks. Args:
     * listToReuse, x, y, width, length
     */
    public float[] getTemperatures(float[] par1ArrayOfFloat, int xStart, int zStart, int xSize, int zSize) {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < xSize * zSize) {
            par1ArrayOfFloat = new float[xSize * zSize];
        }

        Arrays.fill(par1ArrayOfFloat, 0, xSize * zSize, this.hellTemperature);
        return par1ArrayOfFloat;
    }

    /**
     * Returns a list of rainfall values for the specified blocks. Args:
     * listToReuse, x, z, width, length.
     */
    @Override
    public float[] getRainfall(float[] par1ArrayOfFloat, int xStart, int zStart, int xSize, int zSize) {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < xSize * zSize) {
            par1ArrayOfFloat = new float[xSize * zSize];
        }

        Arrays.fill(par1ArrayOfFloat, 0, xSize * zSize, this.rainfall);
        return par1ArrayOfFloat;
    }

    /**
     * Returns biomes to use for the blocks and loads the other data like
     * temperature and humidity onto the WorldChunkManager Args: oldBiomeList,
     * x, z, width, depth
     */
    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] biomes, int xStart, int zStart, int xSize, int zSize) {
        return getBiomeGenAt(biomes, xStart, zStart, xSize, zSize, true);
    }

    /**
     * Return a list of biomes for the specified blocks. Args: listToReuse, x,
     * y, width, length, cacheFlag (if false, don't check biomeCache to avoid
     * infinite loop in BiomeCacheBlock)
     */
    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int xStart, int zStart, int xSize, int zSize, boolean par6) {
        if (biomes == null || biomes.length < xSize * zSize) {
            biomes = new BiomeGenBase[xSize * zSize];
        }

        Arrays.fill(biomes, 0, xSize * zSize, this.biomeGenerator);
        return biomes;
    }

    /**
     * Finds a valid position within a range, that is in one of the listed
     * biomes. Searches {par1,par2} +-par3 blocks. Strongly favors positive y
     * positions.
     */
    @Override
    public BlockPos findBiomePosition(int x, int z, int range, List<BiomeGenBase> biomes, Random random) {
        return biomes.contains(this.biomeGenerator) ? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range
                + random.nextInt(range * 2 + 1)) : null;
    }

    /**
     * checks given Chunk's Biomes against List of allowed ones
     */
    @Override
    public boolean areBiomesViable(int par1, int par2, int par3, List<BiomeGenBase> biomes) {
        return biomes.contains(this.biomeGenerator);
    }
}
