package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;

@SideOnly(Side.CLIENT)
public class MoCRenderSnake extends MoCRenderMoC {

    public MoCRenderSnake(ModelBase modelbase, float f)
    {
        super(modelbase, 0.0F);
        //tempSnake = (MoCModelSnake) modelbase;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntitySnake)par1Entity).getTexture();
    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight)
    {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntitySnake entitysnake = (MoCEntitySnake) entityliving;
        stretch(entitysnake);

        /*if(mod_mocreatures.mc.isMultiplayerWorld() && (entitysnake.pickedUp()))
        {

            GL11.glTranslatef(0.0F, 1.4F, 0.0F);

        }*/

        if (entitysnake.pickedUp())// && entitysnake.getSizeF() < 0.6F)
        {
            float xOff = (entitysnake.getSizeF() - 1.0F);
            if (xOff > 0.0F)
            {
                xOff = 0.0F;
            }
            if (entitysnake.worldObj.isRemote)
            {
                GL11.glTranslatef(xOff, 0.0F, 0F);
            }
            else
            {
                GL11.glTranslatef(xOff, 0F, 0.0F);
                //-0.5 puts it in the right shoulder
            }
            /*//if(small) //works for small snakes
            GL11.glRotatef(20F, 1F, 0F, 0F); 
            if(mod_mocreatures.mc.isMultiplayerWorld())
            {
                GL11.glTranslatef(-0.5F, 1.4F, 0F);

            }
            else
            {
                GL11.glTranslatef(0.7F, 0F, 1.2F);
            }*/
        }

        if (entitysnake.isInsideOfMaterial(Material.water))
        {
            adjustHeight(entitysnake, -0.25F);
        }

        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntitySnake entitysnake)
    {
        /*        float factor = 1.0F;
                int type = entitysnake.type;
                if (type == 1 || type == 2)//small shy snakes
                {
                    factor = 0.6F;
                }else
                if (type == 5)//coral
                {
                    factor = 0.4F;
                }
                if (type == 6)//cobra 1.1
                {
                    factor = 1.2F;
                }
                if (type == 7)//rattlesnake
                {
                    factor = 0.9F;
                }
                if (type == 8)//python
                {
                    factor = 1.5F;
                }
                //float f = 1.0F*factor;
                float f = entitysnake.getEdad()*factor;*/

        float f = entitysnake.getSizeF();
        GL11.glScalef(f, f, f);
    }

    /*@Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntitySnake entitysnake = (MoCEntitySnake) entityliving;
        //tempSnake.textPos = entitysnake.type - 1;
        if (entitysnake.type <4)
        {
            tempSnake.textPos = 0;
        }else
        {
            tempSnake.textPos = 1;
        }
        super.preRenderCallback(entityliving, f);
        
    }
    
    
    
    private MoCModelSnake tempSnake;*/
}