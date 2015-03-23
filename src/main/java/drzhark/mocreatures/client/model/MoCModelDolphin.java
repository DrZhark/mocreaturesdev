package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelDolphin extends ModelBase {

    public ModelRenderer UHead;
    public ModelRenderer DHead;
    public ModelRenderer RTail;
    public ModelRenderer LTail;
    public ModelRenderer PTail;
    public ModelRenderer Body;
    public ModelRenderer UpperFin;
    public ModelRenderer RTailFin;
    public ModelRenderer LTailFin;
    public ModelRenderer LowerFin;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;

    public MoCModelDolphin() {
        this.Body = new ModelRenderer(this, 4, 6);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
        this.Body.setRotationPoint(-4F, 17F, -10F);
        this.UHead = new ModelRenderer(this, 0, 0);
        this.UHead.addBox(0.0F, 0.0F, 0.0F, 5, 7, 8, 0.0F);
        this.UHead.setRotationPoint(-3.5F, 18F, -16.5F);
        this.DHead = new ModelRenderer(this, 50, 0);
        this.DHead.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4, 0.0F);
        this.DHead.setRotationPoint(-2.5F, 21.5F, -20.5F);
        this.PTail = new ModelRenderer(this, 34, 9);
        this.PTail.addBox(0.0F, 0.0F, 0.0F, 5, 5, 10, 0.0F);
        this.PTail.setRotationPoint(-3.5F, 19F, 8F);
        this.UpperFin = new ModelRenderer(this, 4, 12);
        this.UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        this.UpperFin.setRotationPoint(-1.5F, 18F, -4F);
        this.UpperFin.rotateAngleX = 0.7853981F;
        this.LTailFin = new ModelRenderer(this, 34, 0);
        this.LTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        this.LTailFin.setRotationPoint(-2F, 21.5F, 18F);
        this.LTailFin.rotateAngleY = 0.7853981F;
        this.RTailFin = new ModelRenderer(this, 34, 0);
        this.RTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        this.RTailFin.setRotationPoint(-3F, 21.5F, 15F);
        this.RTailFin.rotateAngleY = -0.7853981F;
        this.LeftFin = new ModelRenderer(this, 14, 0);
        this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.LeftFin.setRotationPoint(2.0F, 24F, -7F);
        this.LeftFin.rotateAngleY = -0.5235988F;
        this.LeftFin.rotateAngleZ = 0.5235988F;
        this.RightFin = new ModelRenderer(this, 14, 0);
        this.RightFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.RightFin.setRotationPoint(-10F, 27.5F, -3F);
        this.RightFin.rotateAngleY = 0.5235988F;
        this.RightFin.rotateAngleZ = -0.5235988F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Body.render(f5);
        this.PTail.render(f5);
        this.UHead.render(f5);
        this.DHead.render(f5);
        this.UpperFin.render(f5);
        this.LTailFin.render(f5);
        this.RTailFin.render(f5);
        this.LeftFin.render(f5);
        this.RightFin.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.RTailFin.rotateAngleX = MathHelper.cos(f * 0.4F) * f1;
        this.LTailFin.rotateAngleX = MathHelper.cos(f * 0.4F) * f1;
    }
}
