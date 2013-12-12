package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;


public class MoCModelAnt extends ModelBase
{
    ModelRenderer Head;
    ModelRenderer Mouth;
    ModelRenderer RightAntenna;
    ModelRenderer LeftAntenna;
    ModelRenderer Thorax;
    ModelRenderer Abdomen;
    ModelRenderer MidLegs;
    ModelRenderer FrontLegs;
    ModelRenderer RearLegs;

    public MoCModelAnt()
    {
        textureWidth = 32;
        textureHeight = 32;

        Head = new ModelRenderer(this, 0, 11);
        Head.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        Head.setRotationPoint(0F, 21.9F, -1.3F);
        setRotation(Head, -2.171231F, 0F, 0F);

        Mouth = new ModelRenderer(this, 8, 10);
        Mouth.addBox(0F, 0F, 0F, 2, 1, 0);
        Mouth.setRotationPoint(-1F, 22.3F, -1.9F);
        setRotation(Mouth, -0.8286699F, 0F, 0F);

        RightAntenna = new ModelRenderer(this, 0, 6);
        RightAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        RightAntenna.setRotationPoint(-0.5F, 21.7F, -2.3F);
        setRotation(RightAntenna, -1.041001F, 0.7853982F, 0F);

        LeftAntenna = new ModelRenderer(this, 4, 6);
        LeftAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        LeftAntenna.setRotationPoint(0.5F, 21.7F, -2.3F);
        setRotation(LeftAntenna, -1.041001F, -0.7853982F, 0F);

        Thorax = new ModelRenderer(this, 0, 0);
        Thorax.addBox(-0.5F, 1.5F, -1F, 1, 1, 2);
        Thorax.setRotationPoint(0F, 20F, -0.5F);

        Abdomen = new ModelRenderer(this, 8, 1);
        Abdomen.addBox(-0.5F, -0.2F, -1F, 1, 2, 1);
        Abdomen.setRotationPoint(0F, 21.5F, 0.3F);
        setRotation(Abdomen, 1.706911F, 0F, 0F);

        MidLegs = new ModelRenderer(this, 4, 8);
        MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        MidLegs.setRotationPoint(0F, 22F, -0.7F);
        setRotation(MidLegs, 0.5948578F, 0F, 0F);

        FrontLegs = new ModelRenderer(this, 0, 8);
        FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        FrontLegs.setRotationPoint(0F, 22F, -0.8F);
        setRotation(FrontLegs, -0.6192304F, 0F, 0F);

        RearLegs = new ModelRenderer(this, 0, 8);
        RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        RearLegs.setRotationPoint(0F, 22F, 0F);
        setRotation(RearLegs, 0.9136644F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Head.render(f5);
        Mouth.render(f5);
        RightAntenna.render(f5);
        LeftAntenna.render(f5);
        Thorax.render(f5);
        Abdomen.render(f5);
        MidLegs.render(f5);
        FrontLegs.render(f5);
        RearLegs.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        float legMov = MathHelper.cos((f ) + 3.141593F)  * f1;
        float legMovB = MathHelper.cos(f )  * f1;
        FrontLegs.rotateAngleX = -0.6192304F + legMov;
        MidLegs.rotateAngleX = 0.5948578F + legMovB;
        RearLegs.rotateAngleX =  0.9136644F + legMov;
    }

}
