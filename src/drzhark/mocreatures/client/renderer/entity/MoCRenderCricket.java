package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityCricket;

@SideOnly(Side.CLIENT)
public class MoCRenderCricket extends MoCRenderAnimal {
    public MoCRenderCricket(ModelBase modelbase)
    {
        super(modelbase, 0.0F);
        //this.setRenderPassModel(new MoCModelFirefly());
    }

    @Override
    protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
    {
        rotateCricket((MoCEntityCricket) par1EntityLiving);
    }

    protected void rotateCricket(MoCEntityCricket entityfirefly)
    {
        /*if (entityfirefly.getIsFlying())
        {
            GL11.glRotatef(40F, -1F, 0.0F, 0.0F);
        }*/

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

}
