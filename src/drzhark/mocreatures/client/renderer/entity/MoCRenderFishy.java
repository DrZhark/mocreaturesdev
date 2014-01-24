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
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;

@SideOnly(Side.CLIENT)
public class MoCRenderFishy extends RenderLiving {

    public MoCRenderFishy(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityFishy entityfishy = (MoCEntityFishy) entityliving;
        if (entityfishy.getType() == 0)// && !MoCreatures.mc.isMultiplayerWorld())
        {
            entityfishy.selectType();
        }
        super.doRender(entityfishy, d, d1, d2, f, f1);
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

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityFishy)par1Entity).getTexture();
    }
}
