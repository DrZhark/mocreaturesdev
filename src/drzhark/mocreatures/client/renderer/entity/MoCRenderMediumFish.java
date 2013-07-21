package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;

public class MoCRenderMediumFish extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "mediumfish_salmon.png");
    private static final ResourceLocation TEXTURE_COD = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "mediumfish_cod.png");
    private static final ResourceLocation TEXTURE_BASS = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "mediumfish_bass.png");

    public MoCRenderMediumFish(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityMediumFish)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityMediumFish mediumfish)
    {
        switch (mediumfish.getType())
        {
        case 1:
            return TEXTURE_DEFAULT;
        case 2:
            return TEXTURE_COD;
        case 3:
            return TEXTURE_BASS;
        default:
            return TEXTURE_DEFAULT;
        }
    }
}