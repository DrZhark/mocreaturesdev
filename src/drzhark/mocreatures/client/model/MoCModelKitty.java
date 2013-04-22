package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelKitty extends ModelBiped {

    public MoCModelKitty()
    {
        this(0.0F);
    }

    public MoCModelKitty(float f)
    {
        this(f, 0.0F);
    }

    public MoCModelKitty(float f, float f1)
    {
        super(f, f1, 64, 32);
        bipedHead = new ModelRenderer(this, 1, 1);
        bipedHead.addBox(-2.5F, -3F, -4F, 5, 4, 4, f);
        bipedHead.setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadwear = new ModelRenderer(this, 0, 0);
        bipedHeadwear.addBox(-4F, -3F, -4F, 1, 1, 1, f + 0.5F);
        bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts = new ModelRenderer[9];
        bipedHeadParts[0] = new ModelRenderer(this, 16, 0);
        bipedHeadParts[0].addBox(-2F, -5F, -3F, 1, 1, 1, f);
        bipedHeadParts[0].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts[1] = new ModelRenderer(this, 16, 0);
        bipedHeadParts[1].mirror = true;
        bipedHeadParts[1].addBox(1.0F, -5F, -3F, 1, 1, 1, f);
        bipedHeadParts[1].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts[2] = new ModelRenderer(this, 20, 0);
        bipedHeadParts[2].addBox(-2.5F, -4F, -3F, 2, 1, 1, f);
        bipedHeadParts[2].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts[3] = new ModelRenderer(this, 20, 0);
        bipedHeadParts[3].mirror = true;
        bipedHeadParts[3].addBox(0.5F, -4F, -3F, 2, 1, 1, f);
        bipedHeadParts[3].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts[4] = new ModelRenderer(this, 40, 0);
        bipedHeadParts[4].addBox(-4F, -1.5F, -5F, 3, 3, 1, f);
        bipedHeadParts[4].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts[5] = new ModelRenderer(this, 40, 0);
        bipedHeadParts[5].mirror = true;
        bipedHeadParts[5].addBox(1.0F, -1.5F, -5F, 3, 3, 1, f);
        bipedHeadParts[5].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts[6] = new ModelRenderer(this, 21, 6);
        bipedHeadParts[6].addBox(-1F, -1F, -5F, 2, 2, 1, f);
        bipedHeadParts[6].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts[7] = new ModelRenderer(this, 50, 0);
        bipedHeadParts[7].addBox(-2.5F, 0.5F, -1F, 5, 4, 1, f);
        bipedHeadParts[7].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedHeadParts[8] = new ModelRenderer(this, 60, 0);
        bipedHeadParts[8].addBox(-1.5F, -2F, -4.1F, 3, 1, 1, f);
        bipedHeadParts[8].setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedBody = new ModelRenderer(this, 20, 0);
        bipedBody.addBox(-2.5F, -2F, -0F, 5, 5, 10, f);
        bipedBody.setRotationPoint(0.0F, 0.0F + f1, -2F);
        bipedRightArm = new ModelRenderer(this, 0, 9);
        bipedRightArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        bipedRightArm.setRotationPoint(-1.5F, 3F + f1, -1F);
        bipedLeftArm = new ModelRenderer(this, 0, 9);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        bipedLeftArm.setRotationPoint(1.5F, 3F + f1, -1F);
        bipedRightLeg = new ModelRenderer(this, 8, 9);
        bipedRightLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        bipedRightLeg.setRotationPoint(-1.5F, 3F + f1, 7F);
        bipedLeftLeg = new ModelRenderer(this, 8, 9);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        bipedLeftLeg.setRotationPoint(1.5F, 3F + f1, 7F);
        bipedTail = new ModelRenderer(this, 16, 9);
        bipedTail.mirror = true;
        bipedTail.addBox(-0.5F, -8F, -1F, 1, 8, 1, f);
        bipedTail.setRotationPoint(0.0F, -0.5F + f1, 7.5F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        setRotationAngles(f, f1, f2, f3, f4, f5);
        if (isSitting)
        {
            GL11.glTranslatef(0.0F, 0.25F, 0.0F);
            bipedTail.rotateAngleZ = 0.0F;
            bipedTail.rotateAngleX = -2.3F;
        }
        bipedHead.render(f5);
        for (int i = 0; i < 7; i++)
        {
            bipedHeadParts[i].render(f5);
        }

        if (kittystate > 2)
        {
            bipedHeadParts[7].render(f5);
        }
        if (kittystate == 12)
        {
            bipedHeadParts[8].render(f5);
        }
        bipedBody.render(f5);
        bipedTail.render(f5);
        if (isSitting)
        {
            GL11.glTranslatef(0.0F, 0.0625F, 0.0625F);
            float f6 = -1.570796F;
            bipedRightArm.rotateAngleX = f6;
            bipedLeftArm.rotateAngleX = f6;
            bipedRightLeg.rotateAngleX = f6;
            bipedLeftLeg.rotateAngleX = f6;
            bipedRightLeg.rotateAngleY = 0.1F;
            bipedLeftLeg.rotateAngleY = -0.1F;
        }
        bipedRightArm.render(f5);
        bipedLeftArm.render(f5);
        bipedRightLeg.render(f5);
        bipedLeftLeg.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        bipedHead.rotateAngleY = f3 / 57.29578F;
        bipedHead.rotateAngleX = f4 / 57.29578F;
        for (int i = 0; i < 9; i++)
        {
            bipedHeadParts[i].rotateAngleY = bipedHead.rotateAngleY;
            bipedHeadParts[i].rotateAngleX = bipedHead.rotateAngleX;
        }

        bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
        bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
        bipedRightArm.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 2.0F * f1 * 0.5F;
        bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        bipedLeftLeg.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;
        if (heldItemLeft != 0)
        {
            bipedLeftArm.rotateAngleX = (bipedLeftArm.rotateAngleX * 0.5F) - 0.3141593F;
        }
        if (heldItemRight != 0)
        {
            bipedRightArm.rotateAngleX = (bipedRightArm.rotateAngleX * 0.5F) - 0.3141593F;
        }
        if (isSwinging)
        {
            bipedRightArm.rotateAngleX = -2F + swingProgress;
            bipedRightArm.rotateAngleY = 2.25F - (swingProgress * 2.0F);
        }
        else
        {
            bipedRightArm.rotateAngleY = 0.0F;
        }
        bipedLeftArm.rotateAngleY = 0.0F;
        bipedTail.rotateAngleX = -0.5F;
        bipedTail.rotateAngleZ = bipedLeftLeg.rotateAngleX * 0.625F;
    }

    public boolean isSitting;
    public boolean isSwinging;
    public float swingProgress;
    public ModelRenderer bipedHeadParts[];
    public static int parts = 9;
    public ModelRenderer bipedTail;
    public int kittystate;
}
