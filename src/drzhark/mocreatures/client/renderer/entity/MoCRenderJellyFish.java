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
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;

@SideOnly(Side.CLIENT)
public class MoCRenderJellyFish extends RenderLiving {

    float depth = 0F;

    public MoCRenderJellyFish(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        GL11.glTranslatef(0.0F, depth, 0.0F);
        MoCEntityJellyFish jelly = (MoCEntityJellyFish) entityliving;
        if (!jelly.isSwimming() && jelly.onGround)
        {

            adjustHeight(jelly);
            rotateAnimal(jelly);
        }
        else
        {
            pulse(jelly);
        }

    }

    protected void rotateAnimal(EntityLiving entityliving)
    {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void adjustHeight(EntityLiving entityliving)
    {
        GL11.glTranslatef(0.0F, -0.3F, 0.0F);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityJellyFish entityjellyfish = (MoCEntityJellyFish) entityliving;
        boolean flag = entityjellyfish.isGlowing();

        if (!entityjellyfish.isSwimming())
        {
            depth = 0.09F;
        }
        else

        {
            depth = 0.3F;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042 /*GL_BLEND*/);
        if (!flag)
        {
            float transparency = 0.7F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        }
        else
        {
            GL11.glBlendFunc(770, 1);
            //GL11.glBlendFunc(770, GL11.GL_ONE);
        }
        super.doRender(entityliving, d, d1, d2, f, f1);
        GL11.glDisable(3042/*GL_BLEND*/);
        GL11.glPopMatrix();

    }


    protected void stretch(MoCEntityJellyFish entityjellyfish)
    {
        GL11.glScalef(entityjellyfish.getEdad() * 0.01F, entityjellyfish.getEdad() * 0.01F, entityjellyfish.getEdad() * 0.01F);
    }

    protected void pulse(MoCEntityJellyFish entityjellyfish)
    {

        float pulseSize = entityjellyfish.pulsingSize;
        if (pulseSize > 0.2F)
        {
            pulseSize = 0.2F - (pulseSize - 0.2F);
        }
        float scale = entityjellyfish.getEdad() * 0.01F + (pulseSize/4);
        float scale2 = entityjellyfish.getEdad() * 0.01F + (pulseSize / 4);
        GL11.glScalef(scale, scale2, scale);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityJellyFish)par1Entity).getTexture();
    }
}
