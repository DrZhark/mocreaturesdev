package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelBigCat1;
import drzhark.mocreatures.client.model.MoCModelBigCat2;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderBigCat extends RenderLiving<MoCEntityBigCat> {

    public MoCModelBigCat2 bigcat1;

    public MoCRenderBigCat(MoCModelBigCat2 modelbigcat2, MoCModelBigCat1 modelbigcat1, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelbigcat2, f);
        this.addLayer(new LayerMoCBigCat(this));
        this.bigcat1 = modelbigcat2;
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBigCat entitybigcat) {
        return entitybigcat.getTexture();
    }

    @Override
    public void doRender(MoCEntityBigCat entitybigcat, double d, double d1, double d2, float f, float f1) {
        super.doRender(entitybigcat, d, d1, d2, f, f1);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitybigcat.getPetName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();

        if (entitybigcat.renderName()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f5 = entitybigcat.getDistanceToEntity(this.renderManager.livingPlayer);
            if (f5 < 16F) {
                String s = "";
                s = (new StringBuilder()).append(s).append(entitybigcat.getPetName()).toString();
                float f7 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) d + 0.0F, (float) d1 + f7, (float) d2);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator1 = Tessellator.getInstance();
                byte byte0 = -60;
                if (flag1) {
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    if (!flag) {
                        byte0 += 8;
                    }

                    tessellator1.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    // may break SSP, need to test
                    float f8;
                    f8 = entitybigcat.getHealth();

                    /*
                     * if(MoCreatures.mc.isMultiplayerWorld()) { f8 =
                     * entityliving.getHealth(); } else { f8 =
                     * entityliving.getHealth(); }
                     */
                    float f9 = entitybigcat.getMaxHealth();
                    float f10 = f8 / f9;
                    float f11 = 40F * f10;
                    tessellator1.getWorldRenderer().pos(-20F + f11, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getWorldRenderer().pos(-20F + f11, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getWorldRenderer().pos(20D, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getWorldRenderer().pos(20D, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getWorldRenderer().pos(-20D, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getWorldRenderer().pos(-20D, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getWorldRenderer().pos(f11 - 20F, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getWorldRenderer().pos(f11 - 20F, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                }
                if (flag) {
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
                    GL11.glEnable(3042 /* GL_BLEND */);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator1.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator1.getWorldRenderer().pos(-i - 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getWorldRenderer().pos(-i - 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getWorldRenderer().pos(i + 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getWorldRenderer().pos(i + 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
                    GL11.glEnable(2929 /* GL_DEPTH_TEST */);
                    GL11.glDepthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
                    GL11.glDisable(3042 /* GL_BLEND */);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
                GL11.glEnable(2896 /* GL_LIGHTING */);
                GL11.glPopMatrix();
            }
        }

    }

    @Override
    protected float handleRotationFloat(MoCEntityBigCat entitybigcat, float f) {
        stretch(entitybigcat);
        return entitybigcat.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityBigCat entitybigcat, float f) {
        this.bigcat1.sitting = entitybigcat.getIsSitting();
        this.bigcat1.tamed = entitybigcat.getIsTamed();
    }

    protected void stretch(MoCEntityBigCat entitybigcat) {
        float f = entitybigcat.getEdad() * 0.01F;
        if (entitybigcat.getIsAdult()) {
            f = 1.0F;
        }
        GL11.glScalef(f * entitybigcat.getWidthF(), f * entitybigcat.getHeightF(), f * entitybigcat.getLengthF());
    }

    private class LayerMoCBigCat implements LayerRenderer<MoCEntityBigCat> {

        private final MoCRenderBigCat mocRenderer;
        private final MoCModelBigCat1 mocModel = new MoCModelBigCat1();

        public LayerMoCBigCat(MoCRenderBigCat render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityBigCat entitybigcat, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            if (entitybigcat.getType() == 2 && entitybigcat.getIsAdult()) {
                bindTexture(MoCreatures.proxy.getTexture("lionb.png"));
            } else {
                bindTexture(MoCreatures.proxy.getTexture("lionc.png"));
            }
            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entitybigcat, f, f1, f2);
            this.mocModel.render(entitybigcat, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }

    }
}
