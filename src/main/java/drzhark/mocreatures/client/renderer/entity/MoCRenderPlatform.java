package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.item.MoCEntityPlatform;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderPlatform extends Render<MoCEntityPlatform> {

    public MoCRenderPlatform() {
        super(MoCClientProxy.mc.getRenderManager());
        this.shadowSize = 0.0F;
    }

    @Override
    public void doRender(MoCEntityPlatform var1, double var2, double var4, double var6, float var8, float var9) {
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityPlatform entity) {
        return null;
    }

}
