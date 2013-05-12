package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;

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

    public MoCModelButterfly()
    {
        textureWidth = 32;
        textureHeight = 32;

        Head = new ModelRenderer(this, 0, 11);
        Head.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        Head.setRotationPoint(0F, 21.9F, -1.3F);
        setRotation(Head, -2.171231F, 0F, 0F);

        Mouth = new ModelRenderer(this, 0, 8);
        Mouth.addBox(0F, 0F, 0F, 1, 2, 0);
        Mouth.setRotationPoint(-0.2F, 22F, -2.5F);
        setRotation(Mouth, 0.6548599F, 0F, 0F);

        RightAntenna = new ModelRenderer(this, 0, 7);
        RightAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        RightAntenna.setRotationPoint(-0.5F, 21.7F, -2.3F);
        setRotation(RightAntenna, -1.041001F, 0.7853982F, 0F);

        LeftAntenna = new ModelRenderer(this, 4, 7);
        LeftAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        LeftAntenna.setRotationPoint(0.5F, 21.7F, -2.3F);
        setRotation(LeftAntenna, -1.041001F, -0.7853982F, 0F);

        Thorax = new ModelRenderer(this, 0, 0);
        Thorax.addBox(-0.5F, 1.5F, -1F, 1, 1, 2);
        Thorax.setRotationPoint(0F, 20F, -1F);

        Abdomen = new ModelRenderer(this, 8, 1);
        Abdomen.addBox(-0.5F, 0F, -1F, 1, 3, 1);
        Abdomen.setRotationPoint(0F, 21.5F, 0F);
        setRotation(Abdomen, 1.427659F, 0F, 0F);

        FrontLegs = new ModelRenderer(this, 0, 8);
        FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        FrontLegs.setRotationPoint(0F, 21.5F, -1.8F);
        setRotation(FrontLegs, 0.1487144F, 0F, 0F);

        MidLegs = new ModelRenderer(this, 4, 8);
        MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        MidLegs.setRotationPoint(0F, 22F, -1.2F);
        setRotation(MidLegs, 0.5948578F, 0F, 0F);

        RearLegs = new ModelRenderer(this, 0, 8);
        RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        RearLegs.setRotationPoint(0F, 22.5F, -0.4F);
        setRotation(RearLegs, 1.070744F, 0F, 0F);

        WingLeftFront = new ModelRenderer(this, 4, 20);
        WingLeftFront.addBox(0F, 0F, -4F, 8, 0, 6);
        WingLeftFront.setRotationPoint(0.3F, 21.4F, -1F);

        WingLeft = new ModelRenderer(this, 4, 26);
        WingLeft.addBox(0F, 0F, -1F, 8, 0, 6);
        WingLeft.setRotationPoint(0.3F, 21.5F, -0.5F);
        
        WingLeftBack = new ModelRenderer(this, 4, 0);
        WingLeftBack.addBox(0F, 0F, -1F, 5, 0, 8);
        WingLeftBack.setRotationPoint(0.3F, 21.2F, -1F);
        setRotation(WingLeftBack, 0F, 0F, 0.5934119F);

        /*
        WingRight = new ModelRenderer(this, 4, 14);
        WingRight.addBox(-8F, 0F, 0F, 8, 0, 6);
        WingRight.setRotationPoint(-0.3F, 21.5F, 0F);

        WingLeft = new ModelRenderer(this, 4, 26);
        WingLeft.addBox(0F, 0F, 0F, 8, 0, 6);
        WingLeft.setRotationPoint(0.3F, 21.5F, 0F);
      
        */
        
        WingRightFront = new ModelRenderer(this, 4, 8);
        WingRightFront.addBox(-8F, 0F, -4F, 8, 0, 6);
        WingRightFront.setRotationPoint(-0.3F, 21.4F, -1F);
        
        WingRight = new ModelRenderer(this, 4, 14);
        WingRight.addBox(-8F, 0F, -1F, 8, 0, 6);
        WingRight.setRotationPoint(-0.3F, 21.5F, -0.5F);
        
        WingRightBack = new ModelRenderer(this, 14, 0);
        WingRightBack.addBox(-5F, 0F, -1F, 5, 0, 8);
        WingRightBack.setRotationPoint(0.3F, 21.2F, -1F);
        setRotation(WingRightBack, 0F, 0F, -0.5934119F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityButterfly butterfly = (MoCEntityButterfly) entity;
        //boolean onGround = butterfly.onGround;
        boolean flying = (butterfly.getIsFlying() || butterfly.isOnAir());//butterfly.getIsFlying();
        setRotationAngles(f, f1, f2, f3, f4, f5, !flying);
        Abdomen.render(f5);
        FrontLegs.render(f5);
        RightAntenna.render(f5);
        LeftAntenna.render(f5);
        RearLegs.render(f5);
        MidLegs.render(f5);
        Head.render(f5);
        Thorax.render(f5);

        Mouth.render(f5);

        GL11.glPushMatrix();
        GL11.glEnable(3042 /*GL_BLEND*/);
        float transparency = 0.8F;
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        //GL11.glScalef(1.3F, 1.0F, 1.3F);
        WingRight.render(f5);
        WingLeft.render(f5);
        WingRightFront.render(f5);
        WingLeftFront.render(f5);
        WingRightBack.render(f5);
        WingLeftBack.render(f5);
        GL11.glDisable(3042/*GL_BLEND*/);
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean onGround)
    {

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

            /*      WingRot = MathHelper.cos((f2 * 0.6662F)) * 0.5F;
                  if (f2a > 40 & f2a < 60)
                  {
                      WingRot = MathHelper.cos((f2 * 0.9F)) * 0.9F;
                  }
            */
            legMov = (f1 * 1.5F);
            legMovB = legMov;
        }
        else
        {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
            if (f2a > 40 & f2a < 60) //random movement
            {
                WingRot = MathHelper.cos((f2 * 0.15F)) * 0.9F;
            }

        }

        float baseAngle = 0.52359F;

        WingLeft.rotateAngleZ = -baseAngle + WingRot;
        WingRight.rotateAngleZ = baseAngle - WingRot;
        WingLeftFront.rotateAngleZ = -baseAngle + WingRot;

        WingLeftBack.rotateAngleZ = 0.5934119F + -baseAngle + WingRot; 
        WingRightFront.rotateAngleZ = baseAngle - WingRot;
        WingRightBack.rotateAngleZ = -0.5934119F + baseAngle - WingRot;

        FrontLegs.rotateAngleX = 0.1487144F + legMov;
        MidLegs.rotateAngleX = 0.5948578F + legMovB;
        RearLegs.rotateAngleX = 1.070744F + legMov;

    }

}

