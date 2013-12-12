package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import drzhark.mocreatures.entity.ambient.MoCEntityRoach;

public class MoCModelRoach extends ModelBase
{
    ModelRenderer Head;
    ModelRenderer LAnthenna;
    ModelRenderer LAnthennaB;
    ModelRenderer RAnthenna;
    ModelRenderer RAnthennaB;
    ModelRenderer Thorax;
    ModelRenderer FrontLegs;
    ModelRenderer MidLegs;
    ModelRenderer RearLegs;
    ModelRenderer Abdomen;
    ModelRenderer TailL;
    ModelRenderer TailR;
    ModelRenderer LShellClosed;
    ModelRenderer RShellClosed;
    ModelRenderer LShellOpen;
    ModelRenderer RShellOpen;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;
    private float radianF = 57.29578F;

    public MoCModelRoach()
    {
        textureWidth = 32;
        textureHeight = 32;

        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-0.5F, 0F, -1F, 1, 1, 2);
        Head.setRotationPoint(0F, 23F, -2F);
        setRotation(Head, -2.171231F, 0F, 0F);

        LAnthenna = new ModelRenderer(this, 3, 21);
        //LAnthenna.addBox(0F, 0F, 0F, 4, 0, 1);
        //LAnthenna.setRotationPoint(0F, 22F, -2.5F);
        LAnthenna.addBox(0F, 0F, 0F, 4, 0, 1);
        LAnthenna.setRotationPoint(0.5F, 0F, 0F);
        
        //LAnthenna.addBox(0.5F, 0.7F, -1.5F, 4, 0, 1);
        //LAnthenna.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotation(LAnthenna, -90F/radianF, 0.4363323F, 0F);//-45F/radianF);
        Head.addChild(LAnthenna);

        LAnthennaB = new ModelRenderer(this, 4, 21);
        //LAnthennaB.addBox(3.9F, 7F, -1.7F, 3, 0, 1);
        //LAnthennaB.setRotationPoint(0F, 15F, -2.5F);
        
        LAnthennaB.addBox(0F, 0F, 1F, 3, 0, 1);
        LAnthennaB.setRotationPoint(2.5F, 0F, -0.5F);
                
        //LAnthennaB.addBox(0F, 0F, 1F, 3, 0, 1);
        //LAnthennaB.setRotationPoint(2.5F, 0F, 0F);
        setRotation(LAnthennaB, 0F, 45F/radianF, 0F);
        LAnthenna.addChild(LAnthennaB);

        RAnthenna = new ModelRenderer(this, 3, 19);
        RAnthenna.addBox(-4.5F, 0F, 0F, 4, 0, 1);
        //RAnthenna.setRotationPoint(-3.5F, 0.7F, -1.5F);
        //RAnthenna.setRotationPoint(0F, 0.7F, -1.5F);
        RAnthenna.setRotationPoint(0F, 0F, 0F);
        setRotation(RAnthenna, -90F/radianF, -0.4363323F, 0F);
        Head.addChild(RAnthenna);
        
        RAnthennaB = new ModelRenderer(this, 4, 19);
        RAnthennaB.addBox(-4.0F, 0F, 1F, 3, 0, 1);
        RAnthennaB.setRotationPoint(-2.5F, 0F, 0.5F);
        setRotation(RAnthennaB, 0F, -45F/radianF, 0F);
        RAnthenna.addChild(RAnthennaB);
        
        Thorax = new ModelRenderer(this, 0, 3);
        Thorax.addBox(-1F, 0F, -1F, 2, 1, 2);
        Thorax.setRotationPoint(0F, 22F, -1F);

        FrontLegs = new ModelRenderer(this, 0, 11);
        FrontLegs.addBox(-2F, 0F, 0F, 4, 2, 0);
        FrontLegs.setRotationPoint(0F, 23F, -1.8F);
        setRotation(FrontLegs, -1.115358F, 0F, 0F);

        MidLegs = new ModelRenderer(this, 0, 13);
        MidLegs.addBox(-2.5F, 0F, 0F, 5, 2, 0);
        MidLegs.setRotationPoint(0F, 23F, -1.2F);
        setRotation(MidLegs, 1.264073F, 0F, 0F);

        RearLegs = new ModelRenderer(this, 0, 15);
        RearLegs.addBox(-2F, 0F, 0F, 4, 4, 0);
        RearLegs.setRotationPoint(0F, 23F, -0.4F);
        setRotation(RearLegs, 1.368173F, 0F, 0F);

        Abdomen = new ModelRenderer(this, 0, 6);
        Abdomen.addBox(-1F, 0F, -1F, 2, 4, 1);
        Abdomen.setRotationPoint(0F, 22F, 0F);
        setRotation(Abdomen, 1.427659F, 0F, 0F);

