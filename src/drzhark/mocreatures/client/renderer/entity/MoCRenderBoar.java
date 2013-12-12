package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.model.MoCModelBoar;
import drzhark.mocreatures.entity.passive.MoCEntityBoar;

@SideOnly(Side.CLIENT)
public class MoCRenderBoar extends MoCRenderMoC {

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityBoar)par1Entity).getTexture();
    }

    public MoCRenderBoar(MoCModelBoar modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
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
