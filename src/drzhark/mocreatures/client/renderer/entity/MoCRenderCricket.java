package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;

@SideOnly(Side.CLIENT)
public class MoCRenderCricket extends MoCRenderMoC {

    public MoCRenderCricket(ModelBase modelbase)
    {
        super(modelbase, 0.0F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
    {
        rotateCricket((MoCEntityCricket) par1EntityLiving);
    }

    protected void rotateCricket(MoCEntityCricket entityfirefly)
    {
        if (!entityfirefly.onGround)
        {
            if (entityfirefly.motionY > 0.5D)
            {
                GL11.glRotatef(35F, -1F, 0.0F, 0.0F);
            }
            else if (entityfirefly.motionY < -0.5D)
            {
                GL11.glRotatef(-35F, -1F, 0.0F, 0.0F);
            }
            else
            {
                GL11.glRotatef((float) (entityfirefly.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityCricket)par1Entity).getTexture();
    }
}
