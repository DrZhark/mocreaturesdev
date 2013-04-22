package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.model.MoCModelTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;

@SideOnly(Side.CLIENT)
public class MoCRenderTurtle extends MoCRenderAnimal {
    public MoCRenderTurtle(MoCModelTurtle modelbase, float f)
    {
        super(modelbase, f);
        turtly = modelbase;
    }

    /*public void doRenderLiving2(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityTurtle entityturtle = (MoCEntityTurtle) entityliving;
        super.doRenderLiving(entityturtle, d, d1, d2, f, f1);
    }
    */

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityTurtle entityturtle = (MoCEntityTurtle) entityliving;
        turtly.upsidedown = entityturtle.getIsUpsideDown();
        turtly.swingProgress = entityturtle.swingProgress;
        turtly.isHiding = entityturtle.getIsHiding();

        if (!entityliving.worldObj.isRemote && (entityliving.ridingEntity != null))
        {

            GL11.glTranslatef(0.0F, 1.3F, 0.0F);

        }
        if (entityturtle.getIsHiding())
        {
            adjustHeight(entityturtle, 0.15F * entityturtle.getEdad() * 0.01F);
        }
        else if (!entityturtle.getIsHiding() && !entityturtle.getIsUpsideDown() && !entityturtle.isInsideOfMaterial(Material.water))
        {
            adjustHeight(entityturtle, 0.05F * entityturtle.getEdad() * 0.01F);
        }
        if (entityturtle.getIsUpsideDown())
        {
            rotateAnimal(entityturtle);
        }

        stretch(entityturtle);

    }

    protected void rotateAnimal(MoCEntityTurtle entityturtle)
    {
        //GL11.glRotatef(180F, -1F, 0.0F, 0.0F); //head up 180
        //GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F); //head around 180

        float f = entityturtle.swingProgress * 10F * entityturtle.getFlipDirection();
        float f2 = entityturtle.swingProgress / 30 * entityturtle.getFlipDirection();
        GL11.glRotatef(180F + f, 0.0F, 0.0F, -1.0F);
        GL11.glTranslatef(0.0F - f2, 0.5F * entityturtle.getEdad() * 0.01F, 0.0F);
    }

    protected void adjustHeight(EntityLiving entityliving, float height)
    {
        GL11.glTranslatef(0.0F, height, 0.0F);
    }

    protected void stretch(MoCEntityTurtle entityturtle)
    {
        float f = entityturtle.getEdad() * 0.01F;
        GL11.glScalef(f, f, f);
    }

    /* @Override
     public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
     {
         MoCEntityTurtle entityturtle = (MoCEntityTurtle) entityliving;
         super.doRenderLiving(entityturtle, d, d1, d2, f, f1);
         boolean flag = ((Boolean) mod_mocreatures.displayname.get()).booleanValue() && !entityturtle.getName().isEmpty();
         boolean flag1 = ((Boolean) mod_mocreatures.displayhealth.get()).booleanValue();
         //boolean flag2 = ((Boolean) mod_smpmocreatures.displayemo.get()).booleanValue();
         if(entityturtle.renderName())
         {
             float f2 = 1.6F;
             float f3 = 0.01666667F * f2;
             float f4 = entityturtle.getDistanceToEntity(renderManager.livingPlayer);
             if(f4 < 16F)
             {
                 String s = "";
                 s = (new StringBuilder()).append(s).append(entityturtle.getName()).toString();
                 float f5 = 0.1F;
                 FontRenderer fontrenderer = getFontRendererFromRenderManager();
                 GL11.glPushMatrix();
                 GL11.glTranslatef((float) d + 0.0F, (float) d1 + f5, (float) d2);
                 GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                 GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                 GL11.glScalef(-f3, -f3, f3);
                 GL11.glDisable(2896  GL_LIGHTING );
                 Tessellator tessellator = Tessellator.instance;
                 byte byte0 = (byte) (-15 + (-15 * entityturtle.getEdad()*0.01F));
                 if(flag1)
                 {
                     GL11.glDisable(3553  GL_TEXTURE_2D );
                     if(!flag)
                     {
                         byte0 += 8;
                     }
                     tessellator.startDrawingQuads();
                     // might break SSP
                     float f6 = entityturtle.health;
                     // maxhealth is always 30 for dolphins so we do not need to use a datawatcher
                     float f7 = entityturtle.getMaxHealth();
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
                     GL11.glEnable(3553  GL_TEXTURE_2D );
                 }
                 if(flag)
                 {
                     GL11.glDepthMask(false);
                     GL11.glDisable(2929  GL_DEPTH_TEST );
                     GL11.glEnable(3042  GL_BLEND );
                     GL11.glBlendFunc(770, 771);
                     GL11.glDisable(3553  GL_TEXTURE_2D );
                     tessellator.startDrawingQuads();
                     int i = fontrenderer.getStringWidth(s) / 2;
                     tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                     tessellator.addVertex(-i - 1, -1 + byte0, 0.0D);
                     tessellator.addVertex(-i - 1, 8 + byte0, 0.0D);
                     tessellator.addVertex(i + 1, 8 + byte0, 0.0D);
                     tessellator.addVertex(i + 1, -1 + byte0, 0.0D);
                     tessellator.draw();
                     GL11.glEnable(3553  GL_TEXTURE_2D );
                     fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
                     GL11.glEnable(2929  GL_DEPTH_TEST );
                     GL11.glDepthMask(true);
                     fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
                     GL11.glDisable(3042  GL_BLEND );
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                 }
                 GL11.glEnable(2896  GL_LIGHTING );
                 GL11.glPopMatrix();
             }
         }
     }*/

    public MoCModelTurtle turtly;

}
