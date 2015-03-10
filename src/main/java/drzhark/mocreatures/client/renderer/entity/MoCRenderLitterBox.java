package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLitterBox extends RenderLiving {

    public MoCModelLitterBox litterbox;

    public MoCRenderLitterBox(MoCModelLitterBox modellitterbox, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modellitterbox, f);
        this.litterbox = modellitterbox;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) entityliving;
        this.litterbox.usedlitter = entitylitterbox.getUsedLitter();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityLitterBox) par1Entity).getTexture();
    }
}
