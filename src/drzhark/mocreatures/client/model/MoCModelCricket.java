package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;

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

    public MoCModelCricket()
    {
        textureWidth = 32;
        textureHeight = 32;

        Head = new ModelRenderer(this, 0, 4);
        Head.addBox(-0.5F, 0F, -1F, 1, 1, 2);
        Head.setRotationPoint(0F, 22.5F, -2F);
        setRotation(Head, -2.171231F, 0F, 0F);

        Antenna = new ModelRenderer(this, 0, 11);
        Antenna.addBox(-1F, 0F, 0F, 2, 2, 0);
        Antenna.setRotationPoint(0F, 22.5F, -3F);
        setRotation(Antenna, -2.736346F, 0F, 0F);

        AntennaB = new ModelRenderer(this, 0, 9);
        AntennaB.addBox(-1F, 0F, 0F, 2, 2, 0);
        AntennaB.setRotationPoint(0F, 20.7F, -3.8F);
        setRotation(AntennaB, 2.88506F, 0F, 0F);

        Thorax = new ModelRenderer(this, 0, 0);
        Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        Thorax.setRotationPoint(0F, 21F, -1F);
        setRotation(Thorax, 0F, 0F, 0F);

        Abdomen = new ModelRenderer(this, 8, 0);
        Abdomen.addBox(-1F, 0F, -1F, 2, 3, 2);
        Abdomen.setRotationPoint(0F, 22F, 0F);
        setRotation(Abdomen, 1.427659F, 0F, 0F);

        TailA = new ModelRenderer(this, 4, 9);
        TailA.addBox(-1F, 0F, 0F, 2, 3, 0);
        TailA.setRotationPoint(0F, 22F, 2.8F);
        setRotation(TailA, 1.308687F, 0F, 0F);

        TailB = new ModelRenderer(this, 4, 7);
        TailB.addBox(-1F, 0F, 0F, 2, 2, 0);
        TailB.setRotationPoint(0F, 23F, 2.8F);
        setRotation(TailB, 1.665602F, 0F, 0F);

        FrontLegs = new ModelRenderer(this, 0, 7);
        FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        FrontLegs.setRotationPoint(0F, 23F, -1.8F);
        setRotation(FrontLegs, -0.8328009F, 0F, 0F);

        MidLegs = new ModelRenderer(this, 0, 13);
        MidLegs.addBox(-2F, 0F, 0F, 4, 2, 0);
        MidLegs.setRotationPoint(0F, 23F, -1.2F);
        setRotation(MidLegs, 1.070744F, 0F, 0F);

        ThighLeft = new ModelRenderer(this, 8, 5);
        ThighLeft.addBox(0F, -3F, 0F, 1, 3, 1);
        ThighLeft.setRotationPoint(0.5F, 23F, 0F);
        setRotation(ThighLeft, -0.4886922F, 0.2617994F, 0F);

        ThighLeftB = new ModelRenderer(this, 8, 5);
        ThighLeftB.addBox(0F, -3F, 0F, 1, 3, 1);
        ThighLeftB.setRotationPoint(0.5F, 22.5F, 0F);
        setRotation(ThighLeftB, -1.762782F, 0F, 0F);

        ThighRight = new ModelRenderer(this, 12, 5);
        ThighRight.addBox(-1F, -3F, 0F, 1, 3, 1);
        ThighRight.setRotationPoint(-0.5F, 23F, 0F);
        setRotation(ThighRight, -0.4886922F, -0.2617994F, 0F);

        ThighRightB = new ModelRenderer(this, 12, 5);
        ThighRightB.addBox(-1F, -3F, 0F, 1, 3, 1);
        ThighRightB.setRotationPoint(-0.5F, 22.5F, 0F);
        setRotation(ThighRightB, -1.762782F, 0F, 0F);

        LegLeft = new ModelRenderer(this, 0, 15);
        LegLeft.addBox(0F, 0F, -1F, 0, 3, 2);
        LegLeft.setRotationPoint(2F, 21F, 2.5F);

        LegLeftB = new ModelRenderer(this, 4, 15);
        LegLeftB.addBox(0F, 0F, -1F, 0, 3, 2);
        LegLeftB.setRotationPoint(1.5F, 23F, 2.9F);
        setRotation(LegLeftB, 1.249201F, 0F, 0F);

        LegRight = new ModelRenderer(this, 4, 15);
        LegRight.addBox(0F, 0F, -1F, 0, 3, 2);
        LegRight.setRotationPoint(-2F, 21F, 2.5F);

        LegRightB = new ModelRenderer(this, 4, 15);
        LegRightB.addBox(0F, 0F, -1F, 0, 3, 2);
        LegRightB.setRotationPoint(-1.5F, 23F, 2.9F);
        setRotation(LegRightB, 1.249201F, 0F, 0F);

        LeftWing = new ModelRenderer(this, 0, 30);
        LeftWing.addBox(0F, 0F, -1F, 6, 0, 2);
        LeftWing.setRotationPoint(0F, 20.9F, -1F);
        setRotation(LeftWing, 0F, -0.1919862F, 0F);

        RightWing = new ModelRenderer(this, 0, 28);
        RightWing.addBox(-6F, 0F, -1F, 6, 0, 2);
        RightWing.setRotationPoint(0F, 20.9F, -1F);
        setRotation(RightWing, 0F, 0.1919862F, 0F);

        FoldedWings = new ModelRenderer(this, 0, 26);
        FoldedWings.addBox(0F, 0F, -1F, 6, 0, 2);
        FoldedWings.setRotationPoint(0F, 20.9F, -2F);
        setRotation(FoldedWings, 0F, -1.570796F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityCricket entitycricket = (MoCEntityCricket) entity;
        //boolean onGround = entitycricket.onGround;
        boolean isFlying = (entitycricket.getIsFlying() || entitycricket.isOnAir());// entitycricket.getIsFlying();
        setRotationAngles(f, f1, f2, f3, f4, f5, isFlying);
        Head.render(f5);
        Antenna.render(f5);
        AntennaB.render(f5);
        Thorax.render(f5);
        Abdomen.render(f5);
        TailA.render(f5);
        TailB.render(f5);
        FrontLegs.render(f5);
        MidLegs.render(f5);

        if (!isFlying)
        {

            ThighLeft.render(f5);
            ThighRight.render(f5);
            LegLeft.render(f5);
            LegRight.render(f5);
            FoldedWings.render(f5);

        }
        else
        {

            ThighLeftB.render(f5);
            ThighRightB.render(f5);
            LegLeftB.render(f5);
            LegRightB.render(f5);
            GL11.glPushMatrix();
            GL11.glEnable(3042 /*GL_BLEND*/);
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            LeftWing.render(f5);
            RightWing.render(f5);
            GL11.glDisable(3042/*GL_BLEND*/);
            GL11.glPopMatrix();

        }

        /*if (isFlying)
        {
            
        }else
        {
            
        }*/
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean isFlying)
    {

        float legMov = 0F;
        float legMovB = 0F;

        float frontLegAdj = 0F;

        if (isFlying)
        {
            float WingRot = MathHelper.cos((f2 * 2.0F)) * 0.7F;
            RightWing.rotateAngleZ = WingRot;
            LeftWing.rotateAngleZ = -WingRot;
            legMov = (f1 * 1.5F);
            legMovB = legMov;
            frontLegAdj = 1.4F;

        }
        else
        {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }

        AntennaB.rotateAngleX = 2.88506F - legMov;

        FrontLegs.rotateAngleX = -0.8328009F + frontLegAdj + legMov;
        MidLegs.rotateAngleX = 1.070744F + legMovB;

    }

}
