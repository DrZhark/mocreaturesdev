package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityFox;

@SideOnly(Side.CLIENT)
public class MoCRenderFox extends RenderLiving {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "fox.png");
    private static final ResourceLocation TEXTURE_SNOW = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "foxsnow.png");

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityFox)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityFox fox)
    {
        switch (fox.getType())
        {
        case 2:
            return TEXTURE_SNOW;

        default:
            return TEXTURE_DEFAULT;
        }
    }

    public MoCRenderFox(ModelBase modelbase, float f)
    {
        super(modelbase, 0.5F);
    }
}
