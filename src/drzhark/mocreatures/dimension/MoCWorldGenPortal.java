package drzhark.mocreatures.dimension;


import java.util.Random;

import drzhark.mocreatures.MoCreatures;

import net.minecraft.block.Block;
import net.minecraft.src.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class MoCWorldGenPortal extends WorldGenerator
{
	private int pillarBlockID;
	private int stairBlockID;
	private int wallBlockID;
	private int centerBlockID;
	private int pillarMetadata;
	private int stairMetadata;
	private int wallMetadata;
	private int centerMetadata;
	
	
	public MoCWorldGenPortal(int pillar, int pillarMeta, int stair, int stairMeta, int wall, int wallMeta, int center, int centerMeta)
	{		
		pillarBlockID = pillar;
		stairBlockID = stair;
		wallBlockID = wall;
		centerBlockID = center;
		pillarMetadata = pillarMeta;
		stairMetadata = stairMeta;
		wallMetadata = wallMeta;
		centerMetadata = centerMeta;
		
	}
	
	public boolean generatePillar(World world, int x, int y, int z)
	{
		for (int nY = y; nY < y+6; nY++)
		{
			world.setBlock(x, nY, z, pillarBlockID, pillarMetadata, 2);
		}
		return true;
	}
	
	
	public boolean generate(World world, Random random, int x, int y, int z)
    {
		//System.out.println("Generating Portal @ x = "+ x + " y = "+y + " z = "+z + " server = " + MoCreatures.isServer());
		
		if(world.getBlockId(x, y , z) == centerBlockID || world.getBlockId(x, y-1 , z) == centerBlockID || world.getBlockId(x, y+1 , z) == centerBlockID)
		{
			//System.out.println("portal already exists");
			return true;
		}
		
		if(world.getBlockId(x, y , z) == 0 || world.getBlockId(x, y+1, z) !=0)
		{
			return false;
		}
		
		int origX = x;
		int origY = y;
		int origZ = z;
		
		//cleans the area
		/*for (int i = x-4; i < x+4; i++)
		{
			for (int j = z - 4; j < z + 4 ; j++)
			{
				for (int k = y - 1; k < y + 2; k++)
				{
					world.setBlock(i, k, j, 2, 0, 0);
				}
			}
		}*/
		
		stairMetadata = 2;
		for (int nZ = z-3; nZ < z+3; nZ = nZ+5)
		{
			for (int nX = x-2; nX < x+2; nX++)
			{
				
				if (nZ > z)
				{
					stairMetadata = 3;
				}
				world.setBlock(nX, y+1, nZ, stairBlockID, stairMetadata, 2);
				
				//System.out.println("stairMetadata = " + stairMetadata);
				//stairMetadata++;
			}
		}
		
		for (int nX = x-2; nX < x+2; nX++)
		{
			for (int nZ = z-2; nZ < z+2; nZ++)
			{
				world.setBlock(nX, y+1, nZ, wallBlockID, wallMetadata, 2);
			}
			
		}

		for (int nX = x-1; nX < x+1; nX++)
		{
			for (int nZ = z-1; nZ < z+1; nZ++)
			{
				world.setBlock(nX, y+1, nZ, centerBlockID, centerMetadata, 2);
			}
			
		}
		
		
		
		
		
		for (int j = x-3; j <x+3; j = j+5)
		{
			for (int nZ = z-3; nZ < z+3; nZ++)
			{
				world.setBlock(j, y+6, nZ, wallBlockID, wallMetadata, 2);
			}
		}
		
		
		
		generatePillar(world, x-3, y, z-3);
		generatePillar(world, x-3, y, z+2);
		generatePillar(world, x+2, y, z-3);
		generatePillar(world, x+2, y, z+2);
		
		//System.out.println("portal generated");
		return true;
 
    }
	
	public boolean generate2(World world, Random random, int x, int y, int z)
    {
		//Lazy Mode Engaged!
		if(world.getBlockId(x, y-1 , z) != 0)
		{
			for (int i = 0; i < 9; i++)
			{
				if(world.getBlockId(x+4-i, y-1, z) == 0)
				{
					return false;
				}
			}
			
			//System.out.println("Generating Portal @ x = "+ x + " y = "+y + " z = "+z);
		/*world.setBlockWithNotify(x, y, z, cloudBlockID);
		world.setBlockWithNotify(x, y + 1, z, brickBlockID);
		world.setBlockWithNotify(x, y + 2, z, Block.chest.blockID);
		world.setBlockWithNotify(x, y + 3, z, brickBlockID);
		world.setBlockWithNotify(x, y + 4, z, Block.mobSpawner.blockID);
		world.setBlockWithNotify(x + 1, y + 3, z, grassBlockID);
		world.setBlockWithNotify(x + 2, y + 3, z, grassBlockID);
		world.setBlockWithNotify(x - 1, y + 3, z, grassBlockID);
		world.setBlockWithNotify(x - 2, y + 3, z, grassBlockID);
		world.setBlockWithNotify(x, y + 3, z + 1, grassBlockID);
		world.setBlockWithNotify(x, y + 3, z + 2, grassBlockID);
		world.setBlockWithNotify(x, y + 3, z - 1, grassBlockID);
		world.setBlockWithNotify(x, y + 3, z - 2, grassBlockID);
		world.setBlockWithNotify(x + 1, y + 3, z + 1, grassBlockID);
		world.setBlockWithNotify(x + 1, y + 3, z + 2, grassBlockID);
		world.setBlockWithNotify(x + 2, y + 3, z + 1, grassBlockID);
		world.setBlockWithNotify(x + 2, y + 3, z + 2, grassBlockID);
		world.setBlockWithNotify(x + 1, y + 3, z - 1, grassBlockID);
		world.setBlockWithNotify(x + 1, y + 3, z - 2, grassBlockID);
		world.setBlockWithNotify(x + 2, y + 3, z - 1, grassBlockID);
		world.setBlockWithNotify(x + 2, y + 3, z - 2, grassBlockID);
		world.setBlockWithNotify(x - 1, y + 3, z + 1, grassBlockID);
		world.setBlockWithNotify(x - 1, y + 3, z + 2, grassBlockID);
		world.setBlockWithNotify(x - 2, y + 3, z + 1, grassBlockID);
		world.setBlockWithNotify(x - 2, y + 3, z + 2, grassBlockID);
		world.setBlockWithNotify(x - 1, y + 3, z - 1, grassBlockID);
		world.setBlockWithNotify(x - 1, y + 3, z - 2, grassBlockID);
		world.setBlockWithNotify(x - 2, y + 3, z - 1, grassBlockID);
		world.setBlockWithNotify(x - 2, y + 3, z - 2, grassBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z + 2, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z + 1, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z - 1, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z - 2, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x + 2, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x + 1, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x , y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x - 1, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x - 2, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z - 2, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z - 1, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z , cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z + 1, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z + 2, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z + 2, cloudBlockID);
		world.setBlockWithNotify(x - 3, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x - 2, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x - 1, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x , y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x + 2, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z + 2, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z + 1, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z - 1, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z - 2, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x + 2, y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x    , y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 1, y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 2, y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z - 2, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z - 1, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z    , brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z + 1, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z + 2, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z + 3 ,brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z + 2, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x - 2, y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x - 1, y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x    , y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x    , y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 2, y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z + 2, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z + 1, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z - 1, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z - 2, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z - 3, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 3, y + 5, z - 3, brickBlockID);
		world.setBlockWithNotify(x + 2, y + 5, z - 3, brickBlockID);
		world.setBlockWithNotify(x + 1, y + 5, z - 3, brickBlockID);
		world.setBlockWithNotify(x , y + 5, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 1, y + 5, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 2, y + 5, z - 3,brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z - 3, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z - 2, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z - 1, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z , brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z + 1, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z + 2,brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z + 3,brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z + 3,brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z + 2, brickBlockID);
		world.setBlockWithNotify(x - 3, y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x - 2, y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x - 1, y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x, y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x , y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 2, y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 1, y + 4, z - 3, brickBlockID);
		world.setBlockWithNotify(x + 1, y + 3, z - 3, cloudBlockID);
		world.setBlockWithNotify(x + 1, y + 3, z + 3, cloudBlockID);
		world.setBlockWithNotify(x + 1, y + 4, z + 3, brickBlockID);
		world.setBlockWithNotify(x + 1, y + 5, z + 3, brickBlockID);
		world.setBlockWithNotify(x    , y + 8, z  + 1, cloudBlockID);
		world.setBlockWithNotify(x    , y + 8, z  + 2, cloudBlockID);
		world.setBlockWithNotify(x    , y + 8, z  + 3, cloudBlockID);
		world.setBlockWithNotify(x    , y + 8, z  - 1, cloudBlockID);
		world.setBlockWithNotify(x    , y + 8, z  - 3, cloudBlockID);
		world.setBlockWithNotify(x    , y + 8, z  - 2, cloudBlockID);
		world.setBlockWithNotify(x    , y + 8, z     , cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 7, z  + 1, cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 7, z  + 2, cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 7, z  + 3, cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 7, z  - 1, cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 7, z  - 3, cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 7, z  - 2, cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 7, z     , cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 7, z  + 1, cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 7, z  + 2, cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 7, z  + 3, cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 7, z  - 1, cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 7, z  - 3, cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 7, z  - 2, cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 7, z     , cloudBlockID);
		world.setBlockWithNotify(x  + 2  , y + 6, z  + 1, cloudBlockID);
		world.setBlockWithNotify(x  + 2  , y + 6, z  + 2, cloudBlockID);
		world.setBlockWithNotify(x  + 2  , y + 6, z  + 3, cloudBlockID);
		world.setBlockWithNotify(x  + 2  , y + 6, z  - 1, cloudBlockID);
		world.setBlockWithNotify(x  + 2  , y + 6, z  - 3, cloudBlockID);
		world.setBlockWithNotify(x  + 2  , y + 6, z  - 2, cloudBlockID);
		world.setBlockWithNotify(x  + 2  , y + 6, z     , cloudBlockID);
		world.setBlockWithNotify(x  - 2  , y + 6, z  + 1, cloudBlockID);
		world.setBlockWithNotify(x  - 2  , y + 6, z  + 2, cloudBlockID);
		world.setBlockWithNotify(x  - 2  , y + 6, z  + 3, cloudBlockID);
		world.setBlockWithNotify(x  - 2  , y + 6, z  - 1, cloudBlockID);
		world.setBlockWithNotify(x  - 2  , y + 6, z  - 3, cloudBlockID);
		world.setBlockWithNotify(x  - 2  , y + 6, z  - 2, cloudBlockID);
		world.setBlockWithNotify(x  - 2  , y + 6, z     , cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 6, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x    , y + 6, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x    , y + 7, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 6, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x  - 1  , y + 6, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x    , y + 6, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x    , y + 7, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x  + 1  , y + 6, z - 3  , cloudBlockID);
		
		//Start Floor
		world.setBlockWithNotify(x - 1  , y + 2, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 2, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 2, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 2, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 2, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 2, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 2, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 2, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 2, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 2, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 2, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 2, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 2, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 2, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 2, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 2, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 2, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 2, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 2, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 2, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 2, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 2, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 2, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 2, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 2, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 2, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 2, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 2, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 2, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 2, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 2, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 2, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 2, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 2, z + 1  , cloudBlockID);		
		world.setBlockWithNotify(x - 1  , y + 2, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 2, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 2, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 2, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 2, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 2, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 2, z + 2  , cloudBlockID);		
		world.setBlockWithNotify(x - 1  , y + 2, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 2, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 2, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 2, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 2, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 2, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 2, z + 3  , cloudBlockID);		
		world.setBlockWithNotify(x - 1  , y + 1, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 1, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 1, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 1, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 1, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 1, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 1, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 1, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 1, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 1, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 1, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 1, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 1, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 1, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 1, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 1, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 1, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 1, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 1, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 1, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 1, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 1, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 1, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 1, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 1, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 1, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 1, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 1, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 1, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 1, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 1, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 1, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 1, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 1, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 1, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 1, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 1, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 1, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 1, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 1, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 1, z + 2  , cloudBlockID);	
		world.setBlockWithNotify(x - 1  , y + 1, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 1, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 1, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 1, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 1, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 1, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 1, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 0, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 0, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 0, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 0, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 0, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 0, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 0, z - 3  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 0, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 0, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 0, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 0, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 0, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 0, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 0, z - 2  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 0, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 0, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 0, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 0, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 0, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 0, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 0, z - 1  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 0, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 0, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 0, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 0, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 0, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 0, z - 0  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 0, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 0, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 0, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 0, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 0, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 0, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 0, z + 1  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 0, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 0, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 0, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 0, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 0, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 0, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 0, z + 2  , cloudBlockID);
		world.setBlockWithNotify(x - 1  , y + 0, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x - 2  , y + 0, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x - 3  , y + 0, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x      , y + 0, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 1  , y + 0, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 2  , y + 0, z + 3  , cloudBlockID);
		world.setBlockWithNotify(x + 3  , y + 0, z + 3  , cloudBlockID);*/
		
		return true;
    }
		return false;
	
}
}