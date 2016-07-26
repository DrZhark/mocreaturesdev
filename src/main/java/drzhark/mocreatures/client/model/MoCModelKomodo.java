package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class MoCModelKomodo extends ModelBase {

    //ModelRenderer TongueDown;
    ModelRenderer Tail;
    ModelRenderer Tail4;
    ModelRenderer Tail1;
    ModelRenderer Tail2;
    ModelRenderer Tail3;
    ModelRenderer LegBackLeft1;
    ModelRenderer Neck;
    ModelRenderer Head;
    ModelRenderer Chest;
    ModelRenderer LegBackRight1;
    ModelRenderer LegFrontLeft1;
    ModelRenderer LegFrontLeft3;
    ModelRenderer LegFrontRight1;
    ModelRenderer LegBackLeft2;
    ModelRenderer LegFrontRight2;
    ModelRenderer LegFrontLeft2;
    ModelRenderer LegBackRight2;
    ModelRenderer LegFrontRight3;
    ModelRenderer LegBackLeft3;
    ModelRenderer LegBackRight3;
    ModelRenderer Abdomen;
    ModelRenderer Mouth;
    //ModelRenderer TongeUp;
    ModelRenderer LegFrontLeft;
    ModelRenderer LegBackLeft;
    ModelRenderer LegFrontRight;
    ModelRenderer LegBackRight;
    ModelRenderer Nose;
    ModelRenderer Tongue;

    ModelRenderer SaddleA;
    ModelRenderer SaddleC;
    ModelRenderer SaddleB;
    private float radianF = 57.29578F;

    public MoCModelKomodo() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.Head = new ModelRenderer(this);
        this.Head.setRotationPoint(0F, 13F, -8F);

        this.Neck = new ModelRenderer(this, 22, 34);
        this.Neck.addBox(-2F, 0F, -6F, 4, 5, 6);
        this.Neck.setRotationPoint(0F, 0F, 0F);
        this.Head.addChild(this.Neck);
        //setRotation(Neck, 0.1919862F, 0F, 0F);

        this.Nose = new ModelRenderer(this, 24, 45);
        this.Nose.addBox(-1.5F, -1F, -6.5F, 3, 2, 6);
        this.Nose.setRotationPoint(0F, 1F, -5F);
        this.Neck.addChild(this.Nose);
        //setRotation(Head, 0.3778793F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 0, 12);
        this.Mouth.addBox(-1F, -0.3F, -5F, 2, 1, 6);
        this.Mouth.setRotationPoint(0F, 3F, -5.8F);
        this.Neck.addChild(this.Mouth);
        //setRotation(Mouth, 0.2291648F, 0F, 0F);

        this.Tongue = new ModelRenderer(this, 48, 44);
        this.Tongue.addBox(-1.5F, 0.0F, -5F, 3, 0, 5);
        this.Tongue.setRotationPoint(0F, -0.4F, -4.7F);
        this.Mouth.addChild(this.Tongue);

        this.Chest = new ModelRenderer(this, 36, 2);
        this.Chest.addBox(-3F, 0F, -8F, 6, 6, 7);
        this.Chest.setRotationPoint(0F, 13F, 0F);

        this.Abdomen = new ModelRenderer(this, 36, 49);
        this.Abdomen.addBox(-3F, 0F, -1F, 6, 7, 8);
        this.Abdomen.setRotationPoint(0F, 13F, 0F);

        this.Tail = new ModelRenderer(this);
        this.Tail.setRotationPoint(0F, 13F, 7F);

        this.Tail1 = new ModelRenderer(this, 0, 21);
        this.Tail1.addBox(-2F, 0F, 0F, 4, 5, 8);
        this.Tail1.setRotationPoint(0F, 0F, 0F);
        //setRotation(Tail1, -0.2724366F, 0F, 0F);
        this.Tail.addChild(this.Tail1);

        this.Tail2 = new ModelRenderer(this, 0, 34);
        this.Tail2.addBox(-1.5F, 0F, 0F, 3, 4, 8);
        this.Tail2.setRotationPoint(0F, 0.1F, 7.7F);
        //setRotation(Tail2, -0.5698655F, 0F, 0F);
        this.Tail1.addChild(this.Tail2);

        this.Tail3 = new ModelRenderer(this, 0, 46);
        this.Tail3.addBox(-1F, 0F, 0F, 2, 3, 8);
        this.Tail3.setRotationPoint(0F, 0.1F, 7.3F);
        //setRotation(Tail3, -0.3361566F, 0F, 0F);
        this.Tail2.addChild(this.Tail3);

        this.Tail4 = new ModelRenderer(this, 24, 21);
        this.Tail4.addBox(-0.5F, 0F, 0F, 1, 2, 8);
        this.Tail4.setRotationPoint(0F, 0.1F, 7F);
        //setRotation(Tail4, -0.1502636F, 0F, 0F);
        this.Tail3.addChild(this.Tail4);

        this.LegFrontLeft = new ModelRenderer(this);
        this.LegFrontLeft.setRotationPoint(2F, 17F, -7F);

        this.LegFrontLeft1 = new ModelRenderer(this, 0, 0);
        this.LegFrontLeft1.addBox(0F, -1F, -1.5F, 4, 3, 3);
        this.LegFrontLeft1.setRotationPoint(0F, 0F, 0F);
        //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
        this.LegFrontLeft.addChild(this.LegFrontLeft1);

        this.LegFrontLeft2 = new ModelRenderer(this, 22, 0);
        this.LegFrontLeft2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.LegFrontLeft2.setRotationPoint(3F, 0.5F, 0F);
        this.LegFrontLeft1.addChild(this.LegFrontLeft2);

        this.LegFrontLeft3 = new ModelRenderer(this, 16, 58);
        this.LegFrontLeft3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
        this.LegFrontLeft3.setRotationPoint(0F, 4F, 0F);
        setRotation(this.LegFrontLeft3, 0F, -10F / this.radianF, 0F);
        this.LegFrontLeft2.addChild(this.LegFrontLeft3);

        this.LegBackLeft = new ModelRenderer(this);
        this.LegBackLeft.setRotationPoint(2F, 17F, 6F);

        this.LegBackLeft1 = new ModelRenderer(this, 0, 0);
        this.LegBackLeft1.addBox(0F, -1F, -1.5F, 4, 3, 3);
        this.LegBackLeft1.setRotationPoint(0F, 0F, 0F);
        //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
        this.LegBackLeft.addChild(this.LegBackLeft1);

        this.LegBackLeft2 = new ModelRenderer(this, 22, 0);
        this.LegBackLeft2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.LegBackLeft2.setRotationPoint(3F, 0.5F, 0F);
        this.LegBackLeft1.addChild(this.LegBackLeft2);

        this.LegBackLeft3 = new ModelRenderer(this, 16, 58);
        this.LegBackLeft3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
        this.LegBackLeft3.setRotationPoint(0F, 4F, 0F);
        setRotation(this.LegBackLeft3, 0F, -10F / this.radianF, 0F);
        this.LegBackLeft2.addChild(this.LegBackLeft3);

        this.LegFrontRight = new ModelRenderer(this);
        this.LegFrontRight.setRotationPoint(-2F, 17F, -7F);

        this.LegFrontRight1 = new ModelRenderer(this, 0, 6);
        this.LegFrontRight1.addBox(-4F, -1F, -1.5F, 4, 3, 3);
        this.LegFrontRight1.setRotationPoint(0F, 0F, 0F);
        //setRotation(LegFrontLeft1, 0F, 0F, 10F/radianF0.5235988F);
        this.LegFrontRight.addChild(this.LegFrontRight1);

        this.LegFrontRight2 = new ModelRenderer(this, 22, 7);
        this.LegFrontRight2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.LegFrontRight2.setRotationPoint(-3F, 0.5F, 0F);
        this.LegFrontRight1.addChild(this.LegFrontRight2);

        this.LegFrontRight3 = new ModelRenderer(this, 0, 58);
        this.LegFrontRight3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
        this.LegFrontRight3.setRotationPoint(0F, 4F, 0F);
        setRotation(this.LegFrontRight3, 0F, 10F / this.radianF, 0F);
        this.LegFrontRight2.addChild(this.LegFrontRight3);

        this.LegBackRight = new ModelRenderer(this);
        this.LegBackRight.setRotationPoint(-2F, 17F, 6F);

        this.LegBackRight1 = new ModelRenderer(this, 0, 6);
        this.LegBackRight1.addBox(-4F, -1F, -1.5F, 4, 3, 3);
        this.LegBackRight1.setRotationPoint(0F, 0F, 0F);
        //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
        this.LegBackRight.addChild(this.LegBackRight1);

        this.LegBackRight2 = new ModelRenderer(this, 22, 7);
        this.LegBackRight2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
        this.LegBackRight2.setRotationPoint(-3F, 0.5F, 0F);
        this.LegBackRight1.addChild(this.LegBackRight2);

        this.LegBackRight3 = new ModelRenderer(this, 0, 58);
        this.LegBackRight3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
        this.LegBackRight3.setRotationPoint(0F, 4F, 0F);
        setRotation(this.LegBackRight3, 0F, 10F / this.radianF, 0F);
        this.LegBackRight2.addChild(this.LegBackRight3);

        this.SaddleA = new ModelRenderer(this, 36, 28);
        this.SaddleA.addBox(-2.5F, 0.5F, -4F, 5, 1, 8);
        this.SaddleA.setRotationPoint(0F, 12F, 0F);
        this.SaddleA.setTextureSize(64, 64);
        this.SaddleA.mirror = true;
        setRotation(this.SaddleA, 0F, 0F, 0F);
        this.SaddleC = new ModelRenderer(this, 36, 37);
        this.SaddleC.addBox(-2.5F, 0F, 2F, 5, 1, 2);
        this.SaddleC.setRotationPoint(0F, 12F, 0F);
        this.SaddleC.setTextureSize(64, 64);
        this.SaddleC.mirror = true;
        setRotation(this.SaddleC, 0F, 0F, 0F);
        this.SaddleB = new ModelRenderer(this, 54, 37);
        this.SaddleB.addBox(-1.5F, 0F, -4F, 3, 1, 2);
        this.SaddleB.setRotationPoint(0F, 12F, 0F);
        this.SaddleB.setTextureSize(64, 64);
        this.SaddleB.mirror = true;
        setRotation(this.SaddleB, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityKomodo komodo = (MoCEntityKomodo) entity;
        //int type = komodo.getType();
        //byte harness = komodo.getHarness();
        //byte storage = komodo.getStorage();
        boolean mouth = (komodo.mouthCounter != 0);
        boolean sitting = (komodo.getIsSitting());
        boolean swimming = (komodo.isSwimming());
        boolean moveTail = (komodo.tailCounter != 0);
        boolean tongue = (komodo.tongueCounter != 0);
        setRotationAngles(f, f1, f2, f3, f4, f5, sitting, moveTail, tongue, mouth, swimming);

        this.Tail.render(f5);
        this.Head.render(f5);
        this.Chest.render(f5);
        this.LegFrontLeft.render(f5);
        this.LegBackLeft.render(f5);
        this.LegFrontRight.render(f5);
        this.LegBackRight.render(f5);
        this.Abdomen.render(f5);

        if (komodo.getIsRideable()) {
            this.SaddleA.render(f5);
            this.SaddleC.render(f5);
            this.SaddleB.render(f5);
        }

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    /**
     * Used to adjust the Y offset of the model cubes
     */
    private void AdjustY(float f) {
        float yOff = f;
        this.Tail.rotationPointY = yOff + 13F;
        this.Head.rotationPointY = yOff + 13F;
        this.Chest.rotationPointY = yOff + 13F;
        this.LegFrontLeft.rotationPointY = yOff + 17F;
        this.LegBackLeft.rotationPointY = yOff + 17F;
        this.LegFrontRight.rotationPointY = yOff + 17F;
        this.LegBackRight.rotationPointY = yOff + 17F;
        this.Abdomen.rotationPointY = yOff + 13F;
        this.SaddleA.rotationPointY = yOff + 12F;
        this.SaddleB.rotationPointY = yOff + 12F;
        this.SaddleC.rotationPointY = yOff + 12F;

    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean sitting, boolean movetail, boolean tongue,
            boolean mouth, boolean swimming) {
        float TailXRot = MathHelper.cos(f * 0.4F) * 0.2F * f1;
        float LLegXRot = MathHelper.cos(f * 1.2F) * 1.2F * f1;
        float RLegXRot = MathHelper.cos((f * 1.2F) + 3.141593F) * 1.2F * f1;

        if (f3 > 60F) {
            f3 = 60F;
        }
        if (f3 < -60F) {
            f3 = -60F;
        }

        float f10 = 0F;
        if (swimming) {
            f10 = 4F;
            this.Tail1.rotateAngleX = (0F / this.radianF) - TailXRot;
            this.LegFrontLeft1.rotateAngleZ = 0F / this.radianF;
            this.LegFrontLeft2.rotateAngleZ = -65F / this.radianF;
            this.LegFrontLeft1.rotateAngleY = -80F / this.radianF;

            this.LegBackLeft1.rotateAngleZ = 0F / this.radianF;
            this.LegBackLeft2.rotateAngleZ = -65F / this.radianF;
            this.LegBackLeft1.rotateAngleY = -80F / this.radianF;

            this.LegFrontRight1.rotateAngleZ = 0F / this.radianF;
            this.LegFrontRight2.rotateAngleZ = 65F / this.radianF;
            this.LegFrontRight1.rotateAngleY = 80F / this.radianF;

            this.LegBackRight1.rotateAngleZ = 0F / this.radianF;// + (LLegXRotD/radianF);;
            this.LegBackRight2.rotateAngleZ = 65F / this.radianF;
            this.LegBackRight1.rotateAngleY = 80F / this.radianF;
        } else if (sitting) {
            f10 = 4F;
            this.Tail1.rotateAngleX = (-5F / this.radianF) - TailXRot;
            this.LegFrontLeft1.rotateAngleZ = -30F / this.radianF;
            this.LegFrontLeft2.rotateAngleZ = 0F / this.radianF;
            this.LegFrontLeft1.rotateAngleY = 0F;//LLegXRot;

            this.LegBackLeft1.rotateAngleZ = 0F / this.radianF;
            this.LegBackLeft2.rotateAngleZ = -65F / this.radianF;
            this.LegBackLeft1.rotateAngleY = -40F / this.radianF;

            this.LegFrontRight1.rotateAngleZ = 30F / this.radianF;
            this.LegFrontRight2.rotateAngleZ = 0F / this.radianF;
            this.LegFrontRight1.rotateAngleY = 0F;//-RLegXRot;

            this.LegBackRight1.rotateAngleZ = 0F / this.radianF;// + (LLegXRotD/radianF);;
            this.LegBackRight2.rotateAngleZ = 65F / this.radianF;
            this.LegBackRight1.rotateAngleY = 40F / this.radianF;
        } else {
            this.Tail1.rotateAngleX = (-15F / this.radianF) - TailXRot;
            this.LegFrontLeft1.rotateAngleZ = 30F / this.radianF;
            this.LegFrontLeft2.rotateAngleZ = -30F / this.radianF;
            this.LegFrontLeft1.rotateAngleY = LLegXRot;
            this.LegFrontLeft2.rotateAngleX = -LLegXRot;

            this.LegBackLeft1.rotateAngleZ = 30F / this.radianF;
            this.LegBackLeft2.rotateAngleZ = -30F / this.radianF;
            this.LegBackLeft1.rotateAngleY = RLegXRot;
            this.LegBackLeft2.rotateAngleX = -RLegXRot;

            this.LegFrontRight1.rotateAngleZ = -30F / this.radianF;
            this.LegFrontRight2.rotateAngleZ = 30F / this.radianF;
            this.LegFrontRight1.rotateAngleY = -RLegXRot;
            this.LegFrontRight2.rotateAngleX = -RLegXRot;

            this.LegBackRight1.rotateAngleZ = -30F / this.radianF;// + (LLegXRotD/radianF);;
            this.LegBackRight2.rotateAngleZ = 30F / this.radianF;
            this.LegBackRight1.rotateAngleY = -LLegXRot;
            this.LegBackRight2.rotateAngleX = -LLegXRot;
        }
        AdjustY(f10);

        float tongueF = 0;
        if (!mouth && tongue) {
            tongueF = (MathHelper.cos(f2 * 3F) / 10F);
            this.Tongue.rotationPointZ = -4.7F;
        } else {
            this.Tongue.rotationPointZ = 0.3F;
        }

        float mouthF = 0;
        if (mouth) {
            mouthF = 35F / this.radianF;
            this.Tongue.rotationPointZ = -0.8F;
        }

        this.Neck.rotateAngleX = 11F / this.radianF + (f4 * 0.33F / this.radianF);
        this.Nose.rotateAngleX = 10.6F / this.radianF + (f4 * 0.66F / this.radianF);
        this.Mouth.rotateAngleX = mouthF + (-3F / this.radianF) + (f4 * 0.66F / this.radianF);
        this.Tongue.rotateAngleX = tongueF;

        this.Neck.rotateAngleY = (f3 * 0.33F / this.radianF);
        this.Nose.rotateAngleY = (f3 * 0.66F / this.radianF);
        this.Mouth.rotateAngleY = (f3 * 0.66F / this.radianF);
        //Tail2.rotateAngleY = LLegXRot;

        //y = A * sin(w * t - k *x)
        /*
         * w1 = speed of wave propagation +/- as needed t = time k = number of
         * waves A = amplitude of wave (how much will it depart from center x =
         * position? :)A 1.3k 0.5w -3.5
         */

        this.Tail2.rotateAngleX = (-17F / this.radianF) + TailXRot;
        this.Tail3.rotateAngleX = (13F / this.radianF) + TailXRot;
        this.Tail4.rotateAngleX = (11F / this.radianF) + TailXRot;

        float t = f / 2;

        if (movetail) {
            t = f2 / 4F;
        }
        float A = 0.35F;//0.8F;
        float w = 0.6F;
        float k = 0.6F;

        int i = 0;
        float tailLat = 0F;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail1.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail2.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail3.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * i++);
        this.Tail4.rotateAngleY = tailLat;

    }

}
