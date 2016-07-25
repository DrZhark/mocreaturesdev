package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderTRock extends Render {

    private static final ResourceLocation TEXTURE_TERRAIN = new ResourceLocation("terrain.png");

    public MoCRenderTRock() {
        super(MoCClientProxy.mc.getRenderManager());
        this.shadowSize = 0.5F;
    }

    public void renderMyRock(MoCEntityThrowableRock entitytrock, double par2, double par4, double par6, float par8, float partialTicks) {
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        //GlStateManager.translate(-0.5F, -0.55F, 0.5F);
        GlStateManager.translate(-0.5F, 0F, 0.5F);
        GlStateManager.translate((float) par2, (float) par4, (float) par6);
        GlStateManager.rotate(((100 - entitytrock.acceleration) / 10F) * 36F, 0F, -1F, 0.0F);
        int i = entitytrock.getBrightnessForRender(partialTicks);
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bindTexture(TextureMap.locationBlocksTexture);
        float lightLevel = entitytrock.getBrightness(partialTicks);
        blockrendererdispatcher.renderBlockBrightness(entitytrock.getState(), lightLevel);
        GlStateManager.popMatrix();
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderMyRock((MoCEntityThrowableRock) par1Entity, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getMyTexture(MoCEntityThrowableRock trock) {
        return TextureMap.locationBlocksTexture;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return null;//this.getMyTexture((MoCEntityThrowableRock) par1Entity);
    }
}
