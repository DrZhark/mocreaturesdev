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
    private int textCounter;
    private static TextureManager textureManager = MoCClientProxy.mc.func_110434_K();
    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolfbrown.png");
    private static final ResourceLocation TEXTURE_BLACK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolfblack.png");
    private static final ResourceLocation TEXTURE_TIMBER = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolftimber.png");
    private static final ResourceLocation TEXTURE_FIRE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolffire.png");
    private static final ResourceLocation TEXTURE_FIRE1 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolffire1.png");
    private static final ResourceLocation TEXTURE_BLANK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wereblank.png");
    private static final ResourceLocation TEXTURE_DUDE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "weredude.png");
    private static final ResourceLocation TEXTURE_HUMAN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "werehuman.png");
    private static final ResourceLocation TEXTURE_OLDIE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wereoldie.png");
    private static final ResourceLocation TEXTURE_WOMAN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "werewoman.png");

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
            //loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "wereblank.png");
            textureManager.func_110577_a(TEXTURE_BLANK);
        }
        else
        {
            //System.out.println("type = " + myType);
            switch (myType)
            {

            case 1:
                //loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "weredude.png");
                textureManager.func_110577_a(TEXTURE_DUDE);
                break;
            case 2:
                //loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "werehuman.png");
                textureManager.func_110577_a(TEXTURE_HUMAN);
                break;
            case 3:
                //loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "wereoldie.png");
                textureManager.func_110577_a(TEXTURE_OLDIE);
                break;
            case 4:
                //loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "werewoman.png");
                textureManager.func_110577_a(TEXTURE_WOMAN);
                break;
            default:
                //loadTexture(MoCreatures.proxy.MODEL_TEXTURE + "wereoldie.png");
                textureManager.func_110577_a(TEXTURE_OLDIE);
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
        return this.getTexture((MoCEntityWerewolf)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityWerewolf werewolf)
    {
        if (werewolf.getIsHumanForm()) { return TEXTURE_BLANK; }

        switch (werewolf.getType())
        {
        case 1:
            return TEXTURE_BLACK;
        case 2:
            return TEXTURE_DEFAULT;
        case 3:
            return TEXTURE_TIMBER;
        case 4:
            if (!MoCreatures.proxy.getAnimateTextures()) { return TEXTURE_FIRE1; }
            textCounter++;
            if (textCounter < 10)
            {
                textCounter = 10;
            }
            if (textCounter > 39)
            {
                textCounter = 10;
            }
            String NTA = MoCreatures.proxy.MODEL_TEXTURE + "wolffire";
            String NTB = "" + textCounter;
            NTB = NTB.substring(0, 1);
            String NTC = ".png";

            return new ResourceLocation(NTA + NTB + NTC);
        default:
            return TEXTURE_DEFAULT;
        }
    }
}
