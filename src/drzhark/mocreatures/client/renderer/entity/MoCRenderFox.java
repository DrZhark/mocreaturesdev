package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderFox extends RenderLiving {

    public MoCRenderFox(ModelBase modelbase)
    {
        super(modelbase, 0.5F);
    }
}
