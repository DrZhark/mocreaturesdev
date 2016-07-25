package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderScorpion extends MoCRenderMoC {

    public MoCRenderScorpion(MoCModelScorpion modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
        MoCEntityScorpion entityscorpion = (MoCEntityScorpion) entityliving;
        super.doRender(entityscorpion, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        MoCEntityScorpion entityscorpion = (MoCEntityScorpion) entityliving;
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
        GL11.glTranslatef(-1.5F, -0.5F, -2.5F);
    }

    protected void adjustHeight(EntityLiving entityliving) {

        GL11.glTranslatef(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(EntityLiving entityliving) {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(MoCEntityScorpion entityscorpion) {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult()) {
            f = entityscorpion.getEdad() * 0.01F;
        }
        GL11.glScalef(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityScorpion) par1Entity).getTexture();
    }
}
