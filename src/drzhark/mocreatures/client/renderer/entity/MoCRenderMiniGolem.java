package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;

public class MoCRenderMiniGolem extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "minigolem.png");
 
    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return TEXTURE_DEFAULT;
    }

    public MoCRenderMiniGolem(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }
}
