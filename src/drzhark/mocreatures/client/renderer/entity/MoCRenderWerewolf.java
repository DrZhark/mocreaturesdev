package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;

@SideOnly(Side.CLIENT)
public class MoCRenderWerewolf extends RenderLiving {

    public MoCRenderWerewolf(MoCModelWereHuman modelwerehuman, ModelBase modelbase, float f)
    {
        super(modelbase, f);
        setRenderPassModel(modelwerehuman);
        //tempWerewolf = (MoCModelWerewolf) modelbase;
        tempWerewolf = (MoCModelWere) modelbase;
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityWerewolf entitywerewolf = (MoCEntityWerewolf) entityliving;
        tempWerewolf.hunched = entitywerewolf.getIsHunched();
        super.doRenderLiving(entityliving, d, d1, d2, f, f1);

    }

    protected int setWoolColorAndRender(MoCEntityWerewolf entitywerewolf, int i)
    {
        int myType = entitywerewolf.getType();

        if (!entitywerewolf.getIsHumanForm())
        {
            //entitywerewolf.texture = MoCreatures.proxy.MODEL_TEXTURE + " werewolf.png";
            loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "wereblank.png");
        }
        else
        {
            //System.out.println("type = " + myType);
            switch (myType)
            {

            case 1:
                loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "weredude.png");
                break;
            case 2:
                loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "werehuman.png");
                break;
            case 3:
                loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "wereoldie.png");
                break;
            case 4:
                loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "werewoman.png");
                break;
            default:
                loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "wereoldie.png");
            }

        }
        return 1;//(i == 0) && !entitywerewolf.wereboolean;
    }

    @Override
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return setWoolColorAndRender((MoCEntityWerewolf) entityliving, i);
    }

    private final MoCModelWere tempWerewolf;
}
