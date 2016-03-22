package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderOstrich extends MoCRenderMoC<MoCEntityOstrich> {

    public MoCRenderOstrich(ModelBase modelbase, float f) {
        super(modelbase, 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityOstrich entityostrich) {
        return entityostrich.getTexture();
    }

    protected void adjustHeight(MoCEntityOstrich entityliving, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityOstrich entityliving, float f) {
        MoCEntityOstrich entityostrich = (MoCEntityOstrich) entityliving;
        if (entityostrich.getType() == 1) {
            stretch(entityostrich);
        }

        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityOstrich entityostrich) {

        float f = entityostrich.getEdad() * 0.01F;
        GL11.glScalef(f, f, f);
    }

}
