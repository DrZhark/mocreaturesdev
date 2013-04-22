package drzhark.mocreatures.client.model;

import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;

@SideOnly(Side.CLIENT)
public class MoCModelNewHorseMob extends MoCModelNewHorse {
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
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

        if (!entityhorse.isGhost() && vanishingInt == 0)
        {
            if (saddled)
            {
                HeadSaddle.render(f5);
                Saddle.render(f5);
                SaddleB.render(f5);
                SaddleC.render(f5);
                SaddleL.render(f5);
                SaddleL2.render(f5);
                SaddleR.render(f5);
                SaddleR2.render(f5);
                SaddleMouthL.render(f5);
                SaddleMouthR.render(f5);
                if (rider)
                {
                    SaddleMouthLine.render(f5);
                    SaddleMouthLineR.render(f5);
                }

            }

            if (type == 12) //mule
            {
                MuleEarL.render(f5);
                MuleEarR.render(f5);
            }
            else
            {
                Ear1.render(f5);
                Ear2.render(f5);

            }
            Neck.render(f5);
            Head.render(f5);
            if (openMouth)
            {
                UMouth2.render(f5);
                LMouth2.render(f5);
            }
            else
            {
                UMouth.render(f5);
                LMouth.render(f5);
            }
            Mane.render(f5);
            Body.render(f5);
            TailA.render(f5);
            TailB.render(f5);
            TailC.render(f5);

            Leg1A.render(f5);
            Leg1B.render(f5);
            Leg1C.render(f5);

            Leg2A.render(f5);
            Leg2B.render(f5);
            Leg2C.render(f5);

            Leg3A.render(f5);
            Leg3B.render(f5);
            Leg3C.render(f5);

            Leg4A.render(f5);
            Leg4B.render(f5);
            Leg4C.render(f5);

            if (entityhorse.isUnicorned())
            {
                Unicorn.render(f5);
            }

            /*if (entityhorse.getChestedHorse())
            {
                Bag1.render(f5);
                Bag2.render(f5);
            }*/

            if (entityhorse.isFlyer() && type != 34 && type != 36)//pegasus
            {
                MidWing.render(f5);
                InnerWing.render(f5);
                OuterWing.render(f5);
                InnerWingR.render(f5);
                MidWingR.render(f5);
                OuterWingR.render(f5);
            }
            else if (type == 34 || type == 36)//fairy
            {
                GL11.glPushMatrix();
                GL11.glEnable(3042 /*GL_BLEND*/);
                float transparency = 0.6F;
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
                GL11.glScalef(1.3F, 1.0F, 1.3F);
                ButterflyL.render(f5);
                ButterflyR.render(f5);
                GL11.glDisable(3042/*GL_BLEND*/);
                GL11.glPopMatrix();
            }/*else
                {
                ButterflyL.render(f5);
                   ButterflyR.render(f5);
                }*/

        }
        else
        //rendering a ghost
        {
            float transparency; //= entityhorse.tFloat();

            if (vanishingInt != 0)
            {
                transparency = 1.0F - (((float) vanishingInt) / 25);
            }
            else
            {
                transparency = entityhorse.tFloat();
            }

            GL11.glPushMatrix();
            GL11.glEnable(3042 /*GL_BLEND*/);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            GL11.glScalef(1.3F, 1.0F, 1.3F);

            Ear1.render(f5);
            Ear2.render(f5);

            Neck.render(f5);
            Head.render(f5);
            if (openMouth)
            {
                UMouth2.render(f5);
                LMouth2.render(f5);
            }
            else
            {
                UMouth.render(f5);
                LMouth.render(f5);
            }

            Mane.render(f5);
            Body.render(f5);
            TailA.render(f5);
            TailB.render(f5);
            TailC.render(f5);

            Leg1A.render(f5);
            Leg1B.render(f5);
            Leg1C.render(f5);

            Leg2A.render(f5);
            Leg2B.render(f5);
            Leg2C.render(f5);

            Leg3A.render(f5);
            Leg3B.render(f5);
            Leg3C.render(f5);

            Leg4A.render(f5);
            Leg4B.render(f5);
            Leg4C.render(f5);
            ButterflyL.render(f5);
            ButterflyR.render(f5);
            /*MidWing.render(f5);
            InnerWing.render(f5);
            OuterWing.render(f5);
            InnerWingR.render(f5);
            MidWingR.render(f5);
            OuterWingR.render(f5);*/

            if (saddled)
            {
                HeadSaddle.render(f5);
                Saddle.render(f5);
                SaddleB.render(f5);
                SaddleC.render(f5);
                SaddleL.render(f5);
                SaddleL2.render(f5);
                SaddleR.render(f5);
                SaddleR2.render(f5);
                SaddleMouthL.render(f5);
                SaddleMouthR.render(f5);
                if (rider)
                {
                    SaddleMouthLine.render(f5);
                    SaddleMouthLineR.render(f5);
                }

            }

            GL11.glDisable(3042/*GL_BLEND*/);
            GL11.glPopMatrix();
        }

    }
}
