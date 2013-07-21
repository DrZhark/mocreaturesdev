package drzhark.mocreatures.client.renderer.entity;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityHellRat;

@SideOnly(Side.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat {

    private Random rand = new Random();
    private int textCounter;
    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "hellrat1.png");

    public MoCRenderHellRat(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    @Override
    protected void stretch(EntityLivingBase entityliving)
    {
        float f = 1.3F;
        GL11.glScalef(f, f, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityHellRat)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityHellRat hellrat)
    {
        if (rand.nextInt(2) == 0)
        {
            textCounter++;
        }
        if (textCounter < 10)
        {
            textCounter = 10;
        }
        if (textCounter > 29)
        {
            textCounter = 10;
        }
        String textNumber = "" + textCounter;
        textNumber = textNumber.substring(0, 1);
        return new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "hellrat" + textNumber + ".png");
    }
}
