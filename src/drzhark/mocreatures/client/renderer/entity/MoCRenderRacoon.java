package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;

public class MoCRenderRacoon extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation(MoCreatures.proxy.MODEL_TEXTURE + "racoon.png");
 
    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return TEXTURE_DEFAULT;
    }

    public MoCRenderRacoon(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }
}
