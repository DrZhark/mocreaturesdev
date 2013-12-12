package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.MoCEntityInsect;

@SideOnly(Side.CLIENT)
public class MoCRenderInsect extends MoCRenderMoC {
    public MoCRenderInsect(ModelBase modelbase)
    {
        super(modelbase, 0.0F);

    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
    {
        MoCEntityInsect insect = (MoCEntityInsect) par1EntityLiving;

        if (insect.climbing())
        {
            rotateAnimal(insect);
        }

        stretch(insect);

    }

    protected void rotateAnimal(EntityLiving entityliving)
    {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(MoCEntityInsect entity)
    {
        float sizeFactor = entity.getSizeFactor();
        GL11.glScalef(sizeFactor, sizeFactor, sizeFactor);
    }
}
