package drzhark.mocreatures.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class MoCDirectTeleporter extends Teleporter {

    private boolean portalDone;

    public MoCDirectTeleporter(WorldServer par1WorldServer) {
        super(par1WorldServer);
    }

    @Override
    public void placeInPortal(Entity par1Entity, float rotationYaw) {
        int var9 = MathHelper.floor(par1Entity.posX);
        int var10 = MathHelper.floor(par1Entity.posY) - 1;
        int var11 = MathHelper.floor(par1Entity.posZ);
        par1Entity.setLocationAndAngles(var9, var10, var11, par1Entity.rotationYaw, 0.0F);
        par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
    }

    public void createPortal(World par1World, Random par2Random) {
        MoCWorldGenPortal myPortal =
                new MoCWorldGenPortal(Blocks.QUARTZ_BLOCK, 2, Blocks.QUARTZ_STAIRS, 0, Blocks.QUARTZ_BLOCK, 1, Blocks.QUARTZ_BLOCK, 0);
        for (int i = 0; i < 14; i++) {
            if (!this.portalDone) {
                int randPosY = 58 + i;//par2Random.nextInt(8);
                this.portalDone = myPortal.generate(par1World, par2Random, new BlockPos(0, randPosY, 0));
            }
        }
    }
}
