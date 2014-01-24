package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityBird;

@SideOnly(Side.CLIENT)
public class MoCRenderBird extends MoCRenderMoC {

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityBird)par1Entity).getTexture();
    }

    public MoCRenderBird(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {

        /*if (!worldObj.multiplayerWorld)
        MoCEntityBird entitybird = (MoCEntityBird)entityliving;
        if(!entitybird.getTypeChosen())
        {
            entitybird.chooseType();
        }*/
        super.doRender(entityliving, d, d1, d2, f, f1);

    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        MoCEntityBird entitybird = (MoCEntityBird) entityliving;
        float f1 = entitybird.winge + ((entitybird.wingb - entitybird.winge) * f);
        float f2 = entitybird.wingd + ((entitybird.wingc - entitybird.wingd) * f);
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        if (!entityliving.worldObj.isRemote && (entityliving.ridingEntity != null))
        {

            GL11.glTranslatef(0.0F, 1.3F, 0.0F);

        }
    }
}
