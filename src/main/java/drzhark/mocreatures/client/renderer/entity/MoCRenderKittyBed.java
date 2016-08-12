package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelKittyBed;
import drzhark.mocreatures.client.model.MoCModelKittyBed2;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderKittyBed extends RenderLiving {

    public MoCModelKittyBed kittybed;
    private int mycolor;
    public static float fleeceColorTable[][] = { {1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F},
            {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F},
            {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};

    public MoCRenderKittyBed(MoCModelKittyBed modelkittybed, MoCModelKittyBed2 modelkittybed2, float f) {
        super(MoCClientProxy.mc.getRenderManager(), modelkittybed, f);
        this.kittybed = modelkittybed;
        this.addLayer(new LayerMoCKittyBed(this));
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) entityliving;
        this.mycolor = entitykittybed.getSheetColor();
        this.kittybed.hasMilk = entitykittybed.getHasMilk();
        this.kittybed.hasFood = entitykittybed.getHasFood();
        this.kittybed.pickedUp = entitykittybed.getPickedUp();
        this.kittybed.milklevel = entitykittybed.milklevel;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityKittyBed) par1Entity).getTexture();
    }

    private class LayerMoCKittyBed implements LayerRenderer {

        private final MoCRenderKittyBed mocRenderer;
        private final MoCModelKittyBed2 mocModel = new MoCModelKittyBed2();

        public LayerMoCKittyBed(MoCRenderKittyBed render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityKittyBed entity, float f, float f1, float f2, float f3, float f4, float f5, float f6) {

            //bindTexture(MoCreatures.proxy.getTexture("kittybed.png"));
            float f8 = 0.35F;
            int j = MoCTools.colorize(this.mocRenderer.mycolor);
            GL11.glColor3f(f8 * fleeceColorTable[j][0], f8 * fleeceColorTable[j][1], f8 * fleeceColorTable[j][2]);
            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entity, f, f1, f2);
            this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }

        @Override
        public void doRenderLayer(EntityLivingBase entity, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
            this.doRenderLayer((MoCEntityKittyBed) entity, f1, f2, f3, f4, f5, f6, f7);
        }
    }
}
