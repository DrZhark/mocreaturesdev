package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderWerewolf extends RenderLiving {

    private final MoCModelWere tempWerewolf;

    public MoCRenderWerewolf(MoCModelWereHuman modelwerehuman, ModelBase modelbase, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelbase, f);
        this.addLayer(new LayerMoCWereHuman(this));
        this.tempWerewolf = (MoCModelWere) modelbase;
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
        MoCEntityWerewolf entitywerewolf = (MoCEntityWerewolf) entityliving;
        this.tempWerewolf.hunched = entitywerewolf.getIsHunched();
        super.doRender(entityliving, d, d1, d2, f, f1);

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityWerewolf) par1Entity).getTexture();
    }

    private class LayerMoCWereHuman implements LayerRenderer {

        private final MoCRenderWerewolf mocRenderer;
        private final MoCModelWereHuman mocModel = new MoCModelWereHuman();

        public LayerMoCWereHuman(MoCRenderWerewolf p_i46112_1_) {
            this.mocRenderer = p_i46112_1_;
        }

        public void doRenderLayer(MoCEntityWerewolf p_177162_1_, float p_177162_2_, float p_177162_3_, float p_177162_4_, float p_177162_5_,
                float p_177162_6_, float p_177162_7_, float p_177162_8_) {
            int myType = p_177162_1_.getType();

            if (!p_177162_1_.getIsHumanForm()) {
                bindTexture(MoCreatures.proxy.getTexture("wereblank.png"));
            } else {
                switch (myType) {

                    case 1:
                        bindTexture(MoCreatures.proxy.getTexture("weredude.png"));
                        break;
                    case 2:
                        bindTexture(MoCreatures.proxy.getTexture("werehuman.png"));
                        break;
                    case 3:
                        bindTexture(MoCreatures.proxy.getTexture("wereoldie.png"));
                        break;
                    case 4:
                        bindTexture(MoCreatures.proxy.getTexture("werewoman.png"));
                        break;
                    default:
                        bindTexture(MoCreatures.proxy.getTexture("wereoldie.png"));
                }

            }

            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_4_);
            this.mocModel.render(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_5_, p_177162_6_, p_177162_7_, p_177162_8_);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }

        @Override
        public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_,
                float p_177141_6_, float p_177141_7_, float p_177141_8_) {
            this.doRenderLayer((MoCEntityWerewolf) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_,
                    p_177141_8_);
        }
    }
}
