package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;

public class MoCRenderSmallFish extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "smallfish_clownfish.png");
    private static final ResourceLocation TEXTURE_ANCHOVY = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "smallfish_anchovy.png");
    private static final ResourceLocation TEXTURE_ANGELFISH = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "smallfish_angelfish.png");
    private static final ResourceLocation TEXTURE_ANGLER = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "smallfish_angler.png");
    private static final ResourceLocation TEXTURE_GOLDFISH = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "smallfish_goldfish.png");
    private static final ResourceLocation TEXTURE_HIPPOTANG = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "smallfish_hippotang.png");
    private static final ResourceLocation TEXTURE_MANDERIN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "smallfish_manderin.png");

    public MoCRenderSmallFish(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntitySmallFish)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntitySmallFish smallfish)
    {
        switch (smallfish.getType())
        {
            case 1:
                return TEXTURE_ANCHOVY;
            case 2:
                return TEXTURE_ANGELFISH;
            case 3:
                return TEXTURE_ANGLER;
            case 4:
                return TEXTURE_DEFAULT;
            case 5:
                return TEXTURE_GOLDFISH;
            case 6:
                return TEXTURE_HIPPOTANG;
            case 7:
                return TEXTURE_MANDERIN;
            default:
                return TEXTURE_DEFAULT;
        }
    }

}
