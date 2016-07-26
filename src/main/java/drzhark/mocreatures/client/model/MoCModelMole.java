package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityMole;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MoCModelMole extends ModelBase {

    ModelRenderer Nose;
    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Back;
    ModelRenderer Tail;
    ModelRenderer LLeg;
    ModelRenderer LFingers;
    ModelRenderer RLeg;
    ModelRenderer RFingers;
    ModelRenderer LRearLeg;
    ModelRenderer RRearLeg;

    public MoCModelMole() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Nose = new ModelRenderer(this, 0, 25);
        this.Nose.addBox(-1F, 0F, -4F, 2, 2, 3);
        this.Nose.setRotationPoint(0F, 20F, -6F);
        setRotation(this.Nose, 0.2617994F, 0F, 0F);

        this.Head = new ModelRenderer(this, 0, 18);
        this.Head.addBox(-3F, -2F, -2F, 6, 4, 3);
        this.Head.setRotationPoint(0F, 20F, -6F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-5F, 0F, 0F, 10, 6, 10);
        this.Body.setRotationPoint(0F, 17F, -6F);

        this.Back = new ModelRenderer(this, 18, 16);
        this.Back.addBox(-4F, -3F, 0F, 8, 5, 4);
        this.Back.setRotationPoint(0F, 21F, 4F);

        this.Tail = new ModelRenderer(this, 52, 8);
        this.Tail.addBox(-0.5F, 0F, 1F, 1, 1, 5);
        this.Tail.setRotationPoint(0F, 21F, 6F);
        setRotation(this.Tail, -0.3490659F, 0F, 0F);

        this.LLeg = new ModelRenderer(this, 10, 25);
        this.LLeg.addBox(0F, -2F, -1F, 6, 4, 2);
        this.LLeg.setRotationPoint(4F, 21F, -4F);
        setRotation(this.LLeg, 0F, 0F, 0.2268928F);

        this.LFingers = new ModelRenderer(this, 44, 8);
        this.LFingers.addBox(5F, -2F, 1F, 1, 4, 1);
        this.LFingers.setRotationPoint(4F, 21F, -4F);
        setRotation(this.LFingers, 0F, 0F, 0.2268928F);

        this.RLeg = new ModelRenderer(this, 26, 25);
        this.RLeg.addBox(-6F, -2F, -1F, 6, 4, 2);
        this.RLeg.setRotationPoint(-4F, 21F, -4F);
        setRotation(this.RLeg, 0F, 0F, -0.2268928F);

        this.RFingers = new ModelRenderer(this, 48, 8);
        this.RFingers.addBox(-6F, -2F, 1F, 1, 4, 1);
        this.RFingers.setRotationPoint(-4F, 21F, -4F);
        setRotation(this.RFingers, 0F, 0F, -0.2268928F);

        this.LRearLeg = new ModelRenderer(this, 36, 0);
        this.LRearLeg.addBox(0F, -2F, -1F, 2, 3, 5);
        this.LRearLeg.setRotationPoint(3F, 22F, 5F);
        setRotation(this.LRearLeg, -0.2792527F, 0.5235988F, 0F);

        this.RRearLeg = new ModelRenderer(this, 50, 0);
        this.RRearLeg.addBox(-2F, -2F, -1F, 2, 3, 5);
        this.RRearLeg.setRotationPoint(-3F, 22F, 5F);
        setRotation(this.RRearLeg, -0.2792527F, -0.5235988F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        MoCEntityMole mole = (MoCEntityMole) entity;
        float yOffset = mole.getAdjustedYOffset();
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, yOffset, 0F);
        this.Nose.render(f5);
        this.Head.render(f5);
        this.Body.render(f5);
        this.Back.render(f5);
        this.Tail.render(f5);
        this.LLeg.render(f5);
        this.LFingers.render(f5);
        this.RLeg.render(f5);
        this.RFingers.render(f5);
        this.LRearLeg.render(f5);
        this.RRearLeg.render(f5);
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);

        this.Head.rotateAngleY = f3 / 57.29578F;
        this.Head.rotateAngleX = f4 / 57.29578F;
        this.Nose.rotateAngleX = 0.2617994F + this.Head.rotateAngleX;
        this.Nose.rotateAngleY = this.Head.rotateAngleY;

        float RLegXRot = MathHelper.cos((f * 1.0F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 1.0F) * 0.8F * f1;

        this.RLeg.rotateAngleY = RLegXRot;
        this.RFingers.rotateAngleY = this.RLeg.rotateAngleY;
        this.LLeg.rotateAngleY = LLegXRot;
        this.LFingers.rotateAngleY = this.LLeg.rotateAngleY;
        this.RRearLeg.rotateAngleY = -0.5235988F + LLegXRot;
        this.LRearLeg.rotateAngleY = 0.5235988F + RLegXRot;

        this.Tail.rotateAngleZ = this.LLeg.rotateAngleX * 0.625F;
    }

}
