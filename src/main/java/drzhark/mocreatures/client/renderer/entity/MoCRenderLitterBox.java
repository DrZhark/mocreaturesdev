package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCRenderLitterBox extends RenderLiving<MoCEntityLitterBox> {

    public MoCModelLitterBox litterbox;

    public MoCRenderLitterBox(MoCModelLitterBox modellitterbox, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modellitterbox, f);
        this.litterbox = modellitterbox;
    }

    @Override
    protected void preRenderCallback(MoCEntityLitterBox entitylitterbox, float f) {
        this.litterbox.usedlitter = entitylitterbox.getUsedLitter();
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLitterBox entitylitterbox) {
        return entitylitterbox.getTexture();
    }
}
