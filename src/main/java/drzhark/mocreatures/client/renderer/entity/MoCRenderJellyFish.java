package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderJellyFish extends RenderLiving {

    float depth = 0F;

    public MoCRenderJellyFish(ModelBase modelbase, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelbase, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        GL11.glTranslatef(0.0F, this.depth, 0.0F);
        MoCEntityJellyFish jelly = (MoCEntityJellyFish) entityliving;
        if (!jelly.isSwimming() && jelly.onGround) {

            adjustHeight(jelly);
            rotateAnimal(jelly);
        } else {
            pulse(jelly);
        }

    }

    protected void rotateAnimal(EntityLiving entityliving) {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void adjustHeight(EntityLiving entityliving) {
        GL11.glTranslatef(0.0F, -0.3F, 0.0F);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
        MoCEntityJellyFish entityjellyfish = (MoCEntityJellyFish) entityliving;
        boolean flag = entityjellyfish.isGlowing();

        if (!entityjellyfish.isSwimming()) {
            this.depth = 0.09F;
        } else

        {
            this.depth = 0.3F;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042 /* GL_BLEND */);
        if (!flag) {
            float transparency = 0.7F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        } else {
            GL11.glBlendFunc(770, 1);
            //GL11.glBlendFunc(770, GL11.GL_ONE);
        }
        super.doRender(entityliving, d, d1, d2, f, f1);
        GL11.glDisable(3042/* GL_BLEND */);
        GL11.glPopMatrix();

    }

    protected void stretch(MoCEntityJellyFish entityjellyfish) {
        GL11.glScalef(entityjellyfish.getEdad() * 0.01F, entityjellyfish.getEdad() * 0.01F, entityjellyfish.getEdad() * 0.01F);
    }

    protected void pulse(MoCEntityJellyFish entityjellyfish) {

        float pulseSize = entityjellyfish.pulsingSize;
        if (pulseSize > 0.2F) {
            pulseSize = 0.2F - (pulseSize - 0.2F);
        }
        float scale = entityjellyfish.getEdad() * 0.01F + (pulseSize / 4);
        float scale2 = entityjellyfish.getEdad() * 0.01F + (pulseSize / 4);
        GL11.glScalef(scale, scale2, scale);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityJellyFish) par1Entity).getTexture();
    }
}
