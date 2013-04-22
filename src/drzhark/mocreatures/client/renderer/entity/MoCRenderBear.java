package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.entity.passive.MoCEntityBear;

@SideOnly(Side.CLIENT)
public class MoCRenderBear extends MoCRenderAnimal {

    public MoCRenderBear(MoCModelBear modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityBear entitybear = (MoCEntityBear) entityliving;
        stretch(entitybear);
        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityBear entitybear)
    {
        float sizeFactor = entitybear.getEdad() * 0.01F;
        if (entitybear.getIsAdult())
        {
            sizeFactor = 1.0F;
        }
        sizeFactor *= entitybear.getBearSize();
        GL11.glScalef(sizeFactor, sizeFactor, sizeFactor);
    }

}
