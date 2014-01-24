package drzhark.mocreatures.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class MoCWorldGenPortal extends WorldGenerator
{
    private Block pillarBlock;
    private Block stairBlock;
    private Block wallBlock;
    private Block centerBlock;
    private int pillarMetadata;
    private int stairMetadata;
    private int wallMetadata;
    private int centerMetadata;

    public MoCWorldGenPortal(Block pillar, int pillarMeta, Block stair, int stairMeta, Block wall, int wallMeta, Block center, int centerMeta)
    {        
        pillarBlock = pillar;
        stairBlock = stair;
        wallBlock = wall;
        centerBlock = center;
        pillarMetadata = pillarMeta;
        stairMetadata = stairMeta;
        wallMetadata = wallMeta;
        centerMetadata = centerMeta;
    }
    
    public boolean generatePillar(World world, int x, int y, int z)
    {
        for (int nY = y; nY < y+6; nY++)
        {
            world.setBlock(x, nY, z, pillarBlock, pillarMetadata, 2);
        }
        return true;
    }
    
    
    public boolean generate(World world, Random random, int x, int y, int z)
    {
        if(world.getBlock(x, y , z) == centerBlock || world.getBlock(x, y-1 , z) == centerBlock || world.getBlock(x, y+1 , z) == centerBlock)
        {
            return true;
        }

        if(world.isAirBlock(x, y , z) || !world.isAirBlock(x, y+1, z))
        {
            return false;
        }

        int origX = x;
        int origY = y;
        int origZ = z;

        stairMetadata = 2;
        for (int nZ = z-3; nZ < z+3; nZ = nZ+5)
        {
            for (int nX = x-2; nX < x+2; nX++)
            {
                if (nZ > z)
                {
                    stairMetadata = 3;
                }
                world.setBlock(nX, y+1, nZ, stairBlock, stairMetadata, 2);
            }
        }

        for (int nX = x-2; nX < x+2; nX++)
        {
            for (int nZ = z-2; nZ < z+2; nZ++)
            {
                world.setBlock(nX, y+1, nZ, wallBlock, wallMetadata, 2);
            }
        }

        for (int nX = x-1; nX < x+1; nX++)
        {
            for (int nZ = z-1; nZ < z+1; nZ++)
            {
                world.setBlock(nX, y+1, nZ, centerBlock, centerMetadata, 2);
            }
        }

        for (int j = x-3; j <x+3; j = j+5)
        {
            for (int nZ = z-3; nZ < z+3; nZ++)
            {
                world.setBlock(j, y+6, nZ, wallBlock, wallMetadata, 2);
            }
        }

        generatePillar(world, x-3, y, z-3);
        generatePillar(world, x-3, y, z+2);
        generatePillar(world, x+2, y, z-3);
        generatePillar(world, x+2, y, z+2);

        return true;
    }
}