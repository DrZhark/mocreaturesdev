package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.model.MoCModelBoar;
import drzhark.mocreatures.entity.passive.MoCEntityBoar;

@SideOnly(Side.CLIENT)
public class MoCRenderBoar extends MoCRenderAnimal {

    public MoCRenderBoar(MoCModelBoar modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityBoar entityboar = (MoCEntityBoar) entityliving;
        stretch(entityboar);
        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityBoar entityboar)
    {
        float sizeFactor = entityboar.getEdad() * 0.01F;
        if (entityboar.getIsAdult())
        {
            sizeFactor = 1.0F;
        }

        GL11.glScalef(sizeFactor, sizeFactor, sizeFactor);
    }

}
