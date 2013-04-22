package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelTurkey extends ModelBase {

    ModelRenderer Beak;
    ModelRenderer Head;
    ModelRenderer Neck;
    ModelRenderer Chest;
    ModelRenderer RWing;
    ModelRenderer LWing;
    ModelRenderer UBody;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer RLeg;
    ModelRenderer RFoot;
    ModelRenderer LLeg;
    ModelRenderer LFoot;

    public MoCModelTurkey()
    {
        textureWidth = 64;
        textureHeight = 64;

        Beak = new ModelRenderer(this, 17, 17);
        Beak.addBox(-0.5F, -1.866667F, -3.366667F, 1, 1, 2);
        Beak.setRotationPoint(0F, 9.7F, -5.1F);
        setRotation(Beak, 0.7807508F, 0F, 0F);

        Head = new ModelRenderer(this, 0, 27);
        Head.addBox(-1F, -2F, -2F, 2, 2, 3);
        Head.setRotationPoint(0F, 9.7F, -5.1F);
        setRotation(Head, 0.4833219F, 0F, 0F);

        Neck = new ModelRenderer(this, 0, 32);
        Neck.addBox(-1F, -6F, -1F, 2, 6, 2);
        Neck.setRotationPoint(0F, 14.7F, -6.5F);
        setRotation(Neck, -0.2246208F, 0F, 0F);

        Chest = new ModelRenderer(this, 0, 17);
        Chest.addBox(-3F, 0F, -4F, 6, 6, 4);
        Chest.setRotationPoint(0F, 12.5F, -4F);
        setRotation(Chest, 0.5934119F, 0F, 0F);

        RWing = new ModelRenderer(this, 32, 30);
        RWing.addBox(-1F, -2F, 0F, 1, 6, 7);
        RWing.setRotationPoint(-4F, 14F, -3F);
        setRotation(RWing, -0.3346075F, 0F, 0F);

        LWing = new ModelRenderer(this, 48, 30);
        LWing.addBox(0F, -2F, 0F, 1, 6, 7);
        LWing.setRotationPoint(4F, 14F, -3F);
        setRotation(LWing, -0.3346075F, 0F, 0F);

        UBody = new ModelRenderer(this, 34, 0);
        UBody.addBox(-2.5F, -4F, 0F, 5, 7, 9);
        UBody.setRotationPoint(0F, 15F, -3F);

        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(-4F, -4F, 0F, 8, 8, 9);
        Body.setRotationPoint(0F, 16F, -4F);

        Tail = new ModelRenderer(this, 32, 17);
        Tail.addBox(-8F, -9F, 0F, 16, 12, 0);
        Tail.setRotationPoint(0F, 14F, 6F);
        setRotation(Tail, -0.2974289F, 0F, 0F);

        RLeg = new ModelRenderer(this, 27, 17);
        RLeg.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
        RLeg.setRotationPoint(-2F, 19F, 0.5F);

        RFoot = new ModelRenderer(this, 20, 23);
        RFoot.addBox(-1.5F, 5F, -2.5F, 3, 0, 3);
        RFoot.setRotationPoint(-2F, 19F, 0.5F);

        LLeg = new ModelRenderer(this, 23, 17);
        LLeg.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
        LLeg.setRotationPoint(2F, 19F, 0.5F);

        LFoot = new ModelRenderer(this, 20, 26);
        LFoot.addBox(-1.5F, 5F, -2.5F, 3, 0, 3);
        LFoot.setRotationPoint(2F, 19F, 0.5F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Beak.render(f5);
        Head.render(f5);
        Neck.render(f5);
        Chest.render(f5);
        RWing.render(f5);
        LWing.render(f5);
        UBody.render(f5);
        Body.render(f5);
        Tail.render(f5);
        RLeg.render(f5);
        RFoot.render(f5);
        LLeg.render(f5);
        LFoot.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {

        float LLegXRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        float wingF = (MathHelper.cos(f * 0.6662F) * 1.4F * f1) / 4F;

        Head.rotateAngleX = 0.4833219F + f4 / 57.29578F;//(RLegXRot/7F) + (-f4 / (180F / (float)Math.PI)) ;
        Head.rotateAngleY = f3 / (180F / (float) Math.PI);
        Beak.rotateAngleX = 0.2974F + Head.rotateAngleX;//0.7807508F - Head.rotateAngleX;
        Beak.rotateAngleY = Head.rotateAngleY;

        LLeg.rotateAngleX = LLegXRot;
        LFoot.rotateAngleX = LLeg.rotateAngleX;
        RLeg.rotateAngleX = RLegXRot;
        RFoot.rotateAngleX = RLeg.rotateAngleX;

        LWing.rotateAngleY = wingF;
        RWing.rotateAngleY = -wingF;

        Tail.rotateAngleX = -0.2974289F + wingF;
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }
}
