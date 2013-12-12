package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MoCModelEnt extends ModelBase{


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

    public MoCModelEnt()
    {
        textureWidth = 128;
        textureHeight = 256;

        Body = new ModelRenderer(this, 68, 36);
        Body.addBox(-7.5F, -12.5F, -4.5F, 15, 25, 9);
        Body.setRotationPoint(0F, -31F, 0F);
        LShoulder = new ModelRenderer(this, 48, 108);
        LShoulder.addBox(6F, -14F, -4.8F, 9, 7, 7);
        LShoulder.setRotationPoint(0F, -31F, 0F);
        setRotation(LShoulder, 0F, 0F, -0.1745329F);
        LArm = new ModelRenderer(this, 80, 108);
        LArm.addBox(0F, -4F, -5F, 6, 24, 6);
        LArm.setRotationPoint(10F, -42F, 1F);
        setRotation(LArm, 0F, 0F, -0.1745329F);
        LWrist = new ModelRenderer(this, 0, 169);
        LWrist.addBox(2F, 17F, -6F, 8, 15, 8);
        LWrist.setRotationPoint(10F, -42F, 1F);
        LHand = new ModelRenderer(this, 88, 241);
        LHand.addBox(1F, 28F, -7F, 10, 5, 10);
        LHand.setRotationPoint(10F, -42F, 1F);
        LFingers = new ModelRenderer(this, 88, 176);
        LFingers.addBox(1F, 33F, -7F, 10, 15, 10);
        LFingers.setRotationPoint(10F, -42F, 1F);
        RShoulder = new ModelRenderer(this, 48, 122);
        RShoulder.addBox(-15F, -14F, -4.8F, 9, 7, 7);
        RShoulder.setRotationPoint(0F, -31F, 0F);
        setRotation(RShoulder, 0F, 0F, 0.1745329F);
        RArm = new ModelRenderer(this, 104, 108);
        RArm.addBox(-6F, -4F, -5F, 6, 24, 6);
        RArm.setRotationPoint(-10F, -42F, 1F);
        setRotation(RArm, 0F, 0F, 0.1745329F);
        RWrist = new ModelRenderer(this, 32, 169);
        RWrist.addBox(-10F, 17F, -6F, 8, 15, 8);
        RWrist.setRotationPoint(-10F, -42F, 1F);
        RHand = new ModelRenderer(this, 88, 226);
        RHand.addBox(-11F, 28F, -7F, 10, 5, 10);
        RHand.setRotationPoint(-10F, -42F, 1F);
        RFingers = new ModelRenderer(this, 88, 201);
        RFingers.addBox(-11F, 33F, -7F, 10, 15, 10);
        RFingers.setRotationPoint(-10F, -42F, 1F);
        LLeg = new ModelRenderer(this, 0, 90);
        LLeg.addBox(3F, 0F, -3F, 6, 20, 6);
        LLeg.setRotationPoint(0F, -21F, 0F);
        LThigh = new ModelRenderer(this, 24, 64);
        LThigh.addBox(2.5F, 4F, -3.5F, 7, 12, 7);
        LThigh.setRotationPoint(0F, -21F, 0F);
        LKnee = new ModelRenderer(this, 0, 0);
        LKnee.addBox(2F, 20F, -4F, 8, 24, 8);
        LKnee.setRotationPoint(0F, -21F, 0F);
        LAnkle = new ModelRenderer(this, 32, 29);
        LAnkle.addBox(1.5F, 25F, -4.5F, 9, 20, 9);
        LAnkle.setRotationPoint(0F, -21F, 0F);
        LFoot = new ModelRenderer(this, 0, 206);
        LFoot.addBox(1.5F, 38F, -23.5F, 9, 5, 9);
        LFoot.setRotationPoint(0F, -21F, 0F);
        setRotation(LFoot, 0.2617994F, 0F, 0F);
        RLeg = new ModelRenderer(this, 0, 64);
        RLeg.addBox(-9F, 0F, -3F, 6, 20, 6);
        RLeg.setRotationPoint(0F, -21F, 0F);
        RThigh = new ModelRenderer(this, 24, 83);
        RThigh.addBox(-9.5F, 4F, -3.5F, 7, 12, 7);
        RThigh.setRotationPoint(0F, -21F, 0F);
        RKnee = new ModelRenderer(this, 0, 32);
        RKnee.addBox(-10F, 20F, -4F, 8, 24, 8);
        RKnee.setRotationPoint(0F, -21F, 0F);
        RAnkle = new ModelRenderer(this, 32, 0);
        RAnkle.addBox(-10.5F, 25F, -4.5F, 9, 20, 9);
        RAnkle.setRotationPoint(0F, -21F, 0F);
        RFoot = new ModelRenderer(this, 0, 192);
        RFoot.addBox(-10.5F, 38F, -23.5F, 9, 5, 9);
        RFoot.setRotationPoint(0F, -21F, 0F);
        setRotation(RFoot, 0.2617994F, 0F, 0F);
        Neck = new ModelRenderer(this, 52, 90);
        Neck.addBox(-4F, -8F, -5.8F, 8, 10, 8);
        Neck.setRotationPoint(0F, -44F, 0F);
        setRotation(Neck, 0.5235988F, 0F, 0F);
        Face = new ModelRenderer(this, 52, 70);
        Face.addBox(-4.5F, -11F, -9F, 9, 7, 8);
        Face.setRotationPoint(0F, -44F, 0F);
        Head = new ModelRenderer(this, 84, 88);
        Head.addBox(-6F, -20.5F, -9.5F, 12, 10, 10);
        Head.setRotationPoint(0F, -44F, 0F);
        Nose = new ModelRenderer(this, 82, 88);
        Nose.addBox(-1.5F, -12F, -12F, 3, 7, 3);
        Nose.setRotationPoint(0F, -44F, 0F);
        setRotation(Nose, -0.122173F, 0F, 0F);
        Mouth = new ModelRenderer(this, 77, 36);
        Mouth.addBox(-3F, -8F, -6.8F, 6, 2, 1);
        Mouth.setRotationPoint(0F, -44F, 0F);
        setRotation(Mouth, 0.5235988F, 0F, 0F);
        TreeBase = new ModelRenderer(this, 0, 136);
        TreeBase.addBox(-10F, -31.5F, -11.5F, 20, 13, 20);
        TreeBase.setRotationPoint(0F, -44F, 0F);
        Leave1 = new ModelRenderer(this, 0, 224);
        Leave1.addBox(-16F, -45F, -17F, 16, 16, 16);
        Leave1.setRotationPoint(0F, -44F, 0F);
        Leave2 = new ModelRenderer(this, 0, 224);
        Leave2.addBox(0F, -45F, -17F, 16, 16, 16);
        Leave2.setRotationPoint(0F, -44F, 0F);
        Leave3 = new ModelRenderer(this, 0, 224);
        Leave3.addBox(0F, -45F, -1F, 16, 16, 16);
        Leave3.setRotationPoint(0F, -44F, 0F);
        Leave4 = new ModelRenderer(this, 0, 224);
        Leave4.addBox(-16F, -45F, -1F, 16, 16, 16);
        Leave4.setRotationPoint(0F, -44F, 0F);
        Leave5 = new ModelRenderer(this, 0, 224);
        Leave5.addBox(-16F, -45F, -33F, 16, 16, 16);
        Leave5.setRotationPoint(0F, -44F, 0F);
        Leave6 = new ModelRenderer(this, 0, 224);
        Leave6.addBox(0F, -45F, -33F, 16, 16, 16);
        Leave6.setRotationPoint(0F, -44F, 0F);
        Leave7 = new ModelRenderer(this, 0, 224);
        Leave7.addBox(16F, -45F, -17F, 16, 16, 16);
        Leave7.setRotationPoint(0F, -44F, 0F);
        Leave8 = new ModelRenderer(this, 0, 224);
        Leave8.addBox(16F, -45F, -1F, 16, 16, 16);
        Leave8.setRotationPoint(0F, -44F, 0F);
        Leave9 = new ModelRenderer(this, 0, 224);
        Leave9.addBox(0F, -45F, 15F, 16, 16, 16);
        Leave9.setRotationPoint(0F, -44F, 0F);
        Leave10 = new ModelRenderer(this, 0, 224);
        Leave10.addBox(-16F, -45F, 15F, 16, 16, 16);
        Leave10.setRotationPoint(0F, -44F, 0F);
        Leave11 = new ModelRenderer(this, 0, 224);
        Leave11.addBox(-32F, -45F, -1F, 16, 16, 16);
        Leave11.setRotationPoint(0F, -44F, 0F);
        Leave12 = new ModelRenderer(this, 0, 224);
        Leave12.addBox(-32F, -45F, -17F, 16, 16, 16);
        Leave12.setRotationPoint(0F, -44F, 0F);
        Leave13 = new ModelRenderer(this, 0, 224);
        Leave13.addBox(-16F, -61F, -17F, 16, 16, 16);
        Leave13.setRotationPoint(0F, -44F, 0F);
        Leave14 = new ModelRenderer(this, 0, 224);
        Leave14.addBox(0F, -61F, -17F, 16, 16, 16);
        Leave14.setRotationPoint(0F, -44F, 0F);
        Leave15 = new ModelRenderer(this, 0, 224);
        Leave15.addBox(0F, -61F, -1F, 16, 16, 16);
        Leave15.setRotationPoint(0F, -44F, 0F);
        Leave16 = new ModelRenderer(this, 0, 224);
        Leave16.addBox(-16F, -61F, -1F, 16, 16, 16);
        Leave16.setRotationPoint(0F, -44F, 0F);

    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Body.render(f5);
        LShoulder.render(f5);
        LArm.render(f5);
        LWrist.render(f5);
        LHand.render(f5);
        LFingers.render(f5);
        RShoulder.render(f5);
        RArm.render(f5);
        RWrist.render(f5);
        RHand.render(f5);
        RFingers.render(f5);
        LLeg.render(f5);
        LThigh.render(f5);
        LKnee.render(f5);
        LAnkle.render(f5);
        LFoot.render(f5);
        RLeg.render(f5);
        RThigh.render(f5);
        RKnee.render(f5);
        RAnkle.render(f5);
        RFoot.render(f5);
        Neck.render(f5);
        Face.render(f5);
        Head.render(f5);
        Nose.render(f5);
        Mouth.render(f5);
        TreeBase.render(f5);
        Leave1.render(f5);
        Leave2.render(f5);
        Leave3.render(f5);
        Leave4.render(f5);
        Leave5.render(f5);
        Leave6.render(f5);
        Leave7.render(f5);
        Leave8.render(f5);
        Leave9.render(f5);
        Leave10.render(f5);
        Leave11.render(f5);
        Leave12.render(f5);
        Leave13.render(f5);
        Leave14.render(f5);
        Leave15.render(f5);
        Leave16.render(f5);
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
        float radianF = 57.29578F;
        
        float RArmXRot = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
        float LArmXRot = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        float RLegXRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;

        LWrist.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
        LWrist.rotateAngleX = LArmXRot;
        RWrist.rotateAngleZ = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        RWrist.rotateAngleX = RArmXRot;
        
        LHand.rotateAngleX = LFingers.rotateAngleX = LArm.rotateAngleX = LWrist.rotateAngleX;
        LHand.rotateAngleZ = LFingers.rotateAngleZ = LWrist.rotateAngleZ;
        LArm.rotateAngleZ = (-10F/radianF) + LWrist.rotateAngleZ;
        
        RHand.rotateAngleX = RFingers.rotateAngleX = RArm.rotateAngleX = RWrist.rotateAngleX;
        RHand.rotateAngleZ = RFingers.rotateAngleZ = RWrist.rotateAngleZ;
        RArm.rotateAngleZ = (10F/radianF) + RWrist.rotateAngleZ;
        
        RLeg.rotateAngleX = RLegXRot;
        LLeg.rotateAngleX = LLegXRot;
        LThigh.rotateAngleX = LKnee.rotateAngleX = LAnkle.rotateAngleX = LLeg.rotateAngleX;
        RThigh.rotateAngleX = RKnee.rotateAngleX = RAnkle.rotateAngleX = RLeg.rotateAngleX;
        
        LFoot.rotateAngleX = (15F/radianF) + LLeg.rotateAngleX;
        RFoot.rotateAngleX = (15F/radianF) + RLeg.rotateAngleX;
        Neck.rotateAngleY = f3 / radianF; //this moves head to left and right
        //Neck.rotateAngleX = f4/ radianF; 
        
        Mouth.rotateAngleY = Face.rotateAngleY = Nose.rotateAngleY = Head.rotateAngleY = TreeBase.rotateAngleY = Neck.rotateAngleY;
        Leave1.rotateAngleY = Leave2.rotateAngleY = Leave3.rotateAngleY = Leave4.rotateAngleY = Leave5.rotateAngleY = Leave6.rotateAngleY = Neck.rotateAngleY;
        Leave7.rotateAngleY = Leave8.rotateAngleY = Leave9.rotateAngleY = Leave10.rotateAngleY = Leave11.rotateAngleY = Neck.rotateAngleY;
        Leave12.rotateAngleY = Leave13.rotateAngleY = Leave14.rotateAngleY = Leave15.rotateAngleY = Leave16.rotateAngleY = Neck.rotateAngleY;

    }

}
