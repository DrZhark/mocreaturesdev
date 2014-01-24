package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;

@SideOnly(Side.CLIENT)
public class MoCRenderDolphin extends RenderLiving {

    public MoCRenderDolphin(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityDolphin entitydolphin = (MoCEntityDolphin) entityliving;
        super.doRender(entitydolphin, d, d1, d2, f, f1);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitydolphin.getName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        //boolean flag2 = MoCreatures.proxy.getdisplayPetIcons();
        if (entitydolphin.renderName())
        {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entitydolphin.getDistanceToEntity(renderManager.livingPlayer);
            if (f4 < 16F)
            {
                String s = "";
                s = (new StringBuilder()).append(s).append(entitydolphin.getName()).toString();
                float f5 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) d + 0.0F, (float) d1 + f5, (float) d2);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator = Tessellator.instance;
                byte byte0 = -50;
                if (flag1)
                {
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    if (!flag)
                    {
                        byte0 += 8;
                    }
                    tessellator.startDrawingQuads();
                    // might break SSP
                    float f6 = entitydolphin.getHealth();
                    // maxhealth is always 30 for dolphins so we do not need to use a datawatcher
                    float f7 = entitydolphin.getMaxHealth();
                    float f8 = f6 / f7;
                    float f9 = 40F * f8;
                    tessellator.setColorRGBA_F(0.7F, 0.0F, 0.0F, 1.0F);
                    tessellator.addVertex(-20F + f9, -10 + byte0, 0.0D);
                    tessellator.addVertex(-20F + f9, -6 + byte0, 0.0D);
                    tessellator.addVertex(20D, -6 + byte0, 0.0D);
                    tessellator.addVertex(20D, -10 + byte0, 0.0D);
                    tessellator.setColorRGBA_F(0.0F, 0.7F, 0.0F, 1.0F);
                    tessellator.addVertex(-20D, -10 + byte0, 0.0D);
                    tessellator.addVertex(-20D, -6 + byte0, 0.0D);
                    tessellator.addVertex(f9 - 20F, -6 + byte0, 0.0D);
                    tessellator.addVertex(f9 - 20F, -10 + byte0, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                }
                if (flag)
                {
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
                    GL11.glEnable(3042 /* GL_BLEND */);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator.startDrawingQuads();
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.addVertex(-i - 1, -1 + byte0, 0.0D);
                    tessellator.addVertex(-i - 1, 8 + byte0, 0.0D);
                    tessellator.addVertex(i + 1, 8 + byte0, 0.0D);
                    tessellator.addVertex(i + 1, -1 + byte0, 0.0D);
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

    public void doRender2(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityDolphin entitydolphin = (MoCEntityDolphin) entityliving;
        super.doRender(entitydolphin, d, d1, d2, f, f1);
        if (entitydolphin.renderName())
        {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entityliving.getDistanceToEntity(renderManager.livingPlayer);
            String s = "";
            s = (new StringBuilder()).append(s).append(entitydolphin.getName()).toString();
            if ((f4 < 12F) && (s.length() > 0))
            {
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) d + 0.0F, (float) d1 + 0.3F, (float) d2);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                GL11.glDepthMask(false);
                GL11.glDisable(2929 /* GL_DEPTH_TEST */);
                GL11.glEnable(3042 /* GL_BLEND */);
                GL11.glBlendFunc(770, 771);
                Tessellator tessellator = Tessellator.instance;
                byte byte0 = -50;
                GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                tessellator.startDrawingQuads();
                int i = fontrenderer.getStringWidth(s) / 2;
                tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                tessellator.addVertex(-i - 1, -1 + byte0, 0.0D);
                tessellator.addVertex(-i - 1, 8 + byte0, 0.0D);
                tessellator.addVertex(i + 1, 8 + byte0, 0.0D);
                tessellator.addVertex(i + 1, -1 + byte0, 0.0D);
                if (MoCreatures.proxy.getDisplayPetHealth())
                {
                    float f5 = entitydolphin.getHealth();
                    float f6 = entitydolphin.getMaxHealth();
                    float f7 = f5 / f6;
                    float f8 = 40F * f7;
                    tessellator.setColorRGBA_F(0.7F, 0.0F, 0.0F, 1.0F);
                    tessellator.addVertex(-20F + f8, -10 + byte0, 0.0D);
                    tessellator.addVertex(-20F + f8, -6 + byte0, 0.0D);
                    tessellator.addVertex(20D, -6 + byte0, 0.0D);
                    tessellator.addVertex(20D, -10 + byte0, 0.0D);
                    tessellator.setColorRGBA_F(0.0F, 0.7F, 0.0F, 1.0F);
                    tessellator.addVertex(-20D, -10 + byte0, 0.0D);
                    tessellator.addVertex(-20D, -6 + byte0, 0.0D);
                    tessellator.addVertex(f8 - 20F, -6 + byte0, 0.0D);
                    tessellator.addVertex(f8 - 20F, -10 + byte0, 0.0D);
                }
                tessellator.draw();
                GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
                GL11.glEnable(2929 /* GL_DEPTH_TEST */);
                GL11.glDepthMask(true);
                fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
                GL11.glEnable(2896 /* GL_LIGHTING */);
                GL11.glDisable(3042 /* GL_BLEND */);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        stretch((MoCEntityDolphin) entityliving);
        return entityliving.ticksExisted + f;
    }

    protected void stretch(MoCEntityDolphin entitydolphin)
    {
        GL11.glScalef(entitydolphin.getEdad() * 0.01F, entitydolphin.getEdad() * 0.01F, entitydolphin.getEdad() * 0.01F);
    }


    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityDolphin)par1Entity).getTexture();
    }
}
