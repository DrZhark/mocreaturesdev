package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;

@SideOnly(Side.CLIENT)
public class MoCRenderMouse extends MoCRenderMoC {

    public MoCRenderMouse(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityMouse entitymouse = (MoCEntityMouse) entityliving;
        super.doRender(entitymouse, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        stretch(entityliving);
        return entityliving.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
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

    protected void rotateAnimal(EntityLivingBase entityliving)
    {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(EntityLivingBase entityliving)
    {
        float f = 0.6F;
        GL11.glScalef(f, f, f);
    }

    protected void upsideDown(EntityLivingBase entityliving)
    {
        GL11.glRotatef(-90F, -1F, 0.0F, 0.0F);
        //GL11.glTranslatef(-0.55F, 0F, -0.7F);
        GL11.glTranslatef(-0.55F, 0F, 0F);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityMouse)par1Entity).getTexture();
    }
}
