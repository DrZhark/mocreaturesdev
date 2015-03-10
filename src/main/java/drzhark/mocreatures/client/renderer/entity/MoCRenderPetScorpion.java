package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderPetScorpion extends MoCRenderMoC {

    public MoCRenderPetScorpion(MoCModelScorpion modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
        MoCEntityPetScorpion entityscorpion = (MoCEntityPetScorpion) entityliving;
        super.doRender(entityscorpion, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        MoCEntityPetScorpion entityscorpion = (MoCEntityPetScorpion) entityliving;

        if (entityscorpion.climbing()) {
            rotateAnimal(entityscorpion);
        }

        if (!entityscorpion.getIsAdult()) {
            stretch(entityscorpion);
            if (entityscorpion.getIsPicked()) {
                upsideDown(entityscorpion);
            }
        } else {
            adjustHeight(entityscorpion);
        }
    }

    protected void upsideDown(EntityLiving entityliving) {
        GL11.glRotatef(-90F, -1F, 0.0F, 0.0F);

        if (entityliving.ridingEntity == MoCClientProxy.mc.thePlayer) {
            GL11.glTranslatef(-0.55F, -1.9F, -0.7F);

        } else {
            GL11.glTranslatef(-1.555F, -0.5F, -0.5F);

        }

    }

    protected void adjustHeight(EntityLiving entityliving) {
        GL11.glTranslatef(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(EntityLiving entityliving) {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(MoCEntityPetScorpion entityscorpion) {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult()) {
            f = entityscorpion.getEdad() * 0.01F;
        }
        GL11.glScalef(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityPetScorpion) par1Entity).getTexture();
    }
}
