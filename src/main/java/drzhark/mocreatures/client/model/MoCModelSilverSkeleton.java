package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MoCModelSilverSkeleton extends ModelBase {

    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Back;
    ModelRenderer RightArm;
    ModelRenderer RightHand;
    ModelRenderer RightSwordA;
    ModelRenderer RightSwordB;
    ModelRenderer RightSwordC;
    ModelRenderer LeftArm;
    ModelRenderer LeftHand;
    ModelRenderer LeftSwordA;
    ModelRenderer LeftSwordB;
    ModelRenderer LeftSwordC;
    ModelRenderer RightThigh;
    ModelRenderer RightKnee;
    ModelRenderer RightLeg;
    ModelRenderer RightFoot;
    ModelRenderer LeftThigh;
    ModelRenderer LeftKnee;
    ModelRenderer LeftLeg;
    ModelRenderer LeftFoot;

    private int leftAttack;
    private int rightAttack;
    private boolean riding;
    private float radianF = 57.29578F;

    public MoCModelSilverSkeleton() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-4F, -8F, -4F, 8, 8, 8);
        this.Head.setRotationPoint(0F, -2F, 0F);

        this.Body = new ModelRenderer(this, 32, 0);
        this.Body.addBox(-4F, 0F, -2F, 8, 12, 4);
        this.Body.setRotationPoint(0F, -2F, 0F);

        this.Back = new ModelRenderer(this, 44, 54);
        this.Back.addBox(-4F, -4F, 0.5F, 8, 8, 2);
        this.Back.setRotationPoint(0F, 2F, 2F);
        setRotation(this.Back, -0.1570796F, 0F, 0F);

        this.RightArm = new ModelRenderer(this, 48, 31);
        this.RightArm.addBox(-3F, -2.5F, -2.5F, 4, 11, 4);
        this.RightArm.setRotationPoint(-5F, 1F, 0F);

        this.RightHand = new ModelRenderer(this, 24, 16);
        this.RightHand.addBox(-2.5F, -2F, -2F, 3, 12, 3);
        this.RightHand.setRotationPoint(-5F, 1F, 0F);

        this.RightSwordA = new ModelRenderer(this, 52, 46);
        this.RightSwordA.addBox(-1.5F, 8.5F, -3F, 1, 1, 5);
        this.RightSwordA.setRotationPoint(-5F, 1F, 0F);

        this.RightSwordB = new ModelRenderer(this, 48, 50);
        this.RightSwordB.addBox(-1.5F, 7.5F, -4F, 1, 3, 1);
        this.RightSwordB.setRotationPoint(-5F, 1F, 0F);

        this.RightSwordC = new ModelRenderer(this, 28, 28);
        this.RightSwordC.addBox(-1F, 7.5F, -14F, 0, 3, 10);
        this.RightSwordC.setRotationPoint(-5F, 1F, 0F);

        this.LeftArm = new ModelRenderer(this, 48, 16);
        this.LeftArm.addBox(-1F, -2.5F, -2.5F, 4, 11, 4);
        this.LeftArm.setRotationPoint(5F, 1F, 0F);

        this.LeftHand = new ModelRenderer(this, 36, 16);
        this.LeftHand.addBox(-0.5F, -2F, -2F, 3, 12, 3);
        this.LeftHand.setRotationPoint(5F, 1F, 0F);

        this.LeftSwordA = new ModelRenderer(this, 52, 46);
        this.LeftSwordA.addBox(0.5F, 8.5F, -3F, 1, 1, 5);
        this.LeftSwordA.setRotationPoint(5F, 1F, 0F);

        this.LeftSwordB = new ModelRenderer(this, 48, 46);
        this.LeftSwordB.addBox(0.5F, 7.5F, -4F, 1, 3, 1);
        this.LeftSwordB.setRotationPoint(5F, 1F, 0F);

        this.LeftSwordC = new ModelRenderer(this, 28, 31);
        this.LeftSwordC.addBox(1F, 7.5F, -14F, 0, 3, 10);
        this.LeftSwordC.setRotationPoint(5F, 1F, 0F);

        this.RightThigh = new ModelRenderer(this, 0, 16);
        this.RightThigh.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        this.RightThigh.setRotationPoint(-2F, 10.5F, 0F);

        this.RightKnee = new ModelRenderer(this, 0, 46);
        this.RightKnee.addBox(-2F, 1F, -2F, 4, 4, 4);
        this.RightKnee.setRotationPoint(-2F, 10.5F, 0F);

        this.RightLeg = new ModelRenderer(this, 0, 25);
        this.RightLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        this.RightLeg.setRotationPoint(0F, 6F, 0F);
        this.RightThigh.addChild(this.RightLeg);

        this.RightFoot = new ModelRenderer(this, 0, 54);
        this.RightFoot.addBox(-2F, 0F, -2F, 4, 6, 4);
        this.RightFoot.setRotationPoint(0F, 2F, 0F);
        this.RightLeg.addChild(this.RightFoot);

        this.LeftThigh = new ModelRenderer(this, 12, 16);
        this.LeftThigh.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        this.LeftThigh.setRotationPoint(2F, 10.5F, 0F);

        this.LeftKnee = new ModelRenderer(this, 16, 46);
        this.LeftKnee.addBox(-2F, 1F, -2F, 4, 4, 4);
        this.LeftKnee.setRotationPoint(2F, 10.5F, 0F);

        this.LeftLeg = new ModelRenderer(this, 12, 25);
        this.LeftLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        this.LeftLeg.setRotationPoint(0F, 6F, 0F);
        this.LeftThigh.addChild(this.LeftLeg);

        this.LeftFoot = new ModelRenderer(this, 16, 54);
        this.LeftFoot.addBox(-2F, 0F, -2F, 4, 6, 4);
        this.LeftFoot.setRotationPoint(0F, 2F, 0F);
        this.LeftLeg.addChild(this.LeftFoot);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntitySilverSkeleton samurai = (MoCEntitySilverSkeleton) entity;
        boolean sprinting = samurai.isSprinting();
        this.leftAttack = samurai.attackCounterLeft;
        this.rightAttack = samurai.attackCounterRight;
        this.riding = samurai.getRidingEntity() != null;
        setRotationAngles(f, f1, f2, f3, f4, f5);
        GL11.glPushMatrix();
        if (sprinting && f1 > 0.3F) {
            //GL11.glPushMatrix();
            GL11.glRotatef((float) (f1 * -20D), -1F, 0.0F, 0.0F);
            //renderParts(f5);
            //GL11.glPopMatrix();
        }
        if (riding) {

            GL11.glTranslatef(0.0F, 0.5F, 0.0F);
            //renderParts(f5);
            //GL11.glPopMatrix();
        }
        //renderParts(f5);

        renderParts(f5);
        GL11.glPopMatrix();
    }

    private void renderParts(float f5) {
        this.Head.render(f5);
        this.Body.render(f5);
        this.Back.render(f5);
        this.RightArm.render(f5);
        this.RightHand.render(f5);
        this.RightSwordA.render(f5);
        this.RightSwordB.render(f5);
        this.RightSwordC.render(f5);
        this.LeftArm.render(f5);
        this.LeftHand.render(f5);
        this.LeftSwordA.render(f5);
        this.LeftSwordB.render(f5);
        this.LeftSwordC.render(f5);
        this.RightThigh.render(f5);
        this.RightKnee.render(f5);
        this.LeftThigh.render(f5);
        this.LeftKnee.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float hRotY = f3 / 57.29578F;
        float hRotX = f4 / 57.29578F;

        this.Head.rotateAngleX = hRotX;
        this.Head.rotateAngleY = hRotY;

        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        // float ClothRot = MathHelper.cos(f * 0.9F) * 0.6F * f1;

        float RLegXRotB = RLegXRot;
        float LLegXRotB = LLegXRot;

        if (leftAttack == 0) {
            this.LeftArm.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
            this.LeftArm.rotateAngleX = RLegXRot;
        } else {
            float armMov = -(MathHelper.cos((leftAttack) * 0.18F) * 3F);
            this.LeftArm.rotateAngleX = +armMov;
        }

        if (rightAttack == 0) {
            this.RightArm.rotateAngleZ = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
            this.RightArm.rotateAngleX = LLegXRot;

        } else {
            float armMov = -(MathHelper.cos((rightAttack) * 0.18F) * 3F);
            this.RightArm.rotateAngleX = +armMov;
        }

        this.LeftHand.rotateAngleX =
                this.LeftSwordA.rotateAngleX = this.LeftSwordB.rotateAngleX = this.LeftSwordC.rotateAngleX = this.LeftArm.rotateAngleX;
        this.LeftHand.rotateAngleZ =
                this.LeftSwordA.rotateAngleZ = this.LeftSwordB.rotateAngleZ = this.LeftSwordC.rotateAngleZ = this.LeftArm.rotateAngleZ;

        this.RightHand.rotateAngleX =
                this.RightSwordA.rotateAngleX = this.RightSwordB.rotateAngleX = this.RightSwordC.rotateAngleX = this.RightArm.rotateAngleX;
        this.RightHand.rotateAngleZ =
                this.RightSwordA.rotateAngleZ = this.RightSwordB.rotateAngleZ = this.RightSwordC.rotateAngleZ = this.RightArm.rotateAngleZ;

        if (riding) {
            this.RightLeg.rotateAngleX = 0F;

            this.RightThigh.rotateAngleX = -60F / radianF;
            this.RightThigh.rotateAngleY = 20F / radianF;
            this.RightKnee.rotateAngleY = 20F / radianF;
            this.RightKnee.rotateAngleX = -60F / radianF;
            this.LeftLeg.rotateAngleX = 0F;
            this.LeftThigh.rotateAngleY = -20F / radianF;
            this.LeftKnee.rotateAngleY = -20F / radianF;
            this.LeftThigh.rotateAngleX = -60F / radianF;
            this.LeftKnee.rotateAngleX = -60F / radianF;
        } else {
            this.RightThigh.rotateAngleY = 0F;
            this.RightKnee.rotateAngleY = 0F;
            this.LeftThigh.rotateAngleY = 0F;
            this.LeftKnee.rotateAngleY = 0F;
            this.RightThigh.rotateAngleX = RLegXRot;
            this.LeftThigh.rotateAngleX = LLegXRot;
            this.RightKnee.rotateAngleX = this.RightThigh.rotateAngleX;
            this.LeftKnee.rotateAngleX = this.LeftThigh.rotateAngleX;

            float RLegXRot2 = MathHelper.cos(((f + 0.1F) * 0.6662F) + 3.141593F) * 0.8F * f1;
            float LLegXRot2 = MathHelper.cos((f + 0.1F) * 0.6662F) * 0.8F * f1;

            if (f1 > 0.15F) {
                if (RLegXRot > RLegXRot2) // - - >
                {
                    RLegXRotB = RLegXRot + (25 / 57.29578F);

                }

                if (LLegXRot > LLegXRot2) // - - >
                {
                    LLegXRotB = LLegXRot + (25 / 57.29578F);
                }

            }

            this.RightLeg.rotateAngleX = (LLegXRotB);
            this.LeftLeg.rotateAngleX = (RLegXRotB);
        }
    }

}
