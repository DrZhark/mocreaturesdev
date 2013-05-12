package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;

@SideOnly(Side.CLIENT)
public class MoCModelDragonfly extends ModelBase {

    ModelRenderer Abdomen;
    ModelRenderer FrontLegs;
    ModelRenderer RAntenna;
    ModelRenderer LAntenna;
    ModelRenderer RearLegs;
    ModelRenderer MidLegs;
    ModelRenderer Mouth;
    ModelRenderer WingRearRight;
    ModelRenderer Thorax;
    ModelRenderer WingFrontRight;
    ModelRenderer WingFrontLeft;
    ModelRenderer WingRearLeft;
    ModelRenderer Head;

    public MoCModelDragonfly()
    {
        textureWidth = 32;
        textureHeight = 32;

        Head = new ModelRenderer(this, 0, 4);
        Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        Head.setRotationPoint(0F, 21F, -2F);
        setRotation(Head, -2.171231F, 0F, 0F);

        RAntenna = new ModelRenderer(this, 0, 7);
        RAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        RAntenna.setRotationPoint(-0.5F, 19.7F, -2.3F);
        setRotation(RAntenna, -1.041001F, 0.7853982F, 0F);

        LAntenna = new ModelRenderer(this, 4, 7);
        LAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        LAntenna.setRotationPoint(0.5F, 19.7F, -2.3F);
        setRotation(LAntenna, -1.041001F, -0.7853982F, 0F);

        Mouth = new ModelRenderer(this, 0, 11);
        Mouth.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        Mouth.setRotationPoint(0F, 21.1F, -2.3F);
        setRotation(Mouth, -2.171231F, 0F, 0F);

        Thorax = new ModelRenderer(this, 0, 0);
        Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        Thorax.setRotationPoint(0F, 20F, -1F);

        Abdomen = new ModelRenderer(this, 8, 0);
        Abdomen.addBox(-0.5F, 0F, -1F, 1, 7, 1);
        Abdomen.setRotationPoint(0F, 20.5F, 0F);
        setRotation(Abdomen, 1.427659F, 0F, 0F);

        FrontLegs = new ModelRenderer(this, 0, 8);
        FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        FrontLegs.setRotationPoint(0F, 21.5F, -1.8F);
        setRotation(FrontLegs, 0.1487144F, 0F, 0F);

        MidLegs = new ModelRenderer(this, 4, 8);
        MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        MidLegs.setRotationPoint(0F, 22F, -1.2F);
        setRotation(MidLegs, 0.5948578F, 0F, 0F);

        RearLegs = new ModelRenderer(this, 8, 8);
        RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        RearLegs.setRotationPoint(0F, 22F, -0.4F);
        setRotation(RearLegs, 1.070744F, 0F, 0F);

        WingFrontRight = new ModelRenderer(this, 0, 28);
        WingFrontRight.addBox(-7F, 0F, -1F, 7, 0, 2);
        WingFrontRight.setRotationPoint(-1F, 20F, -1F);
        setRotation(WingFrontRight, 0F, -0.1396263F, 0.0872665F);

        WingFrontLeft = new ModelRenderer(this, 0, 30);
        WingFrontLeft.addBox(0F, 0F, -1F, 7, 0, 2);
        WingFrontLeft.setRotationPoint(1F, 20F, -1F);
        setRotation(WingFrontLeft, 0F, 0.1396263F, -0.0872665F);

        WingRearRight = new ModelRenderer(this, 0, 24);
        WingRearRight.addBox(-7F, 0F, -1F, 7, 0, 2);
        WingRearRight.setRotationPoint(-1F, 20F, -1F);
        setRotation(WingRearRight, 0F, 0.3490659F, -0.0872665F);

        WingRearLeft = new ModelRenderer(this, 0, 26);
        WingRearLeft.addBox(0F, 0F, -1F, 7, 0, 2);
        WingRearLeft.setRotationPoint(1F, 20F, -1F);
        setRotation(WingRearLeft, 0F, -0.3490659F, 0.0872665F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);

        MoCEntityDragonfly dragonfly = (MoCEntityDragonfly) entity;
        //boolean onGround = dragonfly.onGround;
        boolean isFlying = (dragonfly.getIsFlying() || dragonfly.isOnAir());// dragonfly.getIsFlying();
        setRotationAngles(f, f1, f2, f3, f4, f5, isFlying);
        Head.render(f5);
        Abdomen.render(f5);
        FrontLegs.render(f5);
        RAntenna.render(f5);
        LAntenna.render(f5);
        RearLegs.render(f5);
        MidLegs.render(f5);
        Mouth.render(f5);
        Thorax.render(f5);

        GL11.glPushMatrix();
        GL11.glEnable(3042 /*GL_BLEND*/);
        float transparency = 0.6F;
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        //GL11.glScalef(1.3F, 1.0F, 1.3F);
        WingRearRight.render(f5);
        WingFrontRight.render(f5);
        WingFrontLeft.render(f5);
        WingRearLeft.render(f5);
        GL11.glDisable(3042/*GL_BLEND*/);
        GL11.glPopMatrix();

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean flying)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);

        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */

        float WingRot = 0F;
        float legMov = 0F;
        float legMovB = 0F;

        if (flying)
        {
            WingRot = MathHelper.cos((f2 * 2.0F)) * 0.5F;
            legMov = (f1 * 1.5F);
            legMovB = legMov;
        }
        else
        {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }

        WingFrontRight.rotateAngleZ = WingRot;
        WingRearLeft.rotateAngleZ = WingRot;

        WingFrontLeft.rotateAngleZ = -WingRot;
        WingRearRight.rotateAngleZ = -WingRot;

        FrontLegs.rotateAngleX = 0.1487144F + legMov;
        MidLegs.rotateAngleX = 0.5948578F + legMovB;
        RearLegs.rotateAngleX = 1.070744F + legMov;
    }

}
