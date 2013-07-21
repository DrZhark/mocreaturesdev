package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;

@SideOnly(Side.CLIENT)
public class MoCRenderBear extends MoCRenderMoC {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bearbrowm.png");
    private static final ResourceLocation TEXTURE_BLACK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bearblack.png");
    private static final ResourceLocation TEXTURE_PANDA = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bearpanda.png");
    private static final ResourceLocation TEXTURE_POLAR = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bearpolar.png");

    public MoCRenderBear(MoCModelBear modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
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

    protected ResourceLocation func_110775_a(Entity par1Entity) {
    	return this.getTexture((MoCEntityBear)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityBear bear)
    {
        switch (bear.getType())
        {
	        case 2:
	            return TEXTURE_BLACK;
	        case 3:
	            return TEXTURE_PANDA;
	        case 4:
	            return TEXTURE_POLAR;
	        default:
	            return TEXTURE_DEFAULT;
        }
    }
}
