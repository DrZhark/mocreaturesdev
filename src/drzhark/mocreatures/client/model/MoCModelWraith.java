package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelWraith extends ModelBiped {

    public MoCModelWraith()
    {
        //TODO 4.1 FIX
        super(12F, 0.0F, 64, 32);
        heldItemLeft = 0;
        heldItemRight = 0;
        isSneak = false;
        bipedHead = new ModelRenderer(this, 0, 40);
        bipedHead.addBox(-4F, -8F, -4F, 1, 1, 1, 0.0F);
        bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedHeadwear = new ModelRenderer(this, 0, 0);
        bipedHeadwear.addBox(-5F, -8F, -4F, 8, 8, 8, 0.0F);
        bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedBody = new ModelRenderer(this, 36, 0);
        bipedBody.addBox(-6F, 0.0F, -2F, 10, 20, 4, 0.0F);
        bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedRightArm = new ModelRenderer(this, 16, 16);
        bipedRightArm.addBox(-5F, -2F, -2F, 4, 12, 4, 0.0F);
        bipedRightArm.setRotationPoint(-5F, 2.0F, 0.0F);
        bipedLeftArm = new ModelRenderer(this, 16, 16);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1F, -2F, -2F, 4, 12, 4, 0.0F);
        bipedLeftArm.setRotationPoint(5F, 2.0F, 0.0F);
        bipedRightLeg = new ModelRenderer(this, 0, 16);
        bipedRightLeg.addBox(-2F, 0.0F, -2F, 2, 2, 2, 0.0F);
        bipedRightLeg.setRotationPoint(-2F, 12F, 0.0F);
        bipedLeftLeg = new ModelRenderer(this, 0, 16);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-2F, 0.0F, -2F, 2, 2, 2, 0.0F);
        bipedLeftLeg.setRotationPoint(2.0F, 12F, 0.0F);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        float f6 = MathHelper.sin(onGround * 3.141593F);
        float f7 = MathHelper.sin((1.0F - ((1.0F - onGround) * (1.0F - onGround))) * 3.141593F);
        bipedRightArm.rotateAngleZ = 0.0F;
        bipedLeftArm.rotateAngleZ = 0.0F;
        bipedRightArm.rotateAngleY = -(0.1F - (f6 * 0.6F));
        bipedLeftArm.rotateAngleY = 0.1F - (f6 * 0.6F);
        bipedRightArm.rotateAngleX = -1.570796F;
        bipedLeftArm.rotateAngleX = -1.570796F;
        bipedRightArm.rotateAngleX -= (f6 * 1.2F) - (f7 * 0.4F);
        bipedLeftArm.rotateAngleX -= (f6 * 1.2F) - (f7 * 0.4F);
        bipedRightArm.rotateAngleZ += (MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        bipedLeftArm.rotateAngleZ -= (MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
        bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
    }
}
