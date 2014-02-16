package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.model.MoCModelTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;

@SideOnly(Side.CLIENT)
public class MoCRenderTurtle extends MoCRenderMoC {

    public MoCModelTurtle turtly;

    public MoCRenderTurtle(MoCModelTurtle modelbase, float f)
    {
        super(modelbase, f);
        turtly = modelbase;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityTurtle entityturtle = (MoCEntityTurtle) entityliving;
        turtly.upsidedown = entityturtle.getIsUpsideDown();
        turtly.swingProgress = entityturtle.swingProgress;
        turtly.isHiding = entityturtle.getIsHiding();

        if (!entityliving.worldObj.isRemote && (entityliving.ridingEntity != null))
        {

            GL11.glTranslatef(0.0F, 1.3F, 0.0F);

        }
        if (entityturtle.getIsHiding())
        {
            adjustHeight(entityturtle, 0.15F * entityturtle.getEdad() * 0.01F);
        }
        else if (!entityturtle.getIsHiding() && !entityturtle.getIsUpsideDown() && !entityturtle.isInsideOfMaterial(Material.water))
        {
            adjustHeight(entityturtle, 0.05F * entityturtle.getEdad() * 0.01F);
        }
        if (entityturtle.getIsUpsideDown())
        {
            rotateAnimal(entityturtle);
        }

        stretch(entityturtle);

    }

    protected void rotateAnimal(MoCEntityTurtle entityturtle)
    {
        //GL11.glRotatef(180F, -1F, 0.0F, 0.0F); //head up 180
        //GL11.glRotatef(180F, 0.0F, -1.0F, 0.0F); //head around 180

        float f = entityturtle.swingProgress * 10F * entityturtle.getFlipDirection();
        float f2 = entityturtle.swingProgress / 30 * entityturtle.getFlipDirection();
        GL11.glRotatef(180F + f, 0.0F, 0.0F, -1.0F);
        GL11.glTranslatef(0.0F - f2, 0.5F * entityturtle.getEdad() * 0.01F, 0.0F);
    }

    protected void adjustHeight(EntityLiving entityliving, float height)
    {
        GL11.glTranslatef(0.0F, height, 0.0F);
    }

    protected void stretch(MoCEntityTurtle entityturtle)
    {
        float f = entityturtle.getEdad() * 0.01F;
        GL11.glScalef(f, f, f);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityTurtle)par1Entity).getTexture();
    }
}
