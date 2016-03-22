package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderMouse extends MoCRenderMoC<MoCEntityMouse> {

    public MoCRenderMouse(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void doRender(MoCEntityMouse entitymouse, double d, double d1, double d2, float f, float f1) {
        super.doRender(entitymouse, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(MoCEntityMouse entitymouse, float f) {
        stretch(entitymouse);
        return entitymouse.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityMouse entitymouse, float f) {
        if (entitymouse.upsideDown()) {
            upsideDown(entitymouse);

        }
        if (entitymouse.climbing()) {
            rotateAnimal(entitymouse);
        }
    }

    protected void rotateAnimal(MoCEntityMouse entitymouse) {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(MoCEntityMouse entitymouse) {
        float f = 0.6F;
        GL11.glScalef(f, f, f);
    }

    protected void upsideDown(MoCEntityMouse entitymouse) {
        GL11.glRotatef(-90F, -1F, 0.0F, 0.0F);
        //GL11.glTranslatef(-0.55F, 0F, -0.7F);
        GL11.glTranslatef(-0.55F, 0F, 0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityMouse entitymouse) {
        return entitymouse.getTexture();
    }
}
