package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;

public class MoCRenderElephant extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "elephantafrican.png");
    private static final ResourceLocation TEXTURE_INDIAN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "elephantindian.png");
    private static final ResourceLocation TEXTURE_INDIANPRETTY = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "elephantindianpretty.png");
    private static final ResourceLocation TEXTURE_MAMMOTH = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "mammoth.png");
    private static final ResourceLocation TEXTURE_SONGHUA = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "mammothsonghua.png");
 
    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityElephant)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityElephant elephant)
    {
        switch (elephant.getType())
        {
        case 2:
            return TEXTURE_INDIAN;
        case 3:
            return TEXTURE_MAMMOTH;
        case 4:
            return TEXTURE_SONGHUA;
        case 5:
            return TEXTURE_INDIANPRETTY;
        default:
            return TEXTURE_DEFAULT;
        }
    }

    public MoCRenderElephant(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }
}
