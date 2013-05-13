package drzhark.mocreatures.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;

public class BiomeGenWyvernLair extends BiomeGenBase
{

    public BiomeGenWyvernLair(int par1)
    {
        super(par1);
        spawnableCreatureList.clear();
        topBlock = (byte)MoCreatures.mocGrass.blockID;
        fillerBlock = (byte)MoCreatures.mocDirt.blockID;
        this.minHeight = 0.3F;
        this.maxHeight = 1.5F;
        this.biomeName = "WyvernBiome";
        this.theBiomeDecorator.generateLakes = true;
        this.theBiomeDecorator.grassPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = -999;
        this.theBiomeDecorator.mushroomsPerChunk = 20;
        this.theBiomeDecorator.treesPerChunk = 4;
        this.spawnableMonsterList.clear();
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
    {
        return (WorldGenerator) (par1Random.nextInt(10) == 0 ? new MoCWorldGenBigTree(false, MoCreatures.mocLog.blockID, 0, MoCreatures.mocLeaf.blockID, 0, 2, 30, 10) : new WorldGenShrub(3, 0) );
        
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
        return new WorldGenWyvernGrass(MoCreatures.mocTallGrass.blockID, 0);
    }

    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        super.decorate(par1World, par2Random, par3, par4);

        WorldGenVines var5 = new WorldGenVines();

        for (int var6 = 0; var6 < 50; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(16) + 8;
            byte var8 = 64;
            int var9 = par4 + par2Random.nextInt(16) + 8;
            var5.generate(par1World, par2Random, var7, var8, var9);
        }
    }
}