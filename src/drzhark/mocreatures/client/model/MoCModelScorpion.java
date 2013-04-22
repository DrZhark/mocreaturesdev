package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;

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

    float radianF = 57.29578F;

    public MoCModelScorpion()
    {
        textureWidth = 64;
        textureHeight = 64;

        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-5F, 0F, 0F, 10, 5, 13);
        Head.setRotationPoint(0F, 14F, -9F);

        MouthL = new ModelRenderer(this, 18, 58);
        MouthL.addBox(-3F, -2F, -1F, 4, 4, 2);
        MouthL.setRotationPoint(3F, 17F, -9F);
        setRotation(MouthL, 0F, -0.3839724F, 0F);

        MouthR = new ModelRenderer(this, 30, 58);
        MouthR.addBox(-1F, -2F, -1F, 4, 4, 2);
        MouthR.setRotationPoint(-3F, 17F, -9F);
        setRotation(MouthR, 0F, 0.3839724F, 0F);

        Body = new ModelRenderer(this, 0, 18);
        Body.addBox(-4F, -2F, 0F, 8, 4, 10);
        Body.setRotationPoint(0F, 17F, 3F);
        setRotation(Body, 0.0872665F, 0F, 0F);

        Tail1 = new ModelRenderer(this, 0, 32);
        Tail1.addBox(-3F, -2F, 0F, 6, 4, 6);
        Tail1.setRotationPoint(0F, 16F, 12F);
        setRotation(Tail1, 0.6108652F, 0F, 0F);

        Tail2 = new ModelRenderer(this, 0, 42);
        Tail2.addBox(-2F, -2F, 0F, 4, 4, 6);
        Tail2.setRotationPoint(0F, 13F, 16.5F);
        setRotation(Tail2, 1.134464F, 0F, 0F);

        Tail3 = new ModelRenderer(this, 0, 52);
        Tail3.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        Tail3.setRotationPoint(0F, 8F, 18.5F);
        setRotation(Tail3, 1.692143F, 0F, 0F);

        Tail4 = new ModelRenderer(this, 24, 32);
        Tail4.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        Tail4.setRotationPoint(0F, 3F, 18F);
        setRotation(Tail4, 2.510073F, 0F, 0F);

        Tail5 = new ModelRenderer(this, 24, 41);
        Tail5.addBox(-1.5F, -1.5F, 0F, 3, 3, 6);
        Tail5.setRotationPoint(0F, -0.2F, 14F);
        setRotation(Tail5, 3.067752F, 0F, 0F);

        Sting1 = new ModelRenderer(this, 30, 50);
        Sting1.addBox(-1.5F, 0F, -1.5F, 3, 5, 3);
        Sting1.setRotationPoint(0F, -1F, 7F);
        setRotation(Sting1, 0.4089647F, 0F, 0F);

        Sting2 = new ModelRenderer(this, 26, 50);
        Sting2.addBox(-0.5F, 0F, 0.5F, 1, 4, 1);
        Sting2.setRotationPoint(0F, 2.6F, 8.8F);
        setRotation(Sting2, -0.2230717F, 0F, 0F);

        LArm1 = new ModelRenderer(this, 26, 18);
        LArm1.addBox(-1F, -7F, -1F, 2, 7, 2);
        LArm1.setRotationPoint(5F, 18F, -8F);
        setRotation(LArm1, -0.3490659F, 0F, 0.8726646F);

        LArm2 = new ModelRenderer(this, 42, 55);
        LArm2.addBox(-1.5F, -1.5F, -6F, 3, 3, 6);
        LArm2.setRotationPoint(10F, 14F, -6F);
        setRotation(LArm2, 0.1745329F, -0.3490659F, -0.2617994F);

        LArm3 = new ModelRenderer(this, 42, 39);
        LArm3.addBox(-0.5F, -0.5F, -7F, 2, 1, 7);
        LArm3.setRotationPoint(12F, 15F, -11F);
        setRotation(LArm3, 0.2617994F, 0.1570796F, -0.1570796F);

        LArm4 = new ModelRenderer(this, 42, 31);
        LArm4.addBox(-1.5F, -0.5F, -6F, 1, 1, 7);
        LArm4.setRotationPoint(11F, 15F, -11F);
        setRotation(LArm4, 0.2617994F, 0F, -0.1570796F);

        RArm1 = new ModelRenderer(this, 0, 18);
        RArm1.addBox(-1F, -7F, -1F, 2, 7, 2);
        RArm1.setRotationPoint(-5F, 18F, -8F);
        setRotation(RArm1, -0.3490659F, 0F, -0.8726646F);

        RArm2 = new ModelRenderer(this, 42, 55);
        RArm2.addBox(-1.5F, -1.5F, -6F, 3, 3, 6);
        RArm2.setRotationPoint(-10F, 14F, -6F);
        setRotation(RArm2, 0.1745329F, 0.3490659F, 0.2617994F);

        RArm3 = new ModelRenderer(this, 42, 47);
        RArm3.addBox(-1.5F, -0.5F, -7F, 2, 1, 7);
        RArm3.setRotationPoint(-12F, 15F, -11F);
        setRotation(RArm3, 0.2617994F, -0.1570796F, 0.1570796F);

        RArm4 = new ModelRenderer(this, 42, 31);
        RArm4.addBox(0.5F, -0.5F, -6F, 1, 1, 7);
        RArm4.setRotationPoint(-11F, 15F, -11F);
        setRotation(RArm4, 0.2617994F, 0F, 0.1570796F);

        Leg1A = new ModelRenderer(this, 38, 0);
        Leg1A.addBox(-1F, -7F, -1F, 2, 7, 2);
        Leg1A.setRotationPoint(5F, 18F, -5F);
        setRotationG(Leg1A, -10F, 0F, 75F);

        Leg1B = new ModelRenderer(this, 50, 0);
        Leg1B.addBox(2F, -8F, -1F, 5, 2, 2);
        Leg1B.setRotationPoint(5F, 18F, -5F);
        setRotationG(Leg1B, -10F, 0F, 60F);

        Leg1C = new ModelRenderer(this, 52, 16);
        Leg1C.addBox(4.5F, -9F, -0.7F, 5, 1, 1);
        Leg1C.setRotationPoint(5F, 18F, -5F);
        setRotationG(Leg1C, -10F, 0F, 75F);

        Leg2A = new ModelRenderer(this, 38, 0);
        Leg2A.addBox(-1F, -7F, -1F, 2, 7, 2);
        Leg2A.setRotationPoint(5F, 18F, -2F);
        setRotationG(Leg2A, -30F, 0F, 70F);

        Leg2B = new ModelRenderer(this, 50, 4);
        Leg2B.addBox(1F, -8F, -1F, 5, 2, 2);
        Leg2B.setRotationPoint(5F, 18F, -2F);
        setRotationG(Leg2B, -30F, 0F, 60F);

        Leg2C = new ModelRenderer(this, 50, 18);
        Leg2C.addBox(4F, -8.5F, -1F, 6, 1, 1);
        Leg2C.setRotationPoint(5F, 18F, -2F);
        setRotationG(Leg2C, -30F, 0F, 70F);

        Leg3A = new ModelRenderer(this, 38, 0);
        Leg3A.addBox(-1F, -7F, -1F, 2, 7, 2);
        Leg3A.setRotationPoint(5F, 17.5F, 1F);
        setRotationG(Leg3A, -45F, 0F, 70F);

        Leg3B = new ModelRenderer(this, 48, 8);
        Leg3B.addBox(1F, -8F, -1F, 6, 2, 2);
        Leg3B.setRotationPoint(5F, 17.5F, 1F);
        setRotationG(Leg3B, -45F, 0F, 60F);

        Leg3C = new ModelRenderer(this, 50, 20);
        Leg3C.addBox(4.5F, -8.2F, -1.3F, 6, 1, 1);
        Leg3C.setRotationPoint(5F, 17.5F, 1F);
        setRotationG(Leg3C, -45F, 0F, 70F);

        Leg4A = new ModelRenderer(this, 38, 0);
        Leg4A.addBox(-1F, -7F, -1F, 2, 7, 2);
        Leg4A.setRotationPoint(5F, 17F, 4F);
        setRotationG(Leg4A, -60F, 0F, 70F);

        Leg4B = new ModelRenderer(this, 46, 12);
        Leg4B.addBox(0.5F, -8.5F, -1F, 7, 2, 2);
        Leg4B.setRotationPoint(5F, 17F, 4F);
        setRotationG(Leg4B, -60F, 0F, 60F);

        Leg4C = new ModelRenderer(this, 48, 22);
        Leg4C.addBox(3.5F, -8.5F, -1.5F, 7, 1, 1);
        Leg4C.setRotationPoint(5F, 17F, 4F);
        setRotationG(Leg4C, -60F, 0F, 70F);

        Leg5A = new ModelRenderer(this, 0, 0);
        Leg5A.addBox(-1F, -7F, -1F, 2, 7, 2);
        Leg5A.setRotationPoint(-5F, 18F, -5F);
        setRotationG(Leg5A, -10F, 0F, -75F);

        Leg5B = new ModelRenderer(this, 50, 0);
        Leg5B.addBox(-7F, -8F, -1F, 5, 2, 2);
        Leg5B.setRotationPoint(-5F, 18F, -5F);
        setRotationG(Leg5B, -10F, 0F, -60F);

        Leg5C = new ModelRenderer(this, 52, 16);
        Leg5C.addBox(-9.5F, -9F, -0.7F, 5, 1, 1);
        Leg5C.setRotationPoint(-5F, 18F, -5F);
        setRotationG(Leg5C, -10F, 0F, -75F);

        Leg6A = new ModelRenderer(this, 0, 0);
        Leg6A.addBox(-1F, -7F, -1F, 2, 7, 2);
        Leg6A.setRotationPoint(-5F, 18F, -2F);
        setRotationG(Leg6A, -30F, 0F, -70F);

        Leg6B = new ModelRenderer(this, 50, 4);
        Leg6B.addBox(-6F, -8F, -1F, 5, 2, 2);
        Leg6B.setRotationPoint(-5F, 18F, -2F);
        setRotationG(Leg6B, -30F, 0F, -60F);

        Leg6C = new ModelRenderer(this, 50, 18);
        Leg6C.addBox(-10F, -8.5F, -1F, 6, 1, 1);
        Leg6C.setRotationPoint(-5F, 18F, -2F);
        setRotationG(Leg6C, -30F, 0F, -60F);

        Leg7A = new ModelRenderer(this, 0, 0);
        Leg7A.addBox(-1F, -7F, -1F, 2, 7, 2);
        Leg7A.setRotationPoint(-5F, 17.5F, 1F);
        setRotationG(Leg7A, -45F, 0, -70F);

        Leg7B = new ModelRenderer(this, 48, 8);
        Leg7B.addBox(-7F, -8.5F, -1F, 6, 2, 2);
        Leg7B.setRotationPoint(-5F, 17.5F, 1F);
        setRotationG(Leg7B, -45F, 0F, -60F);

        Leg7C = new ModelRenderer(this, 50, 20);
        Leg7C.addBox(-10.5F, -8.7F, -1.3F, 6, 1, 1);
        Leg7C.setRotationPoint(-5F, 17.5F, 1F);
        setRotationG(Leg7C, -45F, 0F, -70F);

        Leg8A = new ModelRenderer(this, 0, 0);
        Leg8A.addBox(-1F, -7F, -1F, 2, 7, 2);
        Leg8A.setRotationPoint(-5F, 17F, 4F);
        setRotationG(Leg8A, -60F, 0F, -70F);

        Leg8B = new ModelRenderer(this, 46, 12);
        Leg8B.addBox(-7.5F, -8.5F, -1F, 7, 2, 2);
        Leg8B.setRotationPoint(-5F, 17F, 4F);
        setRotationG(Leg8B, -60F, 0F, -60F);

        Leg8C = new ModelRenderer(this, 48, 22);
        Leg8C.addBox(-10.5F, -8.5F, -1.5F, 7, 1, 1);
        Leg8C.setRotationPoint(-5F, 17F, 4F);
        setRotationG(Leg8C, -60F, 0F, -70F);

        baby1 = new ModelRenderer(this, 48, 24);
        baby1.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        baby1.setRotationPoint(0F, 12F, 0F);

        baby2 = new ModelRenderer(this, 48, 24);
        baby2.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        baby2.setRotationPoint(-5F, 13.4F, -1F);
        setRotation(baby2, 0.4461433F, 2.490967F, 0.5205006F);

        baby3 = new ModelRenderer(this, 48, 24);
        baby3.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        baby3.setRotationPoint(-2F, 13F, 4F);
        setRotation(baby3, 0F, 0.8551081F, 0F);

        baby4 = new ModelRenderer(this, 48, 24);
        baby4.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        baby4.setRotationPoint(4F, 13F, 2F);
        setRotation(baby4, 0F, 2.714039F, -0.3717861F);

        baby5 = new ModelRenderer(this, 48, 24);
        baby5.addBox(-1.5F, 0F, -2.5F, 3, 2, 5);
        baby5.setRotationPoint(1F, 13F, 8F);
        setRotation(baby5, 0F, -1.189716F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);

        MoCEntityScorpion scorpy = (MoCEntityScorpion) entity;
        boolean poisoning = scorpy.swingingTail();
        boolean isTalking = scorpy.mouthCounter != 0;
        boolean babies = scorpy.getHasBabies();
        int attacking = scorpy.armCounter;
        setRotationAngles(f, f1, f2, f3, f4, f5, poisoning, isTalking, attacking, babies);
        Head.render(f5);
        MouthL.render(f5);
        MouthR.render(f5);
        Body.render(f5);
        Tail1.render(f5);
        Tail2.render(f5);
        Tail3.render(f5);
        Tail4.render(f5);
        Tail5.render(f5);
        Sting1.render(f5);
        Sting2.render(f5);
        LArm1.render(f5);
        LArm2.render(f5);
        LArm3.render(f5);
        LArm4.render(f5);
        RArm1.render(f5);
        RArm2.render(f5);
        RArm3.render(f5);
        RArm4.render(f5);
        Leg1A.render(f5);
        Leg1B.render(f5);
        Leg1C.render(f5);
        Leg2A.render(f5);
        Leg2B.render(f5);
        Leg2C.render(f5);
        Leg3A.render(f5);
        Leg3B.render(f5);
        Leg3C.render(f5);
        Leg4A.render(f5);
        Leg4B.render(f5);
        Leg4C.render(f5);
        Leg5A.render(f5);
        Leg5B.render(f5);
        Leg5C.render(f5);
        Leg6A.render(f5);
        Leg6B.render(f5);
        Leg6C.render(f5);
        Leg7A.render(f5);
        Leg7B.render(f5);
        Leg7C.render(f5);
        Leg8A.render(f5);
        Leg8B.render(f5);
        Leg8C.render(f5);
        if (babies)
        {
            baby1.render(f5);
            baby2.render(f5);
            baby3.render(f5);
            baby4.render(f5);
            baby5.render(f5);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    private void setRotationG(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x / radianF;
        model.rotateAngleY = y / radianF;
        model.rotateAngleZ = z / radianF;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean poisoning, boolean isTalking, int attacking, boolean babies)
    {
        //float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.4F * f1;

        if (!poisoning)
        {
            Body.rotateAngleX = 5F / radianF;
            Tail1.rotateAngleX = 35F / radianF;
            Tail1.rotationPointY = 16F;
            Tail1.rotationPointZ = 12F;

            Tail2.rotateAngleX = 65F / radianF;
            Tail2.rotationPointY = 13F;
            Tail2.rotationPointZ = 16.5F;

            Tail3.rotateAngleX = 90F / radianF;
            Tail3.rotationPointY = 8F;
            Tail3.rotationPointZ = 18.5F;

            Tail4.rotateAngleX = 143F / radianF;
            Tail4.rotationPointY = 3F;
            Tail4.rotationPointZ = 18F;

            Tail5.rotateAngleX = 175F / radianF;
            Tail5.rotationPointY = -0.2F;
            Tail5.rotationPointZ = 14F;

            Sting1.rotateAngleX = 24F / radianF;
            Sting1.rotationPointY = -1F;
            Sting1.rotationPointZ = 7F;

            Sting2.rotateAngleX = -12F / radianF;
            Sting2.rotationPointY = 2.6F;
            Sting2.rotationPointZ = 8.8F;

        }
        else
        {
            Body.rotateAngleX = 50F / radianF;
            Tail1.rotateAngleX = 100F / radianF;
            Tail1.rotationPointY = 9F;
            Tail1.rotationPointZ = 10F;

            Tail2.rotateAngleX = 160F / radianF;
            Tail2.rotationPointY = 3F;
            Tail2.rotationPointZ = 9.5F;

            Tail3.rotateAngleX = -170F / radianF;
            Tail3.rotationPointY = 1F;
            Tail3.rotationPointZ = 3.5F;

            Tail4.rotateAngleX = -156F / radianF;
            Tail4.rotationPointY = 1.8F;
            Tail4.rotationPointZ = -2F;

            Tail5.rotateAngleX = -154F / radianF;
            Tail5.rotationPointY = 3.8F;
            Tail5.rotationPointZ = -7F;

            Sting1.rotateAngleX = -57F / radianF;
            Sting1.rotationPointY = 6F;
            Sting1.rotationPointZ = -12F;

            Sting2.rotateAngleX = -93.7F / radianF;
            Sting2.rotationPointY = 8F;
            Sting2.rotationPointZ = -15.2F;
        }

        /**
         * Mouth animation
         */
        float MouthRot = 0F;
        if (isTalking)
        {
            MouthRot = MathHelper.cos((f2 * 1.1F)) * 0.2F;
        }
        MouthR.rotateAngleY = (22F / radianF) + MouthRot;
        MouthL.rotateAngleY = (-22F / radianF) - MouthRot;

        //Larm normal position
        LArm1.rotateAngleX = -20F / radianF;
        LArm2.rotationPointX = 10F;
        LArm2.rotationPointY = 14F;
        LArm2.rotationPointZ = -6F;
        LArm3.rotationPointX = 12F;
        LArm3.rotationPointY = 15F;
        LArm3.rotationPointZ = -11F;
        LArm4.rotationPointX = 11F;
        LArm4.rotationPointY = 15F;
        LArm4.rotationPointZ = -11F;
        LArm4.rotateAngleY = 0F;

        //Rarm normal position
        RArm1.rotateAngleX = -20F / radianF;
        RArm2.rotationPointX = -10F;
        RArm2.rotationPointY = 14F;
        RArm2.rotationPointZ = -6F;
        RArm3.rotationPointX = -12F;
        RArm3.rotationPointY = 15F;
        RArm3.rotationPointZ = -11F;
        RArm4.rotationPointX = -11F;
        RArm4.rotationPointY = 15F;
        RArm4.rotationPointZ = -11F;
        RArm4.rotateAngleY = 0F;

        /**
         * Random hand animations
         */
        if (attacking == 0)
        {

            /**
             * LHand random animation
             */
            float lHand = 0F;

            float f2a = f2 % 100F;
            if (f2a > 0 & f2a < 20)
            {
                lHand = f2a / radianF;
            }
            LArm3.rotateAngleY = (9F / radianF) - lHand;
            LArm4.rotateAngleY = +lHand;

            /**
             * RHand random animation
             */
            float RHand = 0F;
            float f2b = f2 % 75F;
            if (f2b > 30 & f2b < 50)
            {
                RHand = (f2b - 29) / radianF;
            }
            RArm3.rotateAngleY = (-9F / radianF) + RHand;
            RArm4.rotateAngleY = -RHand;
        }
        else
        //hand attacking sequence 
        {
            if (attacking > 0 && attacking < 5)
            {
                //LArm ahead open
                LArm1.rotateAngleX = 50F / radianF;
                LArm2.rotationPointX = 8F;
                LArm2.rotationPointY = 15F;
                LArm2.rotationPointZ = -13F;
                LArm3.rotationPointX = 10F;
                LArm3.rotationPointY = 16F;
                LArm3.rotationPointZ = -18F;
                LArm4.rotationPointX = 9F;
                LArm4.rotationPointY = 16F;
                LArm4.rotationPointZ = -18F;
                LArm4.rotateAngleY = 40F / radianF;
            }

            if (attacking >= 5 && attacking < 10)
            {
                //LArm ahead closed
                LArm1.rotateAngleX = 70F / radianF;
                LArm2.rotationPointX = 7F;
                LArm2.rotationPointY = 16F;
                LArm2.rotationPointZ = -14F;
                LArm3.rotationPointX = 9F;
                LArm3.rotationPointY = 17F;
                LArm3.rotationPointZ = -19F;
                LArm4.rotationPointX = 8F;
                LArm4.rotationPointY = 17F;
                LArm4.rotationPointZ = -19F;
                LArm4.rotateAngleY = 0F;
            }
            if (attacking >= 10 && attacking < 15)
            {
                //Rarm ahead open
                RArm1.rotateAngleX = 50F / radianF;
                RArm2.rotationPointX = -8F;
                RArm2.rotationPointY = 15F;
                RArm2.rotationPointZ = -13F;
                RArm3.rotationPointX = -10F;
                RArm3.rotationPointY = 16F;
                RArm3.rotationPointZ = -18F;
                RArm4.rotationPointX = -9F;
                RArm4.rotationPointY = 16F;
                RArm4.rotationPointZ = -18F;
                RArm4.rotateAngleY = -40F / radianF;
            }
            if (attacking >= 15 && attacking < 20)
            {
                //RArm ahead closed
                RArm1.rotateAngleX = 70F / radianF;
                RArm2.rotationPointX = -7F;
                RArm2.rotationPointY = 16F;
                RArm2.rotationPointZ = -14F;
                RArm3.rotationPointX = -9F;
                RArm3.rotationPointY = 17F;
                RArm3.rotationPointZ = -19F;
                RArm4.rotationPointX = -8F;
                RArm4.rotationPointY = 17F;
                RArm4.rotationPointZ = -19F;
                RArm4.rotateAngleY = 0F;
            }
        }

        /**
         * Babies animation
         */
        if (babies)
        {
            float fmov = f2 % 100;
            float fb1 = 0F;
            float fb2 = 142F / radianF;
            float fb3 = 49F / radianF;
            float fb4 = 155F / radianF;
            float fb5 = -68F / radianF;

            //fb2 -= fmov;
            //fb5 += fmov;
            //fb1 += (fmov/2F);
            //fb3 -= (fmov*2F);
            //fb4 -= fmov;

            if (fmov > 0F && fmov < 20F)
            {
                fb2 -= (MathHelper.cos((f2 * 0.8F)) * 0.3F);
                fb3 -= (MathHelper.cos((f2 * 0.6F)) * 0.2F);
                fb1 += (MathHelper.cos((f2 * 0.4F)) * 0.4F);
                fb5 += (MathHelper.cos((f2 * 0.7F)) * 0.5F);
            }

            if (fmov > 30F && fmov < 50F)
            {
                fb4 -= (MathHelper.cos((f2 * 0.8F)) * 0.4F);
                fb1 += (MathHelper.cos((f2 * 0.7F)) * 0.1F);
                fb3 -= (MathHelper.cos((f2 * 0.6F)) * 0.2F);
            }
            if (fmov > 80F)
            {
                fb5 += (MathHelper.cos((f2 * 0.2F)) * 0.4F);
                fb2 -= (MathHelper.cos((f2 * 0.6F)) * 0.3F);
                fb4 -= (MathHelper.cos((f2 * 0.4F)) * 0.2F);
            }

            /*if (fmov>0F && fmov < 30F)
            {
                fb2 -= fmov;
                fb5 += fmov;
            }
            
            if (fmov > 30F && fmov <70F)
            {
                fb1 += (fmov/2F);
                fb3 -= (fmov*2F);
            }
            if (fmov >70F)
            {
                fb4 -= fmov;
            }*/
            baby1.rotateAngleY = fb1;///radianF;
            baby2.rotateAngleY = fb2;///radianF;
            baby3.rotateAngleY = fb3;///radianF;
            baby4.rotateAngleY = fb4;///radianF;
            baby5.rotateAngleY = fb5;///radianF;
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

        Leg1A.rotateAngleX = -10F / radianF;
        Leg1A.rotateAngleZ = 75F / radianF;
        Leg1B.rotateAngleZ = 60F / radianF;
        Leg1C.rotateAngleZ = 75F / radianF;

        Leg1A.rotateAngleX += f9;
        Leg1B.rotateAngleX = Leg1A.rotateAngleX;
        Leg1C.rotateAngleX = Leg1A.rotateAngleX;

        Leg1A.rotateAngleZ += f13;
        Leg1B.rotateAngleZ += f13;
        Leg1C.rotateAngleZ += f13;

        Leg2A.rotateAngleX = -30F / radianF;
        Leg2A.rotateAngleZ = 70F / radianF;
        Leg2B.rotateAngleZ = 60F / radianF;
        Leg2C.rotateAngleZ = 70F / radianF;

        Leg2A.rotateAngleX += f10;
        Leg2B.rotateAngleX = Leg2A.rotateAngleX;
        Leg2C.rotateAngleX = Leg2A.rotateAngleX;

        Leg2A.rotateAngleZ += f14;
        Leg2B.rotateAngleZ += f14;
        Leg2C.rotateAngleZ += f14;

        Leg3A.rotateAngleX = -45F / radianF;
        Leg3A.rotateAngleZ = 70F / radianF;
        Leg3B.rotateAngleZ = 60F / radianF;
        Leg3C.rotateAngleZ = 70F / radianF;

        Leg3A.rotateAngleX += f11;
        Leg3B.rotateAngleX = Leg3A.rotateAngleX;
        Leg3C.rotateAngleX = Leg3A.rotateAngleX;

        Leg3A.rotateAngleZ += f15;
        Leg3B.rotateAngleZ += f15;
        Leg3C.rotateAngleZ += f15;

        Leg4A.rotateAngleX = -60F / radianF;
        Leg4A.rotateAngleZ = 70F / radianF;
        Leg4B.rotateAngleZ = 60F / radianF;
        Leg4C.rotateAngleZ = 70F / radianF;

        Leg4A.rotateAngleX += f12;
        Leg4B.rotateAngleX = Leg4A.rotateAngleX;
        Leg4C.rotateAngleX = Leg4A.rotateAngleX;

        Leg4A.rotateAngleZ += f16;
        Leg4B.rotateAngleZ += f16;
        Leg4C.rotateAngleZ += f16;

        Leg5A.rotateAngleX = -10F / radianF;
        Leg5A.rotateAngleZ = -75F / radianF;
        Leg5B.rotateAngleZ = -60F / radianF;
        Leg5C.rotateAngleZ = -75F / radianF;

        Leg5A.rotateAngleX -= f9;
        Leg5B.rotateAngleX = Leg5A.rotateAngleX;
        Leg5C.rotateAngleX = Leg5A.rotateAngleX;

        Leg5A.rotateAngleZ -= f13;
        Leg5B.rotateAngleZ -= f13;
        Leg5C.rotateAngleZ -= f13;

        Leg6A.rotateAngleX = -30F / radianF;
        Leg6A.rotateAngleZ = -70F / radianF;
        Leg6B.rotateAngleZ = -60F / radianF;
        Leg6C.rotateAngleZ = -70F / radianF;

        Leg6A.rotateAngleX -= f10;
        Leg6B.rotateAngleX = Leg6A.rotateAngleX;
        Leg6C.rotateAngleX = Leg6A.rotateAngleX;

        Leg6A.rotateAngleZ -= f14;
        Leg6B.rotateAngleZ -= f14;
        Leg6C.rotateAngleZ -= f14;

        Leg7A.rotateAngleX = -45F / radianF;
        Leg7A.rotateAngleZ = -70F / radianF;
        Leg7B.rotateAngleZ = -60F / radianF;
        Leg7C.rotateAngleZ = -70F / radianF;

        Leg7A.rotateAngleX -= f11;
        Leg7B.rotateAngleX = Leg7A.rotateAngleX;
        Leg7C.rotateAngleX = Leg7A.rotateAngleX;

        Leg7A.rotateAngleZ -= f15;
        Leg7B.rotateAngleZ -= f15;
        Leg7C.rotateAngleZ -= f15;

        Leg8A.rotateAngleX = -60F / radianF;
        Leg8A.rotateAngleZ = -70F / radianF;
        Leg8B.rotateAngleZ = -60F / radianF;
        Leg8C.rotateAngleZ = -70F / radianF;

        Leg8A.rotateAngleX -= f12;
        Leg8B.rotateAngleX = Leg8A.rotateAngleX;
        Leg8C.rotateAngleX = Leg8A.rotateAngleX;

        Leg8A.rotateAngleZ -= f16;
        Leg8B.rotateAngleZ -= f16;
        Leg8C.rotateAngleZ -= f16;

    }

}
