package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderBunny extends MoCRenderMoC<MoCEntityBunny> {

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBunny entitybunny) {
        return ((MoCEntityBunny) entitybunny).getTexture();
    }

    public MoCRenderBunny(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void doRender(MoCEntityBunny entitybunny, double d, double d1, double d2, float f, float f1) {
        super.doRender(entitybunny, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(MoCEntityBunny entitybunny, float f) {
        if (!entitybunny.getIsAdult()) {
            stretch(entitybunny);
        }
        return entitybunny.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityBunny entitybunny, float f) {
        rotBunny(entitybunny);
        adjustOffsets(entitybunny.getAdjustedXOffset(), entitybunny.getAdjustedYOffset(),entitybunny.getAdjustedZOffset());
    }

    protected void rotBunny(MoCEntityBunny entitybunny) {
        if (!entitybunny.onGround && (entitybunny.getRidingEntity() == null)) {
            if (entitybunny.motionY > 0.5D) {
                GL11.glRotatef(35F, -1F, 0.0F, 0.0F);
            } else if (entitybunny.motionY < -0.5D) {
                GL11.glRotatef(-35F, -1F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entitybunny.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected void stretch(MoCEntityBunny entitybunny) {
        float f = entitybunny.getEdad() * 0.01F;
        GL11.glScalef(f, f, f);
    }
}
