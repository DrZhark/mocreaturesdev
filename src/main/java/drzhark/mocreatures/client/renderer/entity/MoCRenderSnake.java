package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderSnake extends MoCRenderMoC<MoCEntitySnake> {

    public MoCRenderSnake(ModelBase modelbase, float f) {
        super(modelbase, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntitySnake par1Entity) {
        return ((MoCEntitySnake) par1Entity).getTexture();
    }

    protected void adjustHeight(MoCEntitySnake entitysnake, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntitySnake entitysnake, float f) {
        stretch(entitysnake);

        /*
         * if(mod_mocreatures.mc.isMultiplayerWorld() &&
         * (entitysnake.pickedUp())) { GL11.glTranslatef(0.0F, 1.4F, 0.0F); }
         */

        if (entitysnake.pickedUp())// && entitysnake.getSizeF() < 0.6F)
        {
            float xOff = (entitysnake.getSizeF() - 1.0F);
            if (xOff > 0.0F) {
                xOff = 0.0F;
            }
            if (entitysnake.worldObj.isRemote) {
                GL11.glTranslatef(xOff, 0.0F, 0F);
            } else {
                GL11.glTranslatef(xOff, 0F, 0.0F);
                //-0.5 puts it in the right shoulder
            }
            /*
             * //if(small) //works for small snakes GL11.glRotatef(20F, 1F, 0F,
             * 0F); if(mod_mocreatures.mc.isMultiplayerWorld()) {
             * GL11.glTranslatef(-0.5F, 1.4F, 0F); } else {
             * GL11.glTranslatef(0.7F, 0F, 1.2F); }
             */
        }

        if (entitysnake.isInsideOfMaterial(Material.water)) {
            adjustHeight(entitysnake, -0.25F);
        }

        super.preRenderCallback(entitysnake, f);
    }

    protected void stretch(MoCEntitySnake entitysnake) {
        float f = entitysnake.getSizeF();
        GL11.glScalef(f, f, f);
    }

    /*
     * @Override protected void preRenderCallback(EntityLiving entityliving,
     * float f) { MoCEntitySnake entitysnake = (MoCEntitySnake) entityliving;
     * //tempSnake.textPos = entitysnake.type - 1; if (entitysnake.type <4) {
     * tempSnake.textPos = 0; }else { tempSnake.textPos = 1; }
     * super.preRenderCallback(entityliving, f); } private MoCModelSnake
     * tempSnake;
     */
}
