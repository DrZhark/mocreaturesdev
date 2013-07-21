package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;

public class MoCRenderDragonfly extends MoCRenderInsect{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "dragonflyd.png");
    private static final ResourceLocation TEXTURE_DRAGONFLY1 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "dragonflya.png");
    private static final ResourceLocation TEXTURE_DRAGONFLY2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "dragonflyb.png");
    private static final ResourceLocation TEXTURE_DRAGONFLY3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "dragonflyc.png");

    public MoCRenderDragonfly(ModelBase modelbase)
    {
        super(modelbase);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityDragonfly)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityDragonfly dragonfly)
    {
        switch (dragonfly.getType())
        {
            case 1:
                return TEXTURE_DRAGONFLY1;
            case 2:
                return TEXTURE_DRAGONFLY2;
            case 3:
                return TEXTURE_DRAGONFLY3;

            default:
                return TEXTURE_DEFAULT;
        }
    }
}
