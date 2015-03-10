package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelCrocodile extends ModelBase {

    public MoCModelCrocodile() {
        this.LJaw = new ModelRenderer(this, 42, 0);
        this.LJaw.addBox(-2.5F, 1F, -12F, 5, 2, 6);
        this.LJaw.setRotationPoint(0F, 18F, -8F);
        this.LJaw.rotateAngleX = 0F;
        this.LJaw.rotateAngleY = 0F;
        this.LJaw.rotateAngleZ = 0F;

        this.TailA = new ModelRenderer(this, 0, 0);
        this.TailA.addBox(-4F, -0.5F, 0F, 8, 4, 8);
        this.TailA.setRotationPoint(0F, 17F, 12F);
        this.TailA.rotateAngleX = 0F;
        this.TailA.rotateAngleY = 0F;
        this.TailA.rotateAngleZ = 0F;

        this.TailB = new ModelRenderer(this, 2, 0);
        this.TailB.addBox(-3F, 0F, 8F, 6, 3, 8);
        this.TailB.setRotationPoint(0F, 17F, 12F);
        this.TailB.rotateAngleX = 0F;
        this.TailB.rotateAngleY = 0F;
        this.TailB.rotateAngleZ = 0F;

        this.TailC = new ModelRenderer(this, 6, 2);
        this.TailC.addBox(-2F, 0.5F, 16F, 4, 2, 6);
        this.TailC.setRotationPoint(0F, 17F, 12F);
        this.TailC.rotateAngleX = 0F;
        this.TailC.rotateAngleY = 0F;
        this.TailC.rotateAngleZ = 0F;

        this.TailD = new ModelRenderer(this, 7, 2);
        this.TailD.addBox(-1.5F, 1F, 22F, 3, 1, 6);
        this.TailD.setRotationPoint(0F, 17F, 12F);
        this.TailD.rotateAngleX = 0F;
        this.TailD.rotateAngleY = 0F;
        this.TailD.rotateAngleZ = 0F;

        this.UJaw = new ModelRenderer(this, 44, 8);
        this.UJaw.addBox(-2F, -1F, -12F, 4, 2, 6);
        this.UJaw.setRotationPoint(0F, 18F, -8F);
        this.UJaw.rotateAngleX = 0F;
        this.UJaw.rotateAngleY = 0F;
        this.UJaw.rotateAngleZ = 0F;

        this.Head = new ModelRenderer(this, 0, 16);
        this.Head.addBox(-3F, -2F, -6F, 6, 5, 6);
        this.Head.setRotationPoint(0F, 18F, -8F);
        this.Head.rotateAngleX = 0F;
        this.Head.rotateAngleY = 0F;
        this.Head.rotateAngleZ = 0F;

        this.Body = new ModelRenderer(this, 4, 7);
        this.Body.addBox(0F, 0F, 0F, 10, 5, 20);
        this.Body.setRotationPoint(-5F, 16F, -8F);
        this.Body.rotateAngleX = 0F;
        this.Body.rotateAngleY = 0F;
        this.Body.rotateAngleZ = 0F;

        this.Leg1 = new ModelRenderer(this, 49, 21);
        this.Leg1.addBox(1F, 2F, -3F, 3, 2, 4);
        this.Leg1.setRotationPoint(5F, 19F, -3F);

        this.Leg1.rotateAngleX = 0F;
        this.Leg1.rotateAngleY = 0F;
        this.Leg1.rotateAngleZ = 0F;

        this.Leg3 = new ModelRenderer(this, 48, 20);
        this.Leg3.addBox(1F, 2F, -3F, 3, 2, 5);
        this.Leg3.setRotationPoint(5F, 19F, 9F);

        this.Leg3.rotateAngleX = 0F;
        this.Leg3.rotateAngleY = 0F;
        this.Leg3.rotateAngleZ = 0F;

        this.Leg2 = new ModelRenderer(this, 49, 21);
        this.Leg2.addBox(-4F, 2F, -3F, 3, 2, 4);
        this.Leg2.setRotationPoint(-5F, 19F, -3F);

        this.Leg2.rotateAngleX = 0F;
        this.Leg2.rotateAngleY = 0F;
        this.Leg2.rotateAngleZ = 0F;

        this.Leg4 = new ModelRenderer(this, 48, 20);
        this.Leg4.addBox(-4F, 2F, -3F, 3, 2, 5);
        this.Leg4.setRotationPoint(-5F, 19F, 9F);

        this.Leg4.rotateAngleX = 0F;
        this.Leg4.rotateAngleY = 0F;
        this.Leg4.rotateAngleZ = 0F;

        this.Leg1A = new ModelRenderer(this, 7, 9);
        this.Leg1A.addBox(0F, -1F, -2F, 3, 3, 3);
        this.Leg1A.setRotationPoint(5F, 19F, -3F);

        this.Leg1A.rotateAngleX = 0F;
        this.Leg1A.rotateAngleY = 0F;
        this.Leg1A.rotateAngleZ = 0F;

        this.Leg2A = new ModelRenderer(this, 7, 9);
        this.Leg2A.addBox(-3F, -1F, -2F, 3, 3, 3);
        this.Leg2A.setRotationPoint(-5F, 19F, -3F);

        this.Leg2A.rotateAngleX = 0F;
        this.Leg2A.rotateAngleY = 0F;
        this.Leg2A.rotateAngleZ = 0F;

        this.Leg3A = new ModelRenderer(this, 6, 8);
        this.Leg3A.addBox(0F, -1F, -2F, 3, 3, 4);
        this.Leg3A.setRotationPoint(5F, 19F, 9F);

        this.Leg3A.rotateAngleX = 0F;
        this.Leg3A.rotateAngleY = 0F;
        this.Leg3A.rotateAngleZ = 0F;

        this.Leg4A = new ModelRenderer(this, 6, 8);
        this.Leg4A.addBox(-3F, -1F, -2F, 3, 3, 4);
        this.Leg4A.setRotationPoint(-5F, 19F, 9F);

        this.Leg4A.rotateAngleX = 0F;
        this.Leg4A.rotateAngleY = 0F;
        this.Leg4A.rotateAngleZ = 0F;

        this.UJaw2 = new ModelRenderer(this, 37, 0);
        this.UJaw2.addBox(-1.5F, -1F, -16F, 3, 2, 4);
        this.UJaw2.setRotationPoint(0F, 18F, -8F);
        this.UJaw2.rotateAngleX = 0F;
        this.UJaw2.rotateAngleY = 0F;
        this.UJaw2.rotateAngleZ = 0F;

        this.LJaw2 = new ModelRenderer(this, 24, 1);
        this.LJaw2.addBox(-2F, 1F, -16F, 4, 2, 4);
        this.LJaw2.setRotationPoint(0F, 18F, -8F);
        this.LJaw2.rotateAngleX = 0F;
        this.LJaw2.rotateAngleY = 0F;
        this.LJaw2.rotateAngleZ = 0F;

        this.TeethA = new ModelRenderer(this, 8, 11);
        this.TeethA.addBox(1.6F, 0F, -16F, 0, 1, 4);
        this.TeethA.setRotationPoint(0F, 18F, -8F);
        this.TeethA.rotateAngleX = 0F;
        this.TeethA.rotateAngleY = 0F;
        this.TeethA.rotateAngleZ = 0F;

        this.TeethB = new ModelRenderer(this, 8, 11);
        this.TeethB.addBox(-1.6F, 0F, -16F, 0, 1, 4);
        this.TeethB.setRotationPoint(0F, 18F, -8F);
        this.TeethB.rotateAngleX = 0F;
        this.TeethB.rotateAngleY = 0F;
        this.TeethB.rotateAngleZ = 0F;

        this.TeethC = new ModelRenderer(this, 6, 9);
        this.TeethC.addBox(2.1F, 0F, -12F, 0, 1, 6);
        this.TeethC.setRotationPoint(0F, 18F, -8F);
        this.TeethC.rotateAngleX = 0F;
        this.TeethC.rotateAngleY = 0F;
        this.TeethC.rotateAngleZ = 0F;

        this.TeethD = new ModelRenderer(this, 6, 9);
        this.TeethD.addBox(-2.1F, 0F, -12F, 0, 1, 6);
        this.TeethD.setRotationPoint(0F, 18F, -8F);
        this.TeethD.rotateAngleX = 0F;
        this.TeethD.rotateAngleY = 0F;
        this.TeethD.rotateAngleZ = 0F;

        this.Leg1A.rotateAngleX = 0F;
        this.Leg1A.rotateAngleY = 0F;
        this.Leg1A.rotateAngleZ = 0F;

        this.Leg2A.rotateAngleX = 0F;
        this.Leg2A.rotateAngleY = 0F;
        this.Leg2A.rotateAngleZ = 0F;

        this.Leg3A.rotateAngleX = 0F;
        this.Leg3A.rotateAngleY = 0F;
        this.Leg3A.rotateAngleZ = 0F;

        this.Leg4A.rotateAngleX = 0F;
        this.Leg4A.rotateAngleY = 0F;
        this.Leg4A.rotateAngleZ = 0F;

        this.TeethF = new ModelRenderer(this, 19, 21);
        this.TeethF.addBox(-1F, 0F, -16.1F, 2, 1, 0);
        this.TeethF.setRotationPoint(0F, 18F, -8F);

        this.Spike0 = new ModelRenderer(this, 44, 16);
        this.Spike0.addBox(-1F, -1F, 23F, 0, 2, 4);
        this.Spike0.setRotationPoint(0F, 17F, 12F);

        this.Spike1 = new ModelRenderer(this, 44, 16);
        this.Spike1.addBox(1F, -1F, 23F, 0, 2, 4);
        this.Spike1.setRotationPoint(0F, 17F, 12F);

        this.Spike2 = new ModelRenderer(this, 44, 16);
        this.Spike2.addBox(-1.5F, -1.5F, 17F, 0, 2, 4);
        this.Spike2.setRotationPoint(0F, 17F, 12F);

        this.Spike3 = new ModelRenderer(this, 44, 16);
        this.Spike3.addBox(1.5F, -1.5F, 17F, 0, 2, 4);
        this.Spike3.setRotationPoint(0F, 17F, 12F);

        this.Spike4 = new ModelRenderer(this, 44, 16);
        this.Spike4.addBox(-2F, -2F, 12F, 0, 2, 4);
        this.Spike4.setRotationPoint(0F, 17F, 12F);

        this.Spike5 = new ModelRenderer(this, 44, 16);
        this.Spike5.addBox(2F, -2F, 12F, 0, 2, 4);
        this.Spike5.setRotationPoint(0F, 17F, 12F);

        this.Spike6 = new ModelRenderer(this, 44, 16);
        this.Spike6.addBox(-2.5F, -2F, 8F, 0, 2, 4);
        this.Spike6.setRotationPoint(0F, 17F, 12F);

        this.Spike7 = new ModelRenderer(this, 44, 16);
        this.Spike7.addBox(2.5F, -2F, 8F, 0, 2, 4);
        this.Spike7.setRotationPoint(0F, 17F, 12F);

        this.Spike8 = new ModelRenderer(this, 44, 16);
        this.Spike8.addBox(-3F, -2.5F, 4F, 0, 2, 4);
        this.Spike8.setRotationPoint(0F, 17F, 12F);

        this.Spike9 = new ModelRenderer(this, 44, 16);
        this.Spike9.addBox(3F, -2.5F, 4F, 0, 2, 4);
        this.Spike9.setRotationPoint(0F, 17F, 12F);

        this.Spike10 = new ModelRenderer(this, 44, 16);
        this.Spike10.addBox(3.5F, -2.5F, 0F, 0, 2, 4);
        this.Spike10.setRotationPoint(0F, 17F, 12F);

        this.Spike11 = new ModelRenderer(this, 44, 16);
        this.Spike11.addBox(-3.5F, -2.5F, 0F, 0, 2, 4);
        this.Spike11.setRotationPoint(0F, 17F, 12F);

        this.SpikeBack0 = new ModelRenderer(this, 44, 10);
        this.SpikeBack0.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack0.setRotationPoint(0F, 14F, 3F);

        this.SpikeBack1 = new ModelRenderer(this, 44, 10);
        this.SpikeBack1.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack1.setRotationPoint(0F, 14F, -6F);

        this.SpikeBack2 = new ModelRenderer(this, 44, 10);
        this.SpikeBack2.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack2.setRotationPoint(4F, 14F, -8F);

        this.SpikeBack3 = new ModelRenderer(this, 44, 10);
        this.SpikeBack3.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack3.setRotationPoint(-4F, 14F, -8F);

        this.SpikeBack4 = new ModelRenderer(this, 44, 10);
        this.SpikeBack4.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack4.setRotationPoint(-4F, 14F, 1F);

        this.SpikeBack5 = new ModelRenderer(this, 44, 10);
        this.SpikeBack5.addBox(0F, 0F, 0F, 0, 2, 8);
        this.SpikeBack5.setRotationPoint(4F, 14F, 1F);

        this.SpikeEye = new ModelRenderer(this, 44, 14);
        this.SpikeEye.addBox(-3F, -3F, -6F, 0, 1, 2);
        this.SpikeEye.setRotationPoint(0F, 18F, -8F);

        this.SpikeEye1 = new ModelRenderer(this, 44, 14);
        this.SpikeEye1.addBox(3F, -3F, -6F, 0, 1, 2);
        this.SpikeEye1.setRotationPoint(0F, 18F, -8F);

        this.TeethA1 = new ModelRenderer(this, 52, 12);
        this.TeethA1.addBox(1.4F, 1F, -16.4F, 0, 1, 4);
        this.TeethA1.setRotationPoint(0F, 18F, -8F);

        this.TeethB1 = new ModelRenderer(this, 52, 12);
        this.TeethB1.addBox(-1.4F, 1F, -16.4F, 0, 1, 4);
        this.TeethB1.setRotationPoint(0F, 18F, -8F);

        this.TeethC1 = new ModelRenderer(this, 50, 10);
        this.TeethC1.addBox(1.9F, 1F, -12.5F, 0, 1, 6);
        this.TeethC1.setRotationPoint(0F, 18F, -8F);

        this.TeethD1 = new ModelRenderer(this, 50, 10);
        this.TeethD1.addBox(-1.9F, 1F, -12.5F, 0, 1, 6);
        this.TeethD1.setRotationPoint(0F, 18F, -8F);
    }

    public void model2() {

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.LJaw.render(f5);
        this.TailA.render(f5);
        this.TailB.render(f5);
        this.TailC.render(f5);
        this.UJaw.render(f5);
        this.Head.render(f5);
        this.Body.render(f5);
        this.Leg1.render(f5);
        this.Leg3.render(f5);
        this.Leg2.render(f5);
        this.Leg4.render(f5);
        this.TailD.render(f5);
        this.Leg1A.render(f5);
        this.Leg2A.render(f5);
        this.Leg3A.render(f5);
        this.Leg4A.render(f5);
        this.UJaw2.render(f5);
        this.LJaw2.render(f5);
        this.TeethA.render(f5);
        this.TeethB.render(f5);
        this.TeethC.render(f5);
        this.TeethD.render(f5);
        this.TeethF.render(f5);
        this.Spike0.render(f5);
        this.Spike1.render(f5);
        this.Spike2.render(f5);
        this.Spike3.render(f5);
        this.Spike4.render(f5);
        this.Spike5.render(f5);
        this.Spike6.render(f5);
        this.Spike7.render(f5);
        this.Spike8.render(f5);
        this.Spike9.render(f5);
        this.Spike10.render(f5);
        this.Spike11.render(f5);
        this.SpikeBack0.render(f5);
        this.SpikeBack1.render(f5);
        this.SpikeBack2.render(f5);
        this.SpikeBack3.render(f5);
        this.SpikeBack4.render(f5);
        this.SpikeBack5.render(f5);
        this.SpikeEye.render(f5);
        this.SpikeEye1.render(f5);
        this.TeethA1.render(f5);
        this.TeethB1.render(f5);
        this.TeethC1.render(f5);
        this.TeethD1.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Head.rotateAngleX = f4 / 57.29578F;
        this.Head.rotateAngleY = f3 / 57.29578F;
        this.SpikeEye.rotateAngleX = this.Head.rotateAngleX;
        this.SpikeEye.rotateAngleY = this.Head.rotateAngleY;
        this.SpikeEye1.rotateAngleX = this.Head.rotateAngleX;
        this.SpikeEye1.rotateAngleY = this.Head.rotateAngleY;

        //LJaw.rotateAngleX = Head.rotateAngleX;
        this.LJaw.rotateAngleY = this.Head.rotateAngleY;
        this.LJaw2.rotateAngleY = this.Head.rotateAngleY;
        //UJaw.rotateAngleX = Head.rotateAngleX;
        this.UJaw.rotateAngleY = this.Head.rotateAngleY;
        this.UJaw2.rotateAngleY = this.Head.rotateAngleY;
        if (this.swimming) {
            //Leg1.setRotationPoint(9F, 18F, 0F);
            this.Leg1.rotationPointX = 9F;
            this.Leg1.rotationPointY = 18F;
            this.Leg1.rotationPointZ = 0F;
            this.Leg1.rotateAngleX = 0F;
            this.Leg1.rotateAngleY = -3.14159F;
            //Leg2.setRotationPoint(-9F, 18F, 0F);
            this.Leg2.rotationPointX = -9F;
            this.Leg2.rotationPointY = 18F;
            this.Leg2.rotationPointZ = 0F;
            this.Leg2.rotateAngleX = 0F;
            this.Leg2.rotateAngleY = -3.14159F;
            //Leg3.setRotationPoint(8F, 18F, 12F);
            this.Leg3.rotationPointX = 8F;
            this.Leg3.rotationPointY = 18F;
            this.Leg3.rotationPointZ = 12F;
            this.Leg3.rotateAngleX = 0F;
            this.Leg3.rotateAngleY = -3.14159F;
            //Leg4.setRotationPoint(-8F, 18F, 12F);
            this.Leg4.rotationPointX = -8F;
            this.Leg4.rotationPointY = 18F;
            this.Leg4.rotationPointZ = 12F;
            this.Leg4.rotateAngleX = 0F;
            this.Leg4.rotateAngleY = -3.14159F;

            //Leg1A.setRotationPoint(5F, 19F, -3F);
            this.Leg1A.rotateAngleX = 1.5708F;
            this.Leg1A.rotationPointX = 5F;
            this.Leg1A.rotationPointY = 19F;
            this.Leg1A.rotationPointZ = -3F;

            //Leg2A.setRotationPoint(-5F, 19F, -3F);
            this.Leg2A.rotateAngleX = 1.5708F;
            this.Leg2A.rotationPointX = -5F;
            this.Leg2A.rotationPointY = 19F;
            this.Leg2A.rotationPointZ = -3F;

            //Leg3A.setRotationPoint(5F, 19F, 9F);
            this.Leg3A.rotateAngleX = 1.5708F;
            this.Leg3A.rotationPointX = 5F;
            this.Leg3A.rotationPointY = 19F;
            this.Leg3A.rotationPointZ = 9F;

            //Leg4A.setRotationPoint(-5F, 19F, 9F);
            this.Leg4A.rotateAngleX = 1.5708F;
            this.Leg4A.rotationPointX = -5F;
            this.Leg4A.rotationPointY = 19F;
            this.Leg4A.rotationPointZ = 9F;

            this.Leg1.rotateAngleZ = 0F;
            this.Leg1A.rotateAngleZ = 0F;
            this.Leg3.rotateAngleZ = 0F;
            this.Leg3A.rotateAngleZ = 0F;

            this.Leg2.rotateAngleZ = 0F;
            this.Leg2A.rotateAngleZ = 0F;
            this.Leg4.rotateAngleZ = 0F;
            this.Leg4A.rotateAngleZ = 0F;

        } else if (this.resting) {
            //Leg1.setRotationPoint(6F, 17F, -6F);
            this.Leg1.rotationPointX = 6F;
            this.Leg1.rotationPointY = 17F;
            this.Leg1.rotationPointZ = -6F;
            this.Leg1.rotateAngleX = 0F;
            this.Leg1.rotateAngleY = -0.7854F;

            //Leg2.setRotationPoint(-6F, 17F, -6F);
            this.Leg2.rotateAngleY = 0.7854F;
            this.Leg2.rotationPointX = -6F;
            this.Leg2.rotationPointY = 17F;
            this.Leg2.rotationPointZ = -6F;
            this.Leg2.rotateAngleX = 0F;

            //Leg3.setRotationPoint(7F, 17F, 7F);
            this.Leg3.rotateAngleY = -0.7854F;
            this.Leg3.rotationPointX = 7F;
            this.Leg3.rotationPointY = 17F;
            this.Leg3.rotationPointZ = 7F;
            this.Leg3.rotateAngleX = 0F;

            this.Leg4.setRotationPoint(-7F, 17F, 7F);
            this.Leg4.rotateAngleY = 0.7854F;
            this.Leg4.rotationPointX = -7F;
            this.Leg4.rotationPointY = 17F;
            this.Leg4.rotationPointZ = 7F;
            this.Leg4.rotateAngleX = 0F;

            //Leg1A.setRotationPoint(5F, 17F, -3F);
            this.Leg1A.rotationPointX = 5F;
            this.Leg1A.rotationPointY = 17F;
            this.Leg1A.rotationPointZ = -3F;
            this.Leg1A.rotateAngleX = 0F;

            //Leg2A.setRotationPoint(-5F, 17F, -3F);
            this.Leg2A.rotationPointX = -5F;
            this.Leg2A.rotationPointY = 17F;
            this.Leg2A.rotationPointZ = -3F;
            this.Leg2A.rotateAngleX = 0F;

            //Leg3A.setRotationPoint(5F, 17F, 9F);
            this.Leg3A.rotationPointX = 5F;
            this.Leg3A.rotationPointY = 17F;
            this.Leg3A.rotationPointZ = 9F;
            this.Leg3A.rotateAngleX = 0F;

            //Leg4A.setRotationPoint(-5F, 17F, 9F);
            this.Leg4A.rotationPointX = -5F;
            this.Leg4A.rotationPointY = 17F;
            this.Leg4A.rotationPointZ = 9F;
            this.Leg4A.rotateAngleX = 0F;

            this.Leg1.rotateAngleZ = 0F;
            this.Leg1A.rotateAngleZ = 0F;
            this.Leg3.rotateAngleZ = 0F;
            this.Leg3A.rotateAngleZ = 0F;

            this.Leg2.rotateAngleZ = 0F;
            this.Leg2A.rotateAngleZ = 0F;
            this.Leg4.rotateAngleZ = 0F;
            this.Leg4A.rotateAngleZ = 0F;
        } else {
            //Leg1.setRotationPoint(5F, 19F, -3F);
            this.Leg1.rotationPointX = 5F;
            this.Leg1.rotationPointY = 19F;
            this.Leg1.rotationPointZ = -3F;
            //Leg2.setRotationPoint(-5F, 19F, -3F);
            this.Leg2.rotationPointX = -5F;
            this.Leg2.rotationPointY = 19F;
            this.Leg2.rotationPointZ = -3F;
            //Leg3.setRotationPoint(5F, 19F, 9F);
            this.Leg3.rotationPointX = 5F;
            this.Leg3.rotationPointY = 19F;
            this.Leg3.rotationPointZ = 9F;
            //Leg4.setRotationPoint(-5F, 19F, 9F);
            this.Leg4.rotationPointX = -5F;
            this.Leg4.rotationPointY = 19F;
            this.Leg4.rotationPointZ = 9F;

            //Leg1A.setRotationPoint(5F, 19F, -3F);
            this.Leg1A.rotationPointX = 5F;
            this.Leg1A.rotationPointY = 19F;
            this.Leg1A.rotationPointZ = -3F;

            //Leg2A.setRotationPoint(-5F, 19F, -3F);
            this.Leg2A.rotationPointX = -5F;
            this.Leg2A.rotationPointY = 19F;
            this.Leg2A.rotationPointZ = -3F;

            //Leg3A.setRotationPoint(5F, 19F, 9F);
            this.Leg3A.rotationPointX = 5F;
            this.Leg3A.rotationPointY = 19F;
            this.Leg3A.rotationPointZ = 9F;

            //Leg4A.setRotationPoint(-5F, 19F, 9F);
            this.Leg4A.rotationPointX = -5F;
            this.Leg4A.rotationPointY = 19F;
            this.Leg4A.rotationPointZ = 9F;

            this.Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
            this.Leg2.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
            this.Leg3.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
            this.Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;

            this.Leg1.rotateAngleY = 0F;
            this.Leg2.rotateAngleY = 0F;
            this.Leg3.rotateAngleY = 0F;
            this.Leg4.rotateAngleY = 0F;

            this.Leg1A.rotateAngleX = this.Leg1.rotateAngleX;
            this.Leg2A.rotateAngleX = this.Leg2.rotateAngleX;
            this.Leg3A.rotateAngleX = this.Leg3.rotateAngleX;
            this.Leg4A.rotateAngleX = this.Leg4.rotateAngleX;

            float latrot = MathHelper.cos(f / (1.919107651F * 1)) * 0.261799387799149F * f1 * 5;
            this.Leg1.rotateAngleZ = latrot;
            this.Leg1A.rotateAngleZ = latrot;
            this.Leg4.rotateAngleZ = -latrot;
            this.Leg4A.rotateAngleZ = -latrot;

            this.Leg3.rotateAngleZ = latrot;
            this.Leg3A.rotateAngleZ = latrot;

            this.Leg2.rotateAngleZ = -latrot;
            this.Leg2A.rotateAngleZ = -latrot;

            //Leg1.rotateAngleZ = MathHelper.cos(f / (1.919107651F * 1 )) * 0.261799387799149F * f1 *10;
            //Leg1A.rotateAngleZ = MathHelper.cos(f / (1.919107651F * 1 )) * 0.261799387799149F * f1 *10;

            //LArm.rotateAngleY = MathHelper.cos(f / (1.919107651F * 1 )) * -0.349065850398866F * f1 + 0.785398163397448F ;

        }
        this.TailA.rotateAngleY = MathHelper.cos(f * 0.6662F) * 0.7F * f1;
        this.TailB.rotateAngleY = this.TailA.rotateAngleY;
        this.TailC.rotateAngleY = this.TailA.rotateAngleY;
        this.TailD.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike0.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike1.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike2.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike3.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike4.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike5.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike6.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike7.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike8.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike9.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike10.rotateAngleY = this.TailA.rotateAngleY;
        this.Spike11.rotateAngleY = this.TailA.rotateAngleY;
        float f25 = this.biteProgress;
        float f26 = f25;
        if (f25 >= 0.5F) {
            f26 = (0.5F - (f25 - 0.5F));
        }
        this.UJaw.rotateAngleX = this.Head.rotateAngleX - f26;
        this.UJaw2.rotateAngleX = this.UJaw.rotateAngleX;
        this.LJaw.rotateAngleX = this.Head.rotateAngleX + (f26 / 2);
        this.LJaw2.rotateAngleX = this.LJaw.rotateAngleX;
        this.TeethA.rotateAngleX = this.LJaw.rotateAngleX;
        this.TeethB.rotateAngleX = this.LJaw.rotateAngleX;
        this.TeethC.rotateAngleX = this.LJaw.rotateAngleX;
        this.TeethD.rotateAngleX = this.LJaw.rotateAngleX;
        this.TeethF.rotateAngleX = this.LJaw.rotateAngleX;
        this.TeethA.rotateAngleY = this.LJaw.rotateAngleY;
        this.TeethB.rotateAngleY = this.LJaw.rotateAngleY;
        this.TeethC.rotateAngleY = this.LJaw.rotateAngleY;
        this.TeethD.rotateAngleY = this.LJaw.rotateAngleY;
        this.TeethF.rotateAngleY = this.LJaw.rotateAngleY;
        this.TeethA1.rotateAngleX = this.UJaw.rotateAngleX;
        this.TeethB1.rotateAngleX = this.UJaw.rotateAngleX;
        this.TeethC1.rotateAngleX = this.UJaw.rotateAngleX;
        this.TeethD1.rotateAngleX = this.UJaw.rotateAngleX;
        this.TeethA1.rotateAngleY = this.UJaw.rotateAngleY;
        this.TeethB1.rotateAngleY = this.UJaw.rotateAngleY;
        this.TeethC1.rotateAngleY = this.UJaw.rotateAngleY;
        this.TeethD1.rotateAngleY = this.UJaw.rotateAngleY;

    }

    ModelRenderer LJaw;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer TailC;
    ModelRenderer UJaw;
    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Leg1;
    ModelRenderer Leg3;
    ModelRenderer Leg2;
    ModelRenderer Leg4;
    ModelRenderer TailD;
    ModelRenderer Leg1A;
    ModelRenderer Leg2A;
    ModelRenderer Leg3A;
    ModelRenderer Leg4A;
    ModelRenderer UJaw2;
    ModelRenderer LJaw2;
    ModelRenderer TeethA;
    ModelRenderer TeethB;
    ModelRenderer TeethC;
    ModelRenderer TeethD;
    public float biteProgress;
    public boolean swimming;
    public boolean resting;

    ModelRenderer TeethF;
    ModelRenderer Spike0;
    ModelRenderer Spike1;
    ModelRenderer Spike2;
    ModelRenderer Spike3;
    ModelRenderer Spike4;
    ModelRenderer Spike5;
    ModelRenderer Spike6;
    ModelRenderer Spike7;
    ModelRenderer Spike8;
    ModelRenderer Spike9;
    ModelRenderer Spike10;
    ModelRenderer Spike11;
    ModelRenderer SpikeBack0;
    ModelRenderer SpikeBack1;
    ModelRenderer SpikeBack2;
    ModelRenderer SpikeBack3;
    ModelRenderer SpikeBack4;
    ModelRenderer SpikeBack5;
    ModelRenderer SpikeEye;
    ModelRenderer SpikeEye1;
    ModelRenderer TeethA1;
    ModelRenderer TeethB1;
    ModelRenderer TeethC1;
    ModelRenderer TeethD1;
}
