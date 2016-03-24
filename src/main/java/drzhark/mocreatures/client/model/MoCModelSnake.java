package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCModelSnake extends ModelBase {

    public MoCModelSnake() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.bodySnake = new ModelRenderer[this.bodyparts];
        float factor = -0.3F;
        float fsegm = 1F / 8F;
        float flength = 0.0F;
        float fsep = -1.6F;
        for (int i = 0; i < this.bodyparts; i++) {

            flength = ((this.bodyparts / 2) - i) * fsep;

            float fport = ((i + 1F) / this.bodyparts);
            if (fport < fsegm) //front narrower
            {
                factor = -0.20F;

            } else if (fport < (fsegm * 2F)) //front narrow
            {
                factor = -0.15F;
            } else if (fport < (fsegm * 4F)) //front from middle body
            {
                factor = 0.0F;
            } else if (fport < (fsegm * 6F)) //back from middle body
            {
                factor = 0.0F;
            } else if (fport < (fsegm * 7F)) //back narrow
            {
                factor = -0.15F;
            } else
            //back narrower
            {
                factor = -0.2F;
            }

            int j = 0;
            if (i % 2 == 0) {
                j = 0;
                //System.out.println("Given number is Even.");
            } else {
                j = 4;
                //System.out.println("Given number is Odd.");
            }
            this.bodySnake[i] = new ModelRenderer(this, 8, j);
            this.bodySnake[i].addBox(-1F, -0.5F, 0F, 2, 2, 2, factor);
            this.bodySnake[i].setRotationPoint(0F, 23F, flength);

        }
        this.Tail = new ModelRenderer(this, 36, 0);
        this.Tail.addBox(-0.5F, 0.5F, -1.0F, 1, 1, 5);
        this.Tail.setRotationPoint(0F, 23F, flength);

        flength = (this.bodyparts / 2) * fsep;
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-1F, -0.5F, -2F, 2, 2, 2);
        this.Head.setRotationPoint(0F, 23F, flength);

        this.Nose = new ModelRenderer(this, 16, 0);
        this.Nose.addBox(-0.5F, -0.3F, -4F, 1, 1, 2);
        this.Nose.setRotationPoint(0F, 23F, flength);

        this.LNose = new ModelRenderer(this, 22, 0);
        this.LNose.addBox(-0.5F, 0.3F, -4F, 1, 1, 2);
        this.LNose.setRotationPoint(0F, 23F, flength);

        this.TeethUR = new ModelRenderer(this, 46, 0);
        this.TeethUR.addBox(-0.4F, 0.3F, -3.8F, 0, 1, 1);
        this.TeethUR.setRotationPoint(0F, 23F, flength);

        this.TeethUL = new ModelRenderer(this, 44, 0);
        this.TeethUL.addBox(0.4F, 0.3F, -3.8F, 0, 1, 1);
        this.TeethUL.setRotationPoint(0F, 23F, flength);

        this.Tongue = new ModelRenderer(this, 28, 0);
        this.Tongue.addBox(-0.5F, 0.5F, -6F, 1, 0, 3);
        this.Tongue.setRotationPoint(0F, 23F, flength);

        this.Tongue1 = new ModelRenderer(this, 28, 0);
        this.Tongue1.addBox(-0.5F, 0.5F, -5F, 1, 0, 3);
        this.Tongue1.setRotationPoint(0F, 23F, flength);

        this.Tongue0 = new ModelRenderer(this, 28, 0);
        this.Tongue0.addBox(-0.5F, 0.25F, -4F, 1, 0, 3);
        this.Tongue0.setRotationPoint(0F, 23F, flength);

        this.Wing1L = new ModelRenderer(this, 8, 4);
        this.Wing1L.addBox(0F, -0.5F, 0F, 2, 2, 2);
        this.Wing1L.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 1) * fsep);

        this.Wing1R = new ModelRenderer(this, 8, 4);
        this.Wing1R.addBox(-2F, -0.5F, 0F, 2, 2, 2);
        this.Wing1R.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 1) * fsep);

        this.Wing2L = new ModelRenderer(this, 8, 4);
        this.Wing2L.addBox(0.5F, -0.5F, 0F, 2, 2, 2);
        this.Wing2L.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 2) * fsep);

        this.Wing2R = new ModelRenderer(this, 8, 4);
        this.Wing2R.addBox(-2.5F, -0.5F, 0F, 2, 2, 2);
        this.Wing2R.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 2) * fsep);

        this.Wing3R = new ModelRenderer(this, 16, 4);
        this.Wing3R.addBox(-3F, -0.5F, 0F, 2, 2, 2);
        this.Wing3R.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 3) * fsep);

        this.Wing3L = new ModelRenderer(this, 16, 4);
        this.Wing3L.addBox(1F, -0.5F, 0F, 2, 2, 2);
        this.Wing3L.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 3) * fsep);

        this.Wing4R = new ModelRenderer(this, 16, 8);
        this.Wing4R.addBox(-2.5F, -0.5F, 0F, 2, 2, 2);
        this.Wing4R.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 4) * fsep);

        this.Wing4L = new ModelRenderer(this, 16, 8);
        this.Wing4L.addBox(0.5F, -0.5F, 0F, 2, 2, 2);
        this.Wing4L.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 4) * fsep);

        this.Wing5L = new ModelRenderer(this, 16, 8);
        this.Wing5L.addBox(0F, -0.5F, 0F, 2, 2, 2);
        this.Wing5L.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 5) * fsep);

        this.Wing5R = new ModelRenderer(this, 16, 8);
        this.Wing5R.addBox(-2F, -0.5F, 0F, 2, 2, 2);
        this.Wing5R.setRotationPoint(0F, 23F, ((this.bodyparts / 2) - 5) * fsep);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
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
        setRotationAngles(f3, f4, tongueOff, mouthOff, rattleOff, nearplayer, typeI);

        float sidef = 0.0F;

        // y = A * sin(w * t - k *x)
        // w1 = speed of wave propagation +/- as needed
        // t = time
        // k = number of waves
        // A = amplitude of wave (how much will it depart from center
        // x = position? :)A 1.3k 0.5w -3.5

        @SuppressWarnings("unused")
        float A = 0.4F;//0.8F;
        float w = 1.5F;
        float t = f / 2;

        for (int i = 0; i < this.bodyparts; i++) {
            float sideperf = 1F;
            float yOff = 0F;
            //sideperf = 1F;

            GL11.glPushMatrix();
            if (isresting) {
                //this part doesn't work at all
                /* if (i > (bodyparts/3) && (i <2*bodyparts/3)) {
                       direction = -1;
                   } else {
                       direction = 1; 
                   }*/

                //this shortens the snake while resting
                //TODO reactivate ?
                //GL11.glTranslatef(0.0F, 0.0F, -0.05F*i);
            } else

            //climbing animation
            if (climbing && i < this.bodyparts / 2) {
                yOff = (i - (this.bodyparts / 2)) * 0.08F;
                GL11.glTranslatef(0.0F, yOff / 3.0F, -yOff * 1.2F);
            } else

            //raises head of snakes near player
            if (nearplayer || picked)//&& !(picked && fsize<0.6F))//&& !picked)
            {
                if (i < this.bodyparts / 3) {
                    yOff = (i - (this.bodyparts / 3)) * 0.09F;
                    float zOff = (i - (this.bodyparts / 3)) * 0.065F;
                    // if (picked) { yOff = yOff*-1F; //zOff = zOff*-1F; }
                    GL11.glTranslatef(0.0F, yOff / 1.5F, -zOff * f6);
                }

                if (i < this.bodyparts / 6) {
                    sideperf = 0.0F;
                } else {
                    sideperf = ((i - 7) / (this.bodyparts / 3F));
                    if (sideperf > 1.0F) {
                        sideperf = 1.0F;
                    }
                }

            }

            //raises tail of rattlesnakes
            if (typeI == 7 && nearplayer && i > (5 * this.bodyparts / 6) && !picked)//&& not picked
            {
                yOff = 0.55F + ((i - this.bodyparts)) * 0.08F;
                GL11.glTranslatef(0.0F, -yOff / 1.5F, 0.0F);
            }

            //TODO reactivate once strangling is working

            if (picked && i > this.bodyparts / 2)//&& big to bring down the tail
            {
                yOff = ((i - (this.bodyparts / 2))) * 0.08F;
                GL11.glTranslatef(0.0F, yOff / 1.5F, -yOff);

            }

            //not working strangling
            /*
             * if (picked && i > bodyparts/3)//&& big to bring down the tail {
             * yOff =((i-(bodyparts/3)))*0.08F; GL11.glTranslatef(0.0F,
             * yOff/1.5F, 0.0F); anglef = (10) * (i+1) ;//works well for small
             * snakes //anglef = (6) * (i+1) ; //this may work!!
             * //GL11.glTranslatef(0.0F, 0.0F, -0.05F*i); GL11.glRotatef(anglef,
             * 0.0F, 1.0F, 0.0F); }
             */

            /*
             * if (picked) //&& first persons pesrpective { GL11.glScalef(fsize,
             * fsize, fsize); }
             */
            /*
             * if (picked) { //curled animation! //sidef = 0.35F *
             * MathHelper.sin(w * t - 0.3F * (float)i)- (movInt/10F) *
             * MathHelper.sin(-1F * t - 0.25F * (float)i); //TODO reactivate
             * this one? //sidef = A * MathHelper.sin(w * t - 0.3F * (float)j);
             * //sidef = 0.5F * MathHelper.sin(2F * (f)- 0.25F * (float)j) -
             * 0.2F * MathHelper.cos(1.2F *f - 0.15F *(float)j); //sidef =
             * MathHelper.sqrt_float(i/10F); //sidef = sidef * sideperf;
             * //GL11.glTranslatef(sidef, 0.0F, 0.0F); anglef = (8) * (i+1)
             * ;//works well for small snakes //anglef = (6) * (i+1) ; //this
             * may work!! GL11.glTranslatef(0.0F, 0.0F, -0.05F*i);
             * GL11.glRotatef(anglef, 0.0F, 1.0F, 0.0F); }else
             */
            {

                sidef = 0.5F * MathHelper.sin(w * t - 0.3F * i) - (movInt / 20F) * MathHelper.sin(+0.8F * t - 0.2F * i);
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

            this.bodySnake[i].render(f5);

            if (i == 0) {
                this.Head.render(f5);
                this.Nose.render(f5);

                this.LNose.render(f5);
                this.TeethUR.render(f5);
                this.TeethUL.render(f5);

                if (tongueOff != 0.0F) {
                    if (mouthOff != 0.0F || tongueOff < 2.0F || tongueOff > 7.0F) {
                        this.Tongue1.render(f5);
                    } else {
                        this.Tongue.render(f5);
                    }

                } else {
                    this.Tongue0.render(f5);
                }

            }

            //if cobra //() nearplayer &&

            if (typeI == 6 && nearplayer)//cobra
            {
                if (i == 1) {
                    this.Wing1L.render(f5);
                    this.Wing1R.render(f5);
                }
                if (i == 2) {
                    this.Wing2L.render(f5);
                    this.Wing2R.render(f5);
                }
                if (i == 3) {
                    this.Wing3L.render(f5);
                    this.Wing3R.render(f5);
                }
                if (i == 4) {
                    this.Wing4L.render(f5);
                    this.Wing4R.render(f5);
                }
                if (i == 5) {
                    this.Wing5L.render(f5);
                    this.Wing5R.render(f5);
                }
            }

            if (i == this.bodyparts - 1 && typeI == 7) {
                this.Tail.render(f5);
            }

            GL11.glPopMatrix();
        }
    }

    @SuppressWarnings("unused")
    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f3, float f4, float f6, float f7, float frattle, boolean nearP, int type) {
        float rAX = (f4 / 57.29578F);// * 1.5F;
        float rAY = (f3 / 57.29578F);// * 1.5F;
        this.Head.rotateAngleX = rAX;
        this.Head.rotateAngleY = rAY;
        this.bodySnake[0].rotateAngleX = rAX * 0.95F;/// 1.5F;
        this.bodySnake[1].rotateAngleX = rAX * 0.90F;/// 2F;
        this.bodySnake[2].rotateAngleX = rAX * 0.85F;/// 2.5F;
        this.bodySnake[3].rotateAngleX = rAX * 0.80F;/// 3.0F;
        this.bodySnake[4].rotateAngleX = rAX * 0.75F;/// 3.5F;

        float f8 = (MathHelper.cos(f6 * 10F) / 40F);

        this.Nose.rotateAngleX = this.Head.rotateAngleX - f7;
        this.LNose.rotateAngleX = this.Head.rotateAngleX + f7;
        this.Tongue1.rotateAngleX = this.Head.rotateAngleX + f8;
        this.Tongue.rotateAngleX = this.Head.rotateAngleX + f8;
        this.Tongue0.rotateAngleX = this.LNose.rotateAngleX;
        this.TeethUR.rotateAngleX = this.Head.rotateAngleX - f7;
        this.TeethUL.rotateAngleX = this.Head.rotateAngleX - f7;
        this.bodySnake[0].rotateAngleY = 0.0F + (rAY * 0.85F);/// 1.5F;
        this.bodySnake[1].rotateAngleY = 0.0F + (rAY * 0.65F);/// 2.0F;
        this.bodySnake[2].rotateAngleY = 0.0F + (rAY * 0.45F);/// 2.5F;
        this.bodySnake[3].rotateAngleY = 0.0F + (rAY * 0.25F);/// 3.0F;
        this.bodySnake[4].rotateAngleY = 0.0F + (rAY * 0.10F);/// 3.5F;
        this.Nose.rotateAngleY = this.Head.rotateAngleY;
        this.Tongue.rotateAngleY = this.Head.rotateAngleY;
        this.Tongue0.rotateAngleY = this.Head.rotateAngleY;
        this.Tongue1.rotateAngleY = this.Head.rotateAngleY;
        this.LNose.rotateAngleY = this.Head.rotateAngleY;
        this.TeethUR.rotateAngleY = this.Head.rotateAngleY;
        this.TeethUL.rotateAngleY = this.Head.rotateAngleY;

        if (type == 6) //cobra
        {
            this.Wing1L.rotateAngleX = this.bodySnake[1].rotateAngleX;
            this.Wing1L.rotateAngleY = this.bodySnake[1].rotateAngleY;
            this.Wing1R.rotateAngleX = this.bodySnake[1].rotateAngleX;
            this.Wing1R.rotateAngleY = this.bodySnake[1].rotateAngleY;

            this.Wing2L.rotateAngleX = this.bodySnake[2].rotateAngleX;
            this.Wing2L.rotateAngleY = this.bodySnake[2].rotateAngleY;
            this.Wing2R.rotateAngleX = this.bodySnake[2].rotateAngleX;
            this.Wing2R.rotateAngleY = this.bodySnake[2].rotateAngleY;

            this.Wing3L.rotateAngleX = this.bodySnake[3].rotateAngleX;
            this.Wing3L.rotateAngleY = this.bodySnake[3].rotateAngleY;
            this.Wing3R.rotateAngleX = this.bodySnake[3].rotateAngleX;
            this.Wing3R.rotateAngleY = this.bodySnake[3].rotateAngleY;

            this.Wing4L.rotateAngleX = this.bodySnake[4].rotateAngleX;
            this.Wing4L.rotateAngleY = this.bodySnake[4].rotateAngleY;
            this.Wing4R.rotateAngleX = this.bodySnake[4].rotateAngleX;
            this.Wing4R.rotateAngleY = this.bodySnake[4].rotateAngleY;

            this.Wing5L.rotateAngleX = this.bodySnake[4].rotateAngleX;
            this.Wing5L.rotateAngleY = this.bodySnake[4].rotateAngleY;
            this.Wing5R.rotateAngleX = this.bodySnake[4].rotateAngleX;
            this.Wing5R.rotateAngleY = this.bodySnake[4].rotateAngleY;
        }

        if (type == 7) //rattlesnake
        {
            if (nearP || frattle != 0.0F) {
                this.Tail.rotateAngleX = ((MathHelper.cos(f3 * 10F) * 20F) + 90F) / 57.29578F;
                //Tail.rotateAngleX = ((MathHelper.cos(f3*10F)*20F) + 90F)/57.29578F;
            } else {
                this.Tail.rotateAngleX = 0.0F;
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
