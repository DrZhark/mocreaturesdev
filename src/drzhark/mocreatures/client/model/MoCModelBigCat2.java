package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelBigCat2 extends ModelBase {

    ModelRenderer snout;
    ModelRenderer Tail;
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer ears;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer Collar;

    public boolean sitting;
    public boolean tamed;

    public MoCModelBigCat2()
    {
        ears = new ModelRenderer(this, 16, 25);
        ears.addBox(-4F, -7F, -3F, 8, 4, 1, 0.0F);
        ears.setRotationPoint(0.0F, 4F, -8F);
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4F, -4F, -6F, 8, 8, 6, 0.0F);
        head.setRotationPoint(0.0F, 4F, -8F);
        snout = new ModelRenderer(this, 14, 14);
        snout.addBox(-2F, 0.0F, -9F, 4, 4, 6, 0.0F);
        snout.setRotationPoint(0.0F, 4F, -8F);
        Collar = new ModelRenderer(this, 24, 0);
        Collar.addBox(-2.5F, 4F, -3F, 5, 4, 1, 0.0F);
        Collar.setRotationPoint(0.0F, 4F, -8F);
        body = new ModelRenderer(this, 28, 0);
        body.addBox(-5F, -10F, -7F, 10, 18, 8, 0.0F);
        body.setRotationPoint(0.0F, 5F, 2.0F);
        Tail = new ModelRenderer(this, 26, 15);
        Tail.addBox(-5F, -5F, -2F, 3, 3, 14, 0.0F);
        Tail.setRotationPoint(3.5F, 9.3F, 9F);
        Tail.rotateAngleX = -0.5235988F;
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        leg1.setRotationPoint(-3F, 12F, 7F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        leg2.setRotationPoint(3F, 12F, 7F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        leg3.setRotationPoint(-3F, 12F, -5F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-2F, 0.0F, -2F, 4, 12, 4, 0.0F);
        leg4.setRotationPoint(3F, 12F, -5F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        snout.render(f5);
        Tail.render(f5);
        ears.render(f5);
        head.render(f5);
        body.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
        if (tamed)
        {
            Collar.render(f5);
        }
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        head.rotateAngleX = f4 / 57.29578F;
        head.rotateAngleY = f3 / 57.29578F;
        leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        leg2.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        leg3.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        snout.rotateAngleY = head.rotateAngleY;
        snout.rotateAngleX = head.rotateAngleX;
        ears.rotateAngleY = head.rotateAngleY;
        ears.rotateAngleX = head.rotateAngleX;
        Collar.rotateAngleY = head.rotateAngleY;
        Collar.rotateAngleX = head.rotateAngleX;
        if (!sitting)
        {
            body.rotationPointX = 0.0F;
            body.rotationPointY = 5F;
            body.rotationPointZ = 2.0F;
            body.rotateAngleX = 1.570796F;
            leg1.rotationPointX = -3F;
            leg1.rotationPointZ = 7F;
            leg2.rotationPointX = 3F;
            leg2.rotationPointZ = 7F;
            leg3.rotationPointX = -3F;
            leg3.rotationPointZ = -5F;
            leg4.rotationPointX = 3F;
            leg4.rotationPointZ = -5F;
            Tail.rotationPointX = 3.5F;
            Tail.rotationPointY = 9.3F;
            Tail.rotationPointZ = 9F;
            Tail.rotateAngleX = -0.5235988F;
            Tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 0.7F * f1;
        }
        else
        {
            body.rotateAngleX = 0.8726646F;
            body.rotationPointX = 0.0F;
            body.rotationPointY = 12F;
            body.rotationPointZ = 1.0F;
            leg1.rotationPointX = -5F;
            leg1.rotationPointZ = 0.0F;
            leg2.rotationPointX = 5F;
            leg2.rotationPointZ = 0.0F;
            leg3.rotationPointX = -2F;
            leg3.rotationPointZ = -8F;
            leg4.rotationPointX = 2.0F;
            leg4.rotationPointZ = -8F;
            Tail.rotationPointX = 3.5F;
            Tail.rotationPointY = 22F;
            Tail.rotationPointZ = 8F;
            Tail.rotateAngleX = -0.1745329F;
        }
    }
}
