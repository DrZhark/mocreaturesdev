package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelNewHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderHorseMob extends RenderLiving<MoCEntityHorseMob> {

    public MoCRenderHorseMob(MoCModelNewHorseMob modelbase) {
        super(MoCClientProxy.mc.getRenderManager(), modelbase, 0.5F);

    }

    protected void adjustHeight(MoCEntityHorseMob entityhorsemob, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityHorseMob entityhorsemob) {
        return entityhorsemob.getTexture();
    }
}
