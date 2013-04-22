// TODO
// open mouth animation (done)
// eating animation
// change mane to the right center (done)
// change mule ears to the right center (done)
// bridles to be different when not ridden (done)
// legsaddles to extend when ridden (done)
// butterfly wings animation
// pegasus wing animation (done)
// pegasus resting wings (done)
// butterfly resting wings
// special fx for fairy horses (magic items), zombie horses(flies), ghost horses
// (?), unicorns (?), pegasus (?)
// articulated legs (done)
// frisky horse animation (done)
// stay animation (neck down) (done)
// when flying, the legs should stop moving and flip backwards or be flexed at
// the knee (done)

// flying pegasus:
// about 15-20 degrees horizontal
// about 45 when abduction
// about -15 when adduction
// if flying and not flapping, keep wings horizontal with small tremor and
// perhaps variation of elbow extension
// when user jumps, flap wings fully
// flap wings rarely as well

package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;

@SideOnly(Side.CLIENT)
public class MoCModelNewHorse extends ModelBase {

    ModelRenderer Head;
    ModelRenderer UMouth;
    ModelRenderer LMouth;
    ModelRenderer UMouth2;
    ModelRenderer LMouth2;
    ModelRenderer Unicorn;
    ModelRenderer Ear1;
    ModelRenderer Ear2;
    ModelRenderer MuleEarL;
    ModelRenderer MuleEarR;
    ModelRenderer Neck;
    ModelRenderer HeadSaddle;
    ModelRenderer Mane;

    ModelRenderer Body;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer TailC;

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

    ModelRenderer Bag1;
    ModelRenderer Bag2;

    ModelRenderer Saddle;
    ModelRenderer SaddleB;
    ModelRenderer SaddleC;

    ModelRenderer SaddleL;
    ModelRenderer SaddleL2;

    ModelRenderer SaddleR;
    ModelRenderer SaddleR2;

    ModelRenderer SaddleMouthL;
    ModelRenderer SaddleMouthR;

    ModelRenderer SaddleMouthLine;
    ModelRenderer SaddleMouthLineR;

    ModelRenderer MidWing;
    ModelRenderer InnerWing;
    ModelRenderer OuterWing;

    ModelRenderer InnerWingR;
    ModelRenderer MidWingR;
    ModelRenderer OuterWingR;

    ModelRenderer ButterflyL;
    ModelRenderer ButterflyR;

    //private float fMov1;
    //private float fMov2;
    //private boolean kneeSwitch;

    public MoCModelNewHorse()
    {
        textureWidth = 128;
        textureHeight = 128;

        Body = new ModelRenderer(this, 0, 34);
        Body.addBox(-5F, -8F, -19F, 10, 10, 24);
        Body.setRotationPoint(0F, 11F, 9F);

        /*Tail = new ModelRenderer(this, 24, 0);
        Tail.addBox(-1.5F, -2F, 3F, 3, 4, 14);
        Tail.setRotationPoint(0F, 3F, 14F);
        setRotation(Tail, -1.134464F, 0F, 0F);
        */

        TailA = new ModelRenderer(this, 44, 0);
        TailA.addBox(-1F, -1F, 0F, 2, 2, 3);
        TailA.setRotationPoint(0F, 3F, 14F);
        setRotation(TailA, -1.134464F, 0F, 0F);

        TailB = new ModelRenderer(this, 38, 7);
        TailB.addBox(-1.5F, -2F, 3F, 3, 4, 7);
        TailB.setRotationPoint(0F, 3F, 14F);
        setRotation(TailB, -1.134464F, 0F, 0F);

        TailC = new ModelRenderer(this, 24, 3);
        TailC.addBox(-1.5F, -4.5F, 9F, 3, 4, 7);
        TailC.setRotationPoint(0F, 3F, 14F);
        setRotation(TailC, -1.40215F, 0F, 0F);

        Leg1A = new ModelRenderer(this, 78, 29);
        Leg1A.addBox(-2.5F, -2F, -2.5F, 4, 9, 5);
        Leg1A.setRotationPoint(4F, 9F, 11F);

        Leg1B = new ModelRenderer(this, 78, 43);
        Leg1B.addBox(-2F, 0F, -1.5F, 3, 5, 3);
        Leg1B.setRotationPoint(4F, 16F, 11F);

        Leg1C = new ModelRenderer(this, 78, 51);
        Leg1C.addBox(-2.5F, 5.1F, -2F, 4, 3, 4);
        Leg1C.setRotationPoint(4F, 16F, 11F);

        Leg2A = new ModelRenderer(this, 96, 29);
        Leg2A.addBox(-1.5F, -2F, -2.5F, 4, 9, 5);
        Leg2A.setRotationPoint(-4F, 9F, 11F);

        Leg2B = new ModelRenderer(this, 96, 43);
        Leg2B.addBox(-1F, 0F, -1.5F, 3, 5, 3);
        Leg2B.setRotationPoint(-4F, 16F, 11F);

        Leg2C = new ModelRenderer(this, 96, 51);
        Leg2C.addBox(-1.5F, 5.1F, -2F, 4, 3, 4);
        Leg2C.setRotationPoint(-4F, 16F, 11F);

        Leg3A = new ModelRenderer(this, 44, 29);
        Leg3A.addBox(-1.9F, -1F, -2.1F, 3, 8, 4);
        Leg3A.setRotationPoint(4F, 9F, -8F);

        Leg3B = new ModelRenderer(this, 44, 41);
        Leg3B.addBox(-1.9F, 0F, -1.6F, 3, 5, 3);
        Leg3B.setRotationPoint(4F, 16F, -8F);

        Leg3C = new ModelRenderer(this, 44, 51);
        Leg3C.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
        Leg3C.setRotationPoint(4F, 16F, -8F);

        Leg4A = new ModelRenderer(this, 60, 29);
        Leg4A.addBox(-1.1F, -1F, -2.1F, 3, 8, 4);
        Leg4A.setRotationPoint(-4F, 9F, -8F);

        Leg4B = new ModelRenderer(this, 60, 41);
        Leg4B.addBox(-1.1F, 0F, -1.6F, 3, 5, 3);
        Leg4B.setRotationPoint(-4F, 16F, -8F);

        Leg4C = new ModelRenderer(this, 60, 51);
        Leg4C.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
        Leg4C.setRotationPoint(-4F, 16F, -8F);

        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-2.5F, -10F, -1.5F, 5, 5, 7);
        Head.setRotationPoint(0F, 4F, -10F);
        setRotation(Head, 0.5235988F, 0F, 0F);

