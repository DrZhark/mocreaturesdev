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
	//protected WorldGenDelphiTree Delphi;

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
		//this.spawnableCreatureList.add(new SpawnListEntry(MoCEntityWyvern.class, 15, 4, 5));
		//this.spawnableCreatureList.add(new SpawnListEntry(MoCEntitySnake.class, 6, 2, 4));
		//this.spawnableCreatureList.add(new SpawnListEntry(MoCEntityBunny.class, 4, 1, 5));
		//this.createPortal(par1World, par2Random, par3, par4)
		
	}

	//private boolean towerDone = false;
	//private boolean portalDone = false;
	/**
     * Gets a WorldGen appropriate for this biome.
     */
	@Override
    public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
    {
        //return (WorldGenerator)(par1Random.nextInt(10) == 0 ? this.worldGeneratorBigTree : (par1Random.nextInt(2) == 0 ? new WorldGenShrub(3, 0) : (par1Random.nextInt(3) == 0 ? new WorldGenHugeTrees(false, 10 + par1Random.nextInt(20), 3, 3) : new WorldGenTrees(false, 4 + par1Random.nextInt(7), 3, 3, true))));
        
       // return (WorldGenerator)(par1Random.nextInt(10) == 0 ? this.worldGeneratorBigTree : (par1Random.nextInt(2) == 0 ? new WorldGenShrub(3, 0) : (par1Random.nextInt(3) == 0 ? new WorldGenWyvernTrees(false) : new WorldGenTrees(false, 4 + par1Random.nextInt(7), 3, 3, true))));
        //return (WorldGenerator) (par1Random.nextInt(10) == 0 ? new MoCWorldGenBigTree(false, MoCreatures.mocLog.blockID, 0, MoCreatures.mocLeaf.blockID, 0, 2, 30, 10) : this.worldGeneratorBigTree );
        return (WorldGenerator) (par1Random.nextInt(10) == 0 ? new MoCWorldGenBigTree(false, MoCreatures.mocLog.blockID, 0, MoCreatures.mocLeaf.blockID, 0, 2, 30, 10) : new WorldGenShrub(3, 0) );
        
    }
    
    
    
    /**
     * Gets a WorldGen appropriate for this biome.
     */
    /*public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
    {
    	return (WorldGenerator)  new MoCWorldGenBigTree(false, MoCreatures.wyvernLog.blockID, MoCreatures.wyvernLeaf.blockID, 2, 12, 6);
        //return (WorldGenerator)(par1Random.nextInt(10) == 0 ? this.worldGeneratorBigTree : (par1Random.nextInt(2) == 0 ? new WorldGenShrub(3, 0) : (par1Random.nextInt(3) == 0 ? new WorldGenHugeTrees(false, 10 + par1Random.nextInt(20), 3, 3) : new WorldGenTrees(false, 4 + par1Random.nextInt(7), 3, 3, true))));
    }
*/
    /**
     * Gets a WorldGen appropriate for this biome.
     */
	@Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
    {
    	//System.out.println("grass rnd called");
        //return par1Random.nextInt(4) == 0 ? new WorldGenWyvernGrass(Block.tallGrass.blockID, 2) : new WorldGenWyvernGrass(Block.tallGrass.blockID, 1);
    	return new WorldGenWyvernGrass(MoCreatures.mocTallGrass.blockID, 0);
    }


    
    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
    	//System.out.println("decorate running");
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
