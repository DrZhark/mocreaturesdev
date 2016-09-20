package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    private boolean bunnyHat;

    public MoCModelBunny() {
        byte byte0 = 16;
        this.part1 = new ModelRenderer(this, 0, 0);
        this.part1.addBox(-2F, -1F, -4F, 4, 4, 6, 0.0F);
        this.part1.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part8 = new ModelRenderer(this, 14, 0);
        this.part8.addBox(-2F, -5F, -3F, 1, 4, 2, 0.0F);
        this.part8.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part9 = new ModelRenderer(this, 14, 0);
        this.part9.addBox(1.0F, -5F, -3F, 1, 4, 2, 0.0F);
        this.part9.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part10 = new ModelRenderer(this, 20, 0);
        this.part10.addBox(-4F, 0.0F, -3F, 2, 3, 2, 0.0F);
        this.part10.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part11 = new ModelRenderer(this, 20, 0);
        this.part11.addBox(2.0F, 0.0F, -3F, 2, 3, 2, 0.0F);
        this.part11.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part2 = new ModelRenderer(this, 0, 10);
        this.part2.addBox(-3F, -4F, -3F, 6, 8, 6, 0.0F);
        this.part2.setRotationPoint(0.0F, 0 + byte0, 0.0F);
        this.part3 = new ModelRenderer(this, 0, 24);
        this.part3.addBox(-2F, 4F, -2F, 4, 3, 4, 0.0F);
        this.part3.setRotationPoint(0.0F, 0 + byte0, 0.0F);
        this.part4 = new ModelRenderer(this, 24, 16);
        this.part4.addBox(-2F, 0.0F, -1F, 2, 2, 2);
        this.part4.setRotationPoint(3F, 3 + byte0, -3F);
        this.part5 = new ModelRenderer(this, 24, 16);
        this.part5.addBox(0.0F, 0.0F, -1F, 2, 2, 2);
        this.part5.setRotationPoint(-3F, 3 + byte0, -3F);
        this.part6 = new ModelRenderer(this, 16, 24);
        this.part6.addBox(-2F, 0.0F, -4F, 2, 2, 4);
        this.part6.setRotationPoint(3F, 3 + byte0, 4F);
        this.part7 = new ModelRenderer(this, 16, 24);
        this.part7.addBox(0.0F, 0.0F, -4F, 2, 2, 4);
        this.part7.setRotationPoint(-3F, 3 + byte0, 4F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.bunnyHat = entity.getRidingEntity() != null;
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.part1.render(f5);
        this.part8.render(f5);
        this.part9.render(f5);
        this.part10.render(f5);
        this.part11.render(f5);
        this.part2.render(f5);
        this.part3.render(f5);
        this.part4.render(f5);
        this.part5.render(f5);
        this.part6.render(f5);
        this.part7.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.part1.rotateAngleX = -(f4 / 57.29578F);
        this.part1.rotateAngleY = f3 / 57.29578F;

        this.part8.rotateAngleX = this.part1.rotateAngleX;
        this.part8.rotateAngleY = this.part1.rotateAngleY;
        this.part9.rotateAngleX = this.part1.rotateAngleX;
        this.part9.rotateAngleY = this.part1.rotateAngleY;
        this.part10.rotateAngleX = this.part1.rotateAngleX;
        this.part10.rotateAngleY = this.part1.rotateAngleY;
        this.part11.rotateAngleX = this.part1.rotateAngleX;
        this.part11.rotateAngleY = this.part1.rotateAngleY;
        this.part2.rotateAngleX = 1.570796F;
        this.part3.rotateAngleX = 1.570796F;
        if (!this.bunnyHat) {
            this.part4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
            this.part6.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
            this.part5.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
            this.part7.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
        }

    }
}
