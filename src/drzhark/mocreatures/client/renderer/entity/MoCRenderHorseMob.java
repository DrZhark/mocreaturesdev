package drzhark.mocreatures.client.renderer.entity;

import java.util.Random;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelNewHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;

@SideOnly(Side.CLIENT)
public class MoCRenderHorseMob extends RenderLiving {

    private Random rand = new Random();
    private int textCounter;
    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseundead.png");
    private static final ResourceLocation TEXTURE_BAT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsebat.png");
    private static final ResourceLocation TEXTURE_SKELETON = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseskeleton.png");
    private static final ResourceLocation TEXTURE_NIGHTMARE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare.png");
    private static final ResourceLocation TEXTURE_NIGHTMARE1 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare1.png");

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

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityHorseMob)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityHorseMob horsemob)
    {
        switch (horsemob.getType())
        {
            case 23://undead horse
                
                if (!MoCreatures.proxy.getAnimateTextures()) 
                {
                    return TEXTURE_DEFAULT;
                }
                String baseTex = MoCreatures.proxy.MODEL_TEXTURE + "horseundead";
                int max = 79;

                if (rand.nextInt(3)== 0) textCounter++;
                if (textCounter < 10) textCounter = 10;
                if (textCounter > max) textCounter = 10;
                
                String iteratorTex = "" + textCounter;
                iteratorTex = iteratorTex.substring(0,1);
                String decayTex = "" + (horsemob.getEdad()/100);
                decayTex = decayTex.substring(0,1);
                return new ResourceLocation(baseTex + decayTex + iteratorTex + ".png");

            case 26:
                return TEXTURE_SKELETON;
            
            case 32:
                return TEXTURE_BAT;

            case 38:
                if (!MoCreatures.proxy.getAnimateTextures()) 
                {
                    return TEXTURE_NIGHTMARE1;
                }
                textCounter++;
                if (textCounter < 10) textCounter = 10;
                if (textCounter > 59) textCounter = 10;
                String NTA = MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare";
                String NTB = "" + textCounter;
                NTB = NTB.substring(0,1);
                String NTC = ".png";

                return new ResourceLocation(NTA + NTB + NTC);

            default:
                return TEXTURE_DEFAULT;
        }
    }
}