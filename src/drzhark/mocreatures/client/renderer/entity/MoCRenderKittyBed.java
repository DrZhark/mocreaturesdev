package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelKittyBed;
import drzhark.mocreatures.client.model.MoCModelKittyBed2;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;

@SideOnly(Side.CLIENT)
public class MoCRenderKittyBed extends RenderLiving {

    public MoCModelKittyBed kittybed;
    private int mycolor;
    public static float fleeceColorTable[][] = { { 1.0F, 1.0F, 1.0F }, { 0.95F, 0.7F, 0.2F }, { 0.9F, 0.5F, 0.85F }, { 0.6F, 0.7F, 0.95F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.7F, 0.8F }, { 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.6F, 0.7F }, { 0.7F, 0.4F, 0.9F }, { 0.2F, 0.4F, 0.8F }, { 0.5F, 0.4F, 0.3F }, { 0.4F, 0.5F, 0.2F }, { 0.8F, 0.3F, 0.3F }, { 0.1F, 0.1F, 0.1F } };

    public MoCRenderKittyBed(MoCModelKittyBed modelkittybed, MoCModelKittyBed2 modelkittybed2, float f)
    {
        super(modelkittybed, f);
        kittybed = modelkittybed;
        setRenderPassModel(modelkittybed2);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) entityliving;
        mycolor = entitykittybed.getSheetColor();
        kittybed.hasMilk = entitykittybed.getHasMilk();
        kittybed.hasFood = entitykittybed.getHasFood();
        kittybed.pickedUp = entitykittybed.getPickedUp();
        kittybed.milklevel = entitykittybed.milklevel;
    }

    protected int shouldRenderPass(MoCEntityKittyBed entityliving, int i)
    {
        this.bindTexture(MoCreatures.proxy.getTexture("kittybed.png"));
        float f1 = 0.35F;
        int j = MoCTools.colorize(mycolor);
        GL11.glColor3f(f1 * fleeceColorTable[j][0], f1 * fleeceColorTable[j][1], f1 * fleeceColorTable[j][2]);
        return 1;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f)
    {
        return shouldRenderPass((MoCEntityKittyBed)entityliving, i);
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityKittyBed)par1Entity).getTexture();
    }
}
