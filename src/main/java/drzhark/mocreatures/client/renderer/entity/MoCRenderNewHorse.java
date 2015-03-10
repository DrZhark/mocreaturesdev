package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelNewHorse;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderNewHorse extends MoCRenderMoC {

    public MoCRenderNewHorse(MoCModelNewHorse modelbase) {
        super(modelbase, 0.5F);

    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityHorse) par1Entity).getTexture();
    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        MoCEntityHorse entityhorse = (MoCEntityHorse) entityliving;
        if (!entityhorse.getIsAdult() || entityhorse.getType() > 64) {
            stretch(entityhorse);
        }
        if (entityhorse.isGhost()) {
            adjustHeight(entityhorse, -0.3F + (entityhorse.tFloat() / 5F));
        }
        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityHorse entityhorse) {
        float sizeFactor = entityhorse.getEdad() * 0.01F;
        if (entityhorse.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        if (entityhorse.getType() > 64) //donkey
        {
            sizeFactor *= 0.9F;
        }
        GL11.glScalef(sizeFactor, sizeFactor, sizeFactor);
    }

}
