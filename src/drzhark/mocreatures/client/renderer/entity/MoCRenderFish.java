package drzhark.mocreatures.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLiving;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderFish extends MoCRenderAnimal{

	public MoCRenderFish(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

	
	@Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
		//the original model is rotated 90 degrees
        //GL11.glTranslatef(0.0F, 0.3F, 0.0F);
        GL11.glRotatef(90F, 0.0F, -1.0F, 0.0F);
        super.preRenderCallback(entityliving, f);
    }
}
