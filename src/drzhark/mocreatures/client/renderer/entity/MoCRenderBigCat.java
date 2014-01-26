package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelBigCat1;
import drzhark.mocreatures.client.model.MoCModelBigCat2;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;

@SideOnly(Side.CLIENT)
public class MoCRenderBigCat extends RenderLiving {

    public MoCModelBigCat2 bigcat1;

    public MoCRenderBigCat(MoCModelBigCat2 modelbigcat2, MoCModelBigCat1 modelbigcat1, float f)
    {
        super(modelbigcat2, f);
        setRenderPassModel(modelbigcat1);
        bigcat1 = modelbigcat2;
    }

    protected int shouldRenderPass(MoCEntityBigCat entitybigcat, int i)
    {
        if (entitybigcat.getType() == 2 && entitybigcat.getIsAdult())
        {
            bindTexture(MoCreatures.proxy.getTexture("lionb.png"));
        }
        else
        {
            bindTexture(MoCreatures.proxy.getTexture("lionc.png"));
        }
        return 1;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f)
    {
        return shouldRenderPass((MoCEntityBigCat) entityliving, i);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityBigCat)par1Entity).getTexture();
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {

        MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entityliving;

        super.doRender(entitybigcat, d, d1, d2, f, f1);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitybigcat.getName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        boolean flag2 = MoCreatures.proxy.getDisplayPetIcons();
        if (entitybigcat.renderName())
        {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f5 = entitybigcat.getDistanceToEntity(renderManager.livingPlayer);
            if (f5 < 16F)
            {
                String s = "";
                s = (new StringBuilder()).append(s).append(entitybigcat.getName()).toString();
                float f7 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) d + 0.0F, (float) d1 + f7, (float) d2);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator1 = Tessellator.instance;
                byte byte0 = -60;
                if (flag1)
                {
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    if (!flag)
                    {
                        byte0 += 8;
                    }
                    tessellator1.startDrawingQuads();
                    // may break SSP, need to test
                    float f8;
                    f8 = entitybigcat.getHealth();

                    /*if(MoCreatures.mc.isMultiplayerWorld())
                    {
                        f8 = entitybigcat.getHealth();
                    }
                    else
                    {
                        f8 = entitybigcat.getHealth();
                    }
                    */
                    float f9 = entitybigcat.getMaxHealth();
                    float f10 = f8 / f9;
                    float f11 = 40F * f10;
                    tessellator1.setColorRGBA_F(0.7F, 0.0F, 0.0F, 1.0F);
                    tessellator1.addVertex(-20F + f11, -10 + byte0, 0.0D);
                    tessellator1.addVertex(-20F + f11, -6 + byte0, 0.0D);
                    tessellator1.addVertex(20D, -6 + byte0, 0.0D);
                    tessellator1.addVertex(20D, -10 + byte0, 0.0D);
                    tessellator1.setColorRGBA_F(0.0F, 0.7F, 0.0F, 1.0F);
                    tessellator1.addVertex(-20D, -10 + byte0, 0.0D);
                    tessellator1.addVertex(-20D, -6 + byte0, 0.0D);
                    tessellator1.addVertex(f11 - 20F, -6 + byte0, 0.0D);
                    tessellator1.addVertex(f11 - 20F, -10 + byte0, 0.0D);
                    tessellator1.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                }
                if (flag)
                {
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
                    GL11.glEnable(3042 /* GL_BLEND */);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator1.startDrawingQuads();
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator1.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator1.addVertex(-i - 1, -1 + byte0, 0.0D);
                    tessellator1.addVertex(-i - 1, 8 + byte0, 0.0D);
                    tessellator1.addVertex(i + 1, 8 + byte0, 0.0D);
                    tessellator1.addVertex(i + 1, -1 + byte0, 0.0D);
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
        if (entitybigcat.roper != null)
        {
            Tessellator tessellator = Tessellator.instance;
            if (entitybigcat.getIsAdult())
            {
                entitybigcat.setEdad(100);
            }
            d1 -= 0.4D / entitybigcat.getEdad() * 0.01F;
            float f4 = ((entitybigcat.roper.prevRotationYaw + ((entitybigcat.roper.rotationYaw - entitybigcat.roper.prevRotationYaw) * f1 * 0.5F)) * 3.141593F) / 180F;
            float f6 = ((entitybigcat.roper.prevRotationPitch + ((entitybigcat.roper.rotationPitch - entitybigcat.roper.prevRotationPitch) * f1 * 0.5F)) * 3.141593F) / 180F;
            double d3 = MathHelper.sin(f4);
            double d4 = MathHelper.cos(f4);
            double d5 = MathHelper.sin(f6);
            double d6 = MathHelper.cos(f6);
            double d7 = (entitybigcat.roper.prevPosX + ((entitybigcat.roper.posX - entitybigcat.roper.prevPosX) * f1)) - (d4 * 0.69999999999999996D) - (d3 * 0.5D * d6);
            double d8 = (entitybigcat.roper.prevPosY + ((entitybigcat.roper.posY - entitybigcat.roper.prevPosY) * f1)) - (d5 * 0.5D);
            double d9 = ((entitybigcat.roper.prevPosZ + ((entitybigcat.roper.posZ - entitybigcat.roper.prevPosZ) * f1)) - (d3 * 0.69999999999999996D)) + (d4 * 0.5D * d6);
            double d10 = entitybigcat.prevPosX + ((entitybigcat.posX - entitybigcat.prevPosX) * f1);
            double d11 = entitybigcat.prevPosY + ((entitybigcat.posY - entitybigcat.prevPosY) * f1) + 0.25D;
            double d12 = entitybigcat.prevPosZ + ((entitybigcat.posZ - entitybigcat.prevPosZ) * f1);
            double d13 = (float) (d7 - d10);
            double d14 = (float) (d8 - d11);
            double d15 = (float) (d9 - d12);
            GL11.glDisable(3553 /* GL_TEXTURE_2D */);
            GL11.glDisable(2896 /* GL_LIGHTING */);
            for (double d16 = 0.0D; d16 < 0.029999999999999999D; d16 += 0.01D)
            {
                tessellator.startDrawing(3);
                tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
                int j = 16;
                for (int k = 0; k <= j; k++)
                {
                    float f12 = (float) k / (float) j;
                    tessellator.addVertex(d + (d13 * f12) + d16, d1 + (d14 * ((f12 * f12) + f12) * 0.5D) + ((((float) j - (float) k) / (j * 0.75F)) + 0.125F), d2 + (d15 * f12));
                }

                tessellator.draw();
            }

            GL11.glEnable(2896 /* GL_LIGHTING */);
            GL11.glEnable(3553 /* GL_TEXTURE_2D */);
        }
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entityliving;
        stretch(entitybigcat);
        return entityliving.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entityliving;
        bigcat1.sitting = entitybigcat.getIsSitting();
        bigcat1.tamed = entitybigcat.getIsTamed();
    }

    protected void stretch(MoCEntityBigCat entitybigcat)
    {
        float f = entitybigcat.getEdad() * 0.01F;
        if (entitybigcat.getIsAdult())
        {
            f = 1.0F;
        }
        GL11.glScalef(f * entitybigcat.getWidthF(), f * entitybigcat.getHeightF(), f * entitybigcat.getLengthF());
    }
}
