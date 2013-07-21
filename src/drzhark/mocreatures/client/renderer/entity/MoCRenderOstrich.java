package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;

@SideOnly(Side.CLIENT)
public class MoCRenderOstrich extends MoCRenderMoC {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ostricha.png");
    private static final ResourceLocation TEXTURE_OSTRICH2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ostrichb.png");
    private static final ResourceLocation TEXTURE_OSTRICH3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ostrichc.png");
    private static final ResourceLocation TEXTURE_OSTRICH4 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ostrichd.png");
    private static final ResourceLocation TEXTURE_OSTRICH5 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ostriche.png");
    private static final ResourceLocation TEXTURE_OSTRICH6 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ostrichf.png");
    private static final ResourceLocation TEXTURE_OSTRICH7 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ostrichg.png");
    private static final ResourceLocation TEXTURE_OSTRICH8 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ostrichh.png");

    public MoCRenderOstrich(ModelBase modelbase, float f)
    {
        super(modelbase, 0.5F);
        //tempSnake = (MoCModelSnake) modelbase;
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityOstrich)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityOstrich ostrich)
    {
        if (ostrich.transformCounter != 0 && ostrich.transformType > 4)
        {
            ResourceLocation newText = TEXTURE_DEFAULT;
            if (ostrich.transformType == 5)
            {
                newText = TEXTURE_OSTRICH5;
            }
            if (ostrich.transformType == 6)
            {
                newText = TEXTURE_OSTRICH6;
            }
            if (ostrich.transformType == 7)
            {
                newText = TEXTURE_OSTRICH7;
            }
            if (ostrich.transformType == 8)
            {
                newText = TEXTURE_OSTRICH8;
            }
            
            if ((ostrich.transformCounter % 5) == 0) { return newText; }
            if (ostrich.transformCounter > 50 && (ostrich.transformCounter % 3) == 0) { return newText; }

            if (ostrich.transformCounter > 75 && (ostrich.transformCounter % 4) == 0) { return newText; }
        }

        switch (ostrich.getType())
        {
            case 1:
                return TEXTURE_OSTRICH3; //chick
            case 2:
                return TEXTURE_OSTRICH2; //female
            case 3:
                return TEXTURE_DEFAULT; //male
            case 4:
                return TEXTURE_OSTRICH4; //albino
            case 5:
                return TEXTURE_OSTRICH5; //nether
            case 6:
                return TEXTURE_OSTRICH6; //black wyvern
            case 7:
                return TEXTURE_OSTRICH7; //undead
            case 8:
                return TEXTURE_OSTRICH8; //unicorned
            default:
                return TEXTURE_DEFAULT;
        }
    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight)
    {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
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