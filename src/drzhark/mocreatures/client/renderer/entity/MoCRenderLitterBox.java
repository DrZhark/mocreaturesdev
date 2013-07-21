package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;

@SideOnly(Side.CLIENT)
public class MoCRenderLitterBox extends RenderLiving {

    public MoCModelLitterBox litterbox;
    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "litterbox.png");

    public MoCRenderLitterBox(MoCModelLitterBox modellitterbox, float f)
    {
        super(modellitterbox, f);
        litterbox = modellitterbox;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) entityliving;
        litterbox.usedlitter = entitylitterbox.getUsedLitter();
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return TEXTURE_DEFAULT;
    }
}