        UMouth = new ModelRenderer(this, 24, 18);
        UMouth.addBox(-2F, -10F, -7F, 4, 3, 6);
        UMouth.setRotationPoint(0F, 4F, -10F);
        setRotation(UMouth, 0.5235988F, 0F, 0F);

        LMouth = new ModelRenderer(this, 24, 27);
        LMouth.addBox(-2F, -7F, -6.5F, 4, 2, 5);
        LMouth.setRotationPoint(0F, 4F, -10F);
        setRotation(LMouth, 0.5235988F, 0F, 0F);

        UMouth2 = new ModelRenderer(this, 24, 18);
        UMouth2.addBox(-2F, -10F, -8F, 4, 3, 6);
        UMouth2.setRotationPoint(0F, 4F, -10F);
        setRotation(UMouth2, 0.4363323F, 0F, 0F);

        LMouth2 = new ModelRenderer(this, 24, 27);
        LMouth2.addBox(-2F, -7F, -5.5F, 4, 2, 5);
        LMouth2.setRotationPoint(0F, 4F, -10F);
        setRotation(LMouth2, 0.7853982F, 0F, 0F);

        Unicorn = new ModelRenderer(this, 24, 0);
        Unicorn.addBox(-0.5F, -18F, 2F, 1, 8, 1);
        Unicorn.setRotationPoint(0F, 4F, -10F);
        setRotation(Unicorn, 0.5235988F, 0F, 0F);

        Ear1 = new ModelRenderer(this, 0, 0);
        Ear1.addBox(0.45F, -12F, 4F, 2, 3, 1);
        Ear1.setRotationPoint(0F, 4F, -10F);
        setRotation(Ear1, 0.5235988F, 0F, 0F);

        Ear2 = new ModelRenderer(this, 0, 0);
        Ear2.addBox(-2.45F, -12F, 4F, 2, 3, 1);
        Ear2.setRotationPoint(0F, 4F, -10F);
        setRotation(Ear2, 0.5235988F, 0F, 0F);

        MuleEarL = new ModelRenderer(this, 0, 12);
        MuleEarL.addBox(-2F, -16F, 4F, 2, 7, 1);
        MuleEarL.setRotationPoint(0F, 4F, -10F);
        setRotation(MuleEarL, 0.5235988F, 0F, 0.2617994F);

        MuleEarR = new ModelRenderer(this, 0, 12);
        MuleEarR.addBox(0F, -16F, 4F, 2, 7, 1);
        MuleEarR.setRotationPoint(0F, 4F, -10F);
        setRotation(MuleEarR, 0.5235988F, 0F, -0.2617994F);

        Neck = new ModelRenderer(this, 0, 12);
        Neck.addBox(-2.05F, -9.8F, -2F, 4, 14, 8);
        Neck.setRotationPoint(0F, 4F, -10F);
        setRotation(Neck, 0.5235988F, 0F, 0F);

        Bag1 = new ModelRenderer(this, 0, 34);
        Bag1.addBox(-3F, 0F, 0F, 8, 8, 3);
        Bag1.setRotationPoint(-7.5F, 3F, 10F);
        setRotation(Bag1, 0F, 1.570796F, 0F);

        Bag2 = new ModelRenderer(this, 0, 47);
        Bag2.addBox(-3F, 0F, 0F, 8, 8, 3);
        Bag2.setRotationPoint(4.5F, 3F, 10F);
        setRotation(Bag2, 0F, 1.570796F, 0F);

        Saddle = new ModelRenderer(this, 80, 0);
        Saddle.addBox(-5F, 0F, -3F, 10, 1, 8);
        Saddle.setRotationPoint(0F, 2F, 2F);

        SaddleB = new ModelRenderer(this, 106, 9);
        SaddleB.addBox(-1.5F, -1F, -3F, 3, 1, 2);
        SaddleB.setRotationPoint(0F, 2F, 2F);

        SaddleC = new ModelRenderer(this, 80, 9);
        SaddleC.addBox(-4F, -1F, 3F, 8, 1, 2);
        SaddleC.setRotationPoint(0F, 2F, 2F);

        SaddleL2 = new ModelRenderer(this, 74, 0);
        SaddleL2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        SaddleL2.setRotationPoint(5F, 3F, 2F);

        SaddleL = new ModelRenderer(this, 70, 0);
        SaddleL.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        SaddleL.setRotationPoint(5F, 3F, 2F);

        SaddleR2 = new ModelRenderer(this, 74, 4);
        SaddleR2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        SaddleR2.setRotationPoint(-5F, 3F, 2F);

        SaddleR = new ModelRenderer(this, 80, 0);
        SaddleR.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        SaddleR.setRotationPoint(-5F, 3F, 2F);

        SaddleMouthL = new ModelRenderer(this, 74, 13);
        SaddleMouthL.addBox(1.5F, -8F, -4F, 1, 2, 2);
        SaddleMouthL.setRotationPoint(0F, 4F, -10F);
        setRotation(SaddleMouthL, 0.5235988F, 0F, 0F);

        SaddleMouthR = new ModelRenderer(this, 74, 13);
        SaddleMouthR.addBox(-2.5F, -8F, -4F, 1, 2, 2);
        SaddleMouthR.setRotationPoint(0F, 4F, -10F);
        setRotation(SaddleMouthR, 0.5235988F, 0F, 0F);

        SaddleMouthLine = new ModelRenderer(this, 44, 10);
        SaddleMouthLine.addBox(2.6F, -6F, -6F, 0, 3, 16);
        SaddleMouthLine.setRotationPoint(0F, 4F, -10F);

        SaddleMouthLineR = new ModelRenderer(this, 44, 5);
        SaddleMouthLineR.addBox(-2.6F, -6F, -6F, 0, 3, 16);
        SaddleMouthLineR.setRotationPoint(0F, 4F, -10F);

        Mane = new ModelRenderer(this, 58, 0);
        Mane.addBox(-1F, -11.5F, 5F, 2, 16, 4);
        Mane.setRotationPoint(0F, 4F, -10F);
        //Mane.addBox(-1F, -9.5F, 6F, 2, 16, 4);
        //Mane.setRotationPoint(0F, 3F, -12F);
        setRotation(Mane, 0.5235988F, 0F, 0F);

