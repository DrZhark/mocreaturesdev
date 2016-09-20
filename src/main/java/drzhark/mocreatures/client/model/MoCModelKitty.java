package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCModelKitty extends ModelBase {

    public boolean isSitting;
    public boolean isSwinging;
    public float swingProgress;
    public int kittystate;
    public ModelRenderer headParts[];
    public ModelRenderer tail;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;

    private ModelRenderer body;

    public MoCModelKitty() {
        this(0.0F);
    }

    public MoCModelKitty(float f) {
        this(f, 0.0F);
    }

    public MoCModelKitty(float f, float f1) {
        this.headParts = new ModelRenderer[10];
        this.headParts[0] = new ModelRenderer(this, 16, 0);
        this.headParts[0].addBox(-2F, -5F, -3F, 1, 1, 1, f);
        this.headParts[0].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[1] = new ModelRenderer(this, 16, 0);
        this.headParts[1].mirror = true;
        this.headParts[1].addBox(1.0F, -5F, -3F, 1, 1, 1, f);
        this.headParts[1].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[2] = new ModelRenderer(this, 20, 0);
        this.headParts[2].addBox(-2.5F, -4F, -3F, 2, 1, 1, f);
        this.headParts[2].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[3] = new ModelRenderer(this, 20, 0);
        this.headParts[3].mirror = true;
        this.headParts[3].addBox(0.5F, -4F, -3F, 2, 1, 1, f);
        this.headParts[3].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[4] = new ModelRenderer(this, 40, 0);
        this.headParts[4].addBox(-4F, -1.5F, -5F, 3, 3, 1, f);
        this.headParts[4].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[5] = new ModelRenderer(this, 40, 0);
        this.headParts[5].mirror = true;
        this.headParts[5].addBox(1.0F, -1.5F, -5F, 3, 3, 1, f);
        this.headParts[5].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[6] = new ModelRenderer(this, 21, 6);
        this.headParts[6].addBox(-1F, -1F, -5F, 2, 2, 1, f);
        this.headParts[6].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[7] = new ModelRenderer(this, 50, 0);
        this.headParts[7].addBox(-2.5F, 0.5F, -1F, 5, 4, 1, f);
        this.headParts[7].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[8] = new ModelRenderer(this, 60, 0);
        this.headParts[8].addBox(-1.5F, -2F, -4.1F, 3, 1, 1, f);
        this.headParts[8].setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.headParts[9] = new ModelRenderer(this, 1, 1);
        this.headParts[9].addBox(-2.5F, -3F, -4F, 5, 4, 4, f);
        this.headParts[9].setRotationPoint(0.0F, 0.0F + f1, -2F);

        this.body = new ModelRenderer(this, 20, 0);
        this.body.addBox(-2.5F, -2F, -0F, 5, 5, 10, f);
        this.body.setRotationPoint(0.0F, 0.0F + f1, -2F);
        this.rightArm = new ModelRenderer(this, 0, 9);
        this.rightArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.rightArm.setRotationPoint(-1.5F, 3F + f1, -1F);
        this.leftArm = new ModelRenderer(this, 0, 9);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.leftArm.setRotationPoint(1.5F, 3F + f1, -1F);
        this.rightLeg = new ModelRenderer(this, 8, 9);
        this.rightLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.rightLeg.setRotationPoint(-1.5F, 3F + f1, 7F);
        this.leftLeg = new ModelRenderer(this, 8, 9);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, f);
        this.leftLeg.setRotationPoint(1.5F, 3F + f1, 7F);
        this.tail = new ModelRenderer(this, 16, 9);
        this.tail.mirror = true;
        this.tail.addBox(-0.5F, -8F, -1F, 1, 8, 1, f);
        this.tail.setRotationPoint(0.0F, -0.5F + f1, 7.5F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityKitty kitty = (MoCEntityKitty) entity;
        this.isSitting = kitty.getIsSitting();
        this.isSwinging = kitty.getIsSwinging();
        this.swingProgress = kitty.swingProgress;
        this.kittystate = kitty.getKittyState();

        GL11.glPushMatrix();
        setRotationAngles(f, f1, f2, f3, f4, f5);
        if (this.isSitting) {
            GL11.glTranslatef(0.0F, 0.25F, 0.0F);
            this.tail.rotateAngleZ = 0.0F;
            this.tail.rotateAngleX = -2.3F;
        }
        //this.bipedHead.render(f5);
        for (int i = 0; i < 7; i++) {
            this.headParts[i].render(f5);
        }

        if (this.kittystate > 2) {
            this.headParts[7].render(f5);
        }
        if (this.kittystate == 12) {
            this.headParts[8].render(f5);
        }
        this.headParts[9].render(f5);
        this.body.render(f5);
        this.tail.render(f5);
        if (this.isSitting) {
            GL11.glTranslatef(0.0F, 0.0625F, 0.0625F);
            float f6 = -1.570796F;
            this.rightArm.rotateAngleX = f6;
            this.leftArm.rotateAngleX = f6;
            this.rightLeg.rotateAngleX = f6;
            this.leftLeg.rotateAngleX = f6;
            this.rightLeg.rotateAngleY = 0.1F;
            this.leftLeg.rotateAngleY = -0.1F;
        }
        this.rightArm.render(f5);
        this.leftArm.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.headParts[9].rotateAngleY = f3 / 57.29578F;
        this.headParts[9].rotateAngleX = f4 / 57.29578F;
        for (int i = 0; i < 9; i++) {
            this.headParts[i].rotateAngleY = this.headParts[9].rotateAngleY;
            this.headParts[i].rotateAngleX = this.headParts[9].rotateAngleX;
        }

        this.rightArm.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 2.0F * f1 * 0.5F;
        this.leftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leftLeg.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
        if (this.isSwinging) {
            this.rightArm.rotateAngleX = -2F + this.swingProgress;
            this.rightArm.rotateAngleY = 2.25F - (this.swingProgress * 2.0F);
        } else {
            this.rightArm.rotateAngleY = 0.0F;
        }
        this.leftArm.rotateAngleY = 0.0F;
        this.tail.rotateAngleX = -0.5F;
        this.tail.rotateAngleZ = this.leftLeg.rotateAngleX * 0.625F;
    }
}
