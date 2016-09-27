package drzhark.mocreatures.client;

import drzhark.mocreatures.MoCProxy;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCEntityFXVanish extends Particle {

    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;
    private final boolean implode;

    public MoCEntityFXVanish(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float red, float green,
            float blue, boolean flag) {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.motionX = par8;
        this.motionY = par10 * 5D;
        this.motionZ = par12;
        this.portalPosX = this.posX = par2;
        this.portalPosY = this.posY = par4;// + 0.7D;
        this.portalPosZ = this.posZ = par6;
        this.implode = flag;
        this.particleMaxAge = (int) (Math.random() * 10.0D) + 70;
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

        int speeder = 0;
        float sizeExp = 2.0F;
        if (this.implode) {
            speeder = (this.particleMaxAge / 2);
            sizeExp = 5.0F;
        }

        float var1 = (float) (this.particleAge + speeder) / (float) this.particleMaxAge;
        float var2 = var1;
        var1 = -var1 + var1 * var1 * sizeExp;//5 insteaf of 2 makes an explosion
        var1 = 1.0F - var1;
        this.posX = this.portalPosX + this.motionX * var1;
        this.posY = this.portalPosY + this.motionY * var1 + (1.0F - var2);
        this.posZ = this.portalPosZ + this.motionZ * var1;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setExpired();
        }
    }

    @Override
    public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float par3, float par4, float par5, float par6, float par7) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("mocreatures", MoCProxy.MISC_TEXTURE + "fxvanish.png"));
        float scale = 0.1F * this.particleScale;
        float xPos = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
        float yPos = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
        float zPos = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
        float colorIntensity = 1.0F;
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        worldRendererIn.pos(xPos - par3 * scale - par6 * scale, yPos - par4 * scale, zPos - par5 * scale - par7 * scale).tex(0D, 1D).color(this.particleRed * colorIntensity, this.particleGreen * colorIntensity, this.particleBlue * colorIntensity, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(xPos - par3 * scale + par6 * scale, yPos + par4 * scale, zPos - par5 * scale + par7 * scale).tex(1D, 1D).color(this.particleRed * colorIntensity, this.particleGreen * colorIntensity, this.particleBlue * colorIntensity, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(xPos + par3 * scale + par6 * scale, yPos + par4 * scale, zPos + par5 * scale + par7 * scale).tex(1D, 0D).color(this.particleRed * colorIntensity, this.particleGreen * colorIntensity, this.particleBlue * colorIntensity, 1.0F).lightmap(j, k).endVertex();
        worldRendererIn.pos(xPos + par3 * scale - par6 * scale, yPos - par4 * scale, zPos + par5 * scale - par7 * scale).tex(0D, 0D).color(this.particleRed * colorIntensity, this.particleGreen * colorIntensity, this.particleBlue * colorIntensity, 1.0F).lightmap(j, k).endVertex();
    }
}
