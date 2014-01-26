package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelFirefly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;

@SideOnly(Side.CLIENT)
public class MoCRenderFirefly extends MoCRenderInsect {

    public MoCRenderFirefly(ModelBase modelbase)
    {
        super(modelbase);
        this.setRenderPassModel(new MoCModelFirefly());
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLiving, int par2, float par3)
    {
        return this.setTailBrightness((MoCEntityFirefly) par1EntityLiving, par2, par3);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
    {
        MoCEntityFirefly firefly = (MoCEntityFirefly) par1EntityLiving;
        if (firefly.getIsFlying())
        {
            rotateFirefly(firefly);
        }
        else if (firefly.climbing())
        {
            rotateAnimal(firefly);
        }

    }

    protected void rotateFirefly(MoCEntityFirefly entityfirefly)
    {
        GL11.glRotatef(40F, -1F, 0.0F, 0.0F);

    }

    /**
     * Sets the glowing belly
     */
    protected int setTailBrightness(MoCEntityFirefly entityliving, int par2, float par3)
    {
        if (par2 != 0)
        {
            return -1;
        }
        else
        {
            this.bindTexture(MoCreatures.proxy.getTexture("fireflyglow.png"));
            float var4 = 1.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            char var5 = 61680;
            int var6 = var5 % 65536;
            int var7 = var5 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) var6 / 1.0F, (float) var7 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
            return 1;
        }
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityFirefly)par1Entity).getTexture();
    }
}
