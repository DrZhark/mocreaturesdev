package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;

@SideOnly(Side.CLIENT)
public class MoCModelSnake extends ModelBase {

    public MoCModelSnake()
    {
        textureWidth = 64;
        textureHeight = 32;

        bodySnake = new ModelRenderer[bodyparts];
        float factor = -0.3F;
        float fsegm = 1F / 8F;
        float flength = 0.0F;
        float fsep = -1.6F;
        for (int i = 0; i < bodyparts; i++)
        {

            flength = ((bodyparts / 2) - i) * fsep;

            float fport = ((i + 1F) / bodyparts);
            if (fport < fsegm) //front narrower
            {
                factor = -0.20F;

            }
            else if (fport < (fsegm * 2F)) //front narrow
            {
                factor = -0.15F;
            }
            else if (fport < (fsegm * 4F)) //front from middle body
            {
                factor = 0.0F;
            }
            else if (fport < (fsegm * 6F)) //back from middle body
            {
                factor = 0.0F;
            }
            else if (fport < (fsegm * 7F)) //back narrow
            {
                factor = -0.15F;
            }
            else
            //back narrower
            {
                factor = -0.2F;
            }

            int j = 0;
            if (i % 2 == 0)
            {
                j = 0;
                //System.out.println("Given number is Even.");
            }
            else
            {
                j = 4;
                //System.out.println("Given number is Odd.");
            }
            bodySnake[i] = new ModelRenderer(this, 8, j);
            bodySnake[i].addBox(-1F, -0.5F, 0F, 2, 2, 2, factor);
            bodySnake[i].setRotationPoint(0F, 23F, flength);

        }
        Tail = new ModelRenderer(this, 36, 0);
        Tail.addBox(-0.5F, 0.5F, -1.0F, 1, 1, 5);
        Tail.setRotationPoint(0F, 23F, flength);

        flength = (bodyparts / 2) * fsep;
        Head = new ModelRenderer(this, 0, 0);
        Head.addBox(-1F, -0.5F, -2F, 2, 2, 2);
        Head.setRotationPoint(0F, 23F, flength);

        Nose = new ModelRenderer(this, 16, 0);
        Nose.addBox(-0.5F, -0.3F, -4F, 1, 1, 2);
        Nose.setRotationPoint(0F, 23F, flength);

        LNose = new ModelRenderer(this, 22, 0);
        LNose.addBox(-0.5F, 0.3F, -4F, 1, 1, 2);
        LNose.setRotationPoint(0F, 23F, flength);

        TeethUR = new ModelRenderer(this, 46, 0);
        TeethUR.addBox(-0.4F, 0.3F, -3.8F, 0, 1, 1);
        TeethUR.setRotationPoint(0F, 23F, flength);

        TeethUL = new ModelRenderer(this, 44, 0);
        TeethUL.addBox(0.4F, 0.3F, -3.8F, 0, 1, 1);
        TeethUL.setRotationPoint(0F, 23F, flength);

        Tongue = new ModelRenderer(this, 28, 0);
        Tongue.addBox(-0.5F, 0.5F, -6F, 1, 0, 3);
        Tongue.setRotationPoint(0F, 23F, flength);

        Tongue1 = new ModelRenderer(this, 28, 0);
        Tongue1.addBox(-0.5F, 0.5F, -5F, 1, 0, 3);
        Tongue1.setRotationPoint(0F, 23F, flength);

        Tongue0 = new ModelRenderer(this, 28, 0);
        Tongue0.addBox(-0.5F, 0.25F, -4F, 1, 0, 3);
        Tongue0.setRotationPoint(0F, 23F, flength);

        Wing1L = new ModelRenderer(this, 8, 4);
        Wing1L.addBox(0F, -0.5F, 0F, 2, 2, 2);
        Wing1L.setRotationPoint(0F, 23F, ((bodyparts / 2) - 1) * fsep);

        Wing1R = new ModelRenderer(this, 8, 4);
        Wing1R.addBox(-2F, -0.5F, 0F, 2, 2, 2);
        Wing1R.setRotationPoint(0F, 23F, ((bodyparts / 2) - 1) * fsep);

        Wing2L = new ModelRenderer(this, 8, 4);
        Wing2L.addBox(0.5F, -0.5F, 0F, 2, 2, 2);
        Wing2L.setRotationPoint(0F, 23F, ((bodyparts / 2) - 2) * fsep);

        Wing2R = new ModelRenderer(this, 8, 4);
        Wing2R.addBox(-2.5F, -0.5F, 0F, 2, 2, 2);
        Wing2R.setRotationPoint(0F, 23F, ((bodyparts / 2) - 2) * fsep);

        Wing3R = new ModelRenderer(this, 16, 4);
        Wing3R.addBox(-3F, -0.5F, 0F, 2, 2, 2);
        Wing3R.setRotationPoint(0F, 23F, ((bodyparts / 2) - 3) * fsep);

        Wing3L = new ModelRenderer(this, 16, 4);
        Wing3L.addBox(1F, -0.5F, 0F, 2, 2, 2);
        Wing3L.setRotationPoint(0F, 23F, ((bodyparts / 2) - 3) * fsep);

        Wing4R = new ModelRenderer(this, 16, 8);
        Wing4R.addBox(-2.5F, -0.5F, 0F, 2, 2, 2);
        Wing4R.setRotationPoint(0F, 23F, ((bodyparts / 2) - 4) * fsep);

        Wing4L = new ModelRenderer(this, 16, 8);
        Wing4L.addBox(0.5F, -0.5F, 0F, 2, 2, 2);
        Wing4L.setRotationPoint(0F, 23F, ((bodyparts / 2) - 4) * fsep);

        Wing5L = new ModelRenderer(this, 16, 8);
        Wing5L.addBox(0F, -0.5F, 0F, 2, 2, 2);
        Wing5L.setRotationPoint(0F, 23F, ((bodyparts / 2) - 5) * fsep);

        Wing5R = new ModelRenderer(this, 16, 8);
        Wing5R.addBox(-2F, -0.5F, 0F, 2, 2, 2);
        Wing5R.setRotationPoint(0F, 23F, ((bodyparts / 2) - 5) * fsep);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntitySnake entitysnake = (MoCEntitySnake) entity;
        int typeI = entitysnake.getType();
        float tongueOff = entitysnake.getfTongue();
        float mouthOff = entitysnake.getfMouth();
        float rattleOff = entitysnake.getfRattle();
        boolean climbing = entitysnake.isClimbing();
        boolean isresting = entitysnake.isResting();
        int movInt = entitysnake.getMovInt();
        float f6 = entitysnake.bodyswing;
        boolean nearplayer = entitysnake.getNearPlayer();
        boolean picked = entitysnake.pickedUp();
        float fsize = entitysnake.getSizeF();
        setRotationAngles(f3, f4, tongueOff, mouthOff, rattleOff, nearplayer, typeI);

        float sidef = 0.0F;
        float anglef = 10F;

        //y = A * sin(w * t - k *x)
        /*
         * w1 = speed of wave propagation +/- as needed
         * t = time
         * k = number of waves
         * A = amplitude of wave (how much will it depart from center
         * x = position? :)

        *A 1.3
        *k 0.5
        *w -3.5
         */

        float A = 0.4F;//0.8F;
        float w = 1.5F;
        float t = f / 2;

        for (int i = 0; i < bodyparts; i++)
        {
            float sideperf = 1F;
            float yOff = 0F;
            //sideperf = 1F;

            GL11.glPushMatrix();
            if (isresting)
            {
                //this part doesn't work at all
                /*if (i > (bodyparts/3) && (i <2*bodyparts/3))
                {
                  direction = -1;
                }else
                {
                  direction = 1;
                }*/

                //this shortens the snake while resting
                //TODO reactivate ?
                //GL11.glTranslatef(0.0F, 0.0F, -0.05F*i);
            }
            else

            //climbing animation
            if (climbing && i < bodyparts / 2)
            {
                yOff = (i - (bodyparts / 2)) * 0.08F;
                GL11.glTranslatef(0.0F, yOff / 3.0F, -yOff * 1.2F);
            }
            else

            //raises head of snakes near player
            if (nearplayer || picked)//&& !(picked && fsize<0.6F))//&& !picked)
            {
                if (i < bodyparts / 3)
                {
                    yOff = (i - (bodyparts / 3)) * 0.09F;
                    float zOff = (i - (bodyparts / 3)) * 0.065F;
                    /*if (picked) 
                    {
                      yOff = yOff*-1F;
                      //zOff = zOff*-1F;
                    }*/
                    GL11.glTranslatef(0.0F, yOff / 1.5F, -zOff * f6);
                }

                if (i < bodyparts / 6)
                {
                    sideperf = 0.0F;
                }
                else
                {
                    sideperf = ((i - 7) / (bodyparts / 3F));
                    if (sideperf > 1.0F)
                    {
                        sideperf = 1.0F;
                    }
                }

            }

            //raises tail of rattlesnakes
            if (typeI == 7 && nearplayer && i > (5 * bodyparts / 6) && !picked)//&& not picked
            {
                yOff = 0.55F + ((i - bodyparts)) * 0.08F;
                GL11.glTranslatef(0.0F, -yOff / 1.5F, 0.0F);
            }

            //TODO reactivate once strangling is working

            if (picked && i > bodyparts / 2)//&& big to bring down the tail
            {
                yOff = ((i - (bodyparts / 2))) * 0.08F;
                GL11.glTranslatef(0.0F, yOff / 1.5F, -yOff);

            }

            //not working strangling
            /*if (picked && i > bodyparts/3)//&& big to bring down the tail
            {
              yOff =((i-(bodyparts/3)))*0.08F;
              GL11.glTranslatef(0.0F, yOff/1.5F, 0.0F);
              anglef = (10) * (i+1) ;//works well for small snakes
              
              //anglef = (6) * (i+1) ; //this may work!!
              //GL11.glTranslatef(0.0F, 0.0F, -0.05F*i);
              GL11.glRotatef(anglef, 0.0F, 1.0F, 0.0F);
            }*/

            /*
            if (picked) //&& first persons pesrpective
            {
              GL11.glScalef(fsize, fsize, fsize);
            }*/
            /*if (picked) 
            {
              //curled animation!
              //sidef = 0.35F * MathHelper.sin(w * t - 0.3F * (float)i)- (movInt/10F) * MathHelper.sin(-1F * t - 0.25F * (float)i);
              
              //TODO reactivate this one?
              //sidef = A * MathHelper.sin(w * t - 0.3F * (float)j);
              //sidef = 0.5F * MathHelper.sin(2F * (f)- 0.25F * (float)j) - 0.2F * MathHelper.cos(1.2F *f - 0.15F *(float)j);
                
              //sidef = MathHelper.sqrt_float(i/10F);
              //sidef = sidef * sideperf;
              //GL11.glTranslatef(sidef, 0.0F, 0.0F);
              
              anglef = (8) * (i+1) ;//works well for small snakes
              
              //anglef = (6) * (i+1) ; //this may work!!
              GL11.glTranslatef(0.0F, 0.0F, -0.05F*i);
              GL11.glRotatef(anglef, 0.0F, 1.0F, 0.0F);
            }else*/
            {

                sidef = 0.5F * MathHelper.sin(w * t - 0.3F * (float) i) - (movInt / 20F) * MathHelper.sin(+0.8F * t - 0.2F * (float) i);
                sidef = sidef * sideperf;
                //GL11.glTranslatef(0.0F, 0.0F, -0.05F*i);

                GL11.glTranslatef(sidef, 0.0F, 0.0F);
            }

            //this one works as well
            //sidef = 0.5F * MathHelper.sin(w * t - 0.3F * (float)j)- 0.1F * MathHelper.sin(+0.8F * t - 0.2F * (float)j);
            //sidef = sidef * sideperf;

            //GL11.glTranslatef(sidef, 0.0F, sidef/5);
            //sidef = (0.4F * MathHelper.sin(-3.7F * t - 0.2F * (float)i)) + (0.3F * MathHelper.sin(-2F * t - 0.2F * (float)i));
            //sidef = 1.1F * MathHelper.sin(-2F * t - 0.2F * (float)i);

            {

            }

            bodySnake[i].render(f5);

            if (i == 0)
            {
                Head.render(f5);
                Nose.render(f5);

                LNose.render(f5);
                TeethUR.render(f5);
                TeethUL.render(f5);

                if (tongueOff != 0.0F)
                {
                    if (mouthOff != 0.0F || tongueOff < 2.0F || tongueOff > 7.0F)
                    {
                        Tongue1.render(f5);
                    }
                    else
                    {
                        Tongue.render(f5);
                    }

                }
                else
                {
                    Tongue0.render(f5);
                }

            }

            //if cobra //() nearplayer && 

            if (typeI == 6 && nearplayer)//cobra
            {
                if (i == 1)
                {
                    Wing1L.render(f5);
                    Wing1R.render(f5);
                }
                if (i == 2)
                {
                    Wing2L.render(f5);
                    Wing2R.render(f5);
                }
                if (i == 3)
                {
                    Wing3L.render(f5);
                    Wing3R.render(f5);
                }
                if (i == 4)
                {
                    Wing4L.render(f5);
                    Wing4R.render(f5);
                }
                if (i == 5)
                {
                    Wing5L.render(f5);
                    Wing5R.render(f5);
                }
            }

            if (i == bodyparts - 1 && typeI == 7)
            {
                Tail.render(f5);
            }

            GL11.glPopMatrix();

        }

    }

