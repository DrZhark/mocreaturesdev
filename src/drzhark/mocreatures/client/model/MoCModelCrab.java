package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;

public class MoCModelCrab extends ModelBase
{

    ModelRenderer Shell;
    ModelRenderer ShellRight;
    ModelRenderer ShellLeft;
    ModelRenderer ShellBack;
    ModelRenderer LeftEye;
    ModelRenderer LeftEyeBase;
    ModelRenderer RightEyeBase;
    ModelRenderer RightEye;
    ModelRenderer RightArmA;
    ModelRenderer RightArmB;
    ModelRenderer RightArmC;
    ModelRenderer RightArmD;
    ModelRenderer LeftArmA;
    ModelRenderer LeftArmB;
    ModelRenderer LeftArmC;
    ModelRenderer LeftArmD;
    ModelRenderer LeftLeg1A;
    ModelRenderer LeftLeg1B;
    ModelRenderer LeftLeg2A;
    ModelRenderer LeftLeg2B;
    ModelRenderer LeftLeg3A;
    ModelRenderer LeftLeg3B;
    ModelRenderer LeftLeg4A;
    ModelRenderer LeftLeg4B;
    ModelRenderer LeftLeg4C;
    ModelRenderer RightLeg1A;
    ModelRenderer RightLeg1B;
    ModelRenderer RightLeg2A;
    ModelRenderer RightLeg2B;
    ModelRenderer RightLeg3A;
    ModelRenderer RightLeg3B;
    ModelRenderer RightLeg4A;
    ModelRenderer RightLeg4B;
    ModelRenderer RightLeg4C;
    
    private float radianF = 57.29578F;
    

