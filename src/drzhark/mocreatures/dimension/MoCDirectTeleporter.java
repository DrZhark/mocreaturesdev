package drzhark.mocreatures.dimension;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
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
        MoCWorldGenPortal myPortal = new MoCWorldGenPortal(Blocks.quartz_block, 2, Blocks.quartz_stairs, 0, Blocks.quartz_block, 1, Blocks.quartz_block, 0);
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