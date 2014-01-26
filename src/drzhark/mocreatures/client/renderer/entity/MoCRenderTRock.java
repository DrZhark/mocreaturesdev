package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;

@SideOnly(Side.CLIENT)
public class MoCRenderTRock extends Render {

    private final RenderBlocks blockRenderer = new RenderBlocks();
    private static final ResourceLocation TEXTURE_TERRAIN = new ResourceLocation("terrain.png");

    public MoCRenderTRock()
    {
        this.shadowSize = 0.5F;
    }

    public void renderMyRock(MoCEntityThrowableRock entitytrock, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        GL11.glRotatef((float) (((100 - entitytrock.acceleration) / 10F) * 36F), 0F, -1F, 0.0F);
        this.bindEntityTexture(entitytrock);
        this.blockRenderer.renderBlockAsItem(entitytrock.getMyBlock(), entitytrock.getMetadata(), entitytrock.getBrightness(par9));
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMyRock((MoCEntityThrowableRock) par1Entity, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation func_110808_a(MoCEntityThrowableRock trock)
    {
        return TextureMap.locationBlocksTexture;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110808_a((MoCEntityThrowableRock)par1Entity);
    }
}
