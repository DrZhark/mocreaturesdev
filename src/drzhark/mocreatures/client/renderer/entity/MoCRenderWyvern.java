package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;

public class MoCRenderWyvern extends MoCRenderMoC{

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyvernsun.png");
    private static final ResourceLocation TEXTURE_SAND = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyvernsand.png");
    private static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyvernjungle.png");
    private static final ResourceLocation TEXTURE_MIX = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyvernmix.png");
    private static final ResourceLocation TEXTURE_MOTHER = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyvernmother.png");
    private static final ResourceLocation TEXTURE_LIGHT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyvernlight.png");
    private static final ResourceLocation TEXTURE_DARK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyverndark.png");
    private static final ResourceLocation TEXTURE_UNDEAD = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyvernundead.png");
    private static final ResourceLocation TEXTURE_NETHER = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wyvernnether.png");

    public MoCRenderWyvern(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityWyvern)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityWyvern wyvern)
    {
        switch (wyvern.getType())
        {
            case 1:
                return TEXTURE_JUNGLE;
            case 2:
                return TEXTURE_MIX;
            case 3:
                return TEXTURE_SAND;
            case 4:
                return TEXTURE_DEFAULT;
            case 5:
                return TEXTURE_MOTHER;
            case 6:
                return TEXTURE_UNDEAD;
            case 7:
                return TEXTURE_LIGHT;
            case 8:
                return TEXTURE_DARK;
            /*case 9:
                return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernnether.png";*/
            default:
                return TEXTURE_DEFAULT;
        }
    }
}
