package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.aquatic.MoCEntityRay;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelRay extends ModelBase {

    public MoCModelRay() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Body = new ModelRenderer(this, 26, 0);
        this.Body.addBox(-4F, -1F, 0F, 8, 2, 11);
        this.Body.setRotationPoint(0F, 22F, -5F);
        //Body.setTextureSize(64, 32);
        //  Body.mirror = true;
        //setRotation(Body, 0F, 0F, 0F);
        this.Right = new ModelRenderer(this, 10, 26);
        this.Right.addBox(-0.5F, -1F, -4F, 1, 2, 4);
        this.Right.setRotationPoint(-3F, 22F, -4.8F);
        //Right.setTextureSize(64, 32);
        //Right.mirror = true;
        //setRotation(Right, 0F, 0F, 0F);
        this.Left = new ModelRenderer(this, 0, 26);
        this.Left.addBox(-0.5F, -1F, -4F, 1, 2, 4);
        this.Left.setRotationPoint(3F, 22F, -4.8F);
        //Left.setTextureSize(64, 32);
        //Left.mirror = true;
        //setRotation(Left, 0F, 0F, 0F);

        this.BodyU = new ModelRenderer(this, 0, 11);
        this.BodyU.addBox(-3F, -1F, 0F, 6, 1, 8);
        this.BodyU.setRotationPoint(0F, 21F, -4F);
        //BodyU.setTextureSize(64, 32);
        //BodyU.mirror = true;
        //setRotation(BodyU, 0F, 0F, 0F);

        this.Tail = new ModelRenderer(this, 30, 15);
        this.Tail.addBox(-0.5F, -0.5F, 1F, 1, 1, 16);
        this.Tail.setRotationPoint(0F, 22F, 8F);
        //Tail.setTextureSize(64, 32);
        //Tail.mirror = true;
        //setRotation(Tail, 0F, 0F, 0F);

        this.BodyTail = new ModelRenderer(this, 0, 20);
        this.BodyTail.addBox(-1.8F, -0.5F, -3.2F, 5, 1, 5);
        this.BodyTail.setRotationPoint(0F, 22F, 7F);
        //BodyTail.setTextureSize(64, 32);
        //BodyTail.mirror = true;
        setRotation(this.BodyTail, 0F, 1F, 0F);

        this.RWingA = new ModelRenderer(this, 0, 0);
        this.RWingA.addBox(-3F, -0.5F, -5F, 3, 1, 10);
        this.RWingA.setRotationPoint(-4F, 22F, 1F);
        //RWingA.setTextureSize(64, 32);
        //RWingA.mirror = true;
        //setRotation(RWingA, 0F, 0F, 0F);

        this.RWingB = new ModelRenderer(this, 2, 2);
        this.RWingB.addBox(-6F, -0.5F, -4F, 3, 1, 8);
        this.RWingB.setRotationPoint(-4F, 22F, 1F);
        //RWingB.setTextureSize(64, 32);
        //RWingB.mirror = true;
        //setRotation(RWingB, 0F, 0F, 0F);
        this.RWingC = new ModelRenderer(this, 5, 4);
        this.RWingC.addBox(-8F, -0.5F, -3F, 2, 1, 6);
        this.RWingC.setRotationPoint(-4F, 22F, 1F);
        //RWingC.setTextureSize(64, 32);
        //RWingC.mirror = true;
        //setRotation(RWingC, 0F, 0F, 0F);
        this.RWingD = new ModelRenderer(this, 6, 5);
        this.RWingD.addBox(-10F, -0.5F, -2.5F, 2, 1, 5);
        this.RWingD.setRotationPoint(-4F, 22F, 1F);
        //RWingD.setTextureSize(64, 32);
        //RWingD.mirror = true;
        //setRotation(RWingD, 0F, 0F, 0F);
        this.RWingE = new ModelRenderer(this, 7, 6);
        this.RWingE.addBox(-12F, -0.5F, -2F, 2, 1, 4);
        this.RWingE.setRotationPoint(-4F, 22F, 1F);
        //RWingE.setTextureSize(64, 32);
        //RWingE.mirror = true;
        //setRotation(RWingE, 0F, 0F, 0F);
        this.RWingF = new ModelRenderer(this, 8, 7);
        this.RWingF.addBox(-14F, -0.5F, -1.5F, 2, 1, 3);
        this.RWingF.setRotationPoint(-4F, 22F, 1F);
        //RWingF.setTextureSize(64, 32);
        //RWingF.mirror = true;
        //setRotation(RWingF, 0F, 0F, 0F);

        this.RWingG = new ModelRenderer(this, 9, 8);
        this.RWingG.addBox(-16F, -0.5F, -1F, 2, 1, 2);
        this.RWingG.setRotationPoint(-4F, 22F, 1F);
        //RWingG.setTextureSize(64, 32);
        //RWingG.mirror = true;
        //setRotation(RWingG, 0F, 0F, 0F);
        this.LWingA = new ModelRenderer(this, 0, 0);
        this.LWingA.addBox(0F, -0.5F, -5F, 3, 1, 10);
        this.LWingA.setRotationPoint(4F, 22F, 1F);
        this.LWingA.mirror = true;

        //LWingA.setTextureSize(64, 32);
        //setRotation(LWingA, 0F, 0F, 0F);

        this.LWingB = new ModelRenderer(this, 2, 2);
        this.LWingB.addBox(3F, -0.5F, -4F, 3, 1, 8);
        this.LWingB.setRotationPoint(4F, 22F, 1F);
        //LWingB.setTextureSize(64, 32);
        this.LWingB.mirror = true;
        //setRotation(LWingB, 0F, 0F, 0F);

        this.LWingC = new ModelRenderer(this, 5, 4);
        this.LWingC.addBox(6F, -0.5F, -3F, 2, 1, 6);
        this.LWingC.setRotationPoint(4F, 22F, 1F);
        //LWingC.setTextureSize(64, 32);
        this.LWingC.mirror = true;
        //setRotation(LWingC, 0F, 0F, 0F);

        this.LWingD = new ModelRenderer(this, 6, 5);
        this.LWingD.addBox(8F, -0.5F, -2.5F, 2, 1, 5);
        this.LWingD.setRotationPoint(4F, 22F, 1F);
        //LWingD.setTextureSize(64, 32);
        this.LWingD.mirror = true;
        //setRotation(LWingD, 0F, 0F, 0F);

        this.LWingE = new ModelRenderer(this, 7, 6);
        this.LWingE.addBox(10F, -0.5F, -2F, 2, 1, 4);
        this.LWingE.setRotationPoint(4F, 22F, 1F);
        //LWingE.setTextureSize(64, 32);
        this.LWingE.mirror = true;
        //setRotation(LWingE, 0F, 0F, 0F);

        this.LWingF = new ModelRenderer(this, 8, 7);
        this.LWingF.addBox(12F, -0.5F, -1.5F, 2, 1, 3);
        this.LWingF.setRotationPoint(4F, 22F, 1F);
        //LWingF.setTextureSize(64, 32);
        this.LWingF.mirror = true;
        //setRotation(LWingF, 0F, 0F, 0F);

        this.LWingG = new ModelRenderer(this, 9, 8);
        this.LWingG.addBox(14F, -0.5F, -1F, 2, 1, 2);
        this.LWingG.setRotationPoint(4F, 22F, 1F);
        //LWingG.setTextureSize(64, 32);
        this.LWingG.mirror = true;
        //setRotation(LWingG, 0F, 0F, 0F);

        this.LEye = new ModelRenderer(this, 0, 0);
        this.LEye.addBox(-3F, -2F, 1F, 1, 1, 2);
        this.LEye.setRotationPoint(0F, 21F, -4F);

        this.REye = new ModelRenderer(this, 0, 3);
        this.REye.addBox(2F, -2F, 1F, 1, 1, 2);
        this.REye.setRotationPoint(0F, 21F, -4F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityRay ray = (MoCEntityRay) entity;
        this.attacking = ray.isPoisoning();
        this.typeInt = ray.getType();

        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Tail.render(f5);
        this.Body.render(f5);
        this.BodyU.render(f5);
        this.BodyTail.render(f5);

        this.RWingA.render(f5);
        this.RWingB.render(f5);

        this.LWingA.render(f5);
        this.LWingB.render(f5);

        if (this.typeInt == 1) {
            this.Right.render(f5);
            this.Left.render(f5);
            this.RWingC.render(f5);
            this.LWingC.render(f5);

            this.RWingD.render(f5);
            this.RWingE.render(f5);
            this.RWingF.render(f5);
            this.RWingG.render(f5);
            this.LWingD.render(f5);
            this.LWingE.render(f5);
            this.LWingF.render(f5);
            this.LWingG.render(f5);
        } else {
            this.REye.render(f5);
            this.LEye.render(f5);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        float rotF = MathHelper.cos(f * 0.6662F) * 1.5F * f1;
        float f6 = 20F;
        this.Tail.rotateAngleY = rotF;//MathHelper.cos(f * 0.6662F) * 0.7F * f1;
        this.RWingA.rotateAngleZ = rotF;
        this.LWingA.rotateAngleZ = -rotF;
        rotF += (rotF / f6);
        this.RWingB.rotateAngleZ = rotF;
        this.LWingB.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingC.rotateAngleZ = rotF;
        this.LWingC.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingD.rotateAngleZ = rotF;
        this.LWingD.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingE.rotateAngleZ = rotF;
        this.LWingE.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingF.rotateAngleZ = rotF;
        this.LWingF.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        this.RWingG.rotateAngleZ = rotF;
        this.LWingG.rotateAngleZ = -rotF;

        if (this.attacking) {
            this.Tail.rotateAngleX = 0.5F;
        } else {
            this.Tail.rotateAngleX = 0F;
        }

    }

    public int typeInt;
    public boolean attacking;
    ModelRenderer Tail;
    ModelRenderer Body;
    ModelRenderer Right;
    ModelRenderer Left;
    ModelRenderer BodyU;
    ModelRenderer RWingA;
    ModelRenderer RWingB;
    ModelRenderer RWingC;
    ModelRenderer RWingD;
    ModelRenderer RWingE;
    ModelRenderer RWingF;
    ModelRenderer BodyTail;
    ModelRenderer RWingG;
    ModelRenderer LWingA;
    ModelRenderer LWingB;
    ModelRenderer LWingC;
    ModelRenderer LWingD;
    ModelRenderer LWingE;
    ModelRenderer LWingF;
    ModelRenderer LWingG;
    ModelRenderer LEye;
    ModelRenderer REye;
}
