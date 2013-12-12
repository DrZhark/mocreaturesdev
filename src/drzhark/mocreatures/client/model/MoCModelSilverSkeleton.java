package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;


public class MoCModelSilverSkeleton extends ModelBase
{
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

    public MoCModelSilverSkeleton()
    {
        textureWidth = 64;
        textureHeight = 64;

        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-4F, -8F, -4F, 8, 8, 8);
        Head.setRotationPoint(0F, -2F, 0F);

        Body = new ModelRenderer(this, 32, 0);
        Body.addBox(-4F, 0F, -2F, 8, 12, 4);
        Body.setRotationPoint(0F, -2F, 0F);

        Back = new ModelRenderer(this, 44, 54);
        Back.addBox(-4F, -4F, 0.5F, 8, 8, 2);
        Back.setRotationPoint(0F, 2F, 2F);
        setRotation(Back, -0.1570796F, 0F, 0F);

        RightArm = new ModelRenderer(this, 48, 31);
        RightArm.addBox(-3F, -2.5F, -2.5F, 4, 11, 4);
        RightArm.setRotationPoint(-5F, 1F, 0F);

        RightHand = new ModelRenderer(this, 24, 16);
        RightHand.addBox(-2.5F, -2F, -2F, 3, 12, 3);
        RightHand.setRotationPoint(-5F, 1F, 0F);

        RightSwordA = new ModelRenderer(this, 52, 46);
        RightSwordA.addBox(-1.5F, 8.5F, -3F, 1, 1, 5);
        RightSwordA.setRotationPoint(-5F, 1F, 0F);

        RightSwordB = new ModelRenderer(this, 48, 50);
        RightSwordB.addBox(-1.5F, 7.5F, -4F, 1, 3, 1);
        RightSwordB.setRotationPoint(-5F, 1F, 0F);

        RightSwordC = new ModelRenderer(this, 28, 28);
        RightSwordC.addBox(-1F, 7.5F, -14F, 0, 3, 10);
        RightSwordC.setRotationPoint(-5F, 1F, 0F);

        LeftArm = new ModelRenderer(this, 48, 16);
        LeftArm.addBox(-1F, -2.5F, -2.5F, 4, 11, 4);
        LeftArm.setRotationPoint(5F, 1F, 0F);

        LeftHand = new ModelRenderer(this, 36, 16);
        LeftHand.addBox(-0.5F, -2F, -2F, 3, 12, 3);
        LeftHand.setRotationPoint(5F, 1F, 0F);

        LeftSwordA = new ModelRenderer(this, 52, 46);
        LeftSwordA.addBox(0.5F, 8.5F, -3F, 1, 1, 5);
        LeftSwordA.setRotationPoint(5F, 1F, 0F);

        LeftSwordB = new ModelRenderer(this, 48, 46);
        LeftSwordB.addBox(0.5F, 7.5F, -4F, 1, 3, 1);
        LeftSwordB.setRotationPoint(5F, 1F, 0F);

        LeftSwordC = new ModelRenderer(this, 28, 31);
        LeftSwordC.addBox(1F, 7.5F, -14F, 0, 3, 10);
        LeftSwordC.setRotationPoint(5F, 1F, 0F);

        RightThigh = new ModelRenderer(this, 0, 16);
        RightThigh.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        RightThigh.setRotationPoint(-2F, 10.5F, 0F);

        RightKnee = new ModelRenderer(this, 0, 46);
        RightKnee.addBox(-2F, 1F, -2F, 4, 4, 4);
        RightKnee.setRotationPoint(-2F, 10.5F, 0F);

        /*RightLeg = new ModelRenderer(this, 0, 25);
        RightLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        RightLeg.setRotationPoint(-2F, 18F, 0F);

        RightFoot = new ModelRenderer(this, 0, 54);
        RightFoot.addBox(-2F, 0F, -2F, 4, 6, 4);
        RightFoot.setRotationPoint(-2F, 19F, 0F);
        
        LeftLeg = new ModelRenderer(this, 12, 25);
        LeftLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        LeftLeg.setRotationPoint(2F, 18F, 0F);

        LeftFoot = new ModelRenderer(this, 16, 54);
        LeftFoot.addBox(-2F, 0F, -2F, 4, 6, 4);
        LeftFoot.setRotationPoint(2F, 19F, 0F);
        */

        RightLeg = new ModelRenderer(this, 0, 25);
        RightLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        RightLeg.setRotationPoint(0F, 6F, 0F);
        RightThigh.addChild(RightLeg);
        
        RightFoot = new ModelRenderer(this, 0, 54);
        RightFoot.addBox(-2F, 0F, -2F, 4, 6, 4);
        RightFoot.setRotationPoint(0F, 2F, 0F);
        RightLeg.addChild(RightFoot);
        
        LeftThigh = new ModelRenderer(this, 12, 16);
        LeftThigh.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        LeftThigh.setRotationPoint(2F, 10.5F, 0F);

