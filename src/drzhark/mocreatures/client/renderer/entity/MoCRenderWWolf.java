package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderWWolf extends RenderLiving {

    public MoCRenderWWolf(ModelBase modelbase, float f)
    {
        super(modelbase, f);
        //setRenderPassModel(modelbase1);
    }

    /*protected int setWoolColorAndRender(MoCEntityWWolf entitywwolf, int i)
    {
        loadTexture(MoCreatures.proxy.MODEL_TEXTURE + " wolfb.png");
        return 1;//(i == 0);// && !entitywwolf.wolfboolean;
    }*/

    /*@Override
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return setWoolColorAndRender((MoCEntityWWolf) entityliving, i);
    }*/
}
