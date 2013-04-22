package drzhark.mocreatures.client.model;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelCrocodile extends ModelBase {

    public MoCModelCrocodile()
    {
        LJaw = new ModelRenderer(this, 42, 0);
        LJaw.addBox(-2.5F, 1F, -12F, 5, 2, 6);
        LJaw.setRotationPoint(0F, 18F, -8F);
        LJaw.rotateAngleX = 0F;
        LJaw.rotateAngleY = 0F;
        LJaw.rotateAngleZ = 0F;

        TailA = new ModelRenderer(this, 0, 0);
        TailA.addBox(-4F, -0.5F, 0F, 8, 4, 8);
        TailA.setRotationPoint(0F, 17F, 12F);
        TailA.rotateAngleX = 0F;
        TailA.rotateAngleY = 0F;
        TailA.rotateAngleZ = 0F;

        TailB = new ModelRenderer(this, 2, 0);
        TailB.addBox(-3F, 0F, 8F, 6, 3, 8);
        TailB.setRotationPoint(0F, 17F, 12F);
        TailB.rotateAngleX = 0F;
        TailB.rotateAngleY = 0F;
        TailB.rotateAngleZ = 0F;

        TailC = new ModelRenderer(this, 6, 2);
        TailC.addBox(-2F, 0.5F, 16F, 4, 2, 6);
        TailC.setRotationPoint(0F, 17F, 12F);
        TailC.rotateAngleX = 0F;
        TailC.rotateAngleY = 0F;
        TailC.rotateAngleZ = 0F;

        TailD = new ModelRenderer(this, 7, 2);
        TailD.addBox(-1.5F, 1F, 22F, 3, 1, 6);
        TailD.setRotationPoint(0F, 17F, 12F);
        TailD.rotateAngleX = 0F;
        TailD.rotateAngleY = 0F;
        TailD.rotateAngleZ = 0F;

        UJaw = new ModelRenderer(this, 44, 8);
        UJaw.addBox(-2F, -1F, -12F, 4, 2, 6);
        UJaw.setRotationPoint(0F, 18F, -8F);
        UJaw.rotateAngleX = 0F;
        UJaw.rotateAngleY = 0F;
        UJaw.rotateAngleZ = 0F;

        Head = new ModelRenderer(this, 0, 16);
        Head.addBox(-3F, -2F, -6F, 6, 5, 6);
        Head.setRotationPoint(0F, 18F, -8F);
        Head.rotateAngleX = 0F;
        Head.rotateAngleY = 0F;
        Head.rotateAngleZ = 0F;

        Body = new ModelRenderer(this, 4, 7);
        Body.addBox(0F, 0F, 0F, 10, 5, 20);
        Body.setRotationPoint(-5F, 16F, -8F);
        Body.rotateAngleX = 0F;
        Body.rotateAngleY = 0F;
        Body.rotateAngleZ = 0F;

        Leg1 = new ModelRenderer(this, 49, 21);
        Leg1.addBox(1F, 2F, -3F, 3, 2, 4);
        Leg1.setRotationPoint(5F, 19F, -3F);

        Leg1.rotateAngleX = 0F;
        Leg1.rotateAngleY = 0F;
        Leg1.rotateAngleZ = 0F;

        Leg3 = new ModelRenderer(this, 48, 20);
        Leg3.addBox(1F, 2F, -3F, 3, 2, 5);
        Leg3.setRotationPoint(5F, 19F, 9F);

        Leg3.rotateAngleX = 0F;
        Leg3.rotateAngleY = 0F;
        Leg3.rotateAngleZ = 0F;

        Leg2 = new ModelRenderer(this, 49, 21);
        Leg2.addBox(-4F, 2F, -3F, 3, 2, 4);
        Leg2.setRotationPoint(-5F, 19F, -3F);

        Leg2.rotateAngleX = 0F;
        Leg2.rotateAngleY = 0F;
        Leg2.rotateAngleZ = 0F;

        Leg4 = new ModelRenderer(this, 48, 20);
        Leg4.addBox(-4F, 2F, -3F, 3, 2, 5);
        Leg4.setRotationPoint(-5F, 19F, 9F);

        Leg4.rotateAngleX = 0F;
        Leg4.rotateAngleY = 0F;
        Leg4.rotateAngleZ = 0F;

        Leg1A = new ModelRenderer(this, 7, 9);
        Leg1A.addBox(0F, -1F, -2F, 3, 3, 3);
        Leg1A.setRotationPoint(5F, 19F, -3F);

        Leg1A.rotateAngleX = 0F;
        Leg1A.rotateAngleY = 0F;
        Leg1A.rotateAngleZ = 0F;

        Leg2A = new ModelRenderer(this, 7, 9);
        Leg2A.addBox(-3F, -1F, -2F, 3, 3, 3);
        Leg2A.setRotationPoint(-5F, 19F, -3F);

        Leg2A.rotateAngleX = 0F;
        Leg2A.rotateAngleY = 0F;
        Leg2A.rotateAngleZ = 0F;

        Leg3A = new ModelRenderer(this, 6, 8);
        Leg3A.addBox(0F, -1F, -2F, 3, 3, 4);
        Leg3A.setRotationPoint(5F, 19F, 9F);

        Leg3A.rotateAngleX = 0F;
        Leg3A.rotateAngleY = 0F;
        Leg3A.rotateAngleZ = 0F;

        Leg4A = new ModelRenderer(this, 6, 8);
        Leg4A.addBox(-3F, -1F, -2F, 3, 3, 4);
        Leg4A.setRotationPoint(-5F, 19F, 9F);

        Leg4A.rotateAngleX = 0F;
        Leg4A.rotateAngleY = 0F;
        Leg4A.rotateAngleZ = 0F;

        UJaw2 = new ModelRenderer(this, 37, 0);
        UJaw2.addBox(-1.5F, -1F, -16F, 3, 2, 4);
        UJaw2.setRotationPoint(0F, 18F, -8F);
        UJaw2.rotateAngleX = 0F;
        UJaw2.rotateAngleY = 0F;
        UJaw2.rotateAngleZ = 0F;

        LJaw2 = new ModelRenderer(this, 24, 1);
        LJaw2.addBox(-2F, 1F, -16F, 4, 2, 4);
        LJaw2.setRotationPoint(0F, 18F, -8F);
        LJaw2.rotateAngleX = 0F;
        LJaw2.rotateAngleY = 0F;
        LJaw2.rotateAngleZ = 0F;

        TeethA = new ModelRenderer(this, 8, 11);
        TeethA.addBox(1.6F, 0F, -16F, 0, 1, 4);
        TeethA.setRotationPoint(0F, 18F, -8F);
        TeethA.rotateAngleX = 0F;
        TeethA.rotateAngleY = 0F;
        TeethA.rotateAngleZ = 0F;

        TeethB = new ModelRenderer(this, 8, 11);
        TeethB.addBox(-1.6F, 0F, -16F, 0, 1, 4);
        TeethB.setRotationPoint(0F, 18F, -8F);
        TeethB.rotateAngleX = 0F;
        TeethB.rotateAngleY = 0F;
        TeethB.rotateAngleZ = 0F;

        TeethC = new ModelRenderer(this, 6, 9);
        TeethC.addBox(2.1F, 0F, -12F, 0, 1, 6);
        TeethC.setRotationPoint(0F, 18F, -8F);
        TeethC.rotateAngleX = 0F;
        TeethC.rotateAngleY = 0F;
        TeethC.rotateAngleZ = 0F;

        TeethD = new ModelRenderer(this, 6, 9);
        TeethD.addBox(-2.1F, 0F, -12F, 0, 1, 6);
        TeethD.setRotationPoint(0F, 18F, -8F);
        TeethD.rotateAngleX = 0F;
        TeethD.rotateAngleY = 0F;
        TeethD.rotateAngleZ = 0F;

        Leg1A.rotateAngleX = 0F;
        Leg1A.rotateAngleY = 0F;
        Leg1A.rotateAngleZ = 0F;

        Leg2A.rotateAngleX = 0F;
        Leg2A.rotateAngleY = 0F;
        Leg2A.rotateAngleZ = 0F;

        Leg3A.rotateAngleX = 0F;
        Leg3A.rotateAngleY = 0F;
        Leg3A.rotateAngleZ = 0F;

        Leg4A.rotateAngleX = 0F;
        Leg4A.rotateAngleY = 0F;
        Leg4A.rotateAngleZ = 0F;

        TeethF = new ModelRenderer(this, 19, 21);
        TeethF.addBox(-1F, 0F, -16.1F, 2, 1, 0);
        TeethF.setRotationPoint(0F, 18F, -8F);

        Spike0 = new ModelRenderer(this, 44, 16);
        Spike0.addBox(-1F, -1F, 23F, 0, 2, 4);
        Spike0.setRotationPoint(0F, 17F, 12F);

        Spike1 = new ModelRenderer(this, 44, 16);
        Spike1.addBox(1F, -1F, 23F, 0, 2, 4);
        Spike1.setRotationPoint(0F, 17F, 12F);

        Spike2 = new ModelRenderer(this, 44, 16);
        Spike2.addBox(-1.5F, -1.5F, 17F, 0, 2, 4);
        Spike2.setRotationPoint(0F, 17F, 12F);

        Spike3 = new ModelRenderer(this, 44, 16);
        Spike3.addBox(1.5F, -1.5F, 17F, 0, 2, 4);
        Spike3.setRotationPoint(0F, 17F, 12F);

        Spike4 = new ModelRenderer(this, 44, 16);
        Spike4.addBox(-2F, -2F, 12F, 0, 2, 4);
        Spike4.setRotationPoint(0F, 17F, 12F);

        Spike5 = new ModelRenderer(this, 44, 16);
        Spike5.addBox(2F, -2F, 12F, 0, 2, 4);
        Spike5.setRotationPoint(0F, 17F, 12F);

        Spike6 = new ModelRenderer(this, 44, 16);
        Spike6.addBox(-2.5F, -2F, 8F, 0, 2, 4);
        Spike6.setRotationPoint(0F, 17F, 12F);

        Spike7 = new ModelRenderer(this, 44, 16);
        Spike7.addBox(2.5F, -2F, 8F, 0, 2, 4);
        Spike7.setRotationPoint(0F, 17F, 12F);

        Spike8 = new ModelRenderer(this, 44, 16);
        Spike8.addBox(-3F, -2.5F, 4F, 0, 2, 4);
        Spike8.setRotationPoint(0F, 17F, 12F);

        Spike9 = new ModelRenderer(this, 44, 16);
        Spike9.addBox(3F, -2.5F, 4F, 0, 2, 4);
        Spike9.setRotationPoint(0F, 17F, 12F);

        Spike10 = new ModelRenderer(this, 44, 16);
        Spike10.addBox(3.5F, -2.5F, 0F, 0, 2, 4);
        Spike10.setRotationPoint(0F, 17F, 12F);

        Spike11 = new ModelRenderer(this, 44, 16);
        Spike11.addBox(-3.5F, -2.5F, 0F, 0, 2, 4);
        Spike11.setRotationPoint(0F, 17F, 12F);

        SpikeBack0 = new ModelRenderer(this, 44, 10);
        SpikeBack0.addBox(0F, 0F, 0F, 0, 2, 8);
        SpikeBack0.setRotationPoint(0F, 14F, 3F);

        SpikeBack1 = new ModelRenderer(this, 44, 10);
        SpikeBack1.addBox(0F, 0F, 0F, 0, 2, 8);
        SpikeBack1.setRotationPoint(0F, 14F, -6F);

        SpikeBack2 = new ModelRenderer(this, 44, 10);
        SpikeBack2.addBox(0F, 0F, 0F, 0, 2, 8);
        SpikeBack2.setRotationPoint(4F, 14F, -8F);

        SpikeBack3 = new ModelRenderer(this, 44, 10);
        SpikeBack3.addBox(0F, 0F, 0F, 0, 2, 8);
        SpikeBack3.setRotationPoint(-4F, 14F, -8F);

        SpikeBack4 = new ModelRenderer(this, 44, 10);
        SpikeBack4.addBox(0F, 0F, 0F, 0, 2, 8);
        SpikeBack4.setRotationPoint(-4F, 14F, 1F);

        SpikeBack5 = new ModelRenderer(this, 44, 10);
        SpikeBack5.addBox(0F, 0F, 0F, 0, 2, 8);
        SpikeBack5.setRotationPoint(4F, 14F, 1F);

        SpikeEye = new ModelRenderer(this, 44, 14);
        SpikeEye.addBox(-3F, -3F, -6F, 0, 1, 2);
        SpikeEye.setRotationPoint(0F, 18F, -8F);

        SpikeEye1 = new ModelRenderer(this, 44, 14);
        SpikeEye1.addBox(3F, -3F, -6F, 0, 1, 2);
        SpikeEye1.setRotationPoint(0F, 18F, -8F);

        TeethA1 = new ModelRenderer(this, 52, 12);
        TeethA1.addBox(1.4F, 1F, -16.4F, 0, 1, 4);
        TeethA1.setRotationPoint(0F, 18F, -8F);

        TeethB1 = new ModelRenderer(this, 52, 12);
        TeethB1.addBox(-1.4F, 1F, -16.4F, 0, 1, 4);
        TeethB1.setRotationPoint(0F, 18F, -8F);

        TeethC1 = new ModelRenderer(this, 50, 10);
        TeethC1.addBox(1.9F, 1F, -12.5F, 0, 1, 6);
        TeethC1.setRotationPoint(0F, 18F, -8F);

        TeethD1 = new ModelRenderer(this, 50, 10);
        TeethD1.addBox(-1.9F, 1F, -12.5F, 0, 1, 6);
        TeethD1.setRotationPoint(0F, 18F, -8F);
    }

    public void model2()
    {

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        LJaw.render(f5);
        TailA.render(f5);
        TailB.render(f5);
        TailC.render(f5);
        UJaw.render(f5);
        Head.render(f5);
        Body.render(f5);
        Leg1.render(f5);
        Leg3.render(f5);
        Leg2.render(f5);
        Leg4.render(f5);
        TailD.render(f5);
        Leg1A.render(f5);
        Leg2A.render(f5);
        Leg3A.render(f5);
        Leg4A.render(f5);
        UJaw2.render(f5);
        LJaw2.render(f5);
        TeethA.render(f5);
        TeethB.render(f5);
        TeethC.render(f5);
        TeethD.render(f5);
        TeethF.render(f5);
        Spike0.render(f5);
        Spike1.render(f5);
        Spike2.render(f5);
        Spike3.render(f5);
        Spike4.render(f5);
        Spike5.render(f5);
        Spike6.render(f5);
        Spike7.render(f5);
        Spike8.render(f5);
        Spike9.render(f5);
        Spike10.render(f5);
        Spike11.render(f5);
        SpikeBack0.render(f5);
        SpikeBack1.render(f5);
        SpikeBack2.render(f5);
        SpikeBack3.render(f5);
        SpikeBack4.render(f5);
        SpikeBack5.render(f5);
        SpikeEye.render(f5);
        SpikeEye1.render(f5);
        TeethA1.render(f5);
        TeethB1.render(f5);
        TeethC1.render(f5);
        TeethD1.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        Head.rotateAngleX = f4 / 57.29578F;
        Head.rotateAngleY = f3 / 57.29578F;
        SpikeEye.rotateAngleX = Head.rotateAngleX;
        SpikeEye.rotateAngleY = Head.rotateAngleY;
        SpikeEye1.rotateAngleX = Head.rotateAngleX;
        SpikeEye1.rotateAngleY = Head.rotateAngleY;

        //LJaw.rotateAngleX = Head.rotateAngleX;
        LJaw.rotateAngleY = Head.rotateAngleY;
        LJaw2.rotateAngleY = Head.rotateAngleY;
        //UJaw.rotateAngleX = Head.rotateAngleX;
        UJaw.rotateAngleY = Head.rotateAngleY;
        UJaw2.rotateAngleY = Head.rotateAngleY;
        if (swimming)
        {
            //Leg1.setRotationPoint(9F, 18F, 0F);
            Leg1.rotationPointX = 9F;
            Leg1.rotationPointY = 18F;
            Leg1.rotationPointZ = 0F;
            Leg1.rotateAngleX = 0F;
            Leg1.rotateAngleY = -3.14159F;
            //Leg2.setRotationPoint(-9F, 18F, 0F);
            Leg2.rotationPointX = -9F;
            Leg2.rotationPointY = 18F;
            Leg2.rotationPointZ = 0F;
            Leg2.rotateAngleX = 0F;
            Leg2.rotateAngleY = -3.14159F;
            //Leg3.setRotationPoint(8F, 18F, 12F);
            Leg3.rotationPointX = 8F;
            Leg3.rotationPointY = 18F;
            Leg3.rotationPointZ = 12F;
            Leg3.rotateAngleX = 0F;
            Leg3.rotateAngleY = -3.14159F;
            //Leg4.setRotationPoint(-8F, 18F, 12F);
            Leg4.rotationPointX = -8F;
            Leg4.rotationPointY = 18F;
            Leg4.rotationPointZ = 12F;
            Leg4.rotateAngleX = 0F;
            Leg4.rotateAngleY = -3.14159F;

            //Leg1A.setRotationPoint(5F, 19F, -3F);
            Leg1A.rotateAngleX = 1.5708F;
            Leg1A.rotationPointX = 5F;
            Leg1A.rotationPointY = 19F;
            Leg1A.rotationPointZ = -3F;

            //Leg2A.setRotationPoint(-5F, 19F, -3F);
            Leg2A.rotateAngleX = 1.5708F;
            Leg2A.rotationPointX = -5F;
            Leg2A.rotationPointY = 19F;
            Leg2A.rotationPointZ = -3F;

            //Leg3A.setRotationPoint(5F, 19F, 9F);
            Leg3A.rotateAngleX = 1.5708F;
            Leg3A.rotationPointX = 5F;
            Leg3A.rotationPointY = 19F;
            Leg3A.rotationPointZ = 9F;

            //Leg4A.setRotationPoint(-5F, 19F, 9F);
            Leg4A.rotateAngleX = 1.5708F;
            Leg4A.rotationPointX = -5F;
            Leg4A.rotationPointY = 19F;
            Leg4A.rotationPointZ = 9F;

            Leg1.rotateAngleZ = 0F;
            Leg1A.rotateAngleZ = 0F;
            Leg3.rotateAngleZ = 0F;
            Leg3A.rotateAngleZ = 0F;

            Leg2.rotateAngleZ = 0F;
            Leg2A.rotateAngleZ = 0F;
            Leg4.rotateAngleZ = 0F;
            Leg4A.rotateAngleZ = 0F;

        }
        else if (resting)
        {
            //Leg1.setRotationPoint(6F, 17F, -6F);
            Leg1.rotationPointX = 6F;
            Leg1.rotationPointY = 17F;
            Leg1.rotationPointZ = -6F;
            Leg1.rotateAngleX = 0F;
            Leg1.rotateAngleY = -0.7854F;

            //Leg2.setRotationPoint(-6F, 17F, -6F);
            Leg2.rotateAngleY = 0.7854F;
            Leg2.rotationPointX = -6F;
            Leg2.rotationPointY = 17F;
            Leg2.rotationPointZ = -6F;
            Leg2.rotateAngleX = 0F;

            //Leg3.setRotationPoint(7F, 17F, 7F);
            Leg3.rotateAngleY = -0.7854F;
            Leg3.rotationPointX = 7F;
            Leg3.rotationPointY = 17F;
            Leg3.rotationPointZ = 7F;
            Leg3.rotateAngleX = 0F;

            Leg4.setRotationPoint(-7F, 17F, 7F);
            Leg4.rotateAngleY = 0.7854F;
            Leg4.rotationPointX = -7F;
            Leg4.rotationPointY = 17F;
            Leg4.rotationPointZ = 7F;
            Leg4.rotateAngleX = 0F;

            //Leg1A.setRotationPoint(5F, 17F, -3F);
            Leg1A.rotationPointX = 5F;
            Leg1A.rotationPointY = 17F;
            Leg1A.rotationPointZ = -3F;
            Leg1A.rotateAngleX = 0F;

            //Leg2A.setRotationPoint(-5F, 17F, -3F);
            Leg2A.rotationPointX = -5F;
            Leg2A.rotationPointY = 17F;
            Leg2A.rotationPointZ = -3F;
            Leg2A.rotateAngleX = 0F;

            //Leg3A.setRotationPoint(5F, 17F, 9F);
            Leg3A.rotationPointX = 5F;
            Leg3A.rotationPointY = 17F;
            Leg3A.rotationPointZ = 9F;
            Leg3A.rotateAngleX = 0F;

            //Leg4A.setRotationPoint(-5F, 17F, 9F);
            Leg4A.rotationPointX = -5F;
            Leg4A.rotationPointY = 17F;
            Leg4A.rotationPointZ = 9F;
            Leg4A.rotateAngleX = 0F;

            Leg1.rotateAngleZ = 0F;
            Leg1A.rotateAngleZ = 0F;
            Leg3.rotateAngleZ = 0F;
            Leg3A.rotateAngleZ = 0F;

            Leg2.rotateAngleZ = 0F;
            Leg2A.rotateAngleZ = 0F;
            Leg4.rotateAngleZ = 0F;
            Leg4A.rotateAngleZ = 0F;
        }
        else
        {
            //Leg1.setRotationPoint(5F, 19F, -3F);
            Leg1.rotationPointX = 5F;
            Leg1.rotationPointY = 19F;
            Leg1.rotationPointZ = -3F;
            //Leg2.setRotationPoint(-5F, 19F, -3F);
            Leg2.rotationPointX = -5F;
            Leg2.rotationPointY = 19F;
            Leg2.rotationPointZ = -3F;
            //Leg3.setRotationPoint(5F, 19F, 9F);
            Leg3.rotationPointX = 5F;
            Leg3.rotationPointY = 19F;
            Leg3.rotationPointZ = 9F;
            //Leg4.setRotationPoint(-5F, 19F, 9F);
            Leg4.rotationPointX = -5F;
            Leg4.rotationPointY = 19F;
            Leg4.rotationPointZ = 9F;

            //Leg1A.setRotationPoint(5F, 19F, -3F);
            Leg1A.rotationPointX = 5F;
            Leg1A.rotationPointY = 19F;
            Leg1A.rotationPointZ = -3F;

            //Leg2A.setRotationPoint(-5F, 19F, -3F);
            Leg2A.rotationPointX = -5F;
            Leg2A.rotationPointY = 19F;
            Leg2A.rotationPointZ = -3F;

            //Leg3A.setRotationPoint(5F, 19F, 9F);
            Leg3A.rotationPointX = 5F;
            Leg3A.rotationPointY = 19F;
            Leg3A.rotationPointZ = 9F;

            //Leg4A.setRotationPoint(-5F, 19F, 9F);
            Leg4A.rotationPointX = -5F;
            Leg4A.rotationPointY = 19F;
            Leg4A.rotationPointZ = 9F;

            Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
            Leg2.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
            Leg3.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
            Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;

            Leg1.rotateAngleY = 0F;
            Leg2.rotateAngleY = 0F;
            Leg3.rotateAngleY = 0F;
            Leg4.rotateAngleY = 0F;

            Leg1A.rotateAngleX = Leg1.rotateAngleX;
            Leg2A.rotateAngleX = Leg2.rotateAngleX;
            Leg3A.rotateAngleX = Leg3.rotateAngleX;
            Leg4A.rotateAngleX = Leg4.rotateAngleX;

            float latrot = MathHelper.cos(f / (1.919107651F * 1)) * 0.261799387799149F * f1 * 5;
            Leg1.rotateAngleZ = latrot;
            Leg1A.rotateAngleZ = latrot;
            Leg4.rotateAngleZ = -latrot;
            Leg4A.rotateAngleZ = -latrot;

            Leg3.rotateAngleZ = latrot;
            Leg3A.rotateAngleZ = latrot;

            Leg2.rotateAngleZ = -latrot;
            Leg2A.rotateAngleZ = -latrot;

            //Leg1.rotateAngleZ = MathHelper.cos(f / (1.919107651F * 1 )) * 0.261799387799149F * f1 *10;
            //Leg1A.rotateAngleZ = MathHelper.cos(f / (1.919107651F * 1 )) * 0.261799387799149F * f1 *10;

            //LArm.rotateAngleY = MathHelper.cos(f / (1.919107651F * 1 )) * -0.349065850398866F * f1 + 0.785398163397448F ;

        }
        TailA.rotateAngleY = MathHelper.cos(f * 0.6662F) * 0.7F * f1;
        TailB.rotateAngleY = TailA.rotateAngleY;
        TailC.rotateAngleY = TailA.rotateAngleY;
        TailD.rotateAngleY = TailA.rotateAngleY;
        Spike0.rotateAngleY = TailA.rotateAngleY;
        Spike1.rotateAngleY = TailA.rotateAngleY;
        Spike2.rotateAngleY = TailA.rotateAngleY;
        Spike3.rotateAngleY = TailA.rotateAngleY;
        Spike4.rotateAngleY = TailA.rotateAngleY;
        Spike5.rotateAngleY = TailA.rotateAngleY;
        Spike6.rotateAngleY = TailA.rotateAngleY;
        Spike7.rotateAngleY = TailA.rotateAngleY;
        Spike8.rotateAngleY = TailA.rotateAngleY;
        Spike9.rotateAngleY = TailA.rotateAngleY;
        Spike10.rotateAngleY = TailA.rotateAngleY;
        Spike11.rotateAngleY = TailA.rotateAngleY;
        float f25 = biteProgress;
        float f26 = f25;
        if (f25 >= 0.5F)
        {
            f26 = (0.5F - (f25 - 0.5F));
        }
        UJaw.rotateAngleX = Head.rotateAngleX - f26;
        UJaw2.rotateAngleX = UJaw.rotateAngleX;
        LJaw.rotateAngleX = Head.rotateAngleX + (f26 / 2);
        LJaw2.rotateAngleX = LJaw.rotateAngleX;
        TeethA.rotateAngleX = LJaw.rotateAngleX;
        TeethB.rotateAngleX = LJaw.rotateAngleX;
        TeethC.rotateAngleX = LJaw.rotateAngleX;
        TeethD.rotateAngleX = LJaw.rotateAngleX;
        TeethF.rotateAngleX = LJaw.rotateAngleX;
        TeethA.rotateAngleY = LJaw.rotateAngleY;
        TeethB.rotateAngleY = LJaw.rotateAngleY;
        TeethC.rotateAngleY = LJaw.rotateAngleY;
        TeethD.rotateAngleY = LJaw.rotateAngleY;
        TeethF.rotateAngleY = LJaw.rotateAngleY;
        TeethA1.rotateAngleX = UJaw.rotateAngleX;
        TeethB1.rotateAngleX = UJaw.rotateAngleX;
        TeethC1.rotateAngleX = UJaw.rotateAngleX;
        TeethD1.rotateAngleX = UJaw.rotateAngleX;
        TeethA1.rotateAngleY = UJaw.rotateAngleY;
        TeethB1.rotateAngleY = UJaw.rotateAngleY;
        TeethC1.rotateAngleY = UJaw.rotateAngleY;
        TeethD1.rotateAngleY = UJaw.rotateAngleY;

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
