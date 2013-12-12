
package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import drzhark.mocreatures.entity.monster.MoCEntityMiniGolem;

public class MoCModelMiniGolem extends ModelBase
{
  
    ModelRenderer Head;
    ModelRenderer HeadRed;
    ModelRenderer Body;
    ModelRenderer BodyRed;
    ModelRenderer LeftShoulder;
    ModelRenderer LeftArm;
    ModelRenderer LeftArmRingA;
    ModelRenderer LeftArmRingB;
    ModelRenderer RightShoulder;
    ModelRenderer RightArm;
    ModelRenderer RightArmRingA;
    ModelRenderer RightArmRingB;
    ModelRenderer RightLeg;
    ModelRenderer RightFoot;
    ModelRenderer LeftLeg;
    ModelRenderer LeftFoot;
    
    private float radianF = 57.29578F;
  
  public MoCModelMiniGolem()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Head = new ModelRenderer(this, 30, 0);
      Head.addBox(-3F, -3F, -3F, 6, 3, 6);
      Head.setRotationPoint(0F, 8F, 0F);
      setRotation(Head, 0F, -0.7853982F, 0F);
      
      HeadRed = new ModelRenderer(this, 30, 29);
      HeadRed.addBox(-3F, -3F, -3F, 6, 3, 6);
      HeadRed.setRotationPoint(0F, 8F, 0F);
      setRotation(HeadRed, 0F, -0.7853982F, 0F);
      
      Body = new ModelRenderer(this, 0, 0);
      Body.addBox(-5F, -10F, -5F, 10, 10, 10);
      Body.setRotationPoint(0F, 18F, 0F);
      
      BodyRed = new ModelRenderer(this, 0, 28);
      BodyRed.addBox(-5F, -10F, -5F, 10, 10, 10);
      BodyRed.setRotationPoint(0F, 18F, 0F);
      
      LeftShoulder = new ModelRenderer(this, 0, 4);
      LeftShoulder.addBox(0F, -1F, -1F, 1, 2, 2);
      LeftShoulder.setRotationPoint(5F, 11F, 0F);
      
      LeftArm = new ModelRenderer(this, 0, 48);
      LeftArm.addBox(1F, -2F, -2F, 4, 12, 4);
      LeftArm.setRotationPoint(5F, 11F, 0F);
      
      LeftArmRingA = new ModelRenderer(this, 20, 20);
      LeftArmRingA.addBox(0.5F, 1F, -2.5F, 5, 3, 5);
      LeftArmRingA.setRotationPoint(5F, 11F, 0F);
      
      LeftArmRingB = new ModelRenderer(this, 20, 20);
      LeftArmRingB.addBox(0.5F, 5F, -2.5F, 5, 3, 5);
      LeftArmRingB.setRotationPoint(5F, 11F, 0F);
      
      RightShoulder = new ModelRenderer(this, 0, 0);
      RightShoulder.addBox(-1F, -1F, -1F, 1, 2, 2);
      RightShoulder.setRotationPoint(-5F, 11F, 0F);
      
      RightArm = new ModelRenderer(this, 16, 48);
      RightArm.addBox(-5F, -2F, -2F, 4, 12, 4);
      RightArm.setRotationPoint(-5F, 11F, 0F);
      
      RightArmRingA = new ModelRenderer(this, 0, 20);
      RightArmRingA.addBox(-5.5F, 1F, -2.5F, 5, 3, 5);
      RightArmRingA.setRotationPoint(-5F, 11F, 0F);
      
      RightArmRingB = new ModelRenderer(this, 0, 20);
      RightArmRingB.addBox(-5.5F, 5F, -2.5F, 5, 3, 5);
      RightArmRingB.setRotationPoint(-5F, 11F, 0F);
      
      RightLeg = new ModelRenderer(this, 40, 9);
      RightLeg.addBox(-2.5F, 0F, -2F, 4, 6, 4);
      RightLeg.setRotationPoint(-2F, 18F, 0F);
      
      RightFoot = new ModelRenderer(this, 15, 22);
      RightFoot.addBox(-2.5F, 5F, -3F, 4, 1, 1);
      RightFoot.setRotationPoint(-2F, 18F, 0F);
      
      LeftLeg = new ModelRenderer(this, 40, 19);
      LeftLeg.addBox(-1.5F, 0F, -2F, 4, 6, 4);
      LeftLeg.setRotationPoint(2F, 18F, 0F);
      
      LeftFoot = new ModelRenderer(this, 15, 20);
      LeftFoot.addBox(-1.5F, 5F, -3F, 4, 1, 1);
      LeftFoot.setRotationPoint(2F, 18F, 0F);
      
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    
    MoCEntityMiniGolem minigolem = (MoCEntityMiniGolem)entity;
    boolean angry = minigolem.getIsAngry();
    boolean hasRock = minigolem.getHasRock();
    
    setRotationAngles(f, f1, f2, f3, f4, f5, hasRock);
    
    if (angry)
    {
        HeadRed.render(f5);
        BodyRed.render(f5);
    }else
    {
        Head.render(f5);
        Body.render(f5);
    }
    
    LeftShoulder.render(f5);
    LeftArm.render(f5);
    LeftArmRingA.render(f5);
    LeftArmRingB.render(f5);
    RightShoulder.render(f5);
    RightArm.render(f5);
    RightArmRingA.render(f5);
    RightArmRingB.render(f5);
    RightLeg.render(f5);
    RightFoot.render(f5);
    LeftLeg.render(f5);
    LeftFoot.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean hasRock)
  {
      float hRotY = f3 / 57.29578F;
      float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
      float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
      
      RightLeg.rotateAngleX = RLegXRot;
      RightFoot.rotateAngleX = RLegXRot;
      LeftLeg.rotateAngleX = LLegXRot;
      LeftFoot.rotateAngleX = LLegXRot;
      
      Head.rotateAngleY = -0.7853982F + hRotY;
      HeadRed.rotateAngleY = -0.7853982F + hRotY;
      
      if (hasRock)
      {
          LeftShoulder.rotateAngleZ = 0F;
          LeftShoulder.rotateAngleX = -180F / radianF;
          RightShoulder.rotateAngleZ =  0F;
          RightShoulder.rotateAngleX = -180F / radianF;
      }else
      {
          LeftShoulder.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
          LeftShoulder.rotateAngleX = RLegXRot;
          RightShoulder.rotateAngleZ = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
          RightShoulder.rotateAngleX = LLegXRot;
      }
            
      RightArm.rotateAngleX = RightArmRingA.rotateAngleX = RightArmRingB.rotateAngleX = RightShoulder.rotateAngleX;
      RightArm.rotateAngleZ = RightArmRingA.rotateAngleZ = RightArmRingB.rotateAngleZ = RightShoulder.rotateAngleZ;
      
      LeftArm.rotateAngleX = LeftArmRingA.rotateAngleX = LeftArmRingB.rotateAngleX = LeftShoulder.rotateAngleX;
      LeftArm.rotateAngleZ = LeftArmRingA.rotateAngleZ = LeftArmRingB.rotateAngleZ = LeftShoulder.rotateAngleZ;
      
    //super.setRotationAngles(f, f1, f2, f3, f4, f5);
  }

}
