package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

    public MoCModelShark()
    {
        Body = new ModelRenderer(this, 6, 6);
        Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
        Body.setRotationPoint(-4F, 17F, -10F);
        UHead = new ModelRenderer(this, 0, 0);
        UHead.addBox(0.0F, 0.0F, 0.0F, 5, 2, 8, 0.0F);
        UHead.setRotationPoint(-3.5F, 21F, -16.5F);
        UHead.rotateAngleX = 0.5235988F;
        DHead = new ModelRenderer(this, 44, 0);
        DHead.addBox(0.0F, 0.0F, 0.0F, 5, 2, 5, 0.0F);
        DHead.setRotationPoint(-3.5F, 21.5F, -13.5F);
        DHead.rotateAngleX = -0.261799F;
        RHead = new ModelRenderer(this, 0, 3);
        RHead.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        RHead.setRotationPoint(-3.45F, 21.3F, -13.85F);
        RHead.rotateAngleX = 0.7853981F;
        LHead = new ModelRenderer(this, 0, 3);
        LHead.addBox(0.0F, 0.0F, 0.0F, 1, 6, 6, 0.0F);
        LHead.setRotationPoint(0.45F, 21.3F, -13.8F);
        LHead.rotateAngleX = 0.7853981F;
        PTail = new ModelRenderer(this, 36, 8);
        PTail.addBox(0.0F, 0.0F, 0.0F, 4, 6, 10, 0.0F);
        PTail.setRotationPoint(-3F, 18F, 8F);
        UpperFin = new ModelRenderer(this, 6, 12);
        UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        UpperFin.setRotationPoint(-1.5F, 17F, -1F);
        UpperFin.rotateAngleX = 0.7853981F;
        UpperTailFin = new ModelRenderer(this, 6, 12);
        UpperTailFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        UpperTailFin.setRotationPoint(-1.5F, 18F, 16F);
        UpperTailFin.rotateAngleX = 0.5235988F;
        LowerTailFin = new ModelRenderer(this, 8, 14);
        LowerTailFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 6, 0.0F);
        LowerTailFin.setRotationPoint(-1.5F, 21F, 18F);
        LowerTailFin.rotateAngleX = -0.7853981F;
        LeftFin = new ModelRenderer(this, 18, 0);
        LeftFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        LeftFin.setRotationPoint(2.0F, 24F, -5F);
        LeftFin.rotateAngleY = -0.5235988F;
        LeftFin.rotateAngleZ = 0.5235988F;
        RightFin = new ModelRenderer(this, 18, 0);
        RightFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        RightFin.setRotationPoint(-10F, 27.5F, -1F);
        RightFin.rotateAngleY = 0.5235988F;
        RightFin.rotateAngleZ = -0.5235988F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Body.render(f5);
        PTail.render(f5);
        UHead.render(f5);
        DHead.render(f5);
        RHead.render(f5);
        LHead.render(f5);
        UpperFin.render(f5);
        UpperTailFin.render(f5);
        LowerTailFin.render(f5);
        LeftFin.render(f5);
        RightFin.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        UpperTailFin.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
        LowerTailFin.rotateAngleY = MathHelper.cos(f * 0.6662F) * f1;
    }
}
