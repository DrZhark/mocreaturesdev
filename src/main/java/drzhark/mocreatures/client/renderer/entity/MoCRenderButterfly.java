package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderButterfly extends MoCRenderInsect {

    public MoCRenderButterfly(ModelBase modelbase) {
        super(modelbase);

    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
        MoCEntityButterfly butterfly = (MoCEntityButterfly) par1EntityLiving;
        if (butterfly.isOnAir() || !butterfly.onGround) {
            adjustHeight(butterfly, butterfly.tFloat());
        }
        if (butterfly.climbing()) {
            rotateAnimal(butterfly);
        }
        stretch(butterfly);
    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityButterfly) par1Entity).getTexture();
    }
}