        LeftKnee = new ModelRenderer(this, 16, 46);
        LeftKnee.addBox(-2F, 1F, -2F, 4, 4, 4);
        LeftKnee.setRotationPoint(2F, 10.5F, 0F);

        LeftLeg = new ModelRenderer(this, 12, 25);
        LeftLeg.addBox(-1.5F, 0F, -1.5F, 3, 6, 3);
        LeftLeg.setRotationPoint(0F, 6F, 0F);
        LeftThigh.addChild(LeftLeg);
        
        LeftFoot = new ModelRenderer(this, 16, 54);
        LeftFoot.addBox(-2F, 0F, -2F, 4, 6, 4);
        LeftFoot.setRotationPoint(0F, 2F, 0F);
        LeftLeg.addChild(LeftFoot);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntitySilverSkeleton samurai = (MoCEntitySilverSkeleton) entity;
        boolean sprinting = samurai.isSprinting();//getIsAngry();
        int leftAttack = samurai.attackCounterLeft;
        int rightAttack = samurai.attackCounterRight;
        setRotationAngles(f, f1, f2, f3, f4, f5, leftAttack, rightAttack);
        
        if (sprinting && f1 > 0.3F)
        {
            GL11.glPushMatrix();
            GL11.glRotatef((float) (f1 * -25D), -1F, 0.0F, 0.0F);
            renderParts(f5);
            GL11.glPopMatrix();
        }else
        {
            renderParts(f5);
        }
        
        
    }

    private void renderParts(float f5)
    {
        Head.render(f5);
        Body.render(f5);
        Back.render(f5);
        RightArm.render(f5);
        RightHand.render(f5);
        RightSwordA.render(f5);
        RightSwordB.render(f5);
        RightSwordC.render(f5);
        LeftArm.render(f5);
        LeftHand.render(f5);
        LeftSwordA.render(f5);
        LeftSwordB.render(f5);
        LeftSwordC.render(f5);
        RightThigh.render(f5);
        RightKnee.render(f5);
        LeftThigh.render(f5);
        LeftKnee.render(f5);
    }
    
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, int leftAttack, int rightAttack)
    {
        float hRotY = f3 / 57.29578F;
        float hRotX = f4 / 57.29578F;
        
        Head.rotateAngleX = hRotX;
        Head.rotateAngleY = hRotY;
        
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        float ClothRot = MathHelper.cos(f * 0.9F) * 0.6F * f1;
        
        float RLegXRotB = RLegXRot;
        float LLegXRotB = LLegXRot;
        
        RightThigh.rotateAngleX =  RLegXRot;
        LeftThigh.rotateAngleX =  LLegXRot;
        RightKnee.rotateAngleX = RightThigh.rotateAngleX;
        LeftKnee.rotateAngleX = LeftThigh.rotateAngleX;
        
        float RLegXRot2 = MathHelper.cos(((f + 0.1F) * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot2 = MathHelper.cos((f + 0.1F) * 0.6662F) * 0.8F * f1;
        if (f1 > 0.15F)
        {
            if (RLegXRot > RLegXRot2) // - - >
            {
                RLegXRotB = RLegXRot + (25 / 57.29578F);
               
            }
            
            if (LLegXRot > LLegXRot2) // - - >
            {
                LLegXRotB = LLegXRot + (25 / 57.29578F);
            }
           
        }
        
        
        RightLeg.rotateAngleX = (LLegXRotB);
        LeftLeg.rotateAngleX = (RLegXRotB);


        if (leftAttack == 0)
        {
            LeftArm.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
            LeftArm.rotateAngleX = RLegXRot;
        }else
        {
            float armMov = -(MathHelper.cos((leftAttack) * 0.18F) * 3F);
            LeftArm.rotateAngleX =  + armMov;
        }
        
                
       
        if (rightAttack == 0) 
        {
            RightArm.rotateAngleZ = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
            RightArm.rotateAngleX = LLegXRot;
            
        }else 
        {
           float armMov = -(MathHelper.cos((rightAttack) * 0.18F) * 3F);
           RightArm.rotateAngleX =  + armMov;
        }
        
        LeftHand.rotateAngleX = LeftSwordA.rotateAngleX = LeftSwordB.rotateAngleX = LeftSwordC.rotateAngleX = LeftArm.rotateAngleX;
        LeftHand.rotateAngleZ = LeftSwordA.rotateAngleZ = LeftSwordB.rotateAngleZ = LeftSwordC.rotateAngleZ = LeftArm.rotateAngleZ;
        
        RightHand.rotateAngleX = RightSwordA.rotateAngleX = RightSwordB.rotateAngleX = RightSwordC.rotateAngleX = RightArm.rotateAngleX;
        RightHand.rotateAngleZ = RightSwordA.rotateAngleZ = RightSwordB.rotateAngleZ = RightSwordC.rotateAngleZ = RightArm.rotateAngleZ;
    }

}
