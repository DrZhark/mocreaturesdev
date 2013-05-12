package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.aquatic.MoCEntityRay;

@SideOnly(Side.CLIENT)
public class MoCModelRay extends ModelBase {

    public MoCModelRay()
    {
        textureWidth = 64;
        textureHeight = 32;

        Body = new ModelRenderer(this, 26, 0);
        Body.addBox(-4F, -1F, 0F, 8, 2, 11);
        Body.setRotationPoint(0F, 22F, -5F);
        //Body.setTextureSize(64, 32);
        //  Body.mirror = true;
        //setRotation(Body, 0F, 0F, 0F);    
        Right = new ModelRenderer(this, 10, 26);
        Right.addBox(-0.5F, -1F, -4F, 1, 2, 4);
        Right.setRotationPoint(-3F, 22F, -4.8F);
        //Right.setTextureSize(64, 32);
        //Right.mirror = true;
        //setRotation(Right, 0F, 0F, 0F);    
        Left = new ModelRenderer(this, 0, 26);
        Left.addBox(-0.5F, -1F, -4F, 1, 2, 4);
        Left.setRotationPoint(3F, 22F, -4.8F);
        //Left.setTextureSize(64, 32);
        //Left.mirror = true;
        //setRotation(Left, 0F, 0F, 0F);    

        BodyU = new ModelRenderer(this, 0, 11);
        BodyU.addBox(-3F, -1F, 0F, 6, 1, 8);
        BodyU.setRotationPoint(0F, 21F, -4F);
        //BodyU.setTextureSize(64, 32);
        //BodyU.mirror = true;
        //setRotation(BodyU, 0F, 0F, 0F);    

        Tail = new ModelRenderer(this, 30, 15);
        Tail.addBox(-0.5F, -0.5F, 1F, 1, 1, 16);
        Tail.setRotationPoint(0F, 22F, 8F);
        //Tail.setTextureSize(64, 32);
        //Tail.mirror = true;
        //setRotation(Tail, 0F, 0F, 0F);    

        BodyTail = new ModelRenderer(this, 0, 20);
        BodyTail.addBox(-1.8F, -0.5F, -3.2F, 5, 1, 5);
        BodyTail.setRotationPoint(0F, 22F, 7F);
        //BodyTail.setTextureSize(64, 32);
        //BodyTail.mirror = true;
        setRotation(BodyTail, 0F, 1F, 0F);

        RWingA = new ModelRenderer(this, 0, 0);
        RWingA.addBox(-3F, -0.5F, -5F, 3, 1, 10);
        RWingA.setRotationPoint(-4F, 22F, 1F);
        //RWingA.setTextureSize(64, 32);
        //RWingA.mirror = true;
        //setRotation(RWingA, 0F, 0F, 0F);    

        RWingB = new ModelRenderer(this, 2, 2);
        RWingB.addBox(-6F, -0.5F, -4F, 3, 1, 8);
        RWingB.setRotationPoint(-4F, 22F, 1F);
        //RWingB.setTextureSize(64, 32);
        //RWingB.mirror = true;
        //setRotation(RWingB, 0F, 0F, 0F);    
        RWingC = new ModelRenderer(this, 5, 4);
        RWingC.addBox(-8F, -0.5F, -3F, 2, 1, 6);
        RWingC.setRotationPoint(-4F, 22F, 1F);
        //RWingC.setTextureSize(64, 32);
        //RWingC.mirror = true;
        //setRotation(RWingC, 0F, 0F, 0F);    
        RWingD = new ModelRenderer(this, 6, 5);
        RWingD.addBox(-10F, -0.5F, -2.5F, 2, 1, 5);
        RWingD.setRotationPoint(-4F, 22F, 1F);
        //RWingD.setTextureSize(64, 32);
        //RWingD.mirror = true;
        //setRotation(RWingD, 0F, 0F, 0F);    
        RWingE = new ModelRenderer(this, 7, 6);
        RWingE.addBox(-12F, -0.5F, -2F, 2, 1, 4);
        RWingE.setRotationPoint(-4F, 22F, 1F);
        //RWingE.setTextureSize(64, 32);
        //RWingE.mirror = true;
        //setRotation(RWingE, 0F, 0F, 0F);    
        RWingF = new ModelRenderer(this, 8, 7);
        RWingF.addBox(-14F, -0.5F, -1.5F, 2, 1, 3);
        RWingF.setRotationPoint(-4F, 22F, 1F);
        //RWingF.setTextureSize(64, 32);
        //RWingF.mirror = true;
        //setRotation(RWingF, 0F, 0F, 0F);    

        RWingG = new ModelRenderer(this, 9, 8);
        RWingG.addBox(-16F, -0.5F, -1F, 2, 1, 2);
        RWingG.setRotationPoint(-4F, 22F, 1F);
        //RWingG.setTextureSize(64, 32);
        //RWingG.mirror = true;
        //setRotation(RWingG, 0F, 0F, 0F);    
        LWingA = new ModelRenderer(this, 0, 0);
        LWingA.addBox(0F, -0.5F, -5F, 3, 1, 10);
        LWingA.setRotationPoint(4F, 22F, 1F);
        LWingA.mirror = true;

        //LWingA.setTextureSize(64, 32);
        //setRotation(LWingA, 0F, 0F, 0F);    

        LWingB = new ModelRenderer(this, 2, 2);
        LWingB.addBox(3F, -0.5F, -4F, 3, 1, 8);
        LWingB.setRotationPoint(4F, 22F, 1F);
        //LWingB.setTextureSize(64, 32);
        LWingB.mirror = true;
        //setRotation(LWingB, 0F, 0F, 0F);    

        LWingC = new ModelRenderer(this, 5, 4);
        LWingC.addBox(6F, -0.5F, -3F, 2, 1, 6);
        LWingC.setRotationPoint(4F, 22F, 1F);
        //LWingC.setTextureSize(64, 32);
        LWingC.mirror = true;
        //setRotation(LWingC, 0F, 0F, 0F);    

        LWingD = new ModelRenderer(this, 6, 5);
        LWingD.addBox(8F, -0.5F, -2.5F, 2, 1, 5);
        LWingD.setRotationPoint(4F, 22F, 1F);
        //LWingD.setTextureSize(64, 32);
        LWingD.mirror = true;
        //setRotation(LWingD, 0F, 0F, 0F);    

        LWingE = new ModelRenderer(this, 7, 6);
        LWingE.addBox(10F, -0.5F, -2F, 2, 1, 4);
        LWingE.setRotationPoint(4F, 22F, 1F);
        //LWingE.setTextureSize(64, 32);
        LWingE.mirror = true;
        //setRotation(LWingE, 0F, 0F, 0F);    

        LWingF = new ModelRenderer(this, 8, 7);
        LWingF.addBox(12F, -0.5F, -1.5F, 2, 1, 3);
        LWingF.setRotationPoint(4F, 22F, 1F);
        //LWingF.setTextureSize(64, 32);
        LWingF.mirror = true;
        //setRotation(LWingF, 0F, 0F, 0F);    

        LWingG = new ModelRenderer(this, 9, 8);
        LWingG.addBox(14F, -0.5F, -1F, 2, 1, 2);
        LWingG.setRotationPoint(4F, 22F, 1F);
        //LWingG.setTextureSize(64, 32);
        LWingG.mirror = true;
        //setRotation(LWingG, 0F, 0F, 0F);    

        LEye = new ModelRenderer(this, 0, 0);
        LEye.addBox(-3F, -2F, 1F, 1, 1, 2);
        LEye.setRotationPoint(0F, 21F, -4F);

        REye = new ModelRenderer(this, 0, 3);
        REye.addBox(2F, -2F, 1F, 1, 1, 2);
        REye.setRotationPoint(0F, 21F, -4F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityRay ray = (MoCEntityRay)entity;
        this.attacking = ray.isPoisoning();
        this.typeInt = ray.getType();
        
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Tail.render(f5);
        Body.render(f5);
        BodyU.render(f5);
        BodyTail.render(f5);

        RWingA.render(f5);
        RWingB.render(f5);

        LWingA.render(f5);
        LWingB.render(f5);

        if (typeInt == 1)
        {
            Right.render(f5);
            Left.render(f5);
            RWingC.render(f5);
            LWingC.render(f5);

            RWingD.render(f5);
            RWingE.render(f5);
            RWingF.render(f5);
            RWingG.render(f5);
            LWingD.render(f5);
            LWingE.render(f5);
            LWingF.render(f5);
            LWingG.render(f5);
        }
        else
        {
            REye.render(f5);
            LEye.render(f5);
        }
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
        float rotF = MathHelper.cos(f * 0.6662F) * 1.5F * f1;
        float f6 = 20F;
        Tail.rotateAngleY = rotF;//MathHelper.cos(f * 0.6662F) * 0.7F * f1;
        RWingA.rotateAngleZ = rotF;
        LWingA.rotateAngleZ = -rotF;
        rotF += (rotF / f6);
        RWingB.rotateAngleZ = rotF;
        LWingB.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        RWingC.rotateAngleZ = rotF;
        LWingC.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        RWingD.rotateAngleZ = rotF;
        LWingD.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        RWingE.rotateAngleZ = rotF;
        LWingE.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        RWingF.rotateAngleZ = rotF;
        LWingF.rotateAngleZ = -rotF;
        rotF += (rotF / f6);

        RWingG.rotateAngleZ = rotF;
        LWingG.rotateAngleZ = -rotF;

        if (attacking)
        {
            Tail.rotateAngleX = 0.5F;
        }
        else
        {
            Tail.rotateAngleX = 0F;
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
