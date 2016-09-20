package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCModelCricket extends ModelBase {

    ModelRenderer Head;
    ModelRenderer Antenna;
    ModelRenderer AntennaB;
    ModelRenderer Thorax;
    ModelRenderer Abdomen;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer FrontLegs;
    ModelRenderer MidLegs;
    ModelRenderer ThighLeft;
    ModelRenderer ThighLeftB;
    ModelRenderer ThighRight;
    ModelRenderer ThighRightB;
    ModelRenderer LegLeft;
    ModelRenderer LegLeftB;
    ModelRenderer LegRight;
    ModelRenderer LegRightB;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;
    ModelRenderer FoldedWings;

    public MoCModelCricket() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-0.5F, 0F, -1F, 1, 1, 2);
        this.Head.setRotationPoint(0F, 22.5F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Antenna = new ModelRenderer(this, 0, 11);
        this.Antenna.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.Antenna.setRotationPoint(0F, 22.5F, -3F);
        setRotation(this.Antenna, -2.736346F, 0F, 0F);

        this.AntennaB = new ModelRenderer(this, 0, 9);
        this.AntennaB.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.AntennaB.setRotationPoint(0F, 20.7F, -3.8F);
        setRotation(this.AntennaB, 2.88506F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setRotationPoint(0F, 21F, -1F);
        setRotation(this.Thorax, 0F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Abdomen.setRotationPoint(0F, 22F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.TailA = new ModelRenderer(this, 4, 9);
        this.TailA.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.TailA.setRotationPoint(0F, 22F, 2.8F);
        setRotation(this.TailA, 1.308687F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 4, 7);
        this.TailB.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.TailB.setRotationPoint(0F, 23F, 2.8F);
        setRotation(this.TailB, 1.665602F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 7);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.FrontLegs.setRotationPoint(0F, 23F, -1.8F);
        setRotation(this.FrontLegs, -0.8328009F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 0, 13);
        this.MidLegs.addBox(-2F, 0F, 0F, 4, 2, 0);
        this.MidLegs.setRotationPoint(0F, 23F, -1.2F);
        setRotation(this.MidLegs, 1.070744F, 0F, 0F);

        this.ThighLeft = new ModelRenderer(this, 8, 5);
        this.ThighLeft.addBox(0F, -3F, 0F, 1, 3, 1);
        this.ThighLeft.setRotationPoint(0.5F, 23F, 0F);
        setRotation(this.ThighLeft, -0.4886922F, 0.2617994F, 0F);

        this.ThighLeftB = new ModelRenderer(this, 8, 5);
        this.ThighLeftB.addBox(0F, -3F, 0F, 1, 3, 1);
        this.ThighLeftB.setRotationPoint(0.5F, 22.5F, 0F);
        setRotation(this.ThighLeftB, -1.762782F, 0F, 0F);

        this.ThighRight = new ModelRenderer(this, 12, 5);
        this.ThighRight.addBox(-1F, -3F, 0F, 1, 3, 1);
        this.ThighRight.setRotationPoint(-0.5F, 23F, 0F);
        setRotation(this.ThighRight, -0.4886922F, -0.2617994F, 0F);

        this.ThighRightB = new ModelRenderer(this, 12, 5);
        this.ThighRightB.addBox(-1F, -3F, 0F, 1, 3, 1);
        this.ThighRightB.setRotationPoint(-0.5F, 22.5F, 0F);
        setRotation(this.ThighRightB, -1.762782F, 0F, 0F);

        this.LegLeft = new ModelRenderer(this, 0, 15);
        this.LegLeft.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegLeft.setRotationPoint(2F, 21F, 2.5F);

        this.LegLeftB = new ModelRenderer(this, 4, 15);
        this.LegLeftB.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegLeftB.setRotationPoint(1.5F, 23F, 2.9F);
        setRotation(this.LegLeftB, 1.249201F, 0F, 0F);

        this.LegRight = new ModelRenderer(this, 4, 15);
        this.LegRight.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegRight.setRotationPoint(-2F, 21F, 2.5F);

        this.LegRightB = new ModelRenderer(this, 4, 15);
        this.LegRightB.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegRightB.setRotationPoint(-1.5F, 23F, 2.9F);
        setRotation(this.LegRightB, 1.249201F, 0F, 0F);

        this.LeftWing = new ModelRenderer(this, 0, 30);
        this.LeftWing.addBox(0F, 0F, -1F, 6, 0, 2);
        this.LeftWing.setRotationPoint(0F, 20.9F, -1F);
        setRotation(this.LeftWing, 0F, -0.1919862F, 0F);

        this.RightWing = new ModelRenderer(this, 0, 28);
        this.RightWing.addBox(-6F, 0F, -1F, 6, 0, 2);
        this.RightWing.setRotationPoint(0F, 20.9F, -1F);
        setRotation(this.RightWing, 0F, 0.1919862F, 0F);

        this.FoldedWings = new ModelRenderer(this, 0, 26);
        this.FoldedWings.addBox(0F, 0F, -1F, 6, 0, 2);
        this.FoldedWings.setRotationPoint(0F, 20.9F, -2F);
        setRotation(this.FoldedWings, 0F, -1.570796F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityCricket entitycricket = (MoCEntityCricket) entity;
        boolean isFlying = (entitycricket.getIsFlying() || entitycricket.motionY < -0.1D);
        setRotationAngles(f, f1, f2, f3, f4, f5, isFlying);
        this.Head.render(f5);
        this.Antenna.render(f5);
        this.AntennaB.render(f5);
        this.Thorax.render(f5);
        this.Abdomen.render(f5);
        this.TailA.render(f5);
        this.TailB.render(f5);
        this.FrontLegs.render(f5);
        this.MidLegs.render(f5);

        if (!isFlying) {

            this.ThighLeft.render(f5);
            this.ThighRight.render(f5);
            this.LegLeft.render(f5);
            this.LegRight.render(f5);
            this.FoldedWings.render(f5);

        } else {

            this.ThighLeftB.render(f5);
            this.ThighRightB.render(f5);
            this.LegLeftB.render(f5);
            this.LegRightB.render(f5);
            GL11.glPushMatrix();
            GL11.glEnable(3042 /* GL_BLEND */);
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            this.LeftWing.render(f5);
            this.RightWing.render(f5);
            GL11.glDisable(3042/* GL_BLEND */);
            GL11.glPopMatrix();

        }

        /*
         * if (isFlying) { }else { }
         */
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean isFlying) {

        float legMov = 0F;
        float legMovB = 0F;

        float frontLegAdj = 0F;

        if (isFlying) {
            float WingRot = MathHelper.cos((f2 * 2.0F)) * 0.7F;
            this.RightWing.rotateAngleZ = WingRot;
            this.LeftWing.rotateAngleZ = -WingRot;
            legMov = (f1 * 1.5F);
            legMovB = legMov;
            frontLegAdj = 1.4F;

        } else {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }

        this.AntennaB.rotateAngleX = 2.88506F - legMov;

        this.FrontLegs.rotateAngleX = -0.8328009F + frontLegAdj + legMov;
        this.MidLegs.rotateAngleX = 1.070744F + legMovB;

    }

}
