package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MoCModelAnt extends ModelBase {

    ModelRenderer Head;
    ModelRenderer Mouth;
    ModelRenderer RightAntenna;
    ModelRenderer LeftAntenna;
    ModelRenderer Thorax;
    ModelRenderer Abdomen;
    ModelRenderer MidLegs;
    ModelRenderer FrontLegs;
    ModelRenderer RearLegs;

    public MoCModelAnt() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 11);
        this.Head.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Head.setRotationPoint(0F, 21.9F, -1.3F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 8, 10);
        this.Mouth.addBox(0F, 0F, 0F, 2, 1, 0);
        this.Mouth.setRotationPoint(-1F, 22.3F, -1.9F);
        setRotation(this.Mouth, -0.8286699F, 0F, 0F);

        this.RightAntenna = new ModelRenderer(this, 0, 6);
        this.RightAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.RightAntenna.setRotationPoint(-0.5F, 21.7F, -2.3F);
        setRotation(this.RightAntenna, -1.041001F, 0.7853982F, 0F);

        this.LeftAntenna = new ModelRenderer(this, 4, 6);
        this.LeftAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.LeftAntenna.setRotationPoint(0.5F, 21.7F, -2.3F);
        setRotation(this.LeftAntenna, -1.041001F, -0.7853982F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-0.5F, 1.5F, -1F, 1, 1, 2);
        this.Thorax.setRotationPoint(0F, 20F, -0.5F);

        this.Abdomen = new ModelRenderer(this, 8, 1);
        this.Abdomen.addBox(-0.5F, -0.2F, -1F, 1, 2, 1);
        this.Abdomen.setRotationPoint(0F, 21.5F, 0.3F);
        setRotation(this.Abdomen, 1.706911F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 4, 8);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.MidLegs.setRotationPoint(0F, 22F, -0.7F);
        setRotation(this.MidLegs, 0.5948578F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 8);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.FrontLegs.setRotationPoint(0F, 22F, -0.8F);
        setRotation(this.FrontLegs, -0.6192304F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 0, 8);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.RearLegs.setRotationPoint(0F, 22F, 0F);
        setRotation(this.RearLegs, 0.9136644F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Head.render(f5);
        this.Mouth.render(f5);
        this.RightAntenna.render(f5);
        this.LeftAntenna.render(f5);
        this.Thorax.render(f5);
        this.Abdomen.render(f5);
        this.MidLegs.render(f5);
        this.FrontLegs.render(f5);
        this.RearLegs.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float legMov = MathHelper.cos((f) + 3.141593F) * f1;
        float legMovB = MathHelper.cos(f) * f1;
        this.FrontLegs.rotateAngleX = -0.6192304F + legMov;
        this.MidLegs.rotateAngleX = 0.5948578F + legMovB;
        this.RearLegs.rotateAngleX = 0.9136644F + legMov;
    }

}
