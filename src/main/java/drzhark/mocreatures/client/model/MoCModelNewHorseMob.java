package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCModelNewHorseMob extends MoCModelNewHorse {

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityHorseMob entityhorse = (MoCEntityHorseMob) entity;

        int type = entityhorse.getType();
        int vanishingInt = 0;//entityhorse.vanishCounter;
        boolean saddled = false;//entityhorse.getIsRideable();
        boolean wings = (entityhorse.isFlyer() && !entityhorse.isGhost() && type < 50);
        boolean eating = (entityhorse.eatingCounter != 0);//entityhorse.getEating();
        boolean standing = (entityhorse.standCounter != 0 && entityhorse.riddenByEntity == null);
        boolean openMouth = (entityhorse.mouthCounter != 0);
        boolean moveTail = (entityhorse.tailCounter != 0);
        boolean flapwings = (entityhorse.wingFlapCounter != 0);
        boolean rider = (entityhorse.riddenByEntity != null);
        boolean floating = (entityhorse.isGhost() || (entityhorse.isFlyer() && entityhorse.isOnAir()));

        setRotationAngles(f, f1, f2, f3, f4, f5, eating, rider, floating, standing, saddled, moveTail, wings, flapwings, false, 0);

        if (!entityhorse.isGhost() && vanishingInt == 0) {
            if (saddled) {
                this.HeadSaddle.render(f5);
                this.Saddle.render(f5);
                this.SaddleB.render(f5);
                this.SaddleC.render(f5);
                this.SaddleL.render(f5);
                this.SaddleL2.render(f5);
                this.SaddleR.render(f5);
                this.SaddleR2.render(f5);
                this.SaddleMouthL.render(f5);
                this.SaddleMouthR.render(f5);
                if (rider) {
                    this.SaddleMouthLine.render(f5);
                    this.SaddleMouthLineR.render(f5);
                }

            }

            if (type == 12) //mule
            {
                this.MuleEarL.render(f5);
                this.MuleEarR.render(f5);
            } else {
                this.Ear1.render(f5);
                this.Ear2.render(f5);

            }
            this.Neck.render(f5);
            this.Head.render(f5);
            if (openMouth) {
                this.UMouth2.render(f5);
                this.LMouth2.render(f5);
            } else {
                this.UMouth.render(f5);
                this.LMouth.render(f5);
            }
            this.Mane.render(f5);
            this.Body.render(f5);
            this.TailA.render(f5);
            this.TailB.render(f5);
            this.TailC.render(f5);

            this.Leg1A.render(f5);
            this.Leg1B.render(f5);
            this.Leg1C.render(f5);

            this.Leg2A.render(f5);
            this.Leg2B.render(f5);
            this.Leg2C.render(f5);

            this.Leg3A.render(f5);
            this.Leg3B.render(f5);
            this.Leg3C.render(f5);

            this.Leg4A.render(f5);
            this.Leg4B.render(f5);
            this.Leg4C.render(f5);

            if (entityhorse.isUnicorned()) {
                this.Unicorn.render(f5);
            }

            /*
             * if (entityhorse.getChestedHorse()) { Bag1.render(f5);
             * Bag2.render(f5); }
             */

            if (entityhorse.isFlyer() && type != 34 && type != 36)//pegasus
            {
                this.MidWing.render(f5);
                this.InnerWing.render(f5);
                this.OuterWing.render(f5);
                this.InnerWingR.render(f5);
                this.MidWingR.render(f5);
                this.OuterWingR.render(f5);
            } else if (type == 34 || type == 36)//fairy
            {
                GL11.glPushMatrix();
                GL11.glEnable(3042 /* GL_BLEND */);
                float transparency = 0.6F;
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
                GL11.glScalef(1.3F, 1.0F, 1.3F);
                this.ButterflyL.render(f5);
                this.ButterflyR.render(f5);
                GL11.glDisable(3042/* GL_BLEND */);
                GL11.glPopMatrix();
            }/*
              * else { ButterflyL.render(f5); ButterflyR.render(f5); }
              */

        } else
        //rendering a ghost
        {
            float transparency; //= entityhorse.tFloat();

            if (vanishingInt != 0) {
                transparency = 1.0F - (((float) vanishingInt) / 25);
            } else {
                transparency = entityhorse.tFloat();
            }

            GL11.glPushMatrix();
            GL11.glEnable(3042 /* GL_BLEND */);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            GL11.glScalef(1.3F, 1.0F, 1.3F);

            this.Ear1.render(f5);
            this.Ear2.render(f5);

            this.Neck.render(f5);
            this.Head.render(f5);
            if (openMouth) {
                this.UMouth2.render(f5);
                this.LMouth2.render(f5);
            } else {
                this.UMouth.render(f5);
                this.LMouth.render(f5);
            }

            this.Mane.render(f5);
            this.Body.render(f5);
            this.TailA.render(f5);
            this.TailB.render(f5);
            this.TailC.render(f5);

            this.Leg1A.render(f5);
            this.Leg1B.render(f5);
            this.Leg1C.render(f5);

            this.Leg2A.render(f5);
            this.Leg2B.render(f5);
            this.Leg2C.render(f5);

            this.Leg3A.render(f5);
            this.Leg3B.render(f5);
            this.Leg3C.render(f5);

            this.Leg4A.render(f5);
            this.Leg4B.render(f5);
            this.Leg4C.render(f5);
            this.ButterflyL.render(f5);
            this.ButterflyR.render(f5);
            /*
             * MidWing.render(f5); InnerWing.render(f5); OuterWing.render(f5);
             * InnerWingR.render(f5); MidWingR.render(f5);
             * OuterWingR.render(f5);
             */

            if (saddled) {
                this.HeadSaddle.render(f5);
                this.Saddle.render(f5);
                this.SaddleB.render(f5);
                this.SaddleC.render(f5);
                this.SaddleL.render(f5);
                this.SaddleL2.render(f5);
                this.SaddleR.render(f5);
                this.SaddleR2.render(f5);
                this.SaddleMouthL.render(f5);
                this.SaddleMouthR.render(f5);
                if (rider) {
                    this.SaddleMouthLine.render(f5);
                    this.SaddleMouthLineR.render(f5);
                }

            }

            GL11.glDisable(3042/* GL_BLEND */);
            GL11.glPopMatrix();
        }

    }
}
