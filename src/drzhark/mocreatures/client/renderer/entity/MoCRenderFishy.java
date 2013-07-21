package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;

@SideOnly(Side.CLIENT)
public class MoCRenderFishy extends RenderLiving {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy1.png");
    private static final ResourceLocation TEXTURE_FISHY2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy2.png");
    private static final ResourceLocation TEXTURE_FISHY3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy3.png");
    private static final ResourceLocation TEXTURE_FISHY4 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy4.png");
    private static final ResourceLocation TEXTURE_FISHY5 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy5.png");
    private static final ResourceLocation TEXTURE_FISHY6 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy6.png");
    private static final ResourceLocation TEXTURE_FISHY7 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy7.png");
    private static final ResourceLocation TEXTURE_FISHY8 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy8.png");
    private static final ResourceLocation TEXTURE_FISHY9 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy9.png");
    private static final ResourceLocation TEXTURE_FISHY10 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fishy10.png");

    public MoCRenderFishy(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityFishy entityfishy = (MoCEntityFishy) entityliving;
        if (entityfishy.getType() == 0)// && !MoCreatures.mc.isMultiplayerWorld())
        {
            entityfishy.selectType();
        }
        super.doRenderLiving(entityfishy, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        GL11.glTranslatef(0.0F, 0.3F, 0.0F);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        MoCEntityFishy entityfishy = (MoCEntityFishy) entityliving;
        if (!entityfishy.getIsAdult())
        {
            stretch(entityfishy);
        }
        return entityliving.ticksExisted + f;
    }

    protected void stretch(MoCEntityFishy entityfishy)
    {
        GL11.glScalef(entityfishy.getEdad() * 0.01F, entityfishy.getEdad() * 0.01F, entityfishy.getEdad() * 0.01F);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityFishy)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityFishy fishy)
    {
        switch (fishy.getType())
        {
        case 1:
            return TEXTURE_DEFAULT;
        case 2:
            return TEXTURE_FISHY2;
        case 3:
            return TEXTURE_FISHY3;
        case 4:
            return TEXTURE_FISHY4;
        case 5:
            return TEXTURE_FISHY5;
        case 6:
            return TEXTURE_FISHY6;
        case 7:
            return TEXTURE_FISHY7;
        case 8:
            return TEXTURE_FISHY8;
        case 9:
            return TEXTURE_FISHY9;
        case 10:
            return TEXTURE_FISHY10;
        default:
            return TEXTURE_DEFAULT;
        }
    }

}
