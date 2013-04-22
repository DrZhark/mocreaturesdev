package drzhark.mocreatures.dimension;


import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.src.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTower extends WorldGenerator
{
	private int MainBlockID;
	private int brickBlockID;
	private int decoBlockID;
	private int MainMetadata;
	private int brickMetadata;
	private int decoMetadata;
	
	public WorldGenTower(int Main, int Brick, int Deco)
	{		
		MainBlockID = Main;
		brickBlockID = Brick;
		decoBlockID = Deco;
	}
	
	public WorldGenTower(int Main, int MainMeta, int Brick, int BrickMeta, int Deco, int DecoMeta)
	{		
		MainBlockID = Main;
		brickBlockID = Brick;
		decoBlockID = Deco;
		MainMetadata = MainMeta;
		brickMetadata = BrickMeta;
		decoMetadata = DecoMeta;
	}
	
	public boolean generate(World world, Random random, int x, int y, int z)
    {
		int t = 3;
		
		if(world.getBlockId(x, y-1 , z) != 0)
		{
			for (int i = 0; i < 9; i++)
			{
				if(world.getBlockId(x+4-i, y-1, z) == 0)
				{
					return false;
				}
			}
			
			//System.out.println("Generating Tower @ x = "+ x + " y = "+y + " z = "+z);
		for (int Ny = y-3; Ny < y+21; Ny++)
		{
				for (int Nz = z-5; Nz < z+6; Nz = Nz + 10)
				{
					for (int Nx = x-2; Nx < x+3; Nx++)
					{
					world.setBlock(Nx, Ny, Nz, MainBlockID, MainMetadata, t);
					}
					
				}
				world.setBlock(x-3, Ny, z-4, MainBlockID, MainMetadata, t);
				world.setBlock(x+3, Ny, z-4, MainBlockID, MainMetadata, t);
				world.setBlock(x-4, Ny, z-3, MainBlockID, MainMetadata, t);
				world.setBlock(x+4, Ny, z-3, MainBlockID, MainMetadata, t);
				
				world.setBlock(x-3, Ny, z+4, MainBlockID, MainMetadata, t);
				world.setBlock(x+3, Ny, z+4, MainBlockID, MainMetadata, t);
				world.setBlock(x-4, Ny, z+3, MainBlockID, MainMetadata, t);
				world.setBlock(x+4, Ny, z+3, MainBlockID, MainMetadata, t);
				
				
				for (int Nx = x-5; Nx < x+6; Nx = Nx + 10)
				{
					for (int Nz = z-2; Nz < z+3; Nz++)
					{
					world.setBlock(Nx, Ny, Nz, MainBlockID, MainMetadata, t);
					}
					
				}
		}
		
		for (int Nx = x-3; Nx < x+4; Nx++)
		{
			for (int Nz = z-3; Nz < z+4; Nz++)
			{
				world.setBlock(Nx, y-1, Nz, Block.lavaMoving.blockID, 0, t);
			}
		}
		
		for (int Ny = y; Ny <y+24; Ny = Ny + 8)
		{
			world.setBlock(x-1, Ny, z-4, brickBlockID, MainMetadata, t);
			world.setBlock(x-2, Ny, z-4, brickBlockID, MainMetadata, t);
			world.setBlock(x-4, Ny+1, z-1, brickBlockID, MainMetadata, t);
			world.setBlock(x-4, Ny+1, z-2, brickBlockID, MainMetadata, t);
			world.setBlock(x-4, Ny+2, z+1, brickBlockID, MainMetadata, t);
			world.setBlock(x-4, Ny+2, z+2, brickBlockID, MainMetadata, t);
			world.setBlock(x-2, Ny+3, z+4, brickBlockID, MainMetadata, t);
			world.setBlock(x-1, Ny+3, z+4, brickBlockID, MainMetadata, t);
			world.setBlock(x+1, Ny+4, z+4, brickBlockID, MainMetadata, t);
			world.setBlock(x+2, Ny+4, z+4, brickBlockID, MainMetadata, t);
			world.setBlock(x+4, Ny+5, z+2, brickBlockID, MainMetadata, t);
			world.setBlock(x+4, Ny+5, z+1, brickBlockID, MainMetadata, t);
			world.setBlock(x+4, Ny+6, z-1, brickBlockID, MainMetadata, t);
			world.setBlock(x+4, Ny+6, z-2, brickBlockID, MainMetadata, t);
			world.setBlock(x+2, Ny+7, z-4, brickBlockID, MainMetadata, t);
			world.setBlock(x+1, Ny+7, z-4, brickBlockID, MainMetadata, t);
			
			/*world.setBlock(x-1, Ny, z-3, brickBlockID, MainMetadata, t);
			world.setBlock(x-2, Ny, z-3, brickBlockID, MainMetadata, t);
			world.setBlock(x-3, Ny+1, z-1, brickBlockID, MainMetadata, t);
			world.setBlock(x-3, Ny+1, z-2, brickBlockID, MainMetadata, t);
			world.setBlock(x-3, Ny+2, z+1, brickBlockID, MainMetadata, t);
			world.setBlock(x-3, Ny+2, z+2, brickBlockID, MainMetadata, t);
			world.setBlock(x-2, Ny+3, z+3, brickBlockID, MainMetadata, t);
			world.setBlock(x-1, Ny+3, z+3, brickBlockID, MainMetadata, t);
			world.setBlock(x+1, Ny+4, z+3, brickBlockID, MainMetadata, t);
			world.setBlock(x+2, Ny+4, z+3, brickBlockID, MainMetadata, t);
			world.setBlock(x+3, Ny+5, z+2, brickBlockID, MainMetadata, t);
			world.setBlock(x+3, Ny+5, z+1, brickBlockID, MainMetadata, t);
			world.setBlock(x+3, Ny+6, z-1, brickBlockID, MainMetadata, t);
			world.setBlock(x+3, Ny+6, z-2, brickBlockID, MainMetadata, t);
			world.setBlock(x+2, Ny+7, z-3, brickBlockID, MainMetadata, t);
			world.setBlock(x+1, Ny+7, z-3, brickBlockID, MainMetadata, t);*/
		}
		return true;
    }
		return false;
	
}
	}