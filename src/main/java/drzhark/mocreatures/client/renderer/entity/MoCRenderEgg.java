package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderEgg extends RenderLiving<MoCEntityEgg> {

    public MoCRenderEgg(ModelBase modelbase, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityEgg entityegg, float f) {
        stretch(entityegg);
        super.preRenderCallback(entityegg, f);

    }

    protected void stretch(MoCEntityEgg entityegg) {
        float f = entityegg.getSize() * 0.01F;
        GL11.glScalef(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityEgg entityegg) {
        return entityegg.getTexture();
    }
}
