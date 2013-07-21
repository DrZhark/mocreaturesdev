package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelGolem;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;

@SideOnly(Side.CLIENT)
public class MoCRenderGolem extends MoCRenderMoC {

    private final ModelBase MoCModelG = new MoCModelGolem();
    private static TextureManager textureManager = MoCClientProxy.mc.func_110434_K();
    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "golemt.png");
    private static final ResourceLocation TEXTURE_EFFECT_1 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "golemeffect1.png");
    private static final ResourceLocation TEXTURE_EFFECT_2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "golemeffect2.png");
    private static final ResourceLocation TEXTURE_EFFECT_3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "golemeffect3.png");
    private static final ResourceLocation TEXTURE_EFFECT_4 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "golemeffect4.png");

    public MoCRenderGolem(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    /**
     * A method used to render a creeper's powered form as a pass model.
     */
    protected int renderGPassModel(MoCEntityGolem par1Entity, int par2, float par3)
    {
        boolean depth = true;

        ResourceLocation effectTexture = getEffectTexture(par1Entity);
        if (effectTexture != null)//(!effectTexture.isEmpty())//(par1Entity.getPowered())
        {
            if (depth)
            {
                GL11.glDepthMask(false);
            }
            else
            {
                GL11.glDepthMask(true);
            }

            if (par2 == 1)
            {
                float var4 = (float) par1Entity.ticksExisted + par3;
                //this.loadTexture("/armor/golemeffect.png");
                //this.loadTexture(effectTexture);
                textureManager.func_110577_a(effectTexture);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                float var5 = var4 * 0.01F;
                float var6 = var4 * 0.01F;
                GL11.glTranslatef(var5, var6, 0.0F);
                this.setRenderPassModel(this.MoCModelG);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);
                float var7 = 0.5F;
                GL11.glColor4f(var7, var7, var7, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                return 1;
            }

            if (par2 == 2)
            {
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        return -1;
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3)
    {
        return this.renderGPassModel((MoCEntityGolem) par1EntityLiving, par2, par3);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return TEXTURE_DEFAULT;
    }

    /**
     * Used for the power texture used on the golem
     * 
     * @return
     */
    public ResourceLocation getEffectTexture(MoCEntityGolem golem)
    {
        switch (golem.getGolemState())
        {
            case 1:
                return TEXTURE_EFFECT_1;
            case 2:
                return TEXTURE_EFFECT_2;
            case 3:
                return TEXTURE_EFFECT_3;
            case 4:
                return TEXTURE_EFFECT_4;
            default:
                return null;
        }
    }
}
