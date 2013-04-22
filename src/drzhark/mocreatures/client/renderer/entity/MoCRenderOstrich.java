package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;

@SideOnly(Side.CLIENT)
public class MoCRenderOstrich extends MoCRenderAnimal {

    public MoCRenderOstrich(ModelBase modelbase, float f)
    {
        super(modelbase, 0.5F);
        //tempSnake = (MoCModelSnake) modelbase;
    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight)
    {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityOstrich entityostrich = (MoCEntityOstrich) entityliving;
        if (entityostrich.getType() == 1)
        {
            stretch(entityostrich);
        }

        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityOstrich entityostrich)
    {

        float f = entityostrich.getEdad() * 0.01F;
        GL11.glScalef(f, f, f);
    }

}