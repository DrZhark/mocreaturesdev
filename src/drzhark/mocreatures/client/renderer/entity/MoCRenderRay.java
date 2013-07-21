package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityRay;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;

public class MoCRenderRay extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "stingray.png");
    private static final ResourceLocation TEXTURE_MANTRAY = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "stingray.png");

    public MoCRenderRay(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityRay)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityRay ray)
    {
        switch (ray.getType())
        {
            case 1:
                return TEXTURE_MANTRAY;
            default:
                return TEXTURE_DEFAULT;
        }
    }
}
