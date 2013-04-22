package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.monster.MoCEntityRat;

@SideOnly(Side.CLIENT)
public class MoCRenderRat extends RenderLiving {

    public MoCRenderRat(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityRat entityrat = (MoCEntityRat) entityliving;
        super.doRenderLiving(entityrat, d, d1, d2, f, f1);
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
        MoCEntityRat entityrat = (MoCEntityRat) entityliving;
        if (entityrat.climbing())
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
        float f = 0.8F;
        GL11.glScalef(f, f, f);
    }
}
