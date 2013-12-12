package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityFox;

@SideOnly(Side.CLIENT)
public class MoCRenderFox extends RenderLiving {

    public MoCRenderFox(ModelBase modelbase)
    {
        super(modelbase, 0.5F);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityFox)par1Entity).getTexture();
    }
}
