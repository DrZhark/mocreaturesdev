package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MoCModelRaccoon extends ModelBase
{
  
    ModelRenderer Head;
    ModelRenderer Snout;
    ModelRenderer RightEar;
    ModelRenderer LeftEar;
    ModelRenderer LeftSideburn;
    ModelRenderer RightSideburn;
    ModelRenderer RightRearFoot;
    ModelRenderer Neck;
    ModelRenderer Body;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer RightFrontLegA;
    ModelRenderer RightFrontLegB;
    ModelRenderer RightFrontFoot;
    ModelRenderer LeftFrontLegA;
    ModelRenderer LeftFrontLegB;
    ModelRenderer LeftFrontFoot;
    ModelRenderer RightRearLegA;
    ModelRenderer RightRearLegB;
    ModelRenderer LeftRearLegB;
    ModelRenderer LeftRearLegA;
    ModelRenderer LeftRearFoot;
    
    private float radianF = 57.29578F;
  
  public MoCModelRaccoon()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Head = new ModelRenderer(this, 38, 21);
      Head.addBox(-4F, -3.5F, -6.5F, 8, 6, 5);
      Head.setRotationPoint(0F, 17F, -4F);
      
      /*RightSideburn = new ModelRenderer(this, 0, 32);
      RightSideburn.addBox(-6.7F, -1.5F, -3.6F, 3, 4, 4);
      RightSideburn.setRotationPoint(0F, 17F, -4F);
      setRotation(RightSideburn, 0F, -0.5235988F, 0F);
      
      LeftSideburn = new ModelRenderer(this, 0, 40);
      LeftSideburn.addBox(3.7F, -1.5F, -3.6F, 3, 4, 4);
      LeftSideburn.setRotationPoint(0F, 17F, -4F);
      setRotation(LeftSideburn, 0F, 0.5235988F, 0F);
      */
      
      RightSideburn = new ModelRenderer(this, 0, 32);
      RightSideburn.addBox(-3F, -2F, -2F, 3, 4, 4);
      RightSideburn.setRotationPoint(-2.5F, 0.5F, -3.2F);
      setRotation(RightSideburn, 0F, -0.5235988F, 0F);
      Head.addChild(RightSideburn);
      
      LeftSideburn = new ModelRenderer(this, 0, 40);
      LeftSideburn.addBox(0F, -2F, -2F, 3, 4, 4);
      LeftSideburn.setRotationPoint(2.5F, 0.5F, -3.2F);
      setRotation(LeftSideburn, 0F, 0.5235988F, 0F);
      Head.addChild(LeftSideburn);
      
      Snout = new ModelRenderer(this, 24, 25);
      Snout.addBox(-1.5F, -0.5F, -10.5F, 3, 3, 4);
      Snout.setRotationPoint(0F, 17F, -4F);
      
      RightEar = new ModelRenderer(this, 24, 22);
      RightEar.addBox(-4F, -5.5F, -3.5F, 3, 2, 1);
      RightEar.setRotationPoint(0F, 17F, -4F);
      
      LeftEar = new ModelRenderer(this, 24, 18);
      LeftEar.addBox(1F, -5.5F, -3.5F, 3, 2, 1);
      LeftEar.setRotationPoint(0F, 17F, -4F);
      
      
      
      
      
      RightRearFoot = new ModelRenderer(this, 46, 0);
      RightRearFoot.addBox(-5F, 5F, -2F, 3, 1, 3);
      RightRearFoot.setRotationPoint(0F, 18F, 4F);
      
      Neck = new ModelRenderer(this, 46, 4);
      Neck.addBox(-2.5F, -2F, -3F, 5, 4, 3);
      Neck.setRotationPoint(0F, 17F, -4F);
      setRotation(Neck, -0.4461433F, 0F, 0F);
      
      Body = new ModelRenderer(this, 0, 0);
      Body.addBox(-3F, 0F, -3F, 6, 6, 12);
      Body.setRotationPoint(0F, 15F, -2F);
      
      TailA = new ModelRenderer(this, 0, 3);
      TailA.addBox(-1.5F, -6F, -1.5F, 3, 6, 3);
      TailA.setRotationPoint(0F, 16.5F, 6.5F);
      setRotation(TailA, -2.024582F, 0F, 0F);
      
      TailB = new ModelRenderer(this, 24, 3);
      TailB.addBox(-1.5F, -11F, 0.3F, 3, 6, 3);
      TailB.setRotationPoint(0F, 16.5F, 6.5F);
     setRotation(TailB, -1.689974F, 0F, 0F);
      
     RightFrontLegA = new ModelRenderer(this, 36, 0);
      RightFrontLegA.addBox(-4F, -1F, -1F, 2, 5, 3);
      RightFrontLegA.setRotationPoint(0F, 18F, -4F);
      setRotation(RightFrontLegA, 0.5205006F, 0F, 0F);
      
      RightFrontLegB = new ModelRenderer(this, 46, 11);
      RightFrontLegB.addBox(-3.5F, 1F, 2F, 2, 4, 2);
      RightFrontLegB.setRotationPoint(0F, 18F, -4F);
      setRotation(RightFrontLegB, -0.3717861F, 0F, 0F);
      
      RightFrontFoot = new ModelRenderer(this, 46, 0);
      RightFrontFoot.addBox(-4F, 5F, -1F, 3, 1, 3);
      RightFrontFoot.setRotationPoint(0F, 18F, -4F);
      
      LeftFrontLegA = new ModelRenderer(this, 36, 8);
      LeftFrontLegA.addBox(2F, -1F, -1F, 2, 5, 3);
      LeftFrontLegA.setRotationPoint(0F, 18F, -4F);
      setRotation(LeftFrontLegA, 0.5205006F, 0F, 0F);
      
      LeftFrontLegB = new ModelRenderer(this, 54, 11);
      LeftFrontLegB.addBox(1.5F, 1F, 2F, 2, 4, 2);
      LeftFrontLegB.setRotationPoint(0F, 18F, -4F);
      setRotation(LeftFrontLegB, -0.3717861F, 0F, 0F);
      
      LeftFrontFoot = new ModelRenderer(this, 46, 0);
      LeftFrontFoot.addBox(1F, 5F, -1F, 3, 1, 3);
      LeftFrontFoot.setRotationPoint(0F, 18F, -4F);
      
      RightRearLegA = new ModelRenderer(this, 12, 18);
      RightRearLegA.addBox(-5F, -2F, -3F, 2, 5, 4);
      RightRearLegA.setRotationPoint(0F, 18F, 4F);
      setRotation(RightRearLegA, 0.9294653F, 0F, 0F);
      
      RightRearLegB = new ModelRenderer(this, 0, 27);
      RightRearLegB.addBox(-4.5F, 2F, -5F, 2, 2, 3);
      RightRearLegB.setRotationPoint(0F, 18F, 4F);
      setRotation(RightRearLegB, 0.9294653F, 0F, 0F);
      
      LeftRearLegB = new ModelRenderer(this, 10, 27);
      LeftRearLegB.addBox(2.5F, 2F, -5F, 2, 2, 3);
      LeftRearLegB.setRotationPoint(0F, 18F, 4F);
      setRotation(LeftRearLegB, 0.9294653F, 0F, 0F);
      
      LeftRearLegA = new ModelRenderer(this, 0, 18);
      LeftRearLegA.addBox(3F, -2F, -3F, 2, 5, 4);
      LeftRearLegA.setRotationPoint(0F, 18F, 4F);
      setRotation(LeftRearLegA, 0.9294653F, 0F, 0F);
      
      LeftRearFoot = new ModelRenderer(this, 46, 0);
      LeftRearFoot.addBox(2F, 5F, -2F, 3, 1, 3);
      LeftRearFoot.setRotationPoint(0F, 18F, 4F);
      
  }
  
  @Override
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Head.render(f5);
    Snout.render(f5);
    RightEar.render(f5);
    LeftEar.render(f5);
    //LeftSideburn.render(f5);
    //RightSideburn.renderWithRotation(f5);
    RightRearFoot.render(f5);
    Neck.render(f5);
    Body.render(f5);
    TailA.render(f5);
    TailB.render(f5);
    RightFrontLegA.render(f5);
    RightFrontLegB.render(f5);
    RightFrontFoot.render(f5);
    LeftFrontLegA.render(f5);
    LeftFrontLegB.render(f5);
    LeftFrontFoot.render(f5);
    RightRearLegA.render(f5);
    RightRearLegB.render(f5);
    LeftRearLegB.render(f5);
    LeftRearLegA.render(f5);
    LeftRearFoot.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
      Head.rotateAngleY = f3 / 57.29578F;
      Head.rotateAngleX = f4 / 57.29578F;
      Snout.rotateAngleY = Head.rotateAngleY;
      Snout.rotateAngleX = Head.rotateAngleX;
      RightEar.rotateAngleX = Head.rotateAngleX;
      RightEar.rotateAngleY = Head.rotateAngleY;
      LeftEar.rotateAngleX = Head.rotateAngleX;
      LeftEar.rotateAngleY = Head.rotateAngleY;
      //RightSideburn.rotateAngleX = Head.rotateAngleX;
      //RightSideburn.rotateAngleY = (-30F/radianF) + Head.rotateAngleY;
      //LeftSideburn.rotateAngleX = Head.rotateAngleX;
      //LeftSideburn.rotateAngleY = (30F/radianF) + Head.rotateAngleY;
      
      float RLegXRot = MathHelper.cos((f * 1.0F) + 3.141593F) * 0.8F * f1;
      float LLegXRot = MathHelper.cos(f * 1.0F) * 0.8F * f1;
        
      RightFrontLegA.rotateAngleX = (30F/radianF) + RLegXRot;
      LeftFrontLegA.rotateAngleX = (30F/radianF) + LLegXRot;
      RightRearLegA.rotateAngleX = (53F/radianF) + LLegXRot;
      LeftRearLegA.rotateAngleX = (53F/radianF) + RLegXRot;
      
      RightFrontLegB.rotateAngleX = (-21F/radianF) + RLegXRot;
      RightFrontFoot.rotateAngleX = RLegXRot;
      LeftFrontLegB.rotateAngleX = (-21F/radianF) + LLegXRot;
      LeftFrontFoot.rotateAngleX = LLegXRot;
      
      RightRearLegB.rotateAngleX = (53F/radianF) + LLegXRot;
      RightRearFoot.rotateAngleX = LLegXRot;
      LeftRearLegB.rotateAngleX = (53F/radianF) + RLegXRot;
      LeftRearFoot.rotateAngleX = RLegXRot;
      
      TailA.rotateAngleY = MathHelper.cos(f * 0.6662F) * 0.7F * f1;
      TailB.rotateAngleY = TailA.rotateAngleY;
  }
}


