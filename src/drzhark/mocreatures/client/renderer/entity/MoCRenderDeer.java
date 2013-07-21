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
import drzhark.mocreatures.entity.passive.MoCEntityDeer;

@SideOnly(Side.CLIENT)
public class MoCRenderDeer extends RenderLiving {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "deer.png");
    private static final ResourceLocation TEXTURE_DEER2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "deerf.png");
    private static final ResourceLocation TEXTURE_DEER3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "deerb.png");

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityDeer)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityDeer deer)
    {
        switch (deer.getType())
        {
        case 2:
            return TEXTURE_DEER2;
        case 3:
            deer.setAdult(false);
            return TEXTURE_DEER3;

        default:
            return TEXTURE_DEFAULT;
        }
    }

    public MoCRenderDeer(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityDeer entitydeer = (MoCEntityDeer) entityliving;
        super.doRenderLiving(entitydeer, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        MoCEntityDeer entitydeer = (MoCEntityDeer) entityliving;
        stretch(entitydeer);
        return entityliving.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        rotateDeer((MoCEntityDeer) entityliving);
    }

    protected void rotateDeer(MoCEntityDeer entitydeer)
    {
        if (!entitydeer.onGround && (entitydeer.getMoveSpeed() > 2.0F))
        {
            if (entitydeer.motionY > 0.5D)
            {
                GL11.glRotatef(20F, -1F, 0.0F, 0.0F);
            }
            else if (entitydeer.motionY < -0.5D)
            {
                GL11.glRotatef(-20F, -1F, 0.0F, 0.0F);
            }
            else
            {
                GL11.glRotatef((float) (entitydeer.motionY * 40D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected void stretch(MoCEntityDeer entitydeer)
    {
        float f = entitydeer.getEdad() * 0.01F;
        float f1 = 0.0F;
        if (entitydeer.getType() == 1)
        {
            f1 = 1.7F;
        }
        else if (entitydeer.getType() == 2)
        {
            f1 = 1.3F;
        }
        else
        {
            f1 = f;
        }
        if (entitydeer.getIsAdult())
        {
            f = 1.0F;
        }
        GL11.glScalef(f1, f1, f1);
    }
}
