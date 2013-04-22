package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelBunny extends ModelBase {

    public ModelRenderer part1;
    public ModelRenderer part2;
    public ModelRenderer part3;
    public ModelRenderer part4;
    public ModelRenderer part5;
    public ModelRenderer part6;
    public ModelRenderer part7;
    public ModelRenderer part8;
    public ModelRenderer part9;
    public ModelRenderer part10;
    public ModelRenderer part11;

    public MoCModelBunny()
    {
        byte byte0 = 16;
        part1 = new ModelRenderer(this, 0, 0);
        part1.addBox(-2F, -1F, -4F, 4, 4, 6, 0.0F);
        part1.setRotationPoint(0.0F, -1 + byte0, -4F);
        part8 = new ModelRenderer(this, 14, 0);
        part8.addBox(-2F, -5F, -3F, 1, 4, 2, 0.0F);
        part8.setRotationPoint(0.0F, -1 + byte0, -4F);
        part9 = new ModelRenderer(this, 14, 0);
        part9.addBox(1.0F, -5F, -3F, 1, 4, 2, 0.0F);
        part9.setRotationPoint(0.0F, -1 + byte0, -4F);
        part10 = new ModelRenderer(this, 20, 0);
        part10.addBox(-4F, 0.0F, -3F, 2, 3, 2, 0.0F);
        part10.setRotationPoint(0.0F, -1 + byte0, -4F);
        part11 = new ModelRenderer(this, 20, 0);
        part11.addBox(2.0F, 0.0F, -3F, 2, 3, 2, 0.0F);
        part11.setRotationPoint(0.0F, -1 + byte0, -4F);
        part2 = new ModelRenderer(this, 0, 10);
        part2.addBox(-3F, -4F, -3F, 6, 8, 6, 0.0F);
        part2.setRotationPoint(0.0F, 0 + byte0, 0.0F);
        part3 = new ModelRenderer(this, 0, 24);
        part3.addBox(-2F, 4F, -2F, 4, 3, 4, 0.0F);
        part3.setRotationPoint(0.0F, 0 + byte0, 0.0F);
        part4 = new ModelRenderer(this, 24, 16);
        part4.addBox(-2F, 0.0F, -1F, 2, 2, 2);
        part4.setRotationPoint(3F, 3 + byte0, -3F);
        part5 = new ModelRenderer(this, 24, 16);
        part5.addBox(0.0F, 0.0F, -1F, 2, 2, 2);
        part5.setRotationPoint(-3F, 3 + byte0, -3F);
        part6 = new ModelRenderer(this, 16, 24);
        part6.addBox(-2F, 0.0F, -4F, 2, 2, 4);
        part6.setRotationPoint(3F, 3 + byte0, 4F);
        part7 = new ModelRenderer(this, 16, 24);
        part7.addBox(0.0F, 0.0F, -4F, 2, 2, 4);
        part7.setRotationPoint(-3F, 3 + byte0, 4F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        part1.render(f5);
        part8.render(f5);
        part9.render(f5);
        part10.render(f5);
        part11.render(f5);
        part2.render(f5);
        part3.render(f5);
        part4.render(f5);
        part5.render(f5);
        part6.render(f5);
        part7.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        part1.rotateAngleX = -(f4 / 57.29578F);
        part1.rotateAngleY = f3 / 57.29578F;
        part8.rotateAngleX = part1.rotateAngleX;
        part8.rotateAngleY = part1.rotateAngleY;
        part9.rotateAngleX = part1.rotateAngleX;
        part9.rotateAngleY = part1.rotateAngleY;
        part10.rotateAngleX = part1.rotateAngleX;
        part10.rotateAngleY = part1.rotateAngleY;
        part11.rotateAngleX = part1.rotateAngleX;
        part11.rotateAngleY = part1.rotateAngleY;
        part2.rotateAngleX = 1.570796F;
        part3.rotateAngleX = 1.570796F;
        part4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
        part6.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
        part5.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
        part7.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
    }
}
