package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
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
import drzhark.mocreatures.client.model.MoCModelGoat;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;

@SideOnly(Side.CLIENT)
public class MoCRenderGoat extends RenderLiving {

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityGoat)par1Entity).getTexture();
    }

    public MoCRenderGoat(ModelBase modelbase, float f)
    {
        super(modelbase, f);
        tempGoat = (MoCModelGoat) modelbase;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        GL11.glTranslatef(0.0F, depth, 0.0F);
        stretch((MoCEntityGoat) entityliving);

    }

    /*@Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityGoat entitygoat = (MoCEntityGoat) entityliving;
        tempGoat.typeInt = entitygoat.type;
        tempGoat.edad = entitygoat.getEdad();
        tempGoat.bleat = entitygoat.getBleating();
        tempGoat.attacking = entitygoat.getAttacking();
        tempGoat.legMov    = entitygoat.legMovement();
        
        if(!entitygoat.isSwimming())
        {
            depth = 0.09F;
        }else
        if (tempGoat.typeInt == 1)
        {
            depth = 0.15F;
        }else
        {
            depth = 0.25F;
        }
        
        tempGoat.attacking = entitygoat.attacking;


        super.doRender(entityliving, d, d1, d2, f, f1);
        
        
    }*/

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityGoat entitygoat = (MoCEntityGoat) entityliving;
        tempGoat.typeInt = entitygoat.getType();
        tempGoat.edad = entitygoat.getEdad() * 0.01F;
        tempGoat.bleat = entitygoat.getBleating();
        tempGoat.attacking = entitygoat.getAttacking();
        tempGoat.legMov = entitygoat.legMovement();
        tempGoat.earMov = entitygoat.earMovement();
        tempGoat.tailMov = entitygoat.tailMovement();
        tempGoat.eatMov = entitygoat.mouthMovement();

        super.doRender(entitygoat, d, d1, d2, f, f1);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitygoat.getName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        //boolean flag2 = MoCreatures.proxy.getdisplayPetIcons();
        if (entitygoat.renderName())
        {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entitygoat.getDistanceToEntity(renderManager.livingPlayer);
            if (f4 < 16F)
            {
                String s = "";
                s = (new StringBuilder()).append(s).append(entitygoat.getName()).toString();
                float f5 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) d + 0.0F, (float) d1 + f5, (float) d2);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator = Tessellator.instance;
                byte byte0 = (byte) (-15 + (-40 * entitygoat.getEdad() * 0.01F));
                if (flag1)
                {
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    if (!flag)
                    {
                        byte0 += 8;
                    }
                    tessellator.startDrawingQuads();
                    // might break SSP
                    float f6 = entitygoat.getHealth();
                    // maxhealth is always 30 for dolphins so we do not need to use a datawatcher
                    float f7 = entitygoat.getMaxHealth();
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
        if (entitygoat.roper != null)
        {
            d1 -= 0.5D / entitygoat.getEdad() * 0.01F;
            Tessellator tessellator = Tessellator.instance;
            float f4 = ((entitygoat.roper.prevRotationYaw + ((entitygoat.roper.rotationYaw - entitygoat.roper.prevRotationYaw) * f1 * 0.5F)) * 3.141593F) / 180F;
            float f6 = ((entitygoat.roper.prevRotationPitch + ((entitygoat.roper.rotationPitch - entitygoat.roper.prevRotationPitch) * f1 * 0.5F)) * 3.141593F) / 180F;
            double d3 = MathHelper.sin(f4);
            double d4 = MathHelper.cos(f4);
            double d5 = MathHelper.sin(f6);
            double d6 = MathHelper.cos(f6);
            double d7 = (entitygoat.roper.prevPosX + ((entitygoat.roper.posX - entitygoat.roper.prevPosX) * f1)) - (d4 * 0.69999999999999996D) - (d3 * 0.5D * d6);
            double d8 = (entitygoat.roper.prevPosY + ((entitygoat.roper.posY - entitygoat.roper.prevPosY) * f1)) - (d5 * 0.5D);
            double d9 = ((entitygoat.roper.prevPosZ + ((entitygoat.roper.posZ - entitygoat.roper.prevPosZ) * f1)) - (d3 * 0.69999999999999996D)) + (d4 * 0.5D * d6);
            double d10 = entitygoat.prevPosX + ((entitygoat.posX - entitygoat.prevPosX) * f1);
            double d11 = entitygoat.prevPosY + ((entitygoat.posY - entitygoat.prevPosY) * f1) + 0.25D;
            double d12 = entitygoat.prevPosZ + ((entitygoat.posZ - entitygoat.prevPosZ) * f1);
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

    protected void stretch(MoCEntityGoat entitygoat)
    {
        GL11.glScalef(entitygoat.getEdad() * 0.01F, entitygoat.getEdad() * 0.01F, entitygoat.getEdad() * 0.01F);
    }

    private final MoCModelGoat tempGoat;
    float depth = 0F;
}
