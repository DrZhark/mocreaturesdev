package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderCricket extends MoCRenderMoC<MoCEntityCricket> {

    public MoCRenderCricket(ModelBase modelbase) {
        super(modelbase, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityCricket entitycricket, float par2) {
        rotateCricket((MoCEntityCricket) entitycricket);
    }

    protected void rotateCricket(MoCEntityCricket entitycricket) {
        if (!entitycricket.onGround) {
            if (entitycricket.motionY > 0.5D) {
                GL11.glRotatef(35F, -1F, 0.0F, 0.0F);
            } else if (entitycricket.motionY < -0.5D) {
                GL11.glRotatef(-35F, -1F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entitycricket.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityCricket par1Entity) {
        return ((MoCEntityCricket) par1Entity).getTexture();
    }
}
