package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;

@SideOnly(Side.CLIENT)
public class MoCRenderWWolf extends RenderLiving {

    public MoCRenderWWolf(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityWWolf)par1Entity).getTexture();
    }
}
