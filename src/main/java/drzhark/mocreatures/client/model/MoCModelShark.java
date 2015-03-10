package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelShark extends ModelBase {

    public ModelRenderer LHead;

    public ModelRenderer RHead;

    public ModelRenderer UHead;

    public ModelRenderer DHead;

    public ModelRenderer RTail;

    public ModelRenderer LTail;

    public ModelRenderer PTail;

    public ModelRenderer Body;

    public ModelRenderer UpperFin;

    public ModelRenderer UpperTailFin;

    public ModelRenderer LowerTailFin;

    public ModelRenderer RightFin;

    public ModelRenderer LeftFin;

    public MoCModelShark() {
        this.Body = new ModelRenderer(this, 6, 6);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
        this.Body.setRotationPoint(-4F, 17F, -10F);
        this.UHead = new ModelRenderer(this, 0, 0);
        this.UHead.addBox(0.0F, 0.0F, 0.0F, 5, 2, 8, 0.0F);
        this.UHead.setRotationPoint(-3.5F, 21F, -16.5F);
        this.UHead.rotateAngleX = 0.5235988F;
        this.DHead = new ModelRenderer(this, 44, 0);
        this.DHead.addBox(0.0F, 0.0F, 0.0F, 5, 2, 5, 0.0F);
        this.DHead.setRotationPoint(-3.5F, 21.5F, -13.5F);
        this.DHead.rotateAngleX = -0.261799F;
        this.RHead = new ModelRenderer(this, 0, 3);
        this.RHead.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        this.RHead.setRotationPoint(-3.45F, 21.3F, -13.85F);
        this.RHead.rotateAngleX = 0.7853981F;
        this.LHead = new ModelRenderer(this, 0, 3);
        this.LHead.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        this.LHead.setRotationPoint(0.45F, 21.3F, -13.8F);
        this.LHead.rotateAngleX = 0.7853981F;
        this.PTail = new ModelRenderer(this, 36, 8);
        this.PTail.addBox(0.0F, 0.0F, 0.0F, 4, 6, 10, 0.0F);
        this.PTail.setRotationPoint(-3F, 18F, 8F);
        this.UpperFin = new ModelRenderer(this, 6, 12);
        this.UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        this.UpperFin.setRotationPoint(-1.5F, 17F, -1F);
        this.UpperFin.rotateAngleX = 0.7853981F;
        this.UpperTailFin = new ModelRenderer(this, 6, 12);
        this.UpperTailFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        this.UpperTailFin.setRotationPoint(-1.5F, 18F, 16F);
        this.UpperTailFin.rotateAngleX = 0.5235988F;
        this.LowerTailFin = new ModelRenderer(this, 8, 14);
        this.LowerTailFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 6, 0.0F);
        this.LowerTailFin.setRotationPoint(-1.5F, 21F, 18F);
        this.LowerTailFin.rotateAngleX = -0.7853981F;
        this.LeftFin = new ModelRenderer(this, 18, 0);
        this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.LeftFin.setRotationPoint(2.0F, 24F, -5F);
        this.LeftFin.rotateAngleY = -0.5235988F;
        this.LeftFin.rotateAngleZ = 0.5235988F;
        this.RightFin = new ModelRenderer(this, 18, 0);
        this.RightFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.RightFin.setRotationPoint(-10F, 27.5F, -1F);
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
        this.RHead.render(f5);
        this.LHead.render(f5);
        this.UpperFin.render(f5);
        this.UpperTailFin.render(f5);
        this.LowerTailFin.render(f5);
        this.LeftFin.render(f5);
        this.RightFin.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.UpperTailFin.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
        this.LowerTailFin.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
    }
}
