package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderFishy extends RenderLiving<MoCEntityFishy> {

    public MoCRenderFishy(ModelBase modelbase, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelbase, f);
    }

    @Override
    public void doRender(MoCEntityFishy entityfishy, double d, double d1, double d2, float f, float f1) {
        if (entityfishy.getType() == 0) { // && !MoCreatures.mc.isMultiplayerWorld())
            entityfishy.selectType();
        }
        super.doRender(entityfishy, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(MoCEntityFishy entityfishy, float f) {
        GL11.glTranslatef(0.0F, 0.3F, 0.0F);
    }

    @Override
    protected float handleRotationFloat(MoCEntityFishy entityfishy, float f) {
        if (!entityfishy.getIsAdult()) {
            stretch(entityfishy);
        }
        return entityfishy.ticksExisted + f;
    }

    protected void stretch(MoCEntityFishy entityfishy) {
        GL11.glScalef(entityfishy.getEdad() * 0.01F, entityfishy.getEdad() * 0.01F, entityfishy.getEdad() * 0.01F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityFishy entityfishy) {
        return entityfishy.getTexture();
    }
}
