package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderBear extends MoCRenderMoC<MoCEntityBear> {

    public MoCRenderBear(MoCModelBear modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityBear entitybear, float f) {
        stretch(entitybear);
        super.preRenderCallback(entitybear, f);

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
    protected ResourceLocation getEntityTexture(MoCEntityBear entitybear) {
        return entitybear.getTexture();
    }
}
