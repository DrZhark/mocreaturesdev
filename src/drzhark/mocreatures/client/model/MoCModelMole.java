package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MoCModelMole extends ModelBase
{

    ModelRenderer Nose;
    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Back;
    ModelRenderer Tail;
    ModelRenderer LLeg;
    ModelRenderer LFingers;
    ModelRenderer RLeg;
    ModelRenderer RFingers;
    ModelRenderer LRearLeg;
    ModelRenderer RRearLeg;

    public MoCModelMole()
    {
        textureWidth = 64;
        textureHeight = 32;

        Nose = new ModelRenderer(this, 0, 25);
        Nose.addBox(-1F, 0F, -4F, 2, 2, 3);
        Nose.setRotationPoint(0F, 20F, -6F);
        setRotation(Nose, 0.2617994F, 0F, 0F);

        Head = new ModelRenderer(this, 0, 18);
        Head.addBox(-3F, -2F, -2F, 6, 4, 3);
        Head.setRotationPoint(0F, 20F, -6F);

        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(-5F, 0F, 0F, 10, 6, 10);
        Body.setRotationPoint(0F, 17F, -6F);

        Back = new ModelRenderer(this, 18, 16);
        Back.addBox(-4F, -3F, 0F, 8, 5, 4);
        Back.setRotationPoint(0F, 21F, 4F);

        Tail = new ModelRenderer(this, 52, 8);
        Tail.addBox(-0.5F, 0F, 1F, 1, 1, 5);
        Tail.setRotationPoint(0F, 21F, 6F);
        setRotation(Tail, -0.3490659F, 0F, 0F);

        LLeg = new ModelRenderer(this, 10, 25);
        LLeg.addBox(0F, -2F, -1F, 6, 4, 2);
        LLeg.setRotationPoint(4F, 21F, -4F);
        setRotation(LLeg, 0F, 0F, 0.2268928F);

        LFingers = new ModelRenderer(this, 44, 8);
        LFingers.addBox(5F, -2F, 1F, 1, 4, 1);
        LFingers.setRotationPoint(4F, 21F, -4F);
        setRotation(LFingers, 0F, 0F, 0.2268928F);

        RLeg = new ModelRenderer(this, 26, 25);
        RLeg.addBox(-6F, -2F, -1F, 6, 4, 2);
        RLeg.setRotationPoint(-4F, 21F, -4F);
        setRotation(RLeg, 0F, 0F, -0.2268928F);

        RFingers = new ModelRenderer(this, 48, 8);
        RFingers.addBox(-6F, -2F, 1F, 1, 4, 1);
        RFingers.setRotationPoint(-4F, 21F, -4F);
        setRotation(RFingers, 0F, 0F, -0.2268928F);

        LRearLeg = new ModelRenderer(this, 36, 0);
        LRearLeg.addBox(0F, -2F, -1F, 2, 3, 5);
        LRearLeg.setRotationPoint(3F, 22F, 5F);
        setRotation(LRearLeg, -0.2792527F, 0.5235988F, 0F);

        RRearLeg = new ModelRenderer(this, 50, 0);
        RRearLeg.addBox(-2F, -2F, -1F, 2, 3, 5);
        RRearLeg.setRotationPoint(-3F, 22F, 5F);
        setRotation(RRearLeg, -0.2792527F, -0.5235988F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Nose.render(f5);
        Head.render(f5);
        Body.render(f5);
        Back.render(f5);
        Tail.render(f5);
        LLeg.render(f5);
        LFingers.render(f5);
        RLeg.render(f5);
        RFingers.render(f5);
        LRearLeg.render(f5);
        RRearLeg.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);

        Head.rotateAngleY = f3 / 57.29578F;
        Head.rotateAngleX = f4 / 57.29578F;
        Nose.rotateAngleX = 0.2617994F + Head.rotateAngleX;
        Nose.rotateAngleY = Head.rotateAngleY;

        float RLegXRot = MathHelper.cos((f * 1.0F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 1.0F) * 0.8F * f1;

        RLeg.rotateAngleY = RLegXRot;
        RFingers.rotateAngleY = RLeg.rotateAngleY;
        LLeg.rotateAngleY = LLegXRot;
        LFingers.rotateAngleY = LLeg.rotateAngleY;
        RRearLeg.rotateAngleY = -0.5235988F + LLegXRot;
        LRearLeg.rotateAngleY = 0.5235988F + RLegXRot;

        Tail.rotateAngleZ = LLeg.rotateAngleX * 0.625F;
    }

}
