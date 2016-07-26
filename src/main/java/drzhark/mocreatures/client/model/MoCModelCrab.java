package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.ambient.MoCEntityCrab;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class MoCModelCrab extends ModelBase {

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
    private boolean fleeing;

    public MoCModelCrab() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.Shell = new ModelRenderer(this, 0, 0);
        this.Shell.addBox(-5F, 0F, -4F, 10, 4, 8);
        this.Shell.setRotationPoint(0F, 16F, 0F);

        this.ShellRight = new ModelRenderer(this, 0, 23);
        this.ShellRight.addBox(4.6F, -2F, -4F, 3, 3, 8);
        this.ShellRight.setRotationPoint(0F, 16F, 0F);
        setRotation(this.ShellRight, 0F, 0F, 0.418879F);

        this.ShellLeft = new ModelRenderer(this, 0, 12);
        this.ShellLeft.addBox(-7.6F, -2F, -4F, 3, 3, 8);
        this.ShellLeft.setRotationPoint(0F, 16F, 0F);
        setRotation(this.ShellLeft, 0F, 0F, -0.418879F);

        this.ShellBack = new ModelRenderer(this, 10, 42);
        this.ShellBack.addBox(-5F, -1.6F, 3.6F, 10, 3, 3);
        this.ShellBack.setRotationPoint(0F, 16F, 0F);
        setRotation(this.ShellBack, -0.418879F, 0F, 0F);

        this.LeftEye = new ModelRenderer(this, 0, 4);
        this.LeftEye.addBox(1F, -2F, -4.5F, 1, 3, 1);
        this.LeftEye.setRotationPoint(0F, 16F, 0F);
        setRotation(this.LeftEye, 0F, 0F, 0.1745329F);

        this.LeftEyeBase = new ModelRenderer(this, 0, 16);
        this.LeftEyeBase.addBox(1F, 1F, -5F, 2, 3, 1);
        this.LeftEyeBase.setRotationPoint(0F, 16F, 0F);
        setRotation(this.LeftEyeBase, 0F, 0F, 0.2094395F);

        this.RightEyeBase = new ModelRenderer(this, 0, 12);
        this.RightEyeBase.addBox(-3F, 1F, -5F, 2, 3, 1);
        this.RightEyeBase.setRotationPoint(0F, 16F, 0F);
        setRotation(this.RightEyeBase, 0F, 0F, -0.2094395F);

        this.RightEye = new ModelRenderer(this, 0, 0);
        this.RightEye.addBox(-2F, -2F, -4.5F, 1, 3, 1);
        this.RightEye.setRotationPoint(0F, 16F, 0F);
        setRotation(this.RightEye, 0F, 0F, -0.1745329F);

        this.RightArmA = new ModelRenderer(this, 0, 34);
        this.RightArmA.addBox(-4F, -1F, -1F, 4, 2, 2);
        this.RightArmA.setRotationPoint(-4F, 19F, -4F);
        setRotation(this.RightArmA, 0F, -0.5235988F, 0F);

        this.RightArmB = new ModelRenderer(this, 22, 12);
        this.RightArmB.addBox(-4F, -1.5F, -1F, 4, 3, 2);
        this.RightArmB.setRotationPoint(-4F, 0F, 0F);
        setRotation(this.RightArmB, 0F, -2.094395F, 0F);
        this.RightArmA.addChild(this.RightArmB);

        this.RightArmC = new ModelRenderer(this, 22, 17);
        this.RightArmC.addBox(-3F, -1.5F, -1F, 3, 1, 2);
        this.RightArmC.setRotationPoint(-4F, 0F, 0F);
        this.RightArmB.addChild(this.RightArmC);

        this.RightArmD = new ModelRenderer(this, 16, 12);
        this.RightArmD.addBox(-2F, 0.5F, -0.5F, 2, 1, 1);
        this.RightArmD.setRotationPoint(-4F, 0F, 0F);
        this.RightArmB.addChild(this.RightArmD);

        this.LeftArmA = new ModelRenderer(this, 0, 38);
        this.LeftArmA.addBox(0F, -1F, -1F, 4, 2, 2);
        this.LeftArmA.setRotationPoint(4F, 19F, -4F);
        setRotation(this.LeftArmA, 0F, 0.5235988F, 0F);

        this.LeftArmB = new ModelRenderer(this, 22, 20);
        this.LeftArmB.addBox(0F, -1.5F, -1F, 4, 3, 2);
        this.LeftArmB.setRotationPoint(4F, 0F, 0F);
        setRotation(this.LeftArmB, 0F, 2.094395F, 0F);
        this.LeftArmA.addChild(this.LeftArmB);

        this.LeftArmC = new ModelRenderer(this, 22, 25);
        this.LeftArmC.addBox(0F, -1.5F, -1F, 3, 1, 2);
        this.LeftArmC.setRotationPoint(4F, 0F, 0F);
        this.LeftArmB.addChild(this.LeftArmC);

        this.LeftArmD = new ModelRenderer(this, 16, 23);
        this.LeftArmD.addBox(0F, 0.5F, -0.5F, 2, 1, 1);
        this.LeftArmD.setRotationPoint(4F, 0F, 0F);
        this.LeftArmB.addChild(this.LeftArmD);

        this.RightLeg1A = new ModelRenderer(this, 0, 42);
        this.RightLeg1A.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        this.RightLeg1A.setRotationPoint(-5F, 19.5F, -2.5F);
        setRotation(this.RightLeg1A, 0F, -0.1745329F, -0.418879F);

        this.RightLeg1B = new ModelRenderer(this, 0, 48);
        this.RightLeg1B.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        this.RightLeg1B.setRotationPoint(-4F, 0F, 0F);
        setRotation(this.RightLeg1B, 0F, 0F, -0.5235988F);
        this.RightLeg1A.addChild(this.RightLeg1B);

        this.RightLeg2A = new ModelRenderer(this, 0, 44);
        this.RightLeg2A.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        this.RightLeg2A.setRotationPoint(-5F, 19.5F, 0F);
        setRotation(this.RightLeg2A, 0F, 0.0872665F, -0.418879F);

        this.RightLeg2B = new ModelRenderer(this, 0, 50);
        this.RightLeg2B.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        this.RightLeg2B.setRotationPoint(-4F, 0F, 0F);
        setRotation(this.RightLeg2B, 0F, 0F, -0.5235988F);
        this.RightLeg2A.addChild(this.RightLeg2B);

        this.RightLeg3A = new ModelRenderer(this, 0, 46);
        this.RightLeg3A.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        this.RightLeg3A.setRotationPoint(-5F, 19.5F, 2.5F);
        setRotation(this.RightLeg3A, 0F, 0.6981317F, -0.418879F);

        this.RightLeg3B = new ModelRenderer(this, 0, 52);
        this.RightLeg3B.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        this.RightLeg3B.setRotationPoint(-4F, 0F, 0F);
        setRotation(this.RightLeg3B, 0F, 0F, -0.5235988F);
        this.RightLeg3A.addChild(this.RightLeg3B);

        this.RightLeg4A = new ModelRenderer(this, 12, 34);
        this.RightLeg4A.addBox(-4F, -0.5F, -0.5F, 4, 1, 1);
        this.RightLeg4A.setRotationPoint(-3F, 19.5F, 3.5F);
        setRotation(this.RightLeg4A, 0F, 0.6108652F, -0.418879F);

        this.RightLeg4B = new ModelRenderer(this, 12, 36);
        this.RightLeg4B.addBox(-3F, -0.5F, -1F, 3, 1, 2);
        this.RightLeg4B.setRotationPoint(-4F, 0F, 0F);
        setRotation(this.RightLeg4B, 0F, 1.308997F, -0.418879F);
        this.RightLeg4A.addChild(this.RightLeg4B);

        this.RightLeg4C = new ModelRenderer(this, 12, 39);
        this.RightLeg4C.addBox(-3F, -0.5F, -1F, 3, 1, 2);
        this.RightLeg4C.setRotationPoint(-3F, 0F, 0F);
        setRotation(this.RightLeg4C, 0F, 0.8726646F, -0.418879F);
        this.RightLeg4C.mirror = true;
        this.RightLeg4B.addChild(this.RightLeg4C);

        this.LeftLeg1A = new ModelRenderer(this, 0, 54);
        this.LeftLeg1A.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        this.LeftLeg1A.setRotationPoint(5F, 19.5F, -2.5F);
        setRotation(this.LeftLeg1A, 0F, 0.1745329F, 0.418879F);

        this.LeftLeg1B = new ModelRenderer(this, 0, 56);
        this.LeftLeg1B.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        this.LeftLeg1B.setRotationPoint(4F, 0F, 0F);
        setRotation(this.LeftLeg1B, 0F, 0F, 0.5235988F);
        this.LeftLeg1A.addChild(this.LeftLeg1B);

        this.LeftLeg2A = new ModelRenderer(this, 0, 62);
        this.LeftLeg2A.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        this.LeftLeg2A.setRotationPoint(5F, 19.5F, 0F);
        setRotation(this.LeftLeg2A, 0F, -0.0872665F, 0.418879F);

        this.LeftLeg2B = new ModelRenderer(this, 10, 62);
        this.LeftLeg2B.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        this.LeftLeg2B.setRotationPoint(4F, 0F, 0F);
        setRotation(this.LeftLeg2B, 0F, 0F, 0.5235988F);
        this.LeftLeg2A.addChild(this.LeftLeg2B);

        this.LeftLeg3A = new ModelRenderer(this, 0, 58);
        this.LeftLeg3A.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        this.LeftLeg3A.setRotationPoint(5F, 19.5F, 2.5F);
        setRotation(this.LeftLeg3A, 0F, -0.6981317F, 0.418879F);

        this.LeftLeg3B = new ModelRenderer(this, 0, 60);
        this.LeftLeg3B.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        this.LeftLeg3B.setRotationPoint(4F, 0F, 0F);
        setRotation(this.LeftLeg3B, 0F, 0F, 0.5235988F);
        this.LeftLeg3A.addChild(this.LeftLeg3B);

        this.LeftLeg4A = new ModelRenderer(this, 22, 34);
        this.LeftLeg4A.addBox(0F, -0.5F, -0.5F, 4, 1, 1);
        this.LeftLeg4A.setRotationPoint(2F, 19.5F, 3.5F);
        setRotation(this.LeftLeg4A, 0F, -0.6108652F, 0.418879F);

        this.LeftLeg4B = new ModelRenderer(this, 22, 36);
        this.LeftLeg4B.addBox(0F, -0.5F, -1F, 3, 1, 2);
        this.LeftLeg4B.setRotationPoint(4F, 0F, 0F);
        setRotation(this.LeftLeg4B, 0F, -1.308997F, 0.418879F);
        this.LeftLeg4A.addChild(this.LeftLeg4B);

        this.LeftLeg4C = new ModelRenderer(this, 22, 39);
        this.LeftLeg4C.addBox(0F, -0.5F, -1F, 3, 1, 2);
        this.LeftLeg4C.setRotationPoint(3F, 0F, 0F);
        setRotation(this.LeftLeg4C, 0F, -0.8726646F, 0.418879F);
        this.LeftLeg4B.addChild(this.LeftLeg4C);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        MoCEntityCrab crab = (MoCEntityCrab) entity;
        this.fleeing = crab.isFleeing();
        setRotationAngles(f, f1, f2, f3, f4, f5);

        this.Shell.render(f5);
        this.ShellRight.render(f5);
        this.ShellLeft.render(f5);
        this.ShellBack.render(f5);
        this.LeftEye.render(f5);
        this.LeftEyeBase.render(f5);
        this.RightEyeBase.render(f5);
        this.RightEye.render(f5);
        this.RightArmA.render(f5);
        this.LeftArmA.render(f5);
        this.LeftLeg1A.render(f5);
        this.LeftLeg2A.render(f5);
        this.LeftLeg3A.render(f5);
        this.LeftLeg4A.render(f5);
        this.RightLeg1A.render(f5);
        this.RightLeg2A.render(f5);
        this.RightLeg3A.render(f5);
        this.RightLeg4A.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        if (fleeing) {
            //LeftArmA.rotateAngleY = 45F/radianF;
            this.LeftArmA.rotateAngleX = -90F / this.radianF;
            this.RightArmA.rotateAngleX = -90F / this.radianF;
        } else {
            //LeftArmA.rotateAngleY = 30F/radianF;
            this.LeftArmA.rotateAngleX = 0F;
            this.RightArmA.rotateAngleX = 0F;
        }

        if (f1 < 0.1F) {
            this.RightArmA.rotateAngleY = -30F / this.radianF;
            this.RightArmB.rotateAngleY = -120F / this.radianF;

            //hand movement

            /**
             * LHand random animation
             */
            float lHand = 0F;

            float f2a = f2 % 100F;
            if (f2a > 0 & f2a < 10) {
                lHand = (f2a * 2F) / this.radianF;
            }

            this.LeftArmA.rotateAngleY = 30F / this.radianF + lHand;
            this.LeftArmB.rotateAngleY = 120F / this.radianF + lHand;

            /**
             * RHand random animation
             */
            float RHand = 0F;
            float f2b = f2 % 75F;
            if (f2b > 30 & f2b < 40) {
                RHand = (f2b - 29) * 2F / this.radianF;
            }
            this.RightArmA.rotateAngleY = -30F / this.radianF - RHand;
            this.RightArmB.rotateAngleY = -120F / this.radianF - RHand;
        }

        /**
         * floats used for the leg animations
         */
        float f9 = -(MathHelper.cos(f * 5F)) * f1 * 2F;
        float f10 = -(MathHelper.cos(f * 5F + 3.141593F)) * f1 * 2F;
        float f11 = -(MathHelper.cos(f * 5.0F + 1.570796F)) * f1 * 2F;
        float f12 = -(MathHelper.cos(f * 5.0F + 4.712389F)) * f1 * 2F;
        float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1 * 5F;
        float f14 = Math.abs(MathHelper.sin(f * 0.6662F + 3.141593F) * 0.4F) * f1;
        float f15 = Math.abs(MathHelper.sin(f * 0.6662F + 1.570796F) * 0.4F) * f1;
        float f16 = Math.abs(MathHelper.sin(f * 0.6662F + 4.712389F) * 0.4F) * f1;

        this.RightLeg1A.rotateAngleY = -0.1745329F;
        this.RightLeg1A.rotateAngleZ = -0.418879F;
        this.RightLeg1A.rotateAngleY += f9;
        this.RightLeg1A.rotateAngleZ += f13;

        this.RightLeg1B.rotateAngleZ = -0.5235988F;
        this.RightLeg1B.rotateAngleZ -= f13;

        this.RightLeg2A.rotateAngleY = 0.0872665F;
        this.RightLeg2A.rotateAngleZ = -0.418879F;
        this.RightLeg2A.rotateAngleY += f10;
        this.RightLeg2A.rotateAngleZ += f14;

        this.RightLeg2B.rotateAngleZ = -0.5235988F;
        this.RightLeg2B.rotateAngleZ -= f14;

        this.RightLeg3A.rotateAngleY = 0.6981317F;
        this.RightLeg3A.rotateAngleZ = -0.418879F;
        this.RightLeg3A.rotateAngleY += f11;
        this.RightLeg3A.rotateAngleZ += f15;

        this.RightLeg3B.rotateAngleZ = -0.5235988F;
        this.RightLeg3B.rotateAngleZ -= f15;

        this.RightLeg4A.rotateAngleY = 0.6108652F;
        this.RightLeg4A.rotateAngleZ = -0.418879F;
        this.RightLeg4A.rotateAngleY += f12;
        this.RightLeg4A.rotateAngleZ += f16;

        this.LeftLeg1A.rotateAngleY = 0.1745329F;
        this.LeftLeg1A.rotateAngleZ = 0.418879F;
        this.LeftLeg1A.rotateAngleY -= f9;
        this.LeftLeg1A.rotateAngleZ -= f13;

        this.LeftLeg1B.rotateAngleZ = 0.5235988F;
        this.LeftLeg1B.rotateAngleZ += f13;

        this.LeftLeg2A.rotateAngleY = -0.0872665F;
        this.LeftLeg2A.rotateAngleZ = 0.418879F;
        this.LeftLeg2A.rotateAngleY -= f10;
        this.LeftLeg2A.rotateAngleZ -= f14;

        this.LeftLeg2B.rotateAngleZ = 0.5235988F;
        this.LeftLeg2B.rotateAngleZ += f14;

        this.LeftLeg3A.rotateAngleY = -0.6981317F;
        this.LeftLeg3A.rotateAngleZ = 0.418879F;
        this.LeftLeg3A.rotateAngleY -= f11;
        this.LeftLeg3A.rotateAngleZ -= f15;

        this.LeftLeg3B.rotateAngleZ = 0.5235988F;
        this.LeftLeg3B.rotateAngleZ += f15;

        this.LeftLeg4A.rotateAngleY = -0.6108652F;
        this.LeftLeg4A.rotateAngleZ = 0.418879F;
        this.LeftLeg4A.rotateAngleY -= f12;
        this.LeftLeg4A.rotateAngleZ -= f16;

    }

}
