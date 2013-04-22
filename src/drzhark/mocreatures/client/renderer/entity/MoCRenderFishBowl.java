package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelFishBowl;
import drzhark.mocreatures.entity.item.MoCEntityFishBowl;

@SideOnly(Side.CLIENT)
public class MoCRenderFishBowl extends RenderLiving {

    //public MoCModelFishy fish;

    /* public MoCRenderFishBowl(MoCModelFishBowl modelfishbowl, MoCModelFishy modelfishy, float f)
     {
         super(modelfishbowl, f);
         setRenderPassModel(modelfishy);
         fish = modelfishy;
        
     }*/

    public MoCRenderFishBowl(MoCModelFishBowl modelfishbowl, float f)
    {
        super(modelfishbowl, f);

    }

    /*protected int setWoolColorAndRender(MoCEntityFishBowl entityfishbowl, int i)
    {
        //TODO check int of fish and select texture accordingly
        loadTexture(MoCreatures.proxy.MODEL_TEXTURE + " fishy1.png");
        return 1;//(i == 0);// && !entitywwolf.wolfboolean;
    }

    @Override
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return setWoolColorAndRender((MoCEntityFishBowl) entityliving, i);
    }
    */

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        MoCEntityFishBowl entityfishbowl = (MoCEntityFishBowl) entityliving;
        //mycolor = entitykittybed.getSheetColor();
        //fishbowl.pickedUp = entityfishbowl.getPickedUp();
        if (MoCreatures.isServer() && (entityliving.ridingEntity == MoCClientProxy.mc.thePlayer))
        {

            GL11.glTranslatef(0.0F, 1.1F, 0.0F);

        }
    }

}
