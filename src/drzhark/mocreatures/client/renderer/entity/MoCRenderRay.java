package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.model.MoCModelRay;
import drzhark.mocreatures.entity.passive.MoCEntityRay;

@SideOnly(Side.CLIENT)
public class MoCRenderRay extends RenderLiving {
    public MoCRenderRay(ModelBase modelbase, float f)
    {
        super(modelbase, f);
        tempRay = (MoCModelRay) modelbase;
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        GL11.glTranslatef(0.0F, depth, 0.0F);
        stretch((MoCEntityRay) entityliving);

    }

    /*protected void a(ls entityliving, float f)
     {
       a((EntityCamoCreeper)entityliving, f);
       updateCreeperCamoColor((EntityCamoCreeper)entityliving, f);
     }
    
    
    protected void updateCreeperCamoColor(EntityCamoCreeper entitycreeper, float renderPartialTicks)
     {
       setCamoRenderColor(entitycreeper.getBodyCamoColor(), entitycreeper, renderPartialTicks);
     }*/

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityRay entityray = (MoCEntityRay) entityliving;
        tempRay.typeInt = entityray.getType();

        if (!entityray.isSwimming())
        {
            depth = 0.09F;
        }
        else if (tempRay.typeInt == 1)
        {
            depth = 0.15F;
        }
        else
        {
            depth = 0.25F;
        }

        tempRay.attacking = entityray.attacking;
        //boolean flag = entityray.switchany;
        //GL11.glPushMatrix();

        //float transparency = 0.5F;
        //GL11.glEnable(3042 /*GL_BLEND*/ );

        //GL11.glDepthMask(false);
        //GL11.glDisable(3008/*GL_ALPHA_TEST*/);
        //GL11.glDisable(2896/*GL_LIGHTING*/);
        //GL11.glEnable(2977/*GL_NORMALIZE*/);

        //GL11.glDisable(2896/*GL_LIGHTING*/);
        //GL11.glColor4f(entityBrightness * camoColor[0], entityBrightness * camoColor[1], entityBrightness * camoColor[2], camoColor[3]);
        //GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        //GL11.glBlendFunc(770, 1);
        //GL11.glBlendFunc(770, 771);
        //GL11.glNormal3f(0.0F, 1.0F, 0.0F);

        //GL11.glDepthMask(false);
        //GL11.glEnable(3042);
        //GL11.glBlendFunc(770, 771);
        //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //GL11.glDisable(3008);

        super.doRenderLiving(entityliving, d, d1, d2, f, f1);

        //GL11.glDepthMask(true);
        //GL11.glDisable(3042/*GL_BLEND*/);
        //GL11.glBlendFunc(770, 1);

        //GL11.glDepthMask(true);
        //GL11.glEnable(2896);
        //GL11.glDisable(2977/*GL_NORMALIZE*/);

        //GL11.glEnable(3008);

        //GL11.glPopMatrix();

    }

    /*@Override
    protected float handleRotationFloat(EntityLiving entityliving, float f)
    {
        MoCEntityRay entitymantray = (MoCEntityRay) entityliving;
        stretch(entitymantray);
        return entityliving.ticksExisted + f;
    }*/

    protected void stretch(MoCEntityRay entitymantray)
    {
        GL11.glScalef(entitymantray.getEdad() * 0.01F, entitymantray.getEdad() * 0.01F, entitymantray.getEdad() * 0.01F);
    }

    private final MoCModelRay tempRay;
    float depth = 0F;
}
