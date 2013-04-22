package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat {

    public MoCRenderHellRat(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void stretch(EntityLiving entityliving)
    {
        float f = 1.3F;
        GL11.glScalef(f, f, f);
    }
}
