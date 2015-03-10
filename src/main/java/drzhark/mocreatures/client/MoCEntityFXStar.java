package drzhark.mocreatures.client;

import drzhark.mocreatures.MoCProxy;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCEntityFXStar extends EntityFX {

    public MoCEntityFXStar(World worldObj, double posX, double posY, double posZ, float red, float green, float blue) {
        super(worldObj, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.8D;
        this.motionY *= 0.8D;
        this.motionZ *= 0.8D;
        this.motionY = this.rand.nextFloat() * 0.4F + 0.05F;

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;

        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.particleMaxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
        this.particleScale *= 0.6F; //it was 0.8 for the old star //0.4 if I'm not using the shrinking
    }

    /**
     * sets which texture to use (2 = items.png)
     */
    @Override
    public int getFXLayer() {
        return 1;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.particleScale *= 0.995F; //slowly shrinks it

        this.motionY -= 0.03D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9D;
        this.motionY *= 0.2D;
        this.motionZ *= 0.9D;

        if (this.onGround) {
            this.motionX *= 0.7D;
            this.motionZ *= 0.7D;
        }

        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }

    @Override
    public void setParticleTextureIndex(int par1) {
    }

    @Override
    public void renderParticle(WorldRenderer p_180434_1_, Entity p_180434_2, float par2, float par3, float par4, float par5, float par6, float par7) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("mocreatures", MoCProxy.MISC_TEXTURE + "fxstar.png"));
        float sizeFactor = 0.1F * this.particleScale;
        float var13 = (float) (this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
        float var14 = (float) (this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
        float var15 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
        float var16 = 1.2F - ((float) Math.random() * 0.5F);
        p_180434_1_.setColorRGBA_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F);
        p_180434_1_.addVertexWithUV(var13 - par3 * sizeFactor - par6 * sizeFactor, var14 - par4 * sizeFactor, var15 - par5 * sizeFactor - par7
                * sizeFactor, 0D, 1D);
        p_180434_1_.addVertexWithUV(var13 - par3 * sizeFactor + par6 * sizeFactor, var14 + par4 * sizeFactor, var15 - par5 * sizeFactor + par7
                * sizeFactor, 1D, 1D);
        p_180434_1_.addVertexWithUV(var13 + par3 * sizeFactor + par6 * sizeFactor, var14 + par4 * sizeFactor, var15 + par5 * sizeFactor + par7
                * sizeFactor, 1D, 0D);
        p_180434_1_.addVertexWithUV(var13 + par3 * sizeFactor - par6 * sizeFactor, var14 - par4 * sizeFactor, var15 + par5 * sizeFactor - par7
                * sizeFactor, 0D, 0D);
    }
}