    public MoCModelCrab()
    {
        textureWidth = 64;
        textureHeight = 64;

        Shell = new ModelRenderer(this, 0, 0);
        Shell.addBox(-5F, 0F, -4F, 10, 4, 8);
        Shell.setRotationPoint(0F, 16F, 0F);

ShellRight = new ModelRenderer(this, 0, 23);
        ShellRight.addBox(4.6F, -2F, -4F, 3, 3, 8);
        ShellRight.setRotationPoint(0F, 16F, 0F);
        setRotation(ShellRight, 0F, 0F, 0.418879F);
        
        ShellLeft = new ModelRenderer(this, 0, 12);
        ShellLeft.addBox(-7.6F, -2F, -4F, 3, 3, 8);
        ShellLeft.setRotationPoint(0F, 16F, 0F);
        setRotation(ShellLeft, 0F, 0F, -0.418879F);
        
        ShellBack = new ModelRenderer(this, 10, 42);
        ShellBack.addBox(-5F, -1.6F, 3.6F, 10, 3, 3);
        ShellBack.setRotationPoint(0F, 16F, 0F);
        setRotation(ShellBack, -0.418879F, 0F, 0F);
        
        LeftEye = new ModelRenderer(this, 0, 4);
        LeftEye.addBox(1F, -2F, -4.5F, 1, 3, 1);
        LeftEye.setRotationPoint(0F, 16F, 0F);
        setRotation(LeftEye, 0F, 0F, 0.1745329F);
        
        LeftEyeBase = new ModelRenderer(this, 0, 16);
        LeftEyeBase.addBox(1F, 1F, -5F, 2, 3, 1);
        LeftEyeBase.setRotationPoint(0F, 16F, 0F);
        setRotation(LeftEyeBase, 0F, 0F, 0.2094395F);
        
        RightEyeBase = new ModelRenderer(this, 0, 12);
        RightEyeBase.addBox(-3F, 1F, -5F, 2, 3, 1);
        RightEyeBase.setRotationPoint(0F, 16F, 0F);
        setRotation(RightEyeBase, 0F, 0F, -0.2094395F);
        
        RightEye = new ModelRenderer(this, 0, 0);
        RightEye.addBox(-2F, -2F, -4.5F, 1, 3, 1);
        RightEye.setRotationPoint(0F, 16F, 0F);
        setRotation(RightEye, 0F, 0F, -0.1745329F);
        
        
        
        RightArmA = new ModelRenderer(this, 0, 34);
        RightArmA.addBox(-4F, -1F, -1F, 4, 2, 2);
        RightArmA.setRotationPoint(-4F, 19F, -4F);
        setRotation(RightArmA, 0F, -0.5235988F, 0F);
          
        RightArmB = new ModelRenderer(this, 22,12);
        RightArmB.addBox(-4F, -1.5F, -1F, 4, 3, 2);
        RightArmB.setRotationPoint(-4F, 0F, 0F);
        setRotation(RightArmB, 0F, -2.094395F, 0F);
        RightArmA.addChild(RightArmB);
        
        RightArmC = new ModelRenderer(this, 22,17);
        RightArmC.addBox(-3F, -1.5F, -1F, 3, 1, 2);
        RightArmC.setRotationPoint(-4F, 0F, 0F);
        RightArmB.addChild(RightArmC);
        
        RightArmD = new ModelRenderer(this, 16, 12);
        RightArmD.addBox(-2F, 0.5F, -0.5F, 2, 1, 1);
        RightArmD.setRotationPoint(-4F, 0F, 0F);
        RightArmB.addChild(RightArmD);
        
        LeftArmA = new ModelRenderer(this, 0, 38);
        LeftArmA.addBox(0F, -1F, -1F, 4, 2, 2);
        LeftArmA.setRotationPoint(4F, 19F, -4F);
        setRotation(LeftArmA, 0F, 0.5235988F, 0F);
          
        LeftArmB = new ModelRenderer(this, 22, 20);
        LeftArmB.addBox(0F, -1.5F, -1F, 4, 3, 2);
        LeftArmB.setRotationPoint(4F, 0F, 0F);
        setRotation(LeftArmB, 0F, 2.094395F, 0F);
        LeftArmA.addChild(LeftArmB);
        
        LeftArmC = new ModelRenderer(this, 22, 25);
        LeftArmC.addBox(0F, -1.5F, -1F, 3, 1, 2);
        LeftArmC.setRotationPoint(4F, 0F, 0F);
        LeftArmB.addChild(LeftArmC);
        
        LeftArmD = new ModelRenderer(this, 16, 23);
        LeftArmD.addBox(0F, 0.5F, -0.5F, 2, 1, 1);
        LeftArmD.setRotationPoint(4F, 0F, 0F);
        LeftArmB.addChild(LeftArmD);
        
        RightLeg1A = new ModelRenderer(this, 0 , 42);
        RightLeg1A.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        RightLeg1A.setRotationPoint(-5F, 19.5F, -2.5F);
        setRotation(RightLeg1A, 0F, -0.1745329F, -0.418879F);
        
        
        RightLeg1B = new ModelRenderer(this, 0, 48);
        RightLeg1B.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        RightLeg1B.setRotationPoint(-4F, 0F, 0F);
        setRotation(RightLeg1B, 0F, 0F, -0.5235988F);
        RightLeg1A.addChild(RightLeg1B);
        
        RightLeg2A = new ModelRenderer(this, 0, 44);
        RightLeg2A.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        RightLeg2A.setRotationPoint(-5F, 19.5F, 0F);
        setRotation(RightLeg2A, 0F, 0.0872665F, -0.418879F);
        
        RightLeg2B = new ModelRenderer(this, 0, 50);
        RightLeg2B.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        RightLeg2B.setRotationPoint(-4F, 0F, 0F);
        setRotation(RightLeg2B, 0F, 0F, -0.5235988F);
        RightLeg2A.addChild(RightLeg2B);
        
        RightLeg3A = new ModelRenderer(this, 0, 46);
        RightLeg3A.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        RightLeg3A.setRotationPoint(-5F, 19.5F, 2.5F);
        setRotation(RightLeg3A, 0F, 0.6981317F, -0.418879F);
        
        RightLeg3B = new ModelRenderer(this, 0, 52);
        RightLeg3B.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        RightLeg3B.setRotationPoint(-4F, 0F, 0F);
        setRotation(RightLeg3B, 0F, 0F, -0.5235988F);
        RightLeg3A.addChild(RightLeg3B);
        
        RightLeg4A = new ModelRenderer(this, 12, 34);
        RightLeg4A.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        RightLeg4A.setRotationPoint(-3F, 19.5F, 3.5F);
        setRotation(RightLeg4A, 0F, 0.6108652F, -0.418879F);
        
        RightLeg4B = new ModelRenderer(this, 12, 36);
        RightLeg4B.addBox(-3F, -0.5F, -1F, 3, 1, 2);
        RightLeg4B.setRotationPoint(-4F, 0F, 0F);
        setRotation(RightLeg4B, 0F, 1.308997F, -0.418879F);
        RightLeg4A.addChild(RightLeg4B); 
        
        RightLeg4C = new ModelRenderer(this, 12, 39);
        RightLeg4C.addBox(-3F, -0.5F, -1F, 3, 1, 2);
        RightLeg4C.setRotationPoint(-3F, 0F, 0F);
        setRotation(RightLeg4C, 0F, 0.8726646F, -0.418879F);
        RightLeg4C.mirror = true;
        RightLeg4B.addChild(RightLeg4C);
          
        LeftLeg1A = new ModelRenderer(this, 0, 54);
        LeftLeg1A.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        LeftLeg1A.setRotationPoint(5F, 19.5F, -2.5F);
        setRotation(LeftLeg1A, 0F, 0.1745329F, 0.418879F);
        
        LeftLeg1B = new ModelRenderer(this, 0, 56);
        LeftLeg1B.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        LeftLeg1B.setRotationPoint(4F, 0F, 0F);
        setRotation(LeftLeg1B, 0F, 0F, 0.5235988F);
        LeftLeg1A.addChild(LeftLeg1B);
        
        LeftLeg2A = new ModelRenderer(this, 0,62);
        LeftLeg2A.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        LeftLeg2A.setRotationPoint(5F, 19.5F, 0F);
        setRotation(LeftLeg2A, 0F, -0.0872665F, 0.418879F);
          
        LeftLeg2B = new ModelRenderer(this, 10,62);
        LeftLeg2B.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        LeftLeg2B.setRotationPoint(4F, 0F, 0F);
        setRotation(LeftLeg2B, 0F, 0F, 0.5235988F);
        LeftLeg2A.addChild(LeftLeg2B);
        
        LeftLeg3A = new ModelRenderer(this, 0,58);
        LeftLeg3A.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        LeftLeg3A.setRotationPoint(5F, 19.5F, 2.5F);
        setRotation(LeftLeg3A, 0F, -0.6981317F, 0.418879F);
          
        LeftLeg3B = new ModelRenderer(this, 0,60);
        LeftLeg3B.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        LeftLeg3B.setRotationPoint(4F, 0F, 0F);
        setRotation(LeftLeg3B, 0F, 0F, 0.5235988F);
        LeftLeg3A.addChild(LeftLeg3B);
        
        LeftLeg4A = new ModelRenderer(this, 22,34);
        LeftLeg4A.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        LeftLeg4A.setRotationPoint(2F, 19.5F, 3.5F);
        setRotation(LeftLeg4A, 0F, -0.6108652F, 0.418879F);
          
        LeftLeg4B = new ModelRenderer(this, 22,36);
        LeftLeg4B.addBox(0F, -0.5F, -1F, 3, 1, 2);
        LeftLeg4B.setRotationPoint(4F, 0F, 0F);
        setRotation(LeftLeg4B, 0F, -1.308997F, 0.418879F);
        LeftLeg4A.addChild(LeftLeg4B); 
          
        LeftLeg4C = new ModelRenderer(this, 22,39);
        LeftLeg4C.addBox(0F, -0.5F, -1F, 3, 1, 2);
        LeftLeg4C.setRotationPoint(3F, 0F, 0F);
        setRotation(LeftLeg4C, 0F, -0.8726646F, 0.418879F);
        LeftLeg4B.addChild(LeftLeg4C); 
          

    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityCrab crab = (MoCEntityCrab) entity;
        setRotationAngles(f, f1, f2, f3, f4, f5, crab.isFleeing());
        
        Shell.render(f5);
        ShellRight.render(f5);
        ShellLeft.render(f5);
        ShellBack.render(f5);
        LeftEye.render(f5);
        LeftEyeBase.render(f5);
        RightEyeBase.render(f5);
        RightEye.render(f5);
        RightArmA.render(f5);
        LeftArmA.render(f5);
        LeftLeg1A.render(f5);
        LeftLeg2A.render(f5);
        LeftLeg3A.render(f5);
        LeftLeg4A.render(f5);
        RightLeg1A.render(f5);
        RightLeg2A.render(f5);
        RightLeg3A.render(f5);
        RightLeg4A.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean fleeing)
    {
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        if (fleeing)
        {
            //LeftArmA.rotateAngleY = 45F/radianF;
            LeftArmA.rotateAngleX = -90F/radianF;
            RightArmA.rotateAngleX = -90F/radianF;
        }else
        {
            //LeftArmA.rotateAngleY = 30F/radianF;
            LeftArmA.rotateAngleX = 0F;
            RightArmA.rotateAngleX = 0F;
        }
        
        if (f1 < 0.1F)
        {
            RightArmA.rotateAngleY = -30F/radianF;
            RightArmB.rotateAngleY = -120F/radianF;
            
            //hand movement
            
            /**
             * LHand random animation
             */
            float lHand = 0F;

            float f2a = f2 % 100F;
            if (f2a > 0 & f2a < 10)
            {
                lHand = (f2a * 2F) / radianF;
            }
            
            LeftArmA.rotateAngleY = 30F/radianF + lHand; 
            LeftArmB.rotateAngleY = 120F/radianF + lHand;
            
            /**
             * RHand random animation
             */
            float RHand = 0F;
            float f2b = f2 % 75F;
            if (f2b > 30 & f2b < 40)
            {
                RHand = (f2b - 29)*2F / radianF;
            }
            RightArmA.rotateAngleY = -30F/radianF - RHand; 
            RightArmB.rotateAngleY = -120F/radianF - RHand;
        }
        
        
        
        /**
         * floats used for the leg animations
         */
        float f9 = -(MathHelper.cos(f * 5F) ) * f1 *2F;
        float f10 = -(MathHelper.cos(f * 5F + 3.141593F) ) * f1 *2F;
        float f11 = -(MathHelper.cos(f * 5.0F + 1.570796F) ) * f1*2F;
        float f12 = -(MathHelper.cos(f * 5.0F + 4.712389F) ) * f1*2F;
        float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1*5F;
        float f14 = Math.abs(MathHelper.sin(f * 0.6662F + 3.141593F) * 0.4F) * f1;
        float f15 = Math.abs(MathHelper.sin(f * 0.6662F + 1.570796F) * 0.4F) * f1;
        float f16 = Math.abs(MathHelper.sin(f * 0.6662F + 4.712389F) * 0.4F) * f1;

        RightLeg1A.rotateAngleY = -0.1745329F;
        RightLeg1A.rotateAngleZ = -0.418879F;
        RightLeg1A.rotateAngleY += f9;
        RightLeg1A.rotateAngleZ += f13;
        
        RightLeg1B.rotateAngleZ = -0.5235988F;
        RightLeg1B.rotateAngleZ -= f13;
        
        RightLeg2A.rotateAngleY = 0.0872665F;
        RightLeg2A.rotateAngleZ = -0.418879F;
        RightLeg2A.rotateAngleY += f10;
        RightLeg2A.rotateAngleZ += f14;
        
        RightLeg2B.rotateAngleZ = -0.5235988F;
        RightLeg2B.rotateAngleZ -= f14;
        
        RightLeg3A.rotateAngleY = 0.6981317F;
        RightLeg3A.rotateAngleZ = -0.418879F;
        RightLeg3A.rotateAngleY += f11;
        RightLeg3A.rotateAngleZ += f15;
        
        RightLeg3B.rotateAngleZ = -0.5235988F;
        RightLeg3B.rotateAngleZ -= f15;
        
        RightLeg4A.rotateAngleY = 0.6108652F;
        RightLeg4A.rotateAngleZ = -0.418879F;
        RightLeg4A.rotateAngleY += f12;
        RightLeg4A.rotateAngleZ += f16;
        
        
        LeftLeg1A.rotateAngleY = 0.1745329F;
        LeftLeg1A.rotateAngleZ = 0.418879F;
        LeftLeg1A.rotateAngleY -= f9;
        LeftLeg1A.rotateAngleZ -= f13;
        
        LeftLeg1B.rotateAngleZ = 0.5235988F;
        LeftLeg1B.rotateAngleZ += f13;
        
        LeftLeg2A.rotateAngleY = -0.0872665F;
        LeftLeg2A.rotateAngleZ = 0.418879F;
        LeftLeg2A.rotateAngleY -= f10;
        LeftLeg2A.rotateAngleZ -= f14;
        
        LeftLeg2B.rotateAngleZ = 0.5235988F;
        LeftLeg2B.rotateAngleZ += f14;
        
        LeftLeg3A.rotateAngleY = -0.6981317F;
        LeftLeg3A.rotateAngleZ = 0.418879F;
        LeftLeg3A.rotateAngleY -= f11;
        LeftLeg3A.rotateAngleZ -= f15;
        
        LeftLeg3B.rotateAngleZ = 0.5235988F;
        LeftLeg3B.rotateAngleZ += f15;
        
        LeftLeg4A.rotateAngleY = -0.6108652F;
        LeftLeg4A.rotateAngleZ = 0.418879F;
        LeftLeg4A.rotateAngleY -= f12;
        LeftLeg4A.rotateAngleZ -= f16;
        
        
    }

}

