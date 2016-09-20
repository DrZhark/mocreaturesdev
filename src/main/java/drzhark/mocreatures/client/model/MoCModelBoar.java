package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelBoar extends ModelBase {

    ModelRenderer Head;
    ModelRenderer Trout;
    ModelRenderer Tusks;
    ModelRenderer Jaw;
    ModelRenderer LeftEar;
    ModelRenderer RightEar;
    ModelRenderer HeadMane;
    ModelRenderer Body;
    ModelRenderer BodyMane;
    ModelRenderer Tail;
    ModelRenderer UpperLegRight;
    ModelRenderer LowerLegRight;
    ModelRenderer UpperLegLeft;
    ModelRenderer LowerLegLeft;
    ModelRenderer UpperHindLegRight;
    ModelRenderer LowerHindLegRight;
    ModelRenderer UpperHindLegLeft;
    ModelRenderer LowerHindLegLeft;

    public MoCModelBoar() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-3F, 0F, -5F, 6, 6, 5);
        this.Head.setRotationPoint(0F, 11F, -5F);
        setRotation(this.Head, 0.2617994F, 0F, 0F);

        this.Trout = new ModelRenderer(this, 0, 11);
        this.Trout.addBox(-1.5F, 1.5F, -9.5F, 3, 3, 5);
        this.Trout.setRotationPoint(0F, 11F, -5F);
        setRotation(this.Trout, 0.3490659F, 0F, 0F);

        this.Tusks = new ModelRenderer(this, 0, 24);
        this.Tusks.addBox(-2F, 3F, -8F, 4, 2, 1);
        this.Tusks.setRotationPoint(0F, 11F, -5F);
        setRotation(this.Tusks, 0.3490659F, 0F, 0F);

        this.Jaw = new ModelRenderer(this, 0, 19);
        this.Jaw.addBox(-1F, 4.9F, -8.5F, 2, 1, 4);
        this.Jaw.setRotationPoint(0F, 11F, -5F);
        setRotation(this.Jaw, 0.2617994F, 0F, 0F);

        this.LeftEar = new ModelRenderer(this, 16, 11);
        this.LeftEar.addBox(1F, -4F, -2F, 2, 4, 2);
        this.LeftEar.setRotationPoint(0F, 11F, -5F);
        setRotation(this.LeftEar, 0.6981317F, 0F, 0.3490659F);

        this.RightEar = new ModelRenderer(this, 16, 17);
        this.RightEar.addBox(-3F, -4F, -2F, 2, 4, 2);
        this.RightEar.setRotationPoint(0F, 11F, -5F);
        setRotation(this.RightEar, 0.6981317F, 0F, -0.3490659F);

        this.HeadMane = new ModelRenderer(this, 23, 0);
        this.HeadMane.addBox(-1F, -2F, -5F, 2, 2, 5);
        this.HeadMane.setRotationPoint(0F, 11F, -5F);
        setRotation(this.HeadMane, 0.4363323F, 0F, 0F);

        this.Body = new ModelRenderer(this, 24, 0);
        this.Body.addBox(-3.5F, 0F, 0F, 7, 8, 13);
        this.Body.setRotationPoint(0F, 11F, -5F);
        setRotation(this.Body, -0.0872665F, 0F, 0F);

        this.BodyMane = new ModelRenderer(this, 0, 27);
        this.BodyMane.addBox(-1F, -2F, -1F, 2, 2, 9);
        this.BodyMane.setRotationPoint(0F, 11.3F, -4F);
        setRotation(this.BodyMane, -0.2617994F, 0F, 0F);

        this.Tail = new ModelRenderer(this, 60, 38);
        this.Tail.addBox(-0.5F, 0F, 0F, 1, 5, 1);
        this.Tail.setRotationPoint(0F, 13F, 7.5F);
        setRotation(this.Tail, 0.0872665F, 0F, 0F);

        this.UpperLegRight = new ModelRenderer(this, 32, 21);
        this.UpperLegRight.addBox(-1F, -2F, -2F, 1, 5, 3);
        this.UpperLegRight.setRotationPoint(-3.5F, 16F, -2.5F);
        setRotation(this.UpperLegRight, 0.1745329F, 0F, 0F);

        this.LowerLegRight = new ModelRenderer(this, 32, 29);
        this.LowerLegRight.addBox(-0.5F, 2F, -1F, 2, 6, 2);
        this.LowerLegRight.setRotationPoint(-3.5F, 16F, -2.5F);

        this.UpperLegLeft = new ModelRenderer(this, 24, 21);
        this.UpperLegLeft.addBox(0F, -2F, -2F, 1, 5, 3);
        this.UpperLegLeft.setRotationPoint(3.5F, 16F, -2.5F);
        setRotation(this.UpperLegLeft, 0.1745329F, 0F, 0F);

        this.LowerLegLeft = new ModelRenderer(this, 24, 29);
        this.LowerLegLeft.addBox(-1.5F, 2F, -1F, 2, 6, 2);
        this.LowerLegLeft.setRotationPoint(3.5F, 16F, -2.5F);

        this.UpperHindLegRight = new ModelRenderer(this, 44, 21);
        this.UpperHindLegRight.addBox(-1.5F, -2F, -2F, 1, 5, 4);
        this.UpperHindLegRight.setRotationPoint(-3F, 16F, 5.5F);
        setRotation(this.UpperHindLegRight, -0.2617994F, 0F, 0F);

        this.LowerHindLegRight = new ModelRenderer(this, 46, 30);
        this.LowerHindLegRight.addBox(-1F, 2F, 0F, 2, 6, 2);
        this.LowerHindLegRight.setRotationPoint(-3F, 16F, 5.5F);

        this.UpperHindLegLeft = new ModelRenderer(this, 54, 21);
        this.UpperHindLegLeft.addBox(0.5F, -2F, -2F, 1, 5, 4);
        this.UpperHindLegLeft.setRotationPoint(3F, 16F, 5.5F);
        setRotation(this.UpperHindLegLeft, -0.2617994F, 0F, 0F);

        this.LowerHindLegLeft = new ModelRenderer(this, 56, 30);
        this.LowerHindLegLeft.addBox(-1F, 2F, 0F, 2, 6, 2);
        this.LowerHindLegLeft.setRotationPoint(3F, 16F, 5.5F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Head.render(f5);
        this.Trout.render(f5);
        this.Tusks.render(f5);
        this.Jaw.render(f5);
        this.LeftEar.render(f5);
        this.RightEar.render(f5);
        this.HeadMane.render(f5);
        this.Body.render(f5);
        this.BodyMane.render(f5);
        this.Tail.render(f5);
        this.UpperLegRight.render(f5);
        this.LowerLegRight.render(f5);
        this.UpperLegLeft.render(f5);
        this.LowerLegLeft.render(f5);
        this.UpperHindLegRight.render(f5);
        this.LowerHindLegRight.render(f5);
        this.UpperHindLegLeft.render(f5);
        this.LowerHindLegLeft.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float XAngle = (f4 / 57.29578F);
        float YAngle = f3 / 57.29578F;
        this.Head.rotateAngleX = 0.2617994F + XAngle;
        this.Head.rotateAngleY = YAngle;
        this.HeadMane.rotateAngleX = 0.4363323F + XAngle;
        this.HeadMane.rotateAngleY = YAngle;
        this.Trout.rotateAngleX = 0.3490659F + XAngle;
        this.Trout.rotateAngleY = YAngle;
        this.Jaw.rotateAngleX = 0.2617994F + XAngle;
        this.Jaw.rotateAngleY = YAngle;
        this.Tusks.rotateAngleX = 0.3490659F + XAngle;
        this.Tusks.rotateAngleY = YAngle;
        this.LeftEar.rotateAngleX = 0.6981317F + XAngle;
        this.LeftEar.rotateAngleY = YAngle;
        this.RightEar.rotateAngleX = 0.6981317F + XAngle;
        this.RightEar.rotateAngleY = YAngle;

        float LLegRotX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float RLegRotX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;

        this.UpperLegLeft.rotateAngleX = LLegRotX;
        this.LowerLegLeft.rotateAngleX = LLegRotX;
        this.UpperHindLegRight.rotateAngleX = LLegRotX;
        this.LowerHindLegRight.rotateAngleX = LLegRotX;

        this.UpperLegRight.rotateAngleX = RLegRotX;
        this.LowerLegRight.rotateAngleX = RLegRotX;
        this.UpperHindLegLeft.rotateAngleX = RLegRotX;
        this.LowerHindLegLeft.rotateAngleX = RLegRotX;

        this.Tail.rotateAngleZ = LLegRotX * 0.2F;

    }

}
