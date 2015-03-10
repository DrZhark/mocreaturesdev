package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelGoat;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderGoat extends RenderLiving {

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityGoat) par1Entity).getTexture();
    }

    public MoCRenderGoat(ModelBase modelbase, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelbase, f);
        this.tempGoat = (MoCModelGoat) modelbase;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        GL11.glTranslatef(0.0F, this.depth, 0.0F);
        stretch((MoCEntityGoat) entityliving);

    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
        MoCEntityGoat entitygoat = (MoCEntityGoat) entityliving;
        this.tempGoat.typeInt = entitygoat.getType();
        this.tempGoat.edad = entitygoat.getEdad() * 0.01F;
        this.tempGoat.bleat = entitygoat.getBleating();
        this.tempGoat.attacking = entitygoat.getAttacking();
        this.tempGoat.legMov = entitygoat.legMovement();
        this.tempGoat.earMov = entitygoat.earMovement();
        this.tempGoat.tailMov = entitygoat.tailMovement();
        this.tempGoat.eatMov = entitygoat.mouthMovement();

        super.doRender(entitygoat, d, d1, d2, f, f1);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitygoat.getName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        //boolean flag2 = MoCreatures.proxy.getdisplayPetIcons();
        if (entitygoat.renderName()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entitygoat.getDistanceToEntity(this.renderManager.livingPlayer);
            if (f4 < 16F) {
                String s = "";
                s = (new StringBuilder()).append(s).append(entitygoat.getName()).toString();
                float f5 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) d + 0.0F, (float) d1 + f5, (float) d2);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator = Tessellator.getInstance();
                byte byte0 = (byte) (-15 + (-40 * entitygoat.getEdad() * 0.01F));
                if (flag1) {
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    if (!flag) {
                        byte0 += 8;
                    }
                    tessellator.getWorldRenderer().startDrawingQuads();
                    // might break SSP
                    float f6 = entitygoat.getHealth();
                    // maxhealth is always 30 for dolphins so we do not need to use a datawatcher
                    float f7 = entitygoat.getMaxHealth();
                    float f8 = f6 / f7;
                    float f9 = 40F * f8;
                    tessellator.getWorldRenderer().setColorRGBA_F(0.7F, 0.0F, 0.0F, 1.0F);
                    tessellator.getWorldRenderer().addVertex(-20F + f9, -10 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(-20F + f9, -6 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(20D, -6 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(20D, -10 + byte0, 0.0D);
                    tessellator.getWorldRenderer().setColorRGBA_F(0.0F, 0.7F, 0.0F, 1.0F);
                    tessellator.getWorldRenderer().addVertex(-20D, -10 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(-20D, -6 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(f9 - 20F, -6 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(f9 - 20F, -10 + byte0, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                }
                if (flag) {
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
                    GL11.glEnable(3042 /* GL_BLEND */);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator.getWorldRenderer().startDrawingQuads();
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator.getWorldRenderer().setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.getWorldRenderer().addVertex(-i - 1, -1 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(-i - 1, 8 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(i + 1, 8 + byte0, 0.0D);
                    tessellator.getWorldRenderer().addVertex(i + 1, -1 + byte0, 0.0D);
                    tessellator.draw();
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

    protected void stretch(MoCEntityGoat entitygoat) {
        GL11.glScalef(entitygoat.getEdad() * 0.01F, entitygoat.getEdad() * 0.01F, entitygoat.getEdad() * 0.01F);
    }

    private final MoCModelGoat tempGoat;
    float depth = 0F;
}
