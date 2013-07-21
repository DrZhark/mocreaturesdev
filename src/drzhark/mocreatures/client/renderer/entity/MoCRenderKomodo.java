package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;

public class MoCRenderKomodo extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "komododragon.png");

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return TEXTURE_DEFAULT;
    }

    public MoCRenderKomodo(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }
}
