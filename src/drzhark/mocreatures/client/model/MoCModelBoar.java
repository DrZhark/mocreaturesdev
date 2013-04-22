package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

    public MoCModelBoar()
    {
        textureWidth = 64;
        textureHeight = 64;

        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-3F, 0F, -5F, 6, 6, 5);
        Head.setRotationPoint(0F, 11F, -5F);
        setRotation(Head, 0.2617994F, 0F, 0F);

        Trout = new ModelRenderer(this, 0, 11);
        Trout.addBox(-1.5F, 1.5F, -9.5F, 3, 3, 5);
        Trout.setRotationPoint(0F, 11F, -5F);
        setRotation(Trout, 0.3490659F, 0F, 0F);

        Tusks = new ModelRenderer(this, 0, 24);
        Tusks.addBox(-2F, 3F, -8F, 4, 2, 1);
        Tusks.setRotationPoint(0F, 11F, -5F);
        setRotation(Tusks, 0.3490659F, 0F, 0F);

        Jaw = new ModelRenderer(this, 0, 19);
        Jaw.addBox(-1F, 4.9F, -8.5F, 2, 1, 4);
        Jaw.setRotationPoint(0F, 11F, -5F);
        setRotation(Jaw, 0.2617994F, 0F, 0F);

        LeftEar = new ModelRenderer(this, 16, 11);
        LeftEar.addBox(1F, -4F, -2F, 2, 4, 2);
        LeftEar.setRotationPoint(0F, 11F, -5F);
        setRotation(LeftEar, 0.6981317F, 0F, 0.3490659F);

        RightEar = new ModelRenderer(this, 16, 17);
        RightEar.addBox(-3F, -4F, -2F, 2, 4, 2);
        RightEar.setRotationPoint(0F, 11F, -5F);
        setRotation(RightEar, 0.6981317F, 0F, -0.3490659F);

        HeadMane = new ModelRenderer(this, 23, 0);
        HeadMane.addBox(-1F, -2F, -5F, 2, 2, 5);
        HeadMane.setRotationPoint(0F, 11F, -5F);
        setRotation(HeadMane, 0.4363323F, 0F, 0F);

        Body = new ModelRenderer(this, 24, 0);
        Body.addBox(-3.5F, 0F, 0F, 7, 8, 13);
        Body.setRotationPoint(0F, 11F, -5F);
        setRotation(Body, -0.0872665F, 0F, 0F);

        BodyMane = new ModelRenderer(this, 0, 27);
        BodyMane.addBox(-1F, -2F, -1F, 2, 2, 9);
        BodyMane.setRotationPoint(0F, 11.3F, -4F);
        setRotation(BodyMane, -0.2617994F, 0F, 0F);

        Tail = new ModelRenderer(this, 60, 38);
        Tail.addBox(-0.5F, 0F, 0F, 1, 5, 1);
        Tail.setRotationPoint(0F, 13F, 7.5F);
        setRotation(Tail, 0.0872665F, 0F, 0F);

        UpperLegRight = new ModelRenderer(this, 32, 21);
        UpperLegRight.addBox(-1F, -2F, -2F, 1, 5, 3);
        UpperLegRight.setRotationPoint(-3.5F, 16F, -2.5F);
        setRotation(UpperLegRight, 0.1745329F, 0F, 0F);

        LowerLegRight = new ModelRenderer(this, 32, 29);
        LowerLegRight.addBox(-0.5F, 2F, -1F, 2, 6, 2);
        LowerLegRight.setRotationPoint(-3.5F, 16F, -2.5F);

        UpperLegLeft = new ModelRenderer(this, 24, 21);
        UpperLegLeft.addBox(0F, -2F, -2F, 1, 5, 3);
        UpperLegLeft.setRotationPoint(3.5F, 16F, -2.5F);
        setRotation(UpperLegLeft, 0.1745329F, 0F, 0F);

        LowerLegLeft = new ModelRenderer(this, 24, 29);
        LowerLegLeft.addBox(-1.5F, 2F, -1F, 2, 6, 2);
        LowerLegLeft.setRotationPoint(3.5F, 16F, -2.5F);

        UpperHindLegRight = new ModelRenderer(this, 44, 21);
        UpperHindLegRight.addBox(-1.5F, -2F, -2F, 1, 5, 4);
        UpperHindLegRight.setRotationPoint(-3F, 16F, 5.5F);
        setRotation(UpperHindLegRight, -0.2617994F, 0F, 0F);

        LowerHindLegRight = new ModelRenderer(this, 46, 30);
        LowerHindLegRight.addBox(-1F, 2F, 0F, 2, 6, 2);
        LowerHindLegRight.setRotationPoint(-3F, 16F, 5.5F);

        UpperHindLegLeft = new ModelRenderer(this, 54, 21);
        UpperHindLegLeft.addBox(0.5F, -2F, -2F, 1, 5, 4);
        UpperHindLegLeft.setRotationPoint(3F, 16F, 5.5F);
        setRotation(UpperHindLegLeft, -0.2617994F, 0F, 0F);

        LowerHindLegLeft = new ModelRenderer(this, 56, 30);
        LowerHindLegLeft.addBox(-1F, 2F, 0F, 2, 6, 2);
        LowerHindLegLeft.setRotationPoint(3F, 16F, 5.5F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Head.render(f5);
        Trout.render(f5);
        Tusks.render(f5);
        Jaw.render(f5);
        LeftEar.render(f5);
        RightEar.render(f5);
        HeadMane.render(f5);
        Body.render(f5);
        BodyMane.render(f5);
        Tail.render(f5);
        UpperLegRight.render(f5);
        LowerLegRight.render(f5);
        UpperLegLeft.render(f5);
        LowerLegLeft.render(f5);
        UpperHindLegRight.render(f5);
        LowerHindLegRight.render(f5);
        UpperHindLegLeft.render(f5);
        LowerHindLegLeft.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        float XAngle = (f4 / 57.29578F);
        float YAngle = f3 / 57.29578F;
        Head.rotateAngleX = 0.2617994F + XAngle;
        Head.rotateAngleY = YAngle;
        HeadMane.rotateAngleX = 0.4363323F + XAngle;
        HeadMane.rotateAngleY = YAngle;
        Trout.rotateAngleX = 0.3490659F + XAngle;
        Trout.rotateAngleY = YAngle;
        Jaw.rotateAngleX = 0.2617994F + XAngle;
        Jaw.rotateAngleY = YAngle;
        Tusks.rotateAngleX = 0.3490659F + XAngle;
        Tusks.rotateAngleY = YAngle;
        LeftEar.rotateAngleX = 0.6981317F + XAngle;
        LeftEar.rotateAngleY = YAngle;
        RightEar.rotateAngleX = 0.6981317F + XAngle;
        RightEar.rotateAngleY = YAngle;

        float LLegRotX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float RLegRotX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;

        UpperLegLeft.rotateAngleX = LLegRotX;
        LowerLegLeft.rotateAngleX = LLegRotX;
        UpperHindLegRight.rotateAngleX = LLegRotX;
        LowerHindLegRight.rotateAngleX = LLegRotX;

        UpperLegRight.rotateAngleX = RLegRotX;
        LowerLegRight.rotateAngleX = RLegRotX;
        UpperHindLegLeft.rotateAngleX = RLegRotX;
        LowerHindLegLeft.rotateAngleX = RLegRotX;

        Tail.rotateAngleZ = LLegRotX * 0.2F;

    }

}
