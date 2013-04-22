package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelMouse extends ModelBase {

    public ModelRenderer Head;
    public ModelRenderer EarR;
    public ModelRenderer EarL;
    public ModelRenderer WhiskerR;
    public ModelRenderer WhiskerL;
    public ModelRenderer Tail;
    public ModelRenderer FrontL;
    public ModelRenderer FrontR;
    public ModelRenderer RearL;
    public ModelRenderer RearR;
    public ModelRenderer BodyF;

    public MoCModelMouse()
    {
        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-1.5F, -1F, -6F, 3, 4, 6, 0.0F);
        Head.setRotationPoint(0.0F, 19F, -9F);
        EarR = new ModelRenderer(this, 16, 26);
        EarR.addBox(-3.5F, -3F, -2F, 3, 3, 1, 0.0F);
        EarR.setRotationPoint(0.0F, 19F, -9F);
        EarL = new ModelRenderer(this, 24, 26);
        EarL.addBox(0.5F, -3F, -1F, 3, 3, 1, 0.0F);
        EarL.setRotationPoint(0.0F, 19F, -10F);
        WhiskerR = new ModelRenderer(this, 20, 20);
        WhiskerR.addBox(-4.5F, -1F, -7F, 3, 3, 1, 0.0F);
        WhiskerR.setRotationPoint(0.0F, 19F, -9F);
        WhiskerL = new ModelRenderer(this, 24, 20);
        WhiskerL.addBox(1.5F, -1F, -6F, 3, 3, 1, 0.0F);
        WhiskerL.setRotationPoint(0.0F, 19F, -9F);
        Tail = new ModelRenderer(this, 56, 0);
        Tail.addBox(-0.5F, 0.0F, -1F, 1, 14, 1, 0.0F);
        Tail.setRotationPoint(0.0F, 20F, 3F);
        Tail.rotateAngleX = 1.570796F;
        FrontL = new ModelRenderer(this, 0, 18);
        FrontL.addBox(-2F, 0.0F, -3F, 2, 1, 4, 0.0F);
        FrontL.setRotationPoint(3F, 23F, -7F);
        FrontR = new ModelRenderer(this, 0, 18);
        FrontR.addBox(0.0F, 0.0F, -3F, 2, 1, 4, 0.0F);
        FrontR.setRotationPoint(-3F, 23F, -7F);
        RearL = new ModelRenderer(this, 0, 18);
        RearL.addBox(-2F, 0.0F, -4F, 2, 1, 4, 0.0F);
        RearL.setRotationPoint(4F, 23F, 2.0F);
        RearR = new ModelRenderer(this, 0, 18);
        RearR.addBox(0.0F, 0.0F, -4F, 2, 1, 4, 0.0F);
        RearR.setRotationPoint(-4F, 23F, 2.0F);
        BodyF = new ModelRenderer(this, 20, 0);
        BodyF.addBox(-3F, -3F, -7F, 6, 6, 12, 0.0F);
        BodyF.setRotationPoint(0.0F, 20F, -2F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Head.render(f5);
        EarR.render(f5);
        EarL.render(f5);
        WhiskerR.render(f5);
        WhiskerL.render(f5);
        Tail.render(f5);
        FrontL.render(f5);
        FrontR.render(f5);
        RearL.render(f5);
        RearR.render(f5);
        BodyF.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        Head.rotateAngleX = -(f4 / 57.29578F);
        Head.rotateAngleY = f3 / 57.29578F;
        EarR.rotateAngleX = Head.rotateAngleX;
        EarR.rotateAngleY = Head.rotateAngleY;
        EarL.rotateAngleX = Head.rotateAngleX;
        EarL.rotateAngleY = Head.rotateAngleY;
        WhiskerR.rotateAngleX = Head.rotateAngleX;
        WhiskerR.rotateAngleY = Head.rotateAngleY;
        WhiskerL.rotateAngleX = Head.rotateAngleX;
        WhiskerL.rotateAngleY = Head.rotateAngleY;
        FrontL.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.6F * f1;
        RearL.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        RearR.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.6F * f1;
        FrontR.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        Tail.rotateAngleY = FrontL.rotateAngleX * 0.625F;
    }
}
