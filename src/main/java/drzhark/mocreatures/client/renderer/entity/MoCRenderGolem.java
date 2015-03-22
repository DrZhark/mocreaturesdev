package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelGolem;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderGolem extends MoCRenderMoC {

    private final ModelBase MoCModelG = new MoCModelGolem();

    public MoCRenderGolem(ModelBase modelbase, float f) {
        super(modelbase, f);
        this.addLayer(new LayerMoCGolem(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityGolem) par1Entity).getTexture();
    }

    private class LayerMoCGolem implements LayerRenderer {

        private final MoCRenderGolem mocRenderer;
        private final MoCModelGolem mocModel = new MoCModelGolem();

        public LayerMoCGolem(MoCRenderGolem render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityGolem entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {

            ResourceLocation effectTexture = entity.getEffectTexture();
            if (effectTexture != null) {
                GL11.glDepthMask(false);

                float var4 = entity.ticksExisted + f1;
                bindTexture(effectTexture);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                float var5 = var4 * 0.01F;
                float var6 = var4 * 0.01F;
                GL11.glTranslatef(var5, var6, 0.0F);
                // TODO
                //this.setRenderPassModel(this.MoCModelG);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);
                float var7 = 0.5F;
                GL11.glColor4f(var7, var7, var7, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

                this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
                this.mocModel.setLivingAnimations(entity, f, f1, f2);
                this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }

        @Override
        public void doRenderLayer(EntityLivingBase entity, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
            this.doRenderLayer((MoCEntityGolem) entity, f1, f2, f3, f4, f5, f6, f7);
        }
    }
}
