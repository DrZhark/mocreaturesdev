package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;

@SideOnly(Side.CLIENT)
public class MoCModelWyvern extends ModelBase{


 ModelRenderer back4;
 ModelRenderer back3;
 ModelRenderer back2;
 ModelRenderer back1;
 ModelRenderer Tail;
 ModelRenderer tail1;
 ModelRenderer tail2;
 ModelRenderer tail3;
 ModelRenderer tail4;
 ModelRenderer tail5;
 ModelRenderer chest;
 ModelRenderer headplate;
 ModelRenderer neckplate3;
 ModelRenderer neck3;
 ModelRenderer neckplate2;
 ModelRenderer neck2;
 ModelRenderer neckplate1;
 ModelRenderer neck1;
 ModelRenderer rightupleg;
 ModelRenderer rightmidleg;
 ModelRenderer rightlowleg;
 ModelRenderer rightfoot;
 ModelRenderer leftupleg;
 ModelRenderer leftmidleg;
 ModelRenderer leftlowleg;
 ModelRenderer leftupjaw;
 ModelRenderer rightupjaw;
 ModelRenderer righteyesock;
 ModelRenderer lefteyesock;
 ModelRenderer beak;
 ModelRenderer snout;
 ModelRenderer torso;
 ModelRenderer rightshoulder;
 ModelRenderer leftshoulder;
 //ModelRenderer rightwingflap;
 //ModelRenderer leftwingflap;
 ModelRenderer leftfoot;
 ModelRenderer righttoe3;
 ModelRenderer righttoe2;
 ModelRenderer righttoe1;
 ModelRenderer rightclaw1;
 ModelRenderer rightclaw2;
 ModelRenderer rightclaw3;
 ModelRenderer lefttoe3;
 ModelRenderer lefttoe2;
 ModelRenderer lefttoe1;
 ModelRenderer leftclaw1;
 ModelRenderer leftclaw2;
 ModelRenderer leftclaw3;
 ModelRenderer head;
 ModelRenderer Jaw;
 ModelRenderer saddle;
 ModelRenderer mouthrod;
 ModelRenderer controlrope1;
 ModelRenderer controlrope2;
 ModelRenderer storage;
 ModelRenderer helmetstrap1;
 ModelRenderer helmetstrap2;
 //ModelRenderer leftspreadwingflap;
 //ModelRenderer rightspreadwingflap;
 ModelRenderer leftspine1;
 ModelRenderer leftspine2;
 ModelRenderer leftspine3;
 ModelRenderer leftearskin;
 ModelRenderer rightspine3;
 ModelRenderer rightspine2;
 ModelRenderer rightspine1;
 ModelRenderer rightearskin;
 ModelRenderer chestbelt;
 ModelRenderer stomachbelt;
 ModelRenderer ironhelmethorn1;
 ModelRenderer ironhelmethorn2;
 ModelRenderer ironhelmet;
 ModelRenderer ironhelmetsnout;
 ModelRenderer ironrightlegarmor;
 ModelRenderer ironleftlegarmor;
 ModelRenderer ironchestarmor;
 ModelRenderer ironrightshoulderpad;
 ModelRenderer ironleftshoulderpad;
 ModelRenderer goldleftshoulder;
 ModelRenderer goldchestarmor;
 ModelRenderer goldrightshoulder;
 ModelRenderer goldleftlegarmor;
 ModelRenderer goldrightlegarmor;
 ModelRenderer goldhelmethorn1;
 ModelRenderer goldhelmethorn2;
 ModelRenderer goldhelmet;
 ModelRenderer goldhelmetsnout;
 ModelRenderer diamondleftshoulder;
 ModelRenderer diamondrightshoulder;
 ModelRenderer diamondchestarmor;
 ModelRenderer diamondleftlegarmor;
 ModelRenderer diamondrightlegarmor;
 ModelRenderer diamondhelmet;
 ModelRenderer diamondhelmethorn2;
 ModelRenderer diamondhelmethorn1;
 ModelRenderer diamondhelmetsnout;
 ModelRenderer rightfing1a;
 ModelRenderer rightfing2a;
 ModelRenderer rightfing3a;
 ModelRenderer rightfing1b;
 ModelRenderer rightfing2b;
 ModelRenderer rightfing3b;
 ModelRenderer leftfing3a;
 ModelRenderer leftfing2a;
 ModelRenderer leftfing1a;
 ModelRenderer leftfing3b;
 ModelRenderer leftfing2b;
 ModelRenderer leftfing1b;
 ModelRenderer leftlowarm;
 ModelRenderer rightlowarm;
 ModelRenderer rightuparm;
 ModelRenderer leftuparm;
 ModelRenderer LeftWing;
 ModelRenderer RightWing;
 ModelRenderer MainHead;
 
 ModelRenderer leftwingflap1;
 ModelRenderer leftwingflap2;
 ModelRenderer leftwingflap3;
 
 ModelRenderer rightwingflap1;
 ModelRenderer rightwingflap2;
 ModelRenderer rightwingflap3;
 
 
 private float radianF = 57.29578F;
 
