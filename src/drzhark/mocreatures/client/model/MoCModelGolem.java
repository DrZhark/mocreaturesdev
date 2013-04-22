package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;

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

    public MoCModelGolem()
    {
        blocks = new ModelRenderer[23][28];
        blocksText = new byte[23];
        textureWidth = 128;
        textureHeight = 128;

        for (byte i = 0; i < 23; i++)
        {
            blocksText[i] = 30;
        }

        //head
        head = new ModelRenderer(this, 96, 64);
        head.addBox(-4F, -4F, -4F, 8, 8, 8);
        head.setRotationPoint(0F, -10F, 0F);
        setRotation(head, 0F, 0.7853982F, 0F);

        headb = new ModelRenderer(this, 96, 80);
        headb.addBox(-4F, -4F, -4F, 8, 8, 8);
        headb.setRotationPoint(0F, -10F, 0F);
        setRotation(headb, 0F, 0.7853982F, 0F);

        chest = new ModelRenderer(this, 96, 96);
        chest.addBox(-4F, -4F, -4F, 8, 8, 8);
        chest.setRotationPoint(0F, -3F, -7F);
        setRotation(chest, 0F, 0.7853982F, 0F);

        chestb = new ModelRenderer(this, 96, 112);
        chestb.addBox(-4F, -4F, -4F, 8, 8, 8);
        chestb.setRotationPoint(0F, -3F, -7F);
        setRotation(chestb, 0F, 0.7853982F, 0F);

        /*chest = new ModelRenderer(this, 96, 96);
        chest.addBox(3F, -12F, -11F, 8, 8, 8);
        chest.setRotationPoint(0F, 6F, 3F);
        setRotation(chest, 0F, 0.7853982F, 0F);

        chestb = new ModelRenderer(this, 96, 112);
        chestb.addBox(3F, -12F, -11F, 8, 8, 8);
        chestb.setRotationPoint(0F, 6F, 3F);
        setRotation(chestb, 0F, 0.7853982F, 0F);*/

        /*blocks[0] = new ModelRenderer(this, 0, 0);
        blocks[0].addBox(-4F, 3F, -4F, 8, 8, 8);
        blocks[0].setRotationPoint(0F, -3F, 0F);
        setRotationG(blocks[0], -97F, -40F, 0F);

        //lchest2
        blocks[1] = new ModelRenderer(this, 0, 0);
        blocks[1].addBox(-4F, 3F, -4F, 8, 8, 8);
        blocks[1].setRotationPoint(0F, -3F, 0F);
        setRotationG(blocks[1], -55F, -41F, 0F);

        //rchest1
        blocks[2] = new ModelRenderer(this, 0, 0);
        blocks[2].addBox(-4F, 3F, -4F, 8, 8, 8);
        blocks[2].setRotationPoint(0F, -3F, 0F);
        setRotationG(blocks[2], -97F, 40F, 0F);

        //rchest2
        blocks[3] = new ModelRenderer(this, 0, 0);
        blocks[3].addBox(-4F, 3F, -4F, 8, 8, 8);
        blocks[3].setRotationPoint(0F, -3F, 0F);
        setRotationG(blocks[3], -55F, 41F, 0F);

        //back
        blocks[4] = new ModelRenderer(this, 0, 0);
        blocks[4].addBox(-7F, -14F, -1F, 8, 8, 8);
        blocks[4].setRotationPoint(0F, 6F, 3F);
        setRotation(blocks[4], 0F, 0.7853982F, 0F);

        //lback1
        blocks[5] = new ModelRenderer(this, 0, 0);
        blocks[5].addBox(-4F, 3F, -4F, 8, 8, 8);
        blocks[5].setRotationPoint(0F, -3F, 0F);
        setRotation(blocks[5], 1.919862F, 0.6981317F, 0F);

        //lback2
        blocks[6] = new ModelRenderer(this, 0, 0);
        blocks[6].addBox(-4F, 3F, -4F, 8, 8, 8);
        blocks[6].setRotationPoint(0F, -3F, 0F);
        setRotation(blocks[6], 1.183003F, 0.6981317F, 0F);

        //rback1
        blocks[7] = new ModelRenderer(this, 0, 0);
        blocks[7].addBox(-4F, 3F, -4F, 8, 8, 8);
        blocks[7].setRotationPoint(0F, -3F, 0F);
        setRotation(blocks[7], 1.919862F, -0.6981317F, 0F);

        //rback2
        blocks[8] = new ModelRenderer(this, 0, 0);
        blocks[8].addBox(-4F, 3F, -4F, 8, 8, 8);
        blocks[8].setRotationPoint(0F, -3F, 0F);
        setRotation(blocks[8], 1.183003F, -0.6981317F, 0F);

        //lshoulder
        blocks[9] = new ModelRenderer(this, 0, 0);
        blocks[9].addBox(0F, -2F, -4F, 8, 8, 8);
        blocks[9].setRotationPoint(8F, -3F, 0F);
        setRotation(blocks[9], 0F, 0F, -0.6981317F);

        //larm[12]
        blocks[10] = new ModelRenderer(this, 0, 0);
        blocks[10].addBox(2F, 4F, -4F, 8, 8, 8);
        blocks[10].setRotationPoint(8F, -3F, 0F);
        setRotation(blocks[10], 0F, 0F, -0.2094395F);

        //lhand
        blocks[11] = new ModelRenderer(this, 0, 0);
        blocks[11].addBox(4.5F, 11F, -4F, 8, 8, 8);
        blocks[11].setRotationPoint(8F, -3F, 0F);

        //rshoulder
        blocks[12] = new ModelRenderer(this, 0, 0);
        blocks[12].addBox(-8F, -2F, -4F, 8, 8, 8);
        blocks[12].setRotationPoint(-8F, -3F, 0F);
        setRotation(blocks[12], 0F, 0F, 0.6981317F);

        //rarm
        blocks[13] = new ModelRenderer(this, 0, 0);
        blocks[13].addBox(-10F, 4F, -4F, 8, 8, 8);
        blocks[13].setRotationPoint(-8F, -3F, 0F);
        setRotation(blocks[13], 0F, 0F, 0.2094395F);

        //rhand
        blocks[14] = new ModelRenderer(this, 0, 0);
        blocks[14].addBox(-12.5F, 11F, -4F, 8, 8, 8);
        blocks[14].setRotationPoint(-8F, -3F, 0F);

        //lthigh
        blocks[15] = new ModelRenderer(this, 0, 0);
        blocks[15].addBox(-3.5F, 0F, -4F, 8, 8, 8);
        blocks[15].setRotationPoint(5F, 4F, 0F);
        setRotation(blocks[15], -0.3490659F, 0F, 0F);

        //lknee
        blocks[16] = new ModelRenderer(this, 0, 0);
        blocks[16].addBox(-4F, 6F, -7F, 8, 8, 8);
        blocks[16].setRotationPoint(5F, 4F, 0F);

        //lfoot
        blocks[17] = new ModelRenderer(this, 0, 0);
        blocks[17].addBox(-3.5F, 12F, -5F, 8, 8, 8);
        blocks[17].setRotationPoint(5F, 4F, 0F);

        //rthigh
        blocks[18] = new ModelRenderer(this, 0, 0);
        blocks[18].addBox(-4.5F, 0F, -4F, 8, 8, 8);
        blocks[18].setRotationPoint(-5F, 4F, 0F);
        setRotation(blocks[18], -0.3490659F, 0F, 0F);

        //rknee
        blocks[19] = new ModelRenderer(this, 0, 0);
        blocks[19].addBox(-4F, 6F, -7F, 8, 8, 8);
        blocks[19].setRotationPoint(-5F, 4F, 0F);

        //rfoot
        blocks[20] = new ModelRenderer(this, 0, 0);
        blocks[20].addBox(-4.5F, 12F, -5F, 8, 8, 8);
        blocks[20].setRotationPoint(-5F, 4F, 0F);

        //groin
        blocks[21] = new ModelRenderer(this, 0, 0);
        blocks[21].addBox(0F, -4F, -8F, 8, 8, 8);
        blocks[21].setRotationPoint(0F, 6F, 3F);
        setRotation(blocks[21], 0F, 0.7853982F, 0F);

        //butt
        blocks[22] = new ModelRenderer(this, 0, 0);
        blocks[22].addBox(-4F, -4F, -4F, 8, 8, 8);
        blocks[22].setRotationPoint(0F, 6F, 3F);
        setRotation(blocks[22], -0.7435722F, 0F, 0F);*/

        for (byte i = 0; i < 28; i++)
        {
            int textX = (i / 8) * w;
            int textY = (i % 8) * h;

            //lchest1
            blocks[0][i] = new ModelRenderer(this, textX, textY);
            blocks[0][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            blocks[0][i].setRotationPoint(0F, -3F, 0F);
            setRotationG(blocks[0][i], -97F, -40F, 0F);

            //lchest2
            blocks[1][i] = new ModelRenderer(this, textX, textY);
            blocks[1][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            blocks[1][i].setRotationPoint(0F, -3F, 0F);
            setRotationG(blocks[1][i], -55F, -41F, 0F);

            //rchest1
            blocks[2][i] = new ModelRenderer(this, textX, textY);
            blocks[2][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            blocks[2][i].setRotationPoint(0F, -3F, 0F);
            setRotationG(blocks[2][i], -97F, 40F, 0F);

            //rchest2
            blocks[3][i] = new ModelRenderer(this, textX, textY);
            blocks[3][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            blocks[3][i].setRotationPoint(0F, -3F, 0F);
            setRotationG(blocks[3][i], -55F, 41F, 0F);

            //back
            blocks[4][i] = new ModelRenderer(this, textX, textY);
            blocks[4][i].addBox(-7F, -14F, -1F, 8, 8, 8);
            blocks[4][i].setRotationPoint(0F, 6F, 3F);
            setRotation(blocks[4][i], 0F, 0.7853982F, 0F);

            //lback1
            blocks[5][i] = new ModelRenderer(this, textX, textY);
            blocks[5][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            blocks[5][i].setRotationPoint(0F, -3F, 0F);
            setRotation(blocks[5][i], 1.919862F, 0.6981317F, 0F);

            //lback2
            blocks[6][i] = new ModelRenderer(this, textX, textY);
            blocks[6][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            blocks[6][i].setRotationPoint(0F, -3F, 0F);
            setRotation(blocks[6][i], 1.183003F, 0.6981317F, 0F);

            //rback1
            blocks[7][i] = new ModelRenderer(this, textX, textY);
            blocks[7][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            blocks[7][i].setRotationPoint(0F, -3F, 0F);
            setRotation(blocks[7][i], 1.919862F, -0.6981317F, 0F);

            //rback2
            blocks[8][i] = new ModelRenderer(this, textX, textY);
            blocks[8][i].addBox(-4F, 3F, -4F, 8, 8, 8);
            blocks[8][i].setRotationPoint(0F, -3F, 0F);
            setRotation(blocks[8][i], 1.183003F, -0.6981317F, 0F);

            //lshoulder
            blocks[9][i] = new ModelRenderer(this, textX, textY);
            blocks[9][i].addBox(0F, -2F, -4F, 8, 8, 8);
            blocks[9][i].setRotationPoint(8F, -3F, 0F);
            setRotation(blocks[9][i], 0F, 0F, -0.6981317F);

            //larm[12]
            blocks[10][i] = new ModelRenderer(this, textX, textY);
            blocks[10][i].addBox(2F, 4F, -4F, 8, 8, 8);
            blocks[10][i].setRotationPoint(8F, -3F, 0F);
            setRotation(blocks[10][i], 0F, 0F, -0.2094395F);

            //lhand
            blocks[11][i] = new ModelRenderer(this, textX, textY);
            blocks[11][i].addBox(4.5F, 11F, -4F, 8, 8, 8);
            blocks[11][i].setRotationPoint(8F, -3F, 0F);

            //rshoulder
            blocks[12][i] = new ModelRenderer(this, textX, textY);
            blocks[12][i].addBox(-8F, -2F, -4F, 8, 8, 8);
            blocks[12][i].setRotationPoint(-8F, -3F, 0F);
            setRotation(blocks[12][i], 0F, 0F, 0.6981317F);

            //rarm
            blocks[13][i] = new ModelRenderer(this, textX, textY);
            blocks[13][i].addBox(-10F, 4F, -4F, 8, 8, 8);
            blocks[13][i].setRotationPoint(-8F, -3F, 0F);
            setRotation(blocks[13][i], 0F, 0F, 0.2094395F);

            //rhand
            blocks[14][i] = new ModelRenderer(this, textX, textY);
            blocks[14][i].addBox(-12.5F, 11F, -4F, 8, 8, 8);
            blocks[14][i].setRotationPoint(-8F, -3F, 0F);

            //lthigh
            blocks[15][i] = new ModelRenderer(this, textX, textY);
            blocks[15][i].addBox(-3.5F, 0F, -4F, 8, 8, 8);
            blocks[15][i].setRotationPoint(5F, 4F, 0F);
            setRotation(blocks[15][i], -0.3490659F, 0F, 0F);

            //lknee
            blocks[16][i] = new ModelRenderer(this, textX, textY);
            blocks[16][i].addBox(-4F, 6F, -7F, 8, 8, 8);
            blocks[16][i].setRotationPoint(5F, 4F, 0F);

            //lfoot
            blocks[17][i] = new ModelRenderer(this, textX, textY);
            blocks[17][i].addBox(-3.5F, 12F, -5F, 8, 8, 8);
            blocks[17][i].setRotationPoint(5F, 4F, 0F);

            //rthigh
            blocks[18][i] = new ModelRenderer(this, textX, textY);
            blocks[18][i].addBox(-4.5F, 0F, -4F, 8, 8, 8);
            blocks[18][i].setRotationPoint(-5F, 4F, 0F);
            setRotation(blocks[18][i], -0.3490659F, 0F, 0F);

            //rknee
            blocks[19][i] = new ModelRenderer(this, textX, textY);
            blocks[19][i].addBox(-4F, 6F, -7F, 8, 8, 8);
            blocks[19][i].setRotationPoint(-5F, 4F, 0F);

            //rfoot
            blocks[20][i] = new ModelRenderer(this, textX, textY);
            blocks[20][i].addBox(-4.5F, 12F, -5F, 8, 8, 8);
            blocks[20][i].setRotationPoint(-5F, 4F, 0F);

            //groin
            blocks[21][i] = new ModelRenderer(this, textX, textY);
            blocks[21][i].addBox(0F, -4F, -8F, 8, 8, 8);
            blocks[21][i].setRotationPoint(0F, 6F, 3F);
            setRotation(blocks[21][i], 0F, 0.7853982F, 0F);

            //butt
            blocks[22][i] = new ModelRenderer(this, textX, textY);
            blocks[22][i].addBox(-4F, -4F, -4F, 8, 8, 8);
            blocks[22][i].setRotationPoint(0F, 6F, 3F);
            setRotation(blocks[22][i], -0.7435722F, 0F, 0F);
        }

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityGolem entityG = (MoCEntityGolem) entity;
        boolean openChest = entityG.openChest();
        boolean isSummoning = entityG.isMissingCubes();
        boolean angry = entityG.getGolemState() > 1;
        boolean throwing = (entityG.tcounter > 25);

        for (int i = 0; i < 23; i++)
        {
            blocksText[i] = entityG.getBlockText(i);
        }

        setRotationAngles(f, f1, f2, f3, f4, f5, openChest, isSummoning, throwing);

        for (int i = 0; i < 23; i++)
        {
            //blocksText[i] = entityG.getBlockText(i);
            if (blocksText[i] != 30)
            {
                blocks[i][blocksText[i]].render(f5);
            }
        }

        if (angry)
        {
            headb.render(f5);
            chestb.render(f5);
        }
        else
        {
            head.render(f5);
            chest.render(f5);
        }
        //setRotationAngles(f, f1, f2, f3, f4, f5, openChest, isSummoning, throwing);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    private void setRotationG(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x / radianF;
        model.rotateAngleY = y / radianF;
        model.rotateAngleZ = z / radianF;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean openChest, boolean isSummoning, boolean throwing)
    {
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 1.2F * f1;
        float RArmZRot = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
        float LArmZRot = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;

        head.rotateAngleY = (45F + f3) / radianF;
        headb.rotateAngleY = (45F + f3) / radianF;

        if (isSummoning)
        {
            chest.rotateAngleY = (45F / radianF) + (f2 / 2F);
            chestb.rotateAngleY = (45F / radianF) + (f2 / 2F);
        }
        else
        {
            chest.rotateAngleY = 45F / radianF;
            chestb.rotateAngleY = 45F / radianF;
        }

        if (openChest)
        {
            chest.rotationPointZ = -7F;
            chestb.rotationPointZ = -7F;

            if (blocksText[0] != 30)
            {
                blocks[0][blocksText[0]].rotateAngleY = -60F / radianF;
            }
            if (blocksText[1] != 30)
            {
                blocks[1][blocksText[1]].rotateAngleY = -55F / radianF;
            }
            if (blocksText[2] != 30)
            {
                blocks[2][blocksText[2]].rotateAngleY = 60F / radianF;
            }
            if (blocksText[3] != 30)
            {
                blocks[3][blocksText[3]].rotateAngleY = 55F / radianF;
            }
        }
        else
        {
            chest.rotationPointZ = -4F;
            chestb.rotationPointZ = -4F;

            if (blocksText[0] != 30)
            {
                blocks[0][blocksText[0]].rotateAngleY = -40F / radianF;
            }
            if (blocksText[1] != 30)
            {
                blocks[1][blocksText[1]].rotateAngleY = -41F / radianF;
            }
            if (blocksText[2] != 30)
            {
                blocks[2][blocksText[2]].rotateAngleY = 40F / radianF;
            }
            if (blocksText[3] != 30)
            {
                blocks[3][blocksText[3]].rotateAngleY = 41F / radianF;
            }
        }

        if (blocksText[15] != 30)
        {
            blocks[15][blocksText[15]].rotateAngleX = (-20F / radianF) + LLegXRot;
        }

        if (blocksText[16] != 30)
        {
            blocks[16][blocksText[16]].rotateAngleX = LLegXRot;
        }

        if (blocksText[17] != 30)
        {
            blocks[17][blocksText[17]].rotateAngleX = LLegXRot;
        }

        if (blocksText[18] != 30)
        {
            blocks[18][blocksText[18]].rotateAngleX = (-20F / radianF) + RLegXRot;
        }

        if (blocksText[19] != 30)
        {
            blocks[19][blocksText[19]].rotateAngleX = RLegXRot;
        }

        if (blocksText[20] != 30)
        {
            blocks[20][blocksText[20]].rotateAngleX = RLegXRot;
        }

        if (throwing)
        {
            LLegXRot = -90F / radianF;
            RLegXRot = -90F / radianF;
            RArmZRot = 0F;
            LArmZRot = 0F;
        }

        if (blocksText[12] != 30)
        {
            blocks[12][blocksText[12]].rotateAngleZ = (40F / radianF) + RArmZRot;
            blocks[12][blocksText[12]].rotateAngleX = LLegXRot;
        }

        if (blocksText[13] != 30)
        {
            blocks[13][blocksText[13]].rotateAngleZ = (12F / radianF) + RArmZRot;
            blocks[13][blocksText[13]].rotateAngleX = LLegXRot;
        }

        if (blocksText[14] != 30)
        {
            blocks[14][blocksText[14]].rotateAngleZ = RArmZRot;
            blocks[14][blocksText[14]].rotateAngleX = LLegXRot;
        }

        if (blocksText[9] != 30)
        {
            blocks[9][blocksText[9]].rotateAngleZ = (-40F / radianF) + LArmZRot;
            blocks[9][blocksText[9]].rotateAngleX = RLegXRot;
        }

        if (blocksText[10] != 30)
        {
            blocks[10][blocksText[10]].rotateAngleZ = (-12F / radianF) + LArmZRot;
            blocks[10][blocksText[10]].rotateAngleX = RLegXRot;
        }

        if (blocksText[11] != 30)
        {
            blocks[11][blocksText[11]].rotateAngleZ = LArmZRot;
            blocks[11][blocksText[11]].rotateAngleX = RLegXRot;
        }

    }

}
