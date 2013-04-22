package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// TODO 4FIX NOT NEEDED?
@SideOnly(Side.CLIENT)
public class MoCRenderSharkEgg extends RenderLiving {

    public MoCRenderSharkEgg(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }
}
