package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MoCModelMediumFish extends ModelBase
{
    //fields
    ModelRenderer Head;
    ModelRenderer LowerHead;
    ModelRenderer Nose;
    ModelRenderer MouthBottom;
    ModelRenderer MouthBottomB;
    ModelRenderer Body;
    ModelRenderer BackUp;
    ModelRenderer BackDown;
    ModelRenderer Tail;
    ModelRenderer TailFin;
    ModelRenderer RightPectoralFin;
    ModelRenderer LeftPectoralFin;
    ModelRenderer UpperFin;
    ModelRenderer LowerFin;
    ModelRenderer RightLowerFin;
    ModelRenderer LeftLowerFin;

    public MoCModelMediumFish()
    {
        textureWidth = 64;
        textureHeight = 32;

        Head = new ModelRenderer(this, 0, 10);
        Head.addBox(-5F, 0F, -1.5F, 5, 3, 3);
        Head.setRotationPoint(-8F, 6F, 0F);
        setRotation(Head, 0F, 0F, -0.4461433F);
        
        LowerHead = new ModelRenderer(this, 0, 16);
        LowerHead.addBox(-4F, -3F, -1.5F, 4, 3, 3);
        LowerHead.setRotationPoint(-8F, 12F, 0F);
        setRotation(LowerHead, 0F, 0F, 0.3346075F);
        
        Nose = new ModelRenderer(this, 14, 17);
        Nose.addBox(-1F, -1F, -1F, 1, 3, 2);
        Nose.setRotationPoint(-11F, 8.2F, 0F);
        setRotation(Nose, 0F, 0F, 1.412787F);
        
        MouthBottom = new ModelRenderer(this, 16, 10);
        MouthBottom.addBox(-2F, -0.4F, -1F, 2, 1, 2);
        MouthBottom.setRotationPoint(-11.5F, 10F, 0F);
        setRotation(MouthBottom, 0F, 0F, 0.3346075F);
        
        MouthBottomB = new ModelRenderer(this, 16, 13);
        MouthBottomB.addBox(-1.5F, -2.4F, -0.5F, 1, 1, 1);
        MouthBottomB.setRotationPoint(-11.5F, 10F, 0F);
        setRotation(MouthBottomB, 0F, 0F, -0.7132579F);
        
        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(0F, -3F, -2F, 9, 6, 4);
        Body.setRotationPoint(-8F, 9F, 0F);
        
        BackUp = new ModelRenderer(this, 26, 0);
        BackUp.addBox(0F, 0F, -1.5F, 8, 3, 3);
        BackUp.setRotationPoint(1F, 6F, 0F);
        setRotation(BackUp, 0F, 0F, 0.1858931F);
        
        BackDown = new ModelRenderer(this, 26, 6);
        BackDown.addBox(0F, -3F, -1.5F, 8, 3, 3);
        BackDown.setRotationPoint(1F, 12F, 0F);
        setRotation(BackDown, 0F, 0F, -0.1919862F);
        
        Tail = new ModelRenderer(this, 48, 0);
        Tail.addBox(0F, -1.5F, -1F, 4, 3, 2);
        Tail.setRotationPoint(8F, 9F, 0F);
        
        TailFin = new ModelRenderer(this, 48, 5);
        TailFin.addBox(3F, -5.3F, 0F, 5, 11, 0);
        TailFin.setRotationPoint(8F, 9F, 0F);

        RightPectoralFin = new ModelRenderer(this, 28, 12);
        RightPectoralFin.addBox(0F, -2F, 0F, 5, 4, 0);
        RightPectoralFin.setRotationPoint(-6.5F, 10F, 2F);
        setRotation(RightPectoralFin, 0F, -0.8726646F, 0.185895F);
        
        LeftPectoralFin = new ModelRenderer(this, 38, 12);
        LeftPectoralFin.addBox(0F, -2F, 0F, 5, 4, 0);
        LeftPectoralFin.setRotationPoint(-6.5F, 10F, -2F);
        setRotation(LeftPectoralFin, 0F, 0.8726646F, 0.1858931F);
        
        UpperFin = new ModelRenderer(this, 0, 22);
        UpperFin.addBox(0F, -4F, 0F, 15, 4, 0);
        UpperFin.setRotationPoint(-7F, 6F, 0F);
        setRotation(UpperFin, 0F, 0F, 0.1047198F);
        
        LowerFin = new ModelRenderer(this, 46, 20);
        LowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        LowerFin.setRotationPoint(0F, 12F, 0F);
        setRotation(LowerFin, 0F, 0F, -0.1858931F);
        
        RightLowerFin = new ModelRenderer(this, 28, 16);
        RightLowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        RightLowerFin.setRotationPoint(-7F, 12F, 1F);
        setRotation(RightLowerFin, 0.5235988F, 0F, 0F);
        
        LeftLowerFin = new ModelRenderer(this, 46, 16);
        LeftLowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        LeftLowerFin.setRotationPoint(-7F, 12F, -1F);
        setRotation(LeftLowerFin, -0.5235988F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Head.render(f5);
        LowerHead.render(f5);
        Nose.render(f5);
        MouthBottom.render(f5);
        MouthBottomB.render(f5);
        Body.render(f5);
        BackUp.render(f5);
        BackDown.render(f5);
        Tail.render(f5);
        TailFin.render(f5);
        RightPectoralFin.render(f5);
        LeftPectoralFin.render(f5);
        UpperFin.render(f5);
        LowerFin.render(f5);
        RightLowerFin.render(f5);
        LeftLowerFin.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        //TailA.rotateAngleY = MathHelper.cos(f2 * 0.7F);
        float tailMov = MathHelper.cos(f * 0.6662F) * f1 * 0.6F;
        float finMov = MathHelper.cos(f2 * 0.2F) * 0.4F;
        float mouthMov = MathHelper.cos(f2 * 0.3F) * 0.2F;
        
        Tail.rotateAngleY = tailMov;
        TailFin.rotateAngleY = tailMov;
        
        LeftPectoralFin.rotateAngleY = 0.8726646F + finMov;
        RightPectoralFin.rotateAngleY = -0.8726646F - finMov;
        
        MouthBottom.rotateAngleZ = 0.3346075F + mouthMov;
        MouthBottomB.rotateAngleZ = -0.7132579F + mouthMov;
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }

}