        HeadSaddle = new ModelRenderer(this, 80, 12);
        HeadSaddle.addBox(-2.5F, -10.1F, -7F, 5, 5, 12, 0.2F);
        HeadSaddle.setRotationPoint(0F, 4F, -10F);
        setRotation(HeadSaddle, 0.5235988F, 0F, 0F);

        MidWing = new ModelRenderer(this, 82, 68);
        MidWing.addBox(1F, 0.1F, 1F, 12, 2, 11);
        MidWing.setRotationPoint(5F, 3F, -6F);
        setRotation(MidWing, 0F, 0.0872665F, 0F);

        InnerWing = new ModelRenderer(this, 0, 96);
        InnerWing.addBox(0F, 0F, 0F, 7, 2, 11);
        InnerWing.setRotationPoint(5F, 3F, -6F);
        setRotation(InnerWing, 0F, -0.3490659F, 0F);

        OuterWing = new ModelRenderer(this, 0, 68);
        OuterWing.addBox(0F, 0F, 0F, 22, 2, 11);
        OuterWing.setRotationPoint(17F, 3F, -6F);
        setRotation(OuterWing, 0F, -0.3228859F, 0F);

        InnerWingR = new ModelRenderer(this, 0, 110);
        InnerWingR.addBox(-7F, 0F, 0F, 7, 2, 11);
        InnerWingR.setRotationPoint(-5F, 3F, -6F);
        setRotation(InnerWingR, 0F, 0.3490659F, 0F);

        MidWingR = new ModelRenderer(this, 82, 82);
        MidWingR.addBox(-13F, 0.1F, 1F, 12, 2, 11);
        MidWingR.setRotationPoint(-5F, 3F, -6F);
        setRotation(MidWingR, 0F, -0.0872665F, 0F);

        OuterWingR = new ModelRenderer(this, 0, 82);
        OuterWingR.addBox(-22F, 0F, 0F, 22, 2, 11);
        OuterWingR.setRotationPoint(-17F, 3F, -6F);
        setRotation(OuterWingR, 0F, 0.3228859F, 0F);

        ButterflyL = new ModelRenderer(this, 0, 98);
        ButterflyL.addBox(-1F, 0F, -14F, 26, 0, 30);
        ButterflyL.setRotationPoint(4.5F, 3F, -2F);
        setRotation(ButterflyL, 0F, 0F, -0.78539F);

