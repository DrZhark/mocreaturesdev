package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

    public MoCModelDolphin()
    {
        Body = new ModelRenderer(this, 4, 6);
        Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
        Body.setRotationPoint(-4F, 17F, -10F);
        UHead = new ModelRenderer(this, 0, 0);
        UHead.addBox(0.0F, 0.0F, 0.0F, 5, 7, 8, 0.0F);
        UHead.setRotationPoint(-3.5F, 18F, -16.5F);
        DHead = new ModelRenderer(this, 50, 0);
        DHead.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4, 0.0F);
        DHead.setRotationPoint(-2.5F, 21.5F, -20.5F);
        PTail = new ModelRenderer(this, 34, 9);
        PTail.addBox(0.0F, 0.0F, 0.0F, 5, 5, 10, 0.0F);
        PTail.setRotationPoint(-3.5F, 19F, 8F);
        UpperFin = new ModelRenderer(this, 4, 12);
        UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        UpperFin.setRotationPoint(-1.5F, 18F, -4F);
        UpperFin.rotateAngleX = 0.7853981F;
        LTailFin = new ModelRenderer(this, 34, 0);
        LTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        LTailFin.setRotationPoint(-2F, 21.5F, 18F);
        LTailFin.rotateAngleY = 0.7853981F;
        RTailFin = new ModelRenderer(this, 34, 0);
        RTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        RTailFin.setRotationPoint(-3F, 21.5F, 15F);
        RTailFin.rotateAngleY = -0.7853981F;
        LeftFin = new ModelRenderer(this, 14, 0);
        LeftFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        LeftFin.setRotationPoint(2.0F, 24F, -7F);
        LeftFin.rotateAngleY = -0.5235988F;
        LeftFin.rotateAngleZ = 0.5235988F;
        RightFin = new ModelRenderer(this, 14, 0);
        RightFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        RightFin.setRotationPoint(-10F, 27.5F, -3F);
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
        UpperFin.render(f5);
        LTailFin.render(f5);
        RTailFin.render(f5);
        LeftFin.render(f5);
        RightFin.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        RTailFin.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
        LTailFin.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
    }
}
