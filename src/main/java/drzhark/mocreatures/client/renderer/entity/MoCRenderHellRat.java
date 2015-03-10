package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.monster.MoCEntityHellRat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat {

    public MoCRenderHellRat(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected void stretch(EntityLivingBase entityliving) {
        float f = 1.3F;
        GL11.glScalef(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityHellRat) par1Entity).getTexture();
    }
}
