package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelScorpion extends ModelBase {

    ModelRenderer Head;
    ModelRenderer MouthL;
    ModelRenderer MouthR;
    ModelRenderer Body;
    ModelRenderer Tail1;
    ModelRenderer Tail2;
    ModelRenderer Tail3;
    ModelRenderer Tail4;
    ModelRenderer Tail5;
    ModelRenderer Sting1;
    ModelRenderer Sting2;
    ModelRenderer LArm1;
    ModelRenderer LArm2;
    ModelRenderer LArm3;
    ModelRenderer LArm4;
    ModelRenderer RArm1;
    ModelRenderer RArm2;
    ModelRenderer RArm3;
    ModelRenderer RArm4;
    ModelRenderer Leg1A;
    ModelRenderer Leg1B;
    ModelRenderer Leg1C;
    ModelRenderer Leg2A;
    ModelRenderer Leg2B;
    ModelRenderer Leg2C;
    ModelRenderer Leg3A;
    ModelRenderer Leg3B;
    ModelRenderer Leg3C;
    ModelRenderer Leg4A;
    ModelRenderer Leg4B;
    ModelRenderer Leg4C;
    ModelRenderer Leg5A;
    ModelRenderer Leg5B;
    ModelRenderer Leg5C;
    ModelRenderer Leg6A;
    ModelRenderer Leg6B;
    ModelRenderer Leg6C;
    ModelRenderer Leg7A;
    ModelRenderer Leg7B;
    ModelRenderer Leg7C;
    ModelRenderer Leg8A;
    ModelRenderer Leg8B;
    ModelRenderer Leg8C;
    ModelRenderer baby1;
    ModelRenderer baby2;
    ModelRenderer baby3;
    ModelRenderer baby4;
    ModelRenderer baby5;
    protected boolean poisoning;
    protected boolean isTalking;
    protected boolean babies;
    protected int attacking;
    protected boolean sitting;

    float radianF = 57.29578F;

    public MoCModelScorpion() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-5F, 0F, 0F, 10, 5, 13);
        this.Head.setRotationPoint(0F, 14F, -9F);

        this.MouthL = new ModelRenderer(this, 18, 58);
        this.MouthL.addBox(-3F, -2F, -1F, 4, 4, 2);
        this.MouthL.setRotationPoint(3F, 17F, -9F);
        setRotation(this.MouthL, 0F, -0.3839724F, 0F);

        this.MouthR = new ModelRenderer(this, 30, 58);
        this.MouthR.addBox(-1F, -2F, -1F, 4, 4, 2);
        this.MouthR.setRotationPoint(-3F, 17F, -9F);
        setRotation(this.MouthR, 0F, 0.3839724F, 0F);

        this.Body = new ModelRenderer(this, 0, 18);
        this.Body.addBox(-4F, -2F, 0F, 8, 4, 10);
        this.Body.setRotationPoint(0F, 17F, 3F);
        setRotation(this.Body, 0.0872665F, 0F, 0F);

        this.Tail1 = new ModelRenderer(this, 0, 32);
        this.Tail1.addBox(-3F, -2F, 0F, 6, 4, 6);
        this.Tail1.setRotationPoint(0F, 16F, 12F);
        setRotation(this.Tail1, 0.6108652F, 0F, 0F);

        this.Tail2 = new ModelRenderer(this, 0, 42);
        this.Tail2.addBox(-2F, -2F, 0F, 4, 4, 6);
        this.Tail2.setRotationPoint(0F, 13F, 16.5F);
        setRotation(this.Tail2, 1.134464F, 0F, 0F);

        this.Tail3 = new ModelRenderer(this, 0, 52);
        this.Tail3.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        this.Tail3.setRotationPoint(0F, 8F, 18.5F);
        setRotation(this.Tail3, 1.692143F, 0F, 0F);

        this.Tail4 = new ModelRenderer(this, 24, 32);
        this.Tail4.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        this.Tail4.setRotationPoint(0F, 3F, 18F);
        setRotation(this.Tail4, 2.510073F, 0F, 0F);

        this.Tail5 = new ModelRenderer(this, 24, 41);
        this.Tail5.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        this.Tail5.setRotationPoint(0F, -0.2F, 14F);
        setRotation(this.Tail5, 3.067752F, 0F, 0F);

        this.Sting1 = new ModelRenderer(this, 30, 50);
        this.Sting1.addBox(-1.5F, 0F, -1.5F, 3, 5, 3);
        this.Sting1.setRotationPoint(0F, -1F, 7F);
        setRotation(this.Sting1, 0.4089647F, 0F, 0F);

        this.Sting2 = new ModelRenderer(this, 26, 50);
        this.Sting2.addBox(-0.5F, 0F, 0.5F, 1, 4, 1);
        this.Sting2.setRotationPoint(0F, 2.6F, 8.8F);
        setRotation(this.Sting2, -0.2230717F, 0F, 0F);

        this.LArm1 = new ModelRenderer(this, 26, 18);
        this.LArm1.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.LArm1.setRotationPoint(5F, 18F, -8F);
        setRotation(this.LArm1, -0.3490659F, 0F, 0.8726646F);

        this.LArm2 = new ModelRenderer(this, 42, 55);
        this.LArm2.addBox(-1.5F, -1.5F, -6F, 3, 3, 6);
        this.LArm2.setRotationPoint(10F, 14F, -6F);
        setRotation(this.LArm2, 0.1745329F, -0.3490659F, -0.2617994F);

        this.LArm3 = new ModelRenderer(this, 42, 39);
        this.LArm3.addBox(-0.5F, -0.5F, -7F, 2, 1, 7);
        this.LArm3.setRotationPoint(12F, 15F, -11F);
        setRotation(this.LArm3, 0.2617994F, 0.1570796F, -0.1570796F);

        this.LArm4 = new ModelRenderer(this, 42, 31);
        this.LArm4.addBox(-1.5F, -0.5F, -6F, 1, 1, 7);
        this.LArm4.setRotationPoint(11F, 15F, -11F);
        setRotation(this.LArm4, 0.2617994F, 0F, -0.1570796F);

        this.RArm1 = new ModelRenderer(this, 0, 18);
        this.RArm1.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.RArm1.setRotationPoint(-5F, 18F, -8F);
        setRotation(this.RArm1, -0.3490659F, 0F, -0.8726646F);

        this.RArm2 = new ModelRenderer(this, 42, 55);
        this.RArm2.addBox(-1.5F, -1.5F, -6F, 3, 3, 6);
        this.RArm2.setRotationPoint(-10F, 14F, -6F);
        setRotation(this.RArm2, 0.1745329F, 0.3490659F, 0.2617994F);

        this.RArm3 = new ModelRenderer(this, 42, 47);
        this.RArm3.addBox(-1.5F, -0.5F, -7F, 2, 1, 7);
        this.RArm3.setRotationPoint(-12F, 15F, -11F);
        setRotation(this.RArm3, 0.2617994F, -0.1570796F, 0.1570796F);

        this.RArm4 = new ModelRenderer(this, 42, 31);
        this.RArm4.addBox(0.5F, -0.5F, -6F, 1, 1, 7);
        this.RArm4.setRotationPoint(-11F, 15F, -11F);
        setRotation(this.RArm4, 0.2617994F, 0F, 0.1570796F);

        this.Leg1A = new ModelRenderer(this, 38, 0);
        this.Leg1A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg1A.setRotationPoint(5F, 18F, -5F);
        setRotationG(this.Leg1A, -10F, 0F, 75F);

        this.Leg1B = new ModelRenderer(this, 50, 0);
        this.Leg1B.addBox(2F, -8F, -1F, 5, 2, 2);
        this.Leg1B.setRotationPoint(5F, 18F, -5F);
        setRotationG(this.Leg1B, -10F, 0F, 60F);

        this.Leg1C = new ModelRenderer(this, 52, 16);
        this.Leg1C.addBox(4.5F, -9F, -0.7F, 5, 1, 1);
        this.Leg1C.setRotationPoint(5F, 18F, -5F);
        setRotationG(this.Leg1C, -10F, 0F, 75F);

        this.Leg2A = new ModelRenderer(this, 38, 0);
        this.Leg2A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg2A.setRotationPoint(5F, 18F, -2F);
        setRotationG(this.Leg2A, -30F, 0F, 70F);

        this.Leg2B = new ModelRenderer(this, 50, 4);
        this.Leg2B.addBox(1F, -8F, -1F, 5, 2, 2);
        this.Leg2B.setRotationPoint(5F, 18F, -2F);
        setRotationG(this.Leg2B, -30F, 0F, 60F);

        this.Leg2C = new ModelRenderer(this, 50, 18);
        this.Leg2C.addBox(4F, -8.5F, -1F, 6, 1, 1);
        this.Leg2C.setRotationPoint(5F, 18F, -2F);
        setRotationG(this.Leg2C, -30F, 0F, 70F);

        this.Leg3A = new ModelRenderer(this, 38, 0);
        this.Leg3A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg3A.setRotationPoint(5F, 17.5F, 1F);
        setRotationG(this.Leg3A, -45F, 0F, 70F);

        this.Leg3B = new ModelRenderer(this, 48, 8);
        this.Leg3B.addBox(1F, -8F, -1F, 6, 2, 2);
        this.Leg3B.setRotationPoint(5F, 17.5F, 1F);
        setRotationG(this.Leg3B, -45F, 0F, 60F);

        this.Leg3C = new ModelRenderer(this, 50, 20);
        this.Leg3C.addBox(4.5F, -8.2F, -1.3F, 6, 1, 1);
        this.Leg3C.setRotationPoint(5F, 17.5F, 1F);
        setRotationG(this.Leg3C, -45F, 0F, 70F);

        this.Leg4A = new ModelRenderer(this, 38, 0);
        this.Leg4A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg4A.setRotationPoint(5F, 17F, 4F);
        setRotationG(this.Leg4A, -60F, 0F, 70F);

        this.Leg4B = new ModelRenderer(this, 46, 12);
        this.Leg4B.addBox(0.5F, -8.5F, -1F, 7, 2, 2);
        this.Leg4B.setRotationPoint(5F, 17F, 4F);
        setRotationG(this.Leg4B, -60F, 0F, 60F);

        this.Leg4C = new ModelRenderer(this, 48, 22);
        this.Leg4C.addBox(3.5F, -8.5F, -1.5F, 7, 1, 1);
        this.Leg4C.setRotationPoint(5F, 17F, 4F);
        setRotationG(this.Leg4C, -60F, 0F, 70F);

        this.Leg5A = new ModelRenderer(this, 0, 0);
        this.Leg5A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg5A.setRotationPoint(-5F, 18F, -5F);
        setRotationG(this.Leg5A, -10F, 0F, -75F);

        this.Leg5B = new ModelRenderer(this, 50, 0);
        this.Leg5B.addBox(-7F, -8F, -1F, 5, 2, 2);
        this.Leg5B.setRotationPoint(-5F, 18F, -5F);
        setRotationG(this.Leg5B, -10F, 0F, -60F);

        this.Leg5C = new ModelRenderer(this, 52, 16);
        this.Leg5C.addBox(-9.5F, -9F, -0.7F, 5, 1, 1);
        this.Leg5C.setRotationPoint(-5F, 18F, -5F);
        setRotationG(this.Leg5C, -10F, 0F, -75F);

        this.Leg6A = new ModelRenderer(this, 0, 0);
        this.Leg6A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg6A.setRotationPoint(-5F, 18F, -2F);
        setRotationG(this.Leg6A, -30F, 0F, -70F);

        this.Leg6B = new ModelRenderer(this, 50, 4);
        this.Leg6B.addBox(-6F, -8F, -1F, 5, 2, 2);
        this.Leg6B.setRotationPoint(-5F, 18F, -2F);
        setRotationG(this.Leg6B, -30F, 0F, -60F);

        this.Leg6C = new ModelRenderer(this, 50, 18);
        this.Leg6C.addBox(-10F, -8.5F, -1F, 6, 1, 1);
        this.Leg6C.setRotationPoint(-5F, 18F, -2F);
        setRotationG(this.Leg6C, -30F, 0F, -60F);

        this.Leg7A = new ModelRenderer(this, 0, 0);
        this.Leg7A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg7A.setRotationPoint(-5F, 17.5F, 1F);
        setRotationG(this.Leg7A, -45F, 0, -70F);

        this.Leg7B = new ModelRenderer(this, 48, 8);
        this.Leg7B.addBox(-7F, -8.5F, -1F, 6, 2, 2);
        this.Leg7B.setRotationPoint(-5F, 17.5F, 1F);
        setRotationG(this.Leg7B, -45F, 0F, -60F);

        this.Leg7C = new ModelRenderer(this, 50, 20);
        this.Leg7C.addBox(-10.5F, -8.7F, -1.3F, 6, 1, 1);
        this.Leg7C.setRotationPoint(-5F, 17.5F, 1F);
        setRotationG(this.Leg7C, -45F, 0F, -70F);

        this.Leg8A = new ModelRenderer(this, 0, 0);
        this.Leg8A.addBox(-1F, -7F, -1F, 2, 7, 2);
        this.Leg8A.setRotationPoint(-5F, 17F, 4F);
        setRotationG(this.Leg8A, -60F, 0F, -70F);

        this.Leg8B = new ModelRenderer(this, 46, 12);
        this.Leg8B.addBox(-7.5F, -8.5F, -1F, 7, 2, 2);
        this.Leg8B.setRotationPoint(-5F, 17F, 4F);
        setRotationG(this.Leg8B, -60F, 0F, -60F);

        this.Leg8C = new ModelRenderer(this, 48, 22);
        this.Leg8C.addBox(-10.5F, -8.5F, -1.5F, 7, 1, 1);
        this.Leg8C.setRotationPoint(-5F, 17F, 4F);
        setRotationG(this.Leg8C, -60F, 0F, -70F);

        this.baby1 = new ModelRenderer(this, 48, 24);
        this.baby1.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby1.setRotationPoint(0F, 12F, 0F);

        this.baby2 = new ModelRenderer(this, 48, 24);
        this.baby2.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby2.setRotationPoint(-5F, 13.4F, -1F);
        setRotation(this.baby2, 0.4461433F, 2.490967F, 0.5205006F);

        this.baby3 = new ModelRenderer(this, 48, 24);
        this.baby3.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby3.setRotationPoint(-2F, 13F, 4F);
        setRotation(this.baby3, 0F, 0.8551081F, 0F);

        this.baby4 = new ModelRenderer(this, 48, 24);
        this.baby4.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby4.setRotationPoint(4F, 13F, 2F);
        setRotation(this.baby4, 0F, 2.714039F, -0.3717861F);

        this.baby5 = new ModelRenderer(this, 48, 24);
        this.baby5.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        this.baby5.setRotationPoint(1F, 13F, 8F);
        setRotation(this.baby5, 0F, -1.189716F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityScorpion scorpy = (MoCEntityScorpion) entity;
        poisoning = scorpy.swingingTail();
        isTalking = scorpy.mouthCounter != 0;
        babies = scorpy.getHasBabies();
        attacking = scorpy.armCounter;
        setRotationAngles(f, f1, f2, f3, f4, f5);
        renderParts(f5);
    }

    protected void renderParts(float f5) {
        this.Head.render(f5);
        this.MouthL.render(f5);
        this.MouthR.render(f5);
        this.Body.render(f5);
        this.Tail1.render(f5);
        this.Tail2.render(f5);
        this.Tail3.render(f5);
        this.Tail4.render(f5);
        this.Tail5.render(f5);
        this.Sting1.render(f5);
        this.Sting2.render(f5);
        this.LArm1.render(f5);
        this.LArm2.render(f5);
        this.LArm3.render(f5);
        this.LArm4.render(f5);
        this.RArm1.render(f5);
        this.RArm2.render(f5);
        this.RArm3.render(f5);
        this.RArm4.render(f5);
        this.Leg1A.render(f5);
        this.Leg1B.render(f5);
        this.Leg1C.render(f5);
        this.Leg2A.render(f5);
        this.Leg2B.render(f5);
        this.Leg2C.render(f5);
        this.Leg3A.render(f5);
        this.Leg3B.render(f5);
        this.Leg3C.render(f5);
        this.Leg4A.render(f5);
        this.Leg4B.render(f5);
        this.Leg4C.render(f5);
        this.Leg5A.render(f5);
        this.Leg5B.render(f5);
        this.Leg5C.render(f5);
        this.Leg6A.render(f5);
        this.Leg6B.render(f5);
        this.Leg6C.render(f5);
        this.Leg7A.render(f5);
        this.Leg7B.render(f5);
        this.Leg7C.render(f5);
        this.Leg8A.render(f5);
        this.Leg8B.render(f5);
        this.Leg8C.render(f5);
        if (babies) {
            this.baby1.render(f5);
            this.baby2.render(f5);
            this.baby3.render(f5);
            this.baby4.render(f5);
            this.baby5.render(f5);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    private void setRotationG(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x / this.radianF;
        model.rotateAngleY = y / this.radianF;
        model.rotateAngleZ = z / this.radianF;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        //float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.4F * f1;

        if (!poisoning) {
            this.Body.rotateAngleX = 5F / this.radianF;
            this.Tail1.rotateAngleX = 35F / this.radianF;
            this.Tail1.rotationPointY = 16F;
            this.Tail1.rotationPointZ = 12F;

            this.Tail2.rotateAngleX = 65F / this.radianF;
            this.Tail2.rotationPointY = 13F;
            this.Tail2.rotationPointZ = 16.5F;

            this.Tail3.rotateAngleX = 90F / this.radianF;
            this.Tail3.rotationPointY = 8F;
            this.Tail3.rotationPointZ = 18.5F;

            this.Tail4.rotateAngleX = 143F / this.radianF;
            this.Tail4.rotationPointY = 3F;
            this.Tail4.rotationPointZ = 18F;

            this.Tail5.rotateAngleX = 175F / this.radianF;
            this.Tail5.rotationPointY = -0.2F;
            this.Tail5.rotationPointZ = 14F;

            this.Sting1.rotateAngleX = 24F / this.radianF;
            this.Sting1.rotationPointY = -1F;
            this.Sting1.rotationPointZ = 7F;

            this.Sting2.rotateAngleX = -12F / this.radianF;
            this.Sting2.rotationPointY = 2.6F;
            this.Sting2.rotationPointZ = 8.8F;

        } else {
            this.Body.rotateAngleX = 50F / this.radianF;
            this.Tail1.rotateAngleX = 100F / this.radianF;
            this.Tail1.rotationPointY = 9F;
            this.Tail1.rotationPointZ = 10F;

            this.Tail2.rotateAngleX = 160F / this.radianF;
            this.Tail2.rotationPointY = 3F;
            this.Tail2.rotationPointZ = 9.5F;

            this.Tail3.rotateAngleX = -170F / this.radianF;
            this.Tail3.rotationPointY = 1F;
            this.Tail3.rotationPointZ = 3.5F;

            this.Tail4.rotateAngleX = -156F / this.radianF;
            this.Tail4.rotationPointY = 1.8F;
            this.Tail4.rotationPointZ = -2F;

            this.Tail5.rotateAngleX = -154F / this.radianF;
            this.Tail5.rotationPointY = 3.8F;
            this.Tail5.rotationPointZ = -7F;

            this.Sting1.rotateAngleX = -57F / this.radianF;
            this.Sting1.rotationPointY = 6F;
            this.Sting1.rotationPointZ = -12F;

            this.Sting2.rotateAngleX = -93.7F / this.radianF;
            this.Sting2.rotationPointY = 8F;
            this.Sting2.rotationPointZ = -15.2F;
        }

        /**
         * Mouth animation
         */
        float MouthRot = 0F;
        if (isTalking) {
            MouthRot = MathHelper.cos((f2 * 1.1F)) * 0.2F;
        }
        this.MouthR.rotateAngleY = (22F / this.radianF) + MouthRot;
        this.MouthL.rotateAngleY = (-22F / this.radianF) - MouthRot;

        //Larm normal position
        this.LArm1.rotateAngleX = -20F / this.radianF;
        this.LArm2.rotationPointX = 10F;
        this.LArm2.rotationPointY = 14F;
        this.LArm2.rotationPointZ = -6F;
        this.LArm3.rotationPointX = 12F;
        this.LArm3.rotationPointY = 15F;
        this.LArm3.rotationPointZ = -11F;
        this.LArm4.rotationPointX = 11F;
        this.LArm4.rotationPointY = 15F;
        this.LArm4.rotationPointZ = -11F;
        this.LArm4.rotateAngleY = 0F;

        //Rarm normal position
        this.RArm1.rotateAngleX = -20F / this.radianF;
        this.RArm2.rotationPointX = -10F;
        this.RArm2.rotationPointY = 14F;
        this.RArm2.rotationPointZ = -6F;
        this.RArm3.rotationPointX = -12F;
        this.RArm3.rotationPointY = 15F;
        this.RArm3.rotationPointZ = -11F;
        this.RArm4.rotationPointX = -11F;
        this.RArm4.rotationPointY = 15F;
        this.RArm4.rotationPointZ = -11F;
        this.RArm4.rotateAngleY = 0F;

        /**
         * Random hand animations
         */
        if (attacking == 0) {

            /**
             * LHand random animation
             */
            float lHand = 0F;

            float f2a = f2 % 100F;
            if (f2a > 0 & f2a < 20) {
                lHand = f2a / this.radianF;
            }
            this.LArm3.rotateAngleY = (9F / this.radianF) - lHand;
            this.LArm4.rotateAngleY = +lHand;

            /**
             * RHand random animation
             */
            float RHand = 0F;
            float f2b = f2 % 75F;
            if (f2b > 30 & f2b < 50) {
                RHand = (f2b - 29) / this.radianF;
            }
            this.RArm3.rotateAngleY = (-9F / this.radianF) + RHand;
            this.RArm4.rotateAngleY = -RHand;
        } else
        //hand attacking sequence
        {
            if (attacking > 0 && attacking < 5) {
                //LArm ahead open
                this.LArm1.rotateAngleX = 50F / this.radianF;
                this.LArm2.rotationPointX = 8F;
                this.LArm2.rotationPointY = 15F;
                this.LArm2.rotationPointZ = -13F;
                this.LArm3.rotationPointX = 10F;
                this.LArm3.rotationPointY = 16F;
                this.LArm3.rotationPointZ = -18F;
                this.LArm4.rotationPointX = 9F;
                this.LArm4.rotationPointY = 16F;
                this.LArm4.rotationPointZ = -18F;
                this.LArm4.rotateAngleY = 40F / this.radianF;
            }

            if (attacking >= 5 && attacking < 10) {
                //LArm ahead closed
                this.LArm1.rotateAngleX = 70F / this.radianF;
                this.LArm2.rotationPointX = 7F;
                this.LArm2.rotationPointY = 16F;
                this.LArm2.rotationPointZ = -14F;
                this.LArm3.rotationPointX = 9F;
                this.LArm3.rotationPointY = 17F;
                this.LArm3.rotationPointZ = -19F;
                this.LArm4.rotationPointX = 8F;
                this.LArm4.rotationPointY = 17F;
                this.LArm4.rotationPointZ = -19F;
                this.LArm4.rotateAngleY = 0F;
            }
            if (attacking >= 10 && attacking < 15) {
                //Rarm ahead open
                this.RArm1.rotateAngleX = 50F / this.radianF;
                this.RArm2.rotationPointX = -8F;
                this.RArm2.rotationPointY = 15F;
                this.RArm2.rotationPointZ = -13F;
                this.RArm3.rotationPointX = -10F;
                this.RArm3.rotationPointY = 16F;
                this.RArm3.rotationPointZ = -18F;
                this.RArm4.rotationPointX = -9F;
                this.RArm4.rotationPointY = 16F;
                this.RArm4.rotationPointZ = -18F;
                this.RArm4.rotateAngleY = -40F / this.radianF;
            }
            if (attacking >= 15 && attacking < 20) {
                //RArm ahead closed
                this.RArm1.rotateAngleX = 70F / this.radianF;
                this.RArm2.rotationPointX = -7F;
                this.RArm2.rotationPointY = 16F;
                this.RArm2.rotationPointZ = -14F;
                this.RArm3.rotationPointX = -9F;
                this.RArm3.rotationPointY = 17F;
                this.RArm3.rotationPointZ = -19F;
                this.RArm4.rotationPointX = -8F;
                this.RArm4.rotationPointY = 17F;
                this.RArm4.rotationPointZ = -19F;
                this.RArm4.rotateAngleY = 0F;
            }
        }

        /**
         * Babies animation
         */
        if (babies) {
            float fmov = f2 % 100;
            float fb1 = 0F;
            float fb2 = 142F / this.radianF;
            float fb3 = 49F / this.radianF;
            float fb4 = 155F / this.radianF;
            float fb5 = -68F / this.radianF;

            //fb2 -= fmov;
            //fb5 += fmov;
            //fb1 += (fmov/2F);
            //fb3 -= (fmov*2F);
            //fb4 -= fmov;

            if (fmov > 0F && fmov < 20F) {
                fb2 -= (MathHelper.cos((f2 * 0.8F)) * 0.3F);
                fb3 -= (MathHelper.cos((f2 * 0.6F)) * 0.2F);
                fb1 += (MathHelper.cos((f2 * 0.4F)) * 0.4F);
                fb5 += (MathHelper.cos((f2 * 0.7F)) * 0.5F);
            }

            if (fmov > 30F && fmov < 50F) {
                fb4 -= (MathHelper.cos((f2 * 0.8F)) * 0.4F);
                fb1 += (MathHelper.cos((f2 * 0.7F)) * 0.1F);
                fb3 -= (MathHelper.cos((f2 * 0.6F)) * 0.2F);
            }
            if (fmov > 80F) {
                fb5 += (MathHelper.cos((f2 * 0.2F)) * 0.4F);
                fb2 -= (MathHelper.cos((f2 * 0.6F)) * 0.3F);
                fb4 -= (MathHelper.cos((f2 * 0.4F)) * 0.2F);
            }

            /*
             * if (fmov>0F && fmov < 30F) { fb2 -= fmov; fb5 += fmov; } if (fmov
             * > 30F && fmov <70F) { fb1 += (fmov/2F); fb3 -= (fmov*2F); } if
             * (fmov >70F) { fb4 -= fmov; }
             */
            this.baby1.rotateAngleY = fb1;///radianF;
            this.baby2.rotateAngleY = fb2;///radianF;
            this.baby3.rotateAngleY = fb3;///radianF;
            this.baby4.rotateAngleY = fb4;///radianF;
            this.baby5.rotateAngleY = fb5;///radianF;
        }

        /**
         * floats used for the scorpion's leg animations
         */
        float f9 = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
        float f10 = -(MathHelper.cos(f * 0.6662F * 2.0F + 3.141593F) * 0.4F) * f1;
        float f11 = -(MathHelper.cos(f * 0.6662F * 2.0F + 1.570796F) * 0.4F) * f1;
        float f12 = -(MathHelper.cos(f * 0.6662F * 2.0F + 4.712389F) * 0.4F) * f1;
        float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
        float f14 = Math.abs(MathHelper.sin(f * 0.6662F + 3.141593F) * 0.4F) * f1;
        float f15 = Math.abs(MathHelper.sin(f * 0.6662F + 1.570796F) * 0.4F) * f1;
        float f16 = Math.abs(MathHelper.sin(f * 0.6662F + 4.712389F) * 0.4F) * f1;

        if (sitting) {
            this.Leg1A.rotateAngleX = -10F / this.radianF;
            this.Leg1A.rotateAngleZ = 35F / this.radianF;
            this.Leg1B.rotateAngleZ = 20F / this.radianF;
            this.Leg1C.rotateAngleZ = 35F / this.radianF;

            this.Leg2A.rotateAngleX = -30F / this.radianF;
            this.Leg2A.rotateAngleZ = 35F / this.radianF;
            this.Leg2B.rotateAngleZ = 20F / this.radianF;
            this.Leg2C.rotateAngleZ = 35F / this.radianF;

            this.Leg3A.rotateAngleX = -45F / this.radianF;
            this.Leg3A.rotateAngleZ = 35F / this.radianF;
            this.Leg3B.rotateAngleZ = 20F / this.radianF;
            this.Leg3C.rotateAngleZ = 35F / this.radianF;

            this.Leg4A.rotateAngleX = -60F / this.radianF;
            this.Leg4A.rotateAngleZ = 35F / this.radianF;
            this.Leg4B.rotateAngleZ = 20F / this.radianF;
            this.Leg4C.rotateAngleZ = 35F / this.radianF;

            this.Leg5A.rotateAngleX = -10F / this.radianF;
            this.Leg5A.rotateAngleZ = -35F / this.radianF;
            this.Leg5B.rotateAngleZ = -20F / this.radianF;
            this.Leg5C.rotateAngleZ = -35F / this.radianF;

            this.Leg6A.rotateAngleX = -30F / this.radianF;
            this.Leg6A.rotateAngleZ = -35F / this.radianF;
            this.Leg6B.rotateAngleZ = -20F / this.radianF;
            this.Leg6C.rotateAngleZ = -35F / this.radianF;

            this.Leg7A.rotateAngleX = -45F / this.radianF;
            this.Leg7A.rotateAngleZ = -35F / this.radianF;
            this.Leg7B.rotateAngleZ = -20F / this.radianF;
            this.Leg7C.rotateAngleZ = -35F / this.radianF;

            this.Leg8A.rotateAngleX = -60F / this.radianF;
            this.Leg8A.rotateAngleZ = -35F / this.radianF;
            this.Leg8B.rotateAngleZ = -20F / this.radianF;
            this.Leg8C.rotateAngleZ = -35F / this.radianF;

        } else {
            this.Leg1A.rotateAngleX = -10F / this.radianF;
            this.Leg1A.rotateAngleZ = 75F / this.radianF;
            this.Leg1B.rotateAngleZ = 60F / this.radianF;
            this.Leg1C.rotateAngleZ = 75F / this.radianF;

            this.Leg1A.rotateAngleX += f9;
            this.Leg1B.rotateAngleX = this.Leg1A.rotateAngleX;
            this.Leg1C.rotateAngleX = this.Leg1A.rotateAngleX;

            this.Leg1A.rotateAngleZ += f13;
            this.Leg1B.rotateAngleZ += f13;
            this.Leg1C.rotateAngleZ += f13;

            this.Leg2A.rotateAngleX = -30F / this.radianF;
            this.Leg2A.rotateAngleZ = 70F / this.radianF;
            this.Leg2B.rotateAngleZ = 60F / this.radianF;
            this.Leg2C.rotateAngleZ = 70F / this.radianF;

            this.Leg2A.rotateAngleX += f10;
            this.Leg2B.rotateAngleX = this.Leg2A.rotateAngleX;
            this.Leg2C.rotateAngleX = this.Leg2A.rotateAngleX;

            this.Leg2A.rotateAngleZ += f14;
            this.Leg2B.rotateAngleZ += f14;
            this.Leg2C.rotateAngleZ += f14;

            this.Leg3A.rotateAngleX = -45F / this.radianF;
            this.Leg3A.rotateAngleZ = 70F / this.radianF;
            this.Leg3B.rotateAngleZ = 60F / this.radianF;
            this.Leg3C.rotateAngleZ = 70F / this.radianF;

            this.Leg3A.rotateAngleX += f11;
            this.Leg3B.rotateAngleX = this.Leg3A.rotateAngleX;
            this.Leg3C.rotateAngleX = this.Leg3A.rotateAngleX;

            this.Leg3A.rotateAngleZ += f15;
            this.Leg3B.rotateAngleZ += f15;
            this.Leg3C.rotateAngleZ += f15;

            this.Leg4A.rotateAngleX = -60F / this.radianF;
            this.Leg4A.rotateAngleZ = 70F / this.radianF;
            this.Leg4B.rotateAngleZ = 60F / this.radianF;
            this.Leg4C.rotateAngleZ = 70F / this.radianF;

            this.Leg4A.rotateAngleX += f12;
            this.Leg4B.rotateAngleX = this.Leg4A.rotateAngleX;
            this.Leg4C.rotateAngleX = this.Leg4A.rotateAngleX;

            this.Leg4A.rotateAngleZ += f16;
            this.Leg4B.rotateAngleZ += f16;
            this.Leg4C.rotateAngleZ += f16;

            this.Leg5A.rotateAngleX = -10F / this.radianF;
            this.Leg5A.rotateAngleZ = -75F / this.radianF;
            this.Leg5B.rotateAngleZ = -60F / this.radianF;
            this.Leg5C.rotateAngleZ = -75F / this.radianF;

            this.Leg5A.rotateAngleX -= f9;
            this.Leg5B.rotateAngleX = this.Leg5A.rotateAngleX;
            this.Leg5C.rotateAngleX = this.Leg5A.rotateAngleX;

            this.Leg5A.rotateAngleZ -= f13;
            this.Leg5B.rotateAngleZ -= f13;
            this.Leg5C.rotateAngleZ -= f13;

            this.Leg6A.rotateAngleX = -30F / this.radianF;
            this.Leg6A.rotateAngleZ = -70F / this.radianF;
            this.Leg6B.rotateAngleZ = -60F / this.radianF;
            this.Leg6C.rotateAngleZ = -70F / this.radianF;

            this.Leg6A.rotateAngleX -= f10;
            this.Leg6B.rotateAngleX = this.Leg6A.rotateAngleX;
            this.Leg6C.rotateAngleX = this.Leg6A.rotateAngleX;

            this.Leg6A.rotateAngleZ -= f14;
            this.Leg6B.rotateAngleZ -= f14;
            this.Leg6C.rotateAngleZ -= f14;

            this.Leg7A.rotateAngleX = -45F / this.radianF;
            this.Leg7A.rotateAngleZ = -70F / this.radianF;
            this.Leg7B.rotateAngleZ = -60F / this.radianF;
            this.Leg7C.rotateAngleZ = -70F / this.radianF;

            this.Leg7A.rotateAngleX -= f11;
            this.Leg7B.rotateAngleX = this.Leg7A.rotateAngleX;
            this.Leg7C.rotateAngleX = this.Leg7A.rotateAngleX;

            this.Leg7A.rotateAngleZ -= f15;
            this.Leg7B.rotateAngleZ -= f15;
            this.Leg7C.rotateAngleZ -= f15;

            this.Leg8A.rotateAngleX = -60F / this.radianF;
            this.Leg8A.rotateAngleZ = -70F / this.radianF;
            this.Leg8B.rotateAngleZ = -60F / this.radianF;
            this.Leg8C.rotateAngleZ = -70F / this.radianF;

            this.Leg8A.rotateAngleX -= f12;
            this.Leg8B.rotateAngleX = this.Leg8A.rotateAngleX;
            this.Leg8C.rotateAngleX = this.Leg8A.rotateAngleX;

            this.Leg8A.rotateAngleZ -= f16;
            this.Leg8B.rotateAngleZ -= f16;
            this.Leg8C.rotateAngleZ -= f16;
        }

    }

}
