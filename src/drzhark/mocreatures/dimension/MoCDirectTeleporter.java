package drzhark.mocreatures.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.src.*;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.PortalPosition;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import drzhark.mocreatures.MoCreatures;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.PortalPosition;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class MoCDirectTeleporter extends Teleporter 
{
    private boolean portalDone;
    
    public MoCDirectTeleporter(WorldServer par1WorldServer) 
    {
        super(par1WorldServer);
    }

    @Override
    public void placeInPortal(Entity par1Entity, double par2X, double par4Y, double par6Z, float par8)
    {
        int var9 = MathHelper.floor_double(par1Entity.posX);
        int var10 = MathHelper.floor_double(par1Entity.posY) - 1;
        int var11 = MathHelper.floor_double(par1Entity.posZ);
        par1Entity.setLocationAndAngles((double) var9, (double) var10, (double) var11, par1Entity.rotationYaw, 0.0F);
        par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
    }
    
    public void createPortal(World par1World, Random par2Random)
    {
        MoCWorldGenPortal myPortal = new MoCWorldGenPortal(Block.blockNetherQuartz.blockID, 2, Block.stairsNetherQuartz.blockID, 0, Block.blockNetherQuartz.blockID, 1, Block.blockNetherQuartz.blockID, 0);
        for (int i = 0; i< 14; i++)
        {
            if (!portalDone)
            {
                int randPosY = 58 + i;//par2Random.nextInt(8);
                portalDone = myPortal.generate(par1World, par2Random, 0, randPosY, 0);
            }
        }
    }
}