package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

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

    public MoCModelDragonfly() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        this.Head.setRotationPoint(0F, 21F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.RAntenna = new ModelRenderer(this, 0, 7);
        this.RAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.RAntenna.setRotationPoint(-0.5F, 19.7F, -2.3F);
        setRotation(this.RAntenna, -1.041001F, 0.7853982F, 0F);

        this.LAntenna = new ModelRenderer(this, 4, 7);
        this.LAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.LAntenna.setRotationPoint(0.5F, 19.7F, -2.3F);
        setRotation(this.LAntenna, -1.041001F, -0.7853982F, 0F);

        this.Mouth = new ModelRenderer(this, 0, 11);
        this.Mouth.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Mouth.setRotationPoint(0F, 21.1F, -2.3F);
        setRotation(this.Mouth, -2.171231F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setRotationPoint(0F, 20F, -1F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-0.5F, 0F, -1F, 1, 7, 1);
        this.Abdomen.setRotationPoint(0F, 20.5F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 8);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.FrontLegs.setRotationPoint(0F, 21.5F, -1.8F);
        setRotation(this.FrontLegs, 0.1487144F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 4, 8);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.MidLegs.setRotationPoint(0F, 22F, -1.2F);
        setRotation(this.MidLegs, 0.5948578F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 8, 8);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.RearLegs.setRotationPoint(0F, 22F, -0.4F);
        setRotation(this.RearLegs, 1.070744F, 0F, 0F);

        this.WingFrontRight = new ModelRenderer(this, 0, 28);
        this.WingFrontRight.addBox(-7F, 0F, -1F, 7, 0, 2);
        this.WingFrontRight.setRotationPoint(-1F, 20F, -1F);
        setRotation(this.WingFrontRight, 0F, -0.1396263F, 0.0872665F);

        this.WingFrontLeft = new ModelRenderer(this, 0, 30);
        this.WingFrontLeft.addBox(0F, 0F, -1F, 7, 0, 2);
        this.WingFrontLeft.setRotationPoint(1F, 20F, -1F);
        setRotation(this.WingFrontLeft, 0F, 0.1396263F, -0.0872665F);

        this.WingRearRight = new ModelRenderer(this, 0, 24);
        this.WingRearRight.addBox(-7F, 0F, -1F, 7, 0, 2);
        this.WingRearRight.setRotationPoint(-1F, 20F, -1F);
        setRotation(this.WingRearRight, 0F, 0.3490659F, -0.0872665F);

        this.WingRearLeft = new ModelRenderer(this, 0, 26);
        this.WingRearLeft.addBox(0F, 0F, -1F, 7, 0, 2);
        this.WingRearLeft.setRotationPoint(1F, 20F, -1F);
        setRotation(this.WingRearLeft, 0F, -0.3490659F, 0.0872665F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);

        MoCEntityDragonfly dragonfly = (MoCEntityDragonfly) entity;
        //boolean onGround = dragonfly.onGround;
        boolean isFlying = (dragonfly.getIsFlying() || dragonfly.motionY < -0.1D);
        setRotationAngles(f, f1, f2, f3, f4, f5, isFlying);
        this.Head.render(f5);
        this.Abdomen.render(f5);
        this.FrontLegs.render(f5);
        this.RAntenna.render(f5);
        this.LAntenna.render(f5);
        this.RearLegs.render(f5);
        this.MidLegs.render(f5);
        this.Mouth.render(f5);
        this.Thorax.render(f5);

        GL11.glPushMatrix();
        GL11.glEnable(3042 /* GL_BLEND */);
        float transparency = 0.6F;
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        //GL11.glScalef(1.3F, 1.0F, 1.3F);
        this.WingRearRight.render(f5);
        this.WingFrontRight.render(f5);
        this.WingFrontLeft.render(f5);
        this.WingRearLeft.render(f5);
        GL11.glDisable(3042/* GL_BLEND */);
        GL11.glPopMatrix();

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean flying) {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);

        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */

        float WingRot = 0F;
        float legMov = 0F;
        float legMovB = 0F;

        if (flying) {
            WingRot = MathHelper.cos((f2 * 2.0F)) * 0.5F;
            legMov = (f1 * 1.5F);
            legMovB = legMov;
        } else {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }

        this.WingFrontRight.rotateAngleZ = WingRot;
        this.WingRearLeft.rotateAngleZ = WingRot;

        this.WingFrontLeft.rotateAngleZ = -WingRot;
        this.WingRearRight.rotateAngleZ = -WingRot;

        this.FrontLegs.rotateAngleX = 0.1487144F + legMov;
        this.MidLegs.rotateAngleX = 0.5948578F + legMovB;
        this.RearLegs.rotateAngleX = 1.070744F + legMov;
    }

}