        ButterflyR = new ModelRenderer(this, 0, 68);
        ButterflyR.addBox(-25F, 0F, -14F, 26, 0, 30);
        ButterflyR.setRotationPoint(-4.5F, 3F, -2F);
        setRotation(ButterflyR, 0F, 0F, 0.78539F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        MoCEntityHorse entityhorse = (MoCEntityHorse) entity;
        //super.render(entity, f, f1, f2, f3, f4, f5);

        int type = entityhorse.getType();
        int vanishingInt = entityhorse.getVanishC();
        int wingflapInt = entityhorse.wingFlapCounter;
        boolean flapwings = (entityhorse.wingFlapCounter != 0);
        boolean shuffling = (entityhorse.shuffleCounter != 0);
        boolean saddled = entityhorse.getIsRideable();
        boolean wings = (entityhorse.isFlyer() && !entityhorse.isGhost() && type < 45);
        //boolean chested = entityhorse.getChestedHorse();
        boolean eating = entityhorse.getEating();
        //boolean flyer = entityhorse.isFlyer();
        boolean standing = (entityhorse.standCounter != 0 && entityhorse.riddenByEntity == null);
        boolean openMouth = (entityhorse.mouthCounter != 0);
        boolean moveTail = (entityhorse.tailCounter != 0);

        boolean rider = (entityhorse.riddenByEntity != null);
        boolean floating = (entityhorse.isGhost() || (entityhorse.isFlyer() && entityhorse.isOnAir()));
        //                || (entityhorse.riddenByEntity == null && !entityhorse.onGround)
        //                || (entityhorse.riddenByEntity != null && !entityhorse.riddenByEntity.onGround));
        setRotationAngles(f, f1, f2, f3, f4, f5, eating, rider, floating, standing, saddled, moveTail, wings, flapwings, shuffling, type);

        if (!entityhorse.isGhost() && vanishingInt == 0)
        {
            if (saddled)
            {
                HeadSaddle.render(f5);
                Saddle.render(f5);
                SaddleB.render(f5);
                SaddleC.render(f5);
                SaddleL.render(f5);
                SaddleL2.render(f5);
                SaddleR.render(f5);
                SaddleR2.render(f5);
                SaddleMouthL.render(f5);
                SaddleMouthR.render(f5);
                if (rider)
                {
                    SaddleMouthLine.render(f5);
                    SaddleMouthLineR.render(f5);
                }

            }

            if (type == 65 || type == 66 || type == 67) //mule, donkey or zonkey
            {
                MuleEarL.render(f5);
                MuleEarR.render(f5);
            }
            else
            {
                Ear1.render(f5);
                Ear2.render(f5);

            }
            Neck.render(f5);
            Head.render(f5);
            if (openMouth)
            {
                UMouth2.render(f5);
                LMouth2.render(f5);
            }
            else
            {
                UMouth.render(f5);
                LMouth.render(f5);
            }
            Mane.render(f5);
            Body.render(f5);
            TailA.render(f5);
            TailB.render(f5);
            TailC.render(f5);

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

            if (entityhorse.isUnicorned())
            {
                Unicorn.render(f5);
            }

            if (entityhorse.getChestedHorse())
            {
                Bag1.render(f5);
                Bag2.render(f5);
            }

            if (entityhorse.isFlyer() && type < 45)//pegasus
            {
                MidWing.render(f5);
                InnerWing.render(f5);
                OuterWing.render(f5);
                InnerWingR.render(f5);
                MidWingR.render(f5);
                OuterWingR.render(f5);
            }
            else if (type > 44 && type < 60)//fairys
            {
                GL11.glPushMatrix();
                GL11.glEnable(3042 /*GL_BLEND*/);
                float transparency = 0.9F;
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(1.2F, 1.2F, 1.2F, transparency);
                GL11.glScalef(1.3F, 1.0F, 1.3F);
                ButterflyL.render(f5);
                ButterflyR.render(f5);
                GL11.glDisable(3042/*GL_BLEND*/);
                GL11.glPopMatrix();
            }/*else
                {
                ButterflyL.render(f5);
                   ButterflyR.render(f5);
                }*/

        }
        else
        //rendering a ghost or vanishing
        {
            float transparency; //= entityhorse.tFloat();

            if (vanishingInt != 0)// && vanishingInt > 30)
            {
                transparency = 1.0F - (((float) (vanishingInt)) / 100);
            }
            else
            {
                transparency = entityhorse.tFloat();
            }

            GL11.glPushMatrix();
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            GL11.glScalef(1.3F, 1.0F, 1.3F);

            Ear1.render(f5);
            Ear2.render(f5);

            Neck.render(f5);
            Head.render(f5);
            if (openMouth)
            {
                UMouth2.render(f5);
                LMouth2.render(f5);
            }
            else
            {
                UMouth.render(f5);
                LMouth.render(f5);
            }

            Mane.render(f5);
            Body.render(f5);
            TailA.render(f5);
            TailB.render(f5);
            TailC.render(f5);

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

            if (type == 39 || type == 40 || type == 28)
            {
                MidWing.render(f5);
                InnerWing.render(f5);
                OuterWing.render(f5);
                InnerWingR.render(f5);
                MidWingR.render(f5);
                OuterWingR.render(f5);
            }
            if (type >= 50 && type < 60)
            {
                ButterflyL.render(f5);
                ButterflyR.render(f5);
            }

            if (saddled)
            {
                HeadSaddle.render(f5);
                Saddle.render(f5);
                SaddleB.render(f5);
                SaddleC.render(f5);
                SaddleL.render(f5);
                SaddleL2.render(f5);
                SaddleR.render(f5);
                SaddleR2.render(f5);
                SaddleMouthL.render(f5);
                SaddleMouthR.render(f5);
                if (rider)
                {
                    SaddleMouthLine.render(f5);
                    SaddleMouthLineR.render(f5);
                }

            }

            GL11.glDisable(3042/*GL_BLEND*/);
            GL11.glPopMatrix();

            if (type == 21 || type == 22)//|| (type >=50 && type <60))
            {
                float wingTransparency = 0F;
                if (wingflapInt != 0)
                {
                    wingTransparency = 1F - (((float) wingflapInt) / 25);
                }
                if (wingTransparency > transparency)
                {
                    wingTransparency = transparency;
                }
                GL11.glPushMatrix();
                GL11.glEnable(3042 /*GL_BLEND*/);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(0.8F, 0.8F, 0.8F, wingTransparency);
                GL11.glScalef(1.3F, 1.0F, 1.3F);
                ButterflyL.render(f5);
                ButterflyR.render(f5);
                GL11.glDisable(3042/*GL_BLEND*/);
                GL11.glPopMatrix();
            }
        }

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean eating, boolean rider, boolean floating, boolean standing, boolean saddled, boolean tail, boolean wings, boolean flapwings, boolean shuffle, int type)
    {
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        float HeadXRot = (f4 / 57.29578F);
        if (f3 > 20F)
        {
            f3 = 20F;
        }
        if (f3 < -20F)
        {
            f3 = -20F;
        }

        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */

        /*if (rider)
        {
          //System.out.println("f = " + f);
          //System.out.println("f1 = " + f1);
          //System.out.println("f2 = " + f2);
          
        }*/

        if (shuffle)
        {
            HeadXRot = HeadXRot + (MathHelper.cos(f2 * 0.4F) * 0.15F);
        }
        else if (f1 > 0.2F && !floating)
        {
            HeadXRot = HeadXRot + (MathHelper.cos(f * 0.4F) * 0.15F * f1);
        }

        Head.rotationPointY = 4.0F;
        Head.rotationPointZ = -10F;
        Head.rotateAngleX = 0.5235988F + (HeadXRot);
        Head.rotateAngleY = (f3 / 57.29578F);//fixes SMP bug
        TailA.rotationPointY = 3F;
        TailB.rotationPointZ = 14F;
        Bag2.rotationPointY = 3F;
        Bag2.rotationPointZ = 10F;
        Body.rotateAngleX = 0F;

        if (standing && !shuffle) //standing on hind legs
        {
            Head.rotationPointY = -6F;
            Head.rotationPointZ = -1F;
            Head.rotateAngleX = (15 / 57.29578F) + (HeadXRot);
            ;//120 degrees
            Head.rotateAngleY = (f3 / 57.29578F);
            TailA.rotationPointY = 9F;
            TailB.rotationPointZ = 18F;
            Bag2.rotationPointY = 5.5F;
            Bag2.rotationPointZ = 15F;
            Body.rotateAngleX = -45 / 57.29578F;

        }
        else if (eating && !shuffle)//neck down
        {
            Head.rotationPointY = 11.0F; //new lower position
            Head.rotationPointZ = -10F;
            Head.rotateAngleX = 2.18166F;//120 degrees
            Head.rotateAngleY = 0.0F;//don't twist your neck if eating

        }

        Ear1.rotationPointY = Head.rotationPointY;
        Ear2.rotationPointY = Head.rotationPointY;
        MuleEarL.rotationPointY = Head.rotationPointY;
        MuleEarR.rotationPointY = Head.rotationPointY;
        Neck.rotationPointY = Head.rotationPointY;
        UMouth.rotationPointY = Head.rotationPointY;
        UMouth2.rotationPointY = Head.rotationPointY;
        LMouth.rotationPointY = Head.rotationPointY;
        LMouth2.rotationPointY = Head.rotationPointY;
        Mane.rotationPointY = Head.rotationPointY;
        Unicorn.rotationPointY = Head.rotationPointY;

        Ear1.rotationPointZ = Head.rotationPointZ;
        Ear2.rotationPointZ = Head.rotationPointZ;
        MuleEarL.rotationPointZ = Head.rotationPointZ;
        MuleEarR.rotationPointZ = Head.rotationPointZ;
        Neck.rotationPointZ = Head.rotationPointZ;
        UMouth.rotationPointZ = Head.rotationPointZ;
        UMouth2.rotationPointZ = Head.rotationPointZ;
        LMouth.rotationPointZ = Head.rotationPointZ;
        LMouth2.rotationPointZ = Head.rotationPointZ;
        Mane.rotationPointZ = Head.rotationPointZ;
        Unicorn.rotationPointZ = Head.rotationPointZ;

        Ear1.rotateAngleX = Head.rotateAngleX;
        Ear2.rotateAngleX = Head.rotateAngleX;
        MuleEarL.rotateAngleX = Head.rotateAngleX;
        MuleEarR.rotateAngleX = Head.rotateAngleX;
        Neck.rotateAngleX = Head.rotateAngleX;
        UMouth.rotateAngleX = Head.rotateAngleX;
        UMouth2.rotateAngleX = Head.rotateAngleX - 0.0872664F;
        LMouth.rotateAngleX = Head.rotateAngleX;
        LMouth2.rotateAngleX = Head.rotateAngleX + 0.261799F;
        Mane.rotateAngleX = Head.rotateAngleX;
        Unicorn.rotateAngleX = Head.rotateAngleX;

        Ear1.rotateAngleY = Head.rotateAngleY;
        Ear2.rotateAngleY = Head.rotateAngleY;
        MuleEarL.rotateAngleY = Head.rotateAngleY;
        MuleEarR.rotateAngleY = Head.rotateAngleY;
        Neck.rotateAngleY = Head.rotateAngleY;
        UMouth.rotateAngleY = Head.rotateAngleY;
        LMouth.rotateAngleY = Head.rotateAngleY;
        UMouth2.rotateAngleY = Head.rotateAngleY;
        LMouth2.rotateAngleY = Head.rotateAngleY;
        Mane.rotateAngleY = Head.rotateAngleY;
        Unicorn.rotateAngleY = Head.rotateAngleY;

        //(if chested)
        Bag1.rotateAngleX = RLegXRot / 5F;//(MathHelper.cos(f * 0.6662F) * 1.4F * f2) / 10F;
        Bag2.rotateAngleX = -RLegXRot / 5F;//(MathHelper.cos(f * 0.6662F) * 1.4F * f2) / 10F;

        if (wings)
        {
            InnerWing.rotateAngleX = Body.rotateAngleX;
            MidWing.rotateAngleX = Body.rotateAngleX;
            OuterWing.rotateAngleX = Body.rotateAngleX;
            InnerWingR.rotateAngleX = Body.rotateAngleX;
            MidWingR.rotateAngleX = Body.rotateAngleX;
            OuterWingR.rotateAngleX = Body.rotateAngleX;

            if (standing)
            {
                InnerWing.rotationPointY = -5F;
                InnerWing.rotationPointZ = 4F;
            }
            else
            {
                InnerWing.rotationPointY = 3F;
                InnerWing.rotationPointZ = -6F;
            }

            /**
             * flapping wings or cruising. IF flapping wings, move up and down.
             * if cruising, movement depends of speed
             */
            float WingRot = 0F;
            if (flapwings)
            {
                WingRot = MathHelper.cos((f2 * 0.3F) + 3.141593F) * 1.2F;// * f1;
            }
            else
            //cruising
            {
                //WingRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
                WingRot = MathHelper.cos((f * 0.5F)) * 0.1F;//* 1.2F * f1;
            }

            //float WingRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * f1 * 1.2F;
            //InnerWing.setRotationPoint(5F, 3F, -6F);
            //setRotation(InnerWing, 0F, -0.3490659F, 0F);
            //X dist = 12    
            //OuterWing.setRotationPoint(17F, 3F, -6F);
            //setRotation(OuterWing, 0F, -0.3228859F, 0F);

            if (floating)
            {
                OuterWing.rotateAngleY = -0.3228859F + (WingRot / 2F);
                OuterWingR.rotateAngleY = 0.3228859F - (WingRot / 2F);

            }
            else
            {
                WingRot = 60 / 57.29578F;//0.7854F;
                OuterWing.rotateAngleY = -90 / 57.29578F;//-1.396F;
                OuterWingR.rotateAngleY = 90 / 57.29578F;//1.396F;
            }

            InnerWingR.rotationPointY = InnerWing.rotationPointY;
            InnerWingR.rotationPointZ = InnerWing.rotationPointZ;

            //OuterWing.rotationPointX = InnerWing.rotationPointX + (MathHelper.cos(WingRot)*12F);
            //the rotation point X rotates depending of the cos of rotation times the distance of the other block:
            //cos (WingRot) * 12F
            //the rotation PointX of Innerwing = 5
            //the rotation PointX of Outerwing = 17
            //the difference = 12.
            // for the rotation point Y, sin is used instead.
            //OuterWing.rotationPointX = InnerWing.rotationPointX + (MathHelper.cos(WingRot)*12F);
            //OuterWing.rotationPointY = InnerWing.rotationPointY + (MathHelper.sin(WingRot)*12F);

            OuterWing.rotationPointX = InnerWing.rotationPointX + (MathHelper.cos(WingRot) * 12F);
            OuterWingR.rotationPointX = InnerWingR.rotationPointX - (MathHelper.cos(WingRot) * 12F);

            MidWing.rotationPointY = InnerWing.rotationPointY;
            MidWingR.rotationPointY = InnerWing.rotationPointY;
            OuterWing.rotationPointY = InnerWing.rotationPointY + (MathHelper.sin(WingRot) * 12F);
            OuterWingR.rotationPointY = InnerWingR.rotationPointY + (MathHelper.sin(WingRot) * 12F);

            MidWing.rotationPointZ = InnerWing.rotationPointZ;
            MidWingR.rotationPointZ = InnerWing.rotationPointZ;
            OuterWing.rotationPointZ = InnerWing.rotationPointZ;
            OuterWingR.rotationPointZ = InnerWing.rotationPointZ;

            MidWing.rotateAngleZ = WingRot;
            InnerWing.rotateAngleZ = WingRot;
            OuterWing.rotateAngleZ = WingRot;

            InnerWingR.rotateAngleZ = -WingRot;
            MidWingR.rotateAngleZ = -WingRot;
            OuterWingR.rotateAngleZ = -WingRot;

            //45deg = 0.7854F
            //1.396 (80degrees folded)
            /*//rear left.  -4X(ignored), 9Y, 11Z  the distance is 7Y
              Leg1B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) + LLegXRot )*7F);
              Leg1B.rotationPointZ = 11F + (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F);
              
              //rear right
              Leg2B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) + RLegXRot )*7F);
              Leg2B.rotationPointZ = 11F + (MathHelper.cos((270/ 57.29578F) + RLegXRot )*7F);
              
              //front left 4X(ign), 9Y, -8Z, the distance is again 7Y
              Leg3B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) + RLegXRot )*7F);
              Leg3B.rotationPointZ = -8F + (MathHelper.cos((270/ 57.29578F) + RLegXRot )*7F);
              
              //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
              Leg4B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) + LLegXRot )*7F);
              Leg4B.rotationPointZ = -8F + (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F);*/

        }

        if (type > 44 && type < 60 || type == 21) //butterfly horses or ghost horse
        {
            /**
             * buttefly to have two / 3 movs: 1 slow movement when idle on
             * ground has to be random from closing up to horizontal 2 fast wing
             * flapping flying movement, short range close to 0 degree RLegXRot
             * = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
             */

            /**
             * f = distance walked f1 = speed 0 - 1 f2 = timer
             */

            float f2a = f2 % 100F;
            float WingRot = 0F;

            if (type != 21)
            {
                //for butterfly horses
                if (flapwings) //when user hits space or randomly
                {
                    WingRot = MathHelper.cos((f2 * 0.9F)) * 0.9F;

                }
                else
                //default movement
                {
                    if (floating) //cruising
                    {
                        WingRot = MathHelper.cos((f2 * 0.6662F)) * 0.5F;
                    }
                    else
                    {
                        if (f2a > 40 & f2a < 60) //random movement
                        {
                            WingRot = MathHelper.cos((f2 * 0.15F)) * 1.20F;
                        }
                    }

                }
            }

            else
            //for ghost horse
            {
                WingRot = MathHelper.cos((f2 * 0.1F));//* 0.2F;
            }

            //from regular horse
            /*if (flapwings)
            {
              WingRot = MathHelper.cos((f2 * 0.3F) + 3.141593F) * 1.2F;// * f1;
            }else //cruising
            {
              //WingRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
              WingRot = MathHelper.cos((f * 0.5F)) *0.1F ;//* 1.2F * f1;
            }*/

            /**
             * this part is needed for position and angle of the butterfly wings
             * and ghost horse wings
             */
            if (standing)
            {
                ButterflyL.rotationPointY = -2.5F;
                ButterflyL.rotationPointZ = 6.5F;
            }
            else
            {
                ButterflyL.rotationPointY = 3F;
                ButterflyL.rotationPointZ = -2F;
            }

            ButterflyR.rotationPointY = ButterflyL.rotationPointY;
            ButterflyR.rotationPointZ = ButterflyL.rotationPointZ;
            ButterflyL.rotateAngleX = Body.rotateAngleX;
            ButterflyR.rotateAngleX = Body.rotateAngleX;

            //this to be added for the ghost or adjusted
            //ButterflyL.rotateAngleZ = -0.52359F + RLegXRot;
            //ButterflyR.rotateAngleZ = 0.52359F -RLegXRot;

            float baseAngle = 0.52359F;
            if (type == 21)
            {
                baseAngle = 0F;
            }
            ButterflyL.rotateAngleZ = -baseAngle + WingRot;
            ButterflyR.rotateAngleZ = baseAngle - WingRot;
        }

        /**
         * knee joints Leg1 and Leg4 use LLegXRot Leg2 and Leg3 use RLegXRot
         */
        //RLegXRot = 45/57.29578F;
        //fMov1 = RLegXRot;
        float RLegXRotB = RLegXRot;
        float LLegXRotB = LLegXRot;
        float RLegXRotC = RLegXRot;
        float LLegXRotC = LLegXRot;

        if (floating)
        {
            RLegXRot = (15 / 57.29578F);
            LLegXRot = RLegXRot;
            RLegXRotB = (45 / 57.29578F);
            RLegXRotC = RLegXRotB;
            LLegXRotB = RLegXRotB;
            LLegXRotC = RLegXRotB;
        }

        if (standing)
        {
            Leg3A.rotationPointY = -2F;
            Leg3A.rotationPointZ = -2F;
            Leg4A.rotationPointY = Leg3A.rotationPointY;
            Leg4A.rotationPointZ = Leg3A.rotationPointZ;

            RLegXRot = (-60 / 57.29578F) + MathHelper.cos((f2 * 0.4F) + 3.141593F);
            LLegXRot = (-60 / 57.29578F) + MathHelper.cos(f2 * 0.4F);

            RLegXRotB = (45 / 57.29578F);
            LLegXRotB = RLegXRotB;

            RLegXRotC = (-15 / 57.29578F);
            LLegXRotC = (15 / 57.29578F);

            Leg3B.rotationPointY = Leg3A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            Leg3B.rotationPointZ = Leg3A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            Leg4B.rotationPointY = Leg4A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            Leg4B.rotationPointZ = Leg4A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            Leg1B.rotationPointY = Leg1A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRotC) * 7F);
            Leg1B.rotationPointZ = Leg1A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRotC) * 7F);

            //rear right
            Leg2B.rotationPointY = Leg1B.rotationPointY;// Leg2A.rotationPointY + (MathHelper.sin((90/ 57.29578F) + RLegXRotC )*7F);
            Leg2B.rotationPointZ = Leg1B.rotationPointZ;//Leg2A.rotationPointZ + (MathHelper.cos((270/ 57.29578F) + RLegXRotC )*7F);

            Leg1A.rotateAngleX = RLegXRotC;
            Leg1B.rotateAngleX = LLegXRotC;
            Leg1C.rotateAngleX = Leg1B.rotateAngleX;

            Leg2A.rotateAngleX = RLegXRotC;
            Leg2B.rotateAngleX = LLegXRotC;
            Leg2C.rotateAngleX = Leg2B.rotateAngleX;

            Leg3A.rotateAngleX = RLegXRot;
            Leg3B.rotateAngleX = RLegXRotB;
            Leg3C.rotateAngleX = RLegXRotB;

            Leg4A.rotateAngleX = LLegXRot;
            Leg4B.rotateAngleX = LLegXRotB;
            Leg4C.rotateAngleX = LLegXRotB;
        }

        else
        //not standing
        {
            Leg3A.rotationPointY = 9F;
            Leg3A.rotationPointZ = -8F;
            Leg4A.rotationPointY = Leg3A.rotationPointY;
            Leg4A.rotationPointZ = Leg3A.rotationPointZ;

            if (!floating && f1 > 0.2F)
            {

                float RLegXRot2 = MathHelper.cos(((f + 0.1F) * 0.6662F) + 3.141593F) * 0.8F * f1;
                float LLegXRot2 = MathHelper.cos((f + 0.1F) * 0.6662F) * 0.8F * f1;
                if (RLegXRot > RLegXRot2) // - - >
                {
                    RLegXRotB = RLegXRot + (55 / 57.29578F);
                    //LLegXRotB = LLegXRot + (55/57.29578F);

                }
                if (RLegXRot < RLegXRot2) // < - -
                {
                    RLegXRotC = RLegXRot + (15 / 57.29578F);
                    //LLegXRotC = LLegXRot - (15/57.29578F);

                }
                if (LLegXRot > LLegXRot2) // - - >
                {
                    LLegXRotB = LLegXRot + (55 / 57.29578F);
                }
                if (LLegXRot < LLegXRot2) // < - -
                {
                    LLegXRotC = LLegXRot + (15 / 57.29578F);
                }

            }

            Leg1B.rotationPointY = Leg1A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            Leg1B.rotationPointZ = Leg1A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //rear right
            Leg2B.rotationPointY = Leg2A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            Leg2B.rotationPointZ = Leg2A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front left 4X(ign), 9Y, -8Z, the distance is again 7Y
            Leg3B.rotationPointY = Leg3A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            Leg3B.rotationPointZ = Leg3A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            Leg4B.rotationPointY = Leg4A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            Leg4B.rotationPointZ = Leg4A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            Leg1A.rotateAngleX = LLegXRot;
            Leg1B.rotateAngleX = LLegXRotC;
            Leg1C.rotateAngleX = LLegXRotC;

            Leg2A.rotateAngleX = RLegXRot;
            Leg2B.rotateAngleX = RLegXRotC;
            Leg2C.rotateAngleX = RLegXRotC;

            Leg3A.rotateAngleX = RLegXRot;
            Leg3B.rotateAngleX = RLegXRotB;
            Leg3C.rotateAngleX = RLegXRotB;

            Leg4A.rotateAngleX = LLegXRot;
            Leg4B.rotateAngleX = LLegXRotB;
            Leg4C.rotateAngleX = LLegXRotB;

        }

        if (type == 60 && shuffle)
        {
            Leg3A.rotationPointY = 9F;
            Leg3A.rotationPointZ = -8F;
            Leg4A.rotationPointY = Leg3A.rotationPointY;
            Leg4A.rotationPointZ = Leg3A.rotationPointZ;

            if (!floating)//&& f1 > 0.2F)
            {

                //float RLegXRot2 = MathHelper.cos(f2 * 0.4F);//MathHelper.cos(((f+0.1F) * 0.6662F) + 3.141593F) * 1.4F * f1;
                RLegXRot = MathHelper.cos(f2 * 0.4F);
                if (RLegXRot > 0.1F)
                {
                    RLegXRot = 0.3F;
                }
                //if (RLegXRot < -0.5F) RLegXRotB = RLegXRot + (45/57.29578F);
                LLegXRot = MathHelper.cos((f2 * 0.4F) + 3.141593F);
                if (LLegXRot > 0.1F)
                {
                    LLegXRot = 0.3F;
                }

                /*if (RLegXRot > RLegXRot2) // - - >
                {
                  RLegXRotB = RLegXRot + (55/57.29578F);
                  LLegXRotB = LLegXRot + (55/57.29578F);
                  
                  
                }
                if (RLegXRot < RLegXRot2) // < - -
                {
                  RLegXRotC = RLegXRot - (15/57.29578F);
                  LLegXRotC = LLegXRot - (15/57.29578F);
                  
                  
                }  */

                /*float RLegXRot2 = MathHelper.cos(((f+0.1F) * 0.6662F) + 3.141593F) * 1.4F * f1;
                
                if (RLegXRot > RLegXRot2) // - - >
                {
                  RLegXRotB = RLegXRot + (55/57.29578F);
                  LLegXRotB = LLegXRot + (55/57.29578F);
                  
                  
                }
                if (RLegXRot < RLegXRot2) // < - -
                {
                  RLegXRotC = RLegXRot - (15/57.29578F);
                  LLegXRotC = LLegXRot - (15/57.29578F);
                  
                  
                }  */
            }

            Leg1B.rotationPointY = Leg1A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            Leg1B.rotationPointZ = Leg1A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //rear right
            Leg2B.rotationPointY = Leg2A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            Leg2B.rotationPointZ = Leg2A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            //front left 4X(ign), 9Y, -8Z, the distance is again 7Y
            Leg3B.rotationPointY = Leg3A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + LLegXRot) * 7F);
            Leg3B.rotationPointZ = Leg3A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + LLegXRot) * 7F);

            //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
            Leg4B.rotationPointY = Leg4A.rotationPointY + (MathHelper.sin((90 / 57.29578F) + RLegXRot) * 7F);
            Leg4B.rotationPointZ = Leg4A.rotationPointZ + (MathHelper.cos((270 / 57.29578F) + RLegXRot) * 7F);

            Leg1A.rotateAngleX = LLegXRot;// + rand.nextFloat();
            Leg1B.rotateAngleX = LLegXRotB;
            Leg1C.rotateAngleX = LLegXRotB;

            Leg3A.rotateAngleX = LLegXRot;
            Leg3B.rotateAngleX = LLegXRotB;
            Leg3C.rotateAngleX = LLegXRotB;

            Leg2A.rotateAngleX = RLegXRot;
            Leg2B.rotateAngleX = RLegXRotB;
            Leg2C.rotateAngleX = RLegXRotB;

            Leg4A.rotateAngleX = RLegXRot;
            Leg4B.rotateAngleX = RLegXRotB;
            Leg4C.rotateAngleX = RLegXRotB;

        }

        Leg1C.rotationPointY = Leg1B.rotationPointY;
        Leg1C.rotationPointZ = Leg1B.rotationPointZ;
        Leg2C.rotationPointY = Leg2B.rotationPointY;
        Leg2C.rotationPointZ = Leg2B.rotationPointZ;
        Leg3C.rotationPointY = Leg3B.rotationPointY;
        Leg3C.rotationPointZ = Leg3B.rotationPointZ;
        Leg4C.rotationPointY = Leg4B.rotationPointY;
        Leg4C.rotationPointZ = Leg4B.rotationPointZ;

        /*//rear left.  -4X(ignored), 9Y, 11Z  the distance is 7Y
              Leg1B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) + LLegXRot )*7F);
              Leg1B.rotationPointZ = 11F + (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F);
              
              //rear right
              Leg2B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) + RLegXRot )*7F);
              Leg2B.rotationPointZ = 11F + (MathHelper.cos((270/ 57.29578F) + RLegXRot )*7F);
              
              //front left 4X(ign), 9Y, -8Z, the distance is again 7Y
              Leg3B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) + RLegXRot )*7F);
              Leg3B.rotationPointZ = -8F + (MathHelper.cos((270/ 57.29578F) + RLegXRot )*7F);
              
              //front right -4X(ign), 9Y, -8Z, the distance is again 7Y
              Leg4B.rotationPointY = 9F + (MathHelper.sin((90/ 57.29578F) + LLegXRot )*7F);
              Leg4B.rotationPointZ = -8F + (MathHelper.cos((270/ 57.29578F) + LLegXRot )*7F);*/

        if (saddled)
        {

            if (standing)
            {
                Saddle.rotationPointY = 0.5F;
                Saddle.rotationPointZ = 11F;

            }
            else
            {
                Saddle.rotationPointY = 2F;
                Saddle.rotationPointZ = 2F;

            }

            SaddleB.rotationPointY = Saddle.rotationPointY;
            SaddleC.rotationPointY = Saddle.rotationPointY;
            SaddleL.rotationPointY = Saddle.rotationPointY;
            SaddleR.rotationPointY = Saddle.rotationPointY;
            SaddleL2.rotationPointY = Saddle.rotationPointY;
            SaddleR2.rotationPointY = Saddle.rotationPointY;
            Bag1.rotationPointY = Bag2.rotationPointY;

            SaddleB.rotationPointZ = Saddle.rotationPointZ;
            SaddleC.rotationPointZ = Saddle.rotationPointZ;
            SaddleL.rotationPointZ = Saddle.rotationPointZ;
            SaddleR.rotationPointZ = Saddle.rotationPointZ;
            SaddleL2.rotationPointZ = Saddle.rotationPointZ;
            SaddleR2.rotationPointZ = Saddle.rotationPointZ;
            Bag1.rotationPointZ = Bag2.rotationPointZ;

            Saddle.rotateAngleX = Body.rotateAngleX;
            SaddleB.rotateAngleX = Body.rotateAngleX;
            SaddleC.rotateAngleX = Body.rotateAngleX;

            SaddleMouthLine.rotationPointY = Head.rotationPointY;
            SaddleMouthLineR.rotationPointY = Head.rotationPointY;
            HeadSaddle.rotationPointY = Head.rotationPointY;
            SaddleMouthL.rotationPointY = Head.rotationPointY;
            SaddleMouthR.rotationPointY = Head.rotationPointY;

            SaddleMouthLine.rotationPointZ = Head.rotationPointZ;
            SaddleMouthLineR.rotationPointZ = Head.rotationPointZ;
            HeadSaddle.rotationPointZ = Head.rotationPointZ;
            SaddleMouthL.rotationPointZ = Head.rotationPointZ;
            SaddleMouthR.rotationPointZ = Head.rotationPointZ;

            SaddleMouthLine.rotateAngleX = HeadXRot;
            SaddleMouthLineR.rotateAngleX = HeadXRot;
            HeadSaddle.rotateAngleX = Head.rotateAngleX;
            SaddleMouthL.rotateAngleX = Head.rotateAngleX;
            SaddleMouthR.rotateAngleX = Head.rotateAngleX;
            HeadSaddle.rotateAngleY = Head.rotateAngleY;
            SaddleMouthL.rotateAngleY = Head.rotateAngleY;
            SaddleMouthLine.rotateAngleY = Head.rotateAngleY;
            SaddleMouthR.rotateAngleY = Head.rotateAngleY;
            SaddleMouthLineR.rotateAngleY = Head.rotateAngleY;

            if (rider)
            {
                SaddleL.rotateAngleX = -60 / 57.29578F;
                SaddleL2.rotateAngleX = -60 / 57.29578F;
                SaddleR.rotateAngleX = -60 / 57.29578F;
                SaddleR2.rotateAngleX = -60 / 57.29578F;

                SaddleL.rotateAngleZ = 0F;
                SaddleL2.rotateAngleZ = 0F;
                SaddleR.rotateAngleZ = 0F;
                SaddleR2.rotateAngleZ = 0F;
                ;
            }
            else
            {
                SaddleL.rotateAngleX = RLegXRot / 3F;
                SaddleL2.rotateAngleX = RLegXRot / 3F;
                SaddleR.rotateAngleX = RLegXRot / 3F;
                SaddleR2.rotateAngleX = RLegXRot / 3F;

                SaddleL.rotateAngleZ = RLegXRot / 5F;
                SaddleL2.rotateAngleZ = RLegXRot / 5F;
                SaddleR.rotateAngleZ = -RLegXRot / 5F;
                SaddleR2.rotateAngleZ = -RLegXRot / 5F;
            }
        }

        //f1 = movement speed!
        //f2 = timer!

        float tailMov = -1.3089F + (f1 * 1.5F);
        if (tailMov > 0)
        {
            tailMov = 0;
        }

        if (tail)
        {
            TailA.rotateAngleY = MathHelper.cos(f2 * 0.7F);
            tailMov = 0;
        }
        else
        {
            TailA.rotateAngleY = 0F;
        }
        TailB.rotateAngleY = TailA.rotateAngleY;
        TailC.rotateAngleY = TailA.rotateAngleY;

        TailB.rotationPointY = TailA.rotationPointY;
        TailC.rotationPointY = TailA.rotationPointY;
        TailB.rotationPointZ = TailA.rotationPointZ;
        TailC.rotationPointZ = TailA.rotationPointZ;

        TailA.rotateAngleX = tailMov;//-1.3089F+(f1*1.5F);
        TailB.rotateAngleX = tailMov;//-1.3089F+(f1*1.5F);
        TailC.rotateAngleX = -0.2618F + tailMov;//-1.5707F -tailMov;

    }

}
