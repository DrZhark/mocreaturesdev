package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelEgg extends ModelBase {

    public MoCModelEgg()
    {
        //textureWidth = 64;
        //textureHeight = 32;

        Egg1 = new ModelRenderer(this, 0, 0);
        Egg1.addBox(0F, 0F, 0F, 3, 3, 3);
        Egg1.setRotationPoint(0F, 20F, 0F);

        Egg2 = new ModelRenderer(this, 10, 0);
        Egg2.addBox(0F, 0F, 0F, 2, 1, 2);
        Egg2.setRotationPoint(0.5F, 19.5F, 0.5F);

        Egg3 = new ModelRenderer(this, 30, 0);
        Egg3.addBox(0F, 0F, 0F, 2, 1, 2);
        Egg3.setRotationPoint(0.5F, 22.5F, 0.5F);

        Egg4 = new ModelRenderer(this, 24, 0);
        Egg4.addBox(0F, 0F, 0F, 1, 2, 2);
        Egg4.setRotationPoint(-0.5F, 20.5F, 0.5F);

        Egg5 = new ModelRenderer(this, 18, 0);
        Egg5.addBox(0F, 0F, 0F, 1, 2, 2);
        Egg5.setRotationPoint(2.5F, 20.5F, 0.5F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Egg1.render(f5);
        Egg2.render(f5);
        Egg3.render(f5);
        Egg4.render(f5);
        Egg5.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }

    public ModelRenderer Egg;
    ModelRenderer Egg1;
    ModelRenderer Egg2;
    ModelRenderer Egg3;
    ModelRenderer Egg4;
    ModelRenderer Egg5;
}
