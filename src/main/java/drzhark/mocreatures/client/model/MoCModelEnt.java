package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class MoCModelEnt extends ModelBase {

    ModelRenderer Body;
    ModelRenderer LShoulder;
    ModelRenderer LArm;
    ModelRenderer LWrist;
    ModelRenderer LHand;
    ModelRenderer LFingers;
    ModelRenderer RShoulder;
    ModelRenderer RArm;
    ModelRenderer RWrist;
    ModelRenderer RHand;
    ModelRenderer RFingers;
    ModelRenderer LLeg;
    ModelRenderer LThigh;
    ModelRenderer LKnee;
    ModelRenderer LAnkle;
    ModelRenderer LFoot;
    ModelRenderer RLeg;
    ModelRenderer RThigh;
    ModelRenderer RKnee;
    ModelRenderer RAnkle;
    ModelRenderer RFoot;
    ModelRenderer Neck;
    ModelRenderer Face;
    ModelRenderer Head;
    ModelRenderer Nose;
    ModelRenderer Mouth;
    ModelRenderer TreeBase;
    ModelRenderer Leave1;
    ModelRenderer Leave2;
    ModelRenderer Leave3;
    ModelRenderer Leave4;
    ModelRenderer Leave5;
    ModelRenderer Leave6;
    ModelRenderer Leave7;
    ModelRenderer Leave8;
    ModelRenderer Leave9;
    ModelRenderer Leave10;
    ModelRenderer Leave11;
    ModelRenderer Leave12;
    ModelRenderer Leave13;
    ModelRenderer Leave14;
    ModelRenderer Leave15;
    ModelRenderer Leave16;

    public MoCModelEnt() {
        this.textureWidth = 128;
        this.textureHeight = 256;

        this.Body = new ModelRenderer(this, 68, 36);
        this.Body.addBox(-7.5F, -12.5F, -4.5F, 15, 25, 9);
        this.Body.setRotationPoint(0F, -31F, 0F);
        this.LShoulder = new ModelRenderer(this, 48, 108);
        this.LShoulder.addBox(6F, -14F, -4.8F, 9, 7, 7);
        this.LShoulder.setRotationPoint(0F, -31F, 0F);
        setRotation(this.LShoulder, 0F, 0F, -0.1745329F);
        this.LArm = new ModelRenderer(this, 80, 108);
        this.LArm.addBox(0F, -4F, -5F, 6, 24, 6);
        this.LArm.setRotationPoint(10F, -42F, 1F);
        setRotation(this.LArm, 0F, 0F, -0.1745329F);
        this.LWrist = new ModelRenderer(this, 0, 169);
        this.LWrist.addBox(2F, 17F, -6F, 8, 15, 8);
        this.LWrist.setRotationPoint(10F, -42F, 1F);
        this.LHand = new ModelRenderer(this, 88, 241);
        this.LHand.addBox(1F, 28F, -7F, 10, 5, 10);
        this.LHand.setRotationPoint(10F, -42F, 1F);
        this.LFingers = new ModelRenderer(this, 88, 176);
        this.LFingers.addBox(1F, 33F, -7F, 10, 15, 10);
        this.LFingers.setRotationPoint(10F, -42F, 1F);
        this.RShoulder = new ModelRenderer(this, 48, 122);
        this.RShoulder.addBox(-15F, -14F, -4.8F, 9, 7, 7);
        this.RShoulder.setRotationPoint(0F, -31F, 0F);
        setRotation(this.RShoulder, 0F, 0F, 0.1745329F);
        this.RArm = new ModelRenderer(this, 104, 108);
        this.RArm.addBox(-6F, -4F, -5F, 6, 24, 6);
        this.RArm.setRotationPoint(-10F, -42F, 1F);
        setRotation(this.RArm, 0F, 0F, 0.1745329F);
        this.RWrist = new ModelRenderer(this, 32, 169);
        this.RWrist.addBox(-10F, 17F, -6F, 8, 15, 8);
        this.RWrist.setRotationPoint(-10F, -42F, 1F);
        this.RHand = new ModelRenderer(this, 88, 226);
        this.RHand.addBox(-11F, 28F, -7F, 10, 5, 10);
        this.RHand.setRotationPoint(-10F, -42F, 1F);
        this.RFingers = new ModelRenderer(this, 88, 201);
        this.RFingers.addBox(-11F, 33F, -7F, 10, 15, 10);
        this.RFingers.setRotationPoint(-10F, -42F, 1F);
        this.LLeg = new ModelRenderer(this, 0, 90);
        this.LLeg.addBox(3F, 0F, -3F, 6, 20, 6);
        this.LLeg.setRotationPoint(0F, -21F, 0F);
        this.LThigh = new ModelRenderer(this, 24, 64);
        this.LThigh.addBox(2.5F, 4F, -3.5F, 7, 12, 7);
        this.LThigh.setRotationPoint(0F, -21F, 0F);
        this.LKnee = new ModelRenderer(this, 0, 0);
        this.LKnee.addBox(2F, 20F, -4F, 8, 24, 8);
        this.LKnee.setRotationPoint(0F, -21F, 0F);
        this.LAnkle = new ModelRenderer(this, 32, 29);
        this.LAnkle.addBox(1.5F, 25F, -4.5F, 9, 20, 9);
        this.LAnkle.setRotationPoint(0F, -21F, 0F);
        this.LFoot = new ModelRenderer(this, 0, 206);
        this.LFoot.addBox(1.5F, 38F, -23.5F, 9, 5, 9);
        this.LFoot.setRotationPoint(0F, -21F, 0F);
        setRotation(this.LFoot, 0.2617994F, 0F, 0F);
        this.RLeg = new ModelRenderer(this, 0, 64);
        this.RLeg.addBox(-9F, 0F, -3F, 6, 20, 6);
        this.RLeg.setRotationPoint(0F, -21F, 0F);
        this.RThigh = new ModelRenderer(this, 24, 83);
        this.RThigh.addBox(-9.5F, 4F, -3.5F, 7, 12, 7);
        this.RThigh.setRotationPoint(0F, -21F, 0F);
        this.RKnee = new ModelRenderer(this, 0, 32);
        this.RKnee.addBox(-10F, 20F, -4F, 8, 24, 8);
        this.RKnee.setRotationPoint(0F, -21F, 0F);
        this.RAnkle = new ModelRenderer(this, 32, 0);
        this.RAnkle.addBox(-10.5F, 25F, -4.5F, 9, 20, 9);
        this.RAnkle.setRotationPoint(0F, -21F, 0F);
        this.RFoot = new ModelRenderer(this, 0, 192);
        this.RFoot.addBox(-10.5F, 38F, -23.5F, 9, 5, 9);
        this.RFoot.setRotationPoint(0F, -21F, 0F);
        setRotation(this.RFoot, 0.2617994F, 0F, 0F);
        this.Neck = new ModelRenderer(this, 52, 90);
        this.Neck.addBox(-4F, -8F, -5.8F, 8, 10, 8);
        this.Neck.setRotationPoint(0F, -44F, 0F);
        setRotation(this.Neck, 0.5235988F, 0F, 0F);
        this.Face = new ModelRenderer(this, 52, 70);
        this.Face.addBox(-4.5F, -11F, -9F, 9, 7, 8);
        this.Face.setRotationPoint(0F, -44F, 0F);
        this.Head = new ModelRenderer(this, 84, 88);
        this.Head.addBox(-6F, -20.5F, -9.5F, 12, 10, 10);
        this.Head.setRotationPoint(0F, -44F, 0F);
        this.Nose = new ModelRenderer(this, 82, 88);
        this.Nose.addBox(-1.5F, -12F, -12F, 3, 7, 3);
        this.Nose.setRotationPoint(0F, -44F, 0F);
        setRotation(this.Nose, -0.122173F, 0F, 0F);
        this.Mouth = new ModelRenderer(this, 77, 36);
        this.Mouth.addBox(-3F, -8F, -6.8F, 6, 2, 1);
        this.Mouth.setRotationPoint(0F, -44F, 0F);
        setRotation(this.Mouth, 0.5235988F, 0F, 0F);
        this.TreeBase = new ModelRenderer(this, 0, 136);
        this.TreeBase.addBox(-10F, -31.5F, -11.5F, 20, 13, 20);
        this.TreeBase.setRotationPoint(0F, -44F, 0F);
        this.Leave1 = new ModelRenderer(this, 0, 224);
        this.Leave1.addBox(-16F, -45F, -17F, 16, 16, 16);
        this.Leave1.setRotationPoint(0F, -44F, 0F);
        this.Leave2 = new ModelRenderer(this, 0, 224);
        this.Leave2.addBox(0F, -45F, -17F, 16, 16, 16);
        this.Leave2.setRotationPoint(0F, -44F, 0F);
        this.Leave3 = new ModelRenderer(this, 0, 224);
        this.Leave3.addBox(0F, -45F, -1F, 16, 16, 16);
        this.Leave3.setRotationPoint(0F, -44F, 0F);
        this.Leave4 = new ModelRenderer(this, 0, 224);
        this.Leave4.addBox(-16F, -45F, -1F, 16, 16, 16);
        this.Leave4.setRotationPoint(0F, -44F, 0F);
        this.Leave5 = new ModelRenderer(this, 0, 224);
        this.Leave5.addBox(-16F, -45F, -33F, 16, 16, 16);
        this.Leave5.setRotationPoint(0F, -44F, 0F);
        this.Leave6 = new ModelRenderer(this, 0, 224);
        this.Leave6.addBox(0F, -45F, -33F, 16, 16, 16);
        this.Leave6.setRotationPoint(0F, -44F, 0F);
        this.Leave7 = new ModelRenderer(this, 0, 224);
        this.Leave7.addBox(16F, -45F, -17F, 16, 16, 16);
        this.Leave7.setRotationPoint(0F, -44F, 0F);
        this.Leave8 = new ModelRenderer(this, 0, 224);
        this.Leave8.addBox(16F, -45F, -1F, 16, 16, 16);
        this.Leave8.setRotationPoint(0F, -44F, 0F);
        this.Leave9 = new ModelRenderer(this, 0, 224);
        this.Leave9.addBox(0F, -45F, 15F, 16, 16, 16);
        this.Leave9.setRotationPoint(0F, -44F, 0F);
        this.Leave10 = new ModelRenderer(this, 0, 224);
        this.Leave10.addBox(-16F, -45F, 15F, 16, 16, 16);
        this.Leave10.setRotationPoint(0F, -44F, 0F);
        this.Leave11 = new ModelRenderer(this, 0, 224);
        this.Leave11.addBox(-32F, -45F, -1F, 16, 16, 16);
        this.Leave11.setRotationPoint(0F, -44F, 0F);
        this.Leave12 = new ModelRenderer(this, 0, 224);
        this.Leave12.addBox(-32F, -45F, -17F, 16, 16, 16);
        this.Leave12.setRotationPoint(0F, -44F, 0F);
        this.Leave13 = new ModelRenderer(this, 0, 224);
        this.Leave13.addBox(-16F, -61F, -17F, 16, 16, 16);
        this.Leave13.setRotationPoint(0F, -44F, 0F);
        this.Leave14 = new ModelRenderer(this, 0, 224);
        this.Leave14.addBox(0F, -61F, -17F, 16, 16, 16);
        this.Leave14.setRotationPoint(0F, -44F, 0F);
        this.Leave15 = new ModelRenderer(this, 0, 224);
        this.Leave15.addBox(0F, -61F, -1F, 16, 16, 16);
        this.Leave15.setRotationPoint(0F, -44F, 0F);
        this.Leave16 = new ModelRenderer(this, 0, 224);
        this.Leave16.addBox(-16F, -61F, -1F, 16, 16, 16);
        this.Leave16.setRotationPoint(0F, -44F, 0F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Body.render(f5);
        this.LShoulder.render(f5);
        this.LArm.render(f5);
        this.LWrist.render(f5);
        this.LHand.render(f5);
        this.LFingers.render(f5);
        this.RShoulder.render(f5);
        this.RArm.render(f5);
        this.RWrist.render(f5);
        this.RHand.render(f5);
        this.RFingers.render(f5);
        this.LLeg.render(f5);
        this.LThigh.render(f5);
        this.LKnee.render(f5);
        this.LAnkle.render(f5);
        this.LFoot.render(f5);
        this.RLeg.render(f5);
        this.RThigh.render(f5);
        this.RKnee.render(f5);
        this.RAnkle.render(f5);
        this.RFoot.render(f5);
        this.Neck.render(f5);
        this.Face.render(f5);
        this.Head.render(f5);
        this.Nose.render(f5);
        this.Mouth.render(f5);
        this.TreeBase.render(f5);
        this.Leave1.render(f5);
        this.Leave2.render(f5);
        this.Leave3.render(f5);
        this.Leave4.render(f5);
        this.Leave5.render(f5);
        this.Leave6.render(f5);
        this.Leave7.render(f5);
        this.Leave8.render(f5);
        this.Leave9.render(f5);
        this.Leave10.render(f5);
        this.Leave11.render(f5);
        this.Leave12.render(f5);
        this.Leave13.render(f5);
        this.Leave14.render(f5);
        this.Leave15.render(f5);
        this.Leave16.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        float radianF = 57.29578F;

        float RArmXRot = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 2.0F * f1 * 0.5F;
        float LArmXRot = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        float RLegXRot = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.0F * f1;

        this.LWrist.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
        this.LWrist.rotateAngleX = LArmXRot;
        this.RWrist.rotateAngleZ = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        this.RWrist.rotateAngleX = RArmXRot;

        this.LHand.rotateAngleX = this.LFingers.rotateAngleX = this.LArm.rotateAngleX = this.LWrist.rotateAngleX;
        this.LHand.rotateAngleZ = this.LFingers.rotateAngleZ = this.LWrist.rotateAngleZ;
        this.LArm.rotateAngleZ = (-10F / radianF) + this.LWrist.rotateAngleZ;

        this.RHand.rotateAngleX = this.RFingers.rotateAngleX = this.RArm.rotateAngleX = this.RWrist.rotateAngleX;
        this.RHand.rotateAngleZ = this.RFingers.rotateAngleZ = this.RWrist.rotateAngleZ;
        this.RArm.rotateAngleZ = (10F / radianF) + this.RWrist.rotateAngleZ;

        this.RLeg.rotateAngleX = RLegXRot;
        this.LLeg.rotateAngleX = LLegXRot;
        this.LThigh.rotateAngleX = this.LKnee.rotateAngleX = this.LAnkle.rotateAngleX = this.LLeg.rotateAngleX;
        this.RThigh.rotateAngleX = this.RKnee.rotateAngleX = this.RAnkle.rotateAngleX = this.RLeg.rotateAngleX;

        this.LFoot.rotateAngleX = (15F / radianF) + this.LLeg.rotateAngleX;
        this.RFoot.rotateAngleX = (15F / radianF) + this.RLeg.rotateAngleX;
        this.Neck.rotateAngleY = f3 / radianF; //this moves head to left and right
        //Neck.rotateAngleX = f4/ radianF;

        this.Mouth.rotateAngleY =
                this.Face.rotateAngleY = this.Nose.rotateAngleY = this.Head.rotateAngleY = this.TreeBase.rotateAngleY = this.Neck.rotateAngleY;
        this.Leave1.rotateAngleY =
                this.Leave2.rotateAngleY =
                        this.Leave3.rotateAngleY =
                                this.Leave4.rotateAngleY = this.Leave5.rotateAngleY = this.Leave6.rotateAngleY = this.Neck.rotateAngleY;
        this.Leave7.rotateAngleY =
                this.Leave8.rotateAngleY = this.Leave9.rotateAngleY = this.Leave10.rotateAngleY = this.Leave11.rotateAngleY = this.Neck.rotateAngleY;
        this.Leave12.rotateAngleY =
                this.Leave13.rotateAngleY =
                        this.Leave14.rotateAngleY = this.Leave15.rotateAngleY = this.Leave16.rotateAngleY = this.Neck.rotateAngleY;

    }

}
