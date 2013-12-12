package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;


public class MoCModelKomodo extends ModelBase
{



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
 
public MoCModelKomodo()
{
 textureWidth = 64;
 textureHeight = 64;
 
     Head = new ModelRenderer(this);
     Head.setRotationPoint(0F, 13F, -8F);
     
     Neck = new ModelRenderer(this, 22, 34);
     Neck.addBox(-2F, 0F, -6F, 4, 5, 6);
     Neck.setRotationPoint(0F, 0F, 0F);
     Head.addChild(Neck);
     //setRotation(Neck, 0.1919862F, 0F, 0F);
     
     Nose = new ModelRenderer(this, 24, 45);
     Nose.addBox(-1.5F, -1F, -6.5F, 3, 2, 6);
     Nose.setRotationPoint(0F, 1F, -5F);
     Neck.addChild(Nose);
     //setRotation(Head, 0.3778793F, 0F, 0F);
    
     Mouth = new ModelRenderer(this, 0, 12);
     Mouth.addBox(-1F, -0.3F, -5F, 2, 1, 6);
     Mouth.setRotationPoint(0F, 3F, -5.8F);
     Neck.addChild(Mouth);
     //setRotation(Mouth, 0.2291648F, 0F, 0F);
    
     Tongue = new ModelRenderer(this, 48, 44);
     Tongue.addBox(-1.5F, 0.0F, -5F, 3, 0, 5);
     Tongue.setRotationPoint(0F, -0.4F, -4.7F);
     Mouth.addChild(Tongue);
   
     Chest = new ModelRenderer(this, 36, 2);
     Chest.addBox(-3F, 0F, -8F, 6, 6, 7);
     Chest.setRotationPoint(0F, 13F, 0F);
   
     Abdomen = new ModelRenderer(this, 36, 49);
     Abdomen.addBox(-3F, 0F, -1F, 6, 7, 8);
     Abdomen.setRotationPoint(0F, 13F, 0F);

     Tail = new ModelRenderer(this);
     Tail.setRotationPoint(0F, 13F, 7F);
     
     Tail1 = new ModelRenderer(this, 0, 21);
     Tail1.addBox(-2F, 0F, 0F, 4, 5, 8);
     Tail1.setRotationPoint(0F, 0F, 0F);
     //setRotation(Tail1, -0.2724366F, 0F, 0F);
     Tail.addChild(Tail1);
     
     Tail2 = new ModelRenderer(this, 0, 34);
     Tail2.addBox(-1.5F, 0F, 0F, 3, 4, 8);
     Tail2.setRotationPoint(0F, 0.1F, 7.7F);
     //setRotation(Tail2, -0.5698655F, 0F, 0F);
     Tail1.addChild(Tail2);
     
     Tail3 = new ModelRenderer(this, 0, 46);
     Tail3.addBox(-1F, 0F, 0F, 2, 3, 8);
     Tail3.setRotationPoint(0F, 0.1F, 7.3F);
     //setRotation(Tail3, -0.3361566F, 0F, 0F);
     Tail2.addChild(Tail3);
  
     Tail4 = new ModelRenderer(this, 24, 21);
     Tail4.addBox(-0.5F, 0F, 0F, 1, 2, 8);
     Tail4.setRotationPoint(0F, 0.1F, 7F);
     //setRotation(Tail4, -0.1502636F, 0F, 0F);
     Tail3.addChild(Tail4);

   
     LegFrontLeft = new ModelRenderer(this);
     LegFrontLeft.setRotationPoint(2F, 17F, -7F);
     
     LegFrontLeft1 = new ModelRenderer(this, 0, 0);
     LegFrontLeft1.addBox(0F, -1F, -1.5F, 4, 3, 3);
     LegFrontLeft1.setRotationPoint(0F, 0F, 0F);
     //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
     LegFrontLeft.addChild(LegFrontLeft1);
     
     LegFrontLeft2 = new ModelRenderer(this, 22, 0);
     LegFrontLeft2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
     LegFrontLeft2.setRotationPoint(3F, 0.5F, 0F);
     LegFrontLeft1.addChild(LegFrontLeft2);
     
     LegFrontLeft3 = new ModelRenderer(this, 16, 58);
     LegFrontLeft3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
     LegFrontLeft3.setRotationPoint(0F, 4F, 0F);
     setRotation(LegFrontLeft3, 0F, -10F/radianF, 0F);
     LegFrontLeft2.addChild(LegFrontLeft3);
     
     LegBackLeft = new ModelRenderer(this);
     LegBackLeft.setRotationPoint(2F, 17F, 6F);
     
     LegBackLeft1 = new ModelRenderer(this, 0, 0);
     LegBackLeft1.addBox(0F, -1F, -1.5F, 4, 3, 3);
     LegBackLeft1.setRotationPoint(0F, 0F, 0F);
     //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
     LegBackLeft.addChild(LegBackLeft1);
     
     LegBackLeft2 = new ModelRenderer(this, 22, 0);
     LegBackLeft2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
     LegBackLeft2.setRotationPoint(3F, 0.5F, 0F);
     LegBackLeft1.addChild(LegBackLeft2);
     
     LegBackLeft3 = new ModelRenderer(this, 16, 58);
     LegBackLeft3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
     LegBackLeft3.setRotationPoint(0F, 4F, 0F);
     setRotation(LegBackLeft3, 0F, -10F/radianF, 0F);
     LegBackLeft2.addChild(LegBackLeft3);
     

   
     LegFrontRight = new ModelRenderer(this);
     LegFrontRight.setRotationPoint(-2F, 17F, -7F);
     
     LegFrontRight1 = new ModelRenderer(this, 0, 6);
     LegFrontRight1.addBox(-4F, -1F, -1.5F, 4, 3, 3);
     LegFrontRight1.setRotationPoint(0F, 0F, 0F);
     //setRotation(LegFrontLeft1, 0F, 0F, 10F/radianF0.5235988F);
     LegFrontRight.addChild(LegFrontRight1);
     
     LegFrontRight2 = new ModelRenderer(this, 22, 7);
     LegFrontRight2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
     LegFrontRight2.setRotationPoint(-3F, 0.5F, 0F);
     LegFrontRight1.addChild(LegFrontRight2);
     
     LegFrontRight3 = new ModelRenderer(this, 0, 58);
     LegFrontRight3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
     LegFrontRight3.setRotationPoint(0F, 4F, 0F);
     setRotation(LegFrontRight3, 0F, 10F/radianF, 0F);
     LegFrontRight2.addChild(LegFrontRight3);
     
     
     LegBackRight = new ModelRenderer(this);
     LegBackRight.setRotationPoint(-2F, 17F, 6F);
     
     LegBackRight1 = new ModelRenderer(this, 0, 6);
     LegBackRight1.addBox(-4F, -1F, -1.5F, 4, 3, 3);
     LegBackRight1.setRotationPoint(0F, 0F, 0F);
     //setRotation(LegFrontLeft1, 0F, 0F, 0.5235988F);
     LegBackRight.addChild(LegBackRight1);
     
     LegBackRight2 = new ModelRenderer(this, 22, 7);
     LegBackRight2.addBox(-1.5F, 0F, -1.5F, 3, 4, 3);
     LegBackRight2.setRotationPoint(-3F, 0.5F, 0F);
     LegBackRight1.addChild(LegBackRight2);
     
     LegBackRight3 = new ModelRenderer(this, 0, 58);
     LegBackRight3.addBox(-1.5F, 0F, -3.5F, 3, 1, 5);
     LegBackRight3.setRotationPoint(0F, 4F, 0F);
     setRotation(LegBackRight3, 0F, 10F/radianF, 0F);
     LegBackRight2.addChild(LegBackRight3);
     

   
     SaddleA = new ModelRenderer(this, 36, 28);
     SaddleA.addBox(-2.5F, 0.5F, -4F, 5, 1, 8);
     SaddleA.setRotationPoint(0F, 12F, 0F);
     SaddleA.setTextureSize(64, 64);
     SaddleA.mirror = true;
     setRotation(SaddleA, 0F, 0F, 0F);
     SaddleC = new ModelRenderer(this, 36, 37);
     SaddleC.addBox(-2.5F, 0F, 2F, 5, 1, 2);
     SaddleC.setRotationPoint(0F, 12F, 0F);
     SaddleC.setTextureSize(64, 64);
     SaddleC.mirror = true;
     setRotation(SaddleC, 0F, 0F, 0F);
     SaddleB = new ModelRenderer(this, 54, 37);
     SaddleB.addBox(-1.5F, 0F, -4F, 3, 1, 2);
     SaddleB.setRotationPoint(0F, 12F, 0F);
     SaddleB.setTextureSize(64, 64);
     SaddleB.mirror = true;
     setRotation(SaddleB, 0F, 0F, 0F);
}

public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
{
    MoCEntityKomodo komodo = (MoCEntityKomodo) entity;
    //int type = komodo.getType();
    //byte harness = komodo.getHarness();
    //byte storage = komodo.getStorage();
    boolean mouth = (komodo.mouthCounter != 0);
    boolean sitting = (komodo.sitCounter != 0);
    boolean swimming = (komodo.isSwimming());
    boolean moveTail = (komodo.tailCounter != 0);
    boolean tongue = (komodo.tongueCounter != 0);
 setRotationAngles(f, f1, f2, f3, f4, f5, sitting, moveTail, tongue, mouth, swimming);

 Tail.render(f5);
 Head.render(f5);
 Chest.render(f5);
 LegFrontLeft.render(f5);
 LegBackLeft.render(f5);
 LegFrontRight.render(f5);
 LegBackRight.render(f5);
 Abdomen.render(f5);
 
 if (komodo.getIsRideable())
 {
     SaddleA.render(f5);
     SaddleC.render(f5);
     SaddleB.render(f5);
 }
 
}

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    /**
     * Used to adjust the Y offset of the model cubes
     */
    private void AdjustY(float f)
    {
        float yOff = f;
        Tail.rotationPointY = yOff + 13F;
        Head.rotationPointY = yOff + 13F;
        Chest.rotationPointY = yOff + 13F;
        LegFrontLeft.rotationPointY = yOff + 17F;
        LegBackLeft.rotationPointY = yOff + 17F;
        LegFrontRight.rotationPointY = yOff + 17F;
        LegBackRight.rotationPointY = yOff + 17F;
        Abdomen.rotationPointY = yOff + 13F;
        SaddleA.rotationPointY = yOff + 12F;
        SaddleB.rotationPointY = yOff + 12F;
        SaddleC.rotationPointY = yOff + 12F;
         
        
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean sitting, boolean movetail, boolean tongue, boolean mouth, boolean swimming)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        float TailXRot = MathHelper.cos(f * 0.4F) * 0.2F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6F) * 0.9F * f1;
        float RLegXRot = MathHelper.cos((f * 0.6F) + 3.141593F) * 0.9F * f1;
        //System.out.println("F3 = " + f3);
        
        if (f3 > 60F)
        {
            f3 = 60F;
        }
        if (f3 < -60F)
        {
            f3 = -60F;
        }
        
        float f10 = 0F;
        if (swimming)
        {
            f10 = 4F;
            Tail1.rotateAngleX = (0F /radianF) - TailXRot;
            LegFrontLeft1.rotateAngleZ = 0F/radianF;
            LegFrontLeft2.rotateAngleZ = -65F/radianF;
            LegFrontLeft1.rotateAngleY = -80F/radianF;
            
            LegBackLeft1.rotateAngleZ = 0F/radianF;
            LegBackLeft2.rotateAngleZ = -65F/radianF;
            LegBackLeft1.rotateAngleY = -80F/radianF;
            
            LegFrontRight1.rotateAngleZ = 0F/radianF ;
            LegFrontRight2.rotateAngleZ = 65F/radianF;
            LegFrontRight1.rotateAngleY = 80F/radianF;
            
            LegBackRight1.rotateAngleZ = 0F/radianF;// + (LLegXRotD/radianF);;
            LegBackRight2.rotateAngleZ = 65F/radianF;
            LegBackRight1.rotateAngleY = 80F/radianF;
        }
        else if (sitting) 
        {
            f10 = 4F;
            Tail1.rotateAngleX = (-5F /radianF) - TailXRot;
            LegFrontLeft1.rotateAngleZ = -30F/radianF;
            LegFrontLeft2.rotateAngleZ = 0F/radianF;
            LegFrontLeft1.rotateAngleY = LLegXRot;
            
            LegBackLeft1.rotateAngleZ = 0F/radianF;
            LegBackLeft2.rotateAngleZ = -65F/radianF;
            LegBackLeft1.rotateAngleY = -40F/radianF;
            
            LegFrontRight1.rotateAngleZ = 30F/radianF ;
            LegFrontRight2.rotateAngleZ = 0F/radianF;
            LegFrontRight1.rotateAngleY = -RLegXRot;
            
            LegBackRight1.rotateAngleZ = 0F/radianF;// + (LLegXRotD/radianF);;
            LegBackRight2.rotateAngleZ = 65F/radianF;
            LegBackRight1.rotateAngleY = 40F/radianF;
        }
        else
        {
            Tail1.rotateAngleX = (-15F /radianF) - TailXRot;
            LegFrontLeft1.rotateAngleZ = 30F/radianF;
            LegFrontLeft2.rotateAngleZ = -30F/radianF;
            LegFrontLeft1.rotateAngleY = LLegXRot;
            LegFrontLeft2.rotateAngleX = -LLegXRot;
            
            LegBackLeft1.rotateAngleZ = 30F/radianF;
            LegBackLeft2.rotateAngleZ = -30F/radianF;
            LegBackLeft1.rotateAngleY = RLegXRot;
            LegBackLeft2.rotateAngleX = -RLegXRot;
            
            LegFrontRight1.rotateAngleZ = -30F/radianF ;
            LegFrontRight2.rotateAngleZ = 30F/radianF;
            LegFrontRight1.rotateAngleY = -RLegXRot;
            LegFrontRight2.rotateAngleX = -RLegXRot;
            
            LegBackRight1.rotateAngleZ = -30F/radianF;// + (LLegXRotD/radianF);;
            LegBackRight2.rotateAngleZ = 30F/radianF;
            LegBackRight1.rotateAngleY = -LLegXRot;
            LegBackRight2.rotateAngleX = -LLegXRot;
        }
        AdjustY(f10);
        
        float tongueF = 0;
        if (!mouth && tongue)
        {
            tongueF = (MathHelper.cos(f2 * 3F) / 10F);
            Tongue.rotationPointZ = -4.7F;
        }else
        {
            Tongue.rotationPointZ = 0.3F;
        }
         
        float mouthF = 0;
        if (mouth)
        {
            mouthF = 35F/radianF;
            Tongue.rotationPointZ = -0.8F;
        }
        
        Neck.rotateAngleX = 11F/radianF + (f4 * 0.33F/ radianF);
        Nose.rotateAngleX = 10.6F/radianF + (f4 * 0.66F/ radianF);
        Mouth.rotateAngleX = mouthF + (-3F/radianF) + (f4 * 0.66F/ radianF);
        Tongue.rotateAngleX = tongueF;
        
        Neck.rotateAngleY = (f3 * 0.33F/ radianF);
        Nose.rotateAngleY = (f3 * 0.66F/ radianF);
        Mouth.rotateAngleY = (f3 * 0.66F/ radianF);
        //Tail2.rotateAngleY = LLegXRot;
        
      //y = A * sin(w * t - k *x)
        /*
         * w1 = speed of wave propagation +/- as needed
         * t = time
         * k = number of waves
         * A = amplitude of wave (how much will it depart from center
         * x = position? :)

        *A 1.3
        *k 0.5
        *w -3.5
         */

        
        Tail2.rotateAngleX = (-17F /radianF) + TailXRot;
        Tail3.rotateAngleX = (13F /radianF) + TailXRot;
        Tail4.rotateAngleX = (11F /radianF) + TailXRot;
        
        
        float t = f / 2;
        
        if (movetail)
        {
            t = f2/4F;
        }
        float A = 0.35F;//0.8F;
        float w = 0.6F;
        float k = 0.6F;
        
        int i = 0;
        float tailLat = 0F;
        tailLat = A * MathHelper.sin(w * t - k * (float) i++);
        Tail1.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * (float) i++);
        Tail2.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * (float) i++);
        Tail3.rotateAngleY = tailLat;
        tailLat = A * MathHelper.sin(w * t - k * (float) i++);
        Tail4.rotateAngleY = tailLat;
        

    }

}


