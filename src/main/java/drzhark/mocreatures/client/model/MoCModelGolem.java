package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCModelGolem extends ModelBase {

    ModelRenderer blocks[][];
    ModelRenderer head;
    ModelRenderer headb;
    ModelRenderer chest;
    ModelRenderer chestb;

    byte blocksText[];
    float radianF = 57.29578F;
    int w = 32;
    int h = 16;

    public MoCModelGolem() {
        this.blocks = new ModelRenderer[23][28];
        this.blocksText = new byte[23];
        this.textureWidth = 128;
        this.textureHeight = 128;

        for (byte i = 0; i < 23; i++) {
            this.blocksText[i] = 30;
        }

        //head
        this.head = new ModelRenderer(this, 96, 64);
        this.head.addBox(-4F, -4F, -4F, 8, 8, 8);
        this.head.setRotationPoint(0F, -10F, 0F);
        setRotation(this.head, 0F, 0.7853982F, 0F);

        this.headb = new ModelRenderer(this, 96, 80);
        this.headb.addBox(-4F, -4F, -4F, 8, 8, 8);
        this.headb.setRotationPoint(0F, -10F, 0F);
        setRotation(this.headb, 0F, 0.7853982F, 0F);

        this.chest = new ModelRenderer(this, 96, 96);
        this.chest.addBox(-4F, -4F, -4F, 8, 8, 8);
        this.chest.setRotationPoint(0F, -3F, -7F);
        setRotation(this.chest, 0F, 0.7853982F, 0F);

        this.chestb = new ModelRenderer(this, 96, 112);
        this.chestb.addBox(-4F, -4F, -4F, 8, 8, 8);
        this.chestb.setRotationPoint(0F, -3F, -7F);
        setRotation(this.chestb, 0F, 0.7853982F, 0F);

        for (byte i = 0; i < 28; i++) {
            int textX = (i / 8) * this.w;
            int textY = (i % 8) * this.h;

            //lchest1
            this.blocks[0][i] = new ModelRenderer(this, textX, textY);
            this.blocks[0][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            this.blocks[0][i].setRotationPoint(0F, -3F, 0F);
            setRotationG(this.blocks[0][i], -97F, -40F, 0F);

            //lchest2
            this.blocks[1][i] = new ModelRenderer(this, textX, textY);
            this.blocks[1][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            this.blocks[1][i].setRotationPoint(0F, -3F, 0F);
            setRotationG(this.blocks[1][i], -55F, -41F, 0F);

            //rchest1
            this.blocks[2][i] = new ModelRenderer(this, textX, textY);
            this.blocks[2][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            this.blocks[2][i].setRotationPoint(0F, -3F, 0F);
            setRotationG(this.blocks[2][i], -97F, 40F, 0F);

            //rchest2
            this.blocks[3][i] = new ModelRenderer(this, textX, textY);
            this.blocks[3][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            this.blocks[3][i].setRotationPoint(0F, -3F, 0F);
            setRotationG(this.blocks[3][i], -55F, 41F, 0F);

            //back
            this.blocks[4][i] = new ModelRenderer(this, textX, textY);
            this.blocks[4][i].addBox(-7F, -14F, -1F, 8, 8, 8);
            this.blocks[4][i].setRotationPoint(0F, 6F, 3F);
            setRotation(this.blocks[4][i], 0F, 0.7853982F, 0F);

            //lback1
            this.blocks[5][i] = new ModelRenderer(this, textX, textY);
            this.blocks[5][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            this.blocks[5][i].setRotationPoint(0F, -3F, 0F);
            setRotation(this.blocks[5][i], 1.919862F, 0.6981317F, 0F);

            //lback2
            this.blocks[6][i] = new ModelRenderer(this, textX, textY);
            this.blocks[6][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            this.blocks[6][i].setRotationPoint(0F, -3F, 0F);
            setRotation(this.blocks[6][i], 1.183003F, 0.6981317F, 0F);

            //rback1
            this.blocks[7][i] = new ModelRenderer(this, textX, textY);
            this.blocks[7][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            this.blocks[7][i].setRotationPoint(0F, -3F, 0F);
            setRotation(this.blocks[7][i], 1.919862F, -0.6981317F, 0F);

            //rback2
            this.blocks[8][i] = new ModelRenderer(this, textX, textY);
            this.blocks[8][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            this.blocks[8][i].setRotationPoint(0F, -3F, 0F);
            setRotation(this.blocks[8][i], 1.183003F, -0.6981317F, 0F);

            //lshoulder
            this.blocks[9][i] = new ModelRenderer(this, textX, textY);
            this.blocks[9][i].addBox(0F, -2F, -4F, 8, 8, 8);
            this.blocks[9][i].setRotationPoint(8F, -3F, 0F);
            setRotation(this.blocks[9][i], 0F, 0F, -0.6981317F);

            //larm[12]
            this.blocks[10][i] = new ModelRenderer(this, textX, textY);
            this.blocks[10][i].addBox(2F, 4F, -4F, 8, 8, 8);
            this.blocks[10][i].setRotationPoint(8F, -3F, 0F);
            setRotation(this.blocks[10][i], 0F, 0F, -0.2094395F);

            //lhand
            this.blocks[11][i] = new ModelRenderer(this, textX, textY);
            this.blocks[11][i].addBox(4.5F, 11F, -4F, 8, 8, 8);
            this.blocks[11][i].setRotationPoint(8F, -3F, 0F);

            //rshoulder
            this.blocks[12][i] = new ModelRenderer(this, textX, textY);
            this.blocks[12][i].addBox(-8F, -2F, -4F, 8, 8, 8);
            this.blocks[12][i].setRotationPoint(-8F, -3F, 0F);
            setRotation(this.blocks[12][i], 0F, 0F, 0.6981317F);

            //rarm
            this.blocks[13][i] = new ModelRenderer(this, textX, textY);
            this.blocks[13][i].addBox(-10F, 4F, -4F, 8, 8, 8);
            this.blocks[13][i].setRotationPoint(-8F, -3F, 0F);
            setRotation(this.blocks[13][i], 0F, 0F, 0.2094395F);

            //rhand
            this.blocks[14][i] = new ModelRenderer(this, textX, textY);
            this.blocks[14][i].addBox(-12.5F, 11F, -4F, 8, 8, 8);
            this.blocks[14][i].setRotationPoint(-8F, -3F, 0F);

            //lthigh
            this.blocks[15][i] = new ModelRenderer(this, textX, textY);
            this.blocks[15][i].addBox(-3.5F, 0F, -4F, 8, 8, 8);
            this.blocks[15][i].setRotationPoint(5F, 4F, 0F);
            setRotation(this.blocks[15][i], -0.3490659F, 0F, 0F);

            //lknee
            this.blocks[16][i] = new ModelRenderer(this, textX, textY);
            this.blocks[16][i].addBox(-4F, 6F, -7F, 8, 8, 8);
            this.blocks[16][i].setRotationPoint(5F, 4F, 0F);

            //lfoot
            this.blocks[17][i] = new ModelRenderer(this, textX, textY);
            this.blocks[17][i].addBox(-3.5F, 12F, -5F, 8, 8, 8);
            this.blocks[17][i].setRotationPoint(5F, 4F, 0F);

            //rthigh
            this.blocks[18][i] = new ModelRenderer(this, textX, textY);
            this.blocks[18][i].addBox(-4.5F, 0F, -4F, 8, 8, 8);
            this.blocks[18][i].setRotationPoint(-5F, 4F, 0F);
            setRotation(this.blocks[18][i], -0.3490659F, 0F, 0F);

            //rknee
            this.blocks[19][i] = new ModelRenderer(this, textX, textY);
            this.blocks[19][i].addBox(-4F, 6F, -7F, 8, 8, 8);
            this.blocks[19][i].setRotationPoint(-5F, 4F, 0F);

            //rfoot
            this.blocks[20][i] = new ModelRenderer(this, textX, textY);
            this.blocks[20][i].addBox(-4.5F, 12F, -5F, 8, 8, 8);
            this.blocks[20][i].setRotationPoint(-5F, 4F, 0F);

            //groin
            this.blocks[21][i] = new ModelRenderer(this, textX, textY);
            this.blocks[21][i].addBox(0F, -4F, -8F, 8, 8, 8);
            this.blocks[21][i].setRotationPoint(0F, 6F, 3F);
            setRotation(this.blocks[21][i], 0F, 0.7853982F, 0F);

            //butt
            this.blocks[22][i] = new ModelRenderer(this, textX, textY);
            this.blocks[22][i].addBox(-4F, -4F, -4F, 8, 8, 8);
            this.blocks[22][i].setRotationPoint(0F, 6F, 3F);
            setRotation(this.blocks[22][i], -0.7435722F, 0F, 0F);
        }

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityGolem entityG = (MoCEntityGolem) entity;
        boolean openChest = entityG.openChest();
        boolean isSummoning = entityG.isMissingCubes();
        boolean angry = entityG.getGolemState() > 1;
        boolean throwing = (entityG.tcounter > 25);

        for (int i = 0; i < 23; i++) {
            this.blocksText[i] = entityG.getBlockText(i);
        }
        float yOffset = entityG.getAdjustedYOffset();
        setRotationAngles(f, f1, f2, f3, f4, f5, openChest, isSummoning, throwing);
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, yOffset, 0F);
        for (int i = 0; i < 23; i++) {
            //blocksText[i] = entityG.getBlockText(i);
            if (this.blocksText[i] != 30) {
                this.blocks[i][this.blocksText[i]].render(f5);
            }
        }

        if (angry) {
            this.headb.render(f5);
            this.chestb.render(f5);
        } else {
            this.head.render(f5);
            this.chest.render(f5);
        }
        GL11.glPopMatrix();

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    private void setRotationG(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x / this.radianF;
        model.rotateAngleY = y / this.radianF;
        model.rotateAngleZ = z / this.radianF;
    }

    public void
            setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean openChest, boolean isSummoning, boolean throwing) {
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 1.2F * f1;
        float RArmZRot = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        float LArmZRot = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;

        this.head.rotateAngleY = (45F + f3) / this.radianF;
        this.headb.rotateAngleY = (45F + f3) / this.radianF;

        if (isSummoning) {
            this.chest.rotateAngleY = (45F / this.radianF) + (f2 / 2F);
            this.chestb.rotateAngleY = (45F / this.radianF) + (f2 / 2F);
        } else {
            this.chest.rotateAngleY = 45F / this.radianF;
            this.chestb.rotateAngleY = 45F / this.radianF;
        }

        if (openChest) {
            this.chest.rotationPointZ = -7F;
            this.chestb.rotationPointZ = -7F;

            if (this.blocksText[0] != 30) {
                this.blocks[0][this.blocksText[0]].rotateAngleY = -60F / this.radianF;
            }
            if (this.blocksText[1] != 30) {
                this.blocks[1][this.blocksText[1]].rotateAngleY = -55F / this.radianF;
            }
            if (this.blocksText[2] != 30) {
                this.blocks[2][this.blocksText[2]].rotateAngleY = 60F / this.radianF;
            }
            if (this.blocksText[3] != 30) {
                this.blocks[3][this.blocksText[3]].rotateAngleY = 55F / this.radianF;
            }
        } else {
            this.chest.rotationPointZ = -4F;
            this.chestb.rotationPointZ = -4F;

            if (this.blocksText[0] != 30) {
                this.blocks[0][this.blocksText[0]].rotateAngleY = -40F / this.radianF;
            }
            if (this.blocksText[1] != 30) {
                this.blocks[1][this.blocksText[1]].rotateAngleY = -41F / this.radianF;
            }
            if (this.blocksText[2] != 30) {
                this.blocks[2][this.blocksText[2]].rotateAngleY = 40F / this.radianF;
            }
            if (this.blocksText[3] != 30) {
                this.blocks[3][this.blocksText[3]].rotateAngleY = 41F / this.radianF;
            }
        }

        if (this.blocksText[15] != 30) {
            this.blocks[15][this.blocksText[15]].rotateAngleX = (-20F / this.radianF) + LLegXRot;
        }

        if (this.blocksText[16] != 30) {
            this.blocks[16][this.blocksText[16]].rotateAngleX = LLegXRot;
        }

        if (this.blocksText[17] != 30) {
            this.blocks[17][this.blocksText[17]].rotateAngleX = LLegXRot;
        }

        if (this.blocksText[18] != 30) {
            this.blocks[18][this.blocksText[18]].rotateAngleX = (-20F / this.radianF) + RLegXRot;
        }

        if (this.blocksText[19] != 30) {
            this.blocks[19][this.blocksText[19]].rotateAngleX = RLegXRot;
        }

        if (this.blocksText[20] != 30) {
            this.blocks[20][this.blocksText[20]].rotateAngleX = RLegXRot;
        }

        if (throwing) {
            LLegXRot = -90F / this.radianF;
            RLegXRot = -90F / this.radianF;
            RArmZRot = 0F;
            LArmZRot = 0F;
        }

        if (this.blocksText[12] != 30) {
            this.blocks[12][this.blocksText[12]].rotateAngleZ = (40F / this.radianF) + RArmZRot;
            this.blocks[12][this.blocksText[12]].rotateAngleX = LLegXRot;
        }

        if (this.blocksText[13] != 30) {
            this.blocks[13][this.blocksText[13]].rotateAngleZ = (12F / this.radianF) + RArmZRot;
            this.blocks[13][this.blocksText[13]].rotateAngleX = LLegXRot;
        }

        if (this.blocksText[14] != 30) {
            this.blocks[14][this.blocksText[14]].rotateAngleZ = RArmZRot;
            this.blocks[14][this.blocksText[14]].rotateAngleX = LLegXRot;
        }

        if (this.blocksText[9] != 30) {
            this.blocks[9][this.blocksText[9]].rotateAngleZ = (-40F / this.radianF) + LArmZRot;
            this.blocks[9][this.blocksText[9]].rotateAngleX = RLegXRot;
        }

        if (this.blocksText[10] != 30) {
            this.blocks[10][this.blocksText[10]].rotateAngleZ = (-12F / this.radianF) + LArmZRot;
            this.blocks[10][this.blocksText[10]].rotateAngleX = RLegXRot;
        }

        if (this.blocksText[11] != 30) {
            this.blocks[11][this.blocksText[11]].rotateAngleZ = LArmZRot;
            this.blocks[11][this.blocksText[11]].rotateAngleX = RLegXRot;
        }

    }

}