 public MoCModelWyvern()
 {
     textureWidth = 128;
     textureHeight = 256;
     
     //tail
     Tail = new ModelRenderer(this);
     Tail.setRotationPoint(0F, 0F, 0F);

     back1 = new ModelRenderer(this, 92, 0);
     back1.addBox(-3F, -2F, -12F, 6, 2, 12);
     back1.setRotationPoint(0F, 0F, 0F);

     tail1 = new ModelRenderer(this, 0, 22);
     tail1.addBox(-4F, 0F, 0F, 8, 8, 10);
     tail1.setRotationPoint(0F, 0F, 0F);
     Tail.addChild(tail1);

     back2 = new ModelRenderer(this, 100, 14);
     back2.addBox(-2F, -2F, 0F, 4, 2, 10);
     back2.setRotationPoint(0F, 0F, 0F);
     tail1.addChild(back2);

     tail2 = new ModelRenderer(this, 0, 40);
     tail2.addBox(-3F, 0F, 0F, 6, 6, 9);
     tail2.setRotationPoint(0F, 0F, 10F);
     tail1.addChild(tail2);

     back3 = new ModelRenderer(this, 104, 26);
     back3.addBox(-1.5F, -2F, 0F, 3, 2, 9);
     back3.setRotationPoint(0F, 0F, 0F);
     tail2.addChild(back3);

     tail3 = new ModelRenderer(this, 0, 55);
     tail3.addBox(-2F, 0F, 0F, 4, 5, 8);
     tail3.setRotationPoint(0F, 0F, 8F);
     tail2.addChild(tail3);

     back4 = new ModelRenderer(this, 108, 37);
     back4.addBox(-1F, -2F, 0F, 2, 2, 8);
     back4.setRotationPoint(0F, 0F, 0F);
     tail3.addChild(back4);

     tail4 = new ModelRenderer(this, 0, 68);
     tail4.addBox(-1F, 0F, 0F, 2, 5, 7);
     tail4.setRotationPoint(0F, -1F, 7F);
     tail3.addChild(tail4);

     tail5 = new ModelRenderer(this, 0, 80);
     tail5.addBox(-0.5F, 0F, 0F, 1, 3, 7);
     tail5.setRotationPoint(0F, 1F, 6F);
     tail4.addChild(tail5);

     chest = new ModelRenderer(this, 44, 0);
     chest.addBox(-4.5F, 2.7F, -13F, 9, 10, 4);
     chest.setRotationPoint(0F, 0F, 0F);
     setRotation(chest, -0.2602503F, 0F, 0F);

     neckplate3 = new ModelRenderer(this, 112, 64);
     neckplate3.addBox(-2F, -2F, -2F, 4, 2, 4);
     neckplate3.setRotationPoint(0F, 0F, -12F);
     setRotation(neckplate3, -0.669215F, 0F, 0F);

     neck3 = new ModelRenderer(this, 100, 113);
     neck3.addBox(-3F, 0F, -2F, 6, 7, 8);
     neck3.setRotationPoint(0F, 0F, -12F);
     setRotation(neck3, -0.669215F, 0F, 0F);
     
     //head
     MainHead = new ModelRenderer(this);
     MainHead.setRotationPoint(0F, 3F, -15F);
     
     neck2 = new ModelRenderer(this, 102, 99);
     neck2.addBox(-2.5F, -3F, -8F, 5, 6, 8);
     //neck2.addBox(-2.5F, -3F, -4F, 5, 6, 8);
     neck2.setRotationPoint(0F, 0F, 0F);
     //setRotation(neck2, -66F/radianF, 0F, 0F);
     MainHead.addChild(neck2);
     
     neckplate2 = new ModelRenderer(this, 106, 54);
     neckplate2.addBox(-1.5F, -2F, -8F, 3, 2, 8);
     neckplate2.setRotationPoint(0F, -3F, 0F);
     neck2.addChild(neckplate2);
     
     neck1 = new ModelRenderer(this, 104, 85);
     neck1.addBox(-2F, -3F, -8F, 4, 6, 8);
     //neck1.addBox(-2F, -3F, -4F, 4, 6, 8);
     neck1.setRotationPoint(0F, -0.5F, -5.5F);
     //setRotation(neck1, 30F/radianF, 0F, 0F);
     neck2.addChild(neck1);
     
     neckplate1 = new ModelRenderer(this, 80, 108);
     neckplate1.addBox(-1F, -2F, -8F, 2, 2, 8);
     neckplate1.setRotationPoint(0F, -3F, 0F);
     neck1.addChild(neckplate1);
     
     head = new ModelRenderer(this, 98, 70);
     head.addBox(-3.5F, -3.5F, -8F, 7, 7, 8);
     head.setRotationPoint(0F, 0F, -7F);
     //setRotation(head, 45F/radianF, 0F, 0F);
     neck1.addChild(head);
     
     snout = new ModelRenderer(this, 72, 70);
     snout.addBox(-2F, -1.5F, -9F, 4, 3, 9);
     snout.setRotationPoint(0F, -1.5F, -8F);
     setRotation(snout, 2F/radianF, 0F, 0F);
     head.addChild(snout);
     
     headplate = new ModelRenderer(this, 80, 118);
     headplate.addBox(-1F, -1F, -4F, 2, 2, 8);
     headplate.setRotationPoint(0F, -3F, -1F);
     setRotation(headplate, 10F/radianF, 0F, 0F);
     head.addChild(headplate);

     beak = new ModelRenderer(this, 60, 85);
     beak.addBox(-1.5F, -2.5F, -1.5F, 3, 5, 3);
     beak.setRotationPoint(0F, 0.8F, -8.0F);
     setRotation(beak, -6F/radianF, 45F/radianF, -6F/radianF);
     snout.addChild(beak);
     
     righteyesock = new ModelRenderer(this, 70, 108);
     righteyesock.addBox(0F, 0F, 0F, 1, 2, 4);
     righteyesock.setRotationPoint(-3.5F, -2.5F, -8F);
     head.addChild(righteyesock);
     
     lefteyesock = new ModelRenderer(this, 70, 114);
     lefteyesock.addBox(0F, 0F, 0F, 1, 2, 4);
     lefteyesock.setRotationPoint(2.5F, -2.5F, -8F);
     head.addChild(lefteyesock);

     Jaw = new ModelRenderer(this, 72, 82);
     Jaw.addBox(-2F, -1F, -9F, 4, 2, 9);
     Jaw.setRotationPoint(0F, 2.5F, -7.5F);
     setRotation(Jaw, -10F/radianF, 0F, 0F);
     head.addChild(Jaw);
     
     leftupjaw = new ModelRenderer(this, 42, 93);
     leftupjaw.addBox(-1F, -1F, -6.5F, 2, 2, 13);
     leftupjaw.setRotationPoint(2F, 0F, -10.5F);
     setRotation(leftupjaw, -10F/radianF, 10F/radianF, 0F);
     head.addChild(leftupjaw);
     
     rightupjaw = new ModelRenderer(this, 72, 93);
     rightupjaw.addBox(-1F, -1F, -6.5F, 2, 2, 13);
     rightupjaw.setRotationPoint(-2F, 0F, -10.5F);
     setRotation(rightupjaw, -10F/radianF, -10F/radianF, 0F);
     head.addChild(rightupjaw);

     

     mouthrod = new ModelRenderer(this, 104, 50);
     mouthrod.addBox(-5F, -1F, -1F, 10, 2, 2);
     mouthrod.setRotationPoint(0F, 1F, -8F);
     head.addChild(mouthrod);

     helmetstrap1 = new ModelRenderer(this, 32, 146);
     helmetstrap1.addBox(-4F, -2F, 0F, 8, 4, 1);
     helmetstrap1.setRotationPoint(0F, 2F, -7.5F);
     head.addChild(helmetstrap1);

     helmetstrap2 = new ModelRenderer(this, 32, 141);
     helmetstrap2.addBox(-4F, -2F, 0F, 8, 4, 1);
     helmetstrap2.setRotationPoint(0F, 2F, -3.5F);
     head.addChild(helmetstrap2);
     
     controlrope1 = new ModelRenderer(this, 66, 43);
     controlrope1.addBox(0F, -2F, 0F, 0, 4, 23);
     controlrope1.setRotationPoint(4.5F, 1F, 0F);
     mouthrod.addChild(controlrope1);
     
     controlrope2 = new ModelRenderer(this, 66, 43);
     controlrope2.addBox(0F, -2F, 0F, 0, 4, 23);
     controlrope2.setRotationPoint(-4.5F, 1F, 0F);
     mouthrod.addChild(controlrope2);
     

     rightearskin = new ModelRenderer(this, 112, 201);
     rightearskin.addBox(0F, -4F, 0F, 0, 8, 8);
     rightearskin.setRotationPoint(-3F, -0.5F, 0F);
     head.addChild(rightearskin);

     leftearskin = new ModelRenderer(this, 96, 201);
     leftearskin.addBox(0F, -4F, 0F, 0, 8, 8);
     leftearskin.setRotationPoint(3F, -0.5F, 0F);
     head.addChild(leftearskin);
     
     rightspine1 = new ModelRenderer(this, 50, 141);
     rightspine1.addBox(-0.5F, -1F, 0F, 1, 2, 8);
     rightspine1.setRotationPoint(0F, -2F, 0F);
     setRotation(rightspine1, 15F/radianF, 0F, 0F);
     rightearskin.addChild(rightspine1);
     
     rightspine2 = new ModelRenderer(this, 50, 141);
     rightspine2.addBox(-0.5F, -1F, 0F, 1, 2, 8);
     rightspine2.setRotationPoint(0F, 0F, 0F);
     //setRotation(rightspine2, 0F, 0F, 0F);
     rightearskin.addChild(rightspine2);
     
     rightspine3 = new ModelRenderer(this, 50, 141);
     rightspine3.addBox(-0.5F, -1F, 0F, 1, 2, 8);
     rightspine3.setRotationPoint(0F, 2F, 0F);
     setRotation(rightspine3, -15F/radianF, 0F, 0F);
     rightearskin.addChild(rightspine3);
     
     leftspine1 = new ModelRenderer(this, 68, 141);
     leftspine1.addBox(-0.5F, -1F, 0F, 1, 2, 8);
     leftspine1.setRotationPoint(0F, -2F, 0F);
     setRotation(leftspine1, 15F/radianF, 0F, 0F);
     leftearskin.addChild(leftspine1);
     
     leftspine2 = new ModelRenderer(this, 68, 141);
     leftspine2.addBox(-0.5F, -1F, 0F, 1, 2, 8);
     leftspine2.setRotationPoint(0F, 0F, 0F);
     leftearskin.addChild(leftspine2);
     
     leftspine3 = new ModelRenderer(this, 68, 141);
     leftspine3.addBox(-0.5F, -1F, 0F, 1, 2, 8);
     leftspine3.setRotationPoint(0F, 2F, 0F);
     setRotation(leftspine3, -15F/radianF, 0F, 0F);
     leftearskin.addChild(leftspine3);     

     //helmet
     ironhelmethorn1 = new ModelRenderer(this, 106, 139);
     ironhelmethorn1.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
     ironhelmethorn1.setRotationPoint(-0.5F, 0F, 0.1F);
     leftspine1.addChild(ironhelmethorn1);
     
     ironhelmethorn2 = new ModelRenderer(this, 106, 128);
     ironhelmethorn2.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
     ironhelmethorn2.setRotationPoint(0.5F, 0F, 0.1F);
     rightspine1.addChild(ironhelmethorn2);
     
     ironhelmet = new ModelRenderer(this, 32, 128);
     ironhelmet.addBox(-4F, -4F, -9F, 8, 4, 9);
     ironhelmet.setRotationPoint(0F, 0F, 0F);
     head.addChild(ironhelmet);
     
     ironhelmetsnout = new ModelRenderer(this, 0, 144);
     ironhelmetsnout.addBox(-2.5F, -2F, -7F, 5, 2, 7);
     ironhelmetsnout.setRotationPoint(0F, 0F, -1F);
     snout.addChild(ironhelmetsnout);
     
     goldhelmethorn1 = new ModelRenderer(this, 106, 161);
     goldhelmethorn1.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
     goldhelmethorn1.setRotationPoint(-0.5F, 0F, 0.1F);
     leftspine1.addChild(goldhelmethorn1);

     goldhelmethorn2 = new ModelRenderer(this, 106, 150);
     goldhelmethorn2.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
     goldhelmethorn2.setRotationPoint(0.5F, 0F, 0.1F);
     rightspine1.addChild(goldhelmethorn2);

     goldhelmet = new ModelRenderer(this, 94, 226);
     goldhelmet.addBox(-4F, -4F, -9F, 8, 4, 9);
     goldhelmet.setRotationPoint(0F, 0F, 0F);
     head.addChild(goldhelmet);

     goldhelmetsnout = new ModelRenderer(this, 71, 235);
     goldhelmetsnout.addBox(-2.5F, -2F, -7F, 5, 2, 7);
     goldhelmetsnout.setRotationPoint(0F, 0F, -1F);
     snout.addChild(goldhelmetsnout);

     diamondhelmet = new ModelRenderer(this, 23, 226);
     diamondhelmet.addBox(-4F, -4F, -9F, 8, 4, 9);
     diamondhelmet.setRotationPoint(0F, 0F, 0F);
     head.addChild(diamondhelmet);

     diamondhelmethorn2 = new ModelRenderer(this, 49, 234);
     diamondhelmethorn2.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
     diamondhelmethorn2.setRotationPoint(0.5F, 0F, 0.1F);
     rightspine1.addChild(diamondhelmethorn2);

     diamondhelmethorn1 = new ModelRenderer(this, 49, 245);
     diamondhelmethorn1.addBox(-1.5F, -1.5F, 0F, 3, 3, 8);
     diamondhelmethorn1.setRotationPoint(-0.5F, 0F, 0.1F);
     leftspine1.addChild(diamondhelmethorn1);

     diamondhelmetsnout = new ModelRenderer(this, 0, 235);
     diamondhelmetsnout.addBox(-2.5F, -2F, -7F, 5, 2, 7);
     diamondhelmetsnout.setRotationPoint(0F, 0F, -1F);
     snout.addChild(diamondhelmetsnout);
     
     torso = new ModelRenderer(this, 0, 0);
     torso.addBox(-5F, 0F, -12F, 10, 10, 12);
     torso.setRotationPoint(0F, 0F, 0F);

     saddle = new ModelRenderer(this, 38, 70);
     saddle.addBox(-3.5F, -2.5F, -8F, 7, 3, 10);
     saddle.setRotationPoint(0F, 0F, 0F);
     
     rightshoulder = new ModelRenderer(this, 42, 83);
     rightshoulder.addBox(-6F, 1F, -12.5F, 4, 5, 5);
     rightshoulder.setRotationPoint(0F, 0F, 0F);
     setRotation(rightshoulder, -0.2617994F, 0F, 0F);

     leftshoulder = new ModelRenderer(this, 24, 83);
     leftshoulder.addBox(2F, 1F, -12.5F, 4, 5, 5);
     leftshoulder.setRotationPoint(0F, 0F, 0F);
     setRotation(leftshoulder, -0.2617994F, 0F, 0F);

     //left wing
     LeftWing = new ModelRenderer(this);
     LeftWing.setRotationPoint(4F, 1F, -11F);

     leftuparm = new ModelRenderer(this, 44, 14);
     leftuparm.addBox(0F, -2F, -2F, 10, 4, 4);
     leftuparm.setRotationPoint(0F, 0F, 0F);
     setRotation(leftuparm, 0F, -10F/radianF, 0F);
     LeftWing.addChild(leftuparm);

     leftlowarm = new ModelRenderer(this, 72, 14);
     leftlowarm.addBox(0F, -2F, -2F, 10, 4, 4);
     leftlowarm.setRotationPoint(9F, 0F, 0F);
     setRotation(leftlowarm, 0F, 10F/radianF, 0F);
     leftuparm.addChild(leftlowarm);

     leftfing1a = new ModelRenderer(this, 52, 30);
     leftfing1a.addBox(0F, 0F, -1F, 2, 15, 2);
     leftfing1a.setRotationPoint(9F, 1F, 0F);
     setRotation(leftfing1a, 90F/radianF, 70F/radianF, 0F);
     leftlowarm.addChild(leftfing1a);

     leftfing1b = new ModelRenderer(this, 52, 47);
     leftfing1b.addBox(0F, 0F, -1F, 2, 10, 2);
     leftfing1b.setRotationPoint(0F, 14F, 0F);
     setRotation(leftfing1b, 0F, 0F, 35F/radianF);
     leftfing1a.addChild(leftfing1b);

     leftfing2a = new ModelRenderer(this, 44, 30);
     leftfing2a.addBox(-1F, 0F, 0F, 2, 15, 2);
     leftfing2a.setRotationPoint(9F, 1F, 0F);
     setRotation(leftfing2a, 90F/radianF, 35F/radianF, 0F);
     leftlowarm.addChild(leftfing2a);

     leftfing2b = new ModelRenderer(this, 44, 47);
     leftfing2b.addBox(-1F, 0F, 0F, 2, 10, 2);
     leftfing2b.setRotationPoint(0F, 14F, 0F);
     setRotation(leftfing2b, 0F, 0F, 30F/radianF);
     leftfing2a.addChild(leftfing2b);

     leftfing3a = new ModelRenderer(this, 36, 30);
     leftfing3a.addBox(-1F, 0F, 1F, 2, 15, 2);
     leftfing3a.setRotationPoint(9F, 1F, 0F);
     setRotation(leftfing3a, 90F/radianF, -5F/radianF, 0F);
     leftlowarm.addChild(leftfing3a);

     leftfing3b = new ModelRenderer(this, 36, 47);
     leftfing3b.addBox(-1F, 0F, 1F, 2, 10, 2);
     leftfing3b.setRotationPoint(0F, 14F, 0F);
     setRotation(leftfing3b, 0F, 0F, 30F/radianF);
     leftfing3a.addChild(leftfing3b);

     leftwingflap1 = new ModelRenderer(this, 74, 153);
     leftwingflap1.addBox(3.5F, -3F, 0.95F, 14, 24, 0);
     leftwingflap1.setRotationPoint(0F, 0F, 0F);
     setRotation(leftwingflap1, 0F, 0F, 70F/radianF);
     leftfing1a.addChild(leftwingflap1);

     leftwingflap2 = new ModelRenderer(this, 36, 153);
     leftwingflap2.addBox(-7F, 1.05F, 1.05F, 19, 24, 0);
     leftwingflap2.setRotationPoint(0F, 0F, 0F);
     setRotation(leftwingflap2, 0F, 0F, 40F/radianF);
     leftfing2a.addChild(leftwingflap2);

     leftwingflap3 = new ModelRenderer(this, 0, 153);
     leftwingflap3.addBox(-17.5F, 1F, 1.1F, 18, 24, 0);
     leftwingflap3.setRotationPoint(0F, 0F, 0F);
     leftfing3a.addChild(leftwingflap3);

     //right wing
         RightWing = new ModelRenderer(this);
         RightWing.setRotationPoint(-4F, 1F, -11F);

         rightuparm = new ModelRenderer(this, 44, 22);
         rightuparm.addBox(-10F, -2F, -2F, 10, 4, 4);
         rightuparm.setRotationPoint(0F, 0F, 0F);
         setRotation(rightuparm, 0F, 10F/radianF, 0F);
         RightWing.addChild(rightuparm);

         rightlowarm = new ModelRenderer(this, 72, 22);
         rightlowarm.addBox(-10F, -2F, -2F, 10, 4, 4);
         rightlowarm.setRotationPoint(-9F, 0F, 0F);
         setRotation(rightlowarm, 0F, -10F/radianF, 0F);
         rightuparm.addChild(rightlowarm);

         rightfing1a = new ModelRenderer(this, 36, 30);
         rightfing1a.addBox(-1F, 0F, -1F, 2, 15, 2);
         rightfing1a.setRotationPoint(-9F, 1F, -1F);
         setRotation(rightfing1a, 90F/radianF, -70F/radianF, 0F);
         rightlowarm.addChild(rightfing1a);

         rightfing1b = new ModelRenderer(this, 36, 47);
         rightfing1b.addBox(-1F, 0F, -1F, 2, 10, 2);
         rightfing1b.setRotationPoint(0F, 14F, 0F);
         setRotation(rightfing1b, 0F, 0F, -35F/radianF);
         rightfing1a.addChild(rightfing1b);

         rightwingflap1 = new ModelRenderer(this, 74, 177);
         rightwingflap1.addBox(-17.5F, -3F, 0.95F, 14, 24, 0);
         rightwingflap1.setRotationPoint(0F, 0F, 0F);
         setRotation(rightwingflap1, 0F, 0F, -70F/radianF);
         rightfing1a.addChild(rightwingflap1);
         
         rightfing2a = new ModelRenderer(this, 44, 30);
         rightfing2a.addBox(-1F, 0F, 0F, 2, 15, 2);
         rightfing2a.setRotationPoint(-9F, 1F, 0F);
         setRotation(rightfing2a, 90F/radianF, -35F/radianF, 0F);
         rightlowarm.addChild(rightfing2a);

         rightfing2b = new ModelRenderer(this, 44, 47);
         rightfing2b.addBox(-1F, 0F, 0F, 2, 10, 2);
         rightfing2b.setRotationPoint(0F, 14F, 0F);
         setRotation(rightfing2b, 0F, 0F, -30F/radianF);
         rightfing2a.addChild(rightfing2b);

         rightwingflap2 = new ModelRenderer(this, 36, 177);
         rightwingflap2.addBox(-19F, 1.05F, 1.05F, 19, 24, 0);
         rightwingflap2.setRotationPoint(0F, 0F, 0F);
         setRotation(rightwingflap2, 0F, 0F, -40F/radianF);
         rightfing2a.addChild(rightwingflap2);
         
         rightfing3a = new ModelRenderer(this, 52, 30);
         rightfing3a.addBox(-1F, 0F, 1F, 2, 15, 2);
         rightfing3a.setRotationPoint(-9F, 1F, 0F);
         setRotation(rightfing3a, 90F/radianF, 5F/radianF, 0F);
         rightlowarm.addChild(rightfing3a);

         rightfing3b = new ModelRenderer(this, 52, 47);
         rightfing3b.addBox(-1F, 0F, 1F, 2, 10, 2);
         rightfing3b.setRotationPoint(0F, 14F, 0F);
         setRotation(rightfing3b, 0F, 0F, -30F/radianF);
         rightfing3a.addChild(rightfing3b);
         
         rightwingflap3 = new ModelRenderer(this, 0, 177);
         rightwingflap3.addBox(-0.5F, 1F, 1.1F, 18, 24, 0);
         rightwingflap3.setRotationPoint(0F, 0F, 0F);
         rightfing3a.addChild(rightwingflap3);
     
     
     
        //legs
         leftupleg = new ModelRenderer(this, 0, 111);
         leftupleg.addBox(-2F, -3F, -3F, 4, 10, 7);
         leftupleg.setRotationPoint(5F, 6F, -5F);
         setRotation(leftupleg, -25F/radianF, 0F, 0F);

         leftmidleg = new ModelRenderer(this, 0, 102);
         leftmidleg.addBox(-1.5F, -2F, 0F, 3, 4, 5);
         leftmidleg.setRotationPoint(0F, 5F, 4F);
         leftupleg.addChild(leftmidleg);

         leftlowleg = new ModelRenderer(this, 0, 91);
         leftlowleg.addBox(-1.5F, 0F, -1.5F, 3, 8, 3);
         leftlowleg.setRotationPoint(0F, 2F, 3.5F);
         leftmidleg.addChild(leftlowleg);
         
         leftfoot = new ModelRenderer(this, 44, 121);
         leftfoot.addBox(-2F, -1F, -3F, 4, 3, 4);
         leftfoot.setRotationPoint(0F, 7F, 0.5F);
         setRotation(leftfoot, 25F/radianF, 0F, 0F);
         leftlowleg.addChild(leftfoot);
         
         lefttoe1 = new ModelRenderer(this, 96, 35);
         lefttoe1.addBox(-0.5F, -1F, -3F, 1, 2, 3);
         lefttoe1.setRotationPoint(-1.5F, 1F, -3F);
         leftfoot.addChild(lefttoe1);
         
         lefttoe3 = new ModelRenderer(this, 96, 30);
         lefttoe3.addBox(-0.5F, -1F, -3F, 1, 2, 3);
         lefttoe3.setRotationPoint(1.5F, 1F, -3F);
         leftfoot.addChild(lefttoe3);
         
         lefttoe2 = new ModelRenderer(this, 84, 30);
         lefttoe2.addBox(-1F, -1.5F, -4F, 2, 3, 4);
         lefttoe2.setRotationPoint(0F, 0.5F, -3F);
         leftfoot.addChild(lefttoe2);

         

         leftclaw1 = new ModelRenderer(this, 100, 26);
         leftclaw1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
         leftclaw1.setRotationPoint(0.5F, -0.5F, -2.5F);
         setRotation(leftclaw1, -25F/radianF, 0F, 0F);
         lefttoe1.addChild(leftclaw1);
         
         leftclaw2 = new ModelRenderer(this, 100, 26);
         leftclaw2.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
         leftclaw2.setRotationPoint(0F, -1F, -3.5F);
         setRotation(leftclaw2, -25F/radianF, 0F, 0F);
         lefttoe2.addChild(leftclaw2);
         
         leftclaw3 = new ModelRenderer(this, 100, 26);
         leftclaw3.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
         leftclaw3.setRotationPoint(-0.5F, -0.5F, -2.5F);
         setRotation(leftclaw3, -25F/radianF, 0F, 0F);
         lefttoe3.addChild(leftclaw3);
         
         ironleftlegarmor = new ModelRenderer(this, 39, 97);
         ironleftlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
         ironleftlegarmor.setRotationPoint(0F, 2.5F, 0F);
         leftlowleg.addChild(ironleftlegarmor);
         
         goldleftlegarmor = new ModelRenderer(this, 112, 181);
         goldleftlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
         goldleftlegarmor.setRotationPoint(0F, 2.5F, 0F);
         leftlowleg.addChild(goldleftlegarmor);
         
         diamondleftlegarmor = new ModelRenderer(this, 43, 215);
         diamondleftlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
         diamondleftlegarmor.setRotationPoint(0F, 2.5F, 0F);
         leftlowleg.addChild(diamondleftlegarmor);
         
         //right leg
         rightupleg = new ModelRenderer(this, 0, 111);
         rightupleg.addBox(-2F, -3F, -3F, 4, 10, 7);
         rightupleg.setRotationPoint(-5F, 6F, -5F);
         setRotation(rightupleg, -25F/radianF, 0F, 0F);

         rightmidleg = new ModelRenderer(this, 0, 102);
         rightmidleg.addBox(-1.5F, -2F, 0F, 3, 4, 5);
         rightmidleg.setRotationPoint(0F, 5F, 4F);
         rightupleg.addChild(rightmidleg);

         rightlowleg = new ModelRenderer(this, 0, 91);
         rightlowleg.addBox(-1.5F, 0F, -1.5F, 3, 8, 3);
         rightlowleg.setRotationPoint(0F, 2F, 3.5F);
         rightmidleg.addChild(rightlowleg);
         
         rightfoot = new ModelRenderer(this, 44, 121);
         rightfoot.addBox(-2F, -1F, -3F, 4, 3, 4);
         rightfoot.setRotationPoint(0F, 7F, 0.5F);
         setRotation(rightfoot, 25F/radianF, 0F, 0F);
         rightlowleg.addChild(rightfoot);
         
         righttoe1 = new ModelRenderer(this, 96, 35);
         righttoe1.addBox(-0.5F, -1F, -3F, 1, 2, 3);
         righttoe1.setRotationPoint(-1.5F, 1F, -3F);
         rightfoot.addChild(righttoe1);
         
         righttoe3 = new ModelRenderer(this, 96, 30);
         righttoe3.addBox(-0.5F, -1F, -3F, 1, 2, 3);
         righttoe3.setRotationPoint(1.5F, 1F, -3F);
         rightfoot.addChild(righttoe3);
         
         righttoe2 = new ModelRenderer(this, 84, 30);
         righttoe2.addBox(-1F, -1.5F, -4F, 2, 3, 4);
         righttoe2.setRotationPoint(0F, 0.5F, -3F);
         rightfoot.addChild(righttoe2);

         

         rightclaw1 = new ModelRenderer(this, 100, 26);
         rightclaw1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
         rightclaw1.setRotationPoint(0.5F, -0.5F, -2.5F);
         setRotation(rightclaw1, -25F/radianF, 0F, 0F);
         righttoe1.addChild(rightclaw1);
         
         rightclaw2 = new ModelRenderer(this, 100, 26);
         rightclaw2.addBox(-0.5F, 0F, -0.5F, 1, 3, 1);
         rightclaw2.setRotationPoint(0F, -1F, -3.5F);
         setRotation(rightclaw2, -25F/radianF, 0F, 0F);
         righttoe2.addChild(rightclaw2);
         
         rightclaw3 = new ModelRenderer(this, 100, 26);
         rightclaw3.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
         rightclaw3.setRotationPoint(-0.5F, -0.5F, -2.5F);
         setRotation(rightclaw3, -25F/radianF, 0F, 0F);
         righttoe3.addChild(rightclaw3);
         
         ironrightlegarmor = new ModelRenderer(this, 39, 97);
         ironrightlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
         ironrightlegarmor.setRotationPoint(0F, 2.5F, 0F);
         rightlowleg.addChild(ironrightlegarmor);
         
         goldrightlegarmor = new ModelRenderer(this, 112, 181);
         goldrightlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
         goldrightlegarmor.setRotationPoint(0F, 2.5F, 0F);
         rightlowleg.addChild(goldrightlegarmor);
         
         diamondrightlegarmor = new ModelRenderer(this, 43, 215);
         diamondrightlegarmor.addBox(-2F, -2.5F, -2F, 4, 5, 4);
         diamondrightlegarmor.setRotationPoint(0F, 2.5F, 0F);
         rightlowleg.addChild(diamondrightlegarmor);
    
     storage = new ModelRenderer(this, 28, 59);
     storage.addBox(-5F, -4.5F, 1.5F, 10, 5, 6);
     storage.setRotationPoint(0F, 0F, 0F);
     setRotation(storage, -0.2268928F, 0F, 0F);
     
     chestbelt = new ModelRenderer(this, 0, 201);
     chestbelt.addBox(-5.5F, -0.5F, -9F, 11, 11, 2);
     chestbelt.setRotationPoint(0F, 0F, 0F);

     stomachbelt = new ModelRenderer(this, 0, 201);
     stomachbelt.addBox(-5.5F, -0.5F, -3F, 11, 11, 2);
     stomachbelt.setRotationPoint(0F, 0F, 0F);

     ironchestarmor = new ModelRenderer(this, 0, 128);
     ironchestarmor.addBox(-5.5F, 2.2F, -13.5F, 11, 11, 5);
     ironchestarmor.setRotationPoint(0F, 0F, 0F);
     setRotation(ironchestarmor, -0.2602503F, 0F, 0F);

     ironrightshoulderpad = new ModelRenderer(this, 74, 201);
     ironrightshoulderpad.addBox(-6.5F, 0.5F, -13F, 5, 6, 6);
     ironrightshoulderpad.setRotationPoint(0F, 0F, 0F);
     setRotation(ironrightshoulderpad, -0.2617994F, 0F, 0F);

     ironleftshoulderpad = new ModelRenderer(this, 26, 201);
     ironleftshoulderpad.addBox(1.5F, 0.5F, -13F, 5, 6, 6);
     ironleftshoulderpad.setRotationPoint(0F, 0F, 0F);
     setRotation(ironleftshoulderpad, -0.2617994F, 0F, 0F);

     goldleftshoulder = new ModelRenderer(this, 71, 244);
     goldleftshoulder.addBox(1.5F, 0.5F, -13F, 5, 6, 6);
     goldleftshoulder.setRotationPoint(0F, 0F, 0F);
     setRotation(goldleftshoulder, -0.2617994F, 0F, 0F);

     goldchestarmor = new ModelRenderer(this, 71, 219);
     goldchestarmor.addBox(-5.5F, 2.2F, -13.5F, 11, 11, 5);
     goldchestarmor.setRotationPoint(0F, 0F, 0F);
     setRotation(goldchestarmor, -0.2602503F, 0F, 0F);

     goldrightshoulder = new ModelRenderer(this, 93, 244);
     goldrightshoulder.addBox(-6.5F, 0.5F, -13F, 5, 6, 6);
     goldrightshoulder.setRotationPoint(0F, 0F, 0F);
     setRotation(goldrightshoulder, -0.2617994F, 0F, 0F);
     
     diamondleftshoulder = new ModelRenderer(this, 0, 244);
     diamondleftshoulder.addBox(1.5F, 0.5F, -13F, 5, 6, 6);
     diamondleftshoulder.setRotationPoint(0F, 0F, 0F);
     setRotation(diamondleftshoulder, -0.2617994F, 0F, 0F);

     diamondrightshoulder = new ModelRenderer(this, 22, 244);
     diamondrightshoulder.addBox(-6.5F, 0.5F, -13F, 5, 6, 6);
     diamondrightshoulder.setRotationPoint(0F, 0F, 0F);
     setRotation(diamondrightshoulder, -0.2617994F, 0F, 0F);

     diamondchestarmor = new ModelRenderer(this, 0, 219);
     diamondchestarmor.addBox(-5.5F, 2.2F, -13.5F, 11, 11, 5);
     diamondchestarmor.setRotationPoint(0F, 0F, 0F);
     setRotation(diamondchestarmor, -0.2602503F, 0F, 0F);
 }

public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
{
    //super.render(entity, f, f1, f2, f3, f4, f5);

    MoCEntityWyvern wyvern = (MoCEntityWyvern) entity;
    int armor = wyvern.getArmorType();
    boolean isRidden = (wyvern.riddenByEntity != null);
    boolean isChested = wyvern.getIsChested();
    boolean isSaddled = wyvern.getIsRideable();
    boolean flapwings = (wyvern.wingFlapCounter != 0);
    boolean onAir = wyvern.isOnAir() || wyvern.getIsFlying();
    boolean diving = (wyvern.diveCounter != 0);
    boolean isSitting = wyvern.getIsSitting();
    //boolean isFlying = wyvern.getIsFlying();
    int openMouth = wyvern.mouthCounter;
    //TODO diving
    
    
    setRotationAngles(f, f1, f2, f3, f4, f5, onAir, flapwings, isRidden, openMouth, diving, isSitting);
    back1.render(f5);
    Tail.render(f5);
    
    chest.render(f5);
    
    LeftWing.render(f5);
    RightWing.render(f5);
    
    rightshoulder.render(f5);
    leftshoulder.render(f5);

    neckplate3.render(f5);
    neck3.render(f5);
    torso.render(f5);
    
    
    

    
    if (isChested)
    {
        storage.render(f5);
    }
    
    if (isSaddled)
    {
        saddle.render(f5);
        mouthrod.isHidden = false; //render(f5);
        helmetstrap1.isHidden = false; //render(f5);
        helmetstrap2.isHidden = false; //render(f5);
        chestbelt.render(f5);
        stomachbelt.render(f5);
        
        if (isRidden)
        {
            controlrope1.isHidden = false;
            controlrope2.isHidden = false;
        }else
        {
            controlrope1.isHidden = true;
            controlrope2.isHidden = true;
        }
    }
    else
    {
        mouthrod.isHidden = true; 
        helmetstrap1.isHidden = true; 
        helmetstrap2.isHidden = true;
    }
    
    
    ironhelmethorn1.isHidden = true;
    ironhelmethorn2.isHidden = true;
    ironhelmet.isHidden = true;
    ironhelmetsnout.isHidden = true;
    ironrightlegarmor.isHidden = true;
    ironleftlegarmor.isHidden = true;
    ironchestarmor.isHidden = true;
    ironrightshoulderpad.isHidden = true;
    ironleftshoulderpad.isHidden = true;
    
    goldleftshoulder.isHidden = true;
    goldchestarmor.isHidden = true;
    goldrightshoulder.isHidden = true;
    goldleftlegarmor.isHidden = true;
    goldrightlegarmor.isHidden = true;
    goldhelmethorn1.isHidden = true;
    goldhelmethorn2.isHidden = true;
    goldhelmet.isHidden = true;
    goldhelmetsnout.isHidden = true;
    
    diamondleftshoulder.isHidden = true;
    diamondrightshoulder.isHidden = true;
    diamondchestarmor.isHidden = true;
    diamondleftlegarmor.isHidden = true;
    diamondrightlegarmor.isHidden = true;
    diamondhelmet.isHidden = true;
    diamondhelmethorn2.isHidden = true;
    diamondhelmethorn1.isHidden = true;
    diamondhelmetsnout.isHidden = true;
    
    switch (armor)
    {
    case 1:
        ironhelmethorn1.isHidden = false;
        ironhelmethorn2.isHidden = false;
        ironhelmet.isHidden = false;
        ironhelmetsnout.isHidden = false;
        ironrightlegarmor.isHidden = false;
        ironleftlegarmor.isHidden = false;
        ironchestarmor.isHidden = false;
        ironrightshoulderpad.isHidden = false;
        ironleftshoulderpad.isHidden = false;
        break;
    case 2:
        goldleftshoulder.isHidden = false;
        goldchestarmor.isHidden = false;
        goldrightshoulder.isHidden = false;
        goldleftlegarmor.isHidden = false;
        goldrightlegarmor.isHidden = false;
        goldhelmethorn1.isHidden = false;
        goldhelmethorn2.isHidden = false;
        goldhelmet.isHidden = false;
        goldhelmetsnout.isHidden = false;
        break;
    case 3:
        diamondleftshoulder.isHidden = false;
        diamondrightshoulder.isHidden = false;
        diamondchestarmor.isHidden = false;
        diamondleftlegarmor.isHidden = false;
        diamondrightlegarmor.isHidden = false;
        diamondhelmet.isHidden = false;
        diamondhelmethorn2.isHidden = false;
        diamondhelmethorn1.isHidden = false;
        diamondhelmetsnout.isHidden = false;
        break;
    
    }
    
    MainHead.render(f5);
    leftupleg.render(f5);
    rightupleg.render(f5);
    
    ironchestarmor.render(f5);
    ironrightshoulderpad.render(f5);
    ironleftshoulderpad.render(f5);
    goldleftshoulder.render(f5);
    goldchestarmor.render(f5);
    goldrightshoulder.render(f5);
    diamondleftshoulder.render(f5);
    diamondrightshoulder.render(f5);
    diamondchestarmor.render(f5);
    
}

private void setRotation(ModelRenderer model, float x, float y, float z)
{
 model.rotateAngleX = x;
 model.rotateAngleY = y;
 model.rotateAngleZ = z;
}

public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean onAir, boolean flapwings, boolean rider, int openMouth, boolean diving, boolean sitting)
{
    float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
    float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
    
    f3 = MoCTools.realAngle(f3);
    
    float f10 = 60F;
    if (f3 > f10)
    {
        f3 = f10;
        //f3 = 0;
    }
    if (f3 < -f10)
    {
        f3 = -f10;
        //f3 = 0;
    }
    //System.out.println(f3);
    //TODO Ssegmented neck/head rotation x - y DONE (kinda)
    //shoulders movement contrary to legs while walking DONE
    //leg walking movement DONE
    //leg flying movement DONE
    //head bobbling?
    //wings floating, flapping, DONE
    //open mouth + flap ears when making sounds
    //remove head twisting when ridden
    //wing diving
    
    neck2.rotateAngleX = -66F/radianF + (f4 * 1/3F/ radianF);
    neck1.rotateAngleX = 30F/radianF + (f4 * 2/3F/ radianF);
    
    head.rotateAngleX = 45F/radianF;

    neck2.rotateAngleY =  (f3 * 2/3F)/radianF;
    neck1.rotateAngleY = (f3 * 1/3F)/radianF;
    

    head.rotateAngleY = 0F;
    head.rotateAngleZ = 0F;

    if (rider)
    {
        //straight neck when mounted
        neck1.rotateAngleY = 0F;
        neck2.rotateAngleY = 0F;
        
        if (onAir)
        {
            neck1.rotateAngleX = 0F;
            neck2.rotateAngleX = 0F;
            
        }else
        {
            
            neck2.rotateAngleX = -66F/radianF + (RLegXRot * 1/60F);
            neck1.rotateAngleX = 30F/radianF + (RLegXRot * 2/60F);
        }
    }
    float TailXRot = MathHelper.cos(f * 0.4F) * 0.2F * f1;
    tail1.rotateAngleX = (-19F /radianF);// - TailXRot;
    
    tail2.rotateAngleX = (-16F /radianF);// + TailXRot;
    tail3.rotateAngleX = (7F /radianF);// + TailXRot;
    tail4.rotateAngleX = (11F /radianF);// + TailXRot;
    tail5.rotateAngleX = (8F /radianF);
    
    float t = f / 2;
    
    /*if (movetail)
    {
        t = f2/4F;
    }*/
    float A = 0.15F;//0.8F;
    float w = 0.9F;
    float k = 0.6F;
    
    int i = 0;
    float tailLat = 0F;
    tailLat = A * MathHelper.sin(w * t - k * (float) i++);
    tail1.rotateAngleY = tailLat;
    tailLat = A * MathHelper.sin(w * t - k * (float) i++);
    tail2.rotateAngleY = tailLat;
    tailLat = A * MathHelper.sin(w * t - k * (float) i++);
    tail3.rotateAngleY = tailLat;
    tailLat = A * MathHelper.sin(w * t - k * (float) i++);
    tail4.rotateAngleY = tailLat;
    tailLat = A * MathHelper.sin(w * t - k * (float) i++);
    tail5.rotateAngleY = tailLat;
    
    
    float WingSpread = MathHelper.cos(f * 0.3F) * 0.9F * f1;
    
        /**
         * flapping wings or cruising. IF flapping wings, move up and down.
         * if cruising, movement depends of speed
         */
        //float WingRot = 0F;
        if (flapwings)
        {
            WingSpread = MathHelper.cos((f2 * 0.3F) + 3.141593F) * 1.2F;// * f1;
        }
        else
        //cruising
        {
            //WingRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.2F * f1;
            WingSpread = MathHelper.cos((f * 0.5F)) * 0.1F;//* 1.2F * f1;
        }

                    


    
    if (onAir)
    {
        //rightfing1a.rotateAngleY = -70F/radianF+ WingSpread;
        
        float speedMov = (f1 * 0.5F);
        
        leftuparm.rotateAngleZ = WingSpread * 2/3F;
        rightuparm.rotateAngleZ = - WingSpread *2/3F;
        leftlowarm.rotateAngleZ = WingSpread * 0.1F;
        leftfing1a.rotateAngleZ = WingSpread * 1F;
        leftfing2a.rotateAngleZ = WingSpread * 0.8F;
        
        rightlowarm.rotateAngleZ = -WingSpread * 0.1F;
        rightfing1a.rotateAngleZ = -WingSpread * 1F;
        rightfing2a.rotateAngleZ = -WingSpread * 0.8F;
        
        //System.out.println("WingSpread = " + WingSpread);
        leftuparm.rotateAngleY = -10F/radianF - (WingSpread / 2.5F);
        leftlowarm.rotateAngleY = 15F/radianF + (WingSpread / 2.5F);
        leftfing1a.rotateAngleY = 70F/radianF;
        leftfing2a.rotateAngleY = 35F/radianF;
        leftfing3a.rotateAngleY = -5F/radianF;
        rightuparm.rotateAngleY = 10F/radianF + (WingSpread / 2.5F);
        rightlowarm.rotateAngleY = -15F/radianF - (WingSpread / 2.5F);
        rightfing1a.rotateAngleY = -70F/radianF;
        rightfing2a.rotateAngleY = -35F/radianF;
        rightfing3a.rotateAngleY = 5F/radianF;
        
        leftupleg.rotateAngleX = speedMov;
        leftmidleg.rotateAngleX = speedMov;
        leftfoot.rotateAngleX = 25F/radianF;
        lefttoe1.rotateAngleX = speedMov;
        lefttoe2.rotateAngleX = speedMov;
        lefttoe3.rotateAngleX = speedMov;
        rightfoot.rotateAngleX = 25F/radianF;
        rightupleg.rotateAngleX = speedMov;
        rightmidleg.rotateAngleX = speedMov;
        righttoe1.rotateAngleX = speedMov;
        righttoe2.rotateAngleX = speedMov;
        righttoe3.rotateAngleX = speedMov;
        
        
    }else
    {
        
        leftlowarm.rotateAngleZ = 0F;
        leftfing1a.rotateAngleZ = 0F;
        leftfing2a.rotateAngleZ = 0F;
        rightlowarm.rotateAngleZ = 0F;
        rightfing1a.rotateAngleZ = 0F;
        rightfing2a.rotateAngleZ = 0F;
        //leftuparm.rotateAngleX = RLegXRot;
        //rightuparm.rotateAngleX = LLegXRot;
        
        leftuparm.rotateAngleZ = 30F/radianF ;
        leftuparm.rotateAngleY = -60F/radianF + (LLegXRot /5F);
        
        leftlowarm.rotateAngleY = 105F/radianF;
        leftfing1a.rotateAngleY = -20F/radianF;
        leftfing2a.rotateAngleY = -26F/radianF;
        leftfing3a.rotateAngleY = -32F/radianF;
        
        rightuparm.rotateAngleY = 60F/radianF - (RLegXRot /5F);
        rightuparm.rotateAngleZ = -30F/radianF;
        
        rightlowarm.rotateAngleY = -105F/radianF;
        rightfing1a.rotateAngleY = 16F/radianF;
        rightfing2a.rotateAngleY = 26F/radianF;
        rightfing3a.rotateAngleY = 32F/radianF;
        
        //test mouth opening
        
        
        leftupleg.rotateAngleX = -25F/radianF + LLegXRot;
        rightupleg.rotateAngleX = -25F/radianF + RLegXRot;
        
        
        leftmidleg.rotateAngleX = 0F;
        leftlowleg.rotateAngleX = 0F;
        leftfoot.rotateAngleX = 25F/radianF - (LLegXRot);
        lefttoe1.rotateAngleX = LLegXRot;
        lefttoe2.rotateAngleX = LLegXRot;
        lefttoe3.rotateAngleX = LLegXRot;
        
        
        rightmidleg.rotateAngleX = 0F;
        rightlowleg.rotateAngleX = 0F;
        rightfoot.rotateAngleX = 25F/radianF - (RLegXRot);
        righttoe1.rotateAngleX = RLegXRot;
        righttoe2.rotateAngleX = RLegXRot;
        righttoe3.rotateAngleX = RLegXRot;
        

    }
    
    if (sitting)
    {
        leftupleg.rotateAngleX = 45F/radianF + LLegXRot;
        rightupleg.rotateAngleX = 45F/radianF + RLegXRot;
        leftmidleg.rotateAngleX = 30F;
        rightmidleg.rotateAngleX = 30F;
        neck2.rotateAngleX = -36F/radianF + (f4 * 1/3F/ radianF);
        neck1.rotateAngleX = 30F/radianF + (f4 * 2/3F/ radianF);
    }
    if (diving)
    {
        leftuparm.rotateAngleZ = -40F/radianF;
        rightuparm.rotateAngleZ = 40F/radianF;
        leftlowarm.rotateAngleZ = 0F;
        leftfing1a.rotateAngleZ = 0F;
        leftfing2a.rotateAngleZ = 0F;
        
        rightlowarm.rotateAngleZ = 0F;
        rightfing1a.rotateAngleZ = 0F;
        rightfing2a.rotateAngleZ = 0F;
        
        //leftuparm.rotateAngleZ = 30F/radianF ;
        leftuparm.rotateAngleY = -50F/radianF;// + (LLegXRot /5F);
        leftlowarm.rotateAngleY = 30F/radianF;
        leftfing1a.rotateAngleY = 50F/radianF;
        leftfing2a.rotateAngleY = 30F/radianF;
        leftfing3a.rotateAngleY = 10F/radianF;
        
        rightuparm.rotateAngleY = 50F/radianF;// - (RLegXRot /5F);
        //rightuparm.rotateAngleZ = -30F/radianF;
        
        rightlowarm.rotateAngleY = -30F/radianF;
        rightfing1a.rotateAngleY = -50F/radianF;
        rightfing2a.rotateAngleY = -30F/radianF;
        rightfing3a.rotateAngleY = -10F/radianF;
    }
    
   if (openMouth != 0)
   {
       float mouthMov = (MathHelper.cos((openMouth-15) * 0.11F) * 0.8F);
       Jaw.rotateAngleX = (-10F/radianF) + mouthMov;
       leftearskin.rotateAngleY = + mouthMov;
       rightearskin.rotateAngleY = - mouthMov;
       
   }else
   {
       Jaw.rotateAngleX = -10F/radianF;
       leftearskin.rotateAngleY = 0F;
       rightearskin.rotateAngleY = 0F;
   }
    
    
}

}
