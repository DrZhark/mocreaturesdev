package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCModelButterfly extends ModelBase {

    ModelRenderer Abdomen;
    ModelRenderer FrontLegs;
    ModelRenderer RightAntenna;
    ModelRenderer LeftAntenna;
    ModelRenderer RearLegs;
    ModelRenderer MidLegs;
    ModelRenderer Head;
    ModelRenderer Thorax;
    ModelRenderer WingRight;
    ModelRenderer WingLeft;
    ModelRenderer Mouth;
    ModelRenderer WingLeftFront;
    ModelRenderer WingRightFront;
    ModelRenderer WingRightBack;
    ModelRenderer WingLeftBack;

    public MoCModelButterfly() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 11);
        this.Head.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Head.setRotationPoint(0F, 21.9F, -1.3F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 0, 8);
        this.Mouth.addBox(0F, 0F, 0F, 1, 2, 0);
        this.Mouth.setRotationPoint(-0.2F, 22F, -2.5F);
        setRotation(this.Mouth, 0.6548599F, 0F, 0F);

        this.RightAntenna = new ModelRenderer(this, 0, 7);
        this.RightAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.RightAntenna.setRotationPoint(-0.5F, 21.7F, -2.3F);
        setRotation(this.RightAntenna, -1.041001F, 0.7853982F, 0F);

        this.LeftAntenna = new ModelRenderer(this, 4, 7);
        this.LeftAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.LeftAntenna.setRotationPoint(0.5F, 21.7F, -2.3F);
        setRotation(this.LeftAntenna, -1.041001F, -0.7853982F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-0.5F, 1.5F, -1F, 1, 1, 2);
        this.Thorax.setRotationPoint(0F, 20F, -1F);

        this.Abdomen = new ModelRenderer(this, 8, 1);
        this.Abdomen.addBox(-0.5F, 0F, -1F, 1, 3, 1);
        this.Abdomen.setRotationPoint(0F, 21.5F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 8);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.FrontLegs.setRotationPoint(0F, 21.5F, -1.8F);
        setRotation(this.FrontLegs, 0.1487144F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 4, 8);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.MidLegs.setRotationPoint(0F, 22F, -1.2F);
        setRotation(this.MidLegs, 0.5948578F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 0, 8);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.RearLegs.setRotationPoint(0F, 22.5F, -0.4F);
        setRotation(this.RearLegs, 1.070744F, 0F, 0F);

        this.WingLeftFront = new ModelRenderer(this, 4, 20);
        this.WingLeftFront.addBox(0F, 0F, -4F, 8, 0, 6);
        this.WingLeftFront.setRotationPoint(0.3F, 21.4F, -1F);

        this.WingLeft = new ModelRenderer(this, 4, 26);
        this.WingLeft.addBox(0F, 0F, -1F, 8, 0, 6);
        this.WingLeft.setRotationPoint(0.3F, 21.5F, -0.5F);

        this.WingLeftBack = new ModelRenderer(this, 4, 0);
        this.WingLeftBack.addBox(0F, 0F, -1F, 5, 0, 8);
        this.WingLeftBack.setRotationPoint(0.3F, 21.2F, -1F);
        setRotation(this.WingLeftBack, 0F, 0F, 0.5934119F);

        /*
         * WingRight = new ModelRenderer(this, 4, 14); WingRight.addBox(-8F, 0F,
         * 0F, 8, 0, 6); WingRight.setRotationPoint(-0.3F, 21.5F, 0F); WingLeft
         * = new ModelRenderer(this, 4, 26); WingLeft.addBox(0F, 0F, 0F, 8, 0,
         * 6); WingLeft.setRotationPoint(0.3F, 21.5F, 0F);
         */

        this.WingRightFront = new ModelRenderer(this, 4, 8);
        this.WingRightFront.addBox(-8F, 0F, -4F, 8, 0, 6);
        this.WingRightFront.setRotationPoint(-0.3F, 21.4F, -1F);

        this.WingRight = new ModelRenderer(this, 4, 14);
        this.WingRight.addBox(-8F, 0F, -1F, 8, 0, 6);
        this.WingRight.setRotationPoint(-0.3F, 21.5F, -0.5F);

        this.WingRightBack = new ModelRenderer(this, 14, 0);
        this.WingRightBack.addBox(-5F, 0F, -1F, 5, 0, 8);
        this.WingRightBack.setRotationPoint(0.3F, 21.2F, -1F);
        setRotation(this.WingRightBack, 0F, 0F, -0.5934119F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityButterfly butterfly = (MoCEntityButterfly) entity;
        boolean flying = (butterfly.getIsFlying() || butterfly.motionY < -0.1D);
        setRotationAngles(f, f1, f2, f3, f4, f5, !flying);
        this.Abdomen.render(f5);
        this.FrontLegs.render(f5);
        this.RightAntenna.render(f5);
        this.LeftAntenna.render(f5);
        this.RearLegs.render(f5);
        this.MidLegs.render(f5);
        this.Head.render(f5);
        this.Thorax.render(f5);

        this.Mouth.render(f5);

        GL11.glPushMatrix();
        GL11.glEnable(3042 /* GL_BLEND */);
        float transparency = 0.8F;
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        //GL11.glScalef(1.3F, 1.0F, 1.3F);
        this.WingRight.render(f5);
        this.WingLeft.render(f5);
        this.WingRightFront.render(f5);
        this.WingLeftFront.render(f5);
        this.WingRightBack.render(f5);
        this.WingLeftBack.render(f5);
        GL11.glDisable(3042/* GL_BLEND */);
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean onGround) {

        /**
         * buttefly to have two / 3 movs: 1 slow movement when idle on ground
         * has to be random from closing up to horizontal 2 fast wing flapping
         * flying movement, short range close to 0 degree RLegXRot =
         * MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
         */

        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */

        float f2a = f2 % 100F;
        float WingRot = 0F;
        float legMov = 0F;
        float legMovB = 0F;

        if (!onGround) //flying
        {
            WingRot = MathHelper.cos((f2 * 0.9F)) * 0.9F;

            /*
             * WingRot = MathHelper.cos((f2 * 0.6662F)) * 0.5F; if (f2a > 40 &
             * f2a < 60) { WingRot = MathHelper.cos((f2 * 0.9F)) * 0.9F; }
             */
            legMov = (f1 * 1.5F);
            legMovB = legMov;
        } else {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
            if (f2a > 40 & f2a < 60) //random movement
            {
                WingRot = MathHelper.cos((f2 * 0.15F)) * 0.9F;
            }

        }

        float baseAngle = 0.52359F;

        this.WingLeft.rotateAngleZ = -baseAngle + WingRot;
        this.WingRight.rotateAngleZ = baseAngle - WingRot;
        this.WingLeftFront.rotateAngleZ = -baseAngle + WingRot;

        this.WingLeftBack.rotateAngleZ = 0.5934119F + -baseAngle + WingRot;
        this.WingRightFront.rotateAngleZ = baseAngle - WingRot;
        this.WingRightBack.rotateAngleZ = -0.5934119F + baseAngle - WingRot;

        this.FrontLegs.rotateAngleX = 0.1487144F + legMov;
        this.MidLegs.rotateAngleX = 0.5948578F + legMovB;
        this.RearLegs.rotateAngleX = 1.070744F + legMov;

    }

}
