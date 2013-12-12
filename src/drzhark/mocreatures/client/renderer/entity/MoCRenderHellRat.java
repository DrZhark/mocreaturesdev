package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.monster.MoCEntityHellRat;

@SideOnly(Side.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat {

    public MoCRenderHellRat(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void stretch(EntityLivingBase entityliving)
    {
        float f = 1.3F;
        GL11.glScalef(f, f, f);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityHellRat)par1Entity).getTexture();
    }
}
