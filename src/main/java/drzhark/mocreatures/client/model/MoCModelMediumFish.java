package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MoCModelMediumFish extends ModelBase {

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

    public MoCModelMediumFish() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 10);
        this.Head.addBox(-5F, 0F, -1.5F, 5, 3, 3);
        this.Head.setRotationPoint(-8F, 6F, 0F);
        setRotation(this.Head, 0F, 0F, -0.4461433F);

        this.LowerHead = new ModelRenderer(this, 0, 16);
        this.LowerHead.addBox(-4F, -3F, -1.5F, 4, 3, 3);
        this.LowerHead.setRotationPoint(-8F, 12F, 0F);
        setRotation(this.LowerHead, 0F, 0F, 0.3346075F);

        this.Nose = new ModelRenderer(this, 14, 17);
        this.Nose.addBox(-1F, -1F, -1F, 1, 3, 2);
        this.Nose.setRotationPoint(-11F, 8.2F, 0F);
        setRotation(this.Nose, 0F, 0F, 1.412787F);

        this.MouthBottom = new ModelRenderer(this, 16, 10);
        this.MouthBottom.addBox(-2F, -0.4F, -1F, 2, 1, 2);
        this.MouthBottom.setRotationPoint(-11.5F, 10F, 0F);
        setRotation(this.MouthBottom, 0F, 0F, 0.3346075F);

        this.MouthBottomB = new ModelRenderer(this, 16, 13);
        this.MouthBottomB.addBox(-1.5F, -2.4F, -0.5F, 1, 1, 1);
        this.MouthBottomB.setRotationPoint(-11.5F, 10F, 0F);
        setRotation(this.MouthBottomB, 0F, 0F, -0.7132579F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0F, -3F, -2F, 9, 6, 4);
        this.Body.setRotationPoint(-8F, 9F, 0F);

        this.BackUp = new ModelRenderer(this, 26, 0);
        this.BackUp.addBox(0F, 0F, -1.5F, 8, 3, 3);
        this.BackUp.setRotationPoint(1F, 6F, 0F);
        setRotation(this.BackUp, 0F, 0F, 0.1858931F);

        this.BackDown = new ModelRenderer(this, 26, 6);
        this.BackDown.addBox(0F, -3F, -1.5F, 8, 3, 3);
        this.BackDown.setRotationPoint(1F, 12F, 0F);
        setRotation(this.BackDown, 0F, 0F, -0.1919862F);

        this.Tail = new ModelRenderer(this, 48, 0);
        this.Tail.addBox(0F, -1.5F, -1F, 4, 3, 2);
        this.Tail.setRotationPoint(8F, 9F, 0F);

        this.TailFin = new ModelRenderer(this, 48, 5);
        this.TailFin.addBox(3F, -5.3F, 0F, 5, 11, 0);
        this.TailFin.setRotationPoint(8F, 9F, 0F);

        this.RightPectoralFin = new ModelRenderer(this, 28, 12);
        this.RightPectoralFin.addBox(0F, -2F, 0F, 5, 4, 0);
        this.RightPectoralFin.setRotationPoint(-6.5F, 10F, 2F);
        setRotation(this.RightPectoralFin, 0F, -0.8726646F, 0.185895F);

        this.LeftPectoralFin = new ModelRenderer(this, 38, 12);
        this.LeftPectoralFin.addBox(0F, -2F, 0F, 5, 4, 0);
        this.LeftPectoralFin.setRotationPoint(-6.5F, 10F, -2F);
        setRotation(this.LeftPectoralFin, 0F, 0.8726646F, 0.1858931F);

        this.UpperFin = new ModelRenderer(this, 0, 22);
        this.UpperFin.addBox(0F, -4F, 0F, 15, 4, 0);
        this.UpperFin.setRotationPoint(-7F, 6F, 0F);
        setRotation(this.UpperFin, 0F, 0F, 0.1047198F);

        this.LowerFin = new ModelRenderer(this, 46, 20);
        this.LowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.LowerFin.setRotationPoint(0F, 12F, 0F);
        setRotation(this.LowerFin, 0F, 0F, -0.1858931F);

        this.RightLowerFin = new ModelRenderer(this, 28, 16);
        this.RightLowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.RightLowerFin.setRotationPoint(-7F, 12F, 1F);
        setRotation(this.RightLowerFin, 0.5235988F, 0F, 0F);

        this.LeftLowerFin = new ModelRenderer(this, 46, 16);
        this.LeftLowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.LeftLowerFin.setRotationPoint(-7F, 12F, -1F);
        setRotation(this.LeftLowerFin, -0.5235988F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Head.render(f5);
        this.LowerHead.render(f5);
        this.Nose.render(f5);
        this.MouthBottom.render(f5);
        this.MouthBottomB.render(f5);
        this.Body.render(f5);
        this.BackUp.render(f5);
        this.BackDown.render(f5);
        this.Tail.render(f5);
        this.TailFin.render(f5);
        this.RightPectoralFin.render(f5);
        this.LeftPectoralFin.render(f5);
        this.UpperFin.render(f5);
        this.LowerFin.render(f5);
        this.RightLowerFin.render(f5);
        this.LeftLowerFin.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        //TailA.rotateAngleY = MathHelper.cos(f2 * 0.7F);
        float tailMov = MathHelper.cos(f * 0.6662F) * f1 * 0.6F;
        float finMov = MathHelper.cos(f2 * 0.2F) * 0.4F;
        float mouthMov = MathHelper.cos(f2 * 0.3F) * 0.2F;

        this.Tail.rotateAngleY = tailMov;
        this.TailFin.rotateAngleY = tailMov;

        this.LeftPectoralFin.rotateAngleY = 0.8726646F + finMov;
        this.RightPectoralFin.rotateAngleY = -0.8726646F - finMov;

        this.MouthBottom.rotateAngleZ = 0.3346075F + mouthMov;
        this.MouthBottomB.rotateAngleZ = -0.7132579F + mouthMov;
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }

}
