package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

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

    private boolean male;

    public MoCModelTurkey() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.Beak = new ModelRenderer(this, 17, 17);
        this.Beak.addBox(-0.5F, -1.866667F, -3.366667F, 1, 1, 2);
        this.Beak.setRotationPoint(0F, 9.7F, -5.1F);
        setRotation(this.Beak, 0.7807508F, 0F, 0F);

        this.Head = new ModelRenderer(this, 0, 27);
        this.Head.addBox(-1F, -2F, -2F, 2, 2, 3);
        this.Head.setRotationPoint(0F, 9.7F, -5.1F);
        setRotation(this.Head, 0.4833219F, 0F, 0F);

        this.Neck = new ModelRenderer(this, 0, 32);
        this.Neck.addBox(-1F, -6F, -1F, 2, 6, 2);
        this.Neck.setRotationPoint(0F, 14.7F, -6.5F);
        setRotation(this.Neck, -0.2246208F, 0F, 0F);

        this.Chest = new ModelRenderer(this, 0, 17);
        this.Chest.addBox(-3F, 0F, -4F, 6, 6, 4);
        this.Chest.setRotationPoint(0F, 12.5F, -4F);
        setRotation(this.Chest, 0.5934119F, 0F, 0F);

        this.RWing = new ModelRenderer(this, 32, 30);
        this.RWing.addBox(-1F, -2F, 0F, 1, 6, 7);
        this.RWing.setRotationPoint(-4F, 14F, -3F);
        setRotation(this.RWing, -0.3346075F, 0F, 0F);

        this.LWing = new ModelRenderer(this, 48, 30);
        this.LWing.addBox(0F, -2F, 0F, 1, 6, 7);
        this.LWing.setRotationPoint(4F, 14F, -3F);
        setRotation(this.LWing, -0.3346075F, 0F, 0F);

        this.UBody = new ModelRenderer(this, 34, 0);
        this.UBody.addBox(-2.5F, -4F, 0F, 5, 7, 9);
        this.UBody.setRotationPoint(0F, 15F, -3F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-4F, -4F, 0F, 8, 8, 9);
        this.Body.setRotationPoint(0F, 16F, -4F);

        this.Tail = new ModelRenderer(this, 32, 17);
        this.Tail.addBox(-8F, -9F, 0F, 16, 12, 0);
        this.Tail.setRotationPoint(0F, 14F, 6F);
        setRotation(this.Tail, -0.2974289F, 0F, 0F);

        this.RLeg = new ModelRenderer(this, 27, 17);
        this.RLeg.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
        this.RLeg.setRotationPoint(-2F, 19F, 0.5F);

        this.RFoot = new ModelRenderer(this, 20, 23);
        this.RFoot.addBox(-1.5F, 5F, -2.5F, 3, 0, 3);
        this.RFoot.setRotationPoint(-2F, 19F, 0.5F);

        this.LLeg = new ModelRenderer(this, 23, 17);
        this.LLeg.addBox(-0.5F, 0F, -0.5F, 1, 5, 1);
        this.LLeg.setRotationPoint(2F, 19F, 0.5F);

        this.LFoot = new ModelRenderer(this, 20, 26);
        this.LFoot.addBox(-1.5F, 5F, -2.5F, 3, 0, 3);
        this.LFoot.setRotationPoint(2F, 19F, 0.5F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        this.male = ((MoCEntityTurkey) entity).getType() == 1;
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Beak.render(f5);
        this.Head.render(f5);
        this.Neck.render(f5);
        this.RWing.render(f5);
        this.LWing.render(f5);
        this.Tail.render(f5);
        this.RLeg.render(f5);
        this.RFoot.render(f5);
        this.LLeg.render(f5);
        this.LFoot.render(f5);
        if (male) {
            this.UBody.render(f5);
            this.Body.render(f5);
            this.Chest.render(f5);

        } else {
            GL11.glPushMatrix();
            GL11.glScalef(0.8F, 0.8F, 1F);
            this.Body.render(f5);
            this.Chest.render(f5);

            GL11.glPopMatrix();
        }

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {

        float LLegXRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        float wingF = (MathHelper.cos(f * 0.6662F) * 1.4F * f1) / 4F;

        this.Head.rotateAngleX = 0.4833219F + f4 / 57.29578F;//(RLegXRot/7F) + (-f4 / (180F / (float)Math.PI)) ;
        this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
        this.Beak.rotateAngleX = 0.2974F + this.Head.rotateAngleX;//0.7807508F - Head.rotateAngleX;
        this.Beak.rotateAngleY = this.Head.rotateAngleY;

        this.LLeg.rotateAngleX = LLegXRot;
        this.LFoot.rotateAngleX = this.LLeg.rotateAngleX;
        this.RLeg.rotateAngleX = RLegXRot;
        this.RFoot.rotateAngleX = this.RLeg.rotateAngleX;

        this.LWing.rotateAngleY = wingF;
        this.RWing.rotateAngleY = -wingF;

        if (this.male) {
            this.Tail.rotateAngleX = -0.2974289F + wingF;
            this.Tail.rotationPointY = 14F;
            this.Tail.rotationPointZ = 6F;
            this.Chest.rotationPointY = 12.5F;
            this.Body.rotationPointY = 16F;
            this.LWing.rotationPointX = 4F;
            this.RWing.rotationPointX = -4F;
        } else {
            this.Tail.rotateAngleX = wingF - (110 / 57.29578F);
            this.Tail.rotationPointY = 17F;
            this.Tail.rotationPointZ = 7F;
            this.Chest.rotationPointY = 16F;
            this.Body.rotationPointY = 20F;
            this.LWing.rotationPointX = 3.2F;
            this.RWing.rotationPointX = -3.2F;
        }

    }
}