        TailL = new ModelRenderer(this, 2, 29);
        TailL.addBox(-0.5F, 0F, 0F, 1, 2, 0);
        TailL.setRotationPoint(0F, 23F, 3.6F);
        setRotation(TailL, 1.554066F, 0.6457718F, 0F);

        TailR = new ModelRenderer(this, 0, 29);
        TailR.addBox(-0.5F, 0F, 0F, 1, 2, 0);
        TailR.setRotationPoint(0F, 23F, 3.6F);
        setRotation(TailR, 1.554066F, -0.6457718F, 0F);

        LShellClosed = new ModelRenderer(this, 4, 23);
        LShellClosed.addBox(0F, 0F, 0F, 2, 0, 6);
        LShellClosed.setRotationPoint(0F, 21.5F, -1.5F);
        setRotation(LShellClosed, -0.1487144F, -0.0872665F, 0.1919862F);

        RShellClosed = new ModelRenderer(this, 0, 23);
        RShellClosed.addBox(-2F, 0F, 0F, 2, 0, 6);
        RShellClosed.setRotationPoint(0F, 21.5F, -1.5F);
        setRotation(RShellClosed, -0.1487144F, 0.0872665F, -0.1919862F);

        LShellOpen = new ModelRenderer(this, 4, 23);
        LShellOpen.addBox(0F, 0F, 0F, 2, 0, 6);
        LShellOpen.setRotationPoint(0F, 21.5F, -1.5F);
        setRotation(LShellOpen, 1.117011F, -0.0872665F, 1.047198F);

        RShellOpen = new ModelRenderer(this, 0, 23);
        RShellOpen.addBox(-2F, 0F, 0F, 2, 0, 6);
        RShellOpen.setRotationPoint(0F, 21.5F, -1.5F);
        setRotation(RShellOpen, 1.117011F, 0.0872665F, -1.047198F);


        LeftWing = new ModelRenderer(this, 11, 21);
        LeftWing.addBox(0F, 1F, -1F, 6, 0, 2);
        LeftWing.setRotationPoint(0F, 21.5F, -1.5F);
        LeftWing.setTextureSize(32, 32);
        LeftWing.mirror = true;
        setRotation(LeftWing, 0F, -1.047198F, -0.4363323F);
        RightWing = new ModelRenderer(this, 11, 19);
        RightWing.addBox(-6F, 1F, -1F, 6, 0, 2);
        RightWing.setRotationPoint(0F, 21.5F, -1.5F);
        RightWing.setTextureSize(32, 32);
        RightWing.mirror = true;
        setRotation(RightWing, 0F, 1.047198F, 0.4363323F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        MoCEntityRoach entityroach = (MoCEntityRoach) entity;
        boolean isFlying = (entityroach.getIsFlying() || entityroach.isOnAir());

        setRotationAngles(f, f1, f2, f3, f4, f5, isFlying);
        Head.render(f5);
        //LAnthenna.render(f5);
        //LAnthennaB.render(f5);
        //RAnthenna.render(f5);
        //RAnthennaB.render(f5);
        Thorax.render(f5);
        FrontLegs.render(f5);
        MidLegs.render(f5);
        RearLegs.render(f5);
        Abdomen.render(f5);
        TailL.render(f5);
        TailR.render(f5);



        if (!isFlying)
        {
            LShellClosed.render(f5);
            RShellClosed.render(f5);
        }else
        {    
            LShellOpen.render(f5);
            RShellOpen.render(f5);
            GL11.glPushMatrix();
            GL11.glEnable(3042 /*GL_BLEND*/);
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            LeftWing.render(f5);
            RightWing.render(f5);
            GL11.glDisable(3042/*GL_BLEND*/);
            GL11.glPopMatrix();
        }
    }


    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean isFlying)
    {
        Head.rotateAngleX = -2.171231F + (f4 / 57.29578F);
        //Head.rotateAngleY = f3 / 57.29578F;
        
        //f1 = movement speed!
        //f2 = timer!

        float antMov = 5F/radianF + (f1 * 1.5F);
        
        LAnthenna.rotateAngleZ = -antMov;
        RAnthenna.rotateAngleZ = antMov;
        
        float legMov = 0F;
        float legMovB = 0F;

        float frontLegAdj = 0F;

        if (isFlying)
        {
            float WingRot = MathHelper.cos((f2 * 2.0F)) * 0.7F;
            RightWing.rotateAngleY = 1.047198F + WingRot;
            LeftWing.rotateAngleY = -1.047198F -WingRot;
            legMov = (f1 * 1.5F);
            legMovB = legMov;
            frontLegAdj = 1.4F;

        }
        else
        {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 0.6F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 0.8F * f1;
        }

        //AntennaB.rotateAngleX = 2.88506F - legMov;

        FrontLegs.rotateAngleX = -1.115358F + frontLegAdj + legMov;
        MidLegs.rotateAngleX = 1.264073F + legMovB;
        RearLegs.rotateAngleX = 1.368173F - frontLegAdj + legMov;

    }



}



