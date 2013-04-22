package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelDeer extends ModelBase {

    public MoCModelDeer()
    {
        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-1.5F, -6F, -9.5F, 3, 3, 6, 0.0F);
        Head.setRotationPoint(1.0F, 11.5F, -4.5F);
        Neck = new ModelRenderer(this, 0, 9);
        Neck.addBox(-2F, -2F, -6F, 4, 4, 6, 0.0F);
        Neck.setRotationPoint(1.0F, 11.5F, -4.5F);
        Neck.rotateAngleX = -0.7853981F;
        LEar = new ModelRenderer(this, 0, 0);
        LEar.addBox(-4F, -7.5F, -5F, 2, 3, 1, 0.0F);
        LEar.setRotationPoint(1.0F, 11.5F, -4.5F);
        LEar.rotateAngleZ = 0.7853981F;
        REar = new ModelRenderer(this, 0, 0);
        REar.addBox(2.0F, -7.5F, -5F, 2, 3, 1, 0.0F);
        REar.setRotationPoint(1.0F, 11.5F, -4.5F);
        REar.rotateAngleZ = -0.7853981F;
        LeftAntler = new ModelRenderer(this, 54, 0);
        LeftAntler.addBox(0.0F, -14F, -7F, 1, 8, 4, 0.0F);
        LeftAntler.setRotationPoint(1.0F, 11.5F, -4.5F);
        LeftAntler.rotateAngleZ = 0.2094395F;
        RightAntler = new ModelRenderer(this, 54, 0);
        RightAntler.addBox(0.0F, -14F, -7F, 1, 8, 4, 0.0F);
        RightAntler.setRotationPoint(1.0F, 11.5F, -4.5F);
        RightAntler.rotateAngleZ = -0.2094395F;
        Body = new ModelRenderer(this, 24, 12);
        Body.addBox(-2F, -3F, -6F, 6, 6, 14, 0.0F);
        Body.setRotationPoint(0.0F, 13F, 0.0F);
        Leg1 = new ModelRenderer(this, 9, 20);
        Leg1.addBox(-1F, 0.0F, -1F, 2, 8, 2, 0.0F);
        Leg1.setRotationPoint(3F, 16F, -4F);
        Leg2 = new ModelRenderer(this, 0, 20);
        Leg2.addBox(-1F, 0.0F, -1F, 2, 8, 2, 0.0F);
        Leg2.setRotationPoint(-1F, 16F, -4F);
        Leg3 = new ModelRenderer(this, 9, 20);
        Leg3.addBox(-1F, 0.0F, -1F, 2, 8, 2, 0.0F);
        Leg3.setRotationPoint(3F, 16F, 6F);
        Leg4 = new ModelRenderer(this, 0, 20);
        Leg4.addBox(-1F, 0.0F, -1F, 2, 8, 2, 0.0F);
        Leg4.setRotationPoint(-1F, 16F, 6F);
        Tail = new ModelRenderer(this, 50, 20);
        Tail.addBox(-1.5F, -1F, 0.0F, 3, 2, 4, 0.0F);
        Tail.setRotationPoint(1.0F, 11F, 7F);
        Tail.rotateAngleX = 0.7854F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Body.render(f5);
        Neck.render(f5);
        Head.render(f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg3.render(f5);
        Leg4.render(f5);
        Tail.render(f5);
        LEar.render(f5);
        REar.render(f5);
        LeftAntler.render(f5);
        RightAntler.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        Leg2.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        Leg3.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }

    public ModelRenderer Body;
    public ModelRenderer Neck;
    public ModelRenderer Head;
    public ModelRenderer Leg1;
    public ModelRenderer Leg2;
    public ModelRenderer Leg3;
    public ModelRenderer Leg4;
    public ModelRenderer Tail;
    public ModelRenderer LEar;
    public ModelRenderer REar;
    public ModelRenderer LeftAntler;
    public ModelRenderer RightAntler;

}