    /*
     * public float sinusMovement(float A, float w, float t, float k, float x)
    {
      return A * MathHelper.sin(w* t - k * x);
    }
    */

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f3, float f4, float f6, float f7, float frattle, boolean nearP, int type)
    {
        float rAX = (f4 / 57.29578F);// * 1.5F;
        float rAY = (f3 / 57.29578F);// * 1.5F;
        Head.rotateAngleX = rAX;
        Head.rotateAngleY = rAY;
        bodySnake[0].rotateAngleX = rAX * 0.95F;/// 1.5F;
        bodySnake[1].rotateAngleX = rAX * 0.90F;/// 2F;
        bodySnake[2].rotateAngleX = rAX * 0.85F;/// 2.5F;
        bodySnake[3].rotateAngleX = rAX * 0.80F;/// 3.0F;
        bodySnake[4].rotateAngleX = rAX * 0.75F;/// 3.5F;

        float f8 = (MathHelper.cos(f6 * 10F) / 40F);

        Nose.rotateAngleX = Head.rotateAngleX - f7;
        LNose.rotateAngleX = Head.rotateAngleX + f7;
        Tongue1.rotateAngleX = Head.rotateAngleX + f8;
        Tongue.rotateAngleX = Head.rotateAngleX + f8;
        Tongue0.rotateAngleX = LNose.rotateAngleX;
        TeethUR.rotateAngleX = Head.rotateAngleX - f7;
        TeethUL.rotateAngleX = Head.rotateAngleX - f7;
        bodySnake[0].rotateAngleY = 0.0F + (rAY * 0.85F);/// 1.5F;
        bodySnake[1].rotateAngleY = 0.0F + (rAY * 0.65F);/// 2.0F;
        bodySnake[2].rotateAngleY = 0.0F + (rAY * 0.45F);/// 2.5F;
        bodySnake[3].rotateAngleY = 0.0F + (rAY * 0.25F);/// 3.0F;
        bodySnake[4].rotateAngleY = 0.0F + (rAY * 0.10F);/// 3.5F;
        Nose.rotateAngleY = Head.rotateAngleY;
        Tongue.rotateAngleY = Head.rotateAngleY;
        Tongue0.rotateAngleY = Head.rotateAngleY;
        Tongue1.rotateAngleY = Head.rotateAngleY;
        LNose.rotateAngleY = Head.rotateAngleY;
        TeethUR.rotateAngleY = Head.rotateAngleY;
        TeethUL.rotateAngleY = Head.rotateAngleY;

        if (type == 6) //cobra
        {
            Wing1L.rotateAngleX = bodySnake[1].rotateAngleX;
            Wing1L.rotateAngleY = bodySnake[1].rotateAngleY;
            Wing1R.rotateAngleX = bodySnake[1].rotateAngleX;
            Wing1R.rotateAngleY = bodySnake[1].rotateAngleY;

            Wing2L.rotateAngleX = bodySnake[2].rotateAngleX;
            Wing2L.rotateAngleY = bodySnake[2].rotateAngleY;
            Wing2R.rotateAngleX = bodySnake[2].rotateAngleX;
            Wing2R.rotateAngleY = bodySnake[2].rotateAngleY;

            Wing3L.rotateAngleX = bodySnake[3].rotateAngleX;
            Wing3L.rotateAngleY = bodySnake[3].rotateAngleY;
            Wing3R.rotateAngleX = bodySnake[3].rotateAngleX;
            Wing3R.rotateAngleY = bodySnake[3].rotateAngleY;

            Wing4L.rotateAngleX = bodySnake[4].rotateAngleX;
            Wing4L.rotateAngleY = bodySnake[4].rotateAngleY;
            Wing4R.rotateAngleX = bodySnake[4].rotateAngleX;
            Wing4R.rotateAngleY = bodySnake[4].rotateAngleY;

            Wing5L.rotateAngleX = bodySnake[4].rotateAngleX;
            Wing5L.rotateAngleY = bodySnake[4].rotateAngleY;
            Wing5R.rotateAngleX = bodySnake[4].rotateAngleX;
            Wing5R.rotateAngleY = bodySnake[4].rotateAngleY;
        }

        if (type == 7) //rattlesnake
        {
            if (nearP || frattle != 0.0F)
            {
                Tail.rotateAngleX = ((MathHelper.cos(f3 * 10F) * 20F) + 90F) / 57.29578F;
                //Tail.rotateAngleX = ((MathHelper.cos(f3*10F)*20F) + 90F)/57.29578F;
            }
            else
            {
                Tail.rotateAngleX = 0.0F;
            }
        }
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
    }

    ModelRenderer Head;
    ModelRenderer Nose;
    ModelRenderer Tongue0;
    ModelRenderer Tongue;
    ModelRenderer Tongue1;
    ModelRenderer LNose;
    ModelRenderer TeethUR;
    ModelRenderer TeethUL;
    ModelRenderer Tail;

    ModelRenderer Wing1L;
    ModelRenderer Wing1R;
    ModelRenderer Wing2L;
    ModelRenderer Wing2R;
    ModelRenderer Wing3R;
    ModelRenderer Wing3L;
    ModelRenderer Wing4R;
    ModelRenderer Wing4L;
    ModelRenderer Wing5L;
    ModelRenderer Wing5R;

    public ModelRenderer bodySnake[];
    //public ModelRenderer WingL[];
    //public ModelRenderer WingR[];
    private final int bodyparts = 40;

}
