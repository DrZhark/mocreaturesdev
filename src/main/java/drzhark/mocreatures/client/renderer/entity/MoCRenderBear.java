package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderBear extends MoCRenderMoC {

    public MoCRenderBear(MoCModelBear modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        MoCEntityBear entitybear = (MoCEntityBear) entityliving;
        stretch(entitybear);
        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityBear entitybear) {
        float sizeFactor = entitybear.getEdad() * 0.01F;
        if (entitybear.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        sizeFactor *= entitybear.getBearSize();
        GL11.glScalef(sizeFactor, sizeFactor, sizeFactor);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityBear) par1Entity).getTexture();
    }
}
