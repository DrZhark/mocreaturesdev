package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// TODO 4FIX NOT NEEDED?
@SideOnly(Side.CLIENT)
public class MoCRenderSharkEgg extends RenderLiving {

    public MoCRenderSharkEgg(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        // TODO Auto-generated method stub
        return null;
    }
}
