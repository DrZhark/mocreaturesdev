package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.model.MoCModelNewHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;

@SideOnly(Side.CLIENT)
public class MoCRenderHorseMob extends RenderLiving {

    public MoCRenderHorseMob(MoCModelNewHorseMob modelbase)
    {
        super(modelbase, 0.5F);

    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight)
    {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityHorseMob entityhorse = (MoCEntityHorseMob) entityliving;

        if (entityhorse.isGhost())
        {
            adjustHeight(entityhorse, -0.3F + (entityhorse.tFloat() / 3F));
        }
        super.preRenderCallback(entityliving, f);

    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityHorseMob)par1Entity).getTexture();
    }
}