package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;

@SideOnly(Side.CLIENT)
public class MoCRenderWerewolf extends RenderLiving {

    private final MoCModelWere tempWerewolf;
    private static TextureManager textureManager = MoCClientProxy.mc.func_110434_K();

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
            textureManager.func_110577_a(MoCreatures.proxy.getTexture("wereblank.png"));
        }
        else
        {
            switch (myType)
            {

            case 1:
                textureManager.func_110577_a(MoCreatures.proxy.getTexture("weredude.png"));
                break;
            case 2:
                textureManager.func_110577_a(MoCreatures.proxy.getTexture("werehuman.png"));
                break;
            case 3:
                textureManager.func_110577_a(MoCreatures.proxy.getTexture("wereoldie.png"));
                break;
            case 4:
                textureManager.func_110577_a(MoCreatures.proxy.getTexture("werewoman.png"));
                break;
            default:
                textureManager.func_110577_a(MoCreatures.proxy.getTexture("wereoldie.png"));
            }

        }
        return 1;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f)
    {
        return setWoolColorAndRender((MoCEntityWerewolf) entityliving, i);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return ((MoCEntityWerewolf)par1Entity).getTexture();
    }
}
