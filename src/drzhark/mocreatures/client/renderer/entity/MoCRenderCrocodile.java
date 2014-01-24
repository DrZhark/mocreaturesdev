package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.model.MoCModelCrocodile;
import drzhark.mocreatures.entity.passive.MoCEntityCrocodile;

@SideOnly(Side.CLIENT)
public class MoCRenderCrocodile extends RenderLiving {

    public MoCRenderCrocodile(MoCModelCrocodile modelbase, float f)
    {
        super(modelbase, f);
        croc = modelbase;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityCrocodile)par1Entity).getTexture();
    }

    @Override
    public void doRender(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {

        MoCEntityCrocodile entitycrocodile = (MoCEntityCrocodile) entityliving;
        super.doRender(entitycrocodile, d, d1, d2, f, f1);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityCrocodile entitycrocodile = (MoCEntityCrocodile) entityliving;
        croc.biteProgress = entitycrocodile.biteProgress;
        croc.swimming = entitycrocodile.isSwimming();
        croc.resting = entitycrocodile.getIsSitting();
        if (entitycrocodile.isSpinning())
        {
            spinCroc(entitycrocodile, (EntityLiving) entitycrocodile.riddenByEntity);
        }
        stretch(entitycrocodile);
        if (entitycrocodile.getIsSitting())
        {
            if (!entitycrocodile.isInsideOfMaterial(Material.water))
            {
                adjustHeight(entitycrocodile, 0.2F);
            }
            else
            {
                //adjustHeight(entitycrocodile, 0.1F);
            }

        }
        /*        if(!entitycrocodile.getIsAdult())
                {
                    
                }
        */
    }

    protected void rotateAnimal(MoCEntityCrocodile entitycrocodile)
    {

        //float f = entitycrocodile.swingProgress *10F *entitycrocodile.getFlipDirection();
        //float f2 = entitycrocodile.swingProgress /30 *entitycrocodile.getFlipDirection();
        //GL11.glRotatef(180F + f, 0.0F, 0.0F, -1.0F); 
        //GL11.glTranslatef(0.0F-f2, 0.5F, 0.0F);
    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight)
    {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    protected void spinCroc(MoCEntityCrocodile croc, EntityLiving prey)
    {
        int intSpin = croc.spinInt;

        int direction = 1;
        if (intSpin > 40)
        {
            intSpin -= 40;
            direction = -1;
        }
        int intEndSpin = intSpin;
        if (intSpin >= 20)
        {
            intEndSpin = (20 - (intSpin - 20));
        }
        if (intEndSpin == 0)
        {
            intEndSpin = 1;
        }
        float f3 = ((((float) intEndSpin) - 1.0F) / 20F) * 1.6F;
        f3 = MathHelper.sqrt_float(f3);
        if (f3 > 1.0F)
        {
            f3 = 1.0F;
        }
        f3 *= direction;
        GL11.glRotatef(f3 * 90F, 0.0F, 0.0F, 1.0F);

        if (prey != null)
        {
            prey.deathTime = intEndSpin;
        }
    }

    protected void stretch(MoCEntityCrocodile entitycrocodile)
    {

        //        float f = 1.3F;
        float f = entitycrocodile.getEdad() * 0.01F;
        /*if(!entitycrocodile.getIsAdult())
        {
            f = entitycrocodile.edad;
        }*/
        GL11.glScalef(f, f, f);
    }

    public MoCModelCrocodile croc;

}
