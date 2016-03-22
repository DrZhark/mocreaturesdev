package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderButterfly extends MoCRenderInsect<MoCEntityButterfly> {

    public MoCRenderButterfly(ModelBase modelbase) {
        super(modelbase);

    }

    @Override
    protected void preRenderCallback(MoCEntityButterfly entitybutterfly, float par2) {
        if (entitybutterfly.isOnAir() || !entitybutterfly.onGround) {
            adjustHeight(entitybutterfly, entitybutterfly.tFloat());
        }
        if (entitybutterfly.climbing()) {
            rotateAnimal(entitybutterfly);
        }
        stretch(entitybutterfly);
    }

    protected void adjustHeight(MoCEntityButterfly entitybutterfly, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityButterfly entitybutterfly) {
        return entitybutterfly.getTexture();
    }
}
