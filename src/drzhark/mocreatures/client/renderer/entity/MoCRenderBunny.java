package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;

@SideOnly(Side.CLIENT)
public class MoCRenderBunny extends MoCRenderMoC {

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityBunny)par1Entity).getTexture();
    }

    public MoCRenderBunny(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityBunny entitybunny = (MoCEntityBunny) entityliving;
        super.doRender(entitybunny, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        MoCEntityBunny entitybunny = (MoCEntityBunny) entityliving;
        if (!entitybunny.getIsAdult())
        {
            stretch(entitybunny);
        }
        return entityliving.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        rotBunny((MoCEntityBunny) entityliving);
        if (entityliving.ridingEntity != null)
        {
            //GL11.glTranslatef(0.0F, 1.3F, 0.0F);
        }
        else
        {
            GL11.glTranslatef(0.0F, 0.1F, 0.0F);
        }
    }

    protected void rotBunny(MoCEntityBunny entitybunny)
    {
        if (!entitybunny.onGround && (entitybunny.ridingEntity == null))
        {
            if (entitybunny.motionY > 0.5D)
            {
                GL11.glRotatef(35F, -1F, 0.0F, 0.0F);
            }
            else if (entitybunny.motionY < -0.5D)
            {
                GL11.glRotatef(-35F, -1F, 0.0F, 0.0F);
            }
            else
            {
                GL11.glRotatef((float) (entitybunny.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected void stretch(MoCEntityBunny entitybunny)
    {
        float f = entitybunny.getEdad() * 0.01F;
        GL11.glScalef(f, f, f);
    }
}
