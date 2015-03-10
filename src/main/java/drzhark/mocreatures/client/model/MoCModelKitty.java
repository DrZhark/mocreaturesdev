package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCModelKitty extends ModelBiped {

    public MoCModelKitty() {
        this(0.0F);
    }

    public MoCModelKitty(float f) {
        this(f, 0.0F);
    }

    public MoCModelKitty(float f, float f1) {
        super(f, f1, 64, 32);
        this.bipedHead = new ModelRenderer(this, 1, 1);
        this.bipedHead.addBox(-2.5F, -3F, -4F, 5, 4, 4, f);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadwear = new ModelRenderer(this, 0, 0);
        this.bipedHeadwear.addBox(-4F, -3F, -4F, 1, 1, 1, f + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts = new ModelRenderer[9];
        this.bipedHeadParts[0] = new ModelRenderer(this, 16, 0);
        this.bipedHeadParts[0].addBox(-2F, -5F, -3F, 1, 1, 1, f);
        this.bipedHeadParts[0].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts[1] = new ModelRenderer(this, 16, 0);
        this.bipedHeadParts[1].mirror = true;
        this.bipedHeadParts[1].addBox(1.0F, -5F, -3F, 1, 1, 1, f);
        this.bipedHeadParts[1].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts[2] = new ModelRenderer(this, 20, 0);
        this.bipedHeadParts[2].addBox(-2.5F, -4F, -3F, 2, 1, 1, f);
        this.bipedHeadParts[2].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts[3] = new ModelRenderer(this, 20, 0);
        this.bipedHeadParts[3].mirror = true;
        this.bipedHeadParts[3].addBox(0.5F, -4F, -3F, 2, 1, 1, f);
        this.bipedHeadParts[3].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts[4] = new ModelRenderer(this, 40, 0);
        this.bipedHeadParts[4].addBox(-4F, -1.5F, -5F, 3, 3, 1, f);
        this.bipedHeadParts[4].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts[5] = new ModelRenderer(this, 40, 0);
        this.bipedHeadParts[5].mirror = true;
        this.bipedHeadParts[5].addBox(1.0F, -1.5F, -5F, 3, 3, 1, f);
        this.bipedHeadParts[5].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts[6] = new ModelRenderer(this, 21, 6);
        this.bipedHeadParts[6].addBox(-1F, -1F, -5F, 2, 2, 1, f);
        this.bipedHeadParts[6].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts[7] = new ModelRenderer(this, 50, 0);
        this.bipedHeadParts[7].addBox(-2.5F, 0.5F, -1F, 5, 4, 1, f);
        this.bipedHeadParts[7].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedHeadParts[8] = new ModelRenderer(this, 60, 0);
        this.bipedHeadParts[8].addBox(-1.5F, -2F, -4.1F, 3, 1, 1, f);
        this.bipedHeadParts[8].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedBody = new ModelRenderer(this, 20, 0);
        this.bipedBody.addBox(-2.5F, -2F, -0F, 5, 5, 10, f);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.bipedRightArm = new ModelRenderer(this, 0, 9);
        this.bipedRightArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.bipedRightArm.setRotationPoint(-1.5F, 3F + f1, -1F);
        this.bipedLeftArm = new ModelRenderer(this, 0, 9);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.bipedLeftArm.setRotationPoint(1.5F, 3F + f1, -1F);
        this.bipedRightLeg = new ModelRenderer(this, 8, 9);
        this.bipedRightLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.bipedRightLeg.setRotationPoint(-1.5F, 3F + f1, 7F);
        this.bipedLeftLeg = new ModelRenderer(this, 8, 9);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.bipedLeftLeg.setRotationPoint(1.5F, 3F + f1, 7F);
        this.bipedTail = new ModelRenderer(this, 16, 9);
        this.bipedTail.mirror = true;
        this.bipedTail.addBox(-0.5F, -8F, -1F, 1, 8, 1, f);
        this.bipedTail.setRotationPoint(0.0F, -0.5F + f1, 7.5F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glPushMatrix();
        setRotationAngles(f, f1, f2, f3, f4, f5);
        if (this.isSitting) {
            GL11.glTranslatef(0.0F, 0.25F, 0.0F);
            this.bipedTail.rotateAngleZ = 0.0F;
            this.bipedTail.rotateAngleX = -2.3F;
        }
        this.bipedHead.render(f5);
        for (int i = 0; i < 7; i++) {
            this.bipedHeadParts[i].render(f5);
        }

        if (this.kittystate > 2) {
            this.bipedHeadParts[7].render(f5);
        }
        if (this.kittystate == 12) {
            this.bipedHeadParts[8].render(f5);
        }
        this.bipedBody.render(f5);
        this.bipedTail.render(f5);
        if (this.isSitting) {
            GL11.glTranslatef(0.0F, 0.0625F, 0.0625F);
            float f6 = -1.570796F;
            this.bipedRightArm.rotateAngleX = f6;
            this.bipedLeftArm.rotateAngleX = f6;
            this.bipedRightLeg.rotateAngleX = f6;
            this.bipedLeftLeg.rotateAngleX = f6;
            this.bipedRightLeg.rotateAngleY = 0.1F;
            this.bipedLeftLeg.rotateAngleY = -0.1F;
        }
        this.bipedRightArm.render(f5);
        this.bipedLeftArm.render(f5);
        this.bipedRightLeg.render(f5);
        this.bipedLeftLeg.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.bipedHead.rotateAngleY = f3 / 57.29578F;
        this.bipedHead.rotateAngleX = f4 / 57.29578F;
        for (int i = 0; i < 9; i++) {
            this.bipedHeadParts[i].rotateAngleY = this.bipedHead.rotateAngleY;
            this.bipedHeadParts[i].rotateAngleX = this.bipedHead.rotateAngleX;
        }

        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedRightArm.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 2.0F * f1 * 0.5F;
        this.bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.bipedRightLeg.rotateAngleY = 0.0F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;
        if (this.heldItemLeft != 0) {
            this.bipedLeftArm.rotateAngleX = (this.bipedLeftArm.rotateAngleX * 0.5F) - 0.3141593F;
        }
        if (this.heldItemRight != 0) {
            this.bipedRightArm.rotateAngleX = (this.bipedRightArm.rotateAngleX * 0.5F) - 0.3141593F;
        }
        if (this.isSwinging) {
            this.bipedRightArm.rotateAngleX = -2F + this.swingProgress;
            this.bipedRightArm.rotateAngleY = 2.25F - (this.swingProgress * 2.0F);
        } else {
            this.bipedRightArm.rotateAngleY = 0.0F;
        }
        this.bipedLeftArm.rotateAngleY = 0.0F;
        this.bipedTail.rotateAngleX = -0.5F;
        this.bipedTail.rotateAngleZ = this.bipedLeftLeg.rotateAngleX * 0.625F;
    }

    public boolean isSitting;
    public boolean isSwinging;
    public float swingProgress;
    public ModelRenderer bipedHeadParts[];
    public static int parts = 9;
    public ModelRenderer bipedTail;
    public int kittystate;
}
