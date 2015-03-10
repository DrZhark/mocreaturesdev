package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderWerewolf extends RenderLiving {

    private final MoCModelWere tempWerewolf;

    public MoCRenderWerewolf(MoCModelWereHuman modelwerehuman, ModelBase modelbase, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelbase, f);
        // TODO
        //setRenderPassModel(modelwerehuman);
        //tempWerewolf = (MoCModelWerewolf) modelbase;
        this.tempWerewolf = (MoCModelWere) modelbase;
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
        MoCEntityWerewolf entitywerewolf = (MoCEntityWerewolf) entityliving;
        this.tempWerewolf.hunched = entitywerewolf.getIsHunched();
        super.doRender(entityliving, d, d1, d2, f, f1);

    }

    protected int shouldRenderPass(MoCEntityWerewolf entitywerewolf, int i) {
        int myType = entitywerewolf.getType();

        if (!entitywerewolf.getIsHumanForm()) {
            this.bindTexture(MoCreatures.proxy.getTexture("wereblank.png"));
        } else {
            switch (myType) {

                case 1:
                    this.bindTexture(MoCreatures.proxy.getTexture("weredude.png"));
                    break;
                case 2:
                    this.bindTexture(MoCreatures.proxy.getTexture("werehuman.png"));
                    break;
                case 3:
                    this.bindTexture(MoCreatures.proxy.getTexture("wereoldie.png"));
                    break;
                case 4:
                    this.bindTexture(MoCreatures.proxy.getTexture("werewoman.png"));
                    break;
                default:
                    this.bindTexture(MoCreatures.proxy.getTexture("wereoldie.png"));
            }

        }
        return 1;
    }

    /*
    @Override
    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f) {
        return shouldRenderPass((MoCEntityWerewolf) entityliving, i);
    }*/

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityWerewolf) par1Entity).getTexture();
    }
}
