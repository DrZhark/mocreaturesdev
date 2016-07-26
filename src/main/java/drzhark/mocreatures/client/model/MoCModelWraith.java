package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelWraith extends ModelBiped {

    private int attackCounter;

    public MoCModelWraith() {
        //TODO 4.1 FIX
        super(12F, 0.0F, 64, 32);
        this.heldItemLeft = 0;
        this.heldItemRight = 0;
        this.isSneak = false;
        this.bipedHead = new ModelRenderer(this, 0, 40);
        this.bipedHead.addBox(-4F, -8F, -4F, 1, 1, 1, 0.0F);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 0, 0);
        this.bipedHeadwear.addBox(-5F, -8F, -4F, 8, 8, 8, 0.0F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody = new ModelRenderer(this, 36, 0);
        this.bipedBody.addBox(-6F, 0.0F, -2F, 10, 20, 4, 0.0F);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 16, 16);
        this.bipedRightArm.addBox(-5F, -2F, -2F, 4, 12, 4, 0.0F);
        this.bipedRightArm.setRotationPoint(-5F, 2.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 16, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1F, -2F, -2F, 4, 12, 4, 0.0F);
        this.bipedLeftArm.setRotationPoint(5F, 2.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2F, 0.0F, -2F, 2, 2, 2, 0.0F);
        this.bipedRightLeg.setRotationPoint(-2F, 12F, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2F, 0.0F, -2F, 2, 2, 2, 0.0F);
        this.bipedLeftLeg.setRotationPoint(2.0F, 12F, 0.0F);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity par7Entity) {

        if (par7Entity instanceof MoCEntityWraith) {
            this.attackCounter = ((MoCEntityWraith) par7Entity).attackCounter;
        }

        float f6 = MathHelper.sin(this.swingProgress * 3.141593F);
        float f7 = MathHelper.sin((1.0F - ((1.0F - this.swingProgress) * (1.0F - this.swingProgress))) * 3.141593F);
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotateAngleY = -(0.1F - (f6 * 0.6F));
        this.bipedLeftArm.rotateAngleY = 0.1F - (f6 * 0.6F);
        if (this.attackCounter != 0) {
            float armMov = (MathHelper.cos((attackCounter) * 0.12F) * 4F);

            this.bipedRightArm.rotateAngleX = -armMov;
            this.bipedLeftArm.rotateAngleX = -armMov;
        } else {
            this.bipedRightArm.rotateAngleX = -1.570796F;
            this.bipedLeftArm.rotateAngleX = -1.570796F;
            this.bipedRightArm.rotateAngleX -= (f6 * 1.2F) - (f7 * 0.4F);
            this.bipedLeftArm.rotateAngleX -= (f6 * 1.2F) - (f7 * 0.4F);
            this.bipedRightArm.rotateAngleX += MathHelper.sin(f2 * 0.067F) * 0.05F;
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(f2 * 0.067F) * 0.05F;
        }

        this.bipedRightArm.rotateAngleZ += (MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= (MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;

    }
}
