
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
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;

@SideOnly(Side.CLIENT)
public class MoCRenderButterfly extends MoCRenderInsect {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bfpierisrapae.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY1 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bfagalaisurticae.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bfargyreushyperbius.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bfathymanefte.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY4 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bfcatopsiliapomona.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY5 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bfmorphopeleides.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY6 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "bfvanessaatalanta.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY8 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "mothcamptogrammabilineata.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY9 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "mothidiaaemula.png");
    private static final ResourceLocation TEXTURE_BUTTERFLY10 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "moththyatirabatis.png");

    public MoCRenderButterfly(ModelBase modelbase)
    {
        super(modelbase);

    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
    {
        MoCEntityButterfly butterfly = (MoCEntityButterfly) par1EntityLiving;
        if (butterfly.isOnAir() || !butterfly.onGround)
        {
            adjustHeight(butterfly, butterfly.tFloat());
        }
        if (butterfly.climbing())
        {
            rotateAnimal(butterfly);
        }
        stretch(butterfly);
    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight)
    {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityButterfly)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityButterfly butterfly)
    {
        switch (butterfly.getType())
        {
        case 1:
            return TEXTURE_BUTTERFLY1;
        case 2:
            return TEXTURE_BUTTERFLY2;
        case 3:
            return TEXTURE_BUTTERFLY3;
        case 4:
            return TEXTURE_BUTTERFLY4;
        case 5:
            return TEXTURE_BUTTERFLY5;
        case 6:
            return TEXTURE_BUTTERFLY6;
        case 7:
            return TEXTURE_DEFAULT;
        case 8:
            return TEXTURE_BUTTERFLY8;
        case 9:
            return TEXTURE_BUTTERFLY9;
        case 10:
            return TEXTURE_BUTTERFLY10;
        default:
            return TEXTURE_DEFAULT;
        }
    }
}
