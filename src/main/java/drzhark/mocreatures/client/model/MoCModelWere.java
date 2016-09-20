package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelWere extends ModelBase {

    ModelRenderer Head;
    ModelRenderer Nose;
    ModelRenderer Snout;
    ModelRenderer TeethU;
    ModelRenderer TeethL;
    ModelRenderer Mouth;
    ModelRenderer LEar;
    ModelRenderer REar;
    ModelRenderer Neck;
    ModelRenderer Neck2;
    ModelRenderer SideburnL;
    ModelRenderer SideburnR;
    ModelRenderer Chest;
    ModelRenderer Abdomen;
    ModelRenderer TailA;
    ModelRenderer TailC;
    ModelRenderer TailB;
    ModelRenderer TailD;
    ModelRenderer RLegA;
    ModelRenderer RFoot;
    ModelRenderer RLegB;
    ModelRenderer RLegC;
    ModelRenderer LLegB;
    ModelRenderer LFoot;
    ModelRenderer LLegC;
    ModelRenderer LLegA;
    ModelRenderer RArmB;
    ModelRenderer RArmC;
    ModelRenderer LArmB;
    ModelRenderer RHand;
    ModelRenderer RArmA;
    ModelRenderer LArmA;
    ModelRenderer LArmC;
    ModelRenderer LHand;
    ModelRenderer RFinger1;
    ModelRenderer RFinger2;
    ModelRenderer RFinger3;
    ModelRenderer RFinger4;
    ModelRenderer RFinger5;
    ModelRenderer LFinger1;
    ModelRenderer LFinger2;
    ModelRenderer LFinger3;
    ModelRenderer LFinger4;
    ModelRenderer LFinger5;

    public boolean hunched;

    public MoCModelWere() {
        this.textureWidth = 64;
        this.textureHeight = 128;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-4F, -3F, -6F, 8, 8, 6);
        this.Head.setRotationPoint(0F, -8F, -6F);
        this.Head.setTextureSize(64, 128);

        this.Nose = new ModelRenderer(this, 44, 33);
        this.Nose.addBox(-1.5F, -1.7F, -12.3F, 3, 2, 7);
        this.Nose.setRotationPoint(0F, -8F, -6F);
        setRotation(this.Nose, 0.2792527F, 0F, 0F);

        this.Snout = new ModelRenderer(this, 0, 25);
        this.Snout.addBox(-2F, 2F, -12F, 4, 2, 6);
        this.Snout.setRotationPoint(0F, -8F, -6F);

        this.TeethU = new ModelRenderer(this, 46, 18);
        this.TeethU.addBox(-2F, 4.01F, -12F, 4, 2, 5);
        this.TeethU.setRotationPoint(0F, -8F, -6F);

        this.TeethL = new ModelRenderer(this, 20, 109);
        this.TeethL.addBox(-1.5F, -12.5F, 2.01F, 3, 5, 2);
        this.TeethL.setRotationPoint(0F, -8F, -6F);
        setRotation(this.TeethL, 2.530727F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 42, 69);
        this.Mouth.addBox(-1.5F, -12.5F, 0F, 3, 9, 2);
        this.Mouth.setRotationPoint(0F, -8F, -6F);
        setRotation(this.Mouth, 2.530727F, 0F, 0F);

        this.LEar = new ModelRenderer(this, 13, 14);
        this.LEar.addBox(0.5F, -7.5F, -1F, 3, 5, 1);
        this.LEar.setRotationPoint(0F, -8F, -6F);
        setRotation(this.LEar, 0F, 0F, 0.1745329F);

        this.REar = new ModelRenderer(this, 22, 0);
        this.REar.addBox(-3.5F, -7.5F, -1F, 3, 5, 1);
        this.REar.setRotationPoint(0F, -8F, -6F);
        setRotation(this.REar, 0F, 0F, -0.1745329F);

        this.Neck = new ModelRenderer(this, 28, 0);
        this.Neck.addBox(-3.5F, -3F, -7F, 7, 8, 7);
        this.Neck.setRotationPoint(0F, -5F, -2F);
        setRotation(this.Neck, -0.6025001F, 0F, 0F);

        this.Neck2 = new ModelRenderer(this, 0, 14);
        this.Neck2.addBox(-1.5F, -2F, -5F, 3, 4, 7);
        this.Neck2.setRotationPoint(0F, -1F, -6F);
        setRotation(this.Neck2, -0.4537856F, 0F, 0F);

        this.SideburnL = new ModelRenderer(this, 28, 33);
        this.SideburnL.addBox(3F, 0F, -2F, 2, 6, 6);
        this.SideburnL.setRotationPoint(0F, -8F, -6F);
        setRotation(this.SideburnL, -0.2094395F, 0.418879F, -0.0872665F);

        this.SideburnR = new ModelRenderer(this, 28, 45);
        this.SideburnR.addBox(-5F, 0F, -2F, 2, 6, 6);
        this.SideburnR.setRotationPoint(0F, -8F, -6F);
        setRotation(this.SideburnR, -0.2094395F, -0.418879F, 0.0872665F);

        this.Chest = new ModelRenderer(this, 20, 15);
        this.Chest.addBox(-4F, 0F, -7F, 8, 8, 10);
        this.Chest.setRotationPoint(0F, -6F, -2.5F);
        setRotation(this.Chest, 0.641331F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 0, 40);
        this.Abdomen.addBox(-3F, -8F, -8F, 6, 14, 8);
        this.Abdomen.setRotationPoint(0F, 4.5F, 5F);
        setRotation(this.Abdomen, 0.2695449F, 0F, 0F);

        this.TailA = new ModelRenderer(this, 52, 42);
        this.TailA.addBox(-1.5F, -1F, -2F, 3, 4, 3);
        this.TailA.setRotationPoint(0F, 9.5F, 6F);
        setRotation(this.TailA, 1.064651F, 0F, 0F);

        this.TailC = new ModelRenderer(this, 48, 59);
        this.TailC.addBox(-2F, 6.8F, -4.6F, 4, 6, 4);
        this.TailC.setRotationPoint(0F, 9.5F, 6F);
        setRotation(this.TailC, 1.099557F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 48, 49);
        this.TailB.addBox(-2F, 2F, -2F, 4, 6, 4);
        this.TailB.setRotationPoint(0F, 9.5F, 6F);
        setRotation(this.TailB, 0.7504916F, 0F, 0F);

        this.TailD = new ModelRenderer(this, 52, 69);
        this.TailD.addBox(-1.5F, 9.8F, -4.1F, 3, 5, 3);
        this.TailD.setRotationPoint(0F, 9.5F, 6F);
        setRotation(this.TailD, 1.099557F, 0F, 0F);

        this.RLegA = new ModelRenderer(this, 12, 64);
        this.RLegA.addBox(-2.5F, -1.5F, -3.5F, 3, 8, 5);
        this.RLegA.setRotationPoint(-3F, 9.5F, 3F);
        setRotation(this.RLegA, -0.8126625F, 0F, 0F);

        this.RFoot = new ModelRenderer(this, 14, 93);
        this.RFoot.addBox(-2.506667F, 12.5F, -5F, 3, 2, 3);
        this.RFoot.setRotationPoint(-3F, 9.5F, 3F);

        this.RLegB = new ModelRenderer(this, 14, 76);
        this.RLegB.addBox(-1.9F, 4.2F, 0.5F, 2, 2, 5);
        this.RLegB.setRotationPoint(-3F, 9.5F, 3F);
        setRotation(this.RLegB, -0.8445741F, 0F, 0F);

        this.RLegC = new ModelRenderer(this, 14, 83);
        this.RLegC.addBox(-2F, 6.2F, 0.5F, 2, 8, 2);
        this.RLegC.setRotationPoint(-3F, 9.5F, 3F);
        setRotation(this.RLegC, -0.2860688F, 0F, 0F);

        this.LLegB = new ModelRenderer(this, 0, 76);
        this.LLegB.addBox(-0.1F, 4.2F, 0.5F, 2, 2, 5);
        this.LLegB.setRotationPoint(3F, 9.5F, 3F);
        setRotation(this.LLegB, -0.8445741F, 0F, 0F);

        this.LFoot = new ModelRenderer(this, 0, 93);
        this.LFoot.addBox(-0.5066667F, 12.5F, -5F, 3, 2, 3);
        this.LFoot.setRotationPoint(3F, 9.5F, 3F);

        this.LLegC = new ModelRenderer(this, 0, 83);
        this.LLegC.addBox(0F, 6.2F, 0.5F, 2, 8, 2);
        this.LLegC.setRotationPoint(3F, 9.5F, 3F);
        setRotation(this.LLegC, -0.2860688F, 0F, 0F);

        this.LLegA = new ModelRenderer(this, 0, 64);
        this.LLegA.addBox(-0.5F, -1.5F, -3.5F, 3, 8, 5);
        this.LLegA.setRotationPoint(3F, 9.5F, 3F);
        setRotation(this.LLegA, -0.8126625F, 0F, 0F);

        this.RArmB = new ModelRenderer(this, 48, 77);
        this.RArmB.addBox(-3.5F, 1F, -1.5F, 4, 8, 4);
        this.RArmB.setRotationPoint(-4F, -4F, -2F);
        setRotation(this.RArmB, 0.2617994F, 0F, 0.3490659F);

        this.RArmC = new ModelRenderer(this, 48, 112);
        this.RArmC.addBox(-6F, 5F, 3F, 4, 7, 4);
        this.RArmC.setRotationPoint(-4F, -4F, -2F);
        setRotation(this.RArmC, -0.3490659F, 0F, 0F);

        this.LArmB = new ModelRenderer(this, 48, 89);
        this.LArmB.addBox(-0.5F, 1F, -1.5F, 4, 8, 4);
        this.LArmB.setRotationPoint(4F, -4F, -2F);
        setRotation(this.LArmB, 0.2617994F, 0F, -0.3490659F);

        this.RHand = new ModelRenderer(this, 32, 118);
        this.RHand.addBox(-6F, 12.5F, -1.5F, 4, 3, 4);
        this.RHand.setRotationPoint(-4F, -4F, -2F);

        this.RArmA = new ModelRenderer(this, 0, 108);
        this.RArmA.addBox(-5F, -3F, -2F, 5, 5, 5);
        this.RArmA.setRotationPoint(-4F, -4F, -2F);
        setRotation(this.RArmA, 0.6320364F, 0F, 0F);

        this.LArmA = new ModelRenderer(this, 0, 98);
        this.LArmA.addBox(0F, -3F, -2F, 5, 5, 5);
        this.LArmA.setRotationPoint(4F, -4F, -2F);
        setRotation(this.LArmA, 0.6320364F, 0F, 0F);

        this.LArmC = new ModelRenderer(this, 48, 101);
        this.LArmC.addBox(2F, 5F, 3F, 4, 7, 4);
        this.LArmC.setRotationPoint(4F, -4F, -2F);
        setRotation(this.LArmC, -0.3490659F, 0F, 0F);

        this.LHand = new ModelRenderer(this, 32, 111);
        this.LHand.addBox(2F, 12.5F, -1.5F, 4, 3, 4);
        this.LHand.setRotationPoint(4F, -4F, -2F);

        this.RFinger1 = new ModelRenderer(this, 8, 120);
        this.RFinger1.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
        this.RFinger1.setRotationPoint(-6.5F, 11.5F, -0.5F);

        this.RFinger1 = new ModelRenderer(this, 8, 120);
        this.RFinger1.addBox(-3F, 15.5F, 1F, 1, 3, 1);
        this.RFinger1.setRotationPoint(-4F, -4F, -2F);

        this.RFinger2 = new ModelRenderer(this, 12, 124);
        this.RFinger2.addBox(-3.5F, 15.5F, -1.5F, 1, 3, 1);
        this.RFinger2.setRotationPoint(-4F, -4F, -2F);

        this.RFinger3 = new ModelRenderer(this, 12, 119);
        this.RFinger3.addBox(-4.8F, 15.5F, -1.5F, 1, 4, 1);
        this.RFinger3.setRotationPoint(-4F, -4F, -2F);

        this.RFinger4 = new ModelRenderer(this, 16, 119);
        this.RFinger4.addBox(-6F, 15.5F, -0.5F, 1, 4, 1);
        this.RFinger4.setRotationPoint(-4F, -4F, -2F);

        this.RFinger5 = new ModelRenderer(this, 16, 124);
        this.RFinger5.addBox(-6F, 15.5F, 1F, 1, 3, 1);
        this.RFinger5.setRotationPoint(-4F, -4F, -2F);

        this.LFinger1 = new ModelRenderer(this, 8, 124);
        this.LFinger1.addBox(2F, 15.5F, 1F, 1, 3, 1);
        this.LFinger1.setRotationPoint(4F, -4F, -2F);

        this.LFinger2 = new ModelRenderer(this, 0, 124);
        this.LFinger2.addBox(2.5F, 15.5F, -1.5F, 1, 3, 1);
        this.LFinger2.setRotationPoint(4F, -4F, -2F);

        this.LFinger3 = new ModelRenderer(this, 0, 119);
        this.LFinger3.addBox(3.8F, 15.5F, -1.5F, 1, 4, 1);
        this.LFinger3.setRotationPoint(4F, -4F, -2F);

        this.LFinger4 = new ModelRenderer(this, 4, 119);
        this.LFinger4.addBox(5F, 15.5F, -0.5F, 1, 4, 1);
        this.LFinger4.setRotationPoint(4F, -4F, -2F);

        this.LFinger5 = new ModelRenderer(this, 4, 124);
        this.LFinger5.addBox(5F, 15.5F, 1F, 1, 3, 1);
        this.LFinger5.setRotationPoint(4F, -4F, -2F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Head.render(f5);
        this.Nose.render(f5);
        this.Snout.render(f5);
        this.TeethU.render(f5);
        this.TeethL.render(f5);
        this.Mouth.render(f5);
        this.LEar.render(f5);
        this.REar.render(f5);
        this.Neck.render(f5);
        this.Neck2.render(f5);
        this.SideburnL.render(f5);
        this.SideburnR.render(f5);
        this.Chest.render(f5);
        this.Abdomen.render(f5);
        this.TailA.render(f5);
        this.TailC.render(f5);
        this.TailB.render(f5);
        this.TailD.render(f5);
        this.RLegA.render(f5);
        this.RFoot.render(f5);
        this.RLegB.render(f5);
        this.RLegC.render(f5);
        this.LLegB.render(f5);
        this.LFoot.render(f5);
        this.LLegC.render(f5);
        this.LLegA.render(f5);
        this.RArmB.render(f5);
        this.RArmC.render(f5);
        this.LArmB.render(f5);
        this.RHand.render(f5);
        this.RArmA.render(f5);
        this.LArmA.render(f5);
        this.LArmC.render(f5);
        this.LHand.render(f5);
        this.RFinger1.render(f5);
        this.RFinger2.render(f5);
        this.RFinger3.render(f5);
        this.RFinger4.render(f5);
        this.RFinger5.render(f5);
        this.LFinger1.render(f5);
        this.LFinger2.render(f5);
        this.LFinger3.render(f5);
        this.LFinger4.render(f5);
        this.LFinger5.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {

        float radianF = 57.29578F;
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;

        this.Head.rotateAngleY = f3 / radianF; //this moves head to left and right
        //Head.rotateAngleX = f4 / radianF; //this moves head up and down

        if (!this.hunched) {
            this.Head.rotationPointY = -8F;
            this.Head.rotationPointZ = -6F;
            this.Head.rotateAngleX = f4 / radianF;
            this.Neck.rotateAngleX = -34 / radianF;
            this.Neck.rotationPointY = -5F;
            this.Neck.rotationPointZ = -2F;
            this.Neck2.rotationPointY = -1F;
            this.Neck2.rotationPointZ = -6F;
            this.Chest.rotationPointY = -6F;
            this.Chest.rotationPointZ = -2.5F;
            this.Chest.rotateAngleX = 36 / radianF;
            this.Abdomen.rotateAngleX = 15 / radianF;
            this.LLegA.rotationPointZ = 3F;

            this.LArmA.rotationPointY = -4F;
            this.LArmA.rotationPointZ = -2F;

            this.TailA.rotationPointY = 9.5F;
            this.TailA.rotationPointZ = 6F;

        } else {
            this.Head.rotationPointY = 0F;
            this.Head.rotationPointZ = -11F;
            this.Head.rotateAngleX = (15F + f4) / radianF;

            this.Neck.rotateAngleX = -10 / radianF;
            this.Neck.rotationPointY = 2F;
            this.Neck.rotationPointZ = -6F;
            this.Neck2.rotationPointY = 9F;
            this.Neck2.rotationPointZ = -9F;
            this.Chest.rotationPointY = 1F;
            this.Chest.rotationPointZ = -7.5F;
            this.Chest.rotateAngleX = 60 / radianF;
            this.Abdomen.rotateAngleX = 75 / radianF;
            this.LLegA.rotationPointZ = 7F;
            this.LArmA.rotationPointY = 4.5F;
            this.LArmA.rotationPointZ = -6F;
            this.TailA.rotationPointY = 7.5F;
            this.TailA.rotationPointZ = 10F;

        }

        this.Nose.rotationPointY = this.Head.rotationPointY;
        this.Snout.rotationPointY = this.Head.rotationPointY;
        this.TeethU.rotationPointY = this.Head.rotationPointY;
        this.LEar.rotationPointY = this.Head.rotationPointY;
        this.REar.rotationPointY = this.Head.rotationPointY;
        this.TeethL.rotationPointY = this.Head.rotationPointY;
        this.Mouth.rotationPointY = this.Head.rotationPointY;
        this.SideburnL.rotationPointY = this.Head.rotationPointY;
        this.SideburnR.rotationPointY = this.Head.rotationPointY;

        this.Nose.rotationPointZ = this.Head.rotationPointZ;
        this.Snout.rotationPointZ = this.Head.rotationPointZ;
        this.TeethU.rotationPointZ = this.Head.rotationPointZ;
        this.LEar.rotationPointZ = this.Head.rotationPointZ;
        this.REar.rotationPointZ = this.Head.rotationPointZ;
        this.TeethL.rotationPointZ = this.Head.rotationPointZ;
        this.Mouth.rotationPointZ = this.Head.rotationPointZ;
        this.SideburnL.rotationPointZ = this.Head.rotationPointZ;
        this.SideburnR.rotationPointZ = this.Head.rotationPointZ;

        this.LArmB.rotationPointY = this.LArmA.rotationPointY;
        this.LArmC.rotationPointY = this.LArmA.rotationPointY;
        this.LHand.rotationPointY = this.LArmA.rotationPointY;
        this.LFinger1.rotationPointY = this.LArmA.rotationPointY;
        this.LFinger2.rotationPointY = this.LArmA.rotationPointY;
        this.LFinger3.rotationPointY = this.LArmA.rotationPointY;
        this.LFinger4.rotationPointY = this.LArmA.rotationPointY;
        this.LFinger5.rotationPointY = this.LArmA.rotationPointY;
        this.RArmA.rotationPointY = this.LArmA.rotationPointY;
        this.RArmB.rotationPointY = this.LArmA.rotationPointY;
        this.RArmC.rotationPointY = this.LArmA.rotationPointY;
        this.RHand.rotationPointY = this.LArmA.rotationPointY;
        this.RFinger1.rotationPointY = this.LArmA.rotationPointY;
        this.RFinger2.rotationPointY = this.LArmA.rotationPointY;
        this.RFinger3.rotationPointY = this.LArmA.rotationPointY;
        this.RFinger4.rotationPointY = this.LArmA.rotationPointY;
        this.RFinger5.rotationPointY = this.LArmA.rotationPointY;

        this.LArmB.rotationPointZ = this.LArmA.rotationPointZ;
        this.LArmC.rotationPointZ = this.LArmA.rotationPointZ;
        this.LHand.rotationPointZ = this.LArmA.rotationPointZ;
        this.LFinger1.rotationPointZ = this.LArmA.rotationPointZ;
        this.LFinger2.rotationPointZ = this.LArmA.rotationPointZ;
        this.LFinger3.rotationPointZ = this.LArmA.rotationPointZ;
        this.LFinger4.rotationPointZ = this.LArmA.rotationPointZ;
        this.LFinger5.rotationPointZ = this.LArmA.rotationPointZ;
        this.RArmA.rotationPointZ = this.LArmA.rotationPointZ;
        this.RArmB.rotationPointZ = this.LArmA.rotationPointZ;
        this.RArmC.rotationPointZ = this.LArmA.rotationPointZ;
        this.RHand.rotationPointZ = this.LArmA.rotationPointZ;
        this.RFinger1.rotationPointZ = this.LArmA.rotationPointZ;
        this.RFinger2.rotationPointZ = this.LArmA.rotationPointZ;
        this.RFinger3.rotationPointZ = this.LArmA.rotationPointZ;
        this.RFinger4.rotationPointZ = this.LArmA.rotationPointZ;
        this.RFinger5.rotationPointZ = this.LArmA.rotationPointZ;

        this.RLegA.rotationPointZ = this.LLegA.rotationPointZ;
        this.RLegB.rotationPointZ = this.LLegA.rotationPointZ;
        this.RLegC.rotationPointZ = this.LLegA.rotationPointZ;
        this.RFoot.rotationPointZ = this.LLegA.rotationPointZ;
        this.LLegB.rotationPointZ = this.LLegA.rotationPointZ;
        this.LLegC.rotationPointZ = this.LLegA.rotationPointZ;
        this.LFoot.rotationPointZ = this.LLegA.rotationPointZ;

        this.TailB.rotationPointY = this.TailA.rotationPointY;
        this.TailB.rotationPointZ = this.TailA.rotationPointZ;
        this.TailC.rotationPointY = this.TailA.rotationPointY;
        this.TailC.rotationPointZ = this.TailA.rotationPointZ;
        this.TailD.rotationPointY = this.TailA.rotationPointY;
        this.TailD.rotationPointZ = this.TailA.rotationPointZ;

        this.Nose.rotateAngleY = this.Head.rotateAngleY;
        this.Snout.rotateAngleY = this.Head.rotateAngleY;
        this.TeethU.rotateAngleY = this.Head.rotateAngleY;
        this.LEar.rotateAngleY = this.Head.rotateAngleY;
        this.REar.rotateAngleY = this.Head.rotateAngleY;
        this.TeethL.rotateAngleY = this.Head.rotateAngleY;
        this.Mouth.rotateAngleY = this.Head.rotateAngleY;

        this.TeethL.rotateAngleX = this.Head.rotateAngleX + 2.530727F;
        this.Mouth.rotateAngleX = this.Head.rotateAngleX + 2.530727F;

        this.SideburnL.rotateAngleX = -0.2094395F + this.Head.rotateAngleX;
        this.SideburnL.rotateAngleY = 0.418879F + this.Head.rotateAngleY;
        this.SideburnR.rotateAngleX = -0.2094395F + this.Head.rotateAngleX;
        this.SideburnR.rotateAngleY = -0.418879F + this.Head.rotateAngleY;

        this.Nose.rotateAngleX = 0.2792527F + this.Head.rotateAngleX;
        this.Snout.rotateAngleX = this.Head.rotateAngleX;
        this.TeethU.rotateAngleX = this.Head.rotateAngleX;

        this.LEar.rotateAngleX = this.Head.rotateAngleX;
        this.REar.rotateAngleX = this.Head.rotateAngleX;

        this.RLegA.rotateAngleX = -0.8126625F + RLegXRot;
        this.RLegB.rotateAngleX = -0.8445741F + RLegXRot;
        this.RLegC.rotateAngleX = -0.2860688F + RLegXRot;
        this.RFoot.rotateAngleX = RLegXRot;

        this.LLegA.rotateAngleX = -0.8126625F + LLegXRot;
        this.LLegB.rotateAngleX = -0.8445741F + LLegXRot;
        this.LLegC.rotateAngleX = -0.2860688F + LLegXRot;
        this.LFoot.rotateAngleX = LLegXRot;

        this.RArmA.rotateAngleZ = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        this.LArmA.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
        this.RArmA.rotateAngleX = LLegXRot;//MathHelper.sin(f2 * 0.067F) * 0.05F;
        this.LArmA.rotateAngleX = RLegXRot;//MathHelper.sin(f2 * 0.067F) * 0.05F;

        this.RArmB.rotateAngleZ = 0.3490659F + this.RArmA.rotateAngleZ;
        this.LArmB.rotateAngleZ = -0.3490659F + this.LArmA.rotateAngleZ;
        this.RArmB.rotateAngleX = 0.2617994F + this.RArmA.rotateAngleX;
        this.LArmB.rotateAngleX = 0.2617994F + this.LArmA.rotateAngleX;

        this.RArmC.rotateAngleZ = this.RArmA.rotateAngleZ;
        this.LArmC.rotateAngleZ = this.LArmA.rotateAngleZ;
        this.RArmC.rotateAngleX = -0.3490659F + this.RArmA.rotateAngleX;
        this.LArmC.rotateAngleX = -0.3490659F + this.LArmA.rotateAngleX;

        this.RHand.rotateAngleZ = this.RArmA.rotateAngleZ;
        this.LHand.rotateAngleZ = this.LArmA.rotateAngleZ;
        this.RHand.rotateAngleX = this.RArmA.rotateAngleX;
        this.LHand.rotateAngleX = this.LArmA.rotateAngleX;

        this.RFinger1.rotateAngleX = this.RArmA.rotateAngleX;
        this.RFinger2.rotateAngleX = this.RArmA.rotateAngleX;
        this.RFinger3.rotateAngleX = this.RArmA.rotateAngleX;
        this.RFinger4.rotateAngleX = this.RArmA.rotateAngleX;
        this.RFinger5.rotateAngleX = this.RArmA.rotateAngleX;

        this.LFinger1.rotateAngleX = this.LArmA.rotateAngleX;
        this.LFinger2.rotateAngleX = this.LArmA.rotateAngleX;
        this.LFinger3.rotateAngleX = this.LArmA.rotateAngleX;
        this.LFinger4.rotateAngleX = this.LArmA.rotateAngleX;
        this.LFinger5.rotateAngleX = this.LArmA.rotateAngleX;

        this.RFinger1.rotateAngleZ = this.RArmA.rotateAngleZ;
        this.RFinger2.rotateAngleZ = this.RArmA.rotateAngleZ;
        this.RFinger3.rotateAngleZ = this.RArmA.rotateAngleZ;
        this.RFinger4.rotateAngleZ = this.RArmA.rotateAngleZ;
        this.RFinger5.rotateAngleZ = this.RArmA.rotateAngleZ;

        this.LFinger1.rotateAngleZ = this.LArmA.rotateAngleZ;
        this.LFinger2.rotateAngleZ = this.LArmA.rotateAngleZ;
        this.LFinger3.rotateAngleZ = this.LArmA.rotateAngleZ;
        this.LFinger4.rotateAngleZ = this.LArmA.rotateAngleZ;
        this.LFinger5.rotateAngleZ = this.LArmA.rotateAngleZ;

    }

}
