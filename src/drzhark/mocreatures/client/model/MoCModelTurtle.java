package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;

@SideOnly(Side.CLIENT)
public class MoCModelTurtle extends ModelBase {
    public MoCModelTurtle()
    {
        Shell = new ModelRenderer(this, 28, 0);
        Shell.addBox(0F, 0F, 0F, 9, 1, 9);
        Shell.setRotationPoint(-4.5F, 19F, -4.5F);

        ShellUp = new ModelRenderer(this, 0, 22);
        ShellUp.addBox(0F, 0F, 0F, 8, 2, 8);
        ShellUp.setRotationPoint(-4F, 17F, -4F);

        ShellTop = new ModelRenderer(this, 40, 10);
        ShellTop.addBox(0F, 0F, 0F, 6, 1, 6);
        ShellTop.setRotationPoint(-3F, 16F, -3F);

        Belly = new ModelRenderer(this, 0, 12);
        Belly.addBox(0F, 0F, 0F, 8, 1, 8);
        Belly.setRotationPoint(-4F, 20F, -4F);

        Leg1 = new ModelRenderer(this, 0, 0);
        Leg1.addBox(-1F, 0F, -1F, 2, 3, 2);
        Leg1.setRotationPoint(3.5F, 20F, -3.5F);

        Leg2 = new ModelRenderer(this, 0, 9);
        Leg2.addBox(-1F, 0F, -1F, 2, 3, 2);
        Leg2.setRotationPoint(-3.5F, 20F, -3.5F);

        Leg3 = new ModelRenderer(this, 0, 0);
        Leg3.addBox(-1F, 0F, -1F, 2, 3, 2);
        Leg3.setRotationPoint(3.5F, 20F, 3.5F);

        Leg4 = new ModelRenderer(this, 0, 9);
        Leg4.addBox(-1F, 0F, -1F, 2, 3, 2);
        Leg4.setRotationPoint(-3.5F, 20F, 3.5F);

        Head = new ModelRenderer(this, 10, 0);
        Head.addBox(-1.5F, -1F, -4F, 3, 2, 4);
        Head.setRotationPoint(0F, 20F, -4.5F);

        Tail = new ModelRenderer(this, 0, 5);
        Tail.addBox(-1F, -1F, 0F, 2, 1, 3);
        Tail.setRotationPoint(0F, 21F, 4F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityTurtle entityturtle = (MoCEntityTurtle) entity;
        boolean TMNT = entityturtle.isTMNT();
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Shell.render(f5);
        ShellUp.render(f5);
        if (!TMNT)
        {
            ShellTop.render(f5);
        }
        Belly.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg3.render(f5);
        Leg4.render(f5);
        Head.render(f5);
        Tail.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);

        Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        Leg2.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        Leg3.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        Tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 0.7F * f1;

        if (upsidedown)
        {
            float f25 = swingProgress;
            float f26 = f25;
            if (f25 >= 0.8F)
            {
                f26 = (0.6F - (f25 - 0.8F));
            }
            if (f26 > 1.6F)
            {
                f26 = 1.6F;
            }
            if (f26 < -1.6F)
            {
                f26 = -1.6F;
            }
            Leg1.rotateAngleX = 0F - f26;
            Leg2.rotateAngleX = 0F + f26;
            Leg3.rotateAngleX = 0F + f26;
            Leg4.rotateAngleX = 0F - f26;
            Tail.rotateAngleY = 0F - f26;
        }
        else
        {
            Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
            Leg2.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
            Leg3.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
            Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
            Tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 0.7F * f1;
        }

        if (isHiding)
        {
            Head.rotateAngleX = 0F;
            Head.rotateAngleY = 0F;

            //Leg1.setRotationPoint(2.9F, 18.5F, -2.9F);
            Leg1.rotationPointX = 2.9F;
            Leg1.rotationPointY = 18.5F;
            Leg1.rotationPointZ = -2.9F;

            //Leg2.setRotationPoint(-2.9F, 18.5F, -2.9F);
            Leg2.rotationPointX = -2.9F;
            Leg2.rotationPointY = 18.5F;
            Leg2.rotationPointZ = -2.9F;

            //Leg3.setRotationPoint(2.9F, 18.5F, 2.9F);
            Leg3.rotationPointX = 2.9F;
            Leg3.rotationPointY = 18.5F;
            Leg3.rotationPointZ = 2.9F;

            //Leg4.setRotationPoint(-2.9F, 18.5F, 2.9F);
            Leg4.rotationPointX = -2.9F;
            Leg4.rotationPointY = 18.5F;
            Leg4.rotationPointZ = 2.9F;

            //Head.setRotationPoint(0F, 20F, -1F);
            //Head.rotationPointX = 0F;
            Head.rotationPointY = 19.5F;
            Head.rotationPointZ = -1F;

            //Tail.setRotationPoint(0F, 21F, 2F);
            //Tail.rotationPointX = 0F;
            //Tail.rotationPointY = 21F;
            Tail.rotationPointZ = 2F;
        }
        else
        {
            Head.rotateAngleX = f4 / 57.29578F;
            Head.rotateAngleY = f3 / 57.29578F;

            //Leg1.setRotationPoint(3.5F, 20F, -3.5F);
            Leg1.rotationPointX = 3.5F;
            Leg1.rotationPointY = 20F;
            Leg1.rotationPointZ = -3.5F;

            //Leg2.setRotationPoint(-3.5F, 20F, -3.5F);
            Leg2.rotationPointX = -3.5F;
            Leg2.rotationPointY = 20F;
            Leg2.rotationPointZ = -3.5F;

            //Leg3.setRotationPoint(3.5F, 20F, 3.5F);
            Leg3.rotationPointX = 3.5F;
            Leg3.rotationPointY = 20F;
            Leg3.rotationPointZ = 3.5F;

            //Leg4.setRotationPoint(-3.5F, 20F, 3.5F);
            Leg4.rotationPointX = -3.5F;
            Leg4.rotationPointY = 20F;
            Leg4.rotationPointZ = 3.5F;

            //Head.setRotationPoint(0F, 20F, -4.5F);
            //Head.rotationPointX = 0F;
            Head.rotationPointY = 20F;
            Head.rotationPointZ = -4.5F;

            //Tail.setRotationPoint(0F, 21F, 4F);
            //Tail.rotationPointX = 0F;
            //Tail.rotationPointY = 21F;
            Tail.rotationPointZ = 4F;
        }
    }

    ModelRenderer Shell;
    ModelRenderer ShellUp;
    ModelRenderer ShellTop;
    ModelRenderer Belly;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Head;
    ModelRenderer Tail;
    public boolean isHiding;
    public boolean upsidedown;
    public float swingProgress;

}
