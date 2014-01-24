package drzhark.mocreatures.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTower extends WorldGenerator
{
    private Block MainBlock;
    private Block brickBlock;
    private Block decoBlock;
    private int MainMetadata;
    private int brickMetadata;
    private int decoMetadata;
    
    public WorldGenTower(Block Main, Block Brick, Block Deco)
    {        
        MainBlock = Main;
        brickBlock = Brick;
        decoBlock = Deco;
    }
    
    public WorldGenTower(Block Main, int MainMeta, Block Brick, int BrickMeta, Block Deco, int DecoMeta)
    {        
        MainBlock = Main;
        brickBlock = Brick;
        decoBlock = Deco;
        MainMetadata = MainMeta;
        brickMetadata = BrickMeta;
        decoMetadata = DecoMeta;
    }
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        int t = 3;
        
        if(!world.isAirBlock(x, y-1 , z))
        {
            for (int i = 0; i < 9; i++)
            {
                if(world.isAirBlock(x+4-i, y-1, z))
                {
                    return false;
                }
            }
            for (int Ny = y-3; Ny < y+21; Ny++)
            {
                    for (int Nz = z-5; Nz < z+6; Nz = Nz + 10)
                    {
                        for (int Nx = x-2; Nx < x+3; Nx++)
                        {
                        world.setBlock(Nx, Ny, Nz, MainBlock, MainMetadata, t);
                        }
                    }
                    world.setBlock(x-3, Ny, z-4, MainBlock, MainMetadata, t);
                    world.setBlock(x+3, Ny, z-4, MainBlock, MainMetadata, t);
                    world.setBlock(x-4, Ny, z-3, MainBlock, MainMetadata, t);
                    world.setBlock(x+4, Ny, z-3, MainBlock, MainMetadata, t);
                    world.setBlock(x-3, Ny, z+4, MainBlock, MainMetadata, t);
                    world.setBlock(x+3, Ny, z+4, MainBlock, MainMetadata, t);
                    world.setBlock(x-4, Ny, z+3, MainBlock, MainMetadata, t);
                    world.setBlock(x+4, Ny, z+3, MainBlock, MainMetadata, t);

                    for (int Nx = x-5; Nx < x+6; Nx = Nx + 10)
                    {
                        for (int Nz = z-2; Nz < z+3; Nz++)
                        {
                        world.setBlock(Nx, Ny, Nz, MainBlock, MainMetadata, t);
                        }
                    }
            }

            for (int Nx = x-3; Nx < x+4; Nx++)
            {
                for (int Nz = z-3; Nz < z+4; Nz++)
                {
                    world.setBlock(Nx, y-1, Nz, Blocks.flowing_lava, 0, t);
                }
            }

            for (int Ny = y; Ny <y+24; Ny = Ny + 8)
            {
                world.setBlock(x-1, Ny, z-4, brickBlock, MainMetadata, t);
                world.setBlock(x-2, Ny, z-4, brickBlock, MainMetadata, t);
                world.setBlock(x-4, Ny+1, z-1, brickBlock, MainMetadata, t);
                world.setBlock(x-4, Ny+1, z-2, brickBlock, MainMetadata, t);
                world.setBlock(x-4, Ny+2, z+1, brickBlock, MainMetadata, t);
                world.setBlock(x-4, Ny+2, z+2, brickBlock, MainMetadata, t);
                world.setBlock(x-2, Ny+3, z+4, brickBlock, MainMetadata, t);
                world.setBlock(x-1, Ny+3, z+4, brickBlock, MainMetadata, t);
                world.setBlock(x+1, Ny+4, z+4, brickBlock, MainMetadata, t);
                world.setBlock(x+2, Ny+4, z+4, brickBlock, MainMetadata, t);
                world.setBlock(x+4, Ny+5, z+2, brickBlock, MainMetadata, t);
                world.setBlock(x+4, Ny+5, z+1, brickBlock, MainMetadata, t);
                world.setBlock(x+4, Ny+6, z-1, brickBlock, MainMetadata, t);
                world.setBlock(x+4, Ny+6, z-2, brickBlock, MainMetadata, t);
                world.setBlock(x+2, Ny+7, z-4, brickBlock, MainMetadata, t);
                world.setBlock(x+1, Ny+7, z-4, brickBlock, MainMetadata, t);
            
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