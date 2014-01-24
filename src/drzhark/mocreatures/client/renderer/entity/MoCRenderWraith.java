package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;

@SideOnly(Side.CLIENT)
public class MoCRenderWraith extends RenderLiving {

    public MoCRenderWraith(ModelBiped modelbiped, float f)
    {
        //super(modelbiped, f, 1.0F);
        super(modelbiped, f);
        //modelBipedMain = modelbiped;
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityWraith wraith = (MoCEntityWraith) entityliving;
        //boolean flag = wraith.isGlowing();
        boolean flag = false;

        GL11.glPushMatrix();
        GL11.glEnable(3042 /*GL_BLEND*/);
        if (!flag)
        {
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        }
        else
        {
            GL11.glBlendFunc(770, 1);
        }
        super.doRender(entityliving, d, d1, d2, f, f1);
        GL11.glDisable(3042/*GL_BLEND*/);
        GL11.glPopMatrix();

    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityWraith)par1Entity).getTexture();
    }
}