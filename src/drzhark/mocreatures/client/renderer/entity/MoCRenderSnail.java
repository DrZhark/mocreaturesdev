package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;

public class MoCRenderSnail extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "snaila.png");
    private static final ResourceLocation TEXTURE_SNAIL2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "snailb.png");
    private static final ResourceLocation TEXTURE_SNAIL3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "snailc.png");
    private static final ResourceLocation TEXTURE_SNAIL4 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "snaild.png");
    private static final ResourceLocation TEXTURE_SNAIL5 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "snaile.png");
    private static final ResourceLocation TEXTURE_SNAIL6 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "snailf.png");


    public MoCRenderSnail(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntitySnail)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntitySnail snail)
    {
        switch (snail.getType())
        {
            case 1:
                return TEXTURE_DEFAULT;
            case 2:
                return TEXTURE_SNAIL2;
            case 3:
                return TEXTURE_SNAIL3;
            case 4:
                return TEXTURE_SNAIL4;
            case 5:
                return TEXTURE_SNAIL5;
            case 6:
                return TEXTURE_SNAIL6;
            default:
                return TEXTURE_DEFAULT;
        }
    }
}
