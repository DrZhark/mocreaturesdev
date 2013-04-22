package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;

@SideOnly(Side.CLIENT)
public class MoCRenderMouse extends MoCRenderAnimal {

    public MoCRenderMouse(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityMouse entitymouse = (MoCEntityMouse) entityliving;
        super.doRenderLiving(entitymouse, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(EntityLiving entityliving, float f)
    {
        stretch(entityliving);
        return entityliving.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityMouse entitymouse = (MoCEntityMouse) entityliving;
        if (entitymouse.upsideDown())
        {
            upsideDown(entityliving);

        }
        if (entitymouse.climbing())
        {
            rotateAnimal(entityliving);
        }
    }

    protected void rotateAnimal(EntityLiving entityliving)
    {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(EntityLiving entityliving)
    {
        float f = 0.6F;
        GL11.glScalef(f, f, f);
    }

    protected void upsideDown(EntityLiving entityliving)
    {
        GL11.glRotatef(-90F, -1F, 0.0F, 0.0F);
        //GL11.glTranslatef(-0.55F, 0F, -0.7F);
        GL11.glTranslatef(-0.55F, 0F, 0F);
    }
}
