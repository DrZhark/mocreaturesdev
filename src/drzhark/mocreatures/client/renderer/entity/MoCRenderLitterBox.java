package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) entityliving;
        litterbox.usedlitter = entitylitterbox.getUsedLitter();
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityLitterBox)par1Entity).getTexture();
    }
}
