package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;

public class MoCRenderCrab extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "craba.png");
    private static final ResourceLocation TEXTURE_CRAB2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "crabb.png");

    public MoCRenderCrab(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityCrab)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityCrab crab)
    {
        switch (crab.getType())
        {
            case 2:
                return TEXTURE_CRAB2;
            
            default:
                return TEXTURE_DEFAULT;
            }
    }
}
