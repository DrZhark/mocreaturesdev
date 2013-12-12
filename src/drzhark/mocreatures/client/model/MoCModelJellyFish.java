package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelJellyFish extends ModelBase {

    public MoCModelJellyFish()
    {
        textureWidth = 64;
        textureHeight = 16;
        
          Top = new ModelRenderer(this, 0, 10);
          Top.addBox(-2.5F, 0F, -2.5F, 5, 1, 5);
          Top.setRotationPoint(0F, 11F, 0F);

          Head = new ModelRenderer(this, 0, 0);
          Head.addBox(-4F, 0F, -4F, 8, 2, 8);
          Head.setRotationPoint(0F, 12F, 0F);
          
          HeadSmall = new ModelRenderer(this, 24, 0);
          HeadSmall.addBox(-2F, 0F, -2F, 4, 3, 4);
          HeadSmall.setRotationPoint(0F, 12.5F, 0F);
          
          Body = new ModelRenderer(this, 36, 0);
          Body.addBox(-3.5F, 0F, -3.5F, 7, 7, 7);
          Body.setRotationPoint(0F, 13.8F, 0F);

          BodyCenter = new ModelRenderer(this, 0, 0);
          BodyCenter.addBox(-1F, 0F, -1F, 2, 3, 2);
          BodyCenter.setRotationPoint(0F, 15.5F, 0F);

          BodyBottom = new ModelRenderer(this, 20, 10);
          BodyBottom.addBox(-2F, 0F, -2F, 4, 2, 4);
          BodyBottom.setRotationPoint(0F, 18.3F, 0F);

          Side1 = new ModelRenderer(this, 20, 10);
          Side1.addBox(-2F, 5F, 0F, 4, 2, 4);
          Side1.setRotationPoint(0F, 12.5F, 0F);
          setRotation(Side1, -0.7679449F, 0F, 0F);

          Side2 = new ModelRenderer(this, 20, 10);
          Side2.addBox(-4F, 5F, -2F, 4, 2, 4);
          Side2.setRotationPoint(0F, 12.5F, 0F);
          setRotation(Side2, 0F, 0F, -0.7679449F);
          
          Side3 = new ModelRenderer(this, 20, 10);
          Side3.addBox(0F, 5F, -2F, 4, 2, 4);
          Side3.setRotationPoint(0F, 12.5F, 0F);
          setRotation(Side3, 0F, 0F, 0.7679449F);
          
          Side4 = new ModelRenderer(this, 20, 10);
          Side4.addBox(-2F, 5F, -4F, 4, 2, 4);
          Side4.setRotationPoint(0F, 12.5F, 0F);
          setRotation(Side4, 0.7679449F, 0F, 0F);
          
          LegSmall1 = new ModelRenderer(this, 60, 2);
          LegSmall1.addBox(-1F, 0F, -1F, 1, 3, 1);
          LegSmall1.setRotationPoint(0F, 18.5F, 0F);
          
          LegC1 = new ModelRenderer(this, 15, 10);
          LegC1.addBox(-1F, 0F, -1F, 1, 4, 1);
          LegC1.setRotationPoint(-0.5F, 15.5F, -0.5F);
          setRotation(LegC1, -0.2602503F, 0F, 0.1487144F);
          
          LegC2 = new ModelRenderer(this, 15, 10);
          LegC2.addBox(-1F, 0F, 0F, 1, 4, 1);
          LegC2.setRotationPoint(0.5F, 15.5F, -0.5F);
          setRotation(LegC2, 0.1487144F, 1.747395F, 0F);

          LegC3 = new ModelRenderer(this, 15, 10);
          LegC3.addBox(-1F, 0F, 0F, 1, 4, 1);
          LegC3.setRotationPoint(-0.5F, 15.5F, 0.5F);
          setRotation(LegC3, 0.1115358F, 0.3717861F, 0.2230717F);
          
          Leg1 = new ModelRenderer(this, 0, 10);
          Leg1.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
          Leg1.setRotationPoint(0F, 20F, 2.5F);

          Leg2 = new ModelRenderer(this, 0, 10);
          Leg2.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
          Leg2.setRotationPoint(0F, 20F, -2.5F);

          Leg3 = new ModelRenderer(this, 0, 10);
          Leg3.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
          Leg3.setRotationPoint(2.5F, 20F, 0F);

          Leg4 = new ModelRenderer(this, 0, 10);
          Leg4.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
          Leg4.setRotationPoint(-2.5F, 20F, 0F);

          Leg5 = new ModelRenderer(this, 0, 10);
          Leg5.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
          Leg5.setRotationPoint(2F, 20F, 2F);
          setRotation(Leg5, 0F, 0.7853982F, 0F);

          Leg6 = new ModelRenderer(this, 0, 10);
          Leg6.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
          Leg6.setRotationPoint(2F, 20F, -2F);
          setRotation(Leg6, 0F, 0.7853982F, 0F);
          
          Leg7 = new ModelRenderer(this, 0, 10);
          Leg7.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
          Leg7.setRotationPoint(-2F, 20F, -2F);
          setRotation(Leg7, 0F, 0.7853982F, 0F);
          
          Leg8 = new ModelRenderer(this, 60, 0);
          Leg8.addBox(0F, 0F, 0F, 1, 5, 1);
          Leg8.setRotationPoint(0F, 18.5F, 0F);

          Leg9 = new ModelRenderer(this, 0, 10);
          Leg9.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
          Leg9.setRotationPoint(-2F, 20F, 2F);
          setRotation(Leg9, 0F, 0.7853982F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Top.render(f5);
        Head.render(f5);
        HeadSmall.render(f5);
        Body.render(f5);
        BodyCenter.render(f5);
        BodyBottom.render(f5);
        Side1.render(f5);
        Side2.render(f5);
        Side3.render(f5);
        Side4.render(f5);
        LegSmall1.render(f5);
        LegC1.render(f5);
        LegC2.render(f5);
        LegC3.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg3.render(f5);
        Leg4.render(f5);
        Leg5.render(f5);
        Leg6.render(f5);
        Leg7.render(f5);
        Leg8.render(f5);
        Leg9.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        //FootA.rotateAngleX = (f * 0.6662F) * f1;
        //FootA.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float f6 = f1 * 2.0F;
        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }
        
        LegSmall1.rotateAngleX = f6;
        LegC1.rotateAngleX = f6;
        LegC2.rotateAngleX = f6;
        LegC3.rotateAngleX = f6;
        Leg1.rotateAngleX = f6;
        Leg2.rotateAngleX = f6;
        Leg3.rotateAngleX = f6;
        Leg4.rotateAngleX = f6;
        Leg5.rotateAngleX = f6;
        Leg6.rotateAngleX = f6;
        Leg7.rotateAngleX = f6;
        Leg8.rotateAngleX = f6;
        Leg9.rotateAngleX = f6;
        
        
        /*FootA.rotateAngleX = f6;
        FootB.rotateAngleX = f6;
        FootC.rotateAngleX = f6;
        FootD.rotateAngleX = f6;
        FootE.rotateAngleX = f6;*/
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
      {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
      }

    /*ModelRenderer Body;
    ModelRenderer BodyUp;
    ModelRenderer BodyD;
    ModelRenderer FootA;
    ModelRenderer FootB;
    ModelRenderer FootC;
    ModelRenderer FootD;
    ModelRenderer FootE;
    ModelRenderer SpotA;
    ModelRenderer Core;*/
    //public float pulsingSize;

    ModelRenderer Top;
    ModelRenderer Head;
    ModelRenderer HeadSmall;
    ModelRenderer Body;
    ModelRenderer BodyCenter;
    ModelRenderer BodyBottom;
    ModelRenderer Side1;
    ModelRenderer Side2;
    ModelRenderer Side3;
    ModelRenderer Side4;
    ModelRenderer LegSmall1;
    ModelRenderer LegC1;
    ModelRenderer LegC2;
    ModelRenderer LegC3;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Leg5;
    ModelRenderer Leg6;
    ModelRenderer Leg7;
    ModelRenderer Leg8;
    ModelRenderer Leg9;
}
