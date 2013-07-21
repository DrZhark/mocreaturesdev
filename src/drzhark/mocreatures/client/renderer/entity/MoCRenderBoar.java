package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelBoar;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import drzhark.mocreatures.entity.passive.MoCEntityBoar;

@SideOnly(Side.CLIENT)
public class MoCRenderBoar extends MoCRenderMoC {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "boara.png");
    private static final ResourceLocation TEXTURE_CHILD = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "boarb.png");

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityBoar)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityBoar boar)
    {
        if (boar.getIsAdult()) { return TEXTURE_DEFAULT; }
        return TEXTURE_CHILD;

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
