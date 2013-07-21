package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBird;

@SideOnly(Side.CLIENT)
public class MoCRenderBird extends MoCRenderMoC {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "birdblue.png");
    private static final ResourceLocation TEXTURE_WHITE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "birdwhite.png");
    private static final ResourceLocation TEXTURE_BLACK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "birdblack.png");
    private static final ResourceLocation TEXTURE_GREEN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "birdgreen.png");
    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "birdblue.png");
    private static final ResourceLocation TEXTURE_YELLOW = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "birdyellow.png");
    private static final ResourceLocation TEXTURE_RED = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "birdred.png");

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityBird)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityBird bird)
    {
        switch (bird.getType())
        {
        case 1:
            return TEXTURE_WHITE;
        case 2:
            return TEXTURE_BLACK;
        case 3:
            return TEXTURE_GREEN;
        case 5:
            return TEXTURE_YELLOW;
        case 6:
            return TEXTURE_RED;

        default:
            return TEXTURE_DEFAULT;
        }
    }

    public MoCRenderBird(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {

        /*if (!worldObj.multiplayerWorld)
        MoCEntityBird entitybird = (MoCEntityBird)entityliving;
        if(!entitybird.getTypeChosen())
        {
            entitybird.chooseType();
        }*/
        super.doRenderLiving(entityliving, d, d1, d2, f, f1);

    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        MoCEntityBird entitybird = (MoCEntityBird) entityliving;
        float f1 = entitybird.winge + ((entitybird.wingb - entitybird.winge) * f);
        float f2 = entitybird.wingd + ((entitybird.wingc - entitybird.wingd) * f);
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        if (!entityliving.worldObj.isRemote && (entityliving.ridingEntity != null))
        {

            GL11.glTranslatef(0.0F, 1.3F, 0.0F);

        }
    }
}
