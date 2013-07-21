package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;

public class MoCRenderOgre extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ogregreen.png");
    private static final ResourceLocation TEXTURE_RED = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ogrered.png");
    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "ogreblue.png");

    public MoCRenderOgre(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityOgre)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityOgre ogre)
    {
        switch (ogre.getType())
        {
        case 1: 
        case 2:
            return TEXTURE_DEFAULT;
        case 3: 
        case 4:
            return TEXTURE_RED;
        case 5: 
        case 6: 
            return TEXTURE_BLUE;
        default:
            return TEXTURE_DEFAULT;
        }
    }
}
