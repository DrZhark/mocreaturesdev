package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;

@SideOnly(Side.CLIENT)
public class MoCRenderLitterBox extends RenderLiving {

    public MoCModelLitterBox litterbox;

    public MoCRenderLitterBox(MoCModelLitterBox modellitterbox, float f)
    {
        super(modellitterbox, f);
        litterbox = modellitterbox;
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) entityliving;
        litterbox.usedlitter = entitylitterbox.getUsedLitter();
        /*if (entityliving.ridingEntity == MoCClientProxy.mc.thePlayer)
        {

            GL11.glTranslatef(0.0F, 1.1F, 0.0F);

        }*/
    }
}
